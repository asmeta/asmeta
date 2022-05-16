asm prova

import ../../../../../../../asm_examples/STDL/StandardLibrary
import ../../../../../../../asm_examples/STDL/CTLlibrary

signature:
	enum domain Players = { PLAYER1 | PLAYER2 }
	dynamic controlled winner: Players -> Boolean
	dynamic controlled foo: Players -> Integer
	dynamic controlled z: Integer
	dynamic controlled zz: Powerset(Players)
	dynamic controlled zzz: Integer
	//derived somma: Integer
	derived fds: Bag(Players)
	derived fds2: Powerset(Players)
	derived fds3: Powerset(Integer)
	
definitions:
	function fds = 
		asBag(Players)
		
	function fds2 = 
		{$o in Players | winner($o): $o}
	
	function fds3 = 
		{$o in fds2 | winner($o): foo($o)}
	//function somma = 
	//	sum(<$o in asBag(Players) | winner($o): foo($o)>)
	
	main rule r_Main =
		par
			forall $p in Players with true do
				choose $b in Boolean with true do
					winner($p) := $b
			z := z - 1//somma
			zz := fds2
			zzz := sum(fds3)
		endpar

default init s0:
	function foo($i in Players) = if($i = PLAYER1) then 10 else 11 endif
	function z = 1000
	function winner($i in Players) = false