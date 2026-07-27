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
package atgt.parser.asmgofer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.log4j.Logger;

import atgt.parser.AsmParseException;
import atgt.parser.AsmSpecReader;
import atgt.specification.ASMSpecification;

/**
 * reads spec written in AsmGofer uso questo perch� il parser non pu�
 * estendere delle classi con metodi arbitari (che prendono il file).
 * 
 * @author Sergio Galati, AG
 */
public class AsmGoferLoader extends AsmSpecReader {

	/** The logger. */
	private static Logger logger = Logger.getLogger(AsmGoferLoader.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.parser.AsmSpecReader#read(java.io.File)
	 */
	@Override
	public ASMSpecification read(File f) throws FileNotFoundException,
			AsmParseException {
		AsmGoferParser parser = new AsmGoferParser(new FileReader(f));
		// Elimina la specifica precedente.
		parser.resetSpecification();
		// Esegue il parsing del testo
		try {
			parser.parseSpec();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AsmParseException(e);
		}
		return parser.getSpecification();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.parser.AsmSpecReader#getFileExtension()
	 */
	@Override
	public String getFileExtension() {
		return "gs";
	}

}
