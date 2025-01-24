package org.asmeta.asm2code

import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.AnyDomain
import asmeta.definitions.domains.BagDomain
import asmeta.definitions.domains.BasicTd
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.domains.MapDomain
import asmeta.definitions.domains.PowersetDomain
import asmeta.definitions.domains.ProductDomain
import asmeta.definitions.domains.RuleDomain
import asmeta.definitions.domains.SequenceDomain
import asmeta.definitions.domains.StructuredTd
import asmeta.definitions.domains.impl.StructuredTdImpl
import asmeta.structure.Asm
import org.asmeta.parser.util.ReflectiveVisitor
import asmeta.definitions.domains.Domain
import org.asmeta.asm2code.main.TranslatorOptions
import asmeta.definitions.domains.RealDomain

/** TODO: delete this class -> replaced with DomanToH and FunctionToH */
class DomainToH extends ReflectiveVisitor<String> {

	Asm res;
	boolean pointer;
	TranslatorOptions options;

	new(Asm resource) {
		this.res = resource
		pointer = false
		this.options = new TranslatorOptions(true, false, false,false);
	}

	new(Asm resource, boolean pointer) {
		this.res = resource
		this.pointer = pointer
		this.options = new TranslatorOptions(true, false, false,false);
	}
	
	
	

	/** 
	 * Domain Signature
	 */
	def String visit(ConcreteDomain object) {
		var StringBuffer sb = new StringBuffer
		if (object.typeDomain instanceof StructuredTd || object.typeDomain instanceof StructuredTdImpl)
			sb.append('''typedef «visit(object.typeDomain)» «object.name»;
			''')
		else
			sb.append('''typedef «new ToString(res).visit(object.typeDomain)» «object.name»;
			''')
		return sb.toString
	}

	/*
	 * def String visit(ConcreteDomain object) {
	 * 	var StringBuffer sb = new StringBuffer
	 * 	if (object.typeDomain instanceof StructuredTd || object.typeDomain instanceof StructuredTdImpl)
	 * 		sb.append('''typedef «visit(object.typeDomain)» «object.name»;
	 * 		''')
	 * 	else
	 * 		sb.append('''typedef «new ToString(res).visit(object.typeDomain)» «object.name»;
	 * 		''')
	 * 	return sb.toString + setDomainContainer(object.name,object.isDynamic)
	 * }
	 */
/** TODO: DELETE FOR COVERAGE 	def String visit(RuleDomain object) {
		throw new ExceptionAsmetaToC("RuleDomain", true)
	}
*/
	def String visit(ProductDomain object) {
		var StringBuffer sb = new StringBuffer
		sb.append('''tuple<''')
		for (var int i = 0; i < object.domains.size; i++) {
			if (object.domains.get(i) instanceof StructuredTd)
				sb.append('''«visit(object.domains.get(i))», ''')
			else
				sb.append('''«new ToString(res).visit(object.domains.get(i))», ''')
		}

		return sb.toString.substring(0, sb.length - 2).concat(">")
	}

	def String visit(SequenceDomain object) {
		var StringBuffer sb = new StringBuffer
		//sb.append('''list<''')
		sb.append(options.stdNamespacePrefix + '''vector<''')
		if (object.domain instanceof StructuredTd)
			sb.append('''«visit(object.domain)»''')
		else
			sb.append('''«new ToString(res).visit(object.domain)»''')
		return sb.toString.substring(0, sb.length).concat(">")
	}

	def String visit(PowersetDomain object) {
		var StringBuffer sb = new StringBuffer
		sb.append(options.stdNamespacePrefix + '''set<''')
		if (object.baseDomain instanceof StructuredTd)
			sb.append('''«visit(object.baseDomain)»''')
		else
			sb.append('''«new ToString(res).visit(object.baseDomain)»''')
		return sb.toString.substring(0, sb.length).concat(">")
	}

	def String visit(BagDomain object) {
		var StringBuffer sb = new StringBuffer
		sb.append(options.stdNamespacePrefix + '''multiset<''')
		if (object.domain instanceof StructuredTd)
			sb.append('''«visit(object.domain)»''')
		else
			sb.append('''«new ToString(res).visit(object.domain)»''')
		return sb.toString.substring(0, sb.length).concat(">")
	}

	def String visit(MapDomain object) {
		var StringBuffer sb = new StringBuffer
		sb.append(options.stdNamespacePrefix + '''map<''')
		if (object.sourceDomain instanceof StructuredTd)
			sb.append('''«visit(object.sourceDomain)»,''')
		else
			sb.append('''«new ToString(res).visit(object.sourceDomain)»,''')
		if (object.targetDomain instanceof StructuredTd)
			sb.append('''«visit(object.targetDomain)»,''')
		else
			sb.append('''«new ToString(res).visit(object.targetDomain)»''')
		return sb.toString.substring(0, sb.length).concat(">")
	}

/** TODO: DELETE FOR COVERAGE 	def String setDomainContainer(ConcreteDomain domain) {
		var StringBuffer sb = new StringBuffer
		if (!domain.isDynamic)
			sb.append('''const ''')
		sb.append('''
			set<«domain.name»> «Util.getElemsSetName(domain.name)»;
		''')
		return sb.toString;
	}*/

	/*def String setDomainContainer(String name, boolean isDynamic) {
	 * 	var StringBuffer sb = new StringBuffer
	 * 	if (!isDynamic)
	 * 		sb.append('''const ''')
	 * 	sb.append('''
	 * 	set<«name»> «Util.getElemsSetName(name)»;
	 * 	''')
	 * 	return sb.toString;
	 }*/
	/** TODO: DELETE FOR COVERAGE def void visit(AnyDomain object) {
		throw new ExceptionAsmetaToC("AnyDomain", true)
	}*/

	def String visit(EnumTd object) {
		var StringBuffer sb = new StringBuffer
		sb.append('''enum «object.name» {''')
		for (var int i = 0; i < object.element.size; i++) {
			sb.append('''«new ToString(res).visit(object.element.get(i))», ''')
		}
		return sb.toString.substring(0, sb.length - 2).concat("};\n")
	}

	/*	def String visit(EnumTd object) {
	 * 		var StringBuffer sb = new StringBuffer
	 * 		sb.append('''enum «object.name» {''')
	 * 		for (var int i = 0; i < object.element.size; i++) {
	 * 			sb.append('''«new ToString(res).visit(object.element.get(i))», ''')
	 * 		}
	 * 		return sb.toString.substring(0, sb.length - 2).concat("};\n") + setDomainContainer(object.name,false)
	 }*/
	def String visit(AbstractTd object) {
		if (pointer == true)
			return object.name + "*"
		else
			return object.name
	}

	def String visit(RealDomain object) {
		return ""; // or using a typedef??
	}
}
