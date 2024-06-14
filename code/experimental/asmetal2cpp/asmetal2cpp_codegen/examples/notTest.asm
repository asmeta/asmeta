asm notTest


import STDL/StandardLibrary
//import STDL/CTLLibrary

signature:
	controlled x: Boolean
	controlled y: Boolean
	controlled num: Integer

definitions:


	main rule r_Main =
		if not x = y then
			num:=10
		else
			num := 0
		endif

default init s0:
	function x=false
	function y=false
