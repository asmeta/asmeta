package org.asmeta.atgt.generator;

import static org.junit.Assert.fail;

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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.asmeta.atgt.generator.AsmTestGenerator;
import org.asmeta.atgt.generator.ConverterCounterExample;
import org.asmeta.atgt.generator.CriteriaEnum;
import org.asmeta.atgt.generator.FormatsEnum;
import org.asmeta.atgt.generator.NuSMVtestGenerator;
import org.asmeta.atgt.generator.SaveResults;
import org.asmeta.atgt.generator.TestGenerationWithNuSMV;
import org.asmeta.atgt.generator2.AsmTestGeneratorBySimulation;
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
import atgt.specification.location.Constant;
import atgt.specification.location.Function;
import atgt.specification.location.FunctionApplication;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;
import atgt.testseqexport.toAvalla;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.IdExpressionCreator;
import tgtlib.specification.ParseException;

/**
 * tests for the asmeta generator
 *
 */
public class ExperimentsMVM_ICTSS2021 {

	private static final int TIMEOUT_MS = 600000; // 10 min, 2400000 - 40 min, 7200000 - 2 hour, 3600000 - 1 hour , 2700000 -45 minutes 

	private static final String data = LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
	
	static FileWriter fw;

	@BeforeClass
	public static void setup() throws IOException {
		String fileName = "expriments_data"+ data + ".txt";
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
			println("time:"+ Long.toString(timeElapsed));
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

	static String ex = "../../../../../mvm-asmeta/asm_models/VentilatorICTSS/Ventilatore4SimpleTimeLtdY.asm";

	
	@Test
	public void generateMVM1atatime() throws Exception {
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
		// build the generator
		NuSMVtestGenerator nuSMVtestGenerator = new NuSMVtestGenerator(ex, true);
		// build the tree depending on the criteria
		asmeta.AsmCollection asms = ASMParser.setUpReadAsm(new File(ex));
		ASMSpecification spec = new AsmetaLLoader().read(new File(ex));
		AsmCoverage ct = new MBTCoverage(coverageCriteria).getTPTree(spec);
		for (;;) {
			// queque just one tp
			AsmTestCondition gentc = null;
			for (Iterator<AsmTestCondition> iterator = ct.allTPs().iterator(); iterator.hasNext();) {
				AsmTestCondition tc = iterator.next();
				if (tc.getStatus() == TestConditionState.TODO) {
					tc.setToVerify(true);
					println("generation for "+ tc.getUniqueID() + ""+ tc.getName());
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
			println("time:"+ Long.toString(timeElapsed));
			//
			println("covers:"+ts2.coverageInfo());
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
    		System.out.println("is "+ processName + "running : "+ isRunning);
    		if (isRunning) {
    			pKiller.killProcess(processName);
    			AsmetaSMV.proc.destroy();
    			timeout = true;
    			System.out.println("killing the process for tp "+ tpid);
    		}
    		else {
    			System.out.println("Not able to find the process : "+processName);
    		}
        }
    };

    
    
    @Test
    public void counttp() throws Exception{
    		AsmCoverage ct = buildCT();
    		for (int i=0;i<6;i++) {
    			System.out.println(ct.getChildAt(i).getName() + ""+ ((AsmCoverage)ct.getChildAt(i)).getNumberofTPs());
    		}
    }

	private AsmCoverage buildCT() throws Exception, ParseException {
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
		// read and transform the spec
		asmeta.AsmCollection asms = ASMParser.setUpReadAsm(new File(ex));
		ASMSpecification spec = new AsmetaLLoader().read(new File(ex));
		// build the generator
		// build the tree depending on the criteria
		AsmCoverage ct = new MBTCoverage(coverageCriteria).getTPTree(spec);
		return ct;
	}
    
    @Test
    public void rndGeneration() throws Exception{
		asmeta.AsmCollection asms = ASMParser.setUpReadAsm(new File(ex));
		AsmTestGeneratorBySimulation asmgen = new AsmTestGeneratorBySimulation(asms,10,2);
		AsmTestSuite testsuite = asmgen.getTestSuite();
		// get the coverage tree
		AsmCoverage ct = buildCT();
		// compute coverage of the test suite
		ASMSpecification ASMSpecification = new AsmetaLLoader().read(new File(ex));		
		for (AsmTestSequence asmTest: testsuite) {
			// TRICK riempio lo stato iniziale 
			// model not complete:exp_pause not defined when evaluating exp_pause = true
			addInitialState(asmTest,ASMSpecification,"exp_pause", "false");
			// model not complete:selfTestPassed not defined when evaluating selfTestPassed = true
			addInitialState(asmTest,ASMSpecification,"selfTestPassed", "false");
			//model not complete:turnOffApneaAlarm not defined when evaluating turnOffApneaAlarm = true
			addInitialState(asmTest,ASMSpecification,"turnOffApneaAlarm", "false");
			// model not complete:pawGTMaxPinsp not defined when evaluating pawGTMaxPinsp = true
			addInitialState(asmTest,ASMSpecification,"pawGTMaxPinsp", "false");
			// model not complete:startVentilation not defined when evaluating startVentilation = true
			addInitialState(asmTest,ASMSpecification,"startVentilation", "false");
			// model not complete:mode not defined when evaluating mode = PSV
			addInitialState(asmTest,ASMSpecification,"mode", "PCV");
			//model not complete:startupEnded not defined when evaluating startupEnded = false
			addInitialState(asmTest,ASMSpecification,"startupEnded", "false");
			//model not complete:dropPAW_ITS_PSV not defined when evaluating dropPAW_ITS_PSV = true
			addInitialState(asmTest,ASMSpecification,"dropPAW_ITS_PSV", "false");
			//model not complete:ins_pause not defined when evaluating ins_pause = true
			addInitialState(asmTest,ASMSpecification,"ins_pause", "false");
			// model not complete:rm_request not defined when evaluating rm_request = true
			addInitialState(asmTest,ASMSpecification,"rm_request", "false");
			// model not complete:resume not defined when evaluating resume = true
			addInitialState(asmTest,ASMSpecification,"resume", "false");
			// model not complete:flowDropPSV not defined when evaluating flowDropPSV = false
			addInitialState(asmTest,ASMSpecification,"flowDropPSV", "false");
			// model not complete:stopVentilation not defined when evaluating stopVentilation = true
			addInitialState(asmTest,ASMSpecification,"stopVentilation", "false");
			//model not complete:dropPAW_ITS_PCV not defined when evaluating dropPAW_ITS_PCV = true
			addInitialState(asmTest,ASMSpecification,"dropPAW_ITS_PCV", "false");
			// model not complete:iValve not defined when evaluating iValve = CLOSED
			addInitialState(asmTest,ASMSpecification,"iValve", "OPEN");
			// model not complete:oValve not defined when evaluating oValve = OPEN
			addInitialState(asmTest,ASMSpecification,"oValve", "CLOSED");
			addInitialState(asmTest,ASMSpecification,"state", "MAIN_REGION_STARTUP");
			String[] timers = {"TIMER_INSPIRATION_DURATION_MS","TIMER_EXPIRATION_DURATION_MS","TIMER_MAX_INSP_TIME_PSV","TIMER_MIN_EXP_TIME_PSV","TIMER_APNEALAG ","TIMER_MAX_INS_PAUSE","TIMER_MAX_RM_TIME",
			       			"TIMER_MAX_EXP_PAUSE","TIMER_TRIGGERWINDOWDELAY_MS","TIMER_MIN_INSP_TIME_MS"};
			for (String s : timers)			
				addInitialState(asmTest,ASMSpecification,"start["+s+"]", "0");
			// now compute coverage
			NuSMVtestGenerator.computeCoverage(ct , asmTest, ASMSpecification);
			System.out.println(asmTest.allInstructions());
			// after 
			// optimize the test 
			// the same tests polished
			UnecessaryChangesRemover eucr = new UnecessaryChangesRemover(asms);
			UnchangedRemover.conRemover.optimize(asmTest);
			eucr.optimize(asmTest);
			System.out.println(asmTest.allInstructions());
			
		}
		// how may tp are covered
		System.out.println(StreamSupport.stream(ct.allTPs().spliterator(), false).filter( x-> x.getStatus() == TestConditionState.Covered).count());


    }
    
    static IdExpressionCreator icc = new IdExpressionCreator();
    
    static void addInitialState(AsmTestSequence asmTest, ASMSpecification ASMSpecification, String var, String value) {    	
		Map<Location, String> initialState = asmTest.allInstructions().get(0);
		if (!var.contains("[")) {
			Variable variable = ASMSpecification.getVariable(var);
			assert variable != null;
			String val = initialState.get(variable);
			if (val == null)
				initialState.put(variable, value);
			else
				System.out.println("***********************");
		} else {
			String[] dd = var.split("\\[|\\]");
			//System.out.println(Arrays.toString(dd));
			Function v = ASMSpecification.getFunction(dd[0]);
			//System.out.println(v);			
			IdExpression c = icc.createIdExpression(dd[1], null);
			//System.out.println(c);
			Location l = new FunctionApplication(v,Collections.singletonList(c));
			initialState.put(l, value);
		}
    }
	
}
