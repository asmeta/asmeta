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
import asmeta.terms.furtherterms.LetTerm
import asmeta.terms.furtherterms.ExistTerm
import asmeta.terms.furtherterms.ForallTerm
import asmeta.terms.furtherterms.IntegerTerm
import asmeta.terms.furtherterms.MapTerm
import asmeta.terms.furtherterms.NaturalTerm
import asmeta.terms.furtherterms.SetCt
import asmeta.terms.furtherterms.StringTerm
import asmeta.terms.furtherterms.SequenceCt
import org.asmeta.parser.util.ReflectiveVisitor
import asmeta.definitions.ControlledFunction
import asmeta.definitions.MonitoredFunction
import asmeta.definitions.DerivedFunction
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.PowersetDomain
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.Domain
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.Function
import asmeta.definitions.domains.MapDomain
import asmeta.terms.furtherterms.SequenceTerm
import asmeta.definitions.domains.SequenceDomain
import asmeta.terms.basicterms.RuleAsTerm
import asmeta.terms.basicterms.UndefTerm

class TermToJava extends ReflectiveVisitor<String> {

	package Integer numStaticParam
	Asm res
	boolean leftHandSide
	String varName

	new(Asm resource) {
		this(resource, false)
	}

    //Il boolean identifica se il termine » a sx o dx del :=
	new(Asm resource, boolean leftHandSide) {
		this.res = resource
		this.leftHandSide = leftHandSide
		this.varName = ""
	}
	
	//Il boolean identifica se il termine » a sx o dx del :=
	new(Asm resource, boolean leftHandSide, String varName) {
		this.res = resource
		this.leftHandSide = leftHandSide
		this.varName = varName
	}

	def String visit(VariableTerm term) {
		return term.name
	}
	
	def String visit(UndefTerm term) {
		return "null";
	}

	def String visit(IntegerTerm term) {
		return term.symbol
	}

	def String visit(NaturalTerm term) {
		return term.symbol.substring(0,term.symbol.length-1)
	}

	def String visit(StringTerm term) {
		
		var supp = new String('"');
		return supp+term.symbol+supp
	}

	def String visit(BooleanTerm term) {
		return term.symbol
	}

	def String visit(EnumTerm term) {
		return term.domain.name+"."+term.symbol
	}
	

	def String visit(ConditionalTerm object) {
		return '''
			/*conditionalTerm*/
				(«visit(object.guard)»)
				?
					«visit(object.thenTerm)»
				:
					«visit(object.elseTerm)»
		'''
	}

	def String visit(CaseTerm object) {
		var StringBuffer sb = new StringBuffer

		for (var i = 0; i < object.comparingTerm.size; i++) {
			if (i == 0)
			    
				sb.append(
						'''
					«""»	if(«visit(object.comparedTerm)»==«visit(object.comparingTerm.get(i))») 
							return «visit(object.resultTerms.get(i))»;
				''')
			else
				sb.append(
							'''
					«""»	else if(«visit(object.comparedTerm)»==«visit(object.comparingTerm.get(i))»)
							return «visit(object.resultTerms.get(i))»;
				''')
		}
		if ((object.otherwiseTerm !== null))
			sb.append(
			'''
				«""»	else return «visit(object.otherwiseTerm)»; 
			''')
		sb.append(	''' return null;
		«""»   ''')
		return sb.toString
	}

	def String visit(TupleTerm object) {
		if (object.terms.size == 0)
			throw new RuntimeException("Error: a tuple term with size 0 has been found... why?? **BUG** ")

		if (object.terms.size == 1)
			return '''(«visit(object.terms.get(0))»)'''

		var StringBuffer initial = new StringBuffer("make_tuple(")

		for (var i = 0; i < object.terms.size; i++)
			initial.append(visit(object.terms.get(i)) + ", ")

		return initial.substring(0, initial.length - 2) + ")"
	}


  def String visit(SequenceTerm object) {
		
		var StringBuffer list = new StringBuffer("")
		for (var i = 0; i < object.terms.size; i++){
			if(i==object.terms.size-1)
			list.append(visit(object.terms.get(i)))
			else
			list.append(visit(object.terms.get(i)) + ", ")
		}
		
		list.append(")")
		return list.toString
		
	}
	
	
	//Metodo per settare i domini statici e dinamici atttraverso i vettori 
def String visit(SetTerm object) {
		var StringBuffer type = new StringBuffer("")
	
		var String s = ""
		s += "("
		if (object.term !== null && object.term.size > 0) {
			for (l : object.term)
				s += visit(l) + ", "
			s = s.substring(0, s.length - 2)
		}
		s += ")"
		return type + s
	}


	def String visit(MapTerm object) {
		var StringBuffer map = new StringBuffer("{{\n")
		for (var i = 0; i < object.pair.size; i++)
			map.append(
				"put(" + visit(object.pair.get(i).terms.get(0)) + "," +
					visit(object.pair.get(i).terms.get(1)) + ");\n     ")
		var s = map.substring(0, map.length - 2) + "}}";
		var StringBuffer domain = new StringBuffer()
		for (var i=0; i<object.pair.get(0).terms.size; i++)
			domain.append(new ToString(res).visit(object.pair.get(0).terms.get(i).domain)+", ")
		if (object.pair.size == 0)
			throw new RuntimeException("Empty map is not yet implemented")
		else
			return '''
			«""»
			
			  
			    Map<«domain.substring(0,domain.length-2)»> supporto = new HashMap<«domain.substring(0,domain.length-2)»>()
			    «s»;
			   
			  //'''
			      
	}
	

	def String visit(ExistTerm object) {
		var StringBuffer sb = new StringBuffer
		var StringBuffer app = new StringBuffer
		
		app.append('''«visit(object.guard)»''')
		
		var limiteS = app.toString.indexOf(")")
		var partenza = app.toString.indexOf("(")
		if(partenza == 0)
		partenza=1
		else partenza =0
		//else
		var valore = app.substring(partenza,limiteS-2)
		
		for (var i = 0; i < object.variable.size; i++) {
			if ((object.getRanges.get(i).domain as PowersetDomain).baseDomain instanceof AbstractTd)
			sb.append(
			'''
				«""»	«new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)».elems.stream().anyMatch(c -> c.ToString(c).equals(«app.substring(7,app.length-1)».ToString(c)))
			''')

			else if ((object.getRanges.get(i).domain as PowersetDomain).baseDomain instanceof EnumTd)
			sb.append(
			'''
				«"Arrays.stream("»	«new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)».values()).anyMatch(c -> «valore»c))
			''')
			else
			
			sb.append(
			'''
				«""»	«new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)».elems.stream().anyMatch(c -> c.equals(«app.substring(13,app.length-1)»))
			''')
		}
		
		return sb.toString
	}

	def String visit(ForallTerm object) {
		var StringBuffer sb = new StringBuffer
		
		var StringBuffer supp = new StringBuffer
		
		supp.append('''«visit(object.guard)»''')
		
		sb.append('''
			
			«""»  /*<--- forAllTerm*/
		''')
		for (var i = 0; i < object.variable.size; i++) {
			if((object.getRanges.get(i).domain as PowersetDomain).baseDomain instanceof AbstractTd)
			sb.append(
				'''
					«""»	for(Object «visit(object.variable.get(i))» : «new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)»::elems)
				''')
				else
			if((object.getRanges.get(i).domain as PowersetDomain).baseDomain instanceof ConcreteDomain)
			
				sb.append(
			'''
				«""»	«new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)».elems.stream().allMatch(c -> «supp.substring(0,supp.length-3)»c));
			''')
			else if ((object.getRanges.get(i).domain as PowersetDomain).baseDomain instanceof EnumTd)
				sb.append(
			'''
				«"Arrays.stream("»	«new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)».values()).allMatch(c -> «supp.substring(0,supp.length-3)»c));
			''')
			else
				sb.append(
			'''
				«""»	«new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)»_elemsList.stream().allMatch(c -> «supp.substring(0,supp.length-3)»c));
			''')
			
			}

		return sb.toString
	}

def String visit(LetTerm object) {
		var StringBuffer let = new StringBuffer
		let.append(
		'''
			«"\n"»
			  [&](){    **<--- letTerm**
		''')
		for (var int i = 0; i < object.variable.size; i++) {
			let.append('''	Object «visit(object.variable.get(i))» = «visit(object.assignmentTerm.get(i))»;
			''')
		}
		let.append(
			'''
				return «visit(object.body)»; 
				}()
		''')

		return let.toString
	}

	def String visit(SetCt term) {
		throw new Exception("SetCt not implemented");
	}
	
	def String visit(SequenceCt object)	{
		
		
		var StringBuffer sb = new StringBuffer
		
		var StringBuffer supp = new StringBuffer
		
		supp.append('''«visit(object.guard)»''')
		
		sb.append('''
			
			«""»  /*<--- SequenceCt*/
		''')
		for (var i = 0; i < object.variable.size; i++) {
			if((object.getRanges.get(i).domain as SequenceDomain).domain instanceof AbstractTd)
			sb.append(
				'''
					«""»	for(Object «visit(object.variable.get(i))» : «new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)»::elems)
				''')
				else
			if((object.getRanges.get(i).domain as SequenceDomain).domain instanceof ConcreteDomain)
			
				sb.append(
			'''
				«""»	«new ToString(res).visit((object.getRanges.get(i).domain as SequenceDomain).domain)».elems.stream().allMatch(c -> «supp.substring(0,supp.length-3)»));
			''')
			else
				sb.append(
			'''
				«""»	«new ToString(res).visit((object.getRanges.get(i).domain as SequenceDomain).domain)»_elemsList.stream().allMatch(c -> «supp.substring(0,supp.length-3)»));
			''')
			
			}

		return sb.toString
	}


 def String visit(RuleAsTerm term) {
 	      return "RuleAsRTerm"

	}


    // Metodo richiamato in presenza di due termini ed un operatore da identificare
	def String visit(LocationTerm term) {
		
		return visit(term as FunctionTerm)

	}

    //Metodo per controllare il tipo di operatore(Evaluation) o se si tratta di :=
	def String visit(FunctionTerm term) {
		
		var StringBuffer functionTerm = new StringBuffer
		var name = new Util().parseFunction(term.function.name)
		
		//Controllo se l'operatore » del tipo: &,|,<=,>=,<,>...
		if (ExpressionToJava.hasEvaluateVisitor(name)) {
			 //if the funcion is an expression
			return new ExpressionToJava(res).evaluateFunction(name, term.arguments.terms);
		}
		
		//In questo caso l'operatore rilevato » := 
		 else {
		 	
            if(term.function instanceof ControlledFunction && term.domain instanceof ConcreteDomain)   	 	
	 	    functionTerm.append(caseFunctionTermSuppCont(term.function, term))
	 	    if(term.function instanceof ControlledFunction && term.domain instanceof MapDomain)   	 	
	 	    functionTerm.append(caseFunctionTermSuppCont(term.function, term))
	 	    
			functionTerm.append(term.function.name)
			
			
			
			functionTerm.append(caseFunctionTermSupp(term.function, term))
			if(term.function instanceof ControlledFunction && term.domain instanceof ConcreteDomain)   	 	
	 	    functionTerm.append("\n")
			
			
			
			return functionTerm.toString
		}

	}

    def String caseFunctionTermSuppCont(Function fd, FunctionTerm ft) {
		
		
		var StringBuffer functionTerm = new StringBuffer
		
		
		
		
		if (ft.arguments === null)
		{
		//Identifico Dx o Sx
		
		if(ft.domain instanceof ConcreteDomain)
				{
					if (leftHandSide)
					{
			          leftHandSide = false
			          functionTerm.append(ft.domain.name+" "+ ft.domain.name + varName +"_s = new " + ft.domain.name + "();\n")
			          functionTerm.append(ft.domain.name+ varName +"_s.value = (//")
			            
			        }
					
					
				}
			if(ft.domain instanceof MapDomain)
			{
			functionTerm.append('''@SuppressWarnings("serial") //''')
			
	         }
		}
			
	    //Identifico se la funzione dipende da delle variabili in ingresso
		if (ft.arguments !== null) {
			
			//Caso di studio con una sola variabile
			if (ft.arguments.terms.size == 1)
			
			{
				
				if(ft.domain instanceof ConcreteDomain)
				{
					if (leftHandSide)
					{
			          leftHandSide = false
			          functionTerm.append(ft.domain.name+" "+ ft.domain.name+ varName +"_s = new " + ft.domain.name + "();\n")
			          functionTerm.append(ft.domain.name+ varName +"_s.value = (//")
			            
			        }
		            
					
					
				}
				
			}
			//Caso di studio con variabili multiple in ingresso
			//da controllare se corretto come metodo
			else 
			{
				if(fd instanceof ControlledFunction)
				if(leftHandSide)
				{
					
				functionTerm.append(fd.name+"_elem = null;\n")
				for (var i = 0; i < fd.initialization.get(0).variable.size; i++)
					functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name+"_elem.value = "+visit(ft.arguments.terms.get(i)) + ";\n")
					
				functionTerm.append(fd.name+"_elem = new ")
				
				switch(fd.initialization.get(0).variable.size)
		            {
		             case 2:{
		                      functionTerm.append("Pair<")
		                     
		                     }
		         
				     case 3:{
		                      functionTerm.append("Triplet<")
		                     
		                     }
		                     
		             case 4:{
		                      functionTerm.append("Quartet<")
		                     
		                     }
		                     
		             case 5:{
		                      functionTerm.append("Quintet<")
		                     
		                     }
		         
				     case 6:{
		                      functionTerm.append("Sextet<")
		                     
		                     }
		                     
		             case 7:{
		                      functionTerm.append("Septet<")
		                     
		                     }  
		                     
		             case 8:{
		                      functionTerm.append("Octet<")
		                     
		                     }
		         
				     case 9:{
		                      functionTerm.append("Ennead<")
		                     
		                     }
		                     
		             case 10:{
		                      functionTerm.append("Decade<")
		                     
		                     }  		                            
		            }
		            
		            for(var i =0; i<fd.initialization.get(0).variable.size;i++)
		            {
		             if(i != fd.initialization.get(0).variable.size-1)
		             functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name +",")
		             else
		             functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name +">(")
		             
		            }
		            
		            for(var i =0; i<fd.initialization.get(0).variable.size;i++)
		            {
		             if(i != fd.initialization.get(0).variable.size-1)
		             functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name +"_elem,")
		             else
		             functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name +"_elem);\n")
		             
		             
		            }
				
				functionTerm.append(ft.domain.name+" sup = new " + ft.domain.name +"();\n")
				functionTerm.append("sup.value = (//" )
				
                }
				
				else {
					//parte condizionale
					functionTerm.append("true))\n")
					
					functionTerm.append("System.out.println();\n")
					functionTerm.append(fd.name+"_elem = null;\n")
				for (var i = 0; i < fd.initialization.get(0).variable.size; i++)
					functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name+"_elem.value = "+visit(ft.arguments.terms.get(i)) + ";\n")
					
				functionTerm.append(fd.name+"_elem = new ")
				
				switch(fd.initialization.get(0).variable.size)
		            {
		             case 2:{
		                      functionTerm.append("Pair<")
		                     
		                     }
		         
				     case 3:{
		                      functionTerm.append("Triplet<")
		                     
		                     }
		                     
		             case 4:{
		                      functionTerm.append("Quartet<")
		                     
		                     }
		                     
		             case 5:{
		                      functionTerm.append("Quintet<")
		                     
		                     }
		         
				     case 6:{
		                      functionTerm.append("Sextet<")
		                     
		                     }
		                     
		             case 7:{
		                      functionTerm.append("Septet<")
		                     
		                     }  
		                     
		             case 8:{
		                      functionTerm.append("Octet<")
		                     
		                     }
		         
				     case 9:{
		                      functionTerm.append("Ennead<")
		                     
		                     }
		                     
		             case 10:{
		                      functionTerm.append("Decade<")
		                     
		                     }  		                            
		            }
		            
		            for(var i =0; i<fd.initialization.get(0).variable.size;i++)
		            {
		             if(i != fd.initialization.get(0).variable.size-1)
		             functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name +",")
		             else
		             functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name +">(")
		             
		            }
		            
		            for(var i =0; i<fd.initialization.get(0).variable.size;i++)
		            {
		             if(i != fd.initialization.get(0).variable.size-1)
		             functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name +"_elem,")
		             else
		             functionTerm.append(fd.initialization.get(0).variable.get(i).domain.name +"_elem);\n")
		             
		             
		            }
				
				
				functionTerm.append("if(("+fd.name+".get(" + fd.name +"_elem).value //")
				}
			
			}
		}
		return functionTerm.toString
	}

    //Identifico la tipologia delle variabili e la loro posizione rispetto all'operatore
	def dispatch String caseFunctionTermSupp(ControlledFunction fd, FunctionTerm ft) {
		
		
		var StringBuffer functionTerm = new StringBuffer
		
		
		
		
		if (ft.arguments === null)
		{
		//Identifico Dx o Sx
		
		
		if(ft.domain instanceof ConcreteDomain)
				{
					if (!leftHandSide)
					{	          
			          functionTerm.append(".get()" )
			          if(controllo(fd.codomain))
			        {
				      functionTerm.append(".value" )
			         }
			          }
					
					
				}
				
		else if(ft.domain instanceof SequenceDomain)
				{
					          
			          functionTerm.append("_elem = Collections.unmodifiableList(Arrays.asList(" );
			         
					
					
				}
				
		else if(ft.domain instanceof MapDomain)
				{
					          
			          functionTerm.append("" )
			         
					
					
				}		
				
		else
		{
		if (leftHandSide)
			functionTerm.append(".set(" )
		else
		{
			functionTerm.append(".get()")
			/* */
			if(controllo(fd.codomain))
			        {
				      functionTerm.append(".value" )
			         }
			        
			}
		}
		
		}
			
	    //Identifico se la funzione dipende da delle variabili in ingresso
		if (ft.arguments !== null) {
			
			//Caso di studio con una sola variabile
			if (ft.arguments.terms.size == 1)
			
			{
				
				if(ft.domain instanceof ConcreteDomain)
				{
					if (!leftHandSide)
					{
			      
			          functionTerm.append(".get(" + visit(ft.arguments.terms.get(0)) +")")
			          if(controllo(fd.codomain))
			        {
				      functionTerm.append(".value" )
			         }
			          }
					
					
				}
				else
				{
				
					if (leftHandSide)
					{
			          leftHandSide = false
			          functionTerm.append(".set("+visit(ft.arguments.terms.get(0))+", ")
			            
			        }
		            else
		            {
			          functionTerm.append(".get(" + visit(ft.arguments.terms.get(0))+")")
			          if(controllo(fd.codomain))
			        {
				      functionTerm.append(".value" )
			         }
			          }
			          
			      }
			}
			//Caso di studio con variabili multiple in ingresso
			//da controllare se corretto come metodo
			else 
			{
				//functionTerm.append("[make_tuple(")
				
			}
		}
		return functionTerm.toString
	}
	
	

	def dispatch String caseFunctionTermSupp(MonitoredFunction fd, FunctionTerm ft) {
		var StringBuffer functionTerm = new StringBuffer
		if(ft.arguments === null)
		{
		if (leftHandSide)
			functionTerm.append(".set(")
		else
		{
			functionTerm.append(".get()")
			
			}
			
			}
		
		if (ft.arguments !== null) {
			if (ft.arguments.terms.size == 1)
			{
				
				if (leftHandSide)
					{
			          leftHandSide = false
			          functionTerm.append(".set("+visit(ft.arguments.terms.get(0))+", ")
			            
			        }
		            else
		            {
			          functionTerm.append(".get(" + visit(ft.arguments.terms.get(0))+")")
			          
			          }
		
			}
			else {			
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
		}
		else
			if (ft.domain instanceof AbstractTd)
				functionTerm.append("")
			else			
		{			
			functionTerm.append("()")
			
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
		}else
			if (ft.domain instanceof AbstractTd)
				functionTerm.append("")
			else
		
		{			
			functionTerm.append("()")
			
			
		}	
		return functionTerm.toString
	}

    def Boolean controllo(Domain dom)
    {
    	if(dom instanceof ConcreteDomain)
    	return true
    	else return false
    }
    


}
