package edu.gpnu.controller;

import edu.gpnu.domain.ResponseResult;
import edu.gpnu.domain.Worksheet;
import edu.gpnu.service.DutyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/duty")
public class DutyController {

    @Resource
    private DutyService dutyService;

    //获取网管这个学期的值班信息
    @RequestMapping(value = "/getSelfDuty",method = RequestMethod.GET)
    public ResponseResult<List<Worksheet>> getDutyByUserId(String userId){
        ResponseResult<List<Worksheet>> result = new ResponseResult<>();
        result.requestNormal(dutyService.getDutyWorksheetsByUserId(userId));
        return result;
    }

    @RequestMapping(value = "/addDutyInfo",method = RequestMethod.POST)
    public ResponseResult<Integer> addDutyInfo(Integer weekNumBegin,Integer weekNumEnd,Integer dayWeek,
                                               Integer dayNum,String userId,Integer dutyType){
        ResponseResult<Integer> result = new ResponseResult<>();
        Integer insertCount = dutyService.addDutyInfo(weekNumBegin, weekNumEnd, dayWeek, dayNum, userId, dutyType);
        if (insertCount == 0){
            result.operationError();
        }else {
            result.requestNormal(insertCount);
        }
        return result;
    }
}
