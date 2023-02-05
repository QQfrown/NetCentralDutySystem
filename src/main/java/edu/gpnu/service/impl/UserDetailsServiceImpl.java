package edu.gpnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.gpnu.dao.UserDao;
import edu.gpnu.domain.LoginUser;
import edu.gpnu.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 *实现UserDetailsService接口，使其通过数据库查询用户
 *
 * @author 丘辛意
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根据用户名查询用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,username);
        User user = userDao.selectOne(wrapper);

        if (Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }

        List<String> permissions = new ArrayList<>();
        //数据库权限字符串使用 ； 隔开
        String[] permissionsString = userDao.selectPermsByUserId(user.getId()).split(";");
        for (String permission : permissionsString) {
            permissions.add(permission);
        }
        return new LoginUser(user,permissions);
    }
}
