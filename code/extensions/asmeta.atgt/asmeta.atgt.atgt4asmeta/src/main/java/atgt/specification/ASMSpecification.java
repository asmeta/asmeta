package atgt.specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import atgt.specification.location.Constant;
import atgt.specification.location.DerivedFunction;
import atgt.specification.location.Function;
import atgt.specification.location.Variable;
import atgt.specification.statement.RuleDeclaration;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.Type;
import tgtlib.specification.Axiom;
import tgtlib.specification.Specification;

public class ASMSpecification implements Specification {

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
	 * @param ax
	 *            the ax
	 * 
	 * @return true, if adds the axiom
	 */
	public boolean addAxiom(Axiom ax) {
		return axioms.add(ax);
	}

	/**
	 * Add a new type in the specification.
	 * 
	 * @param _type
	 *            The type to add
	 */
	public void addType(Type _type) {
		assert types.get(_type.getName()) == null : "type " +_type + " already declared";
		this.types.put(_type.getName(), _type);
	}

	/**
	 * Add a new constant definition in the specification attensione solo una
	 * costante per nome.
	 * 
	 * @param _constant
	 *            The constant to add
	 */
	public void addConstant(Constant _constant) {
		this.constants.put(_constant.getName(), _constant);
	}

	/**
	 * return the constant with this name null, if their is no constant with
	 * this name.
	 * 
	 * @param name
	 *            the name
	 * 
	 * @return the constant by name
	 */
	public Constant getConstantByName(String name) {
		return constants.get(name);
	}

	/**
	 * Add a new variable definition in the specification.
	 * 
	 * @param _variable
	 *            The variable to add
	 */
	public void addVariable(Variable _variable) {
		// type is defined
		assert _variable.getType() != null;
		// check that the type is known		
		assert types.contains(_variable.getType()): "type " + _variable.getType() + " not added to spec";
		//add the variable
		this.variables.put(_variable.getName(), _variable);
	}

	/**
	 * Adds the function.
	 * 
	 * @param _function
	 *            the _function
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
	 * @param rule
	 *            the rule
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

	/** as immutable collection*/
	@Override
	public Collection<Variable> getVariables() {
		return Collections.unmodifiableCollection(this.variables.values());
	}

	
	/** return the variable with name var
	 * 
	 * @param var
	 * @return
	 */
	public Variable getVariable(String var){
		return variables.get(var);		
	}

	/** return the function  with name var
	 * 
	 * @param var
	 * @return
	 */
	public Function getFunction(String var){
		return functions.get(var);		
	}

	/** return the derived variable with name var
	 * 
	 * @param var
	 * @return
	 */
	public DerivedFunction getDerivedFunction(String var){
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
		return  Collections.unmodifiableSet(this.rules);
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
	 * @param rd
	 *            the new mainrule
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
}