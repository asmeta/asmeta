package org.asmeta.xt.tests.parsing.positive

import com.google.inject.Inject
import org.asmeta.xt.asmetal.AbstractTD
import org.asmeta.xt.asmetal.AnyDomain
import org.asmeta.xt.asmetal.ConcreteDomain
import org.asmeta.xt.asmetal.EnumTD
import org.asmeta.xt.asmetal.ProductDomain
import org.asmeta.xt.asmetal.impl.AbstractTDImpl
import org.asmeta.xt.asmetal.impl.AnyDomainImpl
import org.asmeta.xt.asmetal.impl.BagDomainImpl
import org.asmeta.xt.asmetal.impl.ConcreteDomainImpl
import org.asmeta.xt.asmetal.impl.ControlledFunctionImpl
import org.asmeta.xt.asmetal.impl.DerivedFunctionImpl
import org.asmeta.xt.asmetal.impl.EnumTDImpl
import org.asmeta.xt.asmetal.impl.MapDomainImpl
import org.asmeta.xt.asmetal.impl.MonitoredFunctionImpl
import org.asmeta.xt.asmetal.impl.OutFunctionImpl
import org.asmeta.xt.asmetal.impl.PowersetDomainImpl
import org.asmeta.xt.asmetal.impl.ProductDomainImpl
import org.asmeta.xt.asmetal.impl.RuleDomainImpl
import org.asmeta.xt.asmetal.impl.SequenceDomainImpl
import org.asmeta.xt.asmetal.impl.SharedFunctionImpl
import org.asmeta.xt.asmetal.impl.StaticFunctionImpl
import org.asmeta.xt.tests.AsmetaLInjectorProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.asmeta.xt.tests.AsmParseHelper
import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.junit.jupiter.api.Tag

@ExtendWith(InjectionExtension)
@InjectWith(AsmetaLInjectorProvider)
class HeaderParsingTest {
	
	@Inject	AsmParseHelper parseHelper
	@Inject extension ValidationTestHelper
	
	
	@Test@Tag("TestToMavenSkip")
	def void testOnlyImports() {
		
		var i = 0;
		
		var result = parseHelper.parse('''
			asyncr asm only_imports
		//	import moduloprova1.asm
		//	import ../moduloprova2.asm(r_a)
		//	import ../moduloprova3.asm(r_a, ProvaDominio, a)
			import ../STDL/StandardLibrary
			import STDL/StandardLibrary
			import StandardLibrary.asm (Integer, eq)
			signature:
			definitions:
		''',"only_imports")
		// There will be an error because the import "../STDL/StandardLibrary" cannot be found. So, the following
		// line cannot be executed
		//result.assertNoErrors
		
		Assertions.assertEquals( 3, result.headerSection.importClause.size)
	
		Assertions.assertEquals( "../STDL/StandardLibrary", result.headerSection.importClause.get(i).moduleName)
		Assertions.assertEquals( 0, result.headerSection.importClause.get(i).importedList.size)		
		
		i++
		Assertions.assertEquals( "STDL/StandardLibrary", result.headerSection.importClause.get(i).moduleName)
		 
		i++
		Assertions.assertEquals( "StandardLibrary.asm", result.headerSection.importClause.get(i).moduleName)
		Assertions.assertEquals( 2, result.headerSection.importClause.get(i).importedList.size)		
		Assertions.assertEquals( "Integer", result.headerSection.importClause.get(i).importedList.get(0).name)	
		Assertions.assertEquals( "eq", result.headerSection.importClause.get(i).importedList.get(1).name)	
									
		Assertions.assertEquals( result.headerSection.exportClause, null)
	}
	
	@Test
	def void testExportAll() {
		var result = parseHelper.parse('''
			asyncr asm export_all
			export *
			signature:
				anydomain Prova
				controlled provafunzione1 : Prova -> Prova	
			definitions:
		''', "export_all")
		result.assertNoErrors
		
		Assertions.assertEquals( 0, result.headerSection.importClause.size )	
		
		Assertions.assertEquals( 0, result.headerSection.exportClause.exportedList.size )	
		Assertions.assertEquals( true, result.headerSection.exportClause.exportAll )			
	}
	
	@Test
	def void testSingleExport() {
		
		var result = parseHelper.parse('''
			asyncr asm single_export
			export provafunzione
			signature:
				anydomain Prova
				controlled provafunzione : Prova -> Prova		
			definitions:
		''',"single_export")
		result.assertNoErrors
		
		Assertions.assertEquals( result.headerSection.importClause.size, 0)	
		
		Assertions.assertEquals( 1, result.headerSection.exportClause.exportedList.size)	
		Assertions.assertEquals( "provafunzione", result.headerSection.exportClause.exportedList.get(0) )	
		Assertions.assertEquals( false, result.headerSection.exportClause.exportAll )			

	}
	
	@Test@Tag("TestToMavenSkip")
	def void testMultipleExport() {
				
		var result = parseHelper.parse('''		
			asm multiple_export
			export provafunzione1, Prova
			signature:
				anydomain Prova
				controlled provafunzione1 : Prova -> Prova	
				controlled provafunzione2 : Prova -> Prova	
			
			definitions:
		''')
		result.assertNoErrors
		
		Assertions.assertEquals( result.headerSection.importClause.size, 0)	
		
		Assertions.assertEquals( 2, result.headerSection.exportClause.exportedList.size )	
		Assertions.assertEquals( "provafunzione1", result.headerSection.exportClause.exportedList.get(0) )
		Assertions.assertEquals( "Prova", result.headerSection.exportClause.exportedList.get(1) )		
		Assertions.assertEquals( false, result.headerSection.exportClause.exportAll )	
	}

	@Test@Tag("TestToMavenSkip")
	def void testConcreteDomain() {
		var result = parseHelper.parse('''
			asm concrete_domain
			
				import StandardLibrary
			
			signature:
				dynamic domain Prova1 subsetof Integer
				domain Book subsetof Prod( String, String, Prova1 )
				domain Prova2 subsetof Integer
			definitions:
		''')
		result.assertNoErrors
		
		var i = 0
		
		Assertions.assertEquals( 3, result.headerSection.signature.domain.size)	
		Assertions.assertEquals( 0, result.headerSection.signature.function.size)	
		
		//	dynamic domain Prova1 subsetof Integer
		var dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( typeof(ConcreteDomainImpl), dom.class )
		var temp = dom as ConcreteDomain
		Assertions.assertEquals( true, temp.dynamic )
		Assertions.assertEquals( "Prova1", temp.name )
		Assertions.assertEquals( "Integer", temp.typeDomain.name )
		
		// --- domain Book subsetof Prod( Isbn, Title, AuthorSurname )
		i++
		dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( typeof(ConcreteDomainImpl), dom.class )
		temp = dom as ConcreteDomain
		Assertions.assertEquals( false, temp.dynamic )
		Assertions.assertEquals( "Book", temp.name )
		// Prod( Isbn, Title, AuthorSurname )
		Assertions.assertEquals( typeof(ProductDomainImpl), temp.typeDomain.class )
		var tem_type = temp.typeDomain as ProductDomain
		Assertions.assertEquals( "String", tem_type.domains.get(0).name )
		Assertions.assertEquals( "String", tem_type.domains.get(1).name )
		Assertions.assertEquals( "Prova1", tem_type.domains.get(2).name )
		
		// domain Prova2 subsetof Integer
		i++
		dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( typeof(ConcreteDomainImpl), dom.class )
		temp = dom as ConcreteDomain
		Assertions.assertEquals( false, temp.dynamic )
		Assertions.assertEquals( "Prova2", temp.name )
		Assertions.assertEquals( "Integer", temp.typeDomain.name )
	}
	
	@Test@Tag("TestToMavenSkip")
	def void testTypeDomain() {
		var result = parseHelper.parse('''
			asm type_domain
			signature:
				anydomain Prova1
				enum domain Prova2 = { AA }
				enum domain Prova3 = { CC | BB }
				dynamic abstract domain Prova4
				abstract domain Prova5
			definitions:
		''')
		result.assertNoErrors
		
		var i = 0		
		
		Assertions.assertEquals( 5, result.headerSection.signature.domain.size)	
		Assertions.assertEquals( 0, result.headerSection.signature.function.size)	
		
		// anydomain prova1
		var dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( typeof(AnyDomainImpl), dom.class )
		var temp1 = dom as AnyDomain
		Assertions.assertEquals( "Prova1", temp1.name )
		
		// enum domain Prova2 = { aa }
		i++
		dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( typeof(EnumTDImpl), dom.class )
		var temp2 = dom as EnumTD
		Assertions.assertEquals( "Prova2", temp2.name )
		Assertions.assertEquals( 1, temp2.element.size )
		Assertions.assertEquals( "AA", temp2.element.get(0).symbol )
		
		// enum domain Prova3 = { aa | bb }
		i++
		dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( typeof(EnumTDImpl), dom.class )
		var temp3 = dom as EnumTD
		Assertions.assertEquals( "Prova3", temp3.name )
		Assertions.assertEquals( 2, temp3.element.size )
		Assertions.assertEquals( "CC", temp3.element.get(0).symbol )
		Assertions.assertEquals( "BB", temp3.element.get(1).symbol )
		
		// dynamic abstract domain prova4
		i++
		dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( typeof(AbstractTDImpl), dom.class )
		var temp4 = dom as AbstractTD
		Assertions.assertEquals( true, temp4.dynamic )
		Assertions.assertEquals( "Prova4", temp4.name )
		
		// abstract domain Prova5
		i++
		dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( typeof(AbstractTDImpl), dom.class )
		var temp5 = dom as AbstractTD
		Assertions.assertEquals( false, temp5.dynamic )
		Assertions.assertEquals( "Prova5", temp5.name )
		
		// basic domain Prova6
		/*i++
		dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( typeof(BasicTDImpl), dom.class )
		var temp6 = dom as BasicTD
		Assertions.assertEquals( "Prova6", temp6.name )*/
	}
	
	@Test@Tag("TestToMavenSkip")
	def void testStructuredDomain() {
		var result = parseHelper.parse('''
			asm structured_domain
			
				import StandardLibrary
			
			signature:
				domain Prova1 subsetof Prod( Integer, Complex )
				domain Prova2 subsetof Prod( Integer, Integer, Complex )
				domain Prova3 subsetof Seq( Complex )
				domain Prova4 subsetof Powerset( Integer )
				domain Prova5 subsetof Bag( Complex )
				domain Prova6 subsetof Map( String, String )
			definitions:
		''',"structured_domain")
		result.assertNoErrors
		
		var i = 0
		
		Assertions.assertEquals( 6, result.headerSection.signature.domain.size)	
		Assertions.assertEquals( 0, result.headerSection.signature.function.size)	
		
		// domain Prova1 subsetof Prod( Integer, Complex )
		var conc_dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( "Prova1", conc_dom.name )
		Assertions.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )
		var dom1 = (conc_dom as ConcreteDomainImpl).typeDomain
		// Prod( prova1, prova2 )
		Assertions.assertEquals( typeof(ProductDomainImpl), dom1.class )
		var temp1 = dom1 as ProductDomain
		Assertions.assertEquals( 2, temp1.domains.size )
		Assertions.assertEquals( "Integer", temp1.domains.get(0).name )
		Assertions.assertEquals( "Complex", temp1.domains.get(1).name )
		
		// domain Prova2 subsetof Prod( Integer, Integer, Complex )
		i++
		conc_dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( "Prova2", conc_dom.name )
		Assertions.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )
		dom1 = (conc_dom as ConcreteDomainImpl).typeDomain
		// Prod( Integer, Integer, Complex )
		Assertions.assertEquals( typeof(ProductDomainImpl), dom1.class )
		temp1 = dom1 as ProductDomain
		Assertions.assertEquals( 3, temp1.domains.size )
		Assertions.assertEquals( "Integer", temp1.domains.get(0).name )
		Assertions.assertEquals( "Integer", temp1.domains.get(1).name )
		Assertions.assertEquals( "Complex", temp1.domains.get(2).name )
		
		// domain Prova3 subsetof Seq( Complex )
		i++
		conc_dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( "Prova3", conc_dom.name )
		Assertions.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )
		var dom2 = (conc_dom as ConcreteDomainImpl).typeDomain
		// Seq( Complex )
		Assertions.assertEquals( typeof(SequenceDomainImpl), dom2.class )
		var temp2 = dom2 as SequenceDomainImpl
		Assertions.assertEquals( "Complex", temp2.domain.name )
		
		// domain Prova4 subsetof Powerset( Integer )
		i++
		conc_dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( "Prova4", conc_dom.name )
		Assertions.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )
		var dom3 = (conc_dom as ConcreteDomainImpl).typeDomain
		// Powerset( Integer )
		Assertions.assertEquals( typeof(PowersetDomainImpl), dom3.class )
		var temp3 = dom3 as PowersetDomainImpl
		Assertions.assertEquals( "Integer", temp3.baseDomain.name )
		
		// domain Prova5 subsetof Bag( Complex )
		i++
		conc_dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( "Prova5", conc_dom.name )
		Assertions.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )
		var dom4 = (conc_dom as ConcreteDomainImpl).typeDomain
		// Bag( Complex )
		Assertions.assertEquals( typeof(BagDomainImpl), dom4.class )
		var temp4 = dom4 as BagDomainImpl
		Assertions.assertEquals( "Complex", temp4.domain.name )
		
		// domain Prova6 subsetof Map( String, String )
		i++
		conc_dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( "Prova6", conc_dom.name )
		Assertions.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )
		var dom5 = (conc_dom as ConcreteDomainImpl).typeDomain
		// Map( String, String )
		Assertions.assertEquals( typeof(MapDomainImpl), dom5.class )
		var temp5 = dom5 as MapDomainImpl
		Assertions.assertEquals( "String", temp5.sourceDomain.name )
		Assertions.assertEquals( "String", temp5.targetDomain.name )
	}

	@Test@Tag("TestToMavenSkip")
	def void testMultipleStructuredDomain() {
		var result = parseHelper.parse('''
			asm multi_structured_domain
			
				import StandardLibrary
			
			signature:
				domain Prova1 subsetof Prod( Bag( String ), Integer )
				domain Prova2 subsetof Seq( Powerset( Complex ) )
			definitions:
		''')
		result.assertNoErrors
		
		var i = 0
		
		Assertions.assertEquals( 2, result.headerSection.signature.domain.size)	
		Assertions.assertEquals( 0, result.headerSection.signature.function.size)	
		
		// --- domain Prova1 subsetof Prod( Bag( String ), Integer )
		var conc_dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( "Prova1", conc_dom.name )
		Assertions.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )	
		// -- Prod( Bag( String ), Integer )
		var dom1 = (conc_dom as ConcreteDomainImpl).typeDomain
		Assertions.assertEquals( typeof(ProductDomainImpl), dom1.class )
		var temp1 = dom1 as ProductDomainImpl
		Assertions.assertEquals( 2, temp1.domains.size )	
		// - Bag( String )
		var dom1_child1 = temp1.domains.get(0)
		Assertions.assertEquals( typeof(BagDomainImpl), dom1_child1.class )
		var temp1_child1 = dom1_child1 as BagDomainImpl
		Assertions.assertEquals( "String", temp1_child1.domain.name )
		// Integer
		Assertions.assertEquals( "Integer", temp1.domains.get(1).name )
		
		// --- domain Prova2 subsetof Seq( Powerset( Complex ) )
		i++
		conc_dom = result.headerSection.signature.domain.get(i)
		Assertions.assertEquals( "Prova2", conc_dom.name )
		Assertions.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )	
		// Seq( Powerset( Complex ) )
		dom1 = (conc_dom as ConcreteDomainImpl).typeDomain
		Assertions.assertEquals( typeof(SequenceDomainImpl), dom1.class )	
		var temp2 = dom1 as SequenceDomainImpl
		// Powerset( Complex )
		dom1_child1 = temp2.domain
		Assertions.assertEquals( typeof(PowersetDomainImpl), dom1_child1.class )
		var temp2_child1 = dom1_child1 as PowersetDomainImpl
		Assertions.assertEquals( "Complex", temp2_child1.baseDomain.name )
		
		
	}

	@Test
	def void testStaticFunction() {
		var result = parseHelper.parse('''
			asm static_function
				
				import StandardLibrary
			
			signature:
				dynamic domain Prova1 subsetof Integer
				static prova1 : Integer
				static prova2 : Integer -> Prova1
				static prova3 : Prod(String, Integer)
				static prova4 : Prod(Integer, Natural) -> Bag( Integer )
				static prova5 : Prod( Seq(Integer), Natural) -> Bag( Integer )
				static program: Agent -> Rule	
			definitions:
		''',"static_function")
		result.assertNoErrors
		
		var i = 0

		Assertions.assertEquals( 1, result.headerSection.signature.domain.size)	
		Assertions.assertEquals( 6, result.headerSection.signature.function.size)

		// static prova1 : Integer
		var func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(StaticFunctionImpl), func.class )	
		Assertions.assertEquals( "prova1", func.name )
		Assertions.assertEquals( null, func.domain )
		Assertions.assertEquals( "Integer", func.codomain.name )
		
		// static prova2 : Integer -> Prova1
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(StaticFunctionImpl), func.class )	
		Assertions.assertEquals( "prova2", func.name )
		Assertions.assertEquals( "Integer", func.domain.name )
		Assertions.assertEquals( "Prova1", func.codomain.name )
		
		// --- static prova3 : Prod(String, Integer)
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(StaticFunctionImpl), func.class )	
		Assertions.assertEquals( "prova3", func.name )
		Assertions.assertEquals( null, func.domain )
		// Prod(String, String)
		Assertions.assertEquals( typeof(ProductDomainImpl), func.codomain.class )	
		var cod1 = func.codomain as ProductDomainImpl
		Assertions.assertEquals( 2, cod1.domains.size )	
		Assertions.assertEquals( "String", cod1.domains.get(0).name )	
		Assertions.assertEquals( "Integer", cod1.domains.get(1).name )	
		
		// ---  static prova4 : Prod(Integer, Natural) -> Bag( Integer )
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(StaticFunctionImpl), func.class )
		Assertions.assertEquals( "prova4", func.name )
		// Prod(Integer, Natural)
		Assertions.assertEquals( typeof(ProductDomainImpl), func.domain.class )	
		var dom1 = func.domain as ProductDomainImpl
		Assertions.assertEquals( 2, dom1.domains.size )	
		Assertions.assertEquals( "Integer", dom1.domains.get(0).name )	
		Assertions.assertEquals( "Natural", dom1.domains.get(1).name )	
		// Bag( Integer )
		Assertions.assertEquals( typeof(BagDomainImpl), func.codomain.class )	
		var cod2 = func.codomain as BagDomainImpl
		Assertions.assertEquals( "Integer", cod2.domain.name )	
		
		// --- static prova5 : Prod( Seq(Integer), Natural) -> Bag( Integer )
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(StaticFunctionImpl), func.class )
		Assertions.assertEquals( "prova5", func.name )
		// -- Prod( Seq(Integer), Natural)
		Assertions.assertEquals( typeof(ProductDomainImpl), func.domain.class )	
		dom1 = func.domain as ProductDomainImpl
		Assertions.assertEquals( 2, dom1.domains.size )	
		// Seq(Integer)
		Assertions.assertEquals( typeof(SequenceDomainImpl), dom1.domains.get(0).class )	
		var dom1_child = dom1.domains.get(0) as SequenceDomainImpl
		Assertions.assertEquals( "Integer", dom1_child.domain.name )	
		// Natural
		Assertions.assertEquals( "Natural", dom1.domains.get(1).name )	
		// Bag( Integer )
		Assertions.assertEquals( typeof(BagDomainImpl), func.codomain.class )	
		cod2 = func.codomain as BagDomainImpl
		Assertions.assertEquals( "Integer", cod2.domain.name )	
		
		// static program: Agent -> Rule	
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(StaticFunctionImpl), func.class )	
		Assertions.assertEquals( "program", func.name )
		Assertions.assertEquals( "Agent", func.domain.name )
		Assertions.assertEquals( typeof(RuleDomainImpl), func.codomain.class )
				
	}
	
	@Test@Tag("TestToMavenSkip")
	def void testDynamicFunction() {
		var result = parseHelper.parse('''
			asm dynamic_function
			
				import StandardLibrary
			
			signature:
				dynamic domain Prova1 subsetof Integer
			
				/*dynamic local prova11 : String
				dynamic local prova12 : Prod(String, Complex) -> Bag( Integer )
				local prova13 : Integer
				local prova14 : Complex -> Integer*/
				
				dynamic controlled prova21 : String
				dynamic controlled prova22 : Integer -> String
				controlled prova23 : String
				controlled prova24 : Integer -> Complex
				
				dynamic shared prova31 : String
				dynamic shared prova32 : Integer -> String
				shared prova33 : Complex
				shared prova34 : String -> Integer
				
				dynamic monitored prova41 : String
				dynamic monitored prova42 : Prova1 -> String
				monitored prova43 : String
				monitored prova44 : String -> Complex
				
				dynamic out prova51 : Complex
				dynamic out prova52 : String -> String
				out prova53 : String
				out prova54 : Integer -> String	
														
			definitions:
		''')
		result.assertNoErrors
		
		var i = 0
		
		Assertions.assertEquals( 1, result.headerSection.signature.domain.size)	
		Assertions.assertEquals( 16, result.headerSection.signature.function.size)	
		
		// dynamic controlled prova21 : String
		//i++
		var func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(ControlledFunctionImpl), func.class )	
		var con_func = func as ControlledFunctionImpl
		Assertions.assertEquals( true, con_func.dynamic )
		Assertions.assertEquals( "prova21", con_func.name )
		Assertions.assertEquals( null, con_func.domain )
		Assertions.assertEquals( "String", con_func.codomain.name )
		
		// dynamic controlled prova22 : Integer -> String
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(ControlledFunctionImpl), func.class )	
		con_func = func as ControlledFunctionImpl
		Assertions.assertEquals( true, con_func.dynamic )
		Assertions.assertEquals( "prova22", con_func.name )
		Assertions.assertEquals( "Integer", con_func.domain.name )
		Assertions.assertEquals( "String", con_func.codomain.name )
		
		// controlled prova23 : String
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(ControlledFunctionImpl), func.class )	
		con_func = func as ControlledFunctionImpl
		Assertions.assertEquals( false, con_func.dynamic )
		Assertions.assertEquals( "prova23", con_func.name )
		Assertions.assertEquals( null, con_func.domain )
		Assertions.assertEquals( "String", con_func.codomain.name )
		
		// controlled prova24 : Integer -> Complex
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(ControlledFunctionImpl), func.class )	
		con_func = func as ControlledFunctionImpl
		Assertions.assertEquals( false, con_func.dynamic )
		Assertions.assertEquals( "prova24", con_func.name )
		Assertions.assertEquals( "Integer", con_func.domain.name )
		Assertions.assertEquals( "Complex", con_func.codomain.name )
				
		// dynamic shared prova31 : String
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(SharedFunctionImpl), func.class )	
		var shared_fun = func as SharedFunctionImpl
		Assertions.assertEquals( true, shared_fun.dynamic )
		Assertions.assertEquals( "prova31", shared_fun.name )
		Assertions.assertEquals( null, shared_fun.domain )
		Assertions.assertEquals( "String", shared_fun.codomain.name )
		
		// dynamic shared prova32 : Integer -> String
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(SharedFunctionImpl), func.class )	
		shared_fun = func as SharedFunctionImpl
		Assertions.assertEquals( true, shared_fun.dynamic )
		Assertions.assertEquals( "prova32", shared_fun.name )
		Assertions.assertEquals( "Integer", shared_fun.domain.name )
		Assertions.assertEquals( "String", shared_fun.codomain.name )
		
		// shared prova33 : Complex
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(SharedFunctionImpl), func.class )	
		shared_fun = func as SharedFunctionImpl
		Assertions.assertEquals( false, shared_fun.dynamic )
		Assertions.assertEquals( "prova33", shared_fun.name )
		Assertions.assertEquals( null, shared_fun.domain )
		Assertions.assertEquals( "Complex", shared_fun.codomain.name )
		
		// shared prova34 : String -> Integer
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(SharedFunctionImpl), func.class )	
		shared_fun = func as SharedFunctionImpl
		Assertions.assertEquals( false, shared_fun.dynamic )
		Assertions.assertEquals( "prova34", shared_fun.name )
		Assertions.assertEquals( "String", shared_fun.domain.name )
		Assertions.assertEquals( "Integer", shared_fun.codomain.name )
				
		// dynamic monitored prova41 : String
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(MonitoredFunctionImpl), func.class )	
		var mon_func = func as MonitoredFunctionImpl
		Assertions.assertEquals( true, mon_func.dynamic )
		Assertions.assertEquals( "prova41", mon_func.name )
		Assertions.assertEquals( null, mon_func.domain )
		Assertions.assertEquals( "String", mon_func.codomain.name )
		
		// dynamic monitored prova42 : Prova1 -> String
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(MonitoredFunctionImpl), func.class )	
		mon_func = func as MonitoredFunctionImpl
		Assertions.assertEquals( true, mon_func.dynamic )
		Assertions.assertEquals( "prova42", mon_func.name )
		Assertions.assertEquals( "Prova1", mon_func.domain.name )
		Assertions.assertEquals( "String", mon_func.codomain.name )
		
		// monitored prova43 : String
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(MonitoredFunctionImpl), func.class )	
		mon_func = func as MonitoredFunctionImpl
		Assertions.assertEquals( false, mon_func.dynamic )
		Assertions.assertEquals( "prova43", mon_func.name )
		Assertions.assertEquals( null, mon_func.domain )
		Assertions.assertEquals( "String", mon_func.codomain.name )
		
		// monitored prova44 : String -> Complex
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(MonitoredFunctionImpl), func.class )	
		mon_func = func as MonitoredFunctionImpl
		Assertions.assertEquals( false, mon_func.dynamic )
		Assertions.assertEquals( "prova44", mon_func.name )
		Assertions.assertEquals( "String", mon_func.domain.name )
		Assertions.assertEquals( "Complex", mon_func.codomain.name )	
				
		// dynamic out prova51 : Complex
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(OutFunctionImpl), func.class )	
		var fun_out = func as OutFunctionImpl
		Assertions.assertEquals( true, fun_out.dynamic )
		Assertions.assertEquals( "prova51", fun_out.name )
		Assertions.assertEquals( null, fun_out.domain )
		Assertions.assertEquals( "Complex", fun_out.codomain.name )
		
		// dynamic out prova52 : String -> String
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(OutFunctionImpl), func.class )	
		fun_out = func as OutFunctionImpl
		Assertions.assertEquals( true, fun_out.dynamic )
		Assertions.assertEquals( "prova52", fun_out.name )
		Assertions.assertEquals( "String", fun_out.domain.name )
		Assertions.assertEquals( "String", fun_out.codomain.name )
		
		// out prova53 : String
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(OutFunctionImpl), func.class )	
		fun_out = func as OutFunctionImpl
		Assertions.assertEquals( false, fun_out.dynamic )
		Assertions.assertEquals( "prova53", fun_out.name )
		Assertions.assertEquals( null, fun_out.domain )
		Assertions.assertEquals( "String", fun_out.codomain.name )
		
		// out prova54 : Integer -> String			
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(OutFunctionImpl), func.class )	
		fun_out = func as OutFunctionImpl
		Assertions.assertEquals( false, fun_out.dynamic )
		Assertions.assertEquals( "prova54", fun_out.name )
		Assertions.assertEquals( "Integer", fun_out.domain.name )
		Assertions.assertEquals( "String", fun_out.codomain.name )
		
		
	}
	
	@Test@Tag("TestToMavenSkip")
	def void testDerivedFunction() {
		var result = parseHelper.parse('''
			asm derived_function
				
				import StandardLibrary
			
			signature:
				dynamic domain Prova1 subsetof Integer
			
				derived prova1 : String
				derived prova2 : Prova1 -> String
			definitions:
		''')
		result.assertNoErrors
		
		var i = 0
		
		Assertions.assertEquals( 1, result.headerSection.signature.domain.size)	
		Assertions.assertEquals( 2, result.headerSection.signature.function.size)
		
		// derived prova1 : Integer
		var func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(DerivedFunctionImpl), func.class )	
		Assertions.assertEquals( "prova1", func.name )
		Assertions.assertEquals( null, func.domain )
		Assertions.assertEquals( "String", func.codomain.name )
		
		//  derived prova2 : Prova1 -> String
		i++
		func = result.headerSection.signature.function.get(i)
		Assertions.assertEquals( typeof(DerivedFunctionImpl), func.class )	
		Assertions.assertEquals( "prova2", func.name )
		Assertions.assertEquals( "Prova1", func.domain.name )
		Assertions.assertEquals( "String", func.codomain.name )
		
	}	
	
	
	@Test@Tag("TestToMavenSkip")
	def void testImportsAbsPathWin() {		
		var result = parseHelper.parse('''
asm __tempAsmetaV7859023612479832841
import D:\\AgHome\\progettidaSVNGIT\\asmeta\\asmeta_based_applications\\fMVC\\AMAN\\model\\StandardLibrary
import D:\\AgHome\\progettidaSVNGIT\\asmeta\\asmeta_based_applications\\fMVC\\AMAN\\model\\CTLlibrary
import D:\\AgHome\\progettidaSVNGIT\\asmeta\\asmeta_based_applications\\fMVC\\AMAN\\model\\TimeLibrary
signature:
			definitions:
		''')
		result.assertNoErrors
		Assertions.assertEquals( 3, result.headerSection.importClause.size)
	}
	@Test@Tag("TestToMavenSkip")
	def void testPathImports() {		
		var result = parseHelper.parse('''
asm __tempAsmetaV7859023612479832841
import a/b
import \\AgHome\\progettidaSVNGIT\\asmeta\\asmeta_based_applications\\fMVC\\AMAN\\model\\CTLlibrary
signature:
			definitions:
		''', "__tempAsmetaV7859023612479832841")
		result.assertNoErrors
		Assertions.assertEquals( 3, result.headerSection.importClause.size)
	}

	@Test@Tag("TestToMavenSkip")
	def void testRelative() {		
		var result = parseHelper.parse('''
asm prova
import ../a/b
signature:
			definitions:
		''')
		result.assertNoErrors
		Assertions.assertEquals( 3, result.headerSection.importClause.size)
	}

	
	
	
}