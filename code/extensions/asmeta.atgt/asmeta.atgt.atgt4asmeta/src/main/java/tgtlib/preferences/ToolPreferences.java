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

import java.util.List;
import java.util.Vector;

import javax.swing.JDialog;

/** set of all the preferences bundles for a given tool
 */

class ToolPreferences {

	String toolName;
	
	List<PreferenceBundle> prefs;
	
	public ToolPreferences(String name){
		toolName = name;
		prefs = new Vector<PreferenceBundle>();
	}
		
	public boolean add(PreferenceBundle p){
		return prefs.add(p);
	}
	
	public List<PreferenceBundle> getPrefecences(){
		return prefs;
	}
	
	/** returns the dialog to show and set the preferences */
	public JDialog getPrefDialog(){
		return new PrefDialog(this);
	}
}
