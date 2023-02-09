package edu.gpnu.service;

import edu.gpnu.domain.Schedule;
import edu.gpnu.domain.Worksheet;

import java.util.List;

public interface ScheduleService {



    List<Worksheet> getWorksheetSchedulesByUserId(String userId);

    List<Worksheet> getOneWorksheetScheduleByUserId(String userId,Integer weekNum);

    List<Schedule> getOneWeekScheduleInfo(String userId,Integer weekNum);

    Integer addRangeScheduleInfo(Integer weekNumBegin, Integer weekNumEnd, Integer dayWeek, Integer dayNum, String userId);

    Integer addOneScheduleInfo(Integer weekNum, Integer dayWeek, Integer dayNum, String userId);

    Integer updateRangeScheduleInfo(Integer weekNumBegin, Integer weekNumEnd, Integer oldDayWeek, Integer oldDayNum, Integer newDayWeek, Integer newDayNum,String userId);

    Integer updateScheduleInfo(Integer weekNum,Integer oldDayWeek, Integer oldDayNum, Integer newDayWeek, Integer newDayNum, String userId);

    Integer deleteScheduleInfo(Integer weekNum, Integer dayWeek, Integer dayNum, String userId);

    Integer deleteRangeScheduleInfo(Integer weekNumBegin, Integer weekNumEnd, Integer dayWeek, Integer dayNum, String userId);
}
