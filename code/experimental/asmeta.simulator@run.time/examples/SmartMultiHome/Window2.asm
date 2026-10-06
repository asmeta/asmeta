asm Window2

import ../StandardLibrary

signature:
	dynamic monitored openCommand2: Boolean
	dynamic out windowOpen2: Boolean

definitions:

	main rule r_Main =
		windowOpen2 := openCommand2

default init s0:
	function windowOpen2 = false
