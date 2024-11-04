package org.asmeta.asm2java.evosuite

import org.asmeta.asm2java.translator.RuleToJava
import asmeta.structure.Asm
import org.asmeta.asm2java.config.TranslatorOptions
import asmeta.transitionrules.basictransitionrules.ConditionalRule
import org.asmeta.asm2java.translator.TermToJava
import asmeta.transitionrules.derivedtransitionrules.CaseRule

class RuleToJavaEvosuite extends RuleToJava {
	
	JavaRule currRule;
	
	new(Asm resource, boolean seqBlock, TranslatorOptions options, JavaRule currRule) {
		super(resource, seqBlock, options)
		this.currRule = currRule;
	}
	
	/**
	 * Create an instance of the {@code RuleToJava} object with the current JavaRule.
	 */
	override RuleToJava createRuleToJava(Asm resource, boolean seqBlock, TranslatorOptions translatorOptions) {
		new RuleToJavaEvosuite(resource, seqBlock, translatorOptions, this.currRule)
	}
	
	/**
	 * Create an instance of the {@code DomainToJavaSigDef} object.
	 */
	override DomainToJavaEvosuiteSigDef createDomainToJavaSigDef(Asm resource) {
		new DomainToJavaEvosuiteSigDef(resource)
	}

	// Method translating the conditional rules
	override String visit(ConditionalRule object) {
		if (object.getElseRule() === null){
			return '''
				if (Boolean.TRUE.equals(«new TermToJava(res).visit(object.guard)»)){ 
					«currRule.addNewBranch()» = true;
					«createRuleToJava(res,seqBlock,options).visit(object.thenRule)»
				}
			'''
		} else
			return '''
				if (Boolean.TRUE.equals(«new TermToJava(res).visit(object.getGuard)»)){ 
					«currRule.addNewBranch()» = true;
					«createRuleToJava(res,seqBlock,options).visit(object.thenRule)»
				} else {
					«currRule.addNewBranch()» = true;
					«createRuleToJava(res,seqBlock,options).visit(object.elseRule)»
				}
			'''
	}

	// Method translating the CaseRules
	override String visit(CaseRule object) {
		var StringBuffer sb = new StringBuffer
		for (var i = 0; i < object.getCaseBranches().size; i++) {
			if (i == 0)
				sb.append('''
				if(«compareTerms(object.getTerm,object.getCaseTerm.get(i))»){
					«currRule.addNewBranch()» = true;
					«createRuleToJava(res,seqBlock,options).visit(object.getCaseBranches.get(i))»
				}''')
			else
				sb.append('''
				else if(«compareTerms(object.getTerm,object.getCaseTerm.get(i))»){
					«currRule.addNewBranch()» = true;
					«createRuleToJava(res,seqBlock,options).visit(object.getCaseBranches().get(i))»
				}''')
		}
		if (object.getOtherwiseBranch() !== null)
			sb.append('''
				else{ 
					«currRule.addNewBranch()» = true;
				 	«createRuleToJava(res,seqBlock,options).visit(object.getOtherwiseBranch())»
				}
			''')
		return sb.toString
	}


}