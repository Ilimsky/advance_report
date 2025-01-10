FROM maven:3-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-alpine
COPY --from=build /target/*.jar job-service-0.0.1.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","job-service-0.0.1.jar"]