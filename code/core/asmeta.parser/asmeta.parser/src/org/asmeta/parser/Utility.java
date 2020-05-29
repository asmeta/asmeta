/*******************************************************************************
 * Copyright (c) 2005, 2006 ASMETA group (http://asmeta.sourceforge.net)
 * License Information: http://asmeta.sourceforge.net/licensing/
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License version 2 as
 *   published by the Free Software Foundation.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301
 *   USA
 *
 *   http://www.gnu.org/licenses/gpl.txt
 *
 *
 *******************************************************************************/
package org.asmeta.parser;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.DefinitionsFactory;
import asmeta.definitions.DynamicFunction;
import asmeta.definitions.Function;
import asmeta.definitions.RuleDeclaration;
import asmeta.definitions.domains.AbstractTd;
import asmeta.definitions.domains.AgentDomain;
import asmeta.definitions.domains.AnyDomain;
import asmeta.definitions.domains.BagDomain;
import asmeta.definitions.domains.BasicTd;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.DomainsFactory;
import asmeta.definitions.domains.IntegerDomain;
import asmeta.definitions.domains.MapDomain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.RealDomain;
import asmeta.definitions.domains.ReserveDomain;
import asmeta.definitions.domains.RuleDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.definitions.domains.StructuredTd;
import asmeta.definitions.domains.TypeDomain;
import asmeta.structure.Signature;
import asmeta.terms.TermsFactory;
import asmeta.terms.basicterms.ConstantTerm;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.VariableKind;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.BagTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.MapTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.RealTerm;
import asmeta.terms.furtherterms.SequenceTerm;
import asmeta.terms.furtherterms.StringTerm;

public class Utility {
	// PA 12/11/2011. Fabio Albani, in method resolve, needs to import just the the
	// first rule with
	// the best ranking. The static field "selectFirstBestRanking" can be used
	// to set this option; if "selectFirstBestRanking" is
	// - true, the first the first rule with the best ranking is selected;
	// - false, if there are at least two rules with the equal best ranking
	// an exception is raised.
	// By default, "selectFirstBestRanking" is true.
	public static boolean selectFirstBestRanking = true;

	static Logger logger = Logger.getLogger(Utility.class);

	// ================================================= appendInKey
	/*
	 * Concatenate to key the representation of t depending on the attribute symbol
	 * key = "" -> t = 10 : returns "10" key "[3," -> t = {3,4} returns "[3,{3,4}"
	 */
	public static StringBuffer appendInKey(StringBuffer key, Term t) {

		if (t instanceof FunctionTerm) {
			FunctionTerm ft = (FunctionTerm) t;
			key.append(ft.getFunction().getName());
			appendInKey(key, ft.getArguments());
		}

		if (t instanceof ConstantTerm)
			key.append(((ConstantTerm) t).getSymbol());

		if (t instanceof VariableTerm)
			key.append(((VariableTerm) t).getName());

		if (t instanceof DomainTerm)
			key.append(((DomainTerm) t).getDomain().getName());

		if (t instanceof SequenceTerm) {
			key.append("[");
			Iterator<Term> iter = ((SequenceTerm) t).getTerms().iterator();
			while (iter.hasNext()) {
				appendInKey(key, iter.next());
				key.append(",");
			}
			key.deleteCharAt(key.length() - 1); // delete the last comma
			key.append("]");
		}
		if (t instanceof SetTerm) {
			key.append("{");
			Iterator<Term> iter = ((SetTerm) t).getTerm().iterator();
			while (iter.hasNext()) {
				appendInKey(key, iter.next());
				key.append(",");
			}
			key.deleteCharAt(key.length() - 1); // delete the last comma
			key.append("}");
		}
		if (t instanceof BagTerm) {
			key.append("<");
			Iterator iter = ((BagTerm) t).getTerm().iterator();
			while (iter.hasNext()) {
				appendInKey(key, (Term) iter.next());
				key.append(",");
			}
			key.deleteCharAt(key.length() - 1); // delete the last comma
			key.append(">");
		}
		if (t instanceof MapTerm) {
			key.append("{");
			Iterator iter = ((MapTerm) t).getPair().iterator();
			while (iter.hasNext()) {
				appendInKey(key, (Term) iter.next());
				key.append(",");
			}
			key.deleteCharAt(key.length() - 1); // delete the last comma
			key.append("}");
		}
		if (t instanceof TupleTerm) {
			key.append("(");
			Iterator iter = ((TupleTerm) t).getTerms().iterator();
			while (iter.hasNext()) {
				Term tupleElem = (Term) iter.next();
				if (tupleElem instanceof FunctionTerm) {
					key.append(((FunctionTerm) tupleElem).getFunction().getName());
					appendInKey(key, ((FunctionTerm) tupleElem).getArguments());
				} else
					appendInKey(key, tupleElem);
				key.append(",");
			}
			key.deleteCharAt(key.length() - 1); // delete the last comma
			key.append(")");
		}
		return key;
	}

	// ================================================= getCommonTD
	/*
	 * Se td ha TD fissato controllo che la funz compare(td, elem_TD) torni true se
	 * td ha TD generico (non ho ancora incotrato un termine con TD fissato) e
	 * l'elem corrente ha TD generico -> passo al prossimo elemento (non faccio
	 * alcun controllo) se td ha TD generico (non ho ancora incotrato un termine con
	 * TD fissato) e l'elem corrente ha TD fissato -> assegno a td tale dominio e
	 * passo al prossimo elem Se non vengono riscontrate differenze tra i
	 * type-domain degli elementi della collection viene tornato il TypeDomain
	 * comune altrimenti viene tornato null NOTA: se tutti gli elementi della
	 * collection hanno come type-domain AnyDomain (sono variabili) il TD del
	 * collectionTerm sar? uno structuredTD su AnyDomain
	 */
	public static TypeDomain getCommonTD(Collection<Term> c) {
		Iterator<Term> iter = c.iterator();
		TypeDomain elem_TD = OCL_Checker.getTypeDomain(iter.next().getDomain()); // take
		// the
		// TypeDomain
		// of
		// the
		// first
		// element
		// of
		// the
		// collection
		TypeDomain td = elem_TD;
		// check if every term of the collection has td as associated TypeDomain
		while (iter.hasNext()) {
			elem_TD = OCL_Checker.getTypeDomain(iter.next().getDomain());
			if (!(td instanceof AnyDomain)) {
				if (!OCL_Checker.compatible(td, elem_TD))
					return null;
			} else {
				if (!(elem_TD instanceof AnyDomain))
					td = elem_TD;
			}

		}
		return td;
	}

	// ================================================= convertDoubleToTerm,
	// convertIntegerToTerm
	/**
	 * Returns a Term (an Unary Expression with domain Real or a RealTerm) for a
	 * double. For n and +n it returns a RealTerm, for -n a FunctionTerm (Unary
	 * Expression), where n is a double. For 0.0 a RealTerm is created.
	 * 
	 * @throws ParseException
	 */

	private static RealTerm/* Term */ convertDoubleToTerm(double n, TermsFactory termsPack, TypeDomain realDom,
			HashMap<String, List<Function>> declared_Func, HashMap<String, Domain> declared_Dom,
			DefinitionsFactory defPack, Signature s) throws ParseException {

		// create a new RealTerm for n
		RealTerm realTerm = termsPack.getFurtherTerms().createRealTerm();
		realTerm.setSymbol(Double.toString(n));
		// set references
		realTerm.setDomain(realDom);

		return realTerm;
		// commentato da PA il 22 aprile 2010. Forse basta ritornare il realTerm.
		/*
		 * if (n >= 0) return realTerm;
		 * 
		 * // otherwise create a FunctionTerm for -n TupleTerm tupleT =
		 * createSingle(realTerm, termsPack, defPack, s); FunctionTerm t =
		 * createFunctionTerm("minus", tupleT, termsPack, declared_Func, declared_Dom,
		 * defPack, s); if (t == null) throw new ParseException(
		 * "Problems in creating the unary expression for" + n); return t;
		 */
	}

	/**
	 * return an Integer term is n is positive, otherwise a Function term
	 * 
	 * @param n
	 * @param termsPack
	 * @param integerDom
	 * @param declared_Func
	 * @param declared_Dom
	 * @param defPack
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	private static IntegerTerm/* Term */ convertIntegerToTerm(int n, TermsFactory termsPack, TypeDomain integerDom,
			HashMap<String, List<Function>> declared_Func, HashMap<String, Domain> declared_Dom,
			DefinitionsFactory defPack, Signature s) throws ParseException {

		// create a new IntegerTerm

		IntegerTerm integerTerm = termsPack.getFurtherTerms().createIntegerTerm();
		// set references
		integerTerm.setDomain(integerDom);
		integerTerm.setSymbol(Integer.toString(n));
		return integerTerm;

		// commentato da PA il 22/04/2010. Forse basta ritornare l'integerTerm.
		/*
		 * if (n >= 0){ integerTerm.setSymbol(Integer.toString(n)); return integerTerm;
		 * } // otherwise create a FunctionTerm -n assert( n < 0);
		 * integerTerm.setSymbol(Integer.toString(n)); TupleTerm tupleT =
		 * createSingle(integerTerm, termsPack, defPack, s); FunctionTerm t =
		 * createFunctionTerm("minus", tupleT, termsPack, declared_Func, declared_Dom,
		 * defPack, s); if (t == null) throw new ParseException(
		 * "Problems in creating the unary expression for" + n); return t;
		 */
	}

	// ================================================= convertToDouble
	/**
	 * Returns the double for a FunctionTerm (Unary Expression with domain Real or
	 * Integer) or for a constant numeric term (real, integer, natural).
	 * 
	 * @throws ParseException
	 */

	public static double convertToDouble(Term ft) throws ParseException {
		double n = 0;

		// if ft is a constant numeric term, return its value
		if (ft instanceof RealTerm)
			return Double.parseDouble(((RealTerm) ft).getSymbol());
		if (ft instanceof IntegerTerm)
			return Double.parseDouble(((IntegerTerm) ft).getSymbol());
		if (ft instanceof NaturalTerm) {
			// Eliminate the suffix "n"
			String s = ((NaturalTerm) ft).getSymbol();
			return Double.parseDouble(s.substring(0, s.length() - 1));
		}
		// otherwise it must be a function term with domain Real or
		// Integer
		if (!(ft instanceof FunctionTerm))
			throw new ParseException(
					"Can convert only function terms like + constant or - constant, found  " + ft.getClass());
		// get the unary Expression
		String name = ((FunctionTerm) ft).getFunction().getName();
		if (!(((FunctionTerm) ft).getFunction().getArity() == 1 && (name.equals("plus") || name.equals("minus"))))
			throw new ParseException("Can convert only unary expressions + constant or - constant ");

		Term argument = ((FunctionTerm) ft).getArguments().getTerms().get(0);

		if (!(argument instanceof RealTerm) && !(argument instanceof IntegerTerm))
			throw new ParseException(
					"Can convert only unary expressions + REALTERM or - REALTERM or + INTEGERTERM - INTEGERTERM ");

		if (argument instanceof RealTerm)
			// REAL FUNCTION TERM
			n = Double.parseDouble(((RealTerm) argument).getSymbol());

		else if (argument instanceof IntegerTerm)
			// INTEGER FUNCTION TERM
			n = Double.parseDouble(((IntegerTerm) argument).getSymbol());
		if (name.equals("plus"))
			return n;
		else
			return -n;
	}

	// ================================================= createTermCollection
	/**
	 * firstElem is already in elemColl. This method put in the collection the
	 * remaining elements till lastElem, taking into account of the step and of the
	 * type. By default the step is 1. The interval notation is allowed only for
	 * reals, integer and natural numbers!
	 * 
	 * @throws ParseException
	 */
	public static void createTermCollection(Term firstElem, Term lastElem, Collection<Term> elemColl, double step,
			boolean natural_step, TermsFactory termsPack, HashMap<String, List<Function>> declared_Func,
			HashMap<String, Domain> declared_Dom, DefinitionsFactory defPack, Signature s) throws ParseException {

		double low = convertToDouble(firstElem);
		double upp = convertToDouble(lastElem);
		if (low > upp)
			throw new ParseException("Error: The last interval element cannot be greatest than the first one.");

		if (natural_step && (firstElem instanceof NaturalTerm))
		// Set of natural terms
		{
			if (!(lastElem instanceof NaturalTerm))
				throw new ParseException(
						"Error: The first interval element is a NaturalTerm so also the last one must be a NaturalTerm."
								+ "(the TypeDomain associated to every element of a set must be the same)");
			NaturalTerm naturalTerm;
			// look for the Natural domain package
			TypeDomain naturalDom = getBasicDomain(defPack.getDomains(), "Natural");
			if (naturalDom == null) {
				throw new ParseException("Error: The natural domain has not been declared.");
			}
			// put the elements
			logger.debug("\t\t\tInterval elements: " + ((NaturalTerm) firstElem).getSymbol());
			for (int i = (int) (low + step); i < (int) upp; i = (int) (i + step))// first
			// element
			// is
			// already
			// stored
			{ // create a new NaturalTerm
				naturalTerm = termsPack.getFurtherTerms().createNaturalTerm();
				naturalTerm.setSymbol(String.valueOf(i) + "n");
				// set references
				naturalTerm.setDomain(naturalDom);

				// link the created Natural term to the Sequence term
				elemColl.add(naturalTerm);
				// add a new association
				// X a_SetTerm_Term.add(term,naturalTerm);
				logger.debug(" " + naturalTerm.getSymbol());
			}
		} // End set of natural

		else if (natural_step && (firstElem.getDomain() instanceof IntegerDomain))
		// Set of integer terms
		{
			if (!(lastElem.getDomain() instanceof IntegerDomain))
				throw new ParseException(
						"Error: The first interval element is integer so also the last one must be integer."
								+ "(the TypeDomain associated to every element of a set must be the same!)");

			// Generate integer terms between first and last given elements and
			// insert them in the element list
			// Two cases: a IntegrTerm or an integer FunctionTerm (for integers
			// preceded by +plus and minus) are created.

			// look for the Integer domain reference in the predefined_Dom
			// HashMap
			TypeDomain integerDom = getBasicDomain(defPack.getDomains(), "Integer");
			if (integerDom == null) {
				throw new ParseException("Error: The integer domain has not been declared.");
			}

			// logger.debug("\t\t\tinterval elements:
			// "+((IntegerTerm)firstElem).getSymbol());
			for (int i = (int) (low + step); i < (int) upp; i = (int) (i + step))// first
			// element
			// is
			// already
			// stored
			{
				// link the created integer term to the Set term
				Term intToadd = convertIntegerToTerm(i, termsPack, integerDom, declared_Func, declared_Dom, defPack, s);
				elemColl.add(intToadd);
				// add a new association
				// X a_SetTerm_Term.add(term,integerTerm);
				logger.debug("i: " + i + " integer term " + intToadd.toString());
				// logger.debug(" "+integerTerm.getSymbol());
			}
		} // End set of integer
		else
		// Set of real terms

		if (firstElem.getDomain() instanceof RealDomain) {
			if (!(lastElem.getDomain() instanceof RealDomain))
				throw new ParseException("Error: The first interval element is real so also the last one must be real."
						+ "(The TypeDomain associated to every element of a set must be the same!)");
			// Generate real terms between first and last given elements and
			// insert them in the element list
			// Two cases: a RealTerm or a real FunctionTerm (for reals
			// preceded by +plus and minus) are created.

			// Look for the Real domain reference in the predefined_Dom
			// HashMap
			TypeDomain realDom = getBasicDomain(defPack.getDomains(), "Real");
			if (realDom == null) {
				throw new ParseException("Error: The real domain has not been declared.");
			}

			// logger.debug("\t\t\tinterval elements:
			// "+((RealTerm)firstElem).getSymbol());
			for (double i = low + step; i < upp; i = i + step)// first element
			// is
			// already stored
			{
				// link the created real term to the Set term
				elemColl.add(convertDoubleToTerm(i, termsPack, realDom, declared_Func, declared_Dom, defPack, s));
				// add a new association
				// X a_SetTerm_Term.add(term,realTerm);
				// logger.debug(" "+realTerm.getSymbol());
				logger.debug(" " + i);
			}
		} // End set of real

		else
			throw new ParseException("Error: The interval is ill formed!");

		// For all kind of sets, check if the lastElem must be included in the
		// set unless is equal to low (AG 31/7/18 for {1..1}
		if (low < upp && (((upp - low) % step) == 0)) {
			elemColl.add(lastElem);
			// add a new association
			// X a_SetTerm_Term.add(term,lastElem);
			logger.debug(" last element has been included!");
			// logger.debug("
			// "+((ConstantTerm)lastElem).getSymbol());
		}
		logger.debug("");

	}

	// NEW by Patrizia 25 Set 2006
	public static Domain fixTypeDomain(Domain td, HashMap<String, Domain> m, HashMap<String, Domain> declared_Dom,
			DefinitionsFactory defPack, Signature s) {
		// ProductDomain
		if (td instanceof ProductDomain) {
			EList<Domain> prodDomList = ((ProductDomain) td).getDomains();
			List<Domain> newlist = new LinkedList<Domain>();
			Iterator<Domain> iter = prodDomList.listIterator();
			while (iter.hasNext())
				newlist.add(fixTypeDomain(iter.next(), m, declared_Dom, defPack, s));
			return getProduct(newlist, defPack, s);
		}
		// PowersetDomain
		else if (td instanceof PowersetDomain)
			return getPowerset(fixTypeDomain(((PowersetDomain) td).getBaseDomain(), m, declared_Dom, defPack, s),
					defPack, s);
		// SequenceDomain
		else if (td instanceof SequenceDomain)
			return getSequence(fixTypeDomain(((SequenceDomain) td).getDomain(), m, declared_Dom, defPack, s), defPack,
					s);
		// BagDomain
		else if (td instanceof BagDomain)
			return getBag(fixTypeDomain(((BagDomain) td).getDomain(), m, declared_Dom, defPack, s), defPack, s);
		// MapDomain
		else if (td instanceof MapDomain)
			return getMap(fixTypeDomain(((MapDomain) td).getSourceDomain(), m, declared_Dom, defPack, s),
					fixTypeDomain(((MapDomain) td).getTargetDomain(), m, declared_Dom, defPack, s), defPack, s);
		else if (td instanceof AnyDomain && m.containsKey(td.getName())) {
			// return (TypeDomain) m.get(td.getName()); Da problemi!
			// Provo invece cosi':
			String dom_name = m.get(td.getName()).getName();
			Domain dom = null;
			try {
				dom = declared_Dom.get(dom_name);
				if (dom == null) {
					logger.debug("Trying predefined domain");
					dom = getPredefinedDomain(defPack.getDomains(), dom_name);
				}
				if (dom == null) {
					logger.debug("Trying structured domain");
					dom = getStructuredDomain(defPack.getDomains(), dom_name, s);
				}
				if (dom == null)
					throw new ParseException("Error: " + dom_name + " domain is not declared.");
			} catch (Exception e) {
				logger.error("Problems in fixing the type domain " + td.getName());
				logger.error("Exception: " + e.getMessage());
			}
			return dom;
		}

		return td;
	}

	// ////////////////////////////////////Function for creation of Structured
	// Type-Domain ////////////////////////////////////

	/* returns the rule domain parameterized for a list of domains */
	public static RuleDomain getRuleDomain(List<Domain> domainList, DefinitionsFactory defPack, Signature s) {
		String name = getRuleDomName(domainList);
		logger.debug("RuleDomain: " + name);
		// check if this structuredTD has been defined previously
		RuleDomain domReturned = (RuleDomain) getStructuredDomain(defPack.getDomains(), name, s);
		if (domReturned == null) // it is not present in the HashMap
		{ // create the object
			logger.debug("RuleDomain: " + name + " not present in the HashMap. It is now been created..");
			logger.debug("domainList: " + name + " of size: " + domainList.size());
			RuleDomain ruleDom;

			ruleDom = defPack.getDomains().createRuleDomain();
			ruleDom.setName(name);
			if (domainList.size() > 0)
				ruleDom.getDomains().addAll(domainList);
			// set the signature
			s.getStructuredDomain().add(ruleDom);
			return ruleDom;
		} else
			return domReturned;
	}

	/*
	 * returns the product domain of a list of domains
	 */
	public static ProductDomain getProduct(List<Domain> domainList, DefinitionsFactory defPack, Signature s) {
		String name = getDomName(domainList);

		// check if this structuredTD has been defined previously
		ProductDomain domReturned = (ProductDomain) getStructuredDomain(defPack.getDomains(), name, s);
		if (domReturned == null) // it is not present in the HashMap
		{ // create the object
			ProductDomain prodDom = defPack.getDomains().createProductDomain();
			prodDom.setName(name);
			prodDom.getDomains().addAll(domainList);
			logger.debug("domainList: " + name + " of size: " + domainList.size());
			// set the signature
			s.getStructuredDomain().add(prodDom);

			return prodDom;
		} else
			return domReturned;
	}

	public static PowersetDomain getPowerset(Domain dom, DefinitionsFactory defPack, Signature s) {
		String name = "Powerset(";
		name = name.concat(dom.getName());
		name = name.concat(")");
		// check if this structuredTD has been defined previously
		PowersetDomain powersetDom = (PowersetDomain) getStructuredDomain(defPack.getDomains(), name, s);
		if (powersetDom == null)// it is not present in the HashMap
		{ // create the object
			powersetDom = defPack.getDomains().createPowersetDomain();
			powersetDom.setName(name);
			// set references
			powersetDom.setBaseDomain(dom);
			// create and set associations
			// X APowersetDomain a_Powerset_Domain =
			// defPack.getAPowersetDomain();
			// X a_Powerset_Domain.add(powersetDom,dom);
			// insert it in the hashMap
			// structTD.put(name, powersetDom);
			// set the signature

			s.getStructuredDomain().add(powersetDom);
		}
		return powersetDom;
	}

	// OK!
	public static SequenceDomain getSequence(Domain dom, DefinitionsFactory defPack, Signature s) {
		String name = "Seq(";
		name = name.concat(dom.getName());
		name = name.concat(")");
		// check if this structuredTD has been defined previously
		SequenceDomain seqDom = (SequenceDomain) getStructuredDomain(defPack.getDomains(), name, s);
		if (seqDom == null)// it is not present in the HashMap
		{ // create the object
			seqDom = defPack.getDomains().createSequenceDomain();
			seqDom.setName(name);
			// set references
			seqDom.setDomain(dom);
			// set the signature
			s.getStructuredDomain().add(seqDom);
		}
		return seqDom;
	}

	// OK!
	public static BagDomain getBag(Domain dom, DefinitionsFactory defPack, Signature s) {
		String name = "Bag(";
		name = name.concat(dom.getName());
		name = name.concat(")");
		// check if this structuredTD has been defined previously
		BagDomain bagDom = (BagDomain) getStructuredDomain(defPack.getDomains(), name, s);
		if (bagDom == null)// it is not present in the HashMap
		{ // create the object
			bagDom = defPack.getDomains().createBagDomain();
			bagDom.setName(name);
			// set references
			bagDom.setDomain(dom);
			// set the signature
			s.getStructuredDomain().add(bagDom);
		}
		return bagDom;
	}

	// OK!
	/* builds a MapDomain dom1 -> dom2, which are typeDomain */
	public static MapDomain getMap(Domain dom1, Domain dom2, DefinitionsFactory defPack, Signature s) {
		String name = "Map(";
		name = name.concat(dom1.getName());
		name = name.concat(",");
		name = name.concat(dom2.getName());
		name = name.concat(")");
		// check if this structuredTD has been defined previously
		MapDomain mapDom = (MapDomain) getStructuredDomain(defPack.getDomains(), name, s);
		if (mapDom == null)// it is not present in the HashMap
		{ // create the object
			mapDom = defPack.getDomains().createMapDomain();
			mapDom.setName(name);
			// set references
			mapDom.setSourceDomain(dom1);
			mapDom.setTargetDomain(dom2);
			// set the signature
			s.getStructuredDomain().add(mapDom);

		}
		return mapDom;
	}

	/**
	 * Per generare il nome di un RuleDomain se la lista e' vuota restituisce
	 * "Rule", se la lista ha un solo elemento D restituisce D altrimenti restitiuce
	 * Rule(D1,...DN). A rule domain can be formed also by concrete domains.
	 */
	public static String getRuleDomName(List<Domain> domains) {
		String dom_Name = "Rule";
		if (domains == null || domains.size() == 0)
			return dom_Name;
		dom_Name = dom_Name.concat("(");
		Iterator<Domain> iter = domains.iterator();
		Domain d;
		while (iter.hasNext()) {
			d = iter.next();
			dom_Name = dom_Name.concat(d.getName()).concat(",");
		}
		dom_Name = dom_Name.substring(0, dom_Name.length() - 1); // delete
		// the
		// last comma
		dom_Name = dom_Name.concat(")");
		return dom_Name;
	}

	/**
	 * Per generare il nome di un product se la lista e' vuota restituisce "", se la
	 * lista ha un solo elemento D restituisce D altrimenti restitiuce
	 * Prod(D1,...DN). A product domain can now be formed also by concrete domains.
	 */
	public static <T extends Domain> String getDomName(List<T> domains) {
		String dom_Name = "";
		int size = domains.size();
		if (size == 0)
			return dom_Name;
		if (size > 1)
			dom_Name = dom_Name.concat("Prod(");
		Iterator<T> iter = domains.iterator();
		Domain d;
		while (iter.hasNext()) {
			d = iter.next();
			/*
			 * if (size > 1 && (d instanceof ConcreteDomain)) return null; // THIS NO LONGER
			 * APPLY: A product domain cannot be formed by concrete domains else
			 */
			dom_Name = dom_Name.concat(d.getName()).concat(",");
		}
		dom_Name = dom_Name.substring(0, dom_Name.length() - 1); // delete
		// the
		// last comma
		if (size > 1)
			dom_Name = dom_Name.concat(")");
		return dom_Name;

	}

	/**
	 * It creates a function term if the funcname function is dynamic, it returns a
	 * LocationTerm otherwise it returns a FunctionTerm
	 * 
	 * @param funcName functionName: it cannot be null
	 * @param tupleT   the tuple argument of the function: it can be null
	 */
	public static FunctionTerm createFunctionTerm(String funcName, TupleTerm tupleT, TermsFactory termsPack,
			HashMap<String, List<Function>> declared_Func, HashMap<String, Domain> declared_Dom,
			DefinitionsFactory defPack, Signature s)

	{
		logger.debug("\tcreating a location/function term for function " + funcName);
		// if the tuple is not null, it computes its Domain
		Domain tupleD = null;
		if (tupleT != null) { // set the reference
			tupleD = tupleT.getDomain();
		}

		// looks for the function
		Function func = null;
		HashMap<String, Domain> genericDomValue = new HashMap<String, Domain>();
		try {
			// NEW by Patrizia 25 Set 2006
			// Added parameter genericDomValue
			// *****
			func = getFunction(funcName, tupleD, true, genericDomValue, declared_Func);
			// *****
			if (func == null)
				throw new ParseException("Function " + funcName
						+ ((tupleT != null) ? ("(" + tupleD.getName() + ")") : "") + " has not been found");
			logger.debug("\t\t\tFound function " + func.getName()
					+ ((func.getDomain() != null) ? " with domain " + func.getDomain().getName() : ""));
		} catch (Exception e) {
			logger.error("Problems locating the function " + funcName
					+ ((tupleT != null) ? ("(" + tupleD.getName() + ")") : ""));
			logger.error("Exception: " + e.getMessage());
			logger.debug("tupleD has domain " + (tupleD != null ? tupleD.getName() : "") + " and ref "
					+ (tupleD != null ? tupleD.toString() : ""));
			// get the functions with the same name
			List<Function> f_list = declared_Func.get(funcName);
			if (f_list == null)
				logger.debug("No other function with the same name!");
			else {
				logger.debug("other functions with the same name have domains: ");
				for (Function f : f_list)
					logger.debug(f.getDomain().getName() + " ref: " + f.getDomain().toString());
			}
			return null;
		}
		// create the object
		FunctionTerm term;
		if (func instanceof DynamicFunction) {
			term = termsPack.getBasicTerms().createLocationTerm();
			logger.debug("\tcreating a location term");
		} else {
			// static function or derived
			term = termsPack.getBasicTerms().createFunctionTerm();
			logger.debug("\tcreating a function term");
		}
		if (tupleT != null) { // set the reference
			term.setArguments(tupleT);
		}
		// set the reference
		term.setFunction(func);

		// NEW by Patrizia 25 Set 2006
		logger.debug("Setting TypeDomain " + func.getCodomain().getName() + "...");
		Domain td;
		if (genericDomValue.isEmpty()) {
			// set Function term domain to the codomain of the function
			td = func.getCodomain();
		} else {
			td = fixTypeDomain(func.getCodomain(), genericDomValue, declared_Dom, defPack, s);
		}
		logger.debug("\t\t--> " + td.getName());

		// set the reference
		term.setDomain(td);

		// constraints 1-2 granted for construction
		// checks applicability
		logger.debug("Checking applicability...");
		// FIXME 1/4/2009 code commented
		if (!OCL_Checker.applicable(term)) {
			logger.fatal("not applicable! " + OCL_Checker.getMSG_ERR());
			return null;
		}
		return term;
	}

	/**
	 * Search a function f_name with domain d. Note that the list of declared
	 * functions must be traversed in reverse order!!!
	 * 
	 * @param f_name          the f_name
	 * @param d               the d
	 * @param withCompare     the with compare
	 * @param genericDomValue the generic dom value
	 * @param declared_Func   the declared_ func
	 * 
	 * @return the function
	 */
	public static Function getFunction(String f_name, Domain d, boolean withCompare,
			HashMap<String, Domain> genericDomValue, HashMap<String, List<Function>> declared_Func) {
		// Functions with the same name of the function we are looking for
		List<Function> f_list = declared_Func.get(f_name);
		if (f_list == null)
			return null;
		else {
			ListIterator<Function> iter = f_list.listIterator(f_list.size());
			Function func;
			while (iter.hasPrevious()) {
				func = iter.previous();
				Domain f_dom = func.getDomain();
				if (d == null && f_dom == null)
					return func;
				else if (d != null && f_dom != null) {
					// if func has domain compatible with d return funct
					// see OCL constraint T9
					// funct domain is self e d is other domain - which can be
					// undef
					// COMMENTATO MA DOVREBBE ANDARE BENE BY Angelo
					// però il compareFixing ha il trucco getFromID che compare
					// non
					// ha ...
					// if (OCL_Checker.compatible(f_dom,d)) return func;
					// metto alcuni casi particolari che non danno errore che
					// dovrebbero andare
					// in compatible
					// if they are equals

					// consider Any Domain with Type Domains
					// NEW by Patrizia 25 Set 06
					if (withCompare) {
						// //TODO Regression testing is necessary!!!
						if (OCL_Checker.compareFixingAnyDomain(f_dom, d, genericDomValue)) {
							logger.debug("\t\t getFunction " + f_name + " Domain " + d.getName() + " found ");
							return func;
						}
					} else if (equals(f_dom, d)
					// TODO Regression testing is necessary!!!
					// NEW! This commented line is to distinguish among
					// different concrete domains
					// ||
					// equals(OCL_Checker.getTypeDomain(f_dom),OCL_Checker.getTypeDomain(d))
					) {
						logger.debug("\t\t getFunction " + f_name + " Domain " + d.getName() + " found ");
						return func;
					}
				}
			}
			logger.debug(
					"\t\t getFunction " + f_name + " Domain " + (d != null ? "d.getName()" : " null") + " not found ");
			return null;
		}
	}

	// ////////////////////////////collections managements
	// //////////////////////////////

	public static RuleDeclaration search_ruleName(Collection<RuleDeclaration> collection, String obj_name) {
		return searchNameInCollection(collection, obj_name);
	}

	public static Function search_funcName(Collection<Function> collection, String obj_name) {
		return searchNameInCollection(collection, obj_name);
	}

	public static Domain search_domName(Collection<Domain> collection, String obj_name) {
		return searchNameInCollection(collection, obj_name);
	}

	/** search an named element in a collection */
	private static <T extends asmeta.structure.NamedElement> T searchNameInCollection(Collection<T> collection,
			String obj_name) {

		Iterator<T> iter = collection.iterator();
		while (iter.hasNext()) {
			T obj = iter.next();
			if (obj.getName().equals(obj_name))
				return obj;
		}
		return null;
	}

	// ////////////////////////////////////Declared-function table management
	// ////////////////////////////////////
	/**
	 * Insert a new Function in the function db and return true iff successful
	 * Function overloading is allowed on the base of the domain!
	 */
	public static boolean insert(Function f, HashMap<String, List<Function>> declared_Func) {
		String f_name = f.getName();
		List<Function> f_list = declared_Func.get(f_name);
		if (f_list == null) {
			f_list = new LinkedList<Function>();
			declared_Func.put(f_name, f_list);
			f_list.add(f);
			return true;
		} else {
			Domain d = f.getDomain();
			for (Function func : f_list) {
				if (func.getDomain() == d)
					return false;
			}
			f_list.add(f);
			return true;
		}
	}

	public static boolean remove(Function f, HashMap<String, List<Function>> declared_Func) {
		String f_name = f.getName();
		List<Function> f_list = declared_Func.get(f_name);
		if (f_list == null)
			return false;
		else {
			Domain d = f.getDomain();
			Function func;
			for (int count = 0; count < f_list.size(); count++) {
				func = f_list.get(count);
				if (OCL_Checker.getTypeDomain(func.getDomain()) == OCL_Checker.getTypeDomain(d)) {
					f_list.remove(count);
					return true;
				}
			}
			return false;
		}
	}

	/**
	 * insert a domain in the declared domain, return true if successfull false:
	 * already presented or null or structured, basic, Agent ....
	 * 
	 * @param d
	 * @param declared_Dom
	 * @return
	 */
	public static boolean insert(Domain d, HashMap<String, Domain> declared_Dom) {
		if (d == null)
			return false;
		String d_name = d.getName();
		if (declared_Dom.get(d_name) == null) {
			if (!(d instanceof StructuredTd || d instanceof BasicTd || d instanceof AgentDomain
					|| d instanceof ReserveDomain || d instanceof AnyDomain))
				// insert the domain in the table of declared domains
				declared_Dom.put(d_name, d);
			return true;
		}
		return false;
	}

	/** a seconda dell'operatore restituisce il nome */
	public static String getFunctionName(String operat) {
		if (operat.equals("-"))
			return "minus";
		if (operat.equals("^"))
			return "pwr";
		if (operat.equals("*"))
			return "mult";
		if (operat.equals("/"))
			return "div";
		if (operat.equals("mod"))
			return "mod";
		if (operat.equals("+"))
			return "plus";
		if (operat.equals("<"))
			return "lt";
		if (operat.equals("<="))
			return "le";
		if (operat.equals(">"))
			return "gt";
		if (operat.equals(">="))
			return "ge";
		if (operat.equals("="))
			return "eq";
		if (operat.equals("!="))
			return "neq";
		if (operat.equals("in"))
			return "in";
		if (operat.equals("notin"))
			return "notin";
		if (operat.equals("not"))
			return "not";
		if (operat.equals("and"))
			return "and";
		if (operat.equals("xor"))
			return "xor";
		if (operat.equals("or"))
			return "or";
		if (operat.equals("implies"))
			return "implies";
		if (operat.equals("iff"))
			return "iff";
		else
			return null;
	}

	/** binds the variable to its domain */
	public static void updateVariable(VariableTerm v, Domain inDom) {
		// set the domain of the variable
		v.setDomain(inDom);
		// nel caso sia un regola
		TypeDomain varTD = OCL_Checker.getTypeDomain(inDom);
		// o da confrontare ul inDom direttamente ???
		if (varTD instanceof RuleDomain)
			// update variable kind
			v.setKind(VariableKind.RULE_VAR);
	}

	/**
	 * binds a variable to a term: the term must have domain a PowersetDomain or a
	 * SequenceDomain like in SetT { $o | $o in X} -> X is a term the variable get
	 * the domain the base domain of the term
	 * 
	 * TODO TO BE COMPLETED WITH OTHER KIND OF DOMAIN ATTENZIONE lega la variabile
	 * al TypeDomain e non al domain nel caso di un concrete domain
	 * 
	 * @throws ParseException
	 */
	public static void updateVariable(VariableTerm v, Term inTerm) throws ParseException {
		// set the variable domain
		Domain D = null;
		// if the domain of interm is a conrecte, get its type domain
		Domain itD = OCL_Checker.getTypeDomain(inTerm.getDomain());
		// check which domain it is
		if (itD instanceof PowersetDomain)
			D = ((PowersetDomain) itD).getBaseDomain();
		else if (itD instanceof SequenceDomain)
			D = ((SequenceDomain) itD).getDomain();
		else if (itD instanceof BagDomain)
			D = ((BagDomain) itD).getDomain();
		else {
			throw new ParseException(
					"binding variable " + v.getName() + " to " + inTerm.getDomain().getName() + " not allowed");
		}
		v.setDomain(D);
		if (D instanceof RuleDomain)
			// update variable kind
			v.setKind(VariableKind.RULE_VAR);
	}

	public static StringTerm createStringT(String s, TermsFactory termsPack, DomainsFactory Dom) {
		// look for the String domain reference in the predefined_Dom HashMap
		TypeDomain dom = getBasicDomain(Dom, "String");
		if (dom == null)
			return null;

		// create the object
		StringTerm term = termsPack.getFurtherTerms().createStringTerm();
		term.setSymbol(s);
		// set references
		term.setDomain(dom);

		// constraint 1 is granted for construction

		return term;
	}

	/** createa a tuple with 1 argument */
	public static TupleTerm createSingle(Term arg1, TermsFactory termsPack, DefinitionsFactory defPack, Signature s) {

		return createPair(arg1, null, termsPack, defPack, s);
	}

	/**
	 * it creates a tuple with 2 arguments the second can be null; it builds the
	 * association
	 * 
	 * if the term is unique, the domain of the tuple is equal to the domain of the
	 * term
	 */
	public static TupleTerm createPair(Term arg1, Term arg2, TermsFactory termsPack, DefinitionsFactory defPack,
			Signature s) {
		TupleTerm term = termsPack.getBasicTerms().createTupleTerm();
		List<Domain> prodDomList = new LinkedList<Domain>();
		// set the list of terms as attribute of the tuple
		List<Term> elemList = term.getTerms();

		// add the first element to the terms list
		elemList.add(arg1);
		// the name of the tuple type-domain
		// TypeDomain elemTD = OCL_Checker.getTypeDomain(arg1.getDomain());
		Domain elemTD = arg1.getDomain();
		// add this type-domain to the list of the product domain
		prodDomList.add(elemTD);

		// add the second element to the terms list if it is a pair
		if (arg2 != null) {
			elemList.add(arg2);
			// the name of the tuple type-domain
			// elemTD = OCL_Checker.getTypeDomain(arg2.getDomain());
			elemTD = arg2.getDomain();
			// add this type-domain to the list of the product domain
			prodDomList.add(elemTD);
		}

		// (OCL constraints 1) set the tuple arity
		int arity = elemList.size();
		term.setArity(arity);

		// (OCL constraints 2) set the tuple Domain
		Domain dom;
		if (arity == 1)// the type-domain associated to the tuple term must be
			// equal to the one associated to its unique term
			// dom = OCL_Checker.getTypeDomain(arg1.getDomain());
			dom = arg1.getDomain();
		else
			// the type-domain associated to the tuple term must be a
			// Cartesian product domain
			dom = Utility.getProduct(prodDomList, defPack, s);
		term.setDomain(dom);
		OCL_Checker.checkTupleTerm(term);
		return term;
	}

	public static String print(Collection<Term> c) {
		Term elem;
		String toPrint = "(";
		Iterator<Term> iter = c.iterator();

		while (iter.hasNext()) {
			elem = iter.next();
			if (elem instanceof ConstantTerm)
				toPrint = toPrint.concat(((ConstantTerm) elem).getSymbol() + ",");
			else if (elem instanceof VariableTerm) {
				toPrint = toPrint.concat(((VariableTerm) elem).getName());
				Domain varInDom = ((VariableTerm) elem).getDomain();
				assert (varInDom != null);
				toPrint = toPrint.concat(" in ").concat(varInDom.getName());
				toPrint = toPrint.concat(",");
			} else
				toPrint = toPrint.concat("TERM,");
		}
		toPrint = toPrint.concat(")");
		return toPrint;
	}

	public static String print(Term t) {
		if (t instanceof ConstantTerm)
			return ((ConstantTerm) t).getSymbol();
		else if (t instanceof VariableTerm)
			return ((VariableTerm) t).getName();
		else
			return "Term";
	}

	/**
	 * given a Domain Factory, returns a basic domain with name name if name is not
	 * valid, return null
	 * 
	 * @throws ParseException in case the basic domain is not known
	 * 
	 */

	static Map<String, BasicTd> basicTds;

	public static asmeta.definitions.domains.BasicTd getBasicDomain(
			asmeta.definitions.domains.DomainsFactory domPackage, String name) {
		BasicTd bd = basicTds.get(name);
		if (bd == null) {
			bd = createBasicDomain(domPackage, name);
			if (bd == null)
				return null;
			basicTds.put(name, bd);
			bd.setName(name);
		}
		return bd;
	}

	public static asmeta.definitions.domains.BasicTd createBasicDomain(
			asmeta.definitions.domains.DomainsFactory domPackage, String name) {
		if (name.equals("Complex")) {
			return domPackage.createComplexDomain();
		} else if (name.equals("Real")) {
			return domPackage.createRealDomain();
		} else if (name.equals("Integer")) {
			return domPackage.createIntegerDomain();
		} else if (name.equals("Natural")) {
			return domPackage.createNaturalDomain();
		} else if (name.equals("Char")) {
			return domPackage.createCharDomain();
		} else if (name.equals("String")) {
			return domPackage.createStringDomain();
		} else if (name.equals("Boolean")) {
			return domPackage.createBooleanDomain();
		} else if (name.equals("Undef")) {
			return domPackage.createUndefDomain();
		}
		/*
		 * else if (name.equals("Rule")) { }
		 */// Adesso Rule e' un dominio strutturato! (vedi getRuleDomain())
		return null;
	}

	static Map<String, AbstractTd> abstractTds;

	/**
	 * get the AbstarctDomains agent and reserve if they exists, otherwise create
	 * them
	 */
	public static asmeta.definitions.domains.AbstractTd getPredefinedAbstractDomain(
			asmeta.definitions.domains.DomainsFactory domFactory, String name) {
		AbstractTd a = abstractTds.get(name);
		if (a == null) {
			logger.debug("Create predefined abstract domain");
			a = createPredefinedAbstractDomain(domFactory, name);
			abstractTds.put(name, a);
		} else {
			logger.debug("Abstract domain " + name + " found.");
		}
		return a;
	}

	public static asmeta.definitions.domains.AbstractTd createPredefinedAbstractDomain(
			asmeta.definitions.domains.DomainsFactory domFactory, String name) {
		AbstractTd result = null;
		if (name.equals("Agent")) {
			result = domFactory.createAgentDomain();
			result.setName("Agent");
			result.setIsDynamic(true);
		} else if (name.equals("Reserve")) {
			result = domFactory.createReserveDomain();
			result.setName("Reserve");
			result.setIsDynamic(true);
		}
		return result;
	}

	/**
	 * get the anydomain name, otherwise return null
	 */
	public static asmeta.definitions.domains.AnyDomain getAnyDomain(
			asmeta.definitions.domains.DomainsFactory domFactory, String name) {
		return DomainsFactory.eINSTANCE.getAnyDomain(name);
	}

	/** sobstitute the predefined domain hash map !!! */
	public static asmeta.definitions.domains.Domain getPredefinedDomain(
			asmeta.definitions.domains.DomainsFactory domFactory, String name) {
		Domain d = null;
		d = getBasicDomain(domFactory, name);
		// try predefined abstract Domain
		if (d == null) {
			logger.debug("Trying predifined abstract domain");
			d = getPredefinedAbstractDomain(domFactory, name);
		}
		// try any domains
		if (d == null) {
			logger.debug("Trying any domain");
			d = getAnyDomain(domFactory, name);
		}
		return d;
	}

	/** get the structured TD or a subclass with a given name */
	public static StructuredTd getStructuredDomain(DomainsFactory domFactory, String name, Signature s) {

		StructuredTd d = DomainsFactory.eINSTANCE.getStructuredTd(name);

		return d;
	}

	/**
	 * Given a name, returns the ruleDeclaration with that name, null if there are
	 * problems.
	 * 
	 * @param declared_Rules  all the declaration rules
	 * @param idElement
	 * @param actualParamList the list of domains of idElement: if it is null,
	 *                        return the only one in pratica posso fare l'import o
	 *                        ecport solo di una regola ... e idem per gli
	 *                        invarianti
	 * @throws ParseException
	 * 
	 */
	static RuleDeclaration getRuleByNameTerm(Map<String, List<RuleDeclaration>> declared_Rules, String idElement,
			List<Term> actualParamList) throws ParseException {
		List<Domain> lst = buildDomains(actualParamList);
		RuleDeclaration res = getRuleByNameDom(declared_Rules, idElement, lst);
		return res;
	}

	/**
	 * modified 7 Jan 2008 by acarioni
	 * 
	 * Given a list of terms, returns a list with the domains of the terms in the
	 * same order.
	 * 
	 * @param actualParamList a list of terms
	 * @return the list of the term domains
	 */
	static <T extends Term> List<Domain> buildDomains(List<T> actualParamList) {
		List<Domain> domains = new ArrayList<Domain>();
		for (Term term : actualParamList) {
			Domain dom = term.getDomain();
			domains.add(dom);
		}
		return domains;
	}

	/*
	 * Given a list of terms, returns a list with the domains of the terms in the
	 * same order. For concrete domains, the astract super domain is considered.
	 * 
	 * @param actualParamList a list of terms
	 * 
	 * @return the list of the term domains
	 */
	/*
	 * static <T extends Term> List<Domain> buildSuperDomains(List<T>
	 * actualParamList) { List<Domain> domains = new ArrayList<Domain>(); for (Term
	 * term : actualParamList) { Domain dom = term.getDomain(); if (dom instanceof
	 * ConcreteDomain) { ConcreteDomain cd = (ConcreteDomain)dom;
	 * domains.add(cd.getTypeDomain()); } else domains.add(dom); } return domains;
	 * }/*
	 * 
	 * /** added 10 Jan 2008 by acarioni
	 * 
	 * Given a RuleDomain, returns the list of domains belonging to it.
	 * 
	 * @param domain a RuleDomain
	 * 
	 * @return the list of domains
	 */
	@Deprecated
	static List<Domain> getList(RuleDomain domain) {
		List<Domain> lst = new ArrayList<Domain>();
		for (Domain dom : domain.getDomains()) {
			lst.add(dom);
		}
		return lst;
	}

	/**
	 * added 10 Jan 2008 by acarioni
	 * 
	 * Returns a string representation.
	 * 
	 * @param expected a list
	 * @return a string
	 */
	static <T extends Domain> String toString(Collection<T> expected) {
		StringBuffer s = new StringBuffer("(");
		for (Domain domain : expected) {
			s.append(domain.getName() + " ");
			// s.append("[" + ((Object) domain).toString() + "]");
		}
		s.append(")");
		return s.toString();
	}

	/** gives the string for the declared rules */
	static String toString(Map<String, List<RuleDeclaration>> declared_Rules) {
		StringBuffer s = new StringBuffer("{");
		for (String ruleName : declared_Rules.keySet()) {
			s.append(ruleName + " declarations:(");
			for (RuleDeclaration rd : declared_Rules.get(ruleName)) {
				s.append(toString(rd));
			}
			s.append(")");
		}
		s.append("}");
		return s.toString();
	}

	static String toString(RuleDeclaration rd) {
		StringBuffer sb = new StringBuffer();
		for (VariableTerm vt : rd.getVariable()) {
			sb.append(vt.getName() + " " + vt.getDomain().getName() + ",");

			// sb.append("[" + ((Object) vt.getDomain()).toString() + "]");

		}
		// sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * modified 8 Jan 2008 by acarioni, see the method resolve() below
	 * 
	 * @param declared_Rules
	 * @param idElement
	 * @param expDoms
	 * @return
	 * @throws ParseException
	 */
	static RuleDeclaration getRuleByNameDom(Map<String, List<RuleDeclaration>> declared_Rules, String idElement,
			List<Domain> expDoms) throws ParseException {

		return resolve(declared_Rules, idElement, expDoms);
	}

	/**
	 * added 7 Jan 2008 by acarioni, adapted from
	 * org.asmeta.simulator.StaticFunctionEvaluator.java
	 * 
	 * Given a macro name and the domains of the actual parameters, returns the
	 * macro declaration that best matches with the given one.
	 * 
	 * @param declared_Rules rules declared in the model
	 * @param name           macro name
	 * @param expDoms        domains of the actual parameters
	 * @return a macro declaration, null if none is found
	 * @throws ParseException if there are too macros compatible with the given
	 *                        declaration
	 * 
	 */
	static RuleDeclaration resolve(Map<String, List<RuleDeclaration>> declared_Rules, String name, List<Domain> expDoms)
			throws ParseException {
		StringBuilder sb = new StringBuilder();
		for (Domain domain : expDoms)
			sb.append(domain.getName() + " ");
		logger.debug("In resolve. Rule name: " + name + ". Expected domains: " + sb.toString());

		// get the rules with name equals to the parameter name
		List<RuleDeclaration> rulesByName = declared_Rules.get(name);
		if (rulesByName == null || rulesByName.size() == 0) {
			logger.debug("In resolve. No rule found.");
			return null;
		}
		// rating of the best candidate
		BigInteger bestRating = null;
		// index of the best candidate
		// bestIndex = -1 means that I have not found a candidate
		int bestIndex = -1;
		// if there is another candidate with the same rating of bestIndex,
		// bestIndex2 points to it
		int bestIndex2 = -1;
		for (int i = 0; i < rulesByName.size(); i++) {
			RuleDeclaration dcl = rulesByName.get(i);
			// get the domains of the formal parameters of the current candidate
			List<Domain> currDoms = buildDomains(dcl.getVariable());
			sb = new StringBuilder();
			for (Domain domain : currDoms)
				sb.append(domain.getName());
			logger.debug("In resolve. Domains of rule # " + i + ": " + sb.toString());
			// rating of the current candidate
			// a negative rating means the declarations are incompatibles
			// a positive one means compatibility
			// small is the rating, best is the compatibility
			// 0 means a perfect match
			BigInteger r = OCL_Checker.rating(expDoms, currDoms);
			if (r.compareTo(BigInteger.ZERO) < 0) {
				// declaration are not compatible
				continue;
			} else if (bestIndex == -1) {
				// found the first candidate
				bestRating = r;
				bestIndex = i;
			} else if (r.compareTo(bestRating) < 0) {
				// the current candidate is better than bestIndex
				// update bestRating and bestIndex
				bestRating = r;
				bestIndex = i;
				bestIndex2 = -1;
			} else if (r.compareTo(bestRating) == 0) {
				// found a candidate with the same rating of bestIndex
				bestIndex2 = i;
			}
		}
		if (bestIndex == -1) {
			// no candidate found
			return null;
		} /*
			 * FIXME 25/06/2010 by Fabio Albani commentato per supportare import multipli di
			 * una stessa regola per poi selezionare solo la prima
			 * 
			 * else if (bestIndex2 != -1) { // too many canditates found with the same
			 * rating throw new ParseException("ERROR: Unresolved reference " + name +
			 * " Too many compatible definitions: " + rulesByName.get(bestIndex) + ", " +
			 * rulesByName.get(bestIndex2)); }
			 */
		// PA 12/11/2011. Fabio Albani needs to import just the the first rule with
		// the best ranking. The static field "selectFirstBestRanking" can be used
		// to set this option; if "selectFirstBestRanking" is
		// - true, the first the first rule with the best ranking is selected;
		// - false, if there are at least two rules with the equal best ranking
		// an exception is raised.
		// By default, "selectFirstBestRanking" is true.
		if (!selectFirstBestRanking) {
			if (bestIndex2 != -1) {
				// too many canditates found with the same rating
				throw new ParseException("ERROR: Unresolved reference " + name + " Too many compatible definitions: "
						+ rulesByName.get(bestIndex) + ", " + rulesByName.get(bestIndex2));
			}
		}

		RuleDeclaration rdcl = rulesByName.get(bestIndex);
		return rdcl;
	}

	/**
	 * modified 8 Jan 2008 by acarioni
	 * 
	 * Given a macro name and a list of domains, returns the declaration of the
	 * macro that has the same name and the same domains as formal parameters.
	 * 
	 * @param declared_Rules  all the rules declared in the module
	 * @param idElement       a macro name
	 * @param actualParamList a list of domains
	 * @return the macro declaration that matches the name and the domains,
	 *         otherwise null
	 */
	static <T extends Term> RuleDeclaration searchRuleByName(Map<String, List<RuleDeclaration>> declared_Rules,
			String idElement, List<T> actualParamList) {
		/*
		 * commented 8 Jan 2008 by acarioni try { return
		 * getRuleByNameTerm(declared_Rules, idElement, actualParamList); } catch
		 * (ParseException e) {
		 * 
		 * return null; }
		 */
		try {
			List<Domain> givenDoms = buildDomains(actualParamList);
			RuleDeclaration dcl = getRuleByNameDom(declared_Rules, idElement, givenDoms);
			// dcl == null, if the macro is not found
			if (dcl == null) {
				return null;
			}
			List<Domain> returnedDoms = buildDomains(dcl.getVariable());
			BigInteger rating = OCL_Checker.rating(givenDoms, returnedDoms);
			// returns a value != null if and only if exists a macro with
			// identical declaration (i.e. rating == 0)
			return rating.compareTo(BigInteger.ZERO) == 0 ? dcl : null;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	// //////////////////////////////

	// // alcune funzioni di utilita' prese dall'inteprete (copiate e
	// incollate)
	// mia colpa AG

	/**
	 * Compares two domain declarations. quando non funziona == eusto dovrebbe
	 * funzionare andrebbe controllato perche' l'== non funziona correttamemte
	 * 
	 * @param d1 a domain
	 * @param d2 another domain
	 * @return true if the declarations are equal, false otherwise
	 */
	public static boolean equals(Domain d1, Domain d2) {
		// structured domains
		if (d1 instanceof ProductDomain) {
			if (!(d2 instanceof ProductDomain)) {
				return false;
			}
			List<?> lst1 = ((ProductDomain) d1).getDomains();
			List<?> lst2 = ((ProductDomain) d2).getDomains();
			if (lst1.size() != lst2.size()) {
				return false;
			}
			Iterator<?> i1 = lst1.iterator();
			Iterator<?> i2 = lst2.iterator();
			while (i1.hasNext()) {
				Domain dd1 = (Domain) i1.next();
				Domain dd2 = (Domain) i2.next();
				if (!equals(dd1, dd2)) {
					return false;
				}
			}
			return true;
		} else if (d1 instanceof PowersetDomain) {
			if (!(d2 instanceof PowersetDomain)) {
				return false;
			}
			Domain dd1 = ((PowersetDomain) d1).getBaseDomain();
			Domain dd2 = ((PowersetDomain) d2).getBaseDomain();
			return equals(dd1, dd2);
		} else if (d1 instanceof SequenceDomain) {
			if (!(d2 instanceof SequenceDomain)) {
				return false;
			}
			Domain dd1 = ((SequenceDomain) d1).getDomain();
			Domain dd2 = ((SequenceDomain) d2).getDomain();
			return equals(dd1, dd2);
		} else if (d1 instanceof BagDomain) {
			if (!(d2 instanceof BagDomain)) {
				return false;
			}
			Domain dd1 = ((BagDomain) d1).getDomain();
			Domain dd2 = ((BagDomain) d2).getDomain();
			return equals(dd1, dd2);
		} else if (d1 instanceof MapDomain) {
			if (!(d2 instanceof MapDomain)) {
				return false;
			}
			Domain sd1 = ((MapDomain) d1).getSourceDomain();
			Domain sd2 = ((MapDomain) d2).getSourceDomain();
			Domain td1 = ((MapDomain) d1).getTargetDomain();
			Domain td2 = ((MapDomain) d2).getTargetDomain();
			return equals(sd1, sd2) && equals(td1, td2);
		}
		// non-structured domains
		String name1 = d1.getName();
		String name2 = d2.getName();
		String moduleName1 = Defs.getAsmName(d1);
		String moduleName2 = Defs.getAsmName(d2);
		return name1.equals(name2) && moduleName1.equals(moduleName2);
	}

	/** reset the utility */
	public static void reset() {
		basicTds = new HashMap<String, BasicTd>();
		abstractTds = new HashMap<String, AbstractTd>();
	}

	/**
	 * read an import file it can be relative to the asmDirLib or absolute
	 * 
	 * @param asmDirLib    can be null, in case "." is taken or absolute is
	 *                     considered
	 * @param moduleName
	 * @param asmExtension
	 */
	static File importFile(String asmDirLib, String moduleName) {
		// it should compute again asmLibPath relatively the import ...??
		File res;
		if (asmDirLib == null) {
			// with asm extesion
			res = checkExistsAsmFile(moduleName + ASMParser.asmExtension);
			if (res != null)
				return res;
			// without asm extension
			res = checkExistsAsmFile(moduleName);
			if (res != null)
				return res;
			// with asm extesion
			res = checkExistsAsmFile("." + moduleName + ASMParser.asmExtension);
			if (res != null)
				return res;
			// without asm extension
			res = checkExistsAsmFile("." + moduleName);
			if (res != null)
				return res;
		}
		assert asmDirLib != null;
		// build the complete module name
		res = checkExistsAsmFile(asmDirLib + File.separatorChar + moduleName + ASMParser.asmExtension);
		if (res != null)
			return res;
		// without asm extension
		res = checkExistsAsmFile(asmDirLib + File.separatorChar + moduleName);
		if (res != null)
			return res;
		// check absolute also with asmDirLib != null
		res = checkExistsAsmFile(moduleName);
		if (res != null)
			return res;
		res = checkExistsAsmFile(moduleName + ASMParser.asmExtension);
		if (res != null)
			return res;
		//
		// ERROR !!!
		throw new ImportNotFoundException(asmDirLib, moduleName);
	}

	static private File checkExistsAsmFile(String fileName) {
		if (!fileName.endsWith(ASMParser.asmExtension))
			return null;
		File moduleFile = new java.io.File(fileName);
		if (moduleFile.exists() && moduleFile.isFile())
			return moduleFile;
		else
			return null;
	}
}
