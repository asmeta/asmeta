package atgt.generator;

import atgt.coverage.AsmTestCondition;
import atgt.specification.ASMSpecification;
import atgt.translator.TranslatorVisitor;
import tgtlib.generator.MCInput;

/** 
 *  * <BR>
 * This Testgenerator contains also the translator (visitor) 

all the test translators whic use a SPEC -_> String translator
 * 
 * @author garganti
 *
 */
public abstract class TransTSeqGenerator<Q extends MCInput<? extends AsmTestCondition>> extends AsmTestSeqGenerator<Q> {

	/**
	 * the spec to be translated
	 */
	protected ASMSpecification spec;
	
	/**
	 * Lo
	 *
	 * <PRE>
	 * SpecificationVisitor
	 * </PRE>
	 *
	 * usato per la traduzione della specifica nel linguaggio target.
	 */
	protected TranslatorVisitor visitor;

}
