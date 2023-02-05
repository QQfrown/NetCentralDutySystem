package edu.gpnu.autoTask;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SigningSchedule {


    //每天零时零分零秒清除过期签约信息
    @Scheduled(cron = "0 0 0 1/1 * ?")
    private void checkSigning(){
        System.out.println(111);
    }
}
