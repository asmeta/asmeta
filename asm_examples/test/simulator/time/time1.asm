// use just one time as seconds
asm time1
import ../../../STDL/StandardLibrary
	
signature:
controlled time: Integer
		
definitions:

	
main rule r_main =
		time:= mCurrTimeSecs
