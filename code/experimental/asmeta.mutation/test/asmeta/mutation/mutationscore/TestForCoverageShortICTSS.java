package asmeta.mutation.mutationscore;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class TestForCoverageShortICTSS {

	static String base_dir = "../asmeta.evotest/asmeta.evotest.experiments/data/ase-exp";

	@Test
	public void test1file() throws Exception {
		String avallaTotest = base_dir + "\\results\\run1\\atgttests\\Lift\\testRG_r_Main_TTRG2.avalla";
		MutatedScenarioExecutor executor = new MutatedScenarioExecutor();
		var res = executor.computeMutationScore(avallaTotest);
		System.out.println(res);
	}
	
	@Test
	public void testAseExperiments() throws IOException {
		Path base = Path.of(base_dir);
		assertTrue(Files.exists(base));
		// now walk from here
		Files.walk(base).forEach( d ->{
        	if (Files.isDirectory(d) && d.toFile().toString().endsWith("atgttests")) {
        		System.out.println(d.toFile().toString());
        		try {
					Files.walk(d).forEach(avalla ->{
						// look for avalla tests
						if (avalla.toFile().toString().endsWith(".avalla")) {
							System.out.println(avalla.toFile().toString());
							// the path of the asmeta is wrong
							// for instance:
							// load ./src\main\resources\models\Ascensore.asm
							// must become
							// load ../../../../src ... etc
							correctLoadSpec(avalla);
							
							try {
								MutatedScenarioExecutor executor = new MutatedScenarioExecutor();
								executor.computeMutationScore(avalla.toFile().toString());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}	
        });
	}

	private void correctLoadSpec(Path avalla) {
		try {
			Charset charset = StandardCharsets.UTF_8;
			String content = new String(Files.readAllBytes(avalla), charset);
			content = content.replaceAll("load ./data\\ase-exp\\", "load ../../../../");
			System.err.println(content);
			Files.write(avalla, content.getBytes(charset));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
