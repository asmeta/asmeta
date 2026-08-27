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

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

/**
 * The Class PreferenceBundle: represents a set of preferences
 * @author garganti
 * @version $Revision: 1.0 $
 */
public class PreferenceBundle {
	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(PreferenceBundle.class);

	// use some string const instead of string
	// TODO public enum INFO_TYPE{TYPE,DESCRIPTION,USE}

	/** The Constant TYPE. */
	private static final String TYPE = "TYPE";

	/** The Constant DESCRIPTION. */
	private static final String DESCRIPTION = "DESCRIPTION";

	/** The Constant USE. */
	public static final String USE = "USE";

	// TODO
	// public enum Selected {YES,NO};
	/** The Constant YES. */
	private static final String YES = "yes";

	/** The Constant NO. */
	private static final String NO = "no";

	/** The root. */
	private String root = "";

	/** The prefs. */
	Preferences prefs;

	/** The _simplepref_def. */
	private Vector<SimplePreference> _simplepref_def = new Vector<SimplePreference>();

	/**
	 * The Constructor.
	 * 
	 * @param nameNode
	 *          the name node
	 */
	public PreferenceBundle(String nameNode) {
		prefs = Preferences.userRoot().node(root + "/" + nameNode);
	}

	/**
	 * The Constructor.
	 * 
	 * @param _root
	 *          the _root
	 * @param nameNode
	 *          the name node
	 */
	public PreferenceBundle(String nameNode, String _root) {
		prefs = Preferences.userRoot().node(_root + "/" + nameNode);
		root = _root;
	}

	/**
	 * Sets the root.
	 * 
	 * @param _root
	 *          the root
	 */
	public void setRoot(String _root) {
		root = _root;
	}

	/**
	 * get all the preferences in thsi bundle
	 * 
	
	 * @return all the preferences in thsi bundle */
	public List<SimplePreference> getPreference() {
		return Collections.unmodifiableList(_simplepref_def);
	}

	/**
	 * link and add this bundle to the single preference taken as paramter
	 * @param sp SimplePreference
	 */
	private void linkpreference(SimplePreference sp) {
		_simplepref_def.add(sp);
		sp.setPrefBundle(this);
	}

	/** store the value in the backing store * @param _key String
	 * @param value String
	 */
	void storeValue(String _key, String value) {
		prefs.put(_key, value);
	}

	/**
	 * Add.
	 * 
	 * @param sp
	 *          the sp
	 */
	public void add(CheckedPreference sp) {
		linkpreference(sp);
		Preferences PrefsType;
		Preferences PrefsDescr;
		Preferences PrefsUse;
		if (prefs.get(sp.getKey(), null) == null) {
			prefs.put(sp.getKey(), sp.getDefaultValue());
			PrefsType = Preferences.userRoot().node(
					root + "/" + prefs.name() + "/" + TYPE);
			PrefsDescr = Preferences.userRoot().node(
					root + "/" + prefs.name() + "/" + DESCRIPTION);
			PrefsUse = Preferences.userRoot().node(
					root + "/" + prefs.name() + "/" + USE);
			PrefsType.put(sp.getKey(), sp.getType().name());
			PrefsDescr.put(sp.getKey(), sp.getDescr());
			if (sp.getDefaultValue().equals("true"))
				PrefsUse.put(sp.getKey(), YES);
			else
				PrefsUse.put(sp.getKey(), NO);
			LOGGER.debug("added " + sp.getKey() + " and set to "
					+ sp.getDefaultValue());
		}
	}

	// attenzione
	/**
	 * Add a simplepreffernce
	 * 
	 * @param sp
	 *          the sp
	 */
	public void add(SimplePreference sp) {
		linkpreference(sp);
		if (prefs.get(sp.getKey(), null) == null) {
			prefs.put(sp.getKey(), sp.getDefaultValue());
			LOGGER.debug(sp.getKey() + " set to " + sp.getDefaultValue());
		}
	}

	/**
	 * Sets the default.
	 * 
	
	 * @throws BackingStoreException
	 *           the backing store exception */
	public void setDefault() throws BackingStoreException {
		for (SimplePreference sp : _simplepref_def) {
			prefs.put(sp.getKey(), sp.getDefaultValue());
		}
	}

	/**
	 * Get.
	 * 
	
	 * 
	
	 * @return the string * @throws BackingStoreException
	 *           the backing store exception */
	public String get() throws BackingStoreException {

		String ret = "";

		if (prefs.childrenNames().length > 1) {
			// is a SinglePreference
			Preferences PrefsType = Preferences.userRoot().node(
					root + "/" + prefs.name() + "/" + TYPE);
			Preferences PrefsUse = Preferences.userRoot().node(
					root + "/" + prefs.name() + "/" + USE);
			String[] k = prefs.keys();

			for (int i = 0; i < k.length; i++) {
				if (PrefsUse.get(k[i], "").equalsIgnoreCase(YES)) {
					if (PrefsType.get(k[i], "").equalsIgnoreCase(
							CheckedPreference.TYPE.BOOL.name()))
						ret += k[i] + " ";
					else if (PrefsType.get(k[i], "").equalsIgnoreCase(
							CheckedPreference.TYPE.INT.name())
							|| PrefsType.get(k[i], "").equalsIgnoreCase(
									CheckedPreference.TYPE.STRING.name()))
						ret += k[i] + prefs.get(k[i], "") + " ";
				}
			}
			return ret;
		} else {
			// is a SimplePreference
			String[] k = prefs.keys();
			ret = prefs.get(k[0], "");

			return ret;
		}
	}

	/**
	 * Keys.
	 * 
	
	 * 
	
	 * @return the string[] * @throws BackingStoreException
	 *           the backing store exception */
	static public String[] keys() throws BackingStoreException {
		Preferences pr = Preferences.userRoot();
		return pr.childrenNames();
	}

	/**
	 * ho definito questa per avere le sotto chiavi.
	 * 
	
	 * 
	
	 * @return the string[] * @throws BackingStoreException
	 *           the backing store exception */
	public String[] subKeys() throws BackingStoreException {
		return prefs.keys();
	}

	/**
	 * Checks if is checked.
	 * 
	 * @param sp
	 *          the sp
	 * 
	
	 * @return true, if is checked (USE is true) */
	public boolean isChecked(CheckedPreference sp) {
		// the pref must have as bundl this one
		assert sp.bundle == this;
		return getValueSubPref(USE, sp).equals(YES);
	}

	/**
	 * given a key and a subkey return the pref otherwise the default (as given by
	 * sp).
	 * 
	 * @param sp
	 *          the sp
	 * @param subPref
	 *          the sub pref
	 * 
	
	 * 
	
	 * @deprecated since <unknown>
	 * @return the value sub pref */
	@Deprecated
	public String getValueSubPref(String subPref, SimplePreference sp) {
		Preferences subPrefs = Preferences.userRoot().node(
				root + "/" + prefs.name() + "/" + subPref);
		return subPrefs.get(sp.getKey(), sp.getDefaultValue());
	}

	/**
	 * given a key return the pref - now it is a Simple.
	 * 
	 * @param key
	 *          the key
	 * @param subPref
	 *          the sub pref
	 * 
	
	 * 
	
	 * @deprecated since <unknown>
	 * @return the value sub pref */	
	@Deprecated
	String getValueSubPref(String key, String subPref) {
		Preferences subPrefs = Preferences.userRoot().node(
				root + "/" + prefs.name() + "/" + subPref);
		return subPrefs.get(key, "");
	}

	/**
	 * note that a prefBunlde could contain a mix between simple and single prefs.
	 * 
	
	 * 
	
	 * @return the dialog * @throws BackingStoreException
	 *           the backing store exception */
	public JPanel getDialog() throws BackingStoreException {
		// Contains the key - subkey (SimplePreference)
		Vector<String> key = new Vector<String>();
		// Contains the subkey (SinplePreference)
		Vector<String> subkey = new Vector<String>();
		JPanel pan = new JPanel();
		String[] k = prefs.keys();
		Preferences subPrefs = Preferences.userRoot().node(root + "/" + prefs.name() + "/" + TYPE);
		String[] sk = subPrefs.keys();
		for (int i = 0; i < k.length; i++) {
			boolean find = false;
			for (int j = 0; j < sk.length; j++) {
				if (k[i].equals(sk[j]))
					find = true;
			}
			if (!find)
				key.add(k[i]);
			else
				subkey.add(k[i]);
		}
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		pan.setBorder(new TitledBorder(new EtchedBorder(Color.white, Color.gray),
				prefs.name()));
		// build the part for STring preference
		JBoxVisitor prefVisitor = new JBoxVisitor(this);
		for (SimplePreference sp : _simplepref_def) {
			Box b = sp.accept(prefVisitor);
			pan.add(b);
			LOGGER.debug("getDialog() - " + pan.getClass() + " count: "
					+ pan.getComponentCount());
		}
		return pan;
	}

	/**
	 * Method getName.
	 * @return String
	 */
	public String getName() {
		return prefs.name();
	}
	/**
	 * Tf lf.
	 * 
	 * @param evt
	 *          the evt
	 */
	void tfLf(AWTEvent evt) {
		JTextField tf = (JTextField) evt.getSource();
		String chiave = tf.getName();
		String valore = tf.getText();
		LOGGER.debug("Evento textField --> setting " + chiave + " to " + valore);
		prefs.put(chiave, valore);
	}

	/**
	 * Cb bool action performed: for preferences true false
	 * 
	 * @param evt
	 *          the evt
	 */
	void cbBoolActionPerformed(ActionEvent evt) {
		JCheckBox cb = (JCheckBox) evt.getSource();
		String mykey = cb.getName();
		setUseAndCheck(mykey, cb.isSelected());
		// System.out.println("Evento cbBoolActionPerformed: set to true");
	}
	
	/**
	 * Combo action performed: for preferences true false
	 * @param evt
	 *          the evt
	 */
	void comboActionPerformed(ActionEvent evt) {
		JComboBox combo = (JComboBox) evt.getSource();
		String mykey = combo.getName();
		String myvalue = (String)combo.getSelectedItem();
		prefs.put(mykey, myvalue);
		LOGGER.debug("Evento combobox --> setting " + mykey + " to " + myvalue);
	}

	/**
	 * Cb action performed: the pref is selected or the value changed
	 * 
	 * @param evt
	 *          the evt
	 * @param value
	 *          the value
	 */
	void cbActionPerformed(ActionEvent evt, String value) {
		JCheckBox cb = (JCheckBox) evt.getSource();
		String mykey = cb.getName();
		setUse(mykey, cb.isSelected());
		prefs.put(mykey, value);
		LOGGER.debug("Evento cbActionPerformed: set to " + value);
	}

	/**
	 * Sets the use of the preference
	 * 
	 * @param yes
	 *          the yes
	 * @param pref
	 *          the pref name
	 */
	void setUse(String pref, boolean yes) {
		Preferences PrefsUse = Preferences.userRoot().node(
				root + "/" + prefs.name() + "/" + USE);
		if (yes)
			PrefsUse.put(pref, YES);
		else
			PrefsUse.put(pref, NO);
	}

	/**
	 * Sets the use and the value of the preference
	 * 
	 * @param yes
	 *          the yes
	 * @param pref
	 *          the pref name
	 */
	void setUseAndCheck(String pref, boolean yes) {
		setUse(pref, yes);
		prefs.put(pref, String.valueOf(yes));
	}

}
