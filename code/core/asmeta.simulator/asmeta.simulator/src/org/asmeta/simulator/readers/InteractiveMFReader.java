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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Map.Entry;

import org.asmeta.parser.Defs;
import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.UpdateSet;
import org.asmeta.simulator.util.DomainPrinter;
import org.asmeta.simulator.util.InputMismatchException;
import org.asmeta.simulator.util.Parser;
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

import asmeta.definitions.Function;
import asmeta.definitions.domains.AbstractTd;
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
 * La classe legge e converte le rappresentazioni in formato stringa delle
 * costanti acquisite da un input stream. La classe inoltre stampa alcuni
 * messaggi diagnostici per facilitare l'inserimento dei dati da parte
 * dell'utente.
 * 
 * @author Alessandro Carioni [acarioni@tele2.it]
 *
 */
public class InteractiveMFReader extends MonFuncReader {

	/**
	 * Keeps the association between a monitored location and the value.
	 */
	// Map<Location, Value> monOldMap = new HashMap<Location, Value>();

	/**
	 * Input stream
	 */
	private BufferedReader in;

	/**
	 * Output stream
	 */
	private PrintStream out;

	private String line;
	protected DomainPrinter domainPrinter;

	/**
	 * Costruisce un nuovo oggetto.
	 * 
	 * @param in  input stream
	 * @param out output stream
	 */
	public InteractiveMFReader(InputStream in, PrintStream out) {
		this(new BufferedReader(new InputStreamReader(in)), out);
	}

	protected InteractiveMFReader(BufferedReader in, PrintStream out) {
		this.out = out;
		this.in = in;
		domainPrinter = new DomainPrinter();
	}

	public PrintStream getOut() {
		return out;
	}

	public void setOut(PrintStream out) {
		this.out = out;
	}

	public String getLine() {
		return line;
	}

	@Override
	public Value readValue(Location location, State state) {
		Function func = location.getSignature();
		// get the old value of location if any
		// TODO
		out.println("Insert a " + domainPrinter.visit(func.getCodomain()) + " for " + location.toString() + ":");
		return visit(func.getCodomain());
	}

	// FIXME : to put into read Location
	public UpdateSet readAll(State state) {
		UpdateSet updates = new UpdateSet();
		for (Entry<Location, Value> entry : state) {
			Location loc = entry.getKey();
			Value value = entry.getValue();
			Function sig = loc.getSignature();
			if (Defs.isMonitored(sig) || Defs.isShared(sig)) {
				out.println(loc + " [" + value + "]?");
				readLine();
				if (!line.isEmpty()) {
					Value newValue = read(loc, state);
					updates.putUpdate(loc, newValue);
				}
			}
		}
		return updates;
	}

	/**
	 * Legge una linea di testo.
	 */
	public void readLine() {
		try {
			line = in.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void setLine(String line) {
		this.line = line;
	}

	public Value visit(Domain domain) {
		return visit((Object) domain);
	}

	public IntegerValue visit(IntegerDomain domain) throws InputMismatchException {
		IntegerValue value = null;
		for (;;) {
			try {
				readLine();
				value = new Parser(line).visit(domain);
			} catch (InputMismatchException e) {
				out.println(e.getMessage());
				continue;
			}
			break;
		}
		return value;
	}

	public IntegerValue visit(NaturalDomain domain) throws InputMismatchException {
		return visit((IntegerDomain) domain);
	}

	public RealValue visit(RealDomain domain) throws InputMismatchException {
		RealValue value = null;
		for (;;) {
			try {
				readLine();
				value = new Parser(line).visit(domain);
			} catch (InputMismatchException e) {
				out.println(e.getMessage());
				continue;
			}
			break;
		}
		return value;
	}

	public BooleanValue visit(BooleanDomain domain) throws InputMismatchException {
		BooleanValue value = null;
		for (;;) {
			try {
				readLine();
				value = new Parser(line).visit(domain);
			} catch (InputMismatchException e) {
				out.println(e.getMessage());
				continue;
			}
			break;
		}
		return value;
	}

	public UndefValue visit(UndefDomain domain) throws InputMismatchException {
		UndefValue value = null;
		out.println("Insert an undef constant:");
		for (;;) {
			try {
				readLine();
				value = new Parser(line).visit(domain);
			} catch (InputMismatchException e) {
				out.println(e.getMessage());
				continue;
			}
			break;
		}
		return value;
	}

	public StringValue visit(StringDomain domain) throws InputMismatchException {
		StringValue value = null;
		for (;;) {
			try {
				readLine();
				value = new Parser(line).visit(domain);
			} catch (InputMismatchException e) {
				out.println(e.getMessage());
				continue;
			}
			break;
		}
		return value;
	}

	public TupleValue visit(ProductDomain domain) throws InputMismatchException {
		TupleValue value = null;
		for (;;) {
			try {
				readLine();
				value = new Parser(line).visit(domain);
			} catch (InputMismatchException e) {
				out.println(e.getMessage());
				continue;
			}
			break;
		}
		return value;
	}

	public SetValue visit(PowersetDomain domain) throws InputMismatchException {
		SetValue value = null;
		out.println("Insert a set:");
		for (;;) {
			try {
				readLine();
				value = new Parser(line).visit(domain);
			} catch (InputMismatchException e) {
				out.println(e.getMessage());
				continue;
			}
			break;
		}
		return value;
	}

	public SequenceValue visit(SequenceDomain domain) throws InputMismatchException {
		SequenceValue value = null;
		for (;;) {
			try {
				readLine();
				value = new Parser(line).visit(domain);
			} catch (InputMismatchException e) {
				out.println(e.getMessage());
				continue;
			}
			break;
		}
		return value;
	}

	public ReserveValue visit(AbstractTd domain) throws InputMismatchException {
		ReserveValue value = null;
		for (;;) {
			try {
				readLine();
				value = new Parser(line).visit(domain);
			} catch (InputMismatchException e) {
				out.println(e.getMessage());
				continue;
			}
			if (!checkAbstract(domain, value)) {
				out.println(
						"The constant " + value.getValue() + " doesn't belong to abstract domain " + domain.getName());
				continue;
			}
			break;
		}
		return value;
	}

	public EnumValue visit(EnumTd domain) throws InputMismatchException {
		EnumValue value = null;
		for (;;) {
			try {
				readLine();
				value = new Parser(line).visit(domain);
			} catch (InputMismatchException e) {
				out.println(e.getMessage());
				continue;
			}
			if (!checkEnum(domain, value)) {
				out.println("The constant " + value.getValue() + " doesn't belong to enum domain " + domain.getName());
				continue;
			}
			break;
		}
		return value;
	}

	public Value visit(ConcreteDomain domain) throws InputMismatchException {
		Value value = null;
		for (;;) {
			try {
				readLine();
				value = new Parser(line).visit(domain);
			} catch (InputMismatchException e) {
				out.println(e.getMessage());
				continue;
			}
			if (!checkConcrete(domain, value)) {
				out.println("The constant " + value + " doesn't belong to concrete domain " + domain.getName());
				continue;
			}
			break;
		}
		return value;
	}

	/** does it support lzy evaluation??? */
	public boolean supportsLazyTermEval() {
		return true;
	} 

	
}
