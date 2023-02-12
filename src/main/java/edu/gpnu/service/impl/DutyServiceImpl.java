package edu.gpnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import edu.gpnu.dao.DutyDao;
import edu.gpnu.dao.UserDao;
import edu.gpnu.domain.Duty;
import edu.gpnu.domain.Schedule;
import edu.gpnu.domain.User;
import edu.gpnu.domain.Worksheet;
import edu.gpnu.service.DutyService;
import edu.gpnu.service.ScheduleService;
import edu.gpnu.service.WorksheetService;
import edu.gpnu.utils.RedisCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class DutyServiceImpl implements DutyService {

    @Resource
    private DutyDao dutyDao;

    @Resource
    private WorksheetService worksheetService;

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private RedisCache redisCache;

    @Resource
    private UserDao userDao;

    @Override
    public List<Worksheet> getDutyWorksheetsByUserId(String userId) {
        List<Worksheet> redisDutyList = redisCache.getCacheList("duty:" + userId);
        if (Objects.isNull(redisDutyList) || redisDutyList.isEmpty()){
            List<Worksheet> worksheets = dutyDao.getDutyWorksheetsByUserId(userId);
            redisCache.setCacheList("duty:" + userId,worksheets);
            redisCache.expire("duty:" + userId,1, TimeUnit.HOURS);
            return worksheets;
        }else{
            return redisDutyList;
        }
    }

    @Override
    public List<Worksheet> getOneDutyWorksheetByUserId(String userId, Integer weekNum) {
        List<Worksheet> redisDutyList = redisCache.getCacheList("week_num:"+weekNum+":duty:" + userId);
        if (Objects.isNull(redisDutyList) || redisDutyList.isEmpty()){
            List<Worksheet> worksheets = dutyDao.getOneDutyWorksheetByUserId(userId,weekNum);
            redisCache.setCacheList("week_num:"+weekNum+":duty:" + userId,worksheets);
            redisCache.expire("week_num:"+weekNum+":duty:"+ userId,1,TimeUnit.HOURS);
            return worksheets;
        }else{
            return redisDutyList;
        }
    }

    @Override
    public List<String> getAClassUserRealNames(Integer weekNum, Integer dayWeek, Integer dayNum) {
        String worksheetId = worksheetService.GetIdByTime(weekNum, dayWeek, dayNum);
        List<String> userRealNames = dutyDao.getAClassUserRealNames(worksheetId);
        return userRealNames;
    }

    /**
     *
     * @param userId 用户ID
     * @param localWeekNum 所在周
     * @param localDayWeek 所在天
     * @param localDayNum   所在节
     * 获取可换班用户满足的条件：
     *  1、我这个班没有他
     *  2、我这个班他有时间
     *  3、他的班我有时间
     *  step：
     *       1、获取本周的值班信息，并去除这个班的值班信息，即：我这个班没有他
     *       2、获取本周我的反课表，找出我可以换的班，遍历step1的结果(注意需要去除自己)，找出我可以换的值班信息，即：他的班我有时间。
     *       3、遍历step2结果的用户的反课表，找出有时间值我这个班的值班信息
     * @return
     */
    @Override
    public List<Map<String,Object>> getCanChangeShiftsUsers(Integer localWeekNum, Integer localDayWeek, Integer localDayNum,String userId) {
        List<String> idByWeek = worksheetService.getIdByWeek(localWeekNum);
        String localWorksheetId = worksheetService.GetIdByTime(localWeekNum, localDayWeek, localDayNum);
        idByWeek.remove(localWorksheetId);
        QueryWrapper<Duty> dutyQueryWrapper = new QueryWrapper<>();
        dutyQueryWrapper.in("worksheet_id",idByWeek);
        List<Duty> stepOneResult = dutyDao.selectList(dutyQueryWrapper);
        List<Duty> stepTwoResult = new ArrayList<>();
        List<Map<String,Object>> finalResult = new ArrayList<>();
        List<Schedule> mySchedules = scheduleService.getOneWeekScheduleInfo(userId, localWeekNum);
        for (Duty duty : stepOneResult){
            if (!duty.getUserId().equals(userId)){
                for (Schedule schedule : mySchedules){
                    if (schedule.getWorksheetId().equals(duty.getWorksheetId())){
                        stepTwoResult.add(duty);
                        break;
                    }
                }
            }
        }
        for (Duty duty : stepTwoResult){
            List<Schedule> schedules = scheduleService.getOneWeekScheduleInfo(duty.getUserId(), localWeekNum);
            for (Schedule schedule : schedules){
                if (schedule.getWorksheetId().equals(localWorksheetId)){
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("userId",duty.getUserId());
                    User user = userDao.selecUsertById(duty.getUserId());
                    map.put("realName",user.getRealName());
                    Worksheet worksheet = worksheetService.getWorksheetById(duty.getWorksheetId());
                    map.put("weekNum",worksheet.getWeekNum());
                    map.put("dayWeek",worksheet.getDayWeek());
                    map.put("dayNum",worksheet.getDayNum());
                    finalResult.add(map);
                    break;
                }
            }
        }
        return finalResult;
    }

    @Override
    @Transactional
    public Integer addRangeDutyInfo(Integer weekNumBegin,Integer weekNumEnd,Integer dayWeek,Integer dayNum,String userId,Integer dutyType) {
        int count = 0;
        if (weekNumBegin <= weekNumEnd && Objects.nonNull(dayWeek) && Objects.nonNull(dayNum) && Objects.nonNull(userId) && Objects.nonNull(dutyType)){
            for (int i = weekNumBegin ; i <= weekNumEnd;i++){
                String worksheetId = worksheetService.GetIdByTime(i, dayWeek, dayNum);
                count = count + dutyDao.insert(new Duty(userId,worksheetId,dutyType));
            }
        }
        return count;
    }

    @Override
    public Integer addOneDutyInfo(Integer weekNum, Integer dayWeek, Integer dayNum, String userId, Integer dutyType) {
        String worksheetId = worksheetService.GetIdByTime(weekNum, dayWeek, dayNum);
        return dutyDao.insert(new Duty(userId,worksheetId,dutyType));
    }

    @Override
    @Transactional
    public Integer exchangeDutyInfo(Integer requesterWeekNum, Integer requesterDayWeek, Integer requesterDayNum, String requesterUserId, Integer weekNum, Integer dayWeek, Integer dayNum, String userId) {
        String requesterWorksheetId = worksheetService.GetIdByTime(requesterWeekNum,requesterDayWeek,requesterDayNum);
        String worksheetId = worksheetService.GetIdByTime(weekNum, dayWeek, dayNum);

        UpdateWrapper<Duty> requesterUpdateWrapper = new UpdateWrapper<>();
        requesterUpdateWrapper.eq("user_id",requesterUserId);
        requesterUpdateWrapper.eq("worksheet_id",requesterWorksheetId);
        requesterUpdateWrapper.set("worksheet_id",worksheetId);
        int requesterUpdate = dutyDao.update(null, requesterUpdateWrapper);

        UpdateWrapper<Duty> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",userId);
        updateWrapper.eq("worksheet_id",worksheetId);
        updateWrapper.set("worksheet_id",requesterWorksheetId);
        int update = dutyDao.update(null, updateWrapper);

        return requesterUpdate+update;
    }

    @Override
    public Integer updateDutyType(Integer weekNum, Integer dayWeek, Integer dayNum, String userId, Integer dutyType) {
        String worksheetId = worksheetService.GetIdByTime(weekNum, dayWeek, dayNum);
        UpdateWrapper<Duty> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",userId);
        updateWrapper.eq("worksheet_id",worksheetId);
        updateWrapper.set("duty_type",dutyType);
        return dutyDao.update(null,updateWrapper);
    }

    @Override
    @Transactional
    public Integer updateRangeDutyType(Integer weekNumBegin, Integer weekNumEnd, Integer dayWeek, Integer dayNum, String userId, Integer dutyType) {
        int count = 0;
        if (weekNumBegin <= weekNumEnd && Objects.nonNull(dayWeek) && Objects.nonNull(dayNum) && Objects.nonNull(userId)){
            for (int i = weekNumBegin ; i <= weekNumEnd;i++){
                UpdateWrapper<Duty> updateWrapper = new UpdateWrapper<>();
                String worksheetId = worksheetService.GetIdByTime(i, dayWeek, dayNum);
                updateWrapper.eq("user_id",userId);
                updateWrapper.eq("worksheet_id",worksheetId);
                updateWrapper.set("duty_type",dutyType);
                count = count + dutyDao.update(null,updateWrapper);
            }
        }
        return count;
    }

    @Override
    public Integer updateRangeDutyDate(Integer weekNumBegin, Integer weekNumEnd, Integer oldDayWeek, Integer oldDayNum, Integer newDayWeek, Integer newDayNum, String userId) {
        int count = 0;
        if (weekNumBegin <= weekNumEnd && Objects.nonNull(userId)){
            for (int i = weekNumBegin ; i <= weekNumEnd;i++){
                String oldWorksheetId = worksheetService.GetIdByTime(i, oldDayWeek,oldDayNum);
                String newWorksheetId = worksheetService.GetIdByTime(i, newDayWeek,newDayNum);
                UpdateWrapper<Duty> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("user_id",userId);
                updateWrapper.eq("worksheet_id",oldWorksheetId);
                updateWrapper.set("worksheet_id",newWorksheetId);
                count = count + dutyDao.update(null,updateWrapper);
            }
        }
        return count;
    }

    @Override
    public Integer updateDutyDate(Integer weekNum, Integer oldDayWeek, Integer oldDayNum, Integer newDayWeek, Integer newDayNum, String userId) {
        String oldWorksheetId = worksheetService.GetIdByTime(weekNum, oldDayWeek,oldDayNum);
        String newWorksheetId = worksheetService.GetIdByTime(weekNum, newDayWeek,newDayNum);
        UpdateWrapper<Duty> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",userId);
        updateWrapper.eq("worksheet_id",oldWorksheetId);
        updateWrapper.set("worksheet_id",newWorksheetId);
        return dutyDao.update(null,updateWrapper);
    }


    @Override
    public Integer deleteDutyInfo(Integer weekNum, Integer dayWeek, Integer dayNum, String userId) {
        String worksheetId = worksheetService.GetIdByTime(weekNum, dayWeek, dayNum);
        QueryWrapper<Duty> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("worksheet_id",worksheetId);
        return dutyDao.delete(queryWrapper);
    }

    @Override
    @Transactional
    public Integer deleteRangeDutyInfo(Integer weekNumBegin,Integer weekNumEnd,Integer dayWeek,Integer dayNum,String userId) {
        int count = 0;
        if (weekNumBegin <= weekNumEnd && Objects.nonNull(dayWeek) && Objects.nonNull(dayNum) && Objects.nonNull(userId)){
            for (int i = weekNumBegin ; i <= weekNumEnd;i++){
                String worksheetId = worksheetService.GetIdByTime(i, dayWeek, dayNum);
                QueryWrapper<Duty> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_id",userId);
                queryWrapper.eq("worksheet_id",worksheetId);
                count = count + dutyDao.delete(queryWrapper);
            }
        }
        return count;
    }

    /**
     * 基于优先级的排班算法
     * @param min :每个班的最少人数    max:每个班的最多人数
     * @step
     *  1、根据查询数据库schedule全表，字段 userId，count（userId），根据count从小到大排序，放入集合。
     *  2、优先排count少的人的班（第一优先级），优先往人数少的班排班，优先排新网管的班
     *  3、每个人遍历 2~5*4 遍，循环完所有网管为止。
     *  4、使用Map<String,List<Duty>>作为容器存储已完成排班的网管，通过判断对应List集合的数量，
     *  确定是否放入该集合，key格式：0-1 ~ 4-3（表示哪一天的哪一节）
     *
     * @return List<Duty>
     */

}
