/**
 * Value.java
 *
 * Created on 22/ago/06, 10:44:37
 *
 */
package org.asmeta.simulator.value;

import org.asmeta.parser.util.AsmetaTermPrinter;
import org.asmeta.simulator.TermEvaluator;

import asmeta.definitions.Function;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.structure.FunctionDefinition;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.UndefTerm;

/**
 * The super class of all values. The subclasses act as wrappers for the
 * corresponding Java implementations.
 *
 * @param <T> the generic type representing the value type in Java
 */
public abstract class Value<T> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public abstract Value<T> clone();

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public abstract T getValue();

	// in case one wants to build a value with a lazy evaluation
	// build the value with
	public static Value lazy(Term term, final TermEvaluator termEvaluator) {
		if (term.getDomain() instanceof BooleanDomain) {			
			// it could be undef in any case
			// simplest case: static function Boolean defined as UNDEF
			// like 
			// static ub : Boolean
			// function ub = undef
			if (term instanceof FunctionTerm) {
				FunctionTerm ft = (FunctionTerm) term;
				if (ft.getFunction() instanceof StaticFunction) {
					StaticFunction sf = (StaticFunction) ft.getFunction();
					FunctionDefinition definition = sf.getDefinition();
					if (definition!= null && definition.getBody() instanceof UndefTerm)
						return UndefValue.UNDEF;
				}
			}
			if (term instanceof LocationTerm) {
				LocationTerm ft = (LocationTerm) term;
				System.err.println("***" + ft.getFunction().getName());
			}
			return new BooleanValue() {
				// the evaluator to be used to evaluate
				@Override
				public Boolean getValue() {
					if (boolValue == null) {
						Value v = termEvaluator.visit(term);
						if (!(v instanceof BooleanValue )) {
							AsmetaTermPrinter ap = AsmetaTermPrinter.getAsmetaTermPrinter(false);
							throw new RuntimeException("this term "+ ap.visit(term) + " is not a boolean value but " + v.getClass() + ": disable lazy evaluation" + "term class " + term.getClass());
						}
						boolValue = ((BooleanValue)v).getValue();
					}
					return boolValue;
				}
			};
		}
		// no lazy eval is possible
	return termEvaluator.visit(term);
}}
