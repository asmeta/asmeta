package tgtlib.definitions.normalform;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class NFExpressionTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test void combinations() {
		List<List<Integer>> li = Arrays.asList(Arrays.asList(1,2,3),Arrays.asList(4,5,6));
		List<List<Integer>> res = NFExpression.combinations(li);
		assertEquals("[[1, 4], [1, 5], [1, 6], [2, 4], [2, 5], [2, 6], [3, 4], [3, 5], [3, 6]]",res.toString());
	}

}
