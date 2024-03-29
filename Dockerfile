FROM openjdk:17-jdk-alpine
MAINTAINER Maurice
COPY target/online_qwiz-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 23991
ENTRYPOINT ["java", "-jar", "/app.jar"]
