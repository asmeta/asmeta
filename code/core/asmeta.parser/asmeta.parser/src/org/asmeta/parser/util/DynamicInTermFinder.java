package org.asmeta.parser.util;

import java.util.List;

import org.apache.log4j.Logger;
import org.asmeta.parser.OCL_Checker;
import org.eclipse.emf.ecore.EObject;

import asmeta.definitions.Function;
import asmeta.definitions.StaticFunction;
import asmeta.definitions.domains.ConcreteDomain;
import asmeta.definitions.domains.Domain;
import asmeta.definitions.domains.PowersetDomain;
import asmeta.definitions.domains.ProductDomain;
import asmeta.definitions.domains.SequenceDomain;
import asmeta.definitions.domains.StructuredTd;
import asmeta.terms.basicterms.BooleanTerm;
import asmeta.terms.basicterms.ConstantTerm;
import asmeta.terms.basicterms.Term;
import asmeta.terms.basicterms.UndefTerm;
import asmeta.terms.furtherterms.IntegerTerm;
import asmeta.terms.furtherterms.NaturalTerm;
import asmeta.terms.furtherterms.RealTerm;
import asmeta.terms.furtherterms.StringTerm;

// return the functions that are not static in a term
// it fills the list given in the constructor
public class DynamicInTermFinder extends ReflectiveVisitor<Void> {
	
	static final Logger logger = Logger.getLogger(DynamicInTermFinder.class); 

	private List<EObject> dynamicEntities;

	// parameter dynamicEntities: fill with synmiac entites
	public DynamicInTermFinder(List<EObject> dynamicEntities) {
		this.dynamicEntities = dynamicEntities;
	}

	public void visit(asmeta.terms.basicterms.FunctionTerm ft) {
		// get the function
		Function fun = ft.getFunction();
		if (!(fun instanceof StaticFunction)) {
			dynamicEntities.add(fun);
		}
		if (ft.getArguments() != null)
			for (Term t : ft.getArguments().getTerms()) {
				visit(t);
			}
	}

	public void visit(asmeta.terms.basicterms.LocationTerm lt) {
		visit((asmeta.terms.basicterms.FunctionTerm) lt);
	}
	
	public void visit(asmeta.terms.basicterms.VariableTerm vt) {
		logger.debug("VariableTerm " + vt + ":" + vt.getDomain().getName() + " " + isDynamic(vt.getDomain()));
		// check the domain of the vt
		visit(vt.getDomain());
		logger.debug(" dynamic entities " + dynamicEntities);
	}

	public void visit(Domain domain) {
		if (isDynamic(domain)) {
			dynamicEntities.add(domain);
		}
	}
	
	private boolean isDynamic(Domain domain) {
		if (OCL_Checker.isDomainDynamic(domain)) {
			return true;
		}
		if (domain instanceof StructuredTd) {
			if (domain instanceof PowersetDomain pd) {
				return OCL_Checker.isDomainDynamic(pd);
			} 
			if (domain instanceof SequenceDomain sd) {
				return OCL_Checker.isDomainDynamic(sd.getDomain());				
			}
			if (domain instanceof ProductDomain pd) {
				return OCL_Checker.isDomainDynamic(pd);
			}
			throw new RuntimeException("NOT implemented " + domain.getClass());
		}
		return false;
	}
	
		
	public void visit(asmeta.terms.furtherterms.ConditionalTerm ct) {
		visit(ct.getGuard());
		visit(ct.getThenTerm());
		visit(ct.getElseTerm());		
	}

	public void visit(ConstantTerm ct) {
		// do nothig
	}
	public void visit(asmeta.terms.furtherterms.EnumTerm et) {
		visit((ConstantTerm) et);
	}

	public void visit(BooleanTerm bt) {
		visit((ConstantTerm) bt);
	}

	public void visit(IntegerTerm it) {
		visit((ConstantTerm) it);
	}

	public void visit(NaturalTerm it) {
		visit((ConstantTerm) it);
	}

	public void visit(RealTerm it) {
		visit((ConstantTerm) it);
	}

	public void visit(StringTerm it) {
		visit((ConstantTerm) it);
	}

	public void visit(asmeta.terms.furtherterms.CaseTerm ct) {
		visit(ct.getComparedTerm());
		visitTerms(ct.getComparingTerm());
		visitTerms(ct.getResultTerms()); 
		if (ct.getOtherwiseTerm()!= null)
			visit(ct.getOtherwiseTerm());
	}
	public void visit(asmeta.terms.basicterms.TupleTerm tt) {
		visitTerms(tt.getTerms());
	}

	public void visit(asmeta.terms.furtherterms.SequenceTerm st) {
		visitTerms(st.getTerms());
	}
	
		
	public void	visit(asmeta.terms.furtherterms.LetTerm lt) {
		visitTerms(lt.getVariable());
		visitTerms(lt.getAssignmentTerm());
		visit(lt.getBody());
	}
	
	public void visit(asmeta.terms.furtherterms.SetCt st) {
		visitTerms(st.getVariable());
		visit(st.getGuard());
		visitTerms(st.getRanges());
		visit(st.getTerm());
	}
	
	// mapterm: { 123456 -> 1234, 234567 -> 2345, 345678 -> 3456}
	public void  visit(asmeta.terms.furtherterms.MapTerm map) {
		visitTerms(map.getPair());
	}
	// bagterm < t1,...,tn >
	public void  visit(asmeta.terms.furtherterms.BagTerm bag) {
		visitTerms(bag.getTerm());
	}
	
	public void  visit(asmeta.terms.furtherterms.BagCt bag) {
		visitTerms(bag.getRanges());
		visit(bag.getTerm());
	}
	
	// example:
	// function invoicable($so in Powerset(Orders)) = 
	//			( forall $o in $so with orderState($o) = PENDING)  
	public void  visit(asmeta.terms.furtherterms.ForallTerm ft) {
		visitTerms(ft.getVariable());
		visit(ft.getGuard());
	}
	public void  visit(asmeta.terms.basicterms.SetTerm st) {
		visitTerms(st.getTerm());
	}	
	
	
	public void  visit(asmeta.terms.basicterms.DomainTerm dt) {
		visit(dt.getDomain());
	}
	
	public void  visit(UndefTerm ut) {
		//do nothing
	}
	
	
	private void visitTerms(List<? extends Term> lt) {
		for(Term t: lt) {
			visit(t);
		}
	}
	
	
	public void visit(asmeta.terms.furtherterms.ExistTerm et) {
		// (exist v1 in D1,...,vn in Dn 	with Gv1,...,vn)
		// variables in dynamic domains???? D1... Dn
		visitTerms(et.getRanges());
		//
		visit(et.getGuard());
	}

	
	public void visit(asmeta.terms.furtherterms.SequenceCt sct) {
		// [$x in $seq | $x < $pivot : $x]
		visitTerms(sct.getRanges());
		visit(sct.getGuard());
	}
}
