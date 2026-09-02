package asmeta.asmetal2java.codegen.translator

import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.BagDomain
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.domains.MapDomain
import asmeta.definitions.domains.PowersetDomain
import asmeta.terms.basicterms.SetTerm
import asmeta.definitions.domains.ProductDomain
import asmeta.definitions.domains.RuleDomain
import asmeta.definitions.domains.SequenceDomain
import asmeta.structure.Asm
import asmeta.structure.DomainDefinition
import asmeta.structure.DomainInitialization
import org.asmeta.parser.util.ReflectiveVisitor

/**
 * Translates the signature and the definition of the domains
 */
class DomainToJavaSigDef extends ReflectiveVisitor<String> {

	protected Asm res

	new(Asm resource) {
		this.res = resource
	}
	
	/**
	 * Create an instance of the {@code DomainToJavaString} object.
	 */
	protected def DomainToJavaString createDomainToJavaString(Asm resource) {
		new DomainToJavaString(resource)
	}
	
	/**
	 * if this is an instance of {@code DomainToJavaSigDef} it returns an empty string 
	 * because the field should not be private
	 */
	protected def String isPrivate(){
		return ""
	}
	
	// Translate product domains using the shared javatuples mapping.
	def String visit(ProductDomain object) {
		return ProductToJava.type(object, [ domain | createDomainToJavaString(res).visit(domain) ])
	}



	def String visit(DomainDefinition object) {
		if (object.definedDomain instanceof ConcreteDomain &&
			(object.definedDomain as ConcreteDomain).typeDomain instanceof PowersetDomain) {
			val values = object.body as SetTerm
			return "(" + values.term.map[
				"new HashSet<>(Arrays.asList" + new TermToJava(res).visit(it) + ")"
			].join(", ") + ")"
		}
		return new TermToJava(res).visit(object.body)
	}

	def String visit(DomainInitialization object) {
		return new TermToJava(res).visit(object.body)
	}

	def String visit(RuleDomain object) {
		throw new RuntimeException("RuleDomain not supported")
	}

	// Translate Seq
	def String visit(SequenceDomain object) {
		var StringBuffer sb = new StringBuffer
		val elementType = if (object.domain instanceof SequenceDomain)
			"List" + visit(object.domain as SequenceDomain).trim
		else if (object.domain instanceof PowersetDomain)
			"Set" + visit(object.domain as PowersetDomain).trim
		else
			createDomainToJavaString(res).visit(object.domain)
		sb.append('''<«elementType»> ''')
		return sb.toString
	}

	// Translate Powerset
	def String visit(PowersetDomain object) {
		var StringBuffer sb = new StringBuffer
		val elementType = if (object.baseDomain instanceof PowersetDomain)
			"Set" + visit(object.baseDomain as PowersetDomain).trim
		else if (object.baseDomain instanceof SequenceDomain)
			"List" + visit(object.baseDomain as SequenceDomain).trim
		else
			createDomainToJavaString(res).visit(object.baseDomain)
		sb.append('''<«elementType»> ''')
		return sb.toString
	}

	// Translate BagDomain
	def String visit(BagDomain object) {
		var StringBuffer sb = new StringBuffer
		sb.append('''<«createDomainToJavaString(res).visit(object.domain)»> ''')
		return sb.toString
	}

	// Translate Map
	def String visit(MapDomain object) {
		var StringBuffer sb = new StringBuffer
		sb.append('''<''')
		sb.append('''«createDomainToJavaString(res).visit(object.sourceDomain)»,''')
		sb.append('''«createDomainToJavaString(res).visit(object.targetDomain)»''')
		return sb.toString.substring(0, sb.length).concat(">")
	}

	// Translate Enumerative domains
	def String visit(EnumTd object) {
		var StringBuffer sb = new StringBuffer
		sb.append('''enum «object.name» {''')
		for (var int i = 0; i < object.element.size; i++) {
			if (i != object.element.size - 1)
				sb.append('''«createDomainToJavaString(res).visit(object.element.get(i))», ''')
			else
				sb.append('''«createDomainToJavaString(res).visit(object.element.get(i))»}
				''')
		}
		
		sb.append(System.lineSeparator)
		sb.append('''
			List<«object.name»> «object.name»_elemsList = new ArrayList<>();''')
		return sb.toString
	}

	// Translates abstract domains
	def String visit(AbstractTd object) {

		var StringBuffer sb = new StringBuffer
		//var String isStatic = ""


		if (!object.isDynamic){
			// TODO: manage static fields
			//isStatic = "static"
		}
		

		sb.append('''
			static class «object.name» {
				«isPrivate»static List<«object.name»> elems = new ArrayList<>();
				«isPrivate»static List<String> val = new ArrayList<>();
			
				«object.name» (String a) {
			    	elems.add(this);
			    	val.add(a);
			    	}
			      
				@Override
				public String toString() {
					if (elems.contains(this)) {
						return val.get(elems.lastIndexOf(this));
					} else
						return null;
					}
					
				  static «object.name» get(String a) {
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

	// Translate concrete domains
	def String visit(ConcreteDomain object) {
		var StringBuffer sb = new StringBuffer
		val type = if (object.typeDomain instanceof SequenceDomain)
			"List" + createDomainToJavaString(res).visit(object.typeDomain).trim
		else if (object.typeDomain instanceof PowersetDomain)
			"Set" + createDomainToJavaString(res).visit(object.typeDomain).trim
		else
			createDomainToJavaString(res).visit(object.typeDomain)
		// Dynamic domains
		if (object.isDynamic) {
			sb.append('''
				class  «object.name»{
				
				List<«type»> elems = new ArrayList<>();
				«type» value;
				«object.name»(«type» i) {
				   value = i;
				   }
				   }
				   
				   List<«type»> «object.name»_elems = new ArrayList<>();
			''')

		} // Static classes -> The list of elements is set after this definition 
		else {
			sb.append('''static class  «object.name» {
				«isPrivate»static List<«type»> elems = new ArrayList<>();
                «type» value;
                
                static «object.name» valueOf(«type» val) {
                	if(elems.contains(val)){
                		«object.name» n = new «object.name»();
                		n.value = elems.get(elems.indexOf(val));
                		return n;
                	}
                	return null;
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
				
				// TODO do not use this object to init - remove this line in the translation
				«object.name» «object.name»_elem = new «object.name»();
				List<«type»> «object.name»_elems = new ArrayList<>();
			''')
		}
		return sb.toString
	}
}
