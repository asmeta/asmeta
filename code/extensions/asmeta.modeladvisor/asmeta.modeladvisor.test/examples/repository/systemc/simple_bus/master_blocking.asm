module master_blocking

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/chanbool
	import ../sched/clock
	import types
	import simple_bus
	import vector
	import tools
	export MasterBlocking, r_initMasterBlocking
	
signature:

	dynamic abstract domain MasterBlocking
	
	controlled clock: MasterBlocking -> Clock
	controlled bus_port: MasterBlocking -> SimpleBus
	controlled m_name: MasterBlocking -> String
	controlled m_unique_priority: MasterBlocking -> Integer
	controlled m_start_address: MasterBlocking -> Integer
	controlled m_lock: MasterBlocking -> Boolean
	controlled m_timeout: MasterBlocking -> Integer
	
	// process local variables
	controlled p_mydata: Process -> IntVector
	controlled p_i: Process -> Integer
	
	controlled owner: Process -> MasterBlocking

definitions:

	macro rule r_main_actionBlocking =
		let ($proc = current_exec,
			$master = owner($proc),
			$frame = frame_exec($proc),
			$mylength = 16) in
			switch step_exec($proc, $frame)
				
				case 0: seq
					r_goto[1]
					extend IntVector with $vect do
						p_mydata($proc) := $vect					
				endseq
				
				case 1: seq
					r_goto[2]
					r_waitStatic[$proc] // ... for the next rising clock edge					
				endseq
				
				case 2: seq
					r_goto[3]
					r_burst_readBus[bus_port($master), m_unique_priority($master), 
						p_mydata($proc), m_start_address($master), $mylength, m_lock($master)]					
				endseq
				
				case 3: seq
					r_goto[4]
					if result = SIMPLE_BUS_ERROR then
						tmp__ := print(time, m_name($master), "blocking read failed at", m_start_address($master))
					endif
					p_i($proc) := 0					
				endseq
				
				case 4:
					r_ifGoto[p_i($proc) < $mylength, 5, 7]
				
				case 5: seq
					r_goto[6]
					let ($vec = p_mydata($proc), 
						$addr = m_start_address($master), 
						$i = p_i($proc)) in
						// next word (byte addressing)
						m_vector($vec, $addr+$i*4) := m_vector($vec, $addr+$i*4) + $i
					endlet
					p_i($proc) := p_i($proc) + 1					
				endseq
				
				case 6: seq
					r_goto[4]
					r_waitStatic[$proc]					
				endseq

				case 7: seq
					r_goto[8]
					r_burst_writeBus[bus_port($master), m_unique_priority($master), 
						p_mydata($proc), m_start_address($master), $mylength, m_lock($master)]					
				endseq
				
				case 8: seq
					r_goto[9]
					if result = SIMPLE_BUS_ERROR then
						tmp__ := print(time, m_name($master), "blocking write failed at", m_start_address($master))
					endif
				endseq

				case 9: seq
					r_goto[1]
					r_waitTimeOut[$proc, m_timeout($master)]					
				endseq

			endswitch
		endlet
		
	macro rule r_initMasterBlocking(
	$master in MasterBlocking,
	$name in String,
	$unique_priority in Integer,
	$start_address in Integer,
	$lock in Boolean,
	$timeout in Integer,
	$clock in Clock,
	$bus in SimpleBus) =
		extend Process with $proc do seq
			clock($master) := $clock
			bus_port($master) := $bus
			m_name($master) := $name
			m_unique_priority($master) := $unique_priority
			m_start_address($master) := $start_address
			m_lock($master) := $lock
			m_timeout($master) := $timeout
			owner($proc) := $master
			r_thread[$proc, <<r_main_actionBlocking>>]
			r_sensitive[$proc, posEdgeEvent($clock)]
		endseq

