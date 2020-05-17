package org.asmeta.xt.tests.parsing.positive

import com.google.inject.Inject
import org.asmeta.xt.asmetal.Asm
import org.asmeta.xt.tests.AsmetaLInjectorProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(XtextRunner)
@InjectWith(AsmetaLInjectorProvider)
class InizializationParsingTest {
	
	@Inject	ParseHelper<Asm> parseHelper
	@Inject extension ValidationTestHelper
	
	@Test
	def void testInitialization() {
		var result = parseHelper.parse('''
			asm prova
				
				import StandardLibrary
			
			signature: 
				dynamic domain Prova1 subsetof String
				dynamic domain Prova2 subsetof String
			definitions: 
			default init prova : 
				domain Prova1 = { "a", "b", "c" }
				domain Prova2 = { "a", "b", "c" }
				//function prova1 = 'a'
				//function prova2($a in Prova1, $b in Prova2) = a
				//function Prova = "testo con spazi"
				//agent Agente : r_a[]
		''')
		result.assertNoErrors
	}
	
}

