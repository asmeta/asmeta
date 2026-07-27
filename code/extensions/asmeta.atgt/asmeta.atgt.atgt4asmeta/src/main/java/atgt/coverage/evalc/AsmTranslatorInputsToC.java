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
package atgt.coverage.evalc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import atgt.coverage.AsmTestCondition;
import atgt.coverage.TPIndex;
import atgt.coverage.ToTpIndex;
import atgt.coverage.VisitableTPTreeNode;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Constant;
import atgt.specification.location.DerivedFunction;
import atgt.specification.location.Function;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;
import atgt.specification.statement.RuleDeclaration;
import atgt.translator.ExpressionToC;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.Undef;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.Type;
import tgtlib.evalcoverage.TranslatorInputsToC;
import tgtlib.util.IterableEnumeration;

/**
 * The Class AsmTranslatorInputsToC.
 *
 * @author garganti
 */
public class AsmTranslatorInputsToC extends TranslatorInputsToC<ASMSpecification> {

	/** The test predicates. */
	private TPIndex testPredicates;

	/**
	 * if to null: no predicates.
	 *
	 * @param s
	 *            the s
	 * @param tp
	 *            the tp
	 */
	public AsmTranslatorInputsToC(ASMSpecification s, VisitableTPTreeNode tp) {
		super(s, false);
		if (tp != null) {
			testPredicates = tp.accept(ToTpIndex.INSTANCE);
		}
	}

	/** cached copy. */
	Map<String, String> tps;

	/*
	 * (non-Javadoc)
	 *
	 * @see tgtlib.evalcoverage.TranslatorInputsToC#getTestPredicates()
	 */
	@Override
	protected Map<String, String> getTestPredicates() {
		if (tps == null) {
			tps = new HashMap<String, String>();
			for (Entry<String, AsmTestCondition> i : testPredicates.entrySet()) {
				tps.put(i.getKey(), i.getValue().getCondition().accept(ExpressionToC.EXPR_TO_C).toString());
			}
		}
		return tps;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tgtlib.evalcoverage.TranslatorInputsToC#getTransactionPart()
	 */
	@Override
	protected StringBuffer getTransactionPart() {
		atgt.specification.statement.RuleDeclaration rl = mySpec.getMainrule();
		// if the main rule is null take the par of all the rules
		if (rl == null) {
			StringBuffer sb = new StringBuffer("");
			for (RuleDeclaration r : mySpec.allRules()) {
				sb.append(r.getBody().accept(RuleToC.RULE_TO_C)).append("\n");
			}
			return sb;
		} else {

			return rl.getBody().accept(RuleToC.RULE_TO_C);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tgtlib.evalcoverage.TranslatorInputsToC#getConstantsDefs()
	 */
	@Override
	protected Map<String, String> getConstantsDefs() {
		Map<String, String> tps = new HashMap<String, String>();
		// constants
		for (Constant c : mySpec.allConstants()) {
			tps.put(c.getName(), c.getValue().accept(ExpressionToC.EXPR_TO_C).toString());
		}
		// also the derived
		for (DerivedFunction d : mySpec.allDerivedFuntion()) {
			tps.put(d.getName(), d.getValue().accept(ExpressionToC.EXPR_TO_C).toString());
		}
		return tps;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tgtlib.evalcoverage.TranslatorInputsToC#getEnumConstDefs()
	 */
	@Override
	protected List<String> getEnumConstDefs() {
		List<String> result = new Vector<String>();
		for (Type type : mySpec.allTypes()) {
			if (type instanceof tgtlib.definitions.expression.type.EnumType
					&& !(type instanceof tgtlib.definitions.expression.type.BoolType)) {
				ElementsType et = (ElementsType) type;
				for (EnumConst e : et.allElements()) {
					result.add(e.toString());
				}
			}
		}
		return result;
	}

	/**
	 * The Class VarSelector.
	 */
	abstract class VarSelector {

		/**
		 * Accept.
		 *
		 * @param var
		 *            the var
		 *
		 * @return true, if successful
		 */
		abstract boolean accept(Location var);
	}

	/**
	 * The Class MonVarSelector.
	 */
	class MonVarSelector extends VarSelector {

		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * atgt.coverage.evalc.AsmTranslatorInputsToC.VarSelector#accept(atgt.
		 * specification.location.Location)
		 */
		@Override
		boolean accept(Location var) {
			return var.isMonitored();
		}
	}

	/** The mon var selector. */
	MonVarSelector monVarSelector = new MonVarSelector();

	/**
	 * The Class NonMonVarSelector.
	 */
	class NonMonVarSelector extends VarSelector {

		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * atgt.coverage.evalc.AsmTranslatorInputsToC.VarSelector#accept(atgt.
		 * specification.location.Location)
		 */
		@Override
		boolean accept(Location var) {
			return !var.isMonitored();
		}
	}

	/** The controlled var selector. */
	NonMonVarSelector controlledVarSelector = new NonMonVarSelector();

	/*
	 * (non-Javadoc)
	 *
	 * @see tgtlib.evalcoverage.TranslatorInputsToC#getOneStateVarsDecl()
	 */
	@Override
	protected Map<String, String> getOneStateVarsDecl() {
		return getVarsDecl(monVarSelector);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tgtlib.evalcoverage.TranslatorInputsToC#getTwoStateVarsDecl()
	 */
	@Override
	protected Map<String, String> getTwoStateVarsDecl() {
		return getVarsDecl(controlledVarSelector);
	}

	/**
	 * initial values - can be null.
	 *
	 * @param varselect
	 *            the varselect
	 *
	 * @return the vars decl
	 */
	private Map<String, String> getVarsDecl(VarSelector varselect) {
		Map<String, String> vars = new HashMap<String, String>();
		Iterator<Variable> allvars = new IterableEnumeration<Variable>(mySpec.allVariables()).iterator();
		while (allvars.hasNext()) {
			Variable var = allvars.next();
			if (varselect.accept(var)) {
				Expression initVal = var.getValue();
				if (initVal != null && initVal != Undef.UNDEF) {
					StringBuffer value = var.getValue().accept(ExpressionToC.EXPR_TO_C);
					vars.put(var.getName(), value.toString());
				} else {
					vars.put(var.getName(), null);
				}
			}
		}
		Iterator<Function> allFunctions = new IterableEnumeration<Function>(mySpec.allFunction()).iterator();
		while (allFunctions.hasNext()) {
			Function fun = allFunctions.next();
			int range = fun.getDomain().range();
			if (varselect.accept(fun)) {
				Expression initVal = fun.getValue();
				// TODO gestire il valore iniziale di una funzione
				//assert initVal == null;
				vars.put(fun.getName() + "[" + range + "]", null);
			}
		}
		return vars;
	}
}