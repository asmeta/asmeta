asm GilbreathCardTrickWithAuxFunctionNotWorking

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
	
	dynamic controlled indexFirstDeck: Index
	dynamic controlled indexSecondDeck: Index
	dynamic controlled chosenI: Index
	dynamic controlled chosenI1: Index
	dynamic controlled chosenI2: Index
	dynamic controlled free: Index
	
	dynamic controlled cardSuitFinal: Index -> Suit  //position -> suit
	dynamic controlled cardNumberFinal: Index -> Number  //position -> number

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
			/*cardNumber($i1) := cardNumber($i2)
			cardNumber($i2) := cardNumber($i1)
			cardSuit($i1) := cardSuit($i2)
			cardSuit($i2) := cardSuit($i1)*/
			r_moveCard[$i1, $i2]
			r_moveCard[$i2, $i1]
		endpar
	
	invariant inv_0 over cardSuit: (forall $q in QuartetIndex, $s in Suit with cardSuit($q * 4 + 1) = $s or cardSuit($q * 4 + 2) = $s or
																				cardSuit($q * 4 + 3) = $s or cardSuit($q * 4 + 4) = $s)
	invariant inv_0Bis over cardSuitFinal: step=END implies (forall $q in QuartetIndex, $s in Suit with cardSuitFinal($q * 4 + 1) = $s or cardSuitFinal($q * 4 + 2) = $s or
																				cardSuitFinal($q * 4 + 3) = $s or cardSuitFinal($q * 4 + 4) = $s)

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
								forall $x in Index with $x <= idiv($cut,2) do
									r_swapCards[$x, ($cut - $x) + 1]
							else
								forall $y in Index with $y > $cut and $y <= ($cut + idiv(12-$cut, 2)) do
									r_swapCards[$y, 12 - (($y - $cut) - 1)]
							endif
						step := SHUFFLE
						cut := $cut
						indexSecondDeck := $cut + 1
					endpar
			case SHUFFLE:
				choose $i in Index, $i1 in Index, $i2 in Index with $i >= free and
																	((($i1 + 1 - indexFirstDeck) = ($i + 1 - free) and $i1 <= cut) or
																	 (($i2 + 1 - indexSecondDeck) = ($i + 1 - free) and $i2 > cut) or
																	 (($i1 + 1 - indexFirstDeck) + ($i2 + 1 - indexSecondDeck) = ($i + 1 - free) and $i1 <= cut and $i2 > cut)
																	 ) do
					par
						chosenI := $i
						chosenI1 := $i1
						chosenI2 := $i2
						if (($i1 + 1 - indexFirstDeck) = ($i + 1 - free) and $i1 <= cut) or (($i1 + 1 - indexFirstDeck) + ($i2 + 1 - indexSecondDeck) = ($i + 1 - free) and $i1 <= cut and $i2 > cut) then
							forall $a in Index with $a >= indexFirstDeck and $a <= $i1 do
								par
									cardNumberFinal(free + ($a - indexFirstDeck)) := cardNumber($a)  
									cardSuitFinal(free + ($a - indexFirstDeck)) := cardSuit($a) 
								endpar
						endif
						if (($i2 + 1 - indexSecondDeck) = ($i + 1 - free) and $i2 > cut) or (($i1 + 1 - indexFirstDeck) + ($i2 + 1 - indexSecondDeck) = ($i + 1 - free) and $i1 <= cut and $i2 > cut) then
							forall $b in Index with $b >= indexSecondDeck and $b <= $i2 do
								par
									cardNumberFinal(free + ($i1 + 1 - indexFirstDeck) + ($b - indexSecondDeck)) := cardNumber($b)
									cardSuitFinal(free + ($i1 + 1 - indexFirstDeck) + ($b - indexSecondDeck)) := cardSuit($b)
								endpar
						endif
						if $i = 12 then
							step := END
						else
							par
								free := $i + 1
								if (($i2 + 1 - indexSecondDeck) = ($i + 1 - free) and $i2 > cut) then
									if $i1 < cut then
										indexFirstDeck := $i1 + 1
									else
										indexFirstDeck := $i1
									endif
								endif
								if (($i1 + 1 - indexFirstDeck) = ($i + 1 - free) and $i1 <= cut) then
									if $i2 < 12 then
										indexSecondDeck := $i2 + 1
									else
										indexSecondDeck := 12
									endif
								endif
							endpar
						endif
					endpar
		endswitch

default init s0:
	function step = CUTANDREVERSE
	function indexFirstDeck = 1
	function free = 1
	function cardNumberFinal($n in Index) = 1
	function cardSuitFinal($n in Index) = SPADES
	function cardNumber($n in Index) = at({ 1 -> 5,  2 -> 3,   3 -> 12, 4 -> 8,
											5 -> 13, 6 -> 2,   7 -> 7,  8 -> 4,
											9 -> 8, 10 -> 11, 11 -> 9, 12 -> 1}, $n)
	function cardSuit($n in Index) = at({1 -> SPADES,  2 -> HEARTS,  3 -> CLUBS,  4 -> DIAMONDS,
										 5 -> SPADES,  6 -> HEARTS,  7 -> CLUBS,  8 -> DIAMONDS,
										 9 -> SPADES, 10 -> HEARTS, 11 -> CLUBS, 12 -> DIAMONDS}, $n)
