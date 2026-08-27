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
package atgt.coverage;

/**
 * Builds a HashTable containing all the test predicates contained in a
 * CoverageTree or in a Coverage key of the hashtable is the unique ID of the
 * test condition.
 */
public class ToTpIndex implements TPTreeNodeVisitor<TPIndex> {

	/** The INSTANCE. */
	public static ToTpIndex INSTANCE = new ToTpIndex();

	/**
	 * Instantiates a new to tp index.
	 */
	private ToTpIndex() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see atgt.coverage.TPTreeNodeVisitor#forCoverageTree(atgt.coverage.CoverageTree)
	 */
	@Override
	public TPIndex forCoverageTree(AsmCoverageTree c) {
		TPIndex table = new TPIndex();

		for (VisitableTPTreeNode e : c.allCoverages()) {
			table.putAll(e.accept(this));
		}
		return table;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see atgt.coverage.TPTreeNodeVisitor#forCoverage(atgt.coverage.Coverage)
	 */
	@Override
	public TPIndex forCoverage(Coverage c) {
		TPIndex table = new TPIndex();
		for (AsmTestCondition tc : c.allTPs()) {
			table.put(tc.getUniqueID(), tc);
		}
		return table;
	}

}
