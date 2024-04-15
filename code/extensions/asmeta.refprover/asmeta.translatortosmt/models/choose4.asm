asm choose4

import StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	dynamic controlled foo: ConcrDom

definitions:
	domain ConcrDom = {1 .. 3}

	main rule r_Main =
		choose $x in ConcrDom with true do
			if $x = 1 then
				foo := 1
			else
				if $x = 2 then
					foo := 2
				else
					foo := 3
				endif
			endif

default init s0:
	function foo = 1