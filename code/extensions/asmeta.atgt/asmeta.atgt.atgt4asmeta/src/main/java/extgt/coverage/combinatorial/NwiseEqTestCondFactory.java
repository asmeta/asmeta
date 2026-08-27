package extgt.coverage.combinatorial;

import java.util.List;

import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.type.EnumConst;

public interface NwiseEqTestCondFactory<P extends TestPredicate> {

	P buildTestPredicate(String string,
			List<TypedInitExpression> vs, List<EnumConst> ecl);

}
