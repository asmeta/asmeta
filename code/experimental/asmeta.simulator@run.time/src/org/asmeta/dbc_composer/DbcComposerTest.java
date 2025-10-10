package org.asmeta.dbc_composer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.simulator.main.Simulator;
import org.junit.Test;

public class DbcComposerTest {

	/*
	 * Data una formula da args, chiamare il parser, che resituisce la gerarchia di
	 * oggetti Composition C, invocare su C un eval overloaded con controllo
	 * pre-post e invariants e sollevamento eccezioni dei contratti. Se passa viene
	 * simulata la composizione, se fallisce deve riportare il msg di errore e l'asm
	 * che è fallita indicando cosa è fallito (pre-post-invariant)
	 */

	String path = "examples/dbc_examples/";

	@Test
	public void testLeafPre() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		LeafAsm c = new LeafAsm(path + "asmCPre.asm");
		try {
			c.eval(true);
			System.out.println(c.toString());
			System.out.println(" ===== new step =====");
			c.eval(true);
			System.out.println(c.toString());
		} catch (CompositionException e) {
			System.out.println(e.getMessage());
			System.out.println(c.toString());
		}
	}

	@Test
	public void testLeafPrePost() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		LeafAsm c = new LeafAsm(path + "asmCPrePost.asm");
		try {
			c.eval(true);
			System.out.println(c.toString());
			System.out.println(" ===== new step =====");
			c.eval(true);
			System.out.println(c.toString());
		} catch (CompositionException e) {
			System.out.println(e.getMessage());
			System.out.println(c.toString());
		}
	}
	
	@Test
	public void testPipe() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		PipeN c = new PipeN(new LeafAsm(path+"asmCPrePost.asm"), new LeafAsm(path + "asmCPrePostInc.asm"));
		try {
			c.eval(true);
			System.out.println(c.toString());
			System.out.println(" ===== new step =====");
			c.eval(true);
			System.out.println(c.toString());
		} catch (CompositionException e) {
			System.out.println(e.getMessage());
			System.out.println(c.toString());
		}
	}
	
}
