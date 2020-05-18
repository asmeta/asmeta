//
// FLIP FLOP version with turbo rules and rule as terms
//
asm FLIP_FLOP_4
import ../../../STDL/StandardLibrary

signature:
domain State subsetof Natural

dynamic controlled ctl_state : State   //we can also write "controlled ctl_state : State"
dynamic monitored high : Boolean       //we can also write "monitored high : Boolean"
dynamic monitored low : Boolean        //we can also write "monitored low : Boolean"
static s0: State
static s1: State

definitions:

domain State = {0n,1n}
function s0= 0n
function s1=1n

turbo rule r_Fsm($ctl_state in State, $i in State , $j in State, $cond in Boolean, $rule in Rule) in Boolean =
            if $ctl_state=$i and $cond
            then  par
                     $rule
                     $ctl_state := $j
                     result := true //result is a reserved 0-ary function used for turbo rule with return value!
                  endpar
            endif

macro rule r_skip = skip

invariant over high(),low(): not(high=true and low =true)

//main rule r_flip_flop_4 =  high <- r_Fsm(ctl_state,0n,1n,high,<<r_skip>>)
main rule r_flip_flop_4 =  high <- r_Fsm(ctl_state,s0,s1,high,<<r_skip>>) //THIS IS OK

default init initial_state:
function ctl_state = 0n
function high = false
function low = false
