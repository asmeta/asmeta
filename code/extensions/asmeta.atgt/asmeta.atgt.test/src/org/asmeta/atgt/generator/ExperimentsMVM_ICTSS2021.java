package org.asmeta.atgt.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.atgt.generator.AsmTestGenerator;
import org.asmeta.atgt.generator.ConverterCounterExample;
import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.FormatsEnum;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.atgt.generator.SaveResults;
import org.asmeta.atgt.generator.TestGenerationWithNuSMV;
import org.asmeta.atgt.generator.AsmTestGenerator.MBTCoverage;
import org.asmeta.atgt.testoptimizer.UnchangedRemover;
import org.asmeta.atgt.testoptimizer.UnecessaryChangesRemover;
import org.asmeta.nusmv.AsmetaSMV;
import org.asmeta.nusmv.AsmetaSMVOptions;
import org.asmeta.parser.ASMParser;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageBuilder;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.tpstatus.TestConditionState;
import atgt.generator.AsmMonitoredDataExtractor;
import atgt.parser.asmeta.AsmetaLLoader;
import atgt.specification.ASMSpecification;
import atgt.testseqexport.toAvalla;

/**
 * tests for the asmeta generator
 *
 */
public class ExperimentsMVM_ICTSS2021 {

	private static final int TIMEOUT_MS = 2400000; // 40 min, 7200000 - 2 hour, 3600000 - 1 hour , 2700000 -45 minutes 

	private static final String data = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
	
	static FileWriter fw;

	@BeforeClass
	public static void setup() throws IOException {
		String fileName = "expriments_data" + data + ".txt";
		fw = new FileWriter(fileName);
		Logger.getLogger(AsmTestGenerator.class).setLevel(Level.OFF);
		Logger.getLogger(TestGenerationWithNuSMV.class).setLevel(Level.OFF);
		Logger.getLogger(NuSMVtestGenerator.class).setLevel(Level.WARN);
		Logger.getLogger(ConverterCounterExample.class).setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.parser").setLevel(Level.OFF);
		Logger.getLogger(AsmMonitoredDataExtractor.class).setLevel(Level.OFF);
		Logger.getLogger("org.asmeta.simulator").setLevel(Level.OFF);
		TestGenerationWithNuSMV.useLTLandBMC = true;
		NuSMVtestGenerator.removeUnaskedChanges = false;
		NuSMVtestGenerator.removeUnChangedControlles = false;
		ConverterCounterExample.IncludeUnchangedVariables = false;
		AsmetaSMV.BMCLength = 100;
		AsmetaSMVOptions.useCoi = true;
	}

	@Test
	public void generateMVMAll() throws Exception {
		String ex = "../../../../../mvm-asmeta/asm_models/VentilatorICTSS/Ventilatore4SimpleTimeLtdY.asm";

		asmeta.AsmCollection asms = ASMParser.setUpReadAsm(new File(ex));

		List<Collection<AsmCoverageBuilder>> criteria = new ArrayList<>();
		List<AsmCoverageBuilder> allcriteria = new ArrayList<>();
		for (CriteriaEnum c : CriteriaEnum.values()) {
			// skip 3 wise and two wise monitored
			if (c == CriteriaEnum.COMBINATORIAL_ALL)
				continue;
			if (c == CriteriaEnum.THREEWISE)
				continue;
			criteria.add(Collections.singleton(c.criteria));
			allcriteria.add(c.criteria);
		}
		criteria.add(allcriteria);

		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(ex, true);

		for (Collection<AsmCoverageBuilder> asmcb : criteria) {
			String name = asmcb.stream().map(x -> x.getCoveragePrefix()).collect(Collectors.joining());
			if (name.length() > 8)
				name = "ALL";
			println(name);
			// generate the tests
			Instant start = Instant.now();
			AsmTestSuite result = nuSMVtestGenerator.generateAbstractTests(asmcb, Integer.MAX_VALUE, ".*");
			Instant finish = Instant.now();
			long timeElapsed = Duration.between(start, finish).toMillis();
			println(nuSMVtestGenerator.getTp());
			println(nuSMVtestGenerator.getGenTests());
			println("time:" + Long.toString(timeElapsed));
			// same not optimezed
			SaveResults.saveResults(result, ex, Collections.singleton(FormatsEnum.AVALLA), name, "");
			// the same tests polished
			List<AsmTestSequence> tests = result.getTests();
			UnecessaryChangesRemover eucr = new UnecessaryChangesRemover(asms);
			for (int i = 0; i < tests.size(); i++) {
				UnchangedRemover.conRemover.optimize(tests.get(i));
				eucr.optimize(tests.get(i));
			}
			SaveResults.saveResults(result, ex, Collections.singleton(FormatsEnum.AVALLA), name + "opt", "");
		}
		fw.close();

	}

	@Test
	public void generateMVM1atatime() throws Exception {
		String ex = "../../../../../mvm-asmeta/asm_models/VentilatorICTSS/Ventilatore4SimpleTimeLtdY.asm";
		// build all the criteria needed for the experiment
		List<AsmCoverageBuilder> coverageCriteria = new ArrayList<>();
		for (CriteriaEnum c : CriteriaEnum.values()) {
			// skip 3 wise and two wise monitored
			if (c == CriteriaEnum.COMBINATORIAL_ALL)
				continue;
			if (c == CriteriaEnum.THREEWISE)
				continue;
			coverageCriteria.add(c.criteria);
		}
		// read and trasform the spec
		asmeta.AsmCollection asms = ASMParser.setUpReadAsm(new File(ex));
		ASMSpecification spec = new AsmetaLLoader().read(new File(ex));
		// build the generator
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(ex, true);
		// build the tree depending on the criteria
		AsmCoverage ct = new MBTCoverage(coverageCriteria).getTPTree(spec);
		for (;;) {
			// queque just one tp
			AsmTestCondition gentc = null;
			for (Iterator<AsmTestCondition> iterator = ct.allTPs().iterator(); iterator.hasNext();) {
				AsmTestCondition tc = iterator.next();
				if (tc.getStatus() == TestConditionState.TODO) {
					tc.setToVerify(true);
					println("generation for " + tc.getUniqueID() + " " + tc.getName());
					gentc = tc;
					break;
				}
			}
			if (gentc == null)
				break;
			// generate the tests
			Instant start = Instant.now();
			NuSMVKillerTask task = new NuSMVKillerTask(gentc.getUniqueID());
			Timer timer = new java.util.Timer();
			timer.schedule(task,TIMEOUT_MS);
			// run nusmv
			AsmTestSuite ts = nuSMVtestGenerator.generateTestforASM(ct,fw);
			assert ts != null;
			assert ts.getTests().size() == 1;
			Instant finish = Instant.now();
			AsmTestSequence ts2 = ts.getTests().get(0);
			// if failed
			if (task.timeout && ts2.allInstructions().size() == 0) {
				gentc.setRunning();
				gentc.setAssertViolated(false);
				assert gentc.getStatus() == TestConditionState.Unknown;
			}
			// if timer is still active, cancel it
			timer.cancel();
			//
			long timeElapsed = Duration.between(start, finish).toMillis();
			println("time:" + Long.toString(timeElapsed));
			//
			println("covers:" +ts2.coverageInfo());
			//
			String parent = new File(ex).getParent(); // .uptoSegment(config.asmetaSpecPath.segmentCount()-1);
			assert (parent != null);
			// System.out.println("Parent: "+parent);
			// find new dir where to put files
			File dir = Paths.get(parent, "experiments"+data).toAbsolutePath().toFile();
			if (! dir.exists()) dir.mkdir();
			// same not optimezed
			File ftc = new File(dir, gentc.getName().replace("@","") + ".avalla");
			new toAvalla(ftc, ts2, ex).save();
			// the same tests polished
			UnecessaryChangesRemover eucr = new UnecessaryChangesRemover(asms);
			UnchangedRemover.conRemover.optimize(ts2);
			eucr.optimize(ts2);
			dir = Paths.get(parent, "experiments_optimized"+data).toFile();
			if (! dir.exists()) dir.mkdir();
			ftc = new File(dir, gentc.getName().replace("@","") + ".avalla");
			new toAvalla(ftc, ts2, ex).save();
		}
		fw.close();
	}
	
	private void println(String s) throws IOException {
		fw.append(s+ "\n");
		fw.flush();
		System.out.println(s);
	}

	class NuSMVKillerTask extends java.util.TimerTask {
		boolean timeout = false;
		private String tpid;
        public NuSMVKillerTask(String uniqueID) {
			tpid = uniqueID;
		}
		@Override
        public void run() {
    		WindowsProcessKiller pKiller = new WindowsProcessKiller();
    		// To kill a command prompt
    		String processName = "NuSMV.exe";
    		boolean isRunning = pKiller.isProcessRunning(processName);
    		System.out.println("is " + processName + " running : " + isRunning);
    		if (isRunning) {
    			pKiller.killProcess(processName);
    			AsmetaSMV.proc.destroy();
    			timeout = true;
    			System.out.println("killing the process for tp " + tpid);
    		}
    		else {
    			System.out.println("Not able to find the process : "+processName);
    		}
        }
    };

    
    
    @Test
    public void counttp() throws Exception{
    		String ex = "../../../../../mvm-asmeta/asm_models/VentilatorICTSS/Ventilatore4SimpleTimeLtdY.asm";
    		// build all the criteria needed for the experiment
    		List<AsmCoverageBuilder> coverageCriteria = new ArrayList<>();
    		for (CriteriaEnum c : CriteriaEnum.values()) {
    			// skip 3 wise and two wise monitored
    			if (c == CriteriaEnum.COMBINATORIAL_ALL)
    				continue;
    			if (c == CriteriaEnum.THREEWISE)
    				continue;
    			coverageCriteria.add(c.criteria);
    		}
    		// read and trasform the spec
    		asmeta.AsmCollection asms = ASMParser.setUpReadAsm(new File(ex));
    		ASMSpecification spec = new AsmetaLLoader().read(new File(ex));
    		// build the generator
    		// build the tree depending on the criteria
    		for (int i=0;i<6;i++) {AsmCoverage ct = new MBTCoverage(coverageCriteria).getTPTree(spec);
    		System.out.println(ct.getChildAt(i).getName() + " " + ((AsmCoverage)ct.getChildAt(i)).getNumberofTPs());
    		}
    }
    
    
	
}
