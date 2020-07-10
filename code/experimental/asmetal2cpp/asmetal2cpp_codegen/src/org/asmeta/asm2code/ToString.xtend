package org.asmeta.asm2code

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
		return "string"
	}


	def String visit(BooleanDomain domain) {
		return "bool"
	}


	def String visit(IntegerDomain domain) {
		return "int"
	}
	
	def String visit(NaturalDomain domain) {
		return "unsigned int"
	}
	
	
	def String visit(AnyDomain object) {
		return "ANY"
	}

	def String visit(RealDomain object) {
		return "double"
	}
	
	
	def String visit(PowersetDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToH(res).visit(object)»''')
		return sb.toString
	}

	
	def String visit(SequenceDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToH(res).visit(object)»''')
		return sb.toString
	}
	
	def String visit(AbstractTd object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToH(res,pointer).visit(object)»''')
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




/** TODO: DELETE FOR COVERAGE 	
	def String visit(RuleDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToH(res).visit(object)»''')
		return sb.toString
	}

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

def String visit(ProductDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToH(res).visit(object)»''')
		return sb.toString
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
