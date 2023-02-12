package edu.gpnu.controller;

import edu.gpnu.domain.ResponseResult;
import edu.gpnu.domain.Worksheet;
import edu.gpnu.service.ScheduleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/schedule")
public class ScheduleController {

    @Resource
    private ScheduleService scheduleService;

    @PreAuthorize("hasAuthority('query')")
    @RequestMapping(value = "/getATermSchedule",method = RequestMethod.GET)
    public ResponseResult<List<Worksheet>> getATermDuty(String userId){
        ResponseResult<List<Worksheet>> result = new ResponseResult<>();
        result.requestNormal(scheduleService.getWorksheetSchedulesByUserId(userId));
        return result;
    }

    @PreAuthorize("hasAuthority('query')")
    @RequestMapping(value = "/getAWeekSchedule",method = RequestMethod.GET)
    public ResponseResult<List<Worksheet>> getAWeekDuty(String userId,Integer weekNum){
        ResponseResult<List<Worksheet>> result = new ResponseResult<>();
        result.requestNormal(scheduleService.getOneWorksheetScheduleByUserId(userId,weekNum));
        return result;
    }

    @PreAuthorize("hasAnyAuthority('apply','edit')")
    @RequestMapping(value = "/addRangeScheduleInfo",method = RequestMethod.POST)
    public ResponseResult<Integer> addDutyInfo(Integer weekNumBegin,Integer weekNumEnd,Integer dayWeek,
                                               Integer dayNum,String userId){
        ResponseResult<Integer> result = new ResponseResult<>();
        Integer insertCount = scheduleService.addRangeScheduleInfo(weekNumBegin, weekNumEnd, dayWeek, dayNum, userId);
        result.requestNormal(insertCount);
        return result;
    }

    @PreAuthorize("hasAnyAuthority('apply','edit')")
    @RequestMapping(value = "/addOneScheduleInfo",method = RequestMethod.POST)
    public ResponseResult<Integer> addOneDutyInfo(Integer weekNum,Integer dayWeek,Integer dayNum,String userId){
        ResponseResult<Integer> result = new ResponseResult<>();
        Integer insertCount = scheduleService.addOneScheduleInfo(weekNum, dayWeek, dayNum, userId);
        result.requestNormal(insertCount);
        return result;
    }

    @PreAuthorize("hasAnyAuthority('apply','edit')")
    @RequestMapping(value = "/updateScheduleDate",method = RequestMethod.POST)
    public ResponseResult<Integer> updateDutyDate(Integer weekNum, Integer oldDayWeek, Integer oldDayNum, Integer newDayWeek, Integer newDayNum,String userId){
        ResponseResult<Integer> result = new ResponseResult<>();
        Integer count = scheduleService.updateScheduleInfo(weekNum,oldDayWeek,oldDayNum,newDayWeek,newDayNum,userId);
        result.requestNormal(count);
        return result;
    }

    @PreAuthorize("hasAnyAuthority('apply','edit')")
    @RequestMapping(value = "/updateRangeScheduleDate",method = RequestMethod.POST)
    public ResponseResult<Integer> updateRangeDutyDate(Integer weekNumBegin, Integer weekNumEnd, Integer oldDayWeek, Integer oldDayNum, Integer newDayWeek, Integer newDayNum,String userId){
        ResponseResult<Integer> result = new ResponseResult<>();
        Integer count = scheduleService.updateRangeScheduleInfo(weekNumBegin,weekNumEnd,oldDayWeek,oldDayNum,newDayWeek,newDayNum,userId);
        result.requestNormal(count);
        return result;
    }

    @PreAuthorize("hasAuthority('edit')")
    @RequestMapping(value = "/deleteScheduleInfo",method = RequestMethod.POST)
    public ResponseResult<Integer> deleteDutyInfo(Integer weekNum, Integer dayWeek, Integer dayNum, String userId){
        ResponseResult<Integer> result = new ResponseResult<>();
        Integer count = scheduleService.deleteScheduleInfo(weekNum, dayWeek, dayNum, userId);
        result.requestNormal(count);
        return result;
    }

    @PreAuthorize("hasAuthority('edit')")
    @RequestMapping(value = "/deleteRangeScheduleInfo",method = RequestMethod.POST)
    public ResponseResult<Integer> deleteRangeDutyInfo(Integer weekNumBegin,Integer weekNumEnd, Integer dayWeek, Integer dayNum, String userId){
        ResponseResult<Integer> result = new ResponseResult<>();
        Integer count = scheduleService.deleteRangeScheduleInfo(weekNumBegin, weekNumEnd, dayWeek, dayNum, userId);
        result.requestNormal(count);
        return result;
    }
}
