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

import atgt.specification.ASMSpecification;
import tgtlib.coverage.CoverageBuilder;

/**
 * given a specification returns a tree of test predicates, by analyzing the
 * specification. It can be a CoverageTree or a simple Coverage
 *
 * @author garganti
 */
public interface AsmCoverageBuilder extends CoverageBuilder<ASMSpecification, AsmCoverage>{
	
	
	@Override
	public AsmCoverage getTPTree(ASMSpecification spec);
	

}
