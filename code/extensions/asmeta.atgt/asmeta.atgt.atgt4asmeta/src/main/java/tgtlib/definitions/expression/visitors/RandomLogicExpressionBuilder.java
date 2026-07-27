package tgtlib.definitions.expression.visitors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.UnaryExpression;
import tgtlib.definitions.expression.type.BoolType;

/**
 * builds random logic expressions
 * 
 * @author garganti
 * 
 */
public class RandomLogicExpressionBuilder implements Iterator<Expression> {

	private static final String ID_NAME = "e_";
	
	public  static boolean includeXor = false;
	
	static Random rnd = new Random();
	
	private int maxDepth;
	boolean includeTrueFalse;
	private int nIds;

	static IdExpressionCreator icc = new IdExpressionCreator();
	static List<IdExpression> ids;

	/**
	 * reinti also the ids
	 * @param definedIds
	 * @param depth
	 * @param inTF
	 */
	public RandomLogicExpressionBuilder(List<IdExpression> lids, int depth, boolean inTF) {
		this.depth = depth;
		this.maxDepth = depth;
		includeTrueFalse = inTF;
		ids = new ArrayList<IdExpression>(lids);
		nIds = ids.size();
	}

	
	/**
	 * Instantiates a new random logic expression builder.
	 *
	 * @param nIds the number of  ids
	 * @param depth the depth (fixed)
	 * @param inTF the in tf
	 */
	public RandomLogicExpressionBuilder(int nIds, int depth, boolean inTF) {
		if (ids == null) ids = new ArrayList<IdExpression>();
		this.depth = depth;
		this.maxDepth = depth;
		includeTrueFalse = inTF;
		// build the ids if they are not enough. IDs are reused (it can be
		// useful when collecting)
		// if 10 ids ..create 10 ... nIds-1
		// the first has suffix name 0
		for (int i = ids.size(); i < nIds; i++) {
			ids.add(icc.createIdExpression(ID_NAME + i, null));
		}
		// ids may contain more ids than requested
		this.nIds = nIds;
		assert this.nIds <= ids.size();
	}

	private int depth;
	
	private Expression getExpression() {
		if (depth == 0)
			return getAnId();
		int type;
		if (includeXor) {
			type = rnd.nextInt(4);// from 0 to 3
		} else {
			type = rnd.nextInt(3);// from 0 to 2
		}
		
		switch (type) {
		case 0:
			// AND
			depth--;
			Expression e1 = getExpression();
			Expression e2 = getExpression();
			return BinaryExpression.mkBinExpr(e1, Operator.AND, e2);
		case 1:
			// OR
			depth--;
			e1 = getExpression();
			e2 = getExpression();
			return BinaryExpression.mkBinExpr(e1, Operator.OR, e2);
		case 2:
			// NOT
			depth--;
			e1 = getExpression();
			return UnaryExpression.mkUnExpr(Operator.NOT, e1);
		case 3:
			// XOR
			depth--;
			e1 = getExpression();
			e2 = getExpression();
			return BinaryExpression.mkBinExpr(e1, Operator.XOR, e2);
		default:
			throw new RuntimeException("Type " + type + " not defined");
		}
	}

	// including true and false, if requested
	
	private Expression getAnId() {
		int id;
		if (includeTrueFalse)
			id = rnd.nextInt(nIds + 2);
		else
			id = rnd.nextInt(nIds);
		if(id < nIds){	
			assert ids != null;
			return ids.get(id);
		} else {
			// true or false
			if (id == nIds) return BoolType.FALSE_CONST;
			 assert (id == nIds +1);
			 return BoolType.TRUE_CONST;
		}
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public Expression next() {
		depth = maxDepth;
		return getExpression();
	}

	@Override
	public void remove() {
		throw new RuntimeException("operation not supported");
	}
}
