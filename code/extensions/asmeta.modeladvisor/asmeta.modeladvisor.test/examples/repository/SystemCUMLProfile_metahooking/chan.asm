module chan

	import StandardLibrary
	export * 

signature:
	/*
	 * Dominio Process 
	 */
	dynamic abstract domain Process
	/*
	 * Dominio Primitive channel 
	 */
	dynamic abstract domain PrimChannel
	/*
	 * Dominio Event 
	 */
	dynamic abstract domain Event
	/*
	 * Dominio Timeout event 
	 */
	dynamic domain TimeOut subsetof Event
	/*
	 * Dominio Sc_Thread 
	 */
	dynamic domain Sc_Thread subsetof Process
	/*
	 * Dominio Integer channel 
	 */
	dynamic domain ChanInteger subsetof PrimChannel 
	/*
	 * Dominio Boolean channel 
	 */
	dynamic domain ChanBoolean subsetof PrimChannel 
	/*
	 * Dominio Process status 
	 */
	enum domain Status = {RUNNABLE | SUSPENDED | EXECUTING | STOPPED}
	/*
	 * Dominio Scheduler phase 
	 */
 	enum domain Phase = {STEP_AGAIN | ELABORATION | INITIALIZATION | EVALUATION | 
		UPDATE | DELTA_NOTIFICATION | TIMED_NOTIFICATION | STOP}
	/*
	 * Dato un canale Intero, restituisce il valore del canale 
	 */
	controlled valueInt: ChanInteger -> Integer
	/*
	 * Dato un canale Booleano, restituisce il valore del canale
	 */
	controlled valueBool: ChanBoolean -> Boolean
	/*
	 * Dato un canale Intero, memorizza il valore del canale 
	 */	
	controlled newValueInt: ChanInteger -> Integer
	/*
	 * Dato un canale Booleano, memorizza il valore del canale 
	 */	
	controlled newValueBool: ChanBoolean -> Boolean
	/*
	 * Dato un canale Primitivo, restituisce il canale intero 
	 */	
	controlled whoAmIInt: PrimChannel -> ChanInteger
	/*
	 * Dato un canale Primitivo, restituisce il canale booleano
	 */	
	controlled whoAmIBool: PrimChannel -> ChanBoolean
	/*
	 * Dato un canale Intero, legge il valore del canale 
	 */	
	derived readInt: ChanInteger -> Integer
	/*
	 * Dato un canale Booleano, memorizza il valore del canale 
	 */
	derived readBool: ChanBoolean -> Boolean
	/*
	 * Dato un canale Booleano, restituisce l'evento associato al fornte di salita del clock
	 */
	controlled posEdgeEvent: ChanBoolean -> Event
	/*
	 * Dato un canale Booleano, restituisce l'evento associato al fornte di discesa del clock
	 */
	controlled negEdgeEvent: ChanBoolean -> Event
	/*
	 * Dato un canale Primitivo, ritorna vero se il canale deve essere aggiornato
	 */
 	controlled isOutOfDate: PrimChannel -> Boolean
	/*
	 * Dato un canale Primitivo, ritorna l'evento associato al cambio del canale
	 */
 	controlled defaultEvent: PrimChannel -> Event
	/*
	 * Dato un canale Primitivo, ritorna la regola di aggiornamento
	 */
	controlled updateRule: PrimChannel -> Rule(PrimChannel)
	/*
	 * Dato un processo, ritorna vero se il processo è staticamente azionato,
	 * cioè se è riattivato secondo la sua lista di sensitività statica
	 */
	derived isStaticTriggered: Sc_Thread -> Boolean
	/*
	 * Dato un processo, ritorna vero se il processo è staticamente azionato,
	 * cioè se è riattivato secondo la sua lista di sensitività dinamica
	 */
	controlled isDynTriggered: Sc_Thread -> Boolean
	/*
	 * Dato un processo, ritorna il suo stato
	 */
	controlled status: Sc_Thread -> Status
	/*
	 * Dato un evento, ritorna vero se l'evento è in attesa di essere notificato
	 */
	controlled isPending: Event -> Boolean
	/*
	 * Dato un evento, ritorna l'istante in cui l'evento viene notificato
	 */
	controlled eventTime: Event -> Integer
	/*
	 * Dato un processo ed un evento, ritorna vero se l'evento appartiene alla
	 * lista di sentività statica del processo. Altrimenti, ritorna undef
	 */
	controlled isSensibleOf: Prod(Sc_Thread, Event) -> Boolean
	/*
	 * Dato un processo ed un evento, ritorna vero se il processo è stato 
	 * sospeso da un evento da un waiting statement (sensitività dinamica).
	 * Altrimenti, ritorna undef
	 */
	controlled isDynSensibleOf: Prod(Sc_Thread, Event) -> Boolean
	/*
	 * Ritorna il tempo di simulazione
	 */
	controlled time: Integer
	/*
	 * Returna la fase dello scheduler
	 */
 	controlled phase: Phase
	/*
	 * Ritorna il processo attualmente eseguito
	 */
	controlled current_exec: Sc_Thread
	/*
	 * Dato un processo, ritorna l'ultimo indice del frame, cioè il primo 
	 * ad essere compeltato.
	 * Un frame definisce i dati necessari a riprendere l'esecuzione 
	 * di una procedura sospesa da un waiting statement. Tali dati sono: 
	 * corpo della procedura, valori delle variabili locali, il prossimo stato da eseguire
	 */
	controlled frame_exec: Sc_Thread -> Integer
	/*
	 * Dato un processo e un indice del frame, ritorna l'indice del passo da eseguire
	 */
	controlled step_exec: Prod(Sc_Thread, Integer) -> Integer
	/*
	 * Dato un processo e un indice del frame, ritorna la regola associata al frame
	 */
	controlled run_exec: Prod(Sc_Thread, Integer) -> Rule


	/*
 	 * Creazione dei canali 
 	 */

	static lOAD: ChanBoolean
	static dIN: ChanInteger
	static dOUT: ChanInteger



definitions:

	/*
	 * Returns true if the given process is statically triggered
	 */
	function isStaticTriggered($p in Sc_Thread) =
		not(isDynTriggered($p))
	/*
	 * Dato un canale Intero, legge il valore del canale 
	 */	
	function readInt($c in ChanInteger) = valueInt($c)
	/*
	 * Dato un canale Booleano, legge il valore del canale 
	 */	
	function readBool($c in ChanBoolean) = valueBool($c)
	
	/*
	 * Inizializza l'evento dato
	 */
	macro rule r_initEvent($e in Event) =
		seq
			eventTime($e) := 0
			isPending($e) := false
		endseq
	/*
	 * Notifica l'evento
	 */
	macro rule r_notifyNow($e in Event) =
		seq
			isPending($e) := false
			forall $p in Sc_Thread do
				if isStaticTriggered($p) and isSensibleOf($p, $e) != undef then
					status($p) := RUNNABLE
				else if isDynTriggered($p) and isDynSensibleOf($p, $e) != undef then seq
					status($p) := RUNNABLE
					isDynTriggered($p) := false
					isDynSensibleOf($p, $e) := undef
				endseq endif endif
		endseq
	/*
	 * Notifica un evento all'istante indicato. Se l'istante è zero, l'evento
	 * è notificato nel prossimo delta cycle
	 */
	macro rule r_notify($e in Event, $t in Integer) =
		if isPending($e) then
			eventTime($e) := min(eventTime($e), time + $t)
		else seq
			isPending($e) := true
			eventTime($e) := time + $t
		endseq endif
	/*
	 * Aggiunge un evento alla lista di sensitività statica di un processo
	 */
	macro rule r_sensitive($p in Sc_Thread, $e in Event) =
		isSensibleOf($p, $e) := true

	/*
	 * Inizializza il processo
	 */
	macro rule r_thread($p in Sc_Thread, $prule in Rule) =
		seq
			status($p) := RUNNABLE
			isDynTriggered($p) := false
			frame_exec($p) := 0
			run_exec($p, 0) := $prule
			step_exec($p, 0) := 0
		endseq

	/*
	 * Sospende il processo dato. Il processo è riattivato secondo la sua 
	 * lista di sensitività statica.
	 */
	macro rule r_waitStatic($p in Sc_Thread) = 
		seq
			isDynTriggered($p) := false
			status($p) := SUSPENDED
		endseq
	/*
	 * Sospende il processo dato. Il processo è riattivato quando 
	 * è notificato l'evento dato.
	 */
	macro rule r_waitEvent($p in Sc_Thread, $e in Event) =
		seq
			isDynTriggered($p) := true
			isDynSensibleOf($p, $e) := true
			status($p) := SUSPENDED
		endseq
	/*
	 * Sospende il processo dato. Il processo riprende l'esecuzione quando l'evento è completato
	 */
	macro rule r_waitTimeOut($p in Sc_Thread, $t in Integer) =
		let ($events = {$ee in TimeOut| eventTime($ee) < time: $ee}) in
			if isEmpty($events) then
				extend TimeOut with $new do seq
					r_initEvent[$new]
					r_notify[$new, $t]
					r_waitEvent[$p, $new]
				endseq
			else let ($e = chooseone($events)) in seq
				// set it to not pending
				isPending($e) := false
				r_notify[$e, $t]
				r_waitEvent[$p, $e]
			endseq endlet endif
		endlet
	/*
	 * Ferma il processo dato
	 */
 	macro rule r_stop($p in Sc_Thread) =
 		status($p) := STOPPED
	/*
	 * Il processo dato non è eseguito durante la fase di inizializzazione
	 */
	macro rule r_dontInitialize($p in Sc_Thread) =
		status($p)  := SUSPENDED
	/*
	 * Inizializza il canale.
	 */
	macro rule r_initPrimChannel($c in PrimChannel, $urule in Rule(PrimChannel)) =
		extend Event with $e do seq
			r_initEvent[$e]
			defaultEvent($c) := $e
			updateRule($c) := $urule
			isOutOfDate($c) := false
		endseq
	/*
	 * Richiede un aggiornamento per il canale dato
	 */
	macro rule r_requestUpdate($c in PrimChannel) =
		isOutOfDate($c) := true
	/*
	 * Imposta come successivo stato, lo stato definito dalla data etichetta
	 */
	macro rule r_goto($label in Integer) =
		let ($proc = current_exec) in
			step_exec($proc, frame_exec($proc)) := $label
		endlet
	/*
	 * Salta a $labelTrue se la condizione è vera. Altrimenti a $labelFalse
	 */
	macro rule r_ifGoto($cond in Boolean, $labelTrue in Integer, $labelFalse in Integer) =
		if $cond then
			r_goto[$labelTrue]
		else
			r_goto[$labelFalse]
		endif
	/*
	 * Crea un nuovo frame e lo associa alla regola data 
	 */
	macro rule r_enterFrame($macroRule in Rule) =
		let ($proc = current_exec) in seq
			frame_exec($proc) := frame_exec($proc) + 1
			step_exec($proc, frame_exec($proc)) := 0
			run_exec($proc, frame_exec($proc)) := $macroRule
		endseq endlet
	/*
	 * Cancella l'ultimo frame
	 */
	macro rule r_leaveFrame =
		let ($proc = current_exec) in seq			
			step_exec($proc, frame_exec($proc)) := undef
			run_exec($proc, frame_exec($proc)) := undef
			frame_exec($proc) := frame_exec($proc) - 1
		endseq endlet
	/*
	 * Scrive sul canale Intero il valore dato
	 */	
	macro rule r_writeInt($c in ChanInteger, $exp in Integer) =
		if $exp != valueInt($c) then seq
			newValueInt($c) := $exp
			r_requestUpdate[$c]
		endseq endif
	/*
	 * Aggiorna il canale dato come canale Intero
	 */	
	macro rule r_updateInt($pc in PrimChannel) =
		let ($c = whoAmIInt($pc)) in
			if newValueInt($c) != valueInt($c) then seq
				valueInt($c) := newValueInt($c)
				r_notifyNow[defaultEvent($c)]
			endseq endif
		endlet
	/*
	 * Inizializza il canale Intero
	 */	
	macro rule r_initInt($c in ChanInteger) =
		seq
			whoAmIInt($c) := $c
			r_initPrimChannel[$c, <<r_updateInt(PrimChannel)>>]
			// initial values
			valueInt($c) := 0
			newValueInt($c) := 0
		endseq
	/*
	 * Scrive sul canale Booleano il valore dato
	 */	
	macro rule r_write($c in ChanBoolean, $exp in Boolean) =
		if $exp != valueBool($c) then seq
			newValueBool($c) := $exp
			r_requestUpdate[$c]
		endseq endif
	/*
	 * Aggiorna il canale come canale Booleano
	 */	
	macro rule r_update($pc in PrimChannel) =
		let ($c = whoAmIBool($pc)) in
			if newValueBool($c) != valueBool($c) then seq
				valueBool($c) := newValueBool($c)
				r_notifyNow[defaultEvent($c)]
				if newValueBool($c) then
					r_notifyNow[posEdgeEvent($c)]
				else
					r_notifyNow[negEdgeEvent($c)]
				endif
			endseq endif
		endlet
	/*
	 * Inizializza il canale Booleano
	 */	
	macro rule r_init($c in ChanBoolean) =
		seq
			whoAmIBool($c) := $c
			r_initPrimChannel[$c, <<r_update(PrimChannel)>>]
			extend Event with $e1, $e2 do seq
				r_initEvent[$e1]
				r_initEvent[$e2]
				posEdgeEvent($c) := $e1
				negEdgeEvent($c) := $e2
			endseq
			// initial values
			valueBool($c) := false
			newValueBool($c) := false
		endseq
				