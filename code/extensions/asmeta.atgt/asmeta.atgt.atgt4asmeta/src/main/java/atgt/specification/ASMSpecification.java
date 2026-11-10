package atgt.specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.NaturalDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import atgt.specification.statement.RuleDeclaration;
import atgt.specification.type.AbstractType;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.IntegerType;
import tgtlib.definitions.expression.type.Type;
import tgtlib.specification.Axiom;
import tgtlib.specification.Specification;
import tgtlib.specification.location.Constant;
import tgtlib.specification.location.DerivedFunction;
import tgtlib.specification.location.Function;
import tgtlib.specification.location.Variable;

/**
 * old class for the specification of an ASM. It contains all the elements of a
 * specification: types, variables, functions, constants, rules, axioms.
 * 
 * @author Angelo Gargantini
 *
 */
public class ASMSpecification implements Specification {
	
	/** The logger : use the same ad the AsmetLoader */
	private final static Logger logger = Logger.getLogger(ASMSpecification.class);

	/** Set of all types in the specification. */
	protected Hashtable<String, Type> types;
	/** Set of all the variables mapped with their names. */
	protected Hashtable<String, Variable> variables;
	/** Set of all the functions. */
	protected Hashtable<String, Function> functions;
	/** Set of all the constants. */
	protected Hashtable<String, Constant> constants;
	/** derived functions */
	protected Hashtable<String, DerivedFunction> derivedFunctions;
	/** Set of all rules. */
	// note that several rules may have equal names
	protected Set<RuleDeclaration> rules;
	/** name. */
	public String name;
	/** The axioms. */
	protected java.util.Collection<Axiom> axioms;
	/** The main rule. */
	RuleDeclaration mainRule;

	/**
	 * Instantiates a new aSM specification.
	 */
	public ASMSpecification() {
		this.types = new Hashtable<String, Type>();
		this.variables = new Hashtable<String, Variable>();
		this.functions = new Hashtable<String, Function>();
		this.derivedFunctions = new Hashtable<String, DerivedFunction>();
		this.constants = new Hashtable<String, Constant>();
		this.rules = new HashSet<RuleDeclaration>();

		this.axioms = new ArrayList<Axiom>();

		// Si aggiunge per default il tipo boolean, poiche' e' presente in ogni
		// macchina ASM
		addType(BoolType.BOOLTYPE);
	}

	/**
	 * Gets the axioms defined for this specification.
	 * 
	 * @return the axiom
	 */
	@Override
	public java.util.Collection<Axiom> getAxiom() {
		return axioms;
	}

	/**
	 * add an axiom.
	 * 
	 * @param ax the ax
	 * 
	 * @return true, if adds the axiom
	 */
	public boolean addAxiom(Axiom ax) {
		return axioms.add(ax);
	}

	/**
	 * Add a new type in the specification.
	 * 
	 * @param _type The type to add
	 */
	public void addType(Type _type) {
		assert types.get(_type.getName()) == null : "type " + _type + " already declared";
		this.types.put(_type.getName(), _type);
	}

	/**
	 * Add a new constant definition in the specification attensione solo una
	 * costante per nome.
	 * 
	 * @param _constant The constant to add
	 */
	public void addConstant(Constant _constant) {
		this.constants.put(_constant.getName(), _constant);
	}

	/**
	 * return the constant with this name null, if their is no constant with this
	 * name.
	 * 
	 * @param name the name
	 * 
	 * @return the constant by name
	 */
	public Constant getConstantByName(String name) {
		return constants.get(name);
	}

	/**
	 * Add a new variable definition in the specification.
	 * 
	 * @param _variable The variable to add
	 */
	public void addVariable(Variable _variable) {
		// type is defined
		assert _variable.getType() != null;
		// check that the type is known
		assert types.contains(_variable.getType()) : "type " + _variable.getType() + " not added to spec";
		// add the variable
		this.variables.put(_variable.getName(), _variable);
	}

	/**
	 * Adds the function.
	 * 
	 * @param _function the _function
	 */
	public void addFunction(Function _function) {
		this.functions.put(_function.getName(), _function);
	}

	public void addDerivedFunction(DerivedFunction _function) {
		this.derivedFunctions.put(_function.getName(), _function);
	}

	/**
	 * Add a new rule to the specification.
	 * 
	 * @param rule the rule
	 */
	public void addRule(RuleDeclaration rule) {
		this.rules.add(rule);
	}

	/**
	 * Return all type inserted.
	 * 
	 * @return the collection< type>
	 */
	public Collection<Type> allTypes() {
		return this.types.values();
	}

	/**
	 * Return all variables.
	 * 
	 * @return the enumeration< variable>
	 */
	public Enumeration<Variable> allVariables() {
		return this.variables.elements();
	}

	/** as immutable collection */
	@Override
	public Collection<Variable> getVariables() {
		return Collections.unmodifiableCollection(this.variables.values());
	}

	/**
	 * return the variable with name var
	 * 
	 * @param var
	 * @return
	 */
	public Variable getVariable(String var) {
		return variables.get(var);
	}

	/**
	 * return the function with name var
	 * 
	 * @param var
	 * @return
	 */
	public Function getFunction(String var) {
		return functions.get(var);
	}

	/**
	 * return the derived variable with name var
	 * 
	 * @param var
	 * @return
	 */
	public DerivedFunction getDerivedFunction(String var) {
		return derivedFunctions.get(var);
	}

	/**
	 * return the functions i.e. variable + argument
	 * 
	 * @return the enumeration< function>
	 */
	public Enumeration<Function> allFunction() {
		return this.functions.elements();
	}

	public Collection<DerivedFunction> allDerivedFuntion() {
		return derivedFunctions.values();
	}

	/**
	 * Return all constants.
	 * 
	 * @return the collection< constant>
	 */
	public Collection<Constant> allConstants() {
		return this.constants.values();
	}

	/**
	 * Returns alla rules.
	 * 
	 * @return the enumeration< rule declaration>
	 */
	public Iterable<RuleDeclaration> allRules() {
		return Collections.unmodifiableSet(this.rules);
	}

	/**
	 * Gets the mainrule.
	 * 
	 * @return the mainrule
	 */
	public RuleDeclaration getMainrule() {
		return this.mainRule;
	}

	/**
	 * Sets the mainrule.
	 * 
	 * @param rd the new mainrule
	 */
	public void setMainrule(RuleDeclaration rd) {
		this.mainRule = rd;
	}

	/**
	 * Gets the type for.
	 * 
	 * @param typename the typename
	 * 
	 * @return the type for, null if not found
	 */
	public Type getTypeFor(String typename) {
		Type result = this.types.get(typename);
		return result;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Type getDomain(Domain dom, EnumConstCreator ecc) {
		// check if already created
		Type t = getTypeFor(dom.getName());
		// check if it is boolean, it is memorized ad Bool
		if (dom instanceof BooleanDomain) {
			t = getTypeFor(BoolType.BOOLTYPE.getName());
			assert t != null;
			return t;
		}
		// still no type with this name
		if (t == null) {
			// else create it
			t = createDomain(dom, ecc);
			assert !t.getName().equals("Bool");
			addType(t);
		}
		assert t != null;
		return t;
	}

	/**
	 * Create the domain for ATGT Specification.
	 * 
	 * @param dom The domain of the XMI model
	 * @return the type for the ATGT Specification
	 */
	public Type createDomain(Domain dom, EnumConstCreator ecc) {
		// check if already created
		assert getTypeFor(dom.getName()) == null;
		return convertDomainToType(dom, ecc);
	}
	
	public static Type convertDomainToType(Domain dom, EnumConstCreator ecc) {
		if (dom instanceof EnumTd) {
			logger.debug("adding enum domain " + dom.getName());
			EnumTd enumTD = (EnumTd) dom;
			ElementsType enumT = new EnumType(enumTD.getName());
			// reverse the order
			List<EnumElement> elemnts = new ArrayList<EnumElement>();
			elemnts.addAll(enumTD.getElement());
			Collections.reverse(elemnts);
			for (EnumElement element : elemnts) {
				EnumConst enumConst = ecc.createEnumConst(element.getSymbol(),enumT);
				enumT.addElement(enumConst);
			}
			return enumT;
		} else if (dom instanceof BooleanDomain) {
			return BoolType.BOOLTYPE;
		} else if ((dom instanceof IntegerDomain) || (dom instanceof NaturalDomain)) {
			return IntegerType.INTEGER_TYPE;
		} else if (dom instanceof RealDomain) {
			throw new RuntimeException("real domains not considered");
		} else if (dom instanceof ConcreteDomain) {
			logger.debug("creating ConcreteDomain" + dom.getName());
			// the only type I can deal with is subset of Natural
			ConcreteDomain CD = (ConcreteDomain) dom;
			if ((CD.getTypeDomain() instanceof IntegerDomain) || (CD.getTypeDomain() instanceof NaturalDomain)) {

				String nome = CD.getName();
				assert nome != null;
				//
				int low, up;
				Integer step = null;

				asmeta.structure.DomainDefinition di = CD.getDefinition();
				// if it not initialized, then consider as Integer
				if (di == null) {
					return new IntegerType(nome);
				}
				// take the first initialization
				Term te = di.getBody();
				// this part used to build delta, no longer in asmm
				if (te instanceof SetTerm) {
					// ||(te instanceof BagIntervalTerm)||
					// (te instanceof SequenceIntervalTerm)){
					EList<Term> elements = ((SetTerm) te).getTerm();
					low = toInteger(elements.get(0));
					up = toInteger(elements.get(elements.size() - 1));
				} else {
					throw new RuntimeException(
							"creaDomain(Domain) - cannot deal with concrete domain init of subtype of "
									+ te.getClass().getName());
				}
				if (low > up) {
					// alcune volte sono scambiati !!!
					int t = low;
					low = up;
					up = t;
				}
				return new BoundType(nome, low, up, step);
			} else {
				logger.error("creaDomain(Domain) -  cannot deal with concrete domain of subtype of " //$NON-NLS-1$
						+ CD.getTypeDomain().getName(), null);
			}
		} else if (dom instanceof AbstractTd) {
			return new AbstractType(dom.getName());
		}
		throw new RuntimeException("what type *" + dom.getName());
	}
	// convert a symbol to an Integer
	static private int toInteger(Term t) {
		if (t instanceof NaturalTerm) {
			NaturalTerm nt = (NaturalTerm) t;
			return Integer.parseInt(nt.getSymbol());
		} else if (t instanceof IntegerTerm) {
			IntegerTerm it = (IntegerTerm) t;
			String itS = it.getSymbol();
			if (itS.startsWith("+"))
				itS = itS.substring(1);
			return Integer.parseInt(itS);
		} else if (t instanceof FunctionTerm) {
			FunctionTerm ft = (FunctionTerm) t;
			asmeta.definitions.Function f = ft.getFunction();
			if (f.getArity() == 1 && f.getName().equals("minus")) {
				return -toInteger(ft.getArguments().getTerms().get(0));
			}
			// PA 2011/10/02
			else if (f.getArity() == 1 && f.getName().equals("plus")) {
				return toInteger(ft.getArguments().getTerms().get(0));
			}
			logger.debug("function arity =" + f.getArity() + " name " + f.getName());
		}
		// System.out.println(((FunctionTerm)t).getFunction().getName());
		throw new RuntimeException("cannot convert " + t + " to Integer");
	}

}