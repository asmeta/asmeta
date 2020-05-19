asm concrDomDef

import ../../STDL/StandardLibrary

signature:
	domain ConcrDomA subsetof Integer
	domain ConcrDomB subsetof Integer
	domain ConcrDomC subsetof Integer
	domain ConcrDomD subsetof Natural
	domain ConcrDomE subsetof Natural
	domain ConcrDomF subsetof Natural

	domain ConcrDomG subsetof Real
	domain ConcrDomH subsetof Real
	domain ConcrDomI subsetof Real

	dynamic controlled fooA: ConcrDomA

definitions:
	domain ConcrDomA = {-2, -1, 0, 1, 2}
	domain ConcrDomB = {-2 : 2}
	domain ConcrDomC = {-6 : 6, 2}
	domain ConcrDomD = {0n, 1n, 2n}
	domain ConcrDomE = {0n : 2n}
	domain ConcrDomF = {0n : 6n, 2n}
	domain ConcrDomG = {-2.3, -1.4, 0.0, 1.3, 2.7}
	domain ConcrDomH = {-2.0 : 2.0}
	domain ConcrDomI = {-6.0 : 6.0, 2.5}

	invariant over fooA: fooA = 0

	main rule r_main =
		choose $x in ConcrDomA with $x = -1 do
			fooA := $x + 1

default init s0:
	function fooA = 0
