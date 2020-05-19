// using and comparing strings 
asm StringExpr01
import  ../../STDL/StandardLibrary
	
signature:
controlled name: String
controlled f: Boolean 
		
definitions:	                 	
main rule r_main = if name = "pippo" then f := true else f:=false endif

default init s0:
    function name = "pippo"