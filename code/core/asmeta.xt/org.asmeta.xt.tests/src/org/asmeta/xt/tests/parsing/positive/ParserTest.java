package org.asmeta.xt.tests.parsing.positive;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.asmeta.xt.asmetal.Asm;
import org.asmeta.xt.parser.AsmetaLParserWOHelper;
import org.asmeta.xt.parser.ParseAndValidateResult;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.junit.Assert;

public class ParserTest {
	
	/**
	 * save content to a temp file with filename, parse it and return the ASM
	 * it must be error free
	 * @param content the content
	 * @param filename the filename
	 * @return the asm 
	 */
	protected Asm test(final String content, final String filename) {
//		File tempFile = new File((("temp/" + filename) + ASMParser.asmExtension));
		File tempFile = new File((("temp/" + filename) + ".asm"));
		Assert.assertTrue( new File("temp").exists() && new File("temp").isDirectory());
		boolean _exists = tempFile.exists();
		if (_exists) {
			tempFile.delete();
		}
		Assert.assertFalse(tempFile.exists());
		try {
			final FileWriter write = new FileWriter(tempFile);
			write.write(content);
			write.close();
			Assert.assertTrue(tempFile.exists());
			final ParseAndValidateResult results = new AsmetaLParserWOHelper()
					.parseAndValidateFile(tempFile.getAbsoluteFile().getAbsolutePath(), false);
			tempFile.delete();
			Assert.assertFalse(tempFile.exists());
			int _size = results.getErrors().size();
			boolean _greaterThan = (_size > 0);
			if (_greaterThan) {
				String _string = results.toString();
				throw new AssertionError(_string);
			}
			return results.getAsm().get(0);
		} catch (final Throwable _t) {
			if (_t instanceof IOException) {
				final IOException e = (IOException) _t;
				String _message = e.getMessage();
				throw new AssertionError(_message);
			} else {
				throw Exceptions.sneakyThrow(_t);
			}
		}
	}

}
