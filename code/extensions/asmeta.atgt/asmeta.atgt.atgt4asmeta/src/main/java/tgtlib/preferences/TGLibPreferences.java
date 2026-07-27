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

/** to store preference in SO backend, relatively to any tool 
 *
 * @author  garganti
 */
public abstract class TGLibPreferences extends ToolPreferences{
    
	// generic properties
    
	// non public forzerebbe l'uso del metodo in utility
	static StringPreference TEMP_DIR = new StringPreference("temporary directory",System.getProperty("user.home")+ "/"+ "tmp");
    public static FlagPreference DELETE_TMP = new FlagPreference("deltmp", false, "Delete temp files");
	public static final StringPreference CC = new StringPreference("gcc","gcc","c compiler");
	
    public static final String CINT_INTERPRETER = "cint";
	public static final String COMPILED = "compiled";
	static final String[] covOptions = {COMPILED,CINT_INTERPRETER}; 
	public static final ChoicePreference COV_EVAL = new ChoicePreference("covmethod","coverage evaluation method",covOptions); 
	
	public static StringPreference REPORT_DIR = new StringPreference("reportdir", System.getProperty("user.home") + System.getProperty("file.separator") + "reports","results directory");

	public static CheckedPreference TIMEOUT = CheckedPreference.createCheckedIntPreference("timeout", 0, "timeout for mc");

    

	public static PreferenceBundle GENERIC_PREFS = new PreferenceBundle("TGLIB");
    
	static{
        GENERIC_PREFS.add(TEMP_DIR);
        GENERIC_PREFS.add(DELETE_TMP);
        GENERIC_PREFS.add(CC);
        GENERIC_PREFS.add(COV_EVAL);
        GENERIC_PREFS.add(REPORT_DIR);
        GENERIC_PREFS.add(TIMEOUT);
	} 
    
    /** no constructor */
    protected TGLibPreferences(String s){
    	super(s);
        add(GENERIC_PREFS);
    }
    
}
