asm carController

import ../STDL/StandardLibrary
import ../STDL/CTLLibrary

signature:
	enum domain Speed = {STOP | SLOW | FAST}
	dynamic controlled velocity: Speed
	dynamic monitored accelerate: Boolean
	dynamic monitored brake: Boolean

definitions:

	CTLSPEC ef(velocity = FAST)

	main rule r_Main =
		if brake then
			velocity := STOP
		else
			if accelerate then
				switch velocity
					case STOP: velocity := SLOW
					case SLOW: velocity := FAST
				endswitch
			else
				switch velocity
					case FAST: velocity := SLOW
					case SLOW: velocity := STOP
				endswitch
			endif
		endif

default init s0:
	function velocity = STOP
