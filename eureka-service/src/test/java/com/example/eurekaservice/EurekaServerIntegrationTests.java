package com.example.eurekaservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // Укажите определенный порт
class EurekaServerIntegrationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void testServiceRegistration() {
        // Попробуйте зарегистрировать тестовый сервис
        String serviceUrl = "http://localhost:8761/eureka/apps/test-service"; // URL вашего сервиса
        String response = restTemplate.getForObject(serviceUrl, String.class);

        // Проверьте, что сервис зарегистрирован
        assertThat(response).contains("test-service"); // Проверяем, что в ответе есть название сервиса
    }
}
