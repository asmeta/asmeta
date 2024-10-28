package org.asmeta.asm2java.translator

import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.AnyDomain
import asmeta.definitions.domains.BagDomain
import asmeta.definitions.domains.BooleanDomain
import asmeta.definitions.domains.CharDomain
import asmeta.definitions.domains.ComplexDomain
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.EnumElement
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.domains.IntegerDomain
import asmeta.definitions.domains.MapDomain
import asmeta.definitions.domains.NaturalDomain
import asmeta.definitions.domains.PowersetDomain
import asmeta.definitions.domains.ProductDomain
import asmeta.definitions.domains.RealDomain
import asmeta.definitions.domains.RuleDomain
import asmeta.definitions.domains.SequenceDomain
import asmeta.definitions.domains.StringDomain
import asmeta.definitions.domains.UndefDomain
import asmeta.structure.Asm
import asmeta.terms.basicterms.FunctionTerm
import org.asmeta.parser.util.ReflectiveVisitor
import asmeta.definitions.domains.AgentDomain

class ToString extends ReflectiveVisitor<String> {

	package Integer numStaticParam
	protected Asm res

	new(Asm resource) {
		this.res = resource
	}

	def String visit(StringDomain domain) {
		return "String"
	}

	def String visit(BooleanDomain domain) {
		return "Boolean"
	}

	def String visit(IntegerDomain domain) {
		return "Integer"
	}
	
	def String visit(NaturalDomain domain) {
		return "Integer"
	}
	
	def String visit(AnyDomain object) {
		return "Object"
	}
	
	def String visit(ConcreteDomain domain) {
		return domain.name
	}

	def String visit(EnumElement elem) {
		return elem.symbol;
	}

	def String visit(EnumTd elem) {
		return elem.name;
	}
	
	def String visit(CharDomain domain) {
		return "Character"
	}
	
	def String visit(AgentDomain domain) {
		throw new Exception("Agent domain not supported");
	}
	
	def String visit(UndefDomain domain) {
		throw new Exception("Undefined domain not supported");
	}
	
	def String visit(RealDomain domain) {
		return "Double"
	}
	
	def String visit(PowersetDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToJavaSigDef(res).visit(object)»''')
		return sb.toString
	}
	
	def String visit(SequenceDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToJavaSigDef(res).visit(object)»''')
		return sb.toString
	}
	
	def String visit(AbstractTd object) {
		var StringBuffer sb = new StringBuffer
		     sb.append('''«object.name»''')
		return sb.toString
	}
	
	def String visit(RuleDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToJavaSigDef(res).visit(object)»''')
		return sb.toString
	}
	
	def String visit(ProductDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToJavaSigDef(res).visit(object)»''')
		return sb.toString
	}
	
	def String visit(BagDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToJavaSigDef(res).visit(object)»''')
		return sb.toString
	}
	
	def String visit(MapDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToJavaSigDef(res).visit(object)»''')
		return sb.toString
	}

	def String visit(FunctionTerm term) {
		return new TermToJava(res).visit(term);
	}
	
	def String visit(ComplexDomain domain) {
		throw new Exception("Complex domain not supported");
	}

}
