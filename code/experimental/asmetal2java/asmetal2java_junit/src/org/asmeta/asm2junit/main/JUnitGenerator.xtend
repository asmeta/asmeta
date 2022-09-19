package org.asmeta.asm2junit.main


import asmeta.definitions.ControlledFunction
import asmeta.definitions.RuleDeclaration
import asmeta.definitions.StaticFunction
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.domains.EnumTd
import asmeta.structure.Asm
import asmeta.transitionrules.basictransitionrules.Rule
import org.junit.Assert
import java.util.List
import java.util.ArrayList
import org.asmeta.asm2junit.main.AvallaToJUnitGenerator
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.MapDomain
import asmeta.definitions.domains.SequenceDomain
import asmeta.definitions.MonitoredFunction
import java.util.Collections
import java.util.Arrays


class JUnitGenerator extends AvallaToJUnitGenerator {
	

	//String initConrolledMonitored

	def compileAndWrite(Asm asm, String writerPath, TranslatorOptions userOptions) {
		Assert.assertTrue(writerPath.endsWith(".java"));
		compileAndWrite(asm, writerPath, "JAVA", userOptions)
	}
	// all the rules that must translate in two versions seq and not seq
	// if null, translate all
	List<Rule> seqCalledRules;
	
	String supp

	override compileAsm(Asm asm) {
		// collect alla the seq rules if required
		if (options.optimizeSeqMacroRule) {
			seqCalledRules = new ArrayList
			//for (r : asm.bodySection.ruleDeclaration)
				//seqCalledRules.addAll(new SeqRuleCollector(false).visit(r))
		}
		//
		val asmName = asm.name
		// TODO fix include list
		//«checkCommand(asm)»
		//«setCommand(asm) » 
		return '''
				// «asmName».java automatically generated from AVALLA2CODE
				
				//Struttura Base
				/* Import che potrebbero servire durante l'esecuzione*/
				
				import static org.junit.Assert.*;
				import org.junit.Test;
				import java.util.ArrayList;
				import java.util.Arrays;
				
				/*AC - PARTE MIA*/
				public class «asmName»_Test {
					
					@Test
					public void Test{
						
						///////////////////////////////
						// PARTE CONTENENTE COSTRUTTORE
						///////////////////////////////
						«asmName» v = new «asmName»();
						//Step -> updateAsm()
						//valutare imple funzione oppure
						assertNotNull(v);
						
						
						///////////////////////////////////
						// PARTE CONTENENTE CHECK - COMMAND
						///////////////////////////////////
						/* -> assertTrue <- */
						
						
						/////////////////////////////////
						// PARTE CONTENENTE SET - COMMAND
						/////////////////////////////////
						
						
						
						
						////////////////////////////////////
						// PARTE CONTENENENTE STEP - COMMAND
						////////////////////////////////////
						//incremento contatore
						
						
					}
				}
				
				
					// applicazione dell'aggiornamento del set
					
					//Metodo per l'aggiornamento dell'asm
					void UpdateASM()
					{
						r_Main();
						fireUpdateSet();
					}
					
					public static void main(String[] args) {
						}
					
				}
				
		'''

	}
	
	//Prima parte dedicata allo studio dei metodi per la creazione della classe astratta che rappresenta la parte
	//signature del programma ASM, nel progetto precedente era la classe destinata alla creazione dell' header
	
	//AC
	def checkCommand(Asm asm){
		System.out.println("CIAO");
	}
	
	def setCommand(Asm asm){
		System.out.println("CIAO");
	}



   //Definisce un dominio di tipo astratto
   
	
	



}
