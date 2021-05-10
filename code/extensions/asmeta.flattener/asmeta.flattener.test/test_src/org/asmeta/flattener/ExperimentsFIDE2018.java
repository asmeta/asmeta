package org.asmeta.flattener;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.asmeta.flattener.statistics.Statistics;
import org.asmeta.flattener.statistics.StatisticsVisitor;
import org.asmeta.parser.ASMParser;
import org.junit.Test;

import asmeta.structure.Asm;

public class ExperimentsFIDE2018 extends FlattenerTest {

	@Test
	public void testHemodialysis() throws Exception {
		flattenerTest("benchmarksFIDE2018/HemodialysisRef3.asm", ALL_FLATTENERS);
	}

	@Test
	public void testLandingGearSystem() throws Exception {
		flattenerTest("benchmarksFIDE2018/LandingGearSystem_3L.asm", ALL_FLATTENERS);
	}

	@Test
	public void testRoulette() throws Exception {
		flattenerTest("benchmarksFIDE2018/Roulette.asm", ALL_FLATTENERS);
	}

	@Test
	public void testDijkstraTermination() throws Exception {
		flattenerTest("benchmarksFIDE2018/DijkstraTermination.asm", ALL_FLATTENERS);
	}

	@Test
	public void testGilbreathCardTrick() throws Exception {
		flattenerTest("benchmarksFIDE2018/GilbreathCardTrick.asm", ALL_FLATTENERS);
	}

	@Test
	public void testCoffeeVendingMachine() throws Exception {
		flattenerTest("benchmarksFIDE2018/CoffeeVendingMachine.asm", ALL_FLATTENERS);
	}

	@Test
	public void testConwayGameOfLife() throws Exception {
		flattenerTest("benchmarksFIDE2018/GameOfLife.asm", ALL_FLATTENERS);
	}

	@Test
	public void testStereoacuity() throws Exception {
		flattenerTest("benchmarksFIDE2018/StereoacuityRaff5.asm", ALL_FLATTENERS);
	}

	@Test
	public void testPetriNets() throws Exception {
		flattenerTest("benchmarksFIDE2018/PetriNet.asm", ALL_FLATTENERS);
	}

	@Test
	public void testTrafficLight() throws Exception {
		flattenerTest("benchmarksFIDE2018/OneWayTrafficLight.asm", ALL_FLATTENERS);
	}

	@Test
	public void testSluiceGate() throws Exception {
		flattenerTest("benchmarksFIDE2018/SluiceGateMotorCtl.asm", ALL_FLATTENERS);
	}

	@Test
	public void testFerryman() throws Exception {
		String result = flattenerTest("benchmarksFIDE2018/ferrymanSimulator_raff1.asm", ALL_FLATTENERS);
		System.out.println(result);
		// 	derived oppositeSide: Side -> Side
		// derived allOnRightSide: Boolean

		assertTrue(result.contains(""));
	}

	private static void singleExperiment(String asmModel, boolean printStats) throws Exception {
		String refactoredAsm = AsmetaMultipleFlattener.flattenAsStr(asmModel, ALL_FLATTENERS);
		String asmName = Paths.get(asmModel).getFileName().toString();
		String path = asmModel.substring(0, asmModel.indexOf(asmName));
		String asmPath = path + asmName;
		Asm flattenedAsm = parseFlattenedAsm(refactoredAsm, asmName, path);
		Asm asm = ASMParser.setUpReadAsm(new File(asmPath)).getMain();
		if (printStats) {
			// stuff for statistics
			Statistics.getInstance().setEnabled(true);
			Statistics.getInstance().getPlainStatistics(asm, flattenedAsm);
			Statistics.resetStats();
		}
	}

	private static void getSize(String asmModel) throws Exception {
		Asm asm = ASMParser.setUpReadAsm(new File(asmModel)).getMain();
		StatisticsVisitor sv = new StatisticsVisitor(asm);
		sv.visitAsm();
		System.out.print(asm.getName());
		for (String r : sv.getRulesMap().keySet()) {
			System.out.print("\t" + sv.getRulesMap().get(r));
		}
		System.out.println();
	}

	private static void experimentSimplifier() {
		;
		Logger.getRootLogger();
		Category.shutdown();
		// init();
		FlattenerSetting.simplify = true;
		System.out.println("Model\tTS\tRS");
		File dir = new File("benchmarksFIDE2018");
		for (File f : dir.listFiles()) {
			if (f.isFile() && f.getName().endsWith(".asm") && !f.getName().endsWith("_flat.asm")) {
				try {
					Statistics.resetStats();
					singleExperiment(f.getAbsolutePath(), false);
					LinkedHashMap<String, Integer> map = Statistics.getInstance().getInfoMap();
					System.out.println(f.getName() + "\t" + map.get("TS") + "\t" + map.get("RS"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void experimentFlattener() {
		;
		Logger.getRootLogger();
		Category.shutdown();
		FlattenerSetting.simplify = true;
		System.out.println("Model\tMCR\tFR\tChR\tAR\tLR\tCaR\tNR");
		File dir = new File("benchmarksFIDE2018");
		for (File f : dir.listFiles()) {
			if (f.isFile() && f.getName().endsWith(".asm") && !f.getName().endsWith("_flat.asm")) {
				try {
					singleExperiment(f.getAbsolutePath(), true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void experimentTime(int numOfRuns) {
		Logger.getRootLogger();
		Category.shutdown();
		FlattenerSetting.simplify = true;
		System.out.println("Model\ttime");
		File dir = new File("benchmarksFIDE2018");
		HashMap<String, Long> totalTime = new HashMap<String, Long>();
		for (File f : dir.listFiles()) {
			if (f.isFile() && f.getName().endsWith(".asm") && !f.getName().endsWith("_flat.asm")) {
				try {
					singleExperiment(f.getAbsolutePath(), false);
					totalTime.put(f.getName(), 0L);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for (int i = 0; i < numOfRuns; i++) {
			for (File f : dir.listFiles()) {
				if (f.isFile() && f.getName().endsWith(".asm") && !f.getName().endsWith("_flat.asm")) {
					try {
						singleExperiment(f.getAbsolutePath(), false);
						long time = System.nanoTime();
						singleExperiment(f.getAbsolutePath(), false);
						time = System.nanoTime() - time;
						// System.out.println(f.getName() + "\t" + time);
						totalTime.put(f.getName(), totalTime.get(f.getName()) + time);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		for (Entry<String, Long> e : totalTime.entrySet()) {
			System.out.println(e.getKey() + "\t" + (e.getValue() / numOfRuns));
		}
	}

	private static void benchmarksSize() {
		System.out.println(
				"Model\tUpdateRule\tParallelRule\tConditionalRule\tForallRule\tChooseRule\tCaseRule\tLetRule\tMacroCallRule");
		File dir = new File("benchmarksFIDE2018");
		for (File f : dir.listFiles()) {
			if (f.isFile() && f.getName().endsWith(".asm") && !f.getName().endsWith("_flat.asm")) {
				try {
					getSize(f.getAbsolutePath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void finalSize() {
		System.out.println(
				"Model\tUpdateRule\tParallelRule\tConditionalRule\tForallRule\tChooseRule\tCaseRule\tLetRule\tMacroCallRule");
		File dir = new File("benchmarksFIDE2018");
		for (File f : dir.listFiles()) {
			if (f.isFile() && f.getName().endsWith("_flat.asm")) {
				try {
					getSize(f.getAbsolutePath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		experimentFlattener();
		// experimentSimplifier();
		// experimentTime(5);
		// benchmarksSize();
		// finalSize();
	}
}
