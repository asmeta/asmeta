package org.asmeta.asm2java.evosuite

import asmeta.structure.Asm
import org.asmeta.asm2java.translator.ToString

class ToStringEvosuite extends ToString{
	
	new(Asm resource) {
		super(resource)
	}
	
	/**
	 * Create an instance of the {@code DomainToJavaSigDef} object.
	 */
	override DomainToJavaEvosuiteSigDef createDomainSigDef(Asm resource) {
		new DomainToJavaEvosuiteSigDef(resource)
	}

	
}