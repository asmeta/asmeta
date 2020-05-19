module tools

	import ../../STDL/StandardLibrary
	export *
	
signature:
	
	controlled tmp__: Any
	
	static range: Prod(Integer, Integer, Integer) -> Powerset(Integer)
	
definitions:

	function range($start in Integer, $end in Integer, $step in Integer) =
		if $start > $end then 
			{}
		else
			including(range($start + $step, $end, $step), $start)
		endif

