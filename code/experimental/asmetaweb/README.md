# Asmeta Web App

This project expose the Simulator@Runtime functionality through a Spring Backend and a HTML5 Frontend.


## Project Setup

Install NodeJs version LTS 20.11.1.

Install Java 17 and Eclipse.

## Backend Setup

The backend is builded with Spring Boot Framework version 3.2.0.
The asmetaweb project is created as Maven project.
It need differents dependencies from the Asmeta project:

![Screenshot](./dependencyScreenshot.png)

After resolving all the problems with dependencies, the asmeta web can be builded thought Eclipse, with Run As > Java Application

Here the run configuration:

![Screenshot](./serverConfigurationScreenshot.png)

There is also a API documentation thanks to Swagger, reachable at http://localhost:8080/swagger-ui/index.html

## Frontend Setup

The frontend is builded with HTML5, CSS and plain Javascript.
In addition it's used Wepback in order to compress and build the code.

The dependencies are all tracked in the package.json file, and in order to install  dependencies run the command:

```bash
    npm install
```

To build and test in a local machine with hot reload functionality (it creates a local web server on port 9000):
```bash
    npm run dev
```

To build and deploy in a production enviroment:
```bash
    npm run build
```
## Authors

- [@Lucio Marco Maranta](https://www.github.com/luciomarcomaranta)
