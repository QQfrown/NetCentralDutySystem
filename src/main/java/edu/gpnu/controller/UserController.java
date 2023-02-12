package edu.gpnu.controller;

import edu.gpnu.domain.ResponseResult;
import edu.gpnu.domain.User;
import edu.gpnu.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @PreAuthorize("hasAuthority('edit')")
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public ResponseResult<Integer> addUser(@RequestBody User user){
        ResponseResult<Integer> result = new ResponseResult<>();
        result.jungeResultByResultCode(userService.addUser(user));
        return result;
    }

    @PreAuthorize("hasAnyAuthority('edit','apply')")
    @RequestMapping(value = "/updateUserById",method = RequestMethod.POST)
    public ResponseResult<Integer> updateUser(@RequestBody User user){
        ResponseResult<Integer> result = new ResponseResult<>();
        result.requestNormal(userService.updateUserById(user));
        return result;
    }

    @PreAuthorize("hasAuthority('edit')")
    @RequestMapping(value = "/deleteUserById",method = RequestMethod.POST)
    public ResponseResult<Integer> deleteUser(@RequestBody User user){
        ResponseResult<Integer> result = new ResponseResult<>();
        result.requestNormal(userService.deleteUserById(user.getId()));
        return result;
    }

    @PreAuthorize("hasAuthority('query')")
    @RequestMapping(value = "/getUserById",method = RequestMethod.GET)
    public ResponseResult<User> getUserById(@RequestBody User user){
        User userById = userService.getUserById(user.getId());
        userById.setPassword(null);
        ResponseResult<User> result = new ResponseResult<>();
        result.requestNormal(userById);
        return result;
    }

}
