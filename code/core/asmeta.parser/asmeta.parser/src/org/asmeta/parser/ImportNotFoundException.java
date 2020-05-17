package org.asmeta.parser;
/** the import has not been found */
public class ImportNotFoundException extends RuntimeException {

	public ImportNotFoundException(String asmDirLib, String moduleName) {
		super("import not found in " + asmDirLib + " module name "+ moduleName);
	}

}
