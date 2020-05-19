asm petriNet_forModelAdvisor_InconsUpdateViolation2

//for the chapter "Formal semantics for Domain Specific Languages" of the book
//"Formal and Practical Aspects of Domain-Specific Languages: Recent Developments",
//publisher "IGI Global" 

//petri net with edge weight, place capacity and
//avoiding the use of seq in the firing of the transition.

//the edges are described with two matrices (two boolean functions)

//version for the ModelAdvisor:
//- the rule r_fire is wrong since it can update simultaneously the location tokens($p)
//  of a place $p that is both an input and an output place.

//Example taken from the slides "retiDiPetri.pdf" (on Zotero).
//Slightly less trivial than petriNetEdgeWeightNoPlaceCapacity_NoSeq_Matrix, but
//again it can be easily discovered with simulation in one step.

import ../../../STDL/StandardLibrary
import ../../../STDL/CTLlibrary

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
 
	derived isEnabled : Transition -> Boolean
	derived availableCapacity: Place -> TokenDomain

definitions:
	domain TokenDomain = {0 : 7}

	function inArcWeight($p in Place, $t in Transition) =
		if($t=t1 and ($p=p1 or $p=p2)) then 1 else 0 endif

 	function outArcWeight($t in Transition, $p in Place) =
 		switch($p) 
			case p2: if($t = t1) then 2 else 0 endif
			case p3: if($t = t1) then 3 else 0 endif
			otherwise 0
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
			case p1: 4
			case p2: 5
			case p3: 7
		endswitch

	function availableCapacity($p in Place) =
		capacity($p) - tokens($p)

	function isEnabled ($t in Transition) =
		(forall $p in Place with (isInputPlace($p, $t) implies tokens($p) >= inArcWeight($p, $t)) and
								 (isOutputPlace($p, $t) implies availableCapacity($p) >= incidenceMatrix($p, $t))
		)

	rule r_fire($t in Transition) =
		par
			forall $i in Place with isInputPlace($i, $t) do
				tokens($i) := tokens($i) - inArcWeight($i, $t)
			forall $o in Place with isOutputPlace($o, $t) do
				tokens($o) := tokens($o) + outArcWeight($t, $o)
		endpar

	main rule r_Main =
		choose $t in Transition with isEnabled($t) do
			r_fire[$t]

default init s0:
	 //initial marking
 	function tokens($p in Place) = at({p1 -> 2, p2 -> 1, p3 -> 1}, $p) 
