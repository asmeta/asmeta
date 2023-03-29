asm contrLocAllValues

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain ConcrDom subsetof Integer
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled fooA: EnumDom //takes all values
	dynamic controlled fooB: EnumDom //never takes CC
	dynamic controlled fooC: EnumDom //takes all values
	dynamic controlled fooD: EnumDom //could be removed
	dynamic controlled fooE: EnumDom //could be removed
	dynamic controlled fooF: EnumDom //never takes AA and BB
	dynamic controlled fooG: ConcrDom //takes all values
	dynamic controlled fooH: Boolean -> EnumDom //all locations do not take the value {CC}
	dynamic controlled fooI: Boolean -> EnumDom //fooI(false) does not take the value {AA}. fooI(true) does not take the value {CC}
	dynamic controlled fooL: Boolean -> EnumDom
	dynamic monitored mon: EnumDom
	dynamic monitored mon2: EnumDom //never used. It could be removed.

definitions:
	domain ConcrDom = {1 : 3}
	
	main  rule r_Main =
		par
			choose $x in EnumDom with true do
				fooA := $x
			choose $y in EnumDom with $y!= fooF do
				fooB := $y
			fooC := mon
			
			choose $k in ConcrDom with $k < 3 and (fooG - $k>=1) do
				fooG := fooG - $k
			
			fooH(true) := BB
			fooH(false) := AA
			fooI(true) := BB
			fooI(false) := CC
			fooL(false) := mon
		endpar

default init s0:
	function fooA = AA
	function fooB = AA
	function fooC = AA
	function fooD = AA
	function fooF = CC
	function fooG = 3
	function fooH($x in Boolean) = at({true-> AA, false->BB}, $x)
	function fooI($x in Boolean) = at({true-> AA, false->BB}, $x)
	function fooL($x in Boolean) = at({true-> AA, false->BB}, $x)
