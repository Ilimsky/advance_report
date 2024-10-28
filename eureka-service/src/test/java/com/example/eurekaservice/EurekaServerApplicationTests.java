package com.example.eurekaservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
class EurekaServerApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(EurekaServerApplicationTests.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        logger.info("Тест запускается...");
        assert(applicationContext != null);
        logger.info("Контекст загружен успешно.");
    }
}
