package ru.practicum.explore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ExploreMainSvcApp {
    public static void main(String[] args) {
        SpringApplication.run(ExploreMainSvcApp.class, args);
    }
}
