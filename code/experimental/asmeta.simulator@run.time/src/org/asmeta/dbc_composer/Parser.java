package org.asmeta.dbc_composer;

import java.util.ArrayList;

import org.asmeta.runtime_commander.CommanderOutput;
import org.asmeta.runtime_commander.CommanderStatus;

public class Parser {
	
	String[] tokens;
	ArrayList<String> tokensString=new ArrayList<>();
	ArrayList<Composition> tokensComposition=new ArrayList<>();
	
	Parser(String formula, boolean interactive) throws Exception {
		//split on space
		tokens=formula.split(" ");
				
		for(String i:tokens)
		{
			if(!(i.equals("|")||i.equals("<|>")||i.equals("||")||i.equals("<||>")||i.equals("(")||i.equals(")")))
			{
				tokensComposition.add(new LeafAsm(i,interactive));
				
			}
			tokensString.add(i);
		}		
		Parse();			
	}
	
	private Composition Parse() throws Exception {
		
		int i=0; //close bracket position
		boolean flag=true; //close bracket flag
		int pos=0; //tokensComposition argument position		
		while(flag)
		{
			//arraylist tokensString check to avoid null pointer
			if(i>tokensString.size()-1)
			{
				//GESTISCI FORMULA NON BEN SCRITTA
				CommanderOutput out = new CommanderOutput(CommanderStatus.FAILURE," Couldn't launch command, invalid syntax (check parentheses)!");
				return null;
			}
			//[execution example inside square brackets]
			//[ ( + asm1 + | + asm2 + | + asm3 ) ]
			String str=this.tokensString.get(i); 
			if(!(str.equals("|")||str.equals("<|>")||str.equals("||")||str.equals("<||>")||str.equals("(")||str.equals(")")))
			{
				pos++; //operand encounter counter
			}
			//[pos=3]
			//i start to simplify when i encounter the 1st closed bracket
			if(this.tokensString.get(i).equals(")"))
			{
				flag=false; //while exit condition
				String op=this.tokensString.get(i-2); //in posizione  "i-2" so esserci l'operatore
				Composition appo;
				//check operation
				if(op.equals("|") || op.equals("||") || op.equals("<|>") || op.equals("<||>"))
				{
					//[ ( + asm1 + | + asm2 + | + asm3 ) ]
					//[i-6        i-4        i-2       i ]
					int c=0;
					//2*c to avoid half of the checks
					while(this.tokensString.get(i-2*c).equals("(")== false)
					{
						c++; //argument counter
					}
					//[c=3]
					Composition[] argomenti = new Composition[c];
					for(int k=0; k<c; k++)
					{
						argomenti[k]=this.tokensComposition.get(pos+k-c);
						//verifica posizione
					}
					//operation selection
					if(op.equals("|"))
					{
						appo= new PipeN(argomenti);
					}
					else if(op.equals("||"))
					{
						appo= new ParN(argomenti);
					}
					else if(op.equals("<|>"))
					{
						appo= new BiPipeHalfDup(argomenti[0],argomenti[1]);
					}
					else
					{
						appo= new BiPipeFullDup(argomenti[0],argomenti[1]);
					}
					System.out.println("");
					System.out.println(tokensComposition);
					//system.out da eliminare
					System.out.println("tokensComposition");
					this.tokensComposition.set(pos-c, appo);
					System.out.println(tokensComposition);
					//system.out da eliminare
					//[j=3]? verifica significato j
					//[asm1,asm2,asm3]
					for(int j=pos+1-c; j<this.tokensComposition.size()+1-c; j++) //+1 o +2
					{
						tokensComposition.set(j, tokensComposition.get(j-1+c));
						//
						System.out.print(j);
						System.out.print(" - ");
						System.out.println(this.tokensComposition.size()+1-c);
						//3 righe da eliminare
						//corretto?
						System.out.println(tokensComposition);
						//system.out da eliminare
					}
					
					for(int j=0; j<c-1; j++)
					{
						tokensComposition.remove(tokensComposition.size()-1);
						System.out.println(tokensComposition);
						//system.out da eliminare
					}
					c=c*2; //c=6
					//[  ( + asm1 + | + asm2 + | + asm3 ) ]
					//[ i-6  i-5   i-4  i-3   i-2  i-1  i ]
					System.out.println("");
					System.out.println(tokensString);
					//system.out da eliminare
					this.tokensString.set(i-c, appo.toString());
					//system.out da eliminare
					System.out.println("tokensString");
					System.out.println(tokensString);
					int p=i+1-c; //posso modificare direttamente i? Sì?
					//[ appo + asm1 + | + asm2 + | + asm3 )  ]
					//[ p-1     p    p+1  p+2   p+3  p+4 p+5 ]
					while(this.tokensString.size()-c > p)
					{
						this.tokensString.set(p, this.tokensString.get(p+c));
						p++;
						System.out.println(tokensString);
						//system.out da eliminare
					}
					while(this.tokensString.size() > p)
					{
						this.tokensString.remove(this.tokensString.size()-1);
						System.out.println(tokensString);
						//system.out da eliminare
					}			
				}
				// else if i found "(" or composition argument
				else
				{
					//GESTISCI FORMULA NON BEN SCRITTA
					CommanderOutput out = new CommanderOutput(CommanderStatus.FAILURE," Couldn't launch command, invalid syntax (check parentheses)!");
					return null;
				}				
				/*
				 * else if(op.equals("<|>") || op.equals("<||>")) {
				 * if(op.equals("<|>")) { appo = new
				 * BiPipeHalfDup(this.tokensComposition.get(pos-2),
				 * this.tokensComposition.get(pos-1)); } else { appo = new
				 * BiPipeFullDup(this.tokensComposition.get(pos-2),
				 * this.tokensComposition.get(pos-1)); } this.tokensComposition.set(pos-2,
				 * appo); for(int j=pos; j<this.tokensComposition.size()-1; j++) {
				 * tokensComposition.set(j, tokensComposition.get(j+1)); } //Rimuovo elemento
				 * coda tokensComposition.remove(tokensComposition.size()-1);
				 * //[ 0 1 2 3 4 5 6 7 8 ] //[ ( + asm3 + <|> + ( + asm1 + <|> + asm2 + ) + ) ]
				 * //[ i-8 i-7 i-6 i-5 i-4 i-3 i-2 i-1 i ]
				 * //[ 0 1 2 3 4 5 6 7 8 ] //[ ( + ( + asm1 + <|> + asm2 + ) + asm3 + <|> + ) ]
				 * //[ i-8 i-7 i-6 i-5 i-4 i-3 i-2 i-1 i ] //[ 0 1 2 3 4 5 6 7 8 9 10 11 ]
				 * //[ ( + asm4 + <|> + ( + asm3 + <|> + ( + asm1 + <|> + asm2 + ) + ) ]
				 * //[ i-11 i-10 i-9 i-8 i-7 i-6 i-5 i-4 i-3 i-2 i-1 i ]
				 * //metti contatore che valuta tokensString
				 * //[ ( + asm1 + <|> + asm2 + ) ] //[ i-4 i-3 i-2 i-1 i ] tokensString.set(i-4,appo.toString()); 
				 * int p=i-3; //[ 0 1 2 3 4 ] //[ appo + asm1 + <|> + asm2 + ) ] //[ i-1 i i+1 i+2 i+3 ]
				 * //[ nell'esempio non entro in while ] while(p<tokensString.size()-4) {
				 * tokensString.set(p, tokensString.get(p+4)); p++; }
				 * while(tokensString.size()>p) { tokensString.remove(tokensString.size()-1); }
				 * }
				 */	
			}
			else
			{
				i++; //scan next tokensString element
			}
			//if there is only one element the simplification is done
			if(tokensString.size()==1)
			{
				return tokensComposition.get(0);
			}	
		}
		return Parse(); //recursive call: partially simplified formula
	}
	
	//Composition type object
	public Composition toComposition() {
		Composition ris=this.tokensComposition.get(0);
		return ris;
	} 
}
