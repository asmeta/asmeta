//applied flatteners: AR 
asm coffeeVendingMachine_flat
import ../../STDL/StandardLibrary
import ../../STDL/CTLlibrary

signature:
    enum domain CoinType = {HALF | ONE}
    enum domain Product = {COFFEE | TEA | MILK}
    domain QuantityDomain subsetof Integer
    domain CoinDomain subsetof Integer

    controlled available: Product -> QuantityDomain
    controlled coins: CoinDomain
    monitored insertedCoin: CoinType

definitions:

    domain QuantityDomain = {0,1,2,3,4,5,6,7,8,9,10}
    domain CoinDomain = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25}

    macro rule r_serveProduct($p in Product) =
        par
            available($p) := minus(available($p),1)
            coins := plus(coins,1)
        endpar


    CTLSPEC ef(and(and(eq(available(MILK),0),eq(available(COFFEE),9)),eq(available(TEA),0)))
    CTLSPEC ag(implies(eq(available(MILK),0),ag(eq(available(MILK),0))))
    CTLSPEC ag(implies(eq(available(TEA),0),ag(eq(available(TEA),0))))
    CTLSPEC ag(implies(eq(available(COFFEE),0),ag(eq(available(COFFEE),0))))
    CTLSPEC (forall $p in Product with ag(implies(eq(available($p),0),ag(eq(available($p),0)))))
    CTLSPEC ag(ge(plus(plus(available(COFFEE),available(TEA)),available(MILK)),5))
    main rule r_Main =
        if lt(coins,25) then
            if eq(insertedCoin,HALF) then
                if gt(available(MILK),0) then
                    r_serveProduct[MILK]
                endif
            else 
                choose $p in Product with and(neq($p,MILK),gt(available($p),0)) do
                    r_serveProduct[$p]
            endif
        endif

default init s0:
    function coins = 0
    function available($p in Product) = 10
