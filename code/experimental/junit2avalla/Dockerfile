FROM openjdk:8-jdk-alpine

WORKDIR /app

RUN mkdir -p /app/input
RUN mkdir -p /app/output

VOLUME ["app/input"]
VOLUME ["app/output"]

COPY dist/junit2avalla /app/junit2avalla.jar

ENTRYPOINT ["java", "-jar", "junit2avalla.jar"]
