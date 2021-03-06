asm GilbreathCardTrickWithAuxFunctionSimple

//it uses an auxiliary function for shuffling the cards

import ../../../STDL/StandardLibrary

signature:
	enum domain Suit = {SPADES | HEARTS | CLUBS | DIAMONDS}
	enum domain Step = {CUTANDREVERSE | SHUFFLE | END}
	domain Number subsetof Integer
	domain Index subsetof Integer
	domain QuartetIndex subsetof Integer
	domain InQuartetIndex subsetof Integer
	dynamic controlled cardSuit: Index -> Suit  //position -> suit
	dynamic controlled cardNumber: Index -> Number  //position -> number
	dynamic controlled step: Step
	dynamic controlled cut: Index //"cut" is contained in the first half-deck
	
	dynamic controlled indexFirstDeck: Index
	dynamic controlled indexSecondDeck: Index
	dynamic controlled free: Index

	dynamic controlled cardSuitFinal: Index -> Suit  //position -> suit
	dynamic controlled cardNumberFinal: Index -> Number  //position -> number

definitions:
	domain Number = {1 : 13}
	domain Index = {1 : 12}
	domain QuartetIndex = {0 : 2}
	domain InQuartetIndex = {1 : 4}

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

	rule r_reverse($start in Index, $end in Index) =
		forall $i in Index with $i >= $start and $i <= $start + idiv($end-$start,2) do
			r_swapCards[$i, $end - ($i - $start)]
	
	invariant inv_0 over cardSuit: (forall $q in QuartetIndex, $s in Suit with
													(exist $i in InQuartetIndex with cardSuit($q * 4 + $i) = $s))
	invariant inv_0Bis over cardSuitFinal: step=END implies (forall $q in QuartetIndex, $s in Suit with
																	(exist $i in InQuartetIndex with cardSuitFinal($q * 4 + $i) = $s))

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
				//since "cut" is contained in the first half-deck, we choose a number in [1,11]
				//the obtained half-decks are: [1,cut], [cut+1,12]
				choose $cut in Index with $cut < 12 do
					par
						choose $reverseFirst in Boolean with true do
							if $reverseFirst then
								r_reverse[1, $cut]
							else
								r_reverse[$cut + 1, 12]
							endif
						step := SHUFFLE
						cut := $cut
						indexSecondDeck := $cut + 1
					endpar
			case SHUFFLE:
				//it randomly chooses a deck that still contains cards
				choose $b in Boolean with (isUndef(indexFirstDeck) implies not($b)) and (isUndef(indexSecondDeck) implies $b) do
					par
						free := free + 1
						if $b then
							par
								cardNumberFinal(free) := cardNumber(indexFirstDeck)
								cardSuitFinal(free) := cardSuit(indexFirstDeck)
								if indexFirstDeck < cut then
									indexFirstDeck := indexFirstDeck + 1
								else
									indexFirstDeck := undef
								endif
							endpar
						else
							par
								cardNumberFinal(free) := cardNumber(indexSecondDeck)
								cardSuitFinal(free) := cardSuit(indexSecondDeck)
								if indexSecondDeck < 12 then
									indexSecondDeck := indexSecondDeck + 1
								else
									indexSecondDeck := undef
								endif
							endpar
						endif
					endpar
				ifnone
					step := END
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
