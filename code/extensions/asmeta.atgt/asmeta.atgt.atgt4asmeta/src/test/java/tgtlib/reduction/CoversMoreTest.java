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
package tgtlib.reduction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import tgtlib.generator.TestPredicate4Test;

import org.junit.jupiter.api.Test;
import tgtlib.generator.TestSequence4Test;

/**
 */
class CoversMoreTest extends ReductionTest{

	@Test void compare1() {
		// already covered is empty
		Collection<TestPredicate4Test> alredayCovered = Collections.emptySet();
		CoversMore<TestPredicate4Test, TestSequence4Test> cv = new CoversMore<TestPredicate4Test, TestSequence4Test>(alredayCovered);
		assertEquals(0,cv.compare(tr1, tr2));
		// tr3 > tr4
		assertEquals(1,cv.compare(tr3, tr4));
		// 
		assertEquals(-1,cv.compare(tr4, tr2));
		// already covered is not empty
		cv = new CoversMore<TestPredicate4Test, TestSequence4Test>(Arrays.asList(tc1));
		// tr1 < tr2
		assertEquals(-1,cv.compare(tr1, tr2));
		// tr1 == tr3
		assertEquals(0,cv.compare(tr1, tr3));
		// tr4 < tr3
		assertEquals(-1,cv.compare(tr4, tr3));
	}

	/** same test but with all the test predicates
	 * 
	 */
	@Test void compare2() {
		// already covered is empty
		// to be covered = all the sets
		Collection<TestPredicate4Test> alredayCovered = Collections.emptySet();
		Collection<TestPredicate4Test> tobecovered = Arrays.asList(tc1,tc2,tc3);
		CoversMore<TestPredicate4Test, TestSequence4Test> cv = new CoversMore<TestPredicate4Test, TestSequence4Test>(alredayCovered,tobecovered);
		assertEquals(0,cv.compare(tr1, tr2));
		// tr3 > tr4
		assertEquals(1,cv.compare(tr3, tr4));
		// 
		assertEquals(-1,cv.compare(tr4, tr2));
		// already covered is not empty
		cv = new CoversMore<TestPredicate4Test, TestSequence4Test>(Arrays.asList(tc1));
		// tr1 < tr2
		assertEquals(-1,cv.compare(tr1, tr2));
		// tr1 == tr3
		assertEquals(0,cv.compare(tr1, tr3));
		// tr4 < tr3
		assertEquals(-1,cv.compare(tr4, tr3));
	}

	/** same test but with only some test predicates to be covered
	 * 
	 */
	@Test void compare3() {
		// already covered is empty
		// to be covered = all the sets
		Collection<TestPredicate4Test> alredayCovered = Collections.emptySet();
		Collection<TestPredicate4Test> tobecovered = Arrays.asList(tc1,tc2);
		CoversMore<TestPredicate4Test, TestSequence4Test> cv = new CoversMore<TestPredicate4Test, TestSequence4Test>(alredayCovered,tobecovered);
		// tr1 > tr2
		assertEquals(1,cv.compare(tr1, tr2));
		// tr3 = tr4
		assertEquals(0,cv.compare(tr3, tr4));
		// tr4 == tr2
		assertEquals(0,cv.compare(tr4, tr2));
		// already covered is not empty
		cv = new CoversMore<TestPredicate4Test, TestSequence4Test>(Arrays.asList(tc1));
		// tr1 < tr2
		assertEquals(-1,cv.compare(tr1, tr2));
		// tr1 == tr3
		assertEquals(0,cv.compare(tr1, tr3));
		// tr4 < tr3
		assertEquals(-1,cv.compare(tr4, tr3));
	}


}
