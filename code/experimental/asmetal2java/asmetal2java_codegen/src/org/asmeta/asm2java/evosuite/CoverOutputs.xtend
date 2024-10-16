package org.asmeta.asm2java.evosuite

import org.asmeta.asm2java.ToString
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.ControlledFunction
import asmeta.definitions.MonitoredFunction
import asmeta.structure.Asm

class CoverOutputs {
	
	static 	def coverBranches(Asm asm, StringBuffer sb) {
		for (fd : asm.headerSection.signature.function){
			if(fd instanceof MonitoredFunction || fd instanceof ControlledFunction){
				if(fd.domain === null){ // [] -> ...
					sb.append("\t").append('''private void cover_«fd.name»(){''');
					if(fd.codomain instanceof EnumTd){ // [] -> Enum
						sb.append(System.lineSeparator)
						sb.append("\t\t").append('''switch(this.get_«fd.name»()){''');
						for(dd : asm.headerSection.signature.domain){
							if(dd.equals(fd.codomain)){
								if(dd instanceof EnumTd){
									for (var int i = 0; i < dd.element.size; i++) {
										var symbol = new ToString(asm).visit(dd.element.get(i))
										sb.append(System.lineSeparator)
							sb.append("\t\t\t").append('''case «symbol» :
							System.out.println("Branch «fd.codomain.name» «symbol» covered");
							// Branch «fd.codomain.name» «symbol» covered
							break;''');
									}
								}
							}
						}
						sb.append(System.lineSeparator)
						sb.append("\t\t\t")sb.append('''}''');
					} // [] -> (Integer|String|Boolean|ConcreteDomain|Abstract)
					else{
						sb.append(System.lineSeparator)
						sb.append("\t\t").append('''this.get_«fd.name»();''');
						sb.append(System.lineSeparator)
						sb.append("\t\t").append('''//1 Branch covered''');
					}
					sb.append(System.lineSeparator)
					sb.append("\t").append('''}''');
					sb.append(System.lineSeparator)
					sb.append(System.lineSeparator)
				}
				else{ // (Abstract|Enum) -> ...
					for(dd : asm.headerSection.signature.domain){
						if(dd.equals(fd.domain)){
							sb.append("\t").append('''private void cover_«fd.name»(){''');
							if(dd instanceof EnumTd){ // Enum -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
								for (var int i = 0; i < dd.element.size; i++) {
									var symbol = new ToString(asm).visit(dd.element.get(i))
									sb.append(System.lineSeparator)
									sb.append("\t\t").append('''this.get_«fd.name»_«symbol»();''');
								}
								sb.append(System.lineSeparator)
								sb.append("\t\t").append('''// «dd.element.size» Branch covered''');
							}
							else if (dd instanceof AbstractTd) { // Abstract -> ..
								var i = 0
								for (sf : asm.headerSection.signature.function) {
									if(sf instanceof StaticFunction){
										if(sf.codomain.equals(dd) && sf.domain===null){
											var sd = fd.codomain
											if(sd instanceof EnumTd){ // Abstract -> Enum
												sb.append(System.lineSeparator)
												sb.append("\t\t").append('''switch(this.esecuzione.«fd.name».get(
												this.esecuzione.«fd.domain.name»_Class.get(
												this.esecuzione.«fd.domain.name»_elemsList.indexOf("«sf.name»")))){''');
												for (var int j = 0; j < sd.element.size; j++) {
												var symbol = new ToString(asm).visit(sd.element.get(j))
												sb.append(System.lineSeparator)
												sb.append("\t\t\t").append('''case «symbol» :
												System.out.println("Branch «sf.name» «symbol» covered");
												// Branch «sf.name» «symbol» covered
												break;''');
												i+=1
												}
												sb.append(System.lineSeparator)
												sb.append("\t").append('''}''');
											}
											else{ // Abstract -> (Integer|String|Boolean|ConcreteDomain|Abstract)
												var symbol = sf.name
												sb.append(System.lineSeparator)
												sb.append("\t\t").append('''this.get_«fd.name»_«symbol»();''');
												i+=1
											}
										}
									}
								}
								sb.append(System.lineSeparator)
								sb.append("\t\t").append('''//«i» Branch covered''');
							}
							else {
								sb.append(System.lineSeparator)
								sb.append("\t\t").append('''this.get_«fd.name»();''');
								sb.append(System.lineSeparator)
								sb.append("\t\t").append('''//1 Branch covered''');
							}
							sb.append(System.lineSeparator)
							sb.append("\t").append('''}''');
							sb.append(System.lineSeparator)
							sb.append(System.lineSeparator)
						}
					}
				}
			}
		}
	}
	
	static 	def coverFunctions(Asm asm, StringBuffer sb, boolean monitored) {

		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof MonitoredFunction && monitored==true) {
				sb.append(System.lineSeparator)
				sb.append("\t\t").append('''cover_«fd.name»();''')
			}
		}

		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof ControlledFunction && monitored==false) {
				sb.append(System.lineSeparator)
				sb.append("\t\t").append('''cover_«fd.name»();''')
			}
		}
	}	
	
	static def ifFinalState(Asm asm, StringBuffer sb, String[] finalStateConditions){
		if(finalStateConditions !== null && !finalStateConditions.isEmpty){
				sb.append("\t\t\t\t" ).append('''/* final state condition */''')
				sb.append(System.lineSeparator)
				sb.append("\t\t\t\t" ).append('''if(isFinalState()){
						System.out.println("\n<Stato finale>");
				}
				else''')
				sb.append(System.lineSeparator)
			}
	}
	
	static def setIsFinalState(Asm asm, StringBuffer sb, String[] finalStateConditions){
		if(finalStateConditions !== null && !finalStateConditions.isEmpty){
			sb.append(System.lineSeparator)
			sb.append(System.lineSeparator)
			sb.append("\t").append('''// final state condition''')
			sb.append(System.lineSeparator)
			sb.append("\t").append('''public boolean isFinalState(){''')
			sb.append(System.lineSeparator)
			sb.append("\t\t").append('''return''')
			var numberOfConditionsExpected = 0
			var numberOfConditionsActual = 0
			var ss = new StringBuffer;
			for(condition : finalStateConditions){
				numberOfConditionsExpected += 1
				val cond_name = condition.replaceAll("^\\s*(\\w+)\\s*.*$", "$1")
				var cond_value = condition.replaceAll("^\\s*\\w+\\s*(.*)$", "$1")

				if(cond_name.toLowerCase.equals("stato")){
					ss.append(System.lineSeparator)
					ss.append("\t\t\t").append('''this.stato «cond_value» &&''')
					numberOfConditionsActual +=1
				} else {
					if(!cond_value.matches("\\d+")){ // se la condizione non è numerica
						var cond_value_operators = cond_value.replaceAll("^([><=!]+).*", "$1");
						cond_value = '''«asm.name».«cond_value.replaceAll("^([><=!]+)(.*)","$2")»''' ; // aggiungo il prefisso
						cond_value = cond_value_operators.concat(cond_value)
					}
					for(fd : asm.headerSection.signature.function){
						if(fd instanceof ControlledFunction && fd.name.equals(cond_name)){
							ss.append(System.lineSeparator)
							ss.append("\t\t\t").append('''this.get_«fd.name»() «cond_value» &&''')
							numberOfConditionsActual +=1
						}
					}
				}
			}
			if(numberOfConditionsActual == 0){
				sb.append(" true;")
				sb.append(System.lineSeparator)
				sb.append("\t").append('''// ERROR - NO Conditions found''')
			}
			else if(numberOfConditionsActual == numberOfConditionsExpected){
				sb.append(ss.toString)
				sb.setLength(sb.length() - 3)
				sb.append(''';''')
			}
			else{
				sb.append(ss.toString)
				sb.setLength(sb.length() - 3)
				sb.append(''';''')
				sb.append(System.lineSeparator)
				sb.append("\t").append('''// ERROR - «numberOfConditionsExpected-numberOfConditionsActual» Conditions not generated''')
			}
			sb.append(System.lineSeparator)
			sb.append("\t").append('''}''')
			sb.append(System.lineSeparator)
		}
	}
	
}