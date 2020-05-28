// like FLIP_FLOP in rps_mono/models but with turbo rules
// AsmetaS cannot simulate it because it contains turbo rules

asm FLIP_FLOP

import ../../../STDL/StandardLibrary

signature: 
domain State subsetof Natural

dynamic controlled ctl_state : State   //we can also write "controlled ctl_state : State"
dynamic monitored high : Boolean       //we can also write "monitored high : Boolean"
dynamic monitored low : Boolean        //we can also write "monitored low : Boolean"

definitions:

domain State = {0n,1n}

turbo rule r_Fsm($ctl_state in State, $i in State , $j in State,$cond in Boolean,$rule in Rule)=
            if $ctl_state=$i and $cond
            then  par
                     $rule
                     $ctl_state := $j
                  endpar
            endif

invariant over high(),low(): not(high=true and low =true)  

main rule r_flip_flop =  
                  par
                  r_Fsm(ctl_state,0n,1n,high,<<skip>>)
                  r_Fsm(ctl_state,1n,0n,low,<<skip>>)
                  endpar
                  
default init initial_state:
function ctl_state = 0n
function high = false
function low = false                  