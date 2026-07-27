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
package atgt.specification;

import atgt.specification.location.Function;
import atgt.specification.location.Location;
import atgt.specification.type.DummyType;
import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.Type;

// TODO: Auto-generated Javadoc
/**
 * A data structure to handle ASM specification.
 * 
 * @author Sax Rinzivillo, Angelo Gargantini
 */
public class AsmGoferSpecification extends ASMSpecification{

	/** State variable for Set Types definition. */
	protected state State;

	/**
	 * Instantiates a new aSM specification.
	 */
	public AsmGoferSpecification() {
		super();
		this.State = new invalidState();
	}

	/**
	 * Return the type object for the description string.
	 * 
	 * @param typename
	 *            the typename
	 * 
	 * @return The Type object or
	 * 
	 * <PRE>
	 * 
	 * null
	 * 
	 * </PRE>
	 * 
	 * if not found.
	 */
	@Override
	public Type getTypeFor(String typename) {
		Type result;
		result = this.types.get(typename);

		if ((result != null) && ((result instanceof EnumType)))
			return result;

		if (this.State.isValid()) {
			result = this.State.getTypeFor(typename);
			this.State = this.State.invalidate();
			addType(result);
		} else {
			result = new DummyType(typename);
			// do not add to avoid several dummy types !!!
			//addType(result);
		}

		return result;
	}

	/**
	 * signals that a variable gets updated.
	 * 
	 * @param varName
	 *            the var name
	 */
	public void variableUpdated(String varName) {
		Location result;
		result = this.variables.get(varName);
		if (result != null)
			result.setControlled();
		else
		  throw new RuntimeException("Variabile " + varName + " non trovata\n");
	}

	// Potrebbe non essere necessaria poiche' se la funzione viene cambiata
	// dinamicamente
	// automaticamente passa a Controlled
	/**
	 * Function updated.
	 * 
	 * @param funName
	 *            the fun name
	 */
	public void functionUpdated(String funName) {
		Function result = this.functions.get(funName);
		if (result != null)
			result.setControlled();
	}

	/**
	 * Update the interval values.
	 * 
	 * @param low
	 *            the low
	 * @param up
	 *            the up
	 */
	public void setInterval(int low, int up) {
		this.State = this.State.updateInterval(low, up);
	}

	/**
	 * Update the delta value.
	 * 
	 * @param delta
	 *            the delta
	 */
	public void setDelta(int delta) {
		this.State = this.State.updateDelta(delta);
	}

	/**
	 * The Class state.
	 */
	abstract class state {

		/** The low. */
		protected int low;

		/** The up. */
		protected int up;

		/** The delta. */
		protected int delta;

		/**
		 * Checks if is valid.
		 * 
		 * @return true, if is valid
		 */
		public boolean isValid() {
			return false;
		}

		/**
		 * Invalidate.
		 * 
		 * @return the state
		 */
		public state invalidate() {
			return new invalidState();
		}

		/**
		 * Update interval.
		 * 
		 * @param _low
		 *            the _low
		 * @param _up
		 *            the _up
		 * 
		 * @return the state
		 */
		abstract public state updateInterval(int _low, int _up);

		/**
		 * Update delta.
		 * 
		 * @param _delta
		 *            the _delta
		 * 
		 * @return the state
		 */
		abstract public state updateDelta(int _delta);

		/**
		 * Gets the type for.
		 * 
		 * @param typeDescr
		 *            the type descr
		 * 
		 * @return the type for
		 */
		abstract public Type getTypeFor(String typeDescr);
	}

	/**
	 * The Class invalidState.
	 */
	class invalidState extends state {

		/**
		 * Trans to a new state where the boundaries are set.
		 * 
		 * @param _low
		 *            the _low
		 * @param _up
		 *            the _up
		 * 
		 * @return the state
		 */
		@Override
		public state updateInterval(int _low, int _up) {
			return new validNoDeltaState(_low, _up);
		}

		/**
		 * There was an attempt to set the delta value without setting the
		 * boundaries before. In this case remain in the same invalid state
		 * 
		 * @param _delta
		 *            the _delta
		 * 
		 * @return the state
		 */
		@Override
		public state updateDelta(int _delta) {
			return this;
		}

		/**
		 * No boundaries was set for this type. Returns a dummy type.
		 * 
		 * @param typeDescr
		 *            the type descr
		 * 
		 * @return the type for
		 */
		@Override
		public Type getTypeFor(String typeDescr) {
			// Meglio pensare a qlc per eliminare null come valore di ritorno
			// Sarebbe + corretto pensare ad una classe opportuna.
			// Che ne dici di "undefType"?????
			return null;
		}
	}

	/**
	 * The Class validNoDeltaState.
	 */
	class validNoDeltaState extends state {

		/**
		 * Instantiates a new valid no delta state.
		 * 
		 * @param _low
		 *            the _low
		 * @param _up
		 *            the _up
		 */
		public validNoDeltaState(int _low, int _up) {
			this.low = _low;
			this.up = _up;
		}

		/**
		 * The previous boundaries was changed. Remain in the same state and
		 * wait for a value for delta
		 * 
		 * @param _low
		 *            the _low
		 * @param _up
		 *            the _up
		 * 
		 * @return the state
		 */
		@Override
		public state updateInterval(int _low, int _up) {
			return new validNoDeltaState(_low, _up);
		}

		/**
		 * Was set a value for delta.
		 * 
		 * @param _delta
		 *            the _delta
		 * 
		 * @return the state
		 */
		@Override
		public state updateDelta(int _delta) {
			return new validState(this.low, this.up, _delta);
		}

		/**
		 * Return a new SetType.
		 * 
		 * @param typeDescr
		 *            the type descr
		 * 
		 * @return the type for
		 */
		@Override
		public Type getTypeFor(String typeDescr) {
			return new BoundType(typeDescr, this.low, this.up);
		}
	}

	/**
	 * The Class validState.
	 */
	class validState extends state {

		/**
		 * Instantiates a new valid state.
		 * 
		 * @param _low
		 *            the _low
		 * @param _up
		 *            the _up
		 * @param _delta
		 *            the _delta
		 */
		public validState(int _low, int _up, int _delta) {
			this.low = _low;
			this.up = _up;
			this.delta = _delta;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.specification.ASMSpecification.state#isValid()
		 */
		@Override
		public boolean isValid() {
			return true;
		}

		/**
		 * The previous boundaries was changed. Remain in the same state and
		 * wait for a value for delta
		 * 
		 * @param _low
		 *            the _low
		 * @param _up
		 *            the _up
		 * 
		 * @return the state
		 */
		@Override
		public state updateInterval(int _low, int _up) {
			return new validState(_low, _up, this.delta);
		}

		/**
		 * Was set a new value for delta.
		 * 
		 * @param _delta
		 *            the _delta
		 * 
		 * @return the state
		 */
		@Override
		public state updateDelta(int _delta) {
			return new validState(this.low, this.up, _delta);
		}

		/**
		 * Return a new BoundType.
		 * 
		 * @param typeDescr
		 *            the type descr
		 * 
		 * @return the type for
		 */
		@Override
		public Type getTypeFor(String typeDescr) {
			return new BoundType(typeDescr, this.low, this.up, this.delta);
		}
	}

}
