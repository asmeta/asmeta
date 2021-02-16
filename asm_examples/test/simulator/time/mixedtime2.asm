// two times in two different time units
// it could give errors (if it evaluates the millisec after the secs)
asm mixedtime2
import ../../../STDL/StandardLibrary
	
signature:
controlled timeS: Integer
controlled timeMS: Integer
		
definitions:

	
main rule r_main =
	par
		timeS:= mCurrTimeSecs
		// eval millisec only after a while
		if timeS > 0 then timeMS:= mCurrTimeMillisecs endif
	endpar

default init s0:
	function timeS = 0
