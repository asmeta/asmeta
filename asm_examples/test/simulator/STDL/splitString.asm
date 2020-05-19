asm splitString

import ../../../STDL/StandardLibrary
	
signature:
	controlled str: String
	controlled seqStr: Seq(String)

	controlled str1: String
	controlled seqStr1: Seq(String)

definitions:

	main rule r_main =
		par
			seqStr := split(str, " ")
			seqStr1 := split(str1, "\\|")
		endpar

default init s1:
	function str = "This is a sentence"
	function seqStr = []
	function str1 = "canvas:true|textshadow:true|opacity:true|touch:false|audio:/ogg:false/mp3:true"
	function seqStr1 = []