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
package tgtlib.generator.ordering;

import java.util.List;

import tgtlib.definitions.TestPredicate;


/** take the tp in the same order as they are generate * @author garganti
 * @version $Revision: 1.0 $
d*/
public class GenerationOrder<T extends TestPredicate<?,?>> extends NoCollectOrderWMonitoring<T> {


	/**
	 * Constructor for GenerationOrder.
	 * @param candidates List<T>
	 */
	public GenerationOrder(List<T> candidates) {
		super(candidates);
	}

	
	@Override
	protected void setUpIterator() {
		// take the usual iterator
		candidatesIter = candidates.iterator();		
	}


}
