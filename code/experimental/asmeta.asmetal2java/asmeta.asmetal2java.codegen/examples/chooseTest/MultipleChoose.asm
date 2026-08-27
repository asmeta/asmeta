asm MultipleChoose
import ../STDL/StandardLibrary

signature:
	controlled val1: Integer
	controlled val2: Integer
	controlled b1: Boolean
	controlled b2: Boolean

definitions:

	main rule r_Main =
		par
			choose $b1 in Boolean do
				par
					b1 := $b1
					if $b1 then
						val1 := 1
					else
						val1 := 2
					endif
				endpar
			choose $b2 in Boolean do
				par
					b2 := $b2
					if $b2 then
						val2 := 1
					else
						val2 := 2
					endif
				endpar
		endpar

default init s0:
	function val1 = 0
	function val2 = 0
