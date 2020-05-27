//applied flatteners: CaR LR FR ChR NR MCR 
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
    derived chooseVar0: Philosophers

definitions:

    function right_fork($a in Philosophers) = switch $a case phil_1:fork_2 case phil_2:fork_3 case phil_3:fork_4 case phil_4:fork_5 case phil_5:fork_1 endswitch
    function left_fork($a in Philosophers) = switch $a case phil_1:fork_1 case phil_2:fork_2 case phil_3:fork_3 case phil_4:fork_4 case phil_5:fork_5 endswitch
    function near($x in Philosophers, $y in Philosophers) = or(eq(left_fork($x),right_fork($y)),eq(left_fork($y),right_fork($x)))
    function chooseVar0 = chooseone({$p1 in Philosophers| and(neq($p1,$p2),not(near($p1,$p2))) : $p1})


    main rule r_choose_philo =
        par
            if and(and(isDef(chooseVar0),hungry($p2)),and(isUndef(owner(left_fork($p2))),isUndef(owner(right_fork($p2))))) then
                par
                    owner(left_fork($p2)) := $p2
                    owner(right_fork($p2)) := $p2
                    eating($p2) := true
                endpar
            endif
            if and(and(isDef(chooseVar0),hungry(chooseVar0)),and(isUndef(owner(left_fork(chooseVar0))),isUndef(owner(right_fork(chooseVar0))))) then
                par
                    owner(left_fork(chooseVar0)) := chooseVar0
                    owner(right_fork(chooseVar0)) := chooseVar0
                    eating(chooseVar0) := true
                endpar
            endif
            if and(and(isDef(chooseVar0),not(hungry(chooseVar0))),and(and(eating(chooseVar0),eq(owner(left_fork(chooseVar0)),chooseVar0)),eq(owner(right_fork(chooseVar0)),chooseVar0))) then
                par
                    owner(left_fork(chooseVar0)) := undef
                    owner(right_fork(chooseVar0)) := undef
                    eating(chooseVar0) := false
                endpar
            endif
            if and(and(isDef(chooseVar0),not(hungry($p2))),and(and(eating($p2),eq(owner(left_fork($p2)),$p2)),eq(owner(right_fork($p2)),$p2))) then
                par
                    owner(left_fork($p2)) := undef
                    owner(right_fork($p2)) := undef
                    eating($p2) := false
                endpar
            endif
        endpar

default init initial_state:
    function eating($p in Philosophers) = false
    function owner($f in Fork) = undef
