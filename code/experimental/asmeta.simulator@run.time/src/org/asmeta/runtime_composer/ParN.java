package org.asmeta.runtime_composer;

import org.asmeta.simulator.UpdateSet;

//nuovo codice
public class ParN extends NComposition{
	public ParN(Composition...asm) throws Exception{
		super(asm);
	}

	@Override
	public
	UpdateSet eval() {
		UpdateSet up=c.get(0).eval();
		for(int i = 1; i<c.size();i++)
		{
			UpdateSet tempUp=c.get(i).eval();
			up.union(tempUp);
		}
		c.get(c.size()-1).copyMonitored(up);
		//System.out.println("");
		//System.out.println(up.toString());
		return up;
	}
	
	@Override
	public String toString() {
		String stringa=c.get(0).toString();
		for(int i=1; i<c.size(); i++)
		{
			stringa=stringa+"||"+c.get(i);
		}
		return stringa;
	}
}
