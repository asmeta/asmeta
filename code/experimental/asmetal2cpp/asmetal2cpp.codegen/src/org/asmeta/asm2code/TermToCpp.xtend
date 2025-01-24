package org.asmeta.asm2code

import asmeta.definitions.domains.StructuredTd
import asmeta.definitions.domains.impl.StructuredTdImpl
import asmeta.structure.Asm
import asmeta.terms.basicterms.BooleanTerm
import asmeta.terms.basicterms.DomainTerm
import asmeta.terms.basicterms.FunctionTerm
import asmeta.terms.basicterms.LocationTerm
import asmeta.terms.basicterms.RuleAsTerm
import asmeta.terms.basicterms.SetTerm
import asmeta.terms.basicterms.TupleTerm
import asmeta.terms.basicterms.UndefTerm
import asmeta.terms.basicterms.VariableTerm
import asmeta.terms.furtherterms.BagCt
import asmeta.terms.furtherterms.BagTerm
import asmeta.terms.furtherterms.CaseTerm
import asmeta.terms.furtherterms.CharTerm
import asmeta.terms.furtherterms.ComplexTerm
import asmeta.terms.furtherterms.ConditionalTerm
import asmeta.terms.furtherterms.EnumTerm
import asmeta.terms.furtherterms.ExistTerm
import asmeta.terms.furtherterms.ExistUniqueTerm
import asmeta.terms.furtherterms.ForallTerm
import asmeta.terms.furtherterms.IntegerTerm
import asmeta.terms.furtherterms.LetTerm
import asmeta.terms.furtherterms.MapCt
import asmeta.terms.furtherterms.MapTerm
import asmeta.terms.furtherterms.NaturalTerm
import asmeta.terms.furtherterms.RealTerm
import asmeta.terms.furtherterms.SequenceCt
import asmeta.terms.furtherterms.SequenceTerm
import asmeta.terms.furtherterms.SetCt
import asmeta.terms.furtherterms.StringTerm
import org.asmeta.parser.util.ReflectiveVisitor
import asmeta.structure.FunctionDefinition
import asmeta.definitions.ControlledFunction
import asmeta.definitions.MonitoredFunction
import asmeta.definitions.OutFunction
import asmeta.definitions.SharedFunction
import asmeta.definitions.DerivedFunction
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.PowersetDomain
import asmeta.definitions.domains.AbstractTd

class TermToCpp extends ReflectiveVisitor<String> {

	package Integer numStaticParam
	Asm res
	boolean leftHandSide

	new(Asm resource) {
		this(resource, false)
	}

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
		return term.symbol
	}

	def String visit(NaturalTerm term) {
		return term.symbol.substring(0,term.symbol.length-1)
	}

	

	def String visit(StringTerm term) {
		return "\"" + term.symbol + "\""
	}

	def String visit(BooleanTerm term) {
		return term.symbol
	}

/** TODO: DELETE FOR COVERAGE 	def String visit(UndefTerm term) {
		throw new Exception("Undefined term not supported");
	}*/

	def String visit(EnumTerm term) {
		return term.symbol
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
		sb.append('''
			[&](){      /*<--- caseTerm*/ 
		''')
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
		sb.append(	'''
		«""»   }()''')
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
		/*return '''
		«""»
		
		  [](){
		    auto v = «s»;
		    list<decltype(«visit(object.terms.get(0))»)> l(v.begin(), v.end());
		    return l;
		  }()'''*/
	}
	
	
	def String visit(SetTerm object) {
		var StringBuffer type = new StringBuffer("")
		type.append(new DomainToH(res).visit(object.domain))
		var String s = ""
		s += "{"
		if (object.term !== null && object.term.size > 0) {
			for (l : object.term)
				s += visit(l) + ", "
			s = s.substring(0, s.length - 2)
		}
		s += "}"
		//println(type)
		return type + s
		/*var String s = ""
		s += "{"
		if (object.term !== null && object.term.size > 0) {
			for (l : object.term)
				s += visit(l) + ", "
			s = s.substring(0, s.length - 2)
		}
		s += "}"
		return '''
		«""»
		
		  [](){
		    auto v = «s»;
		    set<decltype(«visit(object.term.get(0))»)> s(v.begin(), v.end());
		    return s;
		  }()'''*/
	}

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

	def String visit(MapTerm object) {
		var StringBuffer map = new StringBuffer("{")
		for (var i = 0; i < object.pair.size; i++)
			map.append(
				"make_pair(" + visit(object.pair.get(i).terms.get(0)) + "," +
					visit(object.pair.get(i).terms.get(1)) + "), ")
		var s = map.substring(0, map.length - 2) + "}";
		var StringBuffer domain = new StringBuffer()
		for (var i=0; i<object.pair.get(0).terms.size; i++)
			domain.append(new ToString(res).visit(object.pair.get(0).terms.get(i).domain)+", ")
		if (object.pair.size == 0)
			throw new RuntimeException("Empty map is not yet implemented")
		else
			return '''
			«""»
			
			  [&](){
			    map<«domain.substring(0,domain.length-2)»> v = «s»;
			   return v;
			  }()'''
			  
			  // map<decltype(«visit(object.pair.get(0).terms.get(0))»),decltype(«visit(object.pair.get(0).terms.get(1))»)> m(v.begin(), v.end());
			    
	}

	def String visit(ExistTerm object) {
		var StringBuffer sb = new StringBuffer
		sb.append('''
			
			«""»  [&]()->bool{ /*<--- ExistsTerm*/
		''')
		for (var i = 0; i < object.variable.size; i++) {
			if ((object.getRanges.get(i).domain as PowersetDomain).baseDomain instanceof AbstractTd)
			sb.append(
			'''
				«""»	for(auto «visit(object.variable.get(i))» : «new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)»::elems)
			''')
			else
			sb.append(
			'''
				«""»	for(auto «visit(object.variable.get(i))» : «new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)»_elems)
			''')
		}
		if (object.guard !== null)
			sb.append('''
				«""»	if(«visit(object.guard)»)
							return true;
						return false;
						}()
			''')
		return sb.toString
	}

	/**TODO */
	/** TODO: DELETE FOR COVERAGE def String visit(ExistUniqueTerm object) {
		return "TODO ExistTerm"
	}*/

	def String visit(ForallTerm object) {
		var StringBuffer sb = new StringBuffer
		sb.append('''
			
			«""»  [&]()->bool{ /*<--- forAllTerm*/
		''')
		for (var i = 0; i < object.variable.size; i++) {
			if((object.getRanges.get(i).domain as PowersetDomain).baseDomain instanceof AbstractTd)
			sb.append(
				'''
					«""»	for(auto «visit(object.variable.get(i))» : «new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)»::elems)
				''')
				else
				sb.append(
				'''
					«""»	for(auto «visit(object.variable.get(i))» : «new ToString(res).visit((object.getRanges.get(i).domain as PowersetDomain).baseDomain)»_elems)
				''')
		}
		if (object.guard !== null)
			sb.append('''
				«""»	if(!(«visit(object.guard)»))
						return false;
					return true;
					}()
			''')
		return sb.toString
	}

/** TODO: DELETE FOR COVERAGE 	def String visit(LetTerm object) {
		var StringBuffer let = new StringBuffer
"		let.append(
		'''
			«"\n"»
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
		throw new Exception("SetCt not implemented");
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
	def String visit(LocationTerm term) {
		return visit(term as FunctionTerm)
		/*if (term.arguments)
			return visit(term as FunctionTerm)
		else
	 		return (term.function.name + "(" + visit(term.arguments)+")")*/
	}

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
		if (ExpressionToCpp.hasEvaluateVisitor(name)) { //if the funcion is an expression
			return new ExpressionToCpp(res).evaluateFunction(name, term.arguments.terms);
		} else {
			if (term.function.name == "iton") {
				functionTerm.append("(unsigned int)")
				functionTerm.append(caseFunctionTermSupp(term.function, term))
			}
			else if(term.function.name == "length") {
				functionTerm.append(caseFunctionTermSupp(term.function, term))
				functionTerm.append(".size()")
			}
			else if(term.function.name == "at") {
				functionTerm.append(visit(term.arguments.terms.get(0)))
				functionTerm.append("[" + visit(term.arguments.terms.get(1)) + "]")
			}else if(term.function.name == "rtoi"){
				functionTerm.append("(int)")
				functionTerm.append(caseFunctionTermSupp(term.function, term))
			}else if(term.function.name == "itor"){
				functionTerm.append("(double)")
				functionTerm.append(caseFunctionTermSupp(term.function, term))
			}else {
				functionTerm.append(term.function.name)
				functionTerm.append(caseFunctionTermSupp(term.function, term))
			}
			/*functionTerm.append(term.function.name)
			functionTerm.append(caseFunctionTermSupp(term.function, term))*/
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
	def dispatch String caseFunctionTermSupp(OutFunction fd, FunctionTerm ft) {
		throw new RuntimeException("out funvtions not traslated yet");
	}
	def dispatch String caseFunctionTermSupp(ControlledFunction fd, FunctionTerm ft) {
		var StringBuffer functionTerm = new StringBuffer
		if (leftHandSide)
			functionTerm.append("[1]")
		else
			functionTerm.append("[0]")
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

	def dispatch String caseFunctionTermSupp(MonitoredFunction fd, FunctionTerm ft) {
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
			functionTerm.append("()")
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
			functionTerm.append("()")
				
		return functionTerm.toString
	}

/* 	def String visit(LetTerm term) {
		var StringBuffer letTerm = new StringBuffer
		letTerm.append("//  use a local variable \n")
		letTerm.append("int ").append(visit(term.variable.get(0))).append("\n");
		letTerm.append(visit(term.body))
		return letTerm.toString		
	}*/
	
	def String visit(LetTerm term){
		var StringBuffer letTerm = new StringBuffer
		for(var i = 0;i < term.variable.length;i++){
			letTerm.append(''' auto «visit(term.variable.get(i))» = «visit(term.assignmentTerm.get(i))»; 
			''')
		}
		letTerm.append(
			'''return «visit(term.body)» '''
		)
		return letTerm.toString
	}
	
	def String visit(asmeta.terms.furtherterms.RealTerm rt){
		return rt.symbol
	}
	
}
