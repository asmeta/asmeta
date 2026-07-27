/*
 * Combinator.java
 *
 * Created on 3-mrt-2006, based on CombinationGenerator from Michael  Gilleland.
 *
 * Copyright (C) 2006, 2010 Hendrik Maryns <hendrik@sfs.uni-tuebingen.de>.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package tgtlib.util.combinatorial;

import java.math.BigInteger;

/**
 * A class that sequentially returns all combinations of a certain  number out of
 * an array of given elements.  Thanks to Michael Gillegand for the base
 * implementation: {@link http://www.merriampark.com/comb.htm}
 *
 * @author <a href="mailto:hendrik.maryns@uni-tuebingen.de">Hendrik Maryns</a>

 * @version $Revision: 1.0 $
 */
public class Combinator<T> extends CombinatoricOperator<T> {

        /**
         * Initialise a new Combinator, with given elements and size of the arrays
         * to be returned.
         *
         * @param elements
         *       The elements of which combinations have to be computed.
         * @param r
         *       The size of the combinations to compute.
         * @pre    r should not be greater than the length of the elements, and
         *       not smaller than 0.
         *     | 0 <= r <= elements.length
         * @post        The total number of iterations is set to the factorial of the
         *                      number of elements divided by the factorials of the size of the
         *                      combinations and the number of elements minus the size of the
         *                      combinations.  That is, with the number of elements = n and the
         *                      size of the combinations = r:
         *                      n                n!
         *                       (     )  =  ---------
         *                      r         (n-r)!r!
         *     | new.getTotal() == factorial(elements.length).divide(
         *     |  factorial(r).multiply(factorial(elements.length-r))
         * @post  The number of combinations left is set to the total number.
         *     | new.getNumLeft() == new.getTotal()
         */
        public Combinator(T[] elements, int r) {
                super(elements, r);
                assert r <= elements.length;
        }

        /**
         * Compute the total number of elements to return.
         *
        
        
         * @param n int
         * @param r int
         * @return  The factorial of the number of elements divided by the
         *       factorials of the size of the combinations and the number of
         *       elements minus the size of the combinations.
         *       That is, with the number of elements = n and the size of the
         *       combinations = r:
         *          n            n!
         *       (     )  =  ---------
         *          r         (n-r)!r! * @see CombinatoricOperator#initialiseTotal(int, int) */
        @Override
        protected BigInteger initialiseTotal(int n, int r) {
                BigInteger nFact = factorial(n);
                BigInteger rFact = factorial(r);
                BigInteger nminusrFact = factorial(n - r);
                return nFact.divide(rFact.multiply(nminusrFact));
        }

        /**
         * Compute the next array of indices.
         *
         * @see CombinatoricOperator#computeNext()
         */
        @Override
        protected void computeNext() {
                int r = indices.length;
                int i = r - 1;
                int n = elements.length;
                while (indices[i] == n - r + i) {
                        i--;
                }
                indices[i] = indices[i] + 1;
                for (int j = i + 1; j < r; j++) {
                        indices[j] = indices[i] + j - i;
                }
                // TODO: understand this.
        }

}

