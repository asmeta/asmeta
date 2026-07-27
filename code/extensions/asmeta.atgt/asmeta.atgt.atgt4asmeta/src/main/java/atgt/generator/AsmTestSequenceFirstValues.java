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
package atgt.generator;

import atgt.coverage.AsmTestCondition;
import atgt.coverage.AsmTestSequence;

// TODO: Auto-generated Javadoc
/**
 * similar to Test Sequence: keep track only of the first identical to
 * AsmTestSequence, except the new state does nothing it is used by SAL when
 * only the first is the correct (still to check) in case on no temporal
 * constraints.
 */
class AsmTestSequenceFirstValues extends AsmTestSequence {

	public AsmTestSequenceFirstValues(AsmTestCondition tc) {
		super(tc);
		content = new FirstValues();
	}
	
	

}
