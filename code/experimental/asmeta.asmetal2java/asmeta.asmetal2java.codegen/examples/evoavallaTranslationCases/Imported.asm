asm Imported
import ../STDL/StandardLibrary
export *

signature:
    enum domain WindowState = {WINDOW_OPEN | WINDOW_CLOSED}

    monitored windowSensor: Boolean
    controlled windowState: WindowState

definitions:

    rule r_UpdateWindow =
        if windowSensor then
            windowState := WINDOW_OPEN
        else
            windowState := WINDOW_CLOSED
        endif

default init s0:
	function windowState= WINDOW_CLOSED
