FROM openjdk:17-jdk-alpine

WORKDIR /app

ENV INPUT="" WORKING_DIR="" OUTPUT="" MODE="" COMPILER_VERSION="" CLEAN="" HELP="" PROPERTIES=""

RUN mkdir -p /app/input /app/output

VOLUME ["/app/input", "/app/output"]

COPY ./dist/asmetal2java.jar /app/asmetal2java.jar
COPY ./entrypoint.sh /app/entrypoint.sh

# Convert to UNIX format
RUN dos2unix /app/entrypoint.sh

RUN chmod +x /app/entrypoint.sh

ENTRYPOINT ["/app/entrypoint.sh"]
