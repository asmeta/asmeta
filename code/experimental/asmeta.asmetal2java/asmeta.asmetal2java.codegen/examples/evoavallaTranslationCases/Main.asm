asm Main
import ../STDL/StandardLibrary
import Imported

signature:
    enum domain DoorState = {OPEN | CLOSED}
    monitored doorSensor: Boolean
    controlled doorState: DoorState

definitions:

    main rule r_Main =
        par
            if doorSensor then
                doorState := OPEN
            else
                doorState := CLOSED
            endif
            r_UpdateWindow[]
        endpar

default init s0:
	function doorState = CLOSED
