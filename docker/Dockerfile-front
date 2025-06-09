FROM openjdk:21-jdk-slim

WORKDIR /app

COPY ./app.jar app.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","./app.jar"]