package atgt.generator.testsuite.ordering;

import extgt.coverage.combinatorial.EqTestCondition;

/**
 * comparatore for TestCondition: the first one (the minimum) will be that
 * choosen
 * 
 * @author garganti
 * 
 */
public class DEVComparator extends HITComparator {

	
	public DEVComparator(){
		super(); 
	}
	
	@Override
	int evaluate(EqTestCondition t) {
		// several policies are possible
		//return max(t); //don't forget to reverse the order.
		//return min(t);
		//return numVarValTouched(t);
		return deviation(t);
		// return (int) (u1+u2)/2f);// average
		// return (int) usage(t.var1.getName(),
		// t.val1)+usage(t.var2.getName(),
		// t.val2);// sum
		// return u1*u1 + u2 *u2; // square distance:
		// max frequency of an assignment
		// min //
		// standard deviation
	}

	int deviation(EqTestCondition t){
		int x[]= new int[t.size()];
		double average=0, deviation=0;
		for (int i = 0; i < t.size(); i++) 
			x[i] = hits.get(t.getVar(i).getName()).get(t.getVal(i).toString()).intValue();		
		for(double v : x) average += v; 
		average = average/t.size();
		for(float v : x) deviation += Math.pow(v-average,2); 
		deviation = Math.sqrt(deviation/t.size());
	
		return Math.round((float)deviation);
	}


}