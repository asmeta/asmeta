// simple choose with a big domain, to have the if covered one must take anothe value
asm simplechooseBiGDomain
import StandardLibrary
	
signature:
    enum domain Letter = {AA, BB, CC, DD, EE, FF, GG, HH}
	controlled myLetter: Letter

definitions:
	main rule r_Main =
		choose $i in Letter do
				if $i = CC	then
					myLetter := $i
				else
					myLetter := AA
				endif

default init s0:
