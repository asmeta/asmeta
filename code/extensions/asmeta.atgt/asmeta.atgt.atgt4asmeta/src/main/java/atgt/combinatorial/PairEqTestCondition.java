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
package atgt.combinatorial;


import atgt.coverage.AsmTestCondition;
import extgt.coverage.combinatorial.CombinatorialCovBuilder;
import extgt.coverage.combinatorial.EqTestCondition;
import extgt.coverage.combinatorial.PairEqTestCondFactory;
import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.type.EnumConst;

/**
 * represent a pair test condition like var1 = val1 and var2 = val2. Particular
 * case of PairTestCondition
 */
public class PairEqTestCondition extends PairTestCondition implements EqTestCondition {


	private EnumConst val1, val2;
	
	/**
	 * Instantiates a new pair test condition.
	 * 
	 * @param _name
	 *            the _name
	 * @param varK
	 *            the var1
	 * @param val1
	 *            the val1
	 * @param varJ
	 *            the var2
	 * @param val2
	 *            the val2
	 */
	private PairEqTestCondition(String _name, TypedInitExpression varK, EnumConst val1,
			TypedInitExpression varJ, EnumConst val2) {
		super(_name, varK, varJ, CombinatorialCovBuilder.makeAndExpression(CombinatorialCovBuilder.makeListFromPair(varK,varJ), CombinatorialCovBuilder.makeListFromPair(val1,val2)));
		this.val1 = val1;
		this.val2 = val2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.NamedTerm#toString()
	 */
	@Override
	public String toString() {
		return var1.toString() + "=" + val1.toString() + " - "
				+ var2.toString() + "=" + val2.toString();
	}
	@Override
	public EnumConst getVal(int i) {
		assert i == 0 || i == 1;
		return i == 0 ? val1 : val2; 
	}
	@Override
	public TypedInitExpression getVar(int i) {
		assert i == 0 || i == 1;
		return i == 0 ? var1 : var2; 
	}
	@Override
	public int size() {
		return 2;
	}

	
	static final public PairEqTestCondFactory<AsmTestCondition> factory = new PairEqTestCondFactory<AsmTestCondition>() {
		
		@Override
		public PairEqTestCondition buildTestPredicate(String n,
				TypedInitExpression varK, EnumConst val1,
				TypedInitExpression varJ, EnumConst val2) {
			return new PairEqTestCondition(n, varK, val1, varJ, val2);
		}
	};

}
