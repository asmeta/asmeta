asm main

import ../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../asm_examples/STDL/CTLLibrary

//import client
import taxi

signature:

definitions:
	//esiste un periodo di tre minuti in cui non ci sono taxi disponibili
	//it exists a three minutes period during which no taxi is available
	CTLSPEC ef(numtaxis = 0 and ex(numtaxis = 0 and ex(numtaxis = 0)))

	//non esiste un cammino in cui i taxi sono sempre occupati
	//it does not exist a path where the taxis are always busy
	CTLSPEC not(ef(numtaxis = 0 and eg(numtaxis = 0)))

	//proprieta' di liveness: se non ci sono taxi liberi, prima o poi se ne libera almeno uno
	//liveness property: if no taxis are available, sooner or later at least a taxi will become available
	CTLSPEC ag(numtaxis = 0 implies af(numtaxis > 0))

	//esiste uno stato in cui il gruppo GR3 richiede tutti e tre i taxi e viene
	//soddisfatto; questo comporta che nello stato successivo non ci sono taxi liberi
	//it exists a state in which group GR3 requests all the three taxis and its
	//request is satisfied; this means that in the next state no taxi is available 
	//soddisfatto; questo comporta che nello stato successivo non ci sono taxi liberi
	CTLSPEC ef((numtaxis = 3 and state(gr3) = WAITING) and
				ex(numtaxis = 0 and state(gr3) = TRAVELLING))

	//proprieta' di liveness: se qualunque cliente o gruppo sono in attesa
	//di un taxi prima o poi verranno serviti
	//liveness properties: if a client or a group are waiting a taxis, sooner or
	//later they will start traveling
	CTLSPEC ag(state(cl1) = WAITING implies ef(state(cl1) = TRAVELLING))
	CTLSPEC ag(state(cl2) = WAITING implies ef(state(cl2) = TRAVELLING))
	CTLSPEC ag(state(cl3) = WAITING implies ef(state(cl3) = TRAVELLING))
	CTLSPEC ag(state(gr2) = WAITING implies ef(state(gr2) = TRAVELLING))
	CTLSPEC ag(state(gr3) = WAITING implies ef(state(gr3) = TRAVELLING))
	//equivalent version
	CTLSPEC (forall $c in ClientAgent with ag(state($c) = WAITING implies ef(state($c) = TRAVELLING)))
	
	
	//proprieta' di reachability; esiste uno stato in cui tutti i clienti singoli e 
	//il gruppo GR3 hanno chiamato un taxi. Negato per avere la stampa di un caso particolare
	//reachability property; it exists a state in which all the single clients an the GR3 group
	//are waiting  for a taxi.
	CTLSPEC ef(state(cl1) = WAITING and state(cl2) = WAITING and state(cl3) = WAITING and state(gr2) = WAITING)
	//We negate the property in order to obtain a counterexample showing how such situation can be obtained.
	//CTLSPEC not(ef(state(cl1) = WAITING and state(cl2) = WAITING and state(cl3) = WAITING and state(gr2) = WAITING))


	//assenza di deadlock
	//deadlock absence (always true)
	CTLSPEC ag(ex(true))

	main rule r_Main = 
		par
			forall $c in ClientAgent with true do
				program($c)
			program(taxi)
		endpar

default init s0:
	function state($c in ClientAgent) = IDLE
	function keepTaxi($c in ClientAgent) = 0

	agent ClientAgent:
		r_ClientAgent[]

	agent TaxiAgent:
		r_TaxiAgent[]
