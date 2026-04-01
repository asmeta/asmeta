asm ComputePlan2Charging
import ../../../STDL/StandardLibrary
import RoverDomains

signature:

// Inputs
dynamic monitored currentPosition: Position
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
static nearestCharger: Position * Powerset(Position) -> Position
static hasCharger: Powerset(Position) -> Boolean

definitions:

function hasCharger($cs in Powerset(Position)) =
    not(isEmpty($cs))

function nearestCharger($from in Position, $cs in Powerset(Position)) =
    chooseone $c in $cs with
        (forall $d in $cs with true do distManhattan($from,$c) <= distManhattan($from,$d))
    // If empty, this chooseone is undefined; guarded by hasCharger in rules

// --- CPC1: Planner computes a set of plans from goal to nearest charger
rule r_Planner_computePlanSet =
    if hasCharger(chargers) then
        // Abstract planner: nondeterministically produces a set of candidate plans.
        // Each plan must start at goal and end at nearest charger.
        choose $ps in Powerset(Plan) with true do
            par
                planSet := $ps
            endpar
    else
        planSet := {}
    endif

// --- CPC3: PRA selects shortest plan to charger as plan2C
rule r_PRA_selectShortest =
    if not(isEmpty(planSet)) then
        chooseone $p in planSet with
            (forall $q in planSet with true do planLength($p) <= planLength($q))
        do
            plan2C := $p
        endchoose
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