package edu.gpnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.gpnu.dao.WorksheetDao;
import edu.gpnu.domain.Worksheet;
import edu.gpnu.service.WorksheetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WorksheetServiceImpl implements WorksheetService {

    @Resource
    private WorksheetDao worksheetDao;

    //根据时间，返回对应周的对应天的对应节数的ID
    @Override
    public String GetIdByTime(Integer weekNum, Integer dayWeek, Integer dayNum) {
        QueryWrapper<Worksheet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("week_num",weekNum);
        queryWrapper.eq("day_week",dayWeek);
        queryWrapper.eq("day_num",dayNum);
        Worksheet worksheet = worksheetDao.selectOne(queryWrapper);
        return worksheet.getId();
    }
}
