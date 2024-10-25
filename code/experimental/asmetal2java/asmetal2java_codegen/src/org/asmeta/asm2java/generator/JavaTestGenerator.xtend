package org.asmeta.asm2java.generator

import asmeta.definitions.RuleDeclaration
import asmeta.structure.Asm
import asmeta.transitionrules.basictransitionrules.Rule
import org.asmeta.asm2java.Util
import org.asmeta.asm2java.evosuite.RulesAdder
import org.asmeta.asm2java.evosuite.impl.JavaRuleImpl
import org.asmeta.asm2java.evosuite.RuleToJavaEvosuite
import org.asmeta.asm2java.evosuite.JavaRule

class JavaTestGenerator extends JavaGenerator {
	
	RulesAdder rules;
	
	new(RulesAdder rules){
		super()
		this.rules = rules
	}
	
	override String foo2(RuleDeclaration r, String methodName, Asm asm) {
		var rule = new JavaRuleImpl(methodName)
		var sb = new StringBuffer();
		if (r.arity == 0){
			sb.append('''
				@Override
				void «methodName»(){
					«rule.addNewBranch()» = true;
					«new RuleToJavaEvosuite(asm, false, options, rule).visit(r.ruleBody as Rule)»
				}
				
			''');
			} else {
			sb.append('''
				@Override
				void «methodName» («new Util().adaptRuleParam(r.variable, asm)»){
					«rule.addNewBranch()» = true;
					«new RuleToJavaEvosuite(asm, false, options, rule).visit(r.ruleBody)»
				}
				
			''');
			}

		// initialize the branches flag
		var flagInit = coverBranchesFlagInit(rule);
		
		// add the flag initialization to the top
		sb.insert(0, flagInit)
		
		// add the Rule to the rules Map
		rules.addRule(rule.getName(), rule)
		
		return sb.toString;
		
	}
	
	// initialize the cover branch flags (example: boolean cover_r_main = false;)
	def coverBranchesFlagInit(JavaRule rule){
		val sb = new StringBuffer();
		for(String branch : rule.branches ){
			sb.append('''boolean «branch» = false;''');
			sb.append(System.lineSeparator);
		}
		return sb.toString();
	}
	
}