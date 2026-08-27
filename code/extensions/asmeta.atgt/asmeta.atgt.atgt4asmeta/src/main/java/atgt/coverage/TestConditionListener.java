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

import java.util.EventListener;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving testCondition events. The class that is
 * interested in processing a testCondition event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addTestConditionListener<code> method. When
 * the testCondition event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see TestConditionEvent
 */
public interface TestConditionListener extends EventListener {

	/**
	 * Test condition state changed.
	 * 
	 * @param te
	 *            the te
	 */
	public void TestConditionStateChanged(TestConditionEvent te);

}
