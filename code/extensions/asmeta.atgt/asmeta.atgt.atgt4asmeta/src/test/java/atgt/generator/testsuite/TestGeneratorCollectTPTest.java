package atgt.generator.testsuite;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import atgt.preferences.ATGToolPreferences;
import atgt.project.AsmProject;
import tgtlib.generator.ordering.ChooseRnd;
import tgtlib.generator.ordering.TPProcessor;

public class TestGeneratorCollectTPTest {

	@Test
	public void testGetTPProcessor() {
		ATGToolPreferences.TP_ORDERING.setValue(ATGToolPreferences.OrderKind.RANDOM);
		TestGeneratorCollectTP tgcoll = new TestGeneratorCollectTP(new AsmProject(), null,null);
		TPProcessor tpp = tgcoll.getTPProcessor(null, null);
		assertTrue(tpp instanceof ChooseRnd);
	}

}
