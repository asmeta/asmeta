package org.asmeta.asm2java

import asmeta.structure.Asm
import asmeta.terms.basicterms.FunctionTerm
import asmeta.terms.basicterms.LocationTerm
import asmeta.terms.basicterms.VariableTerm
import org.asmeta.parser.util.ReflectiveVisitor
import asmeta.definitions.ControlledFunction
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.Function
import asmeta.definitions.domains.ProductDomain
import asmeta.definitions.domains.MapDomain
import asmeta.definitions.domains.Domain
import asmeta.definitions.domains.SequenceDomain

class TermToJavaSupportoProdMap extends ReflectiveVisitor<String> {
	
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
	

    // Metodo richiamato in presenza di due termini ed un operatore da identificare
	def String visit(LocationTerm term) {
		return visit(term as FunctionTerm)
		/*if (term.arguments)
			return visit(term as FunctionTerm)
		else
	 		return (term.function.name + "(" + visit(term.arguments)+")")*/
	}


	def String visit(FunctionTerm term) {
		
		var StringBuffer functionTerm = new StringBuffer
		var name = new Util().parseFunction(term.function.name)
		
		//Controllo se l'operatore » del tipo: &,|,<=,>=,<,>...
		if (ExpressionToJava.hasEvaluateVisitor(name)) {
			 

		}
		
		//In questo caso l'operatore rilevato » := 
		 else {
            
            
            
			if(term.function instanceof ControlledFunction && term.domain instanceof ConcreteDomain && !(term.function.domain instanceof ProductDomain))
			functionTerm.append(caseFunctionTermSupp(term.function, term))
			if(term.function instanceof ControlledFunction && term.function.domain instanceof ProductDomain && term.domain instanceof ConcreteDomain)
			functionTerm.append(caseFunctionTermSupp(term.function, term))
			if(term.function instanceof ControlledFunction && term.domain instanceof MapDomain)
			functionTerm.append(caseFunctionTermSupp(term.function, term))
			if(term.function instanceof ControlledFunction && term.domain instanceof SequenceDomain)
			functionTerm.append(caseFunctionTermSupp(term.function, term))
			return functionTerm.toString
		}

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
	def String caseFunctionTermSupp(Function fd, FunctionTerm ft) {
		
		var StringBuffer functionTerm = new StringBuffer
		
		
		
		
		if(fd.domain instanceof ProductDomain)
		{
		        functionTerm.append(fd.name + ".set("+ fd.name+ "_elem, sup);\n")
		        
		}
		if(fd.domain instanceof SequenceDomain)
		{
		        functionTerm.append(fd.name + ".set("+ fd.name+ "_elem);\n")
		        
		}
		if(ft.domain instanceof MapDomain)
		{
		        functionTerm.append(fd.name + ".set(supporto);\n")
		        
		}
		if(ft.domain instanceof SequenceDomain)
		{
		        functionTerm.append(fd.name + ".set("+ fd.name+"_elem);\n")
		        
		}
		
		if (ft.arguments === null)
		{
			if(ft.domain instanceof ConcreteDomain)
		//Identifico Dx o Sx
		    if (!leftHandSide)
			functionTerm.append(fd.name+".set("+ft.domain.name+"_s);")
			
			return functionTerm.toString
		
		}
			
	    //Identifico se la funzione dipende da delle variabili in ingresso
		 if (ft.arguments !== null) {
			
			//Caso di studio con una sola variabile
			if (ft.arguments.terms.size == 1)
			
			{
				if(ft.domain instanceof ConcreteDomain)
					if (!leftHandSide)
					{
			          
			          functionTerm.append(fd.name+".set("+visit(ft.arguments.terms.get(0))+", "+ft.domain.name+"_s);")
			            
			       
			}
			//Caso di studio con variabili multiple in ingresso
			//da controllare se corretto come metodo
			else 
			{

				
			}
		}
		return functionTerm.toString
	}
   }

    def Boolean controllo(Domain dom)
    {
    	if(dom instanceof ConcreteDomain)
    	return true
    	else return false
    }

}