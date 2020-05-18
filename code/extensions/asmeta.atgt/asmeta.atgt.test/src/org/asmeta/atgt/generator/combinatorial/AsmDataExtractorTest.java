package org.asmeta.atgt.generator.combinatorial;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

import atgt.combinatorial.AsmCombCovBuilder;
import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageBuilder;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;

public class AsmDataExtractorTest {

	@Test
	public void testGetAsmCombCovBuilder() {
		String asmPath = "examples/asmenum.asm";
		AsmCoverageBuilder covBuilder = AsmDataExtractor.getAsmCombCovBuilder();
		ASMSpecification spec = new AsmetaLLoader().read(new File(asmPath));
		AsmCoverage tp = covBuilder.getTPTree(spec);
		System.out.println(tp.getNumberofTPs());
	}
	
	@Test
	public void testCarSystem000CommonDomains(){
		String asmPath = "../../asmetal2cpp/asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem000/CarSystem000CommonDomains.asm" ;
		AsmCoverageBuilder covBuilder = AsmDataExtractor.getAsmCombCovBuilder();
		ASMSpecification spec = new AsmetaLLoader().read(new File(asmPath));
		AsmCoverage tp = covBuilder.getTPTree(spec);
		System.out.println(tp.getNumberofTPs());
	}
	
	@Test
	public void testCarSystem000CommonFunctions(){
		String asmPath = "../../asmetal2cpp/asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem000/CarSystem000CommonFunctions.asm" ;
		AsmCoverageBuilder covBuilder = AsmDataExtractor.getAsmCombCovBuilder();
		ASMSpecification spec = new AsmetaLLoader().read(new File(asmPath));
		AsmCoverage tp = covBuilder.getTPTree(spec);
		System.out.println(tp.getNumberofTPs());
	}
	
	@Test
	public void testCarSystem001Functions() {
		String asmPath = "../../asmetal2cpp/asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001Functions.asm" ;
		AsmCoverageBuilder covBuilder = AsmDataExtractor.getAsmCombCovBuilder();
		ASMSpecification spec = new AsmetaLLoader().read(new File(asmPath));
		AsmCoverage tp = covBuilder.getTPTree(spec);
		System.out.println(tp.getNumberofTPs());
	}
	
	
	@Test
	public void testCarSystem001Blink() {
		/*
		 * Non Funziona. Non vede una variabile importata dal modulo precedente, ma se la dichiaro in questo modulo dice che � stata dichiarata due volte.
		 */
		String asmPath = "../../asmetal2cpp/asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem001/CarSystem001Blink.asm" ;
		AsmCoverageBuilder covBuilder = AsmDataExtractor.getAsmCombCovBuilder();
		ASMSpecification spec = new AsmetaLLoader().read(new File(asmPath));
		AsmCoverage tp = covBuilder.getTPTree(spec);
		System.out.println(tp.getNumberofTPs());
	}
	
	@Test
	public void testCarSystem005Domains() {
		String asmPath = "../../asmetal2cpp/asmetal2cpp_codegen/examples/ABZ2020/CarSystemModule/CarSystem005/CarSystem005Domains.asm" ;
		AsmCoverageBuilder covBuilder = AsmDataExtractor.getAsmCombCovBuilder();
		ASMSpecification spec = new AsmetaLLoader().read(new File(asmPath));
		AsmCoverage tp = covBuilder.getTPTree(spec);
		System.out.println(tp.getNumberofTPs());
	}
}