asm Tank

import StandardLibrary

signature:
	domain Level subsetof Integer
	domain IncrDom subsetof Integer
	dynamic controlled level: Level
	derived full: Boolean

definitions:
	domain Level = {0..50}
	domain IncrDom = {-5..5}

	function full =
		level = 50

	main rule r_Main =
		choose $x in IncrDom with level + $x >= 0 and level + $x <= 50 do
			level := level + $x

default init s0:
	function level = 0