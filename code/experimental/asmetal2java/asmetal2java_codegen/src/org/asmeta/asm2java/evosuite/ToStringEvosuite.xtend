package org.asmeta.asm2java.evosuite

import asmeta.structure.Asm
import asmeta.definitions.domains.PowersetDomain
import asmeta.definitions.domains.SequenceDomain
import asmeta.definitions.domains.RuleDomain
import asmeta.definitions.domains.ProductDomain
import asmeta.definitions.domains.BagDomain
import asmeta.definitions.domains.MapDomain
import org.asmeta.asm2java.translator.ToString

class ToStringEvosuite extends ToString{
	
	new(Asm resource) {
		super(resource)
	}
	
	override String visit(PowersetDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToJavaEvosuiteSigDef(res).visit(object)»''')
		return sb.toString
	}
	
	override String visit(SequenceDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToJavaEvosuiteSigDef(res).visit(object)»''')
		return sb.toString
	}
	
	override String visit(RuleDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToJavaEvosuiteSigDef(res).visit(object)»''')
		return sb.toString
	}
	
	override String visit(ProductDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToJavaEvosuiteSigDef(res).visit(object)»''')
		return sb.toString
	}
	
	override String visit(BagDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToJavaEvosuiteSigDef(res).visit(object)»''')
		return sb.toString
	}
	
	override String visit(MapDomain object) {
		var StringBuffer sb = new StringBuffer
			sb.append('''«new DomainToJavaEvosuiteSigDef(res).visit(object)»''')
		return sb.toString
	}

	
}