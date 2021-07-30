package org.asmeta.simulator.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.Utility;
import org.asmeta.simulator.NotCompatibleDomainsException;
import org.asmeta.simulator.RuleEvaluator;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class ActualParameterTest extends BaseTest {

	@BeforeClass
	public static void setup() {
		ASMParser.getResultLogger().setLevel(Level.OFF);
		Logger.getLogger(Simulator.class).setLevel(Level.OFF);
		RuleEvaluator.logger.setLevel(Level.OFF);
		Utility.selectFirstBestRanking = false;
	}

	//viene sollevata giustamente l'eccezione
	@Ignore // ANGELO, ignore this test, I'm not sure it is correct 2021.05
	@Test(expected = NotCompatibleDomainsException.class)
	public void test01() throws Throwable {
		sim = Simulator.createSimulator(BASE + "test/simulator/wrongDomainAsActualParameter.asm");
		try {
			sim.run(2);
		}
		catch(RuntimeException re) {
			assertEquals(re.getCause().getClass(), NotCompatibleDomainsException.class);
			throw re.getCause();
		}
	}

	//l'eccezione viene sollevata ugualmente. Il termine non cambia il suo dominio runtime...
	@Test
	public void test02() throws Exception {
		sim = Simulator.createSimulator(BASE + "test/simulator/correctDomainAsActualParameter.asm");
		try {
			sim.run(2);
		}
		catch(RuntimeException re) {
			if(re.getCause().getClass() == NotCompatibleDomainsException.class) {
				fail("Viene lanciata un'eccezione NotCompatibleDomainsException, ma non dovrebbe.");
			}
		}
	}
}