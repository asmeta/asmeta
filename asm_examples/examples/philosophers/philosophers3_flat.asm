//applied flatteners: CaR AR LR NR MCR 
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


    main rule r_choose_philo =
        choose $p1 in Philosophers, $p2 in Philosophers with and(neq($p1,$p2),not(near($p1,$p2))) do
            par
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_4),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_2),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))) then
                    eating($p1) := true
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_4),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))) then
                    eating($p2) := true
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_3),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))) then
                    eating($p1) := true
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))) then
                    eating($p2) := true
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_5),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_3),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_3),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_4),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_4),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))) then
                    eating($p1) := true
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))) then
                    eating($p2) := true
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))) then
                    eating($p1) := true
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))) then
                    eating($p1) := true
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_2),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_5),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))) then
                    eating($p1) := true
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))) then
                    eating($p2) := true
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))) then
                    eating($p2) := true
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_2),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))) then
                    eating($p1) := true
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))) then
                    eating($p2) := true
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_4),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_3),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_3),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))) then
                    eating($p1) := true
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))) then
                    eating($p1) := true
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))) then
                    eating($p2) := true
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_1),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))) then
                    eating($p2) := true
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_4),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_3),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))) then
                    eating($p2) := true
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_3),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))) then
                    eating($p1) := true
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_2),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_5),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))) then
                    eating($p2) := true
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_5),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))) then
                    eating($p2) := true
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_4),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_5),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_3),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))) then
                    eating($p2) := true
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_3),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))) then
                    eating($p1) := true
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_1),$p2))) then
                    eating($p2) := false
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))) then
                    eating($p2) := true
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))) then
                    eating($p2) := true
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))) then
                    eating($p2) := true
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))) then
                    eating($p1) := true
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_2),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_2),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_1),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))) then
                    eating($p2) := true
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))) then
                    eating($p1) := true
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_1),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_3),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))) then
                    eating($p1) := true
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))) then
                    eating($p1) := true
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))) then
                    eating($p1) := true
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))) then
                    eating($p2) := true
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))) then
                    eating($p2) := true
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))) then
                    eating($p1) := true
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))) then
                    eating($p2) := true
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_4),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_5),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))) then
                    eating($p1) := true
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_2),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))) then
                    eating($p2) := true
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_2),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_5),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))) then
                    eating($p2) := true
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_2),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))) then
                    eating($p2) := true
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))) then
                    eating($p2) := true
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_1)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))) then
                    eating($p1) := true
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_1),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_1),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_1),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_2),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))) then
                    eating($p2) := true
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_3)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_5)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))) then
                    eating($p1) := true
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_2),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_4),$p2))) then
                    eating($p2) := false
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_1),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))) then
                    eating($p1) := true
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))) then
                    eating($p2) := true
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_4),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_3)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))) then
                    eating($p1) := true
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_1),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_4)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_2)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_5)),isUndef(owner(fork_5)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_3)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_5),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_1)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_4)),isUndef(owner(fork_3)))) then
                    eating($p1) := true
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_3),$p2))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_2),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_5),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_2)),isUndef(owner(fork_4)))) then
                    eating($p1) := true
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_2))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_2),$p1))),eq(right_fork($p1),fork_5)) then
                    owner(fork_5) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_3)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_5),$p1))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_5),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_5)),isUndef(owner(fork_4)))),eq(left_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_1),$p1)),eq(owner(fork_1),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_5))),and(isUndef(owner(fork_2)),isUndef(owner(fork_5)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_4))),and(isUndef(owner(fork_1)),isUndef(owner(fork_4)))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(right_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_1),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(isUndef(owner(fork_4)),isUndef(owner(fork_5)))),eq(left_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_2)),isUndef(owner(fork_2)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_5)),isUndef(owner(fork_2)))),eq(right_fork($p2),fork_3)) then
                    owner(fork_3) := $p2
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_1))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_1),$p1))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_3))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_3),$p2))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_5)),isUndef(owner(fork_3)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_2),$p2)),eq(owner(fork_4),$p2))),eq(right_fork($p2),fork_2)) then
                    owner(fork_2) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_4),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_2),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_2),$p1)),eq(owner(fork_5),$p1))) then
                    eating($p1) := false
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_5))),and(and(eating($p1),eq(owner(fork_4),$p1)),eq(owner(fork_5),$p1))),eq(right_fork($p1),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))) then
                    eating($p1) := true
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_3),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_5),eq(right_fork($p1),fork_4))),and(and(eating($p1),eq(owner(fork_5),$p1)),eq(owner(fork_4),$p1))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p1)),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_3))),and(and(eating($p1),eq(owner(fork_3),$p1)),eq(owner(fork_3),$p1))),eq(right_fork($p1),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(left_fork($p2),fork_2)) then
                    owner(fork_2) := $p2
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_1),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(and(eating($p2),eq(owner(fork_4),$p2)),eq(owner(fork_1),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_5))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_5),$p2))),eq(left_fork($p2),fork_3)) then
                    owner(fork_3) := undef
                endif
                if and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_5),eq(right_fork($p2),fork_4))),and(and(eating($p2),eq(owner(fork_5),$p2)),eq(owner(fork_4),$p2))) then
                    eating($p2) := false
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_4),eq(right_fork($p2),fork_4))),and(isUndef(owner(fork_4)),isUndef(owner(fork_4)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_3),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_3),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := undef
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(right_fork($p1),fork_2)) then
                    owner(fork_2) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_1),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_1)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_5)) then
                    owner(fork_5) := $p1
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_3))),and(isUndef(owner(fork_1)),isUndef(owner(fork_3)))),eq(left_fork($p2),fork_5)) then
                    owner(fork_5) := $p2
                endif
                if and(and(and(not(hungry($p2)),and(eq(left_fork($p2),fork_1),eq(right_fork($p2),fork_2))),and(and(eating($p2),eq(owner(fork_1),$p2)),eq(owner(fork_2),$p2))),eq(left_fork($p2),fork_4)) then
                    owner(fork_4) := undef
                endif
                if and(and(and(hungry($p2),and(eq(left_fork($p2),fork_2),eq(right_fork($p2),fork_1))),and(isUndef(owner(fork_2)),isUndef(owner(fork_1)))),eq(left_fork($p2),fork_1)) then
                    owner(fork_1) := $p2
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_3),eq(right_fork($p1),fork_2))),and(isUndef(owner(fork_3)),isUndef(owner(fork_2)))),eq(right_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
                if and(and(and(hungry($p1),and(eq(left_fork($p1),fork_4),eq(right_fork($p1),fork_1))),and(isUndef(owner(fork_4)),isUndef(owner(fork_1)))),eq(left_fork($p1),fork_3)) then
                    owner(fork_3) := $p1
                endif
            endpar

default init initial_state:
    function eating($p in Philosophers) = false
    function owner($f in Fork) = undef
