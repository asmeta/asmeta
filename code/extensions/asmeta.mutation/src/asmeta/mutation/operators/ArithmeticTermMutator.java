package asmeta.mutation.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.util.EcoreUtil;

import asmeta.definitions.Function;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.NaturalDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.structure.Asm;
import asmeta.structure.Header;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.Term;

public final class ArithmeticTermMutator extends TermMutator{

    private static final List<String> OPERATORS = List.of("plus", "minus", "mult", "div", "idiv", "mod", "pwr");

    private final String sourceOperator;
    private final List<String> replacementOperators;

    public ArithmeticTermMutator(String sourceOperator) {
        this(sourceOperator, OPERATORS);
    }

    public ArithmeticTermMutator(String sourceOperator, List<String> replacementOperators) {

        this.sourceOperator = Objects.requireNonNull(sourceOperator);
        this.replacementOperators = List.copyOf(replacementOperators).stream().distinct().toList();

        if (!OPERATORS.contains(sourceOperator) || !OPERATORS.containsAll(this.replacementOperators)) {
            throw new IllegalArgumentException("Unsupported arithmetic operator");
        }
    }

    @Override
    protected List<Term> mutateFunction(FunctionTerm term) {
        List<Term> mutants = new ArrayList<>();
        Function original = term.getFunction();

        boolean applicable = original != null && sourceOperator.equals(original.getName()) && belongsToStandardLibrary(original)
                && hasNumericSignature(original) && term.getArguments() != null
                && Objects.equals(original.getArity(), term.getArguments().getTerms().size());

        if (applicable) {
            for (String operator : replacementOperators) {
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

    private boolean isNumeric(Domain domain) {
        return domain instanceof IntegerDomain || domain instanceof NaturalDomain || domain instanceof RealDomain;
    }

    private boolean hasNumericSignature(Function function) {
        boolean numeric = false;

        if (isNumeric(function.getCodomain())) {
            if (Objects.equals(function.getArity(), 1)) {
                numeric = isNumeric(function.getDomain());
            } else if (Objects.equals(function.getArity(), 2) && function.getDomain() instanceof ProductDomain product) {
                numeric = product.getDomains().size() == 2 && product.getDomains().stream().allMatch(this::isNumeric);
            }
        }

        return numeric;
    }

    private Function findReplacement(Function original, String operator) {

        Function replacement = null;

        for (Function candidate : original.getSignature().getFunction()) {
            if (replacement == null && operator.equals(candidate.getName()) && hasNumericSignature(candidate)
                    && Objects.equals(original.getArity(), candidate.getArity())
                    && EcoreUtil.equals(original.getDomain(), candidate.getDomain())
                    && EcoreUtil.equals(original.getCodomain(), candidate.getCodomain())) {

                replacement = candidate;
            }
        }

        return replacement;
    }
}