// two times in two different time units
// it can give errors (if it evaluates the millisec after the secs)
asm mixedtime1
import ../../../STDL/StandardLibrary
	
signature:
controlled timeS: Integer
controlled timeMS: Integer
		
definitions:

	
main rule r_main =
	par
		timeMS:= mCurrTimeMillisecs
		timeS:= mCurrTimeSecs
	endpar
