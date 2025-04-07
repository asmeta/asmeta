FROM openjdk:17-jdk-alpine

WORKDIR /app

ENV INPUT="" WORKING_DIR="" OUTPUT="" CLEAN="" PARSER="" HELP=""

RUN mkdir -p /app/input /app/output

VOLUME ["/app/input", "/app/output"]

COPY ./dist/junit2avalla.jar /app/junit2avalla.jar
COPY ./entrypoint.sh /app/entrypoint.sh

# Convert to UNIX format
RUN dos2unix /app/entrypoint.sh

RUN chmod +x /app/entrypoint.sh

ENTRYPOINT ["/app/entrypoint.sh"]
