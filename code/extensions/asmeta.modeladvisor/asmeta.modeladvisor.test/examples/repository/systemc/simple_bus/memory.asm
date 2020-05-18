module memory

	import ../../STDL/StandardLibrary
	import ../sched/common
	import ../sched/chanbool
	import ../sched/clock
	import types
	import tools
	export Memory, start_address, end_address, r_direct_readMem, r_readMem,
	r_direct_writeMem, r_writeMem, r_initFastMem, r_initSlowMem, directReadMem
	
signature:

	dynamic abstract domain Memory
	
	enum domain MemType = {FAST_MEM | SLOW_MEM}
	
	controlled m_name: Memory -> String
	controlled m_type: Memory -> MemType
	controlled m_start_address: Memory -> Integer
	controlled m_end_address: Memory -> Integer
	controlled m_mem: Prod(Memory, Integer) -> Integer
	
	// functions belonging to slow memories
	controlled clock:Memory -> Clock
	controlled m_nr_wait_states: Memory -> Integer
	controlled m_wait_count: Memory -> Integer
	controlled owner: Process -> Memory
	
	derived start_address: Memory -> Integer
	derived end_address: Memory -> Integer
	derived directReadMem: Prod(Memory, Integer) -> Integer


definitions:

	function start_address($mem in Memory) =
		m_start_address($mem)
		
	function end_address($mem in Memory) =
		m_end_address($mem)
		
	function directReadMem($mem in Memory, $address in Integer) =
		m_mem($mem, $address)
		
	macro rule r_direct_readMem(
	$mem in Memory, 
	$data in Integer, 
	$address in Integer) =
		seq
			$data := m_mem($mem, $address)
			result := true
		endseq
		
	macro rule r_direct_writeMem(
	$mem in Memory,
	$data in Integer, 
	$address in Integer) =
		seq
			m_mem($mem, $address) := $data
			result := true
		endseq

	macro rule r_readFastMem(
	$mem in Memory, 
	$data in Integer, 
	$address in Integer) =
		seq
			$data := m_mem($mem, $address)
			result := SIMPLE_BUS_OK
		endseq
		
	macro rule r_readSlowMem(
	$mem in Memory,
	$data in Integer,
	$address in Integer) =
		// accept a new call if m_wait_count < 0
		if m_wait_count($mem) < 0 then seq
			m_wait_count($mem) := m_nr_wait_states($mem)
			result := SIMPLE_BUS_WAIT
		endseq else if m_wait_count($mem) = 0 then seq
			$data := m_mem($mem, $address)
			result := SIMPLE_BUS_OK
		endseq else
			result := SIMPLE_BUS_WAIT
		endif endif

	macro rule r_readMem(
	$mem in Memory, 
	$data in Integer, 
	$address in Integer) =
		if m_type($mem) = FAST_MEM then
			r_readFastMem[$mem, $data, $address]
		else
			r_readSlowMem[$mem, $data, $address]
		endif
		
	macro rule r_writeFastMem(
	$mem in Memory, 
	$data in Integer, 
	$address in Integer) =
		seq
			m_mem($mem, $address) := $data
			result := SIMPLE_BUS_OK
		endseq
		
	macro rule r_writeSlowMem(
	$mem in Memory,
	$data in Integer,
	$address in Integer) =
		// accept a new call if m_wait_count < 0
		if m_wait_count($mem) < 0 then seq
			m_wait_count($mem) := m_nr_wait_states($mem)
			result := SIMPLE_BUS_WAIT
		endseq else if m_wait_count($mem) = 0 then seq
			m_mem($mem, $address) := $data
			result := SIMPLE_BUS_OK
		endseq else
			result := SIMPLE_BUS_WAIT
		endif endif
	
	macro rule r_writeMem(
	$mem in Memory, 
	$data in Integer, 
	$address in Integer) =
		if m_type($mem) = FAST_MEM then
			r_writeFastMem[$mem, $data, $address]
		else
			r_writeSlowMem[$mem, $data, $address]
		endif
		
	macro rule r_wait_loop =
		let ($proc = current_exec,
			$mem = owner($proc)) in seq
			if m_wait_count($mem) >= 0 then
				m_wait_count($mem) := m_wait_count($mem) - 1
			endif
			r_waitStatic[$proc]
		endseq endlet
		
	macro rule r_initFastMem(
	$mem in Memory,
	$name in String,
	$start_address in Integer, 
	$end_address in Integer) =
		seq
			m_type($mem) := FAST_MEM
			m_name($mem) := $name			
			m_start_address($mem) := $start_address
			m_end_address($mem) := $end_address
			forall $i in range($start_address, $end_address, 4) do
				m_mem($mem, $i) := 0
		endseq
		
	macro rule r_initSlowMem(
	$mem in Memory,
	$name in String,
	$start_address in Integer,
	$end_address in Integer,
	$nr_wait_states in Integer,
	$clock in Clock) =
		extend Process with $proc do seq
			m_type($mem) := SLOW_MEM
			clock($mem) := $clock
			m_name($mem) := $name			
			m_start_address($mem) := $start_address
			m_end_address($mem) := $end_address
			m_nr_wait_states($mem) := $nr_wait_states
			m_wait_count($mem) := -1
			owner($proc) := $mem
			r_thread[$proc, <<r_wait_loop>>]
			r_dontInitialize[$proc]
			r_sensitive[$proc, posEdgeEvent($clock)]
			forall $i in range($start_address, $end_address, 4) do
				m_mem($mem, $i) := 0
		endseq

