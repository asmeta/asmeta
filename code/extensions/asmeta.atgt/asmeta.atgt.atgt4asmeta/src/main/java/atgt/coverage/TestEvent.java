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

import java.util.EventObject;

// TODO: Auto-generated Javadoc
/**
 * The Class TestEvent.
 */
public class TestEvent extends EventObject {

	/** The description. */
	String description;

	/**
	 * Instantiates a new test event.
	 * 
	 * @param source
	 *            the source
	 */
	public TestEvent(Object source) {
		super(source);
		this.description = "Test Event";
	}

	/**
	 * Instantiates a new test event.
	 * 
	 * @param source
	 *            the source
	 * @param _description
	 *            the _description
	 */
	public TestEvent(Object source, String _description) {
		super(source);
		this.description = _description;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

}
