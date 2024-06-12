asm PetriNet

//for the chapter "Formal semantics for Domain Specific Languages" of the book
//"Formal and Practical Aspects of Domain-Specific Languages: Recent Developments",
//publisher "IGI Global" 

//petri net with edge weight, place capacity and
//avoiding the use of seq in the firing of the transition.

//the edges are described with two matrices (two boolean functions)

//About the transition rule in Petri nets with place capacity ("strict transition rule")
//From "Petri Nets: Properties, Analysis and Applications" by Tadao Murata:  
//For the above rule of transition enabling, it is assumed that each place can
//accommodate an unlimited number of tokens. Such a Petri net is referred to as
//an "infinite capacity net". For modeling many physical systems, it is natural to
//consider an upper limit to the number of tokens that each place can hold. Such
//a Petri net is referred to as a "finite capacity net". For a finite capacity
//net (N, M0), each place "p" has an associated capacity "K(p)", the maximum number of
//tokens that "p" can hold at any time. For finite capacity nets, for a transition
//"t" to be enabled, there is an additional condition that the number of tokens in
//each output place "p" of "t" cannot exceed its capacity "K(p)" after firing t.

//version for AsmetaSMV
//Derived from the model "petriNetEdgeWeightPlaceCapacity_NoSeq_Matrix.asm":
//- Integer domain replaced with a ConcreteDomain whose maximum value is determined
//  by the maximum capacity of the places

import ./STDL/StandardLibrary
import ./STDL/CTLLibrary

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
 	static p4: Place
 	static t1: Transition
 	static t2: Transition
 
	derived isEnabled : Transition -> Boolean
	derived availableCapacity: Place -> TokenDomain

definitions:
	domain TokenDomain = {0 : 5}

	function inArcWeight($p in Place, $t in Transition) =  
		switch($p) 
			case p1: if($t = t1) then 1 else 0 endif
			case p2: if($t = t2) then 2 else 0 endif
			case p3: if($t = t2) then 4 else 0 endif
			case p4: 0
		endswitch

 	function outArcWeight($t in Transition, $p in Place) =  
		switch($p) 
			case p1: if($t = t2) then 1 else 0 endif
			case p2: if($t = t1) then 1 else 0 endif
			case p3: if($t = t1) then 3 else 0 endif
			case p4: if($t = t2) then 1 else 0 endif
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
			case p1: 1
			case p2: 3
			case p3: 5
			case p4: 1
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
	
	//reachability
	CTLSPEC ef(tokens(p2) = 2)
	//to obtain the counterexample and see how it is reached the marking with tokens(p2) = 2
	CTLSPEC not(ef(tokens(p2) = 2))

	//A Petri net (N,M0) is said to be reversible if, for each marking M in R(M0),
	//M0 is reachable from M. Thus, in a reversible net one can always get back
	//to the initial marking or state.
	//Is it reversable? No it's not reversable.
	CTLSPEC ag(ef(tokens(p1) = 1 and tokens(p2) = 1 and tokens(p3) = 2 and tokens(p4) = 1))

	invariant over tokens: (forall $p in Place with tokens($p) >= 0)

	main rule r_Main =
		choose $t in Transition with isEnabled($t) do
			r_fire[$t]

default init s0:
	 //initial marking
 	function tokens($p in Place) = at({p1 -> 1, p2 -> 1, p3 -> 2, p4 -> 1}, $p) 