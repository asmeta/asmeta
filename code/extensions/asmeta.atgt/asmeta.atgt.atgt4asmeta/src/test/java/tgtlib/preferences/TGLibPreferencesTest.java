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
package tgtlib.preferences;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TGLibPreferencesTest {

	class MyPref extends TGLibPreferences{
		public MyPref() {
			super("Mypref");
		}}
	
	class MyPref2 extends TGLibPreferences{
		public MyPref2() {
			super("Mypref2");
		}}

	@Test
	public void testTGLib2Preferences() {
		MyPref my = new MyPref();		
		MyPref2 my2 = new MyPref2();
		TGLibPreferences.TEMP_DIR.setValue("tmp1");
		TGLibPreferences.TEMP_DIR.setValue("tmp2");
		assertEquals("tmp1", TGLibPreferences.TEMP_DIR.getValue());
		assertEquals("tmp2", TGLibPreferences.TEMP_DIR.getValue());		
	}
	
}
