# Experimental Data Overview

This folder contains datasets and experimental outputs used across several papers and a thesis.  
Below is a brief description of each directory and the experiments they correspond to.

---

## Directory Summary

### `thesis-evoavalla-maffeis/`
Experiments for the thesis **"EvoAvalla: Generazione di Scenari Astratti per Asmeta mediante Trasformazioni di codice e Generazione di Test JUnit"**  
(student: Isaac Maffeis).  
The exact code used to reproduce these results (with the same structure) is no longer available.

### `ase-25-exp-OLD/`
Experiments for the paper **"Exploiting Unit Test Generators for Code to Generate Abstract Tests for Models"**,  
submitted to ASE 2025 and rejected.  
Includes results and the JARs/scripts needed to reproduce the experiments.  
See the contained `README.md` for additional details.

### `fm-short-26-exp/`
Experiments for the short paper **"Evaluating the Practical Impact of Parallelism in Asmeta"**,  
submitted to FM 2026.  
Contains all outputs, configuration files, and the required JARs/scripts to fully rerun the experiments.

### `icst-26-exp/`
Experiments for the paper *Generating Abstract Tests for Asmeta Models by Using Code Transformations and EvoSuite**,  
submitted to ICST 2026.  
Contains only results and the Python notebook used to analyze the data in `data.csv`. To replicate these experiments, use the JARs and scripts located in the base folder `data/`.

### `nfm-26-exp/`
Experiments for **Evaluating coverage and fault detection capability of scenarios for the validation of Asmeta specifications**,  
to be submitted to NFM 2026.
Contains only results. To replicate these experiments, use the JARs and scripts located in the base folder `data/`.

---

## Reproduction Notes

- If a directory includes its own JARs/scripts, you may reproduce the experiments directly from that folder.  
- If not, use the JARs and scripts provided in the base folder `data/`.  
- All result files (`.csv`, `.xlsx`, `.zip`, etc.) correspond either to the final outputs referenced in their respective papers or to WIP snapshots for submissions still in preparation.
