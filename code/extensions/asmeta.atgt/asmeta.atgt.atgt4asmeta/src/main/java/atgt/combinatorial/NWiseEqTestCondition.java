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

import java.util.List;

import extgt.coverage.combinatorial.CombinatorialCovBuilder;
import extgt.coverage.combinatorial.EqTestCondition;
import extgt.coverage.combinatorial.NwiseEqTestCondFactory;
import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.type.EnumConst;


/** to represent nwise coverage of type var_i = val_i
 * where val_i is an EnumConst (not an integer)
 * TODO: extend with IDeXpression to allow integers???
 * 
 * */
public class NWiseEqTestCondition extends NwiseTestCondition implements EqTestCondition{

	public static NwiseEqTestCondFactory<NWiseEqTestCondition> factory = new NwiseEqTestCondFactory<NWiseEqTestCondition>() {
		
		@Override
		public NWiseEqTestCondition buildTestPredicate(String _name,
				List<TypedInitExpression> vs, List<EnumConst> _vals) {
			return new NWiseEqTestCondition(_name, vs, _vals);
		}
	};

	/** the enums * */
	protected List<EnumConst> vals;

	/**
	 * Instantiates a new n - wise test condition. assumi che vars e vals hanno
	 * la stessa lunchezza
	 * 
	 * @param _name
	 *            the _name
	 * @param vs
	 *            the _vars the variables
	 * @param _vals
	 *            the _vals their values
	 */
	public NWiseEqTestCondition(String _name, List<? extends TypedInitExpression> vs,
			List<EnumConst> _vals) {
		super(_name, vs, CombinatorialCovBuilder.makeAndExpression(vs, _vals));
		assert vs.size() == _vals.size();
		vals = _vals;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.NamedTerm#toString()
	 */
	@Override
	public String toString() {
		return vars.toString() + "-->" + vals.toString();
	}

	/**return the i-the variable
	 * 
	 * @param i
	 * @return
	 */
	@Override
	public TypedInitExpression getVar(int i) {
		return vars.get(i);
	}

	/**return the i-the value
	 * 
	 * @param i
	 * @return
	 */
	@Override
	public EnumConst getVal(int i) {
		return vals.get(i);
	}

}
