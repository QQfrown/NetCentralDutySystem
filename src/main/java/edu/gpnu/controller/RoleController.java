package edu.gpnu.controller;


import edu.gpnu.domain.ResponseResult;
import edu.gpnu.domain.Role;
import edu.gpnu.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    //Create
    @PreAuthorize("hasAuthority('edit')")
    @RequestMapping(value = "/insertRole",method = RequestMethod.POST)
    public ResponseResult<Integer> insertRole(@RequestBody Role role){
    //获取传入的参数，然后调用dao的方法，将结果放入 responseResult
        ResponseResult<Integer> integerResponseResult = new ResponseResult<>();
        integerResponseResult.jungeResultByResultCode(roleService.insertRole(role));
        return integerResponseResult;
    }

    //Read
    @PreAuthorize("hasAuthority('query')")
    @RequestMapping(value = "/getRoleById",method = RequestMethod.GET)
    public ResponseResult<Role> getRole(@RequestBody Role role){
        ResponseResult<Role> integerResponseResult = new ResponseResult<>();
        integerResponseResult.requestNormal(roleService.getRoleById(role.getId()));
        return integerResponseResult;
    }

    //Update
    @PreAuthorize("hasAuthority('edit')")
    @RequestMapping(value = "/updateRoleById",method = RequestMethod.POST)
    public ResponseResult<Integer> updateRoleById(@RequestBody Role role){
        ResponseResult<Integer> integerResponseResult = new ResponseResult<>();
        integerResponseResult.jungeResultByResultCode(roleService.updateRoleById(role,role.getId()));
        return integerResponseResult;
    }

    //Delete
    @PreAuthorize("hasAuthority('edit')")
    @RequestMapping(value = "/deleteRoleById",method = RequestMethod.POST)
    public ResponseResult<Integer> deleteRoleById(@RequestBody Role role){
        ResponseResult<Integer> integerResponseResult = new ResponseResult<>();
        integerResponseResult.jungeResultByResultCode(roleService.deleteRoleById(role.getId()));
        return integerResponseResult;
    }
}
