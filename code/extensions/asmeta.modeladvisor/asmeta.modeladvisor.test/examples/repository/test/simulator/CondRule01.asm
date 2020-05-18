asm CondRule01
import ../../STDL/StandardLibrary
	
signature:
	controlled f : Integer 
	controlled g : Integer 
	controlled z : Integer 

definitions:
main rule r_main = 
	if f = 33 then
		g := 11
	else if f = 66 then
		g := 22
	else
		g := 44
	endif endif

default init s1:
	function f = 99
