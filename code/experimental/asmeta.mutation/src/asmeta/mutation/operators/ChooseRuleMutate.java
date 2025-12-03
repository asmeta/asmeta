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
import asmeta.transitionrules.basictransitionrules.*;
//import org.asmeta.simulator.util.StdlFunction;

//
// it mutates a ChooseRule into a LetRule in which the chosen element is randomly fixed
// (from non deterministic behavior to deterministic) 
//
public class ChooseRuleMutate extends RuleBasedMutator {

	public ChooseRuleMutate() {
		super(new ChoseRuleToLet());
		
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
			//System.err.println(value);
			// as a set term
			SetTerm in = asmeta.terms.basicterms.BasictermsFactory.eINSTANCE.createSetTerm();
			ConstantTerm domainTerm = valtoterm.visit(value);
			in.getTerm().add(domainTerm);
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