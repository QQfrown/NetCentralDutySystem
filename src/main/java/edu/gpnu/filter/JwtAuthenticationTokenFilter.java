package edu.gpnu.filter;

import edu.gpnu.domain.LoginUser;
import edu.gpnu.utils.JwtUtil;
import edu.gpnu.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


/**
 * token过滤器，除去在SecurityConfig开放的接口，其他所有接口的请求都要在请求头携带token
 *
 * @author 丘辛意
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("token");
        Claims claims;
        if (!StringUtils.hasText(token)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        try {
            //我们是根据userId去创建的token，JWT解析可以获得userId
             claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            throw new RuntimeException("token非法");
        }
        String userId = claims.getSubject();
        String key = "login:"+userId;
        LoginUser loginUser = (LoginUser) redisCache.getCacheObject(key);
        if (Objects.isNull(loginUser)) {
            //无法在redis缓存中查询到
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            throw new RuntimeException("登陆超时");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        //将用户信息存放到SecurityContextHolder中，以后框架可能会调用到
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
