asm petriNetEdgeWeightNoPlaceCapacity_NoSeq_Matrix_Murata17a

//for the chapter "Formal semantics for Domain Specific Languages" of the book
//"Formal and Practical Aspects of Domain-Specific Languages: Recent Developments",
//publisher "IGI Global"
//It is based on the example used in the paper "Combining Formal Methods and MDE
//Techniques for Model-driven System Design and Analysis"
//This model has been used as example of "Infinite Capacity Petri Net" (see Figure 2)
//in the second version of the chapter sent to the reviewers (15 February 2012).

//petri net with edge weight, no place capacity and
//avoiding the use of seq in the firing of the transition.

//the edges are described with two matrices (two boolean functions)

//the example is taken from the paper of Murata (pag. 9 (or 549), Fig.17 (a))

import ../../STDL/StandardLibrary

signature:
	abstract domain Place
	abstract domain Transition

	controlled tokens : Place -> Integer

	static inArcWeight: Prod(Place, Transition) -> Integer
	static outArcWeight: Prod(Transition, Place) -> Integer

	//delta of tokens that a place gains when a transition fires
	static incidenceMatrix: Prod(Place, Transition) -> Integer

	static isInputPlace: Prod(Place, Transition) -> Boolean
	static isOutputPlace: Prod(Place, Transition) -> Boolean

	static p1: Place
 	static p2: Place
 	static p3: Place
 	static p4: Place
 	static t1: Transition
 	static t2: Transition
 	static t3: Transition
 	static t4: Transition
 
	derived isEnabled : Transition -> Boolean

definitions:
	function inArcWeight($p in Place, $t in Transition) =
		switch($p, $t)
			case (p1, t1): 1
			case (p2, t2): 1
			case (p3, t3): 1
			case (p3, t4): 2
			case (p4, t2): 1
			case (p4, t4): 3
			otherwise 0
		endswitch

 	function outArcWeight($t in Transition, $p in Place) =
 		switch($t, $p)
 			case (t1, p2): 1
			case (t1, p3): 3
			case (t2, p1): 1
			case (t2, p2): 1
			case (t3, p4): 2
			case (t4, p1): 1
			otherwise 0
		endswitch

	//delta of tokens that a place "$p" gains when a transition "$t" fires
	function incidenceMatrix($p in Place, $t in Transition) =
		outArcWeight($t, $p) - inArcWeight($p, $t)

	function isInputPlace($p in Place, $t in Transition) =
		inArcWeight($p, $t) > 0

	function isOutputPlace($p in Place, $t in Transition) =
		outArcWeight($t, $p) > 0

	function isEnabled ($t in Transition) =
		(forall $p in Place with isInputPlace($p, $t) implies tokens($p) >= inArcWeight($p, $t))

	rule r_fire($t in Transition) =
		//it can be divided in the three cases
		/*par
			forall $i in Place with isInputPlace($i, $t) and not(isOutputPlace($i, $t)) do
				tokens($i) := tokens($i) - inArcWeight($i, $t)
			forall $o in Place with isOutputPlace($o, $t) and not(isInputPlace($o, $t)) do
				tokens($o) := tokens($o) + outArcWeight($t, $o)
			forall $k in Place with isOutputPlace($k, $t) and isInputPlace($k, $t) do
				tokens($k) := tokens($k) - inArcWeight($k, $t) + outArcWeight($t, $k)
		endpar*/
		//but it can also be written with a single forall
		//if a place is neither an inputPlace nor an outputPlace, its number of
		//tokens does not change since inWeight($p, $t) and outWeight($p, $t)
		//are 0
		forall $p in Place with true do
			tokens($p) := tokens($p) + incidenceMatrix($p, $t)

	invariant over tokens: (forall $p in Place with tokens($p) >= 0) 

	main rule r_Main =
		choose $t in Transition with isEnabled($t) do
			r_fire[$t]

default init s0:
	 //initial marking
 	function tokens($p in Place) = at({p1 -> 1, p2 -> 0, p3 -> 0, p4 -> 1}, $p)