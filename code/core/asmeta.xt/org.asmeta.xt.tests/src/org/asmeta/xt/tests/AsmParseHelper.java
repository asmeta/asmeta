package org.asmeta.xt.tests;

import org.asmeta.xt.asmetal.Asm;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Inject;

// extension of the parser helper for the asmeta
public class AsmParseHelper extends org.eclipse.xtext.testing.util.ParseHelper<Asm> {

	@Inject
	AsmResourceHelper asmresourceHelper;

	@Override
	public Asm parse(CharSequence text) throws Exception {
		// assuming asm name[newline]
		String s = text.toString();
		int from = s.indexOf(' ');
		int to = s.indexOf('\n', from + 1);
		CharSequence name = text.subSequence(from + 1, to-1);
		// no space in the name
		assert name.toString().indexOf(' ') == -1: "index" + from + "->" + to + "name is" + name;
		return parse(text, name.toString());
	}

	public Asm parse(CharSequence text, String specName) throws Exception {
		asmresourceHelper.setSpecname(specName);
		XtextResourceSet res = asmresourceHelper.createResourceSet();
		return super.parse(text, res);
	}

	protected URI computeUnusedUri(ResourceSet resourceSet) {
		return asmresourceHelper.computeUnusedUri(resourceSet);
	}

}
