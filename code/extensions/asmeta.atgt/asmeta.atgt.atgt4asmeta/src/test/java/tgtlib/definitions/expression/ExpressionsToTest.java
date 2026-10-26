package tgtlib.definitions.expression;

import org.junit.jupiter.api.BeforeAll;

/** some expressions useful for testing
 * 
 * @author garganti
 *
 * @version $Revision: 1.0 $
 */
public abstract class ExpressionsToTest {
	
	/** some ids - A,B,C,D*/
	protected static IdExpression A;
	protected static IdExpression B;
	protected static IdExpression C;
	protected static IdExpression D;
	/** The a and b. */
	protected static AndExpression aANDb;
	/** The a or b. */
	protected static OrExpression aORb;
	/** The not a. */
	protected static NotIDExpression notA;
	/** The not (a and b). */
	protected static NotExpression not_AandB;	
	/** The not a and b. */
	protected static AndExpression notA_andB;
	
	protected static IdExpressionCreator icc;
	
	@BeforeAll
	public static void faultTestSetup() {
		icc = new IdExpressionCreator();
		A = icc.createIdExpression("A", null);
		B = icc.createIdExpression("B", null);
		C = icc.createIdExpression("C", null);
		D = icc.createIdExpression("D", null);
		aANDb = new AndExpression(A, B);
		aORb = new OrExpression(A, B);
		notA = (NotIDExpression) NotExpression.createNotExpression(A);
		not_AandB = NotExpression.createNotExpression(aANDb);		
		notA_andB = new AndExpression(notA, B);		
	}

}
