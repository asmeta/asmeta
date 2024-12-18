package org.asmeta.xt.tests;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

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

	// with the name explitecly passed (if the first is unbale to fine the right name
	public Asm parse(CharSequence text, String specName) throws Exception {
		asmresourceHelper.setSpecname(specName);
		XtextResourceSet res = asmresourceHelper.createResourceSet();
		return super.parse(text, res);
	}
	// in case a file is passed to it
	public Asm parse(Path fileToRead) throws Exception{
		// transform the path to test
//		val InputStream fis = Files.newInputStream(fileToRead)
//		val charset = Charset.forName("8859_1")
//		val BufferedReader br = new BufferedReader(new InputStreamReader(fis, charset))
//		val String spec = br.lines().collect(Collectors.joining(System.lineSeparator()));
//		fis.close
//		br.close
		InputStream fis = Files.newInputStream(fileToRead);
		URI uriToUse = URI.createFileURI(fileToRead.toString());
		return super.parse(fis, uriToUse , null , asmresourceHelper.createResourceSet());
	}
	

	protected URI computeUnusedUri(ResourceSet resourceSet) {
		return asmresourceHelper.computeUnusedUri(resourceSet);
	}

}
