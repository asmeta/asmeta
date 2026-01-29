// To test ChooseToLet mutator with a complex choose
asm chooseToLet
import ../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain Product = {COFFEE | TEA | MILK}
	domain QuantityDomain subsetof Integer

	controlled p1: Product
	controlled p2: Product
	controlled qt: QuantityDomain
	controlled bl: Boolean

	static available: Product -> Boolean


definitions:
	domain QuantityDomain = {0 : 10}
	function available($prod in Product) =
	switch $prod
		case COFFEE: true
		case TEA: true
		case MILK: false
	endswitch

	main rule r_Main =
		choose $p1 in {TEA, MILK}, $p2 in Product, $q in QuantityDomain, $b in Boolean
		with available($p1) and not available($p2) and $q = 5 and not $b do
			par
				p1 := $p1
				p2 := $p2
				bl := $b
				qt := $q
			endpar
		ifnone
			par
				p1 := undef
				p2 := undef
				bl := undef
				qt := undef
			endpar

default init s0:
	function p1 = undef
	function p2 = undef
	function bl = undef
	function qt = undef
