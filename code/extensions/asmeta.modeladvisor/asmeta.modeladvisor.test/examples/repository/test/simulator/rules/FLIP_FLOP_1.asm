asm FLIP_FLOP_1
import ../../../STDL/StandardLibrary

signature: 
domain State subsetof Natural

dynamic controlled ctl_state : State   //we can also write "controlled ctl_state : State"
dynamic monitored high : Boolean       //we can also write "monitored high : Boolean"
dynamic monitored low : Boolean        //we can also write "monitored low : Boolean"

definitions:

domain State = {0n,1n}

macro rule r_Fsm =
            if ctl_state = 0n 
            then  
                  ctl_state := 1n
                  
            else ctl_state := 0n
            endif

invariant over high(),low(): not(high=true and low =true)  

main rule r_flip_flop_1 =  
                  par
                  r_Fsm[]
                  r_Fsm[]
                  endpar
                  
default init initial_state:
function ctl_state = 0n
function high = false
function low = false                  