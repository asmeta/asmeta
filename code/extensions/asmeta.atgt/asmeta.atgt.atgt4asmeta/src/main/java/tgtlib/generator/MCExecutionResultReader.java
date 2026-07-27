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
package tgtlib.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/** represents the output produced by the execution of the model checker
 * in the simplest case is just a file input stream
 * 
 * use of composition instead of inheritance
 * @author garganti
 *
 */
public class MCExecutionResultReader extends MCExecutionResult {
	// NOTA CHE lo stream potrebbe essere aperto
	private Reader mcOutput;
	
	private boolean valid;

	public MCExecutionResultReader(File in) throws FileNotFoundException {
		this(new FileReader(in));
	}

	public MCExecutionResultReader(Reader in) {
		mcOutput  = in;
		valid = true;
	}	
	
	public MCExecutionResultReader(InputStream in) {
		this(new InputStreamReader(in));
	}

	public Reader getMcOutputreader() {
		assert valid;
		return mcOutput;
	}

	/** to close the input stream */
	@Override
	public void close(){
		assert valid;
		try {
			mcOutput.close();
			valid = false;
		} catch (IOException e) {
		}
	}

	@Override
	public boolean isValid() {
		return valid;
	}
}