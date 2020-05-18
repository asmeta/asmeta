asm mod

import ../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../asm_examples/STDL/CTLlibrary

signature:
	domain SubDomInt subsetof Integer
	domain SubDomNat subsetof Natural
	domain SubDomIntNeg subsetof Integer
	dynamic controlled fooA_I: SubDomInt
	dynamic controlled fooB_I: SubDomInt
	dynamic controlled fooC_I: SubDomInt
	dynamic controlled fooD_I: SubDomInt
	dynamic controlled fooE_I: SubDomInt
	dynamic controlled fooA_N: SubDomNat
	dynamic controlled fooB_N: SubDomNat
	dynamic controlled fooC_N: SubDomNat
	dynamic controlled fooD_N: SubDomNat
	dynamic controlled fooE_N: SubDomNat
	dynamic controlled fooF: SubDomIntNeg
	dynamic controlled fooG: SubDomIntNeg
	dynamic controlled fooH: SubDomIntNeg
	dynamic controlled fooZ: SubDomInt

definitions:
	domain SubDomInt = {0..10}
	domain SubDomNat = {0n..10n}
	domain SubDomIntNeg = {-10..10}

	//AsmetaL invariants
	invariant over fooA_I: (fooA_I = 2)
	invariant over fooB_I: (fooB_I = 0)
	invariant over fooC_I: (fooC_I = 0)
	invariant over fooD_I: (fooD_I = 0)
	invariant over fooE_I: (fooE_I = 3)
	invariant over fooA_N: (fooA_N = 2n)
	invariant over fooB_N: (fooB_N = 0n)
	invariant over fooC_N: (fooC_N = 0n)
	invariant over fooD_N: (fooD_N = 0n)
	invariant over fooE_N: (fooE_N = 3n)
	invariant over fooF: (fooF = 1)
	invariant over fooG: (fooG = -1)
	invariant over fooH: (fooH = -1)

	//CTL properties
	CTLSPEC ag(fooA_I = 2)
	CTLSPEC ag(fooB_I = 0)
	CTLSPEC ag(fooC_I = 0)
	CTLSPEC ag(fooD_I = 0)
	CTLSPEC ag(fooE_I = 3)
	CTLSPEC ag(fooA_N = 2n)
	CTLSPEC ag(fooB_N = 0n)
	CTLSPEC ag(fooC_N = 0n)
	CTLSPEC ag(fooD_N = 0n)
	CTLSPEC ag(fooE_N = 3n)
	CTLSPEC ag(fooF = 1)
	CTLSPEC ag(fooG = -1)
	CTLSPEC ag(fooH = -1)

	main rule r_Main =
		par
			fooA_I := 8 mod 3
			fooB_I := 0 mod 3
			fooC_I := 4 mod 2
			fooD_I := 4 mod 1
			fooE_I := 3 mod 24
			fooA_N := 8n mod 3n
			fooB_N := 0n mod 3n
			fooC_N := 4n mod 2n
			fooD_N := 4n mod 1n
			fooE_N := 3n mod 24n
			fooF := 3 mod (-2) // 1
			fooG := -3 mod 2 // -1
			fooH := -3 mod -2 // -1
			fooZ := fooD_I mod 5 
		endpar

default init s0:
	function fooA_I = 2
	function fooB_I = 0
	function fooC_I = 0
	function fooD_I = 0
	function fooE_I = 3
	function fooA_N = 2n
	function fooB_N = 0n
	function fooC_N = 0n
	function fooD_N = 0n
	function fooE_N = 3n
	function fooF = 3 mod (-2) // 1
	function fooG = -3 mod 2 // -1
	function fooH = -3 mod -2 // -1
