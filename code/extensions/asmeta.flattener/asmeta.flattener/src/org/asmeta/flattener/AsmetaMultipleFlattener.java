package org.asmeta.flattener;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.asmeta.flattener.nesting.RemoveNestingFlattener;
import org.asmeta.flattener.rule.AsmetaFlattener;
import org.asmeta.flattener.rule.CaseRuleFlattener;
import org.asmeta.flattener.rule.ChooseRuleFlattener;
import org.asmeta.flattener.rule.ForallRuleFlattener;
import org.asmeta.flattener.rule.LetRuleFlattener;
import org.asmeta.flattener.rule.MacroCallRuleFlattener;
import org.asmeta.flattener.rule.RuleSimplifier;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.util.AsmPrinter;

import asmeta.structure.Asm;

/** flatter using multiple flatteners */
public class AsmetaMultipleFlattener {

	public static String flattenAsStr(String asmPath, Class<? extends AsmetaFlattener>... flats) throws Exception {
		return flattenAsStr(asmPath, Arrays.asList(flats));
	}

	public static String flattenAsStr(String asmPath, List<Class<? extends AsmetaFlattener>> refs) throws Exception {
		Asm asm = ASMParser.setUpReadAsm(new File(asmPath)).getMain();
		asm = flatten(asm, refs);
		return printASM(refs, asm);
	}

	public static String printASM(List<Class<? extends AsmetaFlattener>> refs, Asm asm) {
		StringWriter sw = new StringWriter();
		PrintWriter writer = new PrintWriter(sw);
		writer.print("//applied flatteners: ");
		for (Class<? extends AsmetaFlattener> ref : refs) {
			writer.print(AsmetaFlattener.getFlattenerCode(ref) + " ");
		}
		writer.println();
		AsmPrinter ap = new AsmPrinter(writer);
		ap.visit(asm);
		return sw.toString();
	}

	public static String printASM(Asm asm) {
		StringWriter sw = new StringWriter();
		PrintWriter writer = new PrintWriter(sw);
		AsmPrinter ap = new AsmPrinter(writer);
		ap.visit(asm);
		return sw.toString();
	}

	public static Asm flatten(Asm asm, Class<? extends AsmetaFlattener>... flats) throws Exception {
		return flatten(asm, Arrays.asList(flats));
	}

	public static Asm flatten(Asm asm, Collection<Class<? extends AsmetaFlattener>> flats) throws Exception {
		// logger.info(flats);
		if (flats.contains(MacroCallRuleFlattener.class)) {
			asm = new MacroCallRuleFlattener(asm).flattenASM();
			if (FlattenerSetting.simplify) {
				asm = new RuleSimplifier(asm).flattenASM();
			}
			//System.out.println(printASM(asm));
		}
		if (flats.contains(ForallRuleFlattener.class)) {
			asm = new ForallRuleFlattener(asm).flattenASM();
			if (FlattenerSetting.simplify) {
				asm = new RuleSimplifier(asm).flattenASM();
			}
			//System.out.println(printASM(asm));
		}
		if (flats.contains(ChooseRuleFlattener.class)) {
			assert flats.contains(ForallRuleFlattener.class) : flats;
			asm = new ChooseRuleFlattener(asm).flattenASM();
			if (FlattenerSetting.simplify) {
				asm = new RuleSimplifier(asm).flattenASM();
			}
			//System.out.println(printASM(asm));
		}
		if (flats.contains(RemoveArgumentsFlattener.class)) {
			asm = new RemoveArgumentsFlattener(asm).flattenASM();
			if (FlattenerSetting.simplify) {
				asm = new RuleSimplifier(asm).flattenASM();
			}
			//System.out.println(printASM(asm));
		}
		if (flats.contains(LetRuleFlattener.class)) {
			asm = new LetRuleFlattener(asm).flattenASM();
			if (FlattenerSetting.simplify) {
				asm = new RuleSimplifier(asm).flattenASM();
			}
			//System.out.println(printASM(asm));
		}

		if (flats.contains(CaseRuleFlattener.class)) {
			asm = new CaseRuleFlattener(asm).flattenASM();
			if (FlattenerSetting.simplify) {
				asm = new RuleSimplifier(asm).flattenASM();
			}
			//System.out.println(printASM(asm));
		}
		if (flats.contains(RemoveNestingFlattener.class)) {
			asm = new RemoveNestingFlattener(asm).flattenASM();
			if (FlattenerSetting.simplify) {
				asm = new RuleSimplifier(asm).flattenASM();
			}
			//System.out.println(printASM(asm));
		}
		asm.setName(asm.getName() + "_flat");
		//System.out.println(printASM(asm));
		return asm;
	}
}
