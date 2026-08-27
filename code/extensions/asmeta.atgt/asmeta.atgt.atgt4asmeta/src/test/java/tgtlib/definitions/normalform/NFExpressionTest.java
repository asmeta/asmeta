package tgtlib.definitions.normalform;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class NFExpressionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testCombinations() {
		List<List<Integer>> li = Arrays.asList(Arrays.asList(1,2,3),Arrays.asList(4,5,6));
		List<List<Integer>> res = NFExpression.combinations(li);
		assertEquals("[[1, 4], [1, 5], [1, 6], [2, 4], [2, 5], [2, 6], [3, 4], [3, 5], [3, 6]]",res.toString());
	}

}
