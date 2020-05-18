asm inconsistentUpdate
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	domain SubDom subsetof Integer
	dynamic controlled mon: Boolean
	dynamic controlled foo: EnumDom
	dynamic controlled foo1: SubDom
	dynamic controlled fooA: SubDom -> Boolean
	dynamic controlled fooB: SubDom -> Boolean

definitions:
	domain SubDom = {1..4}

	main  rule r_Main =
		par
			if(mon) then
				foo := AA
			endif
			if(mon != false) then
				foo := BB
			endif
			forall $i in SubDom with $i<=foo1 do
				fooA($i) := true
			forall $j in SubDom with $j>=foo1 do
				fooA($j) := false
			choose $x in SubDom, $b in Boolean with true do
				fooB($x) := $b
			choose $z in SubDom, $c in Boolean with true do
				fooB($z) := $c
		endpar

default init s0:
	function mon = true
	function fooA($i in SubDom) = true
	function foo1 = 3