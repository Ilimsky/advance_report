package org.example.employee_departmentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {
        "org.example.employee_departmentservice",
        "org.example.departmentservice",
        "org.example.employeeservice"
})
@EnableDiscoveryClient
public class EmployeeDepartmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeDepartmentServiceApplication.class, args);
    }

}
