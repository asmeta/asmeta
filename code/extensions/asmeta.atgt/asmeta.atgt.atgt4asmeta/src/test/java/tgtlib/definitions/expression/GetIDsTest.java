package tgtlib.definitions.expression;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;

public class GetIDsTest extends ExpressionsToTest {

	@Test
	public void testForAndExpression() {
		List<IdExpression> r1 = aANDb.accept(GetIDs.getIDs);
		checkIds(r1,A,B);
	}

	@Test
	public void testForOrExpression() {
		List<IdExpression> r1 = aORb.accept(GetIDs.getIDs);
		checkIds(r1,A,B);
	}

	@Test
	public void testForComplexExpressions() {
		List<IdExpression> r1 = not_AandB.accept(GetIDs.getIDs);
		checkIds(r1,A,B);
		AndExpression a1 = new AndExpression(aANDb, aORb);
		r1 = a1.accept(GetIDs.getIDs);
		checkIds(r1,A,B);
	}

	@Test
	public void testForDuplicated() throws ParseException {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a and b or (c or b)");
		System.out.println(e.toString());
		List<IdExpression> r1 = e.accept(GetIDs.getIDs);
		checkIdsAsStrings(r1, "a","b","c");
	}
	
	@Test
	public void testEnum() throws ParseException {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("true and b");
		System.out.println(e.toString());
		List<IdExpression> r1 = e.accept(GetIDs.getIDs);
		checkIdsAsStrings(r1, "TRUE","b");
	}

	private void checkIds(List<IdExpression> r1, IdExpression ... ids) {
		assertNotNull(r1);
		assertEquals(ids.length, r1.size());
		IdExpression[] ar1 = r1.toArray(new IdExpression[r1.size()]);
		assertArrayEquals(ids, ar1);
	}

	// assuming ids not duplicated
	private void checkIdsAsStrings(List<IdExpression> r1, String ... ids) {
		assertNotNull(r1);
		assertEquals(ids.length, r1.size());
		List<String> idsL = Arrays.asList(ids);
		for(IdExpression id:r1){
			idsL.contains(id.getIdString());
		}
	}
}
