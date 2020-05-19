asm StringAdd
import  ../../../STDL/StandardLibrary
	
signature:
	controlled str1: String
	controlled str2: String   

definitions:
	main rule r_main =
		str2 := str1 + str2

default init s0:
    function str1 = "aa"
    function str2 = "bb"