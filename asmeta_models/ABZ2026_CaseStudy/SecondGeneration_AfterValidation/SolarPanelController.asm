asm SolarPanelController
import ../../STDL/StandardLibrary
import RoverDomains
import HardwareInterface
export *

signature:

enum domain PanelState = {OPEN | CLOSED}

dynamic controlled panelState: PanelState

definitions:

// HI6: when at a charger, open (and tilt) panels
rule r_openPanels =
    if isAtCharger then
        panelState := OPEN
    endif

// HI7: when charging complete, close panels
rule r_closePanels =
    if chargingComplete then
        panelState := CLOSED
    endif

macro rule r_SolarPanelController =
    par
        r_openPanels[]
        r_closePanels[]
    endpar
