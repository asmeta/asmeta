//applied flatteners: LR FR ChR MCR 
asm roulette_flat
import ../../STDL/StandardLibrary
import ../../STDL/CTLlibrary

signature:
    domain Number subsetof Integer
    domain Money subsetof Integer
    enum domain Color = {GREEN | RED | BLACK}

    controlled playerMoney: Money
    controlled houseMoney: Money
    monitored chosenNumber: Number
    derived color: Number -> Color
    derived chooseVar0: Number

definitions:

    domain Money = {0,1,2,3,4,5,6,7,8,9,10}
    domain Number = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36}

    function color($n in Number) = if eq($n,0) then GREEN else if eq(mod($n,2),0) then RED else BLACK endif endif
    function chooseVar0 = chooseone({$i30 in Number| true : $i30})


    CTLSPEC ag(eq(plus(playerMoney,houseMoney),10))
    CTLSPEC (forall $n in Money with ef(eq(playerMoney,$n)))
    CTLSPEC (forall $n in Money with ef(eq(houseMoney,$n)))
    CTLSPEC ag(implies(and(eq(playerMoney,0),eq(houseMoney,10)),ag(and(eq(playerMoney,0),eq(houseMoney,10)))))
    CTLSPEC ag(implies(and(eq(playerMoney,9),eq(houseMoney,1)),ag(and(eq(playerMoney,9),eq(houseMoney,1)))))
    main rule r_Main =
        if and(gt(playerMoney,0),gt(houseMoney,1)) then
            if isDef(chooseVar0) then
                if eq(chosenNumber,chooseVar0) then
                    par
                        playerMoney := plus(playerMoney,2)
                        houseMoney := minus(houseMoney,2)
                    endpar
                else 
                    if eq(color(chosenNumber),color(chooseVar0)) then
                        par
                            playerMoney := plus(playerMoney,1)
                            houseMoney := minus(houseMoney,1)
                        endpar
                    else 
                        par
                            playerMoney := minus(playerMoney,1)
                            houseMoney := plus(houseMoney,1)
                        endpar
                    endif
                endif
            endif
        endif

default init s0:
    function playerMoney = 5
    function houseMoney = 5
