package org.asmeta.asm2java

import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.AnyDomain
import asmeta.definitions.domains.BooleanDomain
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.EnumElement
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.domains.IntegerDomain
import asmeta.definitions.domains.NaturalDomain
import asmeta.definitions.domains.PowersetDomain
import asmeta.definitions.domains.SequenceDomain
import asmeta.definitions.domains.StringDomain
import asmeta.structure.Asm
import org.asmeta.parser.util.ReflectiveVisitor
import asmeta.definitions.domains.RuleDomain
import asmeta.definitions.domains.ProductDomain

class ToString extends ReflectiveVisitor<String> {

	package Integer numStaticParam
	Asm res
	boolean leftHandSide
	boolean seqBlock
	boolean pointer

	new(Asm resource) {
		this(resource, false, false)
	}

	new(Asm resource, boolean leftHandSide, boolean seqBlock) {
		this.res = resource
		this.leftHandSide = leftHandSide
		this.seqBlock = seqBlock
		this.pointer=false
	}
	
	new(Asm asm, boolean pointer) {
		this.res=asm
		this.pointer=pointer
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
	
	//Non cambio il tipo perch» non trovo una classe come NAtural, per» se ASM compila la conseguenza » che 
	//tutti i valori sono >=0
	def String visit(NaturalDomain domain) {
		return "Integer"
	}
	
	
	def String visit(AnyDomain object) {
		return "Object"
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
			//sb.append('''«new DomainToJava(res,pointer).visit(object)»''')
		return sb.toString
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
	
/** TODO: DELETE FOR COVERAGE 	
	def String visit(CharDomain domain) {
		return "char"
	}


	def String visit(UndefDomain domain) {
		throw new Exception("Undefined domain not supported");
	}

	def String visit(RealDomain domain) {
		return "double"
	}
	
	
	def String visit(ComplexDomain domain) {
		return "TODO"
	}

	def String visit(FunctionTerm term) {
		return new TermToCpp(res).visit(term);
	}


	
	def String visit(BagDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToH(res).visit(object)»''')
		return sb.toString
	}
	
	
	def String visit(MapDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToH(res).visit(object)»''')
		return sb.toString
	}
	*/
}
