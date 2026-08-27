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

import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import atgt.specification.location.Constant;
import atgt.specification.location.Function;
import atgt.specification.location.LocationVisitorI;
import atgt.specification.location.LogicalVariable;
import atgt.specification.location.Variable;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.Undef;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.Type;

/**
 * Fornisce i metodi per la dichiarazione delle variabili e delle costanti.
 * 
 * @author Sergio Galati
 */

public class InitFunctionToSPINVisitor implements LocationVisitorI<String> {

	final static Logger log = Logger.getLogger(InitFunctionToSPINVisitor.class);

	final static InitFunctionToSPINVisitor init = new InitFunctionToSPINVisitor();

	private InitFunctionToSPINVisitor() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.location.LocationVisitorI#forConstant(atgt.specification
	 * .location.Constant)
	 */
	@Override
	public String forConstant(Constant c) {
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.location.LocationVisitorI#forVariable(atgt.specification
	 * .location.Variable)
	 */
	@Override
	public String forVariable(Variable v) {
		log.debug("adding initilizazion for " + v.getName() + " "
				+ v.getValue());
		StringBuffer result = new StringBuffer();
		// get the initial value
		Expression val = v.getValue();
		if (val == Undef.UNDEF) {
			// not initialized
			if (!(v.isControlled())) {
				// TODO consider also the axioms
				monVarSimulationVisitor ask = new monVarSimulationVisitor(
						Collections.EMPTY_LIST, "\t");
				return v.accept(ask).toString();
			} else {
				// controlled and not initialized: just skip
				return "/* " + v.getName() + " not inizialized */";
			}
		}
		// it is initialized
		String valS = val.accept(ExpressionToSPINVisitor.SINGLETON).toString();
		// add assignment
		result.append("\t" + v.getName() + " = " + valS + ";\n");
		addPrintF(result, v, val);
		return result.toString();
	}

	/**
	 * ad dthe print for variable var assignet to the value val
	 * 
	 * @param result
	 * @param var
	 * @param val
	 */
	static void addPrintF(StringBuffer result, Variable var, Expression val) {
		// PRINT
		if (var.isControlled())
			result.append("\tprintf(\"_Update_value ");
		else
			result.append("\tprintf(\"_Monitored_value_update: ");
		result.append(var.getName());
		// if val is a constant, print as it is
		// eg x = 1, x = ENUM1, x = false
		Type t = var.getType();
		if (t instanceof EnumType) {
			// only initialization to enum const admissible for variables of
			// enums
			assert (val instanceof EnumConst);
			String value = val.toString();
			// instead of : result.append("  " + val + ".\\n\");\n");
			// something like:
			// to avoid problems with DEFINE (AG NOV 13)
			// printf("_Monitored_value_update: lever %cEACTIVATE.\n",'D');
			result.append("  " + "%c" + value.substring(1) + ".\\n\",'" + value.charAt(0) + "');\n");
		} else {
			if (t instanceof BoundType) {
				try {
					// it can be an expression or an integer
					// eg x = a + b
					Integer.parseInt(val.toString());
					result.append("  " + val + ".\\n\");\n");
				} catch (NumberFormatException e) {
					result.append("  %d.\\n\"," + var.getName() + ");\n");
				}
			} else {
				// print the boolean as integers (even if it is a constant)
				assert (t instanceof BoolType);
				result.append("  %d.\\n\"," + var.getName() + ");\n");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.location.LocationVisitorI#forFunction(atgt.specification
	 * .location.Function)
	 */
	@Override
	public String forFunction(Function f) {
		String result;
		Hashtable<String, Expression> ht = f.getInitialValues();
		result = "";

		// result = f.getCodomain().accept(new TypeSPINTranslatorVisitor()) + "
		// ";
		// result += f.getName() + "[" ;
		if (f.getDomain() instanceof EnumType) {
			for (Enumeration<String> et = ht.keys(); et.hasMoreElements();) {
				String ke = et.nextElement();
				result += f.getName() + "[" + ke + "]=" + ht.get(ke).toString()
						+ ";\n";

				// Nel caso in cui la variabile e' controllata, c'??? bisogno di
				// una variabile per
				// conservare un eventuale nuovo valore da assegnare alla
				// varibile stessa.
				// Ricordiamo che alla fine del loop principale viene eseguito
				// un update di
				// tutte le variabili controllate.
				if (f.isControlled()) {
					// result += f.getCodomain().accept(new
					// TypeSPINTranslatorVisitor()) + " ";
					result += f.getPrimedName() + "[" + ke + "]=" + ht.get(ke)
							+ ";\n";
					result += "printf(\"_Update_value " + f.getName() + "["
							+ ke + "]" + "  %d.\\n\"," + f.getName() + "[" + ke
							+ "]" + ");\n";
				} else {
					result += "printf(\"_Monitored_value_update: "
							+ f.getName() + "[" + ke + "]" + " %d.\\n\", "
							+ f.getName() + "[" + ke + "]" + ");\n";
				}
			}
		} else if (f.getDomain() instanceof BoundType) {
			BoundType bt = (BoundType) f.getDomain();
			int low = bt.getLow();
			int up = bt.getUp();
			assert bt.getDelta() == null;
			int dt = 1;
			// int dim = (up - low) / dt;
			int dim = up - low + 1;
			int currindex = low;
			for (int i = 0; i <= dim; i++) {
				Object value = ht.get((new Integer(currindex)).toString());
				if (value != null) {
					result += f.getName() + "[" + i + "]=" + value + ";\n";
					if (f.isControlled()) {
						// result += f.getCodomain().accept(new
						// TypeSPINTranslatorVisitor()) + " ";
						result += f.getPrimedName() + "[" + i + "]=" + value
								+ ";\n";
						result += "printf(\"_Update_value " + f.getName() + "["
								+ i + "]" + "  %d.\\n\"," + f.getName() + "["
								+ i + "]" + ");\n";
					} else {
						result += "printf(\"_Monitored_value_update: "
								+ f.getName() + "[" + i + "]" + " %d.\\n\", "
								+ f.getName() + "[" + i + "]" + ");\n";
					}
				}
				currindex += dt;
			}
		} else
			System.out.println("ATTENZIONE: Dominio non gestito.");

		return result;
	}

	@Override
	public String forLogicalVariable(LogicalVariable logicalVariable) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}
}
