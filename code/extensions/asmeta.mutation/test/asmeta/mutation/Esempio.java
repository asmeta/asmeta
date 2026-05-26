package asmeta.mutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Esempio {

	public static void main(String[] args) {
		List<Integer> interiIntegers = Arrays.asList(5, 4, 0);
		Iterator<Integer> x = interiIntegers.iterator();
		Iterator<Iterator<Integer>> ioi = new Iterator<Iterator<Integer>>() {
			@Override
			public boolean hasNext() {
				return x.hasNext();
			}
		
			@Override
			public Iterator<Integer> next() {
				// dato un numero X , trova una lista di numeri da 0 a X
				List<Integer> interi = new ArrayList<>();
				for (int i = 0; i <= x.next(); i++) {
					interi.add(i);
				}
				return interi.iterator();
			}
		};
		IteratorOfIterator<Integer> myit = new IteratorOfIterator<>(ioi);
		while (myit.hasNext()) {
			System.out.print(myit.next() + " ");
		}

	}

}
