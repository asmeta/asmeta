package asmeta.asmetal2java.codegen.evosuite

import asmeta.definitions.ControlledFunction
import asmeta.definitions.MonitoredFunction
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.EnumTd
import asmeta.structure.Asm
import asmeta.asmetal2java.codegen.translator.TermToJava
import asmeta.asmetal2java.codegen.translator.DomainToJavaString
import asmeta.definitions.domains.SequenceDomain
import asmeta.asmetal2java.codegen.config.TranslatorOptions

/**
 * Contains all the methods to control the translated java class as 
 * an abstract state machine (ASM)
 */
class AsmMethods {

	public static val BOOLEAN = AsmMethodsUtil.BOOLEAN
	public static val INTEGER = AsmMethodsUtil.INTEGER
	public static val REAL = AsmMethodsUtil.REAL
	public static val STRING = AsmMethodsUtil.STRING
	public static val CHAR = AsmMethodsUtil.CHAR
	public static val NATURAL = AsmMethodsUtil.NATURAL

	/** 
	 * Controlled functions getters (public getters)
	 * 
	 * @param asm the Asm specification
	 */
	static def controlledGetter(Asm asm, TranslatorOptions translatorOptions) {

		val sb = new StringBuffer;

		var asmName = asm.name;
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof ControlledFunction) {
				sb.append(System.lineSeparator)
				if (fd.domain === null) { // [] -> ...
				// use the wrapper objects to prevent NullPointerException
					if (fd.codomain instanceof ConcreteDomain) { // [] -> ConcreteDomain
						var type = AsmMethodsUtil.getConcreteDomainType(asm, fd, fd.codomain.name)
						sb.append('''
							public «type» get_«fd.name»(){
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
					} else if (fd.codomain instanceof AbstractTd) { // [] -> Abstract
					// return "abstract_" + value for differentiate from string
						sb.append('''
							public String get_«fd.name»(){
								String value = this.execution.«fd.name».get().toString();
								return value != null ? "abstract_" + value : null;
							}
						''');
					} else if (fd.codomain instanceof SequenceDomain) { // [] -> Sequence
						var type = new DomainToJavaString(asm).visit(fd.codomain).replaceAll("<", "").
							replaceAll(">", "").trim()
						if (AsmMethodsUtil.basicTdList.contains(type)) {
							// Seq(BasicTD) example: seq(Integer)
							type = AsmMethodsUtil.getWrapperBasicTdType(type)
							sb.append(AsmMethodsUtil.genSequenceGetter(fd.name, type))
						} else {
							// search the domain (EnumTd or AbstractTd)
							for (cd : asm.headerSection.signature.domain) {
								if (cd.name.equals(type)) {
									if (cd instanceof EnumTd) { // Seq(EnumDomain)
										type = asm.name.concat(".").concat(type)
										sb.append(AsmMethodsUtil.genSequenceGetter(fd.name, type))
									} else if (cd instanceof AbstractTd) { // Seq(AbstractDomain)
										type = asm.name.concat(".").concat(type)
										sb.append(AsmMethodsUtil.genSequenceGetter(fd.name, type))
									}
								}
							}
						}
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
					} else if (fd.codomain.name.equals(REAL)) { // [] -> Real
						sb.append('''
							public Double get_«fd.name»(){
								return this.execution.«fd.name».get();
							}
						''');
					} else if (fd.codomain.name.equals(STRING)) { // [] -> String
						sb.append('''
							public String get_«fd.name»(){
								return this.execution.«fd.name».get();
							}
						''');
					} else if (fd.codomain.name.equals(CHAR)) { // [] -> Char
						sb.append('''
							public Character get_«fd.name»(){
								return this.execution.«fd.name».get();
							}
						''');
					} else if (fd.codomain.name.equals(NATURAL)) { // [] -> Natural
						sb.append('''
							public Integer get_natural_«fd.name»(){
								return this.execution.«fd.name».get();
							}
						''');
					} else {
						// Codomain not supported
						manageNotSupportedDomain(translatorOptions, fd.codomain.name)
					}
				} else { // getter for the Domain -> Codomain functions
				// add the marker _fromDomain_<domainName> to the getter name
					for (dd : asm.headerSection.signature.domain) {
						if (dd.equals(fd.domain)) {
							if (dd instanceof EnumTd) { // Enum -> ...
								for (var int i = 0; i < dd.element.size; i++) {
									var symbol = new DomainToJavaStringEvosuite(asm).visit(dd.element.get(i))
									if (fd.codomain instanceof ConcreteDomain) { // Enum -> ConcreteDomain
										var type = AsmMethodsUtil.getConcreteDomainType(asm, fd, fd.codomain.name)
										sb.append("\t").
											append('''public «type» get_«fd.name»_fromDomain_«symbol»(){''').append(
												System.lineSeparator)
										sb.append("\t\t").append('''return this.execution.«fd.name».get(''').append(
											System.lineSeparator)
										sb.append("\t\t\t").
											append('''this.execution.«fd.domain.name»_elemsList.get(«i»)).value;''').
											append(System.lineSeparator)
										sb.append("\t").append('''}''');
									} else if (fd.codomain instanceof AbstractTd) { // Enum -> Abstract
									// return "abstract_" + value for differentiate from string
										sb.append("\t").
											append('''public String get_«fd.name»_fromDomain_«symbol»(){''').append(
												System.lineSeparator)
										sb.append("\t\t").append('''String value = this.execution.«fd.name».get(''').
											append(System.lineSeparator)
										sb.append("\t\t\t").
											append('''this.execution.«fd.domain.name»_elemsList.get(«i»)).toString();''').
											append(System.lineSeparator)
										sb.append("\t\t").
											append('''return value != null ? "abstract_" + value : null;''').append(
												System.lineSeparator)
										sb.append("\t").append('''}''');
									} else if (fd.codomain.name.equals(INTEGER) || fd.codomain.name.equals(BOOLEAN) ||
										fd.codomain.name.equals(STRING) || fd.codomain.name.equals(REAL) ||
										fd.codomain.name.equals(CHAR) || fd.codomain.name.equals(NATURAL) ||
										fd.codomain instanceof EnumTd) {
										// Enum -> (Integer|Boolean|String|Real|Char|Natural|Enum)
										var methodGetterSignature = new String;
										if (fd.codomain.name.equals(NATURAL)) {
											methodGetterSignature = "get_natural_".concat(fd.name).concat(
												"_fromDomain_").concat(symbol)
										} else {
											methodGetterSignature = "get_".concat(fd.name).concat("_fromDomain_").
												concat(symbol)
										}
										sb.append(
											AsmMethodsUtil.getMethodSignature(asmName, methodGetterSignature,
												fd.codomain.name)).append(System.lineSeparator)
										sb.append("\t\t").append('''return this.execution.«fd.name».get(''').append(
											System.lineSeparator)
										sb.append("\t\t\t").
											append('''this.execution.«fd.domain.name»_elemsList.get(«i»));''').append(
												System.lineSeparator)
										sb.append("\t").append('''}''');
									} else {
										// Codomain not supported
										manageNotSupportedDomain(translatorOptions, fd.codomain.name)
									}
									sb.append(System.lineSeparator)
								}
							} else if (dd instanceof AbstractTd) { // Abstract -> ...
								for (sf : asm.headerSection.signature.function) {
									if (sf instanceof StaticFunction) {
										if (sf.codomain.equals(dd) && sf.domain === null) {
											var symbol = sf.name
											if (fd.codomain instanceof ConcreteDomain) { // Abstract -> ConcreteDomain
												var type = AsmMethodsUtil.getConcreteDomainType(asm, fd,
													fd.codomain.name)
												sb.append("\t").
													append('''public «type» get_«fd.name»_fromDomain_«symbol»(){''').
													append(System.lineSeparator)
												sb.append("\t\t").append('''return this.execution.«fd.name».get(''').
													append(System.lineSeparator)
												sb.append("\t\t\t").
													append('''«asmName».«fd.domain.name».get("«symbol»")).value;''').
													append(System.lineSeparator)
												sb.append("\t").append('''}''');
											} else if (fd.codomain instanceof AbstractTd) { // Abstract -> Abstract
											// return "abstract_" + value for differentiate from string
												sb.append("\t").
													append('''public String get_«fd.name»_fromDomain_«symbol»(){''').
													append(System.lineSeparator)
												sb.append("\t\t").
													append('''String value = this.execution.«fd.name».get(''').append(
														System.lineSeparator)
												sb.append("\t\t").
													append('''«asmName».«fd.domain.name».get("«symbol»")).toString();''').
													append(System.lineSeparator)
												sb.append("\t\t").
													append('''return value != null ? "abstract_" + value : null;''').
													append(System.lineSeparator)
												sb.append("\t").append('''}''');
											} else if (fd.codomain.name.equals(INTEGER) ||
												fd.codomain.name.equals(BOOLEAN) || fd.codomain.name.equals(STRING) ||
												fd.codomain.name.equals(REAL) || fd.codomain.name.equals(CHAR) ||
												fd.codomain.name.equals(NATURAL) || fd.codomain instanceof EnumTd) {
												// Abstract -> (Integer|Boolean|String|Real|Char|Natural|Enum)
												var methodGetterSignature = new String;
												if (fd.codomain.name.equals(NATURAL)) {
													methodGetterSignature = "get_natural_".concat(fd.name).concat(
														"_fromDomain_").concat(symbol)
												} else {
													methodGetterSignature = "get_".concat(fd.name).concat(
														"_fromDomain_").concat(symbol)
												}
												sb.append(
													AsmMethodsUtil.getMethodSignature(asmName, methodGetterSignature,
														fd.codomain.name)).append(System.lineSeparator)
												sb.append("\t\t").append('''return this.execution.«fd.name».get(''').
													append(System.lineSeparator)
												sb.append("\t\t\t").
													append('''«asmName».«fd.domain.name».get("«symbol»"));''').append(
														System.lineSeparator)
												sb.append("\t").append('''}''');
											} else {
												// Codomain not supported
												manageNotSupportedDomain(translatorOptions, fd.codomain.name)
											}
											sb.append(System.lineSeparator)
										}
									}
								}
							} else if (dd instanceof ConcreteDomain) { // ConcreteDomain -> ...	
								for (cd : asm.headerSection.signature.domain) {
									if (cd instanceof ConcreteDomain) {
										if (cd.name.equals(fd.domain.name)) {
											// string containing the elems of the concrete domain 
											// example: "(EnumDomain.STATE1, EnumDomain.STATE2)"
											val elemsString = new TermToJava(asm).visit(cd.definition.body)
											// list containing the elems of the concrete domain
											// example: [STATE1,STATE2]
											val elems = elemsString.replace("(", "").replace(")", "").split(", ").map [
												it.substring(it.lastIndexOf('.') + 1)
											]
											for (String elem : elems) {
												// domain that is reduced by the concrete domain
												var originalDomain = new DomainToJavaString(asm).visit(
													cd.definition.definedDomain.typeDomain)
												var symbol = elem
												// if not a basic domain add the class prefix
												if (!AsmMethodsUtil.basicTdList.contains(originalDomain)) {
													symbol = asmName.concat(".").concat(originalDomain).concat(".").
														concat(elem)
												}
												if (fd.codomain instanceof ConcreteDomain) { // ConcreteDomain -> ConcreteDomain
													sb.append("\t\t").
														append('''public «originalDomain» get_«fd.name»_fromDomain_«elem»(){''').
														append(System.lineSeparator)
													sb.append("\t\t").
														append('''return this.execution.«fd.name».get(''').append(
															System.lineSeparator)
													sb.append("\t\t\t").
														append('''«asmName».«fd.domain.name».valueOf(«symbol»)).value;''').
														append(System.lineSeparator)
													sb.append("\t").append('''}''');
												} else if (fd.codomain instanceof AbstractTd) { // ConcreteDomain -> Abstract
													sb.append("\t\t").
														append('''public String get_«fd.name»_fromDomain_«elem»(){''').
														append(System.lineSeparator)
													sb.append("\t\t").
														append('''return this.execution.«fd.name».get(''').append(
															System.lineSeparator)
													sb.append("\t\t\t").
														append('''«asmName».«fd.domain.name».valueOf(«symbol»)).toString();''').
														append(System.lineSeparator)
													sb.append("\t").append('''}''');
												} else if (fd.codomain.name.equals(INTEGER) ||
													fd.codomain.name.equals(BOOLEAN) ||
													fd.codomain.name.equals(STRING) || fd.codomain.name.equals(REAL) ||
													fd.codomain.name.equals(CHAR) || fd.codomain.name.equals(NATURAL) ||
													fd.codomain instanceof EnumTd) {
													// ConcreteDomain -> (Integer|Boolean|String|Real|Char|Natural|Enum)
													var methodGetterSignature = new String;
													if (fd.codomain.name.equals(NATURAL)) {
														methodGetterSignature = "get_natural_".concat(fd.name).concat(
															"_fromDomain_").concat(elem)
													} else {
														methodGetterSignature = "get_".concat(fd.name).concat(
															"_fromDomain_").concat(elem)
													}
													sb.append(
														AsmMethodsUtil.getMethodSignature(asmName,
															methodGetterSignature, fd.codomain.name)).append(
														System.lineSeparator)
													sb.append("\t\t").
														append('''return this.execution.«fd.name».get(''').append(
															System.lineSeparator)
													sb.append("\t\t\t").
														append('''«asmName».«fd.domain.name».valueOf(«symbol»));''').
														append(System.lineSeparator)
													sb.append("\t").append('''}''');
												} else {
													// Codomain not supported
													manageNotSupportedDomain(translatorOptions, fd.codomain.name)
												}
											}
										}
									}
								}
							} else {
								// Domain not supported
								manageNotSupportedDomain(translatorOptions, fd.domain.name)
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
	static def monitoredSetters(Asm asm, TranslatorOptions translatorOptions) {
		val sb = new StringBuffer;
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof MonitoredFunction) {
				sb.append(System.lineSeparator)
				if (fd.domain === null) { // [] -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
				// var cd = fd.codomain
					if (fd.codomain instanceof EnumTd) { // [] -> Enum
						sb.append('''
						public void set_«fd.name»(«asm.name».«fd.codomain.name» «fd.name») {
							this.execution.«fd.name».set(«fd.name»);
							System.out.println("Set «fd.name» = " + «fd.name»);
						}''')
					} else if (fd.codomain instanceof ConcreteDomain) { // [] -> ConcreteDomain
						var type = AsmMethodsUtil.getConcreteDomainType(asm, fd, fd.codomain.name)
						sb.append('''
						public void set_«fd.name»(«type» «fd.name») {
							this.execution.«fd.name».set(
								«asm.name».«fd.codomain.name».valueOf(«fd.name»));
							System.out.println("Set «fd.name» = " + «fd.name»);
						}''')
						sb.append(System.lineSeparator)
					} else if (fd.codomain instanceof AbstractTd) { // [] -> Abstract
					// use the set_abstract to differentiate from String
						sb.append('''
						public void set_abstract_«fd.name»(String «fd.name») {
							this.execution.«fd.name».set(
							«asm.name».«fd.codomain.name».get(«fd.name»));
							System.out.println("Set «fd.name» = " + «fd.name»);
						}''')
						sb.append(System.lineSeparator)
					} else if (fd.codomain instanceof SequenceDomain) { // [] -> Sequence
						var type = new DomainToJavaString(asm).visit(fd.codomain).replaceAll("<", "").
							replaceAll(">", "").trim()
						if (AsmMethodsUtil.basicTdList.contains(type)) {
							// Seq(BasicTD) example: seq(Integer)
							type = AsmMethodsUtil.getWrapperBasicTdType(type)
							var parsingMethod = AsmMethodsUtil.getParsingMethod(type)

							sb.append(AsmMethodsUtil.genSequenceSetter(fd.name, type, parsingMethod))
						} else {
							// search the domain (EnumTd or AbstractTd)
							for (cd : asm.headerSection.signature.domain) {
								if (cd.name.equals(type)) {
									if (cd instanceof EnumTd) { // Seq(EnumDomain)
										type = asm.name.concat(".").concat(type)
										var parsingMethod = type + "::valueOf"
										sb.append(AsmMethodsUtil.genSequenceSetter(fd.name, type, parsingMethod))
									} else if (cd instanceof AbstractTd) { // Seq(AbstractDomain)
										type = asm.name.concat(".").concat(type)
										var parsingMethod = type + "::get"
										sb.append(AsmMethodsUtil.genSequenceSetter(fd.name, type, parsingMethod))
									}
								}
							}
						}
					} else if (fd.codomain.name.equals(NATURAL)) { // [] -> Natural
						sb.append('''
						public void set_natural_«fd.name»(int «fd.name») {
							this.execution.«fd.name».set(«fd.name»);
							System.out.println("Set «fd.name» = " + «fd.name» +"n");
						}''')
					} else if (fd.codomain.name.equals(INTEGER) || fd.codomain.name.equals(BOOLEAN) ||
						fd.codomain.name.equals(STRING) || fd.codomain.name.equals(REAL) ||
						fd.codomain.name.equals(CHAR)) { // [] -> (Integer|Boolean|String|Real|Char)
						var type = AsmMethodsUtil.getBasicTdType(fd.codomain.name)
						sb.append('''
						public void set_«fd.name»(«type» «fd.name») {
							this.execution.«fd.name».set(«fd.name»);
							System.out.println("Set «fd.name» = " + «fd.name»);
						}''')
					} else {
						// Codomain not supported
						manageNotSupportedDomain(translatorOptions, fd.codomain.name)
					}
				} else { // (Enum|Abstract|ConcreteDomain) -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
				// add the marker _fromDomain_ to the setter name
					var dd = fd.domain
					if (dd instanceof EnumTd) { // Enum -> ...
						for (var int i = 0; i < dd.element.size; i++) {
							var symbol = new DomainToJavaStringEvosuite(asm).visit(dd.element.get(i))
							if (fd.codomain instanceof ConcreteDomain) { // Enum -> ConcreteDomain 
								var type = AsmMethodsUtil.getConcreteDomainType(asm, fd, fd.codomain.name)
								sb.append('''
								public void set_«fd.name»_fromDomain_«symbol»(«type» «fd.name»_«symbol») {
									this.execution.«fd.name».set(
									«asm.name».«dd.name».«symbol»,
									«asm.name».«fd.codomain.name».valueOf(this.execution.«fd.codomain.name»_elems.get(«fd.name»_«symbol»)));
									System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);
								}''')
							} else if (fd.codomain instanceof EnumTd) { // Enum -> Enum
								sb.append('''
								public void set_«fd.name»_fromDomain_«symbol»(«asm.name».«fd.codomain.name» «fd.name»_«symbol») {
									this.execution.«fd.name».set(«asm.name».«dd.name».«symbol», «fd.name»_«symbol»);
									System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);
								}''')
							} else if (fd.codomain instanceof AbstractTd) { // Enum -> Abstract
							// use the set_abstract to differentiate from String
								sb.append('''
								public void set_abstract_«fd.name»_fromDomain_«symbol»(String «fd.name»_«symbol») {
									this.execution.«fd.name».set(«asm.name».«dd.name».«symbol», 
									«asm.name».«fd.codomain.name».get(«fd.name»_«symbol»));
									System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);
								}''')
							} else if (fd.codomain.name.equals(NATURAL)) { // Enum -> Natural
							// use the set_natural flag
								sb.append('''
								public void set_natural_«fd.name»_fromDomain_«symbol»(int «fd.name»_«symbol») {
									this.execution.«fd.name».set(«asm.name».«dd.name».«symbol», «fd.name»_«symbol»);
									System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol» + "n");
								}''')
							} else if (fd.codomain.name.equals(INTEGER) || fd.codomain.name.equals(BOOLEAN) ||
								fd.codomain.name.equals(STRING) || fd.codomain.name.equals(REAL) ||
								fd.codomain.name.equals(CHAR)) { // Enum -> (Integer|String|Boolean|Real|Char)
								var type = AsmMethodsUtil.getBasicTdType(fd.codomain.name)
								sb.append('''
								public void set_«fd.name»_fromDomain_«symbol»(«type» «fd.name»_«symbol») {
									this.execution.«fd.name».set(«asm.name».«dd.name».«symbol», «fd.name»_«symbol»);
									System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);
								}''');
							} else {
								// Codomain not supported
								manageNotSupportedDomain(translatorOptions, fd.codomain.name)
							}
						}
					} else if (fd.domain instanceof AbstractTd) { // Abstract -> ...
						for (sf : asm.headerSection.signature.function) {
							if (sf instanceof StaticFunction) {
								if (sf.codomain.equals(fd.domain) && sf.domain === null) {
									var symbol = sf.name
									if (fd.codomain instanceof ConcreteDomain) { // Abstract -> ConcreteDomain
										var type = AsmMethodsUtil.getConcreteDomainType(asm, fd, fd.codomain.name)
										sb.append('''
										public void set_«fd.name»_fromDomain_«symbol»(«type» «fd.name»_«symbol») {
											this.execution.«fd.name».set(
											«asm.name».«fd.domain.name».get("«symbol»"),
											«asm.name».«fd.codomain.name».valueOf(this.execution.«fd.codomain.name»_elems.get(«fd.name»_«symbol»)));
											System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);
										}''')
									} else if (fd.codomain instanceof EnumTd) { // Abstract -> Enum
										sb.append('''
										public void set_«fd.name»_fromDomain_«symbol»(«asm.name».«fd.codomain.name» «fd.name»_«symbol») {
											this.execution.«fd.name».set(
											«asm.name».«fd.domain.name».get("«symbol»"),«fd.name»_«symbol»);
											System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);
										}''')
									} else if (fd.codomain instanceof AbstractTd) { // Abstract -> Abstract
									// use the set_abstract to differentiate from String
										sb.append('''
										public void set_abstract_«fd.name»_fromDomain_«symbol»(String «fd.name»_«symbol») {
											this.execution.«fd.name».set(
											«asm.name».«fd.domain.name».get("«symbol»"),
											«asm.name».«fd.codomain.name».get(«fd.name»_«symbol»));
											System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);
										}''')
									} else if (fd.codomain.name.equals(NATURAL)) { // Abstract -> Natural
									// use the set_abstract to differentiate from String
										sb.append('''
										public void set_natural_«fd.name»_fromDomain_«symbol»(int «fd.name»_«symbol») {
											this.execution.«fd.name».set(
											«asm.name».«fd.domain.name».get("«symbol»"),«fd.name»_«symbol»);
											System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol» + "n");
										}''')
									} else if (fd.codomain.name.equals(INTEGER) || fd.codomain.name.equals(BOOLEAN) ||
										fd.codomain.name.equals(STRING) || fd.codomain.name.equals(REAL) ||
										fd.codomain.name.equals(CHAR)) { // Abstract -> (Integer|String|Boolean|Real|Char)
										var type = AsmMethodsUtil.getBasicTdType(fd.codomain.name)
										sb.append('''
										public void set_«fd.name»_fromDomain_«symbol»(«type» «fd.name»_«symbol») {
											this.execution.«fd.name».set(
											«asm.name».«fd.domain.name».get("«symbol»"),«fd.name»_«symbol»);
											System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);
										}''')
									} else {
										// Codomain not supported
										manageNotSupportedDomain(translatorOptions, fd.codomain.name)
									}
								}
							}
						}
					} else if (fd.domain instanceof ConcreteDomain) { // ConcreteDomain -> ...
						for (cd : asm.headerSection.signature.domain) {
							if (cd instanceof ConcreteDomain) {
								if (cd.name.equals(fd.domain.name)) {
									// string containing the elems of the concrete domain 
									// example: "(EnumDomain.STATE1, EnumDomain.STATE2)"
									val elemsString = new TermToJava(asm).visit(cd.definition.body)
									// list containing the elems of the concrete domain
									// example: [STATE1,STATE2]
									val elems = elemsString.replace("(", "").replace(")", "").split(", ").map [
										it.substring(it.lastIndexOf('.') + 1)
									]
									for (String elem : elems) {
										var symbol = elem
										// domain that is reduced by the concrete domain
										var originalDomain = new DomainToJavaString(asm).visit(
											cd.definition.definedDomain.typeDomain)
										// if not a basic domain add the class prefix
										if (!AsmMethodsUtil.basicTdList.contains(originalDomain)) {
											symbol = asm.name.concat(".").concat(originalDomain).concat(".").
												concat(elem)
										}
										if (fd.codomain instanceof ConcreteDomain) { // ConcreteDomain -> ConcreteDomain
											var type = AsmMethodsUtil.getConcreteDomainType(asm, fd, fd.codomain.name)
											sb.append('''
											public void set_«fd.name»_fromDomain_«elem»(«type» «fd.name»_«elem») {
												this.execution.«fd.name».set(
												«asm.name».«fd.domain.name».valueOf(«symbol»),
												«asm.name».«fd.codomain.name».valueOf(«fd.name»_«elem»));
												System.out.println("Set «fd.name»_«elem» = " + «fd.name»_«elem»);
											}''')
										} else if (fd.codomain instanceof EnumTd) { // ConcreteDomain -> Enum
											sb.append('''
											public void set_«fd.name»_fromDomain_«elem»(«asm.name».«fd.codomain.name» «fd.name»_«elem») {
												this.execution.«fd.name».set(
												«asm.name».«fd.domain.name».valueOf(«symbol»),«fd.name»_«elem»);
												System.out.println("Set «fd.name»_«elem» = " + «fd.name»_«elem»);
											}''')
										} else if (fd.codomain instanceof AbstractTd) { // ConcreteDomain -> Abstract
											sb.append('''
											public void set_abstract_«fd.name»_fromDomain_«elem»(String «fd.name»_«elem») {
												this.execution.«fd.name».set(
												«asm.name».«fd.domain.name».valueOf(«symbol»),
												«asm.name».«fd.codomain.name».get(«fd.name»_«elem»));
												System.out.println("Set «fd.name»_«elem» = " + «fd.name»_«elem»);
											}''')
										} else if (fd.codomain.name.equals(NATURAL)) { // ConcreteDomain -> Natural
											sb.append('''
											public void set_natural_«fd.name»_fromDomain_«elem»(int «fd.name»_«elem») {
												this.execution.«fd.name».set(
												«asm.name».«fd.domain.name».valueOf(«symbol»),«fd.name»_«elem»);
												System.out.println("Set «fd.name»_«elem» = " + «fd.name»_«elem» + "n");
											}''')
										} else if (fd.codomain.name.equals(INTEGER) ||
											fd.codomain.name.equals(BOOLEAN) || fd.codomain.name.equals(STRING) ||
											fd.codomain.name.equals(REAL) || fd.codomain.name.equals(CHAR)) {
											// ConcreteDomain -> (Integer|String|Boolean|Real|Char)
											var type = AsmMethodsUtil.getBasicTdType(fd.codomain.name)
											sb.append('''
											public void set_«fd.name»_fromDomain_«elem»(«type» «fd.name»_«elem») {
												this.execution.«fd.name».set(
												«asm.name».«fd.domain.name».valueOf(«symbol»),«fd.name»_«elem»);
												System.out.println("Set «fd.name»_«elem» = " + «fd.name»_«elem»);
											}''')
										} else {
											// Codomain not supported
											manageNotSupportedDomain(translatorOptions, fd.codomain.name)
										}
									}
								}
							}
						}
					} else {
						// Domain not supported
						manageNotSupportedDomain(translatorOptions, fd.domain.name)
					}
				}
			}
		}
		sb.append(System.lineSeparator)
		return sb.toString
	}

	/**
	 * Handles the case of an unrecognized domain,
	 * if the ignoreDomainException option is active it prints the error,
	 * otherwise it throws an exception and blocks the program flow.
	 * 
	 * @param translatorOptions translator options
	 * @param domainName name of the unsupported domain
	 */
	static private def manageNotSupportedDomain(TranslatorOptions translatorOptions, String domainName) {
		if (translatorOptions.ignoreDomainException) {
			print("ERROR! domain not supported: " + domainName + "\n")
		} else {
			throw new DomainNotSupportedException("The Domain " + domainName + " is not supported by the ATG class.");
		}
	}

}
