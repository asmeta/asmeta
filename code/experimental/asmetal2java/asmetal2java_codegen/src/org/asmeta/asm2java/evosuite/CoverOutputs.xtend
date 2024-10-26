package org.asmeta.asm2java.evosuite

import asmeta.definitions.domains.EnumTd
import asmeta.definitions.ControlledFunction
import asmeta.definitions.MonitoredFunction
import asmeta.structure.Asm

class CoverOutputs {
	
	// Create a method for the function to cover all its outputs
	static 	def coverOutputBranches(Asm asm) {
		
		val sb = new StringBuffer();
		
		for (fd : asm.headerSection.signature.function){
			if(fd instanceof MonitoredFunction || fd instanceof ControlledFunction){
				if(fd.domain === null){ // [] -> ...
					if(fd.codomain instanceof EnumTd){ // [] -> Enum
						sb.append("\t").append('''private void cover_«fd.name»(){''');
						sb.append(System.lineSeparator)
						sb.append("\t\t").append('''switch(this.get_«fd.name»()){''');
						for(dd : asm.headerSection.signature.domain){
							if(dd.equals(fd.codomain)){
								if(dd instanceof EnumTd){
									for (var int i = 0; i < dd.element.size; i++) {
										var symbol = new ToStringEvosuite(asm).visit(dd.element.get(i))
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
					} /* TODO: add cover functions for domain -> codomain functions if necessary */
				}
			}
		}
		
		return sb.toString;
		
	}
	
	// create a method that calls all the cover output functions
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
			if (fd instanceof MonitoredFunction || fd instanceof ControlledFunction) {
				if(fd.domain === null){ // [] -> ...
					if(fd.codomain instanceof EnumTd){ // [] -> Enum
						sb.append(System.lineSeparator)
						sb.append("\t\t").append('''cover_«fd.name»();''')
					}
				}
			} // TODO: add cover functions for domain -> codomain functions if necessary
		}
		
		sb.append("\t").append('''}''');
		return sb.toString;

	}	
	
	// in order to cover outputs we only need to cover the Enum functions 
	static def monitoredGetter(Asm asm) {
		
		val sb = new StringBuffer();
		
		var asmName = asm.name;
			for (fd : asm.headerSection.signature.function) {
				if (fd instanceof MonitoredFunction) {
					if (fd.domain === null) {
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
					} /* TODO: add monitored getters for domain -> codomain functions if necessary */
				}
			}
			
			return sb.toString;
			
		}
	
	
}