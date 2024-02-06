package org.asmeta.asm2java

import asmeta.structure.Asm
import asmeta.terms.basicterms.BooleanTerm
import asmeta.terms.basicterms.FunctionTerm
import asmeta.terms.basicterms.LocationTerm
import asmeta.terms.basicterms.VariableTerm
import asmeta.terms.furtherterms.EnumTerm
import asmeta.terms.furtherterms.IntegerTerm
import asmeta.terms.furtherterms.NaturalTerm
import asmeta.terms.furtherterms.StringTerm
import org.asmeta.parser.util.ReflectiveVisitor
import asmeta.definitions.ControlledFunction
import asmeta.definitions.MonitoredFunction
import asmeta.definitions.DerivedFunction
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.Domain
import asmeta.definitions.Function
import asmeta.definitions.domains.MapDomain
import asmeta.terms.basicterms.ConstantTerm
import asmeta.definitions.domains.EnumTd

class TermToJavaSupportoConfronto extends ReflectiveVisitor<String> {

	package Integer numStaticParam
	Asm res
	boolean leftHandSide

	new(Asm resource) {
		this(resource, false)
	}

	// Il boolean identifica se il termine » a sx o dx del :=
	new(Asm resource, boolean leftHandSide) {
		this.res = resource
		this.leftHandSide = leftHandSide
	}

	def String visit(VariableTerm term) {
		if (term.domain instanceof ConcreteDomain)
			return term.name + ".value"
		else
			return term.name
	}

	/** TODO: DELETE FOR COVERAGE 
	 * def String visit(ComplexTerm term) {
	 * 	return "TODO"
	 * }
	 * 
	 * def String visit(CharTerm term) {
	 * 	return term.symbol
	 * }
	 * 
	 * 

	 * def String visit(RealTerm term) {
	 * 	return term.symbol
	 * }
	 */
	def String visit(IntegerTerm term) {
		return term.symbol
	}

	def String visit(NaturalTerm term) {
		return term.symbol.substring(0, term.symbol.length - 1)
	}

	def String visit(StringTerm term) {

		var supp = new String('"');
		return supp + term.symbol + supp
	}

	def String visit(BooleanTerm term) {
		return term.symbol
	}

	/** TODO: DELETE FOR COVERAGE 	def String visit(UndefTerm term) {
	 * 		throw new Exception("Undefined term not supported");
	 }*/
	def String visit(EnumTerm term) {
		return term.domain.name + "." + term.symbol
	}

	// Metodo richiamato in presenza di due termini ed un operatore da identificare
	def String visit(LocationTerm term) {
		return visit(term as FunctionTerm)
	/*if (term.arguments)
	 * 	return visit(term as FunctionTerm)
	 * else
	 return (term.function.name + "(" + visit(term.arguments)+")")*/
	}

	// Metodo per controllare il tipo di operatore(Evaluation) o se si tratta di :=
	def String visit(FunctionTerm term) {

		var StringBuffer functionTerm = new StringBuffer
		var name = new Util().parseFunction(term.function.name)

		// Controllo se l'operatore » del tipo: &,|,<=,>=,<,>...
		if (ExpressionToJava.hasEvaluateVisitor(name)) {
			// if the funcion is an expression
			return new ExpressionToJava(res).evaluateFunction(name, term.arguments.terms);
		} // In questo caso l'operatore rilevato » := 
		else {

			if (term.function instanceof ControlledFunction && term.domain instanceof ConcreteDomain)
				functionTerm.append(caseFunctionTermSuppCont(term.function, term))
			if (term.function instanceof ControlledFunction && term.domain instanceof MapDomain)
				functionTerm.append(caseFunctionTermSuppCont(term.function, term))

			functionTerm.append(term.function.name)		

			functionTerm.append(caseFunctionTermSupp(term.function, term))
			if (term.function instanceof ControlledFunction && term.domain instanceof ConcreteDomain)
				functionTerm.append("\n")

			return functionTerm.toString
		}

	}

	/** TODO: DELETE FOR COVERAGE 
	 *  	def dispatch String caseFunctionTermSupp(FunctionDefinition fd, FunctionTerm ft) {
	 * 		println("Warning: Function Definition not handled! function name: " + fd.definedFunction.name)
	 * 		return ""
	 * 	}
	 * 	


	 * 	def dispatch String caseFunctionTermSupp(OutFunction fd, FunctionTerm ft) {
	 * 		var StringBuffer functionTerm = new StringBuffer
	 * 		if (ft.arguments !== null) {
	 * 			if (ft.arguments.terms.size == 1)
	 * 				functionTerm.append("[" + visit(ft.arguments.terms.get(0)) + "]")
	 * 			else {
	 * 				functionTerm.append("[make_tuple(")
	 * 				for (var i = 0; i < ft.arguments.terms.size; i++)
	 * 					functionTerm.append(visit(ft.arguments.terms.get(i)) + ", ")

	 * 				functionTerm = new StringBuffer(functionTerm.substring(0, functionTerm.length - 2) + ")]")
	 * 			}
	 * 		}
	 * 		return functionTerm.toString
	 * 	}

	 * 	def dispatch String caseFunctionTermSupp(SharedFunction fd, FunctionTerm ft) {
	 * 		throw new RuntimeException("Shared Functions not yet supported")
	 * 	}
	 * 	
	 */
	def String caseFunctionTermSuppCont(Function fd, FunctionTerm ft) {

		var StringBuffer functionTerm = new StringBuffer

		if (ft.arguments === null) {
			// Identifico Dx o Sx
			if (ft.domain instanceof ConcreteDomain) {
				if (leftHandSide) {
					leftHandSide = false
					functionTerm.append(ft.domain.name + " " + ft.domain.name + "_s = new " + ft.domain.name + "();\n")
					functionTerm.append(ft.domain.name + "_s.value = (//")

				}

			}
			if (ft.domain instanceof MapDomain) {
				functionTerm.append('''@SuppressWarnings("serial") //''')

			}
		}

		// Identifico se la funzione dipende da delle variabili in ingresso
		if (ft.arguments !== null) {

			// Caso di studio con una sola variabile
			if (ft.arguments.terms.size == 1) {

				if (ft.domain instanceof ConcreteDomain) {
					if (leftHandSide) {
						leftHandSide = false
						functionTerm.append(ft.domain.name + " " + ft.domain.name + "_s = new " + ft.domain.name +
							"();\n")
						functionTerm.append(ft.domain.name + "_s.value = (//")

					}

				}

			} // Caso di studio con variabili multiple in ingresso
			// da controllare se corretto come metodo
			else {
				if (fd instanceof ControlledFunction)
					if (leftHandSide) {

						functionTerm.append(fd.name + "_elem = null;\n")
						for (var i = 0; i < fd.initialization.get(0).variable.size; i++)
							functionTerm.append(
								fd.initialization.get(0).variable.get(i).domain.name + "_elem.value = " +
									visit(ft.arguments.terms.get(i)) + ";\n")

						functionTerm.append(fd.name + "_elem = new ")

						switch (fd.initialization.get(0).variable.size) {
							case 2: {
								functionTerm.append("Pair<")

							}
							case 3: {
								functionTerm.append("Triplet<")

							}
							case 4: {
								functionTerm.append("Quartet<")

							}
							case 5: {
								functionTerm.append("Quintet<")

							}
							case 6: {
								functionTerm.append("Sextet<")

							}
							case 7: {
								functionTerm.append("Septet<")

							}
							case 8: {
								functionTerm.append("Octet<")

							}
							case 9: {
								functionTerm.append("Ennead<")

							}
							case 10: {
								functionTerm.append("Decade<")

							}
						}

						for (var i = 0; i < fd.initialization.get(0).variable.size; i++) {
							if (i != fd.initialization.get(0).variable.size - 1)
								functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name + ",")
							else
								functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name + ">(")

						}

						for (var i = 0; i < fd.initialization.get(0).variable.size; i++) {
							if (i != fd.initialization.get(0).variable.size - 1)
								functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name + "_elem,")
							else
								functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name + "_elem);\n")

						}

						functionTerm.append(ft.domain.name + " sup = new " + ft.domain.name + "();\n")
						functionTerm.append("sup.value = (//")

					} // functionTerm = new StringBuffer(functionTerm.substring(0, functionTerm.length - 2) + ")]")
					else {
						// parte condizionale
						functionTerm.append("true))\n")

						functionTerm.append("System.out.println();\n")
						functionTerm.append(fd.name + "_elem = null;\n")
						for (var i = 0; i < fd.initialization.get(0).variable.size; i++)
							functionTerm.append(
								fd.initialization.get(0).variable.get(i).domain.name + "_elem.value = " +
									visit(ft.arguments.terms.get(i)) + ";\n")

						functionTerm.append(fd.name + "_elem = new ")

						switch (fd.initialization.get(0).variable.size) {
							case 2: {
								functionTerm.append("Pair<")

							}
							case 3: {
								functionTerm.append("Triplet<")

							}
							case 4: {
								functionTerm.append("Quartet<")

							}
							case 5: {
								functionTerm.append("Quintet<")

							}
							case 6: {
								functionTerm.append("Sextet<")

							}
							case 7: {
								functionTerm.append("Septet<")

							}
							case 8: {
								functionTerm.append("Octet<")

							}
							case 9: {
								functionTerm.append("Ennead<")

							}
							case 10: {
								functionTerm.append("Decade<")

							}
						}

						for (var i = 0; i < fd.initialization.get(0).variable.size; i++) {
							if (i != fd.initialization.get(0).variable.size - 1)
								functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name + ",")
							else
								functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name + ">(")

						}

						for (var i = 0; i < fd.initialization.get(0).variable.size; i++) {
							if (i != fd.initialization.get(0).variable.size - 1)
								functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name + "_elem,")
							else
								functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name + "_elem);\n")

						}

						functionTerm.append("if((" + fd.name + ".get(" + fd.name + "_elem).value //")
					}

			}
		}
		return functionTerm.toString
	}

	// Identifico la tipologia delle variabili e la loro posizione rispetto all'operatore
	def dispatch String caseFunctionTermSupp(ControlledFunction fd, FunctionTerm ft) {

		var StringBuffer functionTerm = new StringBuffer

		if (ft.arguments === null) {
			// Identifico Dx o Sx
			if (ft.domain instanceof ConcreteDomain) {
				if (!leftHandSide) {
					functionTerm.append(".get()")
					if (controllo(fd.codomain)) {
						functionTerm.append(".value")
					}
				}

			} else if (ft.domain instanceof MapDomain) {

				functionTerm.append("")

			} else {
				if (leftHandSide)
					functionTerm.append(".set(")
				else {
					functionTerm.append(".get()")
					if (controllo(fd.codomain)) {
						functionTerm.append(".value")
					}
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
						if (controllo(fd.codomain)) {
							functionTerm.append(".value")
						}
					}

				} else {

					if (leftHandSide) {
						leftHandSide = false
						functionTerm.append(".set(" + visit(ft.arguments.terms.get(0)) + ", ")

					} else {
						if (ft.arguments.terms.get(0) instanceof ConstantTerm && !((ft.arguments.eContainer as LocationTerm).function.domain instanceof EnumTd))
							functionTerm.append(".get(" + (ft.arguments.eContainer as LocationTerm).function.domain.name + ".valueOf(" + visit(ft.arguments.terms.get(0)) + "))"
							)
						else
							functionTerm.append(".get(" + visit(ft.arguments.terms.get(0)) + ")")
						
						if (controllo(fd.codomain)) {
							functionTerm.append(".value")
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

	def dispatch String caseFunctionTermSupp(MonitoredFunction fd, FunctionTerm ft) {
		var StringBuffer functionTerm = new StringBuffer
		if (ft.arguments === null)
			if (leftHandSide)
				functionTerm.append(".set(")
			else {
				functionTerm.append(".get()")

				if (controllo(fd.codomain)) {
					functionTerm.append(".value")
				}

			}

		if (ft.arguments !== null) {
			if (ft.arguments.terms.size == 1) {
				// functionTerm.append("[" + visit(ft.arguments.terms.get(0)) + "]")
				if (leftHandSide) {
					leftHandSide = false
					functionTerm.append(".set(" + visit(ft.arguments.terms.get(0)) + ", ")

				} else {
					functionTerm.append(".get(" + visit(ft.arguments.terms.get(0)) + ")")
					if (controllo(fd.codomain)) {
						functionTerm.append(".value")
					}
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

	def dispatch String caseFunctionTermSupp(DerivedFunction fd, FunctionTerm ft) {
		var StringBuffer functionTerm = new StringBuffer
		if (ft.arguments !== null) {
			functionTerm.append("(")
			for (var i = 0; i < ft.arguments.terms.size; i++)
				functionTerm.append(visit(ft.arguments.terms.get(i)) + ", ")

			functionTerm = new StringBuffer(functionTerm.substring(0, functionTerm.length - 2) + ")")
		} else if (ft.domain instanceof AbstractTd)
			functionTerm.append("")
		else {
			functionTerm.append("()")
			if (fd.codomain instanceof ConcreteDomain)
				functionTerm.append(".value")
		}
		return functionTerm.toString
	}

	def dispatch String caseFunctionTermSupp(StaticFunction fd, FunctionTerm ft) {
		var StringBuffer functionTerm = new StringBuffer
		if (ft.arguments !== null) {
			functionTerm.append("(")
			for (var i = 0; i < ft.arguments.terms.size; i++)
				functionTerm.append(visit(ft.arguments.terms.get(i)) + ", ")

			functionTerm = new StringBuffer(functionTerm.substring(0, functionTerm.length - 2) + ")")
		} else if (ft.domain instanceof AbstractTd)
			functionTerm.append("")
		else {
			functionTerm.append("()")
			if (fd.codomain instanceof ConcreteDomain)
				functionTerm.append(".value")
		}
		return functionTerm.toString
	}

	def Boolean controllo(Domain dom) {
		if (dom instanceof ConcreteDomain)
			return true
		else
			return false
	}

}
