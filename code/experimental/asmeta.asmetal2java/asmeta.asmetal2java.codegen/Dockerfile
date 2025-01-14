FROM openjdk:17-alpine

WORKDIR /app

RUN mkdir -p /app/input
RUN mkdir -p /app/output

VOLUME ["/app/input"]
VOLUME ["/app/output"]

COPY ./dist/asmetal2java.jar /app/asmetal2java.jar

ENTRYPOINT ["java", "-jar", "asmetal2java.jar"]