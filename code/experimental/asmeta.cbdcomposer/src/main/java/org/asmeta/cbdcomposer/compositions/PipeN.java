package org.asmeta.cbdcomposer.compositions;

import java.util.Map;

import org.asmeta.cbdcomposer.CompositionException;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.UpdateSet;

/*
 * class Pipe extends BiComposition {
 * 
 * Pipe(Composition asm1, Composition asm2) throws Exception { super(asm1,
 * asm2); }
 * 
 * @Override UpdateSet eval() { // run della prima UpdateSet up[0] = c1.eval();
 * // copio i riulstati c2.copyMonitored(up[0]); // eseguo s2 c2.eval(); return
 * up; }
 * 
 * @Override public String toString() { return c1.toString() + "|" +
 * c2.toString(); } }
 */
//new code |
public class PipeN extends NComposition {
	// we don't know in advance the number of arguments
	// so we use varargs
	public PipeN(Composition... asm) throws Exception {
		super(asm);
	}

	@Override // probabilmente giusto
	public UpdateSet eval() {
		UpdateSet up = c.get(0).eval();
		// for starts from 2nd pipe element
		for (int i = 1; i < c.size(); i++) {
			c.get(i).copyMonitored(up);
			up = c.get(i).eval();
		}
		c.get(c.size() - 1).copyMonitored(up);// così update corretto
		return up;
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
					up = c.get(0).eval(true);
					for (int i = 1; i < c.size(); i++) {
						c.get(i).copyMonitored(up);
						up = c.get(i).eval(true);
					}
					c.get(c.size() - 1).copyMonitored(up);
				} else {
					up = c.get(0).eval(true, mon);
					for (int i = 1; i < c.size(); i++) {
						c.get(i).copyMonitored(up);
						up = c.get(i).eval(true, mon);
					}
					c.get(c.size() - 1).copyMonitored(up);
				}

			} catch (InvalidInvariantException e) {
				checkInvariantException(e);
			}
		} else {
			up = eval();
		}
		return up;
	}

	@Override
	public String toString() {
		String string = c.get(0).toString();
		// for starts from 2nd pipe element
		for (int i = 1; i < c.size(); i++) {
			string = " ( " + string + " | " + c.get(i).toString() + " ) ";
		}
		return string;
	}

}