FROM openjdk:21-jdk-slim
LABEL maintainer="Saif"

COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]