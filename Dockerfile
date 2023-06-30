FROM gradle:8.1.1-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM eclipse-temurin:17-jdk-alpine AS package
VOLUME /tmp
COPY build/libs/*.jar app.jar
EXPOSE 25495
ENTRYPOINT ["java","-jar","/app.jar"]
