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

/**
 * represents possible values of state of  a Test Sequence.
 */
public enum TestSequenceState {

	/**
	 * when assert and then found useless.
	 */

	NORMAL {

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.specification.coverage.tsstatus.TestSequenceState#toString()
		 */
		@Override
		public String toString() {
			return "NORMAL";
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.specification.coverage.tsstatus.TestSequenceState#discardTest(atgt.specification.coverage.tsstatus.TestSeqFSM)
		 */
		@Override
		public void discardTest(TestSeqFSM fsm) {
			fsm.setCurrent(TestSequenceState.TEST_DISCARDED);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.specification.coverage.tsstatus.TestSequenceState#setNormal(atgt.specification.coverage.tsstatus.TestSeqFSM)
		 */
		@Override
		public void setNormal(TestSeqFSM fsm) {

		}
	},
	/**
	 * when assert and then found useless.
	 */

	TEST_DISCARDED {

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.specification.coverage.tsstatus.TestSequenceState#toString()
		 */
		@Override
		public String toString() {
			return "Useless";
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.specification.coverage.tsstatus.TestSequenceState#discardTest(atgt.specification.coverage.tsstatus.TestSeqFSM)
		 */
		@Override
		public void discardTest(TestSeqFSM fsm) {
			// TODO Auto-generated method stub

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see atgt.specification.coverage.tsstatus.TestSequenceState#setNormal(atgt.specification.coverage.tsstatus.TestSeqFSM)
		 */
		@Override
		public void setNormal(TestSeqFSM fsm) {
			fsm.setCurrent(TestSequenceState.NORMAL);
		}

	};

	/**
	 * Sets the normal.
	 * 
	 * @param fsm
	 *            the new normal
	 */
	abstract public void setNormal(TestSeqFSM fsm);

	/**
	 * il test trovato non è necessario pur essendo stato trovato.
	 * 
	 * @param fsm
	 *            TODO
	 */
	abstract public void discardTest(TestSeqFSM fsm);

}
