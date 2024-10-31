package asmeta.fmvclib.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.asmeta.parser.Defs;

import asmeta.definitions.Function;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.ExistTerm;
import asmeta.terms.furtherterms.ExistUniqueTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.LetTerm;
import asmeta.terms.furtherterms.MapTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.RealTerm;
import asmeta.terms.furtherterms.StringTerm;
/**
 * 
 * returns the translation - DUPLICATO???? see the TermVisitor of asmetaSMV
 *
 */
public class TermVisitor extends org.asmeta.parser.util.ReflectiveVisitor<String> {
	
	// eu and au operators in CTLLibrary
	private static final String EU_OP = "eu";
	private static final String AU_OP = "au";
	
	
	InitialStateVisitor mv;
	private Environment env;
	protected FunctionVisitor fv;
	private boolean inLocationArguments;

	public TermVisitor(Environment env, InitialStateVisitor mv) {
		this(env, mv, new FunctionVisitor(env));
	}

	public TermVisitor(Environment env, InitialStateVisitor mv, FunctionVisitor fv) {
		this.env = env;
		env.tv = this;
		this.mv = mv;
		this.fv = fv;
	}

	/**
	 * Visita un termine.
	 * 
	 * @param term the term
	 * 
	 * @return the string
	 */
	public String visit(Term term) {
		//System.out.println("visit term "+term);
		return visit((Object) term);
	}

	/**
	 * Visita una VariableTerm.
	 * 
	 * @param variable the variable
	 * 
	 * @return the string
	 */
	public String visit(VariableTerm variable) {
		return env.varsValues.get(variable);
		//return env.getVarValue(variable);
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
	public String visit(FunctionTerm funcTerm) throws Exception {
		String name;
		StringBuilder s = new StringBuilder();
		Function func = funcTerm.getFunction();
		String funcName = Util.getFunctionName(func);
		String funcSymbol = Util.parsename(funcName);
		if(funcSymbol.equals("self")) {
			//System.out.println("funcSymbol.equals(self) "+env.self);
			return env.self;
		}
		//if(mv.abstractDomains.contains(getDomainName(func.getCodomain()))){
		if(Defs.isAbstractConst(func)) {
			return funcSymbol.toUpperCase();
		}
		if(fv.isNotNativelySupportedFunction(funcSymbol)) {
			return fv.visit(funcSymbol, funcTerm.getArguments().getTerms());
		}
		else if(FunctionVisitor.hasEvaluateVisitor(funcSymbol)) {
			return fv.evaluateFunction(funcSymbol, funcTerm.getArguments().getTerms());
		}

		if(Defs.isDerived(func) || Defs.isUserDefined(func) || Defs.isMonitored(func)) {
			if(inLocationArguments) {
				throw new RuntimeException("It's not possible to translate a location that have as arguments a monitored, controlled or derived function. The argument is " + func.getName());
			}
			name = locationName(funcTerm);
		}
		else {
			TupleTerm arg = funcTerm.getArguments();
			List<Term> tuple = null;
			int arity = 0;
			if (arg != null) {
				tuple = arg.getTerms();
				arity = tuple.size();
			}
			if (tuple == null) {
				s.append(funcSymbol);
			}
			else {
				if(funcName.equals(AU_OP) || funcName.equals(EU_OP)){
					s.append(funcName.toUpperCase() + "[");
				}
				else if(arity >= 1) {
					if(!funcName.equals("u")) {
						s.append(funcSymbol);
					}
					s.append("(");
				}
				Iterator<Term> iterator = tuple.iterator();
				if (iterator.hasNext()) {
					Term term = iterator.next();
					s.append(visit(term));
					while (iterator.hasNext()) {
						term = iterator.next();
						s.append(" " + funcSymbol + " " + visit(term));
					}
				}
				if(funcName.equals(AU_OP) || funcName.equals(EU_OP)) {
					s.append("]");
				}
				else {
					s.append(")");
				}
			}
			name = s.toString();
		}
		
		return name;
	}
	
	/**
	 * Creates the name.
	 * 
	 * @param func the func
	 * @param args the args
	 * 
	 * @return the string
	 */
	private String createName(Function func, TupleTerm args){
		StringBuilder s = new StringBuilder();
		String name;
		s.append(Util.getFunctionName(func));
		if (args != null) {
			s.append("_" + visit(args));
		}
		name = s.toString();
		return name;
	}

	/**
	 * Visit.
	 * 
	 * @param tupleTerm the tuple term
	 * 
	 * @return the string
	 */
	public String visit(TupleTerm tupleTerm) {
		StringBuilder s = new StringBuilder();
		Iterator<Term> iterator = tupleTerm.getTerms().iterator();
		s.append(visit(iterator.next()));
		while(iterator.hasNext()) {
			s.append("_" + visit(iterator.next()));
		}
		return s.toString();
	}
	
	/**
	 * Visit.
	 * 
	 * @param tupleTerm the tuple term
	 * 
	 * @return the string
	 */
	public String visit(StringTerm stringTerm) {
		return stringTerm.getSymbol();
	}

	public String visit(LetTerm letTerm) {
		if(letTerm.getDomain() instanceof BooleanDomain) {
			Term term = letTerm.getBody();
			List<VariableTerm> vars = letTerm.getVariable();
			ArrayList<String[]> values = new ArrayList<String[]>();
			mv.rv.combineValues(vars, 0, values, new String[vars.size()]);
			ArrayList<String> ands = new ArrayList<String>();
			for(String[] value: values) {
				env.setVarsValues(vars, value);
				Iterator<Term> terms = letTerm.getAssignmentTerm().iterator();
				ArrayList<String> conds = new ArrayList<String>();
				for(String v: value) {
					conds.add(Util.equals(v, visit(terms.next())));
				}
				ands.add(Util.implies(Util.and(conds), visit(term)));
			}
			return Util.and(ands);
		}
		else {
			throw new RuntimeException("Only boolean let terms are supported.");
		}
	}

	/**
	 * Location name.
	 * 
	 * @param loc the loc
	 * 
	 * @return the string
	 */
	public String locationName(LocationTerm loc) {
		inLocationArguments = true;//serve per controllare, nella visita degli argomenti, che l'argomento non sia un'altra funzione
		String name = createName(loc.getFunction(), loc.getArguments());
		inLocationArguments = false;
		env.usedLoc.add(name);
		return name;
	}

	/**
	 * Restituisce la traduzione di una location
	 * 
	 * @param func the func
	 * 
	 * @return la traduzione della location
	 */
	public String locationName(FunctionTerm func){
		return createName(func.getFunction(), func.getArguments());	
	}

	/**
	 * Visit.
	 * 
	 * @param location the location
	 * 
	 * @return the string
	 * 
	 * @throws AsmNotSupportedException the asm not supported exception
	 */
	public String visit(LocationTerm location)  {
		Function func = location.getFunction();
		if(func.getName().equals("self")) {
			return env.self;
		}
		/*if(Defs.isMonitored(func)){
			return env.monValues.get(createName(func, location.getArguments()));
		}*/
		String locationName = locationName(location);
		//if(mv.agents.contains(locationName.toUpperCase()))
		//	return locationName.toUpperCase();

		return locationName;
	}

	/**
	 * Visit a natural term.
	 * 
	 * @param number the number
	 * 
	 * @return the string
	 */
	public String visit(NaturalTerm number) {
		String symbol = number.getSymbol();
		assert symbol.charAt(symbol.length() - 1) == 'n';
		//In AsmetaL, natural numbers terminate with an "n"
		//To have the integer representation, we need to remove the "n".
		return symbol.substring(0, symbol.length() - 1);
	}

	/**
	 * Visit.
	 * 
	 * @param number the number
	 * 
	 * @return the string
	 */
	public String visit(IntegerTerm number) {
		return number.getSymbol();
	}
	
	/**
	 * Visit.
	 * 
	 * @param number the number
	 * 
	 * @return the string
	 */
	public String visit(RealTerm number) {
		return number.getSymbol();
	}

	/**
	 * Visit an enum term.
	 * 
	 * @param term the term
	 * 
	 * @return the string
	 */
	public String visit(EnumTerm term) {
		return term.getSymbol().toUpperCase();
	}

	/**
	 * Visit a boolean term.
	 * In NuSMV the symbolic constants are uppercase.
	 * 
	 * @param bool the bool
	 * 
	 * @return the string
	 */
	public String visit(BooleanTerm bool) {
		return bool.getSymbol().toUpperCase();
	}

	/**
	 * Visit.
	 * 
	 * @param undef the undef
	 * 
	 * @return the string
	 */
	public String visit(UndefTerm undef) {
		return "undef";
	}
	
	/**
	 * Visit.
	 * 
	 * @param term the term
	 * 
	 * @return the string
	 */
	public String visit(DomainTerm term) {
		PowersetDomain dom = (PowersetDomain) term.getDomain();
		Domain base = dom.getBaseDomain();
		return Util.getDomainName(base);
	}

	/**
	 * Visita la guardia con tutte le combinazioni possibili dei valori delle
	 * variabili.
	 * Serve per la visita del forall, exist e del existUnique terms che hanno
	 * una visita comune tranne nel modo in cui combinano le guardie.
	 * 
	 * @param vars variabili logiche
	 * @param ranges domini in cui assumo valore le variabili
	 * @param guard condizione booleana
	 * @return uno stack con tutte le valutazioni della guardia
	 * @throws AsmNotSupportedException 
	 */
	Stack<String> getGuardWithAllValues(List<VariableTerm> vars, List<Term> ranges, Term guard) {
		List<Domain> domains = new ArrayList<Domain>();
		ArrayList<String[]> values = new ArrayList<String[]>();
		Stack<String> conds = new Stack<String>();
		for(Term t: ranges) {
			domains.add(t.getDomain());
		}
		//calcola tutte le combinazioni dei valori delle variabili
		mv.rv.combineValues(vars, domains, 0, values, new String[vars.size()]);
		for(String[] value: values) {
			env.setVarsValues(vars, value);
			conds.push(visit(guard)); //visita la guardia con i valori correnti delle variabili
		}
		return conds;
	}
	
	/**
	 * Visits an existTerm.
	 * 
	 * @param existTerm the exist term
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	public String visit(ExistTerm existTerm) throws Exception {
		return Util.or(getGuardWithAllValues(existTerm.getVariable(),
											existTerm.getRanges(),
											existTerm.getGuard()));
	}

	/**
	 * Visit an ExistUniqueTerm.
	 * 
	 * @param exitUniqueTerm the exit unique term
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	public String visit(ExistUniqueTerm exitUniqueTerm) throws Exception {
		return Util.xor(getGuardWithAllValues(exitUniqueTerm.getVariable(),
											exitUniqueTerm.getRanges(),
											exitUniqueTerm.getGuard()));
	}

	/**
	 * Visits a forall term
	 * 
	 * @param forallTerm the forall term
	 * 
	 * @return the string
	 * 
	 * @throws Exception the exception
	 */
	public String visit(ForallTerm forallTerm) throws Exception {
		return Util.and(getGuardWithAllValues(forallTerm.getVariable(),
											forallTerm.getRanges(),
											forallTerm.getGuard()));
	}

	/**
	 * Visit.
	 * 
	 * @param mapTerm the map term
	 * 
	 * @return the map< string, string>
	 */
	public Map<String, String> visit(MapTerm mapTerm) {
		Map<String, String> map = new HashMap<String, String>();
		List<TupleTerm> pairs = mapTerm.getPair();
		List<Term> elementPair;
		for(TupleTerm pair:pairs) {
			elementPair = pair.getTerms();
			map.put(visit(elementPair.get(0)), visit(elementPair.get(1)));
		}
		return map;
	}

	public String visit(CaseTerm caseTerm) {
		String comparedTerm = visit(caseTerm.getComparedTerm());
		List<Term> resultTerms = caseTerm.getResultTerms();
		int index = 0;
		ArrayList<String> conds = new ArrayList<String>();
		ArrayList<String> rules = new ArrayList<String>();
		for(Term comparing: caseTerm.getComparingTerm()) {
			rules.add(visit(resultTerms.get(index)));
			String eq = Util.equals(comparedTerm, visit(comparing));
			conds.add(eq);
			index++;
		}
		if(index == caseTerm.getComparingTerm().size()) {
			Term other = caseTerm.getOtherwiseTerm();
			if(other != null) {
				rules.add(visit(caseTerm.getOtherwiseTerm()));
			}
			else {
				Domain dom = caseTerm.getDomain();
				assert dom != null;
				Map<String, String> undefInDomains = mv.getUndefValue();
				assert undefInDomains != null : mv.toString();
				rules.add(undefInDomains.get(dom.getName()));
			}
		}
		if(conds.contains(Util.trueString)) {
			assert conds.indexOf(Util.trueString) == conds.lastIndexOf(Util.trueString);
			return rules.get(conds.indexOf(Util.trueString));
		}
		else {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < conds.size(); i++) {
				String cond = conds.get(i);
				if(!cond.equals(Util.falseString)) {
					sb.append(cond + " ? " + rules.get(i) + " : ");
				}
			}
			sb.append(rules.get(rules.size() - 1));
			return sb.toString();
		}
	}

	public String visit(ConditionalTerm condTerm) {
		String guard = visit(condTerm.getGuard());
		String thenTermStr = visit(condTerm.getThenTerm());
		if(guard.endsWith(Util.trueString)) {
			return thenTermStr;
		}
		Term elseTerm = condTerm.getElseTerm();
		String elseBranch;
		if(elseTerm != null) {
			elseBranch = visit(elseTerm);
		}
		else {
			Domain dom = condTerm.getDomain();
			elseBranch = mv.getUndefValue().get(dom.getName());
		}
		if(guard.equals(Util.falseString)) {
			return elseBranch;
		}
		else {
			return guard + " ? " + thenTermStr + " : " + elseBranch;
		}
	}
}