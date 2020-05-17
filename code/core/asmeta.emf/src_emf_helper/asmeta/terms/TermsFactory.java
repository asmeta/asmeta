package asmeta.terms;

import asmeta.terms.basicterms.BasictermsFactory;
import asmeta.terms.furtherterms.FurthertermsFactory;

public class TermsFactory {
	public static TermsFactory eINSTANCE = init();

	private static TermsFactory init() {
		return new TermsFactory();
	}

	public asmeta.terms.furtherterms.FurthertermsFactory getFurtherTerms() {
		return FurthertermsFactory.eINSTANCE;
	}

	public asmeta.terms.basicterms.BasictermsFactory getBasicTerms() {
		return BasictermsFactory.eINSTANCE;
	}
}
