package edu.gpnu.controller;

import edu.gpnu.domain.ResponseResult;
import edu.gpnu.domain.User;
import edu.gpnu.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * @登录
 *
 * ①自定义登录接口
 *
 * 			调用ProviderManager的方法进行认证 如果认证通过生成jwt
 *
 * 			把用户信息存入redis中
 *
 * ②自定义UserDetailsService
 *
 * 			在这个实现类中去查询数据库
 *
 * @校验：
 *
 *	①定义Jwt认证过滤器
 *
 *				获取token
 *
 *				解析token获取其中的userid
 *
 *				从redis中获取用户信息
 *
 *				存入SecurityContextHolder
 *
 *
 *
 * @具体接口作用：
 *
 * Authentication接口: 它的实现类，表示当前访问系统的用户，封装了用户相关信息。
 *
 * AuthenticationManager接口：定义了认证Authentication的方法
 *
 * UserDetailsService接口：加载用户特定数据的核心接口。里面定义了一个根据用户名查询用户信息的方法。
 *
 * UserDetails接口：提供核心用户信息。通过UserDetailsService根据用户名获取处理的用户信息要封装成UserDetails对象返回。
 * 然后将这些信息封装到Authentication对象中。
 *
 * @author 丘辛意
 */

@RestController
public class LoginController {

    @Autowired
    private LoginService loginServer;

    @PostMapping("/login")
    public ResponseResult<Map<String,Object>> login(@RequestBody User user){
        return loginServer.login(user);
    }

    @PostMapping("/wechatlogin")
    public ResponseResult<Map<String,Object>> loginForWechat(@RequestParam(name = "code") String code,@RequestBody User user){
        return loginServer.loginForWechat(code,user);
    }

    @GetMapping("/checktoken")
    public ResponseResult<Object> checkToken(){
        ResponseResult<Object> result = new ResponseResult<>();
        result.setCode(HttpURLConnection.HTTP_OK);
        result.setMsg("token 未过期");
        return result;
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public ResponseResult logout(@RequestBody HttpServletRequest request){
        String token = request.getHeader("token");
        return loginServer.logout(token);
    }
}
