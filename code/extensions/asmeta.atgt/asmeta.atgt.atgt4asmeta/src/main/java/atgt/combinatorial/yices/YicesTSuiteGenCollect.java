//package atgt.combinatorial.yices;
//
//import atgt.coverage.AsmTestSequence;
//import atgt.coverage.AsmTestSuite;
//import atgt.generator.testsuite.TestGeneratorCollectTP;
//import atgt.project.AsmProject;
//
//
///**Generatore per Yices che utilizza il collect.*/
//public class YicesTSuiteGenCollect extends TestGeneratorCollectTP {
//
//	/**Istanziazione di un nuovo test suite generator collect di Yices.
//	 * 
//	 * @param _project sistema sottoposto a test.
//	 */
//	public YicesTSuiteGenCollect(AsmProject pro) {
//		super(pro, new YicesModelGenExec(pro.specification.getVariables(), pro.specification.getAxiom(), AsmTestSequence.factory), AsmTestSuite.getAsmTestSuiteFactory());
//	}
//
//}