package org.asmeta.asm2code

import asmeta.structure.Asm
import org.asmeta.parser.util.ReflectiveVisitor
import asmeta.definitions.ControlledFunction
import org.eclipse.emf.common.util.EList
import asmeta.terms.basicterms.VariableTerm
import asmeta.definitions.MonitoredFunction
import asmeta.definitions.StaticFunction
import asmeta.definitions.DerivedFunction
import asmeta.definitions.domains.AbstractTd
import org.asmeta.asm2code.main.TranslatorOptions

class FunctionToCpp extends ReflectiveVisitor<String> {

	Asm asm
	
	TranslatorOptions options;

	int i

	
	new(Asm asm) {
		this.asm = asm
		this.options = null;
	}

	new(Asm asm, TranslatorOptions options) {
		this.asm = asm
		this.options = options;
	}

	/*def String visit(ControlledFunction object) {
	 * 	if (object.domain!==null)
	 * 		return "map<" + new FunctionToH(asm).returnDomain(object.domain) +", " + new FunctionToH(asm).returnDomain(object.codomain) + ">{{" + new TermToCpp(asm).visit(object.initialization.get(0).body) + "}}"
	 * 	else
	 * 		return new TermToCpp(asm).visit(object.initialization.get(0).body)
	 }*/
	def String visit(ControlledFunction object) {
		var StringBuffer sb = new StringBuffer
		if (object.domain !== null) {
			for (var i = 0; i < object.initialization.get(0).variable.size; i++) {
				if (new Util().isNotNumerable((object.initialization.get(0).variable.get(i).domain))) {
					sb.append('''
						//NOT IMPLEMENTED IN C++ (FunctionToCpp line 50)
					''')
				} else
					if (object.initialization.get(0).variable.get(i).domain instanceof AbstractTd)
				sb.append('''
						for(const auto& «new TermToCpp(asm).visit(object.initialization.get(0).variable.get(i))» : «new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)»::elems){
					''')
					else
					sb.append('''
						for(auto const& «new TermToCpp(asm).visit(object.initialization.get(0).variable.get(i))» : «new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)»_elems){
					''')
			}
			
			if (this.options !== null && (!this.options.useMaps || !this.options.initMapsWithInsert))
			{
				sb.
					append('''«object.name»[0][«printVariables(object.initialization.get(0).variable)»] = «new TermToCpp(asm).visit(object.initialization.get(0).body)»;
					''')
	
				sb.
					append('''«object.name»[1][«printVariables(object.initialization.get(0).variable)»] = «new TermToCpp(asm).visit(object.initialization.get(0).body)»;
					''')				
			}
			else
			{
				sb.
					append('''«object.name»[0].insert({«printVariables(object.initialization.get(0).variable)»,«new TermToCpp(asm).visit(object.initialization.get(0).body)»});
					''')
	
				sb.
					append('''«object.name»[1].insert({«printVariables(object.initialization.get(0).variable)»,«new TermToCpp(asm).visit(object.initialization.get(0).body)»});
					''')				
			}
			
			for (var i = 0; i < object.initialization.get(0).variable.size; i++)
				sb.append('''}''')
		} else {
			sb.
				append('''«object.name»[0] = «object.name»[1] = «new TermToCpp(asm).visit(object.initialization.get(0).body)»;''')
		}
		return sb.toString
	}

	def String visit(MonitoredFunction object) {
		var StringBuffer sb = new StringBuffer
		if (object.domain !== null) {
			for (var i = 0; i < object.initialization.get(0).variable.size; i++) {
				if (object.initialization.get(0).variable.get(i).domain instanceof AbstractTd)
				sb.append('''
					for(const auto& «new TermToCpp(asm).visit(object.initialization.get(0).variable.get(i))» : «new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)»::elems)
				''')
				else
				sb.append('''
					for(auto const& «new TermToCpp(asm).visit(object.initialization.get(0).variable.get(i))» : «new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)»_elems)
				''')
			}
			
			if (this.options !== null && (!this.options.useMaps || !this.options.initMapsWithInsert))
			{
				sb.append('''«object.name»[«printVariables(object.initialization.get(0).variable)»] = «new TermToCpp(asm).visit(object.initialization.get(0).body)»;
				''')
			}
			else
			{
				sb.append('''«object.name».insert({«printVariables(object.initialization.get(0).variable)»,«new TermToCpp(asm).visit(object.initialization.get(0).body)»});
				''')
				
			}
		} else {
			sb.append('''«object.name» = «new TermToCpp(asm).visit(object.initialization.get(0).body)»;''')
		}
		return sb.toString
	}

	def String visit(DerivedFunction object) {
		var StringBuffer sb = new StringBuffer
		if (object.domain !== null) {
			// println("Dominio " + object.name + " " + object.domain)
			var t =  new TermToCpp(asm).visit(object.definition.body)
			//println(t)
			if(t.contains(" auto $")){
			sb.
				append('''«new ToString(asm).visit(object.codomain)» «asm.name»::«object.name»(«new Util().adaptRuleParam(object.definition.variable,asm)»){«new TermToCpp(asm).visit(object.definition.body)»;}''')
			}else{ 
			sb.
				append('''«new ToString(asm).visit(object.codomain)» «asm.name»::«object.name»(«new Util().adaptRuleParam(object.definition.variable,asm)»){return «new TermToCpp(asm).visit(object.definition.body)»;}''')
		}} else {
			sb.
				append('''«new ToString(asm).visit(object.codomain)» «asm.name»::«object.name»(){return «new TermToCpp(asm).visit(object.definition.body)»;}''')
		}
		return sb.toString
	}

	def String visit(StaticFunction object) {
		var StringBuffer sb = new StringBuffer
		if (object.domain !== null) {
			sb.
				append('''«new ToString(asm).visit(object.codomain)» «asm.name»::«object.name»(«new Util().adaptRuleParam(object.definition.variable,asm)»){return «new TermToCpp(asm).visit(object.definition.body)»;}''')
		} else {
			sb.
				append('''«new ToString(asm).visit(object.codomain)» «asm.name»::«object.name»(){return «new TermToCpp(asm).visit(object.definition.body)»;}''')
		}
		return sb.toString
	}

	def printVariables(EList<VariableTerm> list) {
		var StringBuffer sb = new StringBuffer
		if (list.size == 1) {
			sb.append('''«new TermToCpp(asm).visit(list.get(i))»''')
			return sb.toString
		} else {
			sb.append('''make_tuple(''')
			for (var i = 0; i < list.size; i++) {
				sb.append('''«new TermToCpp(asm).visit(list.get(i))»,''')
			}
			return sb.toString.substring(0, sb.length - 1) + ")"
		}

	}

}
