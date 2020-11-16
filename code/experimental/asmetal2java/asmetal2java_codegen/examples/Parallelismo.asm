
asm Parallelismo

import STDL/StandardLibrary

signature:

      controlled x: Integer
      controlled y: Integer

definitions:

	rule r_Parallelismo =

	par
	 x := y
	 y := x
	endpar

	main rule r_Main =
         skip

default init s0:

