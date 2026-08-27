package atgt.generator.testsuite.ordering;

import static org.junit.Assert.assertSame;

import java.util.Arrays;

import org.junit.Test;

import atgt.coverage.TestCondition;
import tgtlib.definitions.TestPredicate;
import tgtlib.generator.ordering.GenerationOrder;

public class GenerationOrderTest {


	@Test
	public void testNext() {
		TestPredicate A = new TestCondition("A",null);
		TestPredicate B = new TestCondition("B",null);
		GenerationOrder<TestPredicate> go = new GenerationOrder<TestPredicate>(Arrays.asList(A,B));
		assertSame(A,go.next());
		assertSame(B,go.next());
	}
}
