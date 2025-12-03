package org.asmeta.atgt.generator.newversion;

import org.asmeta.atgt.coverage.AsmetaAsSpec;
import org.asmeta.atgt.generator.base.AsmTestGeneratorBase;

// this is using the new way to computer coverage
public class AsmetaTestGenerator extends AsmTestGeneratorBase<AsmetaAsSpec>{

	protected AsmetaTestGenerator(boolean coverageTp) {
		super(coverageTp);
	}

}
