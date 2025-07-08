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

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.asmeta.parser.util.ReflectiveVisitor;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.EnumValue;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.RealValue;
import org.asmeta.simulator.value.ReserveValue;
import org.asmeta.simulator.value.SequenceValue;
import org.asmeta.simulator.value.SetValue;
import org.asmeta.simulator.value.StringValue;
import org.asmeta.simulator.value.TupleValue;
import org.asmeta.simulator.value.UndefValue;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.NaturalDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.definitions.domains.StringDomain;
import asmeta.definitions.domains.UndefDomain;

/**
 * String to value converter. this
 */
public class Parser extends ReflectiveVisitor<Value> {

	/**
	 * Returns the next input token.
	 */
	private Scanner scanner;

	/**
	 * Parses a string to be converted to a value (depending on the domain)
	 * 
	 * @param reader a string
	 */
	public Parser(String line) {
		scanner = new Scanner(new StringReader(line));
	}

	/**
	 * Converts the read input into a value.
	 * 
	 * @param domain a domain
	 * @return a value
	 * @throws InputMismatchException
	 */
	public Value visit(Domain domain) throws InputMismatchException {
		try {
			return visit((Object) domain);
		} catch (RuntimeException e) {
			Throwable cause = e.getCause();
			if (e != null && cause instanceof InputMismatchException) {
				throw (InputMismatchException) cause;
			} else {
				throw e;
			}
		}
	}

	/**
	 * Converts the read input into an integer value.
	 * 
	 * @param domain a domain
	 * @return a value
	 * @throws InputMismatchException
	 */
	public IntegerValue visit(IntegerDomain domain) throws InputMismatchException {
		int i = scanner.scanInt();
		return new IntegerValue(i);
	}

	/**
	 * Converts the read input into a natural value.
	 * 
	 * @param domain a domain
	 * @return a value
	 * @throws InputMismatchException
	 */
	public IntegerValue visit(NaturalDomain domain) throws InputMismatchException {
		return visit((IntegerDomain) domain);
	}

	/**
	 * Converts the read input into a real value.
	 * 
	 * @param domain a domain
	 * @return a value
	 * @throws InputMismatchException
	 */
	public RealValue visit(RealDomain domain) throws InputMismatchException {
		double d = scanner.scanReal();
		// System.out.println("scanned real " + d);
		return new RealValue(d);
	}

	/**
	 * Converts the read input into a boolean value.
	 * 
	 * @param domain a domain
	 * @return a value
	 * @throws InputMismatchException
	 */
	public BooleanValue visit(BooleanDomain domain) throws InputMismatchException {
		boolean b = scanner.scanBool();
		return BooleanValue.parseBooleanValue(b);
	}

	/**
	 * Converts the read input into an undefined value.
	 * 
	 * @param domain a domain
	 * @return a value
	 * @throws InputMismatchException
	 */
	public UndefValue visit(UndefDomain domain) throws InputMismatchException {
		scanner.scan("undef");
		return UndefValue.UNDEF;
	}

	/**
	 * Converts the read input into a string value.
	 * 
	 * @param domain a domain
	 * @return a value
	 * @throws InputMismatchException
	 */
	public StringValue visit(StringDomain domain) throws InputMismatchException {
		String s = scanner.scanQuoted();
		assert s.startsWith("\"") : s;
		assert s.endsWith("\"") : s;
		return new StringValue(s.substring(1, s.length() - 1));
	}

	/**
	 * Converts the read input into a tuple value.
	 * 
	 * @param domain a domain
	 * @return a value
	 * @throws InputMismatchException
	 */
	public TupleValue visit(ProductDomain domain) throws InputMismatchException {
		scanner.scan("(");
		List<Domain> baseDomains = domain.getDomains();
		List<Value> collection = new ArrayList<Value>();
		Iterator<Domain> it = baseDomains.iterator();
		if (scanner.peekChar() != ')') {
			int i = 0;
			while (true) {
				i++;
				Domain nextDomain = it.next();
				Value elementValue = visit(nextDomain);
				collection.add(elementValue);
				if (scanner.peekChar() != ',') {
					break;
				}
				scanner.scan(",");
			}
			if (i != baseDomains.size()) {
				throw new InputMismatchException("Expected a " + baseDomains.size() + "elements tuple but" + " found a "
						+ i + "elements tuple.");
			}
		}
		scanner.scan(")");
		return new TupleValue(collection);
	}

	/**
	 * Converts the read input into a set value.
	 * 
	 * @param domain a domain
	 * @return a value
	 * @throws InputMismatchException
	 */
	public SetValue visit(PowersetDomain domain) throws InputMismatchException {
		scanner.scan("{");
		Domain baseDomain = (domain).getBaseDomain();
		Set<Value> collection = new HashSet<Value>();
		if (scanner.peekChar() != '}') {
			while (true) {
				Value elementValue = visit(baseDomain);
				collection.add(elementValue);
				if (scanner.peekChar() != ',') {
					break;
				}
				scanner.scan(",");
			}
		}
		scanner.scan("}");
		return new SetValue(collection);
	}

	/**
	 * Converts the read input into a sequence value.
	 * 
	 * @param domain a domain
	 * @return a value
	 * @throws InputMismatchException
	 */
	public SequenceValue visit(SequenceDomain domain) throws InputMismatchException {
		scanner.scan("[");
		Domain baseDomain = (domain).getDomain();
		List<Value> collection = new ArrayList<Value>();
		if (scanner.peekChar() != ']') {
			while (true) {
				Value elementValue = visit(baseDomain);
				collection.add(elementValue);
				if (scanner.peekChar() != ',') {
					break;
				}
				scanner.scan(",");
			}
		}
		scanner.scan("]");
		return new SequenceValue(collection);
	}

	/**
	 * Converts the read input into an abstract value.
	 * 
	 * @param domain a domain
	 * @return a value
	 * @throws InputMismatchException
	 */
	public ReserveValue visit(AbstractTd domain) throws InputMismatchException {
		String s = scanner.scanId();
		return new ReserveValue(s);
	}

	/**
	 * Converts the read input into an enumeration value.
	 * 
	 * @param domain a domain
	 * @return a value
	 * @throws InputMismatchException
	 */
	public EnumValue visit(EnumTd domain) throws InputMismatchException {
		String s = scanner.scanId();
		return new EnumValue(s);
	}

	/**
	 * Converts the read input into a concrete domain value.
	 * 
	 * @param domain a domain
	 * @return a value
	 * @throws InputMismatchException
	 */
	public Value visit(ConcreteDomain domain) throws InputMismatchException {
		ConcreteDomain concreteDomain = domain;
		return visit(concreteDomain.getTypeDomain());
	}

	// PA: 2013/01/10
	public ReserveValue visit(AgentDomain domain) throws InputMismatchException {
		String s = scanner.scanId();
		// TODO: dovrebbe ritornare un agent value
		return new ReserveValue(s);
	}
}
