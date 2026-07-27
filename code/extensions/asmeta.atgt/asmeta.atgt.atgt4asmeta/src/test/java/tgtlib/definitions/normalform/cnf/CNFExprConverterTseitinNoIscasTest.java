package tgtlib.definitions.normalform.cnf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.definitions.expression.parser.ParseException;
import tgtlib.definitions.normalform.Term;

// test of the convrter no iscas
public class CNFExprConverterTseitinNoIscasTest {

	private static final String PHI = CNFExprConverterTseitinNoIscas.ID_PREFIX;
	
		
	@Test
	public void testID() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[a]", res.toString());
	}

	@Test(expected=CNFException.class)
	public void testTRUE() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("true");
		CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
	}
	@Test(expected=CNFException.class)
	public void testFALSE() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("false");
		CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
	}

	@Test
	public void testRemoveTF() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a or false");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[a]", res.toString());
		id = ExpressionParser.parseAsNewBooleanExpression("a and true");
		res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[a]", res.toString());
	}

	@Test(expected=CNFException.class)
	public void testRemoveIDandF() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a and false");
		CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
	}

	@Test(expected=CNFException.class)
	public void testRemoveIDorT() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a or true");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
	}

	@Test
	public void testDoubleID() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[a]", res.toString());
		CNFExpression res2 = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[a]", res2.toString());
		assertSame(res.getTerms().get(0).get(0),res2.getTerms().get(0).get(0));
	}

	
	@Test
	public void testOR1() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a || b");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertTrue(res.toString().equals("[ab]")||res.toString().equals("[ba]"));
	}

	@Test
	public void testNOT() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("not a");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[~a]", res.toString());
	}

	@Test
	public void testAND1() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a && b");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[b, a]", res.toString());						
	}
	
	@Test
	public void testAND2() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("(a and b) and (a or not b)");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertTrue(res.getTerms().toString(),containsTerm(res,"a"));
		assertTrue(res.getTerms().toString(),containsTerm(res,"b"));
		assertTrue(res.getTerms().toString(),containsTerm(res,"a~b"));
	}
	@Test
	public void testNotOR() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("not a || b");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertTrue(res.toString().equals("[~ab]")||res.toString().equals("[b~a]"));
	}

	@Test
	public void testNOTAND() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("not(a && b)");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		// 
		assertTrue(res.getTerms().toString(),containsTerm(res,"~b~a"));
	}

	@Test
	public void testNOR() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("not(a || b)");
		// 
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertTrue(res.getTerms().toString(),containsTerm(res,"~a"));
		assertTrue(res.getTerms().toString(),containsTerm(res,"~b" ));
	}

	
	@Test
	public void testNOR2() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("not(a || b)");
		// TODOOO
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		// not a and not b
		assertTrue(res.getTerms().toString(),containsTerm(res,"~a"));
		assertTrue(res.getTerms().toString(),containsTerm(res,"~b"));
		assertEquals(2, res.getTerms().size());	
	}

	
	//but was:<[[not1or3, ~or3~not1, or3~a, or3~b, ~or3ab, not1]]>

	private boolean containsTerm(CNFExpression res, String string) {
		for(Term t:res.getTerms()){
			if (t.toString().equals(string)) return true;
		}
		return false;
	}

	@Test
	public void testANDNOT() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("(not a) && b");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertTrue(res.getTerms().toString(),containsTerm(res,"~a"));
		assertTrue(res.getTerms().toString(),containsTerm(res,"b"));
	}

	
	@Test
	public void testNotNot() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("not(not a)");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[a]", res.toString());
	}

	@Test
	public void testOrOr() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a or b or c");
		// No extra var is necessary 
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[bca]", res.toString());
	}

	@Test
	public void testOrOrInside() throws ParseException {
		// already as CNF
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a and (b or c or d)");		
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		// TODO
		assertEquals("[a,bcd]", res.toString());
	}

	// example taken from wikipedia
	@Test
	public void testExample() throws ParseException {
		Expression id = ExpressionParser.parseAsNewBooleanExpression("(a && b) || (c && d)");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		// 
		assertTrue(res.getTerms().toString(),containsTerm(res,"a~phi1"));
		assertTrue(res.getTerms().toString(),containsTerm(res,"b~phi1"));
		assertTrue(res.getTerms().toString(),containsTerm(res,"c~phi0"));
		assertTrue(res.getTerms().toString(),containsTerm(res,"d~phi0"));
		//
		assertTrue(res.getTerms().toString(),containsTerm(res,"phi0phi1"));
		// these are included but I'm not sure they should be  
		assertTrue(res.getTerms().toString(),containsTerm(res,"~c~dphi0"));
		assertTrue(res.getTerms().toString(),containsTerm(res,"~a~bphi1"));
	}
	
	@Test
	public void testNoRipetitions() throws ParseException {
		// ripedted itself
		Expression id = ExpressionParser.parseAsNewBooleanExpression("a or a");
		CNFExpression res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[a]", res.toString());
		// and its negation
		id = ExpressionParser.parseAsNewBooleanExpression("a or not a or b");
		res = CNFExprConverterTseitinNoIscas.instance.getCNFExprConverter().getCNF(id);
		assertEquals("[]", res.toString());
	}
	
	@Test
	public void testgetDisjoints() throws ParseException {
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

	@Test
	public void testgetConjoints() throws ParseException {
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
