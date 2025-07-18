asm simpleTime

import StandardLibrary
import TimeLibrarySimple

signature:
	controlled seconds: Integer
	static currentSeconds: Timer

definitions:

	main rule r_Main = 
		seconds := currentTime(currentSeconds)

default init s0:

	function seconds = 10
	function start($t in Timer) = currentTime($t)