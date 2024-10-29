package org.asmeta.asm2java.generator;

import asmeta.definitions.ControlledFunction
import asmeta.definitions.DerivedFunction
import asmeta.definitions.RuleDeclaration
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.EnumTd
import asmeta.structure.Asm
import asmeta.transitionrules.basictransitionrules.Rule
import org.asmeta.asm2java.translator.DomainToJavaSigDef
import org.asmeta.asm2java.translator.FindMonitoredInControlledFunct
import org.asmeta.asm2java.translator.FunctionToJavaDef
import org.asmeta.asm2java.translator.FunctionToJavaSig
import org.asmeta.asm2java.translator.RuleToJava
import org.asmeta.asm2java.translator.SeqRuleCollector
import org.asmeta.asm2java.translator.Util
import org.asmeta.asm2java.translator.ToString
import org.asmeta.asm2java.config.TranslatorOptions
import org.junit.Assert
import java.util.ArrayList
import java.util.List


/**Generates .java ASM file */
class JavaGenerator extends AsmToJavaGenerator {

	protected String initConrolledMonitored

	def compileAndWrite(Asm asm, String writerPath, TranslatorOptions userOptions) {
		Assert.assertTrue(writerPath.endsWith(".java"));
		compileAndWrite(asm, writerPath, "JAVA", userOptions)
	}

	// all the rules that must translate in two versions seq and not seq
	// if null, translate all
	protected List<Rule> seqCalledRules;

	protected String supp

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
			
			
			abstract class «asmName»Sig {
				
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
				
				////////////////////////////////////////////////
				/// RULE DEFINITION
				/////////////////////////////////////////////////
				/* Rule definition here */
				
				«ruleDefinition(asm)»
			}
			
			
			
			class «asmName» extends «asmName»Sig {
				
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
				
				public static void main(String[] args) {
					// TODO: auto-generated main method by Asmeta2Java 
				}
				
			}
			
		'''

	}

	// Prima parte dedicata allo studio dei metodi per la creazione della classe astratta che rappresenta la parte
	// signature del programma ASM, nel progetto precedente era la classe destinata alla creazione dell' header
	// Definisce un dominio di tipo astratto
	def abstractClassDef(Asm asm) {
		var sb = new StringBuffer;
		for (dd : asm.headerSection.signature.domain) {
			if (dd instanceof AbstractTd)
				sb.append("//Variabile di tipo astratto" + "\n\n" + new DomainToJavaSigDef(asm).visit(dd) + "\n")

		}
		return sb.toString
	}

	// Definisce una variabile concreta o enumerativa
	def domainSignature(Asm asm) {
		var sb = new StringBuffer;
		for (dd : asm.headerSection.signature.domain) {

			if (dd instanceof AbstractTd == false)
				sb.append(
					"//Variabile di tipo Concreto o Enumerativo" + "\n\n" + new DomainToJavaSigDef(asm).visit(dd) +
						"\n")

		}
		return sb.toString
	}

	// Metodo per studiare le function del programma ASM
	def functionSignature(Asm asm) {
		var sb = new StringBuffer;
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof DerivedFunction)
				for (fDef : asm.bodySection.functionDefinition) {
					if (fDef.definedFunction.name.equals(fd.name))
						sb.append(new FunctionToJavaSig(asm).visit(fd) + "\n")
				}
			else
				sb.append(new FunctionToJavaSig(asm).visit(fd) + "\n")
		}
		return sb.toString
	}

	// Studio delle regole introdotte nel programma ASM
	def ruleDefinition(Asm asm) {
		var StringBuffer sb = new StringBuffer
		for (var i = 0; i < asm.bodySection.ruleDeclaration.size; i++)
			sb.append(ruleDeclaration(asm.bodySection.ruleDeclaration.get(i), asm))
		return sb.toString
	}

	// metodo di supporto per identificare le regole
	def String ruleDeclaration(RuleDeclaration r, Asm asm) {

		var bb = #{true, false}
		var StringBuffer result = new StringBuffer
		if (seqCalledRules === null || seqCalledRules.contains(r.ruleBody)) {
			result.append(foo(r, r.name + "_seq", asm))
		}
		result.append(foo(r, r.name, asm))
		return result.toString

	}

	// Metodo per riconoscere se la funzione ha o meno delle variabili in ingresso, traducendole
	protected def String foo(RuleDeclaration r, String methodName, Asm asm) {
		if (r.arity == 0)
			return (''' 
				abstract void «methodName»();
				
			''')
		else {
			return ('''
				abstract void «methodName» («new Util().adaptRuleParam(r.variable, asm).replaceAll("\\$","_")»);
				
			''')
		}
	}

	// Seconda parte dedicata allo studio dei metodi per la creazione della classe che rappresenta la parte
	// definitions del programma ASM, nel progetto precedente era la classe destinata alla creazione del file cpp
	// Metodo per settare i valori dei domini definiti static nel signature
	def initialStaticDomainDefinition(Asm asm) {

		var StringBuffer initial = new StringBuffer

		if (asm.bodySection !== null && asm.bodySection.domainDefinition !== null) {
			for (dd : asm.bodySection.domainDefinition) {

				initial.append(
					dd.definedDomain.name + ".elems = Collections.unmodifiableList(Arrays.asList" +
						new DomainToJavaSigDef(asm).visit(dd) + ");\n")
				initial.append(
					dd.definedDomain.name + "_elems = Collections.unmodifiableList(Arrays.asList" +
						new DomainToJavaSigDef(asm).visit(dd) + ");\n")
			}
		}

		if (initial.toString.length != 0)
			return initial.toString + "\n"
		else
			return ""
	}
	
	// Metodo per inizializzare gli array _elemsList associati ai domini enumerativi
	def initialStaticEnumDomainDefinition(Asm asm) {
		
		var StringBuffer initial = new StringBuffer
		
		if (asm.bodySection !== null && asm.bodySection.domainDefinition !== null) {
			for (dd : asm.headerSection.signature.domain) {
				if(dd instanceof EnumTd){
					initial.append(
						dd.name + "_elemsList = Collections.unmodifiableList(Arrays.asList(")
						
					for (var int i = 0; i < dd.element.size; i++) {
						
						if (i != dd.element.size - 1)
							initial.append('''«dd.name».«new ToString(asm).visit(dd.element.get(i))», ''')
						else
							initial.append('''«dd.name».«new ToString(asm).visit(dd.element.get(i))»)''')
					}

					initial.append(");\n")
				}
			}
		}

		if (initial.toString.length != 0)
			return initial.toString + "\n"
		else
			return ""
	}
	

	// Metodo per settare i valori dei domini definiti Dynamic nel signature
	// Da controllarne l'uso: Dynamic domains must be initialized, not defined!
	def initialDynamicDomainDefinition(Asm asm) {

		var StringBuffer initial = new StringBuffer

		if (asm.defaultInitialState !== null && asm.defaultInitialState.domainInitialization !== null) {

			for (dd : asm.defaultInitialState.domainInitialization) {

				val domaintojava = new DomainToJavaSigDef(asm).visit(dd)
				initial.append(Util.getElemsSetName(dd.initializedDomain.name) + "=" + domaintojava + ";\n")
			}
		}
		if (initial.length != 0)
			return initial.toString + "\n"
		else
			return ""
	}

	// Metodo per definire il dominio di una classe Astratta
	def functionAbstractDom(Asm asm) {
		var sb = new StringBuffer;
		for (fd : asm.headerSection.signature.function) {
			if (fd instanceof StaticFunction && fd.codomain instanceof AbstractTd && fd.domain === null) {
				supp = new String('"');

				sb.append(fd.name + " = new " + fd.codomain.name + "(" + supp + fd.name + supp + ")" + ";\n")

				sb.append(fd.codomain.name + "_elemsList.add(" + supp + fd.name + supp + ");\n")
				sb.append(fd.codomain.name + "_Class.add(" + fd.name + ");\n")

			}
		}
		return sb.toString
	}

	// Metodo per l'inizializzazione delle funzioni definite nell'initial state
	def functionInitialization(Asm asm) {

		var StringBuffer initial = new StringBuffer
		var StringBuffer initialMonitored = new StringBuffer
		var boolean containsMonitored = false

		if (asm.defaultInitialState !== null && asm.defaultInitialState.functionInitialization !== null) {

			for (fd : asm.defaultInitialState.functionInitialization) {

				containsMonitored = new FindMonitoredInControlledFunct().visit(fd.body);

				if (containsMonitored == false)
					initial.append(
		  					'''«new FunctionToJavaDef(asm).visit(fd.initializedFunction)»
					''')
				else
					initialMonitored.append(
		  					'''«new FunctionToJavaDef(asm).visit(fd.initializedFunction)»
					''')
			}
		}

		initConrolledMonitored = initialMonitored.toString.replaceAll("\\$", "_")
		return initial.toString.replaceAll("\\$", "_")
	}

	// Metodo per il settaggio delle funzioni statiche e derivate
	def functionDefinition(Asm asm) { // Static and Derived function
		var StringBuffer sb = new StringBuffer

		if (asm.bodySection !== null && asm.bodySection.functionDefinition !== null) {

			for (fd : asm.bodySection.functionDefinition)
				sb.append(
		  					'''«new FunctionToJavaDef(asm).visit(fd.definedFunction)»
				''')
			return sb.toString.replaceAll("\\$", "_")
		}

		return ""
	}

	// Metodo per la traduzione delle regole del programma ASM
	def ruleDefinitions(Asm e) {

		var StringBuffer sb = new StringBuffer
		for (r : e.bodySection.ruleDeclaration)
			sb.append(ruleImplementation(r, e).replaceAll("\\$", "_"))
		return sb.toString

	}

	def String ruleImplementation(RuleDeclaration r, Asm asm) {

		var StringBuffer result = new StringBuffer

		if (seqCalledRules === null || seqCalledRules.contains(r.ruleBody)) {
			result.append(ruleTranslation(r, r.name + "_seq", asm))
		}

		result.append(ruleTranslation(r, r.name, asm))
		return result.toString
	}

	/**
	 * Method to build rule body in Java. (former foo2)
	 * 
	 */
	def String ruleTranslation(RuleDeclaration r, String methodName, Asm asm) {
		if (r.arity == 0)
			return ('''
				@Override
				void «methodName»(){
					«new RuleToJava(asm,false,options).visit(r.ruleBody as Rule)»
				}
				
			''')
		else
			return ( '''
				@Override
				void «methodName» («new Util().adaptRuleParam(r.variable, asm)»){
					«new RuleToJava(asm,false,options).visit(r.ruleBody)»
				}
				
			''')

	}

	// Aggiornamento delle variabile controllate di stato in stato
	def updateSet(Asm asm) {
		var StringBuffer updateset = new StringBuffer
		for (cf : asm.headerSection.signature.function)
			if (cf instanceof ControlledFunction && cf.domain !== null)
				updateset.append('''«cf.name».oldValues = «cf.name».newValues;
				''')
			else if (cf instanceof ControlledFunction && cf.domain === null)
				updateset.append('''«cf.name».oldValue = «cf.name».newValue;
				''')
		return updateset.toString
	}

}
