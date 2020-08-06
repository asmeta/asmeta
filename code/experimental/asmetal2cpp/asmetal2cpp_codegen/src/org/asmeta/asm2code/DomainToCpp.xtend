package org.asmeta.asm2code

import asmeta.structure.Asm
import org.asmeta.parser.util.ReflectiveVisitor
import asmeta.structure.DomainInitialization
import asmeta.definitions.domains.EnumTd
import asmeta.structure.DomainDefinition
import asmeta.terms.basicterms.SetTerm
import asmeta.terms.furtherterms.IntegerTerm

class DomainToCpp extends ReflectiveVisitor<String> {

	Asm asm

	int i

	String elem

	new(Asm asm) {
		this.asm = asm
	}

	def String visit(EnumTd object) {
		if (object.element.length != 0) {
			elem = "{"
			for (i = 0; i < object.element.length; i++) {
				elem += (object.element.get(i).symbol + ",")
			}
			return elem.substring(0,elem.length-1)+"}"
			/*return '''
			«""»
			[](){
			    auto v = {«elem.substring(0,elem.length-1)»};
			    set<«object.name»> s(v.begin(), v.end());
			    return s;
			  }()'''*/
		} else
		//This won't happen because Asmeta Parser doesn't allow to declare enum domain without elements
			return "No elements defined in " + object.name + " domain"
	}
	
		def String visit(EnumTd object, boolean arduino) {
		if(arduino == true){
		
		if (object.element.length != 0) {
			elem = "{"
			for (i = 0; i < object.element.length; i++) {
				elem += (object.element.get(i).symbol + ",")
			}
			return elem.substring(0,elem.length-1)+";};"
			/*return '''
			«""»
			[](){
			    auto v = {«elem.substring(0,elem.length-1)»};
			    set<«object.name»> s(v.begin(), v.end());
			    return s;
			  }()'''*/
		} else
		//This won't happen because Asmeta Parser doesn't allow to declare enum domain without elements
			return "No elements defined in " + object.name + " domain"
				}
			}
	
	def String visitArduino(SetTerm object) {
		var StringBuffer type = new StringBuffer("")
		type.append(new DomainToH(asm).visit(object.domain))
		var String s = ""
		s += "{"
		if (object.term !== null && object.term.size > 0) {
			for (l : object.term){
				var i = l as IntegerTerm
				s += i.symbol + ","
				}
			s = s.substring(0, s.length - 1)
		}
		s += "}"
		return s.substring(0,s.length-1) + ";}"
		
		}
	

	def String visit(DomainDefinition object) {
		//println("Type domain " + object.body )
		return new TermToCpp(asm).visit(object.body)
	}
	
	def String visit(DomainInitialization object) {
		//println("Type domain " + object.body )
		return new TermToCpp(asm).visit(object.body)
	}	
}
