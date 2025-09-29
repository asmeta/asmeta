package org.asmeta.dbc_composer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.simulator.main.Simulator;
import org.junit.Test;

public class DbcComposerTest {

	/*
	 * Data una formula da args, chiamare il parser, che resituisce la gerarchia di oggetti 
	 * Composition C, invocare su C un eval overloaded con controllo pre-post e invariants e sollevamento eccezioni
	 * dei contratti.
	 * Se passa viene simulata la composizione, se fallisce deve riportare il msg di errore e l'asm che è fallita indicando cosa è fallito (pre-post-invariant)
	 */
	
	String path = "examples/dbc_examples/";
	
	
	@Test
	public void testLeafPre() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		LeafAsm c = new LeafAsm(path + "asmCPre.asm");
		c.eval(true);
		System.out.println(" ===== new step =====");
		c.eval(true);
	}
	
	@Test
	public void testLeafPrePost() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		LeafAsm c = new LeafAsm(path + "asmCPrePost.asm");
		c.eval(true);
		System.out.println(" ===== new step =====");
		c.eval(true);
	}
}
