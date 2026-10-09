asm asmInc

import StandardLibrary
 
signature:
 
 	monitored funcMulti: Integer
 	monitored funcDec: Integer
 	
 	controlled maxFunction: Integer
 	
 	out funcInc: Integer
 
 definitions:
 
 macro rule r_max =
 	par
 		if funcMulti >= funcDec then
 		  	maxFunction := funcMulti endif
 		if funcDec > funcMulti then
          maxFunction := funcDec endif
 	endpar
 	
 	
 main rule r_Main =
 	par
 		   funcInc := funcMulti + 2
		 funcInc := (funcMulti + funcDec) + 2
		 r_max[]
 	endpar

 default init s0:
 	function maxFunction = 0