/*******************************************************************************
 * Copyright (c) 2008, 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 *     Andrea Taffi
 ******************************************************************************/

package tgtlib.preferences;

import java.util.Arrays;

/**
 */
public class ChoicePreference extends SimplePreference {

	protected String[] _values;

	/**
	 * Costruttore senza default value: prende il primo della lista di valori
	 * 
	 * @param key
	 *            id preferenza
	 * @param description
	 *            Descrizione corrispondente al titolo della label
	 * @param values
	 *            possibili valori tra cui scegliere (il primo è quello di default)
	 */
	public ChoicePreference(String key, String description, String[] values) {
		super(key, values[0], description);
		this._values = values;
	}

	/**
	 * Costruttore con default value
	 * 
	 * @param key
	 *            id preferenza
	 * @param description
	 *            Descrizione corrispondente al titolo della label
	 * @param values
	 *            possibili valori tra cui scegliere
	 * @param defaultValue
	 *            valore di default nella lista
	 */
	public ChoicePreference(String key, String description, String[] values,
			String defaultValue) {
		super(key, defaultValue, description);
		this._values = values;
	}

	/**
	 * Method getValues.
	 * @return String[]
	 */
	public String[] getValues() {
		return _values;
	}

	/**
	 * set the value for this preference, If it not a real value, an exception
	 * is thrown
	 * @param val String
	 */
	@Override
	public void setValue(String val) {
		if (Arrays.asList(_values).contains(val)) {
			super.setValue(val);
		} else {
			throw new ChoiceNotValidException("choice not permitted");
		}
	}

	/**
	 * return the value as String
	 * 
	 * @return String
	 */
	@Override
	public String getValue() {
		return getStringValue();
	}

	/**
	 * Method accept.
	 * @param prefVisitor PreferenceVisitor<T>
	 * @return T
	 */
	@Override
	public <T> T accept(PreferenceVisitor<T> prefVisitor) {
		return prefVisitor.forChoicePref(this);
	}	
	
}
