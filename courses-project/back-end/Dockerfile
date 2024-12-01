FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml /app
COPY src /app/src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar /app/application.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/application.jar"]
