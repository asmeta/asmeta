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

@RunWith(XtextRunner)
@InjectWith(AsmetaLInjectorProvider)
class RuleNegativeParsingTest {

	@Inject	ParseHelper<Asm> parseHelper
	@Inject extension ValidationTestHelper

	@Test
	def void testUpdateRule() {
		var Asm result

		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled alive: Person -> Boolean
			definitions:
				rule r_dead =
					choose $x in {1 .. 100} with true do
						if($x > 95) then
							alive(self) := 5
						endif
		''')
		result.assertError(AsmetalPackage.Literals.UPDATE_RULE, ErrorCode.UPDATE_RULE__DOMAINS_NOT_COMPATIBLE)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				static alive: Person -> Boolean
			definitions:
				rule r_dead =
					choose $x in {1 .. 100} with true do
						if($x > 95) then
							alive(self) := false
						endif
		''')
		result.assertError(AsmetalPackage.Literals.UPDATE_RULE, ErrorCode.LOCATION_TERM__NOT_DYNAMIC)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				controlled alive: Person -> Boolean
			definitions:
				rule r_dead =
					choose $x in {1 .. 100} with true do
						if($x > 95) then
							alive(self) := false
						endif
		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic monitored alive: Person -> Boolean
			definitions:
				rule r_dead =
					choose $x in {1 .. 100} with true do
						if($x > 95) then
							alive(self) := false
						endif
		''')
		result.assertError(AsmetalPackage.Literals.UPDATE_RULE, ErrorCode.UPDATE_RULE__INVALID_LOCATION)		
		
	}
	
	@Test
	def void testConditionalRule() {
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled alive: Person -> Boolean
		definitions:
			rule r_dead =
				choose $x in {1 .. 100} with true do
					if($x > 95) then
						alive(self) := false
					endif
		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled alive: Person -> Boolean
		definitions:
			rule r_dead =
				choose $x in {1 .. 100} with true do
					if($x + 95) then
						alive(self) := false
					endif
		''')
		result.assertError(AsmetalPackage.Literals.CONDITIONAL_RULE, ErrorCode.TERM__DOMAIN_GUARD_NOT_BOOLEAN)	

	}
		
	@Test
	def void testLetRule() {
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				abstract domain Philosopher
				domain NumOfCherries subsetof Integer
				controlled cherriesInPlate: Philosopher -> NumOfCherries
				derived leftNeighbour: Philosopher -> Philosopher
			definitions:
				rule r_philoRule($p in Philosopher) =
					if(cherriesInPlate($p) > 0) then
						cherriesInPlate($p) := cherriesInPlate($p) - 1
					else
						let($left=leftNeighbour($p)) in
							if(cherriesInPlate($left) > 0) then
								par
									cherriesInPlate($left) := cherriesInPlate($left) - 1
									cherriesInPlate($p) := cherriesInPlate($p) + 1
								endpar
							endif
						endlet
					endif
		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				abstract domain Philosopher
				domain NumOfCherries subsetof Integer
				controlled cherriesInPlate: Philosopher -> NumOfCherries
				derived leftNeighbour: Philosopher -> Philosopher
			definitions:
				rule r_philoRule($p in Philosopher) =
					if(cherriesInPlate($p) > 0) then
						cherriesInPlate($p) := cherriesInPlate($p) - 1
					else
						let($p=leftNeighbour($p)) in
							if(cherriesInPlate($left) > 0) then
								par
									cherriesInPlate($left) := cherriesInPlate($left) - 1
									cherriesInPlate($p) := cherriesInPlate($p) + 1
								endpar
							endif
						endlet
					endif
		''')
		result.assertError(AsmetalPackage.Literals.LET_RULE, ErrorCode.LET_RULE__VARIABLE_ALREADY_USED)	
		
	}
	
	@Test
	def void testChooseRule() {
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled alive: Person -> Boolean
			definitions:
				rule r_dead =
					choose $x in {1 .. 100} with true do
						if($x > 95) then
							alive(self) := false
						endif
		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled alive: Person -> Boolean
			definitions:
				rule r_dead =
					choose $x in [1 .. 100] with true do
						if($x > 95) then
							alive(self) := false
						endif
		''')
		result.assertError(AsmetalPackage.Literals.CHOOSE_RULE, ErrorCode.CHOOSE_RULE__RANGE_NOT_POWERSET)	
		
		/*result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled alive: Person -> Boolean
			definitions:
				rule r_dead =
					choose $x in {1 .. 100} with true do
						if($x > 95) then
							alive(self) := false
						endif
		''')
		result.assertError(AsmetalPackage.Literals.CHOOSE_RULE, ErrorCode.CHOOSE_RULE__DOMAINS_NOT_COMPATIBLE)*/	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled alive: Person -> Boolean
			definitions:
				rule r_dead =
					let ( $x=5 ) in
						choose $x in {1 .. 100} with true do
							if($x > 95) then
								alive(self) := false
							endif
					endlet
		''')
		result.assertError(AsmetalPackage.Literals.CHOOSE_RULE, ErrorCode.CHOOSE_RULE__VARIABLE_ALREADY_USED)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled alive: Person -> Boolean
			definitions:
				rule r_dead =
					choose $x in {1 .. 100} with 5 + 6 do
						if($x > 95) then
							alive(self) := false
						endif
		''')
		result.assertError(AsmetalPackage.Literals.CHOOSE_RULE, ErrorCode.TERM__DOMAIN_GUARD_NOT_BOOLEAN)	
		
	}
	
	@Test
	def void testForallRule() {
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled alive: Person -> Boolean
			definitions:
				main rule r_Main =
					forall $p in Person with alive($p) do
						program($p) 
		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled alive: Person -> Boolean
				dynamic controlled no_powerset : Person -> Person
			definitions:
				main rule r_Main =
					forall $p in no_powerset with alive($p) do
						program($p) 
		''')
		result.assertError(AsmetalPackage.Literals.FORALL_RULE, ErrorCode.FORALL_RULE__RANGE_NOT_POWERSET)	
		
		// TODO ???
		/*result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled no_powerset : Person -> Powerset(Person)
				dynamic controlled alive: Person -> Boolean
			definitions:
				main rule r_Main =
					forall $p in no_powerset with alive($p) do
						program($p) 
		''')
		result.assertError(AsmetalPackage.Literals.FORALL_RULE, ErrorCode.FORALL_RULE__DOMAINS_NOT_COMPATIBLE)	
		*/
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled alive: Person -> Boolean
				controlled persona : Person
			definitions:
				main rule r_Main =
					let($p=persona) in
						forall $p in Person with alive($p) do
							program($p) 
					endlet
		''')
		result.assertError(AsmetalPackage.Literals.FORALL_RULE, ErrorCode.FORALL_RULE__VARIABLE_ALREADY_USED)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled age: Person -> Integer
			definitions:
				main rule r_Main =
					forall $p in Person with age($p) do
						program($p) 
		''')
		result.assertError(AsmetalPackage.Literals.FORALL_RULE, ErrorCode.TERM__DOMAIN_GUARD_NOT_BOOLEAN)	
		
	}
	
		
	@Test
	def void testExtendRule() {
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				enum domain GenderDomain = {MALE | FEMALE}
				dynamic controlled alive: Person -> Boolean
			definitions:
				rule r_reproduce =
					choose $father in Person with alive($father) do
						extend Person with $child do
							choose $g in GenderDomain with true do
								alive($child) := true

		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				enum domain GenderDomain = {MALE | FEMALE}
				dynamic controlled alive: Person -> Boolean
			definitions:
				rule r_reproduce =
					choose $father in Person with alive($father) do
						extend Person with $father do
							choose $g in GenderDomain with true do
								alive($child) := true

		''')
		result.assertError(AsmetalPackage.Literals.EXTEND_RULE, ErrorCode.EXTENDED_RULE__VARIABLE_ALREADY_USED)	
		
		// the domain to extend must be dynamic, and it must be a concrete domain subsetting an abstract TD or an abstract TD."
		
		// NON CONCRETO
		// 		NO ASTRATTO
		// SE CONCRETO
		//		NO DINAMICO				*
		//		NO TYPEDEF ASTRATTO		*
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Integer
				enum domain GenderDomain = {MALE | FEMALE}
				dynamic controlled alive: Person -> Boolean
			definitions:
				rule r_reproduce =
					choose $father in Person with alive($father) do
						extend Person with $child do
							choose $g in GenderDomain with true do
								alive($child) := true

		''')
		result.assertError(AsmetalPackage.Literals.EXTEND_RULE, ErrorCode.EXTENDED_RULE__INVALID_DOMAIN)	
		
			result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				domain Person subsetof Agent
				enum domain GenderDomain = {MALE | FEMALE}
				dynamic controlled alive: Person -> Boolean
			definitions:
				rule r_reproduce =
					choose $father in Person with alive($father) do
						extend Person with $child do
							choose $g in GenderDomain with true do
								alive($child) := true

		''')
		result.assertError(AsmetalPackage.Literals.EXTEND_RULE, ErrorCode.EXTENDED_RULE__INVALID_DOMAIN)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				enum domain GenderDomain = {MALE | FEMALE}
				dynamic controlled alive: Person -> Boolean
			definitions:
				rule r_reproduce =
					choose $father in Person with alive($father) do
						extend Integer with $child do
							choose $g in GenderDomain with true do
								alive($child) := true

		''')
		result.assertError(AsmetalPackage.Literals.EXTEND_RULE, ErrorCode.EXTENDED_RULE__INVALID_DOMAIN)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary ( Boolean ) 
			signature:
				dynamic domain Person subsetof Agent
				enum domain GenderDomain = {MALE | FEMALE}
				dynamic controlled alive: Person -> Boolean
			definitions:
				rule r_reproduce =
					choose $father in Person with alive($father) do
						extend Person with $child do
							choose $g in GenderDomain with true do
								alive($child) := true

		''')
		result.assertError(AsmetalPackage.Literals.EXTEND_RULE, ErrorCode.EXTENDED_RULE__RESERVE_DOMAIN_NOT_DECLARED)	
		
		
	}
	
	@Test
	def void testCaseRule() {
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain CarryDomain = {GOAT | CABBAGE | WOLF | NONE}
				enum domain Side = {LEFT | RIGHT}
				dynamic monitored carry: CarryDomain
				dynamic controlled position: Actors -> Side
				derived oppositeSide: Side -> Side
			definitions:
			
			rule r_carry($a in Actors) =
				position($a) := oppositeSide(position($a))
			
				rule r_travel =
					switch(carry)
						case GOAT: r_carry[GO]
						case CABBAGE: r_carry[CA]
						case WOLF: r_carry[WO]
						case NONE:
							position(FERRYMAN) := oppositeSide(position(FERRYMAN))
					endswitch

		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain CarryDomain = {GOAT | CABBAGE | WOLF | NONE}
				enum domain Side = {LEFT | RIGHT}
				dynamic monitored carry: CarryDomain
				dynamic controlled position: Actors -> Side
				derived oppositeSide: Side -> Side
			definitions:
			
			rule r_carry($a in Actors) =
				position($a) := oppositeSide(position($a))
			
				rule r_travel =
					switch(carry)
						case GO: r_carry[GO]
						case CABBAGE: r_carry[CA]
						case WOLF: r_carry[WO]
						case NONE:
							position(FERRYMAN) := oppositeSide(position(FERRYMAN))
					endswitch

		''')
		result.assertError(AsmetalPackage.Literals.CASE_RULE, ErrorCode.CASE_RULE__BRANCH_DOMAIN_NOT_COMPATIBLE)	
		
		
	}
	
		
	@Test
	def void testWhileRule() {
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				controlled contrC: Integer
				monitored mon: Integer
				monitored doWhile: Boolean
			definitions:
			
				main rule r_Main =
					if( doWhile ) then 
						while( contrC < mon ) do
							contrC := contrC + 1
					else
						contrC := 0
					endif

		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				controlled contrC: Integer
				monitored mon: Integer
				monitored doWhile: Boolean
			definitions:
			
				main rule r_Main =
					if( doWhile ) then 
						while( contrC + mon ) do
							contrC := contrC + 1
					else
						contrC := 0
					endif

		''')
		result.assertError(AsmetalPackage.Literals.TURBO_DERIVED_RULE, ErrorCode.TERM__DOMAIN_GUARD_NOT_BOOLEAN)	
		
	}
	
}