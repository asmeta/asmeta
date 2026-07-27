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
package extgt.coverage.fault.mutators.foms;

import java.util.ArrayList;
import java.util.List;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdUNotIdExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.NotIDExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.XOrExpression;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.ExpressionSettableVisitMutator;
import extgt.coverage.fault.mutators.ExtraVariableFault;

/**
 * chen: Variable Reference Fault (VRF). An occurrence of a condition is
 * replaced by another possible condition. A condition is said to be possible if
 * its variable has already appeared in the expression. For example, (x1 \/ not x2)
 * /\ (not x1 /\ x4) is a VRF of (x1 \/ not x2) /\ (x3 /\ x4).
 */
public class VariableReferenceFault extends ExpressionSettableVisitMutator<VariableReferenceFault.VRFVisitor> {

	public static VariableReferenceFault VRF = new VariableReferenceFault();

	/**
	 * 
	 * @param vars
	 *            to be considered for insertion
	 * @param termtype
	 *            term type to be inserted
	 */
	private VariableReferenceFault() {
	}

	@Override
	public String getName() {
		return "Variable Reference Fault";
	}

	@Override
	public String getAbbrvName() {
		return "VRF";
	}

	@Override
	protected VRFVisitor getNewVisitorForIds(List<IdUNotIdExpression> ids) {
		return new VRFVisitor(ids);
	}

	
	static class VRFVisitor extends ExtraVariableFault {

		private VRFVisitor(List<IdUNotIdExpression> ids) {
			super(ids);
		}

		@Override
		public List<Pair<Integer, Expression>> forAndExpression(AndExpression e) {
			return distribute(e, Operator.AND);
		}

		@Override
		public List<Pair<Integer, Expression>> forOrExpression(OrExpression e) {
			return distribute(e, Operator.OR);
		}

		@Override
		public List<Pair<Integer, Expression>> forXOrExpression(XOrExpression e) {
			return distribute(e, Operator.XOR);
		}

		
		@Override
		public List<Pair<Integer, Expression>> forIdExpression(IdExpression e) {
			List<Pair<Integer, Expression>> result = new ArrayList<Pair<Integer, Expression>>();
			for (IdUNotIdExpression ide : getConditions()) {
				if (ide.equals(e))
					continue;
				result.add(new Pair<Integer, Expression>(1, ide));
			}
			return result;
		}

		@Override
		public List<Pair<Integer, Expression>> forNotExpression(NotExpression e) {
			//if not ID, replace it with other conditions
			if (e instanceof NotIDExpression){
				List<Pair<Integer, Expression>> result = new ArrayList<Pair<Integer, Expression>>();
				for (IdUNotIdExpression ide : getConditions()) {
					if (ide.equals(e))
						continue;
					result.add(new Pair<Integer, Expression>(1, ide));
				}
				return result;				
			}else
			 return distribute(e, Operator.NOT);
		}

		@Override
		public List<Pair<Integer, Expression>> forConditionalExpression(CondExpression cond) {
			// TODO Auto-generated method stub
			throw new RuntimeException("not implemented yet");
		}
	}

}
