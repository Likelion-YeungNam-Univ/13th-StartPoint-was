package com.example.yu13thstartpointer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
@SpringBootApplication
public class Yu13thStartPointerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Yu13thStartPointerApplication.class, args);
    }

}
