package atgt.generator;

import java.io.File;

import atgt.coverage.AsmTestCondition;
import tgtlib.generator.TestPredMCInput;

/** A File + the test predicate
 * 
 * @author garganti
 *
 */
public class SriSalInput extends TestPredMCInput<AsmTestCondition> {

	public File spec;

	public SriSalInput(File specFile, AsmTestCondition tg) {
		super(tg);
		spec = specFile;
	}

}
