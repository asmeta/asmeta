package tgtlib.preferences;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 */
public class JBoxVisitor implements PreferenceVisitor<Box> {

	PreferenceBundle preferenceBundle;

	/**
	 * Constructor for JBoxVisitor.
	 * @param prefB PreferenceBundle
	 */
	public JBoxVisitor(PreferenceBundle prefB) {
		preferenceBundle = prefB;
	}

	/**
	 * Method forStringPreference.
	 * @param sp StringPreference
	 * @return Box
	 * @see tgtlib.preferences.PreferenceVisitor#forStringPreference(StringPreference)
	 */
	@Override
	public Box forStringPreference(StringPreference sp) {
		return buildBox(sp);
	}
	/**
	 * Method forIntegerPreference.
	 * @param ip IntegerPreference
	 * @return Box
	 * @see tgtlib.preferences.PreferenceVisitor#forIntegerPreference(IntegerPreference)
	 */
	@Override
	public Box forIntegerPreference(IntegerPreference ip) {
		return buildBox(ip);
	}

	/** simplest visualization
	 * 
	 * @param sp
	
	 * @return Box
	 */
	private Box buildBox(SimplePreference sp) {
		String ck = sp.getKey();
		//
		Box b = Box.createHorizontalBox();
		JLabel lb = new JLabel(ck);
		b.add(lb);
		lb.setPreferredSize(new Dimension(200, lb.getPreferredSize().height));
		String value = sp.getValue().toString();
		JTextField tf = new JTextField(value);
		tf.setPreferredSize(new Dimension(300, 25));
		tf.setMaximumSize(new Dimension(300, 25));
		tf.setName(ck);
		tf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent evt) {
				preferenceBundle.tfLf(evt);
			}
		});
		b.add(Box.createHorizontalGlue());
		b.add(tf);
		return b;
	}

	/** return the graphical part for this preference * @param cp CheckedPreference
	 * @return Box
	 * @see tgtlib.preferences.PreferenceVisitor#forCheckedPref(CheckedPreference)
	 */
	@Override
	public Box forCheckedPref(final CheckedPreference cp) {
		Box b = Box.createHorizontalBox();
		String ck = cp.getKey();
		JLabel lb = new JLabel(cp.getDescr());
		b.add(lb);
		// build the check button
		JCheckBox cb = new JCheckBox();
		cb.setAlignmentX(Component.RIGHT_ALIGNMENT);
		// cb.setPreferredSize(new Dimension(100,cb.getPreferredSize().height));
		cb.setName(ck);
		if (preferenceBundle.isChecked(cp))
			cb.setSelected(true);
		else
			cb.setSelected(false);
		//
		CheckedPreference.TYPE preftype = cp.getType();
		if (preftype == CheckedPreference.TYPE.BOOL) {
			/*
			 * String value = preferenceBundle.prefs.get(ck, ""); /*JLabel lb2 =
			 * new JLabel(ck); lb2.setPreferredSize(new Dimension(100,
			 * lb2.getPreferredSize().height)); b.add(lb2); JTextField tf2 = new
			 * JTextField(""); tf2.setPreferredSize(new Dimension(200,
			 * lb2.getPreferredSize().height)); tf2.setEnabled(false);
			 * tf2.setEditable(false); tf2.setBorder(null); b.add(tf2);
			 */

			b.add(Box.createHorizontalGlue());
			cb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					preferenceBundle.cbBoolActionPerformed(evt);
				}
			});
			b.add(cb);
		} else {
			// isn't a type bool
			lb
					.setPreferredSize(new Dimension(100,
							lb.getPreferredSize().height));
			final String value = cp.getValue().toString();
			JLabel lb2 = new JLabel(ck);
			lb2.setPreferredSize(new Dimension(100,
					lb2.getPreferredSize().height));
			b.add(lb2);
			JTextField tf2 = new JTextField(value);
			tf2.setPreferredSize(new Dimension(280, 25));
			tf2.setMaximumSize(new Dimension(280, 25));
			tf2.setName(ck);
			tf2.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent evt) {
					preferenceBundle.tfLf(evt);
				}
			});
			b.add(Box.createHorizontalGlue());
			b.add(tf2);
			cb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					preferenceBundle.cbActionPerformed(evt, value);
				}
			});
			b.add(cb);
		}
		return b;
	}

	/**
	 * Method forChoicePref.
	 * @param cp ChoicePreference
	 * @return Box
	 * @see tgtlib.preferences.PreferenceVisitor#forChoicePref(ChoicePreference)
	 */
	@Override
	public Box forChoicePref(ChoicePreference cp) {
		Box b = Box.createHorizontalBox();
		String ck = cp.getKey();
		JLabel lb = new JLabel(cp.getDescr());
		b.add(lb);
		lb.setPreferredSize(new Dimension(200, lb.getPreferredSize().height));
		JComboBox combo = new JComboBox(cp.getValues());
		combo.setName(ck);
		combo.setSelectedItem(cp.getValue());
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				preferenceBundle.comboActionPerformed(evt);
			}
		});
		combo.setPreferredSize(new Dimension(300, 25));
		combo.setMaximumSize(new Dimension(300, 25));
		b.add(Box.createHorizontalGlue());
		b.add(combo);
		return b;
	}

	/**
	 * Method forSubClassChoicePref.
	 * @param cp SubClassChoicePreference<S>
	 * @return Box
	 * @see tgtlib.preferences.PreferenceVisitor#forSubClassChoicePref(SubClassChoicePreference<S>)
	 */
	@Override
	public <S> Box forSubClassChoicePref(SubClassChoicePreference<S> cp) {
		// TODO Auto-generated method stub
		return null;
	}

}
