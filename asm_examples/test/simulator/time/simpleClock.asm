//Simple clock -> hours:minutes
asm simpleClock
import ../../../STDL/TimeLibrary
	
signature:
controlled clockHours: Integer
controlled clockMins: Integer
		
definitions:

	
main rule r_main =
	par
		clockHours:=mCurrTimeHours mod 24
		clockMins:=mCurrTimeMins mod 60
	endpar
