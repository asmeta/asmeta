package org.asmeta.xt.tests.parsing.positive

import org.junit.runner.RunWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.InjectWith
import org.asmeta.xt.tests.AsmetaLInjectorProvider
import com.google.inject.Inject
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.asmeta.xt.asmetal.Asm
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import org.asmeta.xt.tests.AsmParseHelper
import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xtext.testing.extensions.InjectionExtension

@ExtendWith(InjectionExtension)
@InjectWith(AsmetaLInjectorProvider)
class ChooseBoolean {
	
	@Inject	AsmParseHelper parseHelper
	@Inject extension ValidationTestHelper

	@Test
	def void testChooseBoolean() {
		var result = parseHelper.parse('''
			asm blankpage
			import STDL/StandardLibrary
			signature: 
			definitions:
			main rule r_main = choose $sendMsg in Boolean with true do //skip
							if $sendMsg then skip endif 
		''')
		result.assertNoErrors		
		// main rule test
		Assertions.assertNotNull(result.mainrule)
	}
	@Test
	def void testChooseBooleanNoGuard() {
		var result = parseHelper.parse('''
			asm blankpage
			import STDL/StandardLibrary
			signature: 
			definitions:
			main rule r_main = choose $sendMsg in Boolean do //skip
							if $sendMsg then skip endif 
		''')
		result.assertNoErrors		
		// main rule test
		Assertions.assertNotNull(result.mainrule)
	}
	
	
}