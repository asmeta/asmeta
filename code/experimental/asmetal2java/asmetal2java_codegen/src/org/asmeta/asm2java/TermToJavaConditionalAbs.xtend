package org.asmeta.asm2java

import asmeta.structure.Asm
import asmeta.terms.basicterms.BooleanTerm
import asmeta.terms.basicterms.FunctionTerm
import asmeta.terms.basicterms.LocationTerm
import asmeta.terms.basicterms.SetTerm
import asmeta.terms.basicterms.TupleTerm
import asmeta.terms.basicterms.VariableTerm
import asmeta.terms.furtherterms.CaseTerm
import asmeta.terms.furtherterms.ConditionalTerm
import asmeta.terms.furtherterms.EnumTerm
import asmeta.terms.furtherterms.ExistTerm
import asmeta.terms.furtherterms.ForallTerm
import asmeta.terms.furtherterms.IntegerTerm
import asmeta.terms.furtherterms.MapTerm
import asmeta.terms.furtherterms.NaturalTerm
import asmeta.terms.furtherterms.SetCt
import asmeta.terms.furtherterms.StringTerm
import org.asmeta.parser.util.ReflectiveVisitor
import asmeta.definitions.ControlledFunction
import asmeta.definitions.MonitoredFunction
import asmeta.definitions.DerivedFunction
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.AbstractTd

class TermToJavaConditionalAbs extends ReflectiveVisitor<String> {
	package Integer numStaticParam
	Asm res
	boolean leftHandSide

	new(Asm resource) {
		this(resource, false)
	}

    //Il boolean identifica se il termine » a sx o dx del :=
	new(Asm resource, boolean leftHandSide) {
		this.res = resource
		this.leftHandSide = leftHandSide
	}

	def String visit(VariableTerm term) {
		return term.name
	}

	/** TODO: DELETE FOR COVERAGE 
	 * def String visit(ComplexTerm term) {
		return "TODO"
	}
	
	def String visit(CharTerm term) {
		return term.symbol
	}
	
	

	def String visit(RealTerm term) {
		return term.symbol
	}
*/
	def String visit(IntegerTerm term) {
		return " = "+term.symbol
	}

	def String visit(NaturalTerm term) {
		return " = "+term.symbol.substring(0,term.symbol.length-1)
	}

	

	def String visit(StringTerm term) {
		return " = "+term.symbol
	}

	def String visit(BooleanTerm term) {
		return " = "+term.symbol
	}

/** TODO: DELETE FOR COVERAGE 	def String visit(UndefTerm term) {
		throw new Exception("Undefined term not supported");
	}*/

	def String visit(EnumTerm term) {
		return " = "+term.domain.name+"."+term.symbol
	}
	

	def String visit(ConditionalTerm object) {}

	def String visit(CaseTerm object) {
		var StringBuffer sb = new StringBuffer
        sb.append('''= null;''')
        
        if(object.comparedTerm.domain instanceof AbstractTd)
        {
		for (var i = 0; i < object.comparingTerm.size; i++) {
			if (i == 0)
			    
				sb.append(
						'''
						
					«""»	if(«visit(object.comparedTerm)».ToString(«visit(object.comparedTerm)»).equals("«visit(object.comparingTerm.get(i))»"))
							a  «visit(object.resultTerms.get(i))»;
				''')
			else
				sb.append(
							'''
					«""»	else if(«visit(object.comparedTerm)».ToString(«visit(object.comparedTerm)»).equals("«visit(object.comparingTerm.get(i))»"))
							a  «visit(object.resultTerms.get(i))»;
				''')
		}
		
		}
		else 
		{
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
	
	

	def String visit(TupleTerm object) {}

/** TODO: DELETE FOR COVERAGE 	
 * def String visit(SequenceTerm object) {
		var StringBuffer type = new StringBuffer("")
		type.append(new DomainToH(res).visit(object.domain))
		var StringBuffer list = new StringBuffer("{")
		for (var i = 0; i < object.terms.size; i++){
			list.append(visit(object.terms.get(i)) + ", ")
		}
		var String s
		if (list.length==1)
		return type+""
		else
		s = list.substring(0, list.length - 2) + "}"
		return type+s
		return '''
		«""»
		
		  [](){
		    auto v = «s»;
		    list<decltype(«visit(object.terms.get(0))»)> l(v.begin(), v.end());
		    return l;
		  }()'''
	}*/
	
	
	//Metodo per settare i domini statici e dinamici atttraverso i vettori 
def String visit(SetTerm object) {}
	
	

/** TODO: DELETE FOR COVERAGE 	def String visit(BagTerm object) {
		var StringBuffer type = new StringBuffer("")
		type.append(new DomainToH(res).visit(object.domain))
		var StringBuffer bag = new StringBuffer("{")
		for (var i = 0; i < object.term.size; i++)
			bag.append(visit(object.term.get(i)) + ", ")
		var s = bag.substring(0, bag.length - 2) + "}"
		return type+s
		**return '''
		«""»
		
		  [](){
		    auto v = «s»;
		    multiset<decltype(«visit(object.term.get(0))»)> ms(v.begin(), v.end());
		    return ms;
		  }()'''**
	}*/

	def String visit(MapTerm object) {}

	def String visit(ExistTerm object) {
		
	}

	/**TODO */
	/** TODO: DELETE FOR COVERAGE def String visit(ExistUniqueTerm object) {
		return "TODO ExistTerm"
	}*/

	def String visit(ForallTerm object) {
		
	}

/** TODO: DELETE FOR COVERAGE 	def String visit(LetTerm object) {
		var StringBuffer let = new StringBuffer
		let.append(
		'''
			»"\n"«
			  [&](){    **<--- letTerm**
		''')
		for (var int i = 0; i < object.variable.size; i++) {
			let.append('''	auto «visit(object.variable.get(i))» = «visit(object.assignmentTerm.get(i))»;
			''')
		}
		let.append(
			'''
				return «visit(object.body)»; 
			}()
		''')

		return let.toString
	}*/

	def String visit(SetCt term) {
		
	}

/** TODO: DELETE FOR COVERAGE 	def String visit(MapCt term) {
		throw new Exception("MapCt not implemented");
	}

	def String visit(SequenceCt term) {
		return "SEQUENCE CT NOT IMPLEMENTED"
	}

	def String visit(BagCt term) {
		throw new Exception("BagCt not implemented");
	}*/

/** TODO: DELETE FOR COVERAGE 	def String visit(DomainTerm term) {
		var StringBuffer sb = new StringBuffer
		if (term.domain instanceof StructuredTd || term.domain instanceof StructuredTdImpl)
			sb.append('''«new DomainToH(res).visit(term.domain)» «term.domain.name»;
			''')
		else
			sb.append('''«new ToString(res).visit(term.domain)» «term.domain.name»;
			''')
		return sb.toString
	}*/
	


/** TODO: DELETE FOR COVERAGE 
 def String visit(RuleAsTerm term) {
		throw new Exception("RuleAsTerm not implemented");
	}
*/

    // Metodo richiamato in presenza di due termini ed un operatore da identificare
	def String visit(LocationTerm term) {
		return visit(term as FunctionTerm)
		/*if (term.arguments)
			return visit(term as FunctionTerm)
		else
	 		return (term.function.name + "(" + visit(term.arguments)+")")*/
	}

    //Metodo per controllare il tipo di operatore(Evaluation) o se si tratta di :=
	def String visit(FunctionTerm term) {
		
		/*val definitionsFound = res.allContents.toIterable.filter(FunctionDefinition).filter[definedFunction.name.equalsIgnoreCase(term.function.name)].size
		 * if (definitionsFound > 1)
		 * 	throw new RuntimeException("Found multiple definition for function " + term.function)
		 * else if (definitionsFound == 1) { // --> FUNCTION <-- 
		 If the function is not defined the asm parser discovers it*/
		// var FunctionDefinition fd = res.eAllContents.toItera ble.filter(FunctionDefinition).filter [definedFunction == term.function].get(0)
		// var FunctionDefinition fd = term.function as FunctionDefinition
		
		var StringBuffer functionTerm = new StringBuffer
		var name = new Util().parseFunction(term.function.name)
		
		//Controllo se l'operatore » del tipo: &,|,<=,>=,<,>...
		if (ExpressionToJava.hasEvaluateVisitor(name)) {
			 //if the funcion is an expression
			return new ExpressionToJava(res).evaluateFunction(name, term.arguments.terms);
		}
		
		//In questo caso l'operatore rilevato » := 
		 else {
		 	
			functionTerm.append(term.function.name)
			functionTerm.append(caseFunctionTermSupp(term.function, term))
			return functionTerm.toString
		}
	/* }

	 * return term.function.name // TODO here there is also  the Enum value when it's used replace with EnumClass::enumvalue
	 */
	}


/** TODO: DELETE FOR COVERAGE 
 	def dispatch String caseFunctionTermSupp(FunctionDefinition fd, FunctionTerm ft) {
		println("Warning: Function Definition not handled! function name: " + fd.definedFunction.name)
		return ""
	}
	


	def dispatch String caseFunctionTermSupp(OutFunction fd, FunctionTerm ft) {
		var StringBuffer functionTerm = new StringBuffer
		if (ft.arguments !== null) {
			if (ft.arguments.terms.size == 1)
				functionTerm.append("[" + visit(ft.arguments.terms.get(0)) + "]")
			else {
				functionTerm.append("[make_tuple(")
				for (var i = 0; i < ft.arguments.terms.size; i++)
					functionTerm.append(visit(ft.arguments.terms.get(i)) + ", ")

				functionTerm = new StringBuffer(functionTerm.substring(0, functionTerm.length - 2) + ")]")
			}
		}
		return functionTerm.toString
	}

	def dispatch String caseFunctionTermSupp(SharedFunction fd, FunctionTerm ft) {
		throw new RuntimeException("Shared Functions not yet supported")
	}
	
*/

    //Identifico la tipologia delle variabili e la loro posizione rispetto all'operatore
	def dispatch String caseFunctionTermSupp(ControlledFunction fd, FunctionTerm ft) {
		
		
		var StringBuffer functionTerm = new StringBuffer
		
		if (ft.arguments === null)
		{
		//Identifico Dx o Sx
		if (leftHandSide)
			functionTerm.append("")
		else
			functionTerm.append("")
		}
			
	    //Identifico se la funzione dipende da delle variabili in ingresso
		if (ft.arguments !== null) {
			
			//Caso di studio con una sola variabile
			if (ft.arguments.terms.size == 1)
			
			{
					if (leftHandSide)
					{
			          
			          functionTerm.append( "")
			            
			        }
		            else
			          functionTerm.append("")
			}
			//Caso di studio con variabili multiple in ingresso
			//da controllare se corretto come metodo
			else 
			{
				functionTerm.append("[make_tuple(")
				for (var i = 0; i < ft.arguments.terms.size; i++)
					functionTerm.append(visit(ft.arguments.terms.get(i)) + ", ")

				functionTerm = new StringBuffer(functionTerm.substring(0, functionTerm.length - 2) + ")]")
			}
		}
		return functionTerm.toString
	}
	
	

	def dispatch String caseFunctionTermSupp(MonitoredFunction fd, FunctionTerm ft) {
		
	}



	def dispatch String caseFunctionTermSupp(DerivedFunction fd, FunctionTerm ft) {
		
	}

	def dispatch String caseFunctionTermSupp(StaticFunction fd, FunctionTerm ft) {
		var StringBuffer functionTerm = new StringBuffer
		if (ft.arguments !== null) {
				functionTerm.append("(")
				for (var i = 0; i < ft.arguments.terms.size; i++)
					functionTerm.append(visit(ft.arguments.terms.get(i)) + ", ")

				functionTerm = new StringBuffer(functionTerm.substring(0, functionTerm.length - 2) + ")")
		}else
			if (ft.domain instanceof AbstractTd)
				functionTerm.append("")
			else			
			functionTerm.append("()")
				
		return functionTerm.toString
	}


}
