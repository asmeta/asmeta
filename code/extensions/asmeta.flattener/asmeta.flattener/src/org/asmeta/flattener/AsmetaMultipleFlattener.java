package org.asmeta.flattener;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.asmeta.flattener.nesting.RemoveNestingFlattener;
import org.asmeta.flattener.rule.AsmetaFlattener;
import org.asmeta.flattener.rule.CaseRuleFlattener;
import org.asmeta.flattener.rule.ChooseRuleFlattener;
import org.asmeta.flattener.rule.ForallRuleFlattener;
import org.asmeta.flattener.rule.LetRuleFlattener;
import org.asmeta.flattener.rule.MacroCallRuleFlattener;
import org.asmeta.flattener.rule.RuleSimplifier;
import org.asmeta.flattener.rule.RuleFlattener;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.util.AsmPrinter;

import asmeta.structure.Asm;

/** flatter using multiple flatteners */
public class AsmetaMultipleFlattener {

	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AsmetaMultipleFlattener.class);

	
	public static String flattenAsStr(String asmPath, Class<? extends AsmetaFlattener>... flats) throws Exception {
		return flattenAsStr(asmPath, Arrays.asList(flats));
	}

	public static String flattenAsStr(String asmPath, List<Class<? extends AsmetaFlattener>> flats) throws Exception {
		Asm asm = ASMParser.setUpReadAsm(new File(asmPath)).getMain();
		asm = flatten(asm, flats,false); 
		return printASM(flats, asm);
	}

	public static String flattenAsStrWEF(String asmPath, List<Class<? extends AsmetaFlattener>> flats) throws Exception {
		Asm asm = ASMParser.setUpReadAsm(new File(asmPath)).getMain();
		asm = flatten(asm, flats,true); 
		return printASM(flats, asm);
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
		return flatten(asm, Arrays.asList(flats),false);
	}
	
	public static Asm flattenWithExtra(Asm asm, Class<? extends AsmetaFlattener>... flats) throws Exception {
		return flatten(asm, Arrays.asList(flats),true);
	}

	
	private static AsmetaFlattener[] standardFlattenerInOrder(Asm asm) {
		return new AsmetaFlattener[] {
				new MacroCallRuleFlattener(asm),
				new ForallRuleFlattener(asm),
				new ChooseRuleFlattener(asm),
				new RemoveArgumentsFlattener(asm),
				new LetRuleFlattener(asm),
				new CaseRuleFlattener(asm),
				new RemoveNestingFlattener(asm)
		};
	}
	
	
	// flatten using the given flatteners in the order of standardFlattenerInOrder
	private static Asm flatten(Asm asm, Collection<Class<? extends AsmetaFlattener>> flats, boolean ALLOW_EXTRA_FLATTENERS) throws Exception {
		logger.debug(flats);
		List<Class<? extends AsmetaFlattener>> flatsToApply = new ArrayList<Class<? extends AsmetaFlattener>>(flats);
		for (AsmetaFlattener flattener : standardFlattenerInOrder(asm)) {
			if (flatsToApply.contains(flattener.getClass())) {
				if (logger.isDebugEnabled()) {
					StringWriter sw = new StringWriter();
					AsmPrinter ap = new AsmPrinter(sw);
					ap.visit(asm);
					ap.flush();
					logger.debug("before " + flattener.getClass().getSimpleName() + " : " + sw.toString());
				}
				flatsToApply.remove(flattener.getClass());
				asm = flattener.flattenASM();
				if (FlattenerSetting.simplify) {
					asm = new RuleSimplifier(asm).flattenASM();
				}
			}
		}
		if (logger.isDebugEnabled()) {
			StringWriter sw = new StringWriter();
			AsmPrinter ap = new AsmPrinter(sw);
			ap.visit(asm);
			ap.flush();
			logger.debug("final  : " + sw.toString());
		}
		if (!flatsToApply.isEmpty()) {
			if (! ALLOW_EXTRA_FLATTENERS) 
				throw new Exception("Unknown flattener(s): " + flatsToApply);
			else {
				// apply extra flatteners
				for (var f: flatsToApply) {
					Constructor<?>[] constructs = f.getConstructors();
					assert constructs.length == 1 : "flattener " + f + " has constructors " + constructs + " " + constructs.length;
					AsmetaFlattener flattener = (AsmetaFlattener) constructs[0].newInstance(asm);
					asm = flattener.flattenASM();
					if (FlattenerSetting.simplify) {
						asm = new RuleSimplifier(asm).flattenASM();
					}
				}
			}
		}		
		asm.setName(asm.getName() + "_flat");
		return asm;
	}

}
