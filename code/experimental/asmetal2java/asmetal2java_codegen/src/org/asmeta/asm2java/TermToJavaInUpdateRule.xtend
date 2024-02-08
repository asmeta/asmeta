package org.asmeta.asm2java

import asmeta.definitions.ControlledFunction
import asmeta.definitions.Function
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.MapDomain
import asmeta.definitions.domains.ProductDomain
import asmeta.definitions.domains.SequenceDomain
import asmeta.structure.Asm
import asmeta.terms.basicterms.FunctionTerm

/**
 * This class is used in Update Rules
 */
class TermToJavaInUpdateRule extends TermToJava {

	new(Asm resource) {
		this(resource, false)
	}

	new(Asm resource, boolean leftHandSide) {
		super(resource, leftHandSide, "")
	}

	new(Asm resource, boolean leftHandSide, String varName) {
		super(resource, leftHandSide, varName)
	}

	override String visit(FunctionTerm term) {

		var StringBuffer functionTerm = new StringBuffer
		var name = new Util().parseFunction(term.function.name)

		// Controllo se l'operatore » del tipo: &,|,<=,>=,<,>...
		if (!ExpressionToJava.hasEvaluateVisitor(name)) {
			if (term.function instanceof ControlledFunction && term.domain instanceof ConcreteDomain &&
				!(term.function.domain instanceof ProductDomain))
				functionTerm.append(caseFunctionTermSupp(term.function, term))
			if (term.function instanceof ControlledFunction && term.function.domain instanceof ProductDomain &&
				term.domain instanceof ConcreteDomain)
				functionTerm.append(caseFunctionTermSupp(term.function, term))
			if (term.function instanceof ControlledFunction && term.domain instanceof MapDomain)
				functionTerm.append(caseFunctionTermSupp(term.function, term))
			if (term.function instanceof ControlledFunction && term.domain instanceof SequenceDomain)
				functionTerm.append(caseFunctionTermSupp(term.function, term))
			return functionTerm.toString
		}

	}

	// Identifico la tipologia delle variabili e la loro posizione rispetto all'operatore
	override String caseFunctionTermSupp(Function fd, FunctionTerm ft) {

		var StringBuffer functionTerm = new StringBuffer

		if (fd.domain instanceof ProductDomain) {
			functionTerm.append(fd.name + ".set(" + fd.name + "_elem, sup);\n")

		}
		if (fd.domain instanceof SequenceDomain) {
			functionTerm.append(fd.name + ".set(" + fd.name + "_elem);\n")

		}
		if (ft.domain instanceof MapDomain) {
			functionTerm.append(fd.name + ".set(supporto);\n")

		}
		if (ft.domain instanceof SequenceDomain) {
			functionTerm.append(fd.name + ".set(" + fd.name + "_elem);\n")

		}

		if (ft.arguments === null) {
			if (ft.domain instanceof ConcreteDomain)
				// Identifico Dx o Sx
				if (!leftHandSide)
					functionTerm.append(fd.name + ".set(" + ft.domain.name + varName + "_s);")

			return functionTerm.toString

		}

		// Identifico se la funzione dipende da delle variabili in ingresso
		if (ft.arguments !== null) {

			// Caso di studio con una sola variabile
			if (ft.arguments.terms.size == 1) {
				if (ft.domain instanceof ConcreteDomain)
					if (!leftHandSide) {

						functionTerm.append(
							fd.name + ".set(" + visit(ft.arguments.terms.get(0)) + ", " + ft.domain.name + varName +
								"_s);")

					} // Caso di studio con variabili multiple in ingresso
					// da controllare se corretto come metodo
					else {
					}
			}
			return functionTerm.toString
		}
	}
}
