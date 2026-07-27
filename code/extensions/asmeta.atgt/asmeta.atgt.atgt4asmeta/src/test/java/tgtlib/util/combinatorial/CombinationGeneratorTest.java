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
package tgtlib.util.combinatorial;

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

public class CombinationGeneratorTest {

	@Test
	public void testCombinationGenerator() {
		CombinationGenerator cg = new CombinationGenerator(4, 2);
		while(cg.hasNext())
			System.out.println(Arrays.toString(cg.next()));
	}

	@Test
	public void testGetNumLeft() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNext() {
		fail("Not yet implemented");
	}

}
