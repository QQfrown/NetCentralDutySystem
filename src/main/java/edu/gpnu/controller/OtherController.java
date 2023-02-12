package edu.gpnu.controller;

import edu.gpnu.domain.ResponseResult;
import edu.gpnu.utils.PropertiesUtil;
import edu.gpnu.utils.RedisCache;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/other")
public class OtherController {

    @Resource
    private RedisCache redisCache;

    @PreAuthorize("hasAuthority('query')")
    @RequestMapping(value = "/getFirstWeekTime",method = RequestMethod.GET)
    public ResponseResult<String> getFirstWeekTime(){
        ResponseResult<String> result = new ResponseResult<>();
        try {
            String firstWeekTimeByRedis = (String) redisCache.getCacheObject("first_week_time");
            if (Objects.isNull(firstWeekTimeByRedis)){
                String firstWeekTime = PropertiesUtil.getPro(Thread.currentThread().getContextClassLoader().getResource("otherInfo.properties").getPath(), "firstWeekTime");
                redisCache.setCacheObject("first_week_time",firstWeekTime,1, TimeUnit.DAYS);
                result.requestNormal(firstWeekTime);
            }else {
                result.requestNormal(firstWeekTimeByRedis);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result.setCode(HttpURLConnection.HTTP_SERVER_ERROR);
            result.setMsg("IO error！");
            return result;
        }
    }

    @PreAuthorize("hasAuthority('edit')")
    @RequestMapping(value = "/setFirstWeekTime")
    public ResponseResult<Boolean> setFirstWeekTime(Integer year,Integer month,Integer day){
        ResponseResult<Boolean> result = new ResponseResult<>();
        try {
            Boolean updatePro = PropertiesUtil.updatePro(Thread.currentThread().getContextClassLoader().getResource("otherInfo.properties").getPath(),
                    "firstWeekTime", String.valueOf(new Date(year, month, day).getTime()));
            redisCache.setCacheObject("first_week_time",String.valueOf(new Date(year, month, day).getTime()),1, TimeUnit.DAYS);
            result.requestNormal(updatePro);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result.setCode(HttpURLConnection.HTTP_SERVER_ERROR);
            result.setMsg("IO error！");
            return result;
        }

    }
}
