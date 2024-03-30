#FROM openjdk:17-jdk-alpine
#MAINTAINER Maurice
#COPY target/online_qwiz-0.1.0-SNAPSHOT.jar app.jar
#EXPOSE 23901
#ENTRYPOINT ["java", "-jar", "/app.jar"]

#
#FROM maven:3.9.6-openjdk:17 AS build
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean install

##Stage 2: Run the application
#FROM openjdk:17-alpine
#WORKDIR /app
#COPY --from=build /app/target/online_qwiz-0.1.0-SNAPSHOT.jar ./online_quiz.jar
#EXPOSE 23901
#CMD ["java", "-jar", "/online_quiz.jar"]


#FROM maven:latest AS build
#
#COPY pom.xml .
#RUN mvn package
#
#FROM openjdk:17-jdk-slim
#
#COPY --from=build target/online_qwiz-0.1.0-SNAPSHOT.jar app.jar
#
#EXPOSE 23901
#
#ENTRYPOINT ["java", "-jar", "app.jar"]
#
#FROM maven:3.8.5-openjdk-17 AS build
FROM maven:3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/online_qwiz-0.1.0-SNAPSHOT.jar quiz.jar
EXPOSE 23901
ENTRYPOINT ["java","-jar","quiz.jar"]