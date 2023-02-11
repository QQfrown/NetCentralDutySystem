package edu.gpnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import edu.gpnu.dao.RoleDao;
import edu.gpnu.domain.Role;
import edu.gpnu.service.RoleService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;
    //Create
    //正常插入数据
   public Integer insertRole(Role role) {
        // 调用 mybatis-plus 自带的方法插入role
        return roleDao.insert(role);
    }

    //Read
    //根据 description(即角色名字)获取 id
   public  String getRoleIdByDescription(String description) {
        return roleDao.getRoleIdByDescription(description);
    }

    //根据 id 获取 role 记录。
   public Role getRoleById(String id) {
        //调用 mybatis-plus 自带的方法
        return roleDao.selectById(id);
    }

    //Update
    //根据 id 更新 role 记录。
   public Integer updateRoleById(Role role,String id){
        UpdateWrapper<Role> roleUpdateWrapper = new UpdateWrapper<Role>();
        roleUpdateWrapper.eq("id",id);
        return roleDao.update(role,roleUpdateWrapper);
    }
    //Delete
    //根据 id 删除 role 记录。
    public  Integer deleteRoleById(String id){
        return roleDao.deleteById(id);
    }
}
