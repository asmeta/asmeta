asm petriNet_forNuSMV_notLive_bound1_deadlockFree

//for the chapter "Formal semantics for Domain Specific Languages" of the book
//"Formal and Practical Aspects of Domain-Specific Languages: Recent Developments",
//publisher "IGI Global" 

//petri net with edge weight, place capacity and
//avoiding the use of seq in the firing of the transition.

//the edges are described with two matrices (two boolean functions)

//About the liveness property in Petri nets
//From "Petri Nets: Properties, Analysis and Applications" by Tadao Murata:
//The concept of liveness is closely related to the complete absence of deadlocks
//in operating systems. A Petri net (N,M0) said to be live (or equivalently M0 is
//said to be a live marking for N) if, no matter what marking has been reached
//from M0, is possible to ultimately fire any transition of the
//net by progressing through some further firing sequence.
//This means that a live Petri net guarantees deadlock-free operation, no matter
//what firing sequence is chosen.

//About the k-boundeness property in Petri nets
//From "Petri Nets: Properties, Analysis and Applications" by Tadao Murata:
//A Petri net (N, M0) is said to be k-bounded or simply bounded if the number of
//tokens in each place does not exceed a finite number k for any marking reachable
//from M0, i.e., M(p) <= k for every place "p" and every marking "M" \in R(M0).
//A Petri net (N,M0) is said to be safe if it is 1-bounded.

//Deadlock
//There is a deadlock if there is a reachable marking in which no transition can fire.


//version for AsmetaSMV
//Derived from the model "petriNetEdgeWeightPlaceCapacity_NoSeq_Matrix.asm":
//- Integer domain replaced with a ConcreteDomain whose maximum value is determined
//  by the maximum capacity of the places
//- the Petri net is different; this net has been designed in order to violate the liveness property
//  the example is taken from the paper of Murata (pag. 9 (or 549), Fig.17 (f))
//- moreover the net is k-bounded (k = 1)
//- moreover the net is deadlock-free

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
 	static t1: Transition
 	static t2: Transition
 	static t3: Transition
 	static t4: Transition
 	static t5: Transition
 
	derived isEnabled : Transition -> Boolean
	derived availableCapacity: Place -> TokenDomain

definitions:
	domain TokenDomain = {0 : 5}

	function inArcWeight($p in Place, $t in Transition) =  
		switch($p) 
			case p1: if($t = t2 or $t = t3) then 1 else 0 endif
			case p2: if($t = t1 or $t = t4) then 1 else 0 endif
			case p3: if($t = t1 or $t = t5) then 1 else 0 endif
		endswitch

 	function outArcWeight($t in Transition, $p in Place) =  
		switch($p) 
			case p1: if($t = t1 or $t = t4 or $t = t5) then 1 else 0 endif
			case p2: if($t = t2) then 1 else 0 endif
			case p3: if($t = t3) then 1 else 0 endif
		endswitch

	//delta of tokens that a place "$p" gains when a transition "$t" fires
	function incidenceMatrix($p in Place, $t in Transition) =
		outArcWeight($t, $p) - inArcWeight($p, $t)

	function isInputPlace($p in Place, $t in Transition) =
		inArcWeight($p, $t) > 0

	function isOutputPlace($p in Place, $t in Transition) =
		outArcWeight($t, $p) > 0

	function capacity($p in Place) =
		switch($p) 
			case p1: 5
			case p2: 4
			case p3: 2
		endswitch

	function availableCapacity($p in Place) =
		capacity($p) - tokens($p)

	function isEnabled ($t in Transition) =
		(forall $p in Place with (isInputPlace($p, $t) implies tokens($p) >= inArcWeight($p, $t)) and
								 (isOutputPlace($p, $t) implies availableCapacity($p) >= incidenceMatrix($p, $t))
		)

	rule r_fire($t in Transition) =
		forall $p in Place with true do
			tokens($p) := tokens($p) + incidenceMatrix($p, $t)

	CTLSPEC ag((forall $p in Place with tokens($p) <= capacity($p)))

	//Is it live? No, it's not: t1 is dead.
	CTLSPEC (forall $t in Transition with ag(ef(isEnabled($t))))
	CTLSPEC ag(ef(isEnabled(t1)))//t1 is dead yet in the initial state
	CTLSPEC ag(ef(isEnabled(t2)))
	CTLSPEC ag(ef(isEnabled(t3)))
	CTLSPEC ag(ef(isEnabled(t4)))
	CTLSPEC ag(ef(isEnabled(t5)))

	//Is it deadlock-free? Yes.
	CTLSPEC ag((exist $t in Transition with isEnabled($t)))

	//It is 1-bounded (safe)? Yes it is.
	CTLSPEC (forall $p in Place with ag(tokens($p) <= 1))

	invariant over tokens: (forall $p in Place with tokens($p) >= 0)

	main rule r_Main =
		choose $t in Transition with isEnabled($t) do
			r_fire[$t]

default init s0:
	 //initial marking
 	function tokens($p in Place) = at({p1 -> 1, p2 -> 0, p3 -> 0}, $p) 
