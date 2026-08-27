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

/**
 * choice preference among an enum
 * 
 * @author garganti
 * 
 * 
 * @version $Revision: 1.0 $
 */
public class ChoicePreferenceEnum<T extends Enum<T>> extends ChoicePreference {

	/**
	 * the enum of this preference. It is erased by type erasure
	 * 
	 */
	private Class<T> enumClass;

	/**
	 * 
	 * 
	 * @param key
	 * @param description
	 * 
	 * @param defaultValue
	 *            T
	 */
	public ChoicePreferenceEnum(String key, String description, T defaultValue) {
		super(key, description, toStrings(defaultValue.getDeclaringClass()
				.getEnumConstants()), defaultValue.name());
		enumClass = defaultValue.getDeclaringClass();
	}

	/**
	 * Method toStrings.
	 * 
	 * @param tt
	 *            T[]
	 * @return String[]
	 */
	static private <T extends Enum<T>> String[] toStrings(T[] tt) {
		String[] result = new String[tt.length];
		for (int i = 0; i < tt.length; i++) {
			result[i] = tt[i].name();
		}
		return result;
	}

	/**
	 * set the value for this preference, If it not a real value, an exception
	 * is thrown
	 * 
	 * @param val
	 *            T
	 */
	public void setValue(T val) {
		super.setValue(val.name());
	}

	/**
	 * return the value as enum
	 * 
	 * 
	 * @return T
	 */
	public T getValueAsEnum() {
		String s = getStringValue();
		for (T t : enumClass.getEnumConstants()) {
			if (t.name().equals(s))
				return t;
		}
		return null;
	}

	/**
	 * Method accept.
	 * 
	 * @param prefVisitor
	 *            PreferenceVisitor<Q>
	 * @return Q
	 */
	@Override
	public <Q> Q accept(PreferenceVisitor<Q> prefVisitor) {
		return prefVisitor.forChoicePref(this);
	}

}
