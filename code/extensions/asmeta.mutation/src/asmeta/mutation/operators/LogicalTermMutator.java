package asmeta.mutation.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.util.EcoreUtil;

import asmeta.definitions.Function;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.structure.Asm;
import asmeta.structure.Header;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.Term;

public final class LogicalTermMutator extends TermMutator {

    private static final List<String> BINARY_OPERATORS = List.of("and", "xor", "or", "implies", "iff");

    private final String sourceOperator;

    public LogicalTermMutator(String sourceOperator) {
        this.sourceOperator = Objects.requireNonNull(sourceOperator);

        if (!"not".equals(sourceOperator) && !BINARY_OPERATORS.contains(sourceOperator)) {
            throw new IllegalArgumentException("Unsupported logical operator: " + sourceOperator);
        }
    }

    @Override
    protected List<Term> mutateFunction(FunctionTerm term) {
        List<Term> mutants = new ArrayList<>();
        Function original = term.getFunction();

        int arity = "not".equals(sourceOperator) ? 1 : 2;

        boolean applicable = original != null && sourceOperator.equals(original.getName()) && belongsToStandardLibrary(original)
                && hasBooleanSignature(original, arity) && term.getArguments() != null && term.getArguments().getTerms().size() == arity;

        if (applicable) {
            if (arity == 1) {
                Term operand = term.getArguments().getTerms().get(0);
                mutants.add(EcoreUtil.copy(operand));
            } else {
                for (String operator : BINARY_OPERATORS) {
                    if (!sourceOperator.equals(operator)) {
                        Function replacement = findReplacement(original, operator);
                        if (replacement != null) {
                            FunctionTerm mutant = EcoreUtil.copy(term);
                            mutant.setFunction(replacement);
                            mutants.add(mutant);
                        }
                    }
                }
            }
        }
        return mutants;
    }

    private boolean belongsToStandardLibrary(Function function) {
        boolean standard = false;
        if (function.getSignature() != null && function.getSignature().eContainer() instanceof Header header
                && header.eContainer() instanceof Asm asm) {
            standard = "StandardLibrary".equals(asm.getName());
        }
        return standard;
    }

    private boolean hasBooleanSignature(Function function, int arity) {
        boolean compatible = Objects.equals(function.getArity(), arity) && function.getCodomain() instanceof BooleanDomain;
        if (compatible) {
            if (arity == 1) {
                compatible = function.getDomain() instanceof BooleanDomain;
            } else {
                compatible = function.getDomain() instanceof ProductDomain product && product.getDomains().size() == 2
                        && product.getDomains().stream().allMatch(domain -> domain instanceof BooleanDomain);
            }
        }
        return compatible;
    }

    private Function findReplacement(Function original, String operator) {
        Function replacement = null;
        for (Function candidate : original.getSignature().getFunction()) {
            if (replacement == null && operator.equals(candidate.getName()) && hasBooleanSignature(candidate, 2)
                    && EcoreUtil.equals(original.getDomain(), candidate.getDomain())
                    && EcoreUtil.equals(original.getCodomain(), candidate.getCodomain())) {
                replacement = candidate;
            }
        }
        return replacement;
    }
}