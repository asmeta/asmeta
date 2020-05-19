module master_direct

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/chanbool
	import ../sched/clock
	import simple_bus
	import tools
	export MasterDirect, r_initMasterDirect

signature:

	dynamic abstract domain MasterDirect
	
	controlled clock: MasterDirect -> Clock
	controlled bus_port: MasterDirect -> SimpleBus
	controlled m_name: MasterDirect -> String
	controlled m_address: MasterDirect -> Integer
	controlled m_timeout: MasterDirect -> Integer
	controlled m_verbose: MasterDirect -> Boolean
	controlled my_data: Prod(MasterDirect, Integer) -> Integer
	
	controlled owner: Process -> MasterDirect
	
definitions:

	macro rule r_main_actionDirect =
		let ($proc = current_exec,
			$master = owner($proc), 
			$bus = bus_port($master)) in seq
			r_direct_readBus[$bus, my_data($master, 0), m_address($master)]
			r_direct_readBus[$bus, my_data($master, 1), m_address($master) + 4]
			r_direct_readBus[$bus, my_data($master, 2), m_address($master) + 8]
			r_direct_readBus[$bus, my_data($master, 3), m_address($master) + 12]
			if m_verbose($master) then
				tmp__ := print(
					time, m_name($master), "mem", m_address($master), 
					m_address($master) + 15, "=", my_data($master, 0), 
					my_data($master, 1), my_data($master, 2), my_data($master, 3))
			endif
			r_waitTimeOut[$proc, m_timeout($master)]
		endseq endlet
		
	macro rule r_initMasterDirect(
	$master in MasterDirect,
	$name in String,
	$address in Integer,
	$timeout in Integer,
	$verbose in Boolean,
	$clock in Clock,
	$bus in SimpleBus) =
		extend Process with $proc do seq
			clock($master) := $clock
			bus_port($master) := $bus
			m_name($master) := $name
			m_address($master) := $address
			m_timeout($master) := $timeout
			m_verbose($master) := $verbose
			owner($proc) := $master
			r_thread[$proc, <<r_main_actionDirect>>]
		endseq

