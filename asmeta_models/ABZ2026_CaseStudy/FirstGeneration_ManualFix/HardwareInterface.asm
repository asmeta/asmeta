asm HardwareInterface
import ../../STDL/StandardLibrary
import RoverDomains

export *

signature:

// Inputs
dynamic monitored goal: Position
dynamic monitored chargers: Powerset(Position)
dynamic monitored plan2C: Plan
dynamic monitored noplan: Boolean
dynamic monitored chargingComplete: Boolean

// From BatteryMonitor
dynamic monitored batteryLevel: BatteryPct

// Outputs / state
dynamic controlled currentPosition: Position
dynamic controlled recharge: Boolean
dynamic controlled atGoal: Boolean

// Local execution state (active movement plan)
dynamic controlled activePlan: Plan
dynamic controlled moving: Boolean
dynamic controlled isAtCharger: Boolean

// Parameters
static safetyMarginSteps: Integer

// Helper
static stepsToFinishPlan: Plan -> Integer
static isChargerPos: Prod(Position, Powerset(Position)) -> Boolean

definitions:

function safetyMarginSteps = 1

function stepsToFinishPlan($pl in Plan) =
    planLength($pl)

function isChargerPos($p in Position, $cs in Powerset(Position)) =
    contains($cs, $p)

// HI1: set recharge true if battery not enough to reach goal and then a charger.
// In reduced architecture we approximate needed steps as:
//   steps to reach goal: dist(currentPosition, goal)
//   steps from goal to charger: length(plan2C)   (computed by CPC)
// plus a small safety margin.
rule r_setRechargeFlag =
    if (batteryLevel <= itor(distManhattan(currentPosition, goal) + stepsToFinishPlan(plan2C) + safetyMarginSteps)) then
        recharge := true
    else
        recharge := false
    endif

// Track atGoal (goal may be a charger when recharge=true and GRA sets it)
rule r_setAtGoal =
    atGoal := (currentPosition = goal)

// Keep isAtCharger updated (for SolarPanelController)
rule r_setIsAtCharger =
    isAtCharger := isChargerPos(currentPosition, chargers)

// Load plan2C as the active plan when moving toward charger-goal
rule r_loadPlan =
    if (not(noplan) and recharge) then
        par
            activePlan := plan2C
            moving := true
        endpar
    endif

// Execute one movement step along the active plan (1 unit battery consumed is assumed externally;
// SL1 is handled via invariants in the main machine)
rule r_moveOneStep =
    if moving then
        if not(isEmptyPlan(activePlan)) then
            par
                currentPosition := headPos(activePlan)
                activePlan := tailPlan(activePlan)
            endpar
        else
            moving := false
        endif
    endif

// HI2: once at a charging position, remain there until chargingComplete
rule r_holdAtChargerUntilRecharged =
    if isAtCharger then
        if not(chargingComplete) then
            // do not move while charging
            moving := false
        endif
    endif

macro rule r_HardwareInterface =
    par
        r_setRechargeFlag[]
        r_setAtGoal[]
        r_setIsAtCharger[]
        r_loadPlan[]
        r_holdAtChargerUntilRecharged[]
        r_moveOneStep[]
    endpar