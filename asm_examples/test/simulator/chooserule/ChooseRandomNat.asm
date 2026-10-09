// this is with true OK
asm ChooseRandomNat
import  ../../../STDL/StandardLibrary
	
signature:
	controlled myNat: Natural
definitions:
	main rule r_Main =
		choose $i in Natural with true do
			myNat := $i
default init s0:
	function myNat = 0n
