asm counter
import ../../../STDL/StandardLibrary
	
signature:
controlled count: Integer
		
definitions:

turbo rule r_count($x in Integer) =
	if count < $x then seq
		count := count + 1
		r_count($x - 1)
	endseq endif
	
main rule r_main =
	seq
		count := 0
		r_count(10)
	endseq
	
