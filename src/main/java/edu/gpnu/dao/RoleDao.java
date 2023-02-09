package edu.gpnu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.gpnu.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface RoleDao extends BaseMapper<Role> {

//   Crud
   String getRoleIdByDescription(@Param("description") String description);

}
