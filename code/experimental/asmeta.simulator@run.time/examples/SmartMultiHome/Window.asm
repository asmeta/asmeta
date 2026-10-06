asm Window

import ../StandardLibrary

signature:
	dynamic monitored openCommand: Boolean
	dynamic out windowOpen: Boolean

definitions:

	main rule r_Main =
		windowOpen := openCommand

default init s0:
	function windowOpen = false
