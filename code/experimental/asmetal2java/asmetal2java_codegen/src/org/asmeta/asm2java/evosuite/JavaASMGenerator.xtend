package org.asmeta.asm2java.evosuite

import asmeta.structure.Asm
import java.util.List
import asmeta.transitionrules.basictransitionrules.Rule
import org.junit.Assert
import java.util.ArrayList
import org.asmeta.asm2java.SeqRuleCollector
import org.asmeta.asm2java.main.AsmToJavaGenerator
import org.asmeta.asm2java.main.TranslatorOptions

class JavaASMGenerator extends AsmToJavaGenerator {

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
		
		// «asmName»_ASM.java automatically generated from ASM2CODE
		
		import java.util.Scanner;
		
		/**
		* This class allows you to simulate the behavior of an Abstract State Machine (ASM)
		* without asking the user for input values ​​of the monitored functions.
		*
		* <p>
		* It has been optimized to be used with evosuite in order to automatically generate test scenarios.
		* </p>
		*/
		class «asmName»_ASM {
		
			private final «asmName» esecuzione;
			private int stato;
		
		   /**
		    * Constructor of the {@code «asmName»_ASM} class. Creates a private instance of the asm
		    * {@link «asmName»} and sets the initial state of the state machine to 1.
		    */
			public «asmName»_ASM(){
				this.esecuzione = new «asmName»();
				this.stato = 0;
			}
		
			/** The step function is the only public method of the ASM,
			* it receives as parameters the values ​​of the monitored functions to be updated
			* and allows to perform a step of the asm by incrementing the state.
			*/
			public void step(''');

 			AsmMethods.setMonitoredArgsFunc(asm,sb);

			sb.append('''){
				System.out.println("<State "+ stato +" (controlled)>");

				printControlled();''');
			sb.append(System.lineSeparator())
				
			AsmMethods.setMonitoredArgsMet(asm,sb);
			
			sb.append('''
		this.esecuzione.updateASM();
		
		System.out.println("</State "+ stato +" (controlled)>");
		
		System.out.println("\n<Stato attuale>");
		printControlled();
		
		/* monitored */
		coverMonitored();
		/* controlled */
		coverControlled();''');

			sb.append(System.lineSeparator)
			
			CoverOutputs.ifFinalState(asm, sb, this.finalStateConditions)
			
			sb.append("\t\t\t\t\t\t" ).append('''stato++;
				}''')
			
			CoverOutputs.setIsFinalState(asm, sb, this.finalStateConditions)
			
			sb.append(System.lineSeparator)

			sb.append("\t" ).append('''// Monitored getters''');

			AsmMethods.monitoredGetter(asm, sb);

			sb.append(System.lineSeparator)
			sb.append("\t\t").append('''// Controlled getters''');
			sb.append(System.lineSeparator)

			AsmMethods.controlledGetter(asm,sb);

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

			sb.append('''
		
		// ASM Methods
		
		private void printControlled() {
		
			«AsmMethods.printControlled(asm)»
		
		}
		
		private void setMonitored( ''');

			AsmMethods.setMonitoredArgsFunc(asm,sb);

			sb.append(''') {

					«AsmMethods.setMonitored(asm)»
				}
			}''');

		return sb.toString();

	}

}
