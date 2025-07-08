Configurations:

The report csv files in this folder contain the mean values ​​of 10 experiments with the following settings:

- asmetal2java updated with abstract type collections

EvoAvalla:
- EvoSuite version: 1.0.6
- JavaVersion: 8
- Evosuite Budget: No budget (Evosuite defaults)
- criterion LINE:BRANCH

Random:
- number of tests and steps based on evoAvalla test suite
- if evoAvalla fails, default values are:
    - number of test: 5
    - number of steps: 5

Atgt:
- criteria:
    - COMPLETE_RULE
    - RULE_GUARD
- maxTest: Integer.MAX_VALUE

The subfolders contain the individual experiments that were used to compose the mean report csvs
