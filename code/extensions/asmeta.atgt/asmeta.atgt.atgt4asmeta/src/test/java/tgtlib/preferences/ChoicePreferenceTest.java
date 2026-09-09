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
package tgtlib.preferences;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class ChoicePreferenceTest {

	@Test void setValue() {
		String A = "A";
		String[] vals ={A,"B", "C"};
		PreferenceBundle pb = new PreferenceBundle("PROVA");
		ChoicePreference cp = new ChoicePreference("prova","pref di prova",vals);
		pb.add(cp);
		cp.setValue(A);
		assertEquals(A,cp.getValue());
		try{
			cp.setValue("PIPPO");
			fail("setting to a wrong value should raise an exception");
		} catch (RuntimeException rte){
			
		}

	}
	

}
