package edu.gpnu.controller;

import edu.gpnu.domain.ResponseResult;
import edu.gpnu.domain.User;
import edu.gpnu.service.UserService;
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

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public ResponseResult<Integer> addUser(@RequestBody User user){
        ResponseResult<Integer> result = new ResponseResult<>();
        result.requestNormal(userService.addUser(user));
        return result;
    }
}
