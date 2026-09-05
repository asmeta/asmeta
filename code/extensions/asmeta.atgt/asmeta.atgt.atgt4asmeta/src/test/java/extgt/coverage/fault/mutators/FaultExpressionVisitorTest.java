package extgt.coverage.fault.mutators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FaultExpressionVisitorTest {

	@Test void msb() {
		check("1000", "1000");
		check("1", "1");
		check("1010", "1000");	
		check("10100001", "10000000");	
		assertEquals(2, FaultExpressionVisitor.MSB(2));
		assertEquals(2, FaultExpressionVisitor.MSB(3));
		assertEquals(4, FaultExpressionVisitor.MSB(4));
		assertEquals(4, FaultExpressionVisitor.MSB(7));
	}

	private void check(String a, String b) {
		int x = Integer.parseInt(a,2);
		int res = FaultExpressionVisitor.MSB(x);
		assertEquals(b, Integer.toBinaryString(res));
	}

	@Test void nextPos() {
		// 1 -> 2, 3
		assertEquals(2,FaultExpressionVisitor.getNextNodePos(1, false));
		assertEquals(3,FaultExpressionVisitor.getNextNodePos(1, true));
		// add the node to left 2 -> 4, 3-> 6
		assertEquals(4,FaultExpressionVisitor.getNextNodePos(2, false));
		assertEquals(6,FaultExpressionVisitor.getNextNodePos(3, false));
		// add the node to right 2 -> 5, 3->  7
		assertEquals(5,FaultExpressionVisitor.getNextNodePos(2, true));
		assertEquals(7,FaultExpressionVisitor.getNextNodePos(3, true));
	}	
}
