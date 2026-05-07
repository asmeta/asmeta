asm multi
import StandardLibrary

signature:
	controlled prevFuncMulti: Integer
	monitored myinput : Integer
	out funcMulti: Integer

definitions:
	main rule r_Main =
		let ($newVal = prevFuncMulti + (myinput * 2)) in
			par
				funcMulti := $newVal
				prevFuncMulti := $newVal
			endpar
		endlet

default init s0:
	function prevFuncMulti = 0