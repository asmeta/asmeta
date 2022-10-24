asm emptyModel
 
import ../../STDL/StandardLibrary

signature:

enum domain Lights = {ALL_OFF | RED | BLINK_YELLOW | GREEN | YELLOW}

monitored lightsB: Lights // traffic light A
monitored lightsA: Lights // traffic light A
definitions:

	main rule r_Main =
		skip