package org.asmeta.asm2java

import asmeta.structure.Asm
import org.asmeta.parser.util.ReflectiveVisitor
import asmeta.definitions.ControlledFunction
import asmeta.definitions.MonitoredFunction
import asmeta.definitions.StaticFunction
import asmeta.definitions.DerivedFunction
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.domains.SequenceDomain
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.ProductDomain
import asmeta.terms.furtherterms.CaseTerm
import asmeta.terms.furtherterms.ForallTerm
import asmeta.definitions.domains.BasicTd

class FunctionToJavaDef extends ReflectiveVisitor<String> {

	Asm asm

	int i

	new(Asm asm) {
		this.asm = asm
	}

	def String visit(ControlledFunction object) {
		var StringBuffer sb = new StringBuffer

		if (object.codomain instanceof SequenceDomain || object.domain instanceof SequenceDomain) {
			sb.append('''
				controllo lista
			''')
		} else {
			if (object.domain !== null) {
				for (var i = 0; i < object.initialization.get(0).variable.size; i++) {

					// Controllo quali domini sono implementabili, quindi traducibili nel linguaggio di riferimento
					/* */
					if (new Util().isNotNumerable((object.initialization.get(0).variable.get(i).domain))) {
						// In questo caso sono comandi non accettati
						sb.append('''
							//NOT IMPLEMENTED IN Java (FunctionToCpp line 50)
						''')

						return sb.toString
					}

					// Per tutti gli altri tipi di comando avviene l'inizializazzione
					if (object.initialization.get(0).variable.get(i).domain instanceof ConcreteDomain)
						sb.append('''
							
							for(int «new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»=0; «new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))» < «new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)».elems.size(); «new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»++ ){
								
								
								«new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)»_elem.value = «new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)».elems.get(«new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»);
							
						''')
					// Controllo se il dominio » di tipo Astratto
					else if (object.initialization.get(0).variable.get(i).domain instanceof AbstractTd)
						sb.append('''
							for(«new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)» «new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»: «new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)».elems){
						''')
					else if (object.initialization.get(0).variable.get(i).domain instanceof EnumTd)
						sb.append('''
							for(«new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)» «new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»: «new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)».values()){
						''')
					// Se il dominio » astratto
					else
						sb.append('''
							for(«new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)» «new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»: «new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)».elems){
						''')

				}

				if (object.codomain instanceof AbstractTd) {
					var a = new TermToJava(asm).visit(object.initialization.get(0).body)
					var b = new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))

					if (a.equals(b))
						sb.append('''
							«object.name».oldValues.put(«new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»,«new TermToJava(asm).visit(object.initialization.get(0).body)»);
							«object.name».newValues.put(«new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»,«new TermToJava(asm).visit(object.initialization.get(0).body)»);
						''')
					else
						sb.
							append('''«new ToString(asm).visit(object.codomain)» «new TermToJava(asm).visit(object.initialization.get(0).body)» = new «new ToString(asm).visit(object.codomain)»("«new TermToJava(asm).visit(object.initialization.get(0).body)»");
				

				      «object.name».oldValues.put(«new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»,«new TermToJava(asm).visit(object.initialization.get(0).body)»);
				      «object.name».newValues.put(«new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»,«new TermToJava(asm).visit(object.initialization.get(0).body)»);
				''')

				} else if (controllo(object.codomain.name) || object.codomain instanceof EnumTd) {
					if (object.domain instanceof ConcreteDomain && controllo(object.codomain.name)) {
						if ((object.domain as ConcreteDomain).typeDomain instanceof BasicTd) {
							sb.
								append('''«new ToString(asm).visit(object.codomain)» a «new TermToJavaConditionalAbs(asm).visit(object.initialization.get(0).body)»;
					
					      «object.name».oldValues.put(«object.domain.name»_elem.value,a);
					      «object.name».newValues.put(«object.domain.name»_elem.value,a);
					''')
						} else {
							sb.
								append('''«new ToString(asm).visit(object.codomain)» a «new TermToJavaConditionalAbs(asm).visit(object.initialization.get(0).body)»;
					
					      «object.name».oldValues.put(«object.domain.name»_elem,a);
					      «object.name».newValues.put(«object.domain.name»_elem,a);
					''')
						}
					}
					else
						sb.
							append('''«new ToString(asm).visit(object.codomain)» a «new TermToJavaConditionalAbs(asm).visit(object.initialization.get(0).body)»;
				
				      «object.name».oldValues.put(«new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»,a);
				      «object.name».newValues.put(«new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»,a);
				''')
				} else {
					// In ogni caso si studia lo sviluppo delle definizioni delle funzioni
					sb.
						append('''«new ToString(asm).visit(object.codomain)» a = new «new ToString(asm).visit(object.codomain)»();
			    
			           a.value «new TermToJavaConditionalAbs(asm).visit(object.initialization.get(0).body)»;
			           ''')

					if (object.domain instanceof ProductDomain) {
						sb.append(''' «object.name»_elem = new ''')

						switch (object.initialization.get(0).variable.size) {
							case 2: {
								sb.append('''Pair<''')

							}
							case 3: {
								sb.append('''Triplet<''')

							}
							case 4: {
								sb.append('''Quartet<''')

							}
							case 5: {
								sb.append('''Quintet<''')

							}
							case 6: {
								sb.append('''Sextet<''')

							}
							case 7: {
								sb.append('''Septet<''')

							}
							case 8: {
								sb.append('''Octet<''')

							}
							case 9: {
								sb.append('''Ennead<''')

							}
							case 10: {
								sb.append('''Decade<''')

							}
						}

						for (var i = 0; i < object.initialization.get(0).variable.size; i++) {
							if (i != object.initialization.get(0).variable.size - 1)
								sb.
									append('''«new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)»,''')
							else
								sb.
									append('''«new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)»>(''')

						}

						for (var i = 0; i < object.initialization.get(0).variable.size; i++) {
							if (i != object.initialization.get(0).variable.size - 1)
								sb.
									append('''«new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)»_elem,''')
							else
								sb.
									append('''«new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)»_elem);
									''')

						}

						sb.append('''    
								«object.name».oldValues.put(«object.name»_elem,a);
								«object.name».newValues.put(«object.name»_elem,a);
							''')
					} else {
						sb.append('''    
							«object.name».oldValues.put(«new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»,a);
							«object.name».newValues.put(«new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»,a);
						''')
					}

				}

				for (var i = 0; i < object.initialization.get(0).variable.size; i++)
					sb.append('''}''')
			} // Caso nel quale il dominio » nullo, quindi presente solo il codominio
			else {

				if (controllo(object.codomain.name) || object.codomain instanceof EnumTd) {
					sb.append('''
						«object.name».oldValue = «object.name».newValue = «new TermToJava(asm).visit(object.initialization.get(0).body)»;
					''')
				} else
					sb.append('''		
						
						«object.codomain.name»_elem.value = «new TermToJava(asm).visit(object.initialization.get(0).body)»;
						
						«object.name».oldValue = «object.name».newValue = «object.codomain.name»_elem;
						
					''')
			}

		}

		return sb.toString
	}

	def String visit(MonitoredFunction object) {
		var StringBuffer sb = new StringBuffer
		if (object.domain !== null) {
			for (var i = 0; i < object.initialization.get(0).variable.size; i++) {

				if (object.initialization.get(0).variable.get(i).domain instanceof ConcreteDomain)
					sb.append('''
						
						for(int «new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»=0; «new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))» < «new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)».elems.size()-1; «new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»++ ){
							
							«new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)» «new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»Val = new «new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)»();
							«new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»Val.value = «new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)».elems.get(«new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»);
						
					''')
				else if (object.initialization.get(0).variable.get(i).domain instanceof AbstractTd) {
					sb.append('''
						for(«new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)» «new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»: «new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)».elems){
					''')

				} else if (object.initialization.get(0).variable.get(i).domain instanceof EnumTd)
					sb.append('''
						for(«new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)» «new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»: «new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)».values()){
					''')
				else
					sb.append('''
						for(«new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)» «new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»: «new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)».elems){
					''')
			}

			if (object.codomain instanceof AbstractTd) {
				var a = new TermToJava(asm).visit(object.initialization.get(0).body)
				var b = new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))

				if (a.equals(b))
					sb.
						append('''«object.name».Values.put(«new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»,«new TermToJava(asm).visit(object.initialization.get(0).body)»);
						''')
				else
					sb.
						append('''«new ToString(asm).visit(object.codomain)» «new TermToJava(asm).visit(object.initialization.get(0).body)» = new «new ToString(asm).visit(object.codomain)»("«new TermToJava(asm).visit(object.initialization.get(0).body)»");
				
				      

				      
				      «object.name».Values.put(«new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»,«new TermToJava(asm).visit(object.initialization.get(0).body)»);
				''')
			} else if (controllo(object.codomain.name) || object.codomain instanceof EnumTd) {
				sb.
					append('''«new ToString(asm).visit(object.codomain)» a «new TermToJavaConditionalAbs(asm).visit(object.initialization.get(0).body)»;
				
				      

				      
				      «object.name».Values.put(«new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»,a);
				''')
			} else {
				// In ogni caso si studia lo sviluppo delle definizioni delle funzioni
				sb.
					append('''«new ToString(asm).visit(object.codomain)» a = new «new ToString(asm).visit(object.codomain)»();
			    
			           a.value «new TermToJavaConditionalAbs(asm).visit(object.initialization.get(0).body)»;
			           ''')

				if (object.domain instanceof ProductDomain) {
					sb.append(''' «object.name»_elem = new ''')

					switch (object.initialization.get(0).variable.size) {
						case 2: {
							sb.append('''Pair<''')

						}
						case 3: {
							sb.append('''Triplet<''')

						}
						case 4: {
							sb.append('''Quartet<''')

						}
						case 5: {
							sb.append('''Quintet<''')

						}
						case 6: {
							sb.append('''Sextet<''')

						}
						case 7: {
							sb.append('''Septet<''')

						}
						case 8: {
							sb.append('''Octet<''')

						}
						case 9: {
							sb.append('''Ennead<''')

						}
						case 10: {
							sb.append('''Decade<''')

						}
					}

					for (var i = 0; i < object.initialization.get(0).variable.size; i++) {
						if (i != object.initialization.get(0).variable.size - 1)
							sb.
								append('''«new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)»,''')
						else
							sb.
								append('''«new ToString(asm).visit(object.initialization.get(0).variable.get(i).domain)»>(''')

					}

					for (var i = 0; i < object.initialization.get(0).variable.size; i++) {
						if (i != object.initialization.get(0).variable.size - 1)
							sb.
								append('''«new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»Val,''')
						else
							sb.append('''«new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»Val);
							''')

					}

					sb.append('''    
						«object.name».Values.put(«object.name»_elem,a);
					''')

				}

				sb.append('''    
					«object.name».Values.put(«new TermToJava(asm).visit(object.initialization.get(0).variable.get(i))»,a);
				''')
			}

			for (var i = 0; i < object.initialization.get(0).variable.size; i++)
				sb.append('''}''')
		} else {

			if (controllo(object.codomain.name) || object.codomain instanceof EnumTd) {
				sb.append('''
					«object.name».Value = «new TermToJava(asm).visit(object.initialization.get(0).body)»;
				''')
			} else {
				sb.append('''		
					
					«object.codomain.name»_elem.value = «new TermToJava(asm).visit(object.initialization.get(0).body)»;
					
					«object.name».Value = «object.name».Value = «object.codomain.name»_elem;
					
				''')

			}
		}
		return sb.toString
	}

	def String visit(DerivedFunction object) {
		var StringBuffer sb = new StringBuffer

		if (object.domain !== null) {

			if (object.definition.body instanceof CaseTerm) {
				sb.
					append('''«new ToString(asm).visit(object.codomain)» «object.name»(«new Util().adaptRuleParam(object.definition.variable,asm)»){ «new TermToJava(asm).visit(object.definition.body)»}''')
			} else if (object.definition.body instanceof ForallTerm) {
				sb.
					append('''«new ToString(asm).visit(object.codomain)» «object.name»( return «new Util().adaptRuleParam(object.definition.variable,asm)»){ «new TermToJava(asm).visit(object.definition.body)»}''')
			} else {

				if (object.codomain instanceof ConcreteDomain) {
					sb.
						append('''«new ToString(asm).visit(object.codomain)» «object.name»(«new Util().adaptRuleParam(object.definition.variable,asm)»){
					
					«object.codomain.name» supp = new «object.codomain.name»();
					supp.value = «new TermToJava(asm).visit(object.definition.body)»;
					
					return supp;
				}''')

				} else
					sb.
						append('''«new ToString(asm).visit(object.codomain)» «object.name»(«new Util().adaptRuleParam(object.definition.variable,asm)»){return «new TermToJava(asm).visit(object.definition.body)»;}''')

			}
		} else {

			if (object.definition.body instanceof CaseTerm) {
				sb.
					append('''«new ToString(asm).visit(object.codomain)» «object.name»(){ «new TermToJava(asm).visit(object.definition.body)»}''')
			} else if (object.definition.body instanceof ForallTerm) {
				sb.
					append('''«new ToString(asm).visit(object.codomain)» «object.name»(){ return «new TermToJava(asm).visit(object.definition.body)»}''')
			} else {

				if (object.codomain instanceof ConcreteDomain) {
					sb.append('''«new ToString(asm).visit(object.codomain)» «object.name»(){
					
					«object.codomain.name»_elem.value = «new TermToJava(asm).visit(object.definition.body)»;
					
					return «object.codomain.name»_elem;
				}''')

				} else
					sb.
						append('''«new ToString(asm).visit(object.codomain)» «object.name»(){return «new TermToJava(asm).visit(object.definition.body)»;}''')

			}
		}
		return sb.toString
	}

	def String visit(StaticFunction object) {
		var StringBuffer sb = new StringBuffer

		if (object.domain !== null) {

			if (object.definition.body instanceof CaseTerm) {
				sb.
					append('''«new ToString(asm).visit(object.codomain)» «object.name»(«new Util().adaptRuleParam(object.definition.variable,asm)»){ «new TermToJava(asm).visit(object.definition.body)»}''')
			} else if (object.definition.body instanceof ForallTerm) {
				sb.
					append('''«new ToString(asm).visit(object.codomain)» «object.name»( return «new Util().adaptRuleParam(object.definition.variable,asm)»){ «new TermToJava(asm).visit(object.definition.body)»}''')
			} else {

				if (object.codomain instanceof ConcreteDomain) {
					sb.
						append('''«new ToString(asm).visit(object.codomain)» «object.name»(«new Util().adaptRuleParam(object.definition.variable,asm)»){
					
					«object.codomain.name» supp = new «object.codomain.name»();
					supp.value = «new TermToJava(asm).visit(object.definition.body)»;
					
					return supp;
				}''')

				} else if (object.codomain instanceof SequenceDomain) {
					sb.
						append('''ArrayList«new ToString(asm).visit(object.codomain)» «object.name»(ArrayList«new Util().adaptRuleParam(object.definition.variable,asm)»){return «new TermToJava(asm).visit(object.definition.body)»;}''')
				} else
					sb.
						append('''«new ToString(asm).visit(object.codomain)» «object.name»(«new Util().adaptRuleParam(object.definition.variable,asm)»){return «new TermToJava(asm).visit(object.definition.body)»;}''')

			}
		} else {

			if (object.definition.body instanceof CaseTerm) {
				sb.
					append('''«new ToString(asm).visit(object.codomain)» «object.name»(){ «new TermToJava(asm).visit(object.definition.body)»}''')
			} else if (object.definition.body instanceof ForallTerm) {
				sb.
					append('''«new ToString(asm).visit(object.codomain)» «object.name»(){ return «new TermToJava(asm).visit(object.definition.body)»}''')
			} else {

				if (object.codomain instanceof ConcreteDomain) {
					sb.append('''«new ToString(asm).visit(object.codomain)» «object.name»(){
					
					«object.codomain.name» supp = new «object.codomain.name»();
					
					supp.value = «new TermToJava(asm).visit(object.definition.body)»;
					
					return supp;
				}''')

				} else
					sb.
						append('''«new ToString(asm).visit(object.codomain)» «object.name»(){return «new TermToJava(asm).visit(object.definition.body)»;}''')

			}
		}
		return sb.toString
	}

	def Boolean controllo(String domain) {
		if (domain.equals("Integer") || domain.equals("String") || domain.equals("Boolean"))
			return true
		else
			return false
	}

}
