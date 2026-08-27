/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package tgtlib.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 */
public class SimpleCmdExecutorTest {

	private static final String SUFFIX = "yyy";
	private static final String PREFIX = "xxx";

	/**
	 * Method testGetExecNameFileinTemp.
	 * @throws IOException
	 */
	@Test
	public void testGetExecNameFileinTemp() throws IOException {
		File tempDir = tgtlib.preferences.Utility.getTempDirPref();
		File specFile = java.io.File.createTempFile(PREFIX, SUFFIX, tempDir);
		// Delete temp file when program exits.
		specFile.deleteOnExit();
		String specS = specFile.getName();
		String execName = SimpleCmdExecutor.getExecName(tempDir, specS);
		System.out.println(execName);
		System.out.println(tempDir.getAbsolutePath());
		assertTrue(execName.startsWith(tempDir.getAbsolutePath()+File.separatorChar+PREFIX));
		assertTrue(execName.endsWith(SUFFIX + ".exe"));
	}

}
