# EvoAsmetaTG

## Project Overview

**EvoAsmetaTG** is an automated tool that uses Evosuite to generate test scenarios in Avalla language for an Abstract State Machine (ASM) specification.

## How to Start:

### Using Maven

1. Open PowerShell or your preferred terminal and clone the repository using a password-protected SSH key:
    ```shell
    git clone git@github.com:asmeta/asmeta.git
    ```

2. Install the dependencies
   ```shell
    mvn install -DskipTests
    ```
    install the **asmetal2java** service:
    ```shell
    cd asmeta\code\experimental\asmetal2java\asmetal2java_codegen
    mvn install -DskipTests
    ```
    install the **junit2avalla** service:
    ```shell
    cd  ..\..\asmeta.evotest\junit2avalla
    mvn install -DskipTests
    ```
	
3. Navigate into the project directory:
    ```shell
    cd ..\evoAsmetaTG\
    ```

4. Build and test the project, then generate the executable JAR:
    ```shell
    mvn clean verify
    ```

5. Start the application by running the generated JAR file:
    ```shell
    java -jar .\dist\evoasmetatg.jar
     ```

6. Customize execution with additional options:
    ```shell
   java -jar .\dist\asmetal2java.jar -workingDir <workingDir> -input <input> -output <output> -javaPath <path to the java jdk dir> -evosuitePath <path to the evosuite jars dir> -evosuiteVersion <desired evosuite version> -timeBudget <desired time budget for the evosuite process> -D<property=value> -clean
    ```
    
    - `-workingDir` : The custom working directory path (optional, defaults to `./input/`).
    
    - `-input` : The ASM input file (required).

    - `-output` : The output folder (optional, defaults to `./output/`).

    - `javaPath` : Set the path to the java jdk folder used to run Evosuite.
    
    - `evosuitePath` : Set the path to the evosuite jars folder. Defaults to `./evosuite/evosuite-jar`.
    
    - `evosuiteVersion` : Set the version of Evosuite to use for test scenarios generation.
    
    - `timeBudget` :  Set the time budget allocated for the Evosuite process.

    - `-D <property=value>` : Additional translator options.

    - `-clean` : Delete the files used by the translator in the input folder.
      
    - `-help` : Displays a help message describing all available options.

    See [Command-Line Options](#Command-Line_Options) for more infromations about the available command-line options.

8. Example of a use case:
    ```shell
    java -jar .\dist\evoasmetatg.jar  -workingDir "." -input ".\src\test\resources\Pillbox_1.asm" -output ".\output" -javaPath "C:\Program Files\Java\jdk-1.8" -evosuitePath "evosuite\evosuite-jar" -evosuiteVersion "1.0.6" -timeBudget 10 -clean
     ```

### Using the zip file

1. Obtain the zip file from the Asmeta repository and extract its contents to a directory of your choice on your computer.

2. Run the extracted JAR file using the following command:
    ```shell
    java -jar .\example\evoasmetatg.jar
     ```

3. Customize execution with additional options:
    ```shell
   java -jar .\dist\asmetal2java.jar -workingDir <workingDir> -input <input> -output <output> -javaPath <path to the java jdk dir> -evosuitePath <path to the evosuite jars dir> -evosuiteVersion <desired evosuite version> -timeBudget <desired time budget for the evosuite process> -D<property=value> -clean
    ```
    By default, the application creates the working directories and intermediate files in the same directory where the Java application is launched.
    To keep your environment organized, use the `-workingDir <pat to the working dir>` option to specify a separate folder for the generated file

## Command-Line Options

This section covers all available command-line options for the application and how to use them effectively.

 | Option  				| Argument Type 	| Description 																					 |
 |----------------------|-------------------|------------------------------------------------------------------------------------------------|
 | `-workingDir` 		| String 			| Path to the working directory of the application. Defaults to `.`. 							 |
 | `-input` 			| String (required)	| Path to the ASM input file. 																	 |
 | `-javaPath` 			| String (required)	| Set the path of java jdk folder used to run Evosuite. Example: "C:\Program Files\Java\jdk-1.8".|
 | `-evosuiteVersion` 	| String (required) | Set the version of Evosuite to use for test scenarios generation. 							 |
 | `-evosuitePath` 		| String 			| Set the path to the evosuite jars folder. Defaults to `./evosuite/evosuite-jar`. 	 			 |
 | `-output`			| String 			| Specifies the output folder. Defaults to `./output/`. 										 |  
 | `-clean` 			| None				| Delete all intermediate files created and processed by the application. 						 |
 | `-timeBudget` 		| String 			| Set the time budget allocated for the Evosuite process. 										 |
 | `-Dcompiler` 		| boolean 			| whether to translate and compile the generated java class. 									 |
 | `-DcoverOutputs` 	| boolean 			| whether to  cover the outputs in the testGen class. 											 |
 | `-DcoverRules` 		| boolean 			| whether to cover the rules in the testGen class. 												 |
 
### Example

Below is an example use case using the above mentioned options:
```
-workingDir "."
-input ".\src\test\resources\Pillbox_1.asm"
-output ".\output"
-javaPath "C:\Program Files\Java\jdk-1.8"
-evosuitePath "evosuite/evosuite-jar"
-evosuiteVersion "1.0.6"
-timeBudget 10
-clean
```

## Developer guide

The project is structured in the following packages:
- [main](#main)
- [application](#application)
- [config](#config)

and in the following folders, in addition to the classic src and test (considering the default position of the working directory: `-workingDir "."`):
- [asmetal2java](#asmetal2java)
- [output](#output)
- [evosuite/evosuite-jars](#evosuite-evosuite-jars)
- [evosuite/evosuite-target](#evosuite-evosuite-target)
- [evosuite/evosuite-report](#evosuite-evosuite-report)
- [evosuite/evosuite-tests](#evosuite-evosuite-tests)
- [junit2avalla](#junit2avalla)
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

### output
This is the default folder where files are exported if you have not specified a custom **output** folder via `-output <path_to_output_dir>`.

### asmetal2java
This folder performs the role of the asmetal2java project input folder, in fact it is essential to run the asmetal2java application, because the selected Asmeta specification is copied into this folder to be processed. Based on the mode chosen during execution, specific subfolders are created with the same name as the mode, for example with the testGen mode the testGen subfolder is created. The required files are generated within these folders. There is also the SDL folder that contains the asm specification libraries. 
If this directory doesn't exist the application creates a new one with the required libraries.
If we add the `-clean` option to the CLI, all the contents of the asmetal2java folder are deleted except the STDL folder.

### evosuite/evosuite-jars
This directory contains the Evosuite jars, the application by default searches in this folder for the selected evosuite jar, we can provide another path using `-evosuitePath <path to the evosuite path dir>`

### evosuite/evosuite-target
This folder is where Evosuite looks for compiled java files (.class) to generate junit tests, so it's the output folder of the asmetal2java service and the input folder for Evosuite. 
Its content is deleted at each use.

### evosuite/evosuite-report
This folder is automatically created by Evosuite and contains a csv file with the statistics of the junit scenario generation. This file is not deleted even if we specify the `-clean` option.

### evosuite/evosuite-tests
This folder is automatically created by Evosuite and contains the junit tests produced. It is used as input folder for the junit2Avalla service.

### junit2avalla
This folder performs the role of the junit2avalla project input folder, in fact the junit file generated by evosuite is copied into this directory in order to be processed by junit2avalla service. 
If we add the `-clean` option to the CLI, all the contents of the junit2avalla folder are deleted.

### logs
This folder contains the logging file with the level of debug, created by log4j `debug.log`. The configuration file for this logging system is present in `src/main/resources/log4j2.yaml`, it needs the maven dependencies of `log4j-api` , `log4j-core` and `jackson-dataformat-yaml` specified in the `pom.xml`.

## Javadoc
javadoc for the project is available, to generate it make sure you have maven installed on your machine, then open a terminal, go to the root of the evoasmetatg project and type:
```shell
mvn javadoc:javadoc
```
Once generated, the javadoc will be placed in the docs/apidocs folder and will be accessible through the `index.html` file.
