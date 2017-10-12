package com.nouhoun.springboot.jwt.integration;

import org.h2.server.web.DbStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SpringbootJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJwtApplication.class, args);
	}

	/*
  @Bean
  public DbStarter dbStarter() {
    return new DbStarter();
  }
  
  @Bean
  public ServletContextInitializer initializer() {
    return sc -> {
      sc.setInitParameter("db.user", "sa");
      sc.setInitParameter("db.password", "");
      sc.setInitParameter("db.tcpServer", "-tcpAllowOthers");
    };
  }
  */
}
