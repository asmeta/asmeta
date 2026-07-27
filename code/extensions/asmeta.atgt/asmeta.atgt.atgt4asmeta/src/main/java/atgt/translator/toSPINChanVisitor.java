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

import atgt.specification.ASMSpecification;
import atgt.specification.location.Constant;
import atgt.specification.location.Function;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;
import atgt.specification.statement.RuleDeclaration;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.type.Type;

// TODO: Auto-generated Javadoc
/**
 * The Class toSPINChanVisitor.
 */
public class toSPINChanVisitor extends ToSpinTranslatorVisitor {

	/**
	 * Instantiates a new to spin chan visitor.
	 */
	public toSPINChanVisitor() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.translator.TranslatorVisitor#analyze(atgt.specification.ASMSpecification)
	 */
	@Override
	public StringBuffer analyze(ASMSpecification sp) {
		String result;

		result = "";

		// Translate the constant definition
		result += "/* numeric constants */ \n";
		result += "bool TRUE = 1;\n";
		result += "bool FALSE = 0;\n";
		result += "mtype = {start}\n\n";

		for (Type t : sp.allTypes()) {
			result += t.accept(new TypeToSPINVisitor());
			result += "\n";
		}
		result += "\n\n";
		for (Constant c : sp.allConstants()) {
			result += c.accept(new LocationToSPINDeclarationVisitor());
		}

		result += "\n\n\n";

		// Now it's variables turn
		result += "/* variables definitions */ \n";
		for (Enumeration<Variable> e = sp.allVariables(); e.hasMoreElements();) {
			result += (e.nextElement())
					.accept(new LocationToSPINDeclarationVisitor());
		}

		// Translate the Functions
		result += "\n\n";
		result += "/* functions definitions */ \n";
		for (Enumeration<Function> e = sp.allFunction(); e.hasMoreElements();) {
			result += (e.nextElement())
					.accept(new LocationToSPINDeclarationVisitor());
		}

		result += "\n\n\n";
		result += this.indent + "/* Rules transaltion goes here */\n\n\n";
		for (RuleDeclaration r : sp.allRules()) {
			result += r.accept(new StatementToSPINVisitor(this.indent));
		}

		// Begin the init function
		result += "/* init function */\n";
		result += "init{\n";
		// the functions initialization
		for (Enumeration<Variable> e = sp.allVariables(); e.hasMoreElements();) {
			result += (e.nextElement())
					.accept(InitFunctionToSPINVisitor.init);
		}

		for (Enumeration<Function> e = sp.allFunction(); e.hasMoreElements();) {
			result += (e.nextElement())
					.accept(InitFunctionToSPINVisitor.init);
		}
		result += "printf(\"_New_State\\n\");\n";

		result += "\n/* main processing loop */\n";

		// Main loop for ambient simulation and rules firing

		this.indent += "\t";
		for (RuleDeclaration r: sp.allRules()) {
			// result += r.accept(new StatementToSPINVisitor(indent));
			result += this.indent + "run " + r.getName() + "();\n";
		}
		result += this.indent + "do \n";

		// Start modeling monitored variable simulation
		this.indent += "\t";
		result += this.indent + "::\n";
		result += this.indent + "/* Simulation of monitored variables */\n";
		for (Enumeration<Variable> e = sp.allVariables(); e.hasMoreElements();) {
			Location v = e.nextElement();
			if (!(v.isControlled())) {
				result += this.indent + "if\n";
				result += v.accept(new monVarSimulationVisitor(sp.getAxiom(),
						this.indent + "\t"));
				result += this.indent + "fi;\n";
			}
		}

		result += "\n" + this.indent
				+ "/* Simulation of monitored functions */\n";
		for (Enumeration<Function> e = sp.allFunction(); e.hasMoreElements();) {
			Function f = e.nextElement();
			if (!(f.isControlled())) {
				result += f.accept(new monVarSimulationVisitor(sp.getAxiom(),
						this.indent + "\t"));
			}
		}

		result += "\n\n\n";

		result += this.indent + "atomic{\n";
		// RuleDeclaration translation
		this.indent += "\t";
		result += this.indent + "/* Rules firing */\n\n\n";
		for (RuleDeclaration r: sp.allRules()) {
			// result += r.accept(new StatementToSPINVisitor(indent));
			result += this.indent + r.getName() + "chan!start;\n";
		}

		this.indent = this.indent.substring(1);
		result += this.indent + "} /* End of d_step statement */\n";

		// if(isToSearchCommonCoverage)
		// result += searchCommonCoverage
		result += this.indent + "/* searching for other coverages */\n";
		if (this.searchCommonCoverage && (this.coverages != null))
			result += this.coverages.accept(new CoverageToSPINVisitor(
					this.indent, this.tc));
		// assert part
		result += this.indent + " /* specification assert */ \n";
		if (this.tc != null){
			NotExpression assertExpression = NotExpression.createNotExpression(this.tc.getCondition());
			result += this.indent + "assert( "
					+ assertExpression.toString() + " );\n";
		}
		result += this.indent + " /* Update variables for state change */\n";
		for (Enumeration<Variable> e = sp.allVariables(); e.hasMoreElements();) {
			Variable v = e.nextElement();
			result += this.indent
					+ v.accept(new VarUpdaterVisitor(this.indent + "\t"))
					+ "\n";
		}

		result += this.indent + " /* Update functions for state change */\n";
		for (Enumeration<Function> e = sp.allFunction(); e.hasMoreElements();) {
			Function f = e.nextElement();
			result += f.accept(new VarUpdaterVisitor(this.indent + "\t"))
					+ "\n";
		}

		result += this.indent + "printf(\"_New_State\\n\");\n";
		this.indent = this.indent.substring(1);
		result += this.indent + "od  /* End of main loop */\n";
		result += "} /* End of init function */\n";
		return new StringBuffer(result);
	}
}
