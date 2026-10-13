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
public final class RelationalTermMutator {

    private final String sourceOperator;
    private final List<String> replacementOperators;

    public RelationalTermMutator(String sourceOperator, List<String> replacementOperators) {
        this.sourceOperator = Objects.requireNonNull(sourceOperator);
        this.replacementOperators = List.copyOf(replacementOperators);
    }

    public List<Term> mutate(Term expression) {
        List<Term> mutants = new ArrayList<>();
        List<List<Integer>> paths = new ArrayList<>();

        collect(expression, new ArrayList<>(), paths);

        for (List<Integer> path : paths) {
            FunctionTerm original = at(expression, path);

            for (String operator : replacementOperators) {
                if (!sourceOperator.equals(operator)) {
                	Function replacement = findReplacement(original, operator);
                    if (replacement != null) {
                        Term mutant = replace(expression, path, 0, replacement);

                        mutants.add(mutant);
                    }
                }
            }
        }

        return mutants;
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
    
    private void collect(Term term, List<Integer> path, List<List<Integer>> paths) {

        if (term instanceof FunctionTerm) {
            FunctionTerm functionTerm = (FunctionTerm) term;

            if (functionTerm.getFunction() != null && sourceOperator.equals(functionTerm.getFunction().getName())
                    && functionTerm.getArguments() != null
                    && functionTerm.getArguments().getTerms().size() == 2) {

                paths.add(new ArrayList<>(path));
            }

            if (functionTerm.getArguments() != null) {
                List<Term> arguments = functionTerm.getArguments().getTerms();

                for (int i = 0; i < arguments.size(); i++) {
                    path.add(i);
                    collect(arguments.get(i), path, paths);
                    path.remove(path.size() - 1);
                }
            }
        }
    }

    private FunctionTerm at(Term root, List<Integer> path) {
        FunctionTerm current = (FunctionTerm) root;

        for (int index : path) {
            current = (FunctionTerm) current.getArguments().getTerms().get(index);
        }

        return current;
    }

    private Term replace(Term term, List<Integer> path, int depth, Function replacement) {

        FunctionTerm result = EcoreUtil.copy((FunctionTerm) term);

        if (depth == path.size()) {
            result.setFunction(replacement);
        } else {
            int index = path.get(depth);
            Term child = result.getArguments().getTerms().get(index);

            Term mutatedChild = replace(child, path, depth + 1, replacement);

            result.getArguments().getTerms().set(index, mutatedChild);
        }

        return result;
    }

    /*private Function findReplacement(Function original, String replacementOperator) {

        Function replacement = null;

        if (original.getSignature() != null && original.getSignature().eContainer() instanceof Header) {

            EObject owner = original.getSignature().eContainer().eContainer();

            if (owner instanceof Asm && "StandardLibrary".equals(((Asm) owner).getName())) {

                for (Function candidate : original.getSignature().getFunction()) {

                    if (replacementOperator.equals(candidate.getName())
                            && Objects.equals(
                                    original.getArity(),
                                    candidate.getArity())
                            && EcoreUtil.equals(
                                    original.getDomain(),
                                    candidate.getDomain())
                            && EcoreUtil.equals(
                                    original.getCodomain(),
                                    candidate.getCodomain())) {

                        replacement = candidate;
                    }
                }
            }
        }

        return replacement;
    }*/
}