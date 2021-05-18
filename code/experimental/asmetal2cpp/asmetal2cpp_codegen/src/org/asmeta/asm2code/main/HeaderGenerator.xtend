package org.asmeta.asm2code.main;

import asmeta.definitions.RuleDeclaration
import asmeta.definitions.domains.AbstractTd
import asmeta.structure.Asm
import asmeta.transitionrules.basictransitionrules.Rule
import java.util.List
import java.util.logging.Logger
import org.asmeta.asm2code.DomainContainerToH
import org.asmeta.asm2code.DomainToH
import org.asmeta.asm2code.FunctionToH
import org.asmeta.asm2code.Util
import org.asmeta.asm2code.main.TranslatorOptions.CompilerType
import org.asmeta.asm2code.SeqRuleCollector
import java.util.ArrayList
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess

import org.asmeta.asm2code.ImportToH

/**Generates .h ASM file */
class HeaderGenerator extends AsmToCGenerator {
	public static String Ext = ".h"
	static final String copyright = '''
		/* this file is under copyright*/
	'''
	static final Logger LOGGER = Logger.getLogger(HeaderGenerator.name)

	List<Rule> seqCalledRules;

	new () {
		super()
	}

	new (TranslatorOptions options) {
		super(options)
	}


	override String compileAsm(Asm asm) {
		if (options.optimizeSeqMacroRule) {
			seqCalledRules = new ArrayList
			for (r : asm.bodySection.ruleDeclaration)
				seqCalledRules.addAll(new SeqRuleCollector(false).visit(r))
		}
		val asmName = asm.name
		val String platformDependentHeaders = if (options.compilerType == CompilerType.ArduinoCompiler)
				'''
					#define ARDUINOCOMPILER
					#include <Arduino.h>
					// The following two libs have to be installed into your Arduino Sketchbook
					#include <ArduinoSTL.h>
					#include <boost_1_51_0.h>
					#include <string.h>				
					#include <iostream> 
					#include <vector> 
					#include <set>
					#include <map>
					#include <list>
					#include <boost/tuple/tuple.hpp>
					#include <LiquidCrystal.h>
					#include <LiquidCrystal_I2C.h>
					#include <DS3231.h>
					using namespace std;
					/*Arduino.h uses WString instead... */
				'''
			else
				'''
				/*Arduino.h uses WString instead... */
				#include <string.h>				
				#include <iostream> 
				#include <vector> 
				#include <set>
				#include <map>
				#include <list>
				//Andrea Belotti
				#include <chrono>
				//#include <tuple>
				//#include <bits/stl_tree.h>
				
				using namespace std;
				
				//typedef std::string String;'''
				//<list> instead of <boost/tuple/tuple.hpp>

		return '''
			«copyright»
				// «asmName».h automatically generated from ASMETA2CODE
				#ifndef «asmName.toUpperCase»_H
				#define «asmName.toUpperCase»_H
				
				«platformDependentHeaders»
				#define ANY String
				
				
				«includeLibrary(asm)»
				
				
			/* DOMAIN DEFINITIONS */
			namespace «asmName»namespace{
				«domainSignature(asm)» //devo togliere questo in Timer perché non serve
				}
			
				
				using namespace «asmName»namespace;
				
				«abstractClassDef(asm)» //devo togliere questo in Timer perché non serve
				
				class «asmName» «addExtension(asm)»{
				  
				/* DOMAIN CONTAINERS */
				«domainContainer(asm)»
				public:
				/* FUNCTIONS */
				«functionSignature(asm)»
				/* RULE DEFINITION */
				«ruleDefinition(asm)»
				
				«asmName»();
				
				void initControlledWithMonitored();
				
				void getInputs();
				
				void setOutputs(); 
				
				void fireUpdateSet();
			
				};
				#endif
		'''

	}

	/**TODO: domain initialization after «domainSignature(asm)»
	 * std::set< «object.name» > «Util.getElemsSetName(object.name)»;
	 */
	/* 	def domainInitialization(Asm asm) {
	 * 		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	 }*/
	/*def includeLibrary(Asm asm) {
	 * 	var String sb = new String("")
	 * 	for (def : asm.bodySection.domainDefinition) {
	 * 		// TODO : change IncludeLibraries.xtend
	 * 		var toappend = new IncludeLibraries(asm).visit(def)
	 * 		if (toappend !== null)
	 * 			sb += toappend
	 * 	}
	 * 	// ADD also the other definitions
	 * 	sb = sb.replace("null", "")
	 * 	var Set<String> includeset = new HashSet<String>(sb.toString.split("\n")) // eliminate duplicates
	 * 	return includeset.reduce[i1, i2|i1 + "\n" + i2]
	 }*/
	 
	 def includeLibrary(Asm asm){
	 	var sb = new StringBuffer;
	 	for (i : asm.headerSection.importClause){ 
	 		if(i !== null ){
	 			var String s = new ImportToH(asm).visit(i)
	 			if (!s.contains("StandardLibrary")
 				&& !s.contains("CTLlibrary")
 				&& !s.contains("LTLlibrary")) {// Ignore StandardLibrary, CTllibrary and LTLlibrary import.
 				if (options.compilerType != CompilerType.ArduinoCompiler)
	 					sb.append('#include "'  + new ImportToH(asm).visit(i) + '.h" \n')
	 			else {
	 				var String[] buffer = new ImportToH(asm).visit(i).split('/');
	 				sb.append('#include "' + buffer.get(buffer.size - 1 ) + '.h" \n')
	 			} 
	 			}
	 		}
	 			
	 	}
	 	return sb.toString
	 }
	 
	 def addExtension(Asm asm){
	 	var sb = new StringBuffer;
	 	var boolean isFirst = true;
	 	for(i : asm.headerSection.importClause){
	 		if(i !== null){
	 			var s = new ImportToH(asm).visit(i)
	 			if(!s.contains("StandardLibrary")
 				&& !s.contains("CTLlibrary")
 				&& !s.contains("LTLlibrary")) { //Ignore StandardLibrary, CTllibrary and LTLlibrary import.
		 			var String[] splitted = s.split("/")
		 			var String s1 = splitted.get(splitted.length-1)
		 			if(isFirst){
		 				isFirst = false;
		 				sb.append(": public virtual " + s1)
	 				}
			 		else sb.append(" , public virtual " +s1)
 				}
	 		}
	 	}
	 	return sb.toString
	 }
	 
	/** DONE */
	def domainSignature(Asm asm) {
		var sb = new StringBuffer;
		for (dd : asm.headerSection.signature.domain) {
			if (dd instanceof AbstractTd)
				sb.append("class " + new DomainToH(asm).visit(dd) + ";\n")
			else
				sb.append(new DomainToH(asm).visit(dd) + "\n")
		}
		return sb.toString
	}

	def abstractClassDef(Asm asm) {
		var sb = new StringBuffer;
		for (dd : asm.headerSection.signature.domain) {
			if (dd instanceof AbstractTd)
				sb.append("class " + asm.name + "namespace::" + new DomainToH(asm).visit(dd) + "{\n" + defineElems(dd) +
					"};\n")
		}
		return sb.toString
	}

	/*
	 * def domainSignature(Asm asm) {
	 * 	var sb = new StringBuffer;
	 * 	for (dd : asm.headerSection.signature.domain) {
	 * 		if (dd instanceof AbstractTd)
	 * 			sb.append("class " + new DomainToH(asm).visit(dd) + "{\n" + defineElems(dd) +"};\n")
	 * 		else
	 * 			sb.append(new DomainToH(asm).visit(dd) + "\n")
	 * 	}
	 * 	return sb.toString
	 * }
	 */
	def defineElems(AbstractTd td) {
		var sb = new StringBuffer;
		sb.append("public:\n")
		sb.append("static " + options.stdNamespacePrefix + "set<" + td.name + "*> elems;\n")
		sb.append(td.name + "(){elems.insert(this);}\n")
		return sb.toString
	}

	def domainContainer(Asm asm) {
		var sb = new StringBuffer;
		for (dd : asm.headerSection.signature.domain) {
			if (!(dd instanceof AbstractTd))
				sb.append(new DomainContainerToH(asm).visit(dd) + "\n")
		}
		return sb.toString
	}

	/** DONE */
	def functionSignature(Asm asm) {
		var sb = new StringBuffer;
		for (fd : asm.headerSection.signature.function) {
			sb.append(new FunctionToH(asm, options).visit(fd) + "\n")
		}
		return sb.toString
	}

	/** DONE */
	def ruleDefinition(Asm asm) {
		var StringBuffer sb = new StringBuffer
		for (var i = 0; i < asm.bodySection.ruleDeclaration.size; i++)
			sb.append(ruleDeclaration(asm.bodySection.ruleDeclaration.get(i), asm))
		return sb.toString
	}

	def String ruleDeclaration(RuleDeclaration r, Asm asm) {
		var bb = #{true, false}
		var StringBuffer result = new StringBuffer
		// seq variant if required
		if (seqCalledRules === null || seqCalledRules.contains(r.ruleBody)) {
			result.append(foo(r, r.name + "_seq", asm))
		}
		// not seq done in any case
		result.append(foo(r, r.name, asm))
		/*for (boolean seq : bb) {
		 * 	var methodName = object.name
		 * 	if(seq) methodName += "_seq"
		 * 	foo(object, result, methodName, asm)
		 }*/
		return result.toString
	}

	def protected String foo(RuleDeclaration r, String methodName, Asm asm) {
		if (r.arity == 0)
			return (''' 
				void «methodName»();
			''')
		else {
			return ('''
				void «methodName» («new Util().adaptRuleParam(r.variable, asm).replaceAll("\\$","_")»);
			''')
		}
	}
	
	override doGenerate(Resource input, IFileSystemAccess fsa) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

/*def private String adaptParam(List<VariableTerm> parameters,  Asm res) {
 * 	var StringBuffer paramDef = new StringBuffer
 * 	var EList<SingleParameterDefinition> singleParam = parameters.singleParam
 * 	for (var i = 0; i < singleParam.size; i++) {
 * 		if (singleParam.get(i).idIn !== null)
 * 			paramDef.append(
 * 				'''«new ToString(res).doSwitch(singleParam.get(i).domainIn)» «singleParam.get(i).idIn», ''')
 * 		if (singleParam.get(i).id !== null)
 * 			throw new RuntimeException("IdDomain null in rule definition not allowed")
 * 		if (singleParam.get(i).domain !== null)
 * 			throw new RuntimeException("Domain null in rule definition not allowed")
 * 	}
 * 	return paramDef.substring(0, paramDef.length - 2)
 * }
 }*/
 
 
}
