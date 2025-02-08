FROM openjdk:17-jdk-alpine

WORKDIR /app

ENV INPUT="" ARGS=""

RUN mkdir -p /app/input /app/output

VOLUME ["/app/input", "/app/output"]

COPY ./dist/junit2avalla.jar /app/junit2avalla.jar

ENTRYPOINT ["java", "-jar", "junit2avalla.jar"]
CMD []
