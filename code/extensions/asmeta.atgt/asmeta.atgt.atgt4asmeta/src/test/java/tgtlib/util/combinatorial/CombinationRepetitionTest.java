/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package tgtlib.util.combinatorial;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 */
public class CombinationRepetitionTest {

	@Test
	public void testCombinationRepetition() {
	        // n  =  5  and we want combinations of 3  
	        String[] str = {"A", "B", "C", "D", "E"};
	 
	        CombinationRepetition<String> cb = new CombinationRepetition<String>(String.class,str, 3);
	 
	        ArrayList<String[]> alist = new ArrayList<String[]>();
	        alist = cb.getAlist();
	 
	        for (String[] val : alist) {
	            System.out.println(Arrays.toString(val));
	        }
	         
	        System.out.println("number of element created " +alist.size());
	    }

}
