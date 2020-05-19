asm producerConsumerRaff

//Produttore - Consumatore
// - un solo produttore ed un solo consumatore
// - ogni product prodotto dal produttore viene consumato dal consumatore
// - gli accessi al buffer devono essere sincronizzati

//la funzione booleana waiting($b in Buffer) dice se un agente e' in attesa del
//buffer. Un agente in attesa sul buffer viene opportunamente svegliato.
//il buffer e' modellato in modo esplicito.
//il produttore ed il consumatore si mettono in wait sul buffer

import ../../STDL/StandardLibrary

signature:
	domain Producer subsetof Agent
	domain Consumer subsetof Agent
	dynamic abstract domain Products
	abstract domain Buffer

	dynamic controlled waiting: Buffer -> Agent // agente in attesa sul buffer (non una lista. Solo un agente puo' essere in attesa)
	dynamic controlled run: Agent -> Boolean // se l'agente puo' eseguire le sue azioni
	dynamic controlled inBuffer: Buffer -> Products // contenuto del buffer

	dynamic controlled consumedProducts: Consumer -> Powerset(Products) // prodotti consumati

	static producer: Producer
	static consumer: Consumer
	static buffer: Buffer

definitions:

	rule r_writeBuffer($b in Buffer, $p in Products) =
		inBuffer($b) := $p // mette il prodotto nel buffer

	rule r_readBuffer($b in Buffer) =
		par
			consumedProducts(self) := including(consumedProducts(self), inBuffer($b)) //legge il valore dal buffer
			inBuffer($b) := undef // svuota il buffer
		endpar

	//l'agent si mette in attesa sul buffer
	rule r_waiting($b in Buffer) =
		par
			waiting($b) := self
			run(self) := false
		endpar

	//sveglia l'agente che e' in attesa sul buffer
	rule r_notify($b in Buffer) =
		run(waiting($b)) := true

	rule r_produce =
		if(run(self)) then
			par
				extend Products with $newProduct do
					r_writeBuffer[buffer, $newProduct] // mette il prodotto sul buffer
				r_waiting[buffer] // si mette in attesa sul buffer
				r_notify[buffer] // dice al buffer di svegliare l'agente in attesa
			endpar
		endif

	rule r_consume =
		if(run(self)) then
			par
				r_readBuffer[buffer] // legge un prodotto dal buffer
				r_waiting[buffer] // si mette in attesa sul buffer
				r_notify[buffer] // dice al buffer di svegliare l'agente in attesa
			endpar
		endif

	//quando il produttore e' in esecuzione il buffer deve essere vuoto (undef)
	invariant over buffer: run(producer) iff isUndef(inBuffer(buffer))
	//quando il consumatore e' in esecuzione il buffer deve essere pieno
	invariant over buffer: run(consumer) iff isDef(inBuffer(buffer))
	//se un elemento e' sul buffer non e' tra gli elementi consumati dal consumatore
	invariant over buffer: isDef(inBuffer(buffer)) implies
						not(contains(consumedProducts(consumer), inBuffer(buffer)))
	//il produttore e il consumatore non sono mai in esecuzione nello stesso momento
	invariant over run: run(producer) xor run(consumer)
	//il consumatore consuma tutti i prodotti generati dal produttore
	invariant over run: run(producer) implies
						(forall $p in Products with contains(consumedProducts(consumer), $p))

	main rule r_Main =
		par
			program(producer)
			program(consumer)
		endpar

default init s0:
	function waiting($b in Buffer) = consumer
	function run($a in Agent) = if($a=producer) then true else false endif
	function consumedProducts($c in Consumer) = {}

	agent Producer:
		r_produce[]

	agent Consumer:
		r_consume[]
