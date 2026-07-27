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
//import atgt.spin.translator.TypeSPINTranslatorVisitor;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.Undef;
import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.Type;

/**
 * Formisce i metodi per tradurre in stringe le location
 */
public class LocationLeftUseToSPINVisitor implements LocationVisitorI<StringBuffer> {

	
	static LocationLeftUseToSPINVisitor instance = new  LocationLeftUseToSPINVisitor();
	
	private LocationLeftUseToSPINVisitor(){}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * atgt.specification.location.LocationVisitorI#forConstant(atgt.specification
	 * .location.Constant)
	 */
	@Override
	public StringBuffer forConstant(Constant c) {
		return new StringBuffer(c.getName());
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
		return new StringBuffer(v.getName() + Variable.primeSuffix);
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
		return new StringBuffer(f.getName() + Variable.primeSuffix);
	}

	@Override
	public StringBuffer forLogicalVariable(LogicalVariable lv) {		
		return new StringBuffer(lv.getIdExpression().accept(ExpressionToSPINVisitor.SINGLETON));
	}			
	
}
