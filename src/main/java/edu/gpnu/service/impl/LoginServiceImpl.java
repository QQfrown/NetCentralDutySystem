package edu.gpnu.service.impl;

import edu.gpnu.domain.LoginUser;
import edu.gpnu.domain.ResponseResult;
import edu.gpnu.domain.User;
import edu.gpnu.service.LoginService;
import edu.gpnu.utils.JwtUtil;
import edu.gpnu.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

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
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtil.createJWT(userId);

        //将已登录的用户信息存到redis中
        String key = "login:"+userId;
        redisCache.setCacheObject(key,loginUser);

        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("userId",userId);

        return new ResponseResult(HttpURLConnection.HTTP_OK,"登陆成功",map);
    }

    @Override
    public ResponseResult logout(String token) {
        Claims claims;
        if (!StringUtils.hasText(token)){
            return  new ResponseResult(HttpURLConnection.HTTP_FORBIDDEN,"未携带token");
        }
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
