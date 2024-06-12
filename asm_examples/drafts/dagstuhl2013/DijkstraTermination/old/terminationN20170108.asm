// con i numeri

asm terminationN

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLLibrary
import ../../../STDL/LTLLibrary

signature:
	// domains
   domain Machine subsetof Integer 
   enum domain Color = {WHITE | BLACK}
   
   controlled machineColor: Machine -> Color
   controlled tokenColor : Color
   static master : Machine
   controlled active : Machine -> Boolean
   derived hasToken: Machine -> Boolean
   static isMaster: Machine -> Boolean
   controlled token : Machine
   static pred : Machine -> Machine
   monitored initActive : Machine -> Boolean
   derived terminated : Boolean
   derived stableState: Boolean
   
   controlled firstRound: Boolean //brutto. da risolvere

definitions:
   domain Machine = {0 : 5}

   function master = 0
   function pred($m in Machine) = (($m + 1) mod 6)
   function isMaster($m in Machine) = ($m = master)
   function hasToken($m in Machine) = ($m = token)
   function terminated = not(firstRound) and (token=0 and machineColor(0)=WHITE and tokenColor=WHITE)

   //the state in which all machines are passive is stable
   function stableState = (forall $m in Machine with not(active($m)))

	// Upon trasmission of the token
	rule r_rule5($m in Machine) =
		machineColor($m) := WHITE  

	rule r_passToken($m in Machine) =
		par 
			token := pred($m)
			r_rule5[$m] // r_5
		endpar

	rule r_sendMsg($m in Machine) =
		// send and received together 
		if not active($m) then
			active($m) := true
		endif

	//an active machine can spontaneously decide to become passive
	rule r_becomePassive($m in Machine) =
		if active($m) then 
			choose $nextStatus in Boolean with true do
				active($m) := $nextStatus
		endif

	// if active, it can send a message (and it becomes black)
	rule r_rule1($m1 in Machine) =
		if active($m1) then 
			choose $sendMsg in Boolean, 
					$higherMachine in Machine //with $higherMachine > $m1 do
										with true do
				if $sendMsg then 
					par
						machineColor($m1) := BLACK
						r_sendMsg[$higherMachine]
					endpar
				endif
		endif

	//when the machine passes the token:
	//- if the machine is black, the token becomes black as well
	//- if the machine is white, the token is passed as it is
	/*rule r_rule2($m in Machine) =
		if not(active($m)) and $m=token then
			if machineColor($m) = BLACK then
				tokenColor:= BLACK
			endif
		endif

	// if not active - just pass the token
	rule r_rule0($m in Machine) =
		if active($m) then
			skip
		else 
			if hasToken($m) then 
				r_passToken[$m]
            endif
       endif*/

	// if not active - just pass the token
	//when the machine passes the token:
	//- if the machine is black, the token becomes black as well
	//- if the machine is white, the token is passed as it is
	rule r_rule0_2($m in Machine) =
		//master puo' eseguire questa regola? forse no, perche'
		//il master passa il token bianco, non nero
		if not(active($m)) and hasToken($m) and $m!=master then
			par 
				r_passToken[$m]
				if machineColor($m) = BLACK then
					tokenColor := BLACK
				endif
			endpar
       endif

	// master initiates a probe
	rule r_rule4 =
		par
			machineColor(0) := WHITE
			tokenColor := WHITE
		endpar

	rule r_rule3_4($m in Machine) =
		//se master non puo' eseguire la regola r_rule0_2,
		//allora questa regola e' l'unico modo che dispone per
		//passare un token.
		//il problema e' che non funziona nel primo stato
		
		
		if not(active($m)) //added this. The master cannot start a round if it is active
		and hasToken($m) and isMaster($m) and (machineColor($m) = BLACK or tokenColor= BLACK
												or firstRound//brutto
				) then
			par
				r_passToken[$m]
				r_rule4[]
				
				if firstRound then firstRound := false endif
				
			endpar
		endif

	CTLSPEC ag(terminated implies stableState)
	
	
	CTLSPEC ag(stableState implies ef(terminated))
	LTLSPEC g(terminated implies stableState)
	LTLSPEC g(stableState implies f(terminated))

	main rule r_main =
		forall $ma in Machine with true do
		//choose $ma in Machine with true do //not necessary
			par
				//r_rule0[$ma] //rule 0 and 2 can be merged
				r_rule1[$ma]
				//r_rule2[$ma] //rule 0 and 2 can be merged
				r_rule0_2[$ma] //merge of rules 0 and 2
				r_rule3_4[$ma]
				r_becomePassive[$ma]
			endpar

default init s_0:
	function token = 0
	function active($m in Machine) = initActive($m) //for MC
	//function active($m in Machine) = chooseone({true,false}) //for simulation
	function machineColor($m in Machine) = /*if $m=master then
												WHITE
											else
												BLACK
											endif*/
											//at({0 -> WHITE, 1 -> BLACK, 2 -> BLACK, 3 -> BLACK},$m)
											at({0 -> WHITE, 1 -> BLACK, 2 -> BLACK, 3 -> BLACK, 4 -> BLACK, 5 -> BLACK},$m)
	function tokenColor = WHITE
	
	function firstRound = true

