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
 * RulePrinter.java
 *
 * Created on 27 giugno 2006, 18.04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.parser.util;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.VariableTerm;
import asmeta.transitionrules.basictransitionrules.BlockRule;
import asmeta.transitionrules.basictransitionrules.ChooseRule;
import asmeta.transitionrules.basictransitionrules.ConditionalRule;
import asmeta.transitionrules.basictransitionrules.ExtendRule;
import asmeta.transitionrules.basictransitionrules.ForallRule;
import asmeta.transitionrules.basictransitionrules.LetRule;
import asmeta.transitionrules.basictransitionrules.MacroCallRule;
import asmeta.transitionrules.basictransitionrules.Rule;
import asmeta.transitionrules.basictransitionrules.SkipRule;
import asmeta.transitionrules.basictransitionrules.TermAsRule;
import asmeta.transitionrules.basictransitionrules.UpdateRule;
import asmeta.transitionrules.derivedtransitionrules.CaseRule;
import asmeta.transitionrules.derivedtransitionrules.IterativeWhileRule;
import asmeta.transitionrules.derivedtransitionrules.RecursiveWhileRule;
import asmeta.transitionrules.turbotransitionrules.SeqRule;
import asmeta.transitionrules.turbotransitionrules.TryCatchRule;
import asmeta.transitionrules.turbotransitionrules.TurboCallRule;
import asmeta.transitionrules.turbotransitionrules.TurboReturnRule;

/**
 * A rule printer.
 * 
 */
public class RuleXMIVisitor extends TermXMIVisitor {
        	
	/** 
	 * Creates a rule printer.
	 * 
	 * @param showAsmName if true show the fully qualified name
	 */
	public RuleXMIVisitor(boolean showAsmName,EList<EObject> resourceList){
		super(showAsmName,resourceList);		
	}
	
	public RuleXMIVisitor(EList<EObject> resourceList){
		super(resourceList);		
	}
    /**
     * Converts a rule into string.
     * 
     * @param rule a rule
     * @return a string
     */
    public String visit(Rule rule) {
        return (String) visit((Object) rule);
    }

    /**
     * Converts a skip rule into string.
     * 
     * @param rule a rule
     * @return a string
     */
    public String visit(SkipRule skipRule) {
        return "skip";
    }
    
    /**
     * Converts an update rule into string.
     * 
     * @param rule a rule
     * @return a string
     */
    public String visit(UpdateRule updateRule) {
        Term leftHandSide;
        leftHandSide = updateRule.getLocation();
        
        resourceList.add(updateRule.getLocation());
    	resourceList.add(updateRule.getUpdatingTerm());
    	
        return visit(leftHandSide) + ":=" + visit(updateRule.getUpdatingTerm());
    }
    
    /**
     * Converts a block rule into string.
     * 
     * @param rule a rule
     * @return a string
     */
    public String visit(BlockRule blockRule) {
    	    	
    	StringBuffer s = new StringBuffer();
        s.append("par ");
        for (Rule rule : blockRule.getRules()) {
            s.append(visit(rule) + " ");
        }
        s.append("endpar");
        return s.toString();
    }
    
    /**
     * Converts a sequence rule into string.
     * 
     * @param rule a rule
     * @return a string
     */
    public String visit(SeqRule seqRule) {
        StringBuffer s = new StringBuffer();
        s.append("seq ");
        for (Rule rule : seqRule.getRules()) {
            s.append(visit(rule) + " ");
        }
        s.append("endseq");
        return s.toString();
    }
    
    /**
     * Converts a term as rule into string.
     * 
     * @param rule a rule
     * @return a string
     */
    public String visit(TermAsRule r) {
    	
    	resourceList.add(r);
    	resourceList.add(r.getTerm());
    	//visit(r.getTerm());    
    	String termStr = visit(r.getTerm());
		
    	
    	EList<Term> params = r.getParameters();
    	//resourceList.addAll(params); //NO, � un attributo di r
    	
    	String paramsStr ="";
		if (params!=null && params.size() > 0){ 
			paramsStr = visit(params, "[", "]");
			
		}
    	return termStr + paramsStr;
    }
    
    /**
     * Converts a conditional rule into string.
     * 
     * @param rule a rule
     * @return a string
     */
    public String visit(ConditionalRule condRule) {
       
    	resourceList.add(condRule.getGuard());
    	
    	String guard = visit(condRule.getGuard());
        String thenRule = visit(condRule.getThenRule());
        String elseRule = (condRule.getElseRule() != null) ? (" else " + visit(condRule.getElseRule())) : "";
        return "if " + guard + " then " + thenRule + elseRule + " endif";
    }
    
    /**
     * Converts a case rule into string.
     * 
     * @param rule a rule
     * @return a string
     */
	public String visit(CaseRule caseRule) {
		
		resourceList.add(caseRule.getTerm());
    	resourceList.addAll(caseRule.getCaseTerm());
		
		StringBuffer s = new StringBuffer();
		s.append("switch " + visit(caseRule.getTerm()));
		Iterator<Rule> branchIt = caseRule.getCaseBranches().iterator();
		for (Term comp : caseRule.getCaseTerm()) {
			Rule branch = branchIt.next();
			s.append(" case " + visit(comp) + ":" + visit(branch));
		}
		if (caseRule.getOtherwiseBranch() != null) {
			s.append(" otherwise " + visit(caseRule.getOtherwiseBranch()));
		}
		s.append(" endswitch");
		return s.toString();
	}
	
    /**
     * Converts an extend rule into string.
     * 
     * @param rule a rule
     * @return a string
     */
	public String visit(ExtendRule extendRule) {
		return "extend " + extendRule.getExtendedDomain().getName() +
			visit(extendRule.getBoundVar(), " with ", " do ") + 
			visit(extendRule.getDoRule());
	}
    
    /**
     * Converts a let rule into string.
     * 
     * @param rule a rule
     * @return a string
     */
    public String visit(LetRule letRule) {
    	resourceList.addAll(letRule.getInitExpression());
    	resourceList.addAll(letRule.getVariable());
    	
        StringBuffer s = new StringBuffer();
        s.append("let(");
        Iterator<?>/*<VariableTerm>*/ variableIterator = letRule.getVariable().iterator();
        Iterator<?>/*<Term>*/ termIterator = letRule.getInitExpression().iterator();
        String variable = ((VariableTerm) variableIterator.next()).getName();
        String term = visit(((Term) termIterator.next()));
        s.append(variable + "=" + term);
        while (variableIterator.hasNext()) {
            variable = ((VariableTerm) variableIterator.next()).getName();
            term = visit(((Term) termIterator.next()));
            s.append("," + variable + "=" + term);
        }
        s.append(")in ");
        String rule = visit(letRule.getInRule());
        s.append(rule);
        s.append(" endlet");
        return s.toString();
    }
    
    /**
     * Converts a forall rule into string.
     * 
     * @param rule a rule
     * @return a string
     */
    public String visit(ForallRule forRule) {
    	
    	resourceList.add(forRule.getGuard());
    	resourceList.addAll(forRule.getVariable());
    	
        StringBuffer s = new StringBuffer();
        s.append("forall ");
        Iterator<?>/*<VariableTerm>*/ variableIterator = forRule.getVariable().iterator();
        Iterator<?>/*<Term>*/ domaTermsiteartor = forRule.getRanges().iterator();
        VariableTerm variableTerm = (VariableTerm) variableIterator.next();
        String variable = variableTerm.getName();
        String term = visit(((Term) domaTermsiteartor.next()));
        s.append(variable + " in " + term);
        while (variableIterator.hasNext()) {
            variableTerm = (VariableTerm) variableIterator.next();
            variable = variableTerm.getName();
            term = visit(((Term) domaTermsiteartor.next()));
            s.append("," + variable + " in " + term);
        }
        s.append(" with " + ((forRule.getGuard() != null) ? visit(forRule.getGuard()) : "true"));
        s.append(" do " + visit(forRule.getDoRule()));
        return s.toString();
    }
    
    /**
     * Converts a choose rule into string.
     * 
     * @param rule a rule
     * @return a string
     */
    public String visit(ChooseRule chooseRule) {
    	resourceList.add(chooseRule.getGuard());
    	resourceList.addAll(chooseRule.getVariable());
    	
        StringBuffer s = new StringBuffer();
        s.append("choose ");
        Iterator<?>/*<VariableTerm>*/ variableIterator = chooseRule.getVariable().iterator();
        Iterator<?>/*<Term>*/ domaTermsiteartor = chooseRule.getRanges().iterator();
        VariableTerm variableTerm = (VariableTerm) variableIterator.next();
        String variable = variableTerm.getName();
        String term = visit(((Term) domaTermsiteartor.next()));
        s.append(variable + " in " + term);
        while (variableIterator.hasNext()) {
            variableTerm = (VariableTerm) variableIterator.next();
            variable = variableTerm.getName();
            term = visit(((Term) domaTermsiteartor.next()));
            s.append("," + variable + " in " + term);
        }
        s.append(" with " + visit(chooseRule.getGuard()));
        s.append(" do " + visit(chooseRule.getDoRule()));
        if (chooseRule.getIfnone() != null) {
            s.append(" ifnone " + visit(chooseRule.getIfnone()));
        }
        return s.toString();
    }
    
    /**
     * Converts a macro call rule into string.
     * 
     * @param rule a rule
     * @return a string
     */
    public String visit(MacroCallRule macro) {
    	return (showAsmName ? (Defs.getAsmName(macro.getCalledMacro())  + "::") : "") 
    	+ macro.getCalledMacro().getName() + visit(macro.getParameters(), "[", "]");
    }
    
    public String visit(TurboCallRule turbo) {
    	return (showAsmName ? (Defs.getAsmName(turbo.getCalledRule())  + "::") : "") 
    	+ turbo.getCalledRule().getName() + visit(turbo.getParameters(), "(", ")");
    }
    
    public String visit(TurboReturnRule retRule) {
    	
    	 resourceList.add(retRule.getLocation());
     	 resourceList.add(retRule.getUpdateRule());
    	return visit(retRule.getLocation()) + "<-" + visit(retRule.getUpdateRule());
    }
    
    public String visit(TryCatchRule r) {
    	
   	     resourceList.addAll(r.getLocation());
    	 
   	  StringBuffer s = new StringBuffer();
      s.append(" try ");
      s.append(visit(r.getTryRule()));
      s.append(" catch ");
      
      Iterator<?>/*<Term>*/ termIterator = r.getLocation().iterator();
      String term = visit(((Term) termIterator.next()));
      s.append(term);
      
      while (termIterator.hasNext()) {
          term = visit(((Term) termIterator.next()));
          s.append("," + term);
      }
      
      s.append(visit(r.getCatchRule()));
       return s.toString();
   }
    
    public String visit(IterativeWhileRule r) {
    	
  	   resourceList.add(r.getGuard());
  	   String guard = visit(r.getGuard());
       String rule = visit (r.getRule());
       return "while " + guard + " do " + rule; 
  }
    
    public String visit(RecursiveWhileRule r) {
    	
   	   resourceList.add(r.getGuard());
   	   String guard = visit(r.getGuard());
        String rule = visit (r.getRule());
        return "whilerec " + guard + " do " + rule;
   	 
   }
    
}
