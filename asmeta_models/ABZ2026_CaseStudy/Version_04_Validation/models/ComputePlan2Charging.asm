asm ComputePlan2Charging
import ../../../STDL/StandardLibrary
import RoverDomains
import HardwareInterface
export *

signature:

// Inputs

// Optional: allow environment to signal a planner failure (timeout etc.)
dynamic monitored planningFailure: Boolean

// Outputs
dynamic controlled failure: Boolean

// Internal: set of candidate plans returned by the Planner
dynamic monitored planSet: Powerset(Seq(Prod(Coord,Coord)))

// Helper (derived): nearest charger to a given position (if any)
static hasCharger: Powerset(Prod(Coord,Coord)) -> Boolean

definitions:

function hasCharger($cs in Powerset(Prod(Coord,Coord))) =
    not(isEmpty($cs))


// --- CPC3: PRA selects shortest plan to charger as plan2C
rule r_PRA_selectShortest =
    if not(isEmpty(planSet)) then
        plan2C :=         
        	chooseone ({$p in planSet | not(exist $q in planSet with planLength($q) > planLength($p)) : $p})   
    endif

// --- CPC4: noplan iff no viable plans, else send plan2C (here: assign plan2C)
rule r_setNoPlanFlag =
    if isEmpty(planSet) then
        noplan := true
    else
        noplan := false
    endif

// --- CPC5: if planning failure occurs, notify GRA via failure flag
rule r_setFailureFlag =
    if planningFailure then
        failure := true
    else
        failure := false
    endif

macro rule r_ComputePlan2Charging =
    par
        r_PRA_selectShortest[]
        r_setNoPlanFlag[]
        r_setFailureFlag[]
    endpar
