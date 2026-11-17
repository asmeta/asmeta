package org.asmeta.cbdcomposer.compositions;

import java.util.ArrayList;

import org.asmeta.simulator.UpdateSet;

//nuovo codice
abstract class NComposition extends Composition {
	ArrayList<Composition> c;

	NComposition(Composition... c) {
		this.c = new ArrayList<>();
		for (Composition i : c) {
			this.c.add(i);
		}
	}

	protected void copyMonitored(UpdateSet up) {
		for (int i = 0; i < c.size(); i++) {
			c.get(i).copyMonitored(up);
		}
	}
}