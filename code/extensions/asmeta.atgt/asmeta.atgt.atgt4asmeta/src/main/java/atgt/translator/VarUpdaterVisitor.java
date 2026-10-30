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

import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.specification.location.Constant;
import tgtlib.specification.location.Function;
import tgtlib.specification.location.LocationVisitorI;
import tgtlib.specification.location.LogicalVariable;
import tgtlib.specification.location.Variable;

// TODO: Auto-generated Javadoc
/**
 * Implementa l'interfaccia LocationisitorI per fare l'update delle variabili
 * monitorate alla fine del ciclo principale.
 * 
 * @author Sax Rinzivillo, AG
 */

public class VarUpdaterVisitor implements LocationVisitorI<StringBuffer> {

	/** The indent. */
	private String indent;

	/**
	 * Instantiates a new var updater visitor.
	 * 
	 * @param _indent
	 *            the _indent
	 */
	public VarUpdaterVisitor(String _indent) {
		this.indent = _indent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see tgtlib.specification.location.LocationVisitorI#forConstant(tgtlib.specification.location.Constant)
	 */
	@Override
	public StringBuffer forConstant(Constant c) {
		// Do nothing
		return new StringBuffer("");
	}

	// Viene richiamata per fare gli aggiornamenti sullo stato
	// prima di rifare un nuovo ciclo.
	/*
	 * (non-Javadoc)
	 * 
	 * @see tgtlib.specification.location.LocationVisitorI#forVariable(tgtlib.specification.location.Variable)
	 */
	@Override
	public StringBuffer forVariable(Variable v) {
		StringBuffer result = new StringBuffer("");

		if (v.isControlled())
			result.append(v.getName() + " = " + v.getName()
					+ Variable.primeSuffix + ";");
		return result;
	}

	// Viene richiamata pre fare gli aggiornamenti sullo stato
	// prima di rifare un nuovo ciclo.
	/*
	 * (non-Javadoc)
	 * 
	 * @see tgtlib.specification.location.LocationVisitorI#forFunction(tgtlib.specification.location.Function)
	 */
	@Override
	public StringBuffer forFunction(Function f) {
		StringBuffer result = new StringBuffer();
		if (f.isControlled()) {
			if (f.getDomain() instanceof EnumType) {
				ElementsType dom = (ElementsType) f.getType();
				for (EnumConst et : dom.allElements()) {
					result.append(this.indent + f.getName() + "["
							+ et.toString() + "]=" + f.getPrimedName() + "["
							+ et.toString() + "];\n");
				}
			} else if (f.getDomain() instanceof BoundType) {
				BoundType bt = (BoundType) f.getDomain();
				int low = bt.getLow();
				int up = bt.getUp();
				assert bt.getDelta()== null;
				//int dt = bt.getDelta();
				//int dim = (up - low) / dt;
				int dim = up - low;
				int dt = 1;
				int currindex = low;
				for (int i = 0; i <= dim; i++) {
					// update this function
					result.append(this.indent + f.getName() + "[" + i
								+ "]=" + f.getPrimedName() + "[" + i + "];\n");
				}
			} else
				System.out.println("ATTENZIONE: Dominio non gestito.");
		}
		return result;
	}

	@Override
	public StringBuffer forLogicalVariable(LogicalVariable logicalVariable) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

}
