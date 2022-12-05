package temp;

import static org.junit.Assert.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.simulator.main.Simulator;
import org.junit.Test;

public class CompositionTest {

	String path = "examples/testUnbound/";
	String path2 = "examples/MVM/";
	String pathTrafficLight = "examples/trafficLightCoSim/";

	@Test
	public void test3() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipe asm2 = new BiPipe(new LeafAsm(path + "asmC.asm"), new LeafAsm(path + "asmS.asm"));
		BiPipe b1 = new BiPipe(new LeafAsm(path + "asmH.asm"), asm2);
		b1.eval();
		System.out.println(" ===== new step =====");
		asm2.eval();
	}

	@Test
	public void test2() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipe asm2 = new BiPipe(new LeafAsm(path + "asmC.asm"), new LeafAsm(path + "asmS.asm"));
		asm2.eval();
		System.out.println(" ===== new step =====");
		asm2.eval();
	}

	@Test
	public void testTrafficLight() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		Par asm2 = new Par(new LeafAsm(pathTrafficLight + "trafficlightA.asm"),
				new LeafAsm(pathTrafficLight + "trafficlightB.asm"));
		Pipe b1 = new Pipe(new LeafAsm(pathTrafficLight + "controller.asm"), asm2);
		while (true) {
			b1.eval();
			System.out.println(" ===== new step =====");
		}
	}
	
	@Test
	public void testTrafficLight2() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		Par asm1 = new Par(new LeafAsm(pathTrafficLight + "trafficlightB.asm"),
				new LeafAsm(pathTrafficLight + "trafficlightB.asm"));
		Par asm2 = new Par(new LeafAsm(pathTrafficLight + "trafficlightA.asm"),asm1);
		Par asm3 = new Par(new LeafAsm(pathTrafficLight + "trafficlightA.asm"),asm2);
		Pipe b1 = new Pipe(new LeafAsm(pathTrafficLight + "controller.asm"), asm3);
		while (true) {
			b1.eval();
			System.out.println(" ===== new step =====");
		}
	}

	@Test
	public void testMVM() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipe asm2 = new BiPipe(new LeafAsm(path2 + "MVMcontroller04.asm"), new LeafAsm(path2 + "supervisor03.asm"));
		BiPipe b1 = new BiPipe(new LeafAsm(path2 + "MVMHardware.asm"), asm2);
		int count = 1;
		while (true) {
			System.out.println(" ===== I/O  ASM  assembly " + count + " =====");
			b1.eval();
			count++;
		}
	}
	
	//nuovo Codice
	@Test
	public void testFullDup() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipeFullDup asmTest = new BiPipeFullDup(new LeafAsm(path + "asmC.asm"), new LeafAsm(path + "asmS.asm"));
		asmTest.evalbis();
	}
	
	@Test
	public void testParN() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		ParN asm = new ParN(new LeafAsm(path + "asmC.asm"),new LeafAsm(path + "asmS.asm"),new LeafAsm(path + "asmH.asm"));
		asm.eval();
	}
	
	@Test
	public void testPipeN() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		PipeN asm = new PipeN(new LeafAsm(path + "asmC.asm"),new LeafAsm(path + "asmS.asm"),new LeafAsm(path + "asmH.asm"));
		asm.eval();
	}
	
	@Test
	public void testPipe() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		Pipe asm = new Pipe(new LeafAsm(path + "asmC.asm"), new LeafAsm(path + "asmS.asm"));
		asm.eval();
	}


}
