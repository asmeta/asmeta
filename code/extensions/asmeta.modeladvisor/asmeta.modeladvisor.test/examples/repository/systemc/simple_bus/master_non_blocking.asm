module master_non_blocking

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/chanbool
	import ../sched/clock
	import types
	import simple_bus
	import vector
	import tools
	export MasterNonBlocking, r_initMasterNonBlocking
	
signature:

	dynamic abstract domain MasterNonBlocking
	
	controlled clock: MasterNonBlocking -> Clock
	controlled bus_port: MasterNonBlocking -> SimpleBus
	controlled m_name: MasterNonBlocking -> String
	controlled m_unique_priority: MasterNonBlocking -> Integer
	controlled m_start_address: MasterNonBlocking -> Integer
	controlled m_lock: MasterNonBlocking -> Boolean
	controlled m_timeout: MasterNonBlocking -> Integer

	controlled step: MasterNonBlocking -> Integer
	controlled mydata: MasterNonBlocking -> IntVector
	controlled cnt: MasterNonBlocking -> Integer
	controlled addr: MasterNonBlocking -> Integer
	
	controlled owner: Process -> MasterNonBlocking

definitions:

	macro rule r_initBlock($master in MasterNonBlocking, $proc in Process) =
		seq
			extend IntVector with $vect do
				mydata($master) := $vect
			cnt($master) := 0
			addr($master) := m_start_address($master)
		endseq
		
	macro rule r_readBlock($master in MasterNonBlocking, $proc in Process) =
		r_readBus[bus_port($master), m_unique_priority($master), 
			mydata($master), addr($master), m_lock($master)]

	macro rule r_statusBlock($master in MasterNonBlocking, $proc in Process) =
		r_get_statusBus[bus_port($master), m_unique_priority($master)]
		
	macro rule r_writeBlock($master in MasterNonBlocking, $proc in Process) =
		seq
			r_get_statusBus[bus_port($master), m_unique_priority($master)]
			if result = SIMPLE_BUS_ERROR then
				tmp__ := print(time, m_name($master), "ERROR cannot read from", addr($master)) 
			endif
			m_vector(mydata($master), addr($master)) := 
				m_vector(mydata($master), addr($master)) + cnt($master)
			cnt($master) := cnt($master) + 1
			r_writeBus[bus_port($master), m_unique_priority($master),
				mydata($master), addr($master), m_lock($master)]
		endseq

	macro rule r_endReadWrite($master in MasterNonBlocking, $proc in Process) =
		seq
			r_get_statusBus[bus_port($master), m_unique_priority($master)]
			if result = SIMPLE_BUS_ERROR then
				tmp__ := print(time, m_name($master), "ERROR cannot write to", addr($master)) 
			endif
		endseq

	macro rule r_endBlock($master in MasterNonBlocking, $proc in Process) =
		seq
			addr($master) := addr($master) + 4 // next word (byte addressing)
			if addr($master) > m_start_address($master) + 128 then seq
				addr($master) := m_start_address($master)
				cnt($master) := 0
			endseq endif
		endseq
		
	macro rule r_case2($master in MasterNonBlocking, $proc in Process) =
		seq
			r_statusBlock[$master, $proc]
			if result != SIMPLE_BUS_OK and result != SIMPLE_BUS_ERROR then seq
				step($master) := 2
				r_waitStatic[$proc]
			endseq else seq
				r_writeBlock[$master, $proc]
				r_statusBlock[$master, $proc]
				if result != SIMPLE_BUS_OK and result != SIMPLE_BUS_ERROR then seq
					step($master) := 3
					r_waitStatic[$proc]
				endseq else seq
					r_endReadWrite[$master, $proc]
					step($master) := 4
					r_waitTimeOut[$proc, m_timeout($master)]
				endseq endif
			endseq endif
		endseq

	macro rule r_main_actionNonBlocking =
		let ($proc = current_exec,
			$master = owner($proc)) in 
			switch step($master)
				
				case 0: seq
					r_initBlock[$master, $proc]
					step($master) := 1
					r_waitStatic[$proc] // ... for the next rising clock edge
				endseq
				
				case 1: seq
					r_readBlock[$master, $proc]
					r_case2[$master, $proc]
				endseq
				
				case 2: 
					r_case2[$master, $proc]
				
				case 3: seq
					r_statusBlock[$master, $proc]
					if result != SIMPLE_BUS_OK and result != SIMPLE_BUS_ERROR then seq
						step($master) := 3
						r_waitStatic[$proc]
					endseq else seq
						r_endReadWrite[$master, $proc]
						step($master) := 4
						r_waitTimeOut[$proc, m_timeout($master)]
					endseq endif
				endseq
				
				case 4: seq
					step($master) := 5
					r_waitStatic[$proc] // ... for the next rising clock edge
				endseq
				
				case 5: seq
					r_endBlock[$master, $proc]
					r_readBlock[$master, $proc]
					r_case2[$master, $proc]
				endseq
				
			endswitch
		endlet
		
	macro rule r_initMasterNonBlocking(
	$master in MasterNonBlocking,
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
			r_thread[$proc, <<r_main_actionNonBlocking>>]
			r_sensitive[$proc, posEdgeEvent($clock)]
			step($master) := 0
		endseq

