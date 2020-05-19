asm containsString

import ../../../STDL/StandardLibrary
	
signature:
	controlled foo1: String
	controlled foo2: String
	controlled foo3: String
		
definitions:

	main rule r_main =
		par
			if contains(foo1, "asm") then
				foo1 := "a"
			endif
			if contains("asm", foo2) then
				foo2 := "b"
			endif
			if contains("asm", "a") then
				foo3 := "c"
			endif
		endpar

default init s1:
	function foo1 = "asmeta"
	function foo2 = "a"
	function foo3 = "asmeta"