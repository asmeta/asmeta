asm FLIP_FLOP_3
import ../../../STDL/StandardLibrary

signature: 
domain State subsetof Natural

dynamic controlled ctl_state : State   //we can also write "controlled ctl_state : State"
dynamic monitored high : Boolean       //we can also write "monitored high : Boolean"
dynamic monitored low : Boolean        //we can also write "monitored low : Boolean"

definitions:

domain State = {0n,1n}

turbo rule r_Fsm in Boolean =
            if ctl_state = 0n 
            then  
                  result := true //result is a reserved 0-ary function used for turbo rule with return value!
                  
            else  result := false
            endif

invariant over high(),low(): not(high=true and low =true)  

main rule r_flip_flop_3 =  
                  par
                  high <- r_Fsm()
                  low  <- r_Fsm()
                  endpar
                  

default init initial_state:
function ctl_state = 0n
function high = false
function low = false                  