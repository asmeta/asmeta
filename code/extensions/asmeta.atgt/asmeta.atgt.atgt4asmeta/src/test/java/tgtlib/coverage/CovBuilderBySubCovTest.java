package tgtlib.coverage;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;

import tgtlib.definitions.TestPredicate;
import tgtlib.definitions.expression.type.Variable;
import tgtlib.specification.Axiom;
import tgtlib.specification.Specification;

/**
 */
public class CovBuilderBySubCovTest {

	/**
	 * if rootname is null. take the name of the spec
	 * 
	 */
	@Test
	public void testRegisterNull() {
		final String specName = "SPEC";
		String rootName = null;
		CoverageTree<TestPredicate> tree = testRegister(specName, rootName);
		assertEquals(specName, tree.name);
	}

	/**
	 * if root has name. take that name
	 * 
	 */
	@Test
	public void testRegister() {
		final String specName = "SPEC";
		String rootName = "ROOT";
		CoverageTree<TestPredicate> tree = testRegister(specName, rootName);
		assertEquals(rootName, tree.name);
	}

	/**
	 * Test register.
	 * 
	 * @param specName
	 *            the spec name
	 * @param rootName
	 *            the root name
	
	 * @return CoverageTree<TestPredicate>
	 */
	private CoverageTree<TestPredicate> testRegister(final String specName,
			String rootName) {
		String covName = "MYCOV";
		CoverageTreeFactory<? extends CoverageTree<TestPredicate>> ctf = new CoverageTreeFactory<CoverageTree<TestPredicate>>() {

			@Override
			public CoverageTree<TestPredicate> buildEmptyCovTree(String n) {
				return new CoverageTree<TestPredicate>(n) {
				};
			}
		};
		CovBuilderBySubCov<Specification, TestPredicate, CoverageTree<TestPredicate>>
		// do not give the name of the tree
		root = new CovBuilderBySubCov<Specification, TestPredicate, CoverageTree<TestPredicate>>(
				rootName, ctf);
		CoverageBuilder<Specification, CoverageTree<TestPredicate>> covBuilder = new CovBuilderBySubCov<Specification, TestPredicate, CoverageTree<TestPredicate>>(
				covName, ctf);
		root.register(covBuilder);
		Specification spec = new Specification() {

			@Override
			public String getName() {
				return specName;
			}

			@Override
			public Collection<? extends Variable> getVariables() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Collection<Axiom> getAxiom() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		CoverageTree<TestPredicate> tree = root.getTPTree(spec);
		assertEquals(covName, tree.getChildAt(0).getName());
		return tree;
	}
}
