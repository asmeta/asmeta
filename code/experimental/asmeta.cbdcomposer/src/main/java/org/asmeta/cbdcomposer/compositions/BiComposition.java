package org.asmeta.cbdcomposer.compositions;

import org.asmeta.simulator.UpdateSet;

abstract class BiComposition extends Composition {
	Composition c1, c2;

	BiComposition(Composition c1, Composition c2) {

		this.c1 = c1;
		this.c2 = c2;
	}

	protected void copyMonitored(UpdateSet up) {
		// System.out.println("copying update set " + up + " in " + c1.toString() + "
		// and in " + c2.toString());
		c1.copyMonitored(up);
		c2.copyMonitored(up);
	}

}