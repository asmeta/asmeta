package asmeta.mutation.operators;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.util.BasictermsSwitch;

/**
 * Generates alternatives containing one mutation each.
 *
 * Traverses function applications and tuples.
 * Unchanged subterms may be shared until the final model copy.
 */
public abstract class TermMutator extends BasictermsSwitch<List<Term>> {

    public List<Term> mutate(Term term) {
        List<Term> mutants = List.of();

        if (term != null) {
            mutants = doSwitch(term);
        }

        return mutants;
    }

    /**
     * Mutates only this function application, without visiting its arguments.
     */
    protected abstract List<Term> mutateFunction(FunctionTerm term);

    @Override
    public List<Term> caseFunctionTerm(FunctionTerm term) {
        List<Term> mutants = new ArrayList<>();

        // Alternatives obtained by mutating this application.
        mutants.addAll(mutateFunction(term));

        // Alternatives obtained by mutating one argument.
        if (term.getArguments() != null) {
            for (Term arguments : mutate(term.getArguments())) {
                FunctionTerm mutant = EcoreUtil.copy(term);
                mutant.setArguments((TupleTerm) arguments);
                mutants.add(mutant);
            }
        }

        return mutants;
    }

    @Override
    public List<Term> caseTupleTerm(TupleTerm term) {
        List<Term> mutants = new ArrayList<>();

        for (int i = 0; i < term.getTerms().size(); i++) {
            Term argument = term.getTerms().get(i);

            for (Term mutatedArgument : mutate(argument)) {
                TupleTerm mutant = EcoreUtil.copy(term);
                mutant.getTerms().set(i, mutatedArgument);
                mutants.add(mutant);
            }
        }

        return mutants;
    }

    @Override
    public List<Term> defaultCase(EObject object) {
        return List.of();
    }
}