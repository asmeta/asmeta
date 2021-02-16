asm time2
import ../../../STDL/StandardLibrary
	
signature:
controlled time1: Integer
controlled time2: Integer
		
definitions:

	
main rule r_main =
	par
		time1:= mCurrTimeMillisecs
		time2:= mCurrTimeMillisecs
	endpar
