package edu.gpnu.service;

import edu.gpnu.domain.ResponseResult;
import edu.gpnu.domain.User;

import java.util.Map;

public interface LoginService {
    ResponseResult<Map<String,Object>> login(User user);

    ResponseResult<Map<String,Object>> loginForWechat(String code,User user);

    ResponseResult logout(String user);
}
