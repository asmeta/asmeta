package asmeta.asmetal2java.codegen.evosuite

import asmeta.definitions.domains.ConcreteDomain
import asmeta.asmetal2java.codegen.translator.DomainToJavaString
import java.util.Arrays
import asmeta.structure.Asm
import asmeta.definitions.Function
import asmeta.definitions.domains.EnumTd

class AsmMethodsUtil {
	
	public static val BOOLEAN = "Boolean"
	public static val INTEGER = "Integer"
	public static val REAL = "Real"
	public static val DOUBLE = "Double"
	public static val STRING = "String"
	public static val CHAR = "Char"
	public static val CHARACTER = "Character"
	public static val basicTdList = Arrays.asList(BOOLEAN, INTEGER, REAL, STRING, CHAR, DOUBLE, CHARACTER) 
	// Double and Character are the translated domains of Real and Char
	
	/**
	 * Get the specific domain type under consideration.
	 */
	protected def static String getConcreteDomainType(Asm asm, Function fd, String concreteDomName) {
		for(to : asm.headerSection.signature.domain){
			if(to instanceof ConcreteDomain){
				if(to.name.equals(concreteDomName)){
					var type = new DomainToJavaString(asm).visit(to.typeDomain)
					if(!basicTdList.contains(type)){
						type = asm.name.concat(".").concat(type)
					}
					return type
				}
			}
		}
	}
	
	/**
	 * Get the specific basic domain type under consideration.
	 */
	protected def static String getBasicTdType(String domainType) {
		var type = domainType
		switch (type){
			case BOOLEAN:
			type="boolean"
			case INTEGER:
				type="int"
			case REAL:
				type="double"
			case CHAR:
				type="char"
			case STRING:
				type="String"
		}
		return type
	}
	
	/**
	 * Get the specific wrapper object of the basic domain type under consideration.
	 */
	protected def static String getWrapperBasicTdType(String domainType) {
		var type = domainType
		switch (type){
			case BOOLEAN:
			type="Boolean"
			case INTEGER:
				type="Integer"
			case REAL:
				type="Double"
			case CHAR:
				type="Character"
			case STRING:
				type="String"
			case DOUBLE:
				type="Double"
			case CHARACTER:
				type="Character"
		}
		return type
	}
	
	/**
	 * Get the specific parsing method for the argument type.
	 */
	protected def static String getParsingMethod(String domainType) {
		var type = domainType
		switch (type){
			case BOOLEAN:
			type="Boolean::parseBoolean"
			case INTEGER:
				type="Integer::parseInt"
			case REAL:
				type="Double::parseDouble"
			case DOUBLE:
				type="Double::parseDouble"		
			case CHAR:
				type="e -> e.charAt(0)"
			case CHARACTER:
				type="e -> e.charAt(0)"
			case STRING:
				type="e -> e"
		}
		return type
	}
	
	/**
	 * 	Generate and return the method signature for getter functions
	 */
	protected def static String getMethodSignature(String asmName, String methodGetterSignature, String codomain) {
		var methodDeclaration = "\t"
		if (codomain.equals(INTEGER)){ // ... -> Integer
			methodDeclaration += ('''public Integer «methodGetterSignature»(){''');
		}
		else if (codomain.equals(REAL)){ // ... -> Real
			methodDeclaration += ('''public Double «methodGetterSignature»(){''');
		}
		else if (codomain.equals(BOOLEAN)){ // ... -> Boolean
			methodDeclaration += ('''public Boolean «methodGetterSignature»(){''');
		}
		else if (codomain.equals(STRING)){ // ... -> String
			methodDeclaration += ('''public String «methodGetterSignature»(){''');
		}
		else if (codomain.equals(CHAR)){ // ... -> Character
			methodDeclaration += ('''public Character «methodGetterSignature»(){''');
		}
		else{  // ... -> Enum (and other)
			methodDeclaration += ('''public «asmName».«codomain» «methodGetterSignature»(){''');
		}
		
	}
	
	/**
	 * Generates and returns the getter for a function with sequence codomain.
	 */
	protected def static String genSequenceGetter(String functionName, String type){
		var sb = new StringBuffer();
		sb.append('''
			public String get_«functionName»(){
				java.util.List<«type»> list = this.execution.«functionName».get();
				if(list == null || list.isEmpty()){
					return "[]";
				}
				return "[" + list.toString() + "]";
				    
			}
			''');
		return sb.toString()
	}
	
	/**
	 * Generates and returns the setter for a function with sequence codomain.
	 */
	protected def static String genSequenceSetter(String functionName, String type, String parsingMethod){
		var sb = new StringBuffer();
		sb.append('''
			public void set_sequence_«functionName»(String «functionName») {
				java.util.List<«type»> list = 
					java.util.Arrays.stream(«functionName».replaceAll("[\\[\\]]", "").split(","))
						.map(«parsingMethod»)
						.collect(java.util.stream.Collectors.toList());
				this.execution.«functionName».set(list);
				System.out.println("Set «functionName» = " + «functionName»);
			}''');
		return sb.toString()
	}
	
	/**
	 * Generates the private method that covers the outputs
	 */
	protected def static String genCoverOutputMethod(Function fd, String enumState, Asm asm) {
		var sb = new StringBuffer;
		sb.append(System.lineSeparator);
		sb.append("\t").append('''private void cover_«fd.name»_fromDomain_«enumState»(){''');
		sb.append(System.lineSeparator)
		sb.append("\t\t").append('''if(this.get_«fd.name»_fromDomain_«enumState»() == null){''');
		sb.append(System.lineSeparator)
		sb.append("\t\t\t").append('''return;''');
		sb.append(System.lineSeparator)
		sb.append("\t\t").append('''}''')
		sb.append(System.lineSeparator)
		sb.append("\t\t").append('''switch(this.get_«fd.name»_fromDomain_«enumState»()){''');
		for(ddd : asm.headerSection.signature.domain){
		if(ddd.equals(fd.codomain)){
			if(ddd instanceof EnumTd){
				for (var int j = 0; j < ddd.element.size; j++) {
					var output = new DomainToJavaStringEvosuite(asm).visit(ddd.element.get(j))
					sb.append(System.lineSeparator)
					sb.append("\t\t\t").append('''case «output» :
					System.out.println("Branch «fd.domain.name» -> «fd.codomain.name» «output» covered");
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
		return sb.toString()
	}
	
}