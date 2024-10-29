package org.asmeta.asm2java.evosuite

import org.asmeta.asm2java.translator.DomainToJavaSigDef
import asmeta.structure.Asm
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.ConcreteDomain

class DomainToJavaEvosuiteSigDef extends DomainToJavaSigDef {
	
	new(Asm resource) {
		super(resource)
	}
	
	/**
	 * Create an instance of the {@code ToStringEvosuite} object.
	 */
	override ToStringEvosuite createToString(Asm resource) {
		new ToStringEvosuite(resource)
	}
	
	/**
	 * if this is an instance of {@code DomainToJavaEvosuiteSigDef} return private
	 * because this way Evosuite cannot access static fields.
	 */
	override String isPrivate(){
		return "private "
	}
	
	
}