asm BasicDomain

import StandardLibrary

signature:

enum domain Prova = { AA | BB }

controlled x : Integer

definitions:


main rule r_Main =
	x := 1 * 2 + 3 * 4

