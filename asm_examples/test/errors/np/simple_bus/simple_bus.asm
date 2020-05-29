module simple_bus

	import ../../../STDL/StandardLibrary
	import ../../../systemc/sched/common
	import ../../../systemc/sched/chanbool
	import ../../../systemc/sched/clock
	import ../../../systemc/simple_bus/request
	import ../../../systemc/simple_bus/arbiter
	import ../../../systemc/simple_bus/memory
	import ../../../systemc/simple_bus/tools
	export SimpleBus, r_direct_readBus, r_direct_writeBus, 
		r_readBus, r_writeBus, r_get_statusBus, r_burst_readBus, r_burst_writeBus, 
		r_initSimpleBus, directReadBus
	
signature:

	dynamic abstract domain SimpleBus

	controlled clock: SimpleBus -> Clock
	controlled arbiter_port: SimpleBus -> Arbiter
	controlled slave_port: SimpleBus -> Powerset(Memory)

	controlled m_name: SimpleBus -> String
	controlled m_verbose: SimpleBus -> Boolean
	controlled m_current_request: SimpleBus -> Request
	
	controlled owner: Process -> SimpleBus

	// macro parameters and logical variables
	controlled p_bus: Prod(Process, Integer) -> SimpleBus
	controlled p_unique_priority: Prod(Process, Integer) -> Integer
	controlled p_data: Prod(Process, Integer) -> IntVector
	controlled p_address: Prod(Process, Integer) -> Integer
	controlled p_length: Prod(Process, Integer) -> Integer
	controlled p_lock: Prod(Process, Integer) -> Boolean
	controlled p_request: Prod(Process, Integer) -> Request
	
	derived get_slave: Prod(SimpleBus, Integer) -> Memory
	derived directReadBus: Prod(SimpleBus, Integer) -> Integer

definitions:

	//----------------------------------------------------------------------------
	//-- BUS methods:
	//
	//     handle_request()   : performs atomic bus-to-slave request
	//     get_request()      : BUS-interface: gets the request form of given 
	//                          priority
	//     get_next_request() : returns a valid request out of the list of 
	//                          pending requests
	//     clear_locks()      : downgrade the lock status of the requests once
	//                          the transfer is done
	//----------------------------------------------------------------------------

	function get_slave($bus in SimpleBus, $address in Integer) =
		let ($slaves = {$s in slave_port($bus)| 
			start_address($s) <= $address and $address <= end_address($s): $s}) in
			if size($slaves) = 1 then
				chooseone($slaves)
			else
				undef
			endif
		endlet
		
	function directReadBus($bus in SimpleBus, $address in Integer) =
		let ($slave = get_slave($bus, $address)) in
			if $slave = undef then
				undef
			else
				directReadMem($slave, $address)
			endif
		endlet

	macro rule r_get_request($bus in SimpleBus, $priority in Integer) =
		choose $request in Request with priority($request) = $priority do
			result := $request
		ifnone
			extend Request with $new_request do seq
				r_initRequest[$new_request]
				priority($new_request) := $priority
				result := $new_request
			endseq
	
	macro rule r_direct_readBus(
	$bus in SimpleBus, 
	$data in Integer, 
	$address in Integer) =
		let ($slave = get_slave($bus, $address)) in
			if $slave = undef then
				result := false
			else
				r_direct_readMem[$slave, $data, $address]
			endif
		endlet

	macro rule r_direct_writeBus(
	$bus in SimpleBus, 
	$data in Integer, 
	$address in Integer) =
		let ($slave = get_slave($bus, $address)) in
			if $slave = undef then
				result := false
			else
				r_direct_writeMem[$slave, $data, $address]
			endif
		endlet
		
	macro rule r_readBus(
	$bus in SimpleBus,
	$unique_priority in Integer,
	$data in IntVector,
	$address in Integer,
	$lock in Boolean) =
		seq
			if m_verbose($bus) then
				tmp__ := print(time, m_name($bus), ": read", $unique_priority, "@", $address)
			endif
			r_get_request[$bus, $unique_priority]
			let ($request = result) in seq
				// assert...
				do_write($request) := false
				address($request) := $address
				end_address($request) := $address
				data($request) := $data
				if $lock then
					lock($request) := if lock($request) = SIMPLE_BUS_LOCK_SET then
						SIMPLE_BUS_LOCK_GRANTED else SIMPLE_BUS_LOCK_SET endif
				endif
				status($request) := SIMPLE_BUS_REQUEST
			endseq endlet
		endseq
		
	macro rule r_writeBus(
	$bus in SimpleBus,
	$unique_priority in Integer,
	$data in IntVector,
	$address in Integer,
	$lock in Boolean) =
		seq
			if m_verbose($bus) then
				tmp__ := print(time, m_name($bus), ": write", $unique_priority, "@", $address)
			endif
			r_get_request[$bus, $unique_priority]
			let ($request = result) in seq
				// assert...
				do_write($request) := true
				address($request) := $address
				end_address($request) := $address
				data($request) := $data
				if $lock then
					lock($request) := if lock($request) = SIMPLE_BUS_LOCK_SET then
						SIMPLE_BUS_LOCK_GRANTED else SIMPLE_BUS_LOCK_SET endif
				endif
				status($request) := SIMPLE_BUS_REQUEST
			endseq endlet
		endseq
		
	macro rule r_get_statusBus($bus in SimpleBus, $unique_priority in Integer) =
		seq
			r_get_request[$bus, $unique_priority]
			result := status(result)
		endseq
		
	macro rule r_burstReadBody =
		let ($proc = current_exec, 
			$frame = frame_exec($proc),
			$bus = p_bus($proc, $frame),
			$unique_priority = p_unique_priority($proc, $frame),
			$data = p_data($proc, $frame),
			$address = p_address($proc, $frame),
			$length = p_length($proc, $frame),
			$lock = p_lock($proc, $frame)) in
			switch step_exec($proc, $frame)
				
				case 0:	seq
					r_goto[1]
					if m_verbose($bus) then
						tmp__ := print(time, m_name($bus), ": burst_read", $unique_priority, "@", $address)
					endif
					r_get_request[$bus, $unique_priority]
					p_request($proc, $frame) := result
					let ($request = p_request($proc, $frame)) in seq
						// assert...
						do_write($request) := false
						address($request) := $address
						end_address($request) := $address + ($length - 1) * 4
						data($request) := $data
						if $lock then
							lock($request) := if lock($request) = SIMPLE_BUS_LOCK_SET then
								SIMPLE_BUS_LOCK_GRANTED else SIMPLE_BUS_LOCK_SET endif
						endif
						status($request) := SIMPLE_BUS_REQUEST
					endseq endlet					
				endseq
				
				case 1: seq
					r_goto[2]
					let ($request1 = p_request($proc, $frame)) in
						r_waitEvent[$proc, transfer_done($request1)]
					endlet					
				endseq

				case 2: seq
					r_goto[3]
					r_waitEvent[$proc, posEdgeEvent(clock($bus))]					
				endseq
				
				case 3: seq
					let ($request2 = p_request($proc, $frame)) in
						result := status($request2)
					endlet
					r_leaveFrame[]
				endseq
				
			endswitch
		endlet
		
	macro rule r_burst_readBus(
	$bus in SimpleBus,
	$unique_priority in Integer,
	$data in IntVector,
	$address in Integer,
	$length in Integer,
	$lock in Boolean) =
		seq
			r_enterFrame[<<r_burstReadBody>>]
			let ($proc = current_exec, $frame = frame_exec($proc)) in seq 
				p_bus($proc, $frame) := $bus
				p_unique_priority($proc, $frame) := $unique_priority
				p_data($proc, $frame) := $data
				p_address($proc, $frame) := $address
				p_length($proc, $frame) := $length
				p_lock($proc, $frame) := $lock
			endseq endlet
			r_burstReadBody[]
		endseq
		
	macro rule r_burstWriteBody =
		let ($proc = current_exec, 
			$frame = frame_exec($proc),
			$bus = p_bus($proc, $frame),
			$unique_priority = p_unique_priority($proc, $frame),
			$data = p_data($proc, $frame),
			$address = p_address($proc, $frame),
			$length = p_length($proc, $frame),
			$lock = p_lock($proc, $frame)) in
			switch step_exec($proc, $frame)
				
				case 0:	seq
					r_goto[1]
					if m_verbose($bus) then
						tmp__ := print(time, m_name($bus), ": burst_write", $unique_priority, "@", $address)
					endif
					r_get_request[$bus, $unique_priority]
					p_request($proc, $frame) := result
					let ($request = p_request($proc, $frame)) in seq
						// assert...
						do_write($request) := true
						address($request) := $address
						end_address($request) := $address + ($length - 1) * 4
						data($request) := $data
						if $lock then
							lock($request) := if lock($request) = SIMPLE_BUS_LOCK_SET then
								SIMPLE_BUS_LOCK_GRANTED else SIMPLE_BUS_LOCK_SET endif
						endif
						status($request) := SIMPLE_BUS_REQUEST
					endseq endlet					
				endseq
				
				case 1: seq
					r_goto[2]
					let ($request1 = p_request($proc, $frame)) in
						r_waitEvent[$proc, transfer_done($request1)]
					endlet					
				endseq

				case 2: seq
					r_goto[3]
					r_waitEvent[$proc, posEdgeEvent(clock($bus))]					
				endseq
				
				case 3: seq
					let ($request2 = p_request($proc, $frame)) in
						result := status($request2)
					endlet
					r_leaveFrame[]
				endseq
				
			endswitch
		endlet

	macro rule r_burst_writeBus(
	$bus in SimpleBus,
	$unique_priority in Integer,
	$data in IntVector,
	$address in Integer,
	$length in Integer,
	$lock in Boolean) =
		seq
			r_enterFrame[<<r_burstWriteBody>>]
			let ($proc = current_exec, $frame = frame_exec($proc)) in seq 
				p_bus($proc, $frame) := $bus
				p_unique_priority($proc, $frame) := $unique_priority
				p_data($proc, $frame) := $data
				p_address($proc, $frame) := $address
				p_length($proc, $frame) := $length
				p_lock($proc, $frame) := $lock
			endseq endlet
			r_burstWriteBody[]
		endseq
		
	macro rule r_handle_request($bus in SimpleBus) =
		let ($current_request = m_current_request($bus),
			 $slave = get_slave($bus, address($current_request))) in seq
			if (m_verbose($bus)) then
				tmp__ := print(time, m_name($bus), "Handle Slave", priority($current_request))
			endif
			status($current_request) := SIMPLE_BUS_WAIT
			result := SIMPLE_BUS_OK
			if do_write($current_request) then
				r_writeMem[$slave, 
					m_vector(data($current_request), address($current_request)), 
					address($current_request)]
			else
				r_readMem[$slave, 
					m_vector(data($current_request), address($current_request)),
					address($current_request)]
			endif
			if m_verbose($bus) then
				tmp__ := print(" --> status=", result)
			endif
			switch result
				case SIMPLE_BUS_ERROR: seq
					status($current_request) := SIMPLE_BUS_ERROR
					r_notifyNow[transfer_done($current_request)]
					m_current_request($bus) := undef
				endseq
				case SIMPLE_BUS_OK: seq
					// next word (byte addressing)
					address($current_request) := address($current_request) + 4
					if address($current_request) > end_address($current_request) then seq
						// burst-transfer (or single transfer) completed
						status($current_request) := SIMPLE_BUS_OK
						r_notifyNow[transfer_done($current_request)]
						m_current_request($bus) := undef
					endseq else
						// more data to transfer, but the (atomic) slave transfer is done
						m_current_request($bus) := undef
					endif
				endseq
				case SIMPLE_BUS_WAIT:
					// the slave is still processing: no clearance of the current request
					skip
			endswitch
		endseq endlet
		
	macro rule r_get_next_request($bus in SimpleBus) =
		// the slave is done with its action, m_current_request is
		// empty, so go over the bag of request-forms and compose
		// a set of likely requests. Pass it to the arbiter for the
		// final selection
		let ($requests = {$req in Request| status($req) = SIMPLE_BUS_REQUEST
			or status($req) = SIMPLE_BUS_WAIT: $req}) in seq
			if m_verbose($bus) then
				forall $req1 in $requests do
					tmp__ := print(time, m_name($bus), "request", priority($req1), status($req1))
			endif
			result := undef
			if size($requests) > 0 then
				r_arbitrate[arbiter_port($bus), $requests]
			endif
		endseq endlet
		
	macro rule r_clear_locks($bus in SimpleBus) =
		forall $req in Request do
			if lock($req) = SIMPLE_BUS_LOCK_GRANTED then
				lock($req) := SIMPLE_BUS_LOCK_SET
			else
				lock($req) := SIMPLE_BUS_LOCK_NO
			endif
			
	macro rule r_main_actionBus =
		// m_current_request is cleared after the slave is done with a
		// single data transfer. Burst requests require the arbiter to
		// select the request again.
		let ($proc = current_exec,
			$bus = owner($proc)) in seq
			if m_current_request($bus) = undef then seq
				r_get_next_request[$bus]
				m_current_request($bus) := result
			endseq else
				// monitor slave wait states
				if m_verbose($bus) then
					tmp__ := print(time, "SLV", address(m_current_request($bus)))
				endif
			endif
			if m_current_request($bus) != undef then
				r_handle_request[$bus]
			endif
			if m_current_request($bus) = undef then
				r_clear_locks[$bus]
			endif
			r_waitStatic[$proc]		
		endseq endlet

	macro rule r_initSimpleBus(
	$bus in SimpleBus,
	$name in String,
	$verbose in Boolean,
	$clock in Clock, 
	$arbiter_port in Arbiter,
	$slave_port in Powerset(Memory)) =
		extend Process with $proc do seq
			clock($bus) := $clock
			arbiter_port($bus) := $arbiter_port
			slave_port($bus) := $slave_port
			m_name($bus) := $name
			m_verbose($bus) := $verbose
			m_current_request($bus) := undef
			owner($proc) := $bus
			r_thread[$proc, <<r_main_actionBus>>]
			r_dontInitialize[$proc]
			r_sensitive[$proc, negEdgeEvent($clock)]
		endseq
		
