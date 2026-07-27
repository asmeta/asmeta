package atgt.coverage;

/**
 * coverage tree or coverage for ASMs TestConditions
 * 
 * @author garganti
 * 
 */
public abstract class AsmCoverage extends tgtlib.coverage.CoverageTree<AsmTestCondition>
		implements VisitableTPTreeNode {

	public AsmCoverage(String name) {
		super(name);
	}
}