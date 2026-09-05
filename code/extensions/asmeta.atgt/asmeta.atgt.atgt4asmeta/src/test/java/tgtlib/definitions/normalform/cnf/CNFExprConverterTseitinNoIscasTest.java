package tgtlib.definitions.normalform.cnf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import tgtlib.definitions.expression.AndExpression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.normalform.Term;

// test of the convrter no iscas
class CNFExprConverterTseitinNoIscasTest {

	private static final String PHI = CNFExprConverterTseitinNoIscas.ID_PREFIX;


	@Test void id() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[a]", res.toString());
	}

	@Test void testTRUE() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("true");
		assertThrows(CNFException.class, () ->
			CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id));
	}

	@Test void testFALSE() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("false");
		assertThrows(CNFException.class, () ->
			CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id));
	}

	@Test void removeTF() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a or false");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[a]", res.toString());
		id = ExpressionParser.parseAsNewBooleanExpression("a and true");
		res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[a]", res.toString());
	}

	@Test void removeIDandF() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a and false");
		assertThrows(CNFException.class, () ->
			CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id));
	}

	@Test void removeIDorT() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a or true");
		assertThrows(CNFException.class, () -> {
			CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		});
	}

	@Test void doubleID() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[a]", res.toString());
		CNFExpression res2 = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[a]", res2.toString());
		assertSame(res.getTerms().get(0).get(0),res2.getTerms().get(0).get(0));
	}


	@Test void or1() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a || b");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertTrue(res.toString().equals("[ab]")||res.toString().equals("[ba]"));
	}

	@Test void not() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("not a");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[~a]", res.toString());
	}

	@Test void and1() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a && b");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[b, a]", res.toString());						
	}

	@Test void and2() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("(a and b) and (a or not b)");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertTrue(containsTerm(res,"a"),res.getTerms().toString());
		assertTrue(containsTerm(res,"b"),res.getTerms().toString());
		assertTrue(containsTerm(res,"a~b"),res.getTerms().toString());
	}

	@Test void notOR() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("not a || b");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertTrue(res.toString().equals("[~ab]")||res.toString().equals("[b~a]"));
	}

	@Test void notand() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("not(a && b)");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		// 
		assertTrue(containsTerm(res,"~b~a"),res.getTerms().toString());
	}

	@Test void nor() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("not(a || b)");
		// 
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertTrue(containsTerm(res,"~a"),res.getTerms().toString());
		assertTrue(containsTerm(res,"~b" ),res.getTerms().toString());
	}


	@Test void nor2() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("not(a || b)");
		// TODOOO
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		// not a and not b
		assertTrue(containsTerm(res,"~a"),res.getTerms().toString());
		assertTrue(containsTerm(res,"~b"),res.getTerms().toString());
		assertEquals(2, res.getTerms().size());	
	}

	
	//but was:<[[not1or3, ~or3~not1, or3~a, or3~b, ~or3ab, not1]]>

	private boolean containsTerm(CNFExpression res, String string) {
		for(Term t:res.getTerms()){
			if (t.toString().equals(string)) return true;
		}
		return false;
	}

	@Test void andnot() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("(not a) && b");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertTrue(containsTerm(res,"~a"),res.getTerms().toString());
		assertTrue(containsTerm(res,"b"),res.getTerms().toString());
	}


	@Test void notNot() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("not(not a)");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[a]", res.toString());
	}

	@Test void orOr() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a or b or c");
		// No extra var is necessary 
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[bca]", res.toString());
	}

	@Test void orOrInside() throws Exception {
		// already as CNF
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a and (b or c or d)");		
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		// TODO
		assertEquals("[a,bcd]", res.toString());
	}

	// example taken from wikipedia
	@Test void example() throws Exception {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("(a && b) || (c && d)");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		// 
		assertTrue(containsTerm(res,"a~phi1"),res.getTerms().toString());
		assertTrue(containsTerm(res,"b~phi1"),res.getTerms().toString());
		assertTrue(containsTerm(res,"c~phi0"),res.getTerms().toString());
		assertTrue(containsTerm(res,"d~phi0"),res.getTerms().toString());
		//
		assertTrue(containsTerm(res,"phi0phi1"),res.getTerms().toString());
		// these are included but I'm not sure they should be  
		assertTrue(containsTerm(res,"~c~dphi0"),res.getTerms().toString());
		assertTrue(containsTerm(res,"~a~bphi1"),res.getTerms().toString());
	}

	@Test void noRipetitions() throws Exception {
		// ripedted itself
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a or a");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[a]", res.toString());
		// and its negation
		id = ExpressionParser.parseAsNewBooleanExpression("a or not a or b");
		res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[]", res.toString());
	}

	@Test void testgetDisjoints() throws Exception {
		//
		OrExpression id = (OrExpression) ExpressionParser.parseAsNewBooleanExpression("a or b or c");
		CNFExprConverterTseitinNoIscas cnfExprConverterTseitinNoIscas = new CNFExprConverterTseitinNoIscas();
		Collection<Expression> res = cnfExprConverterTseitinNoIscas.collect(id);
		assertContains(res,"a","b","c");
		//
		id = (OrExpression) ExpressionParser.parseAsNewBooleanExpression("(a or b) or c");
		res = cnfExprConverterTseitinNoIscas.collect(id);
		assertContains(res,"a","b","c");
		//
		id = (OrExpression) ExpressionParser.parseAsNewBooleanExpression("a or (b or c)");
		res = cnfExprConverterTseitinNoIscas.collect(id);
		assertContains(res,"a","b","c");
		// some other operators
		id = (OrExpression) ExpressionParser.parseAsNewBooleanExpression("a or (b and c) or d");
		res = cnfExprConverterTseitinNoIscas.collect(id);
		assertContains(res,"a","b and c","d");
		// with some repations
		id = (OrExpression) ExpressionParser.parseAsNewBooleanExpression("a or a");
		res = cnfExprConverterTseitinNoIscas.collect(id);
		assertContains(res,"a");
		// with some repations
		id = (OrExpression) ExpressionParser.parseAsNewBooleanExpression("a or not a");
		res = cnfExprConverterTseitinNoIscas.collect(id);
		assertContains(res,"a","not a");
	}

	@Test void testgetConjoints() throws Exception {
		//
		AndExpression id = (AndExpression) ExpressionParser.parseAsNewBooleanExpression("a and b and c");
		CNFExprConverterTseitinNoIscas cnfExprConverterTseitinNoIscas = new CNFExprConverterTseitinNoIscas();
		Collection<Expression> res = cnfExprConverterTseitinNoIscas.collect(id);
		assertContains(res,"a","b","c");
		//
		id = (AndExpression) ExpressionParser.parseAsNewBooleanExpression("(a and b) and c");
		res = cnfExprConverterTseitinNoIscas.collect(id);
		assertContains(res,"a","b","c");
		//
		id = (AndExpression) ExpressionParser.parseAsNewBooleanExpression("a and (b and c)");
		res = cnfExprConverterTseitinNoIscas.collect(id);
		assertContains(res,"a","b","c");
		// some other operators
		id = (AndExpression) ExpressionParser.parseAsNewBooleanExpression("a and (b or c) and d");
		res = cnfExprConverterTseitinNoIscas.collect(id);
		assertContains(res,"a","b or c","d");
		// with some repations
		id = (AndExpression) ExpressionParser.parseAsNewBooleanExpression("a and a");
		res = cnfExprConverterTseitinNoIscas.collect(id);
		assertContains(res,"a");
		// with some repations
		id = (AndExpression) ExpressionParser.parseAsNewBooleanExpression("a and not a");
		res = cnfExprConverterTseitinNoIscas.collect(id);
		assertContains(res,"a","not a");
	}

	
	
	private void assertContains(Collection<Expression> res, String ...string) {
		assertEquals(string.length,res.size());
		// get the strings:
		Collection<String> result = new ArrayList<String>();
		for(Expression e: res){
			result.add(e.toString());
		}
		for(String s: string){
			assertTrue(result.contains(s));
		}
		
	}
	
}
