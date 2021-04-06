// two times in two different time units
// it will give errors (it evaluates the millisec after the secs)
// in auto time unit mode
asm mixedtime2
import ../../../STDL/StandardLibrary
import ../../../STDL/TimeLibrary
	
signature:
controlled timeS: Integer
controlled timeMS: Integer
		
definitions:

	
main rule r_main =
	par
		timeS:= mCurrTimeSecs 
		// eval millisec only after a while
		if timeS > 1 then timeMS:= mCurrTimeMillisecs endif
	endpar

default init s0:
	function timeS = mCurrTimeSecs
