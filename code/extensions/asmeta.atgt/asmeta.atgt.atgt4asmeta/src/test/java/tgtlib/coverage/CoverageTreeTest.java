package tgtlib.coverage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.expression.IdExpressionCreator;


/**
 */
public class CoverageTreeTest {

	
	/**
	 */
	class MyCoverageTree extends CoverageTree<NamedTerm>{

		protected MyCoverageTree() {
			super("");
			// TODO Auto-generated constructor stub
		}		
	}
	IdExpressionCreator icc = new IdExpressionCreator();
	
	NamedTerm  aT = new NamedTerm("a",icc.createIdExpression("a", null));
	NamedTerm  bT = new NamedTerm("a",icc.createIdExpression("b", null));
	NamedTerm  cT = new NamedTerm("a",icc.createIdExpression("c", null));
	
	TPInCoverage<NamedTerm> a = new TPInCoverage<NamedTerm>(aT);
	TPInCoverage<NamedTerm> b = new TPInCoverage<NamedTerm>(bT);
	TPInCoverage<NamedTerm> c = new TPInCoverage<NamedTerm>(cT);
	

	@Test
	public void testPreOrderEnumerationEmpty() {
		MyCoverageTree empty = new MyCoverageTree();
		checkNodes(empty,empty);
	}
	
	@Test
	public void testPreOrderEnumeration1() {
		MyCoverageTree cov = new MyCoverageTree();
		cov.addNode(a);
		checkNodes(cov,cov,a);
		checkTPs(cov, a);
	}
	@Test
	public void testPreOrderEnumeration2() {
		MyCoverageTree cov1 = new MyCoverageTree();
		MyCoverageTree cov2 = new MyCoverageTree();
		cov1.addNode(cov2);
		cov1.addNode(a);
		checkNodes(cov1,cov1,cov2,a);
		checkTPs(cov1, a);
		cov1.addNode(b);
		checkNodes(cov1,cov1,cov2,a,b);
		checkTPs(cov1, a,b);
		cov2.addNode(c);
		checkNodes(cov1,cov1,cov2,c,a,b);
		checkTPs(cov1, c,a,b);
		MyCoverageTree cov3 = new MyCoverageTree();
		cov1.addNode(cov3);
		checkNodes(cov1,cov1,cov2,c,a,b,cov3);
		checkTPs(cov1, c,a,b);		
	}

		
	/**
	 * Method checkNodes.
	 * @param c MyCoverageTree
	 * @param e TestPredicateTreeNode<NamedTerm>[]
	 */
	private void checkNodes(MyCoverageTree c, TestPredicateTreeNode<NamedTerm>  ... e) {
		Iterator<TestPredicateTreeNode<NamedTerm>> i = c.preOrderEnumeration();
		for(int j = 0; j < e.length; j++){
			assertTrue(" element "+ j + " not found",i.hasNext());			
			TestPredicateTreeNode<NamedTerm> n= i.next();
			assertSame(e[j],n);
		}
		assertFalse(i.hasNext());
	}

	
	/**
	 * Method checkTPs.
	 * @param c MyCoverageTree
	 * @param e TestPredicateTreeNode<NamedTerm>[]
	 */
	private void checkTPs(MyCoverageTree c, TestPredicateTreeNode<NamedTerm>  ... e) {
		Iterator<NamedTerm> i = c.allTPs().iterator();
		for(int j = 0; j < e.length; j++){			
			assertTrue(" element "+ j + " not found",i.hasNext());			
			NamedTerm n = i.next();
			assertSame(((TPInCoverage)e[j]).testPredicate,n);
		}
		assertFalse(i.hasNext());
	}

}