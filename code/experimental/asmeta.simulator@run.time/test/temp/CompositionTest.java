package temp;

import static org.junit.Assert.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.simulator.main.Simulator;
import org.junit.Test;

public class CompositionTest {

	String path = "examples/testUnbound/";

	@Test
	public void test3() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipe asm2 = new BiPipe(new LeafAsm(path + "asmC.asm"), new LeafAsm(path + "asmS.asm"));
		BiPipe b1 = new BiPipe(new LeafAsm(path + "asmH.asm"), asm2);
		b1.eval();
	}

	@Test
	public void test2() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.DEBUG);
		BiPipe asm2 = new BiPipe(new LeafAsm(path + "asmC.asm"), new LeafAsm(path + "asmS.asm"));
		asm2.eval();
	}

}
