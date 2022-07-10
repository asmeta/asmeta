//una locazione controllata potrebbe diventare statica se viene inizializzata e non viene mai modificata
//La locazione deve essere letta almeno una volta; in caso contrario pensiamo che debba essere eliminata.
asm controlledLocationCouldBeStatic

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled fooA: EnumDom //could be static
	dynamic controlled fooB: EnumDom //could not be static
	dynamic controlled fooC: EnumDom //could be static
	dynamic controlled fooD: EnumDom //non viene segnalato che potrebbe essere statica perche' deve essere rimossa
	dynamic controlled fooE: EnumDom -> Boolean//all locations could be static. The function too could be static
	dynamic controlled fooF: EnumDom -> Boolean //only location fooF_AA could be static
	dynamic controlled fooG: EnumDom //could be static

definitions:
	
	main  rule r_Main =
		par
			fooB := BB
			if(fooA = CC) then
				fooC := BB
			endif
			forall $x in EnumDom with $x=BB do
				fooE($x) := true
			choose $y in EnumDom with $y=AA do
				fooE($y) := false
			fooF(AA) := true
			fooF(BB) := false
			fooG := undef
		endpar

default init s0:
	function fooA = AA //could be static
	function fooB = AA //could not be static
	function fooC = AA //could be static
	function fooD = AA //It must be removed
	function fooE($x in EnumDom) = ($x = BB)
	function fooF($x in EnumDom) = true
