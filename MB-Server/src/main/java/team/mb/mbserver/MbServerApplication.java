package team.mb.mbserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MbServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MbServerApplication.class, args);
    }

}
