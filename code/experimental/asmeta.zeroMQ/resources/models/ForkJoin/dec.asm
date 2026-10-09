asm dec
import StandardLibrary

signature:
	controlled prevFuncDec: Integer
	monitored myinput : Integer
	out funcDec: Integer

definitions:
	main rule r_Main =
		let ($newVal = prevFuncDec + myinput - 1) in
			par
				funcDec := $newVal
				prevFuncDec := $newVal
			endpar
		endlet

default init s0:
	function prevFuncDec = 0