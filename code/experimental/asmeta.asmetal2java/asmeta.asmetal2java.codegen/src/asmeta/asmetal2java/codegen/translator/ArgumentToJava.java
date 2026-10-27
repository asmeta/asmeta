package asmeta.asmetal2java.codegen.translator;

import java.util.List;
import java.util.StringJoiner;

import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.SequenceTerm;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;

/** Converts each argument to the domain declared for its parameter. */
public final class ArgumentToJava {
    private ArgumentToJava() {
    }

    public static String macroArguments(MacroCallRule call, TermToJava translator) {
        return arguments(call.getParameters(), call.getCalledMacro().getVariable().stream()
                .map(variable -> variable.getDomain()).toList(), translator);
    }

    public static String arguments(List<Term> arguments, List<? extends Domain> domains, TermToJava translator) {
        if (arguments.size() != domains.size()) {
            throw new IllegalArgumentException("Expected one declared domain per argument");
        }
        StringJoiner result = new StringJoiner(", ");
        for (int i = 0; i < arguments.size(); i++) {
            result.add(value(arguments.get(i), domains.get(i), translator));
        }
        return result.toString();
    }

    public static String functionArguments(FunctionTerm term, TermToJava translator) {
        Domain domain = term.getFunction().getDomain();
        List<Term> arguments = term.getArguments().getTerms();
        List<? extends Domain> domains = arguments.size() == 1 ? List.of(domain)
                : ((ProductDomain) domain).getDomains();
        return arguments(arguments, domains, translator);
    }

    public static String locationArguments(FunctionTerm term, TermToJava translator) {
        if (term.getArguments().getTerms().size() == 1) {
            return functionArguments(term, translator);
        }
        ProductDomain domain = (ProductDomain) term.getFunction().getDomain();
        return ProductToJava.value(domain,
                i -> value(term.getArguments().getTerms().get(i), domain.getDomains().get(i), translator));
    }

    public static String value(Term term, Domain expected, TermToJava translator) {
        if (term instanceof UndefTerm) {
            return "(" + Util.javaType(expected, translator.res) + ") null";
        }
        if (term instanceof ConditionalTerm conditional) {
            return "(" + translator.visit(conditional.getGuard()) + " ? "
                    + value(conditional.getThenTerm(), expected, translator) + " : "
                    + (conditional.getElseTerm() == null ? "null"
                            : value(conditional.getElseTerm(), expected, translator)) + ")";
        }
        if (term instanceof CaseTerm cases) {
            String result = cases.getOtherwiseTerm() == null ? "null"
                    : value(cases.getOtherwiseTerm(), expected, translator);
            for (int i = cases.getResultTerms().size() - 1; i >= 0; i--) {
                result = "(java.util.Objects.equals(" + translator.visit(cases.getComparedTerm()) + ", "
                        + translator.visit(cases.getComparingTerm().get(i)) + ") ? "
                        + value(cases.getResultTerms().get(i), expected, translator) + " : " + result + ")";
            }
            return result;
        }
        if (term instanceof TupleTerm tuple && expected instanceof ProductDomain product) {
            return ProductToJava.value(product, i -> value(tuple.getTerms().get(i), product.getDomains().get(i), translator));
        }
        if (term instanceof SequenceTerm sequence && expected instanceof SequenceDomain domain) {
            return "new java.util.ArrayList<>(java.util.Arrays.asList(" + elements(sequence.getTerms(), domain.getDomain(), translator) + "))";
        }
        if (term instanceof SetTerm set && expected instanceof PowersetDomain domain) {
            return "new java.util.HashSet<>(java.util.Arrays.asList(" + elements(set.getTerm(), domain.getBaseDomain(), translator) + "))";
        }

        String expression = translator.visit(term).trim();
        boolean wrapper = term.getDomain() instanceof ConcreteDomain;
        if (term instanceof FunctionTerm function && Util.isControlledOrOut(function.getFunction())) {
            wrapper = false;
        }
        String normalized = wrapper ? term.getDomain().getName() + ".valueOf(" + expression + ")" : expression;
        if (expected instanceof ConcreteDomain concrete) {
            if (!(term.getDomain() instanceof ConcreteDomain)) {
                expression = value(term, concrete.getTypeDomain(), translator);
            }
            if (wrapper && !expected.equals(term.getDomain())) {
                expression = unwrap(normalized);
            }
            return concrete.getName() + ".valueOf(" + expression + ")";
        }
        return wrapper ? unwrap(normalized) : expression;
    }

    private static String unwrap(String expression) {
        return "java.util.Optional.ofNullable(" + expression + ").map(__asmetaValue -> __asmetaValue.value).orElse(null)";
    }

    private static String elements(List<Term> terms, Domain domain, TermToJava translator) {
        StringJoiner result = new StringJoiner(", ");
        for (Term term : terms) {
            result.add(value(term, domain, translator));
        }
        return result.toString();
    }
}
