package edu.gpnu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.gpnu.domain.UserRole;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface UserRoleDao extends BaseMapper<UserRole> {


}
