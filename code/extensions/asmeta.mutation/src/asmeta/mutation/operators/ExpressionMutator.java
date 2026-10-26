package asmeta.mutation.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.asmeta.simulator.RuleVisitor;
import org.eclipse.emf.ecore.util.EcoreUtil;

import asmeta.AsmCollection;
import asmeta.terms.basicterms.Term;
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

public class ExpressionMutator extends RuleBasedMutator {

    public ExpressionMutator(TermMutator termMutator) {
        super(new RuleVisitorAdapter(new MutateExpressions(termMutator)));
    }

    @Override
    protected AsmCollection buildMutant(AsmCollection original, int ruleIndex, Rule mutatedRule) {
        MutationModelCopier copier = new MutationModelCopier();
        return copier.copyMutant(original, ruleIndex, mutatedRule);
    }

    public static class MutateExpressions extends RuleVisitor<List<Rule>> {

        private final TermMutator termMutator;

        public MutateExpressions(TermMutator termMutator) {
            this.termMutator = termMutator;
        }

        @Override
        public List<Rule> visit(ConditionalRule rule) {
            List<Rule> mutants = new ArrayList<>();

            for (Term guard : termMutator.mutate(rule.getGuard())) {
                ConditionalRule mutant = EcoreUtil.copy(rule);
                mutant.setGuard(guard);
                mutants.add(mutant);
            }

            return mutants;
        }

        @Override
        public List<Rule> visit(UpdateRule rule) {
            List<Rule> mutants = new ArrayList<>();

            for (Term value : termMutator.mutate(rule.getUpdatingTerm())) {
                UpdateRule mutant = EcoreUtil.copy(rule);
                mutant.setUpdatingTerm(value);
                mutants.add(mutant);
            }

            return mutants;
        }

        @Override
        public List<Rule> visit(SkipRule rule) {
            return Collections.emptyList();
        }

        @Override
        public List<Rule> visit(TermAsRule rule) {
            return Collections.emptyList();
        }

        @Override
        public List<Rule> visit(BlockRule rule) {
            return Collections.emptyList();
        }

        @Override
        public List<Rule> visit(SeqRule rule) {
            return Collections.emptyList();
        }

        @Override
        public List<Rule> visit(ExtendRule rule) {
            return Collections.emptyList();
        }

        @Override
        public List<Rule> visit(LetRule rule) {
            return Collections.emptyList();
        }

        @Override
        public List<Rule> visit(ChooseRule rule) {
            List<Rule> mutants = new ArrayList<>();

            for (Term guard : termMutator.mutate(rule.getGuard())) {
                ChooseRule mutant = EcoreUtil.copy(rule);
                mutant.setGuard(guard);
                mutants.add(mutant);
            }

            return mutants;
        }


        @Override
        public List<Rule> visit(ForallRule rule) {
            List<Rule> mutants = new ArrayList<>();

            for (Term guard : termMutator.mutate(rule.getGuard())) {
                ForallRule mutant = EcoreUtil.copy(rule);
                mutant.setGuard(guard);
                mutants.add(mutant);
            }

            return mutants;
        }

        @Override
        public List<Rule> visit(MacroCallRule rule) throws Exception {
            return Collections.emptyList();
        }

        @Override
        public List<Rule> visit(CaseRule rule) {
            return Collections.emptyList();
        }
    }
}