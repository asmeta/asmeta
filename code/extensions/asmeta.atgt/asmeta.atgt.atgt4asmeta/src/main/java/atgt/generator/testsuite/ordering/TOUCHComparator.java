package atgt.generator.testsuite.ordering;

import extgt.coverage.combinatorial.EqTestCondition;

/**
 * comparator for TestCondition: the first one (the minimum) will be that
 * chosen
 * 
 * @author garganti
 * 
 */
public class TOUCHComparator extends HITComparator {

	
	public TOUCHComparator(){
		super(); 
	}
	
	@Override
	int evaluate(EqTestCondition  t) {
		// several policies are possible
		//return max(t); //don't forget to reverse the order.
		//return min(t);
		return numVarValTouched(t);
		//return deviation(t);
		// return (int) (u1+u2)/2f);// average
		// return (int) usage(t.var1.getName(),
		// t.val1)+usage(t.var2.getName(),
		// t.val2);// sum
		// return u1*u1 + u2 *u2; // square distance:
		// max frequency of an assignment
		// min //
		// standard deviation
	}

	
	/** number of variables and values already touched by the test suite 
	 * questo permette di dare la precedenza ai tp in cui un assegnamento
	 * non sia mai stato testato */
	int numVarValTouched(EqTestCondition  t){
		int res = 0;
		for (int i = 0; i < t.size(); i++) {
			String var = t.getVar(i).getName();
			String val = t.getVal(i).toString();
			if (hits.get(var).get(val).intValue() > 0)
				res++;
		}
		return res;
	}

	

}