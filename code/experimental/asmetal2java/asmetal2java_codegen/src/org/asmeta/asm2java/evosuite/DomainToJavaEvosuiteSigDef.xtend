package org.asmeta.asm2java.evosuite

import org.asmeta.asm2java.translator.DomainToJavaSigDef
import asmeta.structure.Asm

/**
 * Redefinition of the {@link DomainToJavaSigDef} class by adding 
 * specific methods for the Evosuite tool
 */
class DomainToJavaEvosuiteSigDef extends DomainToJavaSigDef {
	
	new(Asm resource) {
		super(resource)
	}
	
	/**
	 * Create an instance of the {@code ToStringEvosuite} object.
	 */
	override DomainToJavaStringEvosuite createDomainToJavaString(Asm resource) {
		new DomainToJavaStringEvosuite(resource)
	}
	
	/**
	 * if this is an instance of {@code DomainToJavaEvosuiteSigDef} return private
	 * because this way Evosuite cannot access static fields.
	 */
	override String isPrivate(){
		return "private "
	}
	
	
}