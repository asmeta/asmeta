asm model

import StandardLibrary

signature:
	controlled x: Integer -> Integer
	controlled y: Integer -> Integer
	controlled t: Boolean -> Boolean
	monitored z: Boolean
	monitored w: Integer

definitions:

	rule r_otherRule =
		choose $v in {0 .. 5} with y($v) < 4 do
			y($v) := y($v) + 1
			ifnone
				forall $v2 in {0 .. 5} with y($v2) < 4 do
					if y($v2) mod 2 = 0 then
						skip
					else
						if w > 20 then
							y($v2) := 0
						endif
					endif

	main rule r_Main =
		par
			seq
				t(true) := false
				t(true) := not t(true)
			endseq
			forall $v in {0 .. 5} with x($v) < 4 do
				x($v) := x($v) + 1
			if z then
				skip
			else
				if w > 10 then
					r_otherRule[]
				endif
			endif
		endpar
		
default init s0:
	function x($v in Integer) = $v
	function y($v in Integer) = $v