**Note**: this `README` and the `experiments-runner.jar` come from the ASE 2025 replication package, available on [Zenodo](https://zenodo.org/records/15555337). Running the JAR file allows replication of the test generation and experiments for all the subfolders named `atgttests`, `evoavallatests`, and `randomtests`.
However, it is not possible to replicate the test generation using the same configuration applied to subfolders named `atgt_sefm` and `random100tests`. Once the configuration used for those cases is known, the same results can be reproduced by running the code included in this Java project.

# ASE 2025 - replication Package for: Exploiting Unit Test Generators for Code to Generate Abstract Tests for Models

This repository contains the replication package for the paper "Exploiting Unit Test Generators for Code to Generate Abstract Tests for Models" of [ASE conference 2025](https://conf.researchr.org/home/ase-2025).
The replication package includes the Asmeta models, the datasets supporting our findings, a Python notebook for used for data analysis and the JAR files required to replicate the experiments.

### Repository content
The package is structured as follows:

* `data/`:
    - `images/`: contains the plots and histograms presented in the paper.
    - `models/`: contains all the models used in the experiment described in the paper.
    - `results/`: contains all the scenarios generated from the 10 experimental runs, along with the corresponding CSV files containing validation and coverage data.
    - `DataAnalyzer.ipynb`: the Python notebook used for data analysis.
* `experiments-runner.jar`: the executable used to run the experiments.
* `evosuite-1.0.6.jar`: the EvoSuite version 1.0.6 executable used by `experiments-runner.jar`. This file must be located in the same directory as `experiments-runner.jar`.

### Running the experiments
In order to run `experiments-runner.jar` it is necessary to have Java JRE 21 or higher and Java JDK 8 on the machine. The JAR must be run with Java 21, Java JDK 8 is required to run EvoSuite.

Note that the scripts running the experiments looks for the `java.exe` file. Thus, it **only runs on Windows**!!.

 The JAR file requires three mandatory arguments:
* Path to Java JDK 8 – The absolute path to a Java Development Kit (JDK) version 8 installation.
* Path to the ASM specification – Either: (i) the path to a source folder containing .asm specification files, or (ii) the path to a single .asm specification file.
* Relative path to the target folder – The path (relative to the source folder) where the results will be stored.

The following is an example of how to run the server. First, navigate to the base folder of the replication package and execute the following command:

```bash
java -jar experiments-runner.jar local/path/to/Java/jdk1.8 ./data/models/PhdMaster.asm ../../results
```

After running the above command, the tool will process the specified ASM model (PhdMaster.asm) using the provided JDK 8 path and store the results in the `results` folder, which will be created in the base directory of the replication package. In addition, an `evoAvalla` folder will be generated, containing logs and coverage data produced during the execution of EvoAvalla. It can be safely deleted after the experiments are completed.