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
import org.asmeta.avallaxt.avalla.Command;
import org.asmeta.avallaxt.avalla.Element;
import org.asmeta.avallaxt.avalla.Pick;
import org.asmeta.avallaxt.avalla.Scenario;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

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

	static String checkPick(Pick pick, Map<ChooseRule, String> choseRules) {
		if (pick.getRule() == null) {
			// The pick does not define the rule declaration
			// => there must exists one and only one Choose rule with a variable that
			// matches with the pick variable
			int nMatch = 0;
			for (Entry<ChooseRule, String> chooseRule : choseRules.entrySet()) {
				if (chooseRule.getKey().getVariable().stream().anyMatch(v -> v.getName().equals(pick.getVar())))
					nMatch++;
			}
			if (nMatch != 1) {
				// TODO GESTIONE ERRORE
				return "Errore, nMatch: " + nMatch;
			}
		} else {
			// The pick defines the rule declaration
			// => there must exists, in the defined rule declaration, one Choose rule with a
			// variable that matches with the pick variable
			boolean match = false;
			for (Entry<ChooseRule, String> chooseRule : choseRules.entrySet()) {
				if (chooseRule.getKey().getVariable().stream().anyMatch(var -> var.getName().equals(pick.getVar()))
						&& chooseRule.getValue().equals(pick.getRule())) {
					match = true;
					break;
				}
			}
			if (!match) {
				// TODO GESTIONE ERRORE
				System.err.println("Errore, match: false");
			}

		}
		return null;
	}

	public static void checkAllPicks(Scenario scenario) {
		List<Pick> allPick = new ArrayList<>();
		for (ArrayList<Command> list : allMonitored) {
			allPick.addAll(
					list.stream()
					.filter(x -> x instanceof Pick)
					.map(x -> ((Pick) x))
					.collect(Collectors.toList()));
		}
		for (Pick pick : allPick) {
			checkPick(pick);
		}
	}
}
