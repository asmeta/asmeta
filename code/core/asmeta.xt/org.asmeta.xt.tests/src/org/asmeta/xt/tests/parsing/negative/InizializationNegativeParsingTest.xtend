package org.asmeta.xt.tests.parsing.negative

import com.google.inject.Inject
import org.asmeta.xt.asmetal.Asm
import org.asmeta.xt.asmetal.AsmetalPackage
import org.asmeta.xt.tests.AsmetaLInjectorProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith
import org.asmeta.xt.validation.ErrorCode
import org.asmeta.xt.tests.AsmParseHelper

@RunWith(XtextRunner)
@InjectWith(AsmetaLInjectorProvider)
class InizializationNegativeParsingTest {
	
	@Inject	AsmParseHelper parseHelper
	@Inject extension ValidationTestHelper

	@Test
	def void testInizialization() {
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			signature:
			definitions:
			default init s0:
		''')
		result.assertError(AsmetalPackage.Literals.INITIALIZATION, ErrorCode.INITILIZATION_EMPTY_INIT)	
	}

	@Test
	def void testDomainInizialization() {
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = {0 : 3} 
				domain NumOfCherries = {0 : 5} 
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN_INITIALIZATION, ErrorCode.DOMAIN_INIZIALIZATION__DEFINED_TWICE)	
			
		result = parseHelper.parse('''
			asm prova
			signature:
			definitions:
			default init s0:
				domain NumOfCherries = {0 : 3} 
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN_INITIALIZATION, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = {0 : 3}
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN_INITIALIZATION, ErrorCode.DOMAIN_INIZIALIZATION__IS_STATIC)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = {0 : 3} 
		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = "A"
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN_INITIALIZATION, ErrorCode.DOMAIN_INIZIALIZATION__ILL_FORMED_BODY)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = { 1 : "Z" }
		''')
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__DIFFERENT_DOMAINS)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = { "A" : 5 }
		''')
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__DIFFERENT_DOMAINS)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = { 1, 2, 5.4 }
		''')
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__DIFFERENT_DOMAINS)	
			
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = { "A" : "Z" }
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN_INITIALIZATION, ErrorCode.DOMAIN_INIZIALIZATION__ILL_FORMED_BODY)		
		
		result = parseHelper.parse('''
			asm prova
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = {}
		''')
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__ANY_DOMAIN_NOT_DECLARED)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = {}
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = { 1, 2, 3 }
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = { 1, 2, 2, 3 }
		''')
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__ADDED_TWICE)
	
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = { 1 : 1 }
		''')	
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__ADDED_TWICE)	
	
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = { 1 : 15, 2 }
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = { 1 .. 15, +2 }
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = { 1 .. 15, -2 }
		''')	
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__STEP_NEGATIVE)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = { 1 .. 15, "A" }
		''')	
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__STEP_NAN)	
		
	}
	
	@Test
	def void testFunctionInizialization() {
		var Asm result
	
		result = parseHelper.parse('''
			asm prova
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
			definitions:
			default init s0:
				function position($a in Actors) = LEFT
				function position($a in Actors) = RIGHT				
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_INITIALIZATION, ErrorCode.FUNCTION_INIZIALIZATION__DEFINED_TWICE)	
		
		result = parseHelper.parse('''
			asm prova
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Prod(Actors,Actors) -> Side
			definitions:
			default init s0:
				function position($a in Actors, $a in Actors) = LEFT
				
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_INITIALIZATION, ErrorCode.FUNCTION_INIZIALIZATION__INVALID_VARIABLE)	
		
		result = parseHelper.parse('''
			asm prova
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Prod(Actors,Actors) -> Side
			definitions:
			default init s0:
				function position($a in Actors, $b in Actors) = LEFT
				
		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
			definitions:
			default init s0:
				function position($a in ActorsNone) = LEFT				
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_INITIALIZATION, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND)	
		
		result = parseHelper.parse('''
			asm prova
				import BodyLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Integer -> Side
			definitions:
			default init s0:
				function position($a in Integer) = LEFT				
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_INITIALIZATION, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED)	

		result = parseHelper.parse('''
			asm prova
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
			definitions:
			default init s0:
				function position($a in Actors) = LEFT
				
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_INITIALIZATION, ErrorCode.SIGNATURE__FUNCTION_NOT_FOUND)	
		
		result = parseHelper.parse('''
			asm prova
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				static position: Actors -> Side
			definitions:
			default init s0:
				function position($a in Actors) = LEFT				
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_INITIALIZATION, ErrorCode.FUNCTION_INIZIALIZATION__FUNCTION_NOT_DYNAMIC)	
	
		result = parseHelper.parse('''
			asm prova
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled  position: Actors -> Side
			definitions:
			default init s0:
				function position($a in Actors) = LEFT
			init s1:
				function position($a in Actors) = RIGHT
				
		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled  position: Actors -> Side
			definitions:
			default init s0:
				function position($a in Actors) = "prova"
				
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_INITIALIZATION, ErrorCode.FUNCTION_INIZIALIZATION__ILL_FORMED_BODY)	
	
		
	}
	
	@Test
	def void testAgentInizialization() {
		var Asm result
	
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled age: Person -> Natural
				dynamic controlled alive: Person -> Boolean				
			definitions:
			
				rule r_dead =
					choose $x in {1 .. 100} with true do
						if($x > 95) then
							alive(self) := false
						endif
			
				rule r_lifeRule =
					par
						age(self) := age(self) + 1n
						r_dead[]
					endpar   
			default init s0:
					agent Person:
						r_lifeRule[]		
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Integer
				dynamic controlled age: Person -> Natural
				dynamic controlled alive: Person -> Boolean				
			definitions:
			
				rule r_dead =
					choose $x in {1 .. 100} with true do
						if($x > 95) then
							alive(self) := false
						endif
			
				rule r_lifeRule =
					par
						age(self) := age(self) + 1n
						r_dead[]
					endpar
			default init s0:
					agent Person:
						r_lifeRule[]		
		''')
		result.assertError(AsmetalPackage.Literals.AGENT_INITIALIZATION, ErrorCode.AGENT_INIZIALIZATION__INVALID_DOMAIN)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic controlled age: Person -> Natural
				dynamic controlled alive: Person -> Boolean				
			definitions:
			
				rule r_dead =
					choose $x in {1 .. 100} with true do
						if($x > 95) then
							alive(self) := false
						endif
			
				rule r_lifeRule =
					par
						age(self) := age(self) + 1n
						r_dead[]
					endpar
			default init s0:
					agent Person:
						r_lifeRule[]		
		''')
		result.assertError(AsmetalPackage.Literals.AGENT_INITIALIZATION, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND)	
		
		result = parseHelper.parse('''
			asm prova
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled age: Person -> Natural
				dynamic controlled alive: Person -> Boolean				
			definitions:
			
				rule r_dead =
					choose $x in {1 .. 100} with true do
						if($x > 95) then
							alive(self) := false
						endif
			
				rule r_lifeRule =
					par
						age(self) := age(self) + 1n
						r_dead[]
					endpar
			default init s0:
					agent Person:
						r_lifeRule[]		
		''')
		result.assertError(AsmetalPackage.Literals.AGENT_INITIALIZATION, ErrorCode.AGENT_INIZIALIZATION__AGENT_DOMAIN_NOT_DECLARED)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled age: Person -> Natural
				dynamic controlled alive: Person -> Boolean				
			definitions:
			default init s0:
					agent Person:
						r_lifeRule[]		
		''')
		result.assertError(AsmetalPackage.Literals.AGENT_INITIALIZATION, ErrorCode.AGENT_INIZIALIZATION__PROGRAM_NOT_FOUND)	
	
	}
	
}