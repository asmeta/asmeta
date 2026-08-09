package asm_mbtJunit;

import org.asmeta.atgt.coverage.AsmetaAsSpec;
import org.asmeta.atgt.coverage.AsmetaCoverageBuilder;

import atgt.coverage.AsmCoverage;
import atgt.specification.ASMSpecification;
import tgtlib.coverage.CoverageBuilder;

//TODO : find the original class, this has been created only to avoid compilation errors - LUG26
public class JUnitTestGenerator {

	
	static public class MBTCoverage implements  CoverageBuilder<ASMSpecification, AsmCoverage>{

		@Override
		public String getCoveragePrefix() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AsmCoverage getTPTree(ASMSpecification spec) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}
