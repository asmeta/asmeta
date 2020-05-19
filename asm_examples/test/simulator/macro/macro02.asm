asm macro02

	import ../../../STDL/StandardLibrary

signature:

	abstract domain Chan
	domain ChanBool subsetof Chan
	domain ChanInt subsetof Chan
	
	abstract domain Bike
	domain Ducati subsetof Bike
	domain Honda subsetof Bike
	
	static chan1: ChanBool
	static bike1: Ducati

definitions:

	macro rule r_broom1($x in Chan, $y in Bike) =
		skip
		
	macro rule r_broom2($x in Chan, $y in Ducati) =
		skip
		
	macro rule r_broom3($x in Chan, $y in Honda) =
		skip

	macro rule r_broom4($x in ChanBool, $y in Bike) =
		skip
		
	macro rule r_broom5($x in ChanBool, $y in Ducati) =
		skip
		
	macro rule r_broom6($x in ChanBool, $y in Honda) =
		skip
		
	macro rule r_broom7($x in ChanInt, $y in Bike) =
		skip
		
	macro rule r_broom8($x in ChanInt, $y in Ducati) =
		skip
		
	macro rule r_broom9($x in ChanInt, $y in Honda) =
		skip

	main rule r_main =
		r_broom5[chan1, bike1]
		
