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
                                forall $i524 in Index with and(ge($i524,1),le($i524,plus(1,idiv(minus($cut,1),2)))) do
                                    par
                                        par
                                            cardNumber(minus($cut,minus($i524,1))) := cardNumber($i524)
                                            cardSuit(minus($cut,minus($i524,1))) := cardSuit($i524)
                                        endpar
                                        par
                                            cardNumber($i524) := cardNumber(minus($cut,minus($i524,1)))
                                            cardSuit($i524) := cardSuit(minus($cut,minus($i524,1)))
                                        endpar
                                    endpar
                            else 
                                forall $i525 in Index with and(ge($i525,plus($cut,1)),le($i525,plus(plus($cut,1),idiv(minus(12,plus($cut,1)),2)))) do
                                    par
                                        par
                                            cardNumber(minus(12,minus($i525,plus($cut,1)))) := cardNumber($i525)
                                            cardSuit(minus(12,minus($i525,plus($cut,1)))) := cardSuit($i525)
                                        endpar
                                        par
                                            cardNumber($i525) := cardNumber(minus(12,minus($i525,plus($cut,1))))
                                            cardSuit($i525) := cardSuit(minus(12,minus($i525,plus($cut,1))))
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
                                forall $i526 in Index with and(ge($i526,free),le($i526,minus(indexFirstDeck,1))) do
                                    par
                                        cardSuit(plus($i526,1)) := cardSuit($i526)
                                        cardNumber(plus($i526,1)) := cardNumber($i526)
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
                                forall $i527 in Index with and(ge($i527,free),le($i527,minus(indexSecondDeck,1))) do
                                    par
                                        cardSuit(plus($i527,1)) := cardSuit($i527)
                                        cardNumber(plus($i527,1)) := cardNumber($i527)
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
