package org.asmeta.asm2java.evosuite

import org.asmeta.asm2java.RuleToJava
import asmeta.structure.Asm
import org.asmeta.asm2java.main.TranslatorOptions
import asmeta.transitionrules.basictransitionrules.ConditionalRule
import org.asmeta.asm2java.TermToJava
import asmeta.transitionrules.derivedtransitionrules.CaseRule
import asmeta.transitionrules.basictransitionrules.LetRule
import asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule
import asmeta.transitionrules.basictransitionrules.ExtendRule
import org.asmeta.asm2java.DomainToJavaSigDef
import asmeta.transitionrules.basictransitionrules.BlockRule
import asmeta.transitionrules.turbotransitionrules.SeqRule

class RuleToJavaEvosuite extends RuleToJava {
	
	RulesAdder rules;
	
	new(Asm resource, boolean seqBlock, TranslatorOptions options, RulesAdder rules) {
		super(resource, seqBlock, options)
		this.rules = rules
	}
	
	// Method translating the par block
	override String visit(BlockRule object) {
		return '''
		//{ //par
			«new RuleToJavaEvosuite(res,false,options,rules).printRules(object.getRules(), false)»
		//} //endpar'''
	}

	// Method translating the Seq blocks
	override String visit(SeqRule object) {
		return '''
			//{ //seq
				«new RuleToJavaEvosuite(res,true,options,rules).printRules(object.rules,true)»
			//} //endseq
		'''
	}

	// Method translating the conditional rules
	override String visit(ConditionalRule object) {
		if (object.getElseRule() === null)
			return '''
				if (Boolean.TRUE.equals(«new TermToJava(res).visit(object.guard)»)){ 
					«new RuleToJavaEvosuite(res,seqBlock,options,rules).visit(object.thenRule)»
				}
			'''
		else
			return '''
				if (Boolean.TRUE.equals(«new TermToJava(res).visit(object.getGuard)»)){ 
					«new RuleToJavaEvosuite(res,seqBlock,options,rules).visit(object.thenRule)»
				} else {
						«new RuleToJavaEvosuite(res,seqBlock,options,rules).visit(object.elseRule)»
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
					«new RuleToJavaEvosuite(res,seqBlock,options,rules).visit(object.getCaseBranches.get(i))»
				}''')
			else
				sb.append('''
				else if(«compareTerms(object.getTerm,object.getCaseTerm.get(i))»){
					«new RuleToJavaEvosuite(res,seqBlock,options,rules).visit(object.getCaseBranches().get(i))»
				}''')
		}
		if (object.getOtherwiseBranch() !== null)
			sb.append('''
				else{ 
				 	«new RuleToJavaEvosuite(res,seqBlock,options,rules).visit(object.getOtherwiseBranch())»
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
		let.append('''«new RuleToJavaEvosuite(res,seqBlock,options,rules).visit(object.getInRule)»
}''')
		return let.toString
	}

	override String visit(IterativeWhileRule object) {
		return '''
		while («new TermToJava(res,false).visit(object.guard)»){
			«new RuleToJavaEvosuite(res,true,options,rules).visit(object.rule)»
		}'''
	}

	override visit(ExtendRule rule) {
		var string = new StringBuffer
		for (var i = 0; i < rule.boundVar.size; i++)
			string.append(
				new DomainToJavaSigDef(res).visit(rule.extendedDomain) + " " +
					new TermToJava(res).visit(rule.boundVar.get(i)) + " = new " +
					new DomainToJavaSigDef(res).visit(rule.extendedDomain) + "();\n");
		return string.toString + new RuleToJavaEvosuite(res, seqBlock, options, rules).visit(rule.doRule)

	}

}
