//package tgtlib.definitions.expression.bdd;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.Set;
//
//import org.junit.Test;
//
//import net.sf.javabdd.BDD;
//import tgtlib.definitions.expression.Expression;
//import tgtlib.definitions.expression.IdExpression;
//import tgtlib.definitions.expression.parser.ExpressionParser;
//import tgtlib.definitions.expression.parser.ParseException;
//import tgtlib.definitions.expression.type.EnumConstCreator;
//import tgtlib.definitions.expression.visitors.IDExprCollector;
//
//public class ToBDDTranslatorTest {
//
//	@Test
//	public void testNOTANOT() throws ParseException {
//		Expression a = ExpressionParser.parseAsNewBooleanExpression("not a and not b");
//		IDExprCollector idex = IDExprCollector.instance;
//		ToBDDTranslator bddt = new ToBDDTranslator(a.accept(idex));
//		BDD bdd =  bddt.translateToBDD(a);
//		bdd.printDot();
//	}
//
//	
//	
//	@Test
//	public void testForIdExpression() throws ParseException {
//		Expression a = ExpressionParser.parseAsNewBooleanExpression("a");
//		IDExprCollector idex = IDExprCollector.instance;
//		ToBDDTranslator bddt = new ToBDDTranslator(a.accept(idex));
//		BDD bdd =  bddt.translateToBDD(a);
//		assertEquals(1,bdd.nodeCount());
//		System.out.println(bdd.toString());
//	}
//
//	@Test
//	public void testForAndExpression() throws ParseException {
//		Expression a = ExpressionParser.parseAsNewBooleanExpression("a and b");
//		IDExprCollector idex = IDExprCollector.instance;
//		ToBDDTranslator bddt = new ToBDDTranslator(a.accept(idex));
//		BDD bdd =  bddt.translateToBDD(a);
//		assertEquals(2,bdd.nodeCount());
//		assertEquals(1, bdd.satCount(),0);
//		System.out.println(bdd.toString());
//	}
//
//	@Test
//	public void testForImpliesExpression() throws ParseException {
//		Expression a = ExpressionParser.parseAsNewBooleanExpression("a implies b");
//		IDExprCollector idex = IDExprCollector.instance;
//		ToBDDTranslator bddt = new ToBDDTranslator(a.accept(idex));
//		BDD bdd =  bddt.translateToBDD(a);
//		assertEquals(2,bdd.nodeCount());
//		assertEquals(3, bdd.satCount(),0);
//		System.out.println(bdd.toString());
//	}
//	@Test
//	public void testForEquals() throws ParseException {
//		Expression a = ExpressionParser.parseAsNewBooleanExpression("a == b");
//		IDExprCollector idex = IDExprCollector.instance;
//		ToBDDTranslator bddt = new ToBDDTranslator(a.accept(idex));
//		BDD bdd =  bddt.translateToBDD(a);
//		System.out.println(bdd.toString());
//		//assertEquals(2,bdd.nodeCount());
//		assertEquals(2, bdd.satCount(),0);
//		System.out.println(bdd.toString());
//	}
//
//	@Test
//	public void testBDD() throws Exception {
//		EnumConstCreator creator = new EnumConstCreator();
//		Expression exp = ExpressionParser.parseAsBooleanExpression("(a implies b) and not(a implies c)", creator);
//		Set<IdExpression> ids = exp.accept(IDExprCollector.instance);
//		ToBDDTranslator bddTranslator = new ToBDDTranslator(ids);
//		BDD bdd = exp.accept(bddTranslator);
//		assertEquals(1, bdd.satCount(), 0);
//		Expression newConstr = ExpressionParser.parseAsBooleanExpression("a implies c", creator);
//		bdd = bdd.and(newConstr.accept(bddTranslator));
//		assertEquals(0, bdd.satCount(), 0);
//	}
//}
