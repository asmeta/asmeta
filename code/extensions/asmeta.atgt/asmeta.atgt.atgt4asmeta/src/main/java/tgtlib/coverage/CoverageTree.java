/*******************************************************************************
 * Copyright (c) 2010 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Angelo Gargantini - initial API and implementation
 *******************************************************************************/
package tgtlib.coverage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tgtlib.definitions.NamedTerm;
import tgtlib.definitions.TestPredicate;

/**
 * represents a tree of TP. the root of the tree or an intermediate node.
 * 
 * @author garganti
 * 
 */
public abstract class CoverageTree<Q extends NamedTerm> implements
		TestPredicateTreeNode<Q> {

	// TODO
	// make this list only either of coverage or tps, not allow to mix things
	// protected List<? extends TestPredicateTreeNode<Q>> subTP;
	// to now we are more tolerant in case of faulty tps is necessary
	
	protected List<TestPredicateTreeNode<Q>> subTP;
	
	protected static boolean beTolerant = false; 
	
	// now done dynamically
	protected enum ContentTypes {EMPTY , COVERAGE_TREE, TP; }
	
	protected ContentTypes contentType; 
	
	/* Synthetic description (name) of the coverage*/
	protected String name;

	protected CoverageTree(String name) {
		subTP = new ArrayList<TestPredicateTreeNode<Q>>();
		contentType = ContentTypes.EMPTY;
		this.name = name;
	}
	
	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	@Override
	public final String getName() {
		return this.name;
	}
	
	/**
	 * Aggiunge una lista di casi di test alla lista dei test condition.
	 *
	 * @param tcs
	 *            the tcs
	 */
	public void addTestConditions(List<? extends Q> tcs) {
		for(Q tc: tcs){
			addNode(tc);
		}
	}


	/** add a node and classify this as internal node or final node (with tps)
	 *  
	 * @param newNode
	 * @return
	 */
	public boolean addNode(TestPredicateTreeNode<Q> newNode) {
		assert newNode != this;
		// check if it is correct
		assert (beTolerant || contentType ==  ContentTypes.EMPTY || 
				(contentType ==  ContentTypes.COVERAGE_TREE && (newNode instanceof CoverageTree))||
				(contentType ==  ContentTypes.TP && (newNode instanceof TPInCoverage))): "content type " +contentType + " inserting " + newNode.getClass() + " name : " + newNode.getName();
		boolean result = subTP.add(newNode);
		if (contentType ==  ContentTypes.EMPTY){
			if (newNode instanceof CoverageTree) contentType =  ContentTypes.COVERAGE_TREE;
			else {
				assert (newNode instanceof TPInCoverage);
				contentType =  ContentTypes.TP;
			}
		}
		return result;
	}

	/**
	 * add a new leaf
	 * 
	 * @param newTP
	 * @return
	 */
	public boolean addNode(Q newTP) {
		return addNode(new TPInCoverage<Q>(newTP));
	}

	/**
	 * returns the list of the TP in the tree
	 */
	public Iterable<Q> allTPs() {

		return new Iterable<Q>() {

			@Override
			public Iterator<Q> iterator() {
				return new Iterator<Q>() {
					Iterator<TestPredicateTreeNode<Q>> i = preOrderEnumeration();

					Q nextElement = findNext();

					@Override
					public boolean hasNext() {
						return (nextElement != null);
					}

					@Override
					public Q next() {
						Q result = nextElement;
						assert result != null;
						nextElement = findNext();
						return result;
					}

					@Override
					public void remove() {
						i.remove();
					}

					/**
					 * null if no Q
					 * 
					 * @return
					 */
					private Q findNext() {
						while (i.hasNext()) {
							TestPredicateTreeNode<Q> n = i.next();
							if (n instanceof TPInCoverage) {
								return ((TPInCoverage<Q>) n).testPredicate;
							}
						}
						return null;
					}
				};
			};
		};
	}

	/**
	 * this part is about visiting the tree !!! return an Iterator for the
	 * purpose of visiting the tree in depth first (in pre order) root -> left
	 * -> right
	 * 
	 * @return
	 */
	public Iterator<TestPredicateTreeNode<Q>> preOrderEnumeration() {

		// An anonymous Iterator class. The Iterator will iterate over the
		// elements in this Tree at first. Then it will work its way through all
		// the
		// subtrees.
		return new Iterator<TestPredicateTreeNode<Q>>() {

			// current element (last returned)
			// -1 => none returned yet, 0, ... is a child, if any
			private int currentElement = -1;

			// if current >=0, this keeps track of which element is considering
			// if null, the currentElement is a leaf
			private Iterator<TestPredicateTreeNode<Q>> currentIterator = null;

			/**
			 * next element
			 * 
			 * @return
			 */
			@Override
			public boolean hasNext() {
				// if the next element is this one:
				if (currentElement == -1)
					return true;
				// if the current element does not actually exist
				// for example 0, and size =0
				// or n and size = n , i.e. an array [0...n-1]
				if (currentElement >= subTP.size())
					return false;
				// otherwise:
				// if the last element is the last element in the array
				// then check that element
				if (currentElement == (subTP.size() - 1)) {
					return (currentIterator == null ? true : currentIterator
							.hasNext());
				}
				// otherwise:
				// the current element is intermediate ( 0<= current< size -1 ,
				// and size > 1)
				return true;
			}

			/**
			 * assume that hasMoreElement is true !!! the caller first check
			 * with hasMoreElement, then can call nextElement;
			 * 
			 * @return TestPredicateTreeNode<T>
			 */
			@Override
			public TestPredicateTreeNode<Q> next() {
				TestPredicateTreeNode<Q> result;
				// setup the next element
				// if the current is this
				if (currentElement == -1) {
					// return itself
					result = CoverageTree.this;
				} else {
					assert currentElement < subTP.size();
					// 
					if (currentIterator != null) {
						result = currentIterator.next();
					} else {
						assert currentIterator == null;
						result = subTP.get(currentElement);
					}
				}
				setupNext();
				return result;
			}

			/**
			 * point to the next element
			 */
			private void setupNext() {
				// point to the first child (if any)
				if (currentIterator != null && currentIterator.hasNext()) {
					// do nothing;
				} else {
					assert currentIterator == null
							|| !currentIterator.hasNext();
					// change element: the last one is finished
					currentElement++;
					if (currentElement < subTP.size()) {
						assert (currentElement < subTP.size());
						TestPredicateTreeNode<Q> currentChild = subTP
								.get(currentElement);
						if (currentChild instanceof CoverageTree<?>) {
							// go in depth
							currentIterator = ((CoverageTree<Q>) currentChild)
									.preOrderEnumeration();
						} else {
							assert currentChild instanceof TPInCoverage<?>;
							currentIterator = null;
						}
					} else {
						// do nothing, current element outside
					}

				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("remove not supported");
			}
		};
	}

	/**
	 * number of test predicates (in depth)
	 * 
	 * @return
	 */
	public int getNumberofTPs() {
		if (subTP.isEmpty()) return 0;
		if (contentType == ContentTypes.TP){
			// at least the first element is a TP
			assert subTP.get(0) instanceof TPInCoverage;
			return subTP.size();			
		}
		assert (contentType == ContentTypes.COVERAGE_TREE): " contentType is " + contentType;
		int result = 0;
		for (TestPredicateTreeNode<Q> tgtn : subTP) {
			if (tgtn instanceof TPInCoverage) result += 1;
			else result += ((CoverageTree<Q>) tgtn).getNumberofTPs();
		}
		return result;
	}

	/**
	 * finds a test condition in the coverage
	 * 
	 * @param tc
	 * @return
	 */
	public boolean contains(Q tc) {
		for (Q tci : this.allTPs()) {
			if (tci.equals(tc))
				return true;
		}
		return false;
	}

	/**
	 * child a position i
	 * 
	 * @param i
	 *            position
	 * @return child at position i
	 */
	public TestPredicateTreeNode<Q> getChildAt(final int i) {
		return subTP.get(i);
	}

	/**
	 * the number of children (without a depth visit)
	 * 
	 * @return the number of sub TPs
	 */
	public int getChildCount() {
		return subTP.size();
	}

	
	/**
	 * returns a string for the complete tree
	 * 
	 * @return
	 */
	public String toStringCompleteTree() {
		return toStringCompleteTree(0).toString();
	}

	/** returns a string from complete tree */
	private StringBuffer toStringCompleteTree(int depth) {
		StringBuffer result = new StringBuffer("---------------------------".substring(0, depth)
				+ "> ");
		result.append(this.name);
		// result += " - " + this.getStatus().getLabel() + " ";
		for (TestPredicateTreeNode<Q> tgtn : subTP) {
			result.append('\n');
			if (tgtn instanceof TPInCoverage) {
				Q tp = ((TPInCoverage<Q>) tgtn).testPredicate;
				result.append("(" + tp.getName() + ")");
				//used for SCR because every TP has an unique ID
				result.append("[" + ((TestPredicate)tp).getUniqueID() + "]");
				result.append(": " + tp.getCondition().toString() + "  ");
			} else {
				result.append(((CoverageTree<Q>) tgtn).toStringCompleteTree(depth + 1));
			}
		}
		return result;
	}

}
