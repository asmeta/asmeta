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

/** can be set or unset 
 * For flag preference USE and VALUE must be consistent
 * It is a subclass of CheckedPreference with type BOOL, 
 * USE = yes <=> VALUE = true
 * 
 * TODO : make an independent class
 */
public class FlagPreference extends CheckedPreference {
	
	/**
	 * The Constructor.
	 * 
	 * @param value the default value
	 * @param descr the description
	 * @param key the key (ID of the preference)
	 */
	public FlagPreference(String key, boolean value, String descr){
        super(key,String.valueOf(value),TYPE.BOOL,descr);
	}

	/**
	 * Sets if this preference is checked or not 
	 * @param yes
	 *            the yes
	 */
	@Override
	public void setChecked(boolean yes) {
		bundle.setUseAndCheck(getKey(), yes);
	}

	/**
	 * given a FlagPreference return the value return the default if the pref is
	 * not found. Note: VALUE and USE for flag must be equals
	 * 
	 * @return the value: id the flag has been checked or not
	 */
	@Override
	public Boolean getValue() {
		return Boolean.valueOf(getStringValue());
	}
}
