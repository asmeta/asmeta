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
package atgt.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import atgt.specification.ASMSpecification;
import atgt.specification.constraints.OneInputAssumption;
import atgt.translator.ExpressionToSALVisitor;
import atgt.translator.TranslatorVisitor;
import extgt.coverage.combinatorial.MonitoredData;
import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.Undef;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.Type;
import tgtlib.specification.Axiom;
import tgtlib.util.SRISALTransation;

/**
 * traduce a specification to SAL tenendo conto solo i dati monitorati. Note
 * that - shuffle: it can shuffle the domains - � un singleton
 */

public final class MonitoredDataToSAL extends TranslatorVisitor {
	
	
	/** The translator. */
	private ExpressionToSALVisitor translator = new ExpressionToSALVisitor();

	/**
	 * Instantiates a new monitored data to sal. WIth the standard mon. extration utility which ignroe the intergers
	 */
	private MonitoredDataToSAL() {
	}
	

	/** The Constant SINGLETON. */
	public static final MonitoredDataToSAL SINGLETON = new MonitoredDataToSAL();

	/** needed to have the name, which must be equal to the name of the file. */
	private String contextName = null;

	/** the translation consider the int?
	 * it does not read the preferences, it is  fixed
	 */
	private static final boolean CONSIDER_INT = true;
	
	/**
	 * translate to SAL.
	 * 
	 * @param md
	 *            the md
	 * @param specAxioms
	 *            the spec axioms
	 * @return the string buffer
	 */
	public StringBuffer translate(MonitoredData md, java.util.Collection<Axiom> specAxioms) {
		// 
		StringBuffer result = new StringBuffer();

		result.append(contextName + ": CONTEXT = \n\n\tBEGIN\n\n");

		// get the set of types - delete duplicate
		List<EnumType> types = new ArrayList<EnumType>();
		for (TypedInitExpression var : md.getVars()) {
			Type t = var.getType();
			if (t instanceof BoolType){
				continue;
			} else 	if (t instanceof EnumType) {
				// add the enumtype
				EnumType et = (EnumType) t;
				if (!types.contains(et)) {
					types.add(et);
					result.append("\t" + et.getName() + " : TYPE = ");
					addType(result, et);
					result.append("\n");
				}
			} else if (t instanceof BoundType && CONSIDER_INT) {
				// add the integers in case
				BoundType bt = (BoundType) var.getType();
				result.append("\t" + bt.getName()).append(" :TYPE =  [");
				result.append(bt.getLow() + " .. " + bt.getUp() + "];\n\n");
			}
		}
		// create the monitored module
		result.append("\tmonitored : MODULE = BEGIN\n\n");
		// print all the inputs as OUTPUT VARIABLES
		for (TypedInitExpression mv : md.getVars()) {
			result.append("\t\t");
			result.append("OUTPUT " + mv.getName() + ": ");
			result.append(mv.getType().equals(BoolType.BOOLTYPE) ? "BOOLEAN"
					: mv.getType().getName());
			result.append("\n");
		}
		result.append("\n");
		// INTITIALIZATION PART
		if (atgt.preferences.ATGToolPreferences.ConsiderInitNext
				.getValue()) {
			// get the initialization part
			for (TypedInitExpression mv : md.getVars()) {
				Expression value = mv.getValue();
				if (value == null || value == Undef.UNDEF)
					continue;
				// if it is defined as output
				result.append("\t\tINITIALIZATION " + mv.getName() + " = "
						+ value.toString() + ";\n");
			}
			// get if there is the OIA !!!
			if (specAxioms.contains(OneInputAssumption.OIA)) {
				result.append("\t\tTRANSITION\n\t\t[  ");
				boolean first = true;
				for (TypedInitExpression mv : md.getVars()) {
					if (first)
						first = false;
					else
						result.append("\t\t[] ");
					result.append("true --> ");
					addVarInNextState(result, mv);
				}
				result.append("\t\t]\n");
			} else {
				// NO OIA
				for (TypedInitExpression mv : md.getVars()) {
					// if it is defined as output
					result.append("\t\tTRANSITION ");
					addVarInNextState(result, mv);
				}
			}
			// other constraints are translated as axioms
		}
		// end of monitored
		result.append("\tEND;\n\n");

		// IN SCR qui ci va il modulo vero e propro da comporre cone monitored

		// add test predicates
		// add axioms
		StringBuffer axioms = null;
		for (Axiom ax : specAxioms) {
			if (ax.getBody() == null)
				continue;
			StringBuffer axS = ax.getBody().accept(translator);
			assert axS.indexOf("!=") == -1;
			if (axioms == null)
				axioms = new StringBuffer(axS);
			else
				axioms.append(" AND " + axS);
		}

		if (tc != null) {

			result.append("\t% trap property for " + tc.getName() + "\n");
			result.append("\t" + tc.getUniqueID() + " : THEOREM monitored |-");
			// NOTA: => is right associative
			if (axioms != null) {
				// AND ha la massima precedenza
				result.append("G(" + axioms + ")");
				result.append(" => ");
			}
			// important: open parethesis
			result.append("G(NOT(")
					.append(tc.getCondition().accept(translator));
			result.append("));\n\n");
			result.append("END\n\n");
		}

		return result;

	}

	/**
	 * add the transition for the variable mv in the next atsat like Block' IN
	 * {On,Off};.
	 * 
	 * @param result
	 *            the result
	 * @param mv
	 *            the mv
	 */
	private void addVarInNextState(StringBuffer result, TypedInitExpression mv) {
		result.append(mv.getName() + "' IN ");
		Type t = mv.getType();
		if (t instanceof ElementsType) {
			ElementsType et = (ElementsType) t;
			addType(result, et);
		} else if (t instanceof BoundType) {
			// get the transaction for the integer variables with
			// delta
			BoundType bt = (BoundType) t;
			result.append(SRISALTransation.toSriSalTrans(mv.getName(), bt
					.getName(), bt.getLow(), bt.getUp(), bt.getDelta(), false));
		} else {
			throw new RuntimeException("TYPE of " + mv +" is " + t + " not treated!!");
		}
		result.append("\n");
	}

	/**
	 * add the content of the type t between {}.
	 * 
	 * @param result
	 *            the result
	 * @param et
	 *            the et
	 */
	private void addType(StringBuffer result, ElementsType et) {
		result.append("{");
		List<EnumConst> cosntati = new ArrayList<EnumConst>(et.allElements());
		if (atgt.preferences.ATGToolPreferences.ShuffleSAL
				.getValue())
			Collections.shuffle(cosntati);
		for (Enumeration<EnumConst> e = Collections.enumeration(cosntati);;) {
			result.append(e.nextElement().toString());
			if (e.hasMoreElements())
				result.append(", ");
			else
				break;
		}
		result.append("};");
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.translator.TranslatorVisitor#analyze(atgt.specification.ASMSpecification)
	 */
	@Override
	public StringBuffer analyze(ASMSpecification SP) {
		/** the monitor extraction utility
		 * in th future one could change this extraction method (by considering for example the integers*/
		AsmMonitoredDataExtractor mde  = AsmMonitoredDataExtractor.getMonitoredDataExtractor();		
		assert (contextName != null);
		
		MonitoredData md = mde.analyze(SP);

		StringBuffer result = new StringBuffer();

		result.append("% specification for " + SP.name + "\n");

		result.append(translate(md, SP.getAxiom()));
		return result;

	}

	/**
	 * Sets the context name. Note that the context name must be equal to the
	 * file name
	 * 
	 * @param contextName
	 *            the new context name
	 */
	public void setContextName(String contextName) {
		this.contextName = contextName;
	}

}
