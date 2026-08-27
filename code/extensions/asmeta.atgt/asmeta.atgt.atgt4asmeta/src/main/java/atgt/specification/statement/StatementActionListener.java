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

package atgt.specification.statement;

/**
 * 
 * @author sergio
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import atgt.specification.ASMSpecification;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving statementAction events. The class that
 * is interested in processing a statementAction event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addStatementActionListener<code> method. When
 * the statementAction event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see StatementActionEvent
 */
public class StatementActionListener implements ActionListener {

	/** The statement. */
	RuleDeclaration statement;

	/** The S p_ curr. */
	ASMSpecification SP_Curr;

	/**
	 * Creates a new instance of StatementActionListener.
	 * 
	 * @param _statement
	 *            the _statement
	 * @param _SP_Curr
	 *            the _ s p_ curr
	 */
	public StatementActionListener(RuleDeclaration _statement,
			ASMSpecification _SP_Curr) {
		this.statement = _statement;
		this.SP_Curr = _SP_Curr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Event for statement");
	}

}
