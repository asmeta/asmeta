package atgt.generator.testsuite.ordering;

import tgtlib.specification.Specification;

/** factory for NWiseTcComparatorFactory */
public class NWiseTcComparatorFactory {
	
	static NWiseTcComparator NWTCC = null;  
	
	/** get the NWiseTcComparator
	 * 
	 * @param spec
	 * @return
	 */
	static <S extends Specification> NWiseTcComparator getInstance(S spec){ 
		if (NWTCC == null) NWTCC = new TOUCHComparator();		
		NWTCC.setSpecification(spec);
		return NWTCC;
	}
	/** set the compartor to be used
	 * 
	 * @param nwtcc2
	 */
	public static void setComparator(NWiseTcComparator nwtcc2) {
		NWTCC = nwtcc2;
	}

}
