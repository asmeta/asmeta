asm contrLocIsUpdated

import ../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	domain ConcrDom subsetof Integer
	dynamic controlled fooA: EnumDom //updated
	dynamic controlled fooB: EnumDom //not updated
	dynamic controlled fooC: EnumDom //updated
	dynamic controlled fooD: EnumDom //updated
	dynamic controlled fooE: EnumDom //not updated
	dynamic controlled fooF: EnumDom -> Boolean //fooF(BB) not updated
	dynamic controlled fooG: EnumDom //not updated
	dynamic controlled fooH: EnumDom //updated
	
	dynamic controlled fooI: ConcrDom//updated
	dynamic controlled fooJ: Boolean//updated

definitions:
	domain ConcrDom = {1..3}
	
	main  rule r_Main =
		par
			fooA := BB
			if(fooA = CC) then
				fooB := BB
			endif
			fooC := AA
			forall $x in EnumDom with $x=BB do
				fooD := $x
			forall $y in EnumDom with $y=BB and $y=AA do
				fooE := $y
			forall $k in EnumDom with $k!=BB do
				fooF($k) := false
			choose $t in EnumDom with $t!=BB and $t=BB do
				fooG := $t
			choose $r in EnumDom with $r!=BB and $r=BB do
				fooH := $r
			choose $e in EnumDom with $e=BB do
				fooH := $e
				
			choose $q in ConcrDom with fooI - $q >=1 do
				fooI := fooI -$q
			if(fooI=2) then
				fooJ := false
			endif
		endpar

default init s0:
	function fooA = AA
	function fooB = AA
	function fooD = AA
	function fooE = AA
	function fooF($x in EnumDom) = true
	function fooI = 3
	function fooJ = true
