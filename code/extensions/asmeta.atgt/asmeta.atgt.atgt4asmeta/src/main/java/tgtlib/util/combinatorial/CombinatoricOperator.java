/*
 * CombinatoricOperator.java
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package tgtlib.util.combinatorial;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Iterator;

/**
 * A common superclass for all combinatoric operators.  Makes use of the
 * template method pattern to define all others.
 *
 * @author <a href="mailto:hendrik.maryns@uni-tuebingen.de">Hendrik  Maryns</a>
 * @version $Revision: 1.0 $
 */
public abstract class CombinatoricOperator<T> implements Iterator<T[]>,
                Iterable<T[]> {

        /**
         * Initialise a new operator, with given elements and size of the arrays
         * to be returned.
         *
         * @param elements
         *       The elements on which this combinatoric operator has to act.
         * @param r
         *       The size of the arrays to compute.
         * @pre    r should not be smaller than 0.
         *     | 0 <= r
         * @post   The total number of iterations is set to the correct number.
         *     | new.getTotal() == initialiseTotal();
         * @post  The number of variations left is set to the total number.
         *     | new.getNumLeft() == new.getTotal()
         */
        protected CombinatoricOperator(T[] elements, int r) {
                assert 0 <= r;
                indices = new int[r];
                this.elements = elements.clone();
                total = initialiseTotal(elements.length, r);
                reset();
        }

        /**
         * The elements the operator works upon.
         */
        protected T[] elements;

        /**
         * An integer array backing up the original one to keep track of the
         * indices.
         */
        protected int[] indices;

        /**
         * Initialise the array of indices.  By default, it is initialised with
         * incrementing integers.
         */
        protected void initialiseIndices() {
                for (int i = 0; i < indices.length; i++) {
                        indices[i] = i;
                }
        }

        /**
         * The variations still to go.
         */
        private BigInteger numLeft;

        /**
         * The total number of variations to be computed.
         */
        private BigInteger total;

        /**
         * Compute the total number of elements to return.
         *
         * @param n
         *                      The number of elements the operator works on.
         * @param r
         *                      The size of the arrays to return.
        
         * @return      The total number of elements is always bigger than 0.
         *              | result >= 0 */
        protected abstract BigInteger initialiseTotal(int n, int r);

        /**
         * Reset the iteration.
         *
         * @post        The number of iterations to go is the same as the total number
         *                      of iterations.
         *              | new.getNumLeft() == getTotal()
         */
        public void reset() {
                initialiseIndices();
                numLeft = total;
        }

        /**
         * Return number of variations not yet generated.
         * @return BigInteger
         */
        public BigInteger getNumLeft() {
                return numLeft;
        }

        /**
         * Return the total number of variations.
         *
        
         * @return  The factorial of the number of elements divided by the
         *       factorials of the size of the variations and the number of
         *       elements minus the size of the variations.
         *       That is, with the number of elements = n and the size of the
         *       variations = r: n^r */
        public BigInteger getTotal() {
                return total;
        }

        /**
         * Returns <tt>true</tt> if the iteration has more elements.  This is the
         * case if not all n! permutations have been covered.
         *
        
        
         * @return  The number of permutations to go is bigger than zero.
         *     | result == getNumLeft().compareTo(BigInteger.ZERO) > 0; * @see java.util.Iterator#hasNext() */
        @Override
		public boolean hasNext() {
                return numLeft.compareTo(ZERO) == 1;
        }

        /**
         * Compute the next combination.
         *
         * @see java.util.Iterator#next()
         */
        @Override
		public T[] next() {
                if (!numLeft.equals(total)) {
                        computeNext();
                }
                numLeft = numLeft.subtract(ONE);
                return getResult(indices);
        }

        /**
         * Compute the next array of indices.
         */
        protected abstract void computeNext();

        /**
         * Compute the result, based on the given array of indices.
         *
         * @param indexes
         *       An array of indices into the element array.
        
         * @return  An array consisting of the elements at positions of the given
         *       array.
         *     | result[i] == elements[indexes[i]] */
        @SuppressWarnings("unchecked")
        private T[] getResult(int[] indexes) {
                T[] result = (T[]) Array.newInstance(elements.getClass()
                                .getComponentType(), indexes.length);
                for (int i = 0; i < result.length; i++) {
                        result[i] = elements[indexes[i]];
                }
                return result;
        }

        /**
         * Not supported.
         *
         * @see java.util.Iterator#remove()
         */
        @Override
		public void remove() {
                throw new UnsupportedOperationException();
        }

        /**
         * A combinatoric operator is itself an iterator.
         *
        
        
         * @return  Itself.
         *     | result == this * @see java.lang.Iterable#iterator() */
        @Override
		public Iterator<T[]> iterator() {
                return this;
        }

        /**
         * Compute the factorial of n.
         * @param n int
         * @return BigInteger
         */
        public static BigInteger factorial(int n) {
                BigInteger fact = ONE;
                for (int i = n; i > 1; i--) {
                        fact = fact.multiply(BigInteger.valueOf(i));
                }
                return fact;
        }
}

