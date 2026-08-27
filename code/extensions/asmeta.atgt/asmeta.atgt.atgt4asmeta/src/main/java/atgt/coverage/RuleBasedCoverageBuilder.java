/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package atgt.coverage;

import java.util.List;
import java.util.Vector;

import atgt.specification.ASMSpecification;
import atgt.specification.statement.DoStatement;
import atgt.specification.statement.MacroCallRule;
import atgt.specification.statement.RuleDeclaration;
import tgtlib.definitions.NamedTerm;

/**
 * Given a SPECIFICATION returns a Coverage (tree root with test predicates)
 * for Rules and Rules declaration returns a List of named expressions
 * representing the coverage.
 */

public abstract class RuleBasedCoverageBuilder extends RuleTPExpractor implements AsmCoverageBuilder {

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * Gets the abbr name.
	 * 
	 * @return the abbr name
	 */
	public abstract String getAbbrName();

	// this part for visiting RuleDecl and Specification
	/* for a rule declaration call for that rule */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.statement.RuleDeclarationVisitor#forRuleDeclaration
	 * (atgt.specification.statement.RuleDeclaration)
	 */
	@Override
	final public List<NamedTerm> forRuleDeclaration(RuleDeclaration r) {
		String ruleName = r.getName();
		List<NamedTerm> tcforrule = r.getBody().accept(this);
		// change the name
		for (NamedTerm tc : tcforrule) {
			tc.setName(getAbbrName() + "_" + ruleName + "_" + tc.getName());
		}
		return (tcforrule);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeatgt.specification.SpecificationAnalyzer#analyze(atgt.specification.
	 * ASMSpecification)
	 */
	@Override
	final public Coverage getTPTree(ASMSpecification SP) {
		List<AsmTestCondition> list = new Vector<AsmTestCondition>();

		// Per ogni regola nella specifica esegue la generazione dei <I>Test
		// Predicate</I>
		// if SP has no main rule, otherwise, just generate for main rule
		// note: an asm without main rule has as main rule the par of all the
		// rules
		// as in asm gofer
		// TO CHANGE: Add a main rule in spec when reading gofer files
		if (SP.getMainrule() == null) {
			for (RuleDeclaration r: SP.allRules()){
				for (NamedTerm ne : r.accept(this)) {
					list.add(new AsmTestCondition(ne.getName(), ne
									.getCondition()));
				}
			}
		} else {
			RuleDeclaration rd = SP.getMainrule();
			for (NamedTerm ne : rd.accept(this)) {
				list.add(new AsmTestCondition(ne.getName(), ne.getCondition()));
			}
		}
		return new Coverage(getName(), list);
	}

	/**
	 * returns the unique prefix for the test goal generated for this coverage.
	 * 
	 * @return the coverage prefix
	 */
	@Override
	public String getCoveragePrefix(){
		return getAbbrName();
	}
	
	/**
	 * 
	 * @param ite
	 *            the ite
	 * 
	 * @return the list< named expression>
	 */
	@Override
	public final List<NamedTerm> forMacroCallRule(MacroCallRule ite) {
		RuleDeclaration ruleDeclaration = ite.getRuleDeclaration();
		// TODO substitute the parameters with the actual values
		if (!ruleDeclaration.getParamters().isEmpty()){
			// TODO do not know what to do if there are parameters
			return java.util.Collections.EMPTY_LIST;
		}
		return ruleDeclaration.accept(this);
	}

	/**
	 * It builds the test predicates for all the rules in the block.
	 * 
	 * @param d
	 *            the d
	 * 
	 * @return the list< named expression>
	 */
	@Override
	public final List<NamedTerm> forDoStatement(DoStatement d) {
		return d.addResults(this);
	}
}
