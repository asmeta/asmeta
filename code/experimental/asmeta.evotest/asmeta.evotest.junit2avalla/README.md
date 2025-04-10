# Junit2Avalla
[![Static Badge](https://img.shields.io/badge/asmeta-main_repository-black?style=social&logo=github&link=https%3A%2F%2Fgithub.com%2Fasmeta%2Fasmeta)](https://github.com/asmeta/asmeta)
[![Junit2Avalla - Build and Push CI Pipeline](https://github.com/asmeta/asmeta/actions/workflows/junit2avalla_pipeline.yml/badge.svg)](https://github.com/asmeta/asmeta/actions/workflows/junit2avalla_pipeline.yml)

## Project Overview

**Junit2Avalla**  is a tool that allows you to automatically convert a Junit test scenarios into Avalla scenarios with the aim of simplifying the testing procedure of an Abstract State Machine (ASM).

## How to start

### Using Maven

If you prefer to run the application directly using Maven, make sure you have installed:

  - Java 8+
  - Maven 3+

and follow these steps:

1. Open PowerShell or your preferred terminal and clone the repository using a password-protected SSH key:
    ```shell
    git clone git@github.com:asmeta/asmeta.git
    ```

2. Navigate into the project directory:
    ```shell
    cd ./code/experimental/asmeta.evotest/asmeta.evotest.junit2avalla
    ```

3. Build the Project:
   ```shell
   mvn clean package
   ```

4. Run the Application:
   ```shell
   java -jar ./dist/junit2avalla.jar -help
   ```

5. Customize execution with additional options:
   ```shell
   java -jar ./dist/junit2avalla.jar -input <input> -output <output> -clean
   ```

    - `-input` : The ASM input file (required).

    - `-output` : The output folder (optional, defaults to `./output/`).
    
    - `-parser` : Select the desired parser to parse the JUnit file. (optional, defaults to `customParser`)

    - `-clean` : Delete the files used by the translator in the input folder.
    
    - `-help` : Displays a help message describing all available options.

6. Example of a use case:
   ```shell
   java -jar .\dist\junit2avalla.jar -input "input/RegistroDiCassav4_ATG_ESTest.java" -parser customParser -clean 
   ```

### Using Docker

While if you prefer to use Docker make sure you have installed and opened Docker desktop, then follow these steps:

1. Open Docker Desktop and pull the Docker image:
    ```shell
    docker pull isaacmaffeis/junit2avalla:latest
    ```

2. Mount the volumes and run:
    ```shell
    docker run --rm -v "$(pwd)/<input>:/app/input" -v "$(pwd)/<output>:/app/output" isaacmaffeis/junit2avalla -input <input> -output <output> -clean
    ```

    - `-v "$(pwd)/<input>:/app/input"` : Maps the input file from the host to the container (required)

    - `-v "$(pwd)/<output>:/app/output"` : Maps the output folder from the host to the container (required)

3. Example of a use case:
   Copy the junit example file `RegistroDiCassav4_ATG_ESTest.java` into the `input` directory, 
   then, inside the `./junit2avalla` folder, run:
    ```shell
     docker run --rm -v "$(pwd)/input:/app/input" -v "$(pwd)/output:/app/output" isaacmaffeis/junit2avalla -clean
    ```

## Usage

After building the project, you can use it with the following command-line options:

### Command-Line Options

| Option             | Argument Type     | Description                                                                 	|
|--------------------|-------------------|------------------------------------------------------------------------------|
| `-help`            | None 			 | Prints the help message with a description of available options.            	|
| `-workingDir`	     | String			 | Specifies the custom working directory path. Defaults to `./input`.		   	|
| `-input`           | String (required) | Path to the Java input file.                                                	|
| `-output`          | String  			 | Specifies the output folder. Defaults to `./output/`.                       	|
| `-clean`           | None				 | Cleans the input and stepFunctionArgs files after the process.              	|
| `-parser`          | String			 | Select the desired parser to parse the JUnit file. Defaults to `customParser`|

## Developer guide
 
The project is structured in the following packages:
- [main](#main)
- [application](#application)
- [antlr](#antlr)
- [model](#model)
- [javascenario](#javascenario)
- [avallascenario](#avallascenario)
- [filewriter](#filewriter)


and in the following folders (in addition to the classic src and test):
- [input](#input)
- [output](#output)
- [generated-sources](#generated-sources)
 
### main
This package contains the **Junit2AvallaCI** class, responsible for generating a CLI application through the Apache Commons CLI package (integrated as a Maven dependency in the `pom.xml`) performs the task of being the access point for the application.

### application
The application package is responsible for exposing the methods to interact with the application from the outside,
this is the task of the **Translator** interface, which is implemented by the **TranslatorImpl** class.
The **FileManager** class provides support to the TranslatorImpl class for file writing operations, providing the paths to the folders.

### antlr
This package contains the grammar file **JavaScenario.g4** used to define the rules for the lexer and parser, enabling the decomposition of incoming JUnit files into tokens. It also includes all the autogenerated files required to support the parsing operations, generated by the ANTLR plugin. Such as the the **JavaScenarioBaseListener** class, which serves as a base class for creating custom listeners through inheritance, and the **JavaScenarioLexer**, **JavaScenarioParser**, and **JavaScenarioListener** classes, which are essential for parsing and processing operations.

### model
This package provides the object models used in this application. A key component of this package is the **terms** subpackage, which contains the models for both Java and Avalla terms. These terms play a critical role in processing and defining scenarios. The Java terms represent constructs specific to Java, while the Avalla terms capture elements unique to Avalla, enabling translation and integration between the two domains. The package also includes the **Scenario** class, which consist in a queue of an Avalla terms, representing a parsed scenario. Furthermore it includes the **ScenarioFile** class that models a file in the Avalla format. This class encapsulates the content of an Avalla file as a textual object.
<p>
The subpackage terms contains the classes that define and manage the terms involved in the translation process. The base class is the abstract superclass **Term**, which serves as the foundation for all term types. From this base class, two other abstract classes emerges, each addressing specific domains: `JavaTerm` and `AvallaTerm`.
The first branch, **JavaTerm**, introduces a type field and provides the structure for representing terms related to Java constructs. It is further extended by two concrete classes: `JavaAssertionTerm` and `JavaArgumentTerm`. The **JavaAssertionTerm** class is designed specifically for mapping JUnit assertions, capturing the `actual` and `expected` values of these operations. On the other hand, **JavaArgumentTerm** models a Java argument, characterized by its name and a boolean field, `isPrimitive`, which indicates whether the argument represents a primitive type. JavaArgument is extended by the **JavaVariableTerm** class, which adds a `value` field to encapsulate the specific value of a Java variable.
The second branch, **AvallaTerm**, shifts focus to Avalla-specific constructs. It is an abstract class extended by several concrete implementations, each represents distinct aspects of Avalla terms. For instance, **AvallaSetTerm** is used to map terms that form sets of a variable, incorporating fields for the `name` and `value` of the term. Similarly, **AvallaStepTerm** corresponds to an Avalla set term but does not introduce any additional fields. Beyond these, **AvallaCheckTerm** models check terms in Avalla, featuring `actual` and `expected` fields that mirror its Java counterpart. The package also includes the **AvallaHeaderTerm** class, which provides a way to store the name of a scenario through the `scenarioName` field. Finally, **AvallaLoadTerm** is designed to handle references to specific Asmeta models, storing the model’s name in the `asmName` field.

### javascenario
This package provides the implementation of the **JavaScenarioListener**, a crucial component for parsing JUnit scenarios using the grammar defined in the antlr package. The listener is designed to process JUnit scenarios creating immediately Avalla objects that are added to the scenario (a queue of Avalla terms). However, in cases where it is not possible to create Avalla objects immediately, the listener generates supporting Java objects instead and then translates it later into Avalla terms. For example, variables are created as temporary placeholders and stored in a dictionary, this allows their values to be resolved later when required, ensuring flexibility and accuracy in the parsing process. To manage the scenario construction, the listener relies on the **ScenarioManager** class, which handles the storage and organization of parsed avalla terms. Additionally, the package exposes the **ScenarioReader** interface, and its implementation **ScenarioReaderImpl**, providing external control over the scenario reading process.

### avallascenario
This package provides a set of interfaces and classes designed to manage Avalla scenarios, focusing on mapping scenario objects to scenario files and enabling writing operations. Central to the package is the **ScenarioListMapper** interface, which establishes a contract for converting a list of `Scenario` objects into a corresponding list of `ScenarioFile` objects. This functionality allows multiple scenarios to be systematically transformed into their file-based representations. Additionally, the **ScenarioWriter** class provides concrete methods to the implementation **ScenarioListMapperImpl** for writing individual `Scenario` objects into `ScenarioFile` instances.

### filewriter
This package provides the interface and implementation necessary for writing `ScenarioFile` objects to files with the `.avalla` extension. The package ensures a clear contract for this functionality through its interface **FileWriter**, implemented in the class **FileWriterImpl**. The new Avalla files are created in the specified output folder.

### input
The **input** folder is essential for the application to work, in fact the selected junit class is copied into this folder to be processed.
If we add the `-clean` option to the CLI, all the contents of this folder are deleted except the STDL folder and the gitignore.
If this folder does not exist the app will create a new one, we can also use the `-workingDir <path to input dir>` option to select another folder in a custom path on our PC.

### output
This is the default folder where avalla files are exported if you have not specified a custom **output** folder via `-output <path_to_output_dir>`.

### generated-sources
The **generated-sources** folder is automatically created at build time and contains all the java classes and tokens generated by the antlr plugin.

## Javadoc
javadoc for the project is available, to generate it make sure you have maven installed on your machine, then open a terminal, go to the root of the junit2avalla project and type:
```shell
mvn javadoc:javadoc
```
Once generated, the javadoc will be placed in the docs/apidocs folder and will be accessible through the `index.html` file.

