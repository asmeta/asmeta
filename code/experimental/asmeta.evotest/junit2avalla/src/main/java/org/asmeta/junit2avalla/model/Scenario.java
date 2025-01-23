package org.asmeta.junit2avalla.model;

import java.util.LinkedList;
import java.util.Queue;

import org.asmeta.junit2avalla.model.terms.AvallaStepTerm;
import org.asmeta.junit2avalla.model.terms.AvallaTerm;

/**
 * The {@code Scenario} class represents a scenario that consists of a queue of
 * {@link AvallaTerm} objects.
 *
 * <p>
 * This class manages the sequence of terms used in a scenario and tracks
 * whether the scenario is valid based on the inclusion of at least one
 * {@link AvallaStepTerm}.
 * </p>
 *
 * <p>
 * The validity of the scenario is updated when an {@link AvallaStepTerm} is
 * added to the scenario, and it uses a {@link LinkedList} internally to
 * maintain the order of the terms.
 * </p>
 */
public class Scenario {

	/**
	 * A queue of {@link AvallaTerm} objects representing the scenario.
	 */
	private final Queue<AvallaTerm> scenarioObject;

	/**
	 * Flag indicating whether the scenario contains at least one
	 * {@link AvallaStepTerm}, making it valid.
	 */
	private boolean valid;

	/**
	 * Default constructor that initializes an empty scenario with an invalid state.
	 */
	public Scenario() {
		this.scenarioObject = new LinkedList<>();
		this.valid = false;
	}

	/**
	 * Returns the queue of {@link AvallaTerm} objects in the scenario.
	 *
	 * @return the scenario queue.
	 */
	public Queue<AvallaTerm> getScenario() {
		return scenarioObject;
	}

	/**
	 * Adds an {@link AvallaTerm} to the scenario.
	 *
	 * <p>
	 * If the term is an instance of {@link AvallaStepTerm}, the scenario is marked
	 * as valid.
	 * </p>
	 *
	 * @param avallaTerm the term to be added to the scenario.
	 */
	public void add(AvallaTerm avallaTerm) {
		if (avallaTerm instanceof AvallaStepTerm) {
			this.valid = true;
		}
		this.scenarioObject.add(avallaTerm);
	}

	/**
	 * Removes and returns the first term in the scenario queue.
	 *
	 * @return the removed term.
	 */
	public AvallaTerm remove() {
		return this.scenarioObject.remove();
	}

	/**
	 * Retrieves, but does not remove, the first term in the scenario queue.
	 *
	 * @return the first term in the scenario.
	 */
	public AvallaTerm element() {
		return this.scenarioObject.element();
	}

	/**
	 * Returns whether the scenario is valid, i.e., contains at least one
	 * {@link AvallaStepTerm}.
	 *
	 * @return {@code true} if the scenario is valid, {@code false} otherwise.
	 */
	public boolean isValid() {
		return valid;
	}

}