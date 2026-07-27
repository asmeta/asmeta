package tgtlib.util.combinatorial;
/*
 * PermuterTest.java
 *
 * Created on 3-mrt-2006
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

import java.util.Arrays;

/**
 * A class for testing the combinatoric operators.
 *
 * @author <a href="mailto:hendrik.maryns@uni-tuebingen.de">Hendrik Maryns</a>
 * @version $Revision: 1.0 $
 */
public class PermuterTest {

        /**
         *
         *
         * @param args
         */
        public static void main(String args[]) {
                final Integer[] data = new Integer[3];
                for (int i = 0; i < data.length; i++) {
                        data[i] = i;
                }
                for (Integer[] variation : new Permuter<Integer>(data)) {
                        System.out.println(Arrays.toString(variation));
                }
                System.out.println();
                for (int i = 0; i <= data.length; i++) {
                        for (Integer[] combination : new Combinator<Integer>(data, i)) {
                                System.out.println(Arrays.toString(combination));
                        }
                }
                System.out.println();
                for (int i = 0; i <= data.length; i++) {
                        for (Integer[] variation : new VariatorWithRepetition<Integer>(data,i)) {
                                System.out.println(Arrays.toString(variation));
                        }
                }
        }

}
