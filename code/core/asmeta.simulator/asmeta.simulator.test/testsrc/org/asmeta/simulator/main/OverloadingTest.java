package org.asmeta.simulator.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.parser.AsmetaParserUtility;
import org.asmeta.parser.ParseException;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;
import org.junit.jupiter.api.Test;

import asmeta.definitions.Function;

class OverloadingTest extends BaseTest {

	@Test void test01() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/parser/overloading/m1.asm");
		sim.run(1);
		State s = sim.getCurrentState();
		Function f1 = searchFunction("f1");
		Function f2 = searchFunction("f2");
		Function f3 = searchFunction("f3");
		Function f4 = searchFunction("f4");
		Value af1 = s.read(new Location(f1, new Value[0]));
		Value af2 = s.read(new Location(f2, new Value[0]));
		Value af3 = s.read(new Location(f3, new Value[0]));
		Value af4 = s.read(new Location(f4, new Value[0]));
		assertEquals(new IntegerValue(1), af1);
		assertEquals(new IntegerValue(1), af2);
		assertEquals(new IntegerValue(1), af3);
		assertEquals(UndefValue.UNDEF, af4);
	}

	@Test void test02() throws Exception {
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/parser/overloading/m2.asm");
		sim.run(1);
		State s = sim.getCurrentState();
		Function f = searchFunction("f");
		Value af = s.read(new Location(f, new Value[0]));
		assertEquals(new IntegerValue(1), af);
	}

	@Test void test03_optionFalse() throws Exception {
		Logger.getLogger(Simulator.class).setLevel(Level.OFF);
		AsmetaParserUtility.selectFirstBestRanking = false;
		assertThrows(ParseException.class, () -> {
			// too many compatible macro declarations
			sim = Simulator.createSimulator(ASM_EXAMPLES + "test/parser/overloading/m3.asm");
		});
	}

	@Test void test03_optionTrue() throws Exception {
		//ASMParser.getResultLogger().addAppender(new ConsoleAppender(new SimpleLayout()));
		//Simulator.logger.addAppender(new ConsoleAppender(new SimpleLayout()));
		Logger.getLogger(Simulator.class).setLevel(Level.OFF);
		AsmetaParserUtility.selectFirstBestRanking = true;
		// too many compatible macro declarations
		sim = Simulator.createSimulator(ASM_EXAMPLES + "test/parser/overloading/m3.asm");
	}
}