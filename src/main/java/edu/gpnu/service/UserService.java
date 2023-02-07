package edu.gpnu.service;

import edu.gpnu.domain.User;

public interface UserService {
    Integer addUser(User user);
    Integer updateUserById(User user);
    Integer deleteUserById(String id);
    User getUserById(String id);
}
