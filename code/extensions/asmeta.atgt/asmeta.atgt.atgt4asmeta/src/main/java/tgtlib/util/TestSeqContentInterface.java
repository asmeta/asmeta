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

/** represents the content of a Test sequence
 * 
 * A test sequence than can have just one content or more than one
 * (i.e. a testsequence could be  testSeqContentHanlderitself)
 * @author garganti
 *
 */
public interface TestSeqContentInterface {

	/** to prepare a next state 
	 * it must be called also the first time for the first state
	 * */
	public  void addState();

	/** to add an event var = value 
	 * @throws InconsistentUpdateException 
	 * it must be called after a call of addState*/           
	public  void addAssignment(String var, String value);

	/** if the test sequence is unfeasible
	 * unfeasible is the test predicate not the sequence*/
	@Deprecated
	public  void setUnfeasible();

	/** if the test sequence was not found
	 * argument: a possible cause
	 */
	public void setNotFound(String message);
	
	/** close or flush the test sequence*/
    public void close();

}
