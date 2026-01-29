package org.asmeta.atgt.generator.combinatorial;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageBuilder;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;
import tgtlib.specification.ParseException;

public class AsmDataExtractorTest {

	private static final String CAR_SYSTEM_MODULE = "../../../experimental/asmetal2cpp/asmetal2cpp.codegen/examples/ABZ2020_FOR_TESTS/CarSystemModule/";

	@Test
	public void testGetAsmCombCovBuilder() throws ParseException {
		String asmPath = "examples/asmenum.asm";
		AsmCoverageBuilder covBuilder = AsmAllDataExtractor.getAsmCombCovBuilder();
		ASMSpecification spec = new AsmetaLLoader().read(new File(asmPath));
		AsmCoverage tp = covBuilder.getTPTree(spec);
		System.out.println(tp.getNumberofTPs());
	}

	@Test
	public void testCarSystem000CommonDomains() throws ParseException {
		assertTrue(Files.exists(Path.of("../../../experimental")));
		assertTrue(Files.exists(Path.of("../../../experimental/asmetal2cpp/asmetal2cpp.codegen/")));
		String asmPath = CAR_SYSTEM_MODULE + "CarSystem000/CarSystem000CommonDomains.asm";
		AsmCoverageBuilder covBuilder = AsmAllDataExtractor.getAsmCombCovBuilder();
		ASMSpecification spec = new AsmetaLLoader().read(new File(asmPath));
		AsmCoverage tp = covBuilder.getTPTree(spec);
		System.out.println(tp.getNumberofTPs());
	}

	@Test
	public void testCarSystem000CommonFunctions() throws ParseException {
		String asmPath = CAR_SYSTEM_MODULE + "CarSystem000/CarSystem000CommonFunctions.asm";
		AsmCoverageBuilder covBuilder = AsmAllDataExtractor.getAsmCombCovBuilder();
		ASMSpecification spec = new AsmetaLLoader().read(new File(asmPath));
		AsmCoverage tp = covBuilder.getTPTree(spec);
		System.out.println(tp.getNumberofTPs());
	}

	@Test
	public void testCarSystem001Functions() throws ParseException {
		String asmPath = CAR_SYSTEM_MODULE + "CarSystem001/CarSystem001Functions.asm";
		AsmCoverageBuilder covBuilder = AsmAllDataExtractor.getAsmCombCovBuilder();
		ASMSpecification spec = new AsmetaLLoader().read(new File(asmPath));
		AsmCoverage tp = covBuilder.getTPTree(spec);
		System.out.println(tp.getNumberofTPs());
	}

	@Test
	@Category(org.asmeta.annotations.TestToMavenSkip.class)
	public void testCarSystem001Blink() throws ParseException {
		/*
		 * Non Funziona. Non vede una variabile importata dal modulo precedente, ma se
		 * la dichiaro in questo modulo dice che � stata dichiarata due volte.
		 */
		String asmPath = CAR_SYSTEM_MODULE + "CarSystem001/CarSystem001Blink.asm";
		AsmCoverageBuilder covBuilder = AsmAllDataExtractor.getAsmCombCovBuilder();
		ASMSpecification spec = new AsmetaLLoader().read(new File(asmPath));
		AsmCoverage tp = covBuilder.getTPTree(spec);
		System.out.println(tp.getNumberofTPs());
	}

	@Test
	public void testCarSystem005Domains() throws ParseException {
		String asmPath = CAR_SYSTEM_MODULE + "CarSystem005/CarSystem005Domains.asm";
		AsmCoverageBuilder covBuilder = AsmAllDataExtractor.getAsmCombCovBuilder();
		ASMSpecification spec = new AsmetaLLoader().read(new File(asmPath));
		AsmCoverage tp = covBuilder.getTPTree(spec);
		System.out.println(tp.getNumberofTPs());
	}
}
