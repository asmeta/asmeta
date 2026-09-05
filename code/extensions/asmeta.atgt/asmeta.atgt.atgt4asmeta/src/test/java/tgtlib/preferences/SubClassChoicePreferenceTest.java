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

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 */
class SubClassChoicePreferenceTest {

	@Test void setGetSubClass() {
		PreferenceBundle pb = new PreferenceBundle("PROVA");
		SubClassChoicePreference<A> sbc = new SubClassChoicePreference<A>("id","prova subclass");
		sbc.setPrefBundle(pb);
		sbc.addSubClass(B.class);
		sbc.setValue(B.class.getSimpleName());
		//
		assertSame(B.class, sbc.getValue());
	}

	
	/**
	 */
	abstract class A{}
	
	/**
	 */
	class B extends A{}
}
