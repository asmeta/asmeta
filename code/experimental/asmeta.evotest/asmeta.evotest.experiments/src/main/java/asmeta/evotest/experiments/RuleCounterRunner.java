package asmeta.evotest.experiments;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.asmeta.avallaxt.validation.RuleExtractorFromMacroDecl;
import org.asmeta.parser.ASMParser;
import org.eclipse.emf.common.util.EList;

import asmeta.AsmCollection;
import asmeta.definitions.RuleDeclaration;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;

public class RuleCounterRunner {
	
	private static final String ASM_EXAMPLES = "../../../../asm_examples";
	
	/*
	 * Change the following private fields as needed
	 */
	private static final Class<? extends Rule> RULE_CLASS = CaseRule.class;
	private static final String INPUT_LIST = "data/fm-short-26-exp/model_list_in.txt";
	private static final String OUTUPT_LIST = "data/fm-short-26-exp/model_list_output.txt";
	
	/**
	 * Entry point.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		// Read the file
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(INPUT_LIST));
			System.out.println("Loaded " + lines.size() + " entries from model list.");
		} catch (IOException e) {
			System.out.println("Falied to load " + INPUT_LIST + ".\n" + e);
			return;
		}
		// For on the lines, skipping commented ones
		// For each asm in the list: generate tests -> run validation -> run mutation
		List<String> newLines = new ArrayList<>();
		for (String line : lines) {
			// Skip commented asms
			if (line.isEmpty() || line.startsWith("//"))
				continue;
			// Retrieve asm
			String asmPath = Paths.get(ASM_EXAMPLES).resolve(line).toString().replace('\\', '/');
			File asmFile = new File(asmPath);
			// Parse asm and check for rule
			boolean ruleFound = false;
			try {
				AsmCollection asms = ASMParser.setUpReadAsm(asmFile);
				EList<RuleDeclaration> ruleDeclarations = asms.getMain().getBodySection().getRuleDeclaration();
				for (RuleDeclaration ruleDecl : ruleDeclarations) {
					List<Rule> a = RuleExtractorFromMacroDecl.getAllContainedRules((MacroDeclaration) ruleDecl);
					if (a.stream().anyMatch(rule -> RULE_CLASS.isInstance(rule))) {
						ruleFound = true;
						break;
					}
				}
			} catch (Exception e) {
				newLines.add("//check manually: " + line);
			}
			if (ruleFound) {
				System.out.println("Adding " + line);
				newLines.add(line);
			}
		}
		// Write new list
		System.out.println("Writing to " + OUTUPT_LIST);
		FileOutputStream os;
		try {
			os = new FileOutputStream(OUTUPT_LIST);
			String initialComment = "// total ASM with " + RULE_CLASS.toString() +  ": " + newLines.size() + System.lineSeparator() + System.lineSeparator();;
	        os.write(initialComment.getBytes());
	        os.write(System.lineSeparator().getBytes());
	        for (String line: newLines) {
	        	os.write(line.getBytes());
	        	os.write(System.lineSeparator().getBytes());
	        }
	        os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Finished");
	}

}
