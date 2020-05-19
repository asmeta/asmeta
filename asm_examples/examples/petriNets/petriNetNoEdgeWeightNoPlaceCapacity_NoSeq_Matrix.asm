asm petriNetNoEdgeWeightNoPlaceCapacity_NoSeq_Matrix

//for the chapter "Formal semantics for Domain Specific Languages" of the book
//"Formal and Practical Aspects of Domain-Specific Languages: Recent Developments",
//publisher "IGI Global"

//petri net with no edge weight, no place capacity and
//avoiding the use of seq in the firing of the transition.

//the edges are described with two matrices (two boolean functions)

import ../../STDL/StandardLibrary

signature:
	abstract domain Place
	abstract domain Transition

	controlled tokens : Place -> Integer

	static isInputPlace: Prod(Place, Transition) -> Boolean
	static isOutputPlace: Prod(Place, Transition) -> Boolean

	static p1: Place
 	static p2: Place
 	static p3: Place
 	static p4: Place
 	static t1: Transition
 	static t2: Transition
 
	derived isEnabled : Transition -> Boolean

definitions:
	function isInputPlace($p in Place, $t in Transition) =  
		/*switch(($p, $t))//con lo switch ci sono problemi TODO: controllare 
			case (p1, t1): true
			case (p2, t2): true
			case (p3, t2): true
			otherwise false
		endswitch*/
		/*switch($p)//cosi' funziona 
			case p1: $t=t1
			case p2: $t=t2
			case p3: $t=t2
			otherwise false
		endswitch*/
		($t = t1 and $p = p1) or ($t = t2 and ($p = p2 or $p = p3))
				
 	function isOutputPlace($p in Place, $t in Transition) =  
		/*switch(($p, $t))//con lo switch ci sono problemi TODO: controllare
			case (p2, t1): true
			case (p3, t1): true
			case (p1, t2): true
			case (p4, t2): true
			otherwise false
		endswitch*/
		/*switch($p) //cosi' funziona
			case p1: $t=t2
			case p2: $t=t1
			case p3: $t=t1
			case p4: $t=t2
			otherwise false
		endswitch*/
		($t = t1 and ($p = p2 or $p = p3)) or ($t = t2 and ($p = p1 or $p = p4))

	function isEnabled ($t in Transition) =
		(forall $p in Place with isInputPlace($p, $t) implies tokens($p) > 0)

	rule r_fire($t in Transition) =
		par
			forall $i in Place with isInputPlace($i, $t) and not(isOutputPlace($i, $t)) do
				tokens($i) := tokens($i) - 1
			forall $o in Place with isOutputPlace($o, $t) and not(isInputPlace($o, $t)) do
				tokens($o) := tokens($o) + 1
			//no need to update the places that are both inputPlaces and outputPlaces,
			//since they loose a token and receive a token
		endpar

	invariant over tokens: (forall $p in Place with tokens($p) >= 0)

	main rule r_Main =
		choose $t in Transition with isEnabled($t) do
			r_fire[$t]

default init s0:
	 //initial marking
 	function tokens($p in Place) = at({p1 -> 1, p2 -> 0, p3 -> 2, p4 -> 1}, $p) 