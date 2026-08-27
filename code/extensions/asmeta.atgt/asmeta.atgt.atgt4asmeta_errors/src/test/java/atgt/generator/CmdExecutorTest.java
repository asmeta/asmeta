/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package atgt.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import tgtlib.util.SimpleCmdExecutor;

/**
 * The Class CmdExecutorTest.
 */
public class CmdExecutorTest {

	@BeforeClass
	public static void createexecutable() throws IOException{
		// creates the executable
		// TODO fix paths
		Runtime.getRuntime().exec("gcc -o test_cmd test_cmd.cpp");
	}
	
	/**
	 * Test run command with test_cmd.
	 * it must be compiled, otherwise it won't work
	 */
	@Test
	public void testRunCommandWithTest_cmd() {
		try {
			SimpleCmdExecutor.CMD.runCommand("test_cmd");
			// get output
			BufferedReader output = new BufferedReader(new FileReader(
					SimpleCmdExecutor.CMD.getOutput()));
			assertEquals("test std output", output.readLine());
			// get the errors
			BufferedReader err = new BufferedReader(new FileReader(
					SimpleCmdExecutor.CMD.errors));
			assertEquals("test err output", err.readLine());
		} catch (Exception e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("'test_cmd' not found as executable file");
		}
	}

}
