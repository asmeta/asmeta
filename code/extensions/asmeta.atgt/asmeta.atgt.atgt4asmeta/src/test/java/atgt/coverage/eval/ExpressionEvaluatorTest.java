package atgt.coverage.eval;

import org.junit.Test;

import atgt.coverage.AsmTestSequence;
import atgt.parser.asmgofer.AsmExpressionParser;
import atgt.parser.asmgofer.ParseException;
import tgtlib.definitions.expression.Expression;

public class ExpressionEvaluatorTest {


	@Test
	public void testProblemwithSAT4j() throws ParseException {
		testExpreSeq("((((a and not b) and d) or ((a and not c) and d)) or e) xor ((false or ((a and not c) and d)) or e)", 
				"d=false, b=false, e=false, false=true, a=false, c=false");
	}
	
	

	private void testExpreSeq(String es, String testseq) throws ParseException {
		// the expression evaluator is not able to discore if it is covered or
		// not
		Expression e =  AsmExpressionParser.parse(es);
		// build test the TestSequence
		//TODO
	}

	// TODO to be completed
	private AsmTestSequence parse(String test){
		String[] assignements = test.split(",");
		for(String a: assignements){
			String[] l = a.trim().split("=");
			assert l.length == 2;
			// TODO finish
		}
		return null;
	}
}
