package edu.gpnu.service.impl;

import edu.gpnu.dao.DutyDao;
import edu.gpnu.dao.WorksheetDao;
import edu.gpnu.domain.Duty;
import edu.gpnu.domain.Worksheet;
import edu.gpnu.service.DutyService;
import edu.gpnu.service.WorksheetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class DutyServiceImpl implements DutyService {

    @Resource
    private DutyDao dutyDao;

    @Resource
    private WorksheetService worksheetService;

    @Override
    public List<Worksheet> getDutyWorksheetsByUserId(String userId) {
        return dutyDao.getDutyWorksheetsByUserId(userId);
    }

    @Override
    public Integer addDutyInfo(Integer weekNumBegin,Integer weekNumEnd,Integer dayWeek,Integer dayNum,String userId,Integer dutyType) {
        int count = 0;
        if (weekNumBegin.intValue() <= weekNumEnd.intValue() && Objects.nonNull(dayWeek) && Objects.nonNull(dayNum) && Objects.nonNull(userId) && Objects.nonNull(dutyType)){
            for (int i = weekNumBegin ; i <= weekNumEnd;i++){
                count++;
                String worksheetId = worksheetService.GetIdByTime(i, dayWeek, dayNum);
                dutyDao.insert(new Duty(userId,worksheetId,dutyType));
            }
            if (count-1 == weekNumEnd - weekNumBegin){
                return count;
            }
            else return 0;
        }
        return count;
    }
}
