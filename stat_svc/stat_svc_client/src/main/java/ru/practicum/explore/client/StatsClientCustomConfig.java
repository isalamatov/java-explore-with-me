//package ru.practicum.explore.client;
//
//import feign.Feign;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class StatsClientCustomConfig {
//    @Bean
//    public StatsClient statsClient() {
//        return Feign.builder().target(StatsClient.class, "http://localhost");
//    }
//}
