/*
 * generated by Xtext 2.36.0
 */
package org.asmeta.avallaxt.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class AvallaAntlrTokenFileProvider implements IAntlrTokenFileProvider {

	@Override
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResourceAsStream("org/asmeta/avallaxt/parser/antlr/internal/InternalAvalla.tokens");
	}
}
