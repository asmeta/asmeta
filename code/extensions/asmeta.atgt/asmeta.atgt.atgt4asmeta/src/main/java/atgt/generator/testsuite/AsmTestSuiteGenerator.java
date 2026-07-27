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
package atgt.generator.testsuite;

import java.util.List;
import java.util.Vector;

import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;

import atgt.coverage.AsmCoverage;
import atgt.coverage.AsmCoverageTree;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;
import atgt.coverage.AsmTestSuite;
import atgt.coverage.Coverage;
import atgt.coverage.CoveragesVisitorI;
import atgt.coverage.DefaultTestConditionFilter;
import atgt.coverage.TestEvent;
import atgt.coverage.VisitableTPTreeNode;
import atgt.coverage.evalc.AsmCoverageEvaluatorC;
import atgt.coverage.evalc.NavigableAsmInputs;
import atgt.specification.ASMSpecification;
import tgtlib.definitions.expression.visitors.ExpressionEvaluator;
import tgtlib.specification.Specification;

/**
 * main class for the generation of the entire test suite from a specification
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */
public abstract class AsmTestSuiteGenerator
		extends TestSuiteGeneratorCov<Specification, AsmTestSequence, AsmTestCondition, AsmTestSuite, AsmCoverage>
		implements CoveragesVisitorI<AsmTestSuite> {

	/** Logger for this class. */
	private static final Logger log = Logger.getLogger(AsmTestSuiteGenerator.class);

	/**
	 * La lista dei listener registrati per la gestione degli eventi di testing.
	 */
	protected EventListenerList listenerList;

	/** The test event. */
	protected TestEvent testEvent;

	/** The automatic save on file */
	protected boolean automaticSave = false;

	/**
	 * spec to translate.
	 * 
	 * @param _visitor
	 *            : translator
	 * @param _project
	 *            the _project
	 */
	protected AsmTestSuiteGenerator(Specification spec, AsmCoverage cov) {
		super(spec, cov, AsmTestSuite.getAsmTestSuiteFactory());
		//
		// assert spec instanceof ASMSpecification : "for now only ASM
		// specifications";
		this.listenerList = new EventListenerList();
		this.tcFilter = DefaultTestConditionFilter.DefaultTestConditionFilter;
	}

	/**
	 * Adds the test listener.
	 * 
	 * @param l
	 *            the l
	 */
	public void addTestListener(TestListener l) {
		this.listenerList.add(TestListener.class, l);
	}

	/**
	 * Removes the test listener.
	 * 
	 * @param l
	 *            the l
	 */
	public void removeTestListener(TestListener l) {
		this.listenerList.remove(TestListener.class, l);
	}

	/**
	 * Fire test condition started.
	 * 
	 * @param source
	 *            the source
	 */
	public void fireTestConditionStarted(Object source) {
		// Ritorna un array di Object sempre diverso da null
		Object[] listeners = this.listenerList.getListenerList();
		// Notifica ai listener interessati l'evento che si e'
		// verificato. Comincia dalla fine della lista.
		this.testEvent = new TestEvent(source);
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TestListener.class) {
				((TestListener) listeners[i + 1]).TestConditionStarted(this.testEvent);
			}
		}
	}

	/**
	 * Fire test condition completed.
	 * 
	 * @param source
	 *            the source
	 */
	public void fireTestConditionCompleted(Object source) {
		// Ritorna un array di Object sempre diverso da null
		Object[] listeners = this.listenerList.getListenerList();
		// Notifica ai listener interessati l'evento che si ???
		// verificato. Comincia dalla fine della lista.
		this.testEvent = new TestEvent(source);
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TestListener.class) {
				((TestListener) listeners[i + 1]).TestConditionCompleted(this.testEvent);
			}
		}
	}

	/**
	 * Fire test condition error.
	 * 
	 * @param source
	 *            the source
	 * @param description
	 *            the description
	 */
	public void fireTestConditionError(Object source, String description) {
		// Ritorna un array di Object sempre diverso da null
		Object[] listeners = this.listenerList.getListenerList();
		// Notifica ai listener interessati l'evento che si ???
		// verificato. Comincia dalla fine della lista.
		this.testEvent = new TestEvent(source, description);
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TestListener.class) {
				((TestListener) listeners[i + 1]).TestConditionError(this.testEvent);
			}
		}
	}

	/**
	 * Fire test condition step completed.
	 * 
	 * @param source
	 *            the source
	 * @param description
	 *            the description
	 */
	public void fireTestConditionStepCompleted(Object source, String description) {
		// Ritorna un array di Object sempre diverso da null
		Object[] listeners = this.listenerList.getListenerList();
		// Notifica ai listener interessati l'evento che si ???
		// verificato. Comincia dalla fine della lista.
		this.testEvent = new TestEvent(source, description);
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TestListener.class) {
				((TestListener) listeners[i + 1]).TestConditionStepCompleted(this.testEvent);
			}
		}
	}

	/**
	 * Fire coverage completed.
	 * 
	 * @param source
	 *            the source
	 */
	public void fireCoverageCompleted(Object source) {
		// Ritorna un array di Object sempre diverso da null
		TestListener[] listeners = this.listenerList.getListeners(TestListener.class);
		// Notifica ai listener interessati l'evento che si ???
		// verificato. Comincia dalla fine della lista.
		TestEvent te = new TestEvent(source);
		for (TestListener t : listeners) {
			t.CoverageCompleted(te);
		}
	}

	/**
	 * Fire coverages completed.
	 * 
	 * @param source
	 *            the source
	 */
	public void fireCoveragesCompleted(Object source) {
		// Ritorna un array di Object sempre diverso da null
		Object[] listeners = this.listenerList.getListenerList();
		// Notifica ai listener interesati l'evento che si ???
		// verificato. Comincia dalla dine della lista.
		this.testEvent = new TestEvent(source);
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TestListener.class) {
				((TestListener) listeners[i + 1]).CoveragesCompleted(this.testEvent);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.coverage.TPTreeNodeVisitor#forCoverage(atgt.specification
	 * .coverage.Coverage)
	 */
	@Override
	abstract public AsmTestSuite forCoverage(Coverage cv);

	/**
	 * generates the tests simply calling the test generator method for every
	 * test predicate in the coverage. return true if successful
	 *
	 * @author garganti
	 */
	@Override
	public AsmTestSuite forCoverageTree(AsmCoverageTree cvgs) {
		log.debug("starting test generation for AsmCoverageTree - " + cvgs.getName());
		AsmTestSuite result = new AsmTestSuite();
		for (VisitableTPTreeNode c : cvgs.allCoverages()) {
			AsmTestSuite partial = c.accept(this);
			result.addAllTest(partial);
			// log.debug("finished with "+ partial.size() + " total " +
			// result.size());
			fireCoverageCompleted(c);
		}
		fireCoveragesCompleted(cvgs);
		return result;
	}

	/**
	 * 
	 * @param ts
	 * @return the test conditions covered by ts.
	 */
	@Override
	protected List<AsmTestCondition> computeCoverage(AsmTestSequence ts) {
		// choose the right evaluator
		Vector<AsmTestCondition> tgCovered = null;
		boolean covCompleted = false;
		if (ts.allInstructions().size() == 1) {
			log.debug("using the simple evaluator");
			tgCovered = new Vector<AsmTestCondition>();
			// build the simple evaluator
			try {
				ExpressionEvaluator ev = new ExpressionEvaluator(ts.allInstructions().get(0),
						specification.getVariables());
				for (AsmTestCondition current : coverage.allTPs()) {
					if (current.getCondition().accept(ev)) {
						tgCovered.add(current);
					}
				}
				covCompleted = true;
			} catch (tgtlib.definitions.expression.visitors.ModelIncomplete me) {
				log.debug("Model incomplete (" + me.getMessage() + ") not possible with simple evaluator, switch to c");
			} catch (tgtlib.definitions.expression.visitors.EvaluationNotSupported es) {
				log.debug("Evaluation not supported (" + es.getMessage()
						+ ")not possible with simple evaluator, switch to c");
			}
		}
		if (!covCompleted) {
			log.debug("using the evaluator to C");
			// build the coverage evaluator
			try {
				AsmCoverageEvaluatorC evaluator = new AsmCoverageEvaluatorC((ASMSpecification) specification, coverage);
				// compute coverage
				tgCovered = evaluator.computeCoverage(new NavigableAsmInputs(ts, (ASMSpecification) specification));
			} catch (ClassCastException cce) {
				throw new Error("Class cast exception \nspecification = " + specification.getClass().getSimpleName());
			}

		}
		return tgCovered;
	}

	@Override
	protected final void addTestsForCoverage(AsmCoverage cov, AsmTestSuite testSuite) {
		// run with the coverage and then accept
		// TODO make a proper method that takes an tc at the time (or something
		// similar)
		AsmTestSuite result = cov.accept(this);
		testSuite.addAllTest(result);
	}
}
