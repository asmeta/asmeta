/*******************************************************************************
 * Copyright (c) 2009 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.asmeta.simulator.main;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import org.asmeta.parser.AsmParserTest;

/** some utilities
 * 
 * @author garganti
 *
 */
public class Util {

	public static Simulator getSimulatorForTestSpec(String filepath) throws Exception{
		String completPath = checkPath(filepath);
		try {
			return Simulator.createSimulator(completPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("file not found");
		}
	}

	private static String checkPath(String filepath) {
		String completPath = AsmParserTest.FILE_BASE + filepath;
		File f = new File(completPath);
		assertTrue("file " + f.getAbsolutePath()
				+ " does not exist, current dir: "
				+ new File(".").getAbsolutePath(), f.exists());
		return completPath;
	}

	public static Simulator getSimulatorForTestSpec(String filepath,
			String env) {
		String completPath = checkPath(filepath);
		String completeEnv = checkPath(env);
		try {
			return Simulator.createSimulator(completPath,completeEnv);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("file not found");
		}
	}
	
}
