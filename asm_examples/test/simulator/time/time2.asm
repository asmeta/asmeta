// two times, but the same time unit
// must be equal at the same time
asm time2
import ../../../STDL/TimeLibrary
	
signature:

domain Sleep subsetof Integer

controlled time1: Integer
controlled time2: Integer
		
definitions:
	domain Sleep = {1:1000}
	
main rule r_main =
	par
		time1:= mCurrTimeMillisecs
		forall $x in Sleep do skip
		time2:= mCurrTimeMillisecs
	endpar
