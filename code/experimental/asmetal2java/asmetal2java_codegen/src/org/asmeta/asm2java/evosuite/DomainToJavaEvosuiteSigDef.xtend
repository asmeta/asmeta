package org.asmeta.asm2java.evosuite

import org.asmeta.asm2java.DomainToJavaSigDef
import asmeta.structure.Asm
import asmeta.definitions.domains.AbstractTd

class DomainToJavaEvosuiteSigDef extends DomainToJavaSigDef {
	
	new(Asm resource) {
		super(resource)
	}
	
		// Translates abstract domains
	override String visit(AbstractTd object) {

		var StringBuffer sb = new StringBuffer
		var String isStatic = ""

		if (!object.isDynamic)
			isStatic = "static"

		sb.append('''
			static class «object.name» {
				static List<«object.name»> elems = new ArrayList<>();
				«isStatic + " "»List<String> val = new ArrayList<>();
			
			«object.name» (String a) {
			      elems.add(this);
			      val.add(a);
			      }
			      
			      static String toString(«object.name» a) {
			      if(elems.contains(a)) {
			      return val.get(elems.lastIndexOf(a));
			      }
			      else return null;
			      }
			      
				  «object.name» get(String a) {
			      if(val.contains(a)) {
			      return elems.get(val.lastIndexOf(a));
			      }
			      else return null;
			      }
			      }
			      
			      List<String> «object.name»_elemsList = new ArrayList<>();
			      List<«object.name»> «object.name»_Class = new ArrayList<>();
		''')
		return sb.toString
	}
	
}