module SystemCUMLProfile_INIT

import StandardLibrary
import chan
export *

signature:

//Classe astratta
abstract domain Behavior  		

//Classi
dynamic abstract domain Constraint	
dynamic abstract domain ValueSpecification
dynamic abstract domain Trigger		
dynamic abstract domain Region				
dynamic abstract domain Transition
dynamic abstract domain Vertex
	
//Classi derivate	
dynamic domain OpaqueExpression subsetof ValueSpecification
dynamic domain StateMachine subsetof Behavior
dynamic domain State subsetof Vertex
dynamic domain FinalState subsetof Vertex
dynamic domain OpaqueBehavior subsetof Behavior
dynamic domain TimeEvent subsetof Event
/*TimeEvent non è utilizzato per questa simulazione
 *si deve implementare un metodo di navigazione del metamodello
 *che consenta di passare ad una funzione un Event e verificare
 *che appartenga a TimeEvent. Es: contains(TimeEvent, $e2)
 *dove $e2 in realtà è un Event
 */

/*I seguenti stereotipi sostituiscono Pseudostate e 
diventano subset di Vertex*/
dynamic domain If subsetof Vertex
dynamic domain Endif subsetof Vertex
dynamic domain While subsetof Vertex
dynamic domain Initial subsetof Vertex

//Enumerazioni
enum domain TransitionKind = { LOCAL| EXTERNAL}
enum domain PseudostateKind = { INITIAL | JUNCTION | CHOICE | TERMINATE }

//Attributi
//Attributi su Transition
controlled kind: Transition -> TransitionKind

//Attributi sugli stereotipi di Pseudostate
controlled kindIf: If -> PseudostateKind
controlled kindEIf: Endif -> PseudostateKind
controlled kindW: While -> PseudostateKind
controlled kindIn: Initial -> PseudostateKind
controlled kindT: FinalState -> PseudostateKind

//Attributi su State
derived isSimple: State -> Boolean
derived isComposite: State -> Boolean
derived isSubmachine: State -> Boolean

//Attributi su Process
controlled sensitive: Process -> Powerset(Event)
controlled selfp: Sc_Thread

//Attributi su Sc_Thread
controlled dont_initialize: Sc_Thread -> Boolean 

//Attributi su TimeEvent
controlled isRelative: TimeEvent -> Boolean
	
//Attributi su OpaqueExpression
controlled body: OpaqueExpression -> String

//Attributi su OpaqueBehavior
controlled body: OpaqueBehavior -> String

//Associazioni
	
//Associazioni su Region
controlled statemachine: Region -> StateMachine
controlled transition: Region -> Powerset(Transition)
controlled state: Region -> Powerset(State)
controlled subvertex: Region -> Powerset(Vertex)

//Associazioni su StateMachine
controlled submachineState: StateMachine -> Powerset(State)
controlled region: StateMachine -> Region

//Associazioni su Transition
controlled containerT: Transition -> Region
controlled trigger: Transition -> Powerset(Trigger)
controlled guard: Transition -> Constraint 						
controlled source: Transition -> Vertex
controlled target: Transition -> Vertex 

//Associazioni su Vertex
controlled container: Vertex -> Region
controlled outgoing: Vertex -> Powerset(Transition)
controlled incoming: Vertex -> Powerset(Transition)

//Associazioni su State
controlled region: State -> Region
controlled doActivity: State -> Behavior
controlled subMachine: State -> StateMachine

//Associazioni su Trigger
controlled event: Trigger -> Event				

//Associazioni su CONSTRAINT
controlled specification: Constraint -> ValueSpecification

//Associazioni su SC_THREAD
controlled baseStateMachine: Sc_Thread -> StateMachine

	
/*/////*Funzioni per la definizione della funzione Tau*/////*/
/*Restituisce lo stato più interno raggiunto dal thread durante l'attuale stato macchina, 
quindi inizializzando la funzione allo stato iniziale si aggiornerà automaticamente*/
controlled deepest: Prod(Sc_Thread,StateMachine) -> Vertex

/*Restituisce la macchina a stati chiamata*/
controlled callMachine: Prod(Sc_Thread,StateMachine) -> StateMachine

/*Restituisce la macchina eseguita dall'attuale Sc_Tread*/
controlled currStateMachine: Sc_Thread -> StateMachine 
	
/*Restituisce gli stati attivi della corrente configurazione*/
controlled currState: Prod(Sc_Thread,StateMachine) -> Powerset(Vertex)

/*Funzione per verificare che un ValueSpecification sia un OpaqueExpression*/
controlled asOpaqueExpression: ValueSpecification -> OpaqueExpression 
	
/*Funzione per operare fra Behavior e OpaqueBehavior*/
controlled asOpaqueBehavior: Behavior -> OpaqueBehavior

/*Funzioni per verifica che un Vertex sia State o Pseudostate*/
controlled asState: Vertex -> State
controlled asStateIn: Vertex -> Initial
controlled asStateW: Vertex -> While
controlled asStateFi: Vertex -> FinalState
controlled asStateIf: Vertex -> If
controlled asStateEif: Vertex -> Endif

/*Restituisce lo stato superiore di un dato stato*/
controlled upState: Vertex -> State
	
/*Restituisce lo stato di cui si compone un dato stato
è un Powerset, perchè uno stato si può comporre di più stati*/
controlled downState: Vertex -> Powerset(Vertex)
	
/*Restituisce la sequenza di stati dallo stato più interno(dato) a quello più esterno(dato)*/
static upChain: Prod(Vertex, Vertex) -> Seq(Vertex)
	
/*Restituisce la sequenza di stati dallo stato più esterno(dato) a quello più interno(dato)*/
static downChain: Prod(Vertex, Vertex) -> Seq(Vertex)
	
/*Contiene gli eventi associati ad un Thread*/
controlled eventqueue: Sc_Thread -> Seq(Event)   		

/*Produce l'elemento di testa della coda degli eventi di un Thread*/
static dispatched: Sc_Thread -> Event 	
	
/*Definisce se la guardia è attiva o non attiva.
Necessita di un meccanismo per settare a false la valutazione delle
guardie non in uso*/
static eval: Prod(Constraint, Transition) -> Boolean

/*Definisce la sola transizione elegibile*/
static transitionenabled: Prod(Transition, Event, Sc_Thread) -> Boolean
	
/*Definisce l'abilitazione alla transizione*/	
static triggering: Prod(Transition, Event)->Boolean
	
/*Restituisce l'insieme delle transizioni abilitate dall'evento e*/
static enabled: Event -> Powerset(Transition)
	
/*Verifica che in uscita ad una transizione ci sia uno stato wait */
derived isAndWait: Prod(State, Event) -> Boolean
	
/*Verifica una and transition*/
controlled isAnd: Transition -> Boolean
	
/*Restituisce la sequenza di eventi dispatched di una and transition, ma scartati*/	
controlled andNotHist: Prod(Process, Transition) -> Powerset(Event)
		
/*Definisce se l'evento in analisi è completo*/
controlled isCompletionEvent: Event -> Boolean
	
/*Verifica il completamento di uno stato attivo*/
static completed: Vertex -> Boolean
	
/*Ritorna lo stato finale di uno stato composto o submachine*/
static final: Vertex -> FinalState
	
/*Dato un evento restituisce l'insieme dei processi a lui sensibili*/
controlled process: Event -> Powerset(Process)
		
/*Verficano l'appartenenza ai stereotipi*/
controlled isStatic_Wait: State -> Boolean
controlled isWait: State -> Boolean
controlled isDont_Initialize: Vertex -> Boolean
controlled isReturn: Vertex -> Boolean
controlled isBreak: Vertex -> Boolean
controlled isContinue: Vertex -> Boolean
static isFinal: Vertex -> Boolean
static isIf: Vertex -> Boolean
static isEndif: Vertex -> Boolean
static isWhile: Vertex -> Boolean
static isInitial: Vertex -> Boolean
static isPseudostate: Vertex -> Boolean

/*Ritorna lo stato comune più interno nelle gerarchia di contenimento*/
static lca: Prod(Vertex, Vertex)-> Vertex

/*Effettua l’LCA tra stato sorgente e destinazione di una transizione*/
static anc: Transition -> Vertex

/*È il solo diretto sottostato di un Vertice v in una sequenza di vertici*/
static directSubState: Prod(Vertex,Seq(Vertex))-> Vertex

/*Calcola il directSubState tra l’anc di una transizione e l’pChain tra stato sorgente e anc della transizione stessa*/
static toS: Transition -> Vertex

/*Calcola il directSubState tra l’anc di una transizione e il downChain tra stato destinazione e anc della transizione stessa*/
static fromS: Transition -> Vertex

/*Inserisce un vertice in una sequenza*/
static push: Prod(Vertex, Seq(Vertex)) -> Seq(Vertex)

/*Estrae il primo elemento da una sequenza di Vertici*/
static pop: Seq(Vertex) -> Seq(Vertex)

/*Estrae il primo elemento da una sequenza di Eventi*/
static pop: Seq(Event) -> Seq(Event)

/*Restituisce, senza eliminarlo, il primo elemento di una sequenza*/
static top: Seq(Vertex) -> Vertex
static top: Seq(Event) -> Event

/*Ritorna lo stato iniziale di uno stato composto o di una sottomacchina*/
controlled stateinit: Vertex -> Vertex

/*È lo stack che “memorizza”lo stato a cui il controllo deve tornare dopo un’operazione di Break*/
controlled nextAfterBreak: Process -> Seq(Vertex)

/*È lo stack che “memorizza”lo stato a cui il controllo deve tornare dopo un’operazione di Break*/
controlled nextAfterContinue: Process -> Seq(Vertex)

/*Restituisce vero se l’esecuzione dell’ttività di uno stato interno restituisce uno stato finale*/
controlled terminated: Prod(Behavior, Vertex) -> Boolean

/*Ritorna la transizione “else” in uscita dallo stato di loop*/
controlled elseTrans: While -> Transition
controlled elseTrans: If -> Transition

/*Viene settata a vero quando uno stato finale è il target di una transizione*/
controlled exitRequest: Process -> Boolean

/**********DEFINITION***************/

definitions :

/*Attributi di State*/
function isSimple($a in State) =   if
	(isDef(container($a)) and
	 isDef(incoming($a)) and
	 isDef(outgoing($a)) and
	 isDef(doActivity($a)))
	 then true
	 	else false
	 endif 

/*Per verificare che sia uno stato composto al posto di manipolare region uso downstate*/
function isComposite($b in State) = if(
	 isDef(downState($b)) and
	 isDef(region($b)) and
	 isDef(incoming($b)) and
	 isDef(outgoing($b))) 
	 then true
	 	else false
	 endif
	
function isSubmachine($c in State) = 	if 
(isComposite($c)) 
	then true 
else false
	endif

/*Deve ritornare la sequenza di stati composti e la upChain la realizza a partire dallo stato più interno (contenuto) t a quello più esterno (composto) s*/						
function upChain($t in Vertex, $s in Vertex) = if 
(upState($t) = asState($s))
	then [$s,$t]
      	else if (isUndef(upState($t))) 
       then []
    		else append(upChain(upState($t),$s),$t) 
       endif
	endif
	
/*DownChain come upChain*/
function downChain($t in Vertex, $s in Vertex) = if 
contains(downState($t), $s)
then [$s,$t]
else if (isUndef(downState($t))) 
       then []
       /*Non verifico in questa sede, la composizione "composta" 
         primo: PERCHé PASSO UN POWERSET DI VERTEX COME ARGOMENTO, mentre la downState restituisce un vertex,
         secondo: per vecchi problemi di overflow*/
    			//else append(downChain(downState($t),$s),$t)  
       endif
endif

//Restituisce l'elemento di testa della coda del thread
function dispatched($s in Sc_Thread) = if 
(isDef(eventqueue($s)))
	then first(eventqueue($s)) 
endif
											
//Definisce se una transizione è "elegibile"
function transitionenabled ($t in Transition, $e in Event, $s in Sc_Thread) =
eval(guard($t), $t) and contains(currState($s,currStateMachine($s)),source($t)) and triggering($t, $e) 


//Definisce l'abilitazione della transizione
function triggering($t in Transition,$e in Event) = 
if (//contains(TimeEvent, $e) and //il TimeEvent non è usato
   (eventTime($e) = time) and 
   (exist $e1 in trigger($t) with event($e1) = $e) and 
   size(trigger($t))=1) 
 	then true
else 
if (isAnd($t) and 
	(exist $k in trigger($t) with event($k) = $e) and 
	(forall $c in trigger($t) with ($e != event($c) and contains(andNotHist(selfp,$t), event($c)))))
	then true
else 
if (isDont_Initialize(asState(source($t))) or (isStatic_Wait(asState(source($t))))) and contains(sensitive(selfp), $e)
	then true 
else 
if	isCompletionEvent($e) and isEmpty(trigger($t)) 
	then true 
else
if	(exist $ee in trigger($t) with event($ee) = $e)
	then true
else false
endif endif endif endif endif 

//Ritorna vero se si tratta di un wait state e in uscita ad una and transition	
function isAndWait ($s in State, $e in Event) = if
(isWait($s) and (exist $t in outgoing($s) with isAnd($t) and (exist $e1 in trigger($t) with event($e1) = $e)))
then true else false endif

//Verifica del completamento di uno stato attivo		
function completed ($v in Vertex) = 
if isDef(asState($v)) //significa che sicuramente il Vertex è uno State
/*Sono aggiunti gli stati dont_init e static_wait perchè pur essendo stati 
con un valido asState non rientrando in questi casi non consentivano l'esecuzione*/
    then 
    if isDont_Initialize(asState($v)) then true
    else if isStatic_Wait(asState($v)) then true 
    else if (contains(currState(selfp, currStateMachine(selfp)), final($v)))
	then true
	else if (isSimple(asState($v)) and terminated(doActivity(asState($v)), $v))
	then true
	else false endif endif endif endif
	else //lo stato v non ha definito asState, quindi è uno pseudostato, quindi sicuramente è uno stato completo
true endif 

function final($v in Vertex) = 
if (isFinal($v) and (isDef(upState($v) )))
/*se il vertex passato è uno stato finale e possiede un upState
  cioè è contenuto in uno stato composto allora ritornalo*/
	  then $v
			else undef 
		endif 

function isFinal($v in Vertex) = if isDef($v) then if
(contains(FinalState, asStateFi($v)))
	then true
		else false endif else false
	 endif

function isIf($v in Vertex) = if isDef($v) then if
(contains(If, asStateIf($v)))
then true
else false endif else false
endif
	 						
function isEndif($v in Vertex) = if isDef($v) then if 
(contains(Endif, asStateEif($v)))
then true
		else false endif else false
	endif

function isWhile($v in Vertex) = if isDef($v) then if
(contains(While, asStateW($v)))
	then true
		else false endif else false
	 endif

function isInitial($v in Vertex) = if isDef($v) then if
(contains(Initial, asStateIn($v)))
	then true
		else false endif else false
	 endif

//Funzione per la verifica di uno pseudostato
function isPseudostate($v in Vertex) = if 
(isInitial($v) = true or isFinal($v) = true or isIf($v) = true or isEndif($v) = true or isWhile($v) = true) 
    	then true
    		else false
    	endif
		

function directSubState($v in Vertex, $s in Seq(Vertex)) = 
if isEmpty(asSet($s)) then undef   
else 
let ($s1 = first($s)) in
    if contains($s, $s1) and upState($s1)= asState($v)
    then $s1
    else
      directSubState($v, tail($s)) endif
endlet 
endif	


//Ritorna la regione(stato) più interna che contiene entrambi gli stati
function lca($s in Vertex, $t in Vertex) = if 
contains(downChain($s,$t), $t)
	then $s
		else if 
contains(downChain($t, $s), $s)
		then $t
			else //non hanno vertex in comune
		undef
	endif endif


function anc($t in Transition) = lca(source($t), target($t))

function toS($t in Transition) = directSubState(anc($t), upChain(source($t),anc($t)))
	
function fromS($t in Transition) = directSubState(anc($t), downChain(anc($t), target($t)))
									
function push($v in Vertex, $sv in Seq(Vertex)) = append($sv,$v)//non è implementato il prepend

function pop($vs in Seq(Vertex)) = excluding($vs, top($vs))

function pop($vs in Seq(Event)) = excluding($vs, top($vs))

function top($sv in Seq(Vertex)) = first($sv)

function top($sv in Seq(Event)) = first($sv)

							  

