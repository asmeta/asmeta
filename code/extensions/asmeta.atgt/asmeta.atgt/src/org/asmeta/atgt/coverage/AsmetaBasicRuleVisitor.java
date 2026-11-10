package org.asmeta.atgt.coverage;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.asmeta.parser.util.AsmPrinter;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.Coverage;
import tgtlib.definitions.NamedTerm;

// substitute the atgt.coverage.BasicRuleVisitor - as asmeta coverage builder i.e. it build a tp tree from asmeta specs directly
public class AsmetaBasicRuleVisitor implements AsmetaCoverageBuilder {

	static Logger logger = Logger.getLogger(AsmetaBasicRuleVisitor.class);

	/**
	 * costruisce un nuovo basic rule visitor messo public per permettere la
	 * creazione da parte del plugin di eclipse.
	 */
	public AsmetaBasicRuleVisitor() {
	}

	@Override
	public String getCoveragePrefix() {
		return "ABR";
	}

	@Override
	public AsmCoverage getTPTree(AsmetaAsSpec spec) {
		AsmetaConditionExtractor ce = new AsmetaConditionExtractor();
		List<AsmTestCondition> list = new Vector<>();
		try {
			List<NamedTerm> conditions = ce.visit(spec.asm.getMainrule().getRuleBody());
			for (NamedTerm ne : conditions) {
				AsmTestCondition e = new AsmTestCondition(ne.getName(), ne.getCondition());
				list.add(e);
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				System.err.println("Error in AsmetaBasicRuleVisitor.getTPTree: " + e.getMessage());
				System.err.println("Printing the ASM for debugging purposes:");
				AsmPrinter asmPrinterStdOut = AsmPrinter.makeAsmPrinterStdOut();
				asmPrinterStdOut.visit(spec.asm);
				asmPrinterStdOut.close();
				System.err.println(spec.asm);
				System.err.println("End of ASM printing.");
				e.printStackTrace();
			}
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return new Coverage("ABR", list);
	}
}
