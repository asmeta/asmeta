//package tgtlib.definitions.expression.bdd;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//
//import tgtlib.definitions.expression.Expression;
//import tgtlib.definitions.expression.parser.ExpressionParser;
//import tgtlib.definitions.expression.parser.ParseException;
//import tgtlib.definitions.expression.visitors.IDExprCollector;
//import tgtlib.util.Pair;
//
//public class ToBDDTranslatorJDDTest {
//
//	@Test
//	public void testID() throws ParseException {
//		Expression a = ExpressionParser.parseAsNewBooleanExpression("a");
//		IDExprCollector idex = IDExprCollector.instance;
//		ToBDDTranslatorJDD jdd = new ToBDDTranslatorJDD(a.accept(idex));
//		Pair<jdd.bdd.BDD, Integer> r = jdd.translateToBDD(a);
//		jdd.bdd.BDD bdd = r.getFirst();
//		bdd.printSet(r.getSecond());
//		assertEquals(1, (bdd.satCount(r.getSecond())), 0);
//	}
//
//	@Test
//	public void testOR() throws ParseException {
//		Expression a = ExpressionParser.parseAsNewBooleanExpression("a or b");
//		IDExprCollector idex = IDExprCollector.instance;
//		ToBDDTranslatorJDD jdd = new ToBDDTranslatorJDD(a.accept(idex));
//		Pair<jdd.bdd.BDD, Integer> r = jdd.translateToBDD(a);
//		jdd.bdd.BDD bdd = r.getFirst();
//		bdd.printSet(r.getSecond());
//	    assertEquals(3, (bdd.satCount(r.getSecond())), 0);
//	}
//
//	@Test
//	public void testAND() throws ParseException {
//		Expression a = ExpressionParser.parseAsNewBooleanExpression("a and b");
//		IDExprCollector idex = IDExprCollector.instance;
//		ToBDDTranslatorJDD jdd = new ToBDDTranslatorJDD(a.accept(idex));
//		Pair<jdd.bdd.BDD, Integer> r = jdd.translateToBDD(a);
//		jdd.bdd.BDD bdd = r.getFirst();
//		bdd.printSet(r.getSecond());
//	    assertEquals(1, (bdd.satCount(r.getSecond())), 0);
//	}
//
//	@Test
//	public void testIMPLIES() throws ParseException {
//		Expression a = ExpressionParser.parseAsNewBooleanExpression("a implies b");
//		IDExprCollector idex = IDExprCollector.instance;
//		ToBDDTranslatorJDD jdd = new ToBDDTranslatorJDD(a.accept(idex));
//		Pair<jdd.bdd.BDD, Integer> r = jdd.translateToBDD(a);
//		jdd.bdd.BDD bdd = r.getFirst();
//		bdd.printSet(r.getSecond());
//	    assertEquals(3, (bdd.satCount(r.getSecond())), 0);
//	}
//
//	@Test
//	public void testAND_OR() throws ParseException {
//		Expression a = ExpressionParser.parseAsNewBooleanExpression("a and (b or c)");
//		IDExprCollector idex = IDExprCollector.instance;
//		ToBDDTranslatorJDD jdd = new ToBDDTranslatorJDD(a.accept(idex));
//		Pair<jdd.bdd.BDD, Integer> r = jdd.translateToBDD(a);
//		jdd.bdd.BDD bdd = r.getFirst();
//		bdd.printSet(r.getSecond());
//	    assertEquals(3, (bdd.satCount(r.getSecond())), 0);
//	}
//
//	@Test
//	public void testAND_OR_noVisitor() throws ParseException {
//		jdd.bdd.BDD bdd = new jdd.bdd.BDD(1000, 100);
//		int a = bdd.createVar();
//		int b = bdd.createVar();
//		int c = bdd.createVar();
//		
//		int or = bdd.or(b, c);
//	    bdd.ref(or);
//	    bdd.deref(b);
//	    bdd.deref(c);
//
//	    int and = bdd.and(a, or);
//	    bdd.ref(and);
//	    bdd.deref(or);
//	    bdd.deref(a);
//
//	    System.out.println(bdd.satCount(and));
//	}
//
//	@Test
//	public void testAND_OR_OR() throws ParseException {
//		Expression a = ExpressionParser.parseAsNewBooleanExpression("(a or b) and (b or c)");
//		IDExprCollector idex = IDExprCollector.instance;
//		ToBDDTranslatorJDD jdd = new ToBDDTranslatorJDD(a.accept(idex));
//		Pair<jdd.bdd.BDD, Integer> r = jdd.translateToBDD(a);
//		jdd.bdd.BDD bdd = r.getFirst();
//		bdd.printSet(r.getSecond());
//	    assertEquals(5, (bdd.satCount(r.getSecond())), 0);
//	}
//
//	@Test
//	public void testForIdExpression() throws ParseException {
//		Expression a = ExpressionParser.parseAsNewBooleanExpression("a or b or c");
//		IDExprCollector idex = IDExprCollector.instance;
//		ToBDDTranslatorJDD jdd = new ToBDDTranslatorJDD(a.accept(idex));
//		Pair<jdd.bdd.BDD, Integer> r = jdd.translateToBDD(a);
//		jdd.bdd.BDD bdd = r.getFirst();
//		bdd.printSet(r.getSecond());
//	    System.out.println(bdd.satCount(r.getSecond()));
//	}
//}
