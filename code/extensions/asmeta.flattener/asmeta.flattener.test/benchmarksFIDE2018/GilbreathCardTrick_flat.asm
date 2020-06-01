//applied flatteners: MCR FR ChR AR LR CaR NR 
asm GilbreathCardTrick_flat
import ./STDL/StandardLibrary
import ./STDL/CTLlibrary
import ./STDL/LTLlibrary

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
    derived chooseVar0: Index
    derived chooseVar1: Boolean
    derived chooseVar2: Boolean

definitions:

    domain Index = {1,2,3,4,5,6,7,8,9,10,11,12}
    domain QuartetIndex = {0,1,2}
    domain InQuartetIndex = {1,2,3,4}

    function chooseVar0 = chooseone({$cut in Index| lt($cut,12) : $cut})
    function chooseVar1 = chooseone({$reverseFirst in Boolean| true : $reverseFirst})
    function chooseVar2 = chooseone({$pickFirst in Boolean| and(implies(isUndef(indexFirstDeck),not($pickFirst)),implies(isUndef(indexSecondDeck),$pickFirst)) : $pickFirst})


    LTLSPEC g((forall $q in QuartetIndex,$s in Suit with (exist $i in InQuartetIndex with eq(cardSuit(plus(mult($q,4),$i)),$s))))
    CTLSPEC ag((forall $q in QuartetIndex,$s in Suit with (exist $i in InQuartetIndex with eq(cardSuit(plus(mult($q,4),$i)),$s))))
    main rule r_Main =
        par
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(-1) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,9),eq(indexSecondDeck,6))) then
                cardSuit(9) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,7),eq(indexSecondDeck,8))) then
                cardSuit(7) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,6),eq(indexSecondDeck,5))) then
                cardSuit(6) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(9) := cardSuit(14)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(4) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(14) := cardSuit(5)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,2),eq(indexSecondDeck,1))) then
                cardSuit(2) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,10),eq(indexSecondDeck,1))) then
                cardSuit(10) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,2),eq(indexFirstDeck,3))) then
                cardSuit(2) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(1) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,12),eq(indexSecondDeck,2))) then
                cardSuit(12) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(8) := cardSuit(-6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(8) := cardSuit(10)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,2),eq(indexFirstDeck,9))) then
                cardSuit(2) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,1),eq(indexSecondDeck,10))) then
                cardSuit(1) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(-1) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,12),eq(indexFirstDeck,7))) then
                cardSuit(12) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(5) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(7) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(1) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(3) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(7) := cardSuit(-4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(-5) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,2),eq(indexSecondDeck,10))) then
                cardSuit(2) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(17) := cardSuit(5)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,3),eq(indexFirstDeck,1))) then
                cardSuit(3) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(16) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,11),eq(indexSecondDeck,3))) then
                cardSuit(11) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(8) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(5) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,10),eq(indexSecondDeck,3))) then
                cardSuit(10) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(12) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(-2) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,1),eq(indexSecondDeck,3))) then
                cardSuit(1) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(2) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(8) := cardSuit(-5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(11) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,5),eq(indexSecondDeck,10))) then
                cardSuit(5) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(5) := cardSuit(20)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(6) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(12) := cardSuit(-6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(ge(4,free),le(4,minus(indexFirstDeck,1)))) then
                cardSuit(5) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(-6) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(10) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(1) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(21) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(ge(11,free),le(11,minus(indexSecondDeck,1)))) then
                cardSuit(12) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,5),eq(indexFirstDeck,5))) then
                cardSuit(5) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(9) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(ge(2,free),le(2,minus(indexSecondDeck,1)))) then
                cardSuit(3) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,12),eq(indexFirstDeck,12))) then
                cardSuit(12) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,12),eq(indexSecondDeck,1))) then
                cardSuit(12) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(5) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(-4) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(3) := cardSuit(20)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(3) := cardSuit(0)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(9) := cardSuit(-3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(8) := cardSuit(15)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(9) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(12) := cardSuit(-2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,11),eq(indexSecondDeck,7))) then
                cardSuit(11) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(5) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,5),eq(indexFirstDeck,7))) then
                cardSuit(5) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(4) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(0) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(-2) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(11) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(-8) := cardSuit(10)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,9),eq(indexFirstDeck,4))) then
                cardSuit(9) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(7) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,1),eq(indexFirstDeck,8))) then
                cardSuit(1) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(18) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,7),eq(indexFirstDeck,4))) then
                cardSuit(7) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(6) := cardSuit(-4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,11),eq(indexSecondDeck,2))) then
                cardSuit(11) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(2) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(7) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(9) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(7) := cardSuit(-1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(-4) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(7) := cardSuit(15)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(9) := cardSuit(-2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,7),eq(indexFirstDeck,2))) then
                cardSuit(7) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(0) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,6),eq(indexFirstDeck,10))) then
                cardSuit(6) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(15) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(1) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(9) := cardSuit(13)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,9),eq(indexSecondDeck,9))) then
                cardSuit(9) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(3) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,6),eq(indexFirstDeck,5))) then
                cardSuit(6) := cardSuit(5)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),lt(indexFirstDeck,cut)) then
                indexFirstDeck := plus(indexFirstDeck,1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(12) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,4),eq(indexFirstDeck,7))) then
                cardSuit(4) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(4) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(7) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(11) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,2),eq(indexFirstDeck,12))) then
                cardSuit(2) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(8) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(19) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(4) := cardSuit(18)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,3),eq(indexFirstDeck,5))) then
                cardSuit(3) := cardSuit(5)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,5),eq(indexFirstDeck,9))) then
                cardSuit(5) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(6) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(7) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(12) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(12) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(8) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(8) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,3),eq(indexFirstDeck,10))) then
                cardSuit(3) := cardSuit(10)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,10),eq(indexSecondDeck,6))) then
                cardSuit(10) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(5) := cardSuit(10)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,6),eq(indexFirstDeck,12))) then
                cardSuit(6) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(6) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(11) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(10) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(5) := cardSuit(0)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,9),eq(indexFirstDeck,6))) then
                cardSuit(9) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,4),eq(indexSecondDeck,5))) then
                cardSuit(4) := cardSuit(5)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(ge(9,free),le(9,minus(indexSecondDeck,1)))) then
                cardSuit(10) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(17) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(7) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(0) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(10) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(6) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,2),eq(indexFirstDeck,2))) then
                cardSuit(2) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(9) := cardSuit(16)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(-10) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,6),eq(indexSecondDeck,1))) then
                cardSuit(6) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(11) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,11),eq(indexSecondDeck,12))) then
                cardSuit(11) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,9),eq(indexSecondDeck,5))) then
                cardSuit(9) := cardSuit(5)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,4),eq(indexFirstDeck,3))) then
                cardSuit(4) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(8) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,10),eq(indexFirstDeck,7))) then
                cardSuit(10) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(3) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(-9) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(8) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(-3) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(10) := cardSuit(15)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(22) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(2) := cardSuit(20)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,5),eq(indexFirstDeck,3))) then
                cardSuit(5) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(6) := cardSuit(10)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,1),eq(indexSecondDeck,11))) then
                cardSuit(1) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(10) := cardSuit(-2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(3) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(5) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,1),eq(indexSecondDeck,4))) then
                cardSuit(1) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(15) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(14) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,5),eq(indexSecondDeck,7))) then
                cardSuit(5) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,2),eq(indexSecondDeck,5))) then
                cardSuit(2) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(8) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(10) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(7) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),not(lt(indexSecondDeck,12))) then
                indexSecondDeck := undef
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(7) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(6) := cardSuit(16)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(19) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,3),eq(indexSecondDeck,6))) then
                cardSuit(3) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(-4) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,9),eq(indexSecondDeck,2))) then
                cardSuit(9) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(12) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,1),eq(indexFirstDeck,11))) then
                cardSuit(1) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(8) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,9),eq(indexFirstDeck,1))) then
                cardSuit(9) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(4) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(11) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(ge(10,free),le(10,minus(indexFirstDeck,1)))) then
                cardSuit(11) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(6) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(ge(12,free),le(12,minus(indexSecondDeck,1)))) then
                cardSuit(13) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(10) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(9) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,4),eq(indexSecondDeck,10))) then
                cardSuit(4) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(15) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,6),eq(indexFirstDeck,9))) then
                cardSuit(6) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(20) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(2) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(14) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,11),eq(indexSecondDeck,1))) then
                cardSuit(11) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(6) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(20) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(-1) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(11) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(2) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(21) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(3) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(ge(1,free),le(1,minus(indexSecondDeck,1)))) then
                cardSuit(2) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(2) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(6) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(6) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(1) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(-3) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(-4) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(-2) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(8) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,12),eq(indexSecondDeck,10))) then
                cardSuit(12) := cardSuit(10)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,11),eq(indexSecondDeck,11))) then
                cardSuit(11) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(ge(6,free),le(6,minus(indexSecondDeck,1)))) then
                cardSuit(7) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,4),eq(indexFirstDeck,6))) then
                cardSuit(4) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(17) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,12),eq(indexSecondDeck,9))) then
                cardSuit(12) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(12) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(14) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(-1) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(2) := cardSuit(0)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,3),eq(indexFirstDeck,9))) then
                cardSuit(3) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(3) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,7),eq(indexFirstDeck,1))) then
                cardSuit(7) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(10) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(11) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,10),eq(indexFirstDeck,8))) then
                cardSuit(10) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(3) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(2) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(11) := cardSuit(-7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(10) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(15) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(6) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,5),eq(indexSecondDeck,3))) then
                cardSuit(5) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(ge(10,free),le(10,minus(indexSecondDeck,1)))) then
                cardSuit(11) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(4) := cardSuit(13)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(8) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,2),eq(indexFirstDeck,1))) then
                cardSuit(2) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,4),eq(indexFirstDeck,8))) then
                cardSuit(4) := cardSuit(8)
            endif
            if and(and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),isDef(indexFirstDeck)),not(lt(indexFirstDeck,cut))) then
                indexFirstDeck := undef
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(-6) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(ge(5,free),le(5,minus(indexSecondDeck,1)))) then
                cardSuit(6) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(6) := cardSuit(15)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,6),eq(indexFirstDeck,11))) then
                cardSuit(6) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(-2) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(11) := cardSuit(-9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(ge(9,free),le(9,minus(indexFirstDeck,1)))) then
                cardSuit(10) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,5),eq(indexSecondDeck,5))) then
                cardSuit(5) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(2) := cardSuit(23)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(3) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,9),eq(indexSecondDeck,8))) then
                cardSuit(9) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,9),eq(indexSecondDeck,3))) then
                cardSuit(9) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(8) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,8),eq(indexFirstDeck,6))) then
                cardSuit(8) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(10) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(2) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(10) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(15) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,9),eq(indexSecondDeck,4))) then
                cardSuit(9) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,7),eq(indexSecondDeck,2))) then
                cardSuit(7) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(7) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(1) := cardSuit(24)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(1) := cardSuit(15)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,6),eq(indexSecondDeck,12))) then
                cardSuit(6) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,8),eq(indexSecondDeck,12))) then
                cardSuit(8) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(9) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,2),eq(indexSecondDeck,9))) then
                cardSuit(2) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(5) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(12) := cardSuit(-8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(-1) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(11) := cardSuit(13)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,1),eq(indexSecondDeck,2))) then
                cardSuit(1) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,8),eq(indexFirstDeck,11))) then
                cardSuit(8) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(7) := cardSuit(0)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,7),eq(indexSecondDeck,7))) then
                cardSuit(7) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,2),eq(indexFirstDeck,10))) then
                cardSuit(2) := cardSuit(10)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,5),eq(indexSecondDeck,11))) then
                cardSuit(5) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(2) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(3) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(5) := cardSuit(-3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(1) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(7) := cardSuit(18)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(2) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(1) := cardSuit(23)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(6) := cardSuit(14)
            endif
            if and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)) then
                cut := plus(cut,1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(10) := cardSuit(-5)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,2),eq(indexFirstDeck,5))) then
                cardSuit(2) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(6) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(0) := cardSuit(10)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),lt(indexSecondDeck,12)) then
                indexSecondDeck := plus(indexSecondDeck,1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,5),eq(indexSecondDeck,2))) then
                cardSuit(5) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,10),eq(indexSecondDeck,2))) then
                cardSuit(10) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(8) := cardSuit(14)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(11) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(4) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,7),eq(indexSecondDeck,12))) then
                cardSuit(7) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(ge(7,free),le(7,minus(indexSecondDeck,1)))) then
                cardSuit(8) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,1),eq(indexFirstDeck,4))) then
                cardSuit(1) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(1) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(15) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,1),eq(indexFirstDeck,5))) then
                cardSuit(1) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(4) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(5) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,5),eq(indexFirstDeck,11))) then
                cardSuit(5) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,2),eq(indexSecondDeck,8))) then
                cardSuit(2) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(22) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(8) := cardSuit(-1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,6),eq(indexSecondDeck,3))) then
                cardSuit(6) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(12) := cardSuit(-5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(4) := cardSuit(20)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,3),eq(indexSecondDeck,2))) then
                cardSuit(3) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(14) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(7) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(12) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(14) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(14) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(-3) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(6) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(12) := cardSuit(13)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(10) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(-9) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,2),eq(indexFirstDeck,11))) then
                cardSuit(2) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(10) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,4),eq(indexSecondDeck,7))) then
                cardSuit(4) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,6),eq(indexSecondDeck,9))) then
                cardSuit(6) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,7),eq(indexFirstDeck,3))) then
                cardSuit(7) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(4) := cardSuit(-1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(-3) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(5) := cardSuit(13)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(5) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(3) := cardSuit(18)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(4) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(-8) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,2),eq(indexSecondDeck,3))) then
                cardSuit(2) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(16) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(4) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,4),eq(indexFirstDeck,10))) then
                cardSuit(4) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(11) := cardSuit(-3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,3),eq(indexFirstDeck,8))) then
                cardSuit(3) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(9) := cardSuit(0)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,8),eq(indexFirstDeck,1))) then
                cardSuit(8) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(6) := cardSuit(19)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(5) := cardSuit(15)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,4),eq(indexSecondDeck,1))) then
                cardSuit(4) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(4) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(ge(3,free),le(3,minus(indexSecondDeck,1)))) then
                cardSuit(4) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(11) := cardSuit(-6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,11),eq(indexSecondDeck,8))) then
                cardSuit(11) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(1) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(1) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,12),eq(indexFirstDeck,1))) then
                cardSuit(12) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(9) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(13) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,2),eq(indexSecondDeck,11))) then
                cardSuit(2) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(13) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(11) := cardSuit(14)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(3) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(1) := cardSuit(17)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(1) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(0) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(-5) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(11) := cardSuit(-2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(ge(6,free),le(6,minus(indexFirstDeck,1)))) then
                cardSuit(7) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(1) := cardSuit(10)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,12),eq(indexFirstDeck,11))) then
                cardSuit(12) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(7) := cardSuit(17)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,7),eq(indexFirstDeck,9))) then
                cardSuit(7) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(7) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(-4) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(4) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(3) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,12),eq(indexSecondDeck,12))) then
                cardSuit(12) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(4) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(6) := cardSuit(11)
            endif
            if and(eq(CUTANDREVERSE,step),isDef(chooseVar0)) then
                par
                    step := SHUFFLE
                    cut := chooseVar0
                    indexSecondDeck := plus(chooseVar0,1)
                endpar
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(9) := cardSuit(-1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,12),eq(indexSecondDeck,6))) then
                cardSuit(12) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(8) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(7) := cardSuit(10)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,10),eq(indexFirstDeck,3))) then
                cardSuit(10) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(1) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(12) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(7) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(11) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(5) := cardSuit(19)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(21) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(16) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(2) := cardSuit(22)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(-2) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,6),eq(indexSecondDeck,6))) then
                cardSuit(6) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(5) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(9) := cardSuit(-5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(3) := cardSuit(13)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(18) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(1) := cardSuit(20)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(-7) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(6) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(2) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(2) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(10) := cardSuit(-4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(5) := cardSuit(-2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(4) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(4) := cardSuit(21)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(11) := cardSuit(-5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(3) := cardSuit(16)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,8),eq(indexSecondDeck,5))) then
                cardSuit(8) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(11) := cardSuit(0)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(2) := cardSuit(16)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,10),eq(indexFirstDeck,2))) then
                cardSuit(10) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,5),eq(indexSecondDeck,8))) then
                cardSuit(5) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,6),eq(indexFirstDeck,4))) then
                cardSuit(6) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(22) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,11),eq(indexFirstDeck,11))) then
                cardSuit(11) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(12) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,9),eq(indexSecondDeck,12))) then
                cardSuit(9) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(1) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(17) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,7),eq(indexFirstDeck,8))) then
                cardSuit(7) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(4) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(1) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(5) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(6) := cardSuit(-1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(-5) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,11),eq(indexFirstDeck,6))) then
                cardSuit(11) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(17) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,4),eq(indexSecondDeck,8))) then
                cardSuit(4) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,11),eq(indexFirstDeck,4))) then
                cardSuit(11) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(8) := cardSuit(16)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(1) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,3),eq(indexSecondDeck,3))) then
                cardSuit(3) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,12),eq(indexSecondDeck,11))) then
                cardSuit(12) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,9),eq(indexFirstDeck,5))) then
                cardSuit(9) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(11) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(3) := cardSuit(15)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(9) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,2),eq(indexFirstDeck,6))) then
                cardSuit(2) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,9),eq(indexSecondDeck,11))) then
                cardSuit(9) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(9) := cardSuit(15)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,1),eq(indexFirstDeck,2))) then
                cardSuit(1) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(1) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(1) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(0) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,12),eq(indexSecondDeck,8))) then
                cardSuit(12) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,4),eq(indexFirstDeck,12))) then
                cardSuit(4) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(2) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(4) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(7) := cardSuit(-5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(4) := cardSuit(-2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(4) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(6) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(-1) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(13) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,4),eq(indexFirstDeck,1))) then
                cardSuit(4) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(19) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(3) := cardSuit(21)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(12) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(1) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(-8) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(4) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,10),eq(indexSecondDeck,8))) then
                cardSuit(10) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(4) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(8) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(1) := cardSuit(21)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,3),eq(indexFirstDeck,2))) then
                cardSuit(3) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,10),eq(indexFirstDeck,1))) then
                cardSuit(10) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,7),eq(indexFirstDeck,12))) then
                cardSuit(7) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(2) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,9),eq(indexFirstDeck,3))) then
                cardSuit(9) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(8) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,3),eq(indexFirstDeck,7))) then
                cardSuit(3) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,12),eq(indexFirstDeck,10))) then
                cardSuit(12) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(-2) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(1) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(9) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,3),eq(indexSecondDeck,11))) then
                cardSuit(3) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(5) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,7),eq(indexSecondDeck,5))) then
                cardSuit(7) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(9) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,3),eq(indexFirstDeck,4))) then
                cardSuit(3) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,3),eq(indexSecondDeck,10))) then
                cardSuit(3) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(18) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,10),eq(indexSecondDeck,12))) then
                cardSuit(10) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,10),eq(indexSecondDeck,7))) then
                cardSuit(10) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(10) := cardSuit(-6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(13) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,12),eq(indexFirstDeck,9))) then
                cardSuit(12) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,6),eq(indexSecondDeck,10))) then
                cardSuit(6) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(8) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,9),eq(indexFirstDeck,7))) then
                cardSuit(9) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(-1) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(14) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(16) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,9),eq(indexSecondDeck,1))) then
                cardSuit(9) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(2) := cardSuit(17)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,11),eq(indexSecondDeck,10))) then
                cardSuit(11) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(9) := cardSuit(-7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,11),eq(indexSecondDeck,6))) then
                cardSuit(11) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,11),eq(indexFirstDeck,9))) then
                cardSuit(11) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,12),eq(indexSecondDeck,3))) then
                cardSuit(12) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(1) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(12) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(7) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(5) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(8) := cardSuit(-4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(11) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(11) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(5) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(14) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,8),eq(indexSecondDeck,4))) then
                cardSuit(8) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(14) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(4) := cardSuit(17)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(-3) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,7),eq(indexSecondDeck,3))) then
                cardSuit(7) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(1) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,11),eq(indexSecondDeck,9))) then
                cardSuit(11) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,1),eq(indexSecondDeck,6))) then
                cardSuit(1) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(2) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(3) := cardSuit(22)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,8),eq(indexSecondDeck,7))) then
                cardSuit(8) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,10),eq(indexFirstDeck,11))) then
                cardSuit(10) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(12) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(15) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(-5) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,4),eq(indexSecondDeck,4))) then
                cardSuit(4) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,2),eq(indexSecondDeck,7))) then
                cardSuit(2) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,8),eq(indexSecondDeck,9))) then
                cardSuit(8) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,8),eq(indexFirstDeck,8))) then
                cardSuit(8) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(13) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,10),eq(indexSecondDeck,11))) then
                cardSuit(10) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(10) := cardSuit(0)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(9) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(8) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(3) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(11) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(2) := cardSuit(13)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(10) := cardSuit(-3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(8) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,5),eq(indexFirstDeck,12))) then
                cardSuit(5) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(6) := cardSuit(10)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,8),eq(indexSecondDeck,3))) then
                cardSuit(8) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(10) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(15) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(2) := cardSuit(21)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,7),eq(indexFirstDeck,6))) then
                cardSuit(7) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(23) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(13) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(2) := cardSuit(18)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(20) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(12) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(6) := cardSuit(18)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(-4) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(12) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(12) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(3) := cardSuit(14)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,7),eq(indexSecondDeck,4))) then
                cardSuit(7) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(9) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,8),eq(indexSecondDeck,11))) then
                cardSuit(8) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(11) := cardSuit(-1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(11) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(18) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,3),eq(indexFirstDeck,11))) then
                cardSuit(3) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(11) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(2) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(-2) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(4) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(6) := cardSuit(-3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(7) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,12),eq(indexFirstDeck,5))) then
                cardSuit(12) := cardSuit(5)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,4),eq(indexFirstDeck,11))) then
                cardSuit(4) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(18) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,6),eq(indexFirstDeck,2))) then
                cardSuit(6) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(7) := cardSuit(14)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,4),eq(indexSecondDeck,2))) then
                cardSuit(4) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(9) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(3) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(0) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,5),eq(indexFirstDeck,1))) then
                cardSuit(5) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(6) := cardSuit(17)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(12) := cardSuit(0)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(0) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(-5) := cardSuit(10)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,9),eq(indexFirstDeck,12))) then
                cardSuit(9) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(7) := cardSuit(16)
            endif
            if and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(lt(free,12))) then
                free := undef
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,10),eq(indexFirstDeck,10))) then
                cardSuit(10) := cardSuit(10)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,11),eq(indexFirstDeck,12))) then
                cardSuit(11) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(2) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(8) := cardSuit(0)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,8),eq(indexSecondDeck,8))) then
                cardSuit(8) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,11),eq(indexSecondDeck,5))) then
                cardSuit(11) := cardSuit(5)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(ge(8,free),le(8,minus(indexFirstDeck,1)))) then
                cardSuit(9) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(1) := cardSuit(16)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,11),eq(indexFirstDeck,8))) then
                cardSuit(11) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(3) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(-1) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(5) := cardSuit(18)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,3),eq(indexSecondDeck,12))) then
                cardSuit(3) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(12) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,7),eq(indexSecondDeck,11))) then
                cardSuit(7) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(9) := cardSuit(5)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,11),eq(indexFirstDeck,1))) then
                cardSuit(11) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(8) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,10),eq(indexFirstDeck,5))) then
                cardSuit(10) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(7) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(10) := cardSuit(13)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,3),eq(indexSecondDeck,4))) then
                cardSuit(3) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(8) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(6) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(-6) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(5) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,4),eq(indexFirstDeck,2))) then
                cardSuit(4) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,10),eq(indexSecondDeck,5))) then
                cardSuit(10) := cardSuit(5)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,1),eq(indexFirstDeck,9))) then
                cardSuit(1) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(1) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(2) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,8),eq(indexFirstDeck,5))) then
                cardSuit(8) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(4) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(15) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(13) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,3),eq(indexFirstDeck,12))) then
                cardSuit(3) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(12) := cardSuit(-1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(8) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(3) := cardSuit(5)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,5),eq(indexFirstDeck,10))) then
                cardSuit(5) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(19) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,8),eq(indexFirstDeck,10))) then
                cardSuit(8) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(4) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,7),eq(indexFirstDeck,11))) then
                cardSuit(7) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(12) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(3) := cardSuit(17)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(12) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,1),eq(indexSecondDeck,9))) then
                cardSuit(1) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,12),eq(indexSecondDeck,7))) then
                cardSuit(12) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(-4) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(2) := cardSuit(19)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(11) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,10),eq(indexSecondDeck,9))) then
                cardSuit(10) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,3),eq(indexSecondDeck,7))) then
                cardSuit(3) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,9),eq(indexFirstDeck,8))) then
                cardSuit(9) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,5),eq(indexSecondDeck,1))) then
                cardSuit(5) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,1),eq(indexSecondDeck,5))) then
                cardSuit(1) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(-3) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(17) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,11),eq(indexFirstDeck,7))) then
                cardSuit(11) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(7) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(-5) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,4),eq(indexFirstDeck,9))) then
                cardSuit(4) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(7) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(1) := cardSuit(14)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(ge(11,free),le(11,minus(indexFirstDeck,1)))) then
                cardSuit(12) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(4) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(11) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(16) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(10) := cardSuit(-1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,3),eq(indexSecondDeck,1))) then
                cardSuit(3) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,3),eq(indexSecondDeck,5))) then
                cardSuit(3) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(8) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(6) := cardSuit(5)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,9),eq(indexSecondDeck,10))) then
                cardSuit(9) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(21) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,5),eq(indexFirstDeck,8))) then
                cardSuit(5) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(ge(1,free),le(1,minus(indexFirstDeck,1)))) then
                cardSuit(2) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(-1) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(10) := cardSuit(-7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(10) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(6) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,1),eq(indexSecondDeck,1))) then
                cardSuit(1) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(5) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(11) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(5) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(19) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,6),eq(indexFirstDeck,7))) then
                cardSuit(6) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(6) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,4),eq(indexSecondDeck,11))) then
                cardSuit(4) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,2),eq(indexSecondDeck,6))) then
                cardSuit(2) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(8) := cardSuit(-3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(9) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,11),eq(indexFirstDeck,5))) then
                cardSuit(11) := cardSuit(5)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,6),eq(indexFirstDeck,8))) then
                cardSuit(6) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(12) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,8),eq(indexSecondDeck,1))) then
                cardSuit(8) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(9) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(4) := cardSuit(15)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(6) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,2),eq(indexSecondDeck,12))) then
                cardSuit(2) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,7),eq(indexFirstDeck,7))) then
                cardSuit(7) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(-6) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(12) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(2) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(9) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,10),eq(indexSecondDeck,4))) then
                cardSuit(10) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,8),eq(indexFirstDeck,3))) then
                cardSuit(8) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,1),eq(indexFirstDeck,3))) then
                cardSuit(1) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,6),eq(indexSecondDeck,8))) then
                cardSuit(6) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(16) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(11) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(15) := cardSuit(10)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,5),eq(indexSecondDeck,4))) then
                cardSuit(5) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(8) := cardSuit(13)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(7) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(23) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(5) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(10) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(3) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(6) := cardSuit(0)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,12),eq(indexFirstDeck,4))) then
                cardSuit(12) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,8),eq(indexSecondDeck,10))) then
                cardSuit(8) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(9) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,4),eq(indexSecondDeck,9))) then
                cardSuit(4) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(16) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(9) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,1),eq(indexFirstDeck,12))) then
                cardSuit(1) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(11) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(10) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(13) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(13) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(ge(3,free),le(3,minus(indexFirstDeck,1)))) then
                cardSuit(4) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(5) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(ge(8,free),le(8,minus(indexSecondDeck,1)))) then
                cardSuit(9) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(5) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(4) := cardSuit(19)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(2) := cardSuit(14)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,2),eq(indexSecondDeck,4))) then
                cardSuit(2) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(ge(4,free),le(4,minus(indexSecondDeck,1)))) then
                cardSuit(5) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(10) := cardSuit(14)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,6),eq(indexSecondDeck,4))) then
                cardSuit(6) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(20) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,5),eq(indexSecondDeck,6))) then
                cardSuit(5) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,5),eq(indexFirstDeck,4))) then
                cardSuit(5) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),not(lt(indexFirstDeck,cut))) then
                indexFirstDeck := undef
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,10),eq(indexFirstDeck,4))) then
                cardSuit(10) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(12) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,7),eq(indexSecondDeck,1))) then
                cardSuit(7) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(ge(7,free),le(7,minus(indexFirstDeck,1)))) then
                cardSuit(8) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(0) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,1),eq(indexSecondDeck,7))) then
                cardSuit(1) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(9) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,11),eq(indexFirstDeck,10))) then
                cardSuit(11) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(2) := cardSuit(15)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(6) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,1),eq(indexFirstDeck,6))) then
                cardSuit(1) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(3) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,9),eq(indexFirstDeck,10))) then
                cardSuit(9) := cardSuit(10)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,3),eq(indexSecondDeck,9))) then
                cardSuit(3) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(3) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(10) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(7) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,9),eq(indexFirstDeck,11))) then
                cardSuit(9) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,9),eq(indexFirstDeck,9))) then
                cardSuit(9) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(12) := cardSuit(-10)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,3),eq(indexFirstDeck,3))) then
                cardSuit(3) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,4),eq(indexSecondDeck,3))) then
                cardSuit(4) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(0) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(2) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(-7) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(2) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(10) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(6) := cardSuit(-2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(10) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(6) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(-7) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(2) := cardSuit(4)
            endif
            if and(and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),isDef(indexFirstDeck)),lt(indexFirstDeck,cut)) then
                indexFirstDeck := plus(indexFirstDeck,1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(4) := cardSuit(16)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(2) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(12) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(3) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(9) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(-2) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,8),eq(indexFirstDeck,2))) then
                cardSuit(8) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,6),eq(indexFirstDeck,1))) then
                cardSuit(6) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,2),eq(indexFirstDeck,8))) then
                cardSuit(2) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(5) := cardSuit(17)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(8) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(8) := cardSuit(17)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(5) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(3) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(12) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(17) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,12),eq(indexSecondDeck,4))) then
                cardSuit(12) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(ge(2,free),le(2,minus(indexFirstDeck,1)))) then
                cardSuit(3) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(10) := cardSuit(5)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,6),eq(indexSecondDeck,2))) then
                cardSuit(6) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,1),eq(indexSecondDeck,12))) then
                cardSuit(1) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,10),eq(indexFirstDeck,9))) then
                cardSuit(10) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(3) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(17) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,9),eq(indexFirstDeck,2))) then
                cardSuit(9) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,3),eq(indexSecondDeck,8))) then
                cardSuit(3) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(9) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(5) := cardSuit(-1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,12),eq(indexFirstDeck,3))) then
                cardSuit(12) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(4) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(8) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,5),eq(indexFirstDeck,6))) then
                cardSuit(5) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(6) := cardSuit(13)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,12),eq(indexFirstDeck,6))) then
                cardSuit(12) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,8),eq(indexFirstDeck,9))) then
                cardSuit(8) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(5) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,11),eq(indexFirstDeck,3))) then
                cardSuit(11) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,1),eq(indexFirstDeck,7))) then
                cardSuit(1) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(16) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(7) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,10),eq(indexFirstDeck,12))) then
                cardSuit(10) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,7),eq(indexFirstDeck,5))) then
                cardSuit(7) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,12)) then
                cardSuit(10) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(3) := cardSuit(-1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(2) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(9) := cardSuit(-4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(0) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(11) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(4) := cardSuit(14)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(20) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,5)) then
                cardSuit(13) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(2) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,5)) then
                cardSuit(8) := cardSuit(-2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(10) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,7),eq(indexSecondDeck,6))) then
                cardSuit(7) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(1) := cardSuit(22)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(3,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(4) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(9) := cardSuit(-6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(9) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(7,plus(chooseVar0,1)),le(7,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(7) := cardSuit(13)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(19) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,10)) then
                cardSuit(18) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(18) := cardSuit(1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,5),eq(indexSecondDeck,9))) then
                cardSuit(5) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(ge(5,free),le(5,minus(indexFirstDeck,1)))) then
                cardSuit(6) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(2,plus(chooseVar0,1)),le(2,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(13) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(12) := cardSuit(-3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(7) := cardSuit(-3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,12),eq(indexFirstDeck,2))) then
                cardSuit(12) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(10) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,1),eq(indexFirstDeck,1))) then
                cardSuit(1) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,3)) then
                cardSuit(10) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,6),eq(indexSecondDeck,11))) then
                cardSuit(6) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(3) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,1),eq(indexFirstDeck,10))) then
                cardSuit(1) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(10,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(10) := cardSuit(-8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,11),eq(indexSecondDeck,4))) then
                cardSuit(11) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(9) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,7),eq(indexSecondDeck,9))) then
                cardSuit(7) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,6),eq(indexFirstDeck,3))) then
                cardSuit(6) := cardSuit(3)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,6),eq(indexSecondDeck,7))) then
                cardSuit(6) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(3,plus(chooseVar0,1)),le(3,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,9)) then
                cardSuit(3) := cardSuit(19)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(12) := cardSuit(-9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,2),eq(indexFirstDeck,7))) then
                cardSuit(2) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,4),eq(indexSecondDeck,6))) then
                cardSuit(4) := cardSuit(6)
            endif
            if and(and(eq(SHUFFLE,step),isDef(chooseVar2)),lt(free,12)) then
                free := plus(free,1)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,5),eq(indexSecondDeck,12))) then
                cardSuit(5) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(7) := cardSuit(-2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,4),eq(indexFirstDeck,4))) then
                cardSuit(4) := cardSuit(4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(5) := cardSuit(14)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(11) := cardSuit(-8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,8),eq(indexSecondDeck,2))) then
                cardSuit(8) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,9),eq(indexSecondDeck,7))) then
                cardSuit(9) := cardSuit(7)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,1)) then
                cardSuit(1) := cardSuit(13)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(4) := cardSuit(0)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(9,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(3) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(ge(12,free),le(12,minus(indexFirstDeck,1)))) then
                cardSuit(13) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,10),eq(indexFirstDeck,6))) then
                cardSuit(10) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(5) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(-3) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,7),eq(indexSecondDeck,10))) then
                cardSuit(7) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(6,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(6) := cardSuit(3)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,7)) then
                cardSuit(1) := cardSuit(19)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,11),eq(indexFirstDeck,2))) then
                cardSuit(11) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,9)) then
                cardSuit(-1) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(4,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,1)) then
                cardSuit(-2) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,5),eq(indexFirstDeck,2))) then
                cardSuit(5) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(-3) := cardSuit(12)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,12),eq(indexFirstDeck,8))) then
                cardSuit(12) := cardSuit(8)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,8),eq(indexFirstDeck,12))) then
                cardSuit(8) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(14) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(7,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(7) := cardSuit(5)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,8),eq(indexFirstDeck,7))) then
                cardSuit(8) := cardSuit(7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,2),eq(indexSecondDeck,2))) then
                cardSuit(2) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,4)) then
                cardSuit(9) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,2)) then
                cardSuit(1) := cardSuit(2)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(8,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,10)) then
                cardSuit(3) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(9,plus(chooseVar0,1)),le(9,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(12) := cardSuit(9)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(5,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(5) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,1),eq(indexSecondDeck,8))) then
                cardSuit(1) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(8,plus(chooseVar0,1)),le(8,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,11)) then
                cardSuit(16) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,6)) then
                cardSuit(11) := cardSuit(-4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,6),eq(indexFirstDeck,6))) then
                cardSuit(6) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(4,plus(chooseVar0,1)),le(4,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,2)) then
                cardSuit(4) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,3),eq(indexFirstDeck,6))) then
                cardSuit(3) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(1) := cardSuit(18)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,4),eq(indexSecondDeck,12))) then
                cardSuit(4) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(11) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(1,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,11)) then
                cardSuit(1) := cardSuit(11)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,7),eq(indexFirstDeck,10))) then
                cardSuit(7) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,3)) then
                cardSuit(-7) := cardSuit(11)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(5,plus(chooseVar0,1)),le(5,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,8)) then
                cardSuit(5) := cardSuit(16)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(6,plus(chooseVar0,1)),le(6,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(13) := cardSuit(6)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,10),eq(indexSecondDeck,10))) then
                cardSuit(10) := cardSuit(10)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(10,plus(chooseVar0,1)),le(10,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(10) := cardSuit(9)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,12),eq(indexSecondDeck,5))) then
                cardSuit(12) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(2,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,8)) then
                cardSuit(7) := cardSuit(2)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,4),eq(indexFirstDeck,5))) then
                cardSuit(4) := cardSuit(5)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(12) := cardSuit(-7)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,2),eq(indexFirstDeck,4))) then
                cardSuit(2) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),chooseVar2),and(eq(free,8),eq(indexFirstDeck,4))) then
                cardSuit(8) := cardSuit(4)
            endif
            if and(and(and(eq(SHUFFLE,step),isDef(chooseVar2)),not(chooseVar2)),and(eq(free,8),eq(indexSecondDeck,6))) then
                cardSuit(8) := cardSuit(6)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(11,plus(chooseVar0,1)),le(11,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(11) := cardSuit(8)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(1,plus(chooseVar0,1)),le(1,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,12)) then
                cardSuit(24) := cardSuit(1)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),not(chooseVar1)),and(ge(12,plus(chooseVar0,1)),le(12,plus(plus(chooseVar0,1),idiv(minus(12,plus(chooseVar0,1)),2))))),eq(chooseVar0,6)) then
                cardSuit(7) := cardSuit(12)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(12,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,7)) then
                cardSuit(12) := cardSuit(-4)
            endif
            if and(and(and(and(and(eq(CUTANDREVERSE,step),isDef(chooseVar0)),isDef(chooseVar1)),chooseVar1),le(11,plus(1,idiv(minus(chooseVar0,1),2)))),eq(chooseVar0,4)) then
                cardSuit(-6) := cardSuit(11)
            endif
        endpar

default init s0:
    function indexFirstDeck = 1
    function free = 1
    function step = CUTANDREVERSE
    function cardSuit($n in Index) = at({1->SPADES,2->HEARTS,3->CLUBS,4->DIAMONDS,5->SPADES,6->HEARTS,7->CLUBS,8->DIAMONDS,9->SPADES,10->HEARTS,11->CLUBS,12->DIAMONDS},$n)
