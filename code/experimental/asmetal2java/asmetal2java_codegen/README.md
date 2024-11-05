# Asmetal2java
[![Static Badge](https://img.shields.io/badge/asmeta-main_repository-black?style=social&logo=github&link=https%3A%2F%2Fgithub.com%2Fasmeta%2Fasmeta)](https://github.com/asmeta/asmeta)
[![Asm2java - Build and Push CI Pipeline](https://github.com/asmeta/asmeta/actions/workflows/asmetal2java_pipeline.yml/badge.svg)](https://github.com/asmeta/asmeta/actions/workflows/asmetal2java_pipeline.yml)

Asmetal2java is a tool that automatically generates a Java file from an Abstract State Machine (ASM)
specification written in Asmeta. This allows developers and researchers to quickly convert ASM 
models into Java code, facilitating the integration of formal specifications into software 
development processes.

## Project Overview

The main project, **asmetal2java_codegen**, is responsible for translating an Abstract State Machine (ASM) into a Java class,
this is the `-mode translation` (executed by default), but it's not the only function operated by this project, in fact through Command-Line Options (CLI)
we can specify other modes, such as `-mode generateExe` or `-mode generateWin` if we want to generate also an executable java class to run the translated ASM
via console `Exe` or the via a Grapical User Interace (GUI) `Win`, or we can decide to use the `-mode testGen` which generates two specific classes
to test the ASM with automated tools, such as Evosuite. 
Finally we can use the `-mode custom` to obtain a customized behavior by adding additional options via `-Doption=value`, for example we can decide to compile the
generated java file to verify that there are no errors via `-Dcompiler=true`.

## How to Start:

### Using Maven
1. Open PowerShell or your preferred terminal and clone the repository using a password-protected SSH key:
    ```shell
    git clone git@github.com:asmeta/asmeta.git
    ```

2. Inatall the dependencies
   ```shell
    mvn install -DskipTests
    ```

3. Navigate into the project directory:
    ```shell
    cd ./code/experimental/asmetal2java/asmetal2java_codegen
    ```

4. Build and test the project, then generate the executable JAR:
    ```shell
    mvn clean verify
    ```

5. Start the application by running the generated JAR file:
    ```shell
    java -jar ./dist/asmetal2java.jar 
     ```

6. Customize execution with additional options:
    ```shell
   java -jar .\dist\asmetal2java.jar -input <input> -output <output> -D<property=value> -clean
    ```
    - `-input` : The ASM input file (required).

    - `-output` : The output folder (optional, defaults to `./output/`).

    - `-mode <modeName>` : Set the mode of the application.

    - `-D <property=value>` : Additional translator options.

    - `-clean` : Delete the files used by the translator in the input folder.
      
    - `-help` : Displays a help message describing all available options.

    See [Command-Line Options](#Command-Line_Options) for more infromations about the available command-line options.

8. Example of a use case:
    ```shell
    java -jar .\dist\asmetal2java.jar  -input "examples/RegistroDiCassav4.asm" -output "../asmetal2java_examples/src/" -mode translator -Dcompiler=true -clean
     ```

### Using Docker

1. Open Docker Desktop and pull the Docker image:
    ```shell
    docker pull isaacmaffeis/asmetal2java:latest
    ```

2. Mount the volumes and run:
    ```shell
    docker run --rm -v "$(pwd)/<input>:/app/input" -v "$(pwd)/<output>:/app/output" isaacmaffeis/asmetal2java -input <input> -output <output> -D<property=value> -clean
    ```

    - `-v "$(pwd)/<input>:/app/input"` : Maps the input file from the host to the container (required)

    - `-v "$(pwd)/<output>:/app/output"` : Maps the output folder from the host to the container (required)

3. Example of a use case:
   Copy the asm example file `RegistroDiCassav4.asm` from the `examples` directory into the `input` directory, 
   then, inside the `./amsetal2java_codegen` folder, run:
    ```shell
    docker run --rm -v "$(pwd)/input:/app/input" -v "$(pwd)/output:/app/output" isaacmaffeis/asmetal2java -input "input/RegistroDiCassav4.asm" -mode translator -Dcompiler=true -clean
    ```
## Command-Line Options

This section covers all available command-line options for the application and how to use them effectively.

 | Option  | Argument Type | Description |
 |---------|---------------|-------------|
 | `-input` | String (required) | Path to the ASM input file. |
 | `-output` | String (optional) | Specifies the output folder. Defaults to `./output/`. |  
 | `-clean` | |  Delete the files used by the translator in the input folder, please make sure you have enabled the export property -Dexport=true |
 | `-mode` | String (optional) | Set the mode of the application (select only 1 option) |
 | | `translator` | translate the asm file to a java file (default) |
 | | `generateExe` | translate the asm file to a java file and generate an executable java class |
 | | `generateWin` |  translate the asm file to a java file and generate an executable java class with a Grapical User Interace (GUI) |
 | | `testGen` |  generate a test class suited for test generation with Evosuite |
 | | `custom` |  set a custom behavior by adding properties with -D. |
 | `-Dformatter` | boolean (optional) | whether the generated code should be formatted. |
 | `-DshuffleRandom` | boolean (optional) |  whether a random shuffle should be applied. |
 | `-DoptimizeSeqMacroRule` | boolean (optional) | whether to optimize the sequence macro rule. |
 | `-Dtranslator` | boolean (optional) | whether to  translate the asm file to a java class. |
 | `-Dcompiler` | boolean (optional) | whether to translate and compile the generated java class. |
 | `-DgenerateExe` | boolean (optional) | whether to generate a java class for execution. |
 | `-DgenerateWin` | boolean (optional) | generate an executable java class with a GUI. |
 | `-DtestGen` | boolean (optional) | whether to  generate a specific java class designed for test generation with Evosuite. |
 | `-DcoverOutputs` | boolean (optional) | whether to  cover the outputs in the testGen class. |
 | `-DcoverRules` | boolean (optional) | whether to cover the rules in the testGen class. |
 | `-Dexport` | boolean (optional) | export the generated file into the output folder. |

 > **Note:** Please use translator, compiler, generateExe, generateWin and testGen options only if you have selected the -mode custom option.
