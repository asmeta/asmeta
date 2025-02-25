package asmeta.asmetal2java.codegen.translator

import asmeta.definitions.ControlledFunction
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.AbstractTd
import asmeta.structure.Asm
import asmeta.terms.basicterms.BooleanTerm
import asmeta.terms.basicterms.FunctionTerm
import asmeta.terms.basicterms.LocationTerm
import asmeta.terms.furtherterms.CaseTerm
import asmeta.terms.furtherterms.EnumTerm
import asmeta.terms.furtherterms.IntegerTerm
import asmeta.terms.furtherterms.NaturalTerm
import asmeta.terms.furtherterms.StringTerm
import asmeta.terms.furtherterms.RealTerm
import asmeta.terms.furtherterms.CharTerm

/**
 * This class is used to translate Asmeta Terms in assignments
 */
class TermToJavaInAssignments extends TermToJava {

	new(Asm resource) {
		this(resource, false)
	}

	new(Asm resource, boolean leftHandSide) {
		super(resource, leftHandSide)
	}

	override String visit(IntegerTerm term) {
		return " = " + super.visit(term)
	}
	
	override String visit(RealTerm term) {
		return " = " + super.visit(term)
	}

	override String visit(NaturalTerm term) {
		return " = " + super.visit(term)
	}

	override String visit(StringTerm term) {
		return " = " + super.visit(term)
	}
	
	override String visit(CharTerm term) {
		return " = " + super.visit(term)
	}

	override String visit(BooleanTerm term) {
		return " = " + super.visit(term)
	}

	override String visit(EnumTerm term) {
		return " = " + super.visit(term)
	}

	override String visit(CaseTerm object) {
		var StringBuffer sb = new StringBuffer
		sb.append('''= null;''')

		if (object.comparedTerm.domain instanceof AbstractTd) {
			for (var i = 0; i < object.comparingTerm.size; i++) {
				if (i == 0)
					sb.append(
							'''
							
						«""»	if(«visit(object.comparedTerm)».toString(«visit(object.comparedTerm)»).equals("«visit(object.comparingTerm.get(i))»"))
								a  «visit(object.resultTerms.get(i))»;
					''')
				else
					sb.append(
								'''
						«""»	else if(«visit(object.comparedTerm)».toString(«visit(object.comparedTerm)»).equals("«visit(object.comparingTerm.get(i))»"))
								a  «visit(object.resultTerms.get(i))»;
					''')
			}
		} else {
			for (var i = 0; i < object.comparingTerm.size; i++) {

				if (i == 0)
					sb.append(
						'''
							
						«""»	if(«object.comparedTerm.domain.name»_elem.value.equals(«visit(object.comparingTerm.get(i)).substring(3,visit(object.comparingTerm.get(i)).length)»))
								a  «visit(object.resultTerms.get(i))»;
					''')
				else
					sb.append(
							'''
						«""»	else if(«object.comparedTerm.domain.name»_elem.value.equals(«visit(object.comparingTerm.get(i)).substring(3,visit(object.comparingTerm.get(i)).length)»))
								a  «visit(object.resultTerms.get(i))»;
					''')
			}

		}
		if ((object.otherwiseTerm !== null))
			sb.append(
			'''
				«""»	else return «visit(object.otherwiseTerm)»; 
			''')

		return sb.toString
	}

	override String visit(LocationTerm term) {
		return visit(term as FunctionTerm)
	}

	// Metodo per controllare il tipo di operatore(Evaluation) o se si tratta di :=
	override String visit(FunctionTerm term) {
		var StringBuffer functionTerm = new StringBuffer
		var name = new Util().parseFunction(term.function.name)

		// Controllo se l'operatore » del tipo: &,|,<=,>=,<,>...
		if (ExpressionToJava.hasEvaluateVisitor(name)) {
			// if the funcion is an expression
			return "=" + new ExpressionToJava(res).evaluateFunction(name, term.arguments.terms);
		} // In questo caso l'operatore rilevato » := 
		else {

			functionTerm.append(term.function.name)
			functionTerm.append(caseFunctionTermSupp(term.function, term))
			return functionTerm.toString
		}
	}

	// Identifico la tipologia delle variabili e la loro posizione rispetto all'operatore
	override dispatch String caseFunctionTermSupp(ControlledFunction fd, FunctionTerm ft) {

		var StringBuffer functionTerm = new StringBuffer

		if (ft.arguments === null) {
			// Identifico Dx o Sx
			if (leftHandSide)
				functionTerm.append("")
			else
				functionTerm.append("")
		}

		// Identifico se la funzione dipende da delle variabili in ingresso
		if (ft.arguments !== null) {

			// Caso di studio con una sola variabile
			if (ft.arguments.terms.size == 1) {
				if (leftHandSide) {

					functionTerm.append("")

				} else
					functionTerm.append("")
			} // Caso di studio con variabili multiple in ingresso
			// da controllare se corretto come metodo
			else {
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
			for (var i = 0; i < ft.arguments.terms.size; i++)
				functionTerm.append(visit(ft.arguments.terms.get(i)) + ", ")

			functionTerm = new StringBuffer(functionTerm.substring(0, functionTerm.length - 2) + ")")
		} else if (ft.domain instanceof AbstractTd)
			functionTerm.append("")
		else
			functionTerm.append("()")

		return functionTerm.toString
	}

}
