package org.asmeta.parser.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.asmeta.parser.ASMFileFilter;
import org.asmeta.parser.AsmParserTest;
import org.asmeta.parser.util.AsmetaPrintInfo.AsmInfo;
import org.junit.Test;

public class AsmetaPrintInfoTest {

	private void printStatsDirOfASMs(String dirPath) throws Exception {
		File dir = new File(dirPath);
		assertTrue("example dir " + dir.getAbsolutePath() + " does not exist, current dir: "
				+ new File(".").getAbsolutePath(), dir.isDirectory());
		// read all the specs
		for (File f : dir.listFiles(new ASMFileFilter())) {
			AsmetaPrintInfo pi = new AsmetaPrintInfo(f.getAbsolutePath());
			AsmInfo info = pi.getInfo();
			System.out.println(f.getName());
			System.out.println(info.infoMap);
		}
	}

	@Test
	public void testABZ2016() throws Exception {
		printStatsDirOfASMs(AsmParserTest.FILE_BASE + "//examples/hemodialysisDevice/ABZ2016");
	}

	@Test
	public void testSCP2017() throws Exception {
		printStatsDirOfASMs(AsmParserTest.FILE_BASE + "//examples/hemodialysisDevice/SCP2017/");
	}
	
	@Test
	public void testMVM() throws Exception {
		printStatsDirOfASMs("../../../../../mvm-asmeta\\asm_models\\MVM APPFM");
	}
	

	private static void statsASM(String asmPath) throws Exception {
		AsmetaPrintInfo pi = new AsmetaPrintInfo(asmPath);
		AsmInfo info = pi.getInfo();
		pi.asms.stream().forEach(asm -> System.out.print(asm.getName() + " "));
		System.out.println(info.infoMap);
	}

	/*
	
	@Test
	public void testKDSfestschrift() throws Exception {
		// atgt/code/code_asm/asm_mbtJunit/models/LandingGearSystemWithCylValvesSensorsHealthMon3LandSets.asm
		// atgt/code/code_asm/asm_mbtJunit/models/SensorVotingFuncFlat.asm
		// atgt/code/code_asm/hemodialysis_mbt_abzsi16/SCP2017/HemodialysisRef3.asm
		// atgt/code/code_asm/asm_mbtJunit/asm_mbtJunit/models/certifierRaff5_eurospi2016.asm
		statsASM(AsmParserTest.FILE_BASE +"/examples/landingGearSystem/LandingGearSystemWithCylValvesSensorsHealthMon3LandSets.asm");
		statsASM("specsKDS/SensorVotingFuncFlat.asm");
		statsASM(AsmParserTest.FILE_BASE +"/ezamples/hemodialysisDevice/SCP2017/HemodialysisRef3.asm");
		statsASM("specsKDS/certifierRaff5_eurospi2016.asm");
	}
	
	//private static final String ABZ2020I_CASESTUDY = "D:\\AgHome\\Dropbox\\Documenti\\ricerca\\asm\\";
	private static final String ABZ2020I_CASESTUDY = "D:\\AgDocuments\\Dropbox\\Documenti\\ricerca\\asm\\";
	@Test
	public void testABZCASESTUDY2020() throws Exception {
		assert Files.exists(Paths.get(ABZ2020I_CASESTUDY));
		// quelli nella directory
		//statsASM(ABZ2020I_CASESTUDY + "ABZ2020_casestudy\\Casestudy\\ASM model\\Car System module\\CarSystem001\\");
		//
		statsASM(ABZ2020I_CASESTUDY + "ABZ2020_casestudy\\Casestudy\\ASM model\\Car System\\CarSystem001.asm");
		statsASM(ABZ2020I_CASESTUDY + "ABZ2020_casestudy\\Casestudy\\ASM model\\Car System\\CarSystem002.asm");
		statsASM(ABZ2020I_CASESTUDY + "ABZ2020_casestudy\\Casestudy\\ASM model\\Car System\\CarSystem003.asm");
		statsASM(ABZ2020I_CASESTUDY + "ABZ2020_casestudy\\Casestudy\\ASM model\\Car System\\CarSystem004.asm");
		statsASM(ABZ2020I_CASESTUDY + "ABZ2020_casestudy\\Casestudy\\ASM model\\Car System\\CarSystem005.asm");
		statsASM(ABZ2020I_CASESTUDY + "ABZ2020_casestudy\\Casestudy\\ASM model\\Car System\\CarSystem006.asm");
		statsASM(ABZ2020I_CASESTUDY + "ABZ2020_casestudy\\Casestudy\\ASM model\\Car System\\CarSystem007.asm");
		statsASM(ABZ2020I_CASESTUDY + "ABZ2020_casestudy\\Casestudy\\ASM model\\Car System\\CarSystem008.asm");
		statsASM(ABZ2020I_CASESTUDY + "ABZ2020_casestudy\\Casestudy\\ASM model\\Car System\\CarSystem009.asm");
	}
	
	
	private static final String PHD_CASESTUDY = "C:\\Users\\garganti\\Documents\\Rate4PHD\\ASM\\newPHD\\";
	 

	@Test
	public void testPHD() throws Exception {
		assert Files.exists(Paths.get(PHD_CASESTUDY));
		// quelli nella directory
		statsASM(PHD_CASESTUDY + "phd_master_flat2_v0.asm");	
		statsASM(PHD_CASESTUDY + "phd_master_flat2_v1.asm");	
		statsASM(PHD_CASESTUDY + "phd_master_flat2_v2.asm");	
		statsASM(PHD_CASESTUDY + "phd_master_flat2_v3.asm");	
		statsASM(PHD_CASESTUDY + "phd_master_flat2_v4.asm");	
		statsASM(PHD_CASESTUDY + "phd_master_flat2_v5.asm");	
		statsASM(PHD_CASESTUDY + "phd_master_flat2_v6.asm");	
	}
	*/

}