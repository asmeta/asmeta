package extgt.coverage.combinatorial;

import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.type.EnumConst;

public interface PairEqTestCondFactory<P extends TestPredicate<?,?>> {

	P buildTestPredicate(String n, TypedInitExpression varK,
			EnumConst val1, TypedInitExpression varJ, EnumConst val2);

}
