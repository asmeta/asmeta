package org.asmeta.avallaxt.validation;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.asmeta.avallaxt.avalla.Block;
import org.asmeta.avallaxt.avalla.Element;
import org.asmeta.avallaxt.avalla.Pick;
import org.asmeta.avallaxt.avalla.Scenario;
import org.asmeta.simulator.InvalidValueException;
import org.asmeta.simulator.value.SetValue;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.structure.Asm;
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
	 * check whether rule declaration defined in the pick rule exists in the asm
	 * 
	 * @param pick the pick rule
	 * @param asm  the asm
	 * @return the error message, null if there is no error
	 */
	static String checkPickRule(Pick pick, Asm asm) {
		if (pick.getRule() != null && !asm.getBodySection().getRuleDeclaration().stream()
				.anyMatch(rd -> rd.getName().equals(pick.getRule())))
			return pick.getRule() + " is not defined in the main asm";
		else
			return null;
	}

	/**
	 * check whether the pick variable can be matched with one and only one choose
	 * variable in the asm)
	 * 
	 * @param pick        the pick rule
	 * @param chooseRules the map with all the choose rule in the asm being
	 *                    validated as key and the name of the macro rule
	 *                    declaration in which are defined as value
	 * @return the error message, null if there is no error
	 */
	static String checkPickVariable(Pick pick, Map<ChooseRule, String> chooseRules) {
		if (pick.getRule() == null) {
			// The pick does not define the rule declaration
			// => there must exists one and only one Choose rule with a variable that
			// matches with the pick variable
			int nMatch = 0;
			for (Entry<ChooseRule, String> chooseRule : chooseRules.entrySet())
				if (chooseRule.getKey().getVariable().stream().anyMatch(v -> v.getName().equals(pick.getVar())))
					nMatch++;
			if (nMatch == 0)
				return "no choose rule defines the variable " + pick.getVar();
			if (nMatch > 1)
				return "more than one choose rule defines the variable " + pick.getVar()
						+ ", specify the rule name explicitly by adding in r_ruleName";
			return null;
		} else {
			// The pick defines the rule declaration
			// => there must exists, in the defined rule declaration, one Choose rule with a
			// variable that matches with the pick variable
			for (Entry<ChooseRule, String> chooseRule : chooseRules.entrySet())
				if (chooseRule.getKey().getVariable().stream().anyMatch(var -> var.getName().equals(pick.getVar()))
						&& chooseRule.getValue().equals(pick.getRule()))
					return null;
			return "no choose rule in " + pick.getRule() + " defines the variable " + pick.getVar();
		}
	}

	/**
	 * check whether all picks variables and rules are correctly defined (i.e. the variables
	 * can be matched with one and only one choose variable in the asm and rule
	 * declaration defined in the pick rules exists in the asm)
	 * 
	 * @param allPickStatements the list of all pick rules in the avalla
	 * @param chooseRules  the map with all the choose rule in the asm being
	 *                     validated as key and the name of the macro rule
	 *                     declaration in which are defined as value
	 * @param asm          the asm
	 * @return the message of the first occurrence of an error, null if no errors
	 */
	public static String checkAllPicks(List<Pick> allPickStatements, Map<ChooseRule, String> allChooseRules, Asm asm) {
		for (Pick pick : allPickStatements) {
			String checkRule = checkPickRule(pick, asm);
			if (checkRule != null)
				return checkRule;
			String checkValue = checkPickVariable(pick, allChooseRules);
			if (checkValue != null)
				return checkValue;
		}
		return null;
	}
}
