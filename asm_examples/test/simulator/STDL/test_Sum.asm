asm test_Sum

import ../../../STDL/StandardLibrary

signature:
		dynamic domain Players subsetof Agent
		controlled win: Players -> Integer
		controlled bagWin: Agent -> Integer
		controlled counter: Integer
		controlled totalWin: Integer
		controlled totalBagWin: Integer
		static p1: Players

definitions:

	rule r_PlayerRule = 
		skip

		main rule r_Main = 
			par
				extend Players with $p do
					par
						counter := counter + 1
						win($p) := counter
						if(counter mod 2 = 0) then
							bagWin($p) := 10
						else
							bagWin($p) := 11
						endif
					endpar
				totalWin := sum({$o in Players | (win($o) mod 2 = 0): win($o) })
				totalBagWin := sum(<$k in asBag(Players) | (bagWin($k) mod 2 = 0): bagWin($k) >)
				//totalBagWin := sum(<1, 1, 3>)
			endpar
       
default init s0:
	function counter = 1
	function win($o in Players) = 0
	function bagWin($o in Agent) = 0
	
	agent Players:
		r_PlayerRule[]
