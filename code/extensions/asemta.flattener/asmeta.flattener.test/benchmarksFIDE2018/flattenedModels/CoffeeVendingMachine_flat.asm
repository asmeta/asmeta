//applied flatteners: MCR FR ChR AR LR CaR NR 
asm CoffeeVendingMachine_flat
import ./STDL/StandardLibrary
import ./STDL/CTLlibrary

signature:
    enum domain CoinType = {HALF | ONE}
    enum domain Product = {COFFEE | TEA | MILK}
    domain QuantityDomain subsetof Integer
    domain CoinDomain subsetof Integer

    controlled available: Product -> QuantityDomain
    controlled coins: CoinDomain
    monitored insertedCoin: CoinType
    derived chooseVar0: Product

definitions:

    domain QuantityDomain = {0,1,2,3,4,5,6,7,8,9,10}
    domain CoinDomain = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25}

    function chooseVar0 = chooseone({$p in Product| and(neq($p,MILK),gt(available($p),0)) : $p})


    CTLSPEC ef(and(and(eq(available(MILK),0),eq(available(COFFEE),9)),eq(available(TEA),0)))
    CTLSPEC ag(implies(eq(available(MILK),0),ag(eq(available(MILK),0))))
    CTLSPEC ag(implies(eq(available(TEA),0),ag(eq(available(TEA),0))))
    CTLSPEC ag(implies(eq(available(COFFEE),0),ag(eq(available(COFFEE),0))))
    CTLSPEC (forall $p in Product with ag(implies(eq(available($p),0),ag(eq(available($p),0)))))
    CTLSPEC ag(ge(plus(plus(available(COFFEE),available(TEA)),available(MILK)),5))
    main rule r_Main =
        par
            if and(and(and(lt(coins,25),not(eq(insertedCoin,HALF))),isDef(chooseVar0)),eq(chooseVar0,MILK)) then
                available(MILK) := minus(available(MILK),1)
            endif
            if and(and(lt(coins,25),not(eq(insertedCoin,HALF))),isDef(chooseVar0)) then
                coins := plus(coins,1)
            endif
            if and(and(lt(coins,25),eq(insertedCoin,HALF)),gt(available(MILK),0)) then
                par
                    available(MILK) := minus(available(MILK),1)
                    coins := plus(coins,1)
                endpar
            endif
            if and(and(and(lt(coins,25),not(eq(insertedCoin,HALF))),isDef(chooseVar0)),eq(chooseVar0,COFFEE)) then
                available(COFFEE) := minus(available(COFFEE),1)
            endif
            if and(and(and(lt(coins,25),not(eq(insertedCoin,HALF))),isDef(chooseVar0)),eq(chooseVar0,TEA)) then
                available(TEA) := minus(available(TEA),1)
            endif
        endpar

default init s0:
    function coins = 0
    function available($p in Product) = 10
