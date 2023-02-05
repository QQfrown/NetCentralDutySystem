package edu.gpnu.service.impl;

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
}
