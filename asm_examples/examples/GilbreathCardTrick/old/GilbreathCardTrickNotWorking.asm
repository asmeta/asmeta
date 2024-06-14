asm GilbreathCardTrickNotWorking

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLLibrary

signature:
	enum domain Suit = {SPADES | HEARTS | CLUBS | DIAMONDS}
	enum domain Step = {CUTANDREVERSE | SHUFFLE | END}
	domain Number subsetof Integer
	domain Index subsetof Integer
	domain QuartetIndex subsetof Integer
	dynamic controlled cardSuit: Index -> Suit  //position -> suit
	dynamic controlled cardNumber: Index -> Number  //position -> number
	dynamic controlled step: Step
	dynamic controlled cut: Index //"cut" is contained in the first half-deck
	dynamic controlled lowFirstIndex: Index
	
	dynamic controlled indexFirstDeck: Index
	dynamic controlled indexSecondDeck: Index
	dynamic controlled size: Index

definitions:
	domain Number = {1 : 13}
	domain Index = {1 : 12}
	domain QuartetIndex = {0 : 2}

	rule r_moveCard($i1 in Index, $i2 in Index) =
		par
			cardNumber($i2) := cardNumber($i1)
			cardSuit($i2) := cardSuit($i1)
		endpar

	rule r_swapCards($i1 in Index, $i2 in Index) =
		par
			r_moveCard[$i1, $i2]
			r_moveCard[$i2, $i1]
		endpar
	
	invariant inv_0 over cardSuit: step = END implies (forall $q in QuartetIndex, $s in Suit with cardSuit($q * 4 + 1) = $s or cardSuit($q * 4 + 2) = $s or
																				cardSuit($q * 4 + 3) = $s or cardSuit($q * 4 + 4) = $s)

	//no card is created or cancelled
	invariant inv_1 over cardNumber, cardSuit:  (exist $i in Index with cardNumber($i)=5 and cardSuit($i)=SPADES)
	invariant inv_2 over cardNumber, cardSuit:  (exist $i in Index with cardNumber($i)=13 and cardSuit($i)=SPADES)
	invariant inv_3 over cardNumber, cardSuit:  (exist $i in Index with cardNumber($i)=8 and cardSuit($i)=SPADES)
	invariant inv_4 over cardNumber, cardSuit:  (exist $i in Index with cardNumber($i)=3 and cardSuit($i)=HEARTS)
	invariant inv_5 over cardNumber, cardSuit:  (exist $i in Index with cardNumber($i)=2 and cardSuit($i)=HEARTS)
	invariant inv_6 over cardNumber, cardSuit:  (exist $i in Index with cardNumber($i)=11 and cardSuit($i)=HEARTS)
	invariant inv_7 over cardNumber, cardSuit:  (exist $i in Index with cardNumber($i)=12 and cardSuit($i)=CLUBS)
	invariant inv_8 over cardNumber, cardSuit:  (exist $i in Index with cardNumber($i)=7 and cardSuit($i)=CLUBS)
	invariant inv_9 over cardNumber, cardSuit:  (exist $i in Index with cardNumber($i)=9 and cardSuit($i)=CLUBS)
	invariant inv_10 over cardNumber, cardSuit: (exist $i in Index with cardNumber($i)=8 and cardSuit($i)=DIAMONDS)
	invariant inv_11 over cardNumber, cardSuit: (exist $i in Index with cardNumber($i)=4 and cardSuit($i)=DIAMONDS)
	invariant inv_12 over cardNumber, cardSuit: (exist $i in Index with cardNumber($i)=1 and cardSuit($i)=DIAMONDS)


	invariant inv_13 over cardSuit: step=END implies (forall $suit in Suit with (exist $i in Index with $i <= 4 and cardSuit($i)=$suit))
	invariant inv_14 over cardSuit: step=END implies (forall $suit in Suit with (exist $i in Index with $i > 4 and $i <= 8 and cardSuit($i)=$suit))
	invariant inv_15 over cardSuit: step=END implies (forall $suit in Suit with (exist $i in Index with $i > 8 and $i <= 12 and cardSuit($i)=$suit))

	main rule r_Main =
		switch (step)
			case CUTANDREVERSE:
				//since "cut" is contained in the first half-deck, I have to choose a number in [1,11]
				//the obtained half-decks will be: [1,cut], [cut+1,12]
				choose $cut in Index with $cut < 12 do
					par
						choose $reverseFirst in Boolean with true do
							if $reverseFirst then
								forall $a in Index with $a <= idiv($cut,2) do
									r_swapCards[$a, ($cut - $a) + 1]
							else
								forall $b in Index with $b > $cut and $b <= ($cut + idiv(12-$cut, 2)) do
									r_swapCards[$b, 12 - (($b - $cut) - 1)]
							endif
						step := SHUFFLE
						cut := $cut
						indexSecondDeck := $cut + 1
					endpar
			case SHUFFLE:
				if cut < 12 then
					choose $indexFirstDeck in Index, $indexSecondDeck in Index, $size in Index with
										$indexFirstDeck >= lowFirstIndex and $indexFirstDeck <= cut and
										 $indexSecondDeck > cut and ($indexSecondDeck + $size) <= 13 do
						par
							indexFirstDeck := $indexFirstDeck
							indexSecondDeck := $indexSecondDeck
							size := $size
							lowFirstIndex := $indexFirstDeck + $size
							cut :=  $indexSecondDeck + $size
							forall $i in Index with $i >= $indexSecondDeck and $i <= $indexSecondDeck + $size - 1 do
								r_moveCard[$i, $indexFirstDeck + ($i - $indexSecondDeck)]
							forall $i2 in Index with $i2 >= $indexFirstDeck and $i2 <= $indexSecondDeck - 1 do
								r_moveCard[$i2, $i2 + $size]
						endpar
				else
					step := END
				endif
		endswitch

default init s0:
	function step = CUTANDREVERSE
	function lowFirstIndex = 1
	function cardNumber($n in Index) = at({ 1 -> 5,  2 -> 3,   3 -> 12, 4 -> 8,
											5 -> 13, 6 -> 2,   7 -> 7,  8 -> 4,
											9 -> 8, 10 -> 11, 11 -> 9, 12 -> 1}, $n)
	function cardSuit($n in Index) = at({1 -> SPADES,  2 -> HEARTS,  3 -> CLUBS,  4 -> DIAMONDS,
										 5 -> SPADES,  6 -> HEARTS,  7 -> CLUBS,  8 -> DIAMONDS,
										 9 -> SPADES, 10 -> HEARTS, 11 -> CLUBS, 12 -> DIAMONDS}, $n)
