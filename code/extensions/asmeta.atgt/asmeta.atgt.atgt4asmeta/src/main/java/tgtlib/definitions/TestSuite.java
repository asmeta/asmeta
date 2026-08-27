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
package tgtlib.definitions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * a collection of test Sequences
 * 
 * use composition with ArrayList<TS> instead of inheritance to permit
 * overriding
 * */
public abstract class TestSuite<
TP extends TestPredicate<? extends TS,?>, 
TS extends TestSequence<? extends TP>
>
		implements Iterable<TS> {

	protected List<TS> content;

	public TestSuite() {
		content = new ArrayList<TS>();
	}
		
	/**
	 * add a test
	 * 
	 * @return
	 */
	public boolean addTest(TS ts) {
		return this.content.add(ts);
	}

	/**
	 * add an entire test suite
	 * 
	 * @return
	 */
	public boolean addAllTest(TestSuite<TP, TS> ts) {
		return this.content.addAll(ts.content);
	}

	/** return the report for a the test suite */
	public String report(){return content.toString();}

	@Override
	public Iterator<TS> iterator() {
		return content.iterator();
	}

	/** return the size: the number of tests (or test results) in the suite */
	public int size() {
		return content.size();
	}

	/**
	 * 
	 * @return the content of the test suite (not modifiable)
	 */
	final public List<TS> getTests() {
		return Collections.unmodifiableList(content);
	}

}
