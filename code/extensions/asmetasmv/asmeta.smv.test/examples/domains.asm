asm domains

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain EnumDom = {EL_A | EL_B | EL_C}
	domain ConcrDomI1 subsetof Integer
	domain ConcrDomI2 subsetof Integer
	domain ConcrDomN1 subsetof Natural
	domain ConcrDomN2 subsetof Natural
	dynamic controlled fooB: Boolean	
	dynamic controlled fooE: EnumDom
	dynamic controlled fooCI1: ConcrDomI1
	dynamic controlled fooCI2: ConcrDomI2
	dynamic controlled fooCN1: ConcrDomN1
	dynamic controlled fooCN2: ConcrDomN2

definitions:
	domain ConcrDomI1 = {1 : 5}
	domain ConcrDomI2 = {1, 3, 7}
	domain ConcrDomN1 = {2n:6n}
	domain ConcrDomN2 = {3n, 1n, 8n, 12n}

default init s0:
	function fooB = false
	function fooE = EL_A
	function fooCI1 = 1
	function fooCI2 = 3
	function fooCN1 = 3n
	function fooCN2 = 8n
