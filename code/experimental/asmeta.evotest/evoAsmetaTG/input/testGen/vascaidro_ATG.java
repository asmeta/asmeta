// vascaidro_ATG.java automatically generated from ASM2CODE

/**
* This class is designed to simulate the behavior of an abstract state machine in an automated way.
*
* <p>
* It has been optimized to be used with evosuite in order to automatically generate test scenarios.
* </p>
*/
class vascaidro_ATG {
	/** Instance of the asmeta specification translated into a java class.*/
	private final vascaidro execution;
	/** current state. */
	private int state;

	/**
	* Constructor of the {@code vascaidro_ATG} class. Creates a private instance of the asm
	* {@link vascaidro} and sets the initial state of the state machine to 0.
	*/
	public vascaidro_ATG() {
		this.execution = new vascaidro();
		this.state = 0;
	}

	/** The step function allows to perform a step of the asm by incrementing the state.
	*/
	public void step() {
		System.out.println("<State " + state + " (controlled)>");
		printControlled();
		this.execution.updateASM();
		System.out.println("</State " + state + " (controlled)>");
		System.out.println("\n<Current status>");
		printControlled();
		// Cover the rules
		coverRules();
		state++;
	}

	/* Cover the Rules */
	/**
	* Calls all rules covering functions.
	*/
	private void coverRules() {
		cover_r_Main_branches();
		cover_r_IncDec_branches();
	}

	/**
	* Cover all the branches of the rule r_Main.
	*/
	private void cover_r_Main_branches() {
		if (this.execution.branch_r_Main_master) {
			System.out.println("branch_r_Main_master covered");
		}
		if (this.execution.branch_r_Main_1) {
			System.out.println("branch_r_Main_1 covered");
		}
		if (this.execution.branch_r_Main_2) {
			System.out.println("branch_r_Main_2 covered");
		}
		if (this.execution.branch_r_Main_3) {
			System.out.println("branch_r_Main_3 covered");
		}
		if (this.execution.branch_r_Main_4) {
			System.out.println("branch_r_Main_4 covered");
		}
		if (this.execution.branch_r_Main_5) {
			System.out.println("branch_r_Main_5 covered");
		}
		if (this.execution.branch_r_Main_6) {
			System.out.println("branch_r_Main_6 covered");
		}
		if (this.execution.branch_r_Main_7) {
			System.out.println("branch_r_Main_7 covered");
		}
		if (this.execution.branch_r_Main_8) {
			System.out.println("branch_r_Main_8 covered");
		}
		if (this.execution.branch_r_Main_9) {
			System.out.println("branch_r_Main_9 covered");
		}
		if (this.execution.branch_r_Main_10) {
			System.out.println("branch_r_Main_10 covered");
		}
		if (this.execution.branch_r_Main_11) {
			System.out.println("branch_r_Main_11 covered");
		}
		if (this.execution.branch_r_Main_12) {
			System.out.println("branch_r_Main_12 covered");
		}
	}

	/**
	* Cover all the branches of the rule r_IncDec.
	*/
	private void cover_r_IncDec_branches() {
		if (this.execution.branch_r_IncDec_master) {
			System.out.println("branch_r_IncDec_master covered");
		}
		if (this.execution.branch_r_IncDec_1) {
			System.out.println("branch_r_IncDec_1 covered");
		}
		if (this.execution.branch_r_IncDec_2) {
			System.out.println("branch_r_IncDec_2 covered");
		}
	}

	/* ASM Methods */
	// Print controlled
	private void printControlled() {
		System.out.println("statoLivello = " + execution.statoLivello.get().value);
	}

	// Controlled getters
	public int get_statoLivello() {
		return this.execution.statoLivello.get().value;
	}

	// Monitored setters
	public void set_riempi_completamente(boolean riempi_completamente) {
		this.execution.riempi_completamente.set(riempi_completamente);
		System.out.println("Set riempi_completamente = " + riempi_completamente);
	}

	public void set_svuota_completamente(boolean svuota_completamente) {
		this.execution.svuota_completamente.set(svuota_completamente);
		System.out.println("Set svuota_completamente = " + svuota_completamente);
	}

	public void set_riempi_25_percento(boolean riempi_25_percento) {
		this.execution.riempi_25_percento.set(riempi_25_percento);
		System.out.println("Set riempi_25_percento = " + riempi_25_percento);
	}

	public void set_svuota_25_percento(boolean svuota_25_percento) {
		this.execution.svuota_25_percento.set(svuota_25_percento);
		System.out.println("Set svuota_25_percento = " + svuota_25_percento);
	}
}
