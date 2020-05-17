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

@RunWith(XtextRunner)
@InjectWith(AsmetaLInjectorProvider)
class BodyNegativeParsingTest {
	
	@Inject	ParseHelper<Asm> parseHelper
	@Inject extension ValidationTestHelper

	@Test
	def void testDomainDefinition() {
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			import BodyLibrary
			signature:
			definitions:
				domain Cherries = {0 .. 3} 
				domain Cherries = {0 .. 5} 
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN_DEFINITION, ErrorCode.DOMAIN_DEFINITION__DEFINED_TWICE)	
		
		result = parseHelper.parse('''
			asm prova
				import BodyLibrary 
			signature:
			definitions:
				domain Cherries = {0 .. 3} 
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN_DEFINITION, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED)
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				abstract domain Philosopher
			definitions:
				domain Philosopher = {0 .. 3} 
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN_DEFINITION, ErrorCode.DOMAIN_DEFINITION__DOMAIN_NOT_CONCRETE)	
	
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				dynamic domain NumOfCherries subsetof Integer
			definitions:
				domain NumOfCherries = {0 .. 3} 
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN_DEFINITION, ErrorCode.DOMAIN_DEFINITION__IS_DYNAMIC_DOMAIN)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				domain NumOfCherries subsetof Integer
			definitions:
				domain NumOfCherries = {0 .. 3} 
				domain NumOfCherries = {0 .. 5} 
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN_DEFINITION, ErrorCode.DOMAIN_DEFINITION__DEFINED_TWICE)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				domain NumOfCherries subsetof Integer
			definitions:
				domain NumOfCherries = "A"
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN_DEFINITION, ErrorCode.DOMAIN_DEFINITION__ILL_FORMED_BODY)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				domain NumOfCherries subsetof Integer
			definitions:
				domain NumOfCherries = { 1 .. "Z" }
		''')
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__DIFFERENT_DOMAINS)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				domain NumOfCherries subsetof Integer
			definitions:
				domain NumOfCherries = { "A" .. 5 }
		''')
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__DIFFERENT_DOMAINS)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				domain NumOfCherries subsetof Integer
			definitions:
				domain NumOfCherries = { 1, 2, 5.4 }
		''')
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__DIFFERENT_DOMAINS)	
			
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				domain NumOfCherries subsetof Integer
			definitions:
				domain NumOfCherries = { "A" .. "Z" }
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN_DEFINITION, ErrorCode.DOMAIN_DEFINITION__ILL_FORMED_BODY)		
		
		result = parseHelper.parse('''
			asm prova
			signature:
				domain NumOfCherries subsetof Integer
			definitions:
				domain NumOfCherries = {}
		''')
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__ANY_DOMAIN_NOT_DECLARED)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				domain NumOfCherries subsetof Integer
			definitions:
				domain NumOfCherries = {}
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				domain NumOfCherries subsetof Integer
			definitions:
				domain NumOfCherries = { 1, 2, 3 }
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				domain NumOfCherries subsetof Integer
			definitions:
				domain NumOfCherries = { 1, 2, 2, 3 }
		''')
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__ADDED_TWICE)
	
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				domain NumOfCherries subsetof Integer
			definitions:
				domain NumOfCherries = { 1 .. 1 }
		''')	
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__ADDED_TWICE)	
	
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				domain NumOfCherries subsetof Integer
			definitions:
				domain NumOfCherries = { 1 .. 15, 2 }
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				domain NumOfCherries subsetof Integer
			definitions:
				domain NumOfCherries = { 1 .. 15, +2 }
		''')
		result.assertNoErrors	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				domain NumOfCherries subsetof Integer
			definitions:
				domain NumOfCherries = { 1 .. 15, -2 }
		''')	
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__STEP_NEGATIVE)	
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary
			signature:
				domain NumOfCherries subsetof Integer
			definitions:
				domain NumOfCherries = { 1 .. 15, "A" }
		''')	
		result.assertError(AsmetalPackage.Literals.SET_TERM, ErrorCode.SET_TERM__STEP_NAN)	
	}
	
	@Test
	def void testFunctionDefinition() {
		var Asm result
	
		result = parseHelper.parse('''
				asm prova				
				signature:
					abstract domain Philosopher				
					derived leftNeighbour: Philosopher -> Philosopher
					static philo1: Philosopher
					static philo2: Philosopher
				definitions:
					function leftNeighbour($p in Philosopher) =
						switch($p)
							case philo1: philo2
							case philo2: philo1
						endswitch
				
					function leftNeighbour($p in Philosopher) =
						switch($p)
							case philo1: philo1
							case philo2: philo2
						endswitch
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_DEFINITION, ErrorCode.FUNCTION_DEFINITION__DEFINED_TWICE)	
	
		result = parseHelper.parse('''
				asm prova	
					import StandardLibrary			
				signature:
					abstract domain Philosopher	
					abstract domain PhilosopherOther				
					derived leftNeighbour: Philosopher -> Philosopher
					derived leftNeighbour: PhilosopherOther -> PhilosopherOther
					static philo1a: Philosopher
					static philo2a: Philosopher
					static philo1b: PhilosopherOther
					static philo2b: PhilosopherOther
				definitions:
					function leftNeighbour($p in Philosopher) =
						switch($p)
							case philo1a: philo1a
							case philo2a: philo2a
						endswitch
					function leftNeighbour($po in PhilosopherOther) =
						switch($po)
							case philo1b: philo1b
							case philo2b: philo2b
						endswitch
		''')
		result.assertNoErrors
	
		result = parseHelper.parse('''
				asm prova				
				signature:
					abstract domain Philosopher				
					derived leftNeighbour: Philosopher -> Philosopher
					static philo1: Philosopher
					static philo2: Philosopher
				definitions:
					function leftNeighbour($p in PhilosopherNone) =
						switch($p)
							case philo1: philo2
							case philo2: philo1
						endswitch

		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_DEFINITION, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND)	
	
		result = parseHelper.parse('''
				asm prova
					import BodyLibrary2		
				signature:
					abstract domain Philosopher				
					static philo1: Philosopher
					static philo2: Philosopher
				definitions:
					function leftNeighbour($p in Integer) =
						switch($p)
							case 1: philo1
							case 2: philo2
						endswitch

		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_DEFINITION, ErrorCode.SIGNATURE__FUNCTION_NOT_IMPORTED)	
	
		result = parseHelper.parse('''
				asm prova
					import StandardLibrary		
				signature:

				definitions:
					function leftNeighbour($p in Integer) =
						switch($p)
							case 1: philo1
							case 2: philo2
						endswitch

		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_DEFINITION, ErrorCode.SIGNATURE__FUNCTION_NOT_FOUND)	
	
		result = parseHelper.parse('''
				asm prova				
				signature:
					abstract domain Philosopher				
					controlled leftNeighbour: Philosopher -> Philosopher
					static philo1: Philosopher
					static philo2: Philosopher
				definitions:
					function leftNeighbour($p in Philosopher) =
						switch($p)
							case philo1: philo2
							case philo2: philo1
						endswitch

		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_DEFINITION, ErrorCode.FUNCTION_DEFINITION__FUNCTION_NOT_STATIC_DERIVED)	
	
		result = parseHelper.parse('''
				asm prova				
				signature:
					abstract domain Philosopher				
					derived leftNeighbour: Philosopher -> Philosopher
					static philo1: Philosopher
					static philo2: Philosopher
				definitions:
					function leftNeighbour($p in Philosopher, $p in Philosopher ) =
						switch($p)
							case philo1: philo2
							case philo2: philo1
						endswitch

		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_DEFINITION, ErrorCode.FUNCTION_DEFINITION__INVALID_VARIABLE)	
		
		result = parseHelper.parse('''
			asm prova	
			import StandardLibrary				
			signature:

				dynamic abstract domain Library
			
			    domain Isbn   		 subsetof String
			    domain Title  		 subsetof String
			 	domain AuthorSurname subsetof String
				domain AuthorName    subsetof String
				domain CopiesNumber  subsetof Natural
				domain Book subsetof Prod( Isbn, Title, AuthorSurname, AuthorName, CopiesNumber)
				
				dynamic controlled bookIsbn : Book -> Isbn		
				dynamic controlled libraryBooks: Library -> Powerset(Book)	
				derived bookExists: Prod( Book, Library) -> Boolean
				
				derived bookExists: Prod( Book, Library) -> Boolean
			definitions:
				function bookExists( $b in String, $l in Library) =
						let ( $books = libraryBooks( $l ), $isbn = bookIsbn( $b ) ) in
							if ( exist $c in $books with bookIsbn( $c ) = $isbn ) then true
							else false
							endif
						endlet

		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_DEFINITION, ErrorCode.SIGNATURE__FUNCTION_NOT_FOUND)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary					
			signature:

				dynamic abstract domain Library
			
			    domain Isbn   		 subsetof String
			    domain Title  		 subsetof String
			 	domain AuthorSurname subsetof String 
				domain AuthorName    subsetof String
				domain CopiesNumber  subsetof Natural
				domain Book subsetof Prod( Isbn, Title, AuthorSurname, AuthorName, CopiesNumber)
				
				dynamic controlled bookIsbn : Book -> Isbn		
				dynamic controlled libraryBooks: Library -> Powerset(Book)	
				derived bookExists: Prod( Book, Library) -> Boolean
				
			definitions:
				function bookExists( $b in Book, $l in Library) = "prova"
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_DEFINITION, ErrorCode.FUNCTION_DEFINITION__ILL_FORMED_BODY)	
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary					
			signature:
			
				dynamic abstract domain Library
			
			    domain Isbn   		 subsetof String
			    domain Title  		 subsetof String
			 	domain AuthorSurname subsetof String
				domain AuthorName    subsetof String
				domain CopiesNumber  subsetof Natural
				domain Book subsetof Prod( Isbn, Title, AuthorSurname, AuthorName, CopiesNumber)
				
				dynamic controlled bookIsbn : Book -> Isbn		
				dynamic controlled libraryBooks: Library -> Powerset(Book)	
				derived bookExists: Prod( Book, Library) -> Boolean
				
			definitions:
				function bookExists( $b in Book, $l in Library) = 
					let ( $books = libraryBooks( $l ), $isbn = bookIsbn( $b ) ) in
						if ( exist $c in $books with bookIsbn( $c ) = $isbn ) then "a"
						else "b"
						endif
					endlet
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION_DEFINITION, ErrorCode.FUNCTION_DEFINITION__ILL_FORMED_BODY)	
	
	}
			
	@Test
	def void testRuleDeclaration() {
		var Asm result
	
		result = parseHelper.parse('''
				asm prova		
					import StandardLibrary		
				signature:
					domain NumOfCherries subsetof Integer
					abstract domain Philosopher	
					controlled cherriesInPlate: Philosopher -> NumOfCherries
					
				definitions:
				
					domain NumOfCherries = {0 .. 3}
						
					rule r_philoRule($p in Philosopher) =
						if(cherriesInPlate($p) > 0) then
							cherriesInPlate($p) := cherriesInPlate($p) - 1
						else
							cherriesInPlate($p) := cherriesInPlate($p) + 1
						endif
						
					rule r_philoRule($p in Philosopher) =
						if(cherriesInPlate($p) > 0) then
							cherriesInPlate($p) := cherriesInPlate($p) - 1
						else
							cherriesInPlate($p) := cherriesInPlate($p) + 1
						endif
		''')
		result.assertError(AsmetalPackage.Literals.RULE_DECLARATION, ErrorCode.RULE_DECLARATION__DEFINED_TWICE)	
		
		result = parseHelper.parse('''
				asm prova		
					import StandardLibrary		
				signature:
					domain NumOfCherries subsetof Integer
					abstract domain Philosopher	
					controlled cherriesInPlate: Philosopher -> NumOfCherries
					
				definitions:
				
					domain NumOfCherries = {0 .. 3}
						
					rule r_philoRule($p in Philosopher, $p in Philosopher) =
						if(cherriesInPlate($p) > 0) then
							cherriesInPlate($p) := cherriesInPlate($p) - 1
						else
							cherriesInPlate($p) := cherriesInPlate($p) + 1
						endif
						
		''')
		result.assertError(AsmetalPackage.Literals.RULE_DECLARATION, ErrorCode.RULE_DECLARATION__INVALID_VARIABLE)	
		
		result = parseHelper.parse('''
				asm prova		
					import StandardLibrary		
				signature:
					domain NumOfCherries subsetof Integer
					abstract domain Philosopher	
					controlled cherriesInPlate: Philosopher -> NumOfCherries
					
				definitions:
				
					domain NumOfCherries = {0 .. 3}
						
					rule r_philoRule($p in PhilosopherNone) =
						if(cherriesInPlate($p) > 0) then
							cherriesInPlate($p) := cherriesInPlate($p) - 1
						else
							cherriesInPlate($p) := cherriesInPlate($p) + 1
						endif
						
		''')
		result.assertError(AsmetalPackage.Literals.RULE_DECLARATION, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND)	
		
		result = parseHelper.parse('''
				asm prova		
					import BodyLibrary		
				signature:
					domain NumOfCherries subsetof Integer
				definitions:
				
					domain NumOfCherries = {0 .. 3}
						
					rule r_philoRule($p in Integer) =
						if(1 > 0) then
							cherriesInPlate($p) := cherriesInPlate($p) - 1
						else
							cherriesInPlate($p) := cherriesInPlate($p) + 1
						endif
						
		''')
		result.assertError(AsmetalPackage.Literals.RULE_DECLARATION, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED)	
		
		
	}
	
	@Test
	def void testInvariant() {
		var Asm result
	
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary				
			signature:
			definitions:	
				invariant over position: position(GO)=position(CA) implies position(GO)=position(FERRYMAN)
		''')
		result.assertError(AsmetalPackage.Literals.INVARIANT_ELEMENT, ErrorCode.INVARINT__NOT_FOUND)	
		
	 	result = parseHelper.parse('''
			asm prova		
			import StandardLibrary		
			signature:
				enum domain Actors = {FERRYMAN | GO | CA | WO}
				enum domain Side = {LEFT | RIGHT}
				dynamic controlled position: Actors -> Side
			definitions:	
				invariant over position, position: position(GO)=position(CA) implies position(GO)=position(FERRYMAN)
		''')
		result.assertError(AsmetalPackage.Literals.INVARIANT_ELEMENT, ErrorCode.INVARINT__CALLED_TWICE)	
		
	}
	
	@Test
	def void testTemporalSpec() {
		var Asm result
	
		result = parseHelper.parse('''
			asm prova		
			import StandardLibrary		
				signature:
				definitions:	
					CTL prova : 6 > 5
					CTL prova : 6 > 5
		''')
		result.assertError(AsmetalPackage.Literals.CTL_SPEC, ErrorCode.CTL_SPEC__DUPLICATE_NAME)	
		
		result = parseHelper.parse('''
			asm prova	
			import StandardLibrary			
				signature:
				definitions:	
					LTL prova : 6 > 5
					LTL prova : 6 > 5
		''')
		result.assertError(AsmetalPackage.Literals.LTL_SPEC, ErrorCode.LTL_SPEC__DUPLICATE_NAME)	
		
		result = parseHelper.parse('''
			asm prova		
			import StandardLibrary		
			signature:
			definitions:	
				LTL prova : 6 > 5 
				LTL prova2 : 6 > 5
				CTL prova : 6 > 5
		''')
		result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova	
			import StandardLibrary			
				signature:
				definitions:	
					CTL prova : 6 + 5
		''')
		result.assertError(AsmetalPackage.Literals.CTL_SPEC, ErrorCode.CONSTRAINT_DOMAIN_BODY_NOT_BOOLEAN)	
	
		result = parseHelper.parse('''
			asm prova	
			import StandardLibrary			
				signature:
				definitions:	
					JUSTICE 6 + 5
		''')
		result.assertError(AsmetalPackage.Literals.JUSTICE_CONSTRAINT, ErrorCode.CONSTRAINT_DOMAIN_BODY_NOT_BOOLEAN)	
	
		result = parseHelper.parse('''
			asm prova		
				import StandardLibrary		
				signature:
					static alwaysTrue : Boolean
				definitions:	
					COMPASSION ( 6 + 5, alwaysTrue )
		''')
		result.assertError(AsmetalPackage.Literals.COMPASSION_CONSTRAINT, ErrorCode.CONSTRAINT_DOMAIN_BODY_NOT_BOOLEAN)	
		
		result = parseHelper.parse('''
			asm prova	
				import StandardLibrary			
				signature:
					static alwaysTrue : Boolean
				definitions:	
					COMPASSION ( alwaysTrue, 6 + 5 )
		''')
		result.assertError(AsmetalPackage.Literals.COMPASSION_CONSTRAINT, ErrorCode.CONSTRAINT_DOMAIN_BODY_NOT_BOOLEAN)	
		
	
		result = parseHelper.parse('''
			asm prova		
				import StandardLibrary		
				signature:
					static alwaysTrue : Boolean
					static notBoolean : String
				definitions:	
					COMPASSION ( notBoolean, alwaysTrue )
		''')
		result.assertError(AsmetalPackage.Literals.COMPASSION_CONSTRAINT, ErrorCode.CONSTRAINT_DOMAIN_BODY_NOT_BOOLEAN)	
		
		result = parseHelper.parse('''
			asm prova		
				import StandardLibrary		
				signature:
					static alwaysTrue : Boolean
					static notBoolean : String
				definitions:	
					COMPASSION ( alwaysTrue, notBoolean )
		''')
		result.assertError(AsmetalPackage.Literals.COMPASSION_CONSTRAINT, ErrorCode.CONSTRAINT_DOMAIN_BODY_NOT_BOOLEAN)	
		
		result = parseHelper.parse('''
			asm prova	
				import StandardLibrary			
				signature:
					static notBoolean : String
				definitions:	
					INVAR notBoolean
		''')
		result.assertError(AsmetalPackage.Literals.INVARIANT_CONSTRAINT, ErrorCode.CONSTRAINT_DOMAIN_BODY_NOT_BOOLEAN)	
	
			result = parseHelper.parse('''
			asm prova	
				import StandardLibrary			
				signature:
					static notBoolean : String
				definitions:	
					CTL prova : notBoolean
		''')
		result.assertError(AsmetalPackage.Literals.CTL_SPEC, ErrorCode.CONSTRAINT_DOMAIN_BODY_NOT_BOOLEAN)	
	
		result = parseHelper.parse('''
			asm prova		
				import StandardLibrary		
				signature:
					static notBoolean : String
				definitions:	
					JUSTICE notBoolean
		''')
		result.assertError(AsmetalPackage.Literals.JUSTICE_CONSTRAINT, ErrorCode.CONSTRAINT_DOMAIN_BODY_NOT_BOOLEAN)	

		result = parseHelper.parse('''
			asm prova		
				import StandardLibrary		
				signature:
					static alwaysTrue : Boolean
					static notBoolean : String
				definitions:	
					COMPASSION ( notBoolean, alwaysTrue )
		''')
		result.assertError(AsmetalPackage.Literals.COMPASSION_CONSTRAINT, ErrorCode.CONSTRAINT_DOMAIN_BODY_NOT_BOOLEAN)	
		
		result = parseHelper.parse('''
			asm prova		
				import StandardLibrary		
				signature:
					static alwaysTrue : Boolean
				definitions:	
					COMPASSION ( alwaysTrue, notBoolean )
		''')
		result.assertError(AsmetalPackage.Literals.COMPASSION_CONSTRAINT, ErrorCode.CONSTRAINT_DOMAIN_BODY_NOT_BOOLEAN)	
		
		
		result = parseHelper.parse('''
			asm prova		
				import StandardLibrary		
				signature:
					static alwaysTrue : Boolean
				definitions:	
					INVAR notBoolean
		''')
		result.assertError(AsmetalPackage.Literals.INVARIANT_CONSTRAINT, ErrorCode.CONSTRAINT_DOMAIN_BODY_NOT_BOOLEAN)	
	
	
		result = parseHelper.parse('''
			asm prova	
				import StandardLibrary		
			signature:
				static position : Integer
				static alwaysTrue : Boolean
			definitions:
				INVAR alwaysTrue
				JUSTICE alwaysTrue
				COMPASSION ( alwaysTrue, alwaysTrue )
				invariant over position: alwaysTrue
				CTL prova : alwaysTrue	
				LTL prova : alwaysTrue
				
		''')
		result.assertNoErrors
	
		result = parseHelper.parse('''
			asm prova	
				import StandardLibrary		
			signature:
				static position : Integer
				static alwaysTrue : Integer -> Boolean
			definitions:
				INVAR alwaysTrue(1)
				JUSTICE alwaysTrue(1)
				COMPASSION ( alwaysTrue(1), alwaysTrue(1) )
				invariant over position: alwaysTrue(1)
				CTL prova : alwaysTrue(1)	
				LTL prova : alwaysTrue(1)
				
		''')
		result.assertNoErrors
	
	}
	
	
}