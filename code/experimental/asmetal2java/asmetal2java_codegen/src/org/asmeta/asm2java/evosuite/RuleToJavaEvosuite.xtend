package org.asmeta.asm2java.evosuite

import org.asmeta.asm2java.RuleToJava
import asmeta.structure.Asm
import org.asmeta.asm2java.config.TranslatorOptions
import asmeta.transitionrules.basictransitionrules.ConditionalRule
import org.asmeta.asm2java.TermToJava
import asmeta.transitionrules.derivedtransitionrules.CaseRule
import asmeta.transitionrules.basictransitionrules.LetRule
import asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule
import asmeta.transitionrules.basictransitionrules.ExtendRule
import org.asmeta.asm2java.DomainToJavaSigDef
import asmeta.transitionrules.basictransitionrules.BlockRule
import asmeta.transitionrules.turbotransitionrules.SeqRule
import org.eclipse.emf.common.util.EList
import asmeta.transitionrules.basictransitionrules.Rule

class RuleToJavaEvosuite extends RuleToJava {
	
	JavaRule currRule;
	
	new(Asm resource, boolean seqBlock, TranslatorOptions options, JavaRule currRule) {
		super(resource, seqBlock, options)
		this.currRule = currRule;
	}
	
	// Method translating the par block
	override String visit(BlockRule object) {
		return '''
		//{ //par
			«new RuleToJavaEvosuite(res,false,options,currRule).printRules(object.getRules(), false)»
		//} //endpar'''
	}

	// Method translating the Seq blocks
	override String visit(SeqRule object) {
		return '''
			//{ //seq
				«new RuleToJavaEvosuite(res,true,options,currRule).printRules(object.rules,true)»
			//} //endseq
		'''
	}

	// Method translating the conditional rules
	override String visit(ConditionalRule object) {
		if (object.getElseRule() === null){
			// TODO: set the branch flag to true
			return '''
				if (Boolean.TRUE.equals(«new TermToJava(res).visit(object.guard)»)){ 
					«currRule.addNewBranch()» = true;
					«new RuleToJavaEvosuite(res,seqBlock,options,currRule).visit(object.thenRule)»
				}
			'''
		} else
			return '''
				if (Boolean.TRUE.equals(«new TermToJava(res).visit(object.getGuard)»)){ 
					«currRule.addNewBranch()» = true;
					«new RuleToJavaEvosuite(res,seqBlock,options,currRule).visit(object.thenRule)»
				} else {
					«currRule.addNewBranch()» = true;
					«new RuleToJavaEvosuite(res,seqBlock,options,currRule).visit(object.elseRule)»
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
					«new RuleToJavaEvosuite(res,seqBlock,options,currRule).visit(object.getCaseBranches.get(i))»
				}''')
			else
				sb.append('''
				else if(«compareTerms(object.getTerm,object.getCaseTerm.get(i))»){
					«currRule.addNewBranch()» = true;
					«new RuleToJavaEvosuite(res,seqBlock,options,currRule).visit(object.getCaseBranches().get(i))»
				}''')
		}
		if (object.getOtherwiseBranch() !== null)
			sb.append('''
				else{ 
					«currRule.addNewBranch()» = true;
				 	«new RuleToJavaEvosuite(res,seqBlock,options,currRule).visit(object.getOtherwiseBranch())»
				}
			''')
		return sb.toString
	}

	override String visit(LetRule object) {
		var let = new StringBuffer
		let.append("{\n");

		for (var int i = 0; i < object.getVariable.size; i++) {
			object.getVariable.get(i).domain.name
			let.
				append('''«object.getVariable.get(i).domain.name + " " + new TermToJava(res).visit(object.getVariable.get(i))» = «new TermToJava(res).visit(object.getInitExpression.get(i))»;
				''')
		}
		let.append('''«new RuleToJavaEvosuite(res,seqBlock,options,currRule).visit(object.getInRule)»
}''')
		return let.toString
	}

	override String visit(IterativeWhileRule object) {
		return '''
		while («new TermToJava(res,false).visit(object.guard)»){
			«new RuleToJavaEvosuite(res,true,options,currRule).visit(object.rule)»
		}'''
	}

	override visit(ExtendRule rule) {
		var string = new StringBuffer
		for (var i = 0; i < rule.boundVar.size; i++)
			string.append(
				new DomainToJavaEvosuiteSigDef(res).visit(rule.extendedDomain) + " " +
					new TermToJava(res).visit(rule.boundVar.get(i)) + " = new " +
					new DomainToJavaEvosuiteSigDef(res).visit(rule.extendedDomain) + "();\n");
		return string.toString + new RuleToJavaEvosuite(res,seqBlock,options,currRule).visit(rule.doRule)

	}
	
	// Method writing the rules called in a block
	override String printRules(EList<Rule> rules, boolean addFire) {
		var StringBuffer sb = new StringBuffer
		for (var int i = 0; i < rules.size(); i++) {
			sb.append(new RuleToJavaEvosuite(res, seqBlock, options, currRule).visit(rules.get(i)))
			if (addFire) {
				sb.append("\nfireUpdateSet();\n")
			}

		}
		return sb.toString()
	}

}
