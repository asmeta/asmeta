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
package org.asmeta.simulator.util;

import java.io.IOException;
import java.io.Reader;

/**
 * Scans an input stream, identifies the tokens and converts them into the
 * corresponding values.
 * 
 */
public class Scanner {
	
	/**
	 * Next read token.
	 * 
	 */
	private StringBuilder buffer = new StringBuilder(128);
	
	
	/**
	 * Next read character.
	 * 
	 */
	private int lookahead = ' ';
	
	/**
	 * Input stream.
	 *  
	 */
	private Reader reader;

	/**
	 * Creates a scanner.
	 * 
	 * @param reader a reader
	 */
	public Scanner(Reader reader) {
		this.reader = reader;
	}
	
	/**
	 * Clears the buffer.
	 * 
	 */
	private void clearBuffer() {
		buffer.delete(0, buffer.length());
	}
	
	/**
	 * Adds the lookahead character to the buffer.
	 * 
	 */
	private void appendBuffer() {
		buffer.append((char) lookahead);
	}
	
	/**
	 * Gets the token in the buffer.
	 * 
	 * @return the token
	 */
	private String bufferToString() {
		return buffer.toString();
	}
		
	/**
	 * Reads the next character (modifies the lookahead).
	 * 
	 */
	private void next() {
		try {
			lookahead = reader.read();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Skips all the whites characters.
	 * 
	 */
	private void skipWhites() {
		while (Character.isWhitespace(lookahead)) {
			next();
		}
	}
	
	/**
	 * Matches the lookahead with the given character.
	 * 
	 * @param expected expected character
	 * @throws InputMismatchException
	 */
	private void match(int expected) throws InputMismatchException {
		if (lookahead != expected) {
			throw new InputMismatchException(
					"Expected " + (char) expected + 
					" but found " +  (lookahead == -1 ? "EOF" : (char) lookahead));
		}
	}
	
	/**
	 * Matches the lookahead with the digits.  
	 * 
	 * @throws InputMismatchException
	 */
	private void matchDigit() throws InputMismatchException {
		if (!Character.isDigit(lookahead)) {
			throw new InputMismatchException(
					"Expected a digit but found " + 
					(lookahead == -1 ? "EOF" : (char) lookahead));
		}
	}
	
	/**
	 * Matches the lookahead with the letters.
	 * 
	 * @throws InputMismatchException
	 */
	private void matchLetter() throws InputMismatchException {
		if (!Character.isLetter(lookahead)) {
			throw new InputMismatchException(
					"Expected a letter but found " + 
					(lookahead == -1 ? "EOF" : (char) lookahead));
		}
	}
	
	/**
	 * Gets the lookahead (doesn't modify it).
	 * 
	 * @return the lookahead
	 * @throws InputMismatchException if end-of-file is found
	 */
	public char peekChar() throws InputMismatchException {
		skipWhites();
		if (lookahead == -1) {
			throw new InputMismatchException(
					"Expected a char but found EOF");			
		}
		return (char) lookahead;
	}
	
	/**
	 * Converts the current token into an integer.
	 * 
	 * @return an integer
	 * @throws InputMismatchException
	 */
	public int scanInt() throws InputMismatchException {
		clearBuffer();
		skipWhites();
		if (lookahead == '-') {
			appendBuffer();
			next();
		} else if (lookahead == '+') {
			next();
		}
		matchDigit();
		appendBuffer();
		next();
		while (Character.isDigit(lookahead)) {
			appendBuffer();
			next();
		}
		return Integer.parseInt(bufferToString());
	}
	
	/**
	 * Converts the current token into a real.
	 * 
	 * @return a real
	 * @throws InputMismatchException
	 */
	public double scanReal() throws InputMismatchException {
		clearBuffer();
		skipWhites();
		if (lookahead == '-') {
			appendBuffer();
			next();
		} else if (lookahead == '+') {
			next();
		}
		matchDigit();
		appendBuffer();
		next();
		while (Character.isDigit(lookahead)) {
			appendBuffer();
			next();
		}
		if (lookahead == '.') {
			appendBuffer();
			next(); matchDigit(); 
			while (Character.isDigit(lookahead)) {
				appendBuffer();
				next();
			}
		}
		return Double.parseDouble(bufferToString());
	}
	
	/**
	 * Converts the current token into a boolean.
	 * 
	 * @return a boolean
	 * @throws InputMismatchException
	 */
	public boolean scanBool() throws InputMismatchException {
		skipWhites();
		if (lookahead == 't') {
			next(); match('r');
			next(); match('u');
			next(); match('e');
			next();
			return true;
		} else if (lookahead == 'f') {
			next(); match('a');
			next(); match('l');
			next(); match('s');
			next(); match('e');
			next();
			return false;	
		}
		throw new InputMismatchException(
				"Expected boolean but found " + (char) lookahead);
	}
	
	/**
	 * Checks if the current token is equals to the given string.
	 * 
	 * @param expected expected string
	 * @return <i>expected</i>
	 * @throws InputMismatchException if the token is not equals to <i>expected</i>
	 */
	public String scan(String expected) throws InputMismatchException {
		skipWhites();
		for (int i = 0; i < expected.length(); i++) {
			match(expected.charAt(i));
			next();
		}
		return expected;
	}

	/**
	 * Converts the current token into a quoted string.
	 * 
	 * @return a quoted string
	 * @throws InputMismatchException
	 */
	public String scanQuoted() throws InputMismatchException {
		clearBuffer();
		skipWhites();
		match('"');
		appendBuffer();
		next();
		while (lookahead != -1 && lookahead != '"') {
			appendBuffer();
			next();
		}
		match('"');
		appendBuffer();
		next();
		return bufferToString();
	}

	/**
	 * Converts the current token into an identifier (a string of letters, digits,
	 * "_" and "!").
	 * 
	 * @return an identifier
	 * @throws InputMismatchException
	 */
	public String scanId() throws InputMismatchException {
		clearBuffer();
		skipWhites();
		matchLetter();
		appendBuffer();
		next();
		while (
				Character.isLetter(lookahead) || 
				Character.isDigit(lookahead) ||
				lookahead == '_' ||
				lookahead == '!') {
			appendBuffer();
			next();
		}
		return bufferToString();
	}
	
}
