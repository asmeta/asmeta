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
class ChooseBoolean {
	
	@Inject	ParseHelper<Asm> parseHelper
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
		Assert.assertNotNull(result.mainrule)
	}
	
	
}