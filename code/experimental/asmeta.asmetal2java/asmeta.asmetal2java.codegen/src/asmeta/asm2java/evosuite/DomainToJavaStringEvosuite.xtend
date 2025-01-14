package asmeta.asm2java.evosuite

import asmeta.structure.Asm
import asmeta.asm2java.translator.DomainToJavaString

/**
 * Redefinition of the {@link DomainToJavaString} class by adding 
 * specific methods for the Evosuite tool
 */
class DomainToJavaStringEvosuite extends DomainToJavaString{
	
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