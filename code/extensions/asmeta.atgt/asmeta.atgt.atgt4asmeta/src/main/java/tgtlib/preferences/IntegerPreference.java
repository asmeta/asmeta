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

/** Simple Preference is composed by a key and default value
 * which is a String
 */
public class IntegerPreference extends SimplePreference {

	/**
	 * The Constructor.
	 * 
	 * @param value the default value
	 * @param key the key (without spaces)
	 * @param description the description
	 */
	public IntegerPreference(String key, Integer value, String description) {
		super(key, value.toString(), description);
	}

	/**
	 * given a StringPreference return the value return the default if the pref is
	 * not found.
	 * 
	 * @return the value
	 */
	@Override
	public Integer getValue() {
		return Integer.parseInt(getStringValue());
	}

	/**
	 * change the value of this integer  
	 * @param value
	 *            TODO
	 */
	@Override
	public void setValue(String value) {
		try{
			// check if it is an integer 
			Integer.parseInt(value);
			super.setValue(value);
        } catch (NumberFormatException ex) {
            throw new RuntimeException("not avalid option");
        }
	}
	public void setValue(int value) {
		super.setValue(Integer.toString(value));
	}

	
	@Override
	public <T> T accept(PreferenceVisitor<T> prefVisitor) {
		return prefVisitor.forIntegerPreference(this);
	}

}
