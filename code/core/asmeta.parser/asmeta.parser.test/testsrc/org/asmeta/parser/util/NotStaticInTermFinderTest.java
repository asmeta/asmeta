package org.asmeta.parser.util;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.AsmParserTest;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

import asmeta.AsmCollection;
import asmeta.structure.FunctionDefinition;

public class NotStaticInTermFinderTest extends AsmParserTest{

	@Test
	public void testExample1() throws Exception {
		File spec = new File(FILE_BASE + "/" +"test/errors/staticVSDerived.asm");
		AsmCollection x = ASMParser.setUpReadAsm(spec);
		EList<FunctionDefinition> defs = x.getMain().getBodySection().getFunctionDefinition();
		List<EObject> list = new ArrayList<>();
		DynamicInTermFinder nf = new DynamicInTermFinder(list);
		for(FunctionDefinition def: defs) {
			nf.visit(def);
		}
		System.out.println(list);
	}

}
