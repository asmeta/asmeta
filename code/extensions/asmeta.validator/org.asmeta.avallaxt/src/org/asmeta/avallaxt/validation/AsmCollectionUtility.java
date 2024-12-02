package org.asmeta.avallaxt.validation;

import java.util.ArrayList;
import java.util.List;

import org.asmeta.parser.ASMParser;

import asmeta.AsmCollection;
import asmeta.definitions.RuleDeclaration;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.MacroDeclaration;
import asmeta.transitionrules.basictransitionrules.Rule;

public class AsmCollectionUtility {
	
	
	// returns all the choose rules in the ASM collection
	
	getChoseRules(AsmCollection asmCollection){
		asm = asmCollection.getMain();
		List<> allChooseRules = new ArrayList<>();
	for (RuleDeclaration rd : asm.getBodySection().getRuleDeclaration()) {
		if (rd instanceof MacroDeclaration)
			for (Rule r: RuleExtractorFromMacroDecl.getAllContainedRules((MacroDeclaration)rd))
				if (r instanceof ChooseRule)
					allChooseRules.add((ChooseRule) r);
	}

}
