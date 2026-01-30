package org.asmeta.nusmv.main;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.asmeta.nusmv.util.AsmetaSMVOptions;

public abstract class AsmetaSMVtestTranslateBase {

	protected boolean testOneSpec(String spec) {
		AsmetaSMVOptions options = new AsmetaSMVOptions();
		AsmetaSMVOptions.keepNuSMVfile = true;
		//AsmetaSMVOptions.FLATTEN = false;
		return testOneSpec(spec, options);
	}

	/**
	 * @param args
	 * @param options
	 * @return
	 */
	protected boolean testOneSpec(String spec, AsmetaSMVOptions options) {
		try {
			File file = new File(spec);
			assertTrue(file.exists());
			AsmetaSMV a = new AsmetaSMV(file,options);
			a.translation();
			a.createNuSMVfile();
			// the file exists
			if (!Files.exists(Paths.get(a.smvFileName)))
				return false;
			//System.out.println(Paths.get(a.smvFileName));
			// TODO parse the file with nusmv
			if (AsmetaSMVOptions.isRunNuSMV()) a.executeNuSMV();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	protected boolean testOneSpec(File fspec) {
		String spec = fspec.getAbsolutePath();
		return testOneSpec(spec);
	}

}
