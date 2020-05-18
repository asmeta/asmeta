module request

	import ../../STDL/StandardLibrary
	import ../sched/common
	import types
	import vector
	export *
	
signature:
	
	dynamic abstract domain Request

	enum domain LockStatus = 
		{SIMPLE_BUS_LOCK_NO | SIMPLE_BUS_LOCK_SET | SIMPLE_BUS_LOCK_GRANTED}
	
	controlled priority: Request -> Integer
	controlled do_write: Request -> Boolean
	controlled address: Request -> Integer
	controlled end_address: Request -> Integer
	controlled data: Request -> IntVector
	controlled lock: Request -> LockStatus
	controlled transfer_done: Request -> Event
	controlled status: Request -> BusStatus

definitions:

	macro rule r_initRequest($req in Request) =
		extend Event with $event do seq
			r_initEvent[$event]
			priority($req) := 0
			do_write($req) := false
			address($req) := 0
			end_address($req) := 0
			data($req) := undef
			lock($req) := SIMPLE_BUS_LOCK_NO
			transfer_done($req) := $event
			status($req) := SIMPLE_BUS_OK
		endseq
		
