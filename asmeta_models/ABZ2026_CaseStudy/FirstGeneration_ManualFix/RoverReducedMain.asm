asm RoverReducedMain
import ../../STDL/StandardLibrary
import RoverDomains
import BatteryMonitor
import SolarPanelController
import ComputePlan2Charging
import HardwareInterface
import GoalReasoningAgent

signature:

// In reduced architecture, expose isAtCharger to SolarPanelController
// by binding HardwareInterface.isAtCharger to SolarPanelController.isAtCharger
// (they share same name in this specification; kept consistent by r_setIsAtCharger)

definitions:

// System-level requirement SL1: never run out of battery
invariant inv_SL1_overall over batteryLevel: (batteryLevel > 0.0) 

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
    // Initial position and goal are left abstract; can be set by scenarios
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
    function batteryLevel = 100.0