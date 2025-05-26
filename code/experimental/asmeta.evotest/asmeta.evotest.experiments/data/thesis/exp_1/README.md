Configurations:

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
