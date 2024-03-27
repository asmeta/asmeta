package org.asmeta.xt.tests;

import javax.management.RuntimeErrorException;

import org.asmeta.xt.asmetal.Asm;

// extension of the parser helper for the asmeta
public class AsmParseHelper extends org.eclipse.xtext.testing.util.ParseHelper<Asm> {
	
	@Override
	public Asm parse(CharSequence text) throws Exception {
		// either sav eto file or somthing
		throw new RuntimeException("cannot parse without file name");
	}
	
	public Asm parse(CharSequence text, String specName) throws Exception {
		 parse(text, resourceHelper.createResourceSet());
	}
	
}

