package atgt.generator.testsuite;


import atgt.preferences.ATGToolPreferences;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import atgt.project.AsmProject;
import tgtlib.generator.ordering.ChooseRnd;
import tgtlib.generator.ordering.TPProcessor;

class TestGeneratorCollectTPTest {

	@Test void getTPProcessor() {
		ATGToolPreferences.TP_ORDERING.setValue(ATGToolPreferences.OrderKind.RANDOM);
		TestGeneratorCollectTP tgcoll = new TestGeneratorCollectTP(new AsmProject(), null,null);
		TPProcessor tpp = tgcoll.getTPProcessor(null, null);
		assertInstanceOf(ChooseRnd.class, tpp);
	}

}
