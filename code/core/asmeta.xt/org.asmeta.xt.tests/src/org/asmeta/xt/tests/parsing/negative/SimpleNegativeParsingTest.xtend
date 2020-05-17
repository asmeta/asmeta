package org.asmeta.xt.tests.parsing.negative

import com.google.inject.Inject
import org.asmeta.xt.asmetal.Asm
import org.asmeta.xt.asmetal.AsmetalPackage
import org.asmeta.xt.tests.AsmetaLInjectorProvider
import org.asmeta.xt.validation.ErrorCode
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

/**
 * A class for validation test (negative test)
 */

@RunWith(XtextRunner)
@InjectWith(AsmetaLInjectorProvider)
class SimpleNegativeParsingTest {
	
	@Inject	ParseHelper<Asm> parseHelper
	@Inject extension ValidationTestHelper
	
	@Test
	def void blanktest() { }

	@Test
	def void testAsm() {
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			signature:
				
				domain NumOfCherries subsetof Integer
				abstract domain Philosopher
				
				controlled cherriesInPlate: Philosopher -> NumOfCherries
				
			definitions:
			
				rule r_philoRule($p in Philosopher) =
						if(cherriesInPlate($p) > 0) then
							cherriesInPlate($p) := cherriesInPlate($p) - 1
						endif
				
				main rule r_main($p in Philosopher) = 
					choose $p in Philosopher with true do
						r_philoRule[$p]
						
			default init prova : 
		''')
		//result.assertNoErrors
		result.assertError(AsmetalPackage.Literals.ASM, ErrorCode.ASM__INVALID_MAINRULE_ARITY)
		
		result = parseHelper.parse('''
			module prova
			signature:
				
				domain NumOfCherries subsetof Integer
				abstract domain Philosopher
				
				controlled cherriesInPlate: Philosopher -> NumOfCherries
				
			definitions:
			
				rule r_philoRule($p in Philosopher) =
						cherriesInPlate($p) := cherriesInPlate($p) - 1
				
				main rule r_main = 
					choose $p in Prova with true do
						r_philoRule[$p]
						
		''')
		result.assertWarning(AsmetalPackage.Literals.ASM, ErrorCode.ASM__MODULE_MAINRULE)
		
		result = parseHelper.parse('''
			module prova
			signature:
				
				domain NumOfCherries subsetof Integer
				abstract domain Philosopher
				
				controlled cherriesInPlate: Philosopher -> NumOfCherries
				
			definitions:
			
			default init s0:
				function cherriesInPlate($p in Philosopher) = 3
		''')
		result.assertWarning(AsmetalPackage.Literals.ASM, ErrorCode.ASM__MODULE_DEF_INITIAL_STATE)
		

	}
}