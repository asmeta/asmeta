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
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(XtextRunner)
@InjectWith(AsmetaLInjectorProvider)
class RuleTest {
	
	@Inject	ParseHelper<Asm> parseHelper
	@Inject extension ValidationTestHelper

	@Test
	def void testUpdateRule() {
		var result = parseHelper.parse('''
			asm temp
			
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
		Assert.assertEquals( false, result.isAsynchr )													
		Assert.assertEquals( "temp", result.name)														
		// header test																			
		Assert.assertEquals( 1, result.headerSection.importClause.size )							
		Assert.assertEquals( null, result.headerSection.exportClause)
		Assert.assertEquals( 0, result.headerSection.signature.domain.size)	
		Assert.assertEquals( 0, result.headerSection.signature.domain.size)			
		// body test
		// TODO va bene che non sia istanziato ma sia null?
		Assert.assertEquals( 0, result.bodySection.domainDefinition.size)
		Assert.assertEquals( 0, result.bodySection.functionDefinition.size)
		Assert.assertEquals( 0, result.bodySection.ruleDeclaration.size)
		Assert.assertEquals( 0, result.bodySection.invariantConstraint.size)
		Assert.assertEquals( 0, result.bodySection.fairnessConstraint.size)
		Assert.assertEquals( 0, result.bodySection.property.size)
		
		// main rule test
		Assert.assertTrue(result.mainrule instanceof MacroDeclaration)
		var rule = (result.mainrule as MacroDeclaration).ruleBody
		Assert.assertTrue(rule instanceof ConditionalRule)
		var update = (rule as ConditionalRule).thenRule		
		Assert.assertTrue(update instanceof UpdateRule)
		
	}
	
	
}