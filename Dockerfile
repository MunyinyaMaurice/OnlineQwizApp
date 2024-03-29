FROM openjdk:17-jdk-alpine
MAINTAINER Maurice
COPY target/online_qwiz-0.1.0-SNAPSHOT.jar online-qwiz-app.jar
EXPOSE 23991
ENTRYPOINT ["java", "-jar", "/online-qwiz-app.jar"]
