asm RoverReducedMain
import ../../../STDL/StandardLibrary
import RoverDomains
import BatteryMonitor
import SolarPanelController
import ComputePlan2Charging
import HardwareInterface
import GoalReasoningAgent

signature:

// System-level requirement SL1: never run out of battery
invariant inv_SL1_overall =
    (batteryLevel > 0)

// In reduced architecture, expose isAtCharger to SolarPanelController
// by binding HardwareInterface.isAtCharger to SolarPanelController.isAtCharger
// (they share same name in this specification; kept consistent by r_setIsAtCharger)

definitions:

main rule r_Main =
    par
        // update battery abstraction
        r_BatteryMonitor[]

        // compute plan-to-charger (uses goal chosen by GRA)
        r_ComputePlan2Charging[]

        // decide goals (recharge -> nearest charger; handle noplan/failure)
        r_GRA[]

        // execute movement + set recharge/atGoal/isAtCharger
        r_HardwareInterface[]

        // manage solar panels at charger
        r_SolarPanelController[]
    endpar

default init s0:
    // Initialize all controlled functions to reasonable values
    function panelState = CLOSED
    function recharge = false
    function atGoal = false
    function awaitingInstructions = false
    function noplan = false
    function failure = false
    function planSet = {}
    function plan2C = []
    function activePlan = []
    function moving = false
    function isAtCharger = false
    function currentPosition = (0, 0)
    function goal = (0, 0)
    function batteryLevel = 95

    // Reasonable monitored defaults (useful for running without scenarios)
    function measuredBatteryLevel = 100
    function chargingComplete = false
    function planningFailure = false
    function chargers = {}