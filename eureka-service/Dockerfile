# Используйте базовый образ с Java Runtime
FROM openjdk:17-jdk-slim

# Создайте директорию для приложения
WORKDIR /app

# Скопируйте JAR-файл приложения в контейнер
COPY target/eureka-service-0.0.1-SNAPSHOT.jar /app/eureka-service.jar

# Откройте порт 8761, на котором будет работать Eureka Server
EXPOSE 8761

# Запустите JAR-файл приложения
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/eureka-service.jar"]