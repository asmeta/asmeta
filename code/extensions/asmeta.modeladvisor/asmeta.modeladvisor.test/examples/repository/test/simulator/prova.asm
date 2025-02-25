asm prova

import ../../STDL/StandardLibrary

signature:

enum domain Color = {RED | GREEN | BLUE}
abstract domain Beings
domain A subsetof Integer

//static being1: Beings
//static being2: Beings
//static being3: Beings

monitored f1: Integer
monitored f2: Real
monitored f3: Color
monitored f4: A

controlled var1: Integer
controlled var2: Integer
controlled var3: Integer

definitions:

domain A = {1 : 10}

macro rule r_swap($x in Integer, $y in Integer) =
	par
		$x := $y
		$y := $x
	endpar

main rule r_main = 
//	let (
//		$x1 = f1,
//		$x2 = f2,
//		$x3 = f3
//		) in skip
//	endlet
//	r_swap[var1, var2]

//	choose $x in {1..10}, $y in {1..10}, $z in {1..10} 
//			with $x*$x + $y*$y = $z*$z do 
//		par
//			var1 := $x
//			var2 := $y
//			var3 := $z
//		endpar

//	forall $x in Color with true do skip

//	choose $x in A with true do skip

	let ($x4 = f4) in skip endlet


default init s0:

function var1 = 5
function var2 = 7
