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
public class StringPreference extends SimplePreference {

	/**
	 * The Constructor.
	 * 
	 * @param value the default value
	 * @param key the key (without spaces)
	 * @param description the description
	 */
	public StringPreference(String key, String value, String description) {
		super(key, value, description);
	}

	/**
	 * The Constructor.
	 * 
	 * @param value the default value
	 * @param description  -the key (description)
	 */
	@Deprecated
	public StringPreference(String description, String value) {
		super(description.replace(' ', '_'), value, description);
	}

	/**
	 * given a StringPreference return the value return the default if the pref is
	 * not found.
	 * 
	 * @return the value
	 */
	@Override
	public String getValue() {
		return getStringValue();
	}

	@Override
	public <T> T accept(PreferenceVisitor<T> prefVisitor) {
		return prefVisitor.forStringPreference(this);
	}

}
