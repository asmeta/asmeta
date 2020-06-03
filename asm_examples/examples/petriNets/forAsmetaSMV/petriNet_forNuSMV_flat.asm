//applied flatteners: NR MCR AR 
asm petriNet_forNuSMV_flat
import ../../../STDL/StandardLibrary
import ../../../STDL/CTLlibrary

signature:
    abstract domain Place
    abstract domain Transition
    domain TokenDomain subsetof Integer

    controlled tokens: Place -> TokenDomain
    static inArcWeight: Prod(Place, Transition) -> TokenDomain
    static outArcWeight: Prod(Transition, Place) -> TokenDomain
    static incidenceMatrix: Prod(Place, Transition) -> TokenDomain
    static capacity: Place -> TokenDomain
    static isInputPlace: Prod(Place, Transition) -> Boolean
    static isOutputPlace: Prod(Place, Transition) -> Boolean
    static p1: Place
    static p2: Place
    static p3: Place
    static p4: Place
    static t1: Transition
    static t2: Transition
    derived isEnabled: Transition -> Boolean
    derived availableCapacity: Place -> TokenDomain

definitions:

    domain TokenDomain = {0,1,2,3,4,5}

    function inArcWeight($p in Place, $t in Transition) = switch $p case p1:if eq($t,t1) then 1 else 0 endif case p2:if eq($t,t2) then 2 else 0 endif case p3:if eq($t,t2) then 4 else 0 endif case p4:0 endswitch
    function outArcWeight($t in Transition, $p in Place) = switch $p case p1:if eq($t,t2) then 1 else 0 endif case p2:if eq($t,t1) then 1 else 0 endif case p3:if eq($t,t1) then 3 else 0 endif case p4:if eq($t,t2) then 1 else 0 endif endswitch
    function incidenceMatrix($p in Place, $t in Transition) = minus(outArcWeight($t,$p),inArcWeight($p,$t))
    function isInputPlace($p in Place, $t in Transition) = gt(inArcWeight($p,$t),0)
    function isOutputPlace($p in Place, $t in Transition) = gt(outArcWeight($t,$p),0)
    function capacity($p in Place) = switch $p case p1:1 case p2:3 case p3:5 case p4:1 endswitch
    function availableCapacity($p in Place) = minus(capacity($p),tokens($p))
    function isEnabled($t in Transition) = (forall $p in Place with and(implies(isInputPlace($p,$t),ge(tokens($p),inArcWeight($p,$t))),implies(isOutputPlace($p,$t),ge(availableCapacity($p),incidenceMatrix($p,$t)))))


    invariant over tokens: (forall $p in Place with ge(tokens($p),0))
    CTLSPEC ag((forall $p in Place with le(tokens($p),capacity($p))))
    CTLSPEC ef(eq(tokens(p2),2))
    CTLSPEC not(ef(eq(tokens(p2),2)))
    CTLSPEC ag(ef(and(and(and(eq(tokens(p1),1),eq(tokens(p2),1)),eq(tokens(p3),2)),eq(tokens(p4),1))))
    main rule r_Main =
        choose $t in Transition with isEnabled($t) do
            forall $p127 in Place with true do
                tokens($p127) := plus(tokens($p127),incidenceMatrix($p127,$t))

default init s0:
    function tokens($p in Place) = at({p1->1,p2->1,p3->2,p4->1},$p)
