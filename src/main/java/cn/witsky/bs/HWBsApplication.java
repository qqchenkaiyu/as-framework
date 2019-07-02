package cn.witsky.bs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HWBsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HWBsApplication.class, args);
    }

}
