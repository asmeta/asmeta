package org.asmeta.flattener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import org.asmeta.flattener.nesting.RemoveNestingFlattener;
import org.asmeta.flattener.rule.AsmetaFlattener;
import org.asmeta.flattener.rule.ChooseRuleFlattener;
import org.asmeta.parser.ASMParser;
import org.asmeta.parser.util.AsmPrinter;

import asmeta.structure.Asm;
import asmeta.terms.basicterms.Term;
import asmeta.transitionrules.basictransitionrules.Rule;

public class Main {

	public static void main(String[] args) throws Exception {
		String filename = "chooseRule";
		Asm asm = ASMParser.setUpReadAsm(new File("./examples/" + filename + ASMParser.ASM_EXTENSION)).getMain();
		Asm flattenedAsm = ASMParser.setUpReadAsm(new File("./examples/" + filename + ASMParser.ASM_EXTENSION)).getMain();

		// Refactoring -----------------
		AsmetaFlattener refactorer;
		//refactorer = new ForallRuleFlattener(flattenedAsm);
		refactorer = new ChooseRuleFlattener(asm);
		// refactorer = new LetRuleRefactorer(flattenedAsm);
		flattenedAsm =  refactorer.flattenASM();

		// Remove Nesting --------------
		// RemoveNesting rn = new RemoveNesting(flattenedAsm);
		// flattenedAsm = rn.refactorASM();

		// Rename ASM
		flattenedAsm.setName(filename + "_flattened");

		// print to system out
		PrintWriter writer = new PrintWriter(System.out);
		AsmPrinter ap = new AsmPrinter(writer);
		// ap.visit(asm);
		ap.visit(flattenedAsm);
		ap.close();

		//printToFile(flattenedAsm, filename);
	}

	// print to file
	private static void printToFile(Asm asm, String filename) throws FileNotFoundException {
		PrintWriter fileWriter = new PrintWriter(new File("./examples/" + filename + "_flattened.asm"));
		AsmPrinter apf = new AsmPrinter(fileWriter);
		apf.visit(asm);
		apf.close();
	}

	private void showGuardsRulesMap(RemoveNestingFlattener ruleFlattener) {
		Map<ArrayList<Term>, ArrayList<Rule>> guardsRulesMap = ruleFlattener.getGuardsRulesMap();
		PrintWriter writer = new PrintWriter(System.out);
		AsmPrinter p = new AsmPrinter(writer);

		for (ArrayList<Term> bt : guardsRulesMap.keySet()) {
			writer.write("Guards: ");
			p.visitTerm(ruleFlattener.getStdlFunction().and(bt));
			writer.write("\nUpdates:\n");
			ArrayList<Rule> ur = guardsRulesMap.get(bt);
			for (Rule u : ur) {
				// System.out.print(u + " ");

				p.visit(u);
			}
			// System.out.println("\n");

			writer.write("\n");
		}
	}

}
