package tgtlib.definitions.normalform.iscas;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import tgtlib.definitions.expression.AndExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.DivExpression;
import tgtlib.definitions.expression.EqualsExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.ExpressionVisitor;
import tgtlib.definitions.expression.FunctionTerm;
import tgtlib.definitions.expression.GreaterEqualExpression;
import tgtlib.definitions.expression.GreaterThanExpression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.ImpliesExpression;
import tgtlib.definitions.expression.LessEqualExpression;
import tgtlib.definitions.expression.LessThanExpression;
import tgtlib.definitions.expression.MinusExpression;
import tgtlib.definitions.expression.ModuloExpression;
import tgtlib.definitions.expression.MultExpression;
import tgtlib.definitions.expression.NegExpression;
import tgtlib.definitions.expression.NextExpression;
import tgtlib.definitions.expression.NotEqualsExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.OrExpression;
import tgtlib.definitions.expression.PlusExpression;
import tgtlib.definitions.expression.PrimedIdExpression;
import tgtlib.definitions.expression.XOrExpression;

/**
 * ISCAS format
 * http://logic.pdmi.ras.ru/~basolver/rtl.html
 * 
 * from an expression, find all the bits
 * 
 *
 */
public class FromExpressionToIscas implements ExpressionVisitor<Bit> {
	
	
	static final Logger logger = Logger.getLogger(FromExpressionToIscas.class);
	
	// use the set to avoid duplicates
	private Set<Bit> bits;
	private Set<Var> vars;
	private Set<NotVar> notVars;
	private Bit output;

	public FromExpressionToIscas() {
		bits = new HashSet<Bit>();
		vars = new HashSet<Var>();
		notVars = new HashSet<NotVar>();
		
		/*bits = new HashMap<Bit, Bit>();
		vars = new HashMap<Var, Var>();
		notVars = new HashMap<NotVar, NotVar>();*/
	}

	/** build the iscas content (use this not the accept)
	 * 
	 * @param e
	 */
	public void getBits(Expression e) {
		output = e.accept(this);
		if(output instanceof Var) {
			assert vars.size() == 1 && bits.size() == 0;
			output = new Buff((Var)output);
			bits.add(output);
			//bits.put(output, output);
		}
	}
	
	@Override
	public Bit forCaseExpression(CaseExpression caseExpression) {
		throw new RuntimeException("not implemented yet");
	}

	
	@Override
	public Bit forAndExpression(AndExpression e) {
		Bit left = e.getFirstOperand().accept(this);
		Bit right = e.getSecondOperand().accept(this);
		return addBit(new And(left, right), bits);
	}

	@Override
	public Bit forOrExpression(OrExpression orExpression) {
		Bit left = orExpression.getFirstOperand().accept(this);
		Bit right = orExpression.getSecondOperand().accept(this);
		return addBit(new Or(left, right), bits);
	}

	@Override
	public Bit forXOrExpression(XOrExpression xOrExpression) {
		Bit left = xOrExpression.getFirstOperand().accept(this);
		logger.debug("left xor" + left.toString());
		Bit right = xOrExpression.getSecondOperand().accept(this);
		logger.debug("right xor" + right.toString());
		return addBit(new Xor(left, right), bits);
	}

	@Override
	public Bit forNotExpression(NotExpression notExpression) {
		Expression exp = notExpression.getOperand();
		Bit b = exp.accept(this);
		if(b instanceof Var) {
			Var var = (Var)b;
			return addBit(new NotVar(var), notVars);
			//return addBit(new NotVar(var), bits);
		} else {
			return addBit(new NotBit(b), bits);
		}
	}

	@Override
	public Bit forGreaterEqualExpression(
			GreaterEqualExpression greaterEqualExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bit forEqualsExpression(EqualsExpression equalsExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bit forIdExpression(IdExpression idExpression) {
		//String id = idExpression.getID().getIdString();
		//return addBit(new Var(id), vars);
		return addBit(new Var(idExpression), vars);
	}

	@Override
	public Bit forDivExpression(DivExpression divExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bit forPlusExpression(PlusExpression plusExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bit forMinusExpression(MinusExpression minusExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bit forGreaterThanExpression(
			GreaterThanExpression greaterThanExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bit forLessEqualExpression(LessEqualExpression lessEqualExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bit forLessThanExpression(LessThanExpression lessThanExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bit forImpliesExpression(ImpliesExpression impliesExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bit forMultExpression(MultExpression multExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bit forNegExpression(NegExpression negExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bit forNextExpression(NextExpression nextExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bit forNotEqualsExpression(NotEqualsExpression notEqualsExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bit forPrimedIdExpression(PrimedIdExpression primedIdExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bit forModuloExpression(ModuloExpression moduloExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	/*void printBits() {
		for(Bit b: bits) {
			System.out.println(b.name);
		}
	}*/

	public String getIscas() {
		StringBuilder sb = new StringBuilder();
		for(Bit var: getVars()) {
			sb.append(var.toIscas() + "\n");
		}
		sb.append(getIscasOutput() + "\n");
		sb.append(getIscasNoInputOutput());
		return sb.toString();
	}

	public String getIscasNoInputOutput() {
		StringBuilder sb = new StringBuilder();
		for(Bit bit: getBits()) {
			sb.append(bit.toIscas() + "\n");
		}
		for(NotVar notVar: getNotVars()) {
			sb.append(notVar.toIscas() + "\n");
		}
		return sb.toString();
	}

	private <T extends Bit> T addBit(T bit, Set<T> bits) {
		// return the element if already in set
		for(T b: bits) {
			if(b.equals(bit)) {
				logger.debug(bit.name + " already in bits, returning that");
				return b;
			}
		}
		// otherwise add to the set
		logger.debug(bit.name + " added as bit");
		bits.add(bit);
		return bit;
	}

	public String getIscasOutput() {
		return "OUTPUT(" + output.nameForIscas + ")";
	}

	public Set<Bit> getBits() {
		return bits;
		//return bits.keySet();
	}

	public Set<Var> getVars() {
		return vars;
		//return vars.keySet();
	}

	public Set<NotVar> getNotVars() {
		return notVars;
		//return notVars.keySet();
	}

	public Bit getOutput() {
		return output;
	}

	@Override
	public Bit forFunctionTerm(FunctionTerm ft) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bit forConditionalExpression(CondExpression cond) {
		// TODO Auto-generated method stub
		throw new RuntimeException("not implemented yet");
	}
}