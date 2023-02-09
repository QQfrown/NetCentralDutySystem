package edu.gpnu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.gpnu.domain.Schedule;
import edu.gpnu.domain.Worksheet;

import java.util.List;

public interface ScheduleDao extends BaseMapper<Schedule> {
    List<Worksheet> getWorksheetSchedulesByUserId(String userId);
    List<Worksheet> getOneWorksheetScheduleByUserId(String userId,Integer weekNum);
    List<Schedule> getCanTakeShiftsUsers(String worksheetId);
}
