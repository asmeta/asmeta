/*******************************************************************************
 * Copyright (c) 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Angelo Gargantini - initial API and implementation
 *******************************************************************************/
package tgtlib.definitions.expression;

/** all the operators
 * 
 * @author garganti
 *
 * @version $Revision: 1.0 $
 */
public class Operator {

	private final String op;// a string representing

	/**
	 * Constructor for Operator.
	 * @param string String
	 */
	protected Operator(final String string) {
		op = string;
	}

	/** binary operators * @author garganti
	 * @version $Revision: 1.0 $
	 */
	public static abstract class BinaryOperator extends Operator{
		
		/**
		 * Constructor for BinaryOperator.
		 * @param string String
		 */
		protected BinaryOperator(String string) {
			super(string);
		}
	
		/**
		 * Method mkBinExpr.
		 * @param e1 Expression
		 * @param e2 Expression
		 * @return BinaryExpression
		 */
		abstract BinaryExpression mkBinExpr(Expression e1, Expression e2);
	}
	
	
	static final public Operator AND = new Operator.BinaryOperator("and"){

		@Override
		BinaryExpression mkBinExpr(Expression e1, Expression e2) {
			return new AndExpression(e1, e2);
		}
		
	};

	static  final public Operator OR = new Operator.BinaryOperator("or"){

		@Override
		BinaryExpression mkBinExpr(Expression e1, Expression e2) {
			return new OrExpression(e1, e2);
		}
		
	};
	/** The XOR. */
	public final static Operator XOR = new Operator.BinaryOperator("xor"){

		@Override
		BinaryExpression mkBinExpr(Expression e1, Expression e2) {
			return new XOrExpression(e1, e2);
		}
		
	};
	/** The IMPLIES. */
	public final static Operator IMPLIES = new Operator.BinaryOperator("implies"){

		@Override
		BinaryExpression mkBinExpr(Expression e1, Expression e2) {
			return new ImpliesExpression(e1, e2);
		}
		
	};
	/** not equal != */
	static final public Operator NEQ = new Operator.BinaryOperator("!="){

		@Override
		BinaryExpression mkBinExpr(Expression e1, Expression e2) {
			return new NotEqualsExpression(e1, e2);
		}
		
	};
	static final public Operator EQ = new Operator.BinaryOperator("="){

		@Override
		BinaryExpression mkBinExpr(Expression e1, Expression e2) {
			return new EqualsExpression(e1, e2);
		}
		
	};
	static final public Operator LT = new Operator.BinaryOperator("<"){

		@Override
		BinaryExpression mkBinExpr(Expression e1, Expression e2) {
			return new LessThanExpression(e1, e2);
		}
		
	};
	static final public Operator LE = new Operator.BinaryOperator("<="){

		@Override
		BinaryExpression mkBinExpr(Expression e1, Expression e2) {
			return new LessEqualExpression(e1, e2);
		}
		
	};
	static final public Operator GT = new Operator.BinaryOperator(">"){

		@Override
		BinaryExpression mkBinExpr(Expression e1, Expression e2) {
			return new GreaterThanExpression(e1, e2);
		}
		
	};
	static final public Operator GE = new Operator.BinaryOperator(">="){

		@Override
		BinaryExpression mkBinExpr(Expression e1, Expression e2) {
			return new GreaterEqualExpression(e1, e2);
		}
		
	};
	static final public Operator PLUS = new Operator.BinaryOperator("+"){

		@Override
		BinaryExpression mkBinExpr(Expression e1, Expression e2) {
			return new PlusExpression(e1, e2);
		}
		
	};
	static final public Operator MINUS = new Operator.BinaryOperator("-"){

		@Override
		BinaryExpression mkBinExpr(Expression e1, Expression e2) {
			return new MinusExpression(e1, e2);
		}
		
	};
	static final public Operator MULT = new Operator.BinaryOperator("*"){

		@Override
		BinaryExpression mkBinExpr(Expression e1, Expression e2) {
			return new MultExpression(e1, e2);
		}
		
	};
	// this is the integer division: 
	// the other division / may give some problems
	static final public Operator DIV = new Operator.BinaryOperator("idiv"){

		@Override
		BinaryExpression mkBinExpr(Expression e1, Expression e2) {
			return new DivExpression(e1, e2);
		}
		
	};

	/** modulo operator
	 * 
	 */
	static final public Operator MOD = new Operator.BinaryOperator("%"){

		@Override
		BinaryExpression mkBinExpr(Expression e1, Expression e2) {
			return new ModuloExpression(e1, e2);
		}
		
	};
	/** 
	 * unary opposite
	 * 
	 */
	static final public Operator NOT = new Operator("not"); 
	static final public Operator OPPOSITE = new Operator("-");
	static final public Operator prime = new Operator("'"); 

	/**
	 * Method hashCode.
	 * @return int
	 */
	@Override
	public int hashCode() {
		return op.hashCode();
	}	
		
	/**
	 * Method toString.
	 * @return String
	 */
	@Override
	public String toString() {
		return op;
	}

}
