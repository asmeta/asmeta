package asmeta.mutation.operators;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.asmeta.simulator.Environment;
import org.asmeta.simulator.RuleVisitor;
import org.asmeta.simulator.State;
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
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import asmeta.definitions.domains.Domain;
import asmeta.terms.basicterms.ConstantTerm;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.basicterms.impl.BasictermsFactoryImpl;
import asmeta.transitionrules.basictransitionrules.*;
//import org.asmeta.simulator.util.StdlFunction;

//
// it mutates a ChooseRule into a LetRule in which the chosen element is randomly fixed
// (from non deterministic behavior to deterministic) 
//
public class ChooseRuleMutator extends RuleBasedMutator {

	public ChooseRuleMutator() {
		super(new RuleVisitorAdapter(new ChoseRuleToLet()));
	}

	// changes a choose rule to a let rule
	static public class ChoseRuleToLet extends RuleVisitor<List<Rule>> {
		
		static NullPrintStream nullPrintStream = new NullPrintStream();
		
		static RandomMFReader rndReader = new RandomMFReader(new State(null, new Environment(null)), nullPrintStream);
		
		static ValueToTerm valtoterm = new ValueToTerm();

		@Override
		public List<Rule> visit(SkipRule rule) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(UpdateRule rule) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(TermAsRule rule) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(BlockRule block) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(SeqRule seq) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(ConditionalRule rule) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(ExtendRule rule) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(LetRule rule) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(ChooseRule rule) {
			List<Rule> mutated = new ArrayList<>();
			EList<Term> chooseRanges = rule.getRanges();
			EList<VariableTerm> chooseVariables = rule.getVariable();
			// Create an empty let to be populated
			LetRule lr = BasictransitionrulesFactory.eINSTANCE.createLetRule();
			// For each range, get a random element
			List<ConstantTerm> initTerms = new ArrayList<>();
			for (int i = 0; i < chooseRanges.size(); i++) {
				Term range = chooseRanges.get(i);
				ConstantTerm randomTerm;
				if (range instanceof SetTerm st) {
					randomTerm = rndReader.visit(st);
				} else {
					Value randomValue = rndReader.visit(chooseVariables.get(i).getDomain());
					randomTerm = valtoterm.visit(randomValue);
				}
				initTerms.add(randomTerm);
			}
			// Populate the let with same variables, same in rule, and the random terms
			lr.getVariable().addAll(chooseVariables);
			lr.getInitExpression().addAll(initTerms);
			lr.setInRule(EcoreUtil.copy(rule.getDoRule()));
			mutated.add(lr);
			return mutated;
		}

		@Override
		public List<Rule> visit(ForallRule rule) {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(MacroCallRule rule) throws Exception {
			return Collections.EMPTY_LIST;
		}

		@Override
		public List<Rule> visit(CaseRule rule) {
			return Collections.EMPTY_LIST;
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