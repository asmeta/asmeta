asm ComputePlan2Charging
import ../../STDL/StandardLibrary
import RoverDomains
export *

signature:

// Inputs
dynamic monitored currentPosition: Position
dynamic monitored goal: Position
dynamic monitored chargers: Powerset(Position)

// Optional: allow environment to signal a planner failure (timeout etc.)
dynamic monitored planningFailure: Boolean

// Outputs
dynamic controlled plan2C: Plan
dynamic controlled noplan: Boolean
dynamic controlled failure: Boolean

// Internal: set of candidate plans returned by the Planner
dynamic controlled planSet: Powerset(Plan)

// Helper (derived): nearest charger to a given position (if any)
static nearestCharger: Prod(Position, Powerset(Position)) -> Position
static hasCharger: Powerset(Position) -> Boolean

definitions:

function hasCharger($cs in Powerset(Position)) =
    not(isEmpty($cs))

// --- CPC1: Planner computes a set of plans from goal to nearest charger
rule r_Planner_computePlanSet =
    if hasCharger(chargers) then
        // Abstract planner: nondeterministically produces a set of candidate plans.
        // Each plan must start at goal and end at nearest charger.
        choose $ps in Powerset(Plan) with true do
            planSet := $ps
        endchoose
    else
        planSet := {}
    endif

// --- CPC3: PRA selects shortest plan to charger as plan2C
rule r_PRA_selectShortest =
    if not(isEmpty(planSet)) then
        plan2C :=
            chooseone(
                { $p in planSet |
                    not(exist $q in planSet with planLength($q) < planLength($p))
                }
            )
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
        r_Planner_computePlanSet[]
        r_PRA_selectShortest[]
        r_setNoPlanFlag[]
        r_setFailureFlag[]
    endpar