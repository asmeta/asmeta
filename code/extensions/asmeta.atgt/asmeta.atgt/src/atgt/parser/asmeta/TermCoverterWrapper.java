package atgt.parser.asmeta;

import asmeta.terms.basicterms.Term;
import atgt.specification.ASMSpecification;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.type.EnumConstCreator;

public class TermCoverterWrapper {
	TermConverter termConverter;

	public TermCoverterWrapper(ASMSpecification spec, EnumConstCreator ecc) {
		termConverter = new TermConverter(spec, ecc);
	}

	public Expression visit(Term guard) {
		return termConverter.translateTerm(guard);
	}

}
