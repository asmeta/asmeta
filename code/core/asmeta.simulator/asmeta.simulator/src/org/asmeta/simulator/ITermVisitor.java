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
 * TermEvaluator.java
 *
 * Created on 23 maggio 2006, 13.25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.asmeta.simulator;

import org.asmeta.simulator.value.TupleValue;

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
import asmeta.terms.furtherterms.BagTerm;
import asmeta.terms.furtherterms.CaseTerm;
import asmeta.terms.furtherterms.ConditionalTerm;
import asmeta.terms.furtherterms.EnumTerm;
import asmeta.terms.furtherterms.ExistTerm;
import asmeta.terms.furtherterms.ForallTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.LetTerm;
import asmeta.terms.furtherterms.MapTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.RealTerm;
import asmeta.terms.furtherterms.SequenceCt;
import asmeta.terms.furtherterms.SequenceTerm;
import asmeta.terms.furtherterms.SetCt;
import asmeta.terms.furtherterms.StringTerm;

/**
 * visitor of terms.
 * 
 */
interface ITermVisitor<T> {

	public T visit(Term term);
	/**
	 * Evaluates a variable term.
	 *  
	 * @param term a variable term
	 * @return term's value
	 */
	public T visit(VariableTerm var);
	/**
	 * Evaluates a tuple term.
	 *  
	 * @param term a tuple term
	 * @return term's value
	 */
	public TupleValue visit(TupleTerm tuple);

	/**
	 * Evaluates a function term.
	 *  
	 * @param term a function term
	 * @return term's value
	 */
	public T visit(FunctionTerm functionTerm);

	/**
	 * Evaluates a location term.
	 *  
	 * @param term a location term
	 * @return term's value
	 */
	public T visit(LocationTerm functionTerm);


   /**
	 * Evaluates a conditional term.
	 *  
	 * @param term a conditional term
	 * @return term's value
	 */
	public T visit(ConditionalTerm condTerm);

	/**
	 * Evaluates a case term.
	 *  
	 * @param term a case term
	 * @return term's value
	 */
	public T visit(CaseTerm caseTerm);

	/**
	 * Evaluates a domain term.
	 *  
	 * @param term a domain term
	 * @return term's value
	 */
	public T visit(DomainTerm domainTerm);


	/**
	 * Evaluates an enumeration term.
	 *  
	 * @param term an enumeration term
	 * @return term's value
	 */
	public T visit(EnumTerm enumTerm);

	/**
	 * Evaluates a let term.
	 *  
	 * @param term a let term
	 * @return term's value
	 */
	public T visit(LetTerm letTerm);

	/**
	 * Evaluates an exist term.
	 *  
	 * @param term an exist term
	 * @return term's value
	 */
	public T visit(ExistTerm existTerm);


	/**
	 * Evaluates a forall term.
	 *  
	 * @param term a forall term
	 * @return term's value
	 */
	public T visit(ForallTerm forTerm);


	/**
	 * Evaluates a set comprehension term.
	 *  
	 * @param term a set comprehension term
	 * @return term's value
	 */
	public T visit(SetCt setCt);

	/**
	 * Evaluates a sequence comprehension term.
	 *  
	 * @param term a sequence comprehension term
	 * @return term's value
	 */
	public T visit(SequenceCt seqCt);


	/**
	 * Evaluates a set term.
	 *  
	 * @param term a set term
	 * @return term's value
	 */
	public T visit(SetTerm setTerm);
	
	/**
	 * Evaluates a map term.
	 *  
	 * @param term a map term
	 * @return term's value
	 */
	public T visit(MapTerm mapTerm);

	/**
	 * Evaluates a bag term.
	 *  
	 * @param term a bag term
	 * @return term's value
	 */
	public T visit(BagTerm bagTerm);

	/**
	 * Evaluates a sequence term.
	 *  
	 * @param term a sequence term
	 * @return term's value
	 */
	public T visit(SequenceTerm sequence);

	/**
	 * Evaluates a natural term.
	 *  
	 * @param term a natural term
	 * @return term's value
	 */
	public T visit(NaturalTerm numericTerm);

	/**
	 * Evaluates an integer term.
	 *  
	 * @param term an integer term
	 * @return term's value
	 */
	public T visit(IntegerTerm numericTerm);

	/**
	 * Evaluates a real term.
	 *  
	 * @param term a real term
	 * @return term's value
	 */
	public T visit(RealTerm numericTerm);

	/**
	 * Evaluates a boolean term.
	 *  
	 * @param term a boolean term
	 * @return term's value
	 */
	public T visit(BooleanTerm booleanTerm);

	/**
	 * Evaluates a string term.
	 *  
	 * @param term a string term
	 * @return term's value
	 */
	public T visit(StringTerm string);

	/**
	 * Evaluates an undefined term.
	 *  
	 * @param term an undefined term
	 * @return term's value
	 */
	public T visit(UndefTerm undef);

	/**
	 * Evaluates a rule as term.
	 *  
	 * @param term a rule as term
	 * @return term's value
	 */
	public T visit(RuleAsTerm rule);

}
