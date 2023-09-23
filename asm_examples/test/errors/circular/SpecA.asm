// A imports B which imports A
// AG
asm SpecA

import SpecB
export *

signature:

definitions:
	main rule r_Main = skip

default init s0:
