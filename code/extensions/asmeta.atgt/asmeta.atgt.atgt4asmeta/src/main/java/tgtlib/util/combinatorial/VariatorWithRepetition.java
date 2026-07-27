/*
 * VariatorWithRepetition.java
 *
 * Created on 7-mrt-2006
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package tgtlib.util.combinatorial;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * A class that sequentially returns all variations with repetition of a  certain
 * number out of an array of given elements.
 *
 * @author <a href="mailto:hendrik.maryns@uni-tuebingen.de">Hendrik Maryns</a>

 * @version $Revision: 1.0 $
 */
public class VariatorWithRepetition<T> extends CombinatoricOperator<T> {

        /**
         * Initialise a new variator, with given elements and size of the arrays
         * to be returned.
         *
         * @param elements
         *              The elements of which variations have to be computed.
         * @param r
         *              The size of the variations to compute.
         * @pre         r should not be smaller than 0.
         *     | 0 <= r
         * @post        The total number of iterations is set to the number of elements
         *                      to the rth power.
         *     | new.getTotal() == BigInteger.valueOf(elements.length).pow(r)
         * @post  The number of variations left is set to the total number.
         *     | new.getNumLeft() == new.getTotal()
         */
        public VariatorWithRepetition(T[] elements, int r) {
                super(elements, r);
        }

        /**
         * Initialise the array of indices.  For variations with repetition, it
         * needs to be initialised with all 0s
         */
        @Override
        protected void initialiseIndices() {
                Arrays.fill(indices, 0);
        }

        /**
         * Compute the total number of elements to return.
         *
        
         * @param n int
         * @param r int
         * @return BigInteger
         * @see CombinatoricOperator#initialiseTotal() */
        @Override
        protected BigInteger initialiseTotal(int n, int r) {
                return BigInteger.valueOf(n).pow(r);
        }

        /**
         * Compute the next array of indices.
         *
         * @see CombinatoricOperator#computeNext()
         */
        @Override
        protected void computeNext() {
                int i = indices.length - 1;
                int n = elements.length;
                while (++indices[i] == n && i > 0) {
                        indices[i--] = 0;
                }
        }

}

