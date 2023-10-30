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
import org.eclipse.emf.ecore.EObject;

import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.PowersetDomain;
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
import asmeta.terms.furtherterms.ExistUniqueTerm;
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
public class TermXMIVisitor extends ReflectiveVisitor {

	EList<EObject> resourceList;
	
	/**
	 * Fully qualified name flag.
	 * 
	 */
	protected boolean showAsmName;

	/**
	 * Creates a term printer.
	 * 
	 * @param showAsmName if true show the fully qualified name
	 */
	public TermXMIVisitor(boolean showAsmName,EList<EObject> resourceList){
		this.showAsmName = showAsmName;
		this.resourceList = resourceList;
	}
		
	public TermXMIVisitor(EList<EObject> resourceList){
				this.resourceList = resourceList;
	}
	
    /**
     * Visit a term adding it to the resource.
     * 
     * @param term a term
     * 
     */
    public String visit(Term term) {
    	 return (String) visit((Object) term);
    }

        
    public String visit(VariableTerm variable) {
        return variable.getName();
    }
       
    
    public String visit(TupleTerm tuple) {
		return tuple == null ? "" : visit(tuple.getTerms(), "(", ")");
    }
    
   
    public String visit(FunctionTerm function) {
           	
    	StringBuffer s = new StringBuffer();
        if(showAsmName) s.append(Defs.getAsmName(function.getFunction()) + "::");
        s.append(function.getFunction().getName());
        s.append(visit(function.getArguments()));
        
        return s.toString();
    }
  
    public String visit(LocationTerm location) {
    	return visit((FunctionTerm)location);
    }
	
  public String visit(ConditionalTerm cond) {
		
	  resourceList.add(cond.getGuard());
	  resourceList.add(cond.getThenTerm());
	  if ( cond.getElseTerm()!=null) resourceList.add(cond.getElseTerm());
	  
	  return "if " + visit(cond.getGuard()) 
		+ " then " + visit(cond.getThenTerm()) 
		+ (cond.getElseTerm() == null ? "" : " else " + visit(cond.getElseTerm())) 
		+ " endif";
	}
	
    
	public String visit(CaseTerm caseTerm) {
		
		resourceList.add(caseTerm.getComparedTerm());
		resourceList.addAll(caseTerm.getComparingTerm());
		
		StringBuffer s = new StringBuffer();
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
	
    
	public String visit(ExistTerm exist) {
		return visitQuant(exist, "exist");
	}

	
	public String visit(ExistUniqueTerm exist) {
		return visitQuant(exist, "exist unique");
	}

    
	public String visit(ForallTerm forall) {
		return visitQuant(forall, "forall");
	}
	
    /**
     * Converte in stringa un <i>FiniteQuantificationTerm</i>.
     * 
     * @param term termine da convertire
     * @param quantName "exist" o "forall"
     * @return la rappresentazione in stringa del termine
     */
    /**
     * Converts a finite quantification term into string.
     * 
	 * @param quantTerm a finite quantification term
	 * @param quantName "exist" or "forall"
	 * @return a string
	 */
	protected String visitQuant(FiniteQuantificationTerm quantTerm, String quantName) {
		resourceList.add(quantTerm.getGuard());
		
		StringBuffer s = new StringBuffer();
		s.append("(" + quantName + " ");
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
		if (guard != null) s.append(" with " + visit(guard));
		s.append(")");
		return s.toString();
	}
	
    /**
     * Converts a let term into string.
     * 
     * @param term a term
     * @return a string
     */
	public String visit(LetTerm let) {
		resourceList.addAll(let.getAssignmentTerm());
		resourceList.add(let.getBody());
		
		StringBuffer s = new StringBuffer();
		s.append("let(");
		Iterator<?>/*<VariableTerm>*/ varIt = let.getVariable().iterator();
		Iterator<?>/*<Term>*/ initIt = let.getAssignmentTerm().iterator();
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
		s.append(")in " + visit(let.getBody()) + " endlet");
		return s.toString();
	}
	
    /**
     * Converts a domain term into string.
     * 
     * @param term a term
     * @return a string
     */
	public String visit(DomainTerm term) {
		PowersetDomain dom = (PowersetDomain) term.getDomain();
		Domain base = dom.getBaseDomain();
		return (showAsmName? Defs.getAsmName(base) + "::" : "") + base.getName();
	}
	
    /**
     * Converts a set comprehension term into string.
     * 
     * @param term a term
     * @return a string
     */
	public String visit(SetCt ct) {
		return visitCt(ct, "{", "}");
	}
	
    /**
     * Converts a sequence comprehension term into string.
     * 
     * @param term a term
     * @return a string
     */
	public String visit(SequenceCt ct) {
		return visitCt(ct, "[", "]");
	}

    /**
     * Converts a bag comprehension term into string.
     * 
     * @param term a term
     * @return a string
     */
	public String visit(BagCt bt) {
		return visitCt(bt, "<", ">");
	}

    /**
     * Converts a comprehension term into string.
     * 
	 * @param ct a comprehension term
	 * @param startStr "{" or "["
	 * @param stopStr "}" or "]"
	 * @return a string
	 */
	protected String visitCt(ComprehensionTerm ct, String startStr, String stopStr) {
		//assert(ct.getTerm() != null);
		Term g = ct.getGuard();
		if (g!= null) resourceList.add(g);
		resourceList.add(ct.getTerm());
		
		StringBuffer s = new StringBuffer();
		s.append(startStr + visit(ct.getTerm()) + "|");
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
			s.append(" with " + visit(ct.getGuard()));
		}
		s.append(stopStr);
		return s.toString();
	}

    /**
     * Converts a set term into string.
     * 
     * @param term a term
     * @return a string
     */
	public String visit(SetTerm setTerm) {
		resourceList.addAll(setTerm.getTerm());
        return visit(setTerm.getTerm(), "{", "}");
    }
	
    /**
     * Converts a sequence term into string.
     * 
     * @param term a term
     * @return a string
     */
	public String visit(SequenceTerm sequence) {
        return visit(sequence.getTerms(), "[", "]");
    }

    /**
     * Converts a bag term into string.
     * 
     * @param term a term
     * @return a string
     */
	public String visit(BagTerm bag) {
		resourceList.addAll(bag.getTerm());
		return visit(bag.getTerm(), "<", ">");
    }

    /**
     * Converts a rule as term into string.
     * 
     * @param term a term
     * @return a string
     */
	public String visit(RuleAsTerm term) {
		return "<<" + (showAsmName? Defs.getAsmName(term.getRule()) 
				+ "::" : "") + term.getRule().getName() + ">>";
	}
	
    
	public String visit(NaturalTerm number) {
        return number.getSymbol();
    }
	
    /**
     * Converts an integer term into string.
     * 
     * @param term a term
     * @return a string
     */
	public String visit(IntegerTerm number) {
		resourceList.add(number);
        return number.getSymbol();
    }
	
    /**
     * Converts a real term into string.
     * 
     * @param term a term
     * @return a string
     */
	public String visit(RealTerm number) {
		resourceList.add(number);
        return number.getSymbol();
    }
	
    /**
     * Converts a boolean term into string.
     * 
     * @param term a term
     * @return a string
     */
	public String visit(BooleanTerm bool) {
        return bool.getSymbol();
    }
	
    /**
     * Converts a string term into string.
     * 
     * @param term a term
     * @return a string
     */
	public String visit(asmeta.terms.furtherterms.StringTerm st){
		return st.getSymbol();
	}
	
    /**
     * Converts an enumeration term into string.
     * 
     * @param term a term
     * @return a string
     */
	public String visit(EnumTerm enumTerm) {
    	return (showAsmName? Defs.getAsmName(enumTerm.getDomain()) 
    			+ "::" : "") + enumTerm.getSymbol();
    }
		
    /**
     * Converts a mapTerm into string.
     * 
     * @param term a term
     * @return a string
     */
	public String visit(asmeta.terms.furtherterms.MapTerm mapT){
		resourceList.addAll(mapT.getPair());
		return visit(mapT.getPair(),"{","}");
		
	}
    /**
     * Converts a collection of terms into string.
     * 
     * @param collection a collection
     * @param startStr e.g. "("
     * @param stopStr e.g. ")"
     * @return a string
     */
	public String visit(
			Collection<? extends Term> collection, String startStr, String stopStr) {
    	StringBuffer s = new StringBuffer();
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
     * @param term a term
     * @return a string
     */
	public String visit(UndefTerm undef) {
		return "undef";
	}
    
}
