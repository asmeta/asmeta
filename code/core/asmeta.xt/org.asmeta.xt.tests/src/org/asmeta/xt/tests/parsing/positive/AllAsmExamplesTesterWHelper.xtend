package org.asmeta.xt.tests.parsing.positive

import com.google.inject.Inject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Iterator
import java.util.List
import java.util.stream.Collectors
import org.asmeta.xt.asmetal.Asm
import org.asmeta.xt.tests.AsmetaLInjectorProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.eclipse.xtext.validation.Issue
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 *  test for all the examples in asm_examples
 * 
 * NOT WORKING because import is not working
 * 
 */
@RunWith(XtextRunner)
@InjectWith(AsmetaLInjectorProvider)
class AllAsmExamplesTesterWHelper {

	@Inject ParseHelper<Asm> parseHelper
	@Inject extension ValidationTestHelper

	@Test
	def void testAllExamples() {
		val examplePath = Paths.get("../../../../asm_examples/examples/")
		Assert.assertTrue(Files.isDirectory(examplePath))
		val Iterator<Path> files = Files.walk(examplePath).iterator
		while (files.hasNext) {
			var fileToRead = files.next
			if (fileToRead.toString.endsWith(".asm") && !fileToRead.toString.contains("deleteme")) {
				testAsmetaXtFile(fileToRead)
				//return
			}
		}
	}

	protected def void testAsmetaXtFile(Path fileToRead) {
		System.out.print(fileToRead)
		// trasform the path to test
		val InputStream fis = Files.newInputStream(fileToRead)
		val charset = Charset.forName("8859_1")
		val BufferedReader br = new BufferedReader(new InputStreamReader(fis, charset))
		val String spec = br.lines().collect(Collectors.joining(System.lineSeparator()));
		fis.close
		br.close
		//
		var result = parseHelper.parse(spec)
		var List<Issue> validate = validate(result)
		if (!validate.isEmpty) {
			System.err.println(" error")
			System.out.println(validate.toString());
		} else {
			System.out.println(" ok")
		}
		System.out.println()
	}

}
