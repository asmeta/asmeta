package asmeta.mutation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.asmeta.simulator.IRuleVisitor;
import org.asmeta.simulator.RuleVisitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import asmeta.AsmCollection;
import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.BasictransitionrulesFactory;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.basictransitionrules.impl.SkipRuleImpl;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;

public class RuleRemover extends AsmetaMutationOperator {

	@Override
	List<AsmCollection> mutatate(AsmCollection asmeta) {
		List<AsmCollection> result = new ArrayList<>();
		// traverse every rule declaration
		EList<RuleDeclaration> rds = asmeta.getMain().getBodySection().getRuleDeclaration();
		for (int i = 0; i < rds.size(); i++) {
			// get the i-the rule in the original ASM 
			Rule rd = rds.get(i).getRuleBody();
			// mutate that rule
			List<Rule> mutRules = r2s.visit(rd);
			// build the new asmetas
			for(Rule mr: mutRules) {
				// clone the original ASM
				AsmCollection asmc = cloneAsmeta(asmeta);
				// set as i-th rule that mutated
				asmc.getMain().getBodySection().getRuleDeclaration().get(i).setRuleBody(mr);
				result.add(asmc);
			}
		}		
		return result;
	}
	// clone the asmeta (it clones only the main ASM)
	private AsmCollection cloneAsmeta(AsmCollection asmeta) {
		List<Asm> newAsms = new ArrayList<>();
		for( Iterator<Asm> i = asmeta.iterator(); i.hasNext();) {
			Asm asm = i.next();
			if (asm == asmeta.getMain())
				newAsms.add(EcoreUtil.copy(asm));
			else
				newAsms.add(asm);
		}
		AsmCollection asmc = new AsmCollection(newAsms);
		return asmc;
	}

	RuleToSkip r2s = new RuleToSkip();
	
	public class RuleToSkip extends  RuleVisitor<List<Rule>>{

		@Override
		public List<Rule> visit(SkipRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(UpdateRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(TermAsRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(BlockRule block) {
			List<Rule> mutatedRules = new ArrayList<>();
			EList<Rule> rules = block.getRules();
			for (int i = 0; i < rules.size(); i++) {
				List<Rule> rulesN = new ArrayList<>(EcoreUtil.copyAll(rules));
				// TODO call recursively
				// create a skip rule (or it can be reused?) to check
				rulesN.set(i,BasictransitionrulesFactory.eINSTANCE.createSkipRule());
				BlockRule newBlock = BasictransitionrulesFactory.eINSTANCE.createBlockRule();
				newBlock.getRules().addAll(rulesN);
				mutatedRules.add(newBlock);
			}
			return mutatedRules;
		}

		@Override
		public List<Rule> visit(SeqRule seq) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(ConditionalRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(ExtendRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(LetRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(ChooseRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(ForallRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(MacroCallRule rule) throws Exception {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(CaseRule rule) {
			throw new RuntimeException("not implemented yet");
		}
		
	}
	
	
}
