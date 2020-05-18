package org.asmeta.flattener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.asmeta.flattener.args.RemoveArgumentsRuleFlattener;
import org.asmeta.flattener.args.RemoveArgumentsTermFlattener;
import org.asmeta.flattener.nesting.RemoveNestingFlattener;
import org.asmeta.flattener.rule.AsmetaFlattener;
import org.asmeta.flattener.rule.CaseRuleFlattener;
import org.asmeta.flattener.rule.ChooseRuleFlattener;
import org.asmeta.flattener.rule.ForallRuleFlattener;
import org.asmeta.flattener.rule.LetRuleFlattener;
import org.asmeta.flattener.rule.MacroCallRuleFlattener;
import org.asmeta.flattener.rule.RuleFlattener;
import org.asmeta.flattener.rule.RuleSimplifier;
import org.asmeta.flattener.statistics.Statistics;
import org.asmeta.flattener.term.TermRenameVars;
import org.asmeta.parser.ASMParser;
import org.junit.Before;
import org.junit.BeforeClass;

import asmeta.structure.Asm;

public class FlattenerTest {
	protected String examplesDir = "../../../../asm_examples/";
	protected static Class<? extends AsmetaFlattener>[] ALL_FLATTENERS = new Class[] { MacroCallRuleFlattener.class,
			ForallRuleFlattener.class, ChooseRuleFlattener.class, RemoveArgumentsFlattener.class,
			LetRuleFlattener.class, CaseRuleFlattener.class, RemoveNestingFlattener.class };

	@BeforeClass
	public static void init() {
		Logger.getRootLogger().removeAllAppenders();
		Logger.getRootLogger().addAppender(new ConsoleAppender(new SimpleLayout()));
		Logger.getLogger(RemoveArgumentsFlattener.class).setLevel(Level.INFO);
		Logger.getLogger(TermRenameVars.class).setLevel(Level.INFO);
		Logger.getLogger(ChooseRuleFlattener.class).setLevel(Level.INFO);
		Logger.getLogger(RuleFlattener.class).setLevel(Level.DEBUG);
		Logger.getLogger(RuleSimplifier.class).setLevel(Level.DEBUG);
		Logger.getLogger(MacroCallRuleFlattener.class).setLevel(Level.INFO);
		// Logger.getLogger(ProgramFlattener.class).setLevel(Level.INFO);
		Logger.getLogger(ForallRuleFlattener.class).setLevel(Level.INFO);
		Logger.getLogger(RemoveArgumentsTermFlattener.class).setLevel(Level.INFO);
		Logger.getLogger(RemoveArgumentsRuleFlattener.class).setLevel(Level.INFO);
		Logger.getLogger("org.asmeta.parser").setLevel(Level.FATAL);
	}

	@Before
	public void initTest() {
		// RuleFlattener.DO_STATS = false;
		Statistics.resetMap();
	}

	public void flattenerTestAllCombinations(String asmModel, Class<? extends AsmetaFlattener>... flatteners)
			throws Exception {
		Set<Set<Class<? extends AsmetaFlattener>>> allSubsets = buildPowerset(flatteners);
		for (Set<Class<? extends AsmetaFlattener>> a : allSubsets) {
			initTest();
			flattenerTest(asmModel, a.toArray(new Class[a.size()]));
		}
	}

	public String flattenerTest(String asmModel, Class<? extends AsmetaFlattener>... flatteners) throws Exception {
		Statistics.resetStats();
		String refactoredAsm = AsmetaMultipleFlattener.flattenAsStr(asmModel, flatteners);
		System.out.println(refactoredAsm);
		String asmName = Paths.get(asmModel).getFileName().toString();
		String path = asmModel.substring(0, asmModel.indexOf(asmName));
		String asmPath = path + asmName;
		Asm flattenedAsm = parseFlattenedAsm(refactoredAsm, asmName, path);
		Asm asm = ASMParser.setUpReadAsm(new File(asmPath)).getMain();
		// stuff for statistics
		Statistics.getInstance().setEnabled(true);
		Statistics.getInstance().getStatistics(asm, flattenedAsm);
		return refactoredAsm;
	}

	protected static Asm parseFlattenedAsm(String refactoredAsm, String asmName, String path) throws IOException {
		String flattenedAsmPath = path + asmName.substring(0, asmName.length() - 4) + "_flat.asm";
		File f = null;
		BufferedWriter bw = null;
		Asm flattenedAsm = null;
		try {
			f = new File(flattenedAsmPath);
			bw = new BufferedWriter(new FileWriter(f));
			bw.write(refactoredAsm);
			bw.close();
			flattenedAsm = ASMParser.setUpReadAsm(f).getMain();
		} catch (Exception e) {
			e.printStackTrace();
			assert false;
		} finally {
			// f.delete();
			bw.close();
		}
		return flattenedAsm;
	}

	private static Set<Set<Class<? extends AsmetaFlattener>>> buildPowerset(Class<? extends AsmetaFlattener>[] flatteners) {
		Set<Set<Class<? extends AsmetaFlattener>>> result = new HashSet<>();
		HashSet<Class<? extends AsmetaFlattener>> empty = new HashSet<Class<? extends AsmetaFlattener>>();
		result.add(empty);
		for (Class<? extends AsmetaFlattener> element : flatteners) {
			Set<Set<Class<? extends AsmetaFlattener>>> previousSets = new HashSet(result);
			for (Set<Class<? extends AsmetaFlattener>> subSet : previousSets) {
				Set<Class<? extends AsmetaFlattener>> newSubSet = new HashSet(subSet);
				newSubSet.add(element);
				result.add(newSubSet);
			}
		}
		result.remove(empty);
		return result;
	}

	public static void main(String[] args) {
		Class<? extends AsmetaFlattener>[] s = new Class[] { MacroCallRuleFlattener.class, ForallRuleFlattener.class,
				RemoveNestingFlattener.class };
		for (Set<Class<? extends AsmetaFlattener>> a : buildPowerset(s)) {
			System.out.println(a);
		}
	}
}
