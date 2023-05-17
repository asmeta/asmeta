package org.asmeta.runtime_commander;

import java.util.ArrayList;

import org.asmeta.runtime_composer.BiPipeFullDup;
import org.asmeta.runtime_composer.BiPipeHalfDup;
import org.asmeta.runtime_composer.Composition;
import org.asmeta.runtime_composer.LeafAsm;
import org.asmeta.runtime_composer.ParN;
import org.asmeta.runtime_composer.PipeN;

public class Parser {
	
	private String[] tokens;
	private ArrayList<String> tokensString=new ArrayList<>();
	private ArrayList<Composition> tokensComposition=new ArrayList<>();
	
	public Parser(String formula) throws Exception {
			
		//split on space
		tokens=formula.split(" ");
				
		for(String i:tokens)
		{
			if(!(i.equals("|")||i.equals("<|>")||i.equals("||")||i.equals("<||>")||i.equals("(")||i.equals(")")))
			{
				tokensComposition.add(new LeafAsm(i));
			}
			tokensString.add(i);
		}		
		parse();			
	}	
	
	private Composition parse() throws Exception {			
		//in this case the formula is already parsed and ready to eval()
		if(tokensString.size()<=3)//( asm ) -> 3 elements array
		{
			if(tokensComposition.get(0)==null)
			{
				System.out.println("composition è null");
			}
			return tokensComposition.get(0);
		}
		
		int i=0; //close bracket position
		int pos=0; //tokensComposition argument position	
		boolean flag=true; //close bracket flag
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
				Composition temp;
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
						temp= new PipeN(argomenti);
					}
					else if(op.equals("||"))
					{
						temp= new ParN(argomenti);
					}
					else if(op.equals("<|>"))
					{
						temp= new BiPipeHalfDup(argomenti[0],argomenti[1]);
					}
					else
					{
						temp= new BiPipeFullDup(argomenti[0],argomenti[1]);//<||>
					}
					
					this.tokensComposition.set(pos-c, temp);
					//[j=3]
					//[asm1,asm2,asm3]
					for(int j=pos+1-c; j<this.tokensComposition.size()+1-c; j++) //+1 o +2
					{
						tokensComposition.set(j, tokensComposition.get(j-1+c));
					}
					for(int j=0; j<c-1; j++)
					{
						tokensComposition.remove(tokensComposition.size()-1);
					}
					c=c*2; //c=6
					//[  ( + asm1 + | + asm2 + | + asm3 ) ]
					//[ i-6  i-5   i-4  i-3   i-2  i-1  i ]
					this.tokensString.set(i-c, temp.toString());
					int p=i+1-c; //posso modificare direttamente i? Sì?
					//[ appo + asm1 + | + asm2 + | + asm3 )  ]
					//[ p-1     p    p+1  p+2   p+3  p+4 p+5 ]
					while(this.tokensString.size()-c > p)
					{
						this.tokensString.set(p, this.tokensString.get(p+c));
						p++;
					}
					while(this.tokensString.size() > p)
					{
						this.tokensString.remove(this.tokensString.size()-1);
					}
				}
				// else if i found "(" or composition argument
				else
				{
					//GESTISCI FORMULA NON BEN SCRITTA
					CommanderOutput out = new CommanderOutput(CommanderStatus.FAILURE," Couldn't launch command, invalid syntax (check parentheses)!");
					return null;
				}				
			}
			else
			{
				i++; //scan next tokensString element
			}
			//if there is only one element the simplification is done
			/*
			 * if(tokensString.size()==1) { if(tokensComposition.get(0)==null) {
			 * System.out.println("composition è null"); } return tokensComposition.get(0);
			 * }
			 */	
		}
		return parse(); //recursive call: partially simplified formula
	}
	
	//Composition type object
	public Composition toComposition() {
		Composition ris=this.tokensComposition.get(0);
		return ris;
	} 
}

