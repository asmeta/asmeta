package asmeta.asmetal2java.codegen.translator;

import java.util.StringJoiner;
import java.util.function.Function;
import java.util.function.IntFunction;

import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;

/** Central mapping between ASMETA product domains/tuple terms and javatuples. */
public final class ProductToJava {

    private static final String[] TUPLE_CLASSES = {
            null, null, "Pair", "Triplet", "Quartet", "Quintet",
            "Sextet", "Septet", "Octet", "Ennead", "Decade"
    };

    private ProductToJava() {
    }

    /** Returns the exact Java type for a ProductDomain. */
    public static String type(ProductDomain domain, Function<Domain, String> componentType) {
        StringJoiner types = new StringJoiner(", ");
        for (Domain component : domain.getDomains()) {
            types.add(componentType.apply(component));
        }
        return tupleClass(domain.getDomains().size()) + "<" + types + ">";
    }

    /** Returns a public type for ATG generator with wildcards, without exposing component implementation types. */
    public static String wildcardType(ProductDomain domain) {
        int arity = domain.getDomains().size();
        StringJoiner wildcards = new StringJoiner(", ");
        for (int i = 0; i < arity; i++) {
            wildcards.add("?");
        }
        return "org.javatuples." + tupleClass(arity) + "<" + wildcards + ">";
    }

    /** Returns the Java expression that constructs an ASMETA tuple value. */
    public static String value(TupleTerm tuple, Function<Term, String> componentValue) {
        int arity = tuple.getTerms().size();
        StringJoiner values = new StringJoiner(", ");
        for (Term component : tuple.getTerms()) {
            values.add(componentValue.apply(component));
        }
        return tupleClass(arity) + ".with(" + values + ")";
    }

    /** Returns a tuple constructor expression for values already represented in the Java translation. */
    public static String value(ProductDomain domain, IntFunction<String> componentValue) {
        int arity = domain.getDomains().size();
        StringJoiner values = new StringJoiner(", ");
        for (int i = 0; i < arity; i++) {
            values.add(componentValue.apply(i));
        }
        return tupleClass(arity) + ".with(" + values + ")";
    }

    /** Returns a fully-qualified tuple constructor expression. */
    public static String qualifiedValue(ProductDomain domain, IntFunction<String> componentValue) {
        return "org.javatuples." + value(domain, componentValue);
    }

    private static String tupleClass(int arity) {
        if (arity < 2 || arity >= TUPLE_CLASSES.length) {
            throw new IllegalArgumentException(
                    "Product/tuple arity " + arity + " is not supported; expected a value from 2 to 10.");
        }
        return TUPLE_CLASSES[arity];
    }
}
