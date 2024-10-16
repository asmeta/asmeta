package org.asmeta.asm2java.evosuite

import asmeta.structure.Asm
import asmeta.definitions.ControlledFunction
import asmeta.definitions.MonitoredFunction
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.domains.AbstractTd
import org.asmeta.asm2java.ToString
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.MapDomain
import asmeta.definitions.domains.SequenceDomain

class AsmMethods {
	
	static def controlledGetter(Asm asm, StringBuffer sb) {
		var asmName = asm.name;
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof ControlledFunction) {
				if (fd.domain === null) {
					if (fd.codomain.name.equals("Boolean") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append('''

								public boolean get_«fd.name»(){
									return this.esecuzione.«fd.name».get();
								}
						 	''');
					}
					if (fd.codomain.name.equals("Integer") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append('''

								public int get_«fd.name»(){
									return this.esecuzione.«fd.name».get();
								}
						 	''');
					}
					if (fd.codomain.name.equals("String") && !(fd.codomain instanceof ConcreteDomain)) {
						sb.append('''

								public String get_«fd.name»(){
									return this.esecuzione.«fd.name».get();
								}
						 	''');
					}
					if (fd.codomain instanceof ConcreteDomain) {
						sb.append('''

								public int get_«fd.name»(){
									return this.esecuzione.«fd.name».get().value;
								}
						 	''');
					}
					if (fd.codomain instanceof EnumTd ||
						fd.codomain instanceof AbstractTd) {
						sb.append('''

								public «asmName».«fd.codomain.name» get_«fd.name»(){
									return this.esecuzione.«fd.name».get();
								}
						 	''');
					}
				}
				else{ // getter per le funzioni con Dominio -> Codominio

					for(dd : asm.headerSection.signature.domain){
						if(dd.equals(fd.domain)){
							if(dd instanceof EnumTd){
								for (var int i = 0; i < dd.element.size; i++) {
									var symbol = new ToString(asm).visit(dd.element.get(i))
									sb.append(System.lineSeparator)
									if(fd.codomain instanceof ConcreteDomain){ // considero subsetOf Integer
										sb.append("\t").append('''public int get_«fd.name»_«symbol»(){''');
										sb.append(System.lineSeparator)
										sb.append("\t\t").append('''return this.esecuzione.«fd.name».oldValues.get(''');
										sb.append(System.lineSeparator)
										sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_elemsList.get(«i»)).value;''');
										sb.append(System.lineSeparator)
										sb.append("\t").append('''}''');
									} else{
										if (fd.codomain.name.equals("Integer")){
											sb.append("\t").append('''public int get_«fd.name»_«symbol»(){''');
										}
										else if (fd.codomain.name.equals("Boolean")){
											sb.append("\t").append('''public boolean get_«fd.name»_«symbol»(){''');
										}
										else if (fd.codomain.name.equals("String")){
											sb.append("\t").append('''public String get_«fd.name»_«symbol»(){''');
										}
										else{
											sb.append("\t").append('''public «asmName».«fd.codomain.name» get_«fd.name»_«symbol»(){''');
										}
										sb.append(System.lineSeparator)
										sb.append("\t\t").append('''return this.esecuzione.«fd.name».oldValues.get(''');
										sb.append(System.lineSeparator)
										sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_elemsList.get(«i»));''');
										sb.append(System.lineSeparator)
										sb.append("\t").append('''}''');
									}
									sb.append(System.lineSeparator)
								}
							}// TODO: Ritornare pubblicamente dei valori astratti crea problemi perchè non si possono confrontare
							else if(dd instanceof AbstractTd){
								for (sf : asm.headerSection.signature.function) {
									if(sf instanceof StaticFunction){
										if(sf.codomain.equals(dd) && sf.domain===null){
											var symbol = sf.name
											sb.append(System.lineSeparator)
											if(fd.codomain instanceof ConcreteDomain){
												sb.append("\t").append('''public int get_«fd.name»_«symbol»(){''');
												sb.append(System.lineSeparator)
												sb.append("\t\t").append('''return this.esecuzione.«fd.name».oldValues.get(''');
												sb.append(System.lineSeparator)
												sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_Class.get(''');
												sb.append(System.lineSeparator)
												sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_elemsList.indexOf("«symbol»")).value);''');
												sb.append(System.lineSeparator)
												sb.append("\t").append('''}''');
											} else{
												if (fd.codomain.name.equals("Integer")){
													sb.append("\t").append('''public int get_«fd.name»_«symbol»(){''');
												}
												else if (fd.codomain.name.equals("Boolean")){
													sb.append("\t").append('''public boolean get_«fd.name»_«symbol»(){''');
												}
												else if (fd.codomain.name.equals("String")){
													sb.append("\t").append('''public Srting get_«fd.name»_«symbol»(){''');
												}
												else{
													sb.append("\t").append('''public «asm.name».«fd.codomain.name» get_«fd.name»_«symbol»(){''');
												}
												sb.append(System.lineSeparator)
												sb.append("\t\t").append('''return this.esecuzione.«fd.name».oldValues.get(''');
												sb.append(System.lineSeparator)
												sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_Class.get(''');
												sb.append(System.lineSeparator)
												sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_elemsList.indexOf("«symbol»")));''');
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
	}
	
	static def monitoredGetter(Asm asm, StringBuffer sb) {
			var asmName = asm.name;
			for (fd : asm.headerSection.signature.function) {
				if (fd instanceof MonitoredFunction) {
					if (fd.domain === null) {
						if (fd.codomain.name.equals("Boolean") && !(fd.codomain instanceof ConcreteDomain)) {
							sb.append('''

								private boolean get_«fd.name»(){
									return this.esecuzione.«fd.name».get();
								}
						 	''');
						}
						if (fd.codomain.name.equals("Integer") && !(fd.codomain instanceof ConcreteDomain)) {
							sb.append('''

								private int get_«fd.name»(){
									return this.esecuzione.«fd.name».get();
								}
						 	''');
						}
						if (fd.codomain.name.equals("String") && !(fd.codomain instanceof ConcreteDomain)) {
							sb.append('''

								private String get_«fd.name»(){
									return this.esecuzione.«fd.name».get();
								}
						 	''');
						}
						if (fd.codomain instanceof ConcreteDomain) {
							sb.append('''

								private int get_«fd.name»(){
									return this.esecuzione.«fd.name».get().value;
								}
						 	''');
						}
						if (fd.codomain instanceof EnumTd ||
							fd.codomain instanceof AbstractTd) {
							sb.append('''

								private «asmName».«fd.codomain.name» get_«fd.name»(){
									return this.esecuzione.«fd.name».get();
								}
						 	''');
						}
					}
					else{ // getter per le funzioni con Dominio -> Codominio
	
						for(dd : asm.headerSection.signature.domain){
							if(dd.equals(fd.domain)){
								if(dd instanceof EnumTd){
									for (var int i = 0; i < dd.element.size; i++) {
										var symbol = new ToString(asm).visit(dd.element.get(i))
										sb.append(System.lineSeparator)
										if(fd.codomain instanceof ConcreteDomain){ // considero subsetOf Integer
											sb.append("\t").append('''private int get_«fd.name»_«symbol»(){''');
											sb.append(System.lineSeparator)
											sb.append("\t\t").append('''return this.esecuzione.«fd.name».get(''');
											sb.append(System.lineSeparator)
											sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_elemsList.get(«i»)).value;''');
											sb.append(System.lineSeparator)
											sb.append("\t").append('''}''');
										} else{
											if (fd.codomain.name.equals("Integer")){
												sb.append("\t").append('''private int get_«fd.name»_«symbol»(){''');
											}
											else if (fd.codomain.name.equals("Boolean")){
												sb.append("\t").append('''private boolean get_«fd.name»_«symbol»(){''');
											}
											else if (fd.codomain.name.equals("String")){
												sb.append("\t").append('''private String get_«fd.name»_«symbol»(){''');
											}
											else{
												sb.append("\t").append('''private «asmName».«fd.codomain.name» get_«fd.name»_«symbol»(){''');
											}
											sb.append(System.lineSeparator)
											sb.append("\t\t").append('''return this.esecuzione.«fd.name».get(''');
											sb.append(System.lineSeparator)
											sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_elemsList.get(«i»));''');
											sb.append(System.lineSeparator)
											sb.append("\t").append('''}''');
										}
										sb.append(System.lineSeparator)
									}
								}
								else if(dd instanceof AbstractTd){
									for (sf : asm.headerSection.signature.function) { // controllo le funzioni statiche e prendo quelle che aggiungono al dominio astratto
										if(sf instanceof StaticFunction){
											if(sf.codomain.equals(dd) && sf.domain===null){
												var symbol = sf.name
												sb.append(System.lineSeparator)
												if(fd.codomain instanceof ConcreteDomain){
													sb.append("\t").append('''private int get_«fd.name»_«symbol»(){''');
													sb.append(System.lineSeparator)
													sb.append("\t\t").append('''return this.esecuzione.«fd.name».get(''');
													sb.append(System.lineSeparator)
													sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_Class.get(''');
													sb.append(System.lineSeparator)
													sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_elemsList.indexOf("«symbol»"))).value;''');
													sb.append(System.lineSeparator)
													sb.append("\t").append('''}''');
												} else{
													if (fd.codomain.name.equals("Integer")){
														sb.append("\t").append('''private int get_«fd.name»_«symbol»(){''');
													}
													else if (fd.codomain.name.equals("Boolean")){
														sb.append("\t").append('''private boolean get_«fd.name»_«symbol»(){''');
													}
													else if (fd.codomain.name.equals("String")){
														sb.append("\t").append('''private Srting get_«fd.name»_«symbol»(){''');
													}
													else{
														sb.append("\t").append('''private «asm.name».«fd.codomain.name» get_«fd.name»_«symbol»(){''');
													}
													sb.append(System.lineSeparator)
													sb.append("\t\t").append('''return this.esecuzione.«fd.name».get(''');
													sb.append(System.lineSeparator)
													sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_Class.get(''');
													sb.append(System.lineSeparator)
													sb.append("\t\t\t").append('''this.esecuzione.«fd.domain.name»_elemsList.indexOf("«symbol»")));''');
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
		}
	
	static def printControlled(Asm asm) {

		var sb = new StringBuffer;

		for (dd : asm.headerSection.signature.domain) {
			if (dd instanceof AbstractTd) {

				sb.append('''
					System.out.print("«dd.name»"+ " = {");
					for(int i=0 ; i< esecuzione.«dd.name»_elemsList.size(); i++)
						if(i!= esecuzione.«dd.name»_elemsList.size()-1)
							System.out.print(esecuzione.«dd.name»_elemsList.get(i) +", ");
						else
							System.out.print(esecuzione.«dd.name»_elemsList.get(i));
					System.out.println("}");
				''')

			}
		}

		for (fd : asm.headerSection.signature.function) {

			// Studio dei casi controlled con il dominio nullo, quindi funzioni che ricadono nella struttura zeroC<Valore>
			if (fd instanceof ControlledFunction) {
				if (fd.domain === null) {
					if (fd.codomain instanceof ConcreteDomain)
						sb.append('''
							System.out.println("«fd.name» = " + esecuzione.«fd.name».get().value);
						''')
					if (fd.codomain.name.equals("Integer") || fd.codomain.name.equals("Boolean") ||
						fd.codomain.name.equals("String"))
						sb.append('''
							System.out.println("«fd.name» = " + esecuzione.«fd.name».get());
						''')
					if (fd.codomain instanceof MapDomain)
						sb.append('''
							System.out.println("«fd.name» = " + esecuzione.«fd.name».get());
						''')
					if (fd.codomain instanceof SequenceDomain)
						sb.append('''
							System.out.println("«fd.name» = " + esecuzione.«fd.name».get());
						''')
					if (fd.codomain instanceof EnumTd)
						sb.append('''
							System.out.println("«fd.name» = " + esecuzione.«fd.name».oldValue.name());
						''')
				} else {

					if (fd.domain instanceof EnumTd && fd.codomain instanceof ConcreteDomain) {
						sb.append('''
							for(int i=0; i < esecuzione.«fd.domain.name»_elemsList.size(); i++){
								System.out.println(" «fd.name» =>  (" + esecuzione.«fd.domain.name»_elemsList.get(i) +
								") = " + esecuzione.«fd.name».oldValues.get(esecuzione.«fd.domain.name»_elemsList.get(i)).value);
							}
						''')
					}

					if (fd.domain instanceof EnumTd && fd.codomain instanceof EnumTd) {
						sb.append('''
							for(int i=0; i < esecuzione.«fd.domain.name»_elemsList.size(); i++){
								System.out.println("«fd.name» =>  (" + esecuzione.«fd.domain.name»_elemsList.get(i) +
								") = "+ esecuzione.«fd.name».oldValues.get(esecuzione.«fd.domain.name»_elemsList.get(i)));
							}
						''')
					}

				}
			}

		}

		return sb.toString
	}
	
	static 	def setMonitored(Asm asm) {
		var sb = new StringBuffer;
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof MonitoredFunction) {
				if (fd.domain === null) { // [] -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
					if (fd.codomain instanceof EnumTd) { // [] -> Enum
						sb.append('''
						this.esecuzione.«fd.name».set(«fd.name»);
						System.out.println("Set «fd.name» = " + «fd.name»);''')
						sb.append(System.lineSeparator)
						sb.append(System.lineSeparator)
					}
					else if (fd.codomain instanceof ConcreteDomain) { // [] -> ConcreteDomain
						sb.append('''
						this.esecuzione.«fd.name».set(
							«asm.name».«fd.codomain.name».valueOf(
							this.esecuzione.«fd.codomain.name»_elems.get(
							«fd.name» - this.esecuzione.«fd.codomain.name»_elems.get(0))));
						System.out.println("Set «fd.name» = " + «fd.name»);''')
						sb.append(System.lineSeparator)
						sb.append(System.lineSeparator)
					}
					else if (fd.codomain instanceof AbstractTd) { // [] -> Abstract
						sb.append('''
						this.esecuzione.«fd.name».set(
						this.esecuzione.«fd.codomain.name»_Class.get(
						this.esecuzione.«fd.codomain.name»_elemsList.indexOf(«fd.name»)));
						System.out.println("Set «fd.name» = " + «fd.name»);''')
				    	sb.append(System.lineSeparator)
				    	sb.append(System.lineSeparator)
					}
					else{ // [] -> (Integer|String|Boolean)
						sb.append('''
						this.esecuzione.«fd.name».set(«fd.name»);
						System.out.println("Set «fd.name» = " + «fd.name»);''')
						sb.append(System.lineSeparator)
						sb.append(System.lineSeparator)
					}
				} else { // (Enum|Abstract) -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
					var dd = fd.domain
					if(dd instanceof EnumTd){ // Enum -> ...
						for (var int i = 0; i < dd.element.size; i++) {
							var symbol = new ToString(asm).visit(dd.element.get(i))
							if(fd.codomain instanceof ConcreteDomain){ // Enum -> ConcreteDomain
								sb.append('''
								this.esecuzione.«fd.name».set(
								«asm.name».«dd.name».«symbol»,
								«asm.name».«fd.codomain.name».valueOf(this.esecuzione.«fd.codomain.name»_elems.get(«fd.name»_«symbol»)));
								System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);''')
							}
							else{ // Enum -> (Integer|String|Boolean|Enum|Abstract)
								sb.append('''
								this.esecuzione.«fd.name».set(
								«asm.name».«dd.name».«symbol», «fd.name»_«symbol»);''');
							}
							sb.append(System.lineSeparator)
							sb.append(System.lineSeparator)
						}
					}
					else if(fd.domain instanceof AbstractTd){ // Abstract -> ...
						for (sf : asm.headerSection.signature.function) {
							if(sf instanceof StaticFunction){
								if(sf.codomain.equals(fd.domain) && sf.domain===null){
									var symbol = sf.name
									if(fd.codomain instanceof ConcreteDomain){ // Abstract -> ConcreteDomain
										sb.append('''
										this.esecuzione.«fd.name».set(
										this.esecuzione.«fd.domain.name»_Class.get(
										this.esecuzione.«fd.domain.name»_elemsList.indexOf("«symbol»")),
										«asm.name».«fd.codomain.name».valueOf(this.esecuzione.«fd.codomain.name»_elems.get(«fd.name»_«symbol»)));
										System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);''')
									}
									else{ // Abstract -> (Integer|String|Boolean|Enum|Abstract)
										sb.append('''
										this.esecuzione.«fd.name».set(
										this.esecuzione.«fd.domain.name»_Class.get(
										this.esecuzione.«fd.domain.name»_elemsList.indexOf("«symbol»")),«fd.name»_«symbol»);
										System.out.println("Set «fd.name»_«symbol» = " + «fd.name»_«symbol»);''')
									}
									sb.append(System.lineSeparator)
									sb.append(System.lineSeparator)
								}
							}
						}
					}
				}
			}
		}

		return sb.toString
	}
	
	static def setMonitoredArgsMet(Asm asm, StringBuffer sb){
		sb.append('''setMonitored( ''');
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof MonitoredFunction) {
				if (fd.domain === null) { // [] -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
					sb.append(System.lineSeparator)
					sb.append("\t\t\t\t\t").append('''«fd.name»,''')
				}
				else {
					var dd = fd.domain
					if(dd instanceof EnumTd){ // Enum -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
						for (var int i = 0; i < dd.element.size; i++) {
							var symbol = new ToString(asm).visit(dd.element.get(i))
								sb.append(System.lineSeparator())
								sb.append("\t\t\t\t\t").append('''«fd.name»_«symbol»,''')
						}
					}
					if(fd.domain instanceof AbstractTd){ // Abstract -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
						for (sf : asm.headerSection.signature.function) {
							if(sf instanceof StaticFunction){
								if(sf.codomain.equals(fd.domain) && sf.domain===null){
									var symbol = sf.name
									sb.append(System.lineSeparator())
									sb.append("\t\t\t\t\t").append('''«fd.name»_«symbol»,''')
								}
							}
						}
					}
				}
			}
		}
		sb.setLength(sb.length() - 1);
		sb.append(''');''').append(System.lineSeparator());
	}
	
	static def setMonitoredArgsFunc(Asm asm, StringBuffer sb) {
		val asmName = asm.name
		sb.append(''' ''')
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof MonitoredFunction) {
				if (fd.domain === null) { // [] -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
					if (fd.codomain.name.equals("Boolean")) { // [] -> Boolean
						sb.append(System.lineSeparator()).append("\t\t")
						sb.append('''boolean «fd.name»,''')
					}
					else if (fd.codomain.name.equals("Integer") || (fd.codomain instanceof ConcreteDomain)) { // [] -> (Integer|ConcreteDomain)
						sb.append(System.lineSeparator()).append("\t\t")
						sb.append('''int «fd.name»,''')
					}
					else if (fd.codomain.name.equals("String")) { // [] -> String
						sb.append(System.lineSeparator()).append("\t\t")
						sb.append('''String «fd.name»,''')
					}
					else if (fd.codomain instanceof EnumTd) { // [] -> Enum
						sb.append(System.lineSeparator()).append("\t\t")
						sb.append('''«asmName».«fd.codomain.name» «fd.name»,''')
					}
					else if (fd.codomain instanceof AbstractTd) { // [] -> Abstract
						sb.append(System.lineSeparator()).append("\t\t")
						sb.append('''String «fd.name»,''')
					}
				}
				else { // (Enum|Abstract) -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
					var dd = fd.domain
					if(dd instanceof EnumTd){ // Enum -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
						for (var int i = 0; i < dd.element.size; i++) {
							var symbol = new ToString(asm).visit(dd.element.get(i))
							if(fd.codomain.name.equals("Integer") || (fd.codomain instanceof ConcreteDomain)){ // Enum -> (Integer|ConcreteDomain)
								sb.append(System.lineSeparator()).append("\t\t")
								sb.append('''int «fd.name»_«symbol»,''')
							}
							else if(fd.codomain.name.equals("Boolean")){ // Enum -> Boolean
								sb.append(System.lineSeparator()).append("\t\t")
								sb.append('''boolean «fd.name»_«symbol»,''')
							}
							else if(fd.codomain.name.equals("String")){ // Enum -> String
								sb.append(System.lineSeparator()).append("\t\t")
								sb.append('''String «fd.name»_«symbol»,''')
							}
							else /*if (fd.codomain instanceof EnumTd || fd.codomain instanceof AbstractTd)*/ { // Enum -> (Enum|Abstract)
								sb.append(System.lineSeparator()).append("\t\t")
								sb.append('''«asmName».«fd.codomain.name» «fd.name»_«symbol»,''')
							}
						}
					}
					else if(fd.domain instanceof AbstractTd){ // Abstract -> (Integer|String|Boolean|ConcreteDomain|Enum|Abstract)
						for (sf : asm.headerSection.signature.function) {
							if(sf instanceof StaticFunction ){
								if(sf.codomain.equals(fd.domain) && sf.domain===null){
									var symbol = sf.name
									if(fd.codomain.name.equals("Integer") || (fd.codomain instanceof ConcreteDomain)){ // Abstract -> (Integer|ConcreteDomain)
										sb.append(System.lineSeparator()).append("\t\t")
										sb.append('''int «fd.name»_«symbol»,''')
									}
									else if(fd.codomain.name.equals("Boolean")){ // Abstract -> Boolean
										sb.append(System.lineSeparator()).append("\t\t")
										sb.append('''boolean «fd.name»_«symbol»,''')
									}
									else if(fd.codomain.name.equals("String")){ // Abstract -> String
										sb.append(System.lineSeparator()).append("\t\t")
										sb.append('''String «fd.name»_«symbol»,''')
									}
									else /*if (fd.codomain instanceof EnumTd || fd.codomain instanceof AbstractTd)*/ { // Abstract -> (Enum|Abstract)
										sb.append(System.lineSeparator()).append("\t\t")
										sb.append('''«asmName».«fd.codomain.name» «fd.name»_«symbol»,''')
									}
								}
							}
						}
					}
				}
			}
		}
		sb.setLength(sb.length() - 1);
	}
		
}