package org.asmeta.parser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.asmeta.parser.util.AsmetaTermPrinter;
import org.junit.Test;

import asmeta.AsmCollection;
import asmeta.definitions.domains.Domain;
import asmeta.structure.Asm;
import asmeta.structure.DomainDefinition;
import asmeta.structure.Signature;
import asmeta.terms.basicterms.SetTerm;


public class GetDomainTest {
	
	@Test
	public void getDomainTest(){
		File f = new File("../../../../asm_examples/test/parser/GetDomainTest.asm");
		AsmCollection asms = null;
		try {
			asms = ASMParser.setUpReadAsm(f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Signature signature = asms.getMain().getHeaderSection().getSignature();
		List<Domain> domains = signature.getDomain();
		//System.out.println(domains);
		//ci dovrebbe essere un solo dominio
		assertEquals(1, domains.size());
	}

	@Test
	public void getConcrDomDefTest(){
		File f = new File("../../../../asm_examples/test/parser/concrDomDef.asm");
		Asm asm = null;
		try {
			asm = ASMParser.setUpReadAsm(f).getMain();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<DomainDefinition> domsDef = asm.getBodySection().getDomainDefinition();
		SetTerm domain0 = (SetTerm)domsDef.get(0).getBody();
		SetTerm domain1 = (SetTerm)domsDef.get(1).getBody();
		SetTerm domain2 = (SetTerm)domsDef.get(2).getBody();
		SetTerm domain3 = (SetTerm)domsDef.get(3).getBody();
		SetTerm domain4 = (SetTerm)domsDef.get(4).getBody();
		SetTerm domain5 = (SetTerm)domsDef.get(5).getBody();
		SetTerm domain6 = (SetTerm)domsDef.get(6).getBody();
		SetTerm domain7 = (SetTerm)domsDef.get(7).getBody();
		SetTerm domain8 = (SetTerm)domsDef.get(8).getBody();
		AsmetaTermPrinter tp = AsmetaTermPrinter.getAsmetaTermPrinter(false);
		String str0 = tp.visit(domain0);
		String str1 = tp.visit(domain1);
		String str2 = tp.visit(domain2);
		String str3 = tp.visit(domain3);
		String str4 = tp.visit(domain4);
		String str5 = tp.visit(domain5);
		String str6 = tp.visit(domain6);
		String str7 = tp.visit(domain7);
		String str8 = tp.visit(domain8);
		//System.out.println(str0 + "\n" + str1 + "\n" + str2 + "\n" + str3 +
		//	"\n" + str4 + "\n" + str5 + "\n" + str6 + "\n" + str7 + "\n" + str8);
		
		//domain ConcrDomA = {-2, -1, 0, 1, 2}
		assertEquals("{minus(2),minus(1),0,1,2}", str0);
		//domain ConcrDomB = {-2 .. 2}
		assertEquals("{minus(2),-1,0,1,2}", str1);
		//domain ConcrDomC = {-6 .. 6, 2}
		assertEquals("{minus(6),-4,-2,0,2,4,6}", str2);

		//domain ConcrDomD = {0n, 1n, 2n}
		assertEquals("{0n,1n,2n}", str3);
		//domain ConcrDomE = {0n .. 2n}
		assertEquals("{0n,1n,2n}", str4);
		//domain ConcrDomF = {0n .. 6n, 2n}
		assertEquals("{0n,2n,4n,6n}", str5);

		//domain ConcrDomG = {-2.3, -1.4, 0.0, 1.3, 2.7}
		assertEquals("{minus(2.3),minus(1.4),0.0,1.3,2.7}", str6);
		//domain ConcrDomH = {-2.0 .. 2.0}
		assertEquals("{minus(2.0),-1.0,0.0,1.0,2.0}", str7);
		//domain ConcrDomI = {-6.0 .. 6.0, 2.5}
		assertEquals("{minus(6.0),-3.5,-1.0,1.5,4.0}", str8);
	}

}
