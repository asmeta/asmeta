package asmeta.mutation.operators;

import java.util.ArrayList;
import java.util.List;

import org.asmeta.simulator.RuleVisitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import asmeta.AsmCollection;
import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.Rule;

public abstract class RuleBasedMutator extends AsmetaMutationOperator {

	private RuleVisitor<List<Rule>> ruleVisitor;

	RuleBasedMutator(RuleVisitor<List<Rule>> rulevisitor) {
		this.ruleVisitor = rulevisitor;
	}

	@Override
	public final List<AsmCollection> mutate(AsmCollection asmeta) {
		List<AsmCollection> result = new ArrayList<>();
		// traverse every rule declaration
		EList<RuleDeclaration> rds = asmeta.getMain().getBodySection().getRuleDeclaration();
		for (int i = 0; i < rds.size(); i++) {
			// get the i-the rule in the original ASM
			Rule rd = rds.get(i).getRuleBody();
			// mutate that rule
			List<Rule> mutRules = ruleVisitor.visit(rd);
			// build the new asmetas
			for (Rule mr : mutRules) {
				if (mr != rd) {
					// only if it differs
					// clone the original ASM
					AsmCollection asmc = cloneAsmeta(asmeta);
					// set as i-th rule that mutated
					asmc.getMain().getBodySection().getRuleDeclaration().get(i).setRuleBody(mr);
					result.add(asmc);
				} else {
					//TODO metter eil log
					System.err.println("rule is not mutated - skip");
				}
			}
		}
		return result;
	}

	// clone the asmeta (it clones only the main ASM)
	private AsmCollection cloneAsmeta(AsmCollection asmeta) {
		List<Asm> newAsms = new ArrayList<>();
		for (Asm asm : asmeta) {
			if (asm == asmeta.getMain()) {
				newAsms.add(EcoreUtil.copy(asm));
			} else {
				newAsms.add(asm);
			}
		}
		return new AsmCollection(newAsms);
	}

}
