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
package atgt.parser;

import java.io.File;
import java.io.Reader;

import atgt.specification.ASMSpecification;
import tgtlib.specification.SpecReader;

/**
 * * a asm Reader the format can be AsmM, XMI, Gofer more may be added.
 * 
 * @author angelo Gargantini
 */
public abstract class AsmSpecReader extends SpecReader<ASMSpecification>{

	/**
	 * Instantiates a new asm spec reader.
	 */
	public AsmSpecReader() {
	}


	/**
	 * return if the loader cna load this file: now looks only to the extension.
	 * 
	 * @param f
	 *            the f
	 * 
	 * @return true, if checks if is file loadable
	 */
	public boolean isFileLoadable(File f) {
		if (!f.isFile())
			return false;
		String fileName = f.getName();
		if (fileName.endsWith("." + getFileExtension()))
			return true;
		return false;
	}
	@Override
	public final ASMSpecification read(Reader in){
		throw new RuntimeException("only files");
	}

	
	
}
