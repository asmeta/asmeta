package org.asmeta.parser;

import java.util.ArrayList;
import java.util.List;

import org.asmeta.parser.util.DynamicInTermFinder;
import org.eclipse.emf.ecore.EObject;

import asmeta.definitions.DerivedFunction;
import asmeta.definitions.Function;
import asmeta.definitions.StaticFunction;
import asmeta.structure.FunctionDefinition;
import asmeta.terms.basicterms.Term;

/** SingleChecker for checking if a function is defined correctly*/
public class StaticDerivedChecker implements OCLChecker<FunctionDefinition> {

	
	static public StaticDerivedChecker eInstance = new StaticDerivedChecker();
	
	private StaticDerivedChecker() {}
	
	// new constraints 31/10/2024
	// the definition of a static function can contain only static functions
	// while a derived must contain at least one not static
	@Override
	public OCLCheckerResult check(FunctionDefinition f_def) {
		Function fun = f_def.getDefinedFunction();
		if (fun instanceof StaticFunction || fun instanceof DerivedFunction) {
			// get the function body
			Term defBody = f_def.getBody();
			// collect all the terms in the def Body
			List<EObject> notStatic = new ArrayList<>();
			DynamicInTermFinder ns = new DynamicInTermFinder(notStatic);
			ns.visit(defBody);
			if (fun instanceof StaticFunction) {
				if (notStatic.isEmpty())
					return new OCLCheckerResult(true, "no error");
				else {
					return new OCLCheckerResult(false, "Error: static function " + fun.getName()
							+ " contains (dynamic) " + notStatic + " in its definition");
				}
			} else {
				if (!notStatic.isEmpty())
					return new OCLCheckerResult(true, "no error");
				else {
					return new OCLCheckerResult(false, "Error: derived function " + fun.getName()
							+ " does not contain dynamic functions in its definition");
				}
			}

		}
		return new OCLCheckerResult(true, "no static nor derived found");
	}
}
