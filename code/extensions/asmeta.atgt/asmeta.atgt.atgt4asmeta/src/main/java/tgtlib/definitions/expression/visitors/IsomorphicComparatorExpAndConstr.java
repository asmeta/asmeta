package tgtlib.definitions.expression.visitors;

import java.util.Comparator;

import tgtlib.definitions.expression.Expression;

public class IsomorphicComparatorExpAndConstr implements Comparator<Expression[]> {

	@Override
	public int compare(Expression[] arg0, Expression[] arg1) {
		Boolean res = false;
		assert arg0.length == arg1.length;
		if(arg0.length == arg1.length) {
			assert arg0.length > 0;
			ExpressionsComparator comparator = new ExpressionsComparator(arg1[0]);
			res = arg0[0].accept(comparator);
			if(res) {
				for(int i = 1; i < arg0.length; i++) {
					comparator = new ExpressionsComparator(arg1[i], comparator.getMap());
					res = arg0[i].accept(comparator);
					if(!res) {
						break;
					}
				}
			}
		}
		return res?0:1;
	}
}