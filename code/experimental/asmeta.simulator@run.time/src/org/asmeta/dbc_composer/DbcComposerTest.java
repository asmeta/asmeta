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
	 * che � fallita indicando cosa � fallito (pre-post-invariant)
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
		PipeN c = new PipeN(new LeafAsm(path+"asmCPrePost.asm"), new LeafAsm(path + "asmCPrePostInc.asm"), new LeafAsm(path + "asmCPrePostMult.asm"));
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
	public void testPar() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		ParN c = new ParN(new LeafAsm(path+"asmCPrePost.asm"), new LeafAsm(path + "asmCPrePost.asm"));
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
	//asmCPrePostPar | (asmCPrePostInc || asmCPrePostMult)
	public void testPipePar() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		ParN c2 = new ParN(new LeafAsm(path+"asmCPrePostInc.asm"), new LeafAsm(path + "asmCPrePostMult.asm"));
		PipeN c = new PipeN(new LeafAsm(path+"asmCPrePostPar.asm"),c2);
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
	//asmCPrePost <|> asmCPrePostIncBip
	public void testBiPipe() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipeHalfDup c = new BiPipeHalfDup(new LeafAsm(path+"asmCPrePost.asm"), new LeafAsm(path + "asmCPrePostIncBip.asm"));
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
	//asmCPrePost <||> asmCPrePostIncBip
	public void testFullPipe() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipeFullDup c = new BiPipeFullDup(new LeafAsm(path+"asmCPrePost.asm"), new LeafAsm(path + "asmCPrePostIncBip.asm"));
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
	public void testParseLeaf() throws Exception {
		//String a = "( ".concat(path + "asmC.asm").concat(" <|> ").concat(path + "asmCPre.asm").concat(" )");
		//Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		String a = path + "asmCPre.asm";
		System.out.println(a);
		Parser asm = new Parser(a);
		Composition asmC = asm.toComposition();
		System.out.println(asmC.toString());
		try {
			asmC.eval(true);
			System.out.println(asmC.toString());
			
		}
		catch (CompositionException e) {
			System.out.println(e.getMessage());
			System.out.println(asmC.toString());
		}
	}
	
	@Test 
	public void testParsePipeN() throws Exception {
		String a = path + "asmCPrePost.asm";
		String b = path + "asmCPrePostInc.asm";
		String c = path + "asmCPrePostInc.asm";
		String s = "( "+a+" | "+b+" | "+c+" )";
		//Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		System.out.println(s);
		Parser asm = new Parser(s);
		Composition asmC = asm.toComposition();
		System.out.println(asmC.toString());
		try {
			asmC.eval(true);
			System.out.println(asmC.toString());
			
		}
		catch (CompositionException e) {
			System.out.println(e.getMessage());
			System.out.println(asmC.toString());
		}
	}
	

	@Test 
	public void testParsePar() throws Exception {
		String a = "examples/dbc_examples/Pillbox_DBC/configMgr.asm";
		String b = "examples/dbc_examples/Pillbox_DBC/rescheduler.asm";
		String s = a+" || "+b;
		//Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		System.out.println(s);
		Parser asm = new Parser(s);
		Composition asmC = asm.toComposition();
		System.out.println(asmC.toString());
		try {
			asmC.eval(true);
			System.out.println(asmC.toString());
			
		}
		catch (CompositionException e) {
			System.out.println(e.getMessage());
			System.out.println(asmC.toString());
		}
	}
	
}
