asm m1

import ../../../STDL/StandardLibrary

signature:
	domain SubInt subsetof Integer
	controlled f1: Integer
	controlled f2: Integer
	controlled f3: Integer
	controlled f4: Integer
	
definitions:

	macro rule r_m1($x in Integer)=
		f1 := $x
		
	macro rule r_m2($x in SubInt)=
		f2 := $x
		
	macro rule r_m3($x in Integer)=		
		f3 := $x
		
	macro rule r_m3($x in SubInt)=
		f4 := $x
		
	main rule r_main=
		par
			r_m1[1]
			r_m2[1]
			// call r_m3($x in Integer)
			r_m3[1]
		endpar

/* default init initial_state:
	
	function prova  = true
*/
		
