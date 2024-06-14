asm cards

import ../STDL/StandardLibrary
import ../STDL/CTLLibrary

signature:
	domain Number subsetof Integer
	enum domain Move = {SWAP | SWAP_CLOSE | SHIFT}
	dynamic controlled card: Number -> Number
	dynamic monitored move: Move

definitions:
	domain Number = {1 :5}

	macro rule r_swap($a in Number, $b in Number) =
		par
			card($a) := card($b)
			card($b) := card($a) 
		endpar
		
	//la carta in prima posizione e' sempre diversa dalla carta in seconda posizione
	CTLSPEC ag(card(1) != card(2))
	//e' sempre possibile raggiungere uno stato in cui la carta in quinta posizione
	//vale 3
	CTLSPEC ag(ef(card(5) = 3))
	//esiste uno stato in cui in seconda posizione c'è la carta 5 ed in quarta posizione
	//la carta 3 e in uno stato successivo le carte sono invertite
	CTLSPEC ef((card(2) = 5 and card(4) = 3) and ex(card(2) = 3 and card(4) = 5))

	main rule r_Main =
		switch(move)
			case SWAP: 
				choose $a in Number, $b in Number with $a != $b do
					r_swap[$a, $b]
			case SWAP_CLOSE: 
				choose $c in Number, $d in Number with $d = ($c + 1) do
					r_swap[$c, $d]
			case SHIFT:
				forall $e in Number with true do
					card(($e mod 5) + 1) := card($e)
		endswitch

default init s0:
	function card($n in Number) = $n
