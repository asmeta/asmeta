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

/**
 * Simple Preference is composed by a key and default value cannot be selected
 * or not
 * @author garganti
 * @version $Revision: 1.0 $
 */
public abstract class SimplePreference {
	/** Preference's key */
	protected final String _key;
	/** Preference's default value */
	protected final String _value;
	/** Preference's description */
	protected final String _descr;

	/**
	 * Constructor of the class Create a SimplePreference
	 * 
	 * @param key
	 *            preference's key -must be unique without spaces !!!
	 * @param value
	 *            preference's default value (default)
	 * @param description
	 *            TODO
	 */
	protected SimplePreference(String key, String value, String description) {
		assert key != null;
		assert key.length() > 0;
		assert !key.contains(" ");
		_key = key;
		_value = value;
		_descr = description;
	}

	/**
	 * Gets the key
	 * 
	
	 * @return Key */
	public String getKey() {
		return _key;
	}

	/**
	 * Gets the current value
	 * 
	
	 * @return value */
	public String getDefaultValue() {
		return _value;
	}

	/**
	 * Get the description
	 * 
	
	 * @return Description */
	public String getDescr() {
		return _descr;
	}

	protected PreferenceBundle bundle;

	/**
	 * every preference belongs to a bundle
	 * * @param preferenceBundle PreferenceBundle
	 */
	public void setPrefBundle(PreferenceBundle preferenceBundle) {
		bundle = preferenceBundle;
	}

	/**
	 * change the value of a Simple Preference or STring Preference
	 * 
	 * @param value
	 *            TODO
	 */
	public void setValue(String value) {
		bundle.storeValue(_key, value);
	}

	/**
	 * return the value for this preference
	 * 
	
	 * @return Object
	 */
	abstract public Object getValue();

	/**
	 * return the current preference value for the choice preference given
	 * 
	
	 * @return the value: item selected */
	public final String getStringValue() {
		return bundle.prefs.get(getKey(), getDefaultValue());
	}

	/**
	 * Method accept.
	 * @param prefVisitor PreferenceVisitor<T>
	 * @return T
	 */
	abstract public <T> T accept(PreferenceVisitor<T> prefVisitor);

}
