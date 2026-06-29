package org.asmeta.nusmv.main;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
			if (AsmetaSMVOptions.isRunNuSMV()) {
				a.executeNuSMV();
			}
			else {
				// parse the file with nusmv: option -lp to list the properties without executing them
				ProcessBuilder pb = new ProcessBuilder("NuSMV", "-lp", spec.replace(".asm", ".smv"));
				try {
					Process p = pb.start();
					int exitCode = p.waitFor();
					String errors = new String(p.getErrorStream().readAllBytes());
					String output = new String(p.getInputStream().readAllBytes());
					System.out.println(output);
					System.err.println(errors);
					assertEquals(0, exitCode);
				} catch (Exception e) {
					fail("Exception while running NuSMV: " + e.getMessage());
				}
			}
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
