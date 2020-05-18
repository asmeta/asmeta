asm abstractDomain

import ../../../../../asm_examples/STDL/StandardLibrary

signature:
	domain MyDomain subsetof Integer
	abstract domain Place
	
	dynamic controlled x: MyDomain
	dynamic controlled y: MyDomain
	controlled tokens : Place -> MyDomain
	static p1: Place
 	static p2: Place
 	static p3: Place
 	static p4: Place
 	
	
definitions:
	domain MyDomain = {0..10}
	
	main rule r_Main =
		forall $p in Place with true do
			switch($p) 
				case p1: tokens(p1) := 1 
				case p2: tokens(p2) := 2
				case p3: tokens(p3) := 4
				case p4: tokens(p4) := 6
			endswitch

default init s0:
	function x = 1
	function y = 1
	function tokens($p in Place) = at({p1 -> 1, p2 -> 1, p3 -> 2, p4 -> 1}, $p) 