asm test_insertAt_Sequence

//Mi aspetto che la lista alla fine sia [4, 1, 1, 2, 3, 4] 

import StandardLibrary

signature:
	dynamic controlled list: Seq(Integer)
	dynamic controlled run: Boolean

definitions:

	main rule r_Main =
		if(run) then
			par
				list := 
					insertAt(
								insertAt(list, 0n, at(list, 3n)),
								2n,
								at(list, 0n)
							)
				run := false
			endpar
		endif

default init s0:
	function list = [1, 2, 3, 4]
	function run = true