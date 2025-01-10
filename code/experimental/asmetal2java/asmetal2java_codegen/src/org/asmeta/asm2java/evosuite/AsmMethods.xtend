package org.asmeta.asm2java.evosuite

import asmeta.structure.Asm
import asmeta.definitions.ControlledFunction
import asmeta.definitions.MonitoredFunction
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.StaticFunction

/**
 * Contains all the methods to control the translated java class as 
 * an abstract state machine (ASM)
 */
class AsmMethods {
	
	public static val BOOLEAN = "Boolean"
	public static val INTEGER = "Integer"
	public static val STRING = "String"
	
	/** 
	 * Controlled functions getters (public getters)
	 * 
	 * @param asm the Asm specification
	 */
	static def controlledGetter(Asm asm) {
		
		val sb = new StringBuffer;
		
		var asmName = asm.name;
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof ControlledFunction) {
				if (fd.domain === null) { // [] -> ...
					// use the wrapper objects to prevent NullPointerException
					if (fd.codomain instanceof ConcreteDomain) { // [] -> ConcreteDomain
						sb.append('''

								public Integer get_«fd.name»(){
									if(this.execution.«fd.name».get() != null){
										return this.execution.«fd.name».get().value;
									}
									return null;
								}
						 	''');
					} else if (fd.codomain instanceof EnumTd) { // [] -> Enum
						sb.append('''

								public «asmName».«fd.codomain.name» get_«fd.name»(){
									return this.execution.«fd.name».get();
								}
						 	''');
					} else if (fd.codomain instanceof AbstractTd) { // [] -> Abstratc
						sb.append('''

								public String get_«fd.name»(){
									return «asmName».«fd.codomain.name».toString(this.execution.«fd.name».get());
								}
						 	''');
					} else if (fd.codomain.name.equals(BOOLEAN)) { // [] -> Boolean
						sb.append('''

								public Boolean get_«fd.name»(){
									return this.execution.«fd.name».get();
								}
						 	''');
					} else if (fd.codomain.name.equals(INTEGER)) { // [] -> Integer
						sb.append('''

								public Integer get_«fd.name»(){
									return this.execution.«fd.name».get();
								}
						 	''');
					} else if (fd.codomain.name.equals(STRING)) { // [] -> String
						sb.append('''

								public String get_«fd.name»(){
									return this.execution.«fd.name».get();
								}
						 	''');
					} else {
						sb.append('''

								public Object get_«fd.name»(){
									return this.execution.«fd.name».get();
								}
					 		''');
					}
				} else { // getter for the Domain -> Codomain functions
					for(dd : asm.headerSection.signature.domain){
						if(dd.equals(fd.domain)){
							if(dd instanceof EnumTd){ // Enum -> ...
								for (var int i = 0; i < dd.element.size; i++) {
									var symbol = new DomainToJavaStringEvosuite(asm).visit(dd.element.get(i))
									sb.append(System.lineSeparator)
									if(fd.codomain instanceof ConcreteDomain){ // Enum -> ConcreteDomain : consider subsetOf Integer
										sb.append("\t").append('''public Integer get_«fd.name»_«symbol»(){''');
										sb.append(System.lineSeparator)
										sb.append("\t\t").append('''return this.execution.«fd.name».oldValues.get(''');
										sb.append(System.lineSeparator)
										sb.append("\t\t\t").append('''this.execution.«fd.domain.name»_elemsList.get(«i»)).value;''');
										sb.append(System.lineSeparator)
										sb.append("\t").append('''}''');
									} else{ 
										if (fd.codomain.name.equals(INTEGER)){ // Enum -> Integer
											sb.append("\t").append('''public Integer get_«fd.name»_«symbol»(){''');
										}
										else if (fd.codomain.name.equals(BOOLEAN)){ // Enum -> Boolean
											sb.append("\t").append('''public Boolean get_«fd.name»_«symbol»(){''');
										}
										else if (fd.codomain.name.equals(STRING)){ // Enum -> String
											sb.append("\t").append('''public String get_«fd.name»_«symbol»(){''');
										}
										else{ 
											sb.append("\t").append('''public «asmName».«fd.codomain.name» get_«fd.name»_«symbol»(){''');
										}
										sb.append(System.lineSeparator)
										sb.append("\t\t").append('''return this.execution.«fd.name».oldValues.get(''');
										sb.append(System.lineSeparator)
										sb.append("\t\t\t").append('''this.execution.«fd.domain.name»_elemsList.get(«i»));''');
										sb.append(System.lineSeparator)
										sb.append("\t").append('''}''');
									}
									sb.append(System.lineSeparator)
								}
							}// TODO: Ritornare pubblicamente dei valori astratti crea problemi perchè non si possono confrontare
							else if(dd instanceof AbstractTd){ // Abstract -> ...
								for (sf : asm.headerSection.signature.function) {
									if(sf instanceof StaticFunction){
										if(sf.codomain.equals(dd) && sf.domain===null){
											var symbol = sf.name
											sb.append(System.lineSeparator)
											if(fd.codomain instanceof ConcreteDomain){ // Abstract -> ConcreteDomain
												sb.append("\t").append('''public Integer get_«fd.name»_«symbol»(){''');
												sb.append(System.lineSeparator)
												sb.append("\t\t").append('''return this.execution.«fd.name».oldValues.get(''');
												sb.append(System.lineSeparator)
												sb.append("\t\t\t").append('''this.execution.«fd.domain.name»_Class.get(''');
												sb.append(System.lineSeparator)
												sb.append("\t\t\t").append('''this.execution.«fd.domain.name»_elemsList.indexOf("«symbol»")).value);''');
												sb.append(System.lineSeparator)
												sb.append("\t").append('''}''');
											} else{
												if (fd.codomain.name.equals(INTEGER)){ // Abstract -> Integer
													sb.append("\t").append('''public Integer get_«fd.name»_«symbol»(){''');
												}
												else if (fd.codomain.name.equals(BOOLEAN)){ // Abstract -> Boolean
													sb.append("\t").append('''public Boolean get_«fd.name»_«symbol»(){''');
												}
												else if (fd.codomain.name.equals(STRING)){ // Abstract -> String
													sb.append("\t").append('''public Srting get_«fd.name»_«symbol»(){''');
												}
												else{
													sb.append("\t").append('''public «asm.name».«fd.codomain.name» get_«fd.name»_«symbol»(){''');
												}
												sb.append(System.lineSeparator)
												sb.append("\t\t").append('''return this.execution.«fd.name».oldValues.get(''');
												sb.append(System.lineSeparator)
												sb.append("\t\t\t").append('''this.execution.«fd.domain.name»_Class.get(''');
												sb.append(System.lineSeparator)
												sb.append("\t\t\t").append('''this.execution.«fd.domain.name»_elemsList.indexOf("«symbol»")));''');
												sb.append(System.lineSeparator)
												sb.append("\t").append('''}''');
											}
											sb.append(System.lineSeparator)
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return sb.toString;
		
	}

	
	/** 
	 * Monitored functions setters (public setters)
	 * 
	 * @param asm the Asm specification
	 */
	static def monitoredSetters(Asm asm){
		val sb = new StringBuffer;
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof MonitoredFunction) {
				if (fd.domain === null) { // [] -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
					if (fd.codomain instanceof EnumTd) { // [] -> Enum
						sb.append(System.lineSeparator)
						sb.append('''
						public void set_«fd.name»(«asm.name».«fd.codomain.name» «fd.name») {
							this.execution.«fd.name».set(«fd.name»);
							System.out.println("Set «fd.name» = " + «fd.name»);
						}''')
						sb.append(System.lineSeparator)
					}
					else if (fd.codomain instanceof ConcreteDomain) { // [] -> ConcreteDomain
						sb.append(System.lineSeparator)
						sb.append('''
						public void set_«fd.name»(int «fd.name») {
							this.execution.«fd.name».set(
								«asm.name».«fd.codomain.name».valueOf(
								this.execution.«fd.codomain.name»_elems.get(
								«fd.name» - this.execution.«fd.codomain.name»_elems.get(0))));
							System.out.println("Set «fd.name» = " + «fd.name»);
						}''')
						sb.append(System.lineSeparator)
					}
					else if (fd.codomain instanceof AbstractTd) { // [] -> Abstract
						sb.append(System.lineSeparator)
						sb.append('''
						public void set_«fd.name»(String «fd.name») {
							this.execution.«fd.name».set(
							this.execution.«fd.codomain.name»_Class.get(
							this.execution.«fd.codomain.name»_elemsList.indexOf(«fd.name»)));
							System.out.println("Set «fd.name» = " + «fd.name»);
						}''')
				    	sb.append(System.lineSeparator)
					} else { 									// [] -> (Integer|Boolean|String)
						var type = fd.codomain.name;
						switch (type){
							case BOOLEAN:
								type="boolean"
							case INTEGER:
								type="int"
						}
						sb.append(System.lineSeparator)
						sb.append('''
						public void set_«fd.name»(«type» «fd.name») {
							this.execution.«fd.name».set(«fd.name»);
							System.out.println("Set «fd.name» = " + «fd.name»);
						}''')
						sb.append(System.lineSeparator)
					}
				} else { // (Enum|Abstract) -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
					var dd = fd.domain
					if(dd instanceof EnumTd){ // Enum -> ...
						for (var int i = 0; i < dd.element.size; i++) {
							var symbol = new DomainToJavaStringEvosuite(asm).visit(dd.element.get(i))
							if(fd.codomain instanceof ConcreteDomain){ // Enum -> ConcreteDomain 
								sb.append(System.lineSeparator)
								sb.append('''
								public void set_«fd.name»_«symbol»(int «fd.name»_«symbol») {
									this.execution.«fd.name».set(
									«asm.name».«dd.name».«symbol»,
									«asm.name».«fd.codomain.name».valueOf(this.execution.«fd.codomain.name»_elems.get(«fd.name»_«symbol»)));
									System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);
								}''')
								sb.append(System.lineSeparator)
							} else if (fd.codomain instanceof EnumTd) { // Enum -> Enum  (//TODO: test and fix)
								sb.append(System.lineSeparator)
								sb.append('''
								public void set_«fd.name»_«symbol»(«asm.name».«fd.codomain.name» «fd.name»_«symbol») {
									this.execution.«fd.name».set(«asm.name».«dd.name».«symbol», «fd.name»_«symbol»);
									System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);
								}''')
								sb.append(System.lineSeparator)
							} else if (fd.codomain instanceof AbstractTd){ // Enum -> Abstract  (//TODO: test and fix)
								sb.append(System.lineSeparator)
								sb.append('''
								public void set_«fd.name»_«symbol»(String «fd.name»_«symbol») {
									this.execution.«fd.name».set(«asm.name».«dd.name».«symbol», «fd.name»_«symbol»);
									System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);
								}''')
								sb.append(System.lineSeparator)
							} else { // Enum -> (Integer|String|Boolean)
								var type = fd.codomain.name;
								switch (type){
									case BOOLEAN:
										type="boolean"
									case INTEGER:
										type="int"
									}
								sb.append(System.lineSeparator)
								sb.append('''
								public void set_«fd.name»_«symbol»(«type» «fd.name»_«symbol») {
									this.execution.«fd.name».set(«asm.name».«dd.name».«symbol», «fd.name»_«symbol»);
									System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);
								}''');
								sb.append(System.lineSeparator)
							}
						}
					} else if(fd.domain instanceof AbstractTd){ // Abstract -> ...
						for (sf : asm.headerSection.signature.function) {
							if(sf instanceof StaticFunction){
								if(sf.codomain.equals(fd.domain) && sf.domain===null){
									var symbol = sf.name
									if(fd.codomain instanceof ConcreteDomain){ // Abstract -> ConcreteDomain
										sb.append(System.lineSeparator)
										sb.append('''
										public void set_«fd.name»_«symbol»(int «fd.name»_«symbol») {
											this.execution.«fd.name».set(
											this.execution.«fd.domain.name»_Class.get(
											this.execution.«fd.domain.name»_elemsList.indexOf("«symbol»")),
											«asm.name».«fd.codomain.name».valueOf(this.execution.«fd.codomain.name»_elems.get(«fd.name»_«symbol»)));
											System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);
										}''')
									}
									else if (fd.codomain instanceof EnumTd) { // Abstract -> Enum  (//TODO: test and fix)
										sb.append(System.lineSeparator)
										sb.append('''
										public void set_«fd.name»_«symbol»(«asm.name».«fd.codomain.name» «fd.name»_«symbol») {
											this.execution.«fd.name».set(
											this.execution.«fd.domain.name»_Class.get(
											this.execution.«fd.domain.name»_elemsList.indexOf("«symbol»")),«fd.name»_«symbol»);
											System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);
										}''')
										sb.append(System.lineSeparator)
									} else if (fd.codomain instanceof AbstractTd) { // Abstract -> Abstract  (//TODO: test and fix)
										sb.append(System.lineSeparator)
										sb.append('''
										public void set_«fd.name»_«symbol»(String «fd.name»_«symbol») {
											this.execution.«fd.name».set(
											this.execution.«fd.domain.name»_Class.get(
											this.execution.«fd.domain.name»_elemsList.indexOf("«symbol»")),«fd.name»_«symbol»);
											System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);
										}''')
										sb.append(System.lineSeparator)
									} else { // Abstract -> (Integer|String|Boolean)
										var type = fd.codomain.name;
										switch (type){
											case BOOLEAN:
											type="boolean"
										case INTEGER:
											type="int"
											}
										sb.append(System.lineSeparator)
										sb.append('''
										public void set_«fd.name»_«symbol»(«type» «fd.name»_«symbol») {
											this.execution.«fd.name».set(
											this.execution.«fd.domain.name»_Class.get(
											this.execution.«fd.domain.name»_elemsList.indexOf("«symbol»")),«fd.name»_«symbol»);
											System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);
										}''')
										sb.append(System.lineSeparator)
									}
								}
							}
						}
					}
				}
			}
		}
		return sb.toString
	}
	
}