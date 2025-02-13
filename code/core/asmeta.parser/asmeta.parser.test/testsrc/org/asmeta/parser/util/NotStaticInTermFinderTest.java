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
	public void testTest3declarations() throws Exception {
		File spec = new File(FILE_BASE + "/" +"test/parser/staticVSDerived.asm");
		AsmCollection x = ASMParser.setUpReadAsm(spec);
		EList<FunctionDefinition> defs = x.getMain().getBodySection().getFunctionDefinition();
		assertEquals(3,defs.size());
		// 1
		// simple case no indirection
		// function fd($i in Integer) = m + $i
		List<EObject> list = new ArrayList<>();
		DynamicInTermFinder nf = new DynamicInTermFinder(list);
		nf.visit(defs.get(0).getBody());
		// only m 
		assertEquals(1,list.size());
		// 2		
		// function fs($i in Integer) = 2 * $i
		list = new ArrayList<>();
		nf = new DynamicInTermFinder(list);
		nf.visit(defs.get(1).getBody());
		// only m 
		assertEquals(0,list.size());
		// 3
		// indirection	- still derived since it is defined with a derived
		//function fd2($i in Integer) = fd($i) + 2
		list = new ArrayList<>();
		nf = new DynamicInTermFinder(list);
		nf.visit(defs.get(2).getBody());
		// only m 
		assertEquals(1,list.size());
		
	}

}
