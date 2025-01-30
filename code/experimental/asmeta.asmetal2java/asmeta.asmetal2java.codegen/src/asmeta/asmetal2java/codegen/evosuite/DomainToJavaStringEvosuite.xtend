package asmeta.asmetal2java.codegen.evosuite

import asmeta.structure.Asm
import asmeta.asmetal2java.codegen.translator.DomainToJavaString

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