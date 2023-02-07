package edu.gpnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import edu.gpnu.dao.ScheduleDao;
import edu.gpnu.domain.Schedule;
import edu.gpnu.domain.Worksheet;
import edu.gpnu.service.ScheduleService;
import edu.gpnu.service.WorksheetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Resource
    private WorksheetService worksheetService;

    @Resource
    private ScheduleDao scheduleDao;

    @Override
    public List<Worksheet> getWorksheetSchedulesByUserId(String userId) {
        return scheduleDao.getWorksheetSchedulesByUserId(userId);
    }

    @Override
    public List<Worksheet> getOneWorksheetScheduleByUserId(String userId, Integer weekNum) {
        return scheduleDao.getOneWorksheetScheduleByUserId(userId,weekNum);
    }

    @Override
    @Transactional
    public Integer addRangeScheduleInfo(Integer weekNumBegin, Integer weekNumEnd, Integer dayWeek, Integer dayNum, String userId) {

        int count = 0;
        if (weekNumBegin <= weekNumEnd && Objects.nonNull(dayWeek) && Objects.nonNull(dayNum) && Objects.nonNull(userId)){
            for (int i = weekNumBegin ; i <= weekNumEnd;i++){
                String worksheetId = worksheetService.GetIdByTime(i, dayWeek, dayNum);
                count = count + scheduleDao.insert(new Schedule(userId,worksheetId));
            }
        }
        return count;
    }

    @Override
    public Integer addOneScheduleInfo(Integer weekNum, Integer dayWeek, Integer dayNum, String userId) {
        String worksheetId = worksheetService.GetIdByTime(weekNum, dayWeek, dayNum);
        return scheduleDao.insert(new Schedule(userId,worksheetId));
    }

    @Override
    @Transactional
    public Integer updateRangeScheduleInfo(Integer weekNumBegin, Integer weekNumEnd,  Integer oldDayWeek, Integer oldDayNum, Integer newDayWeek, Integer newDayNum, String userId) {
        int count = 0;
        if (weekNumBegin <= weekNumEnd && Objects.nonNull(userId)){
            for (int i = weekNumBegin ; i <= weekNumEnd;i++){
                String oldWorksheetId = worksheetService.GetIdByTime(i, oldDayWeek,oldDayNum);
                String newWorksheetId = worksheetService.GetIdByTime(i, newDayWeek,newDayNum);
                UpdateWrapper<Schedule> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("user_id",userId);
                updateWrapper.eq("worksheet_id",oldWorksheetId);
                updateWrapper.set("worksheet_id",newWorksheetId);
                count = count + scheduleDao.update(null,updateWrapper);
            }
        }
        return count;
    }

    @Override
    public Integer updateScheduleInfo(Integer weekNum, Integer oldDayWeek, Integer oldDayNum, Integer newDayWeek, Integer newDayNum, String userId) {
        String oldWorksheetId = worksheetService.GetIdByTime(weekNum, oldDayWeek,oldDayNum);
        String newWorksheetId = worksheetService.GetIdByTime(weekNum, newDayWeek,newDayNum);
        UpdateWrapper<Schedule> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",userId);
        updateWrapper.eq("worksheet_id",oldWorksheetId);
        updateWrapper.set("worksheet_id",newWorksheetId);
        return scheduleDao.update(null,updateWrapper);
    }

    @Override
    public Integer deleteScheduleInfo(Integer weekNum, Integer dayWeek, Integer dayNum, String userId) {
        String worksheetId = worksheetService.GetIdByTime(weekNum, dayWeek, dayNum);
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("worksheet_id",worksheetId);
        return scheduleDao.delete(queryWrapper);
    }

    @Override
    @Transactional
    public Integer deleteRangeScheduleInfo(Integer weekNumBegin, Integer weekNumEnd, Integer dayWeek, Integer dayNum, String userId) {
        int count = 0;
        if (weekNumBegin <= weekNumEnd && Objects.nonNull(dayWeek) && Objects.nonNull(dayNum) && Objects.nonNull(userId)){
            for (int i = weekNumBegin ; i <= weekNumEnd;i++){
                String worksheetId = worksheetService.GetIdByTime(i, dayWeek, dayNum);
                QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_id",userId);
                queryWrapper.eq("worksheet_id",worksheetId);
                count = count + scheduleDao.delete(queryWrapper);
            }
        }
        return count;
    }
}
