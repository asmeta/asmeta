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

package atgt.specification.type;

/**
 * 
 * @author sergio
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import tgtlib.definitions.expression.type.Type;


// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving typeAction events. The class that is
 * interested in processing a typeAction event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addTypeActionListener<code> method. When
 * the typeAction event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see TypeActionEvent
 */
public class TypeActionListener implements ActionListener {

	/** The type. */
	Type type;

	/** The S p_ curr. */
	//ASMSpecification SP_Curr;

	/**
	 * Creates a new instance of TypeActionListener.
	 * 
	 * @param _type
	 *            the _type
	 */
	public TypeActionListener(Type _type) {
		this.type = _type;
		//this.SP_Curr = _SP_Curr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Event for type");
	}

}
