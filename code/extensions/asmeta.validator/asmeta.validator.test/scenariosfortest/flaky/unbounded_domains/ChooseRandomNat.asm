// With false -> pick always fails
asm ChooseRandomNat
import ../StandardLibrary
	
signature:
	controlled myNat: Natural
definitions:
	main rule r_Main =
		choose $i in Natural with false do
			myNat := $i
		ifnone
			myNat:= undef
			
default init s0:
	function myNat = 0n
