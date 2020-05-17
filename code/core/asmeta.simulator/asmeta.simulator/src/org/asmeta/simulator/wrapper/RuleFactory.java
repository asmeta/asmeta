/*
 * RuleFactory.java
 *
 * Created on 27 giugno 2006, 9.40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.simulator.wrapper;

import asmeta.transitionrules.TransitionRulesFactory;
import asmeta.transitionrules.basictransitionrules.BasictransitionrulesFactory;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.derivedtransitionrules.DerivedtransitionrulesFactory;
import asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import asmeta.transitionrules.turbotransitionrules.TurboCallRule;
import asmeta.transitionrules.turbotransitionrules.TurboReturnRule;
import asmeta.transitionrules.turbotransitionrules.TurbotransitionrulesFactory;

/**
 * A rule factory.
 */
public class RuleFactory extends TermFactory {
	private BasictransitionrulesFactory rulePackage;
	private TurbotransitionrulesFactory turboPackage;
	private DerivedtransitionrulesFactory derivedRulesPackage;

	/**
	 * Creates a rule factory.
	 * 
	 * @param asmetaPackage
	 *            an ASMETA package
	 */
	public RuleFactory() {
		rulePackage = TransitionRulesFactory.eINSTANCE.getBasicTransitionRules();
		turboPackage = TransitionRulesFactory.eINSTANCE.getTurboTransitionRules();
		derivedRulesPackage = TransitionRulesFactory.eINSTANCE.getDerivedTransitionRules();
	}

	public SkipRule createSkipRule() {
		return rulePackage.createSkipRule();
	}

	public UpdateRule createUpdateRule() {
		return rulePackage.createUpdateRule();
	}

	public BlockRule createBlockRule() {
		return rulePackage.createBlockRule();
	}

	public SeqRule createSeqRule() {
		return turboPackage.createSeqRule();
	}

	public ConditionalRule createConditionalRule() {
		return rulePackage.createConditionalRule();
	}

	public LetRule createLetRule() {
		return rulePackage.createLetRule();
	}

	public ForallRule createForallRule() {
		return rulePackage.createForallRule();
	}

	public ChooseRule createChooseRule() {
		return rulePackage.createChooseRule();
	}

	public IterativeWhileRule createIterativeWhileRule() {
		return derivedRulesPackage.createIterativeWhileRule();
	}

	public MacroCallRule createMacroCallRule() {
		return rulePackage.createMacroCallRule();
	}

	public TurboCallRule createTurboCallRule() {
		return turboPackage.createTurboCallRule();
	}

	public TurboReturnRule createTurboReturnRule() {
		return turboPackage.createTurboReturnRule();
	}

	public TermAsRule createTermAsRule() {
		return rulePackage.createTermAsRule();
	}

	public CaseRule createCaseRule() {
		return derivedRulesPackage.createCaseRule();
	}

	public ExtendRule createExtendRule() {
		return rulePackage.createExtendRule();
	}

	public MacroDeclaration createMacroDeclaration() {
		return rulePackage.createMacroDeclaration();
	}
}
