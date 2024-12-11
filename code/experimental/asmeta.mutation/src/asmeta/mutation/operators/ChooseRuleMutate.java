package asmeta.mutation.operators;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.asmeta.simulator.RuleVisitor;
import org.asmeta.simulator.readers.RandomMFReader;
import org.asmeta.simulator.value.Value;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

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
import asmeta.transitionrules.basictransitionrules.impl.LetRuleImpl;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import asmeta.definitions.domains.StringDomain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.ConstantTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.furtherterms.StringTerm;
import asmeta.transitionrules.basictransitionrules.*;
//import org.asmeta.simulator.util.StdlFunction;
import asmeta.AsmetaFactory;

public class ChooseRuleMutate extends RuleBasedMutator {

	public ChooseRuleMutate() {
		super(new ChoseRuleToLet());
		
	}

	static public class ChoseRuleToLet extends RuleVisitor<List<Rule>> {
		
		static NullPrintStream nullPrintStream = new NullPrintStream();
		
		static RandomMFReader rndReader = new RandomMFReader(nullPrintStream);
		
		static ValueToTerm valtoterm = new ValueToTerm();

		@Override
		public List<Rule> visit(SkipRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(UpdateRule rule) {
			return Collections.singletonList(rule);
		}

		@Override
		public List<Rule> visit(TermAsRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(BlockRule block) {
			List<Rule> mutatedRules = visitAll(block.getRules());
			if (mutatedRules.equals(block.getRules())) return Collections.singletonList(block);
			// build teh new block rule
			BlockRule br = AsmetaFactory.eINSTANCE.getTransitionRules().getBasicTransitionRules().createBlockRule();
			br.getRules().addAll(mutatedRules);
			return Collections.singletonList(br);
		}

		// mutate every rule in the list and build the list of mutations
		// TODO mutate and then take the combination of mutants
		// TODO works only if the mutation is 1
		private List<Rule> visitAll(EList<Rule> rules) {
			List<Rule> mutated = new ArrayList<>();
			for (Rule er: rules) {
				List<Rule> m = visit(er);
				if (m.size() == 1) {
					mutated.add(m.get(0));
				} else {
					throw new RuntimeException("not implemented yet");					
				}
			}
			return mutated;
		}

		@Override
		public List<Rule> visit(SeqRule seq) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(ConditionalRule rule) {
			Rule thenRule = rule.getThenRule();
			Rule elseRule = rule.getElseRule();
			List<Rule> ifm = visit(thenRule);
			List<Rule> thenm = elseRule != null ? visit(elseRule) : null;
			// assuming only 1 mutation
			if (ifm.size() == 1 && (thenm== null || thenm.size() == 1)) {
				// if the mutation did not change
				// identical
				if (ifm.get(0) == thenRule && 
						// both null
						((thenm == null && elseRule == null) ||
								(thenm.get(0) ==elseRule)))
								return Collections.singletonList(rule);
				else {
					// changed
					ConditionalRule cr = AsmetaFactory.eINSTANCE.getTransitionRules().getBasicTransitionRules().createConditionalRule();
					cr.setThenRule(ifm.get(0));
					cr.setElseRule(thenm != null? thenm.get(0): null);
					cr.setGuard(rule.getGuard());
					return Collections.singletonList(cr);
				}
						
			}
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
			// assuming
			// only 1 variable in choose			
			assert rule.getVariable().size() == 1;
			assert rule.getVariable().size() == 1;

			List<Rule> result = new ArrayList<>();
			// convert to choose rule which does not chose
			ChooseRule cr = BasictransitionrulesFactory.eINSTANCE.createChooseRule();
			cr.getVariable().add(rule.getVariable().get(0));
			// set the domain as one element with a random value 
			// get a random value
			Value value = rndReader.visit(cr.getVariable().get(0).getDomain());
			System.err.println(value);
			// as a set term
			SetTerm in = asmeta.terms.basicterms.BasictermsFactory.eINSTANCE.createSetTerm();
			ConstantTerm domainTerm = valtoterm.visit(value);
			System.err.println(domainTerm);
			in.getTerm().add(domainTerm);
			System.err.println(cr.getVariable().get(0).getDomain());
			System.err.println(in);
			System.err.println(in.getTerm().get(0));
			cr.getRanges().clear();
			cr.getRanges().add(in);
			// mutate also what's inside the choose rule
			List<Rule> visit = visit(rule.getDoRule());
			assert visit.size() == 1;
			cr.setDoRule(visit.get(0));
			// FunctionTerm eq = std.eq(rule.getVariable().get(0),i);
			// report the guard as it is in the original rule
			cr.setGuard(rule.getGuard());
			//
			result.add(cr);
			return result;

		}

		@Override
		public List<Rule> visit(ForallRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(MacroCallRule rule) throws Exception {
			// do not change
			return Collections.singletonList(EcoreUtil.copy(rule));
		}

		@Override
		public List<Rule> visit(CaseRule rule) {
			throw new RuntimeException("not implemented yet");
		}

	}
	static private class NullPrintStream extends PrintStream {

		private NullPrintStream() {
			super(new NullByteArrayOutputStream());
		}
	}

	static private class NullByteArrayOutputStream extends ByteArrayOutputStream {

		@Override
		public void write(int b) {
			// do nothing
		}

		@Override
		public void write(byte[] b, int off, int len) {
			// do nothing
		}

		@Override
		public void writeTo(OutputStream out) throws IOException {
			// do nothing
		}

	}
	
}