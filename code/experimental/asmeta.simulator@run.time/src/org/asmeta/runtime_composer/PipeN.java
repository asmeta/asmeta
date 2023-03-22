package org.asmeta.runtime_composer;

import org.asmeta.simulator.UpdateSet;

//new code |
public class PipeN extends NComposition {
	//we don't know in advance the number of arguments
	//so we use varargs
	public PipeN(Composition...asm) throws Exception{
		super(asm);
	}
	
	@Override //probabilmente giusto
	public
	UpdateSet eval() {
		UpdateSet up =c.get(0).eval();
		//for starts from 2nd pipe element
		for(int i=1; i<c.size(); i++)
		{
			c.get(i).copyMonitored(up);
			up=c.get(i).eval();	
		}
		c.get(c.size()-1).copyMonitored(up);//così update corretto
		//System.out.println("");
		return up;
	}
	
	@Override
	public String toString() {
		String string=c.get(0).toString();
		//for starts from 2nd pipe element
		for(int i=1; i<c.size(); i++)
		{
			string=string+"|"+c.get(i).toString();
		}
		return string;
	}
	
}