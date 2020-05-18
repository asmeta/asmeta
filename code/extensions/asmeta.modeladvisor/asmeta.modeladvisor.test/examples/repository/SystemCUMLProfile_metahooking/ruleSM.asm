module ruleSM

	import SystemCUMLProfile_INIT
	import StandardLibrary
	import clock


	export r_initCount, r_initCount_stim, Count, Count_stim, cLOCK, u_count, u_count_stim
	
signature:
	/*
	 * Dominio Count_stim 
	 */
	dynamic abstract domain Count_stim
	/*
	 * Dominio Count
	 */
	dynamic abstract domain Count
	/*
	 * Si utilizza un passo per proseguire l'esecuzione del modulo quando il simulatore inizia
	 * una nuova transizione. Con la nuova transizione si deve saltare la fase di init
	 * affinché non si risetti lo stato corrente allo stato iniziale
	 */
	 controlled initializedContext: Sc_Thread -> Boolean
	/*
	 * Restituisce il count_stim che ha generato il processo 
	 */
	controlled prop: Sc_Thread -> Count
	/*
	 * Restituisce il count_stim che ha generato il processo 
	 */
	controlled propCS: Sc_Thread -> Count_stim
	/*
	 * Restituisce il canale booleano associato al Count_stim 
	 */
	controlled load: Count_stim -> ChanBoolean
	/*
	 * Restituisce il clock associato al Count_stim 
	 */
	controlled clock: Count_stim -> Clock
	/*
	 * Restituisce il canale Intero in ingresso al Count_stim 
	 */
	controlled din: Count_stim -> ChanInteger
	/*
	 * Restituisce il canale Intero in uscita al Count_stim 
	 */
	controlled dout: Count_stim -> ChanInteger
	/*
	 * Definisce il valore massimo associato al Count_stim 
	 */
	controlled maxcount: Count_stim -> Integer
	/*
	 * Restituisce il Processo (facendo distinzione fra i due domini Process/Sc_Thread) associato al Count_stim 
	 */
	controlled ownerC: Process -> Count_stim
	/*
	 * Restituisce il canale booleano associato al Count 
	 */
	controlled load: Count -> ChanBoolean
	/*
	 * Restituisce il clock associato al Count
	 */
	controlled clock: Count -> Clock
	/*
	 * Restituisce il canale Intero in ingresso al Count
	 */
	controlled din: Count -> ChanInteger
	/*
	 * Restituisce il canale Intero in uscita al Count 
	 */
	controlled dout: Count -> ChanInteger
	/*
	 * Setta il valore del Count
	 */
	controlled count_val: Count -> Integer
	/*
	 * Restituisce il Processo associato al Count
	 */
	controlled ownerCount: Process -> Count
	/*
	 * Restituisce l'evento completo associato al processo
	 */
	controlled completionEvent: Process -> Event
	
	/*
 	 * Inizializzazione degli oggetti:
	 */
	 /*
	  * Per i processi
	  */
	  static p: Sc_Thread
	  static h: Sc_Thread
	/* Le due regioni associate alle due macchine*/
	static reg1: Region 
	static reg1_1: Region
	static reg2: Region 
	static reg2_2: Region
	/*Macchina Thread*/
	static sm_Thread: StateMachine 
	/*Macchina Method*/
	static sm_Method: StateMachine
	/*Transizioni della macchina Thread*/
	static t01: Transition
	static t02: Transition
	static t03: Transition
	static t04: Transition
	static t05: Transition
	static t06: Transition
	static t07: Transition
	static t08: Transition
	static t09: Transition
	static t10: Transition
	static t11: Transition
	static t12: Transition
	/*Transizioni della macchina Method*/
	static t13: Transition
	static t14: Transition
	static t15: Transition
	static t16: Transition
	static t17: Transition
	static t18: Transition
	static t19: Transition
	static t20: Transition
	static t21: Transition
	static t22: Transition
	static t23: Transition
	static t24: Transition 
	
	/*Stati della macchina Thread*/
	static st01: State
	static st02: State
	static st03: State
	static st04: State
	static st05: State
	static st06: State
	/*Stati della macchina Method*/
	static st07: State
	static st08: State
	static st09: State
	static st10: State
	static st11: State
	static st12: State
	/*Stato finale della macchina Thread*/
	static pfinal: FinalState 
	/*Stato finale della macchina Method*/
	static pfinal2: FinalState
	/*Pseudostato If della macchina Thread*/
	static pif: If
	/*Pseudostato If della macchina Method*/
	static pif2: If
	/*Pseudostato finale dell'If della macchina Thread*/
	static pendif: Endif
	/*Pseudostato finale dell'If della macchina Method*/
	static pendif2: Endif 
	/*Pseudostato while della macchina Thread*/
	static pwhile: While
	/*Pseudostato while della macchina Method*/
	static pwhile2: While 
	/*Pseudostati iniziali della macchina Thread*/
	static pinit1: Initial
	static pinit2: Initial
	/*Pseudostati iniziali della macchina Method*/
	static pinit3: Initial
	static pinit4: Initial 
	/*Creazione di eventi per Trigger*/
	static evTrigger: Trigger
	/*Creazione dell'oggetto Clock*/
	static cLOCK: Clock
	/*Creazione dell'oggetto Count*/
	static u_count: Count
	/*Creazione dell'oggetto Count_stim*/
	static u_count_stim: Count_stim
	
	/*Prove di inizializzazione*/
	/*Oggetti di Constraint, dovranno essere inizializzati per ogni transizione a guard(transizione) := X*/
	static cons01: Constraint
	static cons02: Constraint
	static cons03: Constraint
	static cons04: Constraint
	static cons05: Constraint
	static cons06: Constraint
	static cons07: Constraint
	static cons08: Constraint
	static cons09: Constraint
	static cons10: Constraint
	static cons11: Constraint
	static cons12: Constraint
	static cons13: Constraint
	static cons14: Constraint
	static cons15: Constraint
	static cons16: Constraint
	static cons17: Constraint
	static cons18: Constraint
	static cons19: Constraint
	static cons20: Constraint
	static cons21: Constraint
	static cons22: Constraint
	static cons23: Constraint
	static cons24: Constraint
	
	/*OpaqueExpression*/
	static oggexpelse: OpaqueExpression
	static oggexptrue: OpaqueExpression
	static oggexpload: OpaqueExpression
	static oggexpdout: OpaqueExpression
	static oggexpfals: OpaqueExpression
	
	/*OpaqueBehavior*/
	static oggbehatru: OpaqueBehavior
	static oggbehafal: OpaqueBehavior
	static oggbehadin: OpaqueBehavior
	static oggbehaval: OpaqueBehavior
	static oggbehadout: OpaqueBehavior
	
	
definitions:	 
/*Definisce se la guardia è attiva o non attiva*/	 
function eval($v in Constraint, $t in Transition) = 
if body(asOpaqueExpression(specification(guard($t)))) = "true" 
	and contains(currState(selfp, currStateMachine(selfp)),source($t))
then true
	else 
if //(
    body(asOpaqueExpression(specification(guard($t)))) = "else" 
	//and (forall $x in excluding(outgoing(source($t)),$t) with eval(guard($x),$t)= false))	
	/*Questa esclusione perchè in realtà le altre transizioni continuano a verificare le rispettive condizioni
	e quindi sono valutate a vero se controllate. Per ora eval(guard($x),$t)= false non è usato*/
then true
	else 
if body(asOpaqueExpression(specification(guard($t)))) = "dout" 
	and (valueInt(dout(propCS(selfp)))) = (maxcount(propCS(selfp)) - 1) 
then 
    true //una volta computato il caso true la guardia andrebbe settata a false, così da uscire dal while
	else 
if body(asOpaqueExpression(specification(guard($t)))) = "load" 
	and (valueBool(load(ownerCount(selfp))) = true)
then true
endif endif 
endif endif


	/*
	 *Regole di inizializzazione della segnatura indotta dalla porzione di metamodello Uml relativa alle macchina di stato
	 */
	 
	 /*Per la macchina Thread*/
	 macro rule r_init_thread($p in Sc_Thread) =
	 seq
	 	initializedContext($p) := true
	 	selfp := $p
	 //Per le Transizioni 
	 	isAnd(t01):= false
	 	isAnd(t02):= false
	 	isAnd(t03):= false
	 	isAnd(t04):= false
	 	isAnd(t05):= false
	 	isAnd(t06):= false
	 	isAnd(t07):= false
	 	isAnd(t08):= false
	 	isAnd(t09):= false
	 	isAnd(t10):= false
	 	isAnd(t11):= false
	 	isAnd(t12):= false
	 	kind(t01) := EXTERNAL
	 	kind(t02) := EXTERNAL
	 	kind(t03) := EXTERNAL
	 	kind(t04) := EXTERNAL
	 	kind(t05) := EXTERNAL
	 	kind(t06) := EXTERNAL
	 	kind(t07) := EXTERNAL
	 	kind(t08) := EXTERNAL
	 	kind(t09) := EXTERNAL
	 	kind(t10) := EXTERNAL
	 	kind(t11) := EXTERNAL
	 	kind(t12) := EXTERNAL
	 	andNotHist(selfp, t01) := {}
	 	andNotHist(selfp, t02) := {}
	 	andNotHist(selfp, t03) := {}
	 	andNotHist(selfp, t04) := {}
	 	andNotHist(selfp, t05) := {}
	 	andNotHist(selfp, t06) := {}
	 	andNotHist(selfp, t07) := {}
	 	andNotHist(selfp, t08) := {}
	 	andNotHist(selfp, t09) := {}
	 	andNotHist(selfp, t10) := {}
	 	andNotHist(selfp, t11) := {}
	 	andNotHist(selfp, t12) := {}
	 //Per gli pseudostati
	 	kindIn(pinit1) := INITIAL
	 	kindIn(pinit2) := INITIAL
	 	kindT(pfinal) := TERMINATE
	 	//I seguenti pseudostati sono di tipo Juction/Choice, 
	 	//ma li definisco solo Junction
	 	kindIf(pif) := JUNCTION
	 	kindEIf(pendif) := JUNCTION
	 	kindW(pwhile) := JUNCTION
	 //Per la macchina Thread
	 	propCS(selfp) := u_count_stim
	 	sensitive($p) := {posEdgeEvent(lOAD)}
	 	dont_initialize($p) := false
	 	eventqueue($p) := []
	 	eventqueue($p) := [posEdgeEvent(lOAD)]
	 	nextAfterContinue($p) := []
	 	nextAfterBreak($p) := []
	 	elseTrans(pwhile) := t05
	 	elseTrans(pif) := t08
	 	baseStateMachine($p) := sm_Thread
	 	currStateMachine($p) := baseStateMachine(selfp)
	 	callMachine($p, sm_Thread) := sm_Thread
	 	currState($p, sm_Thread) := {pinit1}
		deepest($p, sm_Thread) := pinit1
		exitRequest($p):= false 
		
	 //Per le guardie
	 	asOpaqueExpression(oggexpelse) := oggexpelse
	 	asOpaqueExpression(oggexptrue) := oggexptrue
	 	asOpaqueExpression(oggexpdout) := oggexpdout
	 	asOpaqueExpression(oggexpfals) := oggexpfals
	 	
	 	body(asOpaqueExpression(oggexpelse)) := "else"
	 	body(asOpaqueExpression(oggexptrue)) := "true"
	 	body(asOpaqueExpression(oggexpdout)) := "dout"
		
		specification(cons01) := oggexptrue
		specification(cons02) := oggexptrue
		specification(cons03) := oggexptrue
		specification(cons04) := oggexptrue
		specification(cons05) := oggexpelse
		specification(cons08) := oggexpelse
		specification(cons06) := oggexptrue
		specification(cons07) := oggexptrue
		specification(cons09) := oggexptrue
		specification(cons10) := oggexpdout
		specification(cons11) := oggexptrue
		specification(cons12) := oggexptrue
		
		//Anche le transizioni che non hanno guardie esplicite devono avere un valore altrimenti 
		//non si uscirà mai dal loro stato, es. stato iniziale
	 	guard(t01) := cons01 
	 	guard(t02) := cons02 
	 	guard(t03) := cons03 
	 	guard(t04) := cons04 
	 	guard(t05) := cons05 
	 	guard(t06) := cons06
	 	guard(t07) := cons07
	 	guard(t08) := cons08
	 	guard(t09) := cons09
	 	guard(t10) := cons10
		guard(t11) := cons11 
		guard(t12) := cons12 
		
	 //Per Region
		statemachine(reg1) := sm_Thread
		statemachine(reg1_1) := sm_Thread
		transition(reg1) := {t01, t02}
		transition (reg1_1):= {t03, t04, t05, t06, t07, t08, t09, t10, t11, t12}
		event(evTrigger) := posEdgeEvent(owner($p))
		trigger(t01) := {}
		trigger(t02) := {evTrigger}
		trigger(t03) := {}
		trigger(t04) := {}
		trigger(t05) := {}
		trigger(t06) := {}
		trigger(t07) := {evTrigger}
		trigger(t08) := {}
		trigger(t09) := {}
		trigger(t10) := {}
		trigger(t11) := {}
		trigger(t12) := {}
		state(reg1) := {st01, st02}
		state(reg1_1) := {st03, st04, st05, st06}	 
		subvertex(reg1) := {pinit1, st01, st02}
		subvertex(reg1_1) := {pinit2, st03, pwhile, pfinal, st04, pif, st05, st06, pendif}
		stateinit(st02) := pinit2
		asState(st01):= st01
		asState(st02):= st02
		asState(st03):= st03
		asState(st04):= st04
		asState(st05):= st05
		asState(st06):= st06
		asStateIn(pinit1) := pinit1
		asStateIn(pinit2) := pinit2
		asStateW(pwhile) := pwhile
		asStateIf(pif) := pif
		asStateEif(pendif) := pendif
		upState(pinit2) := st02
		upState(st03) := st02
		upState(pwhile) := st02
		upState(pfinal) := st02
		upState(st04) := st02
		upState(pif) := st02
		upState(st05) := st02
		upState(st06) := st02
		upState(pendif) := st02
		downState(st02) := {pinit2, st03, pwhile, pfinal, st04, pif, st05, st06, pendif}
	 //Per StateMachine
		submachineState(sm_Thread) := {st01, st02, st03, st04, st05, st06}
		region(sm_Thread) := reg1
	 //Per Transition
		containerT(t01) := reg1
		containerT(t02) := reg1
		containerT(t03) := reg1
		containerT(t04) := reg1
		containerT(t05) := reg1
		containerT(t06) := reg1
		containerT(t07) := reg1
		containerT(t08) := reg1
		containerT(t09) := reg1
		containerT(t10) := reg1
		containerT(t11) := reg1
		containerT(t12) := reg1

		source(t01):= pinit1
	 	source(t02):= st01
	 	source(t03):= pinit2
	 	source(t04):= st03
	 	source(t05):= pwhile
	 	source(t06):= pwhile
	 	source(t07):= st04
	 	source(t08):= pif
	 	source(t09):= st05
	 	source(t10):= pif
	 	source(t11):= st06
	 	source(t12):= pendif
	 		 	
	 	target(t01):= st01
	 	target(t02):= st02
	 	target(t03):= st03
	 	target(t04):= pwhile
	 	target(t05):= pfinal
	 	target(t06):= st04
	 	target(t07):= pif
	 	target(t08):= st05
	 	target(t09):= pendif
	 	target(t10):= st06
	 	target(t11):= pendif
	 	target(t12):= pwhile

		
	 // Per Vertex
	 container(pinit1) := reg1 
	 container(st01) := reg1
	 container(st02) := reg1
	 container(pinit2) := reg1_1
	 container(st03) := reg1_1
	 container(pwhile) := reg1_1
	 container(pfinal) := reg1_1
	 container(st04) := reg1_1
	 container(pif) := reg1_1
	 container(st05) := reg1_1
	 container(st06) := reg1_1
	 container(pendif) := reg1_1
	 
	 region(st02) := reg1_1

	 outgoing(pinit1) := {t01}
	 outgoing(st01) := {t02}
	 outgoing(st02) := {}
	 outgoing(pinit2) := {t03}
	 outgoing(st03) := {t04}
	 outgoing(pwhile) := {t05, t06}
	 outgoing(pfinal) := {}
	 outgoing(st04) := {t07}
	 outgoing(pif) := {t08, t10}
	 outgoing(st05) := {t09}
	 outgoing(st06) := {t11}
	 outgoing(pendif) := {t12}
	 	 
	 incoming(pinit1) := {}
	 incoming(st01) := {t01}
	 incoming(st02) := {t02}
	 incoming(pinit2) := {}
	 incoming(st03) := {t03}
	 incoming(pwhile) := {t04, t12}
	 incoming(pfinal) := {t05}
	 incoming(st04) := {t06}
	 incoming(pif) := {t07}
	 incoming(st05) := {t08}
	 incoming(st06) := {t10}
	 incoming(pendif) := {t09, t11}

	 subMachine(st01) := sm_Thread
	 subMachine(st02) := sm_Thread
	 subMachine(st03) := sm_Thread
	 subMachine(st04) := sm_Thread
	 subMachine(st05) := sm_Thread
	 subMachine(st06) := sm_Thread
	 	  
	 // Per State
	 isStatic_Wait(st01) := false
	 isStatic_Wait(st02) := false
	 isStatic_Wait(st03) := false
	 isStatic_Wait(st04) := true
	 isStatic_Wait(st05) := false
	 isStatic_Wait(st06) := false
	 		 
	 forall $state in State do 
	 seq
	 	isWait($state):= false
	 	isReturn($state):= false
	 	isBreak($state) := false
	 	isContinue($state) := false
	 endseq
	 
	 isDont_Initialize(st01) := true
	 isDont_Initialize(st02) := false
	 isDont_Initialize(st03) := false
	 isDont_Initialize(st04) := false
	 isDont_Initialize(st05) := false
	 isDont_Initialize(st06) := false
	 
	// Per il comportamento interno dello stato	
	 doActivity(st03) := oggbehatru
	 doActivity(st05) := oggbehafal
	 doActivity(st06) := oggbehatru

	 asOpaqueBehavior(oggbehatru) := oggbehatru
	 asOpaqueBehavior(oggbehafal) := oggbehafal
	
	 body(asOpaqueBehavior(doActivity(st03))) := "load = true; din = 0;"
	 body(asOpaqueBehavior(doActivity(st05))) := "load = false;"
	 body(asOpaqueBehavior(doActivity(st06))) := "load = true; din = 0;"
	 
	 terminated(doActivity(st01), st01) := true
	 terminated(doActivity(st02), st02) := false
	 terminated(doActivity(st03), st03) := false
	 terminated(doActivity(st04), st04) := true
	 terminated(doActivity(st05), st05) := false
	 terminated(doActivity(st06), st06) := false
	 result:= print("finito_inizializzare_thread")
	 endseq
	 
	 /*Per la macchina Method*/
	 macro rule r_init_method($p in Sc_Thread) =
	 seq
	 	initializedContext($p) := true
	 	selfp := $p
	 //Per le transizioni
	 	isAnd(t13):= false
	 	isAnd(t14):= false
	 	isAnd(t15):= false
	 	isAnd(t16):= false
	 	isAnd(t17):= false
	 	isAnd(t18):= false
	 	isAnd(t19):= false
	 	isAnd(t20):= false
	 	isAnd(t21):= false
	 	isAnd(t22):= false
	 	isAnd(t23):= false
	 	isAnd(t24):= false
	 	kind(t13) := EXTERNAL
	 	kind(t14) := EXTERNAL
	 	kind(t15) := EXTERNAL
	 	kind(t16) := EXTERNAL
	 	kind(t17) := EXTERNAL
	 	kind(t18) := EXTERNAL
	 	kind(t19) := EXTERNAL
	 	kind(t20) := EXTERNAL
	 	kind(t21) := EXTERNAL
	 	kind(t22) := EXTERNAL
	 	kind(t23) := EXTERNAL
	 	kind(t24) := EXTERNAL
	 	andNotHist(selfp, t13) := {}
	 	andNotHist(selfp, t14) := {}
	 	andNotHist(selfp, t15) := {}
	 	andNotHist(selfp, t16) := {}
	 	andNotHist(selfp, t17) := {}
	 	andNotHist(selfp, t18) := {}
	 	andNotHist(selfp, t19) := {}
	 	andNotHist(selfp, t20) := {}
	 	andNotHist(selfp, t21) := {}
	 	andNotHist(selfp, t22) := {}
	 	andNotHist(selfp, t23) := {}
	 	andNotHist(selfp, t24) := {}
	 	kindIn(pinit3) := INITIAL
	 	kindIn(pinit4) := INITIAL
	 	kindT(pfinal2) := TERMINATE
	 	kindIf(pif2) := JUNCTION
	 	kindEIf(pendif2) := JUNCTION
	 	kindW(pwhile2) := JUNCTION

	 //Per la macchina Method
		prop(selfp) := u_count
	 	sensitive($p) := {posEdgeEvent(lOAD)}
	 	dont_initialize($p) := false
	 	eventqueue($p) := []
	 	eventqueue($p) := [posEdgeEvent(lOAD)]
	 	nextAfterContinue($p) := []
	 	nextAfterBreak($p) := []
	 	elseTrans(pwhile2) := t16
	 	elseTrans(pif2) := t20
	 	baseStateMachine($p) := sm_Method
	 	currStateMachine($p) := baseStateMachine(selfp)
	 	callMachine($p, sm_Method) := sm_Method
	 	currState($p, sm_Method) := {pinit3}
		deepest($p, sm_Method) := pinit3
		exitRequest($p):= false
		
		asOpaqueExpression(oggexpelse) := oggexpelse
	 	asOpaqueExpression(oggexptrue) := oggexptrue
	 	asOpaqueExpression(oggexpload) := oggexpload
	 	
	 	body(asOpaqueExpression(oggexpelse)) := "else"
	 	body(asOpaqueExpression(oggexptrue)) := "true"
	 	body(asOpaqueExpression(oggexpload)) := "load"
	 	
	 	specification(cons13) := oggexptrue
	 	specification(cons14) := oggexptrue
	 	specification(cons15) := oggexptrue
	 	specification(cons16) := oggexpelse
	 	specification(cons20) := oggexpelse
	 	specification(cons17) := oggexptrue
	 	specification(cons18) := oggexpload
	 	specification(cons19) := oggexptrue
	 	specification(cons21) := oggexptrue
	 	specification(cons22) := oggexptrue
	 	specification(cons23) := oggexptrue
	 	specification(cons24) := oggexptrue
	 	
	 	guard(t13) := cons13
	 	guard(t14) := cons14
	 	guard(t15) := cons15
	 	guard(t16) := cons16
	 	guard(t17) := cons17
	 	guard(t18) := cons18
	 	guard(t19) := cons19
	 	guard(t20) := cons20
	 	guard(t21) := cons21
	 	guard(t22) := cons22
	 	guard(t23) := cons23
	 	guard(t24) := cons24
	 	
	 	statemachine(reg2) := sm_Method
		transition(reg2) := {t13, t14}
		transition(reg2_2) := {t15, t16, t17, t18, t19, t20, t21, t22, t23, t24}
		event(evTrigger) := posEdgeEvent(owner($p))
		trigger(t13) := {}
		trigger(t14) := {evTrigger}
		trigger(t15) := {}
		trigger(t16) := {}
		trigger(t17) := {}
		trigger(t18) := {}
		trigger(t19) := {}
		trigger(t20) := {}
		trigger(t21) := {}
		trigger(t22) := {}
		trigger(t23) := {}
		trigger(t24) := {evTrigger}
		state(reg2) := {st07}
		state(reg2_2) := {st08, st09, st10, st11, st12}	
		asState(st07):= st07
		asState(st08):= st08
		asState(st09):= st09
		asState(st10):= st10
		asState(st11):= st11
		asState(st12):= st12
		asStateIn(pinit3) := pinit3
		asStateIn(pinit4) := pinit4
		asStateW(pwhile2) := pwhile2
		asStateFi(pfinal2) := pfinal2
		asStateIf(pif2) := pif2
		asStateEif(pendif2) := pendif2 
		upState(pinit4) := st08
		upState(st09) := st08
		upState(pwhile2) := st08
		upState(pfinal2) := st08
		upState(st10) := st08
		upState(pif2) := st08
		upState(st11) := st08
		upState(st12) := st08
		upState(pendif2) := st08
		downState(st08) := {pinit4, pwhile2, pfinal2, pif2, st09, st10, pendif2, st11, st12}
		subvertex(reg2_2) := {pinit4, pwhile2, pfinal2, pif2, st09, st10, pendif2, st11, st12}		
		subvertex(reg2) := {pinit3, st07, st08}
	 	submachineState(sm_Method) := {st07, st08, st09, st10, st11, st12}
		region(sm_Method) := reg2
		containerT(t13) := reg2
		containerT(t14) := reg2
		containerT(t15) := reg2
		containerT(t16) := reg2
		containerT(t17) := reg2
		containerT(t18) := reg2
		containerT(t19) := reg2
		containerT(t20) := reg2
		containerT(t21) := reg2
		containerT(t22) := reg2
		containerT(t23) := reg2
		containerT(t24) := reg2
		
	 	source(t13):= pinit3
	 	source(t14):= st07
	 	source(t15):= pinit4
	 	source(t16):= pwhile2
	 	source(t17):= pwhile2
	 	source(t18):= pif2
	 	source(t19):= st09
	 	source(t20):= pif2
	 	source(t21):= st10
	 	source(t22):= pendif2
	 	source(t23):= st11
	 	source(t24):= st12
	 
	 	target(t13):= st07
	 	target(t14):= st08
	 	target(t15):= pwhile2
	 	target(t16):= pfinal2
	 	target(t17):= pif2
	 	target(t18):= st09
	 	target(t19):= pendif2
	 	target(t20):= st10
	 	target(t21):= pendif2
	 	target(t22):= st11
	 	target(t23):= st12
	 	target(t24):= pwhile2
	 
	 	container(pinit3) := reg2 
	 	container(st07) := reg2
	 	container(st08) := reg2
	 	container(pinit4) := reg2_2
	 	container(pwhile2) := reg2_2
	 	container(pfinal2) := reg2_2
	 	container(pif2) := reg2_2
	 	container(st09) := reg2_2
	 	container(st10) := reg2_2
	 	container(pendif2) := reg2_2
	 	container(st11) := reg2_2
	 	container(st12) := reg2_2
	 	
	 	outgoing(pinit3) := {t13}
	 	outgoing(st07) := {t14}
	 	outgoing(st08) := {}
	 	outgoing(pinit4) := {t15}
	 	outgoing(pwhile2) := {t16, t17}
	 	outgoing(pfinal2) := {}
	 	outgoing(pif2) := {t18, t20}
	 	outgoing(st09) := {t19}
	 	outgoing(st10) := {t21}
	 	outgoing(pendif2) := {t22}
	 	outgoing(st11) := {t23}
	 	outgoing(st12) := {t24}	 
	 		
	 	incoming(pinit3) := {}
	 	incoming(st07) := {t13}
	 	incoming(st08) := {t14}
	 	incoming(pinit4) := {}
	 	incoming(pwhile2) := {t15}
	 	incoming(pfinal2) := {t16}
	 	incoming(pif2) := {t17}
	 	incoming(st09) := {t18}
	 	incoming(st10) := {t20}
	 	incoming(pendif2) := {t21}
	 	incoming(st11) := {t22}
	 	incoming(st12) := {t23}
	            stateinit(st08) := pinit4
	 	region(st08) := reg2_2
	 	
	 	subMachine(st07) := sm_Method
	 	subMachine(st08) := sm_Method
	 	subMachine(st09) := sm_Method
	 	subMachine(st10) := sm_Method
	 	subMachine(st11) := sm_Method
	 	subMachine(st12) := sm_Method

	//Per State
	 isStatic_Wait(st07) := false
	 isStatic_Wait(st08) := false
	 isStatic_Wait(st09) := false
	 isStatic_Wait(st10) := false
	 isStatic_Wait(st11) := false
	 isStatic_Wait(st12) := true
	 
	 forall $state in State do 
	 seq
	 	isWait($state):= false
	 	isReturn($state):= false
	 	isBreak($state) := false
	 	isContinue($state) := false
	 endseq
	 
	 isDont_Initialize(st07) := true
	 isDont_Initialize(st08) := false
	 isDont_Initialize(st09) := false
	 isDont_Initialize(st10) := false
	 isDont_Initialize(st11) := false
	 isDont_Initialize(st12) := false

	// Per il comportamento interno dello stato
	 doActivity(st09) := oggbehadin
	 doActivity(st10) := oggbehaval
	 doActivity(st11) := oggbehadout

 	 asOpaqueBehavior(oggbehadin) := oggbehadin
	 asOpaqueBehavior(oggbehaval) := oggbehaval
	 asOpaqueBehavior(oggbehadout) := oggbehadout
	
	 body(asOpaqueBehavior(doActivity(st09))) := "count_val = din;"
	 body(asOpaqueBehavior(doActivity(st10))) := "count_val++;"
	 body(asOpaqueBehavior(doActivity(st11))) := "dout = count_val;"
	 
	 terminated(doActivity(st07), st07) := true
	 terminated(doActivity(st08), st08) := false
	 terminated(doActivity(st09), st09) := false
	 terminated(doActivity(st10), st10) := false
	 terminated(doActivity(st11), st11) := false
	 terminated(doActivity(st12), st12) := true
	 result:= print("finito_inizializzare_method")
	 endseq
	
	 
	/*
	 * Regola per la gestione del comportamento interno allo stato
	 */
	 macro rule r_stringToRule($s in State) =
	if body(asOpaqueBehavior(doActivity($s))) = "count_val = din;" then 
	 count_val(ownerCount(selfp)) := readInt(din(ownerCount(selfp))) 
	else 
	if body(asOpaqueBehavior(doActivity($s))) = "count_val++;" then 
	count_val(ownerCount(selfp)) := count_val(ownerCount(selfp)) + 1 
	else
	if body(asOpaqueBehavior(doActivity($s))) = "dout = count_val;" then 
	r_writeInt[dout(ownerCount(selfp)), count_val(ownerCount(selfp))] 
	else
 	if body(asOpaqueBehavior(doActivity($s))) = "load = true; din = 0;" then 
 	seq r_write[load(propCS(selfp)), true]
		r_writeInt[din(propCS(selfp)), 0] 
	endseq 
	else
 	if body(asOpaqueBehavior(doActivity($s))) = "load = false;" then 
 	r_write[load(propCS(selfp)), false]  	
 	else
 	if body(asOpaqueBehavior(doActivity($s))) = "load = true; din = 0;" then 
 	seq r_write[load(propCS(selfp)), true]
	r_writeInt[din(propCS(selfp)), 0] 
	endseq
	endif endif endif
	endif endif endif
	
	
	/*
	 * Regola per l'ingresso in un nuovo stato, include il nuovo stato nel currState
	 */
	macro rule r_enterNextState($v in Vertex) = 
	seq
	currState(selfp, currStateMachine(selfp)) := including(currState(selfp, currStateMachine(selfp)), $v)
	result := print("il_Nuovo_stato_corrente:", currState(selfp, currStateMachine(selfp)))
	endseq
	/*
	 * Regola per l'uscita da uno stato
	 */
	macro rule r_exitState($s in Vertex, $t in Vertex) =
	if isPseudostate($s) then currState(selfp, currStateMachine(selfp)) := excluding(currState(selfp, currStateMachine(selfp)), $s)
	else
	if isUndef($t) //lo stato $s è semplice non composto
    then currState(selfp, currStateMachine(selfp)) := excluding(currState(selfp, currStateMachine(selfp)), $s) else
	forall $p in asSet(upChain(deepest(selfp, currStateMachine(selfp)), $t)) with isUndef(isPseudostate($p)) do
	currState(selfp, currStateMachine(selfp)) := excluding(currState(selfp, currStateMachine(selfp)), $p) endif endif
    
	/*
	 * Regola per l'esecuzione del comportamento interno
	 */
	macro rule r_execute($s in State) = 
	if isPseudostate($s) then 
	terminated(doActivity($s), $s):= true
	else 
	seq
		r_stringToRule[$s]
		terminated(doActivity($s), $s):= true
	endseq
	endif

	/*
	 * Regola per l'ingresso in un nuovo stato, include il nuovo stato nel currState
	 */
	macro rule r_enterState($s in Vertex, $t in Vertex) =
	seq
	result := print("Ingresso_nuovo_stato")
	forall $p in asSet(downChain($s, $t)) do
	seq
	r_enterNextState[asState($p)]
	if isDef(asState($p)) and isSimple(asState($p))//cioè se $p è uno stato ed è semplice
		then                                      //con un comportamento interno da eseguire
		r_execute[asState($p)]
	endif
	endseq endseq

	/*
	 * Regola per l'ingresso in uno stato composto
	 */		
	macro rule r_enterCompositeState($s in State) =
	seq
		r_enterNextState[$s]
		r_execute[$s]
		r_enterNextState[stateinit($s)]
	endseq

	/*
	 * Regola per l'ingresso in uno stato semplice
	 */
	macro rule r_enterSimpleState($s in State) =
	seq
		r_enterNextState[$s]
		r_execute[$s]
	endseq
	
	/*
	 * Regola per l'ingresso in uno stato Loop
	 */	
	macro rule r_enterLoopState($v in Vertex) =
	seq
		r_enterNextState[$v]
		nextAfterBreak(selfp) := push(target(elseTrans(asStateW($v))), nextAfterBreak(selfp))
		nextAfterContinue(selfp) := push($v, nextAfterContinue(selfp))
	endseq
	
	/*
	 * Regola per l'ingresso in uno stato Return, non usata
	 */
	macro rule r_enterReturnState($v in Vertex) = if 
	currStateMachine(selfp) = baseStateMachine(selfp)
	then currStateMachine(selfp) := undef
		else currStateMachine(selfp) := callMachine(selfp, currStateMachine(selfp))
	endif

	/*
	 * Regola per l'ingresso in uno stato Break, non usata
	 */
	macro rule r_enterBreakState($v in Vertex) = 
	seq
		r_enterNextState[top(nextAfterBreak(selfp))]
		nextAfterBreak(selfp) := pop(nextAfterBreak(selfp))
	endseq
	
	/*
	 * Regola per l'ingresso in uno stato Exit
	 */	
	macro rule r_enterExitState($v in Vertex) = 
	seq
		exitRequest(selfp):= true
		currStateMachine(selfp) := undef
	endseq
	
	/*
	 * Regola per l'ingresso in uno stato Wait
	 */	
	macro rule r_enterWaitState($s in State) = 
	seq
		r_enterNextState[$s]
		if (exist $t in outgoing($s) with (exist $e1 in trigger($t), $e2 in Event with event($e1) = $e2 //and contains(TimeEvent, $e2)
		))
		then
			extend TimeEvent with $e do
			seq
			eventTime($e) := time + eventTime(event($e1))
		    forall $trans in outgoing($s), $ev in Trigger with contains(trigger($trans), $ev) and 
			 event($ev) = $e do
				process($e):= including(process($e), current_exec)
			isRelative($e):= false
		    endseq				
		endif
	endseq
	
	/*
	 * Regola per l'esecuzione della macchina
	 */
	macro rule r_stateMachineExecution($t in Transition) =
	seq
	result := print("Nella_StatemachineExecution")
	result := print("Stato_corrente_inizio_statemachine:", currState(selfp,currStateMachine(selfp)))
	r_exitState[source($t), toS($t)]
	result := print("Stato_corrente_dopo_exit:", currState(selfp,currStateMachine(selfp)))
	result := print("Target",target($t))
    result := print(fromS($t))
	r_enterState[fromS($t), target($t)]
	result := print("Stato_corrente_dopo_enter", currState(selfp,currStateMachine(selfp)))
    if isWhile(asStateW(target($t))) then r_enterLoopState[asStateW(target($t))] else
    if isIf(asStateIf(target($t))) then r_enterNextState[asStateIf(target($t))] else
    if isEndif(asStateEif(target($t))) then r_enterNextState[asStateEif(target($t))] else
    if isFinal(asStateFi(target($t))) then r_enterNextState[asStateFi(target($t))] else
	if isStatic_Wait(asState(target($t))) then r_enterNextState[asState(target($t))] else
	if isDont_Initialize(target($t)) then r_enterNextState[target($t)] else
	if isSimple(asState(target($t))) then r_enterSimpleState[asState(target($t))] else
	if isComposite(asState(target($t))) then r_enterCompositeState[asState(target($t))] else
	if isSubmachine(asState(target($t))) then r_enterCompositeState[asState(target($t))] else
	if isReturn(asState(target($t))) then r_enterReturnState[asState(target($t))] //else
	//Questi sono tutti casi non usati, per la'ttuale simulazione, devono solo essere sistemati dal punto di vista di asStateX
	//if isWait(asState(target($t))) then r_enterWaitState[asState(target($t))] else
	//if isBreak(asState(target($t))) then r_enterBreakState[asState(target($t))] else
//if isExit(asState(target($t))) then r_enterExitState[asState(target($t))] else
//if isEndswitch(asState(target($t))) then r_enterNextState[asState(target($t))] else 
//else if isDowhile(asState(target($t))) then r_enterLoopState[asState(target($t))] else
//if isFor(asState(target($t))) then r_enterLoopState[asState(target($t))] 
endif endif endif endif endif endif endif endif endif endif //endif endif // endif endif endif 
endseq // endif



/*****************--------Regole per il main------------***************************/
/*
 * Regola per l'attivazione della transizione
 */
macro rule r_TransitionSelection($p in Sc_Thread) = 
seq
	result := print("Inizio_transition")
	result := print("isCompletionEvent_di_dispatched",dispatched($p),isCompletionEvent(dispatched($p)))
	if isUndef(isCompletionEvent(dispatched($p))) 
	then
		seq
		eventqueue($p) := tail(eventqueue($p))
		result := print("estratto_l'evento_la_coda_è:",eventqueue($p))
		endseq
	else
		seq
            //eventqueue($p) := tail(eventqueue($p)) -- dà eccezione
		result := print("stato_corrente:", currState(selfp,currStateMachine($p)))
		let ($e = dispatched($p)) in
		forall $st in currState(selfp,currStateMachine($p)) do 
		choose $t in outgoing($st) with transitionenabled($t, $e, selfp) 
		do
		seq
			result := print("transitionenabled",transitionenabled($t, $e, selfp))
			result := print("triggering", triggering($t, $e))
			result := print("source_transizione:",source($t))
			r_stateMachineExecution[$t]
			result := print("Dopo_statemachineexec")
			result := print("stato_corrente",currState(selfp,currStateMachine($p)))
		if isAnd($t) 
			then andNotHist($p,$t) := undef 
		endif 
		endseq 
	ifnone
	if isAndWait(asState($st),$e) 
		then
	  		seq
	  		result := print("currState_è_isAndWait")
	  		andNotHist(selfp,$t) := including(andNotHist($p,$t), $e)
	  		endseq 
	 endif
	endlet endseq endif endseq

/*
 * Regola per la generazione di eventi completi
 */
macro rule r_GenerateCompletionEvent($p in Sc_Thread) = 
seq 
	result := print("CS_passato_alla_GenerateCompletion:", currState($p, currStateMachine($p)))
	forall $v in (currState($p, currStateMachine($p))) with completed($v)  
	do 
	seq
		result := print("CS_completo:",$v) 
		extend Event with $new do
		seq	 
			result := print("Valore_new", $new)
			completionEvent($p) := $new
			result := print(eventqueue($p))
		endseq 
	endseq 
	//le operazioni sul nuovo evento vengono eseguite fuori dal seq
	//per evitare che perdano valore a causa dello scope di extend
	result := print("Valore_completionEvent($p)", completionEvent($p))
	r_initEvent[completionEvent($p)] //aggiunta per lo sched
	isCompletionEvent(completionEvent($p)):= true
	isDynSensibleOf($p, completionEvent($p)) := false
	r_sensitive[$p, completionEvent($p)]
	eventqueue($p) := append(eventqueue($p), completionEvent($p)) 
	sensitive($p) := including(sensitive($p), completionEvent($p))
	result := print("eventqueue",eventqueue($p))
	result := print("Fine_della_GenerateCompletionEvent")
endseq

/*
 * Regola per la definizione del comportamento del processo
 */
macro rule r_behavior($p in Sc_Thread) =
seq
	r_TransitionSelection[$p]
	r_GenerateCompletionEvent[$p]
	result := print("TERMINATO_BEHAVIOR") 
endseq

			
/*
 * Regola per il funzionamento del count stim
 */ 	
macro rule r_setup_count_stim =
	if initializedContext(p) = undef then
	seq
		result := print("stepCount_stim:",initializedContext(p), p)
		r_init_thread[p]
		r_behavior[p]
	endseq
	else
	seq
		result := print("stepCount_stim:",initializedContext(p))
		result := print("ramo_behavior_count_stim")
		r_behavior[p]
	endseq
	endif


/*
 * Regola per il funzionamento del count
 */	 	
macro rule r_setup_count =
seq
result := print("initializedContext(h)",initializedContext(h))
	if initializedContext(h) = undef then
 	seq
 		result := print("stepCount:",initializedContext(h), h)
		r_init_method[h]
		r_behavior[h]
	endseq
	else
	seq
		result := print("stepCount:",initializedContext(h))
		result := print("ramo_behavior_count")
		r_behavior[h]
		result := print("terminata_setup_count")
	endseq
	endif
endseq	 
	 
/*
 *Inizializzazione per il modulo Count
 */
 macro rule r_initCount(
$c in Count,
$load in ChanBoolean,
$clock in Clock,
$din in ChanInteger,
$dout in ChanInteger) = 
	seq
		result:= print("InitCount")
		load($c) := $load
		clock($c) := $clock
		din($c) := $din
		dout($c) := $dout
		count_val($c) := 0
		ownerCount(h) := $c
		r_thread[h,<<r_setup_count>>]
			result := print("dopo_setup_count")
		r_dontInitialize[h]
		r_sensitive[h, posEdgeEvent($clock)]			
		result := print("finito_count")
	endseq
	
/*
 *Inizializzazione per il modulo Count_stim
 */
macro rule r_initCount_stim(
$d in Count_stim,
$maxcount in Integer,
$load in ChanBoolean,
$clock in Clock,
$din in ChanInteger,
$dout in ChanInteger) =
	seq
		result:= print("InitCount_stim")
		maxcount($d) := $maxcount
		load($d) := $load
		clock($d) := $clock
		din($d) := $din
		dout($d) := $dout
		ownerC(p) := $d
		r_thread[p, <<r_setup_count_stim>>]
			result := print("dopo_setup_count_stim")
		r_dontInitialize[p]
		r_sensitive[p, posEdgeEvent($clock)]
			result := print("nel_count_stim")
		result := print("finito_count_stim")
	endseq
	
		 