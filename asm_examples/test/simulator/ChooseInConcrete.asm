asm ChooseInConcrete

import ../../STDL/StandardLibrary

signature:

domain A subsetof Integer

controlled y : A

definitions:

domain A = {1 : 10}

main rule r_main =
	choose $x in A with true do y:= $x

default init s0:
