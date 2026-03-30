package org.asmeta.xt.tests.parsing.positive

import java.io.File
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Tag

class SimpleParsingTest extends ParserTest{	

	@Test
	def void testBlankAsm() {
		var result = test('''
			asm blankpage
			signature: 
			definitions: 
		''', "blankpage")
		// asm test
		Assertions.assertEquals( false, result.isAsynchr )													
		Assertions.assertEquals( "blankpage", result.name)														
		// header test																			
		Assertions.assertEquals( 0, result.headerSection.importClause.size )							
		Assertions.assertEquals( null, result.headerSection.exportClause)
		Assertions.assertEquals( 0, result.headerSection.signature.domain.size)	
		Assertions.assertEquals( 0, result.headerSection.signature.domain.size)			
		// body test
		// TODO va bene che non sia istanziato ma sia null?
		Assertions.assertEquals( 0, result.bodySection.domainDefinition.size)
		Assertions.assertEquals( 0, result.bodySection.functionDefinition.size)
		Assertions.assertEquals( 0, result.bodySection.ruleDeclaration.size)
		Assertions.assertEquals( 0, result.bodySection.invariantConstraint.size)
		Assertions.assertEquals( 0, result.bodySection.fairnessConstraint.size)
		Assertions.assertEquals( 0, result.bodySection.property.size)
		
		// main rule test
		// TODO da sistemare
		Assertions.assertEquals( null, result.mainrule)		
		
		// initialstate test
		Assertions.assertEquals( 0, result.initialState.size )
	}
	
	@Test@Tag("TestToMavenSkip")
	def void testImport() {
		// test all the possible imports	
		val f = new File("../../../../asm_examples/STDL/StandardLibrary.asm")
		Assertions.assertTrue(f.exists)
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