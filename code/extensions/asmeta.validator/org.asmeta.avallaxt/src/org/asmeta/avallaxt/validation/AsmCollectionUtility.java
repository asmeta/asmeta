package org.asmeta.avallaxt.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.asmeta.parser.ASMParser;

import asmeta.AsmCollection;
import asmeta.definitions.RuleDeclaration;
import asmeta.structure.Asm;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;

public class AsmCollectionUtility {
	
	
	// returns all the choose rules in the ASM collection	
	// a map with choose with the name of the rule declaration 
	/**
	 * 
	 * @param asmCollection
	 * @return 
	 */
	public static Map<ChooseRule, String> getChoseRules(Asm asm){
		Map<ChooseRule, String> allChooseRules = new HashMap<>();		
		for (RuleDeclaration rd : asm.getBodySection().getRuleDeclaration()) {
			if (rd instanceof MacroDeclaration)
				for (Rule r : RuleExtractorFromMacroDecl.getAllContainedRules((MacroDeclaration) rd))
					if (r instanceof ChooseRule)
						allChooseRules.put((ChooseRule) r, rd.getName());
		}
		return allChooseRules;
	}

}
