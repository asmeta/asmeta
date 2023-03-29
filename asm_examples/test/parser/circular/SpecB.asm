// A imports B which imports A
// AG
asm SpecB

//import SpecC
import SpecA

export *


signature:

definitions:
	main rule r_Main = skip

default init s0:
