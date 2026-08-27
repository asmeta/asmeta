//package atgt.combinatorial.yices;
//
//import atgt.coverage.AsmCoverage;
//import atgt.coverage.AsmTestCondition;
//import atgt.coverage.AsmTestSequence;
//import atgt.generator.testsuite.TestSuiteGeneratorForTC;
//import tgtlib.project.Project;
//
///**
// * Genera una test suite (insieme di casi di test). Data una specifica si vuole
// * generare i casi di test.
// */
//public class YicesTSSuitegenForTC
//		extends
//		TestSuiteGeneratorForTC<YicesModelGen<AsmTestCondition, AsmTestSequence>> {
//
//	/**
//	 * Instaziazione di un nuovo generatore di test suite di Yices (non JNA)
//	 * 
//	 * @param pro
//	 *            che contiene le specifiche del sistema sottoposto a test.
//	 */
//	public YicesTSSuitegenForTC(Project<?, ?, ?, AsmCoverage> pro) {
//		super(pro, new YicesModelGenExec(pro.specification.getVariables(),
//				pro.specification.getAxiom(), AsmTestSequence.factory));
//	}
//}