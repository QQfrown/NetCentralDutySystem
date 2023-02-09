package edu.gpnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import edu.gpnu.dao.ScheduleDao;
import edu.gpnu.domain.Schedule;
import edu.gpnu.domain.Worksheet;
import edu.gpnu.service.ScheduleService;
import edu.gpnu.service.WorksheetService;
import edu.gpnu.utils.RedisCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Resource
    private WorksheetService worksheetService;

    @Resource
    private ScheduleDao scheduleDao;

    @Resource
    private RedisCache redisCache;

    @Override
    public List<Worksheet> getWorksheetSchedulesByUserId(String userId) {
        List<Worksheet> redisDutyList = redisCache.getCacheList("schedule:" + userId);
        if (Objects.isNull(redisDutyList) || redisDutyList.isEmpty()){
            List<Worksheet> worksheets = scheduleDao.getWorksheetSchedulesByUserId(userId);
            redisCache.setCacheList("schedule:" + userId,worksheets);
            redisCache.expire("schedule:" + userId,1, TimeUnit.HOURS);
            return worksheets;
        }else{
            return redisDutyList;
        }
    }

    @Override
    public List<Worksheet> getOneWorksheetScheduleByUserId(String userId, Integer weekNum) {
        List<Worksheet> redisDutyList = redisCache.getCacheList("week_num:"+weekNum+":schedule:" + userId);
        if (Objects.isNull(redisDutyList) || redisDutyList.isEmpty()){
            List<Worksheet> worksheets = scheduleDao.getOneWorksheetScheduleByUserId(userId,weekNum);
            redisCache.setCacheList("week_num:"+weekNum+":schedule:" + userId,worksheets);
            redisCache.expire("week_num:"+weekNum+":schedule:"+ userId,1,TimeUnit.HOURS);
            return worksheets;
        }else{
            return redisDutyList;
        }
    }

    @Override
    public List<Schedule> getOneWeekScheduleInfo(String userId, Integer weekNum) {
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<String> ids = worksheetService.getIdByWeek(weekNum);
        queryWrapper.in("worksheet_id",ids);
        return scheduleDao.selectList(queryWrapper);
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
