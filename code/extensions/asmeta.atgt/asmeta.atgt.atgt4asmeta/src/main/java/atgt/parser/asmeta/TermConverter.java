package atgt.parser.asmeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.NaturalDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.ConstantTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.CharTerm;
import asmeta.terms.furtherterms.ComplexTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.ExistTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.RealTerm;
import asmeta.terms.furtherterms.StringTerm;
import asmeta.terms.furtherterms.impl.ConditionalTermImpl;
import asmeta.terms.furtherterms.impl.FurthertermsFactoryImpl;
import atgt.specification.ASMSpecification;
import atgt.specification.expression.AsmetaOperator;
import atgt.specification.location.Constant;
import atgt.specification.location.DerivedFunction;
import tgtlib.definitions.expression.BinaryExpression;
import tgtlib.definitions.expression.CaseExpression;
import tgtlib.definitions.expression.CondExpression;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.NegExpression;
import tgtlib.definitions.expression.NotExpression;
import tgtlib.definitions.expression.Operator;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.type.EnumerableType;
import tgtlib.definitions.expression.type.IntegerType;
import tgtlib.definitions.expression.type.Type;

/**
 * converts term from ASMETA TO ATGT
 * 
 * @author garganti
 * 
 */
class TermConverter {

	/** The logger : use the same ad the AsmetLoader */
	private final static Logger logger = Logger.getLogger(AsmetaLLoader.class);

	private ASMSpecification SP;

	EnumConstCreator ecc;

	TermConverter(ASMSpecification SP, EnumConstCreator ecc) {
		this.ecc = ecc;
		this.SP = SP;
	}

	/**
	 * from ConstantTerm to Expression.
	 * 
	 * @param ct the ct
	 * 
	 * @return the expression
	 */
	Expression computeConstantTerm(asmeta.terms.basicterms.ConstantTerm ct) {
		Domain d = ct.getDomain();
		Type t = AsmetaLLoader.getDomain(d, SP, ecc);
		assert t != null;
		if (d instanceof IntegerDomain) {
			if (ct.getSymbol().startsWith("+"))
				return ecc.createIdExpression(ct.getSymbol().substring(1), t);
		} else if (d instanceof NaturalDomain) {
			if (ct.getSymbol().endsWith("n"))
				return ecc.createIdExpression(ct.getSymbol().substring(0, ct.getSymbol().length()), t);
		} else if (d instanceof EnumTd) {
			return ecc.createEnumConst(ct.getSymbol(), t);
		}
		return ecc.createIdExpression(ct.getSymbol(), t);
	}

	/**
	 * translates a FunctionTerm in a Expression
	 */
	Expression computeFunctionTerm(asmeta.terms.basicterms.FunctionTerm ft) {
		Expression e1 = null;
		asmeta.definitions.Function fun = ft.getFunction();
		TupleTerm args = ft.getArguments();
		String funName = fun.getName();
		if (AsmetaOperator.isOperator(funName)) {
			logger.debug("translating operator " + funName);
			if (args != null) {
				if (args.getArity() == 2) {
					// ? un operatore binario
					Expression e[] = translateTupleTerm2(args, funName);
					assert e.length == 2;
					Operator op = AsmetaOperator.parseOperator(funName);
					assert op != null;
					logger.debug("is a binary operator: " + AsmetaOperator.getAsmetaLString(op));
					e1 = BinaryExpression.mkBinExpr(e[0], op, e[1]);
				} else {
					logger.debug("is a unuary operator");
					// ? un operatore unario
					if (funName.equals(AsmetaOperator.getAsmetaLString(Operator.NOT))) {
						Expression e = translateTerm(args.getTerms().get(0));
						e1 = NotExpression.createNotExpression(e);
					} else if (funName.equals(AsmetaOperator.getAsmetaLString(Operator.OPPOSITE))) {
						Expression e = translateTerm(args.getTerms().get(0));
						e1 = new NegExpression(e);
					} else if (args instanceof VariableTerm) {
						VariableTerm vt = (VariableTerm) args;
						e1 = ecc.createIdExpression(vt.getName(), null);
					} else if (args instanceof EnumTerm) {
						throw new RuntimeException("Enum Term ???");
					} else if (isConstantID(args)) {
						e1 = ecc.createIdExpression(((ConstantTerm) args).getSymbol(), null);
					} else if ((args instanceof FunctionTerm) || (args instanceof LocationTerm)) {
						e1 = computeFunctionTerm((FunctionTerm) args);
						assert e1 != null;
					} else {
						throw new RuntimeException("traslation to ???");
					}
				}
			}
		} else {
			logger.debug("translating function " + funName + " in function term " + ft);
			// funName is not an operator
			// it must be a function !!
			if (args != null) {
				Type domain;
				Type codomain;
				// normal function (monitored)
				atgt.specification.location.Function f = SP.getFunction(funName);
				if (f!=null) {
					domain = f.getDomain();
					codomain = f.getCodomain();
				} else {
					// can be a derived function
					DerivedFunction df = SP.getDerivedFunction(funName);
					if (df != null)
						throw new RuntimeException("derived function " + funName + " with args not supported");
					// can be a static function
					Constant staticFun = SP.getConstantByName(funName);
					if (staticFun == null) {
						throw new RuntimeException("Function " + funName + " is not declared");
					}
					throw new RuntimeException("static function " + funName + " not supported");
				}			
				// build now the terms
				if (args instanceof TupleTerm) {
					String funname = fun.getName();
					IdExpression name = ecc.createIdExpression(funname, domain);
					List<Expression> argsExpr = translateTerms(args.getTerms());
					return new tgtlib.definitions.expression.FunctionTerm(name, codomain, argsExpr);
				} else {
					throw new RuntimeException("what kind of term???" + args.getClass());
				}
			} else {
				// a variable
				atgt.specification.location.Variable f = SP.getVariable(funName);
				if (f != null) {
					return f.getIdExpression();
				}
				// a derived variable
				DerivedFunction f2 = SP.getDerivedFunction(funName);
				if (f2 != null)
					return f2.getIdExpression();
				// a constant
				Constant f3 = SP.getConstantByName(funName);
				if (f3 != null)
					return f3.getIdExpression();
				throw new RuntimeException("variable " + funName + " is not declared");
			}
		}return e1;

	}

	/**
	 * given a term returns the translation to a an Expression.
	 * 
	 * @param term the term
	 * @return the expression
	 */
	Expression translateTerm(Term term) {
		if (term instanceof FunctionTerm) {
			Expression computeFunctionTerm = computeFunctionTerm((FunctionTerm) term);
			assert computeFunctionTerm != null : ((FunctionTerm) term).getFunction().getName();
			return computeFunctionTerm;
		} else if ((term instanceof VariableTerm)) {// ||
			// (terms[0] instanceof LogicalVariableTerm) ||
			// (terms[0] instanceof LocationVariableTerm))
			// e1 = new IdExpression("[" + ((VariableTerm)terms[0]).getName());
			VariableTerm vt = (VariableTerm) term;
			String typeName = vt.getDomain().getName();
			// get also the type of the variable
			Type type = (typeName.equals("Boolean") ? BoolType.BOOLTYPE : SP.getTypeFor(typeName));
			assert type != null : vt.toString() + " has no type with domain " + typeName;
			return ecc.createIdExpression(((VariableTerm) term).getName(), type);
		} else if (term instanceof EnumTerm) {
			EnumConst result = ecc.createEnumConst(((EnumTerm) term).getSymbol(), SP.getTypeFor(((EnumTerm) term).getDomain().getName()));
			assert result.getType() != null;
			return result;
		} else if (term instanceof CaseTerm) {
			CaseTerm caseTerm = (CaseTerm) term;
			Term comparedTerm = caseTerm.getComparedTerm();
			IdExpression comparedTermAsExp = (IdExpression) translateTerm(comparedTerm);
			CaseExpression result = new CaseExpression(comparedTermAsExp);
			Iterator<Term> resultTerms = caseTerm.getResultTerms().iterator();
			for (Term t : caseTerm.getComparingTerm()) {
				IdExpression comparingTerm = (IdExpression) translateTerm(t);
				assert resultTerms.hasNext();
				Expression resultTerm = translateTerm(resultTerms.next());
				result.addCase(comparingTerm, resultTerm);
			}
			Term otherwise = caseTerm.getOtherwiseTerm();
			if (otherwise != null)
				result.setDefault(translateTerm(otherwise));
			return result;
		} else if (term instanceof ForallTerm) {
			ForallTerm forallTerm = (ForallTerm) term;
			List<VariableTerm> vars = forallTerm.getVariable();
			List<Term> ranges = forallTerm.getRanges();
			Term guard = forallTerm.getGuard();
			Expression result = combine(vars, ranges, guard, Operator.AND);
			return result;
		} else if (term instanceof ExistTerm) {
			ExistTerm existTerm = (ExistTerm) term;
			List<VariableTerm> vars = existTerm.getVariable();
			List<Term> ranges = existTerm.getRanges();
			Term guard = existTerm.getGuard();
			Expression result = combine(vars, ranges, guard, Operator.OR);
			return result;
		} else if (isConstantID(term)) {
			ConstantTerm ct = (ConstantTerm) term;
			Constant r = SP.getConstantByName(ct.getSymbol());
			if (r != null) {
				return r.getIdExpression();
			}
			Type value = AsmetaLLoader.getDomain(ct.getDomain(), SP, ecc);
			assert value != null : term.getDomain().getName();
			return ecc.createIdExpression(ct.getSymbol(), value);
		} else if (term instanceof ConditionalTerm) {
			ConditionalTerm cond = (ConditionalTerm) term;
			Expression condN = translateTerm(cond.getGuard());
			Expression thenN = translateTerm(cond.getThenTerm());
			Expression elseN = translateTerm(cond.getElseTerm());
			return new CondExpression(condN, thenN, elseN);
		} else
			throw new RuntimeException("TERM OF WHAT " + term.getClass());
	}

	// transform exists or forall in a OR/AND term
	private Expression combine(List<VariableTerm> vars, List<Term> ranges, Term guard, Operator op) {
		assert vars.size() == 1;
		assert ranges.size() == 1;
		PowersetDomain domain = (PowersetDomain) ranges.get(0).getDomain();
		assert domain != null;
		Domain domain2 = domain.getBaseDomain();
		EnumerableType type = (EnumerableType) SP.getTypeFor(domain2.getName());
		assert type != null : " type " + domain2.getName() + " not found";
		Expression result = null;
		FunctionTerm body = (FunctionTerm) guard;
		for (Object o : type.allElements()) {
			Expression t = null;
			if (o instanceof Expression) {
				t = (Expression) o;
			} else {
				assert o instanceof Integer;
				t = ecc.createIdExpression(((Integer) o).toString(), IntegerType.INTEGER_TYPE);
			}
			/*
			 * String name = body.getFunction().getName(); Function f =
			 * SP.getFunction(name); assert f != null : "function " + name + " not found";
			 * // build t IdExpression idExpression = f.getIdExpression();
			 * tgtlib.definitions.expression.FunctionTerm ft = new
			 * tgtlib.definitions.expression.FunctionTerm(idExpression, f.getDomain(),
			 * Collections.singletonList(t));
			 */
			Expression ft = translateTerm(body);
			if (result == null) {
				result = ft;
			} else {
				result = BinaryExpression.mkBinExpr(result, op, ft);
			}
		}
		return result;
	}

	/**
	 * Compute term (only constants and numbers - to be included in translateTerm
	 * 
	 * @param term the term
	 * 
	 * @return the expression
	 */
	Expression computeTerm(asmeta.terms.basicterms.Term term) {
		// constant
		if (term instanceof asmeta.terms.basicterms.ConstantTerm)
			return computeConstantTerm((asmeta.terms.basicterms.ConstantTerm) term);
		// Natural term
		if (term instanceof asmeta.terms.furtherterms.NaturalTerm) {
			return ecc.createIdExpression(((NaturalTerm) term).getSymbol(), null);
		}
		// location term
		if (term instanceof asmeta.terms.basicterms.LocationTerm) {
			LocationTerm lt = (LocationTerm) term;
			return translateTerm(lt);
		}
		// TODO: Fix Case term
		//if (term instanceof asmeta.terms.furtherterms.CaseTerm) {
		//	// Convert the case term into a conditional term
		//	return translateTerm((CaseTerm) term);
		//}
		logger.error("computeTerm(asmeta.terms.basicterms.Term) - term of class " + term.getClass() //$NON-NLS-1$
				+ " non translated to EXPR: " + term);
		return null;
	}

	/*
	 * potrebbe non servire , basta utilizzare bene compute FunctionTerm translate a
	 * tuple with two expressions
	 */
	/**
	 * Translate tuple term2.
	 * 
	 * @param pt the pt
	 * @param op the op
	 * 
	 * @return the expression[]
	 */
	private Expression[] translateTupleTerm2(asmeta.terms.basicterms.TupleTerm pt, String op) {
		Expression[] e = new Expression[2];
		EList<Term> terms = pt.getTerms();
		e[0] = translateTerm(terms.get(0));
		assert e[0] != null : terms.get(0);
		e[1] = translateTerm(terms.get(1));
		assert e[1] != null : terms.get(1);
		return e;
	}

	/**
	 * 
	 * @param term list
	 * @return the translation of terms
	 */
	<T extends Expression> List<T> translateTerms(EList<? extends Term> terms) {
		List<T> result = new ArrayList<T>(terms.size());
		for (Term t : terms) {
			// t could be a variable
			atgt.specification.location.Variable f = null;
			if (t instanceof EnumTerm)
				f = SP.getVariable(((EnumTerm) t).getSymbol());
			// if it is not a variable, then translate the term, otherwise create the
			// corresponding variable
			if (f == null) {
				T translateTerm = (T) translateTerm(t);
				// if id, must have type
				assert !(translateTerm instanceof IdExpression) || (((IdExpression) translateTerm).getType() != null)
						: t.toString();
				result.add(translateTerm);
			} else {
				result.add((T) f.getIdExpression());
			}
		}
		return result;
	}

	private boolean isConstantID(Term ter) {
		return (ter instanceof ConstantTerm) || (ter instanceof BooleanTerm) || (ter instanceof CharTerm)
				|| (ter instanceof IntegerTerm) || (ter instanceof NaturalTerm) || (ter instanceof RealTerm)
				|| (ter instanceof ComplexTerm) || (ter instanceof UndefTerm) || (ter instanceof StringTerm);
	}
}
