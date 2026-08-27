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
 * CheckedPreferce is the preference composed by four fields. Key: the
 * preference's key Value: the preference's default value; Type: the type of the
 * preference's value - It can be a String or an Integer; Description: the
 * preference's description.
 * 
 * It represents a preference of type Type which can be activated or not in USE
 * will put if it is check or not in VALUE the value to use (depending on the
 * type)
 * 
 * @author garganti
 * @version $Revision: 1.0 $
 */
public class CheckedPreference extends SimplePreference {

	/**
	 * Method createCheckedIntPreference.
	 * @param key String
	 * @param i int
	 * @param descr String
	 * @return CheckedPreference
	 */
	public static CheckedPreference createCheckedIntPreference(String key,
			int i, String descr) {
		return new CheckedPreference(key, String.valueOf(i), TYPE.INT, descr);
	}

	/**
	 * Method createCheckedStringPreference.
	 * @param key String
	 * @param value String
	 * @param descr String
	 * @return CheckedPreference
	 */
	public static CheckedPreference createCheckedStringPreference(String key,
			String value, String descr) {
		return new CheckedPreference(key, value, TYPE.STRING, descr);
	}

	/**
	 * Preference's type. it must be SinglePreference.TYPE_BOOL or
	 * SinglePreference.TYPE_INT or SinglePreference.TYPE_STRING
	 */
	protected TYPE _type;

	/**
	 */
	protected enum TYPE {
		BOOL, INT, STRING
	}

	/**
	 * the constructor. Create a single preference
	 * 
	 * @param key
	 *            preference's key
	 * @param value
	 *            preference's default value
	 * @param type
	 *            preference's type
	 * @param descr
	 *            preference's description
	 */
	protected CheckedPreference(String key, String value, TYPE type,
			String descr) {
		super(key, value, descr);
		_type = type;
		if (!checkValue(value)) throw new IllegalArgumentException("value "+value + " not compaitible with " + type);
	}

	/**
	 * Gets the type
	 * 
	
	 * @return type */
	public TYPE getType() {
		return _type;
	}

	/**
	 * Sets the USE value for the checke preference. treu: the pref is checked,
	 * fals uncehcke the pref
	 * 
	 * @param yes
	 *            the yes
	 */
	public void setChecked(boolean yes) {
		bundle.setUse(getKey(), yes);
	}

	/**
	 * return the value as String (it will a number in case of an Integer, a
	 * booleaninc ase a Bool and a String
	 * 
	 * @return Object
	 */
	@Override
	public Object getValue() {
		String stringValue = getStringValue();
		switch (_type) {
		case STRING:
			return stringValue;
		case INT:
			return Integer.getInteger(stringValue);
		case BOOL:
			return Boolean.valueOf(stringValue);
		}
		return null;

	}

	/**
	 * Method accept.
	 * @param prefVisitor PreferenceVisitor<T>
	 * @return T
	 */
	@Override
	public <T> T accept(PreferenceVisitor<T> prefVisitor) {
		return prefVisitor.forCheckedPref(this);
	}

	/**
	 * change the value of this preference. It does not check it.
	 * @param value String
	 */
	@Override
	public void setValue(String value) {
		if (! checkValue(value)) throw new RuntimeException("value not of type" + _type);
		bundle.storeValue(_key, value);
	}
	/** 
	 * check if the value is compatible with the type
	 * @param v
	
	 * @return boolean
	 */
	private boolean checkValue(String v) {
		if (_type == TYPE.INT){
				try{
					// if no exception occur
					Integer.parseInt(v);
				}catch (NumberFormatException ne){
					return false;
				}
				return true;
		} else if (_type == TYPE.BOOL){
			return v.equalsIgnoreCase("true") ||v.equalsIgnoreCase("false");
		} else{
			return true;
		}
	}
	
	/***
	 * is this preference checked? 
	 * @return boolean
	 */
	public boolean isChecked(){
		 return this.bundle.isChecked(this);
	}
}
