/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package atgt.coverage;

import java.util.Hashtable;

// TODO: Auto-generated Javadoc

/**
 * an index of all the test condition by theri ID key: unique id for the tp,
 * value : the tp.
 */

public class TPIndex extends Hashtable<String, AsmTestCondition> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Gets the t pby id.
	 * 
	 * @param id
	 *            the id
	 * 
	 * @return the t pby id
	 */
	public AsmTestCondition getTPbyID(String id) {
		return get(id);
	}
}
