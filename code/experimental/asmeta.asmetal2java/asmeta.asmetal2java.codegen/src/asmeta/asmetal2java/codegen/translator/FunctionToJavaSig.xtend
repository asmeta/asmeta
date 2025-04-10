package asmeta.asmetal2java.codegen.translator

import asmeta.definitions.ControlledFunction
import asmeta.definitions.DerivedFunction
import asmeta.definitions.MonitoredFunction
import asmeta.definitions.OutFunction
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.Domain
import asmeta.definitions.domains.ProductDomain
import asmeta.definitions.domains.StructuredTd
import asmeta.definitions.domains.impl.StructuredTdImpl
import asmeta.structure.Asm
import org.asmeta.parser.util.ReflectiveVisitor
import asmeta.definitions.domains.PowersetDomain
import asmeta.definitions.domains.SequenceDomain
import asmeta.definitions.domains.BagDomain
import asmeta.definitions.domains.MapDomain

class FunctionToJavaSig extends ReflectiveVisitor<String>  {

	protected Asm res;

	new(Asm resource) {
		this.res = resource
	}
	
	/**
	 * Create an instance of the {@code DomainToJavaSigDef} object.
	 */
	protected def DomainToJavaSigDef createDomainToJavaSigDef(Asm resource) {
		new DomainToJavaSigDef(resource)
	} 
	
	/**
	 * Create an instance of the {@code DomainToJavaString} object.
	 */
	protected def DomainToJavaString createDomainToJavaString(Asm resource) {
		new DomainToJavaString(resource)
	}
	

	// Metodo per costruire le funzioni di tipo statico
	def String visit(StaticFunction object) {

		var StringBuffer function = new StringBuffer
		function.append('''//Static Function
		''')

		// Caso di studio in cui la funzione ha solo il codominio, quindi non presenta variabili in ingresso alla funzione
		if (object.domain === null) {

			if (object.codomain instanceof AbstractTd)
				function.append('''static «returnDomain(object.codomain,true)» «object.name»;
				''')
			// Metodo per tradurre i comandi di tipo: static nomeFunzione: Prod(Tipo1,Tipo2,...)
			else if (object.codomain instanceof ProductDomain)
				function.append('''static «createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»;
				''')
			// Metodo per tradurre i comandi di tipo: static nomeFunzione: Seq(Tipo)
			else if (object.codomain instanceof SequenceDomain)
				function.
					append('''static List«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name» = new ArrayList«createDomainToJavaSigDef(res).visit(object.codomain)»();
					''')
			// Metodo per tradurre i comandi di tipo: static nomeFunzione: Powerset(Tipo)
			else if (object.codomain instanceof PowersetDomain)
				function.
					append('''static Set«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name» = new HashSet«createDomainToJavaSigDef(res).visit(object.codomain)»();
					''')
			// Metodo per tradurre i comandi di tipo: static nomeFunzione: Bag(Tipo)
			else if (object.codomain instanceof BagDomain)
				function.
					append('''static Bag«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name» = new HashBag«createDomainToJavaSigDef(res).visit(object.codomain)»();
					''')
			// Metodo per tradurre i comandi di tipo: static nomeFunzione: Map(Tipo1,Tipo2)
			else if (object.codomain instanceof MapDomain)
				function.
					append('''static Map«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name» = new HashMap«createDomainToJavaSigDef(res).visit(object.codomain)»();
					''')
			else
				function.append('''abstract «returnDomain(object.codomain,false)» «object.name»();
				''')
		} // Nel caso opposto rilevo il codominio, quindi studio la funzione con ingresso e uscita
		else {
			if (object.domain instanceof ProductDomain)
				function.
					append('''abstract «returnDomain(object.codomain,false)» «object.name» («adaptProdDomain(object.domain as ProductDomain, object.name, true)»);
					''')
			else if (object.domain instanceof SequenceDomain)
				function.
					append('''abstract ArrayList«returnDomain(object.codomain,false)» «object.name» (ArrayList«returnParamDefinition(object.domain, object.name,true)»);
					''')
			else
				function.
					append('''abstract «returnDomain(object.codomain,false)» «object.name» («returnParamDefinition(object.domain, object.name,true)»);
					''')

		}
		return function.toString
	}

	def returnParamDefinition(Domain domain, String name, boolean pointer) {

		var int countparameters = 0;
		var sb = new StringBuffer;
		sb.append('''«createDomainToJavaString(res).visit(domain)» param«countparameters»_«name», ''')
		countparameters++
		return sb.toString.substring(0, sb.toString.length - 2)

	}

	def String adaptProdDomain(ProductDomain domain, String name, boolean pointer) {
		var StringBuffer paramDef = new StringBuffer
		var int countparameters = 0;
		paramDef.append("");
		for (var i = 0; i < domain.domains.size; i++) {

			paramDef.append('''«createDomainToJavaString(res).visit(domain.domains.get(i))» param«countparameters»_«name», ''')
			countparameters++
		}
		return paramDef.substring(0, paramDef.length - 2)
	}

	def String returnDomain(Domain domain, boolean pointer) {
		var sb = new StringBuffer;
		if (domain instanceof StructuredTd || domain instanceof StructuredTdImpl)
			sb.append('''«createDomainToJavaSigDef(res).visit(domain)»''')
		else
			sb.append('''«createDomainToJavaString(res).visit(domain)»''')
		return sb.toString
	}

	// Metodo per identificare le funzioni controllate
	def String visit(ControlledFunction object) {
		var StringBuffer function = new StringBuffer
		function.append('''//Controlled Function
		''')
		if (object.domain === null) {
			// product domain
			if (object.codomain instanceof ProductDomain) {
				function.append('''«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»_elem;
				''')
				function.append('''				
					Fun0Ctrl<«returnDomain(object.codomain,false)»> «object.name» = new Fun0Ctrl<>();
				''')
			} else {
				if (object.codomain instanceof SequenceDomain) {

					function.append('''List«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»_elem = new ArrayList<>();
				
			    ''')

					function.append('''				
						Fun0Ctrl<List«returnDomain(object.codomain,false)»> «object.name» = new Fun0Ctrl<>();
						
					''')

				} else if (object.codomain instanceof PowersetDomain) {

					function.append('''Set«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»_elem = new HashSet<>();
				
			    ''')

					function.append('''				
						Fun0Ctrl<Set«returnDomain(object.codomain,false)»> «object.name» = new Fun0Ctrl<>();
						
					''')

				} else if (object.codomain instanceof BagDomain) {

					function.append('''Bag«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»_elem = new HashBag<>();
				
			    ''')

					function.append('''				
						Fun0Ctrl<Bag«returnDomain(object.codomain,false)»> «object.name» = new Fun0Ctrl<>();
						
					''')

				} else if (object.codomain instanceof MapDomain) {

					function.append('''Map«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»_elem = new HashMap<>();
				
			    ''')

					function.append('''				
						Fun0Ctrl<Map«returnDomain(object.codomain,false)»> «object.name» = new Fun0Ctrl<>();
						
					''')

				} else {

					function.append('''				
						Fun0Ctrl <«returnDomain(object.codomain,false)»> «object.name» = new Fun0Ctrl<>();
						
					''')

				}
			}
		} else {

			if (object.domain instanceof ProductDomain && object.codomain !== null) {
				function.append('''«createDomainToJavaSigDef(res).visit(object.domain)» «object.name»_elem;
				''')
			}

			function.append('''
				FunNCtrl<«returnDomain(object.domain,true)», «returnDomain(object.codomain,true)»> «object.name» = new FunNCtrl<>();
				
			''')

		}

		return function.toString
	}

	def String visit(MonitoredFunction object) {
		var StringBuffer function = new StringBuffer

		function.append('''//Monitored Function
		''')

		if (object.domain === null) {

			// Metodo per tradurre i comandi di tipo: monitored nomeFunzione: Prod(Tipo1,Tipo2,...)
			if (object.codomain instanceof ProductDomain) {
				function.append('''«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»_elem;
				''')
				function.append('''				
					Fun0<«returnDomain(object.codomain,false)»> «object.name» = new Fun0<>();
					
				''')
			} else {

				if (object.codomain instanceof SequenceDomain) {

					function.append('''List«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»_elem = new ArrayList<>();
				
			    ''')

					function.append('''				
						Fun0<List«returnDomain(object.codomain,false)»> «object.name» = new Fun0<>();
						
					''')

				} else if (object.codomain instanceof PowersetDomain) {

					function.append('''Set«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»_elem = new HashSet<>();
				
			    ''')

					function.append('''				
						Fun0<Set«returnDomain(object.codomain,false)»> «object.name» = new Fun0<>();
						
					''')

				} else if (object.codomain instanceof BagDomain) {

					function.append('''Bag«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»_elem = new HashBag<>();
				
			    ''')

					function.append('''				
						Fun0<Bag«returnDomain(object.codomain,false)»> «object.name» = new Fun0<>();
						
					''')

				} else if (object.codomain instanceof MapDomain) {

					function.append('''Map«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»_elem = new HashMap<>();
				
			    ''')

					function.append('''				
						Fun0<Map«returnDomain(object.codomain,false)»> «object.name» = new Fun0<>();
						
					''')

				} else if (object.codomain instanceof ConcreteDomain) {
					function.append('''
						Fun0<«returnDomain(object.codomain,false)»> «object.name» = new Fun0<>();
						
						«returnDomain(object.codomain,false)» «object.name»_supporto = new «returnDomain(object.codomain,false)»();
					''')

				} else {
					function.append('''
						Fun0<«returnDomain(object.codomain,false)»> «object.name» = new Fun0<>();
						
					''')

				}

			}

		} else {

			if (object.domain instanceof ProductDomain)
				function.append('''«createDomainToJavaSigDef(res).visit(object.domain)» «object.name»_elem;
				''')

			function.append('''
				FunN<«returnDomain(object.domain,true)», «returnDomain(object.codomain,true)»> «object.name» = new FunN<>();
				
			''')
		}

		return function.toString
	}

	def String visit(OutFunction object) {

		var StringBuffer function = new StringBuffer

		function.append('''//Out Function
		''')

		if (object.domain === null) {

			// Metodo per tradurre i comandi di tipo: out nomeFunzione: Prod(Tipo1,Tipo2,...)
			if (object.codomain instanceof ProductDomain) {
				function.append('''«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»_elem;
				''')

				function.append('''				
					Fun0<«returnDomain(object.codomain,false)»> «object.name» = new Fun0<>();
					
				''')
			} else {
				if (object.codomain instanceof SequenceDomain) {

					function.append('''List«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»_elem = new ArrayList<>();
				
			    ''')

					function.append('''				
						Fun0<List«returnDomain(object.codomain,false)»> «object.name» = new Fun0<>();
						
					''')

				} else if (object.codomain instanceof PowersetDomain) {

					function.append('''Set«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»_elem = new HashSet<>();
				
			    ''')

					function.append('''				
						Fun0<Set«returnDomain(object.codomain,false)»> «object.name» = new Fun0<>();
						
					''')

				} else if (object.codomain instanceof BagDomain) {

					function.append('''Bag«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»_elem = new HashBag<>();
				
			    ''')

					function.append('''				
						Fun0<Bag«returnDomain(object.codomain,false)»> «object.name» = new Fun0<>();
						
					''')

				} else if (object.codomain instanceof MapDomain) {

					function.append('''Map«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»_elem = new HashMap<>();
				
			    ''')

					function.append('''				
						Fun0<Map«returnDomain(object.codomain,false)»> «object.name» = new Fun0<>();
						
					''')

				} else {
					function.append('''
						Fun0<«returnDomain(object.codomain,false)»> «object.name» = new Fun0<>();
						
					''')

				}

			}

		} else {

			if (object.domain instanceof ProductDomain)
				function.append('''«createDomainToJavaSigDef(res).visit(object.domain)» «object.name»_elem;
				''')

			function.append('''
				FunN<«returnDomain(object.domain,true)», «returnDomain(object.codomain,true)»> «object.name» = new FunN<>();
				
			''')

		}

		return function.toString
	}

	def String visit(DerivedFunction object) {

		var StringBuffer function = new StringBuffer

		function.append('''//Derived Function
		''')

		if (object.domain === null) {

			if (object.codomain instanceof AbstractTd)
				function.append('''«returnDomain(object.codomain,true)» «object.name»;
				''')
			// Metodo per tradurre i comandi di tipo: derived nomeFunzione: Prod(Tipo1,Tipo2,...)
			else if (object.codomain instanceof ProductDomain)
				function.append('''«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name»;
				''')
			// Metodo per tradurre i comandi di tipo: derived nomeFunzione: Seq(Tipo)
			else if (object.codomain instanceof SequenceDomain)
				function.
					append('''List«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name» = new ArrayList«createDomainToJavaSigDef(res).visit(object.codomain)»();
					''')
			// Metodo per tradurre i comandi di tipo: derived nomeFunzione: Powerset(Tipo)
			else if (object.codomain instanceof PowersetDomain)
				function.
					append('''Set«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name» = new HashSet«createDomainToJavaSigDef(res).visit(object.codomain)»();
					''')
			// Metodo per tradurre i comandi di tipo: derived nomeFunzione: Bag(Tipo)
			else if (object.codomain instanceof BagDomain)
				function.
					append('''Bag«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name» = new HashBag«createDomainToJavaSigDef(res).visit(object.codomain)»();
					''')
			// Metodo per tradurre i comandi di tipo: derived nomeFunzione: Map(Tipo1,Tipo2)
			else if (object.codomain instanceof MapDomain)
				function.
					append('''Map«createDomainToJavaSigDef(res).visit(object.codomain)» «object.name» = new HashMap«createDomainToJavaSigDef(res).visit(object.codomain)»();
					''')
			else
				function.append('''abstract «returnDomain(object.codomain,false)» «object.name»();
				''')
		} else {
			if (object.domain instanceof ProductDomain)
				function.
					append('''abstract «returnDomain(object.codomain,true)» «object.name» («adaptProdDomain(object.domain as ProductDomain, object.name,true)»);
					''')
			else
				function.
					append('''abstract «returnDomain(object.codomain,true)» «object.name» («returnParamDefinition(object.domain, object.name,true)»);
					''')
		}
		return function.toString
	}

}
