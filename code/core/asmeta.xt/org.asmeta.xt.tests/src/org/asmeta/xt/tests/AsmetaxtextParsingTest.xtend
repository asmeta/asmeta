/*
 * generated by Xtext 2.13.0
 */
package org.asmeta.xt.tests

import com.google.inject.Inject
import org.asmeta.xt.asmetal.Asm
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(XtextRunner)
@InjectWith(AsmetaLInjectorProvider)
class AsmetaxtextParsingTest {
	@Inject
	ParseHelper<Asm> parseHelper
	
	@Test
	def void loadModel() {
		/*val result = parseHelper.parse('''
			asm machine
			signature:
			definitions:
		''')*/
		val result = parseHelper.parse('''
			asm machine
			signature:
			definitions:
				rule r_moveCard($i1 in Integer, $i2 in Integer) =
						skip
		''')
		Assert.assertNotNull(result)
		val errors = result.eResource.errors
		Assert.assertTrue('''Unexpected errors: «errors.join(", ")»''', errors.isEmpty)
	}
	
	@Test
	def void booleanInvariant() {
		val result = parseHelper.parse('''
			asm machine
			signature:
			definitions:
				
				rule r_moveCard($i1 in Integer, $i2 in Integer) =
						skip
						
				invariant inv_neverNeg over Boolean: balance >= 0
		''')
		Assert.assertNotNull(result)
		val errors = result.eResource.errors
		Assert.assertTrue('''Unexpected errors: «errors.join(", ")»''', errors.isEmpty)
	}
	
}
