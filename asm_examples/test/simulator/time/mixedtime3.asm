// two times in two different time units
// no error - secs are evaluated after a while
// under the assumption that current time starts from 0 and unit = auto  
asm mixedtime3
import ../../../STDL/TimeLibrary
	
signature:
controlled timeS: Integer
controlled timeMS: Integer
		
definitions:

	
main rule r_main =
	par
		// eval Secs only after a while
		if timeMS > 1000 then timeS:= mCurrTimeSecs endif
		timeMS:= mCurrTimeMillisecs 
	endpar

default init s0:
	function timeMS = mCurrTimeMillisecs
