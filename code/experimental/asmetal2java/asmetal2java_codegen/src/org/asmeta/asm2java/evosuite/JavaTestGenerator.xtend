package org.asmeta.asm2java.evosuite

import org.asmeta.asm2java.main.JavaGenerator
import asmeta.definitions.RuleDeclaration
import asmeta.structure.Asm
import asmeta.transitionrules.basictransitionrules.Rule
import org.asmeta.asm2java.Util

class JavaTestGenerator extends JavaGenerator {
	
	RulesAdder rules;
	
	new(RulesAdder rules){
		super()
		this.rules = rules
	}
	
	override String foo2(RuleDeclaration r, String methodName, Asm asm) {
		if (r.arity == 0)
			return ('''
				@Override
				void «methodName»(){
					«new RuleToJavaEvosuite(asm, false, options, rules).visit(r.ruleBody as Rule)»
				}
				
			''')
		else
			return ( '''
				@Override
				void «methodName» («new Util().adaptRuleParam(r.variable, asm)»){
					«new RuleToJavaEvosuite(asm, false, options, rules).visit(r.ruleBody)»
				}
				
			''')

	}
	
}