package org.example.revizorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RevizorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RevizorServiceApplication.class, args);
    }

}
