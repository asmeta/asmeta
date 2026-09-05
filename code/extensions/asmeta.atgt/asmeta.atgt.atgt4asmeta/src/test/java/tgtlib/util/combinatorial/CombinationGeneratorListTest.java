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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 */
class CombinationGeneratorListTest {

	@Test void findCombinationEmpty() {
		// lista vuota chiedo combinazioni --- Eccezioni
		List<Integer> data = new ArrayList<Integer>();
		try {
			new CombinationGeneratorList<Integer>(data, 2);
		} catch (IllegalArgumentException ia) {
			return;
		}
		fail("non ha lanciato l'eccezione");
	}

	@Test void findCombination0() {
		// lista non vuota chido combinazioni di > numero
		List<Integer> data = Arrays.asList(1, 2);
		try {
			new CombinationGeneratorList<Integer>(data, 3);
		} catch (IllegalArgumentException ia) {
			return;
		}
		fail("non ha lanciato l'eccezione");
	}

	@Test void findCombinationSame() {
		List<Integer> data = Arrays.asList(1, 2);
		CombinationGeneratorList<Integer> gen = new CombinationGeneratorList<Integer>(data, 2);
		assertEquals(Arrays.asList(1, 2), gen.next());
		assertFalse(gen.hasNext());
	}

	@Test void findCombination() {
		List<Integer> data = Arrays.asList(1, 2, 3);
		CombinationGeneratorList<Integer> gen = new CombinationGeneratorList<Integer>(data, 2);
		for(List<Integer> k: gen){
			System.out.println(k);
		}
	}

	@Test void getNext() {
		String[] ss = {"a","b","c"};
		List<String> ls = Arrays.asList(ss);
		CombinationGeneratorList<String> cgl = new CombinationGeneratorList<String>(ls,2);
		assertEquals("[a, b]", cgl.next().toString());
		assertEquals("[a, c]", cgl.next().toString());
		assertEquals("[b, c]", cgl.next().toString());
		assertFalse(cgl.hasNext());
	}
	
}
