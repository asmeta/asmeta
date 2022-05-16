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

/*
 * AsmetaTermPrinter.java
 *
 * Created on 26 giugno 2006, 10.47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.parser.util;

import java.util.Collection;
import java.util.Iterator;

import org.asmeta.parser.Defs;
import org.eclipse.emf.common.util.EList;

import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.RuleDomain;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.DomainTerm;
import asmeta.terms.basicterms.FunctionTerm;
import asmeta.terms.basicterms.LocationTerm;
import asmeta.terms.basicterms.RuleAsTerm;
import asmeta.terms.basicterms.SetTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.TupleTerm;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.terms.furtherterms.BagCt;
import asmeta.terms.furtherterms.BagTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.ComprehensionTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.ExistTerm;
import asmeta.terms.furtherterms.FiniteQuantificationTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.LetTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.RealTerm;
import asmeta.terms.furtherterms.SequenceCt;
import asmeta.terms.furtherterms.SequenceTerm;
import asmeta.terms.furtherterms.SetCt;

/**
 * A term printer.
 * 
 */
public class AsmetaTermPrinter extends ReflectiveVisitor<String> {

	
	private static AsmetaTermPrinter  asmetaTermPrinterWAsmName = new AsmetaTermPrinter(true);

	private static AsmetaTermPrinter  asmetaTermPrinterNoAsmName = new AsmetaTermPrinter(false);

	/**
	 * Gets the asmeta term printer. - to print the terms in a readable way
	 *
	 * @param showAsmName show the full qualified asm name
	 * @return the asmeta term printer
	 */
	public static AsmetaTermPrinter getAsmetaTermPrinter(boolean showAsmName) {
		return showAsmName ? asmetaTermPrinterWAsmName : asmetaTermPrinterNoAsmName;
	}

	/**
	 * Fully qualified name flag.
	 * 
	 */
	protected boolean showAsmName;

	/**
	 * Creates a term printer.
	 * 
	 * @param showAsmName
	 *            if true show the fully qualified name
	 */
	protected AsmetaTermPrinter(boolean showAsmName) {
		this.showAsmName = showAsmName;
	}

	/**
	 * Converts a term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(Term term) {
		return visit((Object) term);
	}

	/**
	 * Converts a variable term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(VariableTerm variable) {
		return variable.getName();
	}

	/**
	 * Converts a tuple term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(TupleTerm tuple) {
		return tuple == null ? "" : visit(tuple.getTerms(), "(", ")");
	}

	/**
	 * Converts a function term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(FunctionTerm function) {
		StringBuilder s = new StringBuilder();
		if (showAsmName)
			s.append(Defs.getAsmName(function.getFunction()) + "::");
		s.append(function.getFunction().getName());
		s.append(visit(function.getArguments()));
		return s.toString();
	}

	/**
	 * Converts a location term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(LocationTerm location) {
		return visit((FunctionTerm) location);
	}

	/**
	 * Converts a conditional term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(ConditionalTerm cond) {
		return "if " + visit(cond.getGuard()) + " then " + visit(cond.getThenTerm())
				+ (cond.getElseTerm() == null ? "" : " else " + visit(cond.getElseTerm())) + " endif";
	}

	/**
	 * Converts a case term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(CaseTerm caseTerm) {
		StringBuilder s = new StringBuilder();
		s.append("switch " + visit(caseTerm.getComparedTerm()));
		Iterator<Term> resultIt = caseTerm.getResultTerms().iterator();
		for (Term comp : caseTerm.getComparingTerm()) {
			Term result = resultIt.next();
			s.append(" case " + visit(comp) + ":" + visit(result));
		}
		if (caseTerm.getOtherwiseTerm() != null) {
			s.append(" otherwise " + visit(caseTerm.getOtherwiseTerm()));
		}
		s.append(" endswitch");
		return s.toString();
	}

	/**
	 * Converts an exist term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(ExistTerm exist) {
		return visitQuant(exist, "exist");
	}

	/**
	 * Converts a forall term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(ForallTerm forall) {
		return visitQuant(forall, "forall");
	}

	/**
	 * Converte in stringa un <i>FiniteQuantificationTerm</i>.
	 * 
	 * @param term
	 *            termine da convertire
	 * @param quantName
	 *            "exist" o "forall"
	 * @return la rappresentazione in stringa del termine
	 */
	/**
	 * Converts a finite quantification term into string.
	 * 
	 * @param quantTerm
	 *            a finite quantification term
	 * @param quantName
	 *            "exist" or "forall"
	 * @return a string
	 */
	protected String visitQuant(FiniteQuantificationTerm quantTerm, String quantName) {
		StringBuilder s = new StringBuilder();
		s.append("(" + quantName + " ");
		assert quantTerm.getVariable().size() > 0: quantTerm;
		Iterator<VariableTerm> variableIt = quantTerm.getVariable().iterator();
		Iterator<Term> rangeIt = quantTerm.getRanges().iterator();
		if (variableIt.hasNext()) {
			VariableTerm variable = variableIt.next();
			Term range = rangeIt.next();
			s.append(visit(variable) + " in " + visit(range));
			while (variableIt.hasNext()) {
				variable = variableIt.next();
				range = rangeIt.next();
				s.append("," + visit(variable) + " in " + visit(range));
			}
		}
		Term guard = quantTerm.getGuard();
		if (guard != null)
			s.append(" with " + visit(guard));
		s.append(")");
		//System.out.println(s.toString());
		return s.toString();
	}

	/**
	 * Converts a let term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(LetTerm let) {
		StringBuilder s = new StringBuilder();
		s.append("let(");
		Iterator<?>/* <VariableTerm> */ varIt = let.getVariable().iterator();
		Iterator<?>/* <Term> */ initIt = let.getAssignmentTerm().iterator();
		if (varIt.hasNext()) {
			VariableTerm var = (VariableTerm) varIt.next();
			Term init = (Term) initIt.next();
			s.append(visit(var) + "=" + visit(init));
			while (varIt.hasNext()) {
				var = (VariableTerm) varIt.next();
				init = (Term) initIt.next();
				s.append("," + visit(var) + "=" + visit(init));
			}
		}
		s.append(") in " + visit(let.getBody()) + " endlet");
		return s.toString();
	}

	/**
	 * Converts a domain term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(DomainTerm term) {
		PowersetDomain dom = (PowersetDomain) term.getDomain();
		Domain base = dom.getBaseDomain();
		return (showAsmName ? Defs.getAsmName(base) + "::" : "") + base.getName();
	}

	/**
	 * Converts a set comprehension term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(SetCt ct) {
		return visitCt(ct, "{", "}");
	}

	/**
	 * Converts a sequence comprehension term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(SequenceCt ct) {
		return visitCt(ct, "[", "]");
	}

	/**
	 * Converts a bag comprehension term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(BagCt bt) {
		return visitCt(bt, "<", ">");
	}

	/**
	 * Converts a comprehension term into string.
	 * 
	 * @param ct
	 *            a comprehension term
	 * @param startStr
	 *            "{" or "["
	 * @param stopStr
	 *            "}" or "]"
	 * @return a string
	 */
	protected String visitCt(ComprehensionTerm ct, String startStr, String stopStr) {
		assert (ct.getTerm() != null);
		StringBuilder s = new StringBuilder();
		s.append(startStr);
		Iterator<VariableTerm> variableIt = ct.getVariable().iterator();
		Iterator<Term> rangeIt = ct.getRanges().iterator();
		if (variableIt.hasNext()) {
			VariableTerm variable = variableIt.next();
			Term range = rangeIt.next();
			s.append(visit(variable) + " in " + visit(range));
			while (variableIt.hasNext()) {
				variable = variableIt.next();
				range = rangeIt.next();
				s.append("," + visit(variable) + " in " + visit(range));
			}
		}
		Term guard = ct.getGuard();
		if (guard != null) {
			s.append("| " + visit(ct.getGuard()) + " : ");
		}
		s.append(visit(ct.getTerm()));
		s.append(stopStr);
		return s.toString();
	}

	/**
	 * Converts a set term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(SetTerm setTerm) {
		return visit(setTerm.getTerm(), "{", "}");
	}

	/**
	 * Converts a sequence term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(SequenceTerm sequence) {
		return visit(sequence.getTerms(), "[", "]");
	}

	/**
	 * Converts a bag term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(BagTerm bag) {
		return visit(bag.getTerm(), "<", ">");
	}

	/**
	 * Converts a rule as term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(RuleAsTerm term) {
		Domain domain = term.getDomain();
		StringBuilder sb = new StringBuilder();
		if (domain != null) {
			assert domain instanceof RuleDomain;
			EList<Domain> domains = ((RuleDomain) domain).getDomains();
			if (domains.size()>0) {
				sb.append("(");
				// 	concatenate with comma
				for (int i = 0; i < domains.size() - 1; i++) {
					sb.append(domains.get(i).getName() + ", ");
				}
				sb.append(domains.get(domains.size() - 1).getName());
				// 		close
				sb.append(")");
			}
		}
		return "<<" + (showAsmName ? Defs.getAsmName(term.getRule()) + "::" : "") + term.getRule().getName()
				+ sb.toString() + ">>";
	}

	/**
	 * Converts a natural term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(NaturalTerm number) {
		return number.getSymbol();
	}

	/**
	 * Converts an integer term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(IntegerTerm number) {
		return number.getSymbol();
	}

	/**
	 * Converts a real term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(RealTerm number) {
		return number.getSymbol();
	}

	/**
	 * Converts a boolean term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(BooleanTerm bool) {
        return bool.getSymbol();
    }
	
	
	/**
	 * Converts a string term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(asmeta.terms.furtherterms.StringTerm st) {
		// string term does not contain the "" already
		// AG 2018/07/31
		assert !st.getSymbol().startsWith("\"");
		assert !st.getSymbol().endsWith("\"");
		// TODO it may contain a " that should be escaped in the output
		assert !st.getSymbol().contains("\"");
		return "\"" + st.getSymbol() + "\"";
	}

	/**
	 * Converts an enumeration term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(EnumTerm enumTerm) {
		return (showAsmName ? Defs.getAsmName(enumTerm.getDomain()) + "::" : "") + enumTerm.getSymbol();
	}

	/**
	 * Converts a mapTerm into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(asmeta.terms.furtherterms.MapTerm mapT) {
		StringBuilder s = new StringBuilder("{");
		EList<TupleTerm> pairs = mapT.getPair();
		if (pairs != null) {
			boolean first = true;
			for (TupleTerm pairTT : pairs) {
				if (first)
					first = false;
				else
					s.append(",");
				EList<Term> pair = pairTT.getTerms();
				s.append(visit(pair.get(0)));
				s.append("->");
				s.append(visit(pair.get(1)));
			}
		}
		s.append("}");
		return s.toString();
	}

	/**
	 * Converts a collection of terms into string.
	 * 
	 * @param collection
	 *            a collection
	 * @param startStr
	 *            e.g. "("
	 * @param stopStr
	 *            e.g. ")"
	 * @return a string
	 */
	public String visit(Collection<? extends Term> collection, String startStr, String stopStr) {
		StringBuilder s = new StringBuilder();
		s.append(startStr);
		if (collection != null) {
			Iterator<? extends Term> iterator = collection.iterator();
			if (iterator.hasNext()) {
				Term term = iterator.next();
				s.append(visit(term));
				while (iterator.hasNext()) {
					term = iterator.next();
					s.append("," + visit(term));
				}
			}
		}
		s.append(stopStr);
		return s.toString();
	}

	/**
	 * Converts an undefined term into string.
	 * 
	 * @param term
	 *            a term
	 * @return a string
	 */
	public String visit(UndefTerm undef) {
		return "undef";
	}
}
