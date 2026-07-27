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
package atgt.specification.location;

/**
 * 
 * @author sergio
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import atgt.specification.ASMSpecification;
import tgtlib.definitions.TypedInitExpression;

/**
 * The listener interface for receiving locationAction events. The class that is
 * interested in processing a locationAction event implements this interface,
 * and the object created with that class is registered with a component using
 * the component's <code>addLocationActionListener<code> method. When
 * the locationAction event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see LocationActionEvent
 */
public class LocationActionListener implements ActionListener {

	/** The loc. */
	TypedInitExpression loc;

	/** The S p_ curr. */
	ASMSpecification SP_Curr;

	/**
	 * Creates a new instance of LocationActionListener.
	 * 
	 * @param _loc
	 *            the _loc
	 * @param _SP_Curr
	 *            the _ s p_ curr
	 */
	public LocationActionListener(TypedInitExpression _loc, ASMSpecification _SP_Curr) {
		this.loc = _loc;
		this.SP_Curr = _SP_Curr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Event for location");
		// loc.setValue("11");
	}
}
