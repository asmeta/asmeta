asm GoalReasoningAgent
import ../../../STDL/StandardLibrary
import RoverDomains

signature:

// Inputs
dynamic monitored chargers: Powerset(Position)
dynamic monitored recharge: Boolean
dynamic monitored noplan: Boolean
dynamic monitored failure: Boolean

// Output
dynamic controlled goal: Position

// Internal: whether we are awaiting instructions (G5)
dynamic controlled awaitingInstructions: Boolean

// Helper
derived nearestChargerToNow: Powerset(Position) -> Position
dynamic monitored currentPosition: Position

definitions:

function nearestChargerToNow($cs in Powerset(Position)) =
    chooseone $c in $cs with
        (forall $d in $cs with true do distManhattan(currentPosition,$c) <= distManhattan(currentPosition,$d))

// G2: if recharge=true then set goal to nearest charger
rule r_setGoalToNearestCharger =
    if recharge and not(isEmpty(chargers)) then
        goal := nearestChargerToNow(chargers)
    endif

// G5: if either planning component returns noplan, inform GS and await instructions.
// In reduced architecture we model the “await” as a control flag.
rule r_handleNoPlanOrFailure =
    if (noplan or failure) then
        awaitingInstructions := true
    else
        awaitingInstructions := false
    endif

macro rule r_GRA =
    par
        r_handleNoPlanOrFailure[]
        // only adjust goal if we are not blocked awaiting instructions
        if not(awaitingInstructions) then
            r_setGoalToNearestCharger[]
        endif
    endpar