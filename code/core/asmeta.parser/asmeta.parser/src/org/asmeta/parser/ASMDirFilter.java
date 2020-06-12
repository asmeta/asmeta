package org.asmeta.parser;

import java.io.File;

public class ASMDirFilter implements java.io.FileFilter {
	/**
	 * returns true if f can contain ASM specs skip .svn dirs !!!
	 */
	@Override
	public boolean accept(File f) {
		return (!f.getName().equalsIgnoreCase(".svn"));
	}

	public String getDescription() {
		return ".asm directories";
	}
}
