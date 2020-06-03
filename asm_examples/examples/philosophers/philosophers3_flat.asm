//applied flatteners: NR FR CaR 
asm philosophers3_flat
import ../../STDL/StandardLibrary

signature:
    domain Philosophers subsetof Agent
    abstract domain Fork

    monitored hungry: Philosophers -> Boolean
    controlled eating: Philosophers -> Boolean
    static right_fork: Philosophers -> Fork
    static left_fork: Philosophers -> Fork
    static near: Prod(Philosophers, Philosophers) -> Boolean
    controlled owner: Fork -> Philosophers
    static phil_1: Philosophers
    static phil_2: Philosophers
    static phil_3: Philosophers
    static phil_4: Philosophers
    static phil_5: Philosophers
    static fork_1: Fork
    static fork_2: Fork
    static fork_3: Fork
    static fork_4: Fork
    static fork_5: Fork

definitions:

    function right_fork($a in Philosophers) = switch $a case phil_1:fork_2 case phil_2:fork_3 case phil_3:fork_4 case phil_4:fork_5 case phil_5:fork_1 endswitch
    function left_fork($a in Philosophers) = switch $a case phil_1:fork_1 case phil_2:fork_2 case phil_3:fork_3 case phil_4:fork_4 case phil_5:fork_5 endswitch
    function near($x in Philosophers, $y in Philosophers) = or(eq(left_fork($x),right_fork($y)),eq(left_fork($y),right_fork($x)))

    macro rule r_Eat =
        if and(hungry(self),and(isUndef(owner(left_fork(self))),isUndef(owner(right_fork(self))))) then
            par
                owner(left_fork(self)) := self
                owner(right_fork(self)) := self
                eating(self) := true
            endpar
        endif

    macro rule r_Think =
        if and(not(hungry(self)),and(and(eating(self),eq(owner(left_fork(self)),self)),eq(owner(right_fork(self)),self))) then
            par
                owner(left_fork(self)) := undef
                owner(right_fork(self)) := undef
                eating(self) := false
            endpar
        endif

    macro rule r_Philo =
        par
            r_Eat[]
            r_Think[]
        endpar


    main rule r_choose_philo =
        choose $p1 in Philosophers, $p2 in Philosophers with and(neq($p1,$p2),not(near($p1,$p2))) do
            par
                program($p1)
                program($p2)
            endpar

default init initial_state:
    function eating($p in Philosophers) = false
    function owner($f in Fork) = undef
    agent Philosophers:
r_Philo[]

