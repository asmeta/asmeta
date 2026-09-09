package extgt.coverage.combinatorial;

import static org.junit.jupiter.api.Assertions.assertEquals;


import tgtlib.coverage.CoverageTree;

import org.junit.jupiter.api.Test;
import tgtlib.coverage.CoverageTreeFactory;
import tgtlib.definitions.TypedInitExpression;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.generator.TestPredicate4Test;
import tgtlib.specification.Specification;

class StdPairwiseCovBuildTest {
	MonitorDataExtractor<Specification> mymde = new MonitorDataExtractor<Specification>() {

		@Override
		public MonitoredData analyze(Specification SP) {
			return new MonitoredData();
		}
	};
	PairEqTestCondFactory<TestPredicate4Test> pf = new PairEqTestCondFactory<TestPredicate4Test>() {

		@Override
		public TestPredicate4Test buildTestPredicate(String n,
				TypedInitExpression varK, EnumConst val1,
				TypedInitExpression varJ, EnumConst val2) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	CoverageTreeFactory<? extends CoverageTree<TestPredicate4Test>> cf = new CoverageTreeFactory<CoverageTree<TestPredicate4Test>>() {
		@Override
		public CoverageTree<TestPredicate4Test> buildEmptyCovTree(String name) {
			return new CoverageTree<TestPredicate4Test>(name){};
		}
	};
	StdPairwiseCovBuild<Specification, TestPredicate4Test, CoverageTree<TestPredicate4Test>> std = new StdPairwiseCovBuild<Specification, TestPredicate4Test, CoverageTree<TestPredicate4Test>>(
			mymde, pf, cf);

	@Test void testgetTPTree() {
		// std.computeTPs(v);
	}

	@Test void computeTPEmptyMD() {
		MonitoredData md = new MonitoredData();
		CoverageTree<TestPredicate4Test> ct = std.computeTPs(md);
		assertEquals(0, ct.getChildCount());
	}

	@Test void computeTP2for2Enum() {
		MonitoredData md = new MonitoredData();
		md.add(new TypedInitExpression(NWiseCovBuilderTest.ecc.createIdExpression("a", null),NWiseCovBuilderTest.A ,null ));
		md.add(new TypedInitExpression(NWiseCovBuilderTest.ecc.createIdExpression("b", null),NWiseCovBuilderTest.B ,null ));
		//md.add(o)
		CoverageTree<TestPredicate4Test> ct = std.computeTPs(md);
		assertEquals(4, ct.getChildCount());
	}

	@Test void computeTP2for2Bool() {
		MonitoredData md = new MonitoredData();
		md.add(new TypedInitExpression(NWiseCovBuilderTest.ecc.createIdExpression("a", null),BoolType.BOOLTYPE ,null ));
		md.add(new TypedInitExpression(NWiseCovBuilderTest.ecc.createIdExpression("b", null),BoolType.BOOLTYPE ,null ));
		//md.add(o)
		CoverageTree<TestPredicate4Test> ct = std.computeTPs(md);
		assertEquals(4, ct.getChildCount());
	}

	
}
