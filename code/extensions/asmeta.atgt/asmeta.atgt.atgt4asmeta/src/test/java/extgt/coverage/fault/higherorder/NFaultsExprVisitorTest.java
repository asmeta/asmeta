package extgt.coverage.fault.higherorder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import tgtlib.definitions.expression.Expression;

import org.junit.jupiter.api.Test;
import tgtlib.definitions.expression.parser.ExpressionParser;
import tgtlib.util.Pair;
import extgt.coverage.fault.mutators.ExpressionMutator;
import extgt.coverage.fault.mutators.FaultTest;
import extgt.coverage.fault.mutators.foms.MissingVariableFault;
import extgt.coverage.fault.mutators.foms.VariableNegationFault;
import extgt.coverage.fault.mutators.foms.VariableReferenceFault;

class NFaultsExprVisitorTest extends FaultTest {

	@Test void test1() {
		NFaultsExprVisitor ff1 = new NFaultsExprVisitor(MissingVariableFault.MVF, VariableNegationFault.VNF);
		ExpressionMutator mut = ff1.getExpressionMutator(aANDb);
		List<Pair<Integer, Expression>> ll = mut.getMutations(aANDb);
		assertEquals("[<0, not B>, <0, not A>]", ll.toString());
	}

	@Test void removeAndInsert() throws Exception {
		Expression expr = ExpressionParser.parseAsNewBooleanExpression("a and (b or c)");
		NFaultsExprVisitor ff1 = new NFaultsExprVisitor(MissingVariableFault.MVF, VariableReferenceFault.VRF);
		List<Pair<Integer, Expression>> ll = ff1.getExpressionMutator(expr).getMutations(expr);
		assertEquals("[<0, not B>, <0, not A>]", ll.toString());
	}

	// goal : test that applying n times a fault, the ids do not get reduced otheriwse the insertion won't work 
	@Test void remove() throws Exception {
		NFaultsExprVisitor ff1 = new NFaultsExprVisitor(VariableReferenceFault.VRF);
		Expression expr = ExpressionParser.parseAsNewBooleanExpression("a and (b or c)");
		ExpressionMutator mut = ff1.getExpressionMutator(expr);
		List<Pair<Integer, Expression>> ll = mut.getMutations(expr);
		System.out.println(ll);		
		// now get a new expression 
		// same id applies
		expr = ExpressionParser.parseAsNewBooleanExpression("a or b");
		ll = mut.getMutations(expr);
		System.out.println(ll);
		// same id applies
		expr = ExpressionParser.parseAsNewBooleanExpression("a");
		ll = mut.getMutations(expr);
		System.out.println(ll);
		assertEquals(2, ll.size());
		assertEquals("[<0, b>, <0, c>]", ll.toString());		
	}	
}
