package atgt.combinatorial.yices;

import java.io.IOException;

import org.apache.log4j.Level;
import org.junit.BeforeClass;
import org.junit.Test;

import atgt.coverage.AsmTestSequence;
import atgt.generator.collection.TPCompatibleCollectorTest;
import atgt.parser.asmgofer.ParseException;
import atgt.project.AsmProject;
import atgt.specification.ASMSpecification;
import tgtlib.generator.ModelCheckerExecutionException;
import tgtlib.generator.TestSequenceGenerator;

/**Classe di test che verifica la corretta esecuzione di Yices con un dato modello
 * contenente anche degli assiomi e con un modello che non ne contiene.
 * Deve testare la consistenza.*/
public class YicesCompatibleCollectorTest extends TPCompatibleCollectorTest{
	
	@BeforeClass
	public static void activateLog() {
		YicesModelGen.logger.setLevel(Level.DEBUG);
		YicesModelGenExec.logger.setLevel(Level.DEBUG);
		YicesModelGen.class.getClassLoader().setDefaultAssertionStatus(true);
	}		
	
	/**
	 * Test consistent with axioms.
	 * Sovrascrive il metodo della classe padre.
	 * 
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

	/**Metodo per l'inizializzazione del generatore dei test. Implementazione del
	 * metodo astratto della classe padre.
	 * 
	 * @param pro contiene le specifiche del sistema sottoposto a test.
	 * @return gen restituisce il generatore AsmTestSeqGenerator, in questo caso di Yices.*/
	@Override
	protected TestSequenceGenerator getTSeqgenerator(AsmProject pro) {
		ASMSpecification asm = pro.specification;
		YicesModelGenExec gen = new YicesModelGenExec(asm.getVariables(),asm.getAxiom(),AsmTestSequence.factory);
		return gen;
	}

}