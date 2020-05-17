/*******************************************************************************
 * Copyright (c) 2009 .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.asmeta.simulator.readers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.util.InputMismatchException;
import org.asmeta.simulator.util.Parser;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.Function;

/**
 * La classe legge e converte le rappresentazioni in formato stringa delle
 * costanti contenute in un file. Il file contiene le costanti da assegnare alle
 * funzioni monitorate. Il formato del testo e' il seguente: una linea contiene
 * una costante, un commento (preceduto dal carattere <i>#</i>) oppure e' vuota.
 * Commenti e linee vuote sono ignorati.
 * 
 * @author Alessandro Carioni [acarioni@tele2.it]
 *
 */
public class FileMonFuncReader extends MonFuncReader {

	private BufferedReader in;

	/**
	 * Costruisce un nuovo oggetto che legge i valori da uno stream. utile per
	 * leggere da stringhe, ad esempio
	 * 
	 * @param in stream di input
	 */
	public FileMonFuncReader(Reader in) {
		this.in = new BufferedReader(in);
	}

	/**
	 * Costruisce un nuovo oggetto che legge i valori da un file di testo.
	 * 
	 * @param filePath percorso del file
	 * @throws FileNotFoundException - se il file non esiste
	 */
	public FileMonFuncReader(String filePath) throws FileNotFoundException {
		in = new BufferedReader(new FileReader(filePath));
	}

	@Override
	public Value readValue(Location location, State state) {
		String line;
		do {		
			String readLine = readLine();
			if (readLine == null) throw new RuntimeException("error in reading monitored " + location.toString());
			line = readLine.trim();
		} while (line.length() == 0 || line.charAt(0) == '#');
		Function func = location.getSignature();
		try {
			return new Parser(line).visit(func.getCodomain());
		} catch (InputMismatchException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Legge una linea di testo.
	 * 
	 * @return la linea corrente
	 */
	private String readLine() {
		try {
			return in.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
