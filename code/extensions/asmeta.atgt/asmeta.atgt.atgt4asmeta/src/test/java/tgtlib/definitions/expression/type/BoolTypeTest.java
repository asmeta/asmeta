/*******************************************************************************
 * Copyright (c) 2008, 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package tgtlib.definitions.expression.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * The Class BoolTypeTest.
 */
public class BoolTypeTest {

	/**
	 * Test bool type.
	 */
	@Test
	public void testBoolType() {
		BoolType b = BoolType.BOOLTYPE;
		assertEquals(2, b.allElements().size());
		assertEquals(BoolType.FALSE_CONST,b.allElements().get(0));
		assertEquals(BoolType.TRUE_CONST,b.allElements().get(1));
	}

	/**
	 * Test i ds.
	 */
	@Test
	public void testIDs() {
		assertEquals("true", BoolType.TRUE_CONST.getIdString());
		assertEquals("false", BoolType.FALSE_CONST.getIdString());
	}

	/**
	 * Test enum const andi ds.
	 */
	@Test
	public void testEnumConstANDIDs() {
		assertSame(BoolType.TRUE_CONST, BoolType.TRUE_CONST);
		assertSame(BoolType.FALSE_CONST, BoolType.FALSE_CONST);
	}

}
