//applied flatteners: MCR FR ChR AR LR CaR NR 
asm Roulette_flat
import ./STDL/StandardLibrary
import ./STDL/CTLlibrary

signature:
    domain Number subsetof Integer
    domain Money subsetof Integer
    enum domain Color = {GREEN | RED | BLACK}

    controlled playerMoney: Money
    controlled bancoMoney: Money
    monitored chosenNumber: Number
    derived color: Number -> Color
    derived chooseVar0: Number

definitions:

    domain Money = {0,1,2,3,4,5,6,7,8,9,10}
    domain Number = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36}

    function color($n in Number) = if eq($n,0) then GREEN else if eq(mod($n,2),0) then RED else BLACK endif endif
    function chooseVar0 = chooseone({$i22 in Number| true : $i22})


    CTLSPEC ag(eq(plus(playerMoney,bancoMoney),10))
    CTLSPEC (forall $n in Money with ef(eq(playerMoney,$n)))
    CTLSPEC (forall $n in Money with ef(eq(bancoMoney,$n)))
    CTLSPEC ag(implies(and(eq(playerMoney,0),eq(bancoMoney,10)),ag(and(eq(playerMoney,0),eq(bancoMoney,10)))))
    CTLSPEC ag(implies(and(eq(playerMoney,9),eq(bancoMoney,1)),ag(and(eq(playerMoney,9),eq(bancoMoney,1)))))
    main rule r_Main =
        par
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,36))),not(eq(color(6),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,18))),eq(color(9),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,4))),eq(color(13),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,35))),not(eq(color(22),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,32))),eq(color(6),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,9))),eq(color(29),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,6))),eq(color(28),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,36))),not(eq(color(19),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,11))),not(eq(color(29),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,10))),not(eq(color(16),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,16))),not(eq(color(27),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,15))),eq(color(23),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,27))),not(eq(color(4),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,6))),not(eq(color(8),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,9))),not(eq(color(28),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,9))),not(eq(color(8),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,12))),eq(color(2),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,23))),eq(color(0),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,19))),eq(color(23),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,30))),not(eq(color(1),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,0))),eq(color(17),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,5))),not(eq(color(24),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,14))),not(eq(color(31),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,5))),not(eq(color(7),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,32))),not(eq(color(16),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,22))),eq(color(28),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,10))),not(eq(color(2),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,19))),eq(color(32),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,16))),not(eq(color(35),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,26))),eq(color(6),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,10))),eq(color(1),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,15))),not(eq(color(30),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,3))),not(eq(color(8),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,19))),not(eq(color(15),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,4))),eq(color(11),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,24))),not(eq(color(14),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,36))),eq(color(7),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,0))),eq(color(3),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,29))),not(eq(color(9),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,21))),not(eq(color(20),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,3))),eq(color(0),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,31))),eq(color(22),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,25))),eq(color(30),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,30))),not(eq(color(19),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,7))),not(eq(color(36),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,14))),eq(color(5),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,0))),not(eq(color(33),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,31))),not(eq(color(3),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,7))),not(eq(color(16),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,16))),eq(color(14),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,15))),not(eq(color(7),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,19))),not(eq(color(30),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,32))),eq(color(19),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,18))),not(eq(color(3),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,7))),eq(color(21),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,18))),not(eq(color(33),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,32))),not(eq(color(14),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,0))),eq(color(23),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,26))),not(eq(color(36),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,12))),eq(color(7),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,35))),not(eq(color(7),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,0))),eq(color(31),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,7))),eq(color(8),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,21))),eq(color(5),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,29))),eq(color(16),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,0))),not(eq(color(9),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,30))),not(eq(color(26),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,10))),eq(color(29),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,13))),eq(color(22),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,35))),eq(color(22),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,36))),not(eq(color(32),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,9))),not(eq(color(31),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,2))),eq(color(4),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,5))),not(eq(color(35),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,25))),not(eq(color(13),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,4))),eq(color(1),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,7))),eq(color(32),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,17))),not(eq(color(11),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,10))),not(eq(color(31),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,13))),eq(color(10),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,0))),not(eq(color(4),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,12))),eq(color(29),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,19))),not(eq(color(13),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,26))),not(eq(color(14),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,31))),eq(color(26),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,14))),not(eq(color(6),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,22))),eq(color(31),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,11))),not(eq(color(34),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,17))),eq(color(10),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,20))),not(eq(color(13),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,14))),not(eq(color(15),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,5))),eq(color(11),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,31))),not(eq(color(15),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,0))),not(eq(color(23),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,33))),eq(color(26),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,20))),not(eq(color(33),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,31))),eq(color(6),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,28))),eq(color(32),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,14))),eq(color(29),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,3))),not(eq(color(16),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,25))),not(eq(color(34),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,35))),eq(color(12),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,16))),not(eq(color(32),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,31))),not(eq(color(6),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,6))),not(eq(color(24),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,17))),eq(color(4),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,15))),eq(color(14),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,19))),eq(color(21),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,29))),eq(color(10),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,2))),eq(color(3),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,15))),eq(color(24),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,4))),not(eq(color(13),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,0))),not(eq(color(6),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,10))),not(eq(color(0),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,9))),eq(color(26),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,29))),not(eq(color(31),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,36))),eq(color(3),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,18))),eq(color(27),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,26))),eq(color(4),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,10))),eq(color(2),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,13))),not(eq(color(2),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,1))),not(eq(color(26),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,17))),eq(color(30),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,16))),eq(color(19),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,25))),eq(color(16),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,6))),not(eq(color(30),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,4))),not(eq(color(1),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,31))),eq(color(10),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,35))),eq(color(10),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,20))),not(eq(color(30),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,8))),eq(color(32),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,0))),not(eq(color(15),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,4))),eq(color(5),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,5))),eq(color(0),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,10))),not(eq(color(30),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,17))),not(eq(color(1),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,19))),eq(color(20),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,27))),eq(color(14),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,20))),eq(color(7),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,27))),not(eq(color(3),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,14))),eq(color(33),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,28))),not(eq(color(1),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,24))),not(eq(color(27),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,10))),not(eq(color(20),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,2))),not(eq(color(34),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,36))),not(eq(color(4),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,17))),eq(color(9),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,7))),not(eq(color(27),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,2))),eq(color(33),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,27))),eq(color(26),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,28))),not(eq(color(30),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,9))),not(eq(color(29),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,13))),eq(color(17),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,24))),not(eq(color(32),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,3))),eq(color(20),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,23))),eq(color(34),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,34))),eq(color(2),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,11))),not(eq(color(8),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,36))),eq(color(2),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,23))),eq(color(6),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,9))),not(eq(color(24),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,29))),not(eq(color(8),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,35))),eq(color(29),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,19))),not(eq(color(18),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,21))),eq(color(2),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,21))),eq(color(8),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,25))),eq(color(7),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,3))),eq(color(1),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,13))),not(eq(color(20),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,27))),eq(color(3),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,14))),not(eq(color(26),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,19))),eq(color(13),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,9))),eq(color(5),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,10))),not(eq(color(24),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,7))),not(eq(color(6),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,34))),not(eq(color(7),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,16))),not(eq(color(1),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,32))),not(eq(color(25),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,12))),eq(color(8),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,6))),not(eq(color(25),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,18))),eq(color(12),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,16))),not(eq(color(34),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,9))),eq(color(31),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,27))),not(eq(color(14),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,32))),eq(color(35),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,17))),eq(color(7),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,0))),eq(color(36),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,29))),eq(color(22),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,5))),not(eq(color(8),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,32))),eq(color(31),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,33))),eq(color(23),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,36))),not(eq(color(7),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,24))),eq(color(12),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,21))),not(eq(color(17),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,15))),not(eq(color(34),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,20))),eq(color(13),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,26))),not(eq(color(0),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,11))),eq(color(7),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,19))),not(eq(color(36),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,4))),not(eq(color(30),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,24))),eq(color(32),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,15))),not(eq(color(29),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,35))),not(eq(color(5),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,36))),eq(color(13),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,17))),eq(color(34),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,36))),eq(color(9),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,34))),not(eq(color(31),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,9))),not(eq(color(16),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,13))),eq(color(21),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,15))),not(eq(color(6),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,33))),not(eq(color(18),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,4))),eq(color(0),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,28))),not(eq(color(36),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,24))),eq(color(21),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,15))),not(eq(color(33),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,32))),not(eq(color(29),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,35))),eq(color(26),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,17))),eq(color(32),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,32))),eq(color(24),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,12))),eq(color(32),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,26))),eq(color(14),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,9))),eq(color(20),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,1))),eq(color(10),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,29))),eq(color(24),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,27))),eq(color(10),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,4))),eq(color(34),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,22))),not(eq(color(28),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,8))),eq(color(12),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,28))),eq(color(33),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,35))),eq(color(17),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,4))),not(eq(color(25),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,1))),not(eq(color(33),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,5))),eq(color(25),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,11))),eq(color(16),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,1))),not(eq(color(12),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,5))),eq(color(16),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,32))),not(eq(color(15),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,7))),not(eq(color(20),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,20))),not(eq(color(0),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,1))),eq(color(36),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,0))),eq(color(27),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,22))),not(eq(color(16),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,28))),eq(color(35),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,10))),not(eq(color(29),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,21))),not(eq(color(14),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,15))),not(eq(color(20),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,33))),eq(color(34),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,7))),not(eq(color(17),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,9))),not(eq(color(0),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,20))),not(eq(color(6),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,25))),eq(color(14),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,11))),not(eq(color(12),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,19))),eq(color(27),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,32))),not(eq(color(8),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,24))),not(eq(color(4),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,9))),not(eq(color(11),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,32))),eq(color(3),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,34))),not(eq(color(29),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,3))),eq(color(11),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,11))),eq(color(28),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,15))),eq(color(17),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,19))),eq(color(0),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,26))),eq(color(18),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,11))),eq(color(24),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,10))),not(eq(color(17),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,13))),eq(color(32),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,17))),not(eq(color(30),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,13))),not(eq(color(23),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,36))),not(eq(color(9),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,27))),not(eq(color(33),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,36))),not(eq(color(15),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,23))),eq(color(24),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,32))),eq(color(21),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,23))),not(eq(color(33),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,18))),eq(color(10),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,1))),eq(color(16),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,26))),not(eq(color(12),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,10))),eq(color(5),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,8))),not(eq(color(10),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,30))),not(eq(color(24),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,10))),not(eq(color(18),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,7))),not(eq(color(18),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,8))),eq(color(9),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,35))),not(eq(color(12),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,25))),not(eq(color(15),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,31))),eq(color(4),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,28))),eq(color(15),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,9))),eq(color(18),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,33))),eq(color(32),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,0))),not(eq(color(26),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,12))),eq(color(10),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,31))),eq(color(11),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,20))),eq(color(11),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,26))),not(eq(color(19),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,7))),eq(color(11),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,23))),not(eq(color(8),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,14))),eq(color(21),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,6))),eq(color(31),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,8))),eq(color(30),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,34))),eq(color(25),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,3))),not(eq(color(11),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,6))),eq(color(29),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,35))),eq(color(8),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,14))),not(eq(color(32),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,30))),eq(color(18),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,11))),not(eq(color(6),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,33))),eq(color(8),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,9))),eq(color(34),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,27))),eq(color(12),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,20))),not(eq(color(5),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,29))),eq(color(0),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,16))),eq(color(11),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,34))),eq(color(19),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,1))),eq(color(28),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,16))),eq(color(13),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,35))),not(eq(color(16),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,23))),eq(color(32),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,15))),not(eq(color(4),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,8))),not(eq(color(29),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,33))),not(eq(color(4),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,35))),not(eq(color(6),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,4))),not(eq(color(3),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,24))),not(eq(color(34),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,27))),not(eq(color(15),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,21))),not(eq(color(3),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,14))),eq(color(32),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,0))),not(eq(color(31),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,33))),eq(color(35),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,35))),not(eq(color(32),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,24))),not(eq(color(10),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,23))),eq(color(5),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,27))),not(eq(color(28),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,30))),eq(color(31),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,15))),not(eq(color(14),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,15))),not(eq(color(16),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,8))),eq(color(22),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,23))),not(eq(color(9),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,7))),not(eq(color(33),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,3))),eq(color(34),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,28))),eq(color(21),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,25))),eq(color(32),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,33))),not(eq(color(0),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,17))),not(eq(color(12),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,33))),not(eq(color(20),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,8))),eq(color(29),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,33))),not(eq(color(8),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,21))),not(eq(color(29),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,17))),not(eq(color(3),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,26))),not(eq(color(17),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,9))),eq(color(10),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,8))),eq(color(14),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,1))),not(eq(color(24),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,14))),eq(color(25),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,34))),eq(color(22),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,9))),not(eq(color(10),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,4))),eq(color(15),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,6))),not(eq(color(2),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,1))),eq(color(8),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,5))),eq(color(3),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,22))),not(eq(color(15),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,17))),not(eq(color(15),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,24))),eq(color(27),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,7))),not(eq(color(2),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,17))),not(eq(color(27),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,8))),not(eq(color(2),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,6))),not(eq(color(5),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,20))),eq(color(2),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,12))),eq(color(18),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,22))),not(eq(color(8),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,3))),eq(color(32),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,26))),eq(color(9),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,10))),eq(color(19),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,12))),eq(color(22),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,29))),eq(color(35),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,9))),not(eq(color(5),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,18))),not(eq(color(19),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,13))),not(eq(color(22),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,2))),eq(color(0),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,7))),eq(color(2),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,25))),not(eq(color(28),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,5))),not(eq(color(1),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,5))),eq(color(10),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,7))),not(eq(color(13),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,18))),eq(color(35),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,13))),eq(color(26),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,17))),not(eq(color(13),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,1))),not(eq(color(3),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,16))),eq(color(34),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,23))),not(eq(color(22),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,6))),eq(color(26),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,1))),eq(color(3),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,13))),eq(color(3),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,16))),not(eq(color(25),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,20))),eq(color(29),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,36))),eq(color(27),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,23))),eq(color(27),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,28))),eq(color(9),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,0))),eq(color(18),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,12))),eq(color(30),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,34))),eq(color(15),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,31))),eq(color(17),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,24))),eq(color(16),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,3))),not(eq(color(30),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,14))),eq(color(8),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,3))),eq(color(14),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,26))),not(eq(color(15),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,29))),not(eq(color(21),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,31))),not(eq(color(28),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,6))),not(eq(color(21),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,14))),not(eq(color(4),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,3))),eq(color(24),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,19))),not(eq(color(28),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,31))),not(eq(color(25),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,10))),eq(color(6),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,21))),eq(color(30),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,3))),eq(color(36),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,21))),not(eq(color(28),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,30))),not(eq(color(35),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,31))),not(eq(color(2),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,35))),not(eq(color(28),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,18))),not(eq(color(23),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,17))),not(eq(color(34),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,7))),eq(color(23),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,13))),eq(color(5),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,1))),not(eq(color(15),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,5))),eq(color(28),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,8))),not(eq(color(32),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,9))),not(eq(color(33),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,11))),eq(color(18),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,13))),eq(color(27),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,26))),not(eq(color(9),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,29))),eq(color(19),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,28))),not(eq(color(14),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,34))),eq(color(17),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,26))),not(eq(color(11),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,7))),not(eq(color(29),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,31))),not(eq(color(5),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,1))),not(eq(color(34),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,19))),not(eq(color(35),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,19))),eq(color(12),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,26))),eq(color(29),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,34))),not(eq(color(30),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,34))),eq(color(33),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,12))),eq(color(19),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,13))),eq(color(24),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,17))),eq(color(27),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,28))),not(eq(color(18),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,17))),eq(color(21),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,15))),eq(color(33),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,9))),eq(color(32),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,32))),eq(color(12),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,13))),not(eq(color(35),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,22))),eq(color(21),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,24))),eq(color(20),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,33))),not(eq(color(31),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,28))),eq(color(7),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,31))),not(eq(color(21),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,4))),not(eq(color(34),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,30))),not(eq(color(10),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,32))),eq(color(27),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,6))),eq(color(36),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,13))),eq(color(14),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,13))),eq(color(7),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,20))),not(eq(color(21),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,19))),eq(color(1),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,12))),not(eq(color(11),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,26))),not(eq(color(5),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,13))),eq(color(34),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,6))),eq(color(3),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,9))),not(eq(color(27),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,36))),not(eq(color(0),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,29))),not(eq(color(35),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,35))),eq(color(2),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,30))),eq(color(8),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,29))),eq(color(31),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,14))),eq(color(6),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,19))),not(eq(color(8),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,23))),not(eq(color(34),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,30))),eq(color(5),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,21))),eq(color(14),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,11))),eq(color(27),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,35))),eq(color(0),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,0))),eq(color(9),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,21))),not(eq(color(5),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,8))),not(eq(color(35),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,26))),not(eq(color(34),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,7))),eq(color(25),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,7))),eq(color(34),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,29))),eq(color(2),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,5))),not(eq(color(11),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,29))),eq(color(36),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,14))),eq(color(30),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,33))),not(eq(color(24),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,15))),not(eq(color(31),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,6))),not(eq(color(22),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,31))),eq(color(5),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,36))),eq(color(28),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,4))),not(eq(color(10),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,27))),not(eq(color(10),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,8))),not(eq(color(1),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,11))),eq(color(26),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,18))),not(eq(color(16),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,5))),eq(color(9),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,28))),eq(color(23),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,3))),eq(color(5),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,14))),not(eq(color(35),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,14))),eq(color(27),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,14))),eq(color(0),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,33))),not(eq(color(5),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,24))),not(eq(color(30),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,3))),eq(color(19),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,0))),not(eq(color(8),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,17))),not(eq(color(6),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,24))),eq(color(6),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,1))),not(eq(color(21),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,16))),not(eq(color(20),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,8))),eq(color(0),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,6))),eq(color(11),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,7))),not(eq(color(11),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,2))),eq(color(13),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,31))),not(eq(color(32),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,34))),eq(color(20),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,20))),eq(color(14),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,30))),not(eq(color(28),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,23))),not(eq(color(14),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,0))),not(eq(color(17),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,35))),not(eq(color(13),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,16))),eq(color(35),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,26))),eq(color(8),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,20))),eq(color(26),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,13))),eq(color(16),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,16))),eq(color(7),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,14))),eq(color(13),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,14))),not(eq(color(28),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,33))),eq(color(25),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,15))),eq(color(5),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,27))),not(eq(color(12),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,4))),eq(color(9),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,36))),not(eq(color(3),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,34))),not(eq(color(4),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,23))),eq(color(31),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,21))),not(eq(color(22),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,1))),eq(color(11),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,21))),eq(color(4),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,7))),not(eq(color(15),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,15))),eq(color(30),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,17))),eq(color(0),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,30))),eq(color(24),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,36))),eq(color(21),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,19))),eq(color(8),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,36))),eq(color(16),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,15))),eq(color(32),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,30))),eq(color(27),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,26))),eq(color(0),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,2))),not(eq(color(15),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,16))),not(eq(color(8),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,25))),eq(color(22),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,32))),not(eq(color(31),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,12))),not(eq(color(33),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,28))),not(eq(color(35),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,8))),eq(color(10),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,35))),not(eq(color(18),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,31))),not(eq(color(12),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,21))),eq(color(25),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,15))),not(eq(color(1),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,35))),not(eq(color(11),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,19))),not(eq(color(21),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,10))),eq(color(34),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,12))),eq(color(28),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,32))),not(eq(color(33),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,13))),eq(color(1),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,25))),eq(color(24),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,5))),eq(color(21),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,25))),eq(color(33),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,33))),eq(color(11),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,8))),not(eq(color(3),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,29))),not(eq(color(22),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,27))),eq(color(31),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,3))),eq(color(33),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,34))),eq(color(30),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,7))),eq(color(33),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,9))),not(eq(color(17),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,36))),not(eq(color(30),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,23))),not(eq(color(6),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,19))),not(eq(color(22),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,9))),eq(color(14),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,17))),not(eq(color(4),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,29))),not(eq(color(23),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,22))),eq(color(29),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,19))),not(eq(color(16),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,12))),eq(color(20),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,25))),eq(color(17),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,1))),eq(color(31),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,21))),eq(color(28),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,25))),not(eq(color(7),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,34))),eq(color(21),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,3))),eq(color(22),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,34))),eq(color(11),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,2))),eq(color(10),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,6))),eq(color(4),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,7))),eq(color(9),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,0))),eq(color(29),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,5))),eq(color(19),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,2))),not(eq(color(1),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,26))),not(eq(color(33),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,2))),not(eq(color(20),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,15))),not(eq(color(12),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,19))),eq(color(2),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,2))),not(eq(color(33),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,12))),eq(color(5),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,6))),eq(color(2),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,8))),eq(color(24),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,0))),eq(color(32),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,18))),not(eq(color(26),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,4))),eq(color(3),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,7))),eq(color(30),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,31))),eq(color(35),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,26))),not(eq(color(18),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,31))),eq(color(28),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,8))),eq(color(33),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,19))),not(eq(color(10),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,32))),not(eq(color(28),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,30))),not(eq(color(16),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,8))),not(eq(color(30),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,32))),eq(color(0),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,1))),not(eq(color(23),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,6))),not(eq(color(7),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,21))),eq(color(6),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,0))),not(eq(color(1),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,11))),eq(color(31),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,5))),not(eq(color(6),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,26))),not(eq(color(8),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,34))),eq(color(1),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,16))),eq(color(25),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,11))),eq(color(3),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,22))),eq(color(6),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,17))),eq(color(29),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,27))),eq(color(11),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,13))),not(eq(color(1),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,21))),not(eq(color(12),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,29))),eq(color(18),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,21))),not(eq(color(24),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,32))),eq(color(13),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,26))),eq(color(22),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,20))),eq(color(34),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,11))),eq(color(29),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,25))),eq(color(29),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,21))),not(eq(color(2),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,11))),not(eq(color(24),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,5))),eq(color(33),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,12))),not(eq(color(25),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,7))),not(eq(color(28),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,11))),eq(color(10),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,18))),eq(color(28),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,5))),not(eq(color(20),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,19))),eq(color(30),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,1))),not(eq(color(13),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,21))),not(eq(color(19),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,6))),eq(color(20),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,24))),eq(color(8),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,30))),eq(color(29),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,20))),eq(color(19),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,33))),eq(color(4),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,19))),not(eq(color(29),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,13))),not(eq(color(33),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,5))),not(eq(color(23),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,21))),not(eq(color(6),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,34))),not(eq(color(5),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,19))),eq(color(28),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,27))),eq(color(36),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,3))),not(eq(color(34),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,28))),eq(color(26),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,18))),not(eq(color(29),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,4))),not(eq(color(31),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,0))),not(eq(color(30),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,32))),eq(color(14),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,9))),not(eq(color(26),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,4))),eq(color(8),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,5))),not(eq(color(30),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,7))),not(eq(color(30),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,14))),not(eq(color(5),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,13))),eq(color(23),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,30))),not(eq(color(0),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,11))),eq(color(19),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,9))),not(eq(color(14),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,25))),eq(color(4),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,3))),eq(color(17),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,10))),not(eq(color(5),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,26))),eq(color(24),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,25))),not(eq(color(27),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,18))),eq(color(7),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,33))),eq(color(7),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,8))),eq(color(13),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,34))),not(eq(color(32),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,2))),not(eq(color(12),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,21))),not(eq(color(36),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,24))),not(eq(color(9),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,16))),not(eq(color(0),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,10))),eq(color(26),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,33))),eq(color(21),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,11))),eq(color(21),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,25))),eq(color(5),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,10))),not(eq(color(21),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,4))),not(eq(color(14),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,23))),not(eq(color(13),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,23))),eq(color(35),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,32))),eq(color(9),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,15))),eq(color(25),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,5))),not(eq(color(17),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,16))),not(eq(color(15),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,27))),not(eq(color(19),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,21))),eq(color(36),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,12))),not(eq(color(9),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,34))),not(eq(color(25),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,11))),eq(color(33),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,31))),not(eq(color(29),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,2))),eq(color(36),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,29))),not(eq(color(24),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,9))),eq(color(6),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,33))),not(eq(color(1),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,22))),not(eq(color(32),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,27))),eq(color(33),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,1))),not(eq(color(5),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,26))),eq(color(12),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,10))),eq(color(12),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,19))),not(eq(color(31),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,13))),eq(color(9),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,23))),eq(color(16),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,30))),not(eq(color(14),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,1))),eq(color(27),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,29))),eq(color(25),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,29))),not(eq(color(18),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,4))),eq(color(23),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,5))),eq(color(7),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,29))),eq(color(4),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,15))),eq(color(29),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,15))),eq(color(1),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,28))),eq(color(18),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,3))),not(eq(color(9),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,15))),not(eq(color(19),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,36))),not(eq(color(26),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,7))),not(eq(color(5),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,34))),eq(color(6),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,26))),not(eq(color(22),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,29))),not(eq(color(19),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,1))),eq(color(21),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,12))),not(eq(color(34),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,28))),eq(color(14),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,16))),not(eq(color(10),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,26))),eq(color(25),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,6))),not(eq(color(16),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,24))),not(eq(color(6),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,2))),eq(color(28),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,14))),eq(color(10),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,4))),not(eq(color(12),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,1))),eq(color(13),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,27))),eq(color(32),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,16))),eq(color(33),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,4))),eq(color(32),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,18))),not(eq(color(7),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,18))),eq(color(16),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,35))),not(eq(color(1),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,7))),eq(color(3),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,6))),eq(color(32),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,10))),not(eq(color(33),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,16))),eq(color(32),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,16))),eq(color(36),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,11))),eq(color(13),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,28))),eq(color(1),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,11))),not(eq(color(22),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,15))),not(eq(color(25),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,34))),not(eq(color(21),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,2))),eq(color(20),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,14))),eq(color(3),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,18))),not(eq(color(34),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,12))),eq(color(11),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,5))),eq(color(2),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,12))),not(eq(color(3),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,7))),eq(color(5),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,27))),not(eq(color(30),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,5))),not(eq(color(13),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,27))),not(eq(color(22),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,22))),not(eq(color(0),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,12))),not(eq(color(22),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,32))),not(eq(color(6),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,29))),eq(color(9),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,32))),eq(color(17),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,11))),not(eq(color(13),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,14))),not(eq(color(1),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,25))),not(eq(color(3),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,29))),eq(color(13),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,2))),eq(color(19),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,28))),not(eq(color(29),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,1))),eq(color(2),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,15))),not(eq(color(13),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,30))),eq(color(35),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,0))),eq(color(6),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,17))),not(eq(color(36),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,21))),eq(color(16),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,15))),eq(color(10),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,19))),not(eq(color(12),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,7))),not(eq(color(23),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,22))),eq(color(36),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,4))),eq(color(29),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,8))),eq(color(11),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,1))),not(eq(color(2),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,17))),not(eq(color(33),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,30))),eq(color(17),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,21))),eq(color(23),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,36))),not(eq(color(20),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,20))),not(eq(color(34),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,16))),eq(color(10),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,32))),not(eq(color(27),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,0))),not(eq(color(36),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,2))),not(eq(color(27),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,27))),eq(color(18),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,23))),eq(color(20),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,11))),eq(color(32),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,22))),not(eq(color(5),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,21))),not(eq(color(26),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,9))),not(eq(color(34),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,18))),not(eq(color(4),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,28))),eq(color(27),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,23))),not(eq(color(26),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,21))),eq(color(1),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,0))),not(eq(color(24),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,8))),not(eq(color(13),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,10))),not(eq(color(9),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,3))),not(eq(color(10),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,25))),eq(color(15),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,28))),eq(color(5),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,29))),eq(color(26),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,1))),eq(color(9),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,7))),eq(color(22),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,11))),not(eq(color(15),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,14))),not(eq(color(30),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,23))),not(eq(color(30),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,5))),not(eq(color(19),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,0))),not(eq(color(12),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,3))),eq(color(35),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,10))),eq(color(36),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,30))),not(eq(color(23),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,10))),eq(color(33),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,8))),eq(color(5),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,25))),not(eq(color(2),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,29))),eq(color(7),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,0))),not(eq(color(32),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,30))),eq(color(13),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,31))),not(eq(color(35),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,9))),eq(color(8),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,15))),eq(color(16),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,35))),not(eq(color(21),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,35))),not(eq(color(27),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,18))),not(eq(color(20),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,11))),not(eq(color(7),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,0))),not(eq(color(3),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,26))),not(eq(color(32),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,20))),not(eq(color(27),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,28))),not(eq(color(25),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,21))),eq(color(24),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,29))),not(eq(color(13),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,11))),not(eq(color(25),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,23))),eq(color(21),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,31))),eq(color(19),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,23))),eq(color(11),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,24))),not(eq(color(25),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,34))),eq(color(35),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,18))),not(eq(color(5),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,15))),not(eq(color(32),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,6))),eq(color(22),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,4))),eq(color(30),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,11))),not(eq(color(31),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,17))),not(eq(color(35),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,31))),eq(color(9),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,1))),not(eq(color(11),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,0))),not(eq(color(35),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,24))),eq(color(2),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,29))),not(eq(color(16),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,36))),not(eq(color(17),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,35))),eq(color(19),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,13))),not(eq(color(3),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,12))),not(eq(color(8),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,10))),eq(color(27),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,36))),not(eq(color(28),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,16))),not(eq(color(28),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,5))),eq(color(12),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,17))),eq(color(3),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,21))),not(eq(color(0),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,35))),not(eq(color(25),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,14))),not(eq(color(7),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,4))),not(eq(color(22),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,8))),eq(color(2),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,26))),not(eq(color(28),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,23))),eq(color(13),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,10))),not(eq(color(15),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,32))),eq(color(2),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,27))),eq(color(7),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,34))),not(eq(color(6),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,18))),eq(color(14),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,36))),not(eq(color(31),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,22))),eq(color(30),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,2))),not(eq(color(13),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,9))),eq(color(17),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,28))),not(eq(color(15),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,34))),not(eq(color(1),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,19))),not(eq(color(17),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,10))),not(eq(color(8),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,25))),not(eq(color(16),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,6))),not(eq(color(27),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,20))),not(eq(color(1),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,16))),not(eq(color(18),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,6))),not(eq(color(19),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,27))),not(eq(color(9),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,36))),not(eq(color(22),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,4))),not(eq(color(5),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,9))),not(eq(color(4),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,0))),eq(color(25),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,13))),eq(color(8),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,25))),eq(color(26),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,24))),eq(color(30),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,35))),not(eq(color(3),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,30))),not(eq(color(33),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,22))),not(eq(color(14),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,27))),not(eq(color(18),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,25))),eq(color(35),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,35))),eq(color(36),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,19))),not(eq(color(4),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,25))),not(eq(color(11),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,34))),eq(color(8),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,5))),eq(color(4),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,24))),eq(color(35),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,15))),not(eq(color(35),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,14))),not(eq(color(20),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,3))),not(eq(color(21),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,9))),not(eq(color(1),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,22))),not(eq(color(36),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,9))),not(eq(color(32),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,14))),eq(color(31),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,13))),not(eq(color(14),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,33))),eq(color(18),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,2))),eq(color(25),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,5))),not(eq(color(2),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,33))),eq(color(27),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,13))),not(eq(color(8),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,35))),eq(color(25),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,11))),not(eq(color(2),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,26))),not(eq(color(25),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,17))),not(eq(color(31),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,1))),eq(color(29),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,2))),eq(color(17),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,17))),not(eq(color(8),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,31))),not(eq(color(7),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,35))),eq(color(34),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,32))),not(eq(color(12),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,31))),not(eq(color(22),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,8))),not(eq(color(22),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,8))),not(eq(color(4),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,8))),not(eq(color(9),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,2))),eq(color(1),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,20))),eq(color(28),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,23))),not(eq(color(4),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,5))),not(eq(color(15),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,19))),eq(color(25),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,8))),eq(color(17),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,32))),eq(color(29),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,12))),not(eq(color(10),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,4))),eq(color(24),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,32))),eq(color(5),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,0))),eq(color(14),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,6))),not(eq(color(1),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,12))),not(eq(color(21),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,14))),not(eq(color(13),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,4))),not(eq(color(9),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,21))),eq(color(26),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,34))),eq(color(10),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,4))),eq(color(18),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,30))),not(eq(color(11),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,24))),not(eq(color(31),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,35))),eq(color(33),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,30))),eq(color(14),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,36))),not(eq(color(10),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,28))),eq(color(8),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,4))),eq(color(36),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,31))),not(eq(color(20),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,20))),eq(color(8),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,25))),not(eq(color(8),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,36))),eq(color(1),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,35))),not(eq(color(0),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,2))),eq(color(12),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,16))),eq(color(27),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,1))),not(eq(color(0),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,27))),not(eq(color(16),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,5))),not(eq(color(31),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,20))),eq(color(4),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,26))),eq(color(1),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,21))),eq(color(32),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,24))),not(eq(color(11),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,10))),eq(color(3),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,14))),eq(color(16),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,33))),eq(color(2),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,29))),not(eq(color(1),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,12))),not(eq(color(6),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,32))),not(eq(color(35),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,12))),not(eq(color(28),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,4))),not(eq(color(27),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,35))),eq(color(7),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,34))),not(eq(color(20),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,20))),eq(color(23),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,26))),not(eq(color(27),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,36))),eq(color(8),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,6))),not(eq(color(9),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,1))),not(eq(color(32),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,29))),eq(color(34),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,2))),eq(color(24),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,1))),not(eq(color(9),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,36))),eq(color(11),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,27))),eq(color(25),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,27))),not(eq(color(25),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,31))),eq(color(33),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,26))),eq(color(11),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,2))),eq(color(35),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,12))),not(eq(color(36),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,33))),eq(color(36),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,3))),not(eq(color(31),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,23))),not(eq(color(7),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,12))),eq(color(3),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,24))),eq(color(28),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,27))),not(eq(color(24),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,15))),eq(color(34),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,21))),not(eq(color(30),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,25))),not(eq(color(12),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,22))),eq(color(8),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,34))),not(eq(color(12),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,20))),not(eq(color(24),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,36))),eq(color(29),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,8))),not(eq(color(7),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,23))),eq(color(3),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,6))),eq(color(33),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,5))),not(eq(color(10),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,24))),not(eq(color(19),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,9))),not(eq(color(30),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,12))),eq(color(4),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,26))),not(eq(color(6),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,29))),not(eq(color(32),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,1))),not(eq(color(10),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,5))),eq(color(6),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,30))),not(eq(color(31),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,25))),not(eq(color(22),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,13))),not(eq(color(34),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,15))),not(eq(color(11),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,21))),eq(color(19),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,11))),not(eq(color(0),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,21))),not(eq(color(13),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,33))),not(eq(color(10),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,34))),eq(color(0),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,33))),not(eq(color(30),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,8))),eq(color(16),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,36))),not(eq(color(27),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,33))),eq(color(5),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,1))),eq(color(22),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,23))),eq(color(15),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,22))),eq(color(1),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,1))),eq(color(15),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,3))),eq(color(2),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,12))),not(eq(color(13),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,12))),not(eq(color(14),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,17))),not(eq(color(22),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,5))),eq(color(22),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,19))),not(eq(color(32),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,22))),eq(color(10),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,2))),eq(color(18),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,32))),eq(color(26),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,27))),not(eq(color(1),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,30))),not(eq(color(18),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,5))),eq(color(17),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,29))),eq(color(20),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,32))),not(eq(color(34),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,26))),eq(color(3),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,31))),not(eq(color(10),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,24))),not(eq(color(36),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,0))),not(eq(color(25),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,13))),eq(color(11),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,9))),eq(color(27),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,4))),eq(color(21),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,16))),eq(color(31),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,6))),eq(color(23),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,1))),not(eq(color(20),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,26))),not(eq(color(4),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,28))),eq(color(22),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,23))),not(eq(color(16),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,2))),eq(color(34),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,3))),eq(color(28),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,4))),eq(color(33),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,7))),not(eq(color(22),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,10))),not(eq(color(25),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,33))),not(eq(color(19),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,13))),not(eq(color(32),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,6))),not(eq(color(31),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,22))),not(eq(color(35),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,34))),eq(color(28),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,14))),not(eq(color(9),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,15))),eq(color(26),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,22))),eq(color(20),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,10))),eq(color(20),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,28))),not(eq(color(19),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,5))),not(eq(color(21),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,30))),not(eq(color(36),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,17))),eq(color(19),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,11))),eq(color(2),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,30))),eq(color(19),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,17))),eq(color(20),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,22))),not(eq(color(23),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,12))),not(eq(color(16),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,8))),eq(color(15),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,17))),not(eq(color(0),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,28))),eq(color(2),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,31))),eq(color(16),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,11))),eq(color(8),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,34))),eq(color(23),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,15))),not(eq(color(8),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,32))),not(eq(color(5),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,29))),eq(color(3),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,6))),eq(color(13),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,33))),not(eq(color(28),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,29))),eq(color(28),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,29))),not(eq(color(33),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,17))),not(eq(color(24),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,13))),not(eq(color(5),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,17))),eq(color(13),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,25))),eq(color(6),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,33))),eq(color(12),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,7))),eq(color(26),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,32))),eq(color(18),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,3))),eq(color(26),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,35))),eq(color(20),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,9))),not(eq(color(3),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,2))),not(eq(color(23),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,23))),not(eq(color(12),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,2))),not(eq(color(9),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,32))),not(eq(color(10),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,15))),eq(color(36),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,27))),eq(color(28),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,22))),not(eq(color(26),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,25))),not(eq(color(10),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,34))),not(eq(color(18),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,8))),eq(color(36),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,12))),not(eq(color(15),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,24))),not(eq(color(3),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,7))),not(eq(color(1),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,23))),not(eq(color(27),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,27))),eq(color(15),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,32))),eq(color(16),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,29))),not(eq(color(27),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,3))),eq(color(18),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,26))),not(eq(color(16),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,31))),eq(color(32),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,10))),not(eq(color(3),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,32))),eq(color(20),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,3))),not(eq(color(22),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,5))),eq(color(20),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,22))),eq(color(25),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,1))),not(eq(color(30),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,10))),not(eq(color(12),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,12))),eq(color(34),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,11))),not(eq(color(9),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,30))),eq(color(36),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,15))),eq(color(7),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,20))),not(eq(color(23),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,17))),eq(color(12),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,35))),not(eq(color(9),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,13))),eq(color(28),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,27))),eq(color(20),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,32))),eq(color(4),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,17))),eq(color(6),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,15))),not(eq(color(23),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,21))),not(eq(color(15),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,0))),eq(color(30),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,20))),eq(color(32),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,12))),eq(color(6),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,2))),not(eq(color(7),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,2))),not(eq(color(14),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,30))),eq(color(22),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,26))),eq(color(7),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,8))),not(eq(color(24),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,0))),eq(color(24),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,3))),not(eq(color(32),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,11))),not(eq(color(21),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,18))),not(eq(color(14),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,25))),eq(color(3),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,35))),not(eq(color(2),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,18))),eq(color(13),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,1))),not(eq(color(36),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,7))),eq(color(19),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,19))),eq(color(35),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,31))),not(eq(color(33),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,20))),not(eq(color(32),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,10))),not(eq(color(13),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,25))),eq(color(0),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,28))),not(eq(color(9),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,18))),eq(color(29),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,9))),eq(color(1),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,1))),eq(color(25),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,21))),eq(color(9),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,29))),eq(color(21),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,28))),not(eq(color(34),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,15))),not(eq(color(0),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,33))),not(eq(color(17),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,25))),eq(color(31),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,13))),not(eq(color(18),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,15))),eq(color(3),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,6))),eq(color(25),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,2))),not(eq(color(3),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,4))),not(eq(color(29),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,30))),not(eq(color(17),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,18))),eq(color(33),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,10))),eq(color(24),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,10))),not(eq(color(28),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,18))),eq(color(19),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,16))),not(eq(color(9),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,5))),eq(color(24),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,19))),eq(color(15),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,36))),eq(color(35),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,22))),not(eq(color(21),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,0))),not(eq(color(7),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,26))),eq(color(17),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,23))),not(eq(color(10),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,35))),eq(color(23),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,8))),not(eq(color(15),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,24))),eq(color(3),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,3))),eq(color(31),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,35))),eq(color(13),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,17))),not(eq(color(18),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,4))),not(eq(color(26),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,32))),not(eq(color(13),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,1))),not(eq(color(6),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,7))),eq(color(35),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,4))),not(eq(color(33),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,32))),not(eq(color(18),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,4))),eq(color(16),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,10))),eq(color(28),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,30))),not(eq(color(6),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,24))),not(eq(color(0),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,23))),eq(color(9),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,6))),eq(color(8),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,15))),eq(color(28),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,36))),eq(color(18),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,12))),eq(color(13),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,25))),not(eq(color(33),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,13))),not(eq(color(10),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,8))),eq(color(31),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,18))),not(eq(color(8),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,12))),not(eq(color(23),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,20))),not(eq(color(7),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,33))),eq(color(15),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,31))),eq(color(25),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,0))),eq(color(34),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,10))),not(eq(color(6),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,5))),not(eq(color(29),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,12))),not(eq(color(2),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,13))),not(eq(color(4),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,22))),not(eq(color(6),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,2))),eq(color(8),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,22))),not(eq(color(11),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,12))),not(eq(color(31),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,4))),eq(color(22),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,36))),eq(color(19),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,8))),not(eq(color(14),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,22))),not(eq(color(12),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,29))),not(eq(color(5),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,6))),not(eq(color(26),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,34))),eq(color(3),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,1))),not(eq(color(35),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,1))),eq(color(7),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,4))),eq(color(14),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,21))),eq(color(15),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,32))),eq(color(33),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,21))),eq(color(34),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,33))),eq(color(24),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,33))),not(eq(color(36),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,16))),eq(color(29),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,30))),not(eq(color(27),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,25))),not(eq(color(31),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,21))),eq(color(17),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,26))),eq(color(27),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,5))),not(eq(color(33),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,25))),not(eq(color(18),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,5))),not(eq(color(25),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,8))),not(eq(color(5),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,11))),eq(color(0),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,14))),not(eq(color(12),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,8))),eq(color(6),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,10))),eq(color(16),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,6))),eq(color(35),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,9))),not(eq(color(22),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,0))),not(eq(color(20),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,1))),eq(color(30),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,0))),not(eq(color(11),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,6))),eq(color(10),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,14))),not(eq(color(21),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,15))),not(eq(color(22),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,26))),eq(color(31),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,28))),eq(color(19),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,36))),not(eq(color(5),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,11))),eq(color(34),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,4))),not(eq(color(35),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,9))),eq(color(28),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,21))),not(eq(color(1),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,17))),not(eq(color(5),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,18))),eq(color(32),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,30))),eq(color(11),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,31))),eq(color(2),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,34))),eq(color(31),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,29))),eq(color(5),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,4))),not(eq(color(24),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,0))),eq(color(21),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,12))),eq(color(33),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,29))),eq(color(33),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,28))),eq(color(17),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,3))),not(eq(color(13),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,20))),eq(color(16),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,7))),not(eq(color(12),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,12))),not(eq(color(17),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,22))),not(eq(color(9),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,23))),eq(color(8),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,13))),not(eq(color(17),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,27))),not(eq(color(17),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,14))),not(eq(color(0),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,30))),not(eq(color(22),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,10))),eq(color(22),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,7))),not(eq(color(3),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,13))),eq(color(35),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,27))),not(eq(color(35),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,36))),eq(color(5),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,36))),not(eq(color(23),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,10))),not(eq(color(1),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,31))),eq(color(14),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,4))),not(eq(color(15),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,9))),not(eq(color(19),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,1))),eq(color(35),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,5))),eq(color(27),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,7))),not(eq(color(31),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,30))),eq(color(21),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,23))),not(eq(color(35),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,5))),not(eq(color(4),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,11))),eq(color(4),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,21))),not(eq(color(23),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,36))),not(eq(color(25),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,2))),not(eq(color(25),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,18))),not(eq(color(28),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,32))),not(eq(color(1),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,8))),eq(color(26),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,0))),not(eq(color(5),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,20))),not(eq(color(26),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,9))),not(eq(color(25),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,17))),not(eq(color(28),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,31))),not(eq(color(27),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,29))),not(eq(color(2),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,7))),eq(color(24),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,10))),not(eq(color(26),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,6))),not(eq(color(4),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,3))),eq(color(13),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,20))),eq(color(31),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,23))),not(eq(color(15),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,29))),eq(color(11),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,15))),not(eq(color(10),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,15))),not(eq(color(18),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,23))),eq(color(1),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,30))),eq(color(10),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,0))),not(eq(color(34),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,23))),not(eq(color(2),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,6))),eq(color(27),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,13))),eq(color(20),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,16))),eq(color(8),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,34))),eq(color(36),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,24))),not(eq(color(1),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,31))),not(eq(color(11),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,31))),not(eq(color(8),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,24))),eq(color(25),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,13))),eq(color(31),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,33))),eq(color(22),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,31))),not(eq(color(36),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,1))),not(eq(color(18),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,5))),eq(color(26),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,3))),eq(color(9),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,3))),not(eq(color(6),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,9))),eq(color(0),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,25))),not(eq(color(20),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,34))),eq(color(27),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,34))),not(eq(color(3),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,25))),eq(color(21),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,5))),not(eq(color(3),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,10))),eq(color(4),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,17))),not(eq(color(9),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,23))),eq(color(17),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,33))),not(eq(color(21),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,24))),not(eq(color(29),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,4))),not(eq(color(20),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,32))),eq(color(22),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,5))),eq(color(36),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,6))),eq(color(12),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,30))),not(eq(color(2),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,25))),eq(color(2),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,34))),eq(color(14),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,28))),not(eq(color(10),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,13))),eq(color(2),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,11))),eq(color(14),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,21))),eq(color(3),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,17))),eq(color(33),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,22))),eq(color(35),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,15))),eq(color(18),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,22))),not(eq(color(18),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,23))),eq(color(25),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,6))),not(eq(color(17),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,18))),not(eq(color(27),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,18))),eq(color(31),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,21))),eq(color(29),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,19))),not(eq(color(2),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,29))),not(eq(color(34),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,24))),not(eq(color(2),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,7))),eq(color(1),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,20))),eq(color(25),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,11))),eq(color(36),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,28))),not(eq(color(0),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,30))),eq(color(6),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,8))),not(eq(color(17),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,8))),not(eq(color(11),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,1))),eq(color(0),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,31))),eq(color(24),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,19))),eq(color(3),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,31))),eq(color(21),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,30))),eq(color(25),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,31))),not(eq(color(18),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,19))),not(eq(color(6),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,20))),not(eq(color(19),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,20))),eq(color(17),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,15))),not(eq(color(27),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,32))),eq(color(36),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,20))),eq(color(22),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,20))),eq(color(6),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,18))),eq(color(3),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,16))),not(eq(color(31),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,19))),not(eq(color(11),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,28))),eq(color(6),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,29))),not(eq(color(7),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,11))),not(eq(color(23),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,22))),not(eq(color(2),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,33))),not(eq(color(27),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,24))),eq(color(10),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,14))),eq(color(36),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,25))),eq(color(23),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,31))),not(eq(color(26),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,31))),not(eq(color(17),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,26))),not(eq(color(21),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,15))),eq(color(35),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,16))),not(eq(color(26),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,7))),not(eq(color(14),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,25))),not(eq(color(24),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,8))),eq(color(1),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,22))),not(eq(color(25),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,9))),eq(color(24),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,25))),eq(color(28),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,26))),not(eq(color(20),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,6))),not(eq(color(0),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,24))),eq(color(0),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,33))),eq(color(28),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,26))),eq(color(35),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,33))),eq(color(10),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,16))),eq(color(26),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,10))),not(eq(color(36),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,33))),eq(color(16),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,14))),not(eq(color(8),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,11))),eq(color(35),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,33))),eq(color(1),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,14))),eq(color(1),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,20))),eq(color(24),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,15))),eq(color(0),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,36))),not(eq(color(21),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,8))),eq(color(21),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,13))),not(eq(color(16),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,6))),not(eq(color(23),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,17))),eq(color(15),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,24))),not(eq(color(20),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,35))),not(eq(color(26),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,19))),eq(color(17),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,13))),not(eq(color(27),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,33))),not(eq(color(14),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,6))),eq(color(15),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,29))),not(eq(color(4),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,2))),not(eq(color(18),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,33))),eq(color(30),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,2))),not(eq(color(19),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,13))),not(eq(color(25),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,31))),not(eq(color(0),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,8))),eq(color(19),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,15))),eq(color(12),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,7))),eq(color(0),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,31))),eq(color(12),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,30))),not(eq(color(13),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,16))),not(eq(color(24),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,3))),not(eq(color(2),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,28))),eq(color(3),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,35))),eq(color(21),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,23))),eq(color(4),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,29))),not(eq(color(17),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,19))),eq(color(29),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,30))),eq(color(34),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,14))),eq(color(11),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,29))),not(eq(color(15),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,5))),eq(color(31),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,8))),not(eq(color(18),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,15))),eq(color(31),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,25))),not(eq(color(35),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,29))),eq(color(14),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,3))),not(eq(color(26),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,8))),not(eq(color(16),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,35))),eq(color(18),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,33))),not(eq(color(25),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,23))),eq(color(30),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,31))),eq(color(3),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,36))),not(eq(color(16),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,33))),not(eq(color(6),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,19))),eq(color(4),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,4))),eq(color(2),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,17))),not(eq(color(23),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,19))),not(eq(color(3),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,24))),eq(color(1),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,24))),not(eq(color(17),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,28))),not(eq(color(32),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,0))),eq(color(20),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,12))),not(eq(color(19),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,6))),not(eq(color(18),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,0))),eq(color(11),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,9))),not(eq(color(21),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,25))),not(eq(color(30),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,2))),not(eq(color(0),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,27))),eq(color(35),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,2))),not(eq(color(5),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,0))),not(eq(color(29),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,1))),eq(color(33),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,28))),not(eq(color(21),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,17))),not(eq(color(26),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,3))),not(eq(color(5),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,10))),not(eq(color(11),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,17))),eq(color(35),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,36))),eq(color(23),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,14))),not(eq(color(24),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,20))),not(eq(color(18),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,6))),eq(color(1),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,3))),not(eq(color(0),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,29))),eq(color(8),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,24))),eq(color(33),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,10))),eq(color(30),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,0))),not(eq(color(21),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,14))),not(eq(color(23),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,32))),eq(color(28),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,27))),eq(color(21),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,14))),not(eq(color(18),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,24))),eq(color(19),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,10))),eq(color(9),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,30))),eq(color(23),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,29))),eq(color(1),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,36))),eq(color(22),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,2))),not(eq(color(10),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,16))),not(eq(color(14),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,31))),eq(color(23),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,12))),not(eq(color(1),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,3))),eq(color(6),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,5))),not(eq(color(9),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,18))),eq(color(30),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,30))),not(eq(color(20),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,16))),eq(color(15),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,36))),eq(color(17),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,6))),eq(color(34),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,8))),not(eq(color(36),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,26))),eq(color(36),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,28))),not(eq(color(12),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,9))),not(eq(color(7),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,36))),not(eq(color(13),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,13))),not(eq(color(11),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,6))),not(eq(color(20),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,34))),not(eq(color(17),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,31))),not(eq(color(24),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,21))),not(eq(color(16),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,28))),eq(color(36),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,13))),not(eq(color(24),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,34))),not(eq(color(26),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,16))),not(eq(color(3),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,15))),eq(color(27),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,8))),not(eq(color(26),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,15))),eq(color(13),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,0))),not(eq(color(27),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,29))),not(eq(color(6),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,22))),not(eq(color(24),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,7))),not(eq(color(19),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,30))),not(eq(color(8),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,19))),eq(color(5),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,15))),eq(color(21),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,8))),eq(color(28),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,29))),not(eq(color(20),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,19))),not(eq(color(26),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,5))),not(eq(color(22),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,16))),not(eq(color(22),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,33))),not(eq(color(7),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,19))),not(eq(color(0),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,24))),not(eq(color(35),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,29))),not(eq(color(26),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,4))),eq(color(10),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,22))),eq(color(0),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,35))),not(eq(color(15),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,26))),eq(color(21),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,11))),not(eq(color(1),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,30))),not(eq(color(34),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,30))),eq(color(16),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,5))),eq(color(35),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,28))),not(eq(color(22),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,5))),not(eq(color(16),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,36))),eq(color(33),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,22))),not(eq(color(33),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,10))),eq(color(15),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,5))),not(eq(color(26),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,28))),eq(color(12),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,33))),not(eq(color(3),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,20))),not(eq(color(28),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,2))),eq(color(23),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,31))),not(eq(color(19),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,14))),eq(color(24),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,1))),not(eq(color(27),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,15))),not(eq(color(5),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,20))),eq(color(10),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,11))),not(eq(color(3),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,14))),eq(color(15),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,9))),eq(color(33),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,13))),not(eq(color(9),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,6))),not(eq(color(34),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,18))),eq(color(5),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,8))),not(eq(color(19),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,36))),not(eq(color(35),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,25))),not(eq(color(17),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,17))),not(eq(color(7),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,2))),eq(color(22),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,11))),not(eq(color(16),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,5))),eq(color(8),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,0))),eq(color(28),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,0))),not(eq(color(2),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,30))),eq(color(28),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,8))),not(eq(color(23),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,2))),eq(color(27),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,18))),not(eq(color(6),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,3))),not(eq(color(36),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,3))),eq(color(4),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,7))),not(eq(color(21),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,18))),eq(color(23),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,35))),eq(color(5),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,33))),not(eq(color(29),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,32))),not(eq(color(7),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,10))),not(eq(color(32),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,31))),not(eq(color(9),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,4))),not(eq(color(16),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,5))),not(eq(color(18),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,17))),eq(color(11),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,26))),eq(color(2),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,18))),not(eq(color(11),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,33))),eq(color(14),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,1))),not(eq(color(7),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,18))),eq(color(15),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,20))),not(eq(color(35),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,33))),eq(color(19),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,28))),eq(color(34),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,32))),eq(color(11),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,5))),not(eq(color(36),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,6))),not(eq(color(35),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,3))),eq(color(27),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,19))),not(eq(color(7),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,27))),not(eq(color(11),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,29))),eq(color(15),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,2))),not(eq(color(36),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,23))),eq(color(18),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,4))),not(eq(color(7),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,2))),not(eq(color(35),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,18))),eq(color(34),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,17))),eq(color(28),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,25))),eq(color(36),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,20))),not(eq(color(31),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,12))),eq(color(0),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,14))),not(eq(color(16),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,0))),eq(color(4),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,7))),eq(color(29),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,12))),eq(color(1),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,33))),eq(color(13),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,16))),not(eq(color(11),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,19))),eq(color(24),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,19))),eq(color(11),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,31))),not(eq(color(34),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,28))),eq(color(11),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,1))),not(eq(color(29),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,13))),eq(color(18),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,9))),not(eq(color(6),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,22))),not(eq(color(27),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,18))),not(eq(color(13),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,12))),not(eq(color(4),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,33))),not(eq(color(22),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,20))),eq(color(27),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,17))),eq(color(16),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,17))),eq(color(1),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,6))),not(eq(color(32),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,36))),not(eq(color(12),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,33))),eq(color(0),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,31))),eq(color(15),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,1))),not(eq(color(8),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,27))),not(eq(color(21),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,25))),not(eq(color(1),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,28))),eq(color(10),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,28))),eq(color(16),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,16))),eq(color(17),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,30))),eq(color(26),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,1))),eq(color(14),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,0))),eq(color(26),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,2))),eq(color(32),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,11))),not(eq(color(20),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,28))),eq(color(24),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,19))),not(eq(color(5),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,19))),eq(color(14),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,18))),not(eq(color(1),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,17))),eq(color(25),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,15))),eq(color(11),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,24))),eq(color(31),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,7))),not(eq(color(25),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,32))),not(eq(color(21),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,3))),eq(color(12),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,28))),eq(color(13),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,27))),eq(color(6),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,10))),eq(color(32),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,33))),eq(color(20),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,4))),not(eq(color(0),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,1))),eq(color(34),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,8))),eq(color(18),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,14))),not(eq(color(17),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,22))),eq(color(19),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,34))),eq(color(13),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,4))),eq(color(20),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,2))),eq(color(30),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,5))),not(eq(color(0),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,19))),not(eq(color(9),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,14))),eq(color(20),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,12))),eq(color(27),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,22))),not(eq(color(13),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,1))),eq(color(32),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,20))),not(eq(color(29),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,34))),not(eq(color(22),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,17))),not(eq(color(21),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,6))),not(eq(color(10),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,9))),eq(color(22),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,31))),not(eq(color(4),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,4))),eq(color(25),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,19))),not(eq(color(14),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,7))),eq(color(36),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,20))),eq(color(33),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,28))),not(eq(color(23),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,34))),eq(color(5),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,35))),eq(color(9),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,32))),not(eq(color(2),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,11))),eq(color(23),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,36))),eq(color(12),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,36))),eq(color(30),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,32))),not(eq(color(17),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,8))),not(eq(color(21),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,7))),not(eq(color(8),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,2))),not(eq(color(17),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,20))),not(eq(color(11),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,19))),eq(color(9),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,10))),eq(color(31),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,10))),not(eq(color(35),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,25))),eq(color(13),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,16))),eq(color(21),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,25))),not(eq(color(5),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,23))),eq(color(14),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,25))),not(eq(color(36),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,17))),eq(color(23),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,34))),not(eq(color(8),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,23))),not(eq(color(31),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,25))),not(eq(color(29),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,34))),not(eq(color(0),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,34))),not(eq(color(2),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,14))),not(eq(color(19),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,25))),not(eq(color(0),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,6))),eq(color(5),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,2))),not(eq(color(32),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,26))),not(eq(color(13),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,9))),eq(color(15),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,9))),not(eq(color(13),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,23))),eq(color(26),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,36))),not(eq(color(24),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,35))),eq(color(28),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,22))),eq(color(15),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,33))),not(eq(color(12),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,23))),eq(color(36),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,10))),eq(color(23),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,22))),eq(color(26),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,20))),not(eq(color(8),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,26))),not(eq(color(30),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,27))),not(eq(color(31),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,34))),not(eq(color(24),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,24))),eq(color(7),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,35))),not(eq(color(10),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,10))),not(eq(color(7),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,5))),eq(color(13),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,28))),not(eq(color(8),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,3))),eq(color(10),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,20))),eq(color(3),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,8))),not(eq(color(33),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,34))),eq(color(7),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,1))),eq(color(26),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,13))),eq(color(25),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,26))),not(eq(color(3),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,0))),eq(color(19),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,24))),not(eq(color(18),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,18))),not(eq(color(35),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,2))),eq(color(29),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,2))),not(eq(color(22),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,21))),not(eq(color(33),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,34))),eq(color(12),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,16))),eq(color(3),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,21))),not(eq(color(34),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,16))),not(eq(color(19),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,30))),not(eq(color(9),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,3))),not(eq(color(7),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,1))),not(eq(color(4),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,16))),not(eq(color(30),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,2))),eq(color(9),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,20))),not(eq(color(25),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,24))),not(eq(color(16),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,29))),not(eq(color(12),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,26))),not(eq(color(31),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,18))),not(eq(color(24),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,27))),eq(color(13),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,36))),not(eq(color(14),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,15))),not(eq(color(2),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,19))),eq(color(22),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,14))),eq(color(28),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,26))),eq(color(33),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,31))),eq(color(34),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,31))),eq(color(1),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,36))),eq(color(10),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,26))),not(eq(color(35),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,31))),eq(color(18),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,8))),not(eq(color(25),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,12))),eq(color(16),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,16))),not(eq(color(33),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,16))),eq(color(4),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,33))),eq(color(31),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),eq(chosenNumber,chooseVar0)) then
                par
                    playerMoney := plus(playerMoney,2)
                    bancoMoney := minus(bancoMoney,2)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,35))),not(eq(color(19),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,21))),not(eq(color(7),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,17))),eq(color(8),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,2))),eq(color(14),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,30))),not(eq(color(5),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,3))),eq(color(16),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,8))),eq(color(7),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,35))),not(eq(color(24),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,15))),not(eq(color(26),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,16))),eq(color(0),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,34))),eq(color(9),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,24))),not(eq(color(7),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,36))),eq(color(34),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,1))),eq(color(17),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,24))),eq(color(4),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,15))),not(eq(color(9),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,6))),eq(color(9),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,35))),eq(color(16),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,11))),not(eq(color(26),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,21))),eq(color(31),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,28))),not(eq(color(26),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,22))),eq(color(14),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,9))),eq(color(25),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,31))),eq(color(7),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,20))),not(eq(color(15),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,15))),eq(color(2),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,1))),not(eq(color(19),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,36))),eq(color(25),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,12))),not(eq(color(27),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,9))),eq(color(21),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,13))),not(eq(color(6),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,16))),not(eq(color(6),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,3))),not(eq(color(23),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,8))),eq(color(25),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,22))),not(eq(color(31),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,21))),eq(color(20),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,11))),not(eq(color(28),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,13))),not(eq(color(30),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,17))),not(eq(color(32),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,15))),eq(color(22),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,18))),eq(color(25),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,14))),not(eq(color(27),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,27))),eq(color(24),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,30))),not(eq(color(25),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,24))),eq(color(11),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,24))),eq(color(22),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,2))),not(eq(color(6),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,24))),eq(color(9),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,10))),eq(color(25),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,23))),not(eq(color(19),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,22))),eq(color(27),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,5))),eq(color(18),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,14))),not(eq(color(33),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,27))),eq(color(2),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,21))),eq(color(10),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,31))),not(eq(color(13),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,5))),not(eq(color(34),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,10))),not(eq(color(23),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,23))),not(eq(color(32),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,14))),not(eq(color(29),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,32))),eq(color(1),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,4))),not(eq(color(18),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,25))),eq(color(1),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,25))),not(eq(color(19),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,27))),eq(color(1),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,25))),eq(color(20),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,19))),eq(color(33),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,9))),eq(color(23),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,12))),not(eq(color(35),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,21))),not(eq(color(18),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,28))),not(eq(color(13),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,32))),not(eq(color(23),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,20))),eq(color(21),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,3))),not(eq(color(35),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,12))),not(eq(color(18),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,11))),eq(color(25),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,11))),not(eq(color(19),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,12))),eq(color(24),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,11))),eq(color(6),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,19))),not(eq(color(24),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,12))),eq(color(23),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,27))),eq(color(17),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,12))),not(eq(color(0),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,31))),not(eq(color(1),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,27))),eq(color(0),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,16))),not(eq(color(5),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,7))),eq(color(20),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,0))),eq(color(15),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,18))),not(eq(color(17),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,6))),not(eq(color(28),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,12))),eq(color(9),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,13))),eq(color(0),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,22))),eq(color(4),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,21))),not(eq(color(11),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,12))),not(eq(color(5),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,29))),not(eq(color(28),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,6))),not(eq(color(33),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,35))),not(eq(color(4),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,27))),not(eq(color(2),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,3))),not(eq(color(4),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,32))),not(eq(color(20),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,7))),eq(color(16),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,36))),not(eq(color(2),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,10))),not(eq(color(34),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,35))),not(eq(color(8),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,7))),not(eq(color(34),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,23))),eq(color(7),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,9))),eq(color(3),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,16))),not(eq(color(23),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,11))),eq(color(9),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,26))),eq(color(34),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,33))),eq(color(3),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,20))),eq(color(15),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,1))),not(eq(color(17),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,29))),not(eq(color(3),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,9))),eq(color(11),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,9))),not(eq(color(23),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,27))),not(eq(color(23),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,12))),not(eq(color(29),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,15))),eq(color(8),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,0))),not(eq(color(16),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,36))),not(eq(color(29),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,0))),eq(color(10),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,18))),not(eq(color(10),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,21))),eq(color(11),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,27))),not(eq(color(26),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,6))),not(eq(color(3),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,24))),not(eq(color(15),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,20))),not(eq(color(4),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,23))),eq(color(22),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,6))),eq(color(19),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,13))),eq(color(6),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,18))),eq(color(4),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,15))),eq(color(19),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,23))),eq(color(33),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,22))),eq(color(33),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,18))),not(eq(color(22),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,1))),eq(color(4),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,19))),not(eq(color(23),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,21))),not(eq(color(27),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,31))),not(eq(color(16),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,22))),eq(color(5),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,33))),eq(color(17),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,19))),not(eq(color(1),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,30))),not(eq(color(15),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,32))),not(eq(color(36),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,14))),eq(color(9),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,8))),not(eq(color(27),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,21))),eq(color(27),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,27))),eq(color(19),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,4))),not(eq(color(21),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,31))),eq(color(8),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,33))),not(eq(color(26),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,24))),eq(color(17),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,1))),eq(color(23),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,22))),not(eq(color(34),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,22))),not(eq(color(20),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,30))),not(eq(color(32),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,17))),eq(color(31),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,27))),not(eq(color(7),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,24))),eq(color(36),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,9))),eq(color(16),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,20))),eq(color(35),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,34))),eq(color(18),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,23))),not(eq(color(0),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,12))),eq(color(35),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,36))),eq(color(4),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,11))),eq(color(15),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,9))),not(eq(color(15),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,24))),eq(color(23),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,33))),eq(color(6),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,29))),not(eq(color(36),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,20))),not(eq(color(22),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,5))),not(eq(color(14),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,20))),eq(color(12),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,3))),not(eq(color(27),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,32))),not(eq(color(30),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,6))),not(eq(color(29),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,35))),eq(color(14),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,7))),not(eq(color(35),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,23))),eq(color(10),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,22))),eq(color(13),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,0))),not(eq(color(14),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,32))),eq(color(30),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,12))),eq(color(36),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,22))),not(eq(color(4),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,19))),not(eq(color(20),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,22))),not(eq(color(10),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,6))),not(eq(color(14),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,28))),not(eq(color(16),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,17))),not(eq(color(20),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,28))),eq(color(20),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,9))),eq(color(4),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,30))),eq(color(1),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,16))),eq(color(23),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,9))),eq(color(35),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,26))),eq(color(13),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,13))),not(eq(color(28),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,25))),eq(color(27),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,36))),not(eq(color(1),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,32))),eq(color(25),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,18))),not(eq(color(31),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,18))),eq(color(36),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,8))),eq(color(4),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,35))),eq(color(3),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,23))),not(eq(color(18),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,13))),eq(color(12),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,18))),not(eq(color(12),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,10))),eq(color(35),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,11))),not(eq(color(17),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,1))),eq(color(18),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,1))),not(eq(color(25),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,26))),eq(color(10),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,16))),not(eq(color(17),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,29))),not(eq(color(14),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,34))),not(eq(color(23),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,22))),eq(color(7),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,30))),eq(color(0),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,19))),not(eq(color(34),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,29))),not(eq(color(11),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,17))),not(eq(color(2),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,3))),not(eq(color(14),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,2))),eq(color(21),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,19))),not(eq(color(25),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,32))),not(eq(color(3),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,2))),eq(color(7),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,29))),eq(color(12),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,28))),not(eq(color(7),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,4))),not(eq(color(8),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,13))),not(eq(color(31),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,29))),eq(color(32),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,0))),not(eq(color(18),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,0))),not(eq(color(19),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,35))),not(eq(color(33),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,29))),eq(color(23),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,18))),not(eq(color(15),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,33))),not(eq(color(13),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,30))),eq(color(7),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,35))),eq(color(32),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,14))),eq(color(34),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,32))),not(eq(color(11),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,16))),eq(color(12),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,18))),eq(color(26),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,14))),eq(color(12),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,0))),eq(color(35),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,21))),eq(color(0),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,19))),not(eq(color(33),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,1))),eq(color(5),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,9))),eq(color(19),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,0))),eq(color(22),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,34))),not(eq(color(11),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,27))),not(eq(color(8),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,3))),eq(color(23),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,21))),not(eq(color(9),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,6))),eq(color(30),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,28))),not(eq(color(2),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,21))),eq(color(18),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,10))),not(eq(color(22),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,22))),not(eq(color(17),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,28))),not(eq(color(24),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,0))),eq(color(5),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,24))),not(eq(color(13),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,12))),not(eq(color(30),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,32))),not(eq(color(0),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,25))),eq(color(10),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,11))),eq(color(30),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,14))),eq(color(7),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,4))),not(eq(color(32),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,26))),not(eq(color(7),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,25))),eq(color(19),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,3))),eq(color(25),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,22))),eq(color(23),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,12))),not(eq(color(32),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,7))),eq(color(12),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,24))),eq(color(14),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,0))),eq(color(8),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,15))),eq(color(9),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,28))),eq(color(30),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,26))),eq(color(19),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,18))),eq(color(6),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,30))),not(eq(color(21),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,6))),not(eq(color(36),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,20))),not(eq(color(17),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,9))),eq(color(13),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,16))),not(eq(color(4),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,27))),not(eq(color(32),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,10))),eq(color(18),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,13))),eq(color(19),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,24))),eq(color(13),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,14))),eq(color(19),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,24))),eq(color(15),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,27))),eq(color(22),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,16))),not(eq(color(12),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,6))),eq(color(24),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,7))),eq(color(17),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,22))),eq(color(12),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,34))),not(eq(color(35),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,4))),eq(color(7),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,28))),not(eq(color(11),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,34))),not(eq(color(19),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,11))),eq(color(22),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,11))),not(eq(color(5),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,13))),not(eq(color(19),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,5))),eq(color(30),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,14))),not(eq(color(10),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,30))),not(eq(color(4),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,6))),not(eq(color(13),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,35))),eq(color(30),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,3))),not(eq(color(28),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,34))),not(eq(color(28),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,33))),not(eq(color(23),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,36))),not(eq(color(8),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,32))),eq(color(15),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,12))),not(eq(color(7),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,2))),eq(color(26),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,29))),not(eq(color(0),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,32))),eq(color(8),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,13))),eq(color(33),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,10))),eq(color(7),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,29))),not(eq(color(10),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,16))),eq(color(28),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,6))),eq(color(14),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,0))),eq(color(33),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,23))),not(eq(color(25),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,22))),eq(color(3),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,23))),not(eq(color(1),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,17))),eq(color(18),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,35))),not(eq(color(31),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,17))),eq(color(2),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,20))),eq(color(9),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,25))),not(eq(color(21),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,34))),not(eq(color(27),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,30))),eq(color(33),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,12))),eq(color(21),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,1))),not(eq(color(31),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,20))),not(eq(color(9),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,2))),eq(color(5),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,27))),eq(color(4),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,24))),not(eq(color(28),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,5))),eq(color(29),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,17))),eq(color(24),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,8))),eq(color(23),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,14))),eq(color(22),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,5))),eq(color(23),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,30))),not(eq(color(7),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,5))),eq(color(34),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,22))),eq(color(9),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,7))),not(eq(color(9),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,19))),eq(color(10),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,20))),eq(color(36),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,3))),eq(color(21),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,11))),not(eq(color(33),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,30))),eq(color(32),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,23))),not(eq(color(28),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,20))),not(eq(color(16),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,27))),not(eq(color(13),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,11))),not(eq(color(4),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,3))),eq(color(7),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,13))),not(eq(color(21),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,9))),eq(color(2),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,10))),not(eq(color(19),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,9))),not(eq(color(36),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,25))),eq(color(18),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,15))),eq(color(20),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,21))),eq(color(35),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,23))),not(eq(color(20),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,16))),eq(color(20),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,7))),eq(color(10),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,34))),not(eq(color(13),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,15))),not(eq(color(28),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,25))),not(eq(color(4),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,36))),eq(color(15),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,0))),eq(color(13),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,36))),eq(color(26),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,22))),eq(color(24),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,8))),not(eq(color(6),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,10))),eq(color(21),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,8))),not(eq(color(20),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,13))),eq(color(36),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,8))),eq(color(34),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,23))),not(eq(color(24),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,22))),not(eq(color(3),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,17))),not(eq(color(25),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,5))),eq(color(14),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,6))),eq(color(16),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,21))),eq(color(33),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,2))),not(eq(color(30),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,4))),not(eq(color(28),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,9))),not(eq(color(2),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,35))),not(eq(color(30),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,25))),eq(color(8),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,13))),not(eq(color(15),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,24))),not(eq(color(8),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,2))),not(eq(color(29),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,27))),eq(color(29),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,4))),eq(color(19),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,7))),eq(color(31),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,33))),not(eq(color(11),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,5))),eq(color(1),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,1))),eq(color(19),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,21))),not(eq(color(35),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,34))),not(eq(color(36),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,1))),not(eq(color(22),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,7))),eq(color(18),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,3))),not(eq(color(24),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,30))),eq(color(3),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,32))),eq(color(10),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,35))),eq(color(27),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,34))),not(eq(color(16),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,26))),eq(color(5),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,35))),eq(color(4),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,23))),not(eq(color(36),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,10))),not(eq(color(14),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,3))),eq(color(29),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,10))),eq(color(17),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,12))),eq(color(31),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,35))),eq(color(24),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,17))),eq(color(26),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,34))),eq(color(16),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,28))),eq(color(0),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,7))),eq(color(4),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,20))),not(eq(color(10),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,21))),eq(color(13),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,29))),eq(color(6),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,26))),eq(color(23),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,20))),not(eq(color(2),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,7))),eq(color(27),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,3))),not(eq(color(15),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,2))),not(eq(color(24),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,3))),not(eq(color(1),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,14))),not(eq(color(34),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,11))),not(eq(color(32),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,31))),eq(color(36),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,11))),not(eq(color(10),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,5))),not(eq(color(28),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,34))),eq(color(32),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,18))),eq(color(20),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,11))),not(eq(color(35),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,34))),eq(color(24),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,0))),eq(color(2),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,27))),eq(color(5),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,1))),eq(color(20),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,19))),eq(color(6),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,17))),not(eq(color(14),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,4))),eq(color(17),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,11))),not(eq(color(36),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,22))),not(eq(color(7),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,16))),eq(color(22),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,32))),eq(color(23),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,9))),not(eq(color(35),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,0))),eq(color(1),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,15))),not(eq(color(17),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,19))),not(eq(color(27),color(19)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,29))),not(eq(color(30),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,19))),eq(color(34),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,35))),not(eq(color(20),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,7))),not(eq(color(4),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,32))),not(eq(color(26),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,7))),not(eq(color(26),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,8))),not(eq(color(12),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,16))),not(eq(color(2),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,7))),not(eq(color(32),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,24))),eq(color(29),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,25))),eq(color(9),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,13))),not(eq(color(7),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,4))),eq(color(28),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,19))),eq(color(31),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,19))),eq(color(7),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,34))),eq(color(29),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,30))),eq(color(9),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,27))),eq(color(9),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,28))),eq(color(31),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,14))),not(eq(color(2),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,4))),not(eq(color(17),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,3))),not(eq(color(33),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,2))),not(eq(color(28),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,7))),eq(color(15),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,9))),eq(color(12),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,36))),eq(color(0),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,20))),not(eq(color(14),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,18))),eq(color(0),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,26))),not(eq(color(24),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,1))),eq(color(24),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,23))),eq(color(28),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,26))),eq(color(15),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,36))),eq(color(24),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,30))),not(eq(color(3),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,20))),not(eq(color(3),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,5))),not(eq(color(27),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,29))),not(eq(color(25),color(29)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,32))),not(eq(color(24),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,30))),eq(color(15),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,17))),not(eq(color(19),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,5))),eq(color(15),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,32))),eq(color(34),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,26))),not(eq(color(2),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,6))),not(eq(color(12),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,6))),eq(color(21),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,21))),not(eq(color(32),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,2))),eq(color(6),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,2))),eq(color(11),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,9))),not(eq(color(12),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,19))),eq(color(18),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,18))),not(eq(color(25),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,12))),eq(color(14),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,10))),not(eq(color(27),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,0))),not(eq(color(28),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,11))),eq(color(20),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,17))),not(eq(color(10),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,36))),not(eq(color(11),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,26))),eq(color(32),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,28))),not(eq(color(6),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,13))),not(eq(color(36),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,2))),not(eq(color(21),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,7))),not(eq(color(24),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,22))),not(eq(color(29),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,16))),eq(color(1),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,2))),not(eq(color(8),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,35))),not(eq(color(14),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,32))),not(eq(color(4),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,35))),not(eq(color(36),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,34))),not(eq(color(10),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,10))),eq(color(11),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,32))),not(eq(color(19),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,24))),eq(color(5),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,22))),eq(color(11),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,21))),not(eq(color(4),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,22))),eq(color(16),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,29))),eq(color(30),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,16))),eq(color(6),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,24))),not(eq(color(21),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,22))),not(eq(color(1),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,27))),not(eq(color(29),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,10))),not(eq(color(4),color(10)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,22))),eq(color(32),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,18))),eq(color(2),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,6))),eq(color(7),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,26))),not(eq(color(10),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,8))),not(eq(color(31),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,13))),not(eq(color(26),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,6))),eq(color(17),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,14))),not(eq(color(36),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,11))),eq(color(1),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,33))),not(eq(color(15),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,14))),eq(color(23),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,11))),eq(color(5),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,7))),not(eq(color(0),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,32))),not(eq(color(22),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,4))),eq(color(6),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,35))),eq(color(1),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,35))),not(eq(color(34),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,1))),eq(color(12),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,17))),eq(color(22),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,27))),eq(color(16),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,8))),not(eq(color(0),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,31))),not(eq(color(14),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,30))),eq(color(12),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,5))),eq(color(32),color(5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,35))),eq(color(15),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,4))),not(eq(color(19),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,12))),eq(color(25),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,21))),not(eq(color(31),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,25))),eq(color(34),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,25))),not(eq(color(9),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,23))),eq(color(12),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,22))),not(eq(color(30),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,18))),not(eq(color(21),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,22))),eq(color(18),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,3))),not(eq(color(19),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,18))),eq(color(17),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,32))),eq(color(7),color(32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,2))),not(eq(color(26),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,8))),eq(color(35),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,24))),not(eq(color(23),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,14))),not(eq(color(22),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,15))),eq(color(4),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,16))),not(eq(color(21),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,36))),not(eq(color(18),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,12))),eq(color(15),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,28))),not(eq(color(31),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,24))),eq(color(26),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,18))),not(eq(color(0),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,15))),not(eq(color(3),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,18))),eq(color(1),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,23))),eq(color(19),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,2))),not(eq(color(16),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,28))),not(eq(color(33),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,35))),not(eq(color(23),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,34))),not(eq(color(9),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,18))),eq(color(11),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,10))),eq(color(14),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,23))),not(eq(color(29),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,11))),not(eq(color(30),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,17))),not(eq(color(29),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,28))),not(eq(color(3),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,11))),eq(color(17),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,2))),eq(color(15),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,7))),eq(color(14),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,27))),not(eq(color(5),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,0))),eq(color(16),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,34))),not(eq(color(15),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,29))),eq(color(17),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,33))),eq(color(29),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,18))),not(eq(color(30),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,13))),not(eq(color(0),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,9))),not(eq(color(18),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,27))),eq(color(34),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,32))),not(eq(color(9),color(32)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,22))),eq(color(17),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,3))),eq(color(15),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,31))),eq(color(13),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,31))),not(eq(color(23),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,12))),not(eq(color(26),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,19),eq(chooseVar0,22))),not(eq(color(19),color(22)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,24))),eq(color(34),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,29))),eq(color(27),color(29))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,14))),eq(color(26),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,13))),eq(color(15),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,19))),eq(color(36),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,17))),eq(color(14),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,30))),not(eq(color(29),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,35))),eq(color(11),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,31))),eq(color(29),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,26))),eq(color(30),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,2))),eq(color(16),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,33))),eq(color(9),color(33))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,17))),eq(color(36),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,11))),not(eq(color(27),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,6))),eq(color(18),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,15))),not(eq(color(36),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,21))),not(eq(color(8),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,28))),not(eq(color(17),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,4))),not(eq(color(23),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,18))),eq(color(24),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,13))),eq(color(4),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,26))),not(eq(color(23),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,27))),not(eq(color(6),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,8))),eq(color(27),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,34))),eq(color(26),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,31))),eq(color(27),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,21))),eq(color(22),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,18))),not(eq(color(9),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,3))),not(eq(color(17),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,27))),eq(color(8),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,36))),eq(color(14),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,33))),not(eq(color(16),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,13))),eq(color(30),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,16))),not(eq(color(29),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,32))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,1))),not(eq(color(16),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,23))),not(eq(color(17),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,2))),eq(color(31),color(2))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,4))),not(eq(color(6),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,16))),eq(color(24),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,14))),eq(color(35),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,8))),not(eq(color(34),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,24))),eq(color(18),color(24))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,11))),not(eq(color(14),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,28))),eq(color(25),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,16))),eq(color(18),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,26))),eq(color(28),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,25))),eq(color(12),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,33))),not(eq(color(32),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,0))),not(eq(color(13),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,4))),not(eq(color(36),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,23))),eq(color(29),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,1))),eq(color(6),color(1))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,10))),eq(color(0),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,7))),not(eq(color(10),color(7)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,31))),eq(color(30),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,31))),eq(color(0),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,30))),not(eq(color(12),color(30)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,17))),not(eq(color(16),color(17)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,25))),not(eq(color(26),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,20))),eq(color(0),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,20))),not(eq(color(36),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,33))),not(eq(color(35),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,14))),eq(color(18),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,19))),eq(color(16),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,19))),eq(color(26),color(19))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,8))),eq(color(3),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,10))),eq(color(8),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,1))),not(eq(color(28),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,9))),eq(color(30),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,20))),not(eq(color(12),color(20)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,27))),not(eq(color(0),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,11))),not(eq(color(18),color(11)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,35))),eq(color(6),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,23))),not(eq(color(11),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,24))),not(eq(color(12),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,35),eq(chooseVar0,4))),eq(color(35),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,23))),eq(color(2),color(23))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,7))),eq(color(28),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,30))),eq(color(4),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,34))),not(eq(color(33),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,5))),not(eq(color(12),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,20))),eq(color(30),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,23))),not(eq(color(5),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,28))),not(eq(color(27),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,34))),not(eq(color(14),color(34)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,27),eq(chooseVar0,4))),eq(color(27),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,14))),not(eq(color(11),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,30))),eq(color(20),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,22))),eq(color(34),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,18))),not(eq(color(36),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,16))),eq(color(9),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,14))),eq(color(4),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,3))),not(eq(color(12),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,25))),not(eq(color(6),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,15),eq(chooseVar0,6))),not(eq(color(15),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,17))),eq(color(5),color(17))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,18))),not(eq(color(32),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,9))),eq(color(7),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,16))),not(eq(color(7),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,0),eq(chooseVar0,6))),eq(color(0),color(6))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,14))),not(eq(color(3),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,24))),not(eq(color(22),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,3),eq(chooseVar0,23))),not(eq(color(3),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,28))),not(eq(color(4),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,25))),not(eq(color(32),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,9),eq(chooseVar0,33))),not(eq(color(9),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,3))),eq(color(30),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,18))),eq(color(22),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,28),eq(chooseVar0,8))),not(eq(color(28),color(8)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,28))),not(eq(color(20),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,0))),not(eq(color(10),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,31))),not(eq(color(30),color(31)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,28))),eq(color(4),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,27))),eq(color(23),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,33))),not(eq(color(34),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,1))),not(eq(color(14),color(1)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,12))),not(eq(color(20),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,14),eq(chooseVar0,25))),not(eq(color(14),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,10))),eq(color(13),color(10))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,20))),eq(color(5),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,9))),eq(color(36),color(9))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,36))),eq(color(20),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,3))),not(eq(color(25),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,7))),eq(color(13),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,7))),eq(color(6),color(7))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,15))),eq(color(6),color(15))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,27))),eq(color(30),color(27))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,4))),eq(color(12),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,34))),eq(color(4),color(34))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,16))),eq(color(2),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,3))),not(eq(color(29),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,14))),eq(color(2),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,18))),eq(color(8),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,27))),not(eq(color(20),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,14))),eq(color(17),color(14))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,21))),eq(color(12),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,36))),not(eq(color(34),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,0))),eq(color(7),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,20))),eq(color(1),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,15))),not(eq(color(24),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,7),eq(chooseVar0,21))),eq(color(7),color(21))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,28))),eq(color(29),color(28))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,24))),not(eq(color(26),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,3))),not(eq(color(18),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,30))),eq(color(2),color(30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,28))),not(eq(color(5),color(28)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,21))),not(eq(color(25),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,18),eq(chooseVar0,20))),eq(color(18),color(20))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,5))),not(eq(color(32),color(5)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,13))),not(eq(color(12),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,16))),not(eq(color(36),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,36))),not(eq(color(33),color(36)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,26))),eq(color(20),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,36))),eq(color(31),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,13))),eq(color(29),color(13))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,8))),eq(color(20),color(8))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,22))),eq(color(2),color(22))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,25),eq(chooseVar0,14))),not(eq(color(25),color(14)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,9))),not(eq(color(20),color(9)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,35))),eq(color(31),color(35))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,12))),eq(color(26),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,13),eq(chooseVar0,16))),not(eq(color(13),color(16)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,26))),not(eq(color(29),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,1),eq(chooseVar0,26))),not(eq(color(1),color(26)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,22),eq(chooseVar0,0))),not(eq(color(22),color(0)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,6),eq(chooseVar0,36))),eq(color(6),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,2))),not(eq(color(11),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,6))),not(eq(color(11),color(6)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,31))),eq(color(20),color(31))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,23),eq(chooseVar0,25))),not(eq(color(23),color(25)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,20),eq(chooseVar0,3))),not(eq(color(20),color(3)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,13))),not(eq(color(29),color(13)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,29),eq(chooseVar0,35))),not(eq(color(29),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,25))),eq(color(11),color(25))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,16))),eq(color(5),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,34),eq(chooseVar0,27))),not(eq(color(34),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,12))),eq(color(17),color(12))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,15))),not(eq(color(21),color(15)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,16))),eq(color(30),color(16))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,11))),eq(color(12),color(11))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,33))),not(eq(color(2),color(33)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,23))),not(eq(color(21),color(23)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,17),eq(chooseVar0,35))),not(eq(color(17),color(35)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,4))),eq(color(31),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,5))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,24),eq(chooseVar0,12))),not(eq(color(24),color(12)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,31),eq(chooseVar0,2))),not(eq(color(31),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,10),eq(chooseVar0,21))),not(eq(color(10),color(21)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,5),eq(chooseVar0,24))),not(eq(color(5),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,11),eq(chooseVar0,4))),not(eq(color(11),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,21),eq(chooseVar0,18))),eq(color(21),color(18))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,16),eq(chooseVar0,26))),eq(color(16),color(26))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,8),eq(chooseVar0,3))),eq(color(8),color(3))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,4),eq(chooseVar0,2))),not(eq(color(4),color(2)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,36),eq(chooseVar0,27))),not(eq(color(36),color(27)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,32),eq(chooseVar0,36))),eq(color(32),color(36))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,30),eq(chooseVar0,30))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,12),eq(chooseVar0,0))),eq(color(12),color(0))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,26),eq(chooseVar0,4))),eq(color(26),color(4))) then
                par
                    playerMoney := plus(playerMoney,1)
                    bancoMoney := minus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,4))),not(eq(color(2),color(4)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,2),eq(chooseVar0,18))),not(eq(color(2),color(18)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
            if and(and(and(and(and(gt(playerMoney,0),gt(bancoMoney,1)),isDef(chooseVar0)),not(eq(chosenNumber,chooseVar0))),and(eq(chosenNumber,33),eq(chooseVar0,24))),not(eq(color(33),color(24)))) then
                par
                    playerMoney := minus(playerMoney,1)
                    bancoMoney := plus(bancoMoney,1)
                endpar
            endif
        endpar

default init s0:
    function playerMoney = 5
    function bancoMoney = 5
