package edu.gpnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.gpnu.dao.WorksheetDao;
import edu.gpnu.domain.Worksheet;
import edu.gpnu.service.WorksheetService;
import edu.gpnu.utils.RedisCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class WorksheetServiceImpl implements WorksheetService {

    @Resource
    private WorksheetDao worksheetDao;

    @Resource
    private RedisCache redisCache;

    //根据时间，返回对应周的对应天的对应节数的ID
    @Override
    public String GetIdByTime(Integer weekNum, Integer dayWeek, Integer dayNum) {
        String redisWorksheetId =(String) redisCache.getCacheObject("worksheet_id:" + weekNum + "-" + dayWeek + "-" + dayNum);
        if (Objects.isNull(redisWorksheetId)){
            QueryWrapper<Worksheet> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("week_num",weekNum);
            queryWrapper.eq("day_week",dayWeek);
            queryWrapper.eq("day_num",dayNum);
            Worksheet worksheet = worksheetDao.selectOne(queryWrapper);
            redisCache.setCacheObject("worksheet_id:" + weekNum + "-" + dayWeek + "-" + dayNum,worksheet.getId());
            redisCache.expire("worksheet_id:" + weekNum + "-" + dayWeek + "-" + dayNum,2, TimeUnit.HOURS);
            return worksheet.getId();
        }else {
            return redisWorksheetId;
        }
    }

    //获取某周的所有worksheetId
    @Override
    public List<String> getIdByWeek(Integer weekNum) {
        QueryWrapper<Worksheet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("week_num",weekNum);
        List<Worksheet> worksheets = worksheetDao.selectList(queryWrapper);
        List<String> ids = new ArrayList<>();
        for (Worksheet worksheet : worksheets){
            ids.add(worksheet.getId());
        }
        return ids;
    }

    @Override
    public Worksheet getWorksheetById(String id) {
        return worksheetDao.selectById(id);
    }
}
