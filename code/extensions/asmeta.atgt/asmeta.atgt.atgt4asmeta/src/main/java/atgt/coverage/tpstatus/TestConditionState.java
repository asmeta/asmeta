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
package atgt.coverage.tpstatus;

/**
 * represents possible values of state in a test condition.
 */

public enum TestConditionState {

	AssertViolated("Assert violated") {
		/*
		 * discardTest -> TestDiscarded setCovered(true) -> AssertViolated
		 * setCovered(false) -> AssertViolated setRunning -> Running
		 * setSelected(true) -> Quequed setSelected(false) -> itself
		 * 
		 */

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.coverage.tpstatus.TestConditionState#setCovered(boolean,
		 *      atgt.coverage.tpstatus.TestConditionFSM)
		 */
		@Override
		public void setCovered(boolean b, TestConditionFSM fsm) {
			// DO NOTHING
		}

		/**
		 * In base al valore di <code>b</code> decide la transizione verso un
		 * nuovo stato. Questa transizione avviene dopo la selezione del test
		 * condition a causa dell'utente.
		 * 
		 * @param b
		 *            the b
		 * @param fsm
		 *            the fsm
		 */
		@Override
		public void setSelected(boolean b, TestConditionFSM fsm) {
			if (b) {
				fsm.setState(Queued);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.coverage.tpstatus.TestConditionState#setRunning(atgt.coverage.tpstatus.TestConditionFSM)
		 */
		@Override
		public void setRunning(TestConditionFSM fsm) {
			fsm.setState(TestConditionState.Running);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.coverage.tpstatus.TestConditionState#setInfeasible(atgt.coverage.tpstatus.TestConditionFSM)
		 */
		@Override
		public void setInfeasible(TestConditionFSM fsm) {
			fsm.setState(UNFEASIBLE);
		}
	},

	Covered("Covered") {
		// TODO: Auto-generated Javadoc
		/*
		 * discardTest -> TestDiscarded setCovered(true) -> nothing
		 * setCovered(false) -> nothin setRunning -> Running setSelected(true) ->
		 * quequed setSelected(false) -> nothing
		 * 
		 */

		/**
		 * In base al valore di <code>b</code> decide la transizione verso un
		 * nuovo stato. Questa transizione avviene dopo la verifica di un caso
		 * di test.
		 * 
		 * @param b
		 *            the b
		 * @param fsm
		 *            the fsm
		 */
		@Override
		public void setCovered(boolean b, TestConditionFSM fsm) {
			//
		}

		/**
		 * In base al valore di <code>b</code> decide la transizione verso un
		 * nuovo stato. Questa transizione avviene dopo la selezione del test
		 * condition a causa dell'utente.
		 * 
		 * @param b
		 *            the b
		 * @param fsm
		 *            the fsm
		 */
		@Override
		public void setSelected(boolean b, TestConditionFSM fsm) {
			if (b) {
				fsm.setState(TestConditionState.Queued);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.coverage.tpstatus.TestConditionState#setRunning(atgt.coverage.tpstatus.TestConditionFSM)
		 */
		@Override
		public void setRunning(TestConditionFSM fsm) {
			fsm.setState(TestConditionState.Running);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.coverage.tpstatus.TestConditionState#setInfeasible(atgt.coverage.tpstatus.TestConditionFSM)
		 */
		@Override
		public void setInfeasible(TestConditionFSM fsm) {
			fsm.setState(UNFEASIBLE);

		}
	},

	/*
	 * discardTest -> TestDiscarded setCovered(true) -> Covered
	 * setCovered(false) -> prevoius setRunning -> running setSelected(true) ->
	 * itself setSelected(false) -> previous
	 * 
	 */

	Queued("Queued") {

		/**
		 * Se dopo la verifica il test ha provocato una asser violation allora
		 * si passa allo stato <code>TestConditionAssertViolated</code>
		 * altrimenti si passa allo stato <code>Unknown</code>.
		 * 
		 * @param b
		 *            the b
		 * @param fsm
		 *            the fsm
		 */
		@Override
		public void setCovered(boolean b, TestConditionFSM fsm) {
			if (b) {
				fsm.setState(TestConditionState.Covered);
			} else {
				fsm.restoreState();
			}
		}

		/**
		 * Se il test viene deselezionato si passa allo stato precedente, se
		 * questo esiste. Altrimenti si ritorna allo stato iniziale.
		 * 
		 * @param b
		 *            the b
		 * @param fsm
		 *            the fsm
		 */
		@Override
		public void setSelected(boolean b, TestConditionFSM fsm) {
			if (b) {
				// do nothing
			} else {
				fsm.restoreState();
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.coverage.tpstatus.TestConditionState#setRunning(atgt.coverage.tpstatus.TestConditionFSM)
		 */
		@Override
		public void setRunning(TestConditionFSM fsm) {
			fsm.setState(TestConditionState.Running);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.coverage.tpstatus.TestConditionState#setInfeasible(atgt.coverage.tpstatus.TestConditionFSM)
		 */
		@Override
		public void setInfeasible(TestConditionFSM fsm) {
			fsm.setState(UNFEASIBLE);
		}

	},
	Running("Running") {
		/*
		 * discardTest -> donothing setCovered(true) -> AssertViolated
		 * setCovered(false) -> Unknown setRunning -> itself setSelected(true) ->
		 * nothing setSelected(false) -> nothing
		 * 
		 */

		/**
		 * Se dopo la verifica il test predicate is covered, than it becomes
		 * assertion Violated otherwis eis unknown.
		 * 
		 * @param b
		 *            the b
		 * @param fsm
		 *            the fsm
		 */
		@Override
		public void setCovered(boolean b, TestConditionFSM fsm) {
			if (b)
				fsm.setState(AssertViolated);
			else
				fsm.setState(Unknown);
		}

		/**
		 * non posso selezionare quando runnning.
		 * 
		 * @param b
		 *            the b
		 * @param fsm
		 *            the fsm
		 */
		@Override
		public void setSelected(boolean b, TestConditionFSM fsm) {
			// do nothing
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.coverage.tpstatus.TestConditionState#setRunning(atgt.coverage.tpstatus.TestConditionFSM)
		 */
		@Override
		public void setRunning(TestConditionFSM fsm) {
			// DO NOTHING
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.coverage.tpstatus.TestConditionState#setInfeasible(atgt.coverage.tpstatus.TestConditionFSM)
		 */
		@Override
		public void setInfeasible(TestConditionFSM fsm) {
			fsm.setState(UNFEASIBLE);
		}

	},
	TODO("To do") {
		/*
		 * discardTest -> setCovered(true) -> Covered.Covered setCovered(false) ->
		 * itself setRunning -> running setSelected(true) -> Queued.Queued
		 * setSelected(false) -> itself
		 * 
		 */

		/**
		 * Se il test condition viene coperto da un altro caso di test si passa
		 * allo stato <code>Covered</code>. Altrimenti si rimane nello stato
		 * corrente. Questo secondo caso in effetti non si presenta
		 * direttamente. Nel senso che, se un caso di test non copre questo test
		 * condition, questo metodo non viene invocato. Sarebbe inutile
		 * invocarlo con <code>b = false</code>.
		 * 
		 * @param b
		 *            the b
		 * @param fsm
		 *            the fsm
		 */
		@Override
		public void setCovered(boolean b, TestConditionFSM fsm) {
			if (b)
				fsm.setState(Covered);
		}

		/**
		 * Il test viene selezionato per la verifica.
		 * 
		 * @param b
		 *            the b
		 * @param fsm
		 *            the fsm
		 */
		@Override
		public void setSelected(boolean b, TestConditionFSM fsm) {
			if (b) {
				fsm.setState(TestConditionState.Queued);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.coverage.tpstatus.TestConditionState#setRunning(atgt.coverage.tpstatus.TestConditionFSM)
		 */
		@Override
		public void setRunning(TestConditionFSM fsm) {
			fsm.setState(Running);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.coverage.tpstatus.TestConditionState#setInfeasible(atgt.coverage.tpstatus.TestConditionFSM)
		 */
		@Override
		public void setInfeasible(TestConditionFSM fsm) {
			fsm.setState(UNFEASIBLE);
		}

	},
	UNFEASIBLE("Infeasible") {

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.coverage.tpstatus.TestConditionState#setCovered(boolean,
		 *      atgt.coverage.tpstatus.TestConditionFSM)
		 */
		@Override
		public void setCovered(boolean b, TestConditionFSM fsm) {
			// TODO Auto-generated method stub

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.coverage.tpstatus.TestConditionState#setRunning(atgt.coverage.tpstatus.TestConditionFSM)
		 */
		@Override
		public void setRunning(TestConditionFSM fsm) {
			// TODO Auto-generated method stub

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.coverage.tpstatus.TestConditionState#setSelected(boolean,
		 *      atgt.coverage.tpstatus.TestConditionFSM)
		 */
		@Override
		public void setSelected(boolean b, TestConditionFSM fsm) {
			// TODO Auto-generated method stub

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.coverage.tpstatus.TestConditionState#setInfeasible(atgt.coverage.tpstatus.TestConditionFSM)
		 */
		@Override
		public void setInfeasible(TestConditionFSM fsm) {
			// TODO Auto-generated method stub

		}
	},
	Unknown("Unknown") {
		// TODO: Auto-generated Javadoc
		/*
		 * discardTest -> setCovered(true) -> setCovered(false) -> setRunning ->
		 * running setSelected(true) -> Queued setSelected(false) -> unknown
		 * 
		 */

		/**
		 * Se il test viene coperto da un altro caso di test si passa allo stato
		 * <code>Covered</code>.
		 * 
		 * @param b
		 *            the b
		 * @param fsm
		 *            the fsm
		 */
		@Override
		public void setCovered(boolean b, TestConditionFSM fsm) {
			if (b)
				fsm.setState(Covered);
		}

		/**
		 * Se il caso viene selezionato per l'analisi, si passa allo stato
		 * <code>Queued</code>.
		 * 
		 * @param b
		 *            the b
		 * @param fsm
		 *            the fsm
		 */
		@Override
		public void setSelected(boolean b, TestConditionFSM fsm) {
			if (b) {
				fsm.setState(TestConditionState.Queued);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.coverage.tpstatus.TestConditionState#setRunning(atgt.coverage.tpstatus.TestConditionFSM)
		 */
		@Override
		public void setRunning(TestConditionFSM fsm) {
			fsm.setState(TestConditionState.Running);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.coverage.tpstatus.TestConditionState#setInfeasible(atgt.coverage.tpstatus.TestConditionFSM)
		 */
		@Override
		public void setInfeasible(TestConditionFSM fsm) {
			fsm.setState(UNFEASIBLE);
		}

	};

	private String description;

	private TestConditionState(String d) {
		description = d;
	}

	/**
	 * In base al valore di <code>b</code> decide la transizione verso un
	 * nuovo stato. Questa transizione avviene dopo la verifica di un caso di
	 * test.
	 * 
	 * @param fsm
	 *            TODO
	 * @param b
	 *            the b
	 */
	abstract public void setCovered(boolean b, TestConditionFSM fsm);

	/**
	 * In base al valore di <code>b</code> decide la transizione verso un
	 * nuovo stato. Questa transizione avviene dopo la selezione del test
	 * condition a causa dell'utente.
	 * 
	 * @param fsm
	 *            TODO
	 * @param b
	 *            the b
	 */
	abstract public void setSelected(boolean b, TestConditionFSM fsm);

	/**
	 * la TC diventa running.
	 * 
	 * @param fsm
	 *            TODO
	 */
	abstract public void setRunning(TestConditionFSM fsm);

	/**
	 * il test è trovato infeasible.
	 * 
	 * @param fsm
	 *            the fsm
	 */
	abstract public void setInfeasible(TestConditionFSM fsm);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return description;
	}

}
