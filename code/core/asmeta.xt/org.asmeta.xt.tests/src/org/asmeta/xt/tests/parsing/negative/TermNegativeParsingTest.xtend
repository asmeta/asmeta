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
class TermNegativeParsingTest {
	
	@Inject	AsmParseHelper parseHelper
	@Inject extension ValidationTestHelper

	@Test
	def void testOperation() {
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			import TermLibrary
			signature:
				abstract domain Philosopher
				domain NumOfCherries subsetof Integer
				controlled cherriesInPlate: Philosopher -> NumOfCherries	
			definitions:
			default init s0:
				function cherriesInPlate($p in Philosopher) = 3 + 5
		''')
		result.assertError(AsmetalPackage.Literals.BINARY_OPERATION, ErrorCode.SIGNATURE__FUNCTION_NOT_FOUND)
				
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			import CTLLibrary
			signature:
				abstract domain Philosopher
				domain NumOfCherries subsetof Integer
				controlled cherriesInPlate: Philosopher -> NumOfCherries
				static philo1: Philosopher
				static philo2: Philosopher
				static philo3: Philosopher
				static philo4: Philosopher
				static philo5: Philosopher
			definitions:
				CTLSPEC ag(cherriesInPlate(philo1) + cherriesInPlate(philo2) + cherriesInPlate(philo3) +
								cherriesInPlate(philo4) + cherriesInPlate(philo5) <= 15)
			default init s0:
				function cherriesInPlate($p in Philosopher) = 3
		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				abstract domain Philosopher
				domain NumOfCherries subsetof Integer
				controlled cherriesInPlate: Philosopher -> NumOfCherries
				static philo1: Philosopher
				static philo2: Philosopher
				static philo3: Philosopher
				static philo4: Philosopher
				static philo5: Philosopher
			definitions:
				CTLSPEC ( "a" + 2 ) > 5
			default init s0:
				function cherriesInPlate($p in Philosopher) = 3
		''')
		result.assertError(AsmetalPackage.Literals.BINARY_OPERATION, ErrorCode.SIGNATURE__FUNCTION_NOT_FOUND)	
		
		result = parseHelper.parse('''
			asm prova
			signature:
				static alwaysFalse: Boolean
			definitions:
				CTLSPEC not( alwaysFalse )
		''')
		result.assertError(AsmetalPackage.Literals.EXPRESSION, ErrorCode.SIGNATURE__FUNCTION_NOT_FOUND)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				abstract domain Philosopher
				domain NumOfCherries subsetof Integer
				controlled cherriesInPlate: Philosopher -> NumOfCherries
				static philo1: Philosopher
				static philo2: Philosopher
				static philo3: Philosopher
				static philo4: Philosopher
				static philo5: Philosopher
			definitions:
				CTLSPEC not( "a" )
			default init s0:
				function cherriesInPlate($p in Philosopher) = 3
		''')
		result.assertError(AsmetalPackage.Literals.EXPRESSION, ErrorCode.SIGNATURE__FUNCTION_NOT_FOUND)	
							
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			import CTLLibrary
			signature:
				abstract domain Philosopher
				domain NumOfCherries subsetof Integer
				controlled cherriesInPlate: Philosopher -> NumOfCherries
				static philo1: Philosopher
				static philo2: Philosopher
				static philo3: Philosopher
				static philo4: Philosopher
				static philo5: Philosopher
			definitions:
				CTLSPEC ag(cherriesInPlateERROR(philo1) + cherriesInPlate(philo2) + cherriesInPlate(philo3) +
								cherriesInPlate(philo4) + cherriesInPlate(philo5) <= 15)
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_TERM, ErrorCode.SIGNATURE__FUNCTION_NOT_FOUND)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			import CTLLibrary
			signature:
				abstract domain Philosopher
				domain NumOfCherries subsetof Integer
				controlled cherriesInPlate: Philosopher -> NumOfCherries
				static philo1: Philosopher
				static philo2: Philosopher
				static philo3: Philosopher
				static philo4: Philosopher
				static philo5: Philosopher
			definitions:
				CTLSPEC ag( cherriesInPlateERROR <= 15)
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_TERM, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			import CTLLibrary
			signature:
				abstract domain Philosopher
				domain NumOfCherries subsetof Integer
				controlled cherriesInPlate: Philosopher -> NumOfCherries
				static philo1: Philosopher
				static philo2: Philosopher
				static philo3: Philosopher
				static philo4: Philosopher
				static philo5: Philosopher
			definitions:
				CTLSPEC ag(cherriesInPlate(philo1, philo2) <= 15)
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_TERM, ErrorCode.FUNCTION_TERM__INVALID_ARITY_FOR_N_ARITY)	
	
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			import CTLLibrary
			signature:
				abstract domain Philosopher
				domain NumOfCherries subsetof Integer
				controlled cherriesInPlate: Philosopher -> NumOfCherries
				static philo1: Philosopher
				static philo2: Philosopher
				static philo3: Philosopher
				static philo4: Philosopher
				static philo5: Philosopher
			definitions:
				CTLSPEC ag(cherriesInPlate( philo1(philo2) ) <= 15)
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_TERM, ErrorCode.FUNCTION_TERM__INVALID_ARITY_FOR_ZERO_ARITY)	
			
	}
	
	@Test
	def void testEnum() {
		
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Side = {LEFT | RIGHT}
				derived oppositeSide: Side -> Side
			
			definitions:
			
				function oppositeSide($s in Side) =
					if($s = LEFT) then
						RIGHT
					else
						LEFT
					endif
		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Side = {LEFT | RIGHT}
				derived oppositeSide: Side -> Side
			
			definitions:
			
				function oppositeSide($s in Side) =
					if($s = LEFT) then
						RIGHTERROR
					else
						LEFT
					endif
		''')
		result.assertError(AsmetalPackage.Literals.ENUM_TERM, ErrorCode.ENUM_TERM__SYMBOL_NOT_FOUND)	

	}
	
	@Test
	def void testConditionalTerm() {
		
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Side = {LEFT | RIGHT}
				derived oppositeSide: Side -> Side
			
			definitions:
			
				function oppositeSide($s in Side) =
					if($s = LEFT) then
						RIGHT
					endif
		''')
		result.assertNoErrors
	
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Side = {LEFT | RIGHT}
				derived oppositeSide: Side -> Side
			
			definitions:
			
				function oppositeSide($s in Side) =
					if($s = LEFT) then
						3
					else
						LEFT
					endif
		''')
		result.assertError(AsmetalPackage.Literals.CONDITIONAL_TERM, ErrorCode.CONDITIONAL_TERM__IF_ELSE_DOMAIN_NOT_COMPATIBLE)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Side = {LEFT | RIGHT}
				derived oppositeSide: Side -> Side
			
			definitions:
			
				function oppositeSide($s in Side) =
					if($s = LEFT) then
						RIGHT
					else
						3
					endif
		''')
		result.assertError(AsmetalPackage.Literals.CONDITIONAL_TERM, ErrorCode.CONDITIONAL_TERM__IF_ELSE_DOMAIN_NOT_COMPATIBLE)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Side = {LEFT | RIGHT}
				derived oppositeSide: Side -> Side
			
			definitions:
			
				function oppositeSide($s in Side) =
					if($s) then
						RIGHT
					else
						LEFT
					endif
		''')
		result.assertError(AsmetalPackage.Literals.CONDITIONAL_TERM, ErrorCode.TERM__DOMAIN_GUARD_NOT_BOOLEAN)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Side = {LEFT | RIGHT}
				derived oppositeSide: Side -> Side
			
			definitions:
			
				function oppositeSide($s in Side) =
					if(LEFT) then
						RIGHT
					else
						LEFT
					endif
		''')
		result.assertError(AsmetalPackage.Literals.CONDITIONAL_TERM, ErrorCode.TERM__DOMAIN_GUARD_NOT_BOOLEAN)	

	}
	
	@Test
	def void testCaseTerm() {
		
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				abstract domain Philosopher
				derived leftNeighbour: Philosopher -> Philosopher
			
				static philo1: Philosopher
				static philo2: Philosopher
				static philo3: Philosopher
				static philo4: Philosopher
				static philo5: Philosopher
			
			definitions:
			
				function leftNeighbour($p in Philosopher) =
					switch($p)
						case philo1: philo5
						case philo2: philo1
						case philo3: philo2
						case philo4: philo3
						case philo5: philo4
					endswitch
		''')
		result.assertNoErrors
						
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				abstract domain Philosopher
				abstract domain PhilosopherOther
				
				derived leftNeighbour: Philosopher -> Philosopher
			
				static philo1: Philosopher
				static philo2: Philosopher
				static philo3: Philosopher
				static philo4: Philosopher
				static philo5: Philosopher
				
				static other : PhilosopherOther
			
			definitions:
			
				function leftNeighbour($p in Philosopher) =
					switch($p)
						case philo1: philo5
						case philo2: philo1
						case philo3: philo2
						case philo4: other
						case philo5: philo4
					endswitch
		''')
		result.assertError(AsmetalPackage.Literals.CASE_TERM, ErrorCode.CASE_TERM__RESULT_TERM_DOMAIN_NOT_COMPATIBLE)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				abstract domain Philosopher
				abstract domain PhilosopherOther
				
				derived leftNeighbour: Philosopher -> Philosopher
			
				static philo1: Philosopher
				static philo2: Philosopher
				static philo3: Philosopher
				static philo4: Philosopher
				static philo5: Philosopher
				
				static other : PhilosopherOther
			
			definitions:
			
				function leftNeighbour($p in Philosopher) =
					switch($p)
						case philo1: philo5
						case philo2: philo1
						case philo3: philo2
						case philo4: philo3
						case philo5: philo4
						otherwise other
					endswitch
		''')
		result.assertError(AsmetalPackage.Literals.CASE_TERM, ErrorCode.CASE_TERM__OTHERWISE_DOMAIN_NOT_COMPATIBLE)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				abstract domain Philosopher
				abstract domain PhilosopherOther
				derived leftNeighbour: Philosopher -> Philosopher
			
				static philo1: Philosopher
				static philo2: Philosopher
				static philo3: Philosopher
				static philo4: Philosopher
				static philo5: Philosopher
				
				static other : PhilosopherOther
			
			definitions:
			
				function leftNeighbour($p in Philosopher) =
					switch($p)
						case other: philo5
						case philo2: philo1
						case philo3: philo2
						case philo4: philo3
						case philo5: philo4
					endswitch
		''')
		result.assertError(AsmetalPackage.Literals.CASE_TERM, ErrorCode.CASE_TERM__COMPARED_AND_COMPARING_DOMAIN_NOT_COMPATIBLE)	

	}

	
	@Test
	def void testRuleAsTerm() {
	
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled alive: Person -> Boolean
				dynamic controlled myRule : Rule 
			definitions:
				rule r_dead =
					choose $x in {1 .. 100} with true do
						if($x > 95) then
							alive(self) := false
						endif
			default init s0:
				function alive($p in Person) = true
				function myRule = <<r_dead>>
		''')
		result.assertNoErrors
		
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				dynamic domain Person subsetof Agent
				dynamic controlled alive: Person -> Boolean
				dynamic controlled myRule : Rule 
			definitions:
				rule r_dead =
					choose $x in {1 .. 100} with true do
						if($x > 95) then
							alive(self) := false
						endif
			default init s0:
				function alive($p in Person) = true
				function myRule = <<r_deadddddd>>
		''')
		result.assertError(AsmetalPackage.Literals.RULE_AS_TERM, ErrorCode.BODY__RULE_NOT_FOUND)	

	}

		
	@Test
	def void testSetTerm() {
		
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = { 1 .. "Z" }
		''')
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__DIFFERENT_DOMAINS)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = { "A" .. 5 }
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
				domain NumOfCherries = { "A" .. "Z" }
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
				domain NumOfCherries = { 1 .. 1 }
		''')	
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__ADDED_TWICE)	
	
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
			default init s0:
				domain NumOfCherries = { 1 .. 15, 2 }
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
	def void testBagTerm() {
	

		var Asm result
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled bag_function : Bag(Integer)
			definitions:
			default init s0:
				function bag_function = < 1 .. "Z" >
		''')
		result.assertError(AsmetalPackage.Literals.BAG_TERM, ErrorCode.BAG_TERM__DIFFERENT_DOMAINS)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled bag_function : Bag(Integer)
			definitions:
			default init s0:
				function bag_function = < "A" .. 5 >
		''')
		result.assertError(AsmetalPackage.Literals.BAG_TERM, ErrorCode.BAG_TERM__DIFFERENT_DOMAINS)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled bag_function : Bag(Integer)
			definitions:
			default init s0:
				function bag_function = < 1, 2, 5.4 >
		''')
		result.assertError(AsmetalPackage.Literals.BAG_TERM, ErrorCode.BAG_TERM__DIFFERENT_DOMAINS)	
			
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled bag_function : Bag(Integer)
			definitions:
			default init s0:
				function bag_function = < "A" .. "Z" >
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_INITIALIZATION, ErrorCode.FUNCTION_INIZIALIZATION__ILL_FORMED_BODY)		
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary ( Integer )
			signature:
				dynamic controlled bag_function : Bag(Integer)
			definitions:
			default init s0:
				function bag_function = <>
		''')
		result.assertError(AsmetalPackage.Literals.BAG_TERM, ErrorCode.BAG_TERM__ANY_DOMAIN_NOT_DECLARED)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled bag_function : Bag(Integer)
			definitions:
			default init s0:
				function bag_function = <>
		''')
		result.assertNoErrors
				
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled bag_function : Bag(Integer)
			definitions:
			default init s0:
				function bag_function = < 1, 2, 3 >
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled bag_function : Bag(Integer)
			definitions:
			default init s0:
				function bag_function = < 1, 2, 2, 3 >
		''')
		result.assertError(AsmetalPackage.Literals.BAG_TERM, ErrorCode.BAG_TERM__ADDED_TWICE)
	
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled bag_function : Bag(Integer)
			definitions:
			default init s0:
				function bag_function = < 1 .. 1 >
		''')	
		result.assertError(AsmetalPackage.Literals.BAG_TERM, ErrorCode.BAG_TERM__ADDED_TWICE)	
	
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled bag_function : Bag(Integer)
			definitions:
			default init s0:
				function bag_function = < 1 .. 15, 2 >
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled bag_function : Bag(Integer)
			definitions:
			default init s0:
				function bag_function = < 1 .. 15, +2 >
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled bag_function : Bag(Integer)
			definitions:
			default init s0:
				function bag_function = < 1 .. 15, -2 >
		''')	
		result.assertError(AsmetalPackage.Literals.BAG_TERM, ErrorCode.BAG_TERM__STEP_NEGATIVE)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled bag_function : Bag(Integer)
			definitions:
			default init s0:
				function bag_function = < 1 .. 15, "A" >
		''')	
		result.assertError(AsmetalPackage.Literals.BAG_TERM, ErrorCode.BAG_TERM__STEP_NAN)	
		
	
	}
	
	@Test
	def void testSequenceTerm() {
		
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled seq_function : Bag(Integer)
			definitions:
			default init s0:
				function seq_function = [ 1 .. "Z" ]
		''')
		result.assertError(AsmetalPackage.Literals.SEQUENCE_TERM, ErrorCode.SEQUENCE_TERM__DIFFERENT_DOMAINS)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled seq_function : Seq(Integer)
			definitions:
			default init s0:
				function seq_function = [ "A" .. 5 ]
		''')
		result.assertError(AsmetalPackage.Literals.SEQUENCE_TERM, ErrorCode.SEQUENCE_TERM__DIFFERENT_DOMAINS)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled seq_function : Seq(Integer)
			definitions:
			default init s0:
				function seq_function = [ 1, 2, 5.4 ]
		''')
		result.assertError(AsmetalPackage.Literals.SEQUENCE_TERM, ErrorCode.SEQUENCE_TERM__DIFFERENT_DOMAINS)	
			
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled seq_function : Seq(Integer)
			definitions:
			default init s0:
				function seq_function = [ "A" .. "Z" ]
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_INITIALIZATION, ErrorCode.FUNCTION_INIZIALIZATION__ILL_FORMED_BODY)		
		
		result = parseHelper.parse('''
			asm prova
			signature:
				dynamic controlled seq_function : Seq(Integer)
			definitions:
			default init s0:
				function seq_function = []
		''')
		result.assertError(AsmetalPackage.Literals.SEQUENCE_TERM, ErrorCode.SEQUENCE_TERM__ANY_DOMAIN_NOT_DECLARED)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled seq_function : Seq(Integer)
			definitions:
			default init s0:
				function seq_function = []
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled seq_function : Seq(Integer)
			definitions:
			default init s0:
				function seq_function = [ 1, 2, 3 ]
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled seq_function : Seq(Integer)
			definitions:
			default init s0:
				function seq_function = [ 1, 2, 2, 3 ]
		''')
		result.assertError(AsmetalPackage.Literals.SEQUENCE_TERM, ErrorCode.SEQUENCE_TERM__ADDED_TWICE)
	
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled seq_function : Seq(Integer)
			definitions:
			default init s0:
				function seq_function = [ 1 .. 1 ]
		''')	
		result.assertError(AsmetalPackage.Literals.SEQUENCE_TERM, ErrorCode.SEQUENCE_TERM__ADDED_TWICE)	
		
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled seq_function : Seq(Integer)
			definitions:
			default init s0:
				function seq_function = [ 1 .. 15, 2 ]
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled seq_function : Seq(Integer)
			definitions:
			default init s0:
				function seq_function = [ 1 .. 15, +2 ]
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled seq_function : Seq(Integer)
			definitions:
			default init s0:
				function seq_function = [ 1 .. 15, -2 ]
		''')	
		result.assertError(AsmetalPackage.Literals.SEQUENCE_TERM, ErrorCode.SEQUENCE_TERM__STEP_NEGATIVE)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic controlled seq_function : Seq(Integer)
			definitions:
			default init s0:
				function seq_function = [ 1 .. 15, "A" ]
		''')	
		result.assertError(AsmetalPackage.Literals.SEQUENCE_TERM, ErrorCode.SEQUENCE_TERM__STEP_NAN)	
		
	
	}
	
	@Test
	def void testMapTerm() {
		
		var Asm result		
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
			 	 dynamic abstract domain State
				 dynamic abstract domain Transition
				 controlled source: Map( Transition, State )
				 static s1:State
				 static s2:State
				 static t1:Transition
				 static t2:Transition
				 static t3:Transition
				 static t4:Transition
			definitions:
			default init s0:
				 function source = {t1->s1,t2->s1,t3->s2,t4->s2}
		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
			 	 dynamic abstract domain State
				 dynamic abstract domain Transition
				 controlled source: Map( Transition, State )
				 static s1:State
				 static s2:State
				 static t1:Transition
				 static t2:Transition
				 static t3:Transition
				 static t4:Transition
			definitions:
			default init s0:
				 function source = {}
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_INITIALIZATION, ErrorCode.FUNCTION_INIZIALIZATION__ILL_FORMED_BODY)	
		
		result = parseHelper.parse('''
			asm prova
			signature:
			 	 dynamic abstract domain State
				 dynamic abstract domain Transition
				 controlled source: Map( Transition, State )
				 static s1:State
				 static s2:State
				 static t1:Transition
				 static t2:Transition
				 static t3:Transition
				 static t4:Transition
			definitions:
			default init s0:
				 function source = {->}
		''')
		result.assertError(AsmetalPackage.Literals.MAP_TERM, ErrorCode.MAP_TERM__ANY_DOMAIN_NOT_DECLARED)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
			 	 dynamic abstract domain State
				 dynamic abstract domain Transition
				 controlled source: Map( Transition, State )
				 static s1:State
				 static s2:State
				 static t1:Transition
				 static t2:Transition
				 static t3:Transition
				 static t4:Transition
			definitions:
			default init s0:
				 function source = {->}
		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
			 	 dynamic abstract domain State
				 dynamic abstract domain Transition
				 controlled source: Map( Transition, State )
				 static s1:State
				 static s2:State
				 static t1:Transition
				 static t2:Transition
				 static t3:Transition
				 static t4:Transition
			definitions:
			default init s0:
				 function source = {t1->t2,t2->s1,t3->s2,t4->s2}
		''')
		result.assertError(AsmetalPackage.Literals.MAP_TERM, ErrorCode.MAP_TERM__DIFFERENT_DOMAINS)	
			
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
			 	 dynamic abstract domain State
				 dynamic abstract domain Transition
				 controlled source: Map( Transition, State )
				 static s1:State
				 static s2:State
				 static t1:Transition
				 static t2:Transition
				 static t3:Transition
				 static t4:Transition
			definitions:
			default init s0:
				 function source = {s1->s1,t2->s1,t3->s2,t4->s2}
		''')
		result.assertError(AsmetalPackage.Literals.MAP_TERM, ErrorCode.MAP_TERM__DIFFERENT_DOMAINS)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				 controlled first: Integer
			definitions:
			default init s0:
				 function first = at( {"1"->1,"2"->2,"3"->3}, "1" ) 
		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				 controlled first: Integer
			definitions:
			default init s0:
				 function first = at( {"1"->1,"2"->2,"3"->3}, 1 ) 
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_TERM, ErrorCode.SIGNATURE__FUNCTION_NOT_FOUND)	
		
		
		
	}
	
	
	@Test
	def void testLetTerm() {
		
		var Asm result
		
		result = parseHelper.parse('''
		asm prova
					import StandardLibrary
				signature:
						dynamic abstract domain Library
					   
					    domain Isbn   		 subsetof String
					    domain Title  		 subsetof String
						domain Book subsetof Prod( Isbn, Title )		
				 	 	
				 	 	dynamic controlled libraryBooks: Library -> Powerset(Book)
				 	 	dynamic controlled bookIsbn :          Book -> Isbn
						derived bookExists: Prod( Book, Library) -> Boolean
		
						
				definitions:
					function bookExists( $b in Book, $l in Library) =
							let ( $books = libraryBooks( $l ), $isbn = bookIsbn( $b )) in
								true
							endlet

		''')
		result.assertNoErrors

		
		result = parseHelper.parse('''
			asm prova
					import StandardLibrary
				signature:
						dynamic abstract domain Library
					   
					    domain Isbn   		 subsetof String
					    domain Title  		 subsetof String
						domain Book subsetof Prod( Isbn, Title )		
				 	 	
				 	 	dynamic controlled libraryBooks: Library -> Powerset(Book)
				 	 	dynamic controlled bookIsbn :          Book -> Isbn
						derived bookExists: Prod( Book, Library) -> Boolean
			
						
				definitions:
					function bookExists( $books in Book, $l in Library) =
							let ( $books = libraryBooks( $l ), $isbn = bookIsbn( $b )) in
								true
							endlet

		''')
		result.assertError(AsmetalPackage.Literals.LET_TERM, ErrorCode.LET_TERM__VARIABLE_ALREADY_USED)	

	}
	
	@Test
	def void testFiniteQuantificationTest() {
		
		var Asm result
		
		// FORALL
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				derived allOnRightSide: Boolean		
			definitions:
				function allOnRightSide =
					(forall $a in Actors with position($a) = RIGHT)

		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				derived allOnRightSide: Integer				
			definitions:
				function allOnRightSide =
					(forall $a in Actors with position($a) = RIGHT)

		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_DEFINITION, ErrorCode.FUNCTION_DEFINITION__ILL_FORMED_BODY)		
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				derived allOnRightSide: Boolean

						
			definitions:
				function allOnRightSide =
					(forall $a in Actors with position($a) + RIGHT)

		''')
		result.assertError(AsmetalPackage.Literals.FINITE_QUANTIFICATION_TERM, ErrorCode.TERM__DOMAIN_GUARD_NOT_BOOLEAN)		
	
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				derived allOnRightSide: Actors -> Boolean

						
			definitions:
				function allOnRightSide($a in Actors) =
					(forall $a in Actors with position($a) = RIGHT)

		''')
		result.assertError(AsmetalPackage.Literals.FINITE_QUANTIFICATION_TERM, ErrorCode.FORALL_TERM__VARIABLE_ALREADY_USED)		
	
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				derived allOnRightSide: Boolean

						
			definitions:
				function allOnRightSide =
					(forall $a in [ FERRYMAN ] with position($a) = RIGHT)

		''')
		result.assertError(AsmetalPackage.Literals.FINITE_QUANTIFICATION_TERM, ErrorCode.FINITE_QUANT_TERM__VARIABLE_DOMAIN_NOT_POWERSET)		
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				dynamic controlled pos_NOK: Actors
				derived allOnRightSide: Boolean

						
			definitions:
				function allOnRightSide =
					(forall $a in pos_NOK with position($a) = RIGHT)

		''')
		result.assertError(AsmetalPackage.Literals.FINITE_QUANTIFICATION_TERM, ErrorCode.FINITE_QUANT_TERM__VARIABLE_DOMAIN_NOT_POWERSET)		
	
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				derived allOnRightSide: Boolean

						
			definitions:
				function allOnRightSide =
					(forall $a in { FERRYMAN } with position($a) = RIGHT)

		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				dynamic controlled position_power: Integer -> Powerset(Actors)
				derived allOnRightSide: Boolean

						
			definitions:
				function allOnRightSide =
					(forall $a in position_power(5) with position($a) = RIGHT)

		''')
		result.assertNoErrors
	
		// EXIST
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				derived allOnRightSide: Boolean

						
			definitions:
				function allOnRightSide =
					(exist $a in Actors with position($a) = RIGHT)

		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				derived allOnRightSide: Integer

						
			definitions:
				function allOnRightSide =
					(exist $a in Actors with position($a) = RIGHT)

		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_DEFINITION, ErrorCode.FUNCTION_DEFINITION__ILL_FORMED_BODY)		
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				derived allOnRightSide: Boolean

						
			definitions:
				function allOnRightSide =
					(exist $a in Actors with position($a) + RIGHT)

		''')
		result.assertError(AsmetalPackage.Literals.FINITE_QUANTIFICATION_TERM, ErrorCode.TERM__DOMAIN_GUARD_NOT_BOOLEAN)		
	
			result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				derived allOnRightSide: Actors -> Boolean

						
			definitions:
				function allOnRightSide($a in Actors) =
					(exist $a in Actors with position($a) = RIGHT)

		''')
		result.assertError(AsmetalPackage.Literals.FINITE_QUANTIFICATION_TERM, ErrorCode.EXIST_TERM__VARIABLE_ALREADY_USED)		
	
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				dynamic controlled pos_NOK: Actors
				derived allOnRightSide: Boolean

						
			definitions:
				function allOnRightSide =
					(exist $a in pos_NOK with position($a) = RIGHT)

		''')
		result.assertError(AsmetalPackage.Literals.FINITE_QUANTIFICATION_TERM, ErrorCode.FINITE_QUANT_TERM__VARIABLE_DOMAIN_NOT_POWERSET)		
	
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				derived allOnRightSide: Boolean

						
			definitions:
				function allOnRightSide =
					(exist $a in { FERRYMAN } with position($a) = RIGHT)

		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				dynamic controlled position_power: Integer -> Powerset(Actors)
				derived allOnRightSide: Boolean

						
			definitions:
				function allOnRightSide =
					(exist $a in position_power(5) with position($a) = RIGHT)

		''')
		result.assertNoErrors
		
		// EXIST UNIQUE
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				derived allOnRightSide: Boolean

						
			definitions:
				function allOnRightSide =
					(exist unique $a in Actors with position($a) = RIGHT)

		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				derived allOnRightSide: Integer

						
			definitions:
				function allOnRightSide =
					(exist unique $a in Actors with position($a) = RIGHT)

		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_DEFINITION, ErrorCode.FUNCTION_DEFINITION__ILL_FORMED_BODY)		
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				derived allOnRightSide: Boolean

						
			definitions:
				function allOnRightSide =
					(exist unique $a in Actors with position($a) + RIGHT)

		''')
		result.assertError(AsmetalPackage.Literals.FINITE_QUANTIFICATION_TERM, ErrorCode.TERM__DOMAIN_GUARD_NOT_BOOLEAN)		
	
			result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				derived allOnRightSide: Actors -> Boolean

						
			definitions:
				function allOnRightSide($a in Actors) =
					(exist unique $a in Actors with position($a) = RIGHT)

		''')
		result.assertError(AsmetalPackage.Literals.FINITE_QUANTIFICATION_TERM, ErrorCode.EXIST_UNIQUE_TERM__VARIABLE_ALREADY_USED)		
	
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				dynamic controlled pos_NOK: Actors
				derived allOnRightSide: Boolean

						
			definitions:
				function allOnRightSide =
					(exist unique $a in pos_NOK with position($a) = RIGHT)

		''')
		result.assertError(AsmetalPackage.Literals.FINITE_QUANTIFICATION_TERM, ErrorCode.FINITE_QUANT_TERM__VARIABLE_DOMAIN_NOT_POWERSET)		
	
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				derived allOnRightSide: Boolean

						
			definitions:
				function allOnRightSide =
					(exist unique $a in { FERRYMAN } with position($a) = RIGHT)

		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
				dynamic controlled position_power: Integer -> Powerset(Actors)
				derived allOnRightSide: Boolean

						
			definitions:
				function allOnRightSide =
					(exist unique $a in position_power(5) with position($a) = RIGHT)

		''')
		result.assertNoErrors
		
	}
	
	@Test
	def void testComprehensionTerm() {
		
		var Asm result	

		// SET CT
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Powerset(State)
				 controlled isStart: State -> Boolean
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone({$s in states($m) | isStart($s) : $s}) 
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Powerset(State)
				 controlled isStart: State -> Boolean
				 static chooseone_mine: Powerset(D) -> D
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone_mine({$s in states($m) | isStart($s) : $s}) 
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Powerset(State)
				 controlled isStart: State -> Boolean
				 static chooseone_mine: Powerset(State) -> State
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone_mine({$s in states($m) | isStart($s) : $s}) 
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Powerset(State)
				 controlled isStart: State -> Boolean
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone({$s in states($m) : $s}) 
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Powerset(State)
				 controlled isStart: State -> Boolean
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone({$s in states($m) | 5 + 6 : $s}) 
		''')
		result.assertError(AsmetalPackage.Literals.SET_CT, ErrorCode.TERM__DOMAIN_GUARD_NOT_BOOLEAN)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Bag(State)
				 controlled isStart: State -> Boolean
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone({$s in states($m) : $s}) 
		''')
		result.assertError(AsmetalPackage.Literals.SET_CT, ErrorCode.SET_CT_TERM__VARIABLE_DOMAIN_NOT_POWERSET)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Powerset(State)
				 controlled isStart: State -> Boolean
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone({$m in states($m) | isStart($s) : $s}) 
		''')
		result.assertError(AsmetalPackage.Literals.SET_CT, ErrorCode.SET_CT_TERM__VARIABLE_ALREADY_USED)	
				
		/*result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Bag(State)
				 controlled isStart: State -> Boolean
				 static chooseone_mine: Bag(State) -> State
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone_mine(<$s in states($m) | isStart($s) : $s>) 
		''')
		result.assertNoErrors*/
		
		// BAG CT
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Bag(State)
				 controlled isStart: Bag(State) -> Boolean
				 static chooseone_mine: Bag(D) -> D
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone_mine(<$s in states($m) | isStart($s) : $s>) 
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Bag(State)
				 controlled isStart: Bag(State) -> Boolean
				 static chooseone_mine: Bag(State) -> State
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone_mine(<$s in states($m) | isStart($s) : $s>) 
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Bag(State)
				 controlled isStart: State -> Boolean
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone(<$s in states($m) : $s>) 
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Bag(State)
				 controlled isStart: State -> Boolean
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone(<$s in states($m) | 5 + 6 : $s>) 
		''')
		result.assertError(AsmetalPackage.Literals.BAG_CT, ErrorCode.TERM__DOMAIN_GUARD_NOT_BOOLEAN)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Powerset(State)
				 controlled isStart: State -> Boolean
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone(<$s in states($m) : $s>) 
		''')
		result.assertError(AsmetalPackage.Literals.BAG_CT, ErrorCode.BAG_CT_TERM__VARIABLE_DOMAIN_NOT_BAG)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Bag(State)
				 controlled isStart: State -> Boolean
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone(<$m in states($m) | isStart($s) : $s>) 
		''')
		result.assertError(AsmetalPackage.Literals.BAG_CT, ErrorCode.BAG_CT_TERM__VARIABLE_ALREADY_USED)	
			
		// SEQUENCE CT
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Seq(State)
				 controlled isStart: Seq(State) -> Boolean
				 static chooseone_mine: Seq(D) -> D
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone_mine([$s in states($m) | isStart($s) : $s]) 
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Seq(State)
				 controlled isStart: Seq(State) -> Boolean
				 static chooseone_mine: Seq(State) -> State
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone_mine([$s in states($m) | isStart($s) : $s]) 
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Seq(State)
				 controlled isStart: State -> Boolean
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone([$s in states($m) : $s]) 
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Seq(State)
				 controlled isStart: State -> Boolean
				 static chooseone_mine: Seq(State) -> State
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone_mine([$s in states($m) | 5 + 6 : $s]) 
		''')
		result.assertError(AsmetalPackage.Literals.SEQUENCE_CT, ErrorCode.TERM__DOMAIN_GUARD_NOT_BOOLEAN)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Powerset(State)
				 controlled isStart: State -> Boolean
				 static chooseone_mine: Seq(State) -> State
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone_mine([$s in states($m) : $s]) 
		''')
		result.assertError(AsmetalPackage.Literals.SEQUENCE_CT, ErrorCode.SEQUENCE_CT_TERM__VARIABLE_DOMAIN_NOT_SEQUENCE)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				 abstract domain NamedElement
				 dynamic domain Fsm subsetof NamedElement
				 dynamic domain State subsetof NamedElement
				 controlled currentState: Fsm -> State
				 controlled states: Fsm -> Seq(State)
				 controlled isStart: State -> Boolean
				 static chooseone_mine: Seq(State) -> State
			definitions:
			default init s0:
				function currentState($m in Fsm) = chooseone_mine([$m in states($m) | isStart($s) : $s]) 
		''')
		result.assertError(AsmetalPackage.Literals.SEQUENCE_CT, ErrorCode.SEQUENCE_CT_TERM__VARIABLE_ALREADY_USED)	
	
	
	}
}