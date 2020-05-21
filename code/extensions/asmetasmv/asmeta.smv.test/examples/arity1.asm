asm arity1
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain SubDom subsetof Integer
	enum domain EnumDom = {AA | BB}
	dynamic controlled fooB: Boolean -> EnumDom
	dynamic controlled fooE: EnumDom -> SubDom
	dynamic controlled fooS: SubDom -> Boolean

definitions:
	domain SubDom = {1:2}
	
	main  rule r_Main =
		skip

default init s0:
	function fooB($b in Boolean) = AA
	function fooE($e in EnumDom) = 1
	function fooS($s in SubDom) = true
