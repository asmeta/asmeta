asm GilbreathCardTrickFasterShuffleForAsmetaSMV

//execution time 153 seconds

import ../../STDL/StandardLibrary
import ../../STDL/CTLlibrary
import ../../STDL/LTLlibrary

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

	rule r_shiftCards($startInterval in Index, $endInterval in Index) =
		forall $i in Index with $i >= $startInterval and $i <= $endInterval do
			cardSuit($i + 1) := cardSuit($i)

	CTLSPEC ag((forall $q in QuartetIndex, $s in Suit with
										(exist $i in InQuartetIndex with cardSuit($q * 4 + $i) = $s)))
	LTLSPEC g((forall $q in QuartetIndex, $s in Suit with
										(exist $i in InQuartetIndex with cardSuit($q * 4 + $i) = $s)))

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
				//if an index of a deck is undef, we can stop shuffling, since the remaining cards of the
				//other deck will be place in the same position at the end of the sequence
				if isDef(indexFirstDeck) and isDef(indexFirstDeck) then
					//it randomly chooses a deck
					choose $pickFirst in Boolean with true do
						par
							if(free < 12) then
								free := free + 1
							else
								free := undef
							endif
							//the let rule was needed in the old version of AsmetaSMV
							/*let ($free = free, $indexFirstDeck=indexFirstDeck, $indexSecondDeck=indexSecondDeck) in
								if $pickFirst then
									par
										//cardSuit($free) := cardSuit($indexFirstDeck)
										r_moveCard[$indexFirstDeck, $free]
										r_shiftCards[$free, $indexFirstDeck - 1]
										if indexFirstDeck < cut then
											indexFirstDeck := indexFirstDeck + 1
										else
											indexFirstDeck := undef
										endif
									endpar
								else
									par
										//cardSuit($free) := cardSuit($indexSecondDeck)
										r_moveCard[$indexSecondDeck, $free]
										r_shiftCards[$free, $indexSecondDeck - 1]
										if indexSecondDeck < 12 then
											indexSecondDeck := indexSecondDeck + 1
										else
											indexSecondDeck := undef
										endif
										cut := cut + 1
										if(isDef(indexFirstDeck)) then
											if indexFirstDeck < cut then
												indexFirstDeck := indexFirstDeck + 1
											else
												indexFirstDeck := undef
											endif
										endif
									endpar
								endif
							endlet*/
							if $pickFirst then
								par
									//cardSuit(free) := cardSuit(indexFirstDeck)
									r_moveCard[indexFirstDeck, free]
									r_shiftCards[free, indexFirstDeck - 1]
									if indexFirstDeck < cut then
										indexFirstDeck := indexFirstDeck + 1
									else
										indexFirstDeck := undef
									endif
								endpar
							else
								par
									//cardSuit(free) := cardSuit(indexSecondDeck)
									r_moveCard[indexSecondDeck, free]
									r_shiftCards[free, indexSecondDeck - 1]
									if indexSecondDeck < 12 then
										indexSecondDeck := indexSecondDeck + 1
									else
										indexSecondDeck := undef
									endif
									cut := cut + 1
									if(isDef(indexFirstDeck)) then
										if indexFirstDeck < cut then
											indexFirstDeck := indexFirstDeck + 1
										else
											indexFirstDeck := undef
										endif
									endif
								endpar
							endif
						endpar
				else
					step := END
				endif
		endswitch

default init s0:
	function indexFirstDeck = 1
	function free = 1
	function step = CUTANDREVERSE
	function cardSuit($n in Index) = at({1 -> SPADES,  2 -> HEARTS,  3 -> CLUBS,  4 -> DIAMONDS,
										 5 -> SPADES,  6 -> HEARTS,  7 -> CLUBS,  8 -> DIAMONDS,
										 9 -> SPADES, 10 -> HEARTS, 11 -> CLUBS, 12 -> DIAMONDS}, $n)
