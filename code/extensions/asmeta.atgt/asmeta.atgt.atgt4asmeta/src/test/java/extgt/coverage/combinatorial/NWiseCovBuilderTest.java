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

package extgt.coverage.combinatorial;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import extgt.coverage.combinatorial.NWiseCovBuilder;

import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.type.EnumType;


public class NWiseCovBuilderTest {

	static EnumType A = new EnumType("A");
	static EnumType B = new EnumType("B");
	static EnumConstCreator ecc = new EnumConstCreator();		

	static{
		A.addElement(ecc.createEnumConst("a1"));
		A.addElement(ecc.createEnumConst("a2"));
		B.addElement(ecc.createEnumConst("b1"));
		B.addElement(ecc.createEnumConst("b2"));		
	}
	
	@Test
	public void testAll() {
		EnumConstCreator ecc = new EnumConstCreator();		
		List<EnumType> l = new ArrayList<EnumType>();
		l.add(A);
		l.add(B);
		assertEquals(NWiseCovBuilder.all(l).toString(),
				"[[a1, b1], [a1, b2], [a2, b1], [a2, b2]]");

		EnumType C = new EnumType("C");
		C.addElement(ecc.createEnumConst("c1"));
		C.addElement(ecc.createEnumConst("c2"));
		C.addElement(ecc.createEnumConst("c3"));

		l.add(C);
		assertEquals(
				"[[a1, b1, c1], [a1, b1, c2], [a1, b1, c3], [a1, b2, c1], [a1, b2, c2], [a1, b2, c3], [a2, b1, c1], [a2, b1, c2], [a2, b1, c3], [a2, b2, c1], [a2, b2, c2], [a2, b2, c3]]",
				NWiseCovBuilder.all(l).toString());

	}
	
	@Test public void testAllComb(){
		String[] ss1 = {"a","b","c"};
		String[] ss2 = {"h","j","k"};
		List<List<String>> ssL = new ArrayList<List<String>>();
		ssL.add(Arrays.asList(ss1));
		ssL.add(Arrays.asList(ss2));
		Iterator<List<String>> ll = NWiseCovBuilder.allT(ssL).iterator();	
		assertEquals("[a, h]", ll.next().toString());
		assertEquals("[a, j]", ll.next().toString());
	}

}
