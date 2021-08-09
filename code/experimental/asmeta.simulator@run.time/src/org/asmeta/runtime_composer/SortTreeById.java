package org.asmeta.runtime_composer;

import java.util.Comparator;

public class SortTreeById implements Comparator<CompositionTreeNode>{
	@Override
	public int compare(CompositionTreeNode t1, CompositionTreeNode t2) {
		return t1.getID() - t2.getID();
	}
}
