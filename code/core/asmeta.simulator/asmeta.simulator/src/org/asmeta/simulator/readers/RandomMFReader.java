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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.asmeta.simulator.Location;
import org.asmeta.simulator.State;
import org.asmeta.simulator.TermEvaluator;
import org.asmeta.simulator.value.BooleanValue;
import org.asmeta.simulator.value.EnumValue;
import org.asmeta.simulator.value.IntegerValue;
import org.asmeta.simulator.value.RealValue;
import org.asmeta.simulator.value.ReserveValue;
import org.asmeta.simulator.value.SequenceValue;
import org.asmeta.simulator.value.SetValue;
import org.asmeta.simulator.value.StringValue;
import org.asmeta.simulator.value.TupleValue;
import org.asmeta.simulator.value.Value;

import asmeta.definitions.Function;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.NaturalDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.definitions.domains.StringDomain;

/**
 * simulates a random user
 */
public class RandomMFReader extends MonFuncReader {
	
	
	private final static Random random = new Random();

	public RandomMFReader() {
		this(System.out);
	}

	/**
	 * Random.
	 * 
	 * @param out the output where to show the choices
	 */
	public RandomMFReader(PrintStream out) {
		this.out = out;
	}

	public RandomMFReader(State state) {
		this();
		this.state = state;
	}

	public RandomMFReader(State state, PrintStream out) {
		this(out);
		this.state = state;
	}

	/**
	 * Output stream
	 */
	private PrintStream out;

	@Override
	public Value readValue(Location location, State state) {
		Function func = location.getSignature();
		Value rndValue = visit(func.getCodomain());
		assert rndValue != null;
		out.println("taking " + rndValue + " for " + location);
		return rndValue;
	}

	public Value visit(Domain domain) {
		return visit((Object) domain);
	}

	public IntegerValue visit(IntegerDomain domain) {
		return new IntegerValue(random.nextInt());
	}

	public IntegerValue visit(NaturalDomain domain) {
		return new IntegerValue(random.nextInt(Integer.MAX_VALUE));
	}

	public RealValue visit(RealDomain domain) {
		boolean sign = random.nextBoolean();
		if (sign)
			return new RealValue(random.nextDouble() * Double.MAX_VALUE);
		else
			return new RealValue(-random.nextDouble() * Double.MAX_VALUE);
	}

	public BooleanValue visit(BooleanDomain domain) {
		return BooleanValue.parseBooleanValue(random.nextBoolean());
	}

	public EnumValue visit(asmeta.definitions.domains.EnumTd domain) {
		int numEl = domain.getElement().size();
		int take = random.nextInt(numEl);
		int i = 0;
		for (Object o : domain.getElement()) {
			EnumElement element = (EnumElement) o;
			if (i == take)
				return new EnumValue(element);
			i++;
		}
		return null;

	}

	public StringValue visit(StringDomain domain) {
		return new StringValue("fixedrandomestring");
	}

	static final int max_no_elements = 4;

	public SequenceValue visit(SequenceDomain domain) {
		List<Value> value = new ArrayList<Value>();
		// take 0 to max_no_elements values
		int numEl = random.nextInt(max_no_elements);
		for (int i = 0; i <= numEl; i++) {
			Value val_i = visit(domain.getDomain());
			if (val_i == null)
				break;
			value.add(val_i);
		}
		return new SequenceValue(value);
	}

	/** returns null if an element is null */

	public TupleValue visit(ProductDomain domain) {
//		int n = domain.getDomains().size();
		List<Value> value = new ArrayList<Value>();
		for (Object o : domain.getDomains()) {
			Value val_i = visit(o);
			if (val_i == null)
				return null;
			value.add(val_i);
		}
		return new TupleValue(value);
	}

	/** returns null if the abstract domain is empty */
	public ReserveValue visit(AbstractTd domain) {
		return (ReserveValue) getRndElement(domain);
	}

	private Value getRndElement(Domain domain) {
		TermEvaluator tm = new TermEvaluator(state, null, null);
		SetValue set = tm.getValues(domain);
		int n_elements = set.getValue().size();
		if (n_elements == 0)
			return null;
		int i = random.nextInt(n_elements);
		return (Value) set.getValue().toArray()[i];
	}

	public Value visit(ConcreteDomain domain) {
		return getRndElement(domain);
	}
}
