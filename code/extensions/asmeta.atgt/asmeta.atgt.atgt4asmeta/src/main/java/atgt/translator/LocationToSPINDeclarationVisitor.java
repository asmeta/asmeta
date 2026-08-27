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

import atgt.specification.location.Constant;
import atgt.specification.location.Function;
import atgt.specification.location.LocationVisitorI;
import atgt.specification.location.LogicalVariable;
import atgt.specification.location.Variable;
import atgt.specification.type.DummyType;
import atgt.spin.translator.TypeSPINTranslatorVisitor;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.Undef;
import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.Type;

/**
 * Formisce i metodi per la dichiarazione delle variabili e delle costanti.
 */
public class LocationToSPINDeclarationVisitor implements LocationVisitorI<StringBuffer> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.location.LocationVisitorI#forConstant(atgt.specification
	 * .location.Constant)
	 */
	@Override
	public StringBuffer forConstant(Constant c) {
		return new StringBuffer("#define " + c.getName() + " " + c.getValue()
				+ "\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.location.LocationVisitorI#forVariable(atgt.specification
	 * .location.Variable)
	 */
	@Override
	public StringBuffer forVariable(Variable v) {
		StringBuffer result = new StringBuffer();

		addVarDeclaration(v, result, false);

		// Nel caso in cui la variabile e' controllata, c'??? bisogno di una
		// variabile per
		// conservare un eventuale nuovo valore da assegnare alla varibile
		// stessa.
		// Ricordiamo che alla fine del loop principale viene eseguito un update
		// di
		// tutte le variabili controllate.
		if (v.isControlled()) {
			addVarDeclaration(v, result, true);
			
		}
		return result;
	}

	private void addVarDeclaration(Variable v, StringBuffer result, boolean primed) {
		String varName = primed? v.getPrimedName() : v.getName();
		Type type = v.getType();
		assert ! (type instanceof DummyType): "variable " + v;
		String typeName = type.accept(TypeSPINTranslatorVisitor.instance);
		result.append(typeName+ " ");
		result.append(varName);
		Expression initValue = v.getValue();
		if (initValue != null && initValue != Undef.UNDEF)
			result.append(" = " + initValue.toString());
		result.append(";\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.location.LocationVisitorI#forFunction(atgt.specification
	 * .location.Function)
	 */
	@Override
	public StringBuffer forFunction(Function f) {
		StringBuffer result = new StringBuffer();
		result.append(f.getCodomain().accept(TypeSPINTranslatorVisitor.instance)+ " ");
		result.append(f.getName());
		addTypeDel(f, result);

		// Nel caso in cui la variabile ??? controllata, c'e' bisogno di una
		// variabile per
		// conservare un eventuale nuovo valore da assegnare alla varibile
		// stessa.
		// Ricordiamo che alla fine del loop principale viene eseguito un update
		// di
		// tutte le variabili controllate.
		if (f.isControlled()) {
			result.append(f.getCodomain().accept(TypeSPINTranslatorVisitor.instance)+ " ");
			result.append(f.getPrimedName());
			addTypeDel(f, result);
		}
		return result;
	}

	/**
	 * @param f
	 * @param result
	 */
	private void addTypeDel(Function f, StringBuffer result) {
		if (f.getDomain() instanceof EnumType) {
			result.append("[" + ((ElementsType) f.getDomain()).range() + "];\n");
		} else if (f.getDomain() instanceof BoundType) {
			// TO DO
			BoundType bt = (BoundType) f.getDomain();
			int low = bt.getLow();
			int up = bt.getUp();
			if (bt.getDelta() != null) throw new RuntimeException("not implemented yet");
			// rescale in case of delta
			//int dim = (up - low) / delta + 1;
			int dim = up - low +1;
			result.append("[" + dim + "];\n");
		} else
			System.out.println("ATTENZIONE: Dominio non gestito");
	}

	@Override
	public StringBuffer forLogicalVariable(LogicalVariable logicalVariable) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}

}
