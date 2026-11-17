package org.asmeta.cbdcomposer.compositions;

import java.util.Map;

import org.asmeta.cbdcomposer.CompositionException;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.UpdateSet;

//<||>
public class BiPipeFullDup extends BiComposition {

	public BiPipeFullDup(Composition asm1, Composition asm2) throws Exception {
		super(asm1, asm2);
	}

	@Override
	public	UpdateSet eval() {
		// double eval to simulate parallel

		UpdateSet up1 = c1.eval();
		// COPIO LE MONITORATE ACQUISITE PER C1 IN C2
		// POI FA STEP C1 E STEP C2
		UpdateSet up2 = c2.eval();
		c2.copyMonitored(up1);
		c1.copyMonitored(up2);
		// results union
		up1.unionOnlyOutFun(up2);
		return up1;
	}

	@Override
	public String toString() {
		return " ( " + c1.toString() + "<||>" + c2.toString() + " ) ";

	}

	@Override
	public UpdateSet eval(boolean cdb) throws CompositionException {
		return evalByCheckingContract(cdb, null);
	}

	@Override
	public UpdateSet eval(boolean cdb, Map<String, String> mon) throws CompositionException {
		return evalByCheckingContract(cdb, mon);
	}

	private UpdateSet evalByCheckingContract(boolean cdb, Map<String, String> mon) throws CompositionException {
		UpdateSet up1 = null;
		UpdateSet up2 = null;
		if (cdb) {
			try {
				if (mon == null) {
					up1 = c1.eval(true);
					up2 = c2.eval(true);
					c2.copyMonitored(up1);
					c1.copyMonitored(up2);
					up1.unionOnlyOutFun(up2);
				} else {
					up1 = c1.eval(true, mon);
					up2 = c2.eval(true, mon);
					c2.copyMonitored(up1);
					c1.copyMonitored(up2);
					up1.unionOnlyOutFun(up2);
				}
			} catch (InvalidInvariantException e) {
				checkInvariantException(e);
			}

		} else {
			up1 = eval();
		}
		return up1;// if cdb check inv else eval(); return eval();

	}
}