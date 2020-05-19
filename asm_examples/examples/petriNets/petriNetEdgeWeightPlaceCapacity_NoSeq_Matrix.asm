asm petriNetEdgeWeightPlaceCapacity_NoSeq_Matrix

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

import ../../STDL/StandardLibrary

signature:
	abstract domain Place
	abstract domain Transition

	controlled tokens : Place -> Integer

	//the order in the cartesian product reflect the staring and the arriving
	//point of the edge.
	static inArcWeight: Prod(Place, Transition) -> Integer
	static outArcWeight: Prod(Transition, Place) -> Integer
	
	//delta of tokens that a place gains when a transition fires
	static incidenceMatrix: Prod(Place, Transition) -> Integer
	
	static placeCapacity: Place -> Integer

	static isInputPlace: Prod(Place, Transition) -> Boolean
	static isOutputPlace: Prod(Place, Transition) -> Boolean

	static p1: Place
 	static p2: Place
 	static p3: Place
 	static p4: Place
 	static t1: Transition
 	static t2: Transition
 
	derived isEnabled : Transition -> Boolean
	derived availableCapacity: Place -> Integer

definitions:
	function inArcWeight($p in Place, $t in Transition) =
		switch($p, $t)
			case (p1, t1): 1
			case (p2, t2): 2
			case (p3, t2): 4
			otherwise 0
		endswitch
		/*switch($p)
			case p1: if($t = t1) then 1 else 0 endif
			case p2: if($t = t2) then 2 else 0 endif
			case p3: if($t = t2) then 4 else 0 endif
			case p4: 0
		endswitch*/

 	function outArcWeight($t in Transition, $p in Place) =
 		switch($t, $p)
			case (t1, p2): 1
			case (t1, p3): 3
			case (t2, p1): 1
			case (t2, p4): 1
			otherwise 0
		endswitch  
		/*switch($p) 
			case p1: if($t = t2) then 1 else 0 endif
			case p2: if($t = t1) then 1 else 0 endif
			case p3: if($t = t1) then 3 else 0 endif
			case p4: if($t = t2) then 1 else 0 endif
		endswitch*/

	//delta of tokens that a place "$p" gains when a transition "$t" fires
	function incidenceMatrix($p in Place, $t in Transition) =
		outArcWeight($t, $p) - inArcWeight($p, $t)

	function isInputPlace($p in Place, $t in Transition) =
		inArcWeight($p, $t) > 0

	function isOutputPlace($p in Place, $t in Transition) =
		outArcWeight($t, $p) > 0

	function placeCapacity($p in Place) =
		switch($p) 
			case p1: 1
			case p2: 3
			case p3: 5
			case p4: 1
		endswitch

	function availableCapacity($p in Place) =
		placeCapacity($p) - tokens($p)

	function isEnabled ($t in Transition) =
		(forall $p in Place with (isInputPlace($p, $t) implies tokens($p) >= inArcWeight($p, $t)) and
								 (isOutputPlace($p, $t) implies availableCapacity($p) >= incidenceMatrix($p, $t))
		)

	rule r_fire($t in Transition) =
		forall $p in Place with true do
			tokens($p) := tokens($p) + incidenceMatrix($p, $t)

	invariant over tokens: (forall $p in Place with tokens($p) >= 0) 

	main rule r_Main =
		choose $t in Transition with isEnabled($t) do
			r_fire[$t]

default init s0:
	 //initial marking
 	function tokens($p in Place) = at({p1 -> 1, p2 -> 1, p3 -> 2, p4 -> 1}, $p) 