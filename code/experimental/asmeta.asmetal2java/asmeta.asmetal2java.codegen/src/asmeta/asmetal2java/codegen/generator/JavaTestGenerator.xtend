package asmeta.asmetal2java.codegen.generator

import asmeta.definitions.RuleDeclaration
import asmeta.structure.Asm
import asmeta.transitionrules.basictransitionrules.Rule
import asmeta.asmetal2java.codegen.translator.Util
import asmeta.asmetal2java.codegen.evosuite.RulesAdder
import asmeta.asmetal2java.codegen.evosuite.RuleToJavaEvosuite
import asmeta.asmetal2java.codegen.evosuite.JavaRule
import asmeta.asmetal2java.codegen.translator.SeqRuleCollector
import asmeta.asmetal2java.codegen.evosuite.FunctionToJavaEvosuiteSig
import asmeta.asmetal2java.codegen.evosuite.DomainToJavaEvosuiteSigDef
import asmeta.asmetal2java.codegen.evosuite.DomainToJavaStringEvosuite
import java.util.ArrayList
import asmeta.asmetal2java.codegen.translator.FunctionClassDef

/**
 * This generator creates a translated version of the Java class for testing purposes only,
 *  in fact it creates the class that the _Atg class queries.
 */
class JavaTestGenerator extends JavaGenerator {
	
	RulesAdder rules;
	
	new(RulesAdder rules){
		super()
		this.rules = rules
	}
	
		/**
	 * Create an instance of the {@code DomainToJavaEvosuiteSigDef} object.
	 */
	override DomainToJavaEvosuiteSigDef createDomainToJavaSigDef(Asm resource) {
		new DomainToJavaEvosuiteSigDef(resource)
	}
	
	/**
	 * Create an instance of the {@code ToString} object.
	 */
	override DomainToJavaStringEvosuite createToString(Asm resource) {
		new DomainToJavaStringEvosuite(resource)
	}
	
	/**
	 * Create an instance of the {@code FunctionToJavaSig} object.
	 */
	override FunctionToJavaEvosuiteSig createFunctionToJavaSig(Asm resource) {
		new FunctionToJavaEvosuiteSig(resource)
	}
	
	override compileAsm(Asm asm) {
		// collect alla the seq rules if required
		if (options.getOptimizeSeqMacroRule()) {
			seqCalledRules = new ArrayList
			for (r : asm.bodySection.ruleDeclaration)
				seqCalledRules.addAll(new SeqRuleCollector(false).visit(r))
		}
		//
		val asmName = asm.name
		var updateASMText = updateSet(asm)
		functionSignature(asm)
		// TODO fix include list
		return '''
			
			// «asmName».java automatically generated from ASM2CODE
			
			«getImports()»
			
			class «asmName» {
				
				/////////////////////////////////////////////////
				/// DOMAIN CONTAINERS
				/////////////////////////////////////////////////
				/* Domain containers here */
				«abstractClassDef(asm)»
				«domainSignature(asm)»
				
				//Support methods for implementing controlled functions
				
				«FunctionClassDef.getFun0CtrlClass()»
				
				«FunctionClassDef.getFunNCtrlClass()»
				
				//Support methods for the implementation of non-controlled functions
				
				«FunctionClassDef.getFun0Class()»
				
				«FunctionClassDef.getFunNClass()»
				
				/////////////////////////////////////////////////
				/// FUNCTIONS
				/////////////////////////////////////////////////
				«functionSignature(asm)»
				
				// Inizializzazione di funzioni e domini
				
				«asmName»(){
				
				//Definizione iniziale dei domini statici
				 
				 «initialStaticDomainDefinition(asm)»
				 «initialStaticEnumDomainDefinition(asm)»
				
				 //Definizione iniziale dei domini dinamici
				 
				 «initialDynamicDomainDefinition(asm)»
				
				 //Definizione iniziale dei domini astratti con funzini statiche
				 
				 «functionAbstractDom(asm)»
				
				 //Inizializzazione delle funzioni
				 
				 «functionInitialization(asm)»
				
				}
				
				   // Definizione delle funzioni statiche
				«functionDefinition(asm)»
				
				// Conversione delle regole ASM in metodi java
				
				«ruleDefinitions(asm)»
				
				// inizializazzione delle funzioni controllate che contengono metodi monitorati nei temini iniziali
				void initControlledWithMonitored(){
				  «(initConrolledMonitored.length == 0 ? "// No controlled functions initialized with monitored ones have been found" : initConrolledMonitored)»
				   }
				
				// applicazione dell'aggiornamento del set
				void fireUpdateSet(){
					
					 «updateASMText.length == 0 ? "// No update set has been found" : updateASMText»
				}
				
				//Metodo per l'aggiornamento dell'asm
				«getUpdateASM(asm)»
				
			}
			
		'''

	}

	// Metodo per riconoscere se la funzione ha o meno delle variabili in ingresso, traducendole
	override String ruleTranslationSig(RuleDeclaration r, String methodName, Asm asm) {
		return ""
	}

	override String ruleTranslationDef(RuleDeclaration r, String methodName, Asm asm) { 
		var rule = new JavaRule()
		// add the rule to the rules Map and get the rule name
		rule.name = rules.addRule(methodName, rule)
		var sb = new StringBuffer();
		if (r.arity == 0){
			sb.append('''
				void «methodName»(){
					«rule.addNewBranch()» = true;
					«new RuleToJavaEvosuite(asm, false, options, rule).visit(r.ruleBody as Rule)»
				}
				
			''');
			} else {
			sb.append('''
				void «methodName» («new Util().adaptRuleParam(r.variable, asm)»){
					«rule.addNewBranch()» = true;
					«new RuleToJavaEvosuite(asm, false, options, rule).visit(r.ruleBody)»
				}
				
			''');
			}

		// initialize the branches flag
		var flagInit = coverBranchesFlagInit(rule);
		
		// add the boolean flag initialization before the method declaration
		sb.insert(0, flagInit)
		
		return sb.toString;
		
	}
	
	// initialize the cover branch flags (example: boolean cover_r_main = false;)
	private def coverBranchesFlagInit(JavaRule rule){
		val sb = new StringBuffer();
		for(String branch : rule.branches ){
			sb.append('''boolean «branch» = false;''');
			sb.append(System.lineSeparator);
		}
		return sb.toString();
	}
	
}