package extgt.coverage.combinatorial;

import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.type.EnumConst;

/**
 * represent particular test condition in which the relation is equal useful
 * because pair and Nwise belong to different hierachies
 * 
 * @author garganti
 * 
 */
public interface EqTestCondition {

	int size();

	TypedInitExpression getVar(int i);

	EnumConst getVal(int i);

}
