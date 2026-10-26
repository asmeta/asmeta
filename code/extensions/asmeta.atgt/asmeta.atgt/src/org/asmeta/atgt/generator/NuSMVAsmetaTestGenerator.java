package org.asmeta.atgt.generator;

import org.asmeta.atgt.coverage.AsmetaAsSpec;

import atgt.coverage.AsmTestSuite;
import atgt.specification.ASMSpecification;

// based on NuSMVTestGenerator, it generates tests for an ASM specification based on AsmetaAsSpec - ASMETA specification
public class NuSMVAsmetaTestGenerator extends NuSMVTestGenerator<AsmetaAsSpec> {

	NuSMVAsmetaTestGenerator(String asmfile, boolean coverageTp)
			throws Exception {
		super(asmfile, coverageTp, new AsmetaSpecificationReader());
	}

}
