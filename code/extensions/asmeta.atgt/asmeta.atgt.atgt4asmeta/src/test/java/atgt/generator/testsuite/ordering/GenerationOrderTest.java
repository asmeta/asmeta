package atgt.generator.testsuite.ordering;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;

import atgt.coverage.TestCondition;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.TestPredicate;
import tgtlib.generator.ordering.GenerationOrder;

class GenerationOrderTest {


	@Test void next() {
		TestPredicate<?,?> A = new TestCondition("A",null);
		TestPredicate<?,?> B = new TestCondition("B",null);
		GenerationOrder<TestPredicate<?,?>> go = new GenerationOrder<TestPredicate<?,?>>(Arrays.asList(A,B));
		assertSame(A,go.next());
		assertSame(B,go.next());
	}
}
