package edu.gpnu.service;

import edu.gpnu.domain.Worksheet;

import java.util.List;
import java.util.Map;

public interface DutyService {
    List<Worksheet> getDutyWorksheetsByUserId(String userId);

    List<Worksheet> getOneDutyWorksheetByUserId(String userId,Integer weekNum);

    List<Map<String,Object>> getCanChangeShiftsUsers(Integer localWeekNum, Integer localDayWeek, Integer localDayNum,String userId);

    Integer addRangeDutyInfo(Integer weekNumBegin, Integer weekNumEnd, Integer dayWeek, Integer dayNum, String userId, Integer dutyType);

    Integer addOneDutyInfo(Integer weekNum, Integer dayWeek, Integer dayNum, String userId, Integer dutyType);

    Integer exchangeDutyInfo(Integer requesterWeekNum, Integer requesterDayWeek, Integer requesterDayNum, String requesterUserId, Integer weekNum, Integer dayWeek, Integer dayNum, String userId);

    Integer updateDutyType(Integer weekNum, Integer dayWeek, Integer dayNum, String userId, Integer dutyType);

    Integer updateRangeDutyType(Integer weekNumBegin, Integer weekNumEnd, Integer dayWeek, Integer dayNum, String userId, Integer dutyType);

    Integer updateRangeDutyDate(Integer weekNumBegin, Integer weekNumEnd, Integer oldDayWeek, Integer oldDayNum, Integer newDayWeek, Integer newDayNum,String userId);

    Integer updateDutyDate(Integer weekNum,Integer oldDayWeek, Integer oldDayNum, Integer newDayWeek, Integer newDayNum, String userId);

    Integer deleteDutyInfo(Integer weekNum, Integer dayWeek, Integer dayNum, String userId);

    Integer deleteRangeDutyInfo(Integer weekNumBegin, Integer weekNumEnd, Integer dayWeek, Integer dayNum, String userId);
}