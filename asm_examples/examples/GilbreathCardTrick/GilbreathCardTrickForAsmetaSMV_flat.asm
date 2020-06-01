//applied flatteners: MCR 
asm GilbreathCardTrickForAsmetaSMV_flat
import ../../STDL/StandardLibrary
import ../../STDL/CTLlibrary
import ../../STDL/LTLlibrary

signature:
    enum domain Suit = {SPADES | HEARTS | CLUBS | DIAMONDS}
    enum domain Step = {CUTANDREVERSE | SHUFFLE | END}
    domain Index subsetof Integer
    domain QuartetIndex subsetof Integer
    domain InQuartetIndex subsetof Integer

    controlled cardSuit: Index -> Suit
    controlled step: Step
    controlled cut: Index
    controlled indexFirstDeck: Index
    controlled indexSecondDeck: Index
    controlled free: Index

definitions:

    domain Index = {1,2,3,4,5,6,7,8,9,10,11,12}
    domain QuartetIndex = {0,1,2}
    domain InQuartetIndex = {1,2,3,4}


    LTLSPEC g((forall $q in QuartetIndex,$s in Suit with (exist $i in InQuartetIndex with eq(cardSuit(plus(mult($q,4),$i)),$s))))
    CTLSPEC ag((forall $q in QuartetIndex,$s in Suit with (exist $i in InQuartetIndex with eq(cardSuit(plus(mult($q,4),$i)),$s))))
    main rule r_Main =
        switch step
            case CUTANDREVERSE:
                choose $cut in Index with lt($cut,12) do
                    par
                        choose $reverseFirst in Boolean with true do
                            if $reverseFirst then
                                forall $i524 in Index with and(ge($i524,1),le($i524,plus(1,idiv(minus($cut,1),2)))) do
                                    par
                                        cardSuit(minus($cut,minus($i524,1))) := cardSuit($i524)
                                        cardSuit($i524) := cardSuit(minus($cut,minus($i524,1)))
                                    endpar
                            else 
                                forall $i525 in Index with and(ge($i525,plus($cut,1)),le($i525,plus(plus($cut,1),idiv(minus(12,plus($cut,1)),2)))) do
                                    par
                                        cardSuit(minus(12,minus($i525,plus($cut,1)))) := cardSuit($i525)
                                        cardSuit($i525) := cardSuit(minus(12,minus($i525,plus($cut,1))))
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
                                cardSuit(free) := cardSuit(indexFirstDeck)
                                forall $i526 in Index with and(ge($i526,free),le($i526,minus(indexFirstDeck,1))) do
                                    cardSuit(plus($i526,1)) := cardSuit($i526)
                                if lt(indexFirstDeck,cut) then
                                    indexFirstDeck := plus(indexFirstDeck,1)
                                else 
                                    indexFirstDeck := undef
                                endif
                            endpar
                        else 
                            par
                                cardSuit(free) := cardSuit(indexSecondDeck)
                                forall $i527 in Index with and(ge($i527,free),le($i527,minus(indexSecondDeck,1))) do
                                    cardSuit(plus($i527,1)) := cardSuit($i527)
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
function cardSuit($n in Index) = at({1->SPADES,2->HEARTS,3->CLUBS,4->DIAMONDS,5->SPADES,6->HEARTS,7->CLUBS,8->DIAMONDS,9->SPADES,10->HEARTS,11->CLUBS,12->DIAMONDS},$n)
