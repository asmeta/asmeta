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
package atgt.project;

/** builds the tree for the coverage tree
 * */

import javax.swing.tree.DefaultMutableTreeNode;

import atgt.coverage.AsmCoverageTree;
import atgt.coverage.AsmTestCondition;
import atgt.coverage.Coverage;
import atgt.coverage.CoveragesVisitorI;
import atgt.coverage.VisitableTPTreeNode;

// TODO: Auto-generated Javadoc
/**
 * The Class CoverageTreeModelVisitor.
 */
public class CoverageTreeModelVisitor implements
		CoveragesVisitorI<DefaultMutableTreeNode> {

	/*
	 * (non-Javadoc)
	 *
	 * @see atgt.coverage.CoveragesVisitorI#forTestCondition(atgt.coverage.TestCondition)
	 */
	@Override
	public DefaultMutableTreeNode forTestCondition(AsmTestCondition tc) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(tc);
		// node.add(new DefaultMutableTreeNode(tc.getCondition()));
		return node;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see atgt.coverage.TPTreeNodeVisitor#forCoverage(atgt.coverage.Coverage)
	 */
	@Override
	public DefaultMutableTreeNode forCoverage(Coverage c) {
		DefaultMutableTreeNode coverage = new DefaultMutableTreeNode(c);
		for (AsmTestCondition tc : c.allTPs()) {
			coverage.add(tc.accept(this));
		}
		return coverage;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see atgt.coverage.TPTreeNodeVisitor#forCoverageTree(atgt.coverage.CoverageTree)
	 */
	@Override
	public DefaultMutableTreeNode forCoverageTree(AsmCoverageTree c) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(c);
		for (VisitableTPTreeNode e : c.allCoverages()) {
			node.add(e.accept(this));
		}
		return node;
	}

	public void setSearchCommonCoverage(boolean searchCommonCoverage) {
	}

}
