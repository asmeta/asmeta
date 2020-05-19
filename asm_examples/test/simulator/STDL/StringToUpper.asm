asm StringToUpper
import  ../../../STDL/StandardLibrary
	
signature:
	controlled str1: String
	controlled str2: String   

definitions:
	main rule r_main =
		str2 := toUpper(str1)

default init s0:
    function str1 = "AbC"
    function str2 = "bb"