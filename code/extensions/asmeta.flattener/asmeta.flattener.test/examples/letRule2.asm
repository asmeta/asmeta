asm letRule2

import ../../../../asm_examples/STDL/StandardLibrary

signature:
	dynamic controlled foo: Boolean
	dynamic controlled fooA: Boolean
	dynamic controlled fooB: Boolean
	dynamic controlled fooC: Boolean -> Boolean
	dynamic controlled fooD: Boolean
	dynamic controlled fooE: Boolean -> Boolean
	dynamic monitored mon: Boolean

definitions:

	main rule r_main =
		par
			let ($x = true) in
				foo := $x
			endlet
			let ($y = mon) in
				fooA := not($y)
			endlet
			let ($z = fooB) in
				fooB := not($z)
			endlet
			forall $b in Boolean with true do
				let ($k = $b) in
					fooC($k) := not($b)
				endlet
			choose $t in Boolean with $t=mon do
				let ($s = $t) in
					fooD := not($s)
				endlet
			let ($arg = fooD) in
				fooE($arg) := not(fooE($arg))
			endlet
		endpar

default init s0:
	function foo = false
	function fooA = false
	function fooB = true
	function fooC($b in Boolean) = $b
	function fooD = mon
	function fooE($b in Boolean) = true