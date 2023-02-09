package edu.gpnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import edu.gpnu.dao.RoleDao;
import edu.gpnu.dao.UserDao;
import edu.gpnu.dao.UserRoleDao;
import edu.gpnu.domain.User;
import edu.gpnu.domain.UserRole;
import edu.gpnu.enumeration.RoleIdEnum;
import edu.gpnu.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private RoleDao roleDao;

//Create
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public Integer addUser(User user) {
        /*  1.开启事务，然后开始添加useradd表。
         * 2. 添加完成 useradd 后，填写 user_role表。
         * */
        //插入user表
        int insertUserResult = userDao.insertUser(user);
        //user 对象中的 id ---》在上一步插入完成后会自动赋值
        //roleId根据 角色名查redis缓存内容 或者查表后获得
        String networkManagemenID = roleDao.getRoleIdByDescription("网管");
        UserRole userRole = new UserRole(user.getId(), networkManagemenID);
        int insertRoleResult = userRoleDao.insert(userRole);
        int result = 0;
        if (insertRoleResult == 1 && insertUserResult == 1) {
            result = 1;
        }
        return result;
    }

//Read
    @Override
    public User getUserById(String id) {

        return userDao.selecUsertById(id);
    }

//Update
    @Override
    public Integer updateUserById(User user) {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id", user.getId());
        /*使用 mybatis plus 自带的update 的方法进行修改，可以实现部分更新*/
    // int update(T updateEntity, Wrapper<T> whereWrapper);
        return userDao.update(user, userUpdateWrapper);
    }

    //Delete
    @Override
    public Integer deleteUserById(String id) {
        return userDao.deleteById(id);
    }


}
