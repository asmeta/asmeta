package org.asmeta.runtime_composer;

import org.asmeta.simulator.UpdateSet;

//nuovo codice <||>
public class BiPipeFullDup extends BiComposition {

	public BiPipeFullDup(Composition asm1, Composition asm2) throws Exception {
		super(asm1, asm2);
	}
		
	@Override
	public
	UpdateSet eval() {
		//double eval to simulate parallel
		UpdateSet up1=c1.eval();
		UpdateSet up2=c2.eval();
		c2.copyMonitored(up1);
		c1.copyMonitored(up2);
		//results union
		up1.union(up2);
		return up1;
	}
	
	@Override
	public String toString() {
		return c1.toString() + "<||>" + c2.toString();
	}
	
}