//DANIELA CUGLIETTA
//Example pag.49

asm DEAMONGAME2

import ../../STDL/StandardLibrary

//declare domains and functions

	
signature:
		
       	abstract domain Users
	abstract domain Play
	enum domain State = {NEWGAME | ENDGAME | PROBE | RESULT}
	enum domain Msg = {WIN | LOSE| RETREAT}
	dynamic domain Cartesian subsetof Prod (Msg, Integer)
		
	dynamic monitored input_u: Users -> State
	dynamic monitored input_p: Play -> State
	dynamic monitored winning_lot: Play -> Boolean

//the function of output is divided in 2 functions:
	dynamic controlled output_re: Users -> Play //represents the relatioship between consumers and players
	dynamic controlled output: Play -> Cartesian  //represents the cartesian product between the score 							      	     //and the message
	dynamic controlled score: Play -> Integer
	dynamic controlled del: Play -> Boolean
	
	
definitions:

//Macro Rule for initialization: the score to zero and the consumer becomes a player

	rule r_Initialize ($p in Play, $usr in Users)=
		seq
			score($p) := +0		 
			output_re ($usr):= $p	
		 
		endseq

//rule of beginning game in wich there is the initialization of the player
		
	rule r_NewGame ($usr in Users)=
		if input_u ($usr) = NEWGAME then
		          extend Play with $p do
				r_Initialize [$p, $usr]
		endif

//Macro Rule that cancels the player putting it to false

	rule r_Delete ($p  in Play) =
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
				score ($p) := score ($p) + (+1)
				output ($p) := (WIN, score ($p))
				endseq
			else
				seq
				score ($p) := score ($p) - (+1)
				output ($p) := (LOSE, score ($p))
				endseq
			endif
		endif


//rule that sends in output the result of the game


	rule r_Result ($p in Play)=
		if input_p ($p) = RESULT  then
			output ($p) :=  (RETREAT , score($p))
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