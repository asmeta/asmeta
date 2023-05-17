package org.asmeta.runtime_composer;

import org.asmeta.simulator.UpdateSet;

//<|>
public class BiPipeHalfDup extends BiComposition {

	public BiPipeHalfDup(Composition asm1, Composition asm2) throws Exception {
		super(asm1, asm2);
	}
	
	@Override
	public
	UpdateSet eval() {
		// run first node
		UpdateSet up = c1.eval();
		// result copied in the second node
		c2.copyMonitored(up);
		// run second node
		up = c2.eval();
		// dalla seconda alla prima
		c1.copyMonitored(up);
		// result copied in the first node
		return up;
	}

	@Override
	public String toString() {
		return c1.toString() + "<|>" + c2.toString();
	}

}
