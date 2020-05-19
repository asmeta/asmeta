asm existUniqueTerm

import ../../../STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB | CC}
	dynamic controlled foo: EnumDom -> Boolean
	dynamic controlled foo2: EnumDom -> Boolean
	dynamic controlled foo3: Boolean
	dynamic controlled foo4: Boolean
	dynamic controlled foo5: Boolean
	dynamic controlled foo6: Boolean

definitions:

	invariant inv_1 over foo: (exist $a in EnumDom with foo($a))
	invariant inv_2 over foo: (exist unique $a in EnumDom with foo($a))
	invariant inv_3 over foo2: (exist $a in EnumDom with foo2($a))
	invariant inv_4 over foo2: not(exist unique $a in EnumDom with foo2($a))

	main rule r_Main =
		par
			foo3 := (exist $a in EnumDom with foo($a))
			foo4 := (exist unique $b in EnumDom with foo($b))
			foo5 := (exist $c in EnumDom with foo2($c))
			foo6 := not(exist unique $d in EnumDom with foo2($d))
		endpar

default init s0:
	function foo($a in EnumDom) = if($a = AA) then true else false endif
	function foo2($a in EnumDom) = if($a = AA) then false else true endif
	function foo3 = false
	function foo4 = false
	function foo5 = false
	function foo6 = false