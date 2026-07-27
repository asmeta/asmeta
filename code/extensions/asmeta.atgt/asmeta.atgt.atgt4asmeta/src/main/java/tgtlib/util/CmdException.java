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

package tgtlib.util;

import java.util.Arrays;

/** the extranl command could not be executed */

class CmdException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5476862866046590972L;

	CmdException(Exception t, StringBuffer cmdTotal, String[] cmd) {
		super(t.toString() + "\nIt can't execute "
				+ cmdTotal + " (as array:" + Arrays.toString(cmd)
				+ " ): check your PATH");
	}

}
