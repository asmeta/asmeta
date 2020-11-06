package org.asmeta.asm2java.main

import asmeta.structure.Asm
import java.util.List
import asmeta.transitionrules.basictransitionrules.Rule
import org.junit.Assert
import java.util.ArrayList
import org.asmeta.asm2java.SeqRuleCollector
import asmeta.definitions.domains.AbstractTd
import asmeta.definitions.ControlledFunction
import asmeta.definitions.domains.ConcreteDomain
import asmeta.definitions.domains.MapDomain
import asmeta.definitions.domains.SequenceDomain
import asmeta.definitions.domains.EnumTd
import asmeta.definitions.MonitoredFunction

class JavaExeGenerator extends AsmToJavaGenerator {
	
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
			for (r : asm.bodySection.ruleDeclaration)
				seqCalledRules.addAll(new SeqRuleCollector(false).visit(r))
		}
		//
		val asmName = asm.name
		
		// TODO fix include list
		return '''
				// «asmName»_Exe.java automatically generated from ASM2CODE
				//Classe per l'esecuzione dei file java generati dalla traduzione di un programma ASM
				
				import java.util.Scanner;
				
				class «asmName»_Exe {
					
					static void printControlled(«asmName» esecuzione) {
						
						«printControlled(asm)»
						
						}
						
					static void askMonitored(«asmName» esecuzione) {
						
						«askMonitored(asm)»
						
						}
					
					
					public static void main(String[] args) {
						
						
						  System.out.println("INFO - file java creto e tradotto dal file originale «asmName».asm");
						  System.out.println("Inizio esecuzione del file «asmName».java\n\n");
						  
						  «asmName» esecuzione = new «asmName»();
						  
						  String continuare = "no";
						  int stato =0;
						  stato++;
						  
						  System.out.println("INITIAL STATE: ");
						     
						  do {
						  
						  	   System.out.println("<State "+ stato +" (controlled)>");
						  	   
						  	   //Aggiornamento valori dell'ASM e inserimento dati monitorati
						  	   
						  	   printControlled(esecuzione);
						  	   askMonitored(esecuzione);
						  	   esecuzione.UpdateASM();
						  	   
						  	   System.out.println("</State "+ stato +" (controlled)>");
						  	   
						  	   System.out.println("\n<Stato attuale>");
						  	   printControlled(esecuzione);
						  	   
						  	   
						       System.out.print("Vuoi continuare? (yes/no)  "); 
						       Scanner input = new Scanner(System.in);
						  	   continuare = input.next();
						  	              
						  	   stato++;
						  	 }
						  	 
						  while(continuare.contentEquals("yes") || continuare.contentEquals("Yes") || continuare.contentEquals("YES") );
						  	     
						  	     
						  System.out.println("FINAL STATE:");
						  
						  //Valori finale delle variabili
						  printControlled(esecuzione);
						  
						  System.out.println("esecuzione terminata");
						  	     
						}
					
				}
				
		'''

	}
	
	
	def printControlled(Asm asm) {
		
		var sb = new StringBuffer;
		
		
		for (dd : asm.headerSection.signature.domain) {
			if (dd instanceof AbstractTd) {
				
				
				    sb.append('''
				    		System.out.print("«dd.name»"+ " = {");
				    		for(int i=0 ; i< esecuzione.«dd.name»_lista.size(); i++)
				    		if(i!= esecuzione.«dd.name»_lista.size()-1)
				    		System.out.print(esecuzione.«dd.name»_lista.get(i) +", ");
				    		else
				    		System.out.print(esecuzione.«dd.name»_lista.get(i));	
				    		System.out.println("}");
				    		''')
				    
				  }
				}
				
		
		
		for (fd : asm.headerSection.signature.function) {		
			
			    //Studio dei casi controlled con il dominio nullo, quindi funzioni che ricadono nella struttura zeroC<Valore>
				if(fd instanceof ControlledFunction)
				{
					 if(fd.domain === null)
					 {
					 	 if(fd.codomain instanceof ConcreteDomain )
			              sb.append('''
				    		System.out.println("«fd.name» = " + esecuzione.«fd.name».get().value);
				    		''')	
				         if(fd.codomain.name.equals("Integer") || fd.codomain.name.equals("Boolean") || fd.codomain.name.equals("String"))
			              sb.append('''
				    		System.out.println("«fd.name» = " + esecuzione.«fd.name».get());
				    		
				    		''')
				         if(fd.codomain instanceof MapDomain)
			              sb.append('''
				    		System.out.println("«fd.name» = " + esecuzione.«fd.name».get());
				    		''')
				         if(fd.codomain instanceof SequenceDomain)
			               sb.append('''
				    		System.out.println("«fd.name» = " + esecuzione.«fd.name».get());
				    		''')
				    	 if(fd.codomain instanceof EnumTd)
				    	    sb.append('''
				    		System.out.println("«fd.name» = " + esecuzione.«fd.name».oldValue.name());
				    		''')
				    }	
				    else{
				    	
				    	
				    	if(fd.domain instanceof EnumTd && fd.codomain instanceof ConcreteDomain )
				    	{
				    		sb.append('''
				    		for(int i=0; i < esecuzione.«fd.domain.name»_lista.size(); i++)
				    				{
				    					System.out.println(" «fd.name» =>  (" + esecuzione.«fd.domain.name»_lista.get(i) +") 
				    					= " + esecuzione.«fd.name».oldValues.get(esecuzione.«fd.domain.name»_lista.get(i)).value );
				    				}
				    		''')
				    	}
				    	
				    	if(fd.domain instanceof EnumTd && fd.codomain instanceof EnumTd )
				    	{
				    		sb.append('''
				    		for(int i=0; i < esecuzione.«fd.domain.name»_lista.size(); i++)
				    				{
				    					System.out.println("«fd.name» =>  (" + esecuzione.«fd.domain.name»_lista.get(i) +") 
				    					= "+ esecuzione.«fd.name».oldValues.get(esecuzione.«fd.domain.name»_lista.get(i)));
				    				}
				    		''')
				    	}
				    	
				    	
				    }
				}
		
		 }
		 
		  return sb.toString
		}
		
	def askMonitored(Asm asm){
		
		var sb = new StringBuffer;
		
		for (fd : asm.headerSection.signature.function) {	
			
			if(fd instanceof MonitoredFunction)
				{
					//Solo se il dominio è nullo, quindi funzioni che ricadono nella struttura zero<Valore> 
					if(fd.domain === null)
					{
					   //Caso relativo alle variabili booleane non concrete
					   if(fd.codomain.name.equals("Boolean") && !(fd.codomain instanceof ConcreteDomain))
					   {
					   	sb.append('''
				    		System.out.print("Inserire un valore booleano per «fd.name» (true/false):  ");
				    		Scanner «fd.name»input = new Scanner(System.in);
				    		
				    		for(;;) {
				    			         Boolean y;
				    		             String «fd.name»Controllo = «fd.name»input.nextLine();
				    		             if («fd.name»Controllo.isEmpty()) break;
				    		             try{
				    		                 y = Boolean.parseBoolean(«fd.name»Controllo);
				    		             }catch (Exception e) {
				    		                 System.out.println("hai inserito un valore sbagliato, riprova");
				    		                 continue;
				    		             }
				    		             // setti la variabile
				    		             esecuzione.«fd.name».set(y);
				    		             break;
				    		         }
				    		
				    		''')
					   }
					   
					   if(fd.codomain.name.equals("Integer") && !(fd.codomain instanceof ConcreteDomain))
					   {
					   	sb.append('''
				    		System.out.print("Inserire un valore Intero per «fd.name» (Integer):  ");
				    		Scanner «fd.name»input = new Scanner(System.in);
				    		
				    		for(;;) {
				    			         int x;
				    		             String «fd.name»Controllo = «fd.name»input.nextLine();
				    		             if («fd.name»Controllo.isEmpty()) break;
				    		             try{
				    		                 x = Integer.parseInt(«fd.name»Controllo);
				    		             }catch (Exception e) {
				    		                 System.out.println("hai inserito un valore sbagliato, riprova");
				    		                 continue;
				    		             }
				    		             
				    		             esecuzione.«fd.name».set(x);
				    		             break;
				    		         }
				    		
				    		
				    		
				    		''')
					   }
					   
					   if(fd.codomain instanceof EnumTd)
					 {
					 	sb.append('''
				    		System.out.print("Inserire un numero per indicare l'enumerativo per «fd.name» "+ 
				    		esecuzione.«fd.codomain.name»_lista.toString() +":  ");
				    		Scanner «fd.name»input = new Scanner(System.in);
				    		
				    		for(;;) {
				    			         int x;
				    		             String «fd.name»Controllo = «fd.name»input.nextLine();
				    		             if («fd.name»Controllo.isEmpty()) break;
				    		             try{
				    		                 x = Integer.parseInt(«fd.name»Controllo);
				    		             }catch (Exception e) {
				    		                 System.out.println("hai inserito un valore sbagliato, riprova");
				    		                 continue;
				    		             }
				    		             
				    		             esecuzione.«fd.name».set(esecuzione.«fd.codomain.name»_lista.get(x-1));
				    		             break;
				    		         }				    		
				    		''')
					 }
					 
					 if(fd.codomain instanceof ConcreteDomain)
					   {
					   	sb.append('''
				    		System.out.print("Inserire un valore Intero per «fd.name» (Integer):  ");
				    		Scanner «fd.name»input = new Scanner(System.in);
				    		
				    		for(;;) {
				    			         int x;
				    		             String «fd.name»Controllo = «fd.name»input.nextLine();
				    		             if («fd.name»Controllo.isEmpty()) break;
				    		             try{
				    		                 x = Integer.parseInt(«fd.name»Controllo);
				    		             }catch (Exception e) {
				    		                 System.out.println("hai inserito un valore sbagliato, riprova");
				    		                 continue;
				    		             }
				    		             
				    		             esecuzione.«fd.name»_supporto.value = x;
				    		             esecuzione.«fd.name».set(esecuzione.«fd.name»_supporto);
				    		             break;
				    		         }				    		
				    		
				    		
				    		
				    		''')
					   }
					   
					  if(fd.codomain instanceof AbstractTd)
					   {
					   	sb.append('''
				    		
				    		''')
					   }
						
					}
					
					else{
						
						if(fd.domain instanceof ConcreteDomain && fd.codomain.name.equals("Boolean"))
						{
							sb.append('''
				    		for(int i=0; i< esecuzione.«fd.domain.name»_elems.size() ; i++)
				    		{
				    		esecuzione.«fd.domain.name»_elem.value = esecuzione.«fd.domain.name»_elems.get(i);
				    		System.out.print("Inserire un valore booleano per «fd.name», chiave "+ 
				    		esecuzione.«fd.domain.name»_elem.value +" (true/false):  ");
				    		Scanner «fd.name»input = new Scanner(System.in);
				    		
				    		for(;;) {
				    			         Boolean y;
				    		             String «fd.name»Controllo = «fd.name»input.nextLine();
				    		             if («fd.name»Controllo.isEmpty()) break;
				    		             try{
				    		                 y = Boolean.parseBoolean(«fd.name»Controllo);
				    		             }catch (Exception e) {
				    		                 System.out.println("hai inserito un valore sbagliato, riprova");
				    		                 continue;
				    		             }
				    		             // setti la variabile
				    		             esecuzione.«fd.name».set(esecuzione.«fd.domain.name»_elem,y);
				    		             break;
				    		         }				    		
				    		
				    		
				    		}
				    		''')
						}
						
						if(fd.domain instanceof EnumTd && fd.codomain.name.equals("Boolean"))
						{
							
							sb.append('''
				    		for(int i=0; i < esecuzione.«fd.domain.name»_lista.size(); i++)
				    		{
				    		  System.out.print("Inserire un valore booleano per il dato enumerativo " + 
				    		  esecuzione.«fd.domain.name»_lista.get(i) +" della lista «fd.name» (true/false):  ");
				    		  Scanner «fd.name»input = new Scanner(System.in);
				    		 for(;;) {
				    			         Boolean y;
				    		             String «fd.name»Controllo = «fd.name»input.nextLine();
				    		             if («fd.name»Controllo.isEmpty()) break;
				    		             try{
				    		                 y = Boolean.parseBoolean(«fd.name»Controllo);
				    		             }catch (Exception e) {
				    		                 System.out.println("hai inserito un valore sbagliato, riprova");
				    		                 continue;
				    		             }
				    		             // setti la variabile
				    		              esecuzione.«fd.name».set(esecuzione.«fd.domain.name»_lista.get(i), y);
				    		             break;
				    		         }				    		
				    		}				    		  
				    		  
				    		''')
						}
						
						
					}
						
				}
			
			}
				
			  return sb.toString
			}
			
		
}