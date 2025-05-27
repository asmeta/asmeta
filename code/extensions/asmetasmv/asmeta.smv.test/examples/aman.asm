// from ABZ 2023 - fMVC
// undef and abstract domains

asm aman

import ../../../../../asm_examples/STDL/StandardLibrary


signature:
	abstract domain Airplane
	enum domain Status = {UNSTABLE, STABLE, FREEZE}
	
	monitored m_airplane: Airplane 
	monitored m_airplane2: Airplane
	monitored m_status: Status 
	monitored m_status2: Status
	controlled c_airplane: Airplane
	
	static a1: Airplane
	static a2: Airplane
	static a3: Airplane
	static a4: Airplane
	 
definitions:

	// MAIN RULE
	main rule r_Main =
		par
			// not - it may have incosistent updates
			// this is incorrectly translated 
			if m_airplane = undef then c_airplane := a1 endif
			// this is correct
			if isUndef(m_airplane2) then c_airplane := a3 endif
			// 
			if m_status = undef then c_airplane := a2 endif
			if isUndef(m_status2) then c_airplane := a1 endif
		endpar

// INITIAL STATE
default init s0:
