package edu.gpnu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.gpnu.domain.User;

public interface UserDao extends BaseMapper<User> {
    String selectPermsByUserId(String userId);
    User selecUsertById(String userId);
    Integer insertUser(User user);
}
