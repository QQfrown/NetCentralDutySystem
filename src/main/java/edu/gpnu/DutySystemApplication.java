package edu.gpnu;

import edu.gpnu.utils.WebUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("edu.gpnu.dao")
public class DutySystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(DutySystemApplication.class,args);
        System.out.println("启动成功~~");
    }
}
