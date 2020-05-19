asm StringConcat
import  ../../../STDL/StandardLibrary
	
signature:
	controlled str1: String
	controlled str2: String
	controlled str3: String   

definitions:
	main rule r_main =
		par
			str2 := concat(str1, str2)
			str3 := concat("aa", concat("/", concat("bb", ":"))) 
		endpar

default init s0:
    function str1 = "aa"
    function str2 = "bb"