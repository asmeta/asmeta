package extgt.coverage.combinatorial;

import static org.junit.Assert.*;

import org.junit.Test;

import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.type.IntegerType;

public class MonitoredDataTest {

	@Test
	public void testAddNotEnum() {
		IdExpressionCreator ecc = new IdExpressionCreator();
		IdExpression a = ecc.createIdExpression("a", null);
		TypedInitExpression var = new TypedInitExpression(a,IntegerType.INTEGER_TYPE, null);
		MonitoredData md = new MonitoredData();
		assertFalse(md.add(var));
	}

}
