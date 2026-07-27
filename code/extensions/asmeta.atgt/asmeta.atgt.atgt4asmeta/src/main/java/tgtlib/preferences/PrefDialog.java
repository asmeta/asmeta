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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.BackingStoreException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * 
 * @author sergio
 * @version $Revision: 1.0 $
 */
public class PrefDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ToolPreferences tp;

	/** Creates a new instance of PrefFrame * @param tp ToolPreferences
	 */
	public PrefDialog(ToolPreferences tp) {
		this.tp = tp;
		setTitle("Preferences " + tp.toolName);
		setResizable(true);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent evt) {
				setVisible(false);
			}
		});
		addPanels();
		pack();
	}

	private void addPanels() {
		JPanel options = new JPanel();
		JTabbedPane optiones = new JTabbedPane();
		// options.setLayout(new GridLayout(0, 1));
		try {
			options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
			for (PreferenceBundle P : tp.prefs) {
				optiones.addTab(P.getName(), P.getDialog());
			}
			options.add(optiones);
		} catch (BackingStoreException bse) {
			System.out.println("Eccezione:" + bse);
		}
		// add Ok and default buttons
		JPanel b = new JPanel();
		b.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton butDefault = new JButton("Default");
		butDefault.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				cbActionPerformed(evt);
			}
		});
		b.add(butDefault);
		JButton butOk = new JButton("Ok");
		butOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
			}
		});
		b.add(butOk);
		options.add(b);
		getContentPane().add(new JScrollPane(options));
	}

	/**
	 * Method cbActionPerformed.
	 * @param evt ActionEvent
	 */
	private void cbActionPerformed(ActionEvent evt) {
		try {
			getContentPane().setLayout(
					new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
			for (PreferenceBundle P : tp.prefs) {
				P.setDefault();
			}
			getContentPane().removeAll();
			addPanels();
			pack();
			// repaint();
		} catch (BackingStoreException bse) {
			System.out.println("Eccezione" + bse);
		}
	}

}
