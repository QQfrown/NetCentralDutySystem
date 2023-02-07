package edu.gpnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import edu.gpnu.dao.UserDao;
import edu.gpnu.domain.User;
import edu.gpnu.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public Integer addUser(User user) {
        return userDao.insert(user);
    }

    @Override
    public Integer updateUserById(User user) {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id",user.getId());
        /*使用 mybatis plus 自带的update 的方法进行修改*/
//     int update(T updateEntity, Wrapper<T> whereWrapper);
        return  userDao.update(user,userUpdateWrapper);
    }

    @Override
    public Integer deleteUserById(String id) {
        return userDao.deleteById(id);
    }

    @Override
    public User getUserById(String id) {
        return userDao.selecUsertById(id);
    }

}
