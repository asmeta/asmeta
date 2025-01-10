package org.asmeta.asm2java.evosuite

import asmeta.definitions.domains.EnumTd
import asmeta.definitions.ControlledFunction
import asmeta.definitions.MonitoredFunction
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
				// cover only enum states
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
						// Enum -> Enum
						for(dd : asm.headerSection.signature.domain){
							if(dd.equals(fd.domain)){
								if(dd instanceof EnumTd){ 
									for (var int i = 0; i < dd.element.size; i++) {
										var symbol = new DomainToJavaStringEvosuite(asm).visit(dd.element.get(i))
										sb.append("\t").append('''private void cover_«fd.name»_«symbol»(){''');
										sb.append(System.lineSeparator)
										sb.append("\t\t").append('''if(this.get_«fd.name»_«symbol»() == null){''');
										sb.append(System.lineSeparator)
										sb.append("\t\t\t").append('''return;''');
										sb.append(System.lineSeparator)
										sb.append("\t\t").append('''}''')
										sb.append(System.lineSeparator)
										sb.append("\t\t").append('''switch(this.get_«fd.name»_«symbol»()){''');
										for(ddd : asm.headerSection.signature.domain){
											if(ddd.equals(fd.codomain)){
												if(ddd instanceof EnumTd){
													for (var int j = 0; j < ddd.element.size; j++) {
														var symbolD = new DomainToJavaStringEvosuite(asm).visit(ddd.element.get(j))
														sb.append(System.lineSeparator)
														sb.append("\t\t\t").append('''case «symbolD» :
														System.out.println("Branch «fd.codomain.name» «symbolD» covered");
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
	static 	def coverOutputs(Asm asm) {

		val sb = new StringBuffer();
		
		sb.append('''
			/**
			 * Invokes all output coverage functions.
			 * <p>
			 * To achieve complete output coverage, only the functions related to the enum need to be invoked.
			 * </p>
			 */''');
		sb.append("\t").append('''private void coverOutputs(){''');

		for (fd : asm.headerSection.signature.function) {
			// cover monitored and controlled outputs
			if (fd instanceof MonitoredFunction || fd instanceof ControlledFunction) {
				// cover only enum states
				if(fd.codomain instanceof EnumTd){ // ... -> Enum
					if(fd.domain === null){  // [] -> Enum
						sb.append(System.lineSeparator)
						sb.append("\t\t").append('''cover_«fd.name»();''')
					} else {
						for(dd : asm.headerSection.signature.domain){
							if(dd.equals(fd.domain)){
								if(dd instanceof EnumTd){ 
									// Enum -> Enum
									for (var int i = 0; i < dd.element.size; i++) {
										var symbol = new DomainToJavaStringEvosuite(asm).visit(dd.element.get(i))
										sb.append(System.lineSeparator);
										sb.append("\t\t").append('''cover_«fd.name»_«symbol»();''')
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
		// In order to cover outputs we only need to cover the Enum functions
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
											* Get the monitored function {@code «fd.name»_«symbol»}.
											*
											* @return the selected {@code «asmName».«fd.codomain.name» «fd.name»_«symbol»} «fd.name»_«symbol»
											*/
												private «asmName».«fd.codomain.name» get_«fd.name»_«symbol»(){
													return this.execution.«fd.name».get(
														this.execution.«fd.domain.name»_elemsList.get(«i»));
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
			
			return sb.toString;
			
		}
	
	
}