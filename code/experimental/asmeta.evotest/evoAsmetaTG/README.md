# EvoAsmetaTG

## Project Overview

**EvoAsmetaTG** is an automated tool that uses Evosuite to generate test scenarios in Avalla language for an Abstract State Machine (ASM) specification.

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
    cd ./code/experimental/asmeta.evotest/evoAsmetaTG
    ```

<!-- TODO: choose if run by jar file or mvn:exec plugin -->

## Developer guide

The project is structured in the following packages:
- [main](#main)
- [application](#application)
- [config](#config)

and in the following folders (in addition to the classic src and test):
- [input](#input)
- [output](#output)
- [evosuite-target](#evosuite-target)
- [evosuite-report](#evosuite-report)
- [evosuite-tests](#evosuite-tests)
- [logs](#logs)

### main
This package contains the **EvoAsmetaTgCLI** class, responsible for generating a CLI application through the Apache Commons CLI package (integrated as a Maven dependency in the `pom.xml`) performs the task of being the access point for the application.

### application
The application package is responsible for exposing the methods to interact with the application from the outside,
this is the task of the **Translator** interface, which is implemented by the **TranslatorImpl** class.
The **TranslatorConstants** class contains all the constants used by the Translator in this package.
There is also the custom exception **TranslationException** that handles the errors that occur during the translation process.

### config
This configuration package includes the **Options** interface that includes all the available operations regarding the options of the Translator.
The implementation of this interface is provided by the **OptionsImpl** class. 

### input
This input folder performs the role of the asmetal2java project input folder, in fact it is essential to run the asmetal2java application, because the selected Asmeta specification is copied into this folder to be processed. Based on the mode chosen during execution, specific subfolders are created with the same name as the mode, for example with the testGen mode the testGen subfolder is created. The required files are generated within these folders. There is also the SDL folder that contains the asm specification libraries, be careful to not remove this folder.
If we add the `-clean` option to the CLI, all the contents of the input folder are deleted except the STDL folder.

### output
This is the default folder where files are exported if you have not specified a custom **output** folder via `-output <path_to_output_dir>`.

### evosuite-target
This folder is where Evosuite looks for compiled java files (.class) to generate junit tests, so it's the output folder of the asmetal2java service and the input folder for Evosuite. 
Its content is deleted at each use.

### evosuite-report
This folder is automatically created by Evosuite and contains a csv file with the statistics of the junit scenario generation. For the purposes of this application it is not interesting

### evosuite-tests
This folder is automatically created by Evosuite and contains the junit tests produced. It is used as input folder for the junit2Avalla service.

### logs
This folder contains the logging file with the level of debug, created by log4j `debug.log`. The configuration file for this logging system is present in `src/main/resources/log4j2.yaml`, it needs the maven dependencies of `log4j-api` , `log4j-core` and `jackson-dataformat-yaml` specified in the `pom.xml`.
