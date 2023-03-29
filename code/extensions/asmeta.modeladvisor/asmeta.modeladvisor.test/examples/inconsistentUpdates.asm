asm inconsistentUpdates

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain SubDom subsetof Integer
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled fooA: EnumDom
	dynamic controlled fooB: EnumDom
	dynamic controlled fooC: EnumDom
	dynamic controlled fooD: EnumDom
	dynamic controlled fooE: EnumDom
	dynamic controlled fooF: EnumDom
	dynamic controlled fooG: SubDom
	dynamic monitored mon: Boolean
	dynamic monitored mon2: Boolean

definitions:
	domain SubDom = {1:2}
	
	main  rule r_Main =
		par
			//update inconsistente.Le due choose potrebbero scegliere due valori diversi a cui aggiornare fooA
			choose $x in EnumDom with true do
				fooA := $x
			choose $y in EnumDom with true do
				fooA := $y

			//non e' un update inconsistente
			forall $k in EnumDom with $k!=CC do
				fooB := AA
			
			//update inconsistente
			forall $j in EnumDom with $j!=CC do
				fooC := $j

			//update inconsistenti generati dalle combinazioni delle monitorate:
			//mon and mon2
			//!mon and !mon2
			//!mon and mon2
			if(mon) then
				fooD := AA
			else
				fooD := BB
			endif

			if(mon2) then
				fooD := CC
			else
				fooD := AA
			endif

			//inconsistent update
			if(mon and mon2) then
				if(fooD = BB) then
					fooE := AA
				endif
			endif
			choose $b in Boolean with $b do
				fooE := BB

			
			//inconsistent update
			if(mon2) then
				fooF := AA
			else
				fooF := BB
			endif
			if(mon2 and mon2) then
				fooF := undef
			else
				fooF := BB
			endif

			//inconsistent update
			if(mon2) then
				fooG := 1
			else
				fooG := 2
			endif
			if(mon2 and mon2) then
				fooG := undef
			else
				fooG := 2
			endif
		endpar

default init s0:
	function fooA = AA
	function fooB = CC
	function fooC = CC
	function fooE = CC
	function fooF = CC
	function fooG = 1
