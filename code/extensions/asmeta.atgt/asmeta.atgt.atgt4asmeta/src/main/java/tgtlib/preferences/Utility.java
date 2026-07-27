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

import java.io.File;

import org.apache.log4j.Logger;

/** some utilities to get the preferences right * @author garganti
 * @version $Revision: 1.0 $
 */

public final class Utility {
	
	
	private Utility(){}
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Utility.class);

	/** returns the directory where to store the temporary files * @return File
	 */
	static public File getTempDirPref(){
		String tempDirName = TGLibPreferences.TEMP_DIR.getValue();
		logger.debug("tempDir = " + tempDirName);
		File tempDir = new File(tempDirName);
		// check if it exists
		if (! isTmpDirOk(tempDir)){
			// if not ok, get the property
			tempDirName =  System.getProperty("java.io.tmpdir");
			logger.debug("tmp dir from prop: " + tempDirName);
			tempDir = new File(tempDirName);
		}
		if (! isTmpDirOk(tempDir)){
			throw new TempDirException("the temp directory " + tempDirName
			+ "does not exists: check the options");
		}
		return tempDir;
	}

	/**
	 * Method isTmpDirOk.
	 * @param tempDir File
	 * @return boolean
	 */
	private static boolean isTmpDirOk(File tempDir) {
		// check if it exists and it is a real directory
		if (!tempDir.exists() || !tempDir.isDirectory()) return false;
		// check that it teh file in it can be executed
		// TODO
		return true;
	}	
}
