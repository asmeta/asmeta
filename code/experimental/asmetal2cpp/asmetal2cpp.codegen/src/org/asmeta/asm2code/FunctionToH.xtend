package org.asmeta.asm2code

import asmeta.definitions.ControlledFunction
import asmeta.definitions.DerivedFunction
import asmeta.definitions.MonitoredFunction
import asmeta.definitions.OutFunction
import asmeta.definitions.SharedFunction
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.Domain
import asmeta.definitions.domains.ProductDomain
import asmeta.definitions.domains.StructuredTd
import asmeta.definitions.domains.impl.StructuredTdImpl
import asmeta.structure.Asm
import org.asmeta.parser.util.ReflectiveVisitor
import org.asmeta.asm2code.main.TranslatorOptions
import asmeta.structure.DomainDefinition
import asmeta.definitions.domains.ConcreteDomain
import asmeta.terms.basicterms.SetTerm

class FunctionToH extends ReflectiveVisitor<String> {

	Asm res;
	TranslatorOptions options;

	new(Asm resource) {
		this.res = resource
		this.options = new TranslatorOptions(true, false, false,false);
	}
	
	new (Asm resource, TranslatorOptions options) {
		this.res = resource
		this.options = options
	}

	def String visit(StaticFunction object) {
		var StringBuffer function = new StringBuffer
		if (object.domain === null) {
			// static f : C
			if (object.codomain instanceof AbstractTd)
				function.append('''static «returnDomain(object.codomain,true)» «object.name»;
				''')
			else
				function.append('''static «returnDomain(object.codomain,false)» «object.name»();
				''')
		} else {
			if (object.domain instanceof ProductDomain)
				function.
					append('''static «returnDomain(object.codomain,false)» «object.name» («adaptProdDomain(object.domain as ProductDomain, object.name, true)»);''')
			else
				function.
					append('''static «returnDomain(object.codomain,false)» «object.name» («returnParamDefinition(object.domain, object.name,true)»);''')
		}
		return function.toString
	}

	def returnParamDefinition(Domain domain, String name, boolean pointer) {
		var int countparameters = 0;
		var sb = new StringBuffer;
		/*if (domain instanceof StructuredTd || domain instanceof StructuredTdImpl) {
		 * 	sb.append('''«new DomainToH(res).visit(domain)» param«countparameters»_«name», ''')
		 * 	countparameters++
		 } else {*/
		sb.append('''«new ToString(res,pointer).visit(domain)» param«countparameters»_«name», ''')
		countparameters++

		// }
		return sb.toString.substring(0, sb.toString.length - 2)

	}

	def String adaptProdDomain(ProductDomain domain, String name, boolean pointer) {
		var StringBuffer paramDef = new StringBuffer
		var int countparameters = 0;
		paramDef.append("");
		for (var i = 0; i < domain.domains.size; i++) {
			// paramDef.
			// append('''«new ToString(res).visit(variables.get(i).domain)» «updateVarName(variables.get(i).name)», ''')
			paramDef.append('''«new ToString(res,pointer).visit(domain.domains.get(i))» param«countparameters»_«name», ''')
			countparameters++
		}
		return paramDef.substring(0, paramDef.length - 2)
	}

	/*def String returnDomain(Domain domain) {
	 * 	var sb = new StringBuffer;
	 * 	if (domain instanceof StructuredTd || domain instanceof StructuredTdImpl)
	 * 		sb.append('''«new DomainToH(res,true).visit(domain)»''')
	 * 	else
	 * 		sb.append('''«new ToString(res).visit(domain)»''')
	 * 	return sb.toString
	 }*/
	def String returnDomain(Domain domain, boolean pointer) {
		var sb = new StringBuffer;
		if (domain instanceof StructuredTd || domain instanceof StructuredTdImpl)
			sb.append('''«new DomainToH(res,pointer).visit(domain)»''')
		else 
			sb.append('''«new ToString(res,pointer).visit(domain)»''')
		return sb.toString
	}

	def String visit(ControlledFunction object) {
		var StringBuffer function = new StringBuffer

		if (object.domain === null) { // 0-ary function
			if (object.codomain instanceof AbstractTd)
				function.append('''«returnDomain(object.codomain,true)» «object.name»[2];''')
			else
				function.append('''«returnDomain(object.codomain,false)» «object.name»[2];''')
		} else {
			if (!(object.domain instanceof ConcreteDomain) || this.options.useMaps)
			{
				function.
					append(options.stdNamespacePrefix + '''map<«returnDomain(object.domain,true)», «returnDomain(object.codomain,true)»> «object.name»[2];''')
			}
			else
			{
				var ConcreteDomain domain = object.domain as ConcreteDomain
				var SetTerm t = domain.definition.body as SetTerm
				function.
					append('''«returnDomain(object.codomain,false)» «object.name»[2][«t.size»];''')		
			}
		}
		/* 		if (object.arity<= 1) { // only 1 parameter
		 * 			function.
		 * 				append('''map<«returnDomain(object.domain)», «returnDomain(object.codomain)»> «object.name»[2];''')
		 * 		} else // multiple parameter
		 * 			//function.append('''map<boost::tuple<«returnDomain(object.domain)»>, «returnDomain(object.codomain)»> «object.name»[2];''')
		 * 			function.
		 * 				append('''map<«returnDomain(object.domain)», «returnDomain(object.codomain)»> «object.name»[2];''')
		 * 	
		 }*/
		return function.toString
	}

/** TODO: DELETE FOR COVERAGE 	def String visit(SharedFunction object) {
		var StringBuffer function = new StringBuffer

		if (object.domain === null) { // 0-ary function
			if (object.codomain instanceof AbstractTd)
				function.append('''«returnDomain(object.codomain,true)» «object.name»[2];''')
			else
				function.append('''«returnDomain(object.codomain,false)» «object.name»[2];''')
		} else {
			function.
				append('''map<«returnDomain(object.domain,true)», «returnDomain(object.codomain,true)»> «object.name»[2];''')
		}
		* 		if (object.arity<= 1) { // only 1 parameter
		 * 			function.
		 * 				append('''map<«returnDomain(object.domain)», «returnDomain(object.codomain)»> «object.name»[2];''')
		 * 		} else // multiple parameter
		 * 			//function.append('''map<boost::tuple<«returnDomain(object.domain)»>, «returnDomain(object.codomain)»> «object.name»[2];''')
		 * 			function.
		 * 				append('''map<«returnDomain(object.domain)», «returnDomain(object.codomain)»> «object.name»[2];''')
		 * 	
		 }*
		return function.toString
	}*/

	def String visit(MonitoredFunction object) {
		var StringBuffer function = new StringBuffer

		if (object.domain === null) { // 0-ary function
		if (object.codomain instanceof AbstractTd)
			function.append('''«returnDomain(object.codomain,true)» «object.name»;''')
			else
			function.append('''«returnDomain(object.codomain,false)» «object.name»;''')
		} else {
			if (!(object.domain instanceof ConcreteDomain) || this.options.useMaps)
			{
				function.
					append(options.stdNamespacePrefix + '''map<«returnDomain(object.domain,true)», «returnDomain(object.codomain,true)»> «object.name»;''')		
			}
			else
			{
				var ConcreteDomain domain = object.domain as ConcreteDomain
				var SetTerm t = domain.definition.body as SetTerm
				function.
					append('''«returnDomain(object.codomain,false)» «object.name»[«t.size»];''')		
			}
		}
		/* 		if (object.arity<= 1) { // only 1 parameter
		 * 			function.
		 * 				append('''map<«returnDomain(object.domain)», «returnDomain(object.codomain)»> «object.name»[2];''')
		 * 		} else // multiple parameter
		 * 			//function.append('''map<boost::tuple<«returnDomain(object.domain)»>, «returnDomain(object.codomain)»> «object.name»[2];''')
		 * 			function.
		 * 				append('''map<«returnDomain(object.domain)», «returnDomain(object.codomain)»> «object.name»[2];''')
		 * 	
		 }*/
		return function.toString
	}

	def String visit(OutFunction object) {
		var StringBuffer function = new StringBuffer

		if (object.domain === null) { // 0-ary function
		
		if (object.codomain instanceof AbstractTd)
			function.append('''«returnDomain(object.codomain,true)» «object.name»;''')
		else
			function.append('''«returnDomain(object.codomain,false)» «object.name»;''')
		} else {
			if (this.options.useMaps)
			{
				function.
					append(options.stdNamespacePrefix + '''map<«returnDomain(object.domain,true)», «returnDomain(object.codomain,true)»> «object.name»;''')
			}
			else
			{
				var ConcreteDomain domain = object.domain as ConcreteDomain
				var SetTerm t = domain.definition.body as SetTerm
				function.
					append('''«returnDomain(object.codomain,false)» «object.name»[«t.size»];''')		
			}
		}
		/* 		if (object.arity<= 1) { // only 1 parameter
		 * 			function.
		 * 				append('''map<«returnDomain(object.domain)», «returnDomain(object.codomain)»> «object.name»[2];''')
		 * 		} else // multiple parameter
		 * 			//function.append('''map<boost::tuple<«returnDomain(object.domain)»>, «returnDomain(object.codomain)»> «object.name»[2];''')
		 * 			function.
		 * 				append('''map<«returnDomain(object.domain)», «returnDomain(object.codomain)»> «object.name»[2];''')
		 * 	
		 }*/
		return function.toString
	}

	def String visit(DerivedFunction object) {
		var StringBuffer function = new StringBuffer
		if (object.domain === null) {
		if (object.codomain instanceof AbstractTd)
			function.append('''«returnDomain(object.codomain,true)» «object.name»;
			''')
			else
			function.append('''«returnDomain(object.codomain,false)» «object.name»();
			''')
		} else {
			if (object.domain instanceof ProductDomain)
				function.
					append('''«returnDomain(object.codomain,true)» «object.name» («adaptProdDomain(object.domain as ProductDomain, object.name,true)»);''')
			else
				function.
					append('''«returnDomain(object.codomain,true)» «object.name» («returnParamDefinition(object.domain, object.name,true)»);''')
		}
		return function.toString
	}

}
