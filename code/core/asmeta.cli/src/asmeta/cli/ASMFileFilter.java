package asmeta.cli;

import java.io.File;

import org.asmeta.parser.ASMParser;

public class ASMFileFilter implements java.io.FileFilter {
	/** returns true if f is a valid asm file to be tested */
	@Override
	public boolean accept(File f) {
		return (!f.getName().equalsIgnoreCase("standardlibrary.asm") && !f.isDirectory()
				&& f.getName().toLowerCase().endsWith(ASMParser.ASM_EXTENSION));
	}

	public String getDescription() {
		return ".asm files";
	}
}
