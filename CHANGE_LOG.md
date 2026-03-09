## version 26.02
 - change on how the animator deals with execptions/errors - see [164](https://github.com/asmeta/asmeta/issues/164)
 - fixed a bug on how nusmv is called in macos
 - intervals of reals now are precise (see {0.6,0.7,0.8,0.9} was {0.6,0.799999 ... }
 - now the invariants are translated correctly in nusmv [113](https://github.com/asmeta/asmeta/issues/113)

## version 25.09
 - the with clause in choose is now optional: `choose $x in D do ...` is now correct
 - choose rule can be simulated even with inifinite domain if the condition is true: `choose $x in Integer with true do ...` is now simulated
 - branch coverage: now also considers forall and choose rules  
 - new coverage metrics:
    - rule coverage: measures the percentage of basic transition rules (including seq rules) executed at least once during validation.
    - forall rule coverage: measures how extensively forall rules were executed during validation.

## release 25.06

- fix of bug  *animator does not work with TermAsRule/RuleAsTerm* [135](https://github.com/asmeta/asmeta/issues/135)
- random test generation in UI - new feature [134](https://github.com/asmeta/asmeta/issues/134)
- several bug fixes for ATGT for the experiments for submitted paper (more soon)
- **undef** as monitored value [123](https://github.com/asmeta/asmeta/issues/123)
- other minor bugs 

## version 25.04
 - pick command is now introduced (see our NFM paper)
 - coverage of branches and decisions
 - some problems with dark theme
 - double messages in the console

## version 25.03
 - static and derived functions are now distinguished
 - invariant are translated to INVARSPEC
 - other minor bugs fixed

## version 24.06
 - new TL operators names and minor fixes - multiple architecture (win, lin, macos):
