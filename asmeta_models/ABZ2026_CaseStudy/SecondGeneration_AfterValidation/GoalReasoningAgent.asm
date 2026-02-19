asm GoalReasoningAgent
import ../../STDL/StandardLibrary
import RoverDomains
import HardwareInterface
import ComputePlan2Charging
export *

signature:

// Inputs

dynamic monitored goalSet: Boolean

// Output
// Internal: whether we are awaiting instructions (G5)
dynamic controlled awaitingInstructions: Boolean

// Helper
derived nearestChargerToNow: Powerset(Prod(Coord,Coord)) -> Prod(Coord,Coord)

definitions:

function nearestChargerToNow($cs in Powerset(Prod(Coord,Coord))) =
    chooseone ({$c in $cs | not(exist $d in $cs with distManhattan(currentPosition,$d) < distManhattan(currentPosition,$c)) : $c})

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
        if goalSet and awaitingInstructions then
        		goal := chooseone(Prod(Coord,Coord))
        endif
    endpar
