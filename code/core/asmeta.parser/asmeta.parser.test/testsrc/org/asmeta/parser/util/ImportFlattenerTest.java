package org.asmeta.parser.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Path;

import org.asmeta.parser.ASMParser;
import org.asmeta.parser.AsmParserTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import asmeta.AsmCollection;
import asmeta.structure.Asm;

class ImportFlattenerTest {

	@Test void visitAsm2() throws Exception {
		AsmPrinter spr = new AsmPrinter(new PrintWriter(System.out));
		String asmpath = AsmParserTest.FILE_BASE + "/examples/ABZ2020/CarSystemModule/CarSystem001/";
		File asmetafile = new File(asmpath + "CarSystem001main.asm");
		assertTrue(asmetafile.exists());
		Asm main = ASMParser.setUpReadAsm(asmetafile).getMain();
		ImportFlattener ifl = new ImportFlattener(main, asmpath);
		ifl.visit();
		spr.visit(main);
		spr.close();
	}

	@Test
	void flattenCarSystem(@TempDir Path folder) throws Exception {
		File input = new File(AsmParserTest.FILE_BASE
				+ "/examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001main.asm");
		AsmCollection model = ASMParser.setUpReadAsm(input);
		int initializations = model.getMain().getDefaultInitialState().getFunctionInitialization().size();
		Asm flattened = ImportFlattener.flatten(model, input.getParent());

		Asm original = model.getMain();
		assertThrows(RuntimeException.class, () -> Defs.searchDomain(original, "LightPercentage"));
		assertThrows(RuntimeException.class, () -> Defs.searchFunction(original, "blinkLeft"));
		assertThrows(RuntimeException.class, () -> Defs.searchMacro(original, "r_HazardWarningLight"));
		assertThrows(RuntimeException.class, () -> Defs.searchMacro(original, "r_BlinkLeft"));
		
		assertNotNull(Defs.searchDomain(flattened, "LightPercentage"));
		assertNotNull(Defs.searchFunction(flattened, "blinkLeft"));
		assertNotNull(Defs.searchMacro(flattened, "r_HazardWarningLight"));
		assertNotNull(Defs.searchMacro(flattened, "r_BlinkLeft"));
		
		assertEquals(initializations, flattened.getDefaultInitialState().getFunctionInitialization().size());
	}
}
