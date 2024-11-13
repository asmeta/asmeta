package org.asmeta.asm2java.evosuite

import org.asmeta.asm2java.RuleToJava
import asmeta.structure.Asm
import org.asmeta.asm2java.main.TranslatorOptions
import asmeta.transitionrules.basictransitionrules.ConditionalRule

class RuleToJavaEvosuite extends RuleToJava {
	
	new(Asm resource, boolean seqBlock, TranslatorOptions options) {
		super(resource, seqBlock, options)
	}
	
	
	override visit(ConditionalRule object) {
		// set the flag to true
		super.visit(object)
	}
	
}