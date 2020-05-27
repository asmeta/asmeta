//applied flatteners: MCR 
asm GilbreathCardTrick_flat
import ../../STDL/StandardLibrary

signature:
    enum domain Suit = {SPADES | HEARTS | CLUBS | DIAMONDS}
    enum domain Step = {CUTANDREVERSE | SHUFFLE | END}
    domain Number subsetof Integer
    domain Index subsetof Integer
    domain QuartetIndex subsetof Integer
    domain InQuartetIndex subsetof Integer

    controlled cardSuit: Index -> Suit
    controlled cardNumber: Index -> Number
    controlled step: Step
    controlled cut: Index
    controlled indexFirstDeck: Index
    controlled indexSecondDeck: Index
    controlled free: Index

definitions:

    domain Number = {1,2,3,4,5,6,7,8,9,10,11,12,13}
    domain Index = {1,2,3,4,5,6,7,8,9,10,11,12}
    domain QuartetIndex = {0,1,2}
    domain InQuartetIndex = {1,2,3,4}


    invariant inv_0 over cardSuit: (forall $q in QuartetIndex,$s in Suit with (exist $i in InQuartetIndex with eq(cardSuit(plus(mult($q,4),$i)),$s)))
    invariant inv_1 over cardNumber, cardSuit: (exist $i in Index with and(eq(cardNumber($i),5),eq(cardSuit($i),SPADES)))
    invariant inv_2 over cardNumber, cardSuit: (exist $i in Index with and(eq(cardNumber($i),13),eq(cardSuit($i),SPADES)))
    invariant inv_3 over cardNumber, cardSuit: (exist $i in Index with and(eq(cardNumber($i),8),eq(cardSuit($i),SPADES)))
    invariant inv_4 over cardNumber, cardSuit: (exist $i in Index with and(eq(cardNumber($i),3),eq(cardSuit($i),HEARTS)))
    invariant inv_5 over cardNumber, cardSuit: (exist $i in Index with and(eq(cardNumber($i),2),eq(cardSuit($i),HEARTS)))
    invariant inv_6 over cardNumber, cardSuit: (exist $i in Index with and(eq(cardNumber($i),11),eq(cardSuit($i),HEARTS)))
    invariant inv_7 over cardNumber, cardSuit: (exist $i in Index with and(eq(cardNumber($i),12),eq(cardSuit($i),CLUBS)))
    invariant inv_8 over cardNumber, cardSuit: (exist $i in Index with and(eq(cardNumber($i),7),eq(cardSuit($i),CLUBS)))
    invariant inv_9 over cardNumber, cardSuit: (exist $i in Index with and(eq(cardNumber($i),9),eq(cardSuit($i),CLUBS)))
    invariant inv_10 over cardNumber, cardSuit: (exist $i in Index with and(eq(cardNumber($i),8),eq(cardSuit($i),DIAMONDS)))
    invariant inv_11 over cardNumber, cardSuit: (exist $i in Index with and(eq(cardNumber($i),4),eq(cardSuit($i),DIAMONDS)))
    invariant inv_12 over cardNumber, cardSuit: (exist $i in Index with and(eq(cardNumber($i),1),eq(cardSuit($i),DIAMONDS)))
    main rule r_Main =
        switch step
            case CUTANDREVERSE:
                choose $cut in Index with lt($cut,12) do
                    par
                        choose $reverseFirst in Boolean with true do
                            if $reverseFirst then
                                forall $i108 in Index with and(ge($i108,1),le($i108,plus(1,idiv(minus($cut,1),2)))) do
                                    par
                                        par
                                            cardNumber(minus($cut,minus($i108,1))) := cardNumber($i108)
                                            cardSuit(minus($cut,minus($i108,1))) := cardSuit($i108)
                                        endpar
                                        par
                                            cardNumber($i108) := cardNumber(minus($cut,minus($i108,1)))
                                            cardSuit($i108) := cardSuit(minus($cut,minus($i108,1)))
                                        endpar
                                    endpar
                            else 
                                forall $i109 in Index with and(ge($i109,plus($cut,1)),le($i109,plus(plus($cut,1),idiv(minus(12,plus($cut,1)),2)))) do
                                    par
                                        par
                                            cardNumber(minus(12,minus($i109,plus($cut,1)))) := cardNumber($i109)
                                            cardSuit(minus(12,minus($i109,plus($cut,1)))) := cardSuit($i109)
                                        endpar
                                        par
                                            cardNumber($i109) := cardNumber(minus(12,minus($i109,plus($cut,1))))
                                            cardSuit($i109) := cardSuit(minus(12,minus($i109,plus($cut,1))))
                                        endpar
                                    endpar
                            endif
                        step := SHUFFLE
                        cut := $cut
                        indexSecondDeck := plus($cut,1)
                    endpar
            case SHUFFLE:
                choose $pickFirst in Boolean with and(implies(isUndef(indexFirstDeck),not($pickFirst)),implies(isUndef(indexSecondDeck),$pickFirst)) do
                    par
                        if lt(free,12) then
                            free := plus(free,1)
                        else 
                            free := undef
                        endif
                        if $pickFirst then
                            par
                                par
                                    cardNumber(free) := cardNumber(indexFirstDeck)
                                    cardSuit(free) := cardSuit(indexFirstDeck)
                                endpar
                                forall $i110 in Index with and(ge($i110,free),le($i110,minus(indexFirstDeck,1))) do
                                    par
                                        cardSuit(plus($i110,1)) := cardSuit($i110)
                                        cardNumber(plus($i110,1)) := cardNumber($i110)
                                    endpar
                                if lt(indexFirstDeck,cut) then
                                    indexFirstDeck := plus(indexFirstDeck,1)
                                else 
                                    indexFirstDeck := undef
                                endif
                            endpar
                        else 
                            par
                                par
                                    cardNumber(free) := cardNumber(indexSecondDeck)
                                    cardSuit(free) := cardSuit(indexSecondDeck)
                                endpar
                                forall $i111 in Index with and(ge($i111,free),le($i111,minus(indexSecondDeck,1))) do
                                    par
                                        cardSuit(plus($i111,1)) := cardSuit($i111)
                                        cardNumber(plus($i111,1)) := cardNumber($i111)
                                    endpar
                                if lt(indexSecondDeck,12) then
                                    indexSecondDeck := plus(indexSecondDeck,1)
                                else 
                                    indexSecondDeck := undef
                                endif
                                cut := plus(cut,1)
                                if isDef(indexFirstDeck) then
                                    if lt(indexFirstDeck,cut) then
                                        indexFirstDeck := plus(indexFirstDeck,1)
                                    else 
                                        indexFirstDeck := undef
                                    endif
                                endif
                            endpar
                        endif
                    endpar
            ifnone
                step := END
    endswitch

default init s0:
function indexFirstDeck = 1
function free = 1
function step = CUTANDREVERSE
function cardNumber($n in Index) = at({1->5,2->3,3->12,4->8,5->13,6->2,7->7,8->4,9->8,10->11,11->9,12->1},$n)
function cardSuit($n in Index) = at({1->SPADES,2->HEARTS,3->CLUBS,4->DIAMONDS,5->SPADES,6->HEARTS,7->CLUBS,8->DIAMONDS,9->SPADES,10->HEARTS,11->CLUBS,12->DIAMONDS},$n)
