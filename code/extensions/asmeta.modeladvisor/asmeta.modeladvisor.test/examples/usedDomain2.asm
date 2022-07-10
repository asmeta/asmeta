asm usedDomain2

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}//AA e CC usati nel dominio di funzioni
	enum domain EnumDom2 = {DD | EE | FF}//EE utilizzato nel dominio di fooC
	enum domain EnumDom3 = {GG | HH | II}//GG usato nel dominio di fooD e II nel suo codominio
	enum domain EnumDom4 = {LL | MM | NN}//LL usato nel codominio di fooE
	enum domain EnumDom5 = {OO | PP}
	dynamic controlled fooA: EnumDom -> Boolean
	dynamic controlled fooB: Prod(EnumDom, Boolean) -> Boolean
	dynamic controlled fooC: Prod(Prod(EnumDom2, Boolean), EnumDom) -> Boolean
	dynamic controlled fooD: EnumDom3 -> EnumDom3
	dynamic controlled fooE: EnumDom4
	derived fooDer: Boolean -> EnumDom4

definitions:
	function fooDer($x in Boolean) = if $x then
						LL
					else
						NN
					endif

	main  rule r_Main =
		par
			fooA(AA) := true
			fooB(AA, true) := true
			fooC((EE, true), CC) := true
			fooD(GG) := II
			if(fooDer(true) = LL) then
				fooE := LL
			endif
		endpar

default init s0:
	function fooA($x in EnumDom) = false
	function fooD($k in EnumDom3) = GG
	function fooE = LL
