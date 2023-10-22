package asmeta.mutation;

import java.util.ArrayList;
import java.util.List;

import org.asmeta.simulator.IRuleVisitor;
import org.asmeta.simulator.RuleVisitor;
import org.eclipse.emf.common.util.EList;

import asmeta.AsmCollection;
import asmeta.definitions.RuleDeclaration;
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
		for(RuleDeclaration rd : rds) {
			List<Rule> mutRules = r2s.visit(rd.getRuleBody());
			// build the new asmetas
			for(Rule mr: mutRules) {
				// clone TODO
				rd.setRuleBody(mr);
				result.add(asmeta);
			}			
		}		
		return result;
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
				// TODO call recursively
				// create a skip rule (or it can be reused?) to check
				rules.set(i,BasictransitionrulesFactory.eINSTANCE.createSkipRule());
				BlockRule newBlock = BasictransitionrulesFactory.eINSTANCE.createBlockRule();
				newBlock.getRules().addAll(rules);
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
