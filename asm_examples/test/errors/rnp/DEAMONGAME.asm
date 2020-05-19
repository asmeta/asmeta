//DANIELA CUGLIETTA
//Eexample pag. 49

asm DEAMONGAME

import ../../STDL/StandardLibrary

//declare domains and functions

	
signature:
		
       	abstract domain Users 
	abstract domain Play 
	enum domain State = {NEWGAME | ENDGAME | PROBE | RESULT}
	enum domain Msg = {WIN | LOSE}		

	dynamic monitored input_u: Users -> State
	dynamic monitored input_p: Play -> State
	dynamic monitored winning_lot: Play -> Boolean

//the function of output is divided in 3 functions:
	dynamic monitored output_msg: Play -> Msg        //represents the message
	dynamic monitored output_sc: Play -> Natural     //represents the score
	dynamic controlled output_re: Users -> Play      //represents the relatioship between consumers and players
	
	dynamic controlled score: Play -> Natural
	dynamic controlled del: Play -> Boolean
	
	
definitions:

//Macro Rule for initialization: the score to zero and the consumer becomes a player

	rule r_Initialize ($p in Play, $usr in Users)=
		seq
			score($p) := 0n		 
			output_re($usr):= $p	
		endseq

//rule of beginning game in wich there is the initialization of the player
		
	rule r_NewGame ($usr in Users)=
		if input_u ($usr) = NEWGAME then
		          extend Play with $p do
				r_Initialize [$p, $usr]
		endif

//Macro Rule that cancels the player putting it to false

	rule r_Delete ($p in Play) =
		del($p):= false

//rule of end game in which there is the cancellation of the player

	rule r_EndGame ($p in Play)=
		if input_p ($p) = ENDGAME  then
			r_Delete [$p]
		endif

//rule that increases the score if the player has won or escapes the score if the player has lost

	rule r_Probe ($p in Play)=
		if input_p ($p) = PROBE  then
			if winning_lot ($p) then
				seq
				score ($p) := score ($p) + 1n
				output_msg ($p) := WIN
				endseq
			else
				seq
				score ($p) := score ($p) - 1n
				output_msg ($p) := LOSE
				endseq
			endif
		endif

//rule that sends in output the result of the game

	rule r_Result ($p in Play)=
		if input_p ($p) = RESULT  then
			output_sc ($p) := score ($p)
		endif

//main rule

	main rule r_DeamonGame=
		forall $usr in Users,$p in Play with true do	
		seq
		r_NewGame [$usr]
		r_Probe [$p]
		r_Result [$p]
		r_EndGame [$p]
		endseq