FROM openjdk:17-jdk-alpine
COPY build/libs/*.jar app.jar
ENTRYPOINT["java", "-jar", "-Dspring.profile.active=prod", "/app.jar"]