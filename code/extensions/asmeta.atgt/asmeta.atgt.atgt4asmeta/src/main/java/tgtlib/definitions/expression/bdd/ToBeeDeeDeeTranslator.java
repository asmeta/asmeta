//package tgtlib.definitions.expression.bdd;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import tgtlib.definitions.expression.AndExpression;
//import tgtlib.definitions.expression.CaseExpression;
//import tgtlib.definitions.expression.CondExpression;
//import tgtlib.definitions.expression.DivExpression;
//import tgtlib.definitions.expression.EqualsExpression;
//import tgtlib.definitions.expression.Expression;
//import tgtlib.definitions.expression.ExpressionVisitor;
//import tgtlib.definitions.expression.FunctionTerm;
//import tgtlib.definitions.expression.GreaterEqualExpression;
//import tgtlib.definitions.expression.GreaterThanExpression;
//import tgtlib.definitions.expression.IdExpression;
//import tgtlib.definitions.expression.ImpliesExpression;
//import tgtlib.definitions.expression.IsLogicExpression;
//import tgtlib.definitions.expression.LessEqualExpression;
//import tgtlib.definitions.expression.LessThanExpression;
//import tgtlib.definitions.expression.MinusExpression;
//import tgtlib.definitions.expression.ModuloExpression;
//import tgtlib.definitions.expression.MultExpression;
//import tgtlib.definitions.expression.NegExpression;
//import tgtlib.definitions.expression.NextExpression;
//import tgtlib.definitions.expression.NotEqualsExpression;
//import tgtlib.definitions.expression.NotExpression;
//import tgtlib.definitions.expression.OrExpression;
//import tgtlib.definitions.expression.PlusExpression;
//import tgtlib.definitions.expression.PrimedIdExpression;
//import tgtlib.definitions.expression.VisitNotSupportedExc;
//import tgtlib.definitions.expression.XOrExpression;
//import tgtlib.definitions.expression.type.BoolType;
//import tgtlib.definitions.expression.visitors.IDExprCollector;
//
//import com.juliasoft.beedeedee.bdd.BDD;
//import com.juliasoft.beedeedee.factories.*;
//import com.juliasoft.beedeedee.ger.GERFactory;
//
//import net.sf.javabdd.BDDFactory;
//
//public class ToBeeDeeDeeTranslator implements ExpressionVisitor<BDD> {
//	private Map<IdExpression,BDD> map = new HashMap<IdExpression, BDD>();
//	private GERFactory factory;
//    private List<IdExpression> savedIds = new ArrayList<IdExpression>();
//	 public BDD translateToBDD(Expression e) {
//		return e.accept(this);
//	}
//	 public  Factory getFactor(){
//	    	return factory;
//	    }
//	public ToBeeDeeDeeTranslator(Collection<IdExpression> ids) {
//		factory = new GERFactory(1000000, 100000);
//        this.savedIds.addAll(ids);
//		assert (allBooleanIn(ids));
//	}
//
//	private boolean allBooleanIn(Collection<IdExpression> ids2) {
//		for (IdExpression e : ids2) {
//			if (e.getType() != BoolType.BOOLTYPE)
//				return false;
//		}
//		return true;
//	}
//
//	@Override
//	public BDD forIdExpression(IdExpression idExpression) {
//		if (idExpression == BoolType.TRUE_CONST) {
//			return factory.makeOne();
//		}
//		if (idExpression == BoolType.FALSE_CONST) {
//			return factory.makeZero();
//		}
//		if (!map.containsKey(idExpression)) {
//			map.put(idExpression,factory.makeVar(savedIds.indexOf(idExpression)));
//		}
//		
//		return map.get(idExpression);
//	}
//
//	@Override
//	public BDD forPrimedIdExpression(PrimedIdExpression primedIdExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public BDD forNextExpression(NextExpression nextExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public BDD forFunctionTerm(FunctionTerm ft) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public BDD forAndExpression(AndExpression andExpression) {
//		BDD b1 = andExpression.getFirstOperand().accept(this);
//		BDD b2 = andExpression.getSecondOperand().accept(this);
//		return b1.andWith(b2);
//	}
//
//	@Override
//	public BDD forOrExpression(OrExpression orExpression) {
//		BDD b1 = orExpression.getFirstOperand().accept(this);
//		BDD b2 = orExpression.getSecondOperand().accept(this);
//		// b1.free();
//		// b2.free();
//		return b1.orWith(b2);
//	}
//
//	@Override
//	public BDD forXOrExpression(XOrExpression xOrExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public BDD forNotExpression(NotExpression notExpression) {
//		BDD b1 = notExpression.getOperand().accept(this);
//		//return b1.notWith();
//		return b1.not();
//	}
//
//	@Override
//	public BDD forImpliesExpression(ImpliesExpression impliesExpression) {
//		BDD b1 = impliesExpression.getFirstOperand().accept(this);
//		BDD b2 = impliesExpression.getSecondOperand().accept(this);
//		 
//		// b1.free();
//		// b2.free();
//		return b1.impWith(b2);
//	}
//
//	@Override
//	public BDD forGreaterEqualExpression(GreaterEqualExpression greaterEqualExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public BDD forEqualsExpression(EqualsExpression equalsExpression) {
//		Expression e1 = equalsExpression.getFirstOperand();
//		Expression e2 = equalsExpression.getSecondOperand();
//		assert e1.accept(IsLogicExpression.isLogic);
//		assert e2.accept(IsLogicExpression.isLogic);
//		BDD b1 = e1.accept(this);
//		BDD b2 = e2.accept(this);
//		
//		return b1.biimpWith(b2);
//		
//		
//	}
//
//	@Override
//	public BDD forGreaterThanExpression(GreaterThanExpression greaterThanExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public BDD forLessEqualExpression(LessEqualExpression lessEqualExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public BDD forLessThanExpression(LessThanExpression lessThanExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public BDD forNotEqualsExpression(NotEqualsExpression notEqualsExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public BDD forDivExpression(DivExpression divExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public BDD forPlusExpression(PlusExpression plusExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public BDD forMinusExpression(MinusExpression minusExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public BDD forMultExpression(MultExpression multExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public BDD forNegExpression(NegExpression negExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public BDD forModuloExpression(ModuloExpression moduloExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public BDD forCaseExpression(CaseExpression caseExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//	@Override
//	public BDD forConditionalExpression(CondExpression cond) {
//		// TODO Auto-generated method stub
//		throw new RuntimeException("not implemented yet");
//	}
//
//}
