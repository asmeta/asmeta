asm inconUpdateWithUndef
import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {AA | BB}
	domain MyDomain subsetof Integer
	domain MyDomainNatural subsetof Natural
	dynamic controlled foo: MyDomain
	dynamic controlled fooA: MyDomain
	dynamic controlled fooB: Boolean
	dynamic controlled fooE: EnumDom
	dynamic controlled fooN: MyDomainNatural
	dynamic monitored mon: MyDomain

definitions:
	domain MyDomain = {1:4}
	domain MyDomainNatural = {1n..4n}
	
	main  rule r_Main =
		par
			switch (mon)
				case 1:
					par
						//update inconsistente
						foo := 2
						foo := undef
					endpar
				case 2:
					par
						//update inconsistente
						fooN := 2n
						fooN := undef
					endpar
				case 3:
					par
						//update inconsistente
						fooB := true
						fooB := undef
					endpar
				case 4:
					par
						//update inconsistente
						fooE := AA
						fooE := undef
					endpar
			endswitch
			
			fooA := undef
			fooA := undef
		endpar

default init s0:
	function foo = 1
	function fooA = 1
	function fooN = 1n
	function fooB = false
	function fooE = BB