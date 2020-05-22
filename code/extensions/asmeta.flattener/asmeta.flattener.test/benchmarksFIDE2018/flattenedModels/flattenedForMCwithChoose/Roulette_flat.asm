//applied flatteners: MCR FR AR LR CaR NR 
asm Roulette_flat
import ../STDL/StandardLibrary
import ../STDL/CTLlibrary

signature:
    domain Number subsetof Integer
    domain Money subsetof Integer
    enum domain Color = {GREEN | RED | BLACK}

    controlled playerMoney: Money
    controlled bancoMoney: Money
    monitored chosenNumber: Number
    derived color: Number -> Color

definitions:

    domain Money = {0,1,2,3,4,5,6,7,8,9,10}
    domain Number = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36}

    function color($n in Number) = if eq($n,0) then GREEN else if eq(mod($n,2),0) then RED else BLACK endif endif


    CTLSPEC ag(eq(plus(playerMoney,bancoMoney),10))
    CTLSPEC (forall $n in Money with ef(eq(playerMoney,$n)))
    CTLSPEC (forall $n in Money with ef(eq(bancoMoney,$n)))
    CTLSPEC ag(implies(and(eq(playerMoney,0),eq(bancoMoney,10)),ag(and(eq(playerMoney,0),eq(bancoMoney,10)))))
    CTLSPEC ag(implies(and(eq(playerMoney,9),eq(bancoMoney,1)),ag(and(eq(playerMoney,9),eq(bancoMoney,1)))))
    main rule r_Main =
        if and(gt(playerMoney,0),gt(bancoMoney,1)) then
            choose $i22 in Number with true do
                par
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,21)),not(eq(color(21),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,11)),eq(color(11),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,13)),not(eq(color(13),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,8)),eq(color(8),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,26)),eq(color(26),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,22)),not(eq(color(22),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,31)),eq(color(31),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,14)),not(eq(color(14),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,31)),not(eq(color(31),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,19)),not(eq(color(19),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,30)),eq(color(30),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,33)),not(eq(color(33),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,1)),not(eq(color(1),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,25)),not(eq(color(25),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,12)),not(eq(color(12),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,23)),not(eq(color(23),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,4)),eq(color(4),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,14)),eq(color(14),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,0)),eq(color(0),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,22)),eq(color(22),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,32)),not(eq(color(32),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,10)),not(eq(color(10),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,28)),eq(color(28),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,19)),eq(color(19),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,8)),not(eq(color(8),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,6)),not(eq(color(6),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,24)),not(eq(color(24),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,29)),not(eq(color(29),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,15)),eq(color(15),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,23)),eq(color(23),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,30)),not(eq(color(30),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,21)),eq(color(21),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,5)),not(eq(color(5),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if eq(chosenNumber,$i22) then
                        par
                            playerMoney := plus(playerMoney,2)
                            bancoMoney := minus(bancoMoney,2)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,3)),not(eq(color(3),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,2)),eq(color(2),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,3)),eq(color(3),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,18)),not(eq(color(18),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,25)),eq(color(25),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,17)),not(eq(color(17),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,1)),eq(color(1),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,2)),not(eq(color(2),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,33)),eq(color(33),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,5)),eq(color(5),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,16)),eq(color(16),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,36)),eq(color(36),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,35)),eq(color(35),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,0)),not(eq(color(0),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,27)),eq(color(27),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,20)),eq(color(20),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,20)),not(eq(color(20),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,17)),eq(color(17),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,16)),not(eq(color(16),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,27)),not(eq(color(27),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,11)),not(eq(color(11),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,13)),eq(color(13),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,18)),eq(color(18),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,15)),not(eq(color(15),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,35)),not(eq(color(35),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,28)),not(eq(color(28),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,34)),not(eq(color(34),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,24)),eq(color(24),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,29)),eq(color(29),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,9)),eq(color(9),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,7)),not(eq(color(7),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,6)),eq(color(6),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,36)),not(eq(color(36),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,4)),not(eq(color(4),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,12)),eq(color(12),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,34)),eq(color(34),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,9)),not(eq(color(9),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,32)),eq(color(32),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,7)),eq(color(7),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,10)),eq(color(10),color($i22))) then
                        par
                            playerMoney := plus(playerMoney,1)
                            bancoMoney := minus(bancoMoney,1)
                        endpar
                    endif
                    if and(and(not(eq(chosenNumber,$i22)),eq(chosenNumber,26)),not(eq(color(26),color($i22)))) then
                        par
                            playerMoney := minus(playerMoney,1)
                            bancoMoney := plus(bancoMoney,1)
                        endpar
                    endif
                endpar
        endif

default init s0:
    function playerMoney = 5
    function bancoMoney = 5
