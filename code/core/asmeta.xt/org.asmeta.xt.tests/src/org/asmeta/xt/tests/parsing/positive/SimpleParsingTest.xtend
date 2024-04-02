package org.asmeta.xt.tests.parsing.positive

import java.io.File
import org.junit.Assert
import org.junit.Test

class SimpleParsingTest extends ParserTest{	

	@Test
	def void testBlankAsm() {
		var result = test('''
			asm blankpage
			signature: 
			definitions: 
		''', "blankpage")
		// asm test
		Assert.assertEquals( false, result.isAsynchr )													
		Assert.assertEquals( "blankpage", result.name)														
		// header test																			
		Assert.assertEquals( 0, result.headerSection.importClause.size )							
		Assert.assertEquals( null, result.headerSection.exportClause)
		Assert.assertEquals( 0, result.headerSection.signature.domain.size)	
		Assert.assertEquals( 0, result.headerSection.signature.domain.size)			
		// body test
		// TODO va bene che non sia istanziato ma sia null?
		Assert.assertEquals( 0, result.bodySection.domainDefinition.size)
		Assert.assertEquals( 0, result.bodySection.functionDefinition.size)
		Assert.assertEquals( 0, result.bodySection.ruleDeclaration.size)
		Assert.assertEquals( 0, result.bodySection.invariantConstraint.size)
		Assert.assertEquals( 0, result.bodySection.fairnessConstraint.size)
		Assert.assertEquals( 0, result.bodySection.property.size)
		
		// main rule test
		// TODO da sistemare
		Assert.assertEquals( null, result.mainrule)		
		
		// initialstate test
		Assert.assertEquals( 0, result.initialState.size )
	}
	
	@Test
	def void testImport() {
		// test all the possible imports	
		val f = new File("../../../../asm_examples/STDL/StandardLibrary.asm")
		Assert.assertTrue(f.exists)
		// relative path (from which directory???)
		var asmlib = f.path.replaceAll("\\\\","/")
		// remove the extension
		asmlib = asmlib.substring(0,asmlib.length-4)
		testImport(asmlib)
		// relative path with quotes
		testImport("\""+asmlib+"\"")
		// TODO absolute path				
	}
	
	def testImport(String toimport){
		println("*** testing with " + toimport)
		val string = '''
					asm import1
					import "../'''+toimport+'''" 

signature: 
definitions: 
				'''
		println(string)
		var result = test(string, "import1")
		
	}
}