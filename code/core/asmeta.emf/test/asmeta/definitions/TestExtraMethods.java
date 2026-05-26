package asmeta.definitions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import asmeta.structure.Asm;
import asmeta.structure.StructureFactory;
import asmeta.terms.basicterms.BasictermsFactory;
import asmeta.terms.basicterms.BooleanTerm;

public class TestExtraMethods {

	@Test
	void testBooleanTerm() {
		BooleanTerm bt = BasictermsFactory.eINSTANCE.createBooleanTerm(false);
		assertEquals("false",bt.getSymbol());
	}

	@Test
	void testStructureFactory() {
		Asm asm = StructureFactory.eINSTANCE.createAsm("prova", false);
		assertEquals("prova",asm.getName());
	}

	
}
