package mcdc.scrtgtool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import tgtlib.definitions.TestPredicate;
import tgtlib.util.Pair;

/**
 * represents the list of test predicates for masking MCDC.
 * Every pair represents a pair of tp, the first makes the expression true, the second, false
 * 
 * @author garganti
 *
 */
public class MCDCTPList<Q extends TestPredicate> implements Iterable<Pair<Q, Q>>{

	public static final MCDCTPList EMPTY_LIST = new MCDCTPList<TestPredicate>(Collections.EMPTY_LIST);

	private List<Pair<Q, Q>> content;
	
	private MCDCTPList(List<Pair<Q, Q>> initialList){
		content = initialList;
	}

	public MCDCTPList(){
		this(new ArrayList<Pair<Q, Q>>());
	}


	@Override
	public Iterator<Pair<Q, Q>> iterator() {
		return content.iterator();
	}

/**
 * 
 * @param trueVal: make the expression true
 * @param falseVal: make the expression false
 */
	public void add(Q trueVal, Q falseVal){
		content.add(new Pair<Q, Q>(trueVal, falseVal));
	}
	
	void addAll(MCDCTPList<Q> others){
		content.addAll(others.content);
	}

	/** exanche all the first positions with the second and so on
	 * 
	 * @return
	 */
	MCDCTPList<Q> exchangeTrueFalse() {
		MCDCTPList<Q> result = new MCDCTPList<Q>();
		for(Pair<Q, Q> tc : content){
			result.add(tc.getSecond(), tc.getFirst());
		}
		return result;
	}
}
