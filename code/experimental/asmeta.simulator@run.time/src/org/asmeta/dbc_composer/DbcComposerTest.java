package org.asmeta.dbc_composer;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.simulator.RuleEvaluator;
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

	@Test //To test rescheduler and configuration manager with pillbox and compartments
	public void testPillboxCompWithRes() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.ALL);
		Logger.getLogger(RuleEvaluator.class).setLevel(Level.ALL);
		LeafAsm cm = new LeafAsm(path + "Pillbox_DBC/configMgr.asm",false);
		LeafAsm res = new LeafAsm(path + "Pillbox_DBC/rescheduler.asm",false);
		ParN cmres = new ParN(cm,res);
		LeafAsm c1 = new LeafAsm(path + "Pillbox_DBC/compartment.asm",false);
		LeafAsm c2 = new LeafAsm(path + "Pillbox_DBC/compartment.asm",false);
		LeafAsm pb = new LeafAsm(path + "Pillbox_DBC/pillbox.asm",false);
		ParN c1c2 = new ParN(c1,c2);
		BiPipeHalfDup comp = new BiPipeHalfDup(pb, c1c2);
		BiPipeHalfDup compfull = new BiPipeHalfDup(comp, cmres);
		Map<String, String> mon = new HashMap<>();
		try {	
			
			System.out.println(" ===== new step ===== Config. + Rescheduler" );
			mon.put("pillboxSystemTime","0");
			mon.put("day","0");
			mon.put("isPillMissed(compartment1)","false");
			mon.put("pillTakenWithDelay(compartment1)","false");
			mon.put("isPillMissed(compartment2)","false");
			mon.put("pillTakenWithDelay(compartment2)","false");
			mon.put("redLed(compartment1)","OFF");
			mon.put("redLed(compartment2)","OFF");
			mon.put("name(compartment1)","\"fosamax\"");
			mon.put("name(compartment2)","\"moment\"");
			mon.put("time_consumption(compartment1)","[360]");
			mon.put("time_consumption(compartment2)","[730,1140]");
			mon.put("actual_time_consumption(compartment1)","[0]");
			mon.put("actual_time_consumption(compartment2)","[0,0]");
			mon.put("drugIndex(compartment1)","0");
			mon.put("drugIndex(compartment2)","0");
			System.out.print(mon);
			cmres.eval(true, mon);
			System.out.println(cmres.toString());
						
			System.out.println(" ===== new step ===== " + c1.name);
			mon.clear();
			mon.put("myID", "1");
			mon.put("outMess(undef)", "\"\"");
			mon.put("led(undef)", "OFF");
			System.out.print(mon);
			c1.eval(true, mon);
			System.out.println(c1.toString());
			
			System.out.println(" ===== new step ===== " + c2.name);
			mon.clear();
			mon.put("myID", "2");
			mon.put("outMess(undef)", "\"\"");
			mon.put("led(undef)", "OFF");
			System.out.print(mon);
			c2.eval(true, mon);
			System.out.println(c2.toString());
			
			System.out.println(" ===== new step ===== bipipe");
			mon.clear();
			mon.put("compartmentOpen(1)","false");
			mon.put("compartmentOpen(2)","false");
			mon.put("skipNextPill(compartment1,compartment2)","false");
			mon.put("skipNextPill(compartment2,compartment1)","false");
			mon.put("newTime(compartment1)", "0n");
			mon.put("newTime(compartment2)", "0n");
			mon.put("setOriginalTime(compartment1)", "false");
			mon.put("setOriginalTime(compartment2)", "false");
			mon.put("setNewTime(compartment1)", "false");
			mon.put("setNewTime(compartment2)", "false");
			mon.put("systemTime", "0n");
			mon.put("outMess(undef)", "\"\"");
			mon.put("led(undef)", "OFF");
			mon.put("myID", "1");
			System.out.print(mon);
			comp.eval(true, mon);
			System.out.println(comp.toString());
		}catch (CompositionException e) {
			System.out.println(e.getMessage());
		}
	}

	
	@Test //To test pillbox and compartments
	public void testPillboxComp() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.ALL);
		Logger.getLogger(RuleEvaluator.class).setLevel(Level.ALL);
		LeafAsm c1 = new LeafAsm(path + "Pillbox_DBC/compartment.asm",false);
		LeafAsm c2 = new LeafAsm(path + "Pillbox_DBC/compartment.asm",false);
		LeafAsm pb = new LeafAsm(path + "Pillbox_DBC/pillbox.asm",false);
		ParN c1c2 = new ParN(c1,c2);
		BiPipeHalfDup comp = new BiPipeHalfDup(pb, c1c2);
		Map<String, String> mon = new HashMap<>();
		try {	
			System.out.println(" ===== new step ===== " + c1.name);
			mon.put("myID", "1");
			mon.put("outMess(undef)", "\"\"");
			mon.put("led(undef)", "OFF");
			System.out.print(mon);
			c1.eval(true, mon);
			System.out.println(c1.toString());
			
			System.out.println(" ===== new step ===== " + c2.name);
			mon.clear();
			mon.put("myID", "2");
			mon.put("outMess(undef)", "\"\"");
			mon.put("led(undef)", "OFF");
			System.out.print(mon);
			c2.eval(true, mon);
			System.out.println(c2.toString());
			
			System.out.println(" ===== new step ===== bipipe");
			mon.clear();
			mon.put("compartmentOpen(1)","false");
			mon.put("compartmentOpen(2)","false");
			mon.put("skipNextPill(compartment1,compartment2)","false");
			mon.put("skipNextPill(compartment2,compartment1)","false");
			mon.put("newTime(compartment1)", "0n");
			mon.put("newTime(compartment2)", "0n");
			mon.put("setOriginalTime(compartment1)", "false");
			mon.put("setOriginalTime(compartment2)", "false");
			mon.put("setNewTime(compartment1)", "false");
			mon.put("setNewTime(compartment2)", "false");
			mon.put("systemTime", "0n");
			mon.put("outMess(undef)", "\"\"");
			mon.put("led(undef)", "OFF");
			mon.put("myID", "1");
			System.out.print(mon);
			comp.eval(true, mon);
			System.out.println(comp.toString());
		}catch (CompositionException e) {
			System.out.println(e.getMessage());
		}
	}


	@Test //To test a leaf ASM fed with an hashmap of monitored  functions
	public void testLeafMon() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		LeafAsm c = new LeafAsm(path + "asmCPre.asm",false);
		Map<String, String> mon = new HashMap<>();
		try {
			mon.put("funcS","5");
			mon.put("funcH","2");
			mon.put("myinput", "10");
			System.out.print(mon);
			c.eval(true,mon);
			System.out.println(c.toString());
			System.out.println(" ===== new step =====");
			c.eval(true,mon);
			System.out.println(c.toString());
		} catch (CompositionException e) {
			System.out.println(e.getMessage());
			System.out.println(c.toString());
		}
	}

	@Test
	public void testLeafPre() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		LeafAsm c = new LeafAsm(path + "asmCPre.asm",true);
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
		LeafAsm c = new LeafAsm(path + "asmCPrePost.asm",true);
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
		PipeN c = new PipeN(new LeafAsm(path+"asmCPrePost.asm",true), new LeafAsm(path + "asmCPrePostInc.asm",true), new LeafAsm(path + "asmCPrePostMult.asm",true));
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
		ParN c = new ParN(new LeafAsm(path+"asmCPrePost.asm",true), new LeafAsm(path + "asmCPrePost.asm",true));
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
		ParN c2 = new ParN(new LeafAsm(path+"asmCPrePostInc.asm",true), new LeafAsm(path + "asmCPrePostMult.asm",true));
		PipeN c = new PipeN(new LeafAsm(path+"asmCPrePostPar.asm",true),c2);
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
		BiPipeHalfDup c = new BiPipeHalfDup(new LeafAsm(path+"asmCPrePost.asm",true), new LeafAsm(path + "asmCPrePostIncBip.asm",true));
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
		BiPipeFullDup c = new BiPipeFullDup(new LeafAsm(path+"asmCPrePost.asm",true), new LeafAsm(path + "asmCPrePostIncBip.asm",true));
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
		Parser asm = new Parser(a,true);
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
		Parser asm = new Parser(s,true);
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
		String s = "( "+a+" || "+b+" )"; //Questa funziona e richiede gli spazi dopo le parentesi
		//String s = a+" || "+b; //non funziona senza parentesi
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		System.out.println(s);
		Parser asm = new Parser(s,true);
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
	public void testBiPipePillbox() throws Exception {
		//Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipeHalfDup c = new BiPipeHalfDup(new LeafAsm("examples/dbc_examples/Pillbox_DBC/pillbox.asm",true), new LeafAsm("examples/dbc_examples/Pillbox_DBC/compartment.asm",true));
		try {
			c.eval(true);
			System.out.println(c.toString());
			System.out.println(" ===== new step =====");
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
	public void testBiPipePillboxRescheduler() throws Exception {
		//Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipeHalfDup c = new BiPipeHalfDup(new LeafAsm("examples/dbc_examples/Pillbox_DBC/rescheduler.asm",false), new LeafAsm("examples/dbc_examples/Pillbox_DBC/pillbox.asm",false));
		Map<String, String> mon = new HashMap<>();
		try {
			mon.put("setNewTime(compartment1)","false");
			mon.put("skipNextPill(compartment1)","false");
			mon.put("systemTime","361");
			mon.put("openSwitch(compartment1)","false");
			mon.put("setNewTime(compartment2)","false");
			mon.put("skipNextPill(compartment2)","false");
			mon.put("openSwitch(compartment2)","false");
			c.eval(true,mon);
			System.out.println(c.toString());
			System.out.println(" ===== new step =====");
			/*c.eval(true,mon);
			System.out.println(c.toString());
			System.out.println(" ===== new step =====");
			c.eval(true,mon);
			System.out.println(c.toString());
			*/
		} catch (CompositionException e) {
			System.out.println(e.getMessage());
			System.out.println(c.toString());
		}
	}
	
}
