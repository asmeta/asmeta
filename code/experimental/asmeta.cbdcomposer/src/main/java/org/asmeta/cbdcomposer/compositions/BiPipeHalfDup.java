package org.asmeta.cbdcomposer.compositions;

import java.util.Map;

import org.asmeta.cbdcomposer.CompositionException;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.UpdateSet;

//<|>
public class BiPipeHalfDup extends BiComposition {

	public BiPipeHalfDup(Composition asm1, Composition asm2) throws Exception {
		super(asm1, asm2);
	}

	@Override
	public 	UpdateSet eval() {
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
		return " ( " + c1.toString() + " <|> " + c2.toString() + " ) ";
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
		UpdateSet up = null;
		if (cdb) {
			try {
				if (mon == null) {
					up = c1.eval(true);
					c2.copyMonitored(up);
					up = c2.eval(true);
					c1.copyMonitored(up);
				} else {
					up = c1.eval(true, mon);
					c2.copyMonitored(up);
					up = c2.eval(true, mon);
					c1.copyMonitored(up);
				}
			} catch (InvalidInvariantException e) {
				checkInvariantException(e);
			}

		} else {
			up = eval();
		}
		return up;// if cdb check inv else eval(); return eval();

	}
}