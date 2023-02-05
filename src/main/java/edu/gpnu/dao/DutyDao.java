package edu.gpnu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.gpnu.domain.Duty;
import edu.gpnu.domain.Worksheet;

import java.util.List;

public interface DutyDao extends BaseMapper<Duty> {
    List<Worksheet> getDutyWorksheetsByUserId(String userId);
}
