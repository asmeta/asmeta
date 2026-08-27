package tgtlib.util.combinatorial;

import java.math.BigInteger;

/**
 */
public class Utils {

	// ------------------
	// Compute factorial
	// ------------------

	/**
	 * Method getFactorial.
	 * @param n int
	 * @return BigInteger
	 */
	static BigInteger getFactorial(int n) {
		BigInteger fact = BigInteger.ONE;
		for (int i = n; i > 1; i--) {
			fact = fact.multiply(new BigInteger(Integer.toString(i)));
		}
		return fact;
	}

	
}
