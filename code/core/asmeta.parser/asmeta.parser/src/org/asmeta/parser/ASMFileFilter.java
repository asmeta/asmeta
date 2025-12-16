package org.asmeta.parser;

import java.io.File;

public class ASMFileFilter implements java.io.FileFilter {
	/** returns true if f is a valid asm file to be tested */
	@Override
	public boolean accept(File f) {
		// use Utility.CTL_LIBRARY_NAME and so on instead
		return (!f.getName().equalsIgnoreCase("standardlibrary.asm") && !f.getName().equalsIgnoreCase("ctllibrary.asm")
				&& !f.getName().equalsIgnoreCase("ltllibrary.asm") && !f.isDirectory()
				&& f.getName().toLowerCase().endsWith(ASMParser.ASM_EXTENSION));
	}

	public String getDescription() {
		return ".asm files";
	}
}
