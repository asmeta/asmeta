package org.asmeta.avallaxt.validation;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.asmeta.avallaxt.avalla.Block;
import org.asmeta.avallaxt.avalla.Element;
import org.asmeta.avallaxt.avalla.Pick;
import org.asmeta.avallaxt.avalla.Scenario;
import org.asmeta.parser.util.AsmetaTermPrinter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.ChooseRule;

/** a collection of utility methods */
public class ScenarioUtility {

	/**
	 * return the base dir for an import clause see also asmetaxt
	 */
	public static String getBaseDir(Scenario scenario) {
		// try to get the location when the file is open in eclipse
		// for example in case of the plugin
		URI uri = scenario.eResource().getURI();
		String platformString = uri.toPlatformString(true);
		if (platformString != null) {
			try {
				ResourcesPlugin.getWorkspace();
				// workspace is open
				// get the main file
				IFile myFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(platformString));
				return myFile.getLocation().toFile().getParent();
			} catch (java.lang.IllegalStateException e) {
				// workspace is closed
			}
		}
		// it is a local file
		// println("FFF" + uri.toFileString())
		return new File(uri.toFileString()).getParent();
		// return new File(".").absolutePath
	}

	// return the path for the asm spec to be loaded for this scenario
	public static java.nio.file.Path getAsmPath(Scenario scenario) {
		String spec = scenario.getSpec();
		// remove "" if necessary
		if (spec.startsWith("\"")) {
			assert spec.endsWith("\"");
			spec = spec.substring(1, spec.length() - 1);
		}
		// assuming absolute
		if (Files.exists(Paths.get(spec)))
			return Paths.get(spec);
		// IT MUST BE relative then
		// check if the file exists
		String baseDir = getBaseDir(scenario);
		// try relative
		return Paths.get(baseDir, spec);
	}

	// return the names of blocks in this scenario
	static List<String> getBlockNames(Scenario s) {
		List<String> names = new ArrayList<>();
		List<String> duplicated = new ArrayList<>();
		addBlockNames(names, duplicated, s.getElements());
		// ignore duplicated
		return names;
	}

	/**
	 * lon:list of names (unique) duplicated: duplicated names
	 */
	static void addBlockNames(List<String> lon, List<String> duplicated, List<Element> ee) {
		for (Element e : ee) {
			// if it is a Block, call recursively and add to the list of names
			if (e instanceof Block) {
				// search for blocks inside blocks
				addBlockNames(lon, duplicated, ((Block) e).getElements());
				// add this block, do not check if already declared
				if (lon.contains(((Block) e).getName()))
					duplicated.add(((Block) e).getName());
				else
					lon.add(((Block) e).getName());
			}
		}
	}
	
	/**
	 * check whether the picked value is undef
	 * 
	 * @param pick the pick rule
	 * @return the error message, null if there is no error
	 */
	static String checkPickValue(Pick pick) {
		String value = pick.getValue();
		if (value.equals("undef"))
			return "picking the 'undef' value is not allowed";
		return null;
	}

	/**
	 * check whether rule declaration defined in the pick rule exists in the asm
	 * 
	 * @param pick the pick rule
	 * @param asm  the asm
	 * @return the error message, null if there is no error
	 */
	static String checkPickRule(Pick pick, Asm asm) {
		if (pick.getRule() != null) {
			for (RuleDeclaration rd : asm.getBodySection().getRuleDeclaration()) {
				String name = rd.getName();
				List<String> params = new ArrayList<>();
				for (VariableTerm variable : rd.getVariable()) {
					params.add(variable.getDomain().getName());
				}
				String signature = name + (params.size() > 0 ? ("(" + String.join(",", params)) + ")" : "");
				if (signature.equals(pick.getRule()))
					return null;
			}
			return pick.getRule() + " is not defined in the main asm";
		}
		return null;
	}

	/**
	 * check whether the pick variable can be matched
	 * with one and only one choose variable in the asm
	 * 
	 * @param pick        the pick rule
	 * @param chooseRules the map with all the choose rule in the asm being
	 *                    validated as key and the name of the macro rule
	 *                    declaration in which are defined as value
	 * @return the error message, null if there is no error
	 */
	static String checkPickVariable(Pick pick, Map<ChooseRule, String> chooseRules) {
		String variable = pick.getVar();
		if (pick.getRule() == null) {
			// The pick does not define the rule declaration
			// => there must exists one and only one Choose rule with a variable that
			// matches with the pick variable
			int nMatch = 0;
			ChooseRule lastChoose = null;
			for (Entry<ChooseRule, String> chooseRule : chooseRules.entrySet()) {
				if (chooseRule.getKey().getVariable().stream().anyMatch(v -> v.getName().equals(variable))) {
					nMatch++;
					lastChoose = chooseRule.getKey();
				}
			}
			if (nMatch == 0)
				return "no choose rule in the main asm defines the variable " + variable;
			if (nMatch == 1)
				return checkVariables(lastChoose, variable);
			// nMatch > 1
			return "more than one choose rule in the main asm defines the variable " + variable
					+ ". Specify the rule explicitly using \"in r_ruleName\", or "
					+ "\"in r_ruleName(Param1Type,Param2Type,...)\" if it has parameters";
		} else {
			// The pick defines the rule declaration
			// => there must exists, in the defined rule declaration, one Choose rule with a
			// variable that matches with the pick variable
			for (Entry<ChooseRule, String> chooseRule : chooseRules.entrySet()) {
				if (chooseRule.getKey().getVariable().stream().anyMatch(var -> var.getName().equals(variable))
						&& chooseRule.getValue().equals(pick.getRule().replaceAll("\\(|\\,", "_").replace(")", "")))
					return checkVariables(chooseRule.getKey(), variable);
			}
			return "no choose rule in " + pick.getRule() + " defines the variable " + variable;
		}
	}

	/**
	 * check if a Choose Rule uses variables not defined by itself in ranges or in
	 * the guard. If so, return an error message.
	 * 
	 * @param chooseRule     the choose rule
	 * @param pickedVariable the picked variable
	 * @return the error string, null if no error occurs
	 */
	private static String checkVariables(ChooseRule chooseRule, String pickedVariable) {
		// The check is performed using Strings
		AsmetaTermPrinter printer = AsmetaTermPrinter.getAsmetaTermPrinter(false);
		List<String> definedVars = chooseRule.getVariable().stream().map(var -> var.getName())
				.collect(Collectors.toList());
		String guard = printer.visit(chooseRule.getGuard());
		String ranges = "";
		for (Term range : chooseRule.getRanges()) {
			ranges += printer.visit(range);
		}
		for (String var : definedVars) {
			guard = guard.replace(var, "");
			ranges = ranges.replace(var, "");
		}
		if (guard.contains("$"))
			return "variable " + pickedVariable + " matched with a choose rule"
					+ " whose guard contains variables defined outside the choose rule."
					+ " This feature is not supported yet";
		if (ranges.contains("$"))
			return "variable " + pickedVariable + " matched with a choose rule"
					+ " for which at least a range contains variables defined outside the choose rule."
					+ " This feature is not supported yet";
		return null;
	}

	/**
	 * check whether all picks variables and rules are correctly defined (i.e. the
	 * picked value is NOT undef, the variables can be matched with one and only one
	 * choose variable in the asm, rule declaration defined in the pick rules exists
	 * in the asm, and the choose rule does not use variables not defined by iteslf
	 * in ranges and guard)
	 * 
	 * @param allPickStatements the list of all pick rules in the avalla
	 * @param chooseRules       the map with all the choose rule in the asm being
	 *                          validated as key and the name of the macro rule
	 *                          declaration in which are defined as value
	 * @param asm               the asm
	 * @return the message of the first occurrence of an error, null if no errors
	 */
	public static String checkAllPicks(List<Pick> allPickStatements, Map<ChooseRule, String> allChooseRules, Asm asm) {
		for (Pick pick : allPickStatements) {
			String checkValue = checkPickValue(pick);
			if (checkValue != null)
				return checkValue;
			String checkRule = checkPickRule(pick, asm);
			if (checkRule != null)
				return checkRule;
			String checkVariable = checkPickVariable(pick, allChooseRules);
			if (checkVariable != null)
				return checkVariable;
		}
		return null;
	}
	
}
