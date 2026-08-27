/*
 * Permuter.java
 *
 * Created on 3-mar-2006, based on Permutations from Tim Tyler.
 *
 * Copyright (C) 2005, 2010 Hendrik Maryns <hendrik@sfs.uni-tuebingen.de>.
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
 * A class that permutes a given array of elements.  It is an iterator that
 * returns all permutations, successively.  Thanks to Tim Tyler for the
 * original implementation {@link http://mandala.co.uk/permutations/}.
 *
 * @author <a href="mailto:hendrik.maryns@uni-tuebingen.de">Hendrik  Maryns</a>

 * @version $Revision: 1.0 $
 */
public class Permuter<T> extends CombinatoricOperator<T> {

        /**
         * Initialise a new permuter, with given array of elements to permute.
         *
         * @param elements
         *       The elements to permute.
         * @post  The total number is set to the factorial of the number of
         *       elements.
         *     | new.getTotal() == factorial(elements.length)
         * @post  The number of permutations left is set to the total number.
         *     | new.getNumLeft() == new.getTotal()
         */
        public Permuter(T[] elements) {
                super(elements, elements.length);
        }

        /**
         * Compute the total number of elements to return.
         *
        
        
         * @param n int
         * @param r int
         * @return      The factorial of the number of elements.
         *              | result == factorial(n) * @see CombinatoricOperator#initialiseTotal(int, int) */
        @Override
        protected BigInteger initialiseTotal(int n, int r) {
                return factorial(n);
        }

        /**
         * Compute the next array of indices.
         *
         * @see CombinatoricOperator#computeNext()
         */
        @Override
        protected void computeNext() {
                // find the rightmost element that is smaller than the element at its right
                int i = indices.length - 1;
                while (indices[i - 1] >= indices[i])
                        i = i - 1;
                // find the rightmost element that is bigger then the other one
                int j = indices.length;
                while (indices[j - 1] <= indices[i - 1])
                        j = j - 1;
                // swap them (always is i < j)
                swap(i - 1, j - 1);
                // now the elements at the right of i
                // are in descending order, so  reverse them all
                i++;
                j = indices.length;
                while (i < j) {
                        swap(i - 1, j - 1);
                        i++;
                        j--;
                }
                // TODO: try other algorithms,
                // see http://www.cut-the-knot.org/Curriculum/Combinatorics/JohnsonTrotter.shtml
        }

        /**
         * Swap the elements at positions a and b, both from the index array and
         * from the element array.
         *
        
         * @post        The elements at indices a and b of the array of indices are
         *                      swapped.
         *     | new.indexes[a] = indexes[b]
         *     | new.indexes[b] = indexes[a]
         * @param a int
         * @param b int
         */
        private void swap(int a, int b) {
                int temp = indices[a];
                indices[a] = indices[b];
                indices[b] = temp;
        }

}

