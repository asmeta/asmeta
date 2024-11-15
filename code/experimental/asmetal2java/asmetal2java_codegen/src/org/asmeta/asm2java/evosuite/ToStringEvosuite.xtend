package org.asmeta.asm2java.evosuite

import asmeta.structure.Asm
import org.asmeta.asm2java.translator.ToString

/**
 * Redefinition of the {@link ToString} class by adding 
 * specific methods for the Evosuite tool
 */
class ToStringEvosuite extends ToString{
	
	new(Asm resource) {
		super(resource)
	}
	
	/**
	 * Create an instance of the {@code DomainToJavaSigDef} object.
	 */
	override DomainToJavaEvosuiteSigDef createDomainToJavaSigDef(Asm resource) {
		new DomainToJavaEvosuiteSigDef(resource)
	}

	
}