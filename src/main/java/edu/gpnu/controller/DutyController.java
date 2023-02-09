package edu.gpnu.controller;

import edu.gpnu.domain.ResponseResult;
import edu.gpnu.domain.Worksheet;
import edu.gpnu.service.DutyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/duty")
public class DutyController {

    @Resource
    private DutyService dutyService;

    //获取网管这个学期的值班信息
    @RequestMapping(value = "/getATermDuty",method = RequestMethod.GET)
    public ResponseResult<List<Worksheet>> getATermDuty(String userId){
        ResponseResult<List<Worksheet>> result = new ResponseResult<>();
        result.requestNormal(dutyService.getDutyWorksheetsByUserId(userId));
        return result;
    }

    @RequestMapping(value = "/getAWeekDuty",method = RequestMethod.GET)
    public ResponseResult<List<Worksheet>> getAWeekDuty(String userId,Integer weekNum){
        ResponseResult<List<Worksheet>> result = new ResponseResult<>();
        result.requestNormal(dutyService.getOneDutyWorksheetByUserId(userId,weekNum));
        return result;
    }

    @RequestMapping(value = "/getCanChangeShiftsUsers",method = RequestMethod.GET)
    public ResponseResult<List<Map<String,Object>>> getCanChangeShiftsUsers(Integer weekNum,Integer dayWeek,Integer dayNum,String userId){
        ResponseResult<List<Map<String,Object>>> result = new ResponseResult<>();
        result.requestNormal(dutyService.getCanChangeShiftsUsers(weekNum,dayWeek,dayNum,userId));
        return result;
    }

    @RequestMapping(value = "/addRangeDutyInfo",method = RequestMethod.POST)
    public ResponseResult<Integer> addDutyInfo(Integer weekNumBegin,Integer weekNumEnd,Integer dayWeek,
                                               Integer dayNum,String userId,Integer dutyType){
        ResponseResult<Integer> result = new ResponseResult<>();
        Integer insertCount = dutyService.addRangeDutyInfo(weekNumBegin, weekNumEnd, dayWeek, dayNum, userId, dutyType);
        result.requestNormal(insertCount);
        return result;
    }

    @RequestMapping(value = "/addOneDutyInfo",method = RequestMethod.POST)
    public ResponseResult<Integer> addOneDutyInfo(Integer weekNum,Integer dayWeek,Integer dayNum,String userId,Integer dutyType){
        ResponseResult<Integer> result = new ResponseResult<>();
        Integer insertCount = dutyService.addOneDutyInfo(weekNum, dayWeek, dayNum, userId, dutyType);
        result.requestNormal(insertCount);
        return result;
    }

    @RequestMapping(value = "/exchangeDutyInfo",method = RequestMethod.POST)
    public ResponseResult<Integer> exchangeDutyInfo(Integer requesterWeekNum, Integer requesterDayWeek, Integer requesterDayNum, String requesterUserId, Integer weekNum, Integer dayWeek, Integer dayNum, String userId){
        ResponseResult<Integer> result = new ResponseResult<>();
        Integer count = dutyService.exchangeDutyInfo(requesterWeekNum, requesterDayWeek, requesterDayNum, requesterUserId, weekNum, dayWeek, dayNum, userId);
        if (count != 2){
            result.operationError();
        }else {
            result.requestNormal(count);
        }
        return result;
    }

    @RequestMapping(value = "/updateDutyDate",method = RequestMethod.POST)
    public ResponseResult<Integer> updateDutyDate(Integer weekNum, Integer oldDayWeek, Integer oldDayNum, Integer newDayWeek, Integer newDayNum,String userId){
        ResponseResult<Integer> result = new ResponseResult<>();
        Integer count = dutyService.updateDutyDate(weekNum,oldDayWeek,oldDayNum,newDayWeek,newDayNum,userId);
        result.requestNormal(count);
        return result;
    }

    @RequestMapping(value = "/updateRangeDutyDate",method = RequestMethod.POST)
    public ResponseResult<Integer> updateRangeDutyDate(Integer weekNumBegin, Integer weekNumEnd, Integer oldDayWeek, Integer oldDayNum, Integer newDayWeek, Integer newDayNum,String userId){
        ResponseResult<Integer> result = new ResponseResult<>();
        Integer count = dutyService.updateRangeDutyDate(weekNumBegin,weekNumEnd,oldDayWeek,oldDayNum,newDayWeek,newDayNum,userId);
        result.requestNormal(count);
        return result;
    }

    @RequestMapping(value = "/updateDutyType",method = RequestMethod.POST)
    public ResponseResult<Integer> updateDutyType(Integer weekNum, Integer dayWeek, Integer dayNum, String userId,Integer dutyType){
        ResponseResult<Integer> result = new ResponseResult<>();
        Integer count = dutyService.updateDutyType(weekNum, dayWeek, dayNum, userId, dutyType);
        result.requestNormal(count);
        return result;
    }


    @RequestMapping(value = "/updateRangeDutyType",method = RequestMethod.POST)
    public ResponseResult<Integer> updateRangeDutyType(Integer weekNumBegin,Integer weekNumEnd, Integer dayWeek, Integer dayNum, String userId,Integer dutyType){
        ResponseResult<Integer> result = new ResponseResult<>();
        Integer count = dutyService.updateRangeDutyType(weekNumBegin, weekNumEnd, dayWeek, dayNum, userId,dutyType);
        result.requestNormal(count);
        return result;
    }

    @RequestMapping(value = "/deleteDutyInfo",method = RequestMethod.POST)
    public ResponseResult<Integer> deleteDutyInfo(Integer weekNum, Integer dayWeek, Integer dayNum, String userId){
        ResponseResult<Integer> result = new ResponseResult<>();
        Integer count = dutyService.deleteDutyInfo(weekNum, dayWeek, dayNum, userId);
        result.requestNormal(count);
        return result;
    }

    @RequestMapping(value = "/deleteRangeDutyInfo",method = RequestMethod.POST)
    public ResponseResult<Integer> deleteRangeDutyInfo(Integer weekNumBegin,Integer weekNumEnd, Integer dayWeek, Integer dayNum, String userId){
        ResponseResult<Integer> result = new ResponseResult<>();
        Integer count = dutyService.deleteRangeDutyInfo(weekNumBegin, weekNumEnd, dayWeek, dayNum, userId);
        result.requestNormal(count);
        return result;
    }

}
