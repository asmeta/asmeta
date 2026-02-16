## Asmeta Models and Avalla Acenarios for the Tutorial at FM 2026

This package contains the Asmeta models and Avalla scenarios for the Formal Methods 2026 tutorial "Scenario-Based Model Validation in Asmeta".

### Asmeta Models
The Asmeta specification in **Scheduler.asm** models a simple scheduler with the following characteristics:
- The Scheduler manages 3 jobs.
- Each job can be in one of three states: *ready*, *running*, or *finished*.
- At each step, the scheduler may non-deterministically select a job in state *ready* and set it to *running*.
- If no job is *ready*, the scheduler becomes *idle*.
- At each step, the environment may signal the completion of *running* jobs; in that case, those jobs become *finished*.
- Initial state:
		* All jobs are *ready*.
		* The scheduler is not *idle*.

Three additional specification are derived from the original model:
- **WrongScheduler.asm**: an intentionally incorrect version of the scheduler. In this model, when a *ready* job is selected, it is directly set to *finished*, skipping the *running* state.
  This faulty behavior is used to demonstrate how validation scenarios reveal specification errors.
- **SchedulerInvariant.asm**: An extension of **Scheduler.asm** that introduces a model invariant: *The environment may signal completion only for jobs in the running state*.
  This invariant allows simplification of the macro rule that updates job states and demonstrates invariant-based validation.
- **Scheduler_TLProp.asm**: This specification includes temporal logic properties that can be checked using the Asmeta model checker (via the TE – ``Translate into NuSMV and execute'' command).
  Although not covered during the tutorial, the model is provided as an example of property-based verification.

---

### Avalla Scenarios

The following Avalla scenarios demonstrate different aspects and techniques of scenario-based model validation within Asmeta.

**S1_BasicStart.avalla**
- Executed against Scheduler.asm
- Checks the initial state
- Executes one step
- Checks that no job is *finished*

**S2_Failure.avalla**
- Executed against WrongScheduler.asm
- Checks the initial state
- Executes one step
- Checks that at least one job is running  (this check fails due to the faulty implementation.)

**S3_InvariantViolation.avalla**
- Executed against SchedulerInvariant.asm
- Performs two steps
- Forces completion of all jobs, violating the invariant

**S4_SetSpecificConfig.avalla**
- Executed against Scheduler.asm
- Uses exec to reach the final configuration (all jobs finished, scheduler idle)
- Executes one step
- Checks that the state does not change

**S5_UsingBlocks.avalla**
- Executed against Scheduler.asm
- Uses blocks defined in **SchedulerBlocks.avalla**
- Checks the initial state
- Executes steps until the scheduler becomes idle
- Signals completion for all running jobs
- Performs a final step
- Checks that all jobs are finished

**S6_PickDeterministic.avalla**
- Executed against Scheduler.asm
- Uses pick to enforce a specific deterministic scheduling sequence

**S7_CheckInvariants.avalla**
- Executed against Scheduler.asm
- Extends **S5_UsingBlocks.avalla** by adding a scenario invariant: *All jobs must finish together*

**S8_RandomScenario.avalla**
- Executed against Scheduler.asm
- A 5-step scenario automatically generated using ATGT with random exploration
- The scenario is flaky, as its outcome depends on non-deterministic choices