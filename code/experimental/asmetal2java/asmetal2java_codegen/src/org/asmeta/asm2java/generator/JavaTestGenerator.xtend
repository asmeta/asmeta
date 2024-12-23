package org.asmeta.asm2java.generator

import asmeta.definitions.RuleDeclaration
import asmeta.structure.Asm
import asmeta.transitionrules.basictransitionrules.Rule
import org.asmeta.asm2java.translator.Util
import org.asmeta.asm2java.evosuite.RulesAdder
import org.asmeta.asm2java.evosuite.RuleToJavaEvosuite
import org.asmeta.asm2java.evosuite.JavaRule
import java.util.ArrayList
import org.asmeta.asm2java.translator.SeqRuleCollector
import org.asmeta.asm2java.evosuite.FunctionToJavaEvosuiteSig
import org.asmeta.asm2java.evosuite.DomainToJavaEvosuiteSigDef
import org.asmeta.asm2java.evosuite.ToStringEvosuite

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
	override ToStringEvosuite createToString(Asm resource) {
		new ToStringEvosuite(resource)
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
			
			import java.util.ArrayList;
			import java.util.Arrays;
			import java.util.Collections;
			import java.util.HashMap;
			import java.util.HashSet;
			import java.util.Map;
			import java.util.Set;
			import java.util.List;
			import java.util.Scanner;
			import org.apache.commons.collections4.bag.HashBag;
			import org.apache.commons.collections4.Bag;
			import java.util.concurrent.ThreadLocalRandom;
			import java.util.function.Function;
			import java.util.stream.Collectors;
			import org.javatuples.Decade;
			import org.javatuples.Ennead;
			import org.javatuples.Octet;
			import org.javatuples.Pair;
			import org.javatuples.Quartet;
			import org.javatuples.Quintet;
			import org.javatuples.Septet;
			import org.javatuples.Sextet;
			import org.javatuples.Triplet;
			
			
			class «asmName» {
				
				/////////////////////////////////////////////////
				/// DOMAIN CONTAINERS
				/////////////////////////////////////////////////
				/* Domain containers here */
				«abstractClassDef(asm)»
				«domainSignature(asm)»
				
				//Metodi di supporto per l'implementazione delle funzioni controlled
				
				class Fun0Ctrl<D> {
				   
				   D oldValue;
				   D newValue;
				   
				void set(D d) {
					
						newValue = d;
				}
				
				D get() {
					
						return oldValue;
				}
				}
				
				static class FunNCtrl<D, C> {
					
				Map<D, C> oldValues = new HashMap<>();
				Map<D, C> newValues = new HashMap<>();
				
				void set(D d, C c) {
					
						newValues.put(d, c);
				}
				
				C get(D d) {
					
						return oldValues.get(d);
				}
				}
				
				
				//Metodi di supporto per l'implementazione delle funzioni non controlled
				
				class Fun0<D> {
				   
				   D value;
				   
				void set(D d) {
					
						value = d;
				}
				
				D get() {
					
						return value;
				}
				}
				
				
				class FunN<D, C> {
					
				Map<D, C> values = new HashMap<>();
				
				void set(D d, C c) {
					
						values.put(d, c);
				}
				
				C get(D d) {
					
						return values.get(d);
				}
				}					
				
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
				void updateASM()
				{
					«asm.mainrule.name»();
					fireUpdateSet();
					initControlledWithMonitored();
				}
				
			}
			
		'''

	}

	// Metodo per riconoscere se la funzione ha o meno delle variabili in ingresso, traducendole
	override String ruleTranslationSig(RuleDeclaration r, String methodName, Asm asm) {
		return ""
	}

	override String ruleTranslationDef(RuleDeclaration r, String methodName, Asm asm) { 
		var rule = new JavaRule(methodName)
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
		
		// add the flag initialization to the top
		sb.insert(0, flagInit)
		
		// add the Rule to the rules Map
		rules.addRule(rule.getName(), rule)
		
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