# Используйте базовый образ с Java Runtime
FROM openjdk:17-jdk-slim

# Создайте директорию для приложения
WORKDIR /app

# Скопируйте JAR-файл приложения в контейнер
COPY target/demo-0.0.1.jar /app/demo.jar

EXPOSE 8081

# Запустите JAR-файл приложения
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/demo.jar"]