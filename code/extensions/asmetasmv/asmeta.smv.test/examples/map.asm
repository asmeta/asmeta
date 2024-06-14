asm map

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLLibrary

signature:
	domain MyDomain subsetof Integer
	dynamic controlled aa: MyDomain -> MyDomain
	dynamic controlled bb: Prod(Boolean, Boolean) -> MyDomain
	dynamic controlled cc: MyDomain -> MyDomain
		
definitions:
	domain MyDomain = {1:4}
	
	CTLSPEC (aa(1) = 4 and aa(2) = 3  and aa(3) = 2 and aa(4) = 1) and
					ax(ag(aa(1) = 1 and aa(2) = 2  and aa(3) = 3 and aa(4) = 4))
	
	main  rule r_Main = 
		forall $x in MyDomain with true do
			par
				aa($x) := $x
				cc($x) := $x
			endpar

default init s0:
	function aa($i in MyDomain) = at({1 -> 4, 2->3, 3->2, 4->1}, $i)
	function bb($i in Boolean, $j in Boolean) = at({(false, false) -> 1, (false, true)->3, (true, false)->3, (true, true)->4}, ($i, $j))
	function cc($i in MyDomain) = at({1 -> 4, 4->1}, $i)