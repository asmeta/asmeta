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
import org.asmeta.xt.tests.AsmParseHelper

/**
 * A class for validation test (negative test)
 */

@RunWith(XtextRunner)
@InjectWith(AsmetaLInjectorProvider)
class HeaderNegativeParsingTest {
	
	@Inject	AsmParseHelper parseHelper
	@Inject extension ValidationTestHelper

	@Test
	def void testImport() {
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			
				import StandardLibrarykjfjajda
			
			signature:
				
				abstract domain Philosopher
				
			definitions:
		''')
		result.assertError(AsmetalPackage.Literals.IMPORT_CLAUSE, ErrorCode.IMPORT_CLAUSE__FILE_NOT_FOUND)
		
		result = parseHelper.parse('''
			asm prova
				import StandardLibrary ( Domains )
			signature:
				abstract domain Philosopher
				
			definitions:
		''')
		result.assertError(AsmetalPackage.Literals.IMPORT_CLAUSE, ErrorCode.IMPORT_CLAUSE__NOT_FOUND)
		
		result = parseHelper.parse('''
			asm prova
			
				import StandardLibrary ( Integer, Integer )
			
			signature:
				abstract domain Philosopher
				
			definitions:
		''')
		result.assertError(AsmetalPackage.Literals.IMPORT_CLAUSE, ErrorCode.IMPORT_CLAUSE__CALLED_TWICE)
		
		result = parseHelper.parse('''
			asm prova
			
				import 
			
			signature:
				abstract domain Philosopher
				
			definitions:			 
		''')
		// TODO ^ ok, passa il import.size == 0
		//result.assertNoErrors
		
		result = parseHelper.parse('''
			asm prova
			
				import StandardLibrary ( Integer, eq, Integer )
			
			signature:
				abstract domain Philosopher
				
			definitions:
		''')
		result.assertError(AsmetalPackage.Literals.IMPORT_CLAUSE, ErrorCode.IMPORT_CLAUSE__CALLED_TWICE)
		
		result = parseHelper.parse('''
			asm prova
			
				import EmptyLibrary1
			
			signature:
				abstract domain Philosopher
				
			definitions:
		''')
		result.assertWarning(AsmetalPackage.Literals.IMPORT_CLAUSE, ErrorCode.IMPORT_CLAUSE__NOTHING_TO_IMPORT)

		result = parseHelper.parse('''
			asm prova
			
				import EmptyLibrary2
			
			signature:
				abstract domain Philosopher
				
			definitions:
		''')
		result.assertWarning(AsmetalPackage.Literals.IMPORT_CLAUSE, ErrorCode.IMPORT_CLAUSE__NOTHING_TO_IMPORT)
		
		result = parseHelper.parse('''
			asm prova
			
				import EmptyLibrary2 ( D )
			
			signature:
				abstract domain Philosopher
				
			definitions:
		''')
		result.assertWarning(AsmetalPackage.Literals.IMPORT_CLAUSE, ErrorCode.IMPORT_CLAUSE__NOTHING_TO_IMPORT)
		

	}
	
	@Test
	def void testExport() {
		var Asm result

		result = parseHelper.parse('''
			asm prova
			
				export Philosopher
			
			signature:
				
				abstract domain Philosopher
				
			definitions:	
		''')
		result.assertError(AsmetalPackage.Literals.EXPORT_CLAUSE, ErrorCode.EXPORT_CLAUSE__NOTHING_TO_EXPORT)
		
		result = parseHelper.parse('''
			asm prova
			
				export Prova
			
			signature:
				
				abstract domain Philosopher
				
			definitions:	
		''')
		result.assertError(AsmetalPackage.Literals.EXPORT_CLAUSE, ErrorCode.EXPORT_CLAUSE__NOT_FOUND)
	
		result = parseHelper.parse('''
			asm prova
			
				export eq, Philosopher, eq
			
			signature:
				
				abstract domain Philosopher
				
				controlled eq : Philosopher -> Philosopher
				
			definitions:	
		''')
		result.assertError(AsmetalPackage.Literals.EXPORT_CLAUSE, ErrorCode.EXPORT_CLAUSE__CALLED_TWICE)

	}
	
	@Test
	def void testSignature() {
		var Asm result

		result = parseHelper.parse('''
			asm prova

			signature:
				
				abstract domain Philosopher
				abstract domain Philosopher
				
			definitions:	
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_DEFINED_TWICE)
		
		result = parseHelper.parse('''
			asm prova

			signature:
				
				abstract domain Philosopher
				
				controlled doppio : Philosopher
				controlled doppio : Philosopher
				
			definitions:				 
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__FUNCTION_DEFINED_TWICE)
		
		result = parseHelper.parse('''
			asm prova

			signature:
				
				abstract domain Philosopher
				
				controlled doppio : Prod(Philosopher, Philosopher) -> Philosopher
				controlled doppio : Prod(Philosopher, Philosopher) -> Philosopher
				
			definitions:	
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__FUNCTION_DEFINED_TWICE)
		
		// checking overloading
		result = parseHelper.parse('''
			asm prova

			signature:
				
				abstract domain Philosopher
				abstract domain Cherries
				
				controlled doppio : Philosopher
				controlled doppio : Cherries
				
			definitions:			
		''')
		result.assertNoErrors
		
	 	result = parseHelper.parse('''
			asm prova

			signature:
				
				abstract domain Philosopher
				abstract domain Cherries
				
				controlled doppio : Prod(Philosopher, Philosopher) -> Philosopher
				controlled doppio : Prod(Cherries, Cherries) -> Cherries
				
			definitions:	
		''')
		result.assertNoErrors 
	}
	
	@Test
	def void testConcreteDomain() {
		var Asm result

		result = parseHelper.parse('''
			asm prova

			import StandardLibrary

			signature:
				
				domain Cherries subsetof Integer
				domain Prova subsetof Cherries
				
			definitions:				 
		''')	
		result.assertError(AsmetalPackage.Literals.CONCRETE_DOMAIN, ErrorCode.CONCRETE_DOMAIN__INVALID_TYPE_DOMAIN)		
		
		result = parseHelper.parse('''
			asm prova

			signature:
				
				domain Cherries subsetof Prova
				
			definitions:	
		''')	
		result.assertError(AsmetalPackage.Literals.CONCRETE_DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND)
		
		result = parseHelper.parse('''
			asm prova

			import StandardLibrary ( eq )

			signature:
				
				domain Cherries subsetof Integer
				
			definitions:	
		''')	
		result.assertError(AsmetalPackage.Literals.CONCRETE_DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED)

		result = parseHelper.parse('''
			asm prova

			import SmallLibrary

			signature:
				
				domain Cherries subsetof Integer
				
			definitions:	
		''')	
		result.assertError(AsmetalPackage.Literals.CONCRETE_DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED)

		result = parseHelper.parse('''
			asm prova

			import StandardLibrary

			signature:
				
				domain Cherries subsetof Seq(Integer)
				
			definitions:			
		''')
		result.assertNoErrors 
	
	
	}
	
	@Test
	def void testBasicDomain() {
		var Asm result

		result = parseHelper.parse('''
			asm prova

			import StandardLibrary

			signature:
				
				basic domain Prova
				
			definitions:		
		''')	
		result.assertError(AsmetalPackage.Literals.DOMAIN, ErrorCode.BASIC_DOMAIN__INVALID_NAME)		
	}
	
	@Test
	def void testStructuredDomainsNotFound() {
		var Asm result

		result = parseHelper.parse('''
			asm prova
			signature:
				domain Cherries subsetof Bag(Prova)			
			definitions:		 
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND)		
		
		result = parseHelper.parse('''
			asm prova
			signature:	
				domain Cherries subsetof Seq(Prova)				
			definitions:			
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND)		
		
		result = parseHelper.parse('''
			asm prova
			signature:				
				domain Cherries subsetof Powerset(Prova)				
			definitions:		
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND)		
		
		result = parseHelper.parse('''
			asm prova
			signature:				
				domain Cherries subsetof Prod( Seq(Integer), Prova )				
			definitions:		
		''')	
		result.assertError(AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND)		
	
		result = parseHelper.parse('''
			asm prova
			signature:			
				anydomain Prova1				
				domain Cherries subsetof Prod( Seq(Prova), Prova1 )				
			definitions:		
		''')	
		result.assertError(AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND)		
	
		result = parseHelper.parse('''
			asm prova
			signature:			
				anydomain Prova1				
				domain Cherries subsetof Prod( Seq(Prova1), Prova )				
			definitions:		
		''')	
		result.assertError(AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND)	
		
		
	}
	
	@Test
	def void testStructuredDomainsNotImported() {
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			import SmallLibrary
			signature:
				domain Cherries subsetof Bag(Integer)			
			definitions:		
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED)		
		
		result = parseHelper.parse('''
			asm prova
			import SmallLibrary
			signature:	
				domain Cherries subsetof Seq(Integer)				
			definitions:			
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED)		
		
		result = parseHelper.parse('''
			asm prova
			import SmallLibrary
			signature:				
				domain Cherries subsetof Powerset(Integer)				
			definitions:			
		''')
		result.assertError(AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED)		
		
		result = parseHelper.parse('''
			asm prova
			import SmallLibrary
			signature:				
				domain Cherries subsetof Prod( Seq(Char), Integer )				
			definitions:			
		''')	
		result.assertError(AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED)		
	
		result = parseHelper.parse('''
			asm prova
			import SmallLibrary
			signature:			
				anydomain Prova1				
				domain Cherries subsetof Prod( Seq(Integer), Char )				
			definitions:			
		''')	
		result.assertError(AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED)		
	
		result = parseHelper.parse('''
			asm prova
			import SmallLibrary
			signature:			
				anydomain Prova1				
				domain Cherries subsetof Prod( Seq(Prova1), Integer )				
			definitions:		
		''')	
		result.assertError(AsmetalPackage.Literals.DOMAIN, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED)	
		
	}
	
	@Test
	def void testEnum() {
	
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			signature:
				enum domain Prova = { AA | BB | CC | DD | CC }		
			definitions:			
		''')
		result.assertError(AsmetalPackage.Literals.ENUM_ELEMENT, ErrorCode.ENUM_DOMAIN_ALREADY_DECLARED)	
		
		result = parseHelper.parse('''
			asm prova
			signature:
				enum domain Prova1 = { AA | BB | CC | DD }		
				enum domain Prova2 = { AAA | BBB | CC | DDD }	
			definitions:			
		''')
		result.assertError(AsmetalPackage.Literals.ENUM_ELEMENT, ErrorCode.ENUM_DOMAIN_ALREADY_DECLARED)			
	}
	
	@Test
	def void testFunctionDomainNotFound() {
	
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				controlled mancante : Prova		
			definitions:			
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND)		

		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				controlled mancante : Prova	-> Integer	
			definitions:			
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND)		
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				controlled mancante : Integer -> Prova	
			definitions:		
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND)		
		
		result = parseHelper.parse('''
			asm prova
			import StandardLibrary
			signature:
				controlled mancante : Prova	-> Prova
			definitions:		
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND)		

	}
	
	@Test
	def void testFunctionDomainNotImported() {
	
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			import SmallLibrary
			signature:
				controlled mancante : Integer		
			definitions:								
			default init prova  
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED)		

		result = parseHelper.parse('''
			asm prova
			import SmallLibrary
			signature:
				controlled mancante : Char	-> Integer	
			definitions:			
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED)		
		
		result = parseHelper.parse('''
			asm prova
			import SmallLibrary
			signature:
				controlled mancante : Integer -> Char	
			definitions:		
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED)		
		
		result = parseHelper.parse('''
			asm prova
			import SmallLibrary
			signature:
				controlled mancante : Integer	-> Integer
			definitions:		
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION, ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED)		

	}
	
	@Test
	def void testFunction() {
	
		var Asm result
		
		result = parseHelper.parse('''
			asm prova
			signature:
				anydomain D
				local errata : D		
			definitions:		 
		''')
		result.assertError(AsmetalPackage.Literals.FUNCTION, ErrorCode.LOCAL_FUNCTION__INVALID_DECLARATION)		
	}
	
}