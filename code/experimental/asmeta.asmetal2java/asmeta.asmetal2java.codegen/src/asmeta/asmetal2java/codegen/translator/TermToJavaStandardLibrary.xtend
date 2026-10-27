package asmeta.asmetal2java.codegen.translator

import asmeta.definitions.ControlledFunction
import asmeta.definitions.Function
import asmeta.definitions.MonitoredFunction
import asmeta.definitions.OutFunction
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.MapDomain
import asmeta.structure.Asm
import asmeta.terms.basicterms.FunctionTerm

/**
 * This class is used when implementing operations of the StandardLibrary
 */
class TermToJavaStandardLibrary extends TermToJava {

	new(Asm resource) {
		this(resource, false)
	}

	// Il boolean identifica se il termine » a sx o dx del :=
	new(Asm resource, boolean leftHandSide) {
		super(resource, leftHandSide, "")
	}

	// Identifico la tipologia delle variabili e la loro posizione rispetto all'operatore
	override dispatch String caseFunctionTermSupp(ControlledFunction fd, FunctionTerm ft) {
		return caseControlledOrOutputFunctionSupp(fd, ft)
	}

	override dispatch String caseFunctionTermSupp(OutFunction fd, FunctionTerm ft) {
		return caseControlledOrOutputFunctionSupp(fd, ft)
	}

	private def String caseControlledOrOutputFunctionSupp(Function fd, FunctionTerm ft) {

		var StringBuffer functionTerm = new StringBuffer

		if (ft.arguments === null) {
			// Identifico Dx o Sx
			if (ft.domain instanceof ConcreteDomain) {
				if (!leftHandSide) {
					functionTerm.append(".get()")
				}

			} else if (ft.domain instanceof MapDomain) {

				functionTerm.append(".get()")

			} else {
				if (leftHandSide)
					functionTerm.append(".set(")
				else {
					functionTerm.append(".get()")
				}
			}

		}

		// Identifico se la funzione dipende da delle variabili in ingresso
		if (ft.arguments !== null) {
			val arguments = ArgumentToJava.locationArguments(ft, new TermToJava(res))
			if (leftHandSide) {
				leftHandSide = false
				functionTerm.append(".set(" + arguments + ", ")
			} else
				functionTerm.append(".get(" + arguments + ")")
		}
		return functionTerm.toString
	}

	override dispatch String caseFunctionTermSupp(MonitoredFunction fd, FunctionTerm ft) {
		var StringBuffer functionTerm = new StringBuffer
		if (ft.arguments === null)
			if (leftHandSide){
				functionTerm.append(".set(")
			} else if(fd.codomain instanceof ConcreteDomain){
					functionTerm.append(".get().value")
			} else{
				functionTerm.append(".get()")
			}

		if (ft.arguments !== null) {
			val arguments = ArgumentToJava.locationArguments(ft, new TermToJava(res))
			if (leftHandSide) {
				leftHandSide = false
				functionTerm.append(".set(" + arguments + ", ")
			} else
				functionTerm.append(".get(" + arguments + ")")
		}
		return functionTerm.toString
	}

}
