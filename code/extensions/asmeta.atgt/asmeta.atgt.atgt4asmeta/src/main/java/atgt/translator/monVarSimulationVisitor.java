/*
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import atgt.specification.constraints.NextStateConstraint;
import atgt.specification.constraints.OneInputAssumption;
import atgt.specification.location.Constant;
import atgt.specification.location.Function;
import atgt.specification.location.LocationVisitorI;
import atgt.specification.location.LogicalVariable;
import atgt.specification.location.Variable;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.NumericLiteral;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.Type;
import tgtlib.specification.Axiom;

/**
 * Fornisce la parte del modello SPIN per la simulazione delle variabili
 * monitorate. E' un visitor per le variabili, ma poi effettivamente visita i loro
 * tipi
 * 
 * @author Sax Rinzivillo, AG
 */

public class monVarSimulationVisitor implements LocationVisitorI<StringBuffer> {

	/** The indent. */
	private String indent;

	/** The axioms. */
	private Collection<Axiom> axioms;

	/** The Constant logger. */
	private static final Logger logger = Logger
			.getLogger(monVarSimulationVisitor.class);

	public static boolean SHUFFLE_MON_VALUES = true;

	/**
	 * Instantiates a new mon var simulation visitor.
	 */
	public monVarSimulationVisitor() {
		this(new ArrayList<Axiom>(), "\t");
	}

	/**
	 * Instantiates a new mon var simulation visitor.
	 * 
	 * @param collection
	 *            the collection
	 * @param _indent
	 *            the _indent
	 */
	public monVarSimulationVisitor(Collection<Axiom> collection, String _indent) {
		this.indent = _indent;
		axioms = collection;
	}

	// **************************************

	/**
	 * For enum type.
	 * 
	 * @param var
	 *            the var name
	 * @param et
	 *            the et
	 * 
	 * @return the string buffer
	 */
	private StringBuffer forElementsType(Variable var, ElementsType et) {
		String varname = var.getName();
		StringBuffer result = new StringBuffer(this.indent
				+ "/* Simulation of variable " + varname + " */\n");
		result.append(this.indent).append("if\n");
		List<EnumConst> constrained = new Vector<EnumConst>(et.allElements());
		boolean someConstrained = false;
		for (Iterator<EnumConst> i = constrained.iterator(); i.hasNext();) {
			EnumConst e = i.next();
			// c'e' un assioma che prevede questa varName?
			for (Axiom a : axioms) {
				if (a instanceof NextStateConstraint) {
					NextStateConstraint nsc = (NextStateConstraint) a;
					if (nsc.getVar().getName().equals(varname)
							&& nsc.getCurrentVal().equals(e)) {
						logger.debug("found constraint " + nsc.getName()
								+ "for  var " + nsc.getVar());
						// c'e' un constraint sul valore attuale e la variabile
						result.append(this.indent);
						result.append(":: ").append(varname).append(" == ")
								.append(e.getIdString());
						result.append("  -> \n");
						String oldin = indent;
						indent += "\t";
						result.append(this.indent).append("if\n");
						// one of the following
						addAllVals(var, result, nsc.getNextVals());
						result.append(this.indent).append("fi;\n");
						indent = oldin;
						someConstrained = true;
						i.remove();
						break;
					}
				} else if (a instanceof OneInputAssumption) {
					logger.error("ONE INPUT ASSUMPTION NOT CONSIDERED YET");
				}
			}
		}
		// se alcuni valori sono non vincolati, aggiungi un else
		if (someConstrained && !constrained.isEmpty()) {
			// else puo' prendere una valore qualsiasi
			result.append(indent).append(":: else ->\n");
			indent += "\t";
		}
		// stampa tutti i valori
		if (!constrained.isEmpty()) {
			addAllVals(var, result,
					et.allElements().toArray(new IdExpression[0]));
		}
		// end of fi
		result.append(this.indent).append("fi;\n");
		return result;
	}

	/**
	 * Adds the all vals.
	 * 
	 * @param var
	 *            the var name
	 * @param result
	 *            the result
	 * @param ids
	 *            the ids
	 */
	private void addAllVals(Variable var, StringBuffer result,
			IdExpression[] ids) {
		if (SHUFFLE_MON_VALUES) {
			List<IdExpression> list = Arrays.asList(ids);
			Collections.shuffle(list);
			ids = list.toArray(new IdExpression[ids.length]);
		}
		for (IdExpression nv : ids) {
			addMonitoredValue(var, result, nv);
		}
	}
	/**
	 * 
	 * @param var
	 * @param result
	 * @param value
	 */
	private void addMonitoredValue(Variable var, StringBuffer result, Expression value) {
		result.append("\t"+ indent + ":: ").append(var.getName())
				.append(" = ").append(value).append(";\n");
		InitFunctionToSPINVisitor.addPrintF(result,var,value);
	}

	/**
	 * prints the entire statement of for a bound variable with delta.
	 * 
	 * @param varName
	 *            the var name
	 * @param s
	 *            the s
	 * 
	 * @return the string buffer
	 */
	private StringBuffer forBoundType(Variable var, BoundType s) {
		String varName = var.getName();
		StringBuffer result = new StringBuffer();
		result.append(this.indent).append("if\n");
		// the variables change (i>=1)
		if (s.getDelta() == null) {
			// if shuffle
			if (SHUFFLE_MON_VALUES) {
				// collect all the values
				List<Integer> values = new ArrayList<>(s.getUp() - s.getLow() + 1);
				for (int value = s.getLow(); value <= s.getUp(); value++) {
					values.add(value);
				}
				Collections.shuffle(values);
				for (int value : values) {
					addMonitoredValue(var, result,new NumericLiteral(value));
				}

			} else {
				// no shuffle, print as they are
				for (int value = s.getLow(); value <= s.getUp(); value++) {
					addMonitoredValue(var, result, new NumericLiteral(value));
				}
			}
		} else {
			// only delta changes are permitted
			int delta = s.getDelta();
			for (int i = 1; i <= delta; i++) {
				// the var does not change
				result.append(this.indent).append(":: skip;\n");
				result.append(this.indent).append(":: ((").append(varName)
						.append(" + ").append(i).append(") <= ")
						.append(s.getUp()).append(") -> ");
				result.append(varName).append(" = ").append(varName)
						.append(" + ").append(i).append(";\n");
				result.append(this.indent).append("\t")
						.append("printf(\"_Monitored_value_update: ")
						.append(varName).append(" %d.\\n\", ").append(varName)
						.append(");\n");
				result.append(this.indent).append(":: ((").append(varName)
						.append(" - ").append(i).append(") >= ")
						.append(s.getLow()).append(") -> ");
				result.append(varName).append(" = ").append(varName)
						.append(" - ").append(i).append(";\n");
				result.append(this.indent).append("\t")
						.append("printf(\"_Monitored_value_update: ")
						.append(varName).append(" %d.\\n\", ").append(varName)
						.append(");\n");
			}
		}
		// end of fi
		result.append(this.indent).append("fi;\n");
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.location.LocationVisitorI#forConstant(atgt.specification
	 * .location.Constant)
	 */
	@Override
	public StringBuffer forConstant(Constant constant) {
		throw new RuntimeException(" constant not supported ");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.location.LocationVisitorI#forFunction(atgt.specification
	 * .location.Function)
	 */
	@Override
	public StringBuffer forFunction(Function f) {
		StringBuffer result = new StringBuffer();
		// if domain is EnumType
		if (f.getDomain() instanceof EnumType) {
			ElementsType domain = (ElementsType) f.getDomain();
			for (EnumConst et : domain.allElements()) {
				// build for f.nae[]
				result.append(this.indent).append("if\n");
				// TODO distiguisch location from funtion and funtionterm
				throw new RuntimeException("not implemented yet. Function " + f);
				// TODO correct the following lines
				//result.append(forType(f.getName() + "[" + et + "]",
				//		f.getCodomain()));
				//result.append(this.indent).append("fi;\n");
			}
			// if domain id bound Type
		} else if (f.getType() instanceof BoundType) {
			// TO DO
			BoundType bt = (BoundType) f.getDomain();
			int low = bt.getLow();
			int up = bt.getUp();
			assert bt.getDelta() == null;
			// int dt = bt.getDelta();
			// int dim = (up - low) / dt;
			int dim = up - low;
			for (int i = 0; i <= dim; i++) {
				result.append(this.indent).append("if\n");
				throw new RuntimeException("not implemented yet");
				// TODO correct the following lines
				//result.append(forType(f.getName() + "[" + i + "]",
				//		f.getCodomain()));
				//result.append(this.indent).append("fi;\n");

			}
		}
		return new StringBuffer(result);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.location.LocationVisitorI#forVariable(atgt.specification
	 * .location.Variable)
	 */
	@Override
	public StringBuffer forVariable(Variable var) {
		Type t = var.getType();
		return forType(var, t);
	}

	/**
	 * For type.
	 * 
	 * @param var
	 *            the var
	 * @param t
	 *            the t
	 * 
	 * @return the string buffer
	 */
	private StringBuffer forType(Variable var, Type t) {
		if (t instanceof EnumType || t instanceof BoolType) {
			return forElementsType(var, (ElementsType) t);
		} else if (t instanceof BoundType) {
			return forBoundType(var, (BoundType) t);
		} else {
			throw new RuntimeException(" Type not supported ");
		}
	}

	@Override
	public StringBuffer forLogicalVariable(LogicalVariable logicalVariable) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}
}
