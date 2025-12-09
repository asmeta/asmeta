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
import asmeta.terms.basicterms.ConstantTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
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
		
		static RandomMFReader rndReader = new RandomMFReader(nullPrintStream);
		
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

		// The visit return a choose rule that behaves as a let rule:
		// - the guard is set to true
		// - each domain is restricted to a single element domain
		@Override
		public List<Rule> visit(ChooseRule rule) {
			List<Rule> mutated = new ArrayList<>();
			// convert to choose rule which does not chose
			ChooseRule cr = BasictransitionrulesFactory.eINSTANCE.createChooseRule();
			cr.getVariable().addAll(rule.getVariable());
			cr.getRanges().clear();
			// set the domain as one element with a random value for each variable
			for (Term varibale : cr.getVariable()) {
				// get a random value
				Value value = rndReader.visit(varibale.getDomain());
				// convert it to a set term containing only one constant term
				SetTerm in = asmeta.terms.basicterms.BasictermsFactory.eINSTANCE.createSetTerm();
				ConstantTerm domainTerm = valtoterm.visit(value);
				in.getTerm().add(domainTerm);
				// add the set term to the list of ranges
				cr.getRanges().add(in);
			}
			// Keep the same doRule
			Rule doRuleCopy = EcoreUtil.copy(rule.getDoRule());
			cr.setDoRule(doRuleCopy);
			// Put ifnone rule to null (guard always true)
			cr.setIfnone(null);
			// Change the guard to true
			Term guardCopy = EcoreUtil.copy(rule.getGuard());
			cr.setGuard(guardCopy);
			cr.setGuard(BasictermsFactoryImpl.eINSTANCE.createBooleanTerm(true));
			mutated.add(cr);
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