package tgtlib.definitions.expression;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import tgtlib.definitions.expression.parser.ExpressionParser;

import org.junit.jupiter.api.Test;

class GetConditionsTest extends ExpressionsToTest{

	@Test void forAndExpression() {
		List<IdUNotIdExpression> r1 = aANDb.accept(GetConditions.getConds);
		checkIds(r1,A,B);
	}

	@Test void forOrExpression() {
		List<IdUNotIdExpression> r1 = aORb.accept(GetConditions.getConds);
		checkIds(r1,A,B);
	}

	@Test void forComplexExpressions() {
		List<IdUNotIdExpression> r1 = not_AandB.accept(GetConditions.getConds);
		checkIds(r1,A,B);
		AndExpression a1 = new AndExpression(aANDb, aORb);
		r1 = a1.accept(GetConditions.getConds);
		checkIds(r1,A,B);
	}

	@Test void forDuplicated() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("a and b or (c or b)");
		System.out.println(e.toString());
		List<IdUNotIdExpression> r1 = e.accept(GetConditions.getConds);
		checkIdsAsStrings(r1, "a","b","c");
	}

	@Test void testEnum() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("true and b");
		System.out.println(e.toString());
		List<IdUNotIdExpression> r1 = e.accept(GetConditions.getConds);
		checkIdsAsStrings(r1, "b");
	}

	// with some nots
	@Test void forNot1() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("not a and not b");
		List<IdUNotIdExpression> r1 = e.accept(GetConditions.getConds);
		checkIdsAsStrings(r1, "not a","not b");
	}

	@Test void forNot2() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("not a and b");
		List<IdUNotIdExpression> r1 = e.accept(GetConditions.getConds);
		checkIdsAsStrings(r1, "not a","b");
	}

	@Test void forNot3() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("not a and (b or not a)");
		List<IdUNotIdExpression> r1 = e.accept(GetConditions.getConds);
		checkIdsAsStrings(r1, "not a","b");
	}

	@Test void forNot4() throws Exception {
		Expression e = ExpressionParser.parseAsNewBooleanExpression("not a and (b or a)");
		System.out.println(e.toString());
		List<IdUNotIdExpression> r1 = e.accept(GetConditions.getConds);
		checkIdsAsStrings(r1, "not a","b", "a");
	}
	
	private void checkIds(List<IdUNotIdExpression> r1, IdUNotIdExpression ... ids) {
		assertNotNull(r1);
		assertEquals(ids.length, r1.size());
		IdUNotIdExpression[] ar1 = r1.toArray(new IdUNotIdExpression[r1.size()]);
		assertArrayEquals(ids, ar1);
	}

	// assuming ids not duplicated
	private void checkIdsAsStrings(List<IdUNotIdExpression> r1, String ... ids) {
		assertNotNull(r1);
		assertEquals(ids.length, r1.size());
		List<String> idsL = Arrays.asList(ids);
		for(IdUNotIdExpression id:r1){
			assertTrue(idsL.contains(id.toString()));
		}
	}
}
