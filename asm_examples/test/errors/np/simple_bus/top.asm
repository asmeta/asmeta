module top

	import ../../../STDL/StandardLibrary
	import ../../../systemc/sched/clock
	import ../../../systemc/simple_bus/memory
	import ../../../systemc/simple_bus/arbiter
	import master_direct
	import master_non_blocking
	import master_blocking
	import simple_bus
	export r_initTop, clock, arbiter, master_d, master_nb, master_b, 
		bus, fast_mem, slow_mem
	
signature:

	static clock: Clock
	static arbiter: Arbiter
	static master_d: MasterDirect
	static master_nb: MasterNonBlocking
	static master_b: MasterBlocking
	static bus: SimpleBus
	static slow_mem: Memory
	static fast_mem: Memory

definitions:

	macro rule r_initTop =
		seq
			r_initClock[clock, 30, 0.5, 0, true]
			//r_initArbiter[arbiter, "arbiter", true] // verbose
			r_initArbiter[arbiter, "arbiter", false]
			//r_initSimpleBus[bus, "bus", true, clock, arbiter, {slow_mem, fast_mem}] // verbose
			r_initSimpleBus[bus, "bus", false, clock, arbiter, {slow_mem, fast_mem}]
			r_initMasterDirect[master_d, "master_d", 120, 100, true, clock, bus]
			r_initMasterNonBlocking[master_nb, "master_nb", 3, 56, false, 20, clock, bus]
			r_initMasterBlocking[master_b, "master_b", 4, 76, false, 300, clock, bus]
			r_initFastMem[fast_mem, "mem_fast", 0, 127]
			r_initSlowMem[slow_mem, "mem_slow", 128, 255, 1, clock]
		endseq
		
