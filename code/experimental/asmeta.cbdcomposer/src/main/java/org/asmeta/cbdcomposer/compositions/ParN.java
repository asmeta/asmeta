package org.asmeta.cbdcomposer.compositions;

import java.util.Map;

import org.asmeta.cbdcomposer.CompositionException;
import org.asmeta.simulator.InvalidInvariantException;
import org.asmeta.simulator.UpdateSet;

//nuovo codice
public class ParN extends NComposition {
	public ParN(Composition... asm) throws Exception {
		super(asm);
	}

	@Override
	public
	UpdateSet eval() {
		UpdateSet up = c.get(0).eval();
		for (int i = 1; i < c.size(); i++) {
			UpdateSet tempUp = c.get(i).eval();
			up.unionOnlyOutFun(tempUp);
		}
		c.get(c.size() - 1).copyMonitored(up);
		// System.out.println(up.toString());
		return up;
	}

	@Override
	public UpdateSet eval(boolean cdb) throws CompositionException {
		return evalByCheckingContract(cdb, null);

	}

	@Override
	public String toString() {
		String stringa = c.get(0).toString();
		for (int i = 1; i < c.size(); i++) {
			stringa = " ( " + stringa + " || " + c.get(i) + " ) ";
		}
		return stringa;
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
						UpdateSet tempUp = c.get(i).eval(true);
						up.unionOnlyOutFun(tempUp);
					}
					c.get(c.size() - 1).copyMonitored(up);
				} else {
					up = c.get(0).eval(true, mon);
					for (int i = 1; i < c.size(); i++) {
						UpdateSet tempUp = c.get(i).eval(true, mon);
						up.unionOnlyOutFun(tempUp);
					}
					c.get(c.size() - 1).copyMonitored(up);
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