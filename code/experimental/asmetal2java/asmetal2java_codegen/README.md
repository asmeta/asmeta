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


## Developer guide
 
The project is structured in the following packages:
- [main](#main)
- [application](#application)
- [generator](#generator)
- [compiler](#compiler)
- [formatter](#formatter)
- [evosuite](#evosuite)
- [translator](#translator)
- [config](#config)

and in the following folders (in addition to the classic src and test):
- [input](#input)
- [output](#output)
- [examples](#examples)
- [xtend-gen](#xtend-gen)
 
### main
This package contains the **Asmeta2JavaCLI** class, responsible for generating a CLI application through the Apache Commons CLI package (integrated as a Maven dependency in the `pom.xml`) performs the task of being the access point for the application.
### application
The application package is responsible for exposing the methods to interact with the application from the outside,
this is the task of the **Translator** interface, which is implemented by the **TranslatorImpl** class.
The **FileManager** class provides support to the TranslatorImpl class for file writing operations, providing the paths to the folders and allows the communication with the generators and the compiler.
There is also the custom exception **AsmParsingException** that handles the case of .asm file parsing error.
### generator
In the generator package there are all the generator classes of this application, so we can find the abstract parent class **AsmToJavaGenerator** that provides the `compileAndWrite(...)` method that is redefined in all the specific generators for a more accurate generation.
Then we have the **JavaGenerator** class that allows you to generate the classic translation of the asm specification to the java class, the **JavaExeGenerator** class instead generates a class to interact with the java class translated by the `JavaGenerator` via command line application, while **JavaWindowGenerator** generates a class with the graphical interface always to interact with the class generated by the `JavaGenerator`. Different task instead for the **JavaTestGenerator** class that generates a translated java class with the aim of testing the asm specification via Evosuite, in fact the java class is modified by removing the signature and making all the static fields private, some controls are added via boolean variables to cover the rules.
Finally the **JavaAtgGenerator** class allows you to generate a wrapper class to test the class generated by the `JavaTestGenerator` generator as a abstract state machine, in fact it only exposes the `set()`, `step()` and `get()` methods, in this way it is possible to pass this class to Eevosuite to generate junit test scenarios on the translated java class, it is important to keep the instance of the class generated with JavaTestGenerator private to hide it from the outside so Evosuite can't reach and use it.
### compiler
The compiler package contains the **Compiler** and **CompileResult** interfaces that expose outside the package methods to compile the file, or a list of files and to obtain the result of the operation.
The implementations are performed by the **CompilerImpl** and **CompileResultImpl** classes. The compiler provides the choice of the java version with which to compile the generated java files, this is because if you want to use Evosuite to generate test scenarios it is necessary to compile the files with the java version used by Evosuite, instead if you are interested in the classical translation you can use the default version which is the 17.
Finally the package contains the **NotValidFileException** thrown if the compiler has errors while searching for files.
### formatter
This package provides the **Formatter** interface with its **FormatterImpl** implementation that allows you to format a generated java class.
### evosuite
In this package we can find all the classes needed to generate the wrapper class named with `_ATG` and the translated class for the tests generated respectively by the `JavaAtgGenerator` and `JavaTestGenerator` generators.
In fact we find the redefinition of some translator classes via inheritance (**DomainToJavaEvosuiteSigDef**, **FunctionToJavaEvosuiteSig**, **RuleToJavaEvosuite**, **ToStringEvosuite**), some classes to support the generation of the _ATG class (**AsmMethods**, **CoverOutputs**, **CoverRules**) and classes that manage the java rules.
Specifically, the **JavaRule** class defines an object that consists of the translated java rule, as fields it has the name of the rule, a list of branches of the rule and the number of branches that are currently in the list. The branch in the list is represented as a string with the format `branch_<ruleName>_<count>` , the class also provides a method to allow the ordered insertion of branches in the list.
The **RulesMap** class instead contains a map with the key the name of the rule and contains the java rules, it performs the task of being a buffer and therefore allows you to add and request the rules: to add the rules use the **RulesGetter** interface, while to request them use **RulesAdder**. 
### translator
The translator package contains all the translation support classes used by generators for specific tasks. In general there are the classes:
**DomainToJavaSigDef**, **FindMonitoredInControlledFunct**, **FunctionToJavaDef**, **FunctionToJavaSig**, **RuleToJava**, **SeqRuleCollector**, **TermToJava**, **TermToJavaInAssignments**, **TermToJavaInUpdateRule**, **TermToJavaStandardLibrary**, **ToString**, **Util**, **ExpressionToJava**, **InvalidFunctionException**.
### config
This configuration package includes the **TranslatorOption** interface that includes all the available operations regarding the translation options. The implementation of this interface is contained in the **TranslatorOptionImpl** class, which works through a `HashMap<String,Consumer<Boolean>>` that is a map with the property name as key and a lambda function as content to assign the value to the property, this was designed to reduce the cyclomatic complexity of the class. In addition to this, there is the **ModeConstantsConfig** class that contains the configuration constants that are exposed externally in order to have consistency with other projects and the **Mode** enumerative class that represents the various modes of use of the application.

### input
The **input** folder is essential for the application to work, in fact the selected asm specification is copied into this folder to be processed. Based on the mode chosen during execution, specific subfolders are created with the same name as the mode, for example with the translator mode the translator subfolder is created, generateExe with the generateExe mode, etc... The required files are generated within these folders. There is also the SDL folder that contains the asm specification libraries, be careful to not remove this folder.
If we add the `-clean` option to the CLI, all the contents of the input folder are deleted except the STDL folder.
### output
This is the default folder where files are exported if you have selected the CLI option `-Dexport=true` and have not specified a custom **output** folder via `-output <path_to_output_dir>`.
### examples
In this folder we can find many **examples** of asm used to make tests or provided to the user to get familiar with the application.
### xtend-gen
The **xtend-gen** folder is automatically created at build time and contains all the java classes generated from the xtend classes.

## Javadoc
javadoc for the project is available, to generate it make sure you have maven installed on your machine, then open a terminal, go to the root of the asmetal2java_codegen project and type:
```shell
mvn javadoc:javadoc
```
Once generated, the javadoc will be placed in the docs/apidocs folder and will be accessible through the `index.html` file.