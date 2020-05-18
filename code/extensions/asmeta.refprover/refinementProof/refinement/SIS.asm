asm SIS
	
import StandardLibrary
	
signature:
	enum domain Pressure = { TOOLOW |NORMAL |HIGH }
	controlled pressure : Pressure

definitions:

	macro rule  r_1 = 
		if pressure = TOOLOW then
			choose $x in Pressure with $x = TOOLOW or $x = NORMAL do
				pressure := $x
		endif

	macro rule r_2 =
		if pressure = NORMAL then 
			choose $x in Pressure with $x = HIGH or $x = TOOLOW do
				pressure := $x
     	endif
     
	macro rule r_3 =
		if pressure = HIGH then
			choose $x in Pressure with $x = HIGH or $x = NORMAL do
				pressure := $x
		endif

	main rule r_SIS =
		par
			r_1[]
			r_2[]
			r_3[]
		endpar
	
default init s1:
	function pressure = TOOLOW