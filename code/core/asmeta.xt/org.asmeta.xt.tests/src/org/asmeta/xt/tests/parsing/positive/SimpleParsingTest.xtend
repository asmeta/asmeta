package org.asmeta.xt.tests.parsing.positive

import org.junit.runner.RunWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.InjectWith
import org.asmeta.xt.tests.AsmetaLInjectorProvider
import com.google.inject.Inject
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.asmeta.xt.asmetal.Asm
import org.junit.Test
import org.junit.Assert

@RunWith(XtextRunner)
@InjectWith(AsmetaLInjectorProvider)
class SimpleParsingTest {
	
	@Inject	ParseHelper<Asm> parseHelper
	@Inject extension ValidationTestHelper

	@Test
	def void testBlankAsm() {
		var result = parseHelper.parse('''
			asm blankpage
			signature: 
			definitions: 
		''')
		result.assertNoErrors
		// asm test
		Assert.assertEquals( false, result.isAsynchr )													
		Assert.assertEquals( "blankpage", result.name)														
		// header test																			
		Assert.assertEquals( 0, result.headerSection.importClause.size )							
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
		// TODO da sistemare
		Assert.assertEquals( null, result.mainrule)		
		
		// initialstate test
		Assert.assertEquals( 0, result.initialState.size )
	}	
	
}