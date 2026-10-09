asm nested_forall

import ../../../../../../asm_examples/STDL/StandardLibrary

signature:
	enum domain Rows = {RR1|RR2|RR3}
	enum domain Cols = {CC1|CC2}
	dynamic monitored update: Boolean
	dynamic controlled tab: Prod(Rows, Cols)->Integer


definitions:
	rule r_inc($r in Rows) =
		par
			forall $c in Cols with update do // 0 iterations or multiple iterations depending on update
				tab($r, $c) := tab($r, $c) + 1
			forall $cc in {1} with true do // always one interations
				skip
			forall $ccc in Cols with false do // always 0 iterations
				skip
		endpar

	main rule r_Main =
		forall $r in Rows do // always multiple iterations
			r_inc[$r]
			
default init s0:
	function tab($r in Rows, $c in Cols) = 0
