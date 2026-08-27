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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.junit.Test;

/**
 */
public class JBoxPreferenceTest {

	@Test
	public void testGetJBox() {
		PreferenceBundle bp = new PreferenceBundle("PROVA");
		FlagPreference fg = new FlagPreference("III", true, "prova");
		fg.setPrefBundle(bp);
		JFrame f = new JFrame("PROVA");
		f.getContentPane().add(fg.accept(new JBoxVisitor(bp)));
		f.setSize(400, 150);
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				
			}
		});
		f.setVisible(true);
	}
}
