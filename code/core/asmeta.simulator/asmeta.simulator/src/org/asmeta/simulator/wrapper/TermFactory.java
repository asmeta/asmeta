/*
 * TermFactory.java
 *
 * Created on 26 giugno 2006, 8.48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.simulator.wrapper;

import asmeta.terms.TermsFactory;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.RuleAsTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.ExistTerm;
import asmeta.terms.furtherterms.ExistUniqueTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.LetTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.SequenceCt;
import asmeta.terms.furtherterms.SequenceTerm;
import asmeta.terms.furtherterms.SetCt;

/**
 * A term factory.
 *
 */
public class TermFactory {
	private TermsFactory termFactory;

	/**
	 * Creates a term factory.
	 * 
	 * @param asmetaPackage
	 *            an ASMETA package
	 */
	public TermFactory() {
		termFactory = TermsFactory.eINSTANCE;
	}

	public BooleanTerm createBooleanTerm() {
		return termFactory.getBasicTerms().createBooleanTerm();
	}

	public IntegerTerm createIntegerTerm() {
		return termFactory.getFurtherTerms().createIntegerTerm();
	}

	public CaseTerm createCaseTerm() {
		return termFactory.getFurtherTerms().createCaseTerm();
	}

	public ConditionalTerm createConditionalTerm() {
		return termFactory.getFurtherTerms().createConditionalTerm();
	}

	public DomainTerm createDomainTerm() {
		return termFactory.getBasicTerms().createDomainTerm();
	}

	public EnumTerm createEnumTerm() {
		return termFactory.getFurtherTerms().createEnumTerm();
	}

	public ExistTerm createExistTerm() {
		return termFactory.getFurtherTerms().createExistTerm();
	}

	public ExistUniqueTerm createExistUniqueTerm() {
		return termFactory.getFurtherTerms().createExistUniqueTerm();
	}

	public ForallTerm createForallTerm() {
		return termFactory.getFurtherTerms().createForallTerm();
	}

	public FunctionTerm createFunctionTerm() {
		return termFactory.getBasicTerms().createFunctionTerm();
	}

	public LetTerm createLetTerm() {
		return termFactory.getFurtherTerms().createLetTerm();
	}

	public LocationTerm createLocationTerm() {
		return termFactory.getBasicTerms().createLocationTerm();
	}

	public NaturalTerm createNaturalTerm() {
		return termFactory.getFurtherTerms().createNaturalTerm();
	}

	public RuleAsTerm createRuleAsTerm() {
		return termFactory.getBasicTerms().createRuleAsTerm();
	}

	public SequenceCt createSequenceCt() {
		return termFactory.getFurtherTerms().createSequenceCt();
	}

	public SequenceTerm createSequenceTerm() {
		return termFactory.getFurtherTerms().createSequenceTerm();
	}

	public SetCt createSetCt() {
		return termFactory.getFurtherTerms().createSetCt();
	}

	public SetTerm createSetTerm() {
		return termFactory.getBasicTerms().createSetTerm();
	}

	public TupleTerm createTupleTerm() {
		return termFactory.getBasicTerms().createTupleTerm();
	}

	public UndefTerm createUndefTerm() {
		return termFactory.getBasicTerms().createUndefTerm();
	}

	public VariableTerm createVariableTerm() {
		return termFactory.getBasicTerms().createVariableTerm();
	}
}
