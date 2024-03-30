package org.asmeta.xt.tests;

import java.nio.file.Path;

import org.asmeta.parser.ASMParser;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.util.ResourceHelper;

public class AsmResourceHelper extends ResourceHelper {
			
	private String specname;
	
	@Override
	protected XtextResourceSet createResourceSet() {
		return super.createResourceSet();
	}

	public void setSpecname(String specname) {
		this.specname = specname;
	}

	
	protected URI computeUnusedUri(ResourceSet resourceSet) {
		URI syntheticUri = URI.createURI(specname + ASMParser.ASM_EXTENSION);
		if (resourceSet.getResource(syntheticUri, false) == null)
			return syntheticUri;
		throw new IllegalStateException();
	}

}