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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 */
public class ToolPreferencesTest {

	PreferenceBundle pb; 
	FlagPreference fp;
	CheckedPreference cp;
	@Before
	public void createBundle(){
		pb = new PreferenceBundle("prova");
		fp = new FlagPreference("fp",true,"flag preference");
		cp = CheckedPreference.createCheckedIntPreference("cp",1000,"checked preference");
		pb.add(fp);
		pb.add(cp);		
	}
		
	@Test
	public void testFlagChecked() {
		fp.setChecked(true);
		assertTrue(pb.isChecked(fp));		
		assertTrue(fp.getValue());		
	}

	@Test
	public void testFlagUnChecked() {
		fp.setChecked(false);
		assertFalse(pb.isChecked(fp));
		assertFalse(fp.getValue());				
	}
	@Test
	public void testCPChecked() {
		cp.setChecked(true);
		assertTrue(pb.isChecked(cp));		
	}

	@Test
	public void testCPUnChecked() {
		fp.setChecked(false);
		assertFalse(pb.isChecked(fp));
	}

}
