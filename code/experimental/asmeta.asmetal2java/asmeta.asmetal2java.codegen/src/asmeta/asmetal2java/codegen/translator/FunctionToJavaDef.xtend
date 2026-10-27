package asmeta.asmetal2java.codegen.translator

import asmeta.definitions.ControlledFunction
import asmeta.definitions.DerivedFunction
import asmeta.definitions.DynamicFunction
import asmeta.definitions.MonitoredFunction
import asmeta.definitions.OutFunction
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.BagDomain
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.Domain
import asmeta.definitions.domains.BooleanDomain
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.domains.ProductDomain
import asmeta.definitions.domains.MapDomain
import asmeta.definitions.domains.PowersetDomain
import asmeta.definitions.domains.SequenceDomain
import asmeta.structure.Asm
import asmeta.terms.basicterms.Term
import asmeta.terms.basicterms.VariableTerm
import asmeta.terms.furtherterms.ForallTerm
import asmeta.terms.furtherterms.SequenceTerm
import org.asmeta.parser.util.ReflectiveVisitor
import java.util.ArrayList
import java.util.HashSet
import java.util.List
import java.util.Set

class FunctionToJavaDef extends ReflectiveVisitor<String> {

	Asm asm

	new(Asm asm) {
		this.asm = asm
	}

	def String visit(SequenceTerm object) {
		var StringBuffer sb = new StringBuffer
		for (var index = 0; index < object.terms.size; index++) {
			if (index > 0)
				sb.append(",")
			val Term term = object.terms.get(index)
			if (term instanceof SequenceTerm)
				sb.append("new ArrayList<>(Arrays.asList(" + visit(term) + "))")
			else
				sb.append(new TermToJava(asm).visit(term))
		}
		return sb.toString()
	}

	def String visit(ControlledFunction object) {
		return visitControlledOrOutputFunction(object)
	}

	def String visit(OutFunction object) {
		return visitControlledOrOutputFunction(object)
	}

	private def String visitControlledOrOutputFunction(DynamicFunction object) {
		return dynamicInitialization(object)
	}

	def String visit(MonitoredFunction object) {
		return dynamicInitialization(object)
	}

	/** Initialize dynamic functions using their declared key and value types. */
	private def String dynamicInitialization(DynamicFunction function) {
		val initialization = function.initialization.get(0)
		val translator = new TermToJava(asm)
		val result = new StringBuilder
		val List<VariableTerm> variables = initialization.variable
		val Set<String> names = new HashSet
		for (variable : variables)
			names.add(translator.visit(variable))
		var loopCount = 0
		for (variable : variables) {
			if (!isEnumerableInitializationDomain(variable.domain))
				// Infinite domains cannot be enumerated to initialize a finite Java map.
				return "// Function initialization over " + variable.domain.name + " is not enumerable.\n"
			loopCount += bindInitializationVariable(variable.domain, translator.visit(variable), names, result)
		}

		val value = ArgumentToJava.value(initialization.body, function.codomain, translator)
		var key = ""
		if (!variables.empty) {
			if (variables.size == 1)
				key = ArgumentToJava.value(variables.get(0), function.domain, translator)
			else {
				val product = function.domain as ProductDomain
				key = ProductToJava.value(product, [ index |
					ArgumentToJava.value(variables.get(index), product.domains.get(index), translator)
				])
			}
		}
		result.append(function.name)
		if (function instanceof MonitoredFunction)
			result.append(if (variables.empty) ".value = " + value else ".values.put(" + key + ", " + value + ")")
		else
			result.append(".init(").append(if (variables.empty) value else key + ", " + value).append(")")
		result.append(";\n")
		result.append("}\n".repeat(loopCount))
		return result.toString()
	}

	private def boolean isEnumerableInitializationDomain(Domain domain) {
		if (domain instanceof ProductDomain)
			return domain.domains.forall[isEnumerableInitializationDomain(it)]
		return domain instanceof ConcreteDomain || domain instanceof AbstractTd ||
			domain instanceof EnumTd || domain instanceof BooleanDomain
	}

	/** Open nested loops and bind a variable to each value of a finite domain. */
	private def int bindInitializationVariable(Domain domain, String name, Set<String> names, StringBuilder result) {
		if (domain instanceof ProductDomain) {
			val List<String> components = new ArrayList
			var loops = 0
			for (component : domain.domains) {
				val componentName = freshInitializationName(name + "_component" + components.size, names)
				components.add(componentName)
				loops += bindInitializationVariable(component, componentName, names, result)
			}
			result.append(Util.javaType(domain, asm)).append(" ").append(name).append(" = ")
				.append(ProductToJava.value(domain, [ index | components.get(index) ])).append(";\n")
			return loops
		}
		val elementType = if (domain instanceof ConcreteDomain) domain.typeDomain else domain
		val elements = if (domain instanceof EnumTd) domain.name + ".values()"
			else if (domain instanceof BooleanDomain) "java.util.Arrays.asList(false, true)"
			else domain.name + ".elems"
		val loopVariable = if (domain instanceof ConcreteDomain) freshInitializationName(name + "_initValue", names) else name
		result.append("for (").append(Util.javaType(elementType, asm)).append(" ")
			.append(loopVariable).append(" : ").append(elements).append(") {\n")
		if (domain instanceof ConcreteDomain) {
			// Bind a separate wrapper for each iteration, never mutate an existing map key.
			result.append(domain.name).append(" ").append(name).append(" = ")
				.append(domain.name).append(".valueOf(").append(loopVariable).append(");\n")
		}
		return 1
	}

	private def String freshInitializationName(String baseName, Set<String> names) {
		var name = baseName
		while (!names.add(name))
			name += "_"
		return name
	}

	def String visit(DerivedFunction object) {
		var StringBuffer sb = new StringBuffer

		if (object.domain !== null) {

			if (object.definition.body instanceof ForallTerm) {
				sb.
					append('''«Util.javaType(object.codomain, asm)» «object.name»(«new Util().adaptRuleParam(object.definition.variable,asm)»){ return «new TermToJava(asm).visit(object.definition.body)»;}''')
			} else {

				if (object.codomain instanceof ConcreteDomain) {
					sb.
						append('''«new DomainToJavaString(asm).visit(object.codomain)» «object.name»(«new Util().adaptRuleParam(object.definition.variable,asm)»){
					
					«object.codomain.name» supp = new «object.codomain.name»();
					supp.value = «new TermToJava(asm).visit(object.definition.body)»;
					
					return supp;
				}''')

				} else
					sb.
						append('''«Util.javaType(object.codomain, asm)» «object.name»(«new Util().adaptRuleParam(object.definition.variable,asm)»){ return «new TermToJava(asm).visit(object.definition.body)»;}''')
			}
		} else {

			if (object.definition.body instanceof ForallTerm) {
				sb.
					append('''«Util.javaType(object.codomain, asm)» «object.name»(){ return «new TermToJava(asm).visit(object.definition.body)»;}''')
			} else {

				if (object.codomain instanceof ConcreteDomain) {
					sb.append('''«new DomainToJavaString(asm).visit(object.codomain)» «object.name»(){
					
					«object.codomain.name»_elem.value = «new TermToJava(asm).visit(object.definition.body)»;
					
					return «object.codomain.name»_elem;
				}''')

				} else
					sb.
						append('''«Util.javaType(object.codomain, asm)» «object.name»(){ return «new TermToJava(asm).visit(object.definition.body)»;}''')
			}
		}
		return sb.toString
	}

	def String visit(StaticFunction object) {
	    
		var StringBuffer sb = new StringBuffer

		if (object.domain !== null) {
            
			if (object.definition.body instanceof ForallTerm) {
				sb.
					append('''«Util.javaType(object.codomain, asm)» «object.name»(«new Util().adaptRuleParam(object.definition.variable,asm)»){ return «new TermToJava(asm).visit(object.definition.body)»;}''')
			} else {
                
				if (object.codomain instanceof ConcreteDomain) {
					sb.
						append('''«new DomainToJavaString(asm).visit(object.codomain)» «object.name»(«new Util().adaptRuleParam(object.definition.variable,asm)»){
					
					«object.codomain.name» supp = new «object.codomain.name»();
					supp.value = «new TermToJava(asm).visit(object.definition.body)»;
					
					return supp;
				}''')

				} else if (object.codomain instanceof SequenceDomain || object.codomain instanceof PowersetDomain ||
					object.codomain instanceof BagDomain || object.codomain instanceof MapDomain) {
					sb.append('''«Util.javaType(object.codomain, asm)» «object.name»(«new Util().adaptRuleParam(object.definition.variable,asm)»){return «new TermToJava(asm).visit(object.definition.body)»;}''')
				} else{
					sb.append('''«Util.javaType(object.codomain, asm)» «object.name»(«new Util().adaptRuleParam(object.definition.variable,asm)»){ return «new TermToJava(asm).visit(object.definition.body)»;}''')
			
                }
			}
		} else {
               
			if (object.definition.body instanceof ForallTerm) {
				sb.
					append('''«Util.javaType(object.codomain, asm)» «object.name»(){ return «new TermToJava(asm).visit(object.definition.body)»;}''')
			} else {

				if (object.codomain instanceof ConcreteDomain) {
				    
					sb.append('''«new DomainToJavaString(asm).visit(object.codomain)» «object.name»(){
					
					«object.codomain.name» supp = new «object.codomain.name»();
					
					supp.value = «new TermToJava(asm).visit(object.definition.body)»;
					
					return supp;
				}''')

				} else if (object.codomain instanceof SequenceDomain || object.codomain instanceof PowersetDomain ||
					object.codomain instanceof BagDomain || object.codomain instanceof MapDomain)
					sb.append('''«Util.javaType(object.codomain, asm)» «object.name»(){ return «new TermToJava(asm).visit(object.definition.body)»;}''')
				else
					sb.
						append('''«Util.javaType(object.codomain, asm)» «object.name»(){ return «new TermToJava(asm).visit(object.definition.body)»;}''')
			
			}
		}
		return sb.toString
	}

	def Boolean controllo(String domain) {
		if (domain.equals("Integer") || domain.equals("Natural") || domain.equals("String") ||
			domain.equals("Boolean") || domain.equals("Real"))
			return true
		else
			return false
	}

}
