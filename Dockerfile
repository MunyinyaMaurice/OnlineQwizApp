FROM openjdk:17-jdk-alpine
MAINTAINER Maurice
COPY target/online_qwiz-0.1.0-SNAPSHOT.jar app.jar
EXPOSE 23901
ENTRYPOINT ["java", "-jar", "/app.jar"]
