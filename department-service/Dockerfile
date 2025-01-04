#FROM maven:3.8.8-eclipse-temurin-17 AS build
#WORKDIR /app
#COPY . .
#RUN mvn clean package -DskipTests
#FROM eclipse-temurin:17-jre
#VOLUME /tmp
#EXPOSE 8081
#COPY --from=build /app/target/department-service-0.0.1.jar /department-service.jar
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/department-service.jar"]

FROM maven:3-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-alpine
COPY --from=build /target/*.jar department-service-0.0.1.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","department-service-0.0.1.jar"]