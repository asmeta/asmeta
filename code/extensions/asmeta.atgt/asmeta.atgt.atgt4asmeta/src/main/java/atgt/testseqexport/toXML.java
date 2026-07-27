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
package atgt.testseqexport;

import java.util.Map;

import atgt.coverage.AsmTestSequence;
import atgt.specification.location.Location;
import atgt.specification.location.Variable;

/**
 * The Class toXML.
 */
public class toXML {

	/** The indent. */
	protected String indent;

	/** The Constant scenarioDTD. */
	static final String scenarioDTD = "file:/home/caprino/Vector/scenarioDTD.dtd";

	/**
	 * Instantiates a new to xml.
	 */
	public toXML() {
		this.indent = "";
	}

	/**
	 * Instantiates a new to xml.
	 * 
	 * @param _indent
	 *            the _indent
	 */
	public toXML(String _indent) {
		this.indent = _indent;
	}

	/**
	 * Ritorna la Stringa XML del test condition forse dovrebbe chimarsi in modo
	 * diverso perch? stringa e non string buffer???
	 *  // TODO usare sax e dom invece !!!.
	 * 
	 * @param tc
	 *            the tc
	 * 
	 * @return the string
	 */
	public String export(AsmTestSequence tc) {
		StringBuffer buffer = new StringBuffer();
		// buffer = buffer.append("In TestCondition \n");
		//
		// TestSequence tr = tc.getTestResult();
		int j = 1;
		String num = String.valueOf(j);
		buffer = buffer.append("<?xml version=\"1.0\"?>").append("\n").append(
				"<!DOCTYPE SCENERY SYSTEM \"").append(scenarioDTD)
				.append("\">").append("\n").append("\n"); // ("<!DOCTYPE STATO
		// [ \n <!ELEMENT
		// STATO
		// (VAR,VAL)>\n
		// <!ATTLIST STATO\n
		// NUMERO CDATA
		// #REQUIRED>\n
		// <!ELEMENT VAR
		// (#PCDATA)>\n
		// <!ELEMENT VAL
		// (#PCDATA)>\n]>\n\n");
		buffer = buffer.append("<SCENERY>").append("\n").append("\n");
		buffer = buffer.append("  <STATE NUMBER=").append("\"").append(num)
				.append("\"").append(">").append("\n");
		++j;
		buffer = buffer.append("    <Update>").append("\n");
		for (Map<Location, String> v : tc.allInstructions()) {
			for (Map.Entry<Location, String> a : v.entrySet()) {
				String var = a.getKey().getName();
				String val = a.getValue();
				buffer = buffer.append("      <var>").append(var).append(
						"</var>");
				buffer = buffer.append(" ").append("<val>").append(val).append(
						"</val>").append("\n");

			}
			buffer = buffer.append("    </Update>").append("\n");
			buffer = buffer.append("  </STATE>").append("\n").append("\n");
			num = String.valueOf(j);
			buffer = buffer.append("  <STATE NUMBER=").append("\"").append(num)
					.append("\"").append(">").append("\n");
			buffer = buffer.append("    <Update>").append("\n");
			++j;

		}

		buffer = buffer.append("    </Update>").append("\n");
		buffer = buffer.append("  </STATE>").append("\n").append("\n");
		buffer = buffer.append("</SCENERY>").append("\n");
		return buffer.toString();
	}
}
