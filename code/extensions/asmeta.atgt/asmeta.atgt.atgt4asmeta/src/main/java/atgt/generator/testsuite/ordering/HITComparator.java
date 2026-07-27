package atgt.generator.testsuite.ordering;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import atgt.coverage.AsmTestSequence;
import atgt.specification.ASMSpecification;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.Variable;

/**
 * comparatore for TestCondition: the first one (the minimum) will be that
 * choosen
 * 
 * @author garganti
 * 
 */
 abstract class HITComparator extends NWiseTcComparator<ASMSpecification,AsmTestSequence> {

	
	/**
	 * Hits: counts how many times every assignment of a test sequence is present in the suite 
	 * it uses the name of the variable
	 */
	Hashtable<String, Hashtable<String, Integer>> hits;
	/** The range. */
	Hashtable<String, Integer> range;

	HITComparator() {};
	
	@Override
	void init(){
		
		hits = new Hashtable<String, Hashtable<String, Integer>>();
		range = new Hashtable<String, Integer>();

		// take al the vars to be considered
		for (Variable v : vars) {
			Hashtable<String, Integer> h = new Hashtable<String, Integer>();
			for (EnumConst name : ((ElementsType) v.getType()).allElements())
				h.put(name.toString(), 0);
			hits.put(v.getName(), h);
			range.put(v.getName(), v.getType().range());	
		}
		PreferNovelty.log.debug("HITS  initialized to: " + hits.toString());
		PreferNovelty.log.debug("RANGES initialized to: " + range.toString());
	}

	/* (non-Javadoc)
	 * @see atgt.generator.testsuite.ordering.NWiseTcComparator#compare(atgt.coverage.TestCondition, atgt.coverage.TestCondition)
	 */
	
	/**
	 * Evaluate: restituisce il grado di non novit�: quelle novel vengono valutate pi� basse
	 * 
	 * @param t
	 *            the t
	 * 
	 * @return the int
	 */
	

	/* (non-Javadoc)
	 * @see atgt.generator.testsuite.ordering.NWiseTcComparator#usage(java.lang.String, java.lang.String)
	 */
	public int usage(String var, String value) {
		// computed as the gap from
		// minimum used value for this
		// variable
		Enumeration<String> values = hits.get(var).keys();
		// find minimum usage
		String v = values.nextElement();
		int min = hits.get(var).get(v).intValue();
		for (; values.hasMoreElements();) {
			v = values.nextElement();
			int n = hits.get(var).get(v).intValue();
			if (n <= min)
				min = n;
		}
		PreferNovelty.log.debug("accessing var:" + var + " value:" + value);
		return hits.get(var).get(value).intValue() - min; ///range.get(var).
		// intValue();

	}

	/* (non-Javadoc)
	 * @see atgt.generator.testsuite.ordering.NWiseTcComparator#update(atgt.coverage.AsmTestSequence)
	 */
	@Override
	public void update(AsmTestSequence tseq) {
		// potrebbe contenere anche pi� di una istruzione (nel caso di
		// temporal constraints
		for (Map<atgt.specification.location.Location, String> instruction : tseq.allInstructions()) {
			PreferNovelty.log.debug("HITS: updating for test sequence: "+ instruction.toString());
			for (Entry<atgt.specification.location.Location, String> p : instruction.entrySet()) {
				int n = hits.get(p.getKey().getName()).get(p.getValue()).intValue();
				hits.get(p.getKey().getName()).put(p.getValue(), new Integer(n + 1));
			}
		}
		PreferNovelty.log.debug("HITS now: " + hits.toString());
	}

	/* (non-Javadoc)
	 * @see atgt.generator.testsuite.ordering.NWiseTcComparator#update(java.lang.String, java.lang.String)
	 */
	@Override
	public void update(String var, String value) {
		int n = hits.get(var).get(value).intValue();
		hits.get(var).put(value, new Integer(n + 1));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/* (non-Javadoc)
	 * @see atgt.generator.testsuite.ordering.NWiseTcComparator#toString()
	 */
	@Override
	public String toString() {
		return hits.toString();
	}

}