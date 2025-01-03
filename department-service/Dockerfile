# Этап сборки
FROM maven:3.8.8-eclipse-temurin-17 AS build

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Этап выполнения (финальный образ)
FROM eclipse-temurin:17-jre

# Добавим volume для временных файлов
VOLUME /tmp

# Откроем порт 8081
EXPOSE 8081

# Скопируем только jar-файл из этапа сборки
COPY --from=build /app/target/department-service-0.0.1.jar /department-service.jar

# Запуск приложения
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/department-service.jar"]
