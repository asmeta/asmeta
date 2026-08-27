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
package atgt.translator;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import atgt.preferences.ATGToolPreferences;
import atgt.specification.ASMSpecification;
import atgt.specification.location.Constant;
import atgt.specification.location.DerivedFunction;
import atgt.specification.location.Function;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;
import atgt.specification.statement.RuleDeclaration;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.type.Type;

/**
 * The Class toSPINFlatVisitor translates ASM spec to Spin
 */
public class toSPINFlatVisitor extends ToSpinTranslatorVisitor {

	final static Logger log = Logger.getLogger(toSPINFlatVisitor.class);

	private static final boolean use_atomic_init = true;

	/**
	 * Instantiates a new to spin flat visitor.
	 */
	public toSPINFlatVisitor() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.translator.TranslatorVisitor#analyze(atgt.specification.
	 * ASMSpecification)
	 */
	@Override
	public StringBuffer analyze(ASMSpecification sp) {
		/** use of d_dtep ? */
		boolean use_d_step = ATGToolPreferences.USE_D_STEP.getValue();

		/** use of atomic ? */
		boolean use_atomic = ATGToolPreferences.USE_ATOMIC.getValue();

		StringBuffer result = new StringBuffer();

		// Translate the constant definition
		result.append("/* numeric constants */ \n");
		// result.append("bool True = 1;\n";
		// result.append("bool False = 0;\n";
		// result.append("mtype = {start}\n\n";

		result.append("/* types  */ \n");
		for (Type t : sp.allTypes()) {
			result.append(t.accept(new TypeToSPINVisitor()));
			result.append("\n");
		}
		result.append("\n/* constants */\n");
		LocationToSPINDeclarationVisitor locTrans = new LocationToSPINDeclarationVisitor();
		for (Constant c : sp.allConstants()) {
			result.append(c.accept(locTrans));
		}
		result.append("\n");

		// derived functions
		result.append("/* derived functions */ \n");
		for (DerivedFunction df : sp.allDerivedFuntion()) {
			// translate to spin
			Expression dfval = df.getValue();
			result.append("#define ").append(df.getName()).append(" ");
			result.append(dfval.accept(ExpressionToSPINVisitor.SINGLETON));
			result.append(System.lineSeparator());
		}
		result.append("\n");
		// Now it's variables turn
		result.append("/* variables definitions */ \n");
		for (Enumeration<Variable> e = sp.allVariables(); e.hasMoreElements();) {
			Variable nextElement = e.nextElement();
			result.append(nextElement.accept(locTrans));
		}
		// ****************************** FOR FUNCTION
		// Translate the Functions
		result.append("\n\n");
		result.append("/* functions definitions */ \n");
		for (Enumeration<Function> e = sp.allFunction(); e.hasMoreElements();) {
			result.append((e.nextElement()).accept(locTrans));
		}
		// USE INLINE FOR RULES
		// rule definitions
		result.append("\n\n");
		result.append("/* rules definitions */ \n");

		// map from rule declaration and inline functions of spin
		// spin does not allow several rules with same method and ATGT/asmeta is
		// allowed
		// using double map here
		BiMap<RuleDeclaration, String> ruleDecSpinNames = new BiMap<>();
		for (RuleDeclaration rd : sp.allRules()) {
			if (rd == sp.getMainrule())
				continue;
			String spinName = rd.getName();
			while (ruleDecSpinNames.get2(spinName) != null) {
				spinName += 'i';
			}
			ruleDecSpinNames.put(rd, spinName);
			log.debug("");
		}
		// get the names now
		for (RuleDeclaration rd : sp.allRules()) {
			if (rd == sp.getMainrule())
				continue;
			//
			String spinName = ruleDecSpinNames.get1(rd);
			result.append("inline " + spinName + "(");
			// add the parameters
			if (!rd.getParamters().isEmpty()) {
				// parameters do not need types (it's inline process)
				List<IdExpression> paramters = rd.getParamters();
				for (int i = 0; i < paramters.size(); i++) {
					IdExpression p = paramters.get(i);
					if (i > 0)
						result.append(",");
					StringBuffer idString = p.accept(ExpressionToC.EXPR_TO_C);
					result.append(idString);
				}
			}
			result.append("){\n");
			StatementToSPINVisitor ask = new StatementToSPINVisitor(indent, ruleDecSpinNames.getMap1());
			result.append(rd.getBody().accept(ask));
			result.append("\n}\n");
		}

		// ************************************************************************
		result.append("\n\n\n");

		// Begin the init function
		result.append("/* init function */\n");
		result.append("init{");
		if (use_atomic_init)
			result.append("\natomic{");
		result.append("\n/* print initial values and new state */\n");
		for (Enumeration<Variable> e = sp.allVariables(); e.hasMoreElements();) {
			result.append(e.nextElement().accept(InitFunctionToSPINVisitor.init));
		}

		for (Enumeration<Function> e = sp.allFunction(); e.hasMoreElements();) {
			result.append(e.nextElement().accept(InitFunctionToSPINVisitor.init));
		}
		if (use_atomic_init)
			result.append("\n}");
		result.append("printf(\"_New_State\\n\");\n");
		result.append("\n\n");

		result.append("\n/* main processing loop */\n");
		// Main loop for ambient simulation and rules firing
		this.indent += "\t";
		appendIndeted(result, "do \n");

		// indent += "\t";
		appendIndeted(result, "::\n");

		// RULES
		if (use_atomic)
			appendIndeted(result, "atomic{\n");
		if (use_d_step)
			appendIndeted(result, "\td_step{\n");

		// RuleDeclaration translation
		this.indent += "\t";
		result.append(
				this.indent + "/* Rules firing: update controlled locations and print controlled locations */\n\n\n");
		// of there is a main rules,just translate it
		if (sp.getMainrule() != null) {
			RuleDeclaration r = sp.getMainrule();
			result.append(r.getBody().accept(new StatementToSPINVisitor(this.indent, ruleDecSpinNames.getMap1())));
		} else {
			/*
			 * No more need to flatten the specs FLATTEN???? if
			 * (atgt.tgtlib.ATGToolPreferences.SPINOPTION.getValue(atgt.tgtlib.
			 * ATGToolPreferences.FLATTING).equals("false")){ //for(Enumeration
			 * e= sp.allRules(); e.hasMoreElements();){ for(Enumeration e=
			 * SPUnFlattering.allRules(); e.hasMoreElements();){ RuleDeclaration
			 * r = (RuleDeclaration)e.nextElement();
			 * result.append(r.getBody().accept(new
			 * StatementToSPINVisitor(r.getName(), indent))); } } else
			 */ {
				for (RuleDeclaration r : sp.allRules()) {
					result.append(
							r.getBody().accept(new StatementToSPINVisitor(this.indent, ruleDecSpinNames.getMap1())));
				}
			}
		}
		if (use_d_step)
			appendIndeted(result, "} /* End of d_step statement */\n");

		// assert here
		// add the assert not test condition
		appendIndeted(result, "/* specification assert ");
		if (this.tc != null)
			result.append(this.tc.getUniqueID());
		else
			result.append(" - not specified - ");
		result.append(" */\n");
		if (this.tc != null) {
			NotExpression assertExpression = NotExpression.createNotExpression(this.tc.getCondition());
			StringBuffer tpSB = assertExpression.accept(ExpressionToSPINVisitor.SINGLETON);
			result.append(this.indent).append("assert(").append(tpSB).append(");\n");
		}
		// Start modeling monitored variable simulation

		result.append(this.indent + "/* update of monitored variables and print*/\n");
		for (Enumeration<Variable> e = sp.allVariables(); e.hasMoreElements();) {
			Location v = e.nextElement();
			if (!(v.isControlled())) {
				result.append(v.accept(new monVarSimulationVisitor(sp.getAxiom(), this.indent + "\t")));
			}
		}

		result.append("\n" + this.indent + "/* update of monitored functions and print */\n");
		for (Enumeration<Function> e = sp.allFunction(); e.hasMoreElements();) {
			Function f = e.nextElement();
			if (!(f.isControlled())) {
				result.append(f.accept(new monVarSimulationVisitor(sp.getAxiom(), this.indent + "\t")));
			}
		}

		appendIndeted(result, "/* update of controlled variables for state change */\n");

		for (Enumeration<Variable> e = sp.allVariables(); e.hasMoreElements();) {
			Variable v = e.nextElement();
			appendIndeted(result, v.accept(new VarUpdaterVisitor(this.indent + "\t")) + "\n");
		}
		appendIndeted(result, "/* update of controlled functions for state change */\n");
		for (Enumeration<Function> e = sp.allFunction(); e.hasMoreElements();) {
			Function f = e.nextElement();
			result.append(f.accept(new VarUpdaterVisitor(this.indent)) + "\n");
		}
		// NOW THE NEW STATE IS COMPLETED
		// AND PRINTED
		// CHECK COVERAGE
		if (this.searchCommonCoverage && (this.coverages != null)) {
			result.append("\n" + this.indent + "/* check coverage */\n");
			result.append(this.coverages.accept(new CoverageToSPINVisitor(this.indent, this.tc)));
		}
		// the assert is move to apply one time again the rules

		appendIndeted(result, "printf(\"_New_State\\n\");\n");
		this.indent = this.indent.substring(1);
		if (use_atomic)
			appendIndeted(result, "} /* End of atomic statement */\n");
		this.indent = this.indent.substring(1);
		appendIndeted(result, "od  /* End of main loop */\n");
		result.append("} /* End of init function */\n");
		return result;
	}

	/**
	 * append string to result + indentation
	 * 
	 * @param result
	 * @param string
	 */
	private void appendIndeted(StringBuffer result, String string) {
		result.append(this.indent + string);
	}
}

// to implement bidirectional maps
class BiMap<S, T> {
	private HashMap<S, T> sToT = new HashMap<>();
	private HashMap<T, S> tToS = new HashMap<>();

	public T get1(S s) {
		return sToT.get(s);
	}

	public Map<S, T> getMap1() {
		return java.util.Collections.unmodifiableMap(sToT);
	}

	public S get2(T t) {
		return tToS.get(t);
	}

	public void put(S s, T t) {
		assert sToT.get(s) == null;
		sToT.put(s, t);
		assert tToS.get(t) == null;
		tToS.put(t, s);
	}
}