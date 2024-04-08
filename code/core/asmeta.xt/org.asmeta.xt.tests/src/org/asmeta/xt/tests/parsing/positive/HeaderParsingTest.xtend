package org.asmeta.xt.tests.parsing.positive

import com.google.inject.Inject
import org.asmeta.xt.asmetal.AbstractTD
import org.asmeta.xt.asmetal.AnyDomain
import org.asmeta.xt.asmetal.Asm
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
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.asmeta.xt.tests.AsmParseHelper

@RunWith(XtextRunner)
@InjectWith(AsmetaLInjectorProvider)
class HeaderParsingTest {
	
	@Inject	AsmParseHelper parseHelper
	@Inject extension ValidationTestHelper
	
	
	@Test
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
		
		Assert.assertEquals( 3, result.headerSection.importClause.size)
	
		Assert.assertEquals( "../STDL/StandardLibrary", result.headerSection.importClause.get(i).moduleName)
		Assert.assertEquals( 0, result.headerSection.importClause.get(i).importedList.size)		
		
		i++
		Assert.assertEquals( "STDL/StandardLibrary", result.headerSection.importClause.get(i).moduleName)
		 
		i++
		Assert.assertEquals( "StandardLibrary.asm", result.headerSection.importClause.get(i).moduleName)
		Assert.assertEquals( 2, result.headerSection.importClause.get(i).importedList.size)		
		Assert.assertEquals( "Integer", result.headerSection.importClause.get(i).importedList.get(0).name)	
		Assert.assertEquals( "eq", result.headerSection.importClause.get(i).importedList.get(1).name)	
									
		Assert.assertEquals( result.headerSection.exportClause, null)
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
		
		Assert.assertEquals( 0, result.headerSection.importClause.size )	
		
		Assert.assertEquals( 0, result.headerSection.exportClause.exportedList.size )	
		Assert.assertEquals( true, result.headerSection.exportClause.exportAll )			
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
		
		Assert.assertEquals( result.headerSection.importClause.size, 0)	
		
		Assert.assertEquals( 1, result.headerSection.exportClause.exportedList.size)	
		Assert.assertEquals( "provafunzione", result.headerSection.exportClause.exportedList.get(0) )	
		Assert.assertEquals( false, result.headerSection.exportClause.exportAll )			

	}
	
	@Test
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
		
		Assert.assertEquals( result.headerSection.importClause.size, 0)	
		
		Assert.assertEquals( 2, result.headerSection.exportClause.exportedList.size )	
		Assert.assertEquals( "provafunzione1", result.headerSection.exportClause.exportedList.get(0) )
		Assert.assertEquals( "Prova", result.headerSection.exportClause.exportedList.get(1) )		
		Assert.assertEquals( false, result.headerSection.exportClause.exportAll )	
	}

	@Test
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
		
		Assert.assertEquals( 3, result.headerSection.signature.domain.size)	
		Assert.assertEquals( 0, result.headerSection.signature.function.size)	
		
		//	dynamic domain Prova1 subsetof Integer
		var dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( typeof(ConcreteDomainImpl), dom.class )
		var temp = dom as ConcreteDomain
		Assert.assertEquals( true, temp.dynamic )
		Assert.assertEquals( "Prova1", temp.name )
		Assert.assertEquals( "Integer", temp.typeDomain.name )
		
		// --- domain Book subsetof Prod( Isbn, Title, AuthorSurname )
		i++
		dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( typeof(ConcreteDomainImpl), dom.class )
		temp = dom as ConcreteDomain
		Assert.assertEquals( false, temp.dynamic )
		Assert.assertEquals( "Book", temp.name )
		// Prod( Isbn, Title, AuthorSurname )
		Assert.assertEquals( typeof(ProductDomainImpl), temp.typeDomain.class )
		var tem_type = temp.typeDomain as ProductDomain
		Assert.assertEquals( "String", tem_type.domains.get(0).name )
		Assert.assertEquals( "String", tem_type.domains.get(1).name )
		Assert.assertEquals( "Prova1", tem_type.domains.get(2).name )
		
		// domain Prova2 subsetof Integer
		i++
		dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( typeof(ConcreteDomainImpl), dom.class )
		temp = dom as ConcreteDomain
		Assert.assertEquals( false, temp.dynamic )
		Assert.assertEquals( "Prova2", temp.name )
		Assert.assertEquals( "Integer", temp.typeDomain.name )
	}
	
	@Test
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
		
		Assert.assertEquals( 5, result.headerSection.signature.domain.size)	
		Assert.assertEquals( 0, result.headerSection.signature.function.size)	
		
		// anydomain prova1
		var dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( typeof(AnyDomainImpl), dom.class )
		var temp1 = dom as AnyDomain
		Assert.assertEquals( "Prova1", temp1.name )
		
		// enum domain Prova2 = { aa }
		i++
		dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( typeof(EnumTDImpl), dom.class )
		var temp2 = dom as EnumTD
		Assert.assertEquals( "Prova2", temp2.name )
		Assert.assertEquals( 1, temp2.element.size )
		Assert.assertEquals( "AA", temp2.element.get(0).symbol )
		
		// enum domain Prova3 = { aa | bb }
		i++
		dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( typeof(EnumTDImpl), dom.class )
		var temp3 = dom as EnumTD
		Assert.assertEquals( "Prova3", temp3.name )
		Assert.assertEquals( 2, temp3.element.size )
		Assert.assertEquals( "CC", temp3.element.get(0).symbol )
		Assert.assertEquals( "BB", temp3.element.get(1).symbol )
		
		// dynamic abstract domain prova4
		i++
		dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( typeof(AbstractTDImpl), dom.class )
		var temp4 = dom as AbstractTD
		Assert.assertEquals( true, temp4.dynamic )
		Assert.assertEquals( "Prova4", temp4.name )
		
		// abstract domain Prova5
		i++
		dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( typeof(AbstractTDImpl), dom.class )
		var temp5 = dom as AbstractTD
		Assert.assertEquals( false, temp5.dynamic )
		Assert.assertEquals( "Prova5", temp5.name )
		
		// basic domain Prova6
		/*i++
		dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( typeof(BasicTDImpl), dom.class )
		var temp6 = dom as BasicTD
		Assert.assertEquals( "Prova6", temp6.name )*/
	}
	
	@Test
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
		
		Assert.assertEquals( 6, result.headerSection.signature.domain.size)	
		Assert.assertEquals( 0, result.headerSection.signature.function.size)	
		
		// domain Prova1 subsetof Prod( Integer, Complex )
		var conc_dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( "Prova1", conc_dom.name )
		Assert.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )
		var dom1 = (conc_dom as ConcreteDomainImpl).typeDomain
		// Prod( prova1, prova2 )
		Assert.assertEquals( typeof(ProductDomainImpl), dom1.class )
		var temp1 = dom1 as ProductDomain
		Assert.assertEquals( 2, temp1.domains.size )
		Assert.assertEquals( "Integer", temp1.domains.get(0).name )
		Assert.assertEquals( "Complex", temp1.domains.get(1).name )
		
		// domain Prova2 subsetof Prod( Integer, Integer, Complex )
		i++
		conc_dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( "Prova2", conc_dom.name )
		Assert.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )
		dom1 = (conc_dom as ConcreteDomainImpl).typeDomain
		// Prod( Integer, Integer, Complex )
		Assert.assertEquals( typeof(ProductDomainImpl), dom1.class )
		temp1 = dom1 as ProductDomain
		Assert.assertEquals( 3, temp1.domains.size )
		Assert.assertEquals( "Integer", temp1.domains.get(0).name )
		Assert.assertEquals( "Integer", temp1.domains.get(1).name )
		Assert.assertEquals( "Complex", temp1.domains.get(2).name )
		
		// domain Prova3 subsetof Seq( Complex )
		i++
		conc_dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( "Prova3", conc_dom.name )
		Assert.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )
		var dom2 = (conc_dom as ConcreteDomainImpl).typeDomain
		// Seq( Complex )
		Assert.assertEquals( typeof(SequenceDomainImpl), dom2.class )
		var temp2 = dom2 as SequenceDomainImpl
		Assert.assertEquals( "Complex", temp2.domain.name )
		
		// domain Prova4 subsetof Powerset( Integer )
		i++
		conc_dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( "Prova4", conc_dom.name )
		Assert.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )
		var dom3 = (conc_dom as ConcreteDomainImpl).typeDomain
		// Powerset( Integer )
		Assert.assertEquals( typeof(PowersetDomainImpl), dom3.class )
		var temp3 = dom3 as PowersetDomainImpl
		Assert.assertEquals( "Integer", temp3.baseDomain.name )
		
		// domain Prova5 subsetof Bag( Complex )
		i++
		conc_dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( "Prova5", conc_dom.name )
		Assert.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )
		var dom4 = (conc_dom as ConcreteDomainImpl).typeDomain
		// Bag( Complex )
		Assert.assertEquals( typeof(BagDomainImpl), dom4.class )
		var temp4 = dom4 as BagDomainImpl
		Assert.assertEquals( "Complex", temp4.domain.name )
		
		// domain Prova6 subsetof Map( String, String )
		i++
		conc_dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( "Prova6", conc_dom.name )
		Assert.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )
		var dom5 = (conc_dom as ConcreteDomainImpl).typeDomain
		// Map( String, String )
		Assert.assertEquals( typeof(MapDomainImpl), dom5.class )
		var temp5 = dom5 as MapDomainImpl
		Assert.assertEquals( "String", temp5.sourceDomain.name )
		Assert.assertEquals( "String", temp5.targetDomain.name )
	}

	@Test
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
		
		Assert.assertEquals( 2, result.headerSection.signature.domain.size)	
		Assert.assertEquals( 0, result.headerSection.signature.function.size)	
		
		// --- domain Prova1 subsetof Prod( Bag( String ), Integer )
		var conc_dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( "Prova1", conc_dom.name )
		Assert.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )	
		// -- Prod( Bag( String ), Integer )
		var dom1 = (conc_dom as ConcreteDomainImpl).typeDomain
		Assert.assertEquals( typeof(ProductDomainImpl), dom1.class )
		var temp1 = dom1 as ProductDomainImpl
		Assert.assertEquals( 2, temp1.domains.size )	
		// - Bag( String )
		var dom1_child1 = temp1.domains.get(0)
		Assert.assertEquals( typeof(BagDomainImpl), dom1_child1.class )
		var temp1_child1 = dom1_child1 as BagDomainImpl
		Assert.assertEquals( "String", temp1_child1.domain.name )
		// Integer
		Assert.assertEquals( "Integer", temp1.domains.get(1).name )
		
		// --- domain Prova2 subsetof Seq( Powerset( Complex ) )
		i++
		conc_dom = result.headerSection.signature.domain.get(i)
		Assert.assertEquals( "Prova2", conc_dom.name )
		Assert.assertEquals( typeof(ConcreteDomainImpl), conc_dom.class )	
		// Seq( Powerset( Complex ) )
		dom1 = (conc_dom as ConcreteDomainImpl).typeDomain
		Assert.assertEquals( typeof(SequenceDomainImpl), dom1.class )	
		var temp2 = dom1 as SequenceDomainImpl
		// Powerset( Complex )
		dom1_child1 = temp2.domain
		Assert.assertEquals( typeof(PowersetDomainImpl), dom1_child1.class )
		var temp2_child1 = dom1_child1 as PowersetDomainImpl
		Assert.assertEquals( "Complex", temp2_child1.baseDomain.name )
		
		
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

		Assert.assertEquals( 1, result.headerSection.signature.domain.size)	
		Assert.assertEquals( 6, result.headerSection.signature.function.size)

		// static prova1 : Integer
		var func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(StaticFunctionImpl), func.class )	
		Assert.assertEquals( "prova1", func.name )
		Assert.assertEquals( null, func.domain )
		Assert.assertEquals( "Integer", func.codomain.name )
		
		// static prova2 : Integer -> Prova1
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(StaticFunctionImpl), func.class )	
		Assert.assertEquals( "prova2", func.name )
		Assert.assertEquals( "Integer", func.domain.name )
		Assert.assertEquals( "Prova1", func.codomain.name )
		
		// --- static prova3 : Prod(String, Integer)
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(StaticFunctionImpl), func.class )	
		Assert.assertEquals( "prova3", func.name )
		Assert.assertEquals( null, func.domain )
		// Prod(String, String)
		Assert.assertEquals( typeof(ProductDomainImpl), func.codomain.class )	
		var cod1 = func.codomain as ProductDomainImpl
		Assert.assertEquals( 2, cod1.domains.size )	
		Assert.assertEquals( "String", cod1.domains.get(0).name )	
		Assert.assertEquals( "Integer", cod1.domains.get(1).name )	
		
		// ---  static prova4 : Prod(Integer, Natural) -> Bag( Integer )
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(StaticFunctionImpl), func.class )
		Assert.assertEquals( "prova4", func.name )
		// Prod(Integer, Natural)
		Assert.assertEquals( typeof(ProductDomainImpl), func.domain.class )	
		var dom1 = func.domain as ProductDomainImpl
		Assert.assertEquals( 2, dom1.domains.size )	
		Assert.assertEquals( "Integer", dom1.domains.get(0).name )	
		Assert.assertEquals( "Natural", dom1.domains.get(1).name )	
		// Bag( Integer )
		Assert.assertEquals( typeof(BagDomainImpl), func.codomain.class )	
		var cod2 = func.codomain as BagDomainImpl
		Assert.assertEquals( "Integer", cod2.domain.name )	
		
		// --- static prova5 : Prod( Seq(Integer), Natural) -> Bag( Integer )
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(StaticFunctionImpl), func.class )
		Assert.assertEquals( "prova5", func.name )
		// -- Prod( Seq(Integer), Natural)
		Assert.assertEquals( typeof(ProductDomainImpl), func.domain.class )	
		dom1 = func.domain as ProductDomainImpl
		Assert.assertEquals( 2, dom1.domains.size )	
		// Seq(Integer)
		Assert.assertEquals( typeof(SequenceDomainImpl), dom1.domains.get(0).class )	
		var dom1_child = dom1.domains.get(0) as SequenceDomainImpl
		Assert.assertEquals( "Integer", dom1_child.domain.name )	
		// Natural
		Assert.assertEquals( "Natural", dom1.domains.get(1).name )	
		// Bag( Integer )
		Assert.assertEquals( typeof(BagDomainImpl), func.codomain.class )	
		cod2 = func.codomain as BagDomainImpl
		Assert.assertEquals( "Integer", cod2.domain.name )	
		
		// static program: Agent -> Rule	
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(StaticFunctionImpl), func.class )	
		Assert.assertEquals( "program", func.name )
		Assert.assertEquals( "Agent", func.domain.name )
		Assert.assertEquals( typeof(RuleDomainImpl), func.codomain.class )
				
	}
	
	@Test
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
		
		Assert.assertEquals( 1, result.headerSection.signature.domain.size)	
		Assert.assertEquals( 16, result.headerSection.signature.function.size)	
		
		// dynamic controlled prova21 : String
		//i++
		var func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(ControlledFunctionImpl), func.class )	
		var con_func = func as ControlledFunctionImpl
		Assert.assertEquals( true, con_func.dynamic )
		Assert.assertEquals( "prova21", con_func.name )
		Assert.assertEquals( null, con_func.domain )
		Assert.assertEquals( "String", con_func.codomain.name )
		
		// dynamic controlled prova22 : Integer -> String
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(ControlledFunctionImpl), func.class )	
		con_func = func as ControlledFunctionImpl
		Assert.assertEquals( true, con_func.dynamic )
		Assert.assertEquals( "prova22", con_func.name )
		Assert.assertEquals( "Integer", con_func.domain.name )
		Assert.assertEquals( "String", con_func.codomain.name )
		
		// controlled prova23 : String
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(ControlledFunctionImpl), func.class )	
		con_func = func as ControlledFunctionImpl
		Assert.assertEquals( false, con_func.dynamic )
		Assert.assertEquals( "prova23", con_func.name )
		Assert.assertEquals( null, con_func.domain )
		Assert.assertEquals( "String", con_func.codomain.name )
		
		// controlled prova24 : Integer -> Complex
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(ControlledFunctionImpl), func.class )	
		con_func = func as ControlledFunctionImpl
		Assert.assertEquals( false, con_func.dynamic )
		Assert.assertEquals( "prova24", con_func.name )
		Assert.assertEquals( "Integer", con_func.domain.name )
		Assert.assertEquals( "Complex", con_func.codomain.name )
				
		// dynamic shared prova31 : String
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(SharedFunctionImpl), func.class )	
		var shared_fun = func as SharedFunctionImpl
		Assert.assertEquals( true, shared_fun.dynamic )
		Assert.assertEquals( "prova31", shared_fun.name )
		Assert.assertEquals( null, shared_fun.domain )
		Assert.assertEquals( "String", shared_fun.codomain.name )
		
		// dynamic shared prova32 : Integer -> String
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(SharedFunctionImpl), func.class )	
		shared_fun = func as SharedFunctionImpl
		Assert.assertEquals( true, shared_fun.dynamic )
		Assert.assertEquals( "prova32", shared_fun.name )
		Assert.assertEquals( "Integer", shared_fun.domain.name )
		Assert.assertEquals( "String", shared_fun.codomain.name )
		
		// shared prova33 : Complex
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(SharedFunctionImpl), func.class )	
		shared_fun = func as SharedFunctionImpl
		Assert.assertEquals( false, shared_fun.dynamic )
		Assert.assertEquals( "prova33", shared_fun.name )
		Assert.assertEquals( null, shared_fun.domain )
		Assert.assertEquals( "Complex", shared_fun.codomain.name )
		
		// shared prova34 : String -> Integer
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(SharedFunctionImpl), func.class )	
		shared_fun = func as SharedFunctionImpl
		Assert.assertEquals( false, shared_fun.dynamic )
		Assert.assertEquals( "prova34", shared_fun.name )
		Assert.assertEquals( "String", shared_fun.domain.name )
		Assert.assertEquals( "Integer", shared_fun.codomain.name )
				
		// dynamic monitored prova41 : String
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(MonitoredFunctionImpl), func.class )	
		var mon_func = func as MonitoredFunctionImpl
		Assert.assertEquals( true, mon_func.dynamic )
		Assert.assertEquals( "prova41", mon_func.name )
		Assert.assertEquals( null, mon_func.domain )
		Assert.assertEquals( "String", mon_func.codomain.name )
		
		// dynamic monitored prova42 : Prova1 -> String
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(MonitoredFunctionImpl), func.class )	
		mon_func = func as MonitoredFunctionImpl
		Assert.assertEquals( true, mon_func.dynamic )
		Assert.assertEquals( "prova42", mon_func.name )
		Assert.assertEquals( "Prova1", mon_func.domain.name )
		Assert.assertEquals( "String", mon_func.codomain.name )
		
		// monitored prova43 : String
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(MonitoredFunctionImpl), func.class )	
		mon_func = func as MonitoredFunctionImpl
		Assert.assertEquals( false, mon_func.dynamic )
		Assert.assertEquals( "prova43", mon_func.name )
		Assert.assertEquals( null, mon_func.domain )
		Assert.assertEquals( "String", mon_func.codomain.name )
		
		// monitored prova44 : String -> Complex
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(MonitoredFunctionImpl), func.class )	
		mon_func = func as MonitoredFunctionImpl
		Assert.assertEquals( false, mon_func.dynamic )
		Assert.assertEquals( "prova44", mon_func.name )
		Assert.assertEquals( "String", mon_func.domain.name )
		Assert.assertEquals( "Complex", mon_func.codomain.name )	
				
		// dynamic out prova51 : Complex
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(OutFunctionImpl), func.class )	
		var fun_out = func as OutFunctionImpl
		Assert.assertEquals( true, fun_out.dynamic )
		Assert.assertEquals( "prova51", fun_out.name )
		Assert.assertEquals( null, fun_out.domain )
		Assert.assertEquals( "Complex", fun_out.codomain.name )
		
		// dynamic out prova52 : String -> String
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(OutFunctionImpl), func.class )	
		fun_out = func as OutFunctionImpl
		Assert.assertEquals( true, fun_out.dynamic )
		Assert.assertEquals( "prova52", fun_out.name )
		Assert.assertEquals( "String", fun_out.domain.name )
		Assert.assertEquals( "String", fun_out.codomain.name )
		
		// out prova53 : String
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(OutFunctionImpl), func.class )	
		fun_out = func as OutFunctionImpl
		Assert.assertEquals( false, fun_out.dynamic )
		Assert.assertEquals( "prova53", fun_out.name )
		Assert.assertEquals( null, fun_out.domain )
		Assert.assertEquals( "String", fun_out.codomain.name )
		
		// out prova54 : Integer -> String			
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(OutFunctionImpl), func.class )	
		fun_out = func as OutFunctionImpl
		Assert.assertEquals( false, fun_out.dynamic )
		Assert.assertEquals( "prova54", fun_out.name )
		Assert.assertEquals( "Integer", fun_out.domain.name )
		Assert.assertEquals( "String", fun_out.codomain.name )
		
		
	}
	
	@Test
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
		
		Assert.assertEquals( 1, result.headerSection.signature.domain.size)	
		Assert.assertEquals( 2, result.headerSection.signature.function.size)
		
		// derived prova1 : Integer
		var func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(DerivedFunctionImpl), func.class )	
		Assert.assertEquals( "prova1", func.name )
		Assert.assertEquals( null, func.domain )
		Assert.assertEquals( "String", func.codomain.name )
		
		//  derived prova2 : Prova1 -> String
		i++
		func = result.headerSection.signature.function.get(i)
		Assert.assertEquals( typeof(DerivedFunctionImpl), func.class )	
		Assert.assertEquals( "prova2", func.name )
		Assert.assertEquals( "Prova1", func.domain.name )
		Assert.assertEquals( "String", func.codomain.name )
		
	}	
	
	
	@Test
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
		Assert.assertEquals( 3, result.headerSection.importClause.size)
	}
		@Test
	def void testPathImports() {		
		var result = parseHelper.parse('''
asm __tempAsmetaV7859023612479832841
import a/b
import \\AgHome\\progettidaSVNGIT\\asmeta\\asmeta_based_applications\\fMVC\\AMAN\\model\\CTLlibrary
signature:
			definitions:
		''', "__tempAsmetaV7859023612479832841")
		result.assertNoErrors
		Assert.assertEquals( 3, result.headerSection.importClause.size)
	}
	
	
	
}