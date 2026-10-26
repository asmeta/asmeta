package asmeta.mutation.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import asmeta.definitions.Function;
import asmeta.structure.Asm;
import asmeta.structure.Header;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.Term;
import java.util.HashMap;
import java.util.Map;

import asmeta.definitions.domains.AnyDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.ProductDomain;

/**
 * Replaces one occurrence of a relational function at a time.
 *
 * Traverses FunctionTerm arguments without modifying the input.
 * Unchanged terms may be shared until the final model copy.
 */
public final class RelationalTermMutator extends TermMutator {

    private final String sourceOperator;
    private final List<String> replacementOperators;

    public RelationalTermMutator(String sourceOperator, List<String> replacementOperators) {
        this.sourceOperator = Objects.requireNonNull(sourceOperator);
        this.replacementOperators = List.copyOf(replacementOperators);
    }


    private Function findReplacement(FunctionTerm expression, String replacementOperator) {
        Function replacement = null;
        Function original = expression.getFunction();

        if (original.getSignature() != null && original.getSignature().eContainer() instanceof Header) {
            EObject owner = original.getSignature().eContainer().eContainer();
            if (owner instanceof Asm && "StandardLibrary".equals(((Asm) owner).getName())) {
                for (Function candidate : original.getSignature().getFunction()) {
                    if (replacement == null && replacementOperator.equals(candidate.getName())
                            && Objects.equals(original.getArity(), candidate.getArity())
                            && EcoreUtil.equals(original.getCodomain(), candidate.getCodomain())
                            && acceptsArguments(candidate, expression)) {
                        replacement = candidate;
                    }
                }
            }
        }

        return replacement;
    }
    
    private boolean acceptsArguments(Function candidate, FunctionTerm expression) {
        boolean compatible = false;

        if (candidate.getDomain() instanceof ProductDomain && expression.getArguments() != null) {
            ProductDomain domain = (ProductDomain) candidate.getDomain();
            List<Domain> parameters = domain.getDomains();
            List<Term> arguments = expression.getArguments().getTerms();
            compatible = parameters.size() == arguments.size();
            Map<String, Domain> bindings = new HashMap<>();
            for (int i = 0; i < parameters.size() && compatible; i++) {
                compatible = matchesDomain(parameters.get(i), arguments.get(i).getDomain(), bindings);
            }
        }

        return compatible;
    }

    private boolean matchesDomain(Domain expected, Domain actual, Map<String, Domain> bindings) {
        boolean compatible = false;

        if (expected != null && actual != null) {
            if (expected instanceof AnyDomain) {
                String parameterName = expected.getName();

                if (bindings.containsKey(parameterName)) {
                    compatible = EcoreUtil.equals(bindings.get(parameterName), actual);
                } else {
                    bindings.put(parameterName, actual);
                    compatible = true;
                }
            } else {
                compatible = EcoreUtil.equals(expected, actual);
            }
        }

        return compatible;
    }
    
    @Override
    protected List<Term> mutateFunction(FunctionTerm term) {
        List<Term> mutants = new ArrayList<>();

        boolean applicable = term.getFunction() != null && sourceOperator.equals(term.getFunction().getName())
                && term.getArguments() != null && term.getArguments().getTerms().size() == 2;
        if (applicable) {
            for (String operator : replacementOperators) {
                if (!sourceOperator.equals(operator)) {
                    Function replacement = findReplacement(term, operator);

                    if (replacement != null) {
                        FunctionTerm mutant = EcoreUtil.copy(term);
                        mutant.setFunction(replacement);
                        mutants.add(mutant);
                    }
                }
            }
        }

        return mutants;
    }
    
}