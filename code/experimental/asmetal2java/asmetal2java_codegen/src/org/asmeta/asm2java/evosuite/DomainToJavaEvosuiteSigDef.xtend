package org.asmeta.asm2java.evosuite

import org.asmeta.asm2java.DomainToJavaSigDef
import asmeta.structure.Asm
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.ConcreteDomain
import org.asmeta.asm2java.ToString

class DomainToJavaEvosuiteSigDef extends DomainToJavaSigDef {
	
	new(Asm resource) {
		super(resource)
	}
	
	/**
	 * Translates abstract domains.
	 * With the static fields private and the toString() method static.
	 */ 
	override String visit(AbstractTd object) {

		var StringBuffer sb = new StringBuffer
		var String isStatic = ""

		if (!object.isDynamic)
			isStatic = "static"

		sb.append('''
			static class «object.name» {
				private static List<«object.name»> elems = new ArrayList<>();
				private «isStatic + " "»List<String> val = new ArrayList<>();
			
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
	
	/**
	 * Translate concrete domains.
	 * With the static fields private.
	 */ 
	override String visit(ConcreteDomain object) {
		var StringBuffer sb = new StringBuffer

		// Dynamic domains
		if (object.isDynamic) {
			sb.append('''
				class  «object.name»{
				
				List<«new ToString(res).visit(object.typeDomain)»> elems = new ArrayList<>();			      
				«new ToString(res).visit(object.typeDomain)» value;			      
				«object.name»(«new ToString(res).visit(object.typeDomain)» i) { 
				   value = i;
				   }
				   }
				   
				   List<«new ToString(res).visit(object.typeDomain)»> «object.name»_elems = new ArrayList<>();
			''')

		} // Static classes -> The list of elements is set after this definition 
		else {
			sb.append('''static class  «object.name» {
				private static List<«new ToString(res).visit(object.typeDomain)»> elems = new ArrayList<>();
                private «new ToString(res).visit(object.typeDomain)» value;
                
                static «object.name» valueOf(«new ToString(res).visit(object.typeDomain)» val) {
                	«object.name» n = new «object.name»();
                	n.value = val;
                	return n;
				}
				
				static «object.name» valueOf(«object.name» val) {
				                	return val;
								}
				
				@Override
					public boolean equals(Object obj) {
						if (!(obj instanceof «object.name»)) return false;
						return value.equals(((«object.name»)obj).value);
					}
				
				
				@Override
						public int hashCode() {
							return value.hashCode();
						}
						
				}
				
				«object.name» «object.name»_elem = new «object.name»();
				List<«new ToString(res).visit(object.typeDomain)»> «object.name»_elems = new ArrayList<>();
			''')
		}
		return sb.toString
	}
	
}