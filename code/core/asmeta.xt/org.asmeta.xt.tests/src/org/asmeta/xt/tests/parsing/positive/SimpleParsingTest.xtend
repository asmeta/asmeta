package org.asmeta.xt.tests.parsing.positive

import org.junit.runner.RunWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.InjectWith
import org.asmeta.xt.tests.AsmetaLInjectorProvider
import com.google.inject.Inject
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.asmeta.xt.asmetal.Asm
import org.junit.Test
import org.junit.Assert
import org.asmeta.xt.parser.ParseAndValidateResult
import org.asmeta.xt.parser.AsmetaLParserWOHelper
import java.io.FileWriter
import java.io.IOException
import java.io.File
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue

//@RunWith(XtextRunner)
//@InjectWith(AsmetaLInjectorProvider)
class SimpleParsingTest{	

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
	// save content to a temp file with filename, parse it and return the ASM
	// it must be error free
	static def test(String content, String filename) {
		// create the temp file
		var tempFile = new File("temp/" + filename + ".asm");
		// clean the temp file if present
		if (tempFile.exists())
			tempFile.delete();
		assertFalse(tempFile.exists());
		try {
			val write = new FileWriter(tempFile);
			write.write(content);
			write.close();
			assertTrue(tempFile.exists());
			val results = new AsmetaLParserWOHelper()
					.parseAndValidateFile(tempFile.getAbsoluteFile().getAbsolutePath(), false);
			// clean the temp file
			tempFile.delete();
			assertFalse(tempFile.exists());
			if (results.getErrors().size() > 0)
				throw new AssertionError(results.toString());
			return results.getAsm().get(0);
		} catch (IOException e) {
			throw  new AssertionError(e.getMessage()); 
		}
	}
	
}