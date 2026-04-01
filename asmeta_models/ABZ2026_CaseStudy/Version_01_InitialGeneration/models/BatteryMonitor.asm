asm BatteryMonitor
import ../../../STDL/StandardLibrary
import RoverDomains

signature:

// raw measurement from hardware/sensors
dynamic monitored measuredBatteryLevel: BatteryPct

// batteryLevel reported to the rest of the system
dynamic controlled batteryLevel: BatteryPct

// (optional) charging completion from physical subsystem
dynamic monitored chargingComplete: Boolean

definitions:

// BM2: reported battery is 5% less than measured (degradation margin)
macro rule r_updateBatteryLevel =
    batteryLevel := measuredBatteryLevel * 0.95

// BM1: monitoring is reflected by updating batteryLevel each step
macro rule r_BatteryMonitor =
    r_updateBatteryLevel[]