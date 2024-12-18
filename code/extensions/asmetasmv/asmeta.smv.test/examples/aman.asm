// from ABZ 2023 - fMVC
// undef and abstract domains

asm aman

import ../../../../../asm_examples/STDL/StandardLibrary


signature:
	abstract domain Airplane
	enum domain Status = {UNSTABLE, STABLE, FREEZE}
	
	monitored m_airplane: Airplane 
	controlled c_airplane: Airplane 
	monitored m_status: Status 
	
	static a1: Airplane
	static a2: Airplane
	static a3: Airplane
	static a4: Airplane
	 
definitions:

	// MAIN RULE
	main rule r_Main =
		par
			// this is incorrectly translated 
			if m_airplane = undef then c_airplane := a1 endif
			// this is correct
			if isUndef(m_airplane) then c_airplane := a3 endif
			// 
			if m_status = undef then c_airplane := a2 endif
			if isUndef(m_status) then c_airplane := a1 endif
		endpar

// INITIAL STATE
default init s0:
