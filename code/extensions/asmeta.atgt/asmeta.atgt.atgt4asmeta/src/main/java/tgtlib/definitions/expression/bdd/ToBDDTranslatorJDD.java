//package tgtlib.definitions.expression.bdd;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import jdd.bdd.BDD;
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
//import tgtlib.util.Pair;
//
//public class ToBDDTranslatorJDD implements ExpressionVisitor<Integer> {
//	// private static final String BDD_LIBRARY = "buddy";
//	// using JDD (native java)
//
//	private BDD bdd;
//	private List<IdExpression> ids = new ArrayList<>();
//	private Map<IdExpression, Integer> varMap = new HashMap<>();
//
//	public  Pair<BDD, Integer> translateToBDD(Expression e) {
//		
//		int a = e.accept(this);
//		return new Pair<BDD, Integer>(this.bdd, a);
//	}
//
//	
//
//	public ToBDDTranslatorJDD(Collection<IdExpression> ids) {
//		// check they are all boolean
//		assert (allBooleanIn(ids));
//		this.bdd = new BDD(1000, 100);
//		this.ids.addAll(ids);
//		
//		
//	}
//    
////	public void addFormulas(Collection<IdExpression> idsAdded,Expression e){
////		this.ids.addAll(idsAdded);
////		for (IdExpression id : idsAdded) {
////			// System.out.println(id);
////			int a = bdd.createVar();
////			varMap.put(id, a);
////		}
//		
//		
////	}
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
//	public Integer forIdExpression(IdExpression idExpression) {
//		if (idExpression == BoolType.TRUE_CONST)
//			return bdd.getOne();
//		if (idExpression == BoolType.FALSE_CONST)
//			return bdd.getZero();
//		Integer existing = varMap.get(idExpression);
//		System.out.println(existing);
//		if(existing == null){
//			int var = bdd.createVar();
//			varMap.put(idExpression, var);
//			return var;
//		}
//		return existing ;
//	}
//
//	@Override
//	public Integer forPrimedIdExpression(PrimedIdExpression primedIdExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public Integer forNextExpression(NextExpression nextExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public Integer forFunctionTerm(FunctionTerm ft) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public Integer forAndExpression(AndExpression andExpression) {
//		Integer b1 = andExpression.getFirstOperand().accept(this);
//		Integer b2 = andExpression.getSecondOperand().accept(this);
//		int a = bdd.ref(bdd.and(b1, b2));
//		bdd.deref(b1);
//		bdd.deref(b2);
//		return a;
//	}
//
//	@Override
//	public Integer forOrExpression(OrExpression orExpression) {
//		Integer b1 = orExpression.getFirstOperand().accept(this);
//		Integer b2 = orExpression.getSecondOperand().accept(this);
//		int a = bdd.ref(bdd.or(b1, b2));
//		bdd.deref(b1);
//		bdd.deref(b2);
//		return a;
//	}
//
//	@Override
//	public Integer forXOrExpression(XOrExpression xOrExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public Integer forNotExpression(NotExpression notExpression) {
//		Integer b1 = notExpression.getOperand().accept(this);
//		int a = bdd.ref(bdd.not(b1));
//		bdd.deref(b1);
//		return a;
//	}
//
//	@Override
//	public Integer forImpliesExpression(ImpliesExpression impliesExpression) {
//		Integer b1 = impliesExpression.getFirstOperand().accept(this);
//		Integer b2 = impliesExpression.getSecondOperand().accept(this);
//		int a = bdd.ref(bdd.imp(b1, b2));
//		bdd.deref(b1);
//		bdd.deref(b2);
//		return a;
//	}
//
//	@Override
//	public Integer forGreaterEqualExpression(GreaterEqualExpression greaterEqualExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public Integer forEqualsExpression(EqualsExpression equalsExpression) {
//		Expression e1 = equalsExpression.getFirstOperand();
//		Expression e2 = equalsExpression.getSecondOperand();
//		assert e1.accept(IsLogicExpression.isLogic);
//		assert e2.accept(IsLogicExpression.isLogic);
//		Integer b1 = e1.accept(this);
//		Integer b2 = e2.accept(this);
//		int a = bdd.ref(bdd.biimp(b1, b2));
//		bdd.deref(b1);
//		bdd.deref(b2);
//		return a;
//	}
//
//	@Override
//	public Integer forGreaterThanExpression(GreaterThanExpression greaterThanExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public Integer forLessEqualExpression(LessEqualExpression lessEqualExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public Integer forLessThanExpression(LessThanExpression lessThanExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public Integer forNotEqualsExpression(NotEqualsExpression notEqualsExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public Integer forDivExpression(DivExpression divExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public Integer forPlusExpression(PlusExpression plusExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public Integer forMinusExpression(MinusExpression minusExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public Integer forMultExpression(MultExpression multExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public Integer forNegExpression(NegExpression negExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public Integer forModuloExpression(ModuloExpression moduloExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//	@Override
//	public Integer forCaseExpression(CaseExpression caseExpression) {
//		// TODO Auto-generated method stub
//		throw new VisitNotSupportedExc("");
//	}
//
//
//
//	@Override
//	public Integer forConditionalExpression(CondExpression cond) {
//		// TODO Auto-generated method stub
//		throw new RuntimeException("not implemented yet");
//	}
//}
