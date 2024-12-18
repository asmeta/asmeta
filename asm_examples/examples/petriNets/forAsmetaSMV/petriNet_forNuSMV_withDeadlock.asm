asm petriNet_forNuSMV_withDeadlock

//for the chapter "Formal semantics for Domain Specific Languages" of the book
//"Formal and Practical Aspects of Domain-Specific Languages: Recent Developments",
//publisher "IGI Global" 

//petri net with edge weight, place capacity and
//avoiding the use of seq in the firing of the transition.

//the edges are described with two matrices (two boolean functions)

//Deadlock
//There is a deadlock if there is a reachable marking in which no transition can fire.


//version for AsmetaSMV
//Derived from the model "petriNetEdgeWeightPlaceCapacity_NoSeq_Matrix.asm":
//- Integer domain replaced with a ConcreteDomain whose maximum value is determined
//  by the maximum capacity of the places
//- the Petri net is different; this net has been designed in order to have
//  a net with deadlock.
//  the example is taken from the paper of Murata (pag. 8 (or 548), Fig.16)

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLLibrary

signature:
	abstract domain Place
	abstract domain Transition

	domain TokenDomain subsetof Integer

	controlled tokens : Place -> TokenDomain

	//the order in the cartesian product reflect the staring and the arriving
	//point of the edge.
	static inArcWeight: Prod(Place, Transition) -> TokenDomain
	static outArcWeight: Prod(Transition, Place) -> TokenDomain
	
	//delta of tokens that a place gains when a transition fires
	static incidenceMatrix: Prod(Place, Transition) -> TokenDomain
	
	static capacity: Place -> TokenDomain

	static isInputPlace: Prod(Place, Transition) -> Boolean
	static isOutputPlace: Prod(Place, Transition) -> Boolean

	static p1: Place
 	static p2: Place
 	static p3: Place
 	static t0: Transition
 	static t1: Transition
 	static t2: Transition
 	static t3: Transition
 
	derived isEnabled : Transition -> Boolean
	derived availableCapacity: Place -> TokenDomain

definitions:
	domain TokenDomain = {0 : 5}

	function inArcWeight($p in Place, $t in Transition) =  
		switch($p) 
			case p1: if($t = t0 or $t = t1 or $t = t3) then 1 else 0 endif
			case p2: if($t = t2) then 1 else 0 endif
			case p3: if($t = t0 or $t = t2) then 1 else 0 endif
		endswitch

 	function outArcWeight($t in Transition, $p in Place) =  
		switch($p) 
			case p1: if($t = t3) then 1 else 0 endif
			case p2: if($t = t3) then 1 else 0 endif
			case p3: if($t = t1 or $t = t2) then 1 else 0 endif
		endswitch

	//delta of tokens that a place "$p" gains when a transition "$t" fires
	function incidenceMatrix($p in Place, $t in Transition) =
		outArcWeight($t, $p) - inArcWeight($p, $t)

	function isInputPlace($p in Place, $t in Transition) =
		inArcWeight($p, $t) > 0

	function isOutputPlace($p in Place, $t in Transition) =
		outArcWeight($t, $p) > 0

	function capacity($p in Place) = 1

	function availableCapacity($p in Place) =
		capacity($p) - tokens($p)

	function isEnabled ($t in Transition) =
		(forall $p in Place with (isInputPlace($p, $t) implies tokens($p) >= inArcWeight($p, $t)) and
								 (isOutputPlace($p, $t) implies availableCapacity($p) >= incidenceMatrix($p, $t))
		)

	rule r_fire($t in Transition) =
		forall $p in Place with true do
			tokens($p) := tokens($p) + incidenceMatrix($p, $t)

	//Is it deadlock-free? No.
	CTLSPEC ag((exist $t in Transition with isEnabled($t)))

	invariant over tokens: (forall $p in Place with tokens($p) >= 0)

	main rule r_Main =
		choose $t in Transition with isEnabled($t) do
			r_fire[$t]

default init s0:
	 //initial marking
 	function tokens($p in Place) = at({p1 -> 1, p2 -> 0, p3 -> 0}, $p) 
