package atgt.generator.collection;

import java.io.IOException;

import org.junit.Test;

import atgt.generator.AsmTestSeqGenerator;
import atgt.generator.SalTSeqGenerator;
import atgt.parser.asmgofer.ParseException;
import atgt.project.AsmProject;
import tgtlib.generator.ModelCheckerExecutionException;

public class SALTPCompatibleCollectorTest extends TPCompatibleCollectorTest {

	/**
	 * Test consistent with axioms.
	 * @throws IOException 
	 * @throws ModelCheckerExecutionException 
	 * @throws ParseException 
	 * 
	 * @throws ParseException
	 *             the parse exception
	 * @throws IOException
	 * @throws ModelCheckerExecutionException
	 */
	@Override
	@Test
	public void testConsistentWithAxioms() throws ParseException, ModelCheckerExecutionException, IOException {
		super.testConsistentWithAxioms();
	}

	@Override
	protected AsmTestSeqGenerator getTSeqgenerator(AsmProject pro) {
		SalTSeqGenerator gen = new SalTSeqGenerator(pro.specification);
		return gen;
	}

}
