asm sizeString

import ../../../STDL/StandardLibrary
	
signature:
	controlled str1: String
	controlled str2: String
	controlled str3: String
	controlled sizeStr1: Integer
	controlled sizeStr2: Integer
	controlled sizeStr3: Integer

definitions:

	main rule r_main =
		par
			sizeStr1 := size(str1)
			sizeStr2 := size(str2)
			sizeStr3 := size(str3)
		endpar

default init s0:
	function str1 = "aa"
	function str2 = "a"
	function str3 = ""