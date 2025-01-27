package org.asmeta.asm2java.generator

import asmeta.structure.Asm
import java.util.List
import asmeta.transitionrules.basictransitionrules.Rule
import org.junit.Assert
import java.util.ArrayList
import org.asmeta.asm2java.translator.SeqRuleCollector
import org.asmeta.asm2java.config.TranslatorOptions
import org.asmeta.asm2java.evosuite.CoverRules
import org.asmeta.asm2java.evosuite.CoverOutputs
import org.asmeta.asm2java.evosuite.AsmMethods
import org.asmeta.asm2java.evosuite.RulesGetter

/**
 * This generator creates a Java wrapper class that queries the translated java class 
 * with the aim of generating tests with an external tool like Evosutie.
 */
class JavaAtgGenerator extends AsmToJavaGenerator {

	RulesGetter rules;
	
	new(RulesGetter rules){
		super()
		this.rules = rules
	}

	def compileAndWrite(Asm asm, String writerPath, TranslatorOptions userOptions) {
		Assert.assertTrue(writerPath.endsWith(".java"));
		compileAndWrite(asm, writerPath, "JAVA", userOptions)
	}

	// all the rules that must translate in two versions seq and not seq
	// if null, translate all
	List<Rule> seqCalledRules; 

	override compileAsm(Asm asm) {
		// collect alla the seq rules if required
		if (options.getOptimizeSeqMacroRule()) {
			seqCalledRules = new ArrayList
			for (r : asm.bodySection.ruleDeclaration)
				seqCalledRules.addAll(new SeqRuleCollector(false).visit(r))
		}

		val asmName = asm.name

		var sb = new StringBuffer;

		sb.append(
			'''
		
		// «asmName»_ATG.java automatically generated from ASM2CODE
		
		import java.util.Scanner;
		
		/**
		* This class is designed to simulate the behavior of an abstract state machine in an automated way.
		*
		* <p>
		* It has been optimized to be used with evosuite in order to automatically generate test scenarios.
		* </p>
		*/
		class «asmName»_ATG {
		
			/** Instance of the asmeta specification translated into a java class.*/
			private final «asmName» execution;
			
			/** current state. */
			private int state;
		
		   /**
		    * Constructor of the {@code «asmName»_ATG} class. Creates a private instance of the asm
		    * {@link «asmName»} and sets the initial state of the state machine to 0.
		    */
			public «asmName»_ATG(){
				this.execution = new «asmName»();
				this.state = 0;
			}
		
			/** The step function allows to perform a step of the asm by incrementing the state.
			*/
			public void step(){
				
				this.execution.updateASM();
				
				System.out.println("</State "+ state +" >");
				
				''');
			
		if(options.coverRules){
			sb.append(System.lineSeparator)
			sb.append('''
				// Cover the rules
				coverRules();''');

			sb.append(System.lineSeparator)
		}
			
		if(options.coverOutputs){
			sb.append(System.lineSeparator)
			sb.append('''
				// Cover the outputs
				coverOutputs();''');

			sb.append(System.lineSeparator)
		}
			
		sb.append("\t\t\t\t\t\t" ).append('''state++;
				}''')
		// End Step Function		
		
		// Cover the rules
		if(options.coverRules){
			
			sb.append(System.lineSeparator);
			sb.append("\t" ).append('''/* Cover the Rules */''')
			sb.append(System.lineSeparator)
			sb.append(System.lineSeparator)
			
			sb.append(System.lineSeparator)
			sb.append(CoverRules.coverRulesFunction(rules))
			sb.append(System.lineSeparator);
			
			sb.append(System.lineSeparator)
			sb.append(CoverRules.coverAllRules(rules))
			sb.append(System.lineSeparator);
			
		}
		
		// Cover the outputs 
		if(options.coverOutputs){
			
			sb.append(System.lineSeparator)
			sb.append("\t" ).append('''/* Cover the Outputs */''')
			sb.append(System.lineSeparator)

			sb.append("\t" ).append('''// Monitored getters''');
			sb.append(CoverOutputs.monitoredGetter(asm));
			sb.append(System.lineSeparator)

			sb.append("\t").append('''// Cover functions''');
			sb.append(System.lineSeparator)
			sb.append(System.lineSeparator)
	
			sb.append(CoverOutputs.coverOutputs(asm))
			sb.append(System.lineSeparator)
		
			sb.append(System.lineSeparator)
			sb.append(System.lineSeparator)
	
			sb.append(CoverOutputs.coverOutputBranches(asm));
		
		}

		// Specific method for ASM work.
		sb.append(System.lineSeparator)
		sb.append('''/* ASM Methods */''');
		sb.append(System.lineSeparator)
		sb.append(System.lineSeparator)
	
		// Controlled public getters (to make assertions)
		sb.append(System.lineSeparator)
		sb.append("\t\t").append('''// Controlled getters''');
		sb.append(System.lineSeparator)
		sb.append(AsmMethods.controlledGetter(asm));
		
		// Monitored public setters (to set the values)
		sb.append(System.lineSeparator)
		sb.append("\t\t").append('''// Monitored setters''');
		sb.append(System.lineSeparator)
		sb.append(AsmMethods.monitoredSetters(asm));
		
		sb.append(System.lineSeparator)
		sb.append('''}''')
		
		return sb.toString();

	}

}
