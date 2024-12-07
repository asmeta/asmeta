package asmeta.mutation.operators;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
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
import asmeta.transitionrules.basictransitionrules.impl.LetRuleImpl;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import asmeta.definitions.domains.StringDomain;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.furtherterms.StringTerm;
import asmeta.transitionrules.basictransitionrules.*;
//import org.asmeta.simulator.util.StdlFunction;

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
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(TermAsRule rule) {
			throw new RuntimeException("not implemented yet");
		}

		@Override
		public List<Rule> visit(BlockRule block) {
			throw new RuntimeException("not implemented yet");
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
			// assuming
			// only 1 variable in choose			
			assert rule.getVariable().size() == 1;
			assert rule.getVariable().size() == 1;

			List<Rule> result = new ArrayList<>();
			// convert to choose rule which does not chose
			ChooseRule cr = BasictransitionrulesFactory.eINSTANCE.createChooseRule();
			cr.getVariable().add(rule.getVariable().get(0));
			// in sequence term {1}
			SetTerm in = asmeta.terms.basicterms.BasictermsFactory.eINSTANCE.createSetTerm();
			cr.setDoRule(rule.getDoRule());
			// set the domain as one element with a random value (integer for now)
			Value value = rndReader.visit(cr.getVariable().get(0).getDomain());
			in.getTerm().add(valtoterm.visit(value));
			cr.getRanges().add(in);
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
			throw new RuntimeException("not implemented yet");
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