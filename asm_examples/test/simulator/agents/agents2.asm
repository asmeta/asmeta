asm agents2
//dovrebbe causare update inconsistente. invece foo e' sempre falsa
//inoltre come devo considerare la variabile agente in NuSMV?

//se controllato con AsmetaMA, segnala la presenza dell'update inconsistente

import ../../../STDL/StandardLibrary

signature:
	dynamic controlled foo: Boolean
	static agent1: Agent
	static agent2: Agent
	
definitions:
	
	rule r_rule =
		if(self=agent1) then
			foo := true
		else
			foo := false
		endif	

	//AsmetaL invariant per visualizzare il problema
	invariant over foo: not(foo)
	
	main rule r_Main =
		par
			program(agent1)
			program(agent2)
		endpar
	

default init s0:
	function foo = false

	agent Agent:
		r_rule[]