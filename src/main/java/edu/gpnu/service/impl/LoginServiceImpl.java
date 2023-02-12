package edu.gpnu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import edu.gpnu.dao.UserDao;
import edu.gpnu.domain.LoginUser;
import edu.gpnu.domain.ResponseResult;
import edu.gpnu.domain.User;
import edu.gpnu.service.LoginService;
import edu.gpnu.utils.HttpUtil;
import edu.gpnu.utils.JwtUtil;
import edu.gpnu.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.net.HttpURLConnection;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    private static String APP_ID = "wxf0044ed9e2135399";
    private static String APP_SECRET = "b85cf0b83c9284f7949f9d22337f1f22";

    @Resource
    private RedisCache redisCache;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDao userDao;

    @Override
    public ResponseResult<Map<String,Object>> login(User user) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(
                usernamePasswordAuthenticationToken);
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        User userInfo = loginUser.getUser();
        userInfo.setPassword(null);
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtil.createJWT(userId);
        Long expire = JwtUtil.JWT_TTL+(new Date()).getTime();

        //将已登录的用户信息存到redis中
        String key = "login:"+userId;
        redisCache.setCacheObject(key,loginUser);
        redisCache.expire(key,7, TimeUnit.DAYS);

        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("userInfo",userInfo);
        map.put("expire",expire);

        return new ResponseResult(HttpURLConnection.HTTP_OK,"登陆成功",map);
    }

    @Override
    public ResponseResult<Map<String, Object>> loginForWechat(String code, User user) {
        String jsonResult = HttpUtil.doGet("https://api.weixin.qq.com/sns/jscode2session",
                "appid=" + LoginServiceImpl.APP_ID + "&secret=" + LoginServiceImpl.APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code");
        JSONObject parseObject = JSON.parseObject(jsonResult);
        String openid = (String)parseObject.get("openid");
        ResponseResult<Map<String, Object>> result = this.login(user);
        if (result.getCode() == 200){
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",result.getData().get("userId"));
            updateWrapper.set("open_id",openid);
            userDao.update(null,updateWrapper);
        }
        return result;
    }

    @Override
    public ResponseResult logout(String token) {
        Claims claims;
        try {
             claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            return  new ResponseResult(HttpURLConnection.HTTP_FORBIDDEN,"token验证失败");
        }
        String userId = claims.getSubject();
        String key = "login:"+userId;
        redisCache.deleteObject(key);
        return new ResponseResult(HttpURLConnection.HTTP_OK,"成功退出登录");
    }
}
