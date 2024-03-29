FROM openjdk:17-jdk-alpine
MAINTAINER Maurice
COPY target/online_qwiz-0.1.0-SNAPSHOT.jar apps.jar
EXPOSE 23991
ENTRYPOINT ["java", "-jar", "/apps.jar"]
