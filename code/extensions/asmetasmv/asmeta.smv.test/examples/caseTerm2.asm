asm caseTerm2
import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	enum domain EnumDom = {AA | BB | CC | DD}
	dynamic controlled fooC: EnumDom
	dynamic monitored fooM: EnumDom
	
definitions:

// case term used in an update rule	
	main rule r_Main =
		fooC := switch fooM 
		          case AA: BB
   				  case BB: CC
//				  otherwise DD
		endswitch
