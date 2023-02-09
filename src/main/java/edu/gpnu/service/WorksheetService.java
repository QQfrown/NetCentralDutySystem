package edu.gpnu.service;

import edu.gpnu.domain.Worksheet;

import java.util.List;

public interface WorksheetService {
    String GetIdByTime(Integer weekNum,Integer dayWeek,Integer dayNum);
    List<String> getIdByWeek(Integer weekNum);
    Worksheet getWorksheetById(String id);
}
