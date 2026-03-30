package org.asmeta.xt.tests.parsing.positive

import com.google.inject.Inject
import org.asmeta.xt.asmetal.Asm
import org.asmeta.xt.asmetal.ConditionalRule
import org.asmeta.xt.asmetal.MacroDeclaration
import org.asmeta.xt.asmetal.UpdateRule
import org.asmeta.xt.tests.AsmetaLInjectorProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xtext.testing.extensions.InjectionExtension

@ExtendWith(InjectionExtension)
@InjectWith(AsmetaLInjectorProvider)
class RuleTest {
	
	@Inject	ParseHelper<Asm> parseHelper
	@Inject extension ValidationTestHelper

	@Test
	def void testUpdateRule() {
		var result = parseHelper.parse('''
			asm __synthetic0
			
			import STDL/StandardLibrary
			
			signature:
				controlled a: Integer
				monitored b: Integer    			
			definitions:    

				main rule r_Main = 
					if b = 10 then 
						a:= 20
					endif
			
			default init s0:
				function a = 0
		''')
		result.assertNoErrors
		// asm test
		Assertions.assertEquals( false, result.isAsynchr )													
		Assertions.assertEquals( "__synthetic0", result.name)														
		// header test																			
		Assertions.assertEquals( 1, result.headerSection.importClause.size )							
		Assertions.assertEquals( null, result.headerSection.exportClause)
		Assertions.assertEquals( 0, result.headerSection.signature.domain.size)	
		Assertions.assertEquals( 0, result.headerSection.signature.domain.size)			
		// body test
		// TODO va bene che non sia istanziato ma sia null?
		Assertions.assertEquals( 0, result.bodySection.domainDefinition.size)
		Assertions.assertEquals( 0, result.bodySection.functionDefinition.size)
		Assertions.assertEquals( 0, result.bodySection.ruleDeclaration.size)
		Assertions.assertEquals( 0, result.bodySection.invariantConstraint.size)
		Assertions.assertEquals( 0, result.bodySection.fairnessConstraint.size)
		Assertions.assertEquals( 0, result.bodySection.property.size)
		
		// main rule test
		Assertions.assertTrue(result.mainrule instanceof MacroDeclaration)
		var rule = (result.mainrule as MacroDeclaration).ruleBody
		Assertions.assertTrue(rule instanceof ConditionalRule)
		var update = (rule as ConditionalRule).thenRule		
		Assertions.assertTrue(update instanceof UpdateRule)
		
	}
	
	
}