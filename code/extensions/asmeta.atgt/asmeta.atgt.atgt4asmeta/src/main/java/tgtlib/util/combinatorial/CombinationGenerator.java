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
package tgtlib.util.combinatorial;

//--------------------------------------
// Systematically generate combinations.
//--------------------------------------
//Combination Generator without ripetitions
// 
//by Michael Gilleland
// http://www.merriampark.com/comb.htm

import java.math.BigInteger;
import java.util.Iterator;

/**
 */
final class CombinationGenerator implements Iterator<int[]>{

	private int[] a;
	private int n;
	private int r;
	private BigInteger numLeft;
	private BigInteger total;

	/**
	 * generates the r-combinations of n numbers, without repetition
	 * 
	 * @param n int
	 * @param r int
	 */
	public CombinationGenerator(int n, int r) {
		if (r > n) {
			throw new IllegalArgumentException();
		}
		if (n < 1) {
			throw new IllegalArgumentException();
		}
		this.n = n;
		this.r = r;
		a = new int[r];
		BigInteger nFact = Utils.getFactorial(n);
		BigInteger rFact = Utils.getFactorial(r);
		BigInteger nminusrFact = Utils.getFactorial(n - r);
		total = nFact.divide(rFact.multiply(nminusrFact));// total = n ! / (r ! * (n -r ) !)
		reset();
	}

	// ------
	// Reset
	// ------

	private void reset() {
		for (int i = 0; i < a.length; i++) {
			a[i] = i;
		}
		numLeft = new BigInteger(total.toString());
	}

	// ------------------------------------------------
	// Return number of combinations not yet generated
	// ------------------------------------------------

	/**
	 * Method getNumLeft.
	 * @return BigInteger
	 */
	public BigInteger getNumLeft() {
		return numLeft;
	}

	// -----------------------------
	// Are there more combinations?
	// -----------------------------
	/**
	 * Method hasNext.
	 * @return boolean
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return numLeft.compareTo(BigInteger.ZERO) == 1;
	}

	// ------------------------------------
	// Return total number of combinations
	// ------------------------------------

	/**
	 * Method getTotal.
	 * @return BigInteger
	 */
	public BigInteger getTotal() {
		return total;
	}


	// --------------------------------------------------------
	// Generate next combination (algorithm from Rosen p. 286)
	// --------------------------------------------------------
	/**
	 * Method next.
	 * @return int[]
	 * @see java.util.Iterator#next()
	 */
	@Override
	public int[] next() {

		if (numLeft.equals(total)) {
			numLeft = numLeft.subtract(BigInteger.ONE);
			return a;
		}

		int i = r - 1;
		while (a[i] == n - r + i) {
			i--;
		}
		a[i] = a[i] + 1;
		for (int j = i + 1; j < r; j++) {
			a[j] = a[i] + j - i;
		}

		numLeft = numLeft.subtract(BigInteger.ONE);
		return a;

	}

	/**
	 * Method remove.
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		throw new RuntimeException("remove not supported");
	}
}
