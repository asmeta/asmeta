// correct use of local
asm UsingLocal2


import ../../STDL/StandardLibrary

signature:

definitions:

    turbo rule r_MyRule($a in Integer) in Integer =
         local x: Integer [skip]
         seq	
         		x:= 10
         		result:= x
         endseq
         	
	
	main rule r_Main = skip

default init s0:
