package atgt.coverage;

import org.junit.BeforeClass;

import atgt.specification.statement.ConditionalRule;
import atgt.specification.statement.DoStatement;
import atgt.specification.statement.Skip;
import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.definitions.expression.OrExpression;

public class RuleTest {

	/** The D. */
	static IdExpression A;

	static IdExpression B;


	/** The a and b. */
	static AndExpression aANDb;

	/** The a or b. */
	static OrExpression aORb;
	

	static DoStatement par;
	
	// condition without else
	static ConditionalRule if_woelse;

	static ConditionalRule if_wemptyelse;

	// this ha some rules inside else that may generate tps
	static ConditionalRule c_wnestedelse;

	
	@BeforeClass
	public static void setUp() {
		IdExpressionCreator icc = new IdExpressionCreator();
		A = icc.createIdExpression("A", null);
		B = icc.createIdExpression("B", null);
		aANDb = new AndExpression(A, B);
		aORb = new OrExpression(A, B);
		// rules:
		// if without else
		if_woelse = new ConditionalRule(aORb, Skip.SKIP);
		// if with a SKIP for else
		if_wemptyelse = new ConditionalRule(aANDb,Skip.SKIP,Skip.SKIP);
		//
		c_wnestedelse = new ConditionalRule(A,if_wemptyelse,if_wemptyelse); 
		// a par rule
		par = new DoStatement();
		par.addStatement(if_woelse);
		par.addStatement(if_wemptyelse);
	}	
	
}
