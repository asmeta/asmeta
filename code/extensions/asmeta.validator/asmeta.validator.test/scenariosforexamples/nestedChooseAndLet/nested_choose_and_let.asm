asm nested_choose_and_let

import ../../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain Rows = {RR1|RR2|RR3}
	enum domain Cols = {CC1|CC2}
	dynamic controlled tab: Prod(Rows, Cols)->Integer

definitions:

	main rule r_Main =
		choose $x in {1:10} with $x > 5 do
			choose $row in Rows, $col in Cols with true do
				let ($dup_col = $col) in
					tab($row, $dup_col) := $x
				endlet
			
default init s0:
	function tab($r in Rows, $c in Cols) = 0

