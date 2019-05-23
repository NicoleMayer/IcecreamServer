package com.icecream.server;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan({"com.icecream.server"})

@EnableScheduling
public class AopConfig {
}
