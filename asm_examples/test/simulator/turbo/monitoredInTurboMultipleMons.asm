asm monitoredInTurboMultipleMons

import ../../../STDL/StandardLibrary
	
signature:
	monitored giveElement: Integer -> Integer
	monitored count: Integer 
	controlled seqs: Seq(Integer)

definitions:

	main rule r_main =
		if count!=0 then
			let ($i=0) in
				while $i < count do
					seq
						if giveElement($i) != 0 then
							seqs := append(seqs, giveElement($i)) 
						endif
						$i := $i + 1
					endseq
			endlet
		endif

default init s0:
	function seqs = []
