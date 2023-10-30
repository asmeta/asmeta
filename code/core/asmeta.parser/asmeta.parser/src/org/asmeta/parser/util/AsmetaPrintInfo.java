package org.asmeta.parser.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.asmeta.parser.ASMParser;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.Function;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.StaticFunction;
import asmeta.structure.Asm;
import asmeta.structure.Body;

/**
 * print the information of a specification or of a set a spec in directory
 * 
 * @author garganti
 *
 */
public class AsmetaPrintInfo {

	public class AsmInfo {

		public Map<String, Integer> infoMap = new HashMap<String, Integer>();

		public List<String> ruleNamesList;

		void inc(String s) {
			int val;
			if (infoMap.containsKey(s)) {
				val = infoMap.get(s) + 1;
			} else {
				val = 1;
			}
			infoMap.put(s, val);
		}
	}

	// all the asm in the dir, or just one in case is just one
	public List<Asm> asms = new ArrayList<Asm>();

	public AsmetaPrintInfo(String asmPath) throws Exception {
		File asmFile = new File(asmPath);
		if (asmFile.isDirectory()) {
			System.out.println("reading all the asm in the path");
			File[] files = asmFile.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File arg0, String arg1) {
					return arg1.endsWith(ASMParser.ASM_EXTENSION);
				}
			});
			for (File f : files) {
				asms.add(ASMParser.setUpReadAsm(f).getMain());
			}
		} else {
			asms.add(ASMParser.setUpReadAsm(asmFile).getMain());
		}
	}

	public AsmInfo getInfo() throws Exception {
		AsmInfo info = new AsmInfo();
		for (Asm asm : asms) {
			// functions
			EList<Function> funcs = asm.getHeaderSection().getSignature().getFunction();
			for (Function f : funcs) {
				if (f instanceof ControlledFunction) {
					info.inc("controlled");
				} else if (f instanceof MonitoredFunction) {
					info.inc("monitored");
				} else if (f instanceof DerivedFunction) {
					info.inc("derived");
				} else if (f instanceof StaticFunction) {
					info.inc("static");
				} else {
					throw new RuntimeException(f.getClass().getCanonicalName());
				}
			}
			// rules
			RuleCounter rc = new RuleCounter();
			info.infoMap.put("nrules", rc.getNumOfRules(asm));
			// rule declaration
			Body bodySection = asm.getBodySection();
			info.infoMap.put("nrulesdeclarations", bodySection.getRuleDeclaration().size());
			// rule names
			info.ruleNamesList = bodySection.getRuleDeclaration().stream().map(x -> x.getName()).collect(Collectors.toList());
			// properties
			info.infoMap.put("nproperties", bodySection.getProperty().size());
		}
		return info;
	}
}