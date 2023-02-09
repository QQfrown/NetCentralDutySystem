package edu.gpnu.service;

import edu.gpnu.domain.Role;

public interface RoleService {
   Integer insertRole(Role role);
    String getRoleIdByDescription(String description);
    Role getRoleById(String id);
    Integer updateRoleById(Role role,String id);
    Integer deleteRoleById(String id);
}
