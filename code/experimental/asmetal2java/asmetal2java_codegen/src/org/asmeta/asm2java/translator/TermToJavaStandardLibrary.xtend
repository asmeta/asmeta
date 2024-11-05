package org.asmeta.asm2java.translator

import asmeta.definitions.ControlledFunction
import asmeta.definitions.MonitoredFunction
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.BasicTd
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.domains.MapDomain
import asmeta.structure.Asm
import asmeta.terms.basicterms.ConstantTerm
import asmeta.terms.basicterms.FunctionTerm
import asmeta.terms.basicterms.LocationTerm
import asmeta.definitions.domains.StructuredTd
import asmeta.definitions.domains.ProductDomain

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

		var StringBuffer functionTerm = new StringBuffer

		if (ft.arguments === null) {
			// Identifico Dx o Sx
			if (ft.domain instanceof ConcreteDomain) {
				if (!leftHandSide) {
					functionTerm.append(".get()")
				}

			} else if (ft.domain instanceof MapDomain) {

				functionTerm.append("")

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

			// Caso di studio con una sola variabile
			if (ft.arguments.terms.size == 1) {

				if (ft.domain instanceof ConcreteDomain) {
					if (!leftHandSide) {

						functionTerm.append(".get(" + visit(ft.arguments.terms.get(0)) + ")")
					}

				} else {

					if (leftHandSide) {
						leftHandSide = false
						functionTerm.append(".set(" + visit(ft.arguments.terms.get(0)) + ", ")

					} else {
						if (ft.arguments.terms.get(0) instanceof ConstantTerm && !((ft.arguments.eContainer as LocationTerm).function.domain instanceof EnumTd))
							functionTerm.append(".get(" + (ft.arguments.eContainer as LocationTerm).function.domain.name + ".valueOf(" + visit(ft.arguments.terms.get(0)) + "))"
							)
						else {
							if (fd.domain instanceof ConcreteDomain && (fd.domain as ConcreteDomain).typeDomain instanceof BasicTd) {
								var String visitedFunction = visit(ft.arguments.terms.get(0))
								functionTerm.append(".get(" +fd.domain.name + ".valueOf(" + visitedFunction + "))")
							} else {
								functionTerm.append(".get(" + visit(ft.arguments.terms.get(0)) + ")")
							}
						}
					}

				}
			} // Caso di studio con variabili multiple in ingresso
			// da controllare se corretto come metodo
			else {
				// functionTerm.append("[make_tuple(")
			}
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
			if (ft.arguments.terms.size == 1) {
				// functionTerm.append("[" + visit(ft.arguments.terms.get(0)) + "]")
				if (leftHandSide) {
					leftHandSide = false
					functionTerm.append(".set(" + visit(ft.arguments.terms.get(0)) + ", ")

				} else {
					functionTerm.append(".get(" + visit(ft.arguments.terms.get(0)) + ")")
				}

			} else {
				functionTerm.append("[make_tuple(")
				for (var i = 0; i < ft.arguments.terms.size; i++)
					functionTerm.append(visit(ft.arguments.terms.get(i)) + ", ")
				functionTerm = new StringBuffer(functionTerm.substring(0, functionTerm.length - 2) + ")]")
			}
		}
		return functionTerm.toString
	}

	override dispatch String caseFunctionTermSupp(StaticFunction fd, FunctionTerm ft) {
		var StringBuffer functionTerm = new StringBuffer
		if (ft.arguments !== null) {
			functionTerm.append("(")
			for (var i = 0; i < ft.arguments.terms.size; i++) {
				var String param = visit(ft.arguments.terms.get(i))
				if (ft.function.domain instanceof StructuredTd) {
					// The domain is structured. Check whether the i-th parameter has the domain as expected
					if ((ft.function.domain instanceof ProductDomain)) {
						if ((ft.function.domain as ProductDomain).domains.get(i).equals(ft.arguments.terms.get(i).domain))
							param = param.replaceAll("\\.value","")	
					}					
				} else {
					// The domain is not structured. Check whether the parameter has the domain as expected
					if (ft.domain.equals(ft.arguments.terms.get(i).domain))
							param = param.replaceAll("\\.value","")	
				}
				functionTerm.append(param + ", ")
			}
			functionTerm = new StringBuffer(functionTerm.substring(0, functionTerm.length - 2) + ")")
		} else if (ft.domain instanceof AbstractTd) {
			functionTerm.append("")
		} else {
			functionTerm.append("()")
		}
		return functionTerm.toString
	}

}
