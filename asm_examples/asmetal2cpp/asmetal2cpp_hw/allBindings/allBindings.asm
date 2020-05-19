asm allBindings

import ../../../STDL/StandardLibrary

signature:

	domain D1 subsetof Integer
	domain D2 subsetof Integer

	enum domain Switch = { OFF | ON }
	enum domain Pressure = { PTOOLOW | PNORMAL | PHIGH }

	monitored enumin: Switch
	out enumout : Switch

	monitored boolin : Boolean
	out boolout : Boolean

	monitored intfunctin : D1
	out intfunctout : D1

	monitored doublefunctin : D2
	out doublefunctout : D2

//
definitions:
 	domain D1 = {0 : 9}
	domain D2 = {0 : 9}

rule r_Main = skip

