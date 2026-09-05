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
package atgt.combinatorial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import atgt.specification.location.Variable;

import org.junit.jupiter.api.Test;

import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.util.combinatorial.CombinationGeneratorList;

class CombinationTest {
	
	EnumConstCreator ecc = new EnumConstCreator();

	@Test void findCombinationsVariable() {
		List<Variable> data = new ArrayList<Variable>();
		ElementsType A = new EnumType("A", ecc.createEnumConst("a1"),ecc.createEnumConst("a2"));
		ElementsType B = new EnumType("B", ecc.createEnumConst("b1"),ecc.createEnumConst("b2"));
		ElementsType C = new EnumType("C", ecc.createEnumConst("c1"),ecc.createEnumConst("c2"));
		IdExpression idExpression_a = ecc.createIdExpression("a", A);
		assertEquals(idExpression_a.getType(), A);
		Variable a = new Variable(idExpression_a, A, null);
		Variable b = new Variable(ecc.createIdExpression("b", B), B, null);
		Variable c = new Variable(ecc.createIdExpression("c", C), C, null);
		data.add(a);
		data.add(b);
		data.add(c);
		CombinationGeneratorList<Variable> gen = new CombinationGeneratorList<Variable>(data, 2);
		assertEquals(Arrays.asList(a, b), gen.next());
		assertEquals(Arrays.asList(a, c), gen.next());
		assertEquals(Arrays.asList(b, c), gen.next());
		assertFalse(gen.hasNext());
	}
}
