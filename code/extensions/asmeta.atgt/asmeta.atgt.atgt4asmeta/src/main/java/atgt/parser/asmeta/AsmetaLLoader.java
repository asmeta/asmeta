/*******************************************************************************
 * Copyright (c) 2008 Angelo Gargantini.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Angelo Gargantini - initial API and implementation
 ******************************************************************************/
package atgt.parser.asmeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Logger;
import asmeta.AsmCollection;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.ControlledFunction;
import asmeta.definitions.DerivedFunction;
import asmeta.definitions.DynamicFunction;
import asmeta.definitions.Function;
import asmeta.definitions.Invariant;
import asmeta.definitions.MonitoredFunction;
import asmeta.definitions.Property;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.BooleanDomain;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.EnumElement;
import asmeta.definitions.domains.EnumTd;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.NaturalDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.structure.FunctionDefinition;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import atgt.parser.AsmSpecReader;
import atgt.specification.ASMSpecification;
import atgt.specification.type.AbstractType;
import tgtlib.definitions.expression.Expression;
import tgtlib.definitions.expression.IdExpression;
import tgtlib.definitions.expression.type.BoolType;
import tgtlib.definitions.expression.type.BoundType;
import tgtlib.definitions.expression.type.DummyType;
import tgtlib.definitions.expression.type.ElementsType;
import tgtlib.definitions.expression.type.EnumConst;
import tgtlib.definitions.expression.type.EnumConstCreator;
import tgtlib.definitions.expression.type.EnumType;
import tgtlib.definitions.expression.type.IntegerType;
import tgtlib.definitions.expression.type.Type;
import tgtlib.specification.Axiom;
import tgtlib.specification.location.Constant;
import tgtlib.specification.location.Location;
import tgtlib.specification.location.AsmVariable;

/**
 * reads spec written in AsmetaL and convert them in 
 * 
 * @author Sergio Galati, AG
 */
public class AsmetaLLoader extends AsmSpecReader {

	/** The logger. */
	private final static Logger logger = Logger.getLogger(AsmetaLLoader.class);

	ASMSpecification SP;

	EnumConstCreator ecc;

	TermConverter<ASMSpecification> termConv;

	/**
	 * Instantiates a new asmetaL loader.
	 */
	public AsmetaLLoader() {
		ecc = new EnumConstCreator();
	}

	/**
	 * read an asm file containing a specification written in AsmetaL.
	 * 
	 * @param f the file containing the spec
	 * 
	 * @return the ASM specification
	 * @throws ParseException 
	 */
	@Override
	public ASMSpecification read(File f) throws tgtlib.specification.ParseException {
		return read(f,true);
	}

		
	public ASMSpecification read(File f, boolean convertRule) throws tgtlib.specification.ParseException {
		logger.debug("reading file " + f.getName());
		assert f.exists();
		// read the specification - asmeta parser
		AsmCollection kb;
		try {
			kb = org.asmeta.parser.ASMParser.setUpReadAsm(f);
		} catch (Exception e) {
			throw new tgtlib.specification.ParseException(e.getMessage());
		}
		// get the spec name for the file name
		// load the spec
		assert kb != null;
		asmeta.structure.Asm asmMain = kb.getMain();
		if (asmMain == null) {
			logger.error("loadAsm(AsmetaPackage) - no valid asm found", null); //$NON-NLS-1$
			return SP;
		}
		SP = new ASMSpecification();
		SP.name = kb.getMain().getName();
		termConv = new TermConverter<>(SP, ecc);
		loadAsm(asmMain, convertRule);
		return SP;
	}

	/**
	 * This class is the main class of the parser. It is called from constructor.
	 * The constructor use this function to read and parser the file.
	 * 
	 * @param asmMain the main asm
	 * @param convertRule 
	 */
	private void loadAsm(asmeta.structure.Asm asmMain, boolean convertRule) {
		// Recupero i domini.
		/*
		 * per ora non uso questa parte di codice perch? creo i domini in base alle
		 * funzioni che trovo
		 */
		// Recupero le funzioni all'interno della ASM
		Collection<Function> funtions = asmMain.getHeaderSection().getSignature().getFunction();
		//
		for (Function element : funtions) {
			if (element instanceof DynamicFunction) {
				DynamicFunction fnct = (DynamicFunction) element;
				// Recupero le informazioni sulla funzione
				Domain dom = fnct.getDomain();
				Domain codom = fnct.getCodomain();
				int arity = fnct.getArity();
				// two ways to define a dynamic or in definition or in
				// initializazione
				Expression initExpr = null;
				asmeta.structure.FunctionDefinition fundef = fnct.getDefinition();
				Collection<asmeta.structure.FunctionInitialization> inits = fnct.getInitialization();
				// take the first initialization
				asmeta.structure.FunctionInitialization funini = null;
				if ((inits != null) && (inits.size() > 0)) {
					// its definition is null
					assert fundef == null;
					funini = (asmeta.structure.FunctionInitialization) inits.toArray()[0];
					// get the init value from initialization
					initExpr = termConv.computeTerm(funini.getBody());
					logger.debug(fnct.getName() + " inizializated as " + funini.getBody() + " = " + initExpr);
				} else if (fundef != null) {
					// det the init value from definitions
					initExpr = termConv.computeTerm(fundef.getBody());
					logger.debug(fnct.getName() + " defined as " + fundef.getBody() + "=" + initExpr);
				} else {
					logger.debug(fnct.getName() + " not inizializated");
				}
				if (arity == 0) {
					// variable
					Type tipo = getDomain(codom);
					assert tipo != null : "variable " + fnct.getName() + " has no type";
					IdExpression fcId = ecc.createIdExpression(element.getName(), tipo);
					assert fcId.getType() != null : fcId.getIdString() + " has no type";
					AsmVariable var = new AsmVariable(fcId, tipo, initExpr);
					if (fnct instanceof MonitoredFunction)
						var.setMonitored();
					else if (fnct instanceof ControlledFunction)
						var.setControlled();
					else
						logger.error("loadAsm(AsmetaPackage) - function kind not supported", null); //$NON-NLS-1$
					SP.addVariable(var);
					logger.debug("adding variable " + var);
				} else if (arity == 1) {
					Type domType = getDomain(dom);
					Type coType = getDomain(codom);
					// it's domain (not its values)
					IdExpression fcId = ecc.createIdExpression(element.getName(), domType);
					tgtlib.specification.location.Function var = new tgtlib.specification.location.Function(fcId, domType,
							coType, null/* initValue */);
					// var.setLaw(fundef.getBodyFunctionExpr());
					var.setValue(initExpr);
					if (fnct instanceof MonitoredFunction)
						var.setMonitored();
					else if (fnct instanceof ControlledFunction)
						var.setControlled();
					else
						logger.error("loadAsm(AsmetaPackage) - function kind not supported", null); //$NON-NLS-1$
					SP.addFunction(var);
					logger.debug("adding function " + var + " : " + var.getDomain() + " -> " + var.getCodomain());
				} else if (logger.isDebugEnabled()) {
					logger.debug("loadAsm(AsmetaPackage) - ATTENTION: Ariety = " + arity + " not supported"); //$NON-NLS-1$ //$NON-NLS-2$
				}

			} else if (element instanceof StaticFunction) {
				if (element.getArity() == 0) {
					// variable
					Type tipo = getDomain(element.getCodomain());
					IdExpression fcId = ecc.createIdExpression(element.getName(), tipo);
					AsmVariable var = new AsmVariable(fcId, tipo, null);
					SP.addVariable(var);
					logger.debug("adding variable " + var);
				} else {
					// reads only functions
					StaticFunction fnct = (StaticFunction) element;
					String name = fnct.getName();
					// Recupero le informazioni sulla funzione
					Domain dom = fnct.getDomain();
					Domain codom = fnct.getCodomain();
					asmeta.structure.FunctionDefinition fundef = fnct.getDefinition();
					// if the function is not defined, give up
					if (fundef != null) {
						// Come sopra line 93
						Type tipo = getDomain(codom);
						Expression expr = termConv.computeTerm(fundef.getBody());
						IdExpression fcId = ecc.createIdExpression(element.getName(), tipo);
						Constant var = new Constant(fcId, tipo, expr);
						logger.debug("adding constant " + var);
						SP.addConstant(var);
					}
				}
			} else if (element instanceof DerivedFunction) {
				// this part to read derived functions
				logger.debug("reading derived function " + element.getName());
				DerivedFunction d = (DerivedFunction) element;
				// only derived variables upto now
				// assert d.getArity() == 0;
				// get domain
				Domain dom = d.getCodomain();
				assert dom != null;
				Type type = getDomain(dom);
				// add the derived function
				IdExpression fcId = ecc.createIdExpression(element.getName(), type);
				tgtlib.specification.location.DerivedFunction dv = new tgtlib.specification.location.DerivedFunction(fcId);
				SP.addDerivedFunction(dv);
			} else {
				throw new RuntimeException("element " + element + "not supported ");
			}
		}
		// for derived functions, add also their definitions
		//
		for (Function element : funtions) {
			if (element instanceof DerivedFunction) {
				String name = element.getName();
				logger.debug("adding definition of derived function " + name);
				DerivedFunction d = (DerivedFunction) element;
				// get its definition
				FunctionDefinition def = d.getDefinition();
				assert def != null;
				assert def.getBody() != null;
				Expression expr = termConv.translateTerm(def.getBody());
				tgtlib.specification.location.DerivedFunction asmder = SP.getDerivedFunction(name);
				asmder.setValue(expr);
			}
		}
		if (convertRule) {
			// add all rules - it could be not necessary
			Collection<asmeta.definitions.RuleDeclaration> allRules = asmMain.getBodySection().getRuleDeclaration();
			// build the converter
			RuleConverter ruleConverter = new RuleConverter(SP, ecc, termConv);
			for (asmeta.definitions.RuleDeclaration ruleD : allRules) {
				if (logger.isDebugEnabled()) {
					logger.debug("loadAsm(AsmetaPackage) - adding rule " + ruleD.getName()); //$NON-NLS-1$
				}
				SP.addRule(ruleConverter.convertRuleDeclaration(ruleD));
			}
			// add the main rule (it is not already in body)
			//
			// get main rule
			//
			asmeta.definitions.RuleDeclaration mainrule = asmMain.getMainrule();
			if (mainrule != null) {
				atgt.specification.statement.RuleDeclaration atgtmr = ruleConverter.convertRuleDeclaration(mainrule);
				SP.addRule(atgtmr);
				SP.setMainrule(atgtmr);
			}

		}
		// check axioms for monitored variables
		List<Property> properties = asmMain.getBodySection().getProperty();
		List<Invariant> invariants = new ArrayList<Invariant>();
		for (Property property : properties) {
			if (property instanceof Invariant) {
				invariants.add((Invariant) property);
			}
		}
		findDelta(invariants);
		// add all the invariants
		for (asmeta.definitions.Invariant invariant : invariants) {
			Expression body = termConv.computeFunctionTerm((FunctionTerm) invariant.getBody());
			SP.addAxiom(new Axiom(invariant.getName(), body));

		}
		// return
		return;

	} 

	/**
	 * get a domain, creates if necessary
	 * 
	 * @param dom
	 * @return
	 */
	Type getDomain(Domain dom) {
		return SP.getDomain(dom, ecc);
	}

	Type createDomain(Domain dom) {
		return SP.createDomain(dom, ecc);
	}




	/*
	 * check if axiom TODO da completare
	 */
	/**
	 * Find delta.
	 * 
	 * @param invariants the axioms
	 */
	private void findDelta(Collection<asmeta.definitions.Invariant> invariants) {
		// for every monitored
		for (Enumeration<AsmVariable> i = SP.allVariables(); i.hasMoreElements();) {
			Location v = i.nextElement();
			if (v.isMonitored()) {
				String name = v.getName();
				for (asmeta.definitions.Invariant a : invariants) {
					Collection<asmeta.definitions.Function> fs = a.getConstrainedFunction();
					for (asmeta.definitions.Function f : fs) {
						if (!(f.getName().equals(name)))
							return;
						// same name!!
						// get the body, with must be
						asmeta.terms.basicterms.Term t = a.getBody();
						if ((t instanceof FunctionTerm) && (((FunctionTerm) t).getFunction().getName().equals("abs"))) {
							asmeta.terms.basicterms.TupleTerm difft = ((FunctionTerm) t).getArguments();
							// < operator
							if (difft.getTerms().size() != 1)
								return;

						}
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see atgt.parser.AsmSpecReader#getFileExtension()
	 */
	@Override
	public String getFileExtension() {
		return "asm";
	}

}
