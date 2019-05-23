package com.icecream.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The Spring Boot Application class.
 */
@SpringBootApplication
@EnableScheduling
public class IceCreamServerApplication {

  /**
   * Run the spring application.
   *
   * @param args arguments for spring application
   */
  public static void main(String[] args) {
    SpringApplication.run(IceCreamServerApplication.class, args);
  }
}