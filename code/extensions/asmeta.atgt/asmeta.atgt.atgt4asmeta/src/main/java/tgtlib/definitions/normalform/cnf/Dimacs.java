package tgtlib.definitions.normalform.cnf;

import java.util.ArrayList;
import java.util.List;

import tgtlib.definitions.expression.IdExpression;
import tgtlib.util.IterableIterator;

/** dimacs format used by SAT solvers: 
 * "Each of the next lines specifies a clause: a positive literal is denoted by the corresponding number, 
 * and a negative literal is denoted by the corresponding negative number. 
 * The last number in a line should be zero. For example,
<pre>
c A sample .cnf file.
p cnf 3 2
1 -3 0
2 3 -1 0 
</pre>
 * 
 * @author garganti
 *
 */
public class Dimacs {
	private int nVariables;
	private int nClauses;
	
	/** terms as integers**/
	protected List<int[]> terms;
	
	/** the ids (note that there is an offset by 1)
	 * that is the IdExpression with index "i" in the list
	 * has, as integer identifier, "i + 1".*/
	private List<IdExpression> ids;
	
	/**
	 * Instantiates a new dimacs.
	 *
	 * @param ids2 the literals
	 * @param nClauses the number of clauses clauses
	 */
	protected Dimacs(List<IdExpression> ids2, int nClauses) {		
		this.nVariables = ids2.size();
		this.nClauses = nClauses;
		terms = new ArrayList<int[]>(nClauses);
		ids = ids2;
	}

	public void addClause(List<Integer> clause) {
		// there is still room from a clause
		assert terms.size() < nClauses;
		// no bigger than the number of variables
		// NOT TRUE: one clause can contain -7 and 7 
		// assert clause.size() <= nVariables: "clause " + clause + " var size "+nVariables;
		// zero is not a valid literal
		assert ! clause.contains(0);
		// transform to array and add 
		terms.add(toIntArray(clause));		
	}
	
	/**
	 * @return the nVariables
	 */
	public int getnVariables() {
		return nVariables;
	}

	/**
	 * @return the list of ids (ther ein an offet by 1
	 */
	public List<IdExpression> getIDs(){
		return ids;
	}

	/**
	 * @return the nClauses
	 */
	public int getnClauses() {
		return nClauses;
	}
	
	/*** Note that the  Apache Commons Lang. It has a handy ArrayUtils class that can do what you want. 
	 * Use the toPrimitive method with the overload for an array of Integers.
	 * @param list
	 * @return
	 */
	
	private int[] toIntArray(List<Integer> list)  {
	    int[] ret = new int[list.size()];
	    int i = 0;
	    for (Integer e : list) {
	        ret[i++] = e.intValue();
	    }
	    return ret;
	}

	/**
	 * Gets the clauses.
	 *
	 * @return the clauses
	 */
	public Iterable<int[]> getClauses() {
		return new IterableIterator<int[]>(terms.iterator());
	}

	public String getHeader() {
		return "p cnf " + getnVariables() + " "
				+ getnClauses() + "\n";
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getHeader());
		for(int[] clause: terms) {
			sb.append(toClauseOfInt(clause) + "\n");
		}
		return sb.toString();
	}

	protected static String toClauseOfInt(int[] c) {
		StringBuffer res = new StringBuffer();
		for (int var : c) {
			res.append(var).append(' ');
		}
		res.append('0');
		return res.toString();
	}
}
