asm producerConsumerGround

//Produttore - Consumatore
// - un solo produttore ed un solo consumatore
// - ogni product prodotto dal produttore viene consumato dal consumatore
// - gli accessi al buffer devono essere sincronizzati

//modello semplice
//la funzione booleana waiting($a in Agent) dice se un agente e' in attesa del
//buffer. Il produttore e il consumatore si svegliano a vicenda.
//Il buffer non e' modellato in modo esplicito. Viene semplicemente rappresentato
//il contenuto del buffer con la funzione inBuffer.

import ../../STDL/StandardLibrary

signature:
	domain Producer subsetof Agent
	domain Consumer subsetof Agent
	dynamic abstract domain Products

	dynamic controlled waiting: Agent -> Boolean // se l'agente e' in attesa
	dynamic controlled inBuffer: Products // contenuto del buffer

	dynamic controlled consumedProducts: Consumer -> Powerset(Products) // prodotti consumati dal consumer

	static producer: Producer
	static consumer: Consumer

definitions:

	rule r_writeBuffer($p in Products) =
		inBuffer := $p // mette il prodotto nel buffer

	rule r_readBuffer =
		par
			consumedProducts(self) := including(consumedProducts(self), inBuffer) //legge il valore dal buffer
			inBuffer := undef // svuota il buffer
		endpar

	//l'agente si mette in attesa
	rule r_waiting =
		waiting(self) := true

	//l'agente $a viene svegliato
	rule r_notify($a in Agent) =
		waiting($a) := false

	//se il produttore non e' in attesa puo' produrre
	rule r_produce =
		if(not(waiting(self))) then
			par
				extend Products with $newProduct do
					r_writeBuffer[$newProduct] // mette il prodotto nel buffer
				r_waiting[] // si mette in attesa
				r_notify[consumer] //sveglia il consumatore
			endpar
		endif

	//se il consumatore non e' in attesa puo' consumare
	rule r_consume =
		if(not(waiting(self))) then
			par
				r_readBuffer[] //prende il prodotto dal buffer
				r_waiting[] //si mette in attesa
				r_notify[producer] //sveglia il produttore
			endpar
		endif

	//quando il produttore e' in esecuzione il buffer deve essere vuoto (undef)
	invariant over waiting: waiting(consumer) iff isUndef(inBuffer)
	//quando il consumatore e' in esecuzione il buffer deve essere pieno
	invariant over waiting: waiting(producer) iff isDef(inBuffer)
	//se un elemento e' sul buffer non e' tra gli elementi consumati dal consumatore
	invariant over inBuffer: isDef(inBuffer) implies
						not(contains(consumedProducts(consumer), inBuffer))
	//il produttore e il consumatore non sono mai in esecuzione nello stesso momento
	invariant over waiting: waiting(producer) xor waiting(consumer)
	//il consumatore consuma tutti i prodotti generati dal produttore
	invariant over Products: not(waiting(producer)) implies
						(forall $p in Products with contains(consumedProducts(consumer), $p))

	main rule r_Main =
		par
			program(producer)
			program(consumer)
		endpar

default init s0:
	function waiting($a in Agent) = if($a=consumer) then true else false endif
	function consumedProducts($c in Consumer) = {} // all'inizio il consumatore non ha consumato nulla

	agent Producer:
		r_produce[]

	agent Consumer:
		r_consume[]
