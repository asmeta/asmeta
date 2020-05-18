asm usedDomain

import ../../../../asm_examples/STDL/StandardLibrary

signature:
	domain SubDom subsetof Integer //could be reduced in size. Elements {6, 5} could be removed.
	domain SubDomAll subsetof Integer
	domain SubDomAll2 subsetof Integer
	domain SubDomAll3 subsetof Integer //could be reduced in size. Elements {20, 10, 19, 17, 18, 15, 16, 13, 14, 11, 12} could be removed.
	enum domain EnumDom = {AA | BB | CC}//CC puo' essere eliminato
	enum domain EnumDom2 = {DD | EE | FF} //unuseful
	enum domain EnumDom3 = {GG | HH | II} //all used
	enum domain EnumDom4 = {LL | MM | NN} //all used
	dynamic controlled fooA: EnumDom
	dynamic controlled fooB: EnumDom
	dynamic controlled fooC: EnumDom
	dynamic controlled fooD: SubDom
	dynamic controlled fooE: SubDom
	dynamic controlled fooF: SubDom
	dynamic controlled fooG: SubDomAll
	dynamic controlled fooH: SubDomAll
	dynamic controlled fooI: SubDomAll2
	dynamic controlled fooL: SubDomAll3
	dynamic controlled fooM: EnumDom3 -> Boolean
	dynamic controlled fooN: EnumDom4 -> Boolean

definitions:
	domain SubDom = {1..6}
	domain SubDomAll = {1, 2}
	domain SubDomAll2 = {1 .. 10}
	domain SubDomAll3 = {1 .. 20}

	main  rule r_Main =
		par
			fooA := AA
			fooB := BB
			fooC := BB
			fooD := 4
			fooE := 3
			fooF := 1
			fooG := 2
			fooH := 1
			choose $x in SubDomAll2 with true do
				fooI := $x
			choose $y in SubDomAll3 with $y < 10 do
				fooL := $y
			forall $k in EnumDom3 with true do
				fooM($k) := true
			forall $f in EnumDom4 with $f!=MM do//in questo modo fooN(MM) viene comunque considerata una usedLocation da AsmetaSMV
				fooN($f) := false
			fooN(LL) := true
			fooN(NN) := true
		endpar

default init s0:
	function fooA = AA
	function fooB = AA
	function fooC = AA
	function fooD = 1
	function fooE = 2
	function fooF = 3
	function fooG = 1
	function fooH = 2
	function fooI = 1
	function fooL = 1
	function fooM($x in EnumDom3) = true
	function fooN($g in EnumDom4) = true
