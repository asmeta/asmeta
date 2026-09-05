package extgt.coverage.combinatorial;

import static org.junit.jupiter.api.Assertions.*;


import tgtlib.definitions.TypedInitExpression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.type.IntegerType;

class MonitoredDataTest {

	@Test void addNotEnum() {
		IdExpressionCreator ecc = new IdExpressionCreator();
		IdExpression a = ecc.createIdExpression("a", null);
		TypedInitExpression var = new TypedInitExpression(a,IntegerType.INTEGER_TYPE, null);
		MonitoredData md = new MonitoredData();
		assertFalse(md.add(var));
	}

}
