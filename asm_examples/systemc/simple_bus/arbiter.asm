module arbiter

	import ../../STDL/StandardLibrary
	import request
	import tools
	export Arbiter, r_initArbiter, r_arbitrate
	
signature:

	dynamic abstract domain Arbiter
	
	controlled m_name: Arbiter -> String
	controlled m_verbose: Arbiter -> Boolean
	
	static min: Powerset(Request) -> Request
	
definitions:

	// returns the request with the lower priority, i.e. the best one
	function min($set in Powerset(Request)) =
		let ($x = chooseone($set)) in
			if size($set) = 1 then
				$x
			else
				let ($y = min(excluding($set, $x))) in
					if priority($x) < priority($y) then
						$x
					else
						$y
					endif
				endlet
			endif
		endlet
	
	macro rule r_arbitrate($arb in Arbiter, $requests in Powerset(Request)) =
		seq
			if m_verbose($arb) then seq
				// shows the list of pending requests
				tmp__ := print(time, m_name($arb))
				forall $req in $requests do
					tmp__ := print("R", priority($req), lock($req), status($req), address($req))
			endseq endif
			// highest priority: status==SIMPLE_BUS_WAIT and lock is set: 
			// locked burst-action
			choose $req1 in $requests with status($req1) = SIMPLE_BUS_WAIT
				and lock($req1) = SIMPLE_BUS_LOCK_SET do seq
				if m_verbose($arb) then
					tmp__ := print(" -> R", priority($req1), "(rule 1)")
				endif
				result := $req1
			endseq ifnone
				// second priority: lock is set at previous call, 
				// i.e. SIMPLE_BUS_LOCK_GRANTED
				choose $req2 in $requests with lock($req2) = SIMPLE_BUS_LOCK_GRANTED do seq
					if m_verbose($arb) then
						tmp__ := print(" -> R", priority($req2), "(rule 2)")
					endif
					result := $req2
				endseq ifnone
					// third priority: priority
					let ($best = min($requests)) in seq
						if lock($best) != SIMPLE_BUS_LOCK_NO then
							lock($best) := SIMPLE_BUS_LOCK_GRANTED
						endif
						if m_verbose($arb) then
							tmp__ := print(" -> R", priority($best), "(rule 3)")
						endif
						result := $best
					endseq endlet
		endseq
		
	macro rule r_initArbiter(
	$arb in Arbiter, 
	$name in String, 
	$verbose in Boolean) =
		seq
			m_name($arb) := $name
			m_verbose($arb) := $verbose
		endseq
