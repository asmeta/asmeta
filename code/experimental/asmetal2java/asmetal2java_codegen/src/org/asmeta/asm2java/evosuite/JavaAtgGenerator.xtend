package org.asmeta.asm2java.evosuite

import asmeta.structure.Asm
import java.util.List
import asmeta.transitionrules.basictransitionrules.Rule
import org.junit.Assert
import java.util.ArrayList
import org.asmeta.asm2java.SeqRuleCollector
import org.asmeta.asm2java.main.AsmToJavaGenerator
import org.asmeta.asm2java.main.TranslatorOptions

class JavaAtgGenerator extends AsmToJavaGenerator {

	def compileAndWrite(Asm asm, String writerPath, TranslatorOptions userOptions) {
		Assert.assertTrue(writerPath.endsWith(".java"));
		compileAndWrite(asm, writerPath, "JAVA", userOptions)
	}

	// all the rules that must translate in two versions seq and not seq
	// if null, translate all
	List<Rule> seqCalledRules;

	String [] finalStateConditions;

	def setFinalStateConditions(String [] finalStateConditions){
		this.finalStateConditions = finalStateConditions;
	}

	override compileAsm(Asm asm) {
		// collect alla the seq rules if required
		if (options.optimizeSeqMacroRule) {
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
				System.out.println("<State "+ state +" (controlled)>");

				printControlled();
				
				this.execution.updateASM();
				
				System.out.println("</State "+ state +" (controlled)>");
				
				System.out.println("\n<Current status>");
				printControlled();''');
			
		if(options.coverRules){
			sb.append('''
				// Cover the rules
				coverRules();''');

			sb.append(System.lineSeparator)
			
			}
			
		if(options.coverOutputs){
			sb.append('''
				// Cover the outputs
				coverMonitored();
				coverControlled();''');

			sb.append(System.lineSeparator)
			
			CoverOutputs.ifFinalState(asm, sb, this.finalStateConditions)
			
			}
			
		sb.append("\t\t\t\t\t\t" ).append('''state++;
				}''')
		// End Step Function		
		
		// Cover Outputs
		if(options.coverOutputs){
			
			sb.append("\t" ).append('''/* Cover the Outputs */''')
			sb.append(System.lineSeparator)
			
			CoverOutputs.setIsFinalState(asm, sb, this.finalStateConditions)
			sb.append(System.lineSeparator)

			sb.append("\t" ).append('''// Monitored getters''');
			AsmMethods.monitoredGetter(asm, sb);
			sb.append(System.lineSeparator)

			sb.append("\t").append('''// Cover functions''');
			sb.append(System.lineSeparator)
			sb.append(System.lineSeparator)
			sb.append("\t").append('''
		private void coverMonitored(){''');
	
			CoverOutputs.coverFunctions(asm,sb,true)
			sb.append(System.lineSeparator)
	
			sb.append("\t").append('''
		}
		
			private void coverControlled(){''');
	
			CoverOutputs.coverFunctions(asm,sb,false)
	
			sb.append(System.lineSeparator)
			sb.append("\t").append('''}''');
			sb.append(System.lineSeparator)
			sb.append(System.lineSeparator)
	
			CoverOutputs.coverBranches(asm,sb);
		
		}

		sb.append(System.lineSeparator)
		sb.append('''// ASM Methods''');
		sb.append(System.lineSeparator)
	
		// Print controlled functions - unnecessary for test generation, can be removed
		sb.append("\t\t").append('''// Print controlled''');
		sb.append(System.lineSeparator)
		sb.append(AsmMethods.printControlled(asm))
	
		// Controlled public getters (to make assertions)
		sb.append(System.lineSeparator)
		sb.append("\t\t").append('''// Controlled getters''');
		sb.append(System.lineSeparator)
		AsmMethods.controlledGetter(asm,sb);
		
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
