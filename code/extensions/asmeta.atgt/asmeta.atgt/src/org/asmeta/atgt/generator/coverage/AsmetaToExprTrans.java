package org.asmeta.atgt.generator.coverage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import asmeta.definitions.domains.Domain;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.LetTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.RealTerm;
import atgt.parser.asmeta.AsmetaLLoader;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.NumericLiteral;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.type.Type;

/**
 * 
 * returns the
 *
 */
public class AsmetaToExprTrans extends org.asmeta.parser.util.ReflectiveVisitor<Expression> {

	static EnumConstCreator icc = new EnumConstCreator();

	static Map<Domain, Type> types = new HashMap<>();

	/**
	 * Visita un termine.
	 * 
	 * @param term the term
	 * 
	 * @return the string
	 */
	public Expression visit(Term term) {
		// System.out.println("visit term "+term);
		return visit((Object) term);
	}

	/**
	 * Visita una VariableTerm.
	 * 
	 * @param variable the variable
	 * 
	 * @return the string
	 */
	public Expression visit(VariableTerm variable) {
		// convert to type AsmetaLLoader.
		Type t = getType(variable.getDomain());
		return icc.createIdExpression(variable.getName(), t);
	}

	private Type getType(Domain domain) {
		Type t = types.get(domain);
		if (t == null) {
			t = AsmetaLLoader.convertDomainToType(domain, icc);
			types.put(domain,t);
		}
		return t;
	}

	/**
	 * Visita un FunctionTerm.
	 * 
	 * @param funcTerm il FunctionTerm
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	public Expression visit(FunctionTerm funcTerm) throws Exception {		
		List<Expression> args = funcTerm.getArguments().getTerms().stream().map( x -> this.visit(x)).collect(Collectors.toList());
		// dominio o codominio?? 
		Type t = getType(funcTerm.getDomain());
		IdExpression fid = icc.createIdExpression(funcTerm.getFunction().getName(), t);
		return  new tgtlib.definitions.expression.FunctionTerm(fid, t, args);
	}

	/**
	 * Visit.
	 * 
	 * @param tupleTerm the tuple term
	 * 
	 * @return the string
	 */
	public Expression visit(TupleTerm tupleTerm) {
		throw new RuntimeException("not implemented yet");
//
//		StringBuilder s = new StringBuilder();
//		Iterator<Term> iterator = tupleTerm.getTerms().iterator();
//		s.append(visit(iterator.next()));
//		while(iterator.hasNext()) {
//			s.append("_" + visit(iterator.next()));
//		}
//		return s.toString();
	}

	public Expression visit(LetTerm letTerm) {
		throw new RuntimeException("not implemented yet");
//		if(letTerm.getDomain() instanceof BooleanDomain) {
//			Term term = letTerm.getBody();
//			List<VariableTerm> vars = letTerm.getVariable();
//			ArrayList<String[]> values = new ArrayList<String[]>();
//			mv.rv.combineValues(vars, 0, values, new String[vars.size()]);
//			ArrayList<String> ands = new ArrayList<String>();
//			for(String[] value: values) {
//				env.setVarsValues(vars, value);
//				Iterator<Term> terms = letTerm.getAssignmentTerm().iterator();
//				ArrayList<String> conds = new ArrayList<String>();
//				for(String v: value) {
//					conds.add(Util.equals(v, visit(terms.next())));
//				}
//				ands.add(Util.implies(Util.and(conds), visit(term)));
//			}
//			return Util.and(ands);
//		}
//		else {
//			throw new AsmNotSupportedException("Only boolean let terms are supported.");
//		}
	}

	/**
	 * function + arguments
	 */
	public Expression visit(LocationTerm location) {
		if (location.getArguments() == null) {
			// convert to type AsmetaLLoader.
			return icc.createIdExpression(location.getFunction().getName(), getType(location.getDomain()));
		}
		throw new RuntimeException("not implemented yet");
//		Function func = location.getFunction();
//		if(func.getName().equals("self")) {
//			return env.self;
//		}
//		/*if(Defs.isMonitored(func)){
//			return env.monValues.get(createName(func, location.getArguments()));
//		}*/
//		String locationName = locationName(location);
//		//if(mv.agents.contains(locationName.toUpperCase()))
//		//	return locationName.toUpperCase();
//
//		if(env.inSeqRule) {
//			return env.srv.getLocationValue(locationName);
//		}
//
//		if(AsmetaSMVOptions.simplify) {
//			if(AsmetaSMVOptions.simplifyDerived) {
//				//PA 2013/08/14
//				if(env.inLineFunctions.containsKey(locationName)) {
//					return env.inLineFunctions.get(locationName);
//				}
//			}
//		}
//		return locationName;
	}

	/**
	 * Visit a natural term.
	 * 
	 * @param number the number
	 * 
	 * @return the string
	 */
	public Expression visit(NaturalTerm number) {
		String symbol = number.getSymbol();
		assert symbol.charAt(symbol.length() - 1) == 'n';
		// In AsmetaL, natural numbers terminate with an "n"
		// To have the integer representation, we need to remove the "n".
		return new NumericLiteral(Integer.parseInt(symbol.substring(0, symbol.length() - 1)));
	}

	/**
	 * Visit.
	 * 
	 * @param number the number
	 * 
	 * @return the string
	 */
	public Expression visit(IntegerTerm number) {
		return new NumericLiteral(Integer.parseInt(number.getSymbol()));
	}

	/**
	 * Visit.
	 * 
	 * @param number the number
	 * 
	 * @return the string
	 */
	public Expression visit(RealTerm number) {
		throw new RuntimeException("not implemented yet");
		// return number.getSymbol();
		// Silvia: caso in cui il tipo e' Real
	}

	/**
	 * Visit an enum term.
	 * 
	 * @param term the term
	 * 
	 * @return the string
	 */
	public Expression visit(EnumTerm term) {
		return icc.createEnumConst(term.getSymbol());
	}

	/**
	 * Visit a boolean term. In NuSMV the symbolic constants are uppercase.
	 * 
	 * @param bool the bool
	 * 
	 * @return the string
	 */
	public Expression visit(BooleanTerm bool) {
		String s = bool.getSymbol();		
		if (s.equalsIgnoreCase("true"))
			return BoolType.TRUE_CONST;
		if (s.equalsIgnoreCase("false"))
			return BoolType.FALSE_CONST;
		throw new RuntimeException("not implemented yet");		
	}

	/**
	 * Visit.
	 * 
	 * @param undef the undef
	 * 
	 * @return the string
	 */
	public Expression visit(UndefTerm undef) {
		throw new RuntimeException("not implemented yet");
	}

	/**
	 * Visit.
	 * 
	 * @param term the term
	 * 
	 * @return the string
	 */
	public Expression visit(DomainTerm term) {
		throw new RuntimeException("not implemented yet");
	}

//	/**
//	 * Visits an existTerm.
//	 * 
//	 * @param existTerm the exist term
//	 * 
//	 * @return the string
//	 * 
//	 * @throws Exception the exception
//	 */
//	public Expression visit(ExistTerm existTerm) throws Exception {
//		throw new RuntimeException("not implemented yet");
//	}
//
//	/**
//	 * Visit an ExistUniqueTerm.
//	 * 
//	 * @param exitUniqueTerm the exit unique term
//	 * 
//	 * @return the string
//	 * 
//	 * @throws Exception the exception
//	 */
//	public Expression visit(ExistUniqueTerm exitUniqueTerm) throws Exception {
//		return xor(getGuardWithAllValues(exitUniqueTerm.getVariable(),
//											exitUniqueTerm.getRanges(),
//											exitUniqueTerm.getGuard()));
//	}
//
//	/**
//	 * Visits a forall term
//	 * 
//	 * @param forallTerm the forall term
//	 * 
//	 * @return the string
//	 * 
//	 * @throws Exception the exception
//	 */
//	public String visit(ForallTerm forallTerm) throws Exception {
//		/*List<VariableTerm> vars = forallTerm.getVariable();
//		List<Domain> domains = new ArrayList<Domain>(); 
//		Term guard = forallTerm.getGuard();
//		ArrayList<String[]> values = new ArrayList<String[]>();
//		Stack<String> conds = new Stack<String>();
//		for(Term t: forallTerm.getRanges()) {
//			domains.add(t.getDomain());
//		}
//		mv.rv.combineValues(vars, domains, 0, values, new String[vars.size()]);
//		for(String[] value: values) {
//			env.setVarValues(vars, value);
//			conds.push(visit(guard));
//		}
//		return and(conds);*/
//		return and(getGuardWithAllValues(forallTerm.getVariable(),
//											forallTerm.getRanges(),
//											forallTerm.getGuard()));
//	}
//
//	/**
//	 * Visit.
//	 * 
//	 * @param mapTerm the map term
//	 * 
//	 * @return the map< string, string>
//	 */
//	public Map<String, String> visit(MapTerm mapTerm) {
//		Map<String, String> map = new HashMap<String, String>();
//		List<TupleTerm> pairs = mapTerm.getPair();
//		List<Term> elementPair;
//		for(TupleTerm pair:pairs) {
//			elementPair = pair.getTerms();
//			map.put(visit(elementPair.get(0)), visit(elementPair.get(1)));
//		}
//		return map;
//	}
//
//	public String visit(CaseTerm caseTerm) {
//		String comparedTerm = visit(caseTerm.getComparedTerm());
//		List<Term> resultTerms = caseTerm.getResultTerms();
//		int index = 0;
//		ArrayList<String> conds = new ArrayList<String>();
//		ArrayList<String> rules = new ArrayList<String>();
//		for(Term comparing: caseTerm.getComparingTerm()) {
//			rules.add(visit(resultTerms.get(index)));
//			String eq = Util.equals(comparedTerm, visit(comparing));
//			conds.add(eq);
//			index++;
//		}
//		if(index == caseTerm.getComparingTerm().size()) {
//			Term other = caseTerm.getOtherwiseTerm();
//			if(other != null) {
//				rules.add(visit(caseTerm.getOtherwiseTerm()));
//			}
//			else {
//				Domain dom = caseTerm.getDomain();
//				assert dom != null;
//				Map<String, String> undefInDomains = mv.getUndefValue();
//				assert undefInDomains != null : mv.toString();
//				rules.add(undefInDomains.get(dom.getName()));
//			}
//		}
//		if(conds.contains(Util.trueString)) {
//			assert conds.indexOf(Util.trueString) == conds.lastIndexOf(Util.trueString);
//			return rules.get(conds.indexOf(Util.trueString));
//		}
//		else {
//			StringBuilder sb = new StringBuilder();
//			for(int i = 0; i < conds.size(); i++) {
//				String cond = conds.get(i);
//				if(!cond.equals(Util.falseString)) {
//					sb.append(cond + " ? " + rules.get(i) + " : ");
//				}
//			}
//			sb.append(rules.get(rules.size() - 1));
//			return sb.toString();
//		}
//	}
//
//	public String visit(ConditionalTerm condTerm) {
//		String guard = visit(condTerm.getGuard());
//		String thenTermStr = visit(condTerm.getThenTerm());
//		if(guard.endsWith(Util.trueString)) {
//			return thenTermStr;
//		}
//		Term elseTerm = condTerm.getElseTerm();
//		String elseBranch;
//		if(elseTerm != null) {
//			elseBranch = visit(elseTerm);
//		}
//		else {
//			Domain dom = condTerm.getDomain();
//			elseBranch = mv.getUndefValue().get(dom.getName());
//		}
//		if(guard.equals(falseString)) {
//			return elseBranch;
//		}
//		else {
//			return guard + " ? " + thenTermStr + " : " + elseBranch;
//		}
//	}
}