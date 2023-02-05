package edu.gpnu.service;

import edu.gpnu.domain.Duty;
import edu.gpnu.domain.Worksheet;

import java.util.List;

public interface DutyService {
    List<Worksheet> getDutyWorksheetsByUserId(String userId);
    Integer addDutyInfo(Integer weekNumBegin,Integer weekNumEnd,Integer dayWeek,Integer dayNum,String userId,Integer dutyType);
}
