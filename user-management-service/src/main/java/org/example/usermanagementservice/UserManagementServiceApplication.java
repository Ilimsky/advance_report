package org.example.usermanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {
        "org.example.departmentservice"
})
@EnableDiscoveryClient
public class UserManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagementServiceApplication.class, args);
    }

}
