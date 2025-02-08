FROM openjdk:17-jdk-alpine

WORKDIR /app

ENV INPUT="" ARGS=""

RUN mkdir -p /app/input /app/output

VOLUME ["/app/input", "/app/output"]

COPY ./dist/asmetal2java.jar /app/asmetal2java.jar

ENTRYPOINT ["java", "-jar", "asmetal2java.jar"]
CMD []
