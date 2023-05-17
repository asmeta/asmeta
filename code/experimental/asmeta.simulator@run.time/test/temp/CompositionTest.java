package temp;

import static org.junit.Assert.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.simulator.main.Simulator;
import org.junit.Test;

public class CompositionTest {

	String path = "examples/testUnbound/";
	String path2 = "examples/MVM/";
	String path3 = "examples/exampleFAC2023/";

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
	public void test4() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipe asm2 = new BiPipe(new LeafAsm(path3 + "asmInc.asm"), new LeafAsm(path3 + "asmDec.asm"));
		Pipe asm3 = new Pipe(new LeafAsm(path3 + "asmMulti.asm"), asm2);
		asm3.eval();
		System.out.println(" ===== new step =====");
		asm3.eval();
	}

	
	
	@Test
	public void testMVM() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipe asm2 = new BiPipe(new LeafAsm(path2 + "MVMcontroller04.asm"), new LeafAsm(path2 + "supervisor03.asm"));
		BiPipe b1 = new BiPipe(new LeafAsm(path2 + "MVMHardware.asm"), asm2);
		int count = 1;
		while (true) {
			System.out.println(" ===== I/O  ASM  assembly "+ count +" =====");
			b1.eval();
			count++;
		}
	}

}
