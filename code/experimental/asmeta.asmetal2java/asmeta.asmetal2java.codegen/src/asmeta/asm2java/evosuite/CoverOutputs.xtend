package asmeta.asm2java.evosuite

import asmeta.asm2java.translator.DomainToJavaString
import asmeta.asm2java.translator.TermToJava
import asmeta.definitions.ControlledFunction
import asmeta.definitions.MonitoredFunction
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.EnumTd
import asmeta.structure.Asm

/**
 * Contains the methods to cover the outputs of the abstract state machine (ASM),
 * by output we mean the states of the enumerative domains.
 */
class CoverOutputs {
	
	/**
	 *  Create a method for the function to cover all its outputs
	 * 
	 * @param asm the Asm specification
	 */
	static 	def coverOutputBranches(Asm asm) {
		
		val sb = new StringBuffer();
		
		for (fd : asm.headerSection.signature.function){
			if(fd instanceof MonitoredFunction || fd instanceof ControlledFunction){
				// cover only enum codomain
				if(fd.codomain instanceof EnumTd){ // ... -> Enum
					if(fd.domain === null){ // [] -> Enum
						sb.append("\t").append('''private void cover_«fd.name»(){''');
						sb.append(System.lineSeparator)
						sb.append("\t\t").append('''if(this.get_«fd.name»() == null){''');
						sb.append(System.lineSeparator)
						sb.append("\t\t\t").append('''return;''');
						sb.append(System.lineSeparator)
						sb.append("\t\t").append('''}''')
						sb.append(System.lineSeparator)
						sb.append("\t\t").append('''switch(this.get_«fd.name»()){''');
						for(dd : asm.headerSection.signature.domain){
							if(dd.equals(fd.codomain)){
								if(dd instanceof EnumTd){
									for (var int i = 0; i < dd.element.size; i++) {
										var symbol = new DomainToJavaStringEvosuite(asm).visit(dd.element.get(i))
										sb.append(System.lineSeparator)
										sb.append("\t\t\t").append('''case «symbol» :
										System.out.println("Branch «fd.codomain.name» «symbol» covered");
										break;''');
										sb.append(System.lineSeparator)
									}
								}
							}
						}
						sb.append("\t\t\t")sb.append('''}''');
						sb.append(System.lineSeparator)
						sb.append("\t\t")sb.append('''}''');
						sb.append(System.lineSeparator)
					} else {
						// (Enum|Abstract) -> Enum
						for(dd : asm.headerSection.signature.domain){
							if(dd.equals(fd.domain)){
								if(dd instanceof EnumTd){ // Enum -> Enum
									for (var int i = 0; i < dd.element.size; i++) {
										var elem = new DomainToJavaStringEvosuite(asm).visit(dd.element.get(i))
										sb.append(AsmMethodsUtil.genCoverOutputMethod(fd, elem, asm))
									}
								}
								else if (dd instanceof AbstractTd){ // Abstract -> Enum
									for (sf : asm.headerSection.signature.function) {
										if(sf instanceof StaticFunction){
											if(sf.codomain.equals(dd) && sf.domain===null){
												var elem = sf.name
												sb.append(AsmMethodsUtil.genCoverOutputMethod(fd, elem, asm))
											}
										}
									}
								} else if(dd instanceof ConcreteDomain){ // ConcreteDomain -> Enum
									for(cd : asm.headerSection.signature.domain){
										if(cd instanceof ConcreteDomain){
											if(cd.name.equals(fd.domain.name)){
												val elemsString = new TermToJava(asm).visit(cd.definition.body)
												val elems = elemsString.replace("(", "").replace(")", "").split(", ").map [ it.substring(it.lastIndexOf('.') + 1) ]
												for (String elem : elems){
													sb.append(AsmMethodsUtil.genCoverOutputMethod(fd, elem, asm))
												}
											}
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
	 * Create a method that calls all the cover output functions
	 * 
	 * @param asm the Asm specification
	 */
	static def coverOutputs(Asm asm) {

		val sb = new StringBuffer();
		
		sb.append('''
			/**
			 * Invokes all output coverage functions.
			 * <p>
			 * To achieve complete output coverage, only the functions that cover enum codomains need to be invoked.
			 * </p>
			 */''');
		sb.append("\t").append('''private void coverOutputs(){''');

		for (fd : asm.headerSection.signature.function) {
			// cover monitored and controlled outputs
			if (fd instanceof MonitoredFunction || fd instanceof ControlledFunction) {
				// cover only enum codomain states
				if(fd.codomain instanceof EnumTd){ // ... -> Enum
					if(fd.domain === null){  // [] -> Enum
						sb.append(System.lineSeparator)
						sb.append("\t\t").append('''cover_«fd.name»();''')
					} else {
						for(dd : asm.headerSection.signature.domain){
							if(dd.equals(fd.domain)){
								if(dd instanceof EnumTd){ // Enum -> Enum
									for (var int i = 0; i < dd.element.size; i++) {
										var symbol = new DomainToJavaStringEvosuite(asm).visit(dd.element.get(i))
										sb.append(System.lineSeparator);
										sb.append("\t\t").append('''cover_«fd.name»_fromDomain_«symbol»();''')
									}
								}
								else if (dd instanceof AbstractTd){ // Abstract -> Enum
									for (sf : asm.headerSection.signature.function) {
										if(sf instanceof StaticFunction){
											if(sf.codomain.equals(dd) && sf.domain===null){
												var symbol = sf.name
												sb.append(System.lineSeparator);
												sb.append("\t\t").append('''cover_«fd.name»_fromDomain_«symbol»();''')
											}
										}
									}
								} else if(dd instanceof ConcreteDomain){ // ConcreteDomain -> Enum
									for(cd : asm.headerSection.signature.domain){
										if(cd instanceof ConcreteDomain){
											if(cd.name.equals(fd.domain.name)){
												val elemsString = new TermToJava(asm).visit(cd.definition.body)
												val elems = elemsString.replace("(", "").replace(")", "").split(", ").map [ it.substring(it.lastIndexOf('.') + 1) ]
												for (String elem : elems){
													sb.append(System.lineSeparator);
													sb.append("\t\t").append('''cover_«fd.name»_fromDomain_«elem»();''')
												}
											}
										}
									}
								}
							}
						}
					}
				} 
			}
			
		}
		
		sb.append("\t").append('''}''');
		return sb.toString;

	}	
	
	/**
	 * Monitored functions getters (private, only for cover outputs)
	 * 
	 * @param asm the Asm specification
	 */
	static def monitoredGetter(Asm asm) {
		// In order to cover outputs we only need to cover the Enum codomain functions
		val sb = new StringBuffer();
		
		var asmName = asm.name;
			for (fd : asm.headerSection.signature.function) {
				if (fd instanceof MonitoredFunction) {
					if (fd.domain === null) { // [] -> Enum
						if (fd.codomain instanceof EnumTd) {
							sb.append(System.lineSeparator);
							sb.append('''
							/**
							* Get the monitored function {@code «fd.name»}.
							*
							* @return the selected {@code «asmName».«fd.codomain.name» «fd.name»} «fd.name»
							*/
								private «asmName».«fd.codomain.name» get_«fd.name»(){
									return this.execution.«fd.name».get();
								}
						 	''');
						 	sb.append(System.lineSeparator);
						}
					} else {
						if(fd.codomain instanceof EnumTd){ // Enum -> Enum
							for(dd : asm.headerSection.signature.domain){
								if(dd.equals(fd.domain)){
									if(dd instanceof EnumTd){ 
										for (var int i = 0; i < dd.element.size; i++) {
											var symbol = new DomainToJavaStringEvosuite(asm).visit(dd.element.get(i))
											sb.append(System.lineSeparator);
											sb.append('''
											/**
											* Get the monitored function {@code «fd.name»_fromDomain_«symbol»}.
											*
											* @return the selected {@code «asmName».«fd.codomain.name» «fd.name»_fromDomain_«symbol»} «fd.name»_fromDomain_«symbol»
											*/
												private «asmName».«fd.codomain.name» get_«fd.name»_fromDomain_«symbol»(){
													return this.execution.«fd.name».get(
														this.execution.«fd.domain.name»_elemsList.get(«i»));
												}
										 	''');										 	
										 	sb.append(System.lineSeparator);
										}
									} else if (dd instanceof AbstractTd){ // Abstract -> Enum
										for (sf : asm.headerSection.signature.function) {
											if(sf instanceof StaticFunction){
												if(sf.codomain.equals(dd) && sf.domain===null){
													var symbol = sf.name
													sb.append(System.lineSeparator);
													sb.append('''
													/**
													* Get the monitored function {@code «fd.name»_fromDomain_«symbol»}.
													*
													* @return the selected {@code «asmName».«fd.codomain.name» «fd.name»_fromDomain_«symbol»} «fd.name»_fromDomain_«symbol»
													*/
														private «asmName».«fd.codomain.name» get_«fd.name»_fromDomain_«symbol»(){
															return this.execution.«fd.name».get(
																«asmName».«fd.domain.name».get("«symbol»"));
														}
												 	''');
												 	sb.append(System.lineSeparator);
												}
											}
										}
									} else if(dd instanceof ConcreteDomain){ // ConcreteDomain -> Enum
										for(cd : asm.headerSection.signature.domain){
											if(cd instanceof ConcreteDomain){
												if(cd.name.equals(fd.domain.name)){
													// string containing the elems of the concrete domain 
													// example: "(EnumDomain.STATE1, EnumDomain.STATE2)"
													val elemsString = new TermToJava(asm).visit(cd.definition.body)
													// list containing the elems of the concrete domain
													// example: [STATE1,STATE2]
													val elems = elemsString.replace("(", "").replace(")", "").split(", ").map [ it.substring(it.lastIndexOf('.') + 1) ]
													for (String elem : elems){
														// domain that is reduced by the concrete domain
														var originalDomain = new DomainToJavaString(asm).visit(cd.definition.definedDomain.typeDomain)
														var symbol = elem
															// if not a basic domain add the class prefix
															if(!AsmMethodsUtil.basicTdList.contains(originalDomain)){
																symbol = asmName.concat(".").concat(originalDomain).concat(".").concat(elem)
															}
															sb.append(System.lineSeparator);
															sb.append('''
															/**
															* Get the monitored function {@code «fd.name»_fromDomain_«elem»}.
															*
															* @return the selected {@code «asmName».«fd.codomain.name» «fd.name»_fromDomain_«elem»} «fd.name»_fromDomain_«elem»
															*/
																private «asmName».«fd.codomain.name» get_«fd.name»_fromDomain_«elem»(){
																	return this.execution.«fd.name».get(
																		«asmName».«fd.domain.name».valueOf(«symbol»));
																}
														 	''');
														 	sb.append(System.lineSeparator);
													}
												}
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
	
	
}