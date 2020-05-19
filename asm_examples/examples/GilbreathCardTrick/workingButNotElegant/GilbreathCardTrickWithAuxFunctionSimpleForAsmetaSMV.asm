asm GilbreathCardTrickWithAuxFunctionSimpleForAsmetaSMV

//it uses an auxiliary function for shuffling the cards

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLlibrary

signature:
	enum domain Suit = {SPADES | HEARTS | CLUBS | DIAMONDS}
	enum domain Step = {CUTANDREVERSE | SHUFFLE | END}
	domain Index subsetof Integer
	domain QuartetIndex subsetof Integer
	domain InQuartetIndex subsetof Integer
	dynamic controlled cardSuit: Index -> Suit  //position -> suit
	dynamic controlled step: Step
	dynamic controlled cut: Index //"cut" is contained in the first half-deck

	dynamic controlled indexFirstDeck: Index
	dynamic controlled indexSecondDeck: Index
	dynamic controlled free: Index

	dynamic controlled tempDeck: Index -> Suit  //position -> suit

definitions:
	domain Index = {1 : 12}
	domain QuartetIndex = {0 : 2}
	domain InQuartetIndex = {1 : 4}

	rule r_moveCard($i1 in Index, $i2 in Index) =
		cardSuit($i2) := cardSuit($i1)

	rule r_swapCards($i1 in Index, $i2 in Index) =
		par
			r_moveCard[$i1, $i2]
			r_moveCard[$i2, $i1]
		endpar

	rule r_reverse($start in Index, $end in Index) =
		forall $i in Index with $i >= $start and $i <= $start + idiv($end-$start,2) do
			r_swapCards[$i, $end - ($i - $start)]
	
	CTLSPEC ag((forall $q in QuartetIndex, $s in Suit with (exist $i in InQuartetIndex with cardSuit($q * 4 + $i) = $s))) 
	//CTLSPEC ag(step=END implies (forall $q in QuartetIndex, $s in Suit with (exist $i in InQuartetIndex with tempDeck($q * 4 + $i) = $s)))

	main rule r_Main =
		switch (step)
			case CUTANDREVERSE:
				//since "cut" is contained in the first half-deck, I have to choose a number in [1,11]
				//the obtained half-decks will be: [1,cut], [cut+1,12]
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
						//the let rule was needed in the old version of AsmetaSMV
						/*let ($free = free, $indexFirstDeck=indexFirstDeck, $indexSecondDeck=indexSecondDeck) in
							if $b then
								par
									tempDeck($free) := cardSuit($indexFirstDeck)
									if indexFirstDeck < cut then
										indexFirstDeck := indexFirstDeck + 1
									else
										indexFirstDeck := undef
									endif
								endpar
							else
								par
									tempDeck($free) := cardSuit($indexSecondDeck)
									if indexSecondDeck < 12 then
										indexSecondDeck := indexSecondDeck + 1
									else
										indexSecondDeck := undef
									endif
								endpar
							endif
						endlet*/
						if $b then
							par
								tempDeck(free) := cardSuit(indexFirstDeck)
								if indexFirstDeck < cut then
									indexFirstDeck := indexFirstDeck + 1
								else
									indexFirstDeck := undef
								endif
							endpar
						else
							par
								tempDeck(free) := cardSuit(indexSecondDeck)
								if indexSecondDeck < 12 then
									indexSecondDeck := indexSecondDeck + 1
								else
									indexSecondDeck := undef
								endif
							endpar
						endif
					endpar
				ifnone
					par
						step := END
						forall $i in Index with true do
							cardSuit($i) := tempDeck($i)
					endpar
		endswitch

default init s0:
	function step = CUTANDREVERSE
	function indexFirstDeck = 1
	function free = 1
	function cardSuit($n in Index) = at({1 -> SPADES,  2 -> HEARTS,  3 -> CLUBS,  4 -> DIAMONDS,
										 5 -> SPADES,  6 -> HEARTS,  7 -> CLUBS,  8 -> DIAMONDS,
										 9 -> SPADES, 10 -> HEARTS, 11 -> CLUBS, 12 -> DIAMONDS}, $n)
