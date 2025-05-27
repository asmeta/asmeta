package org.asmeta.nusmv;

import org.asmeta.nusmv.util.AsmNotSupportedException;
import org.asmeta.simulator.Location;

import asmeta.definitions.Function;
import asmeta.structure.FunctionDefinition;
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

public class DoesTermContainConds extends org.asmeta.parser.util.ReflectiveVisitor<Boolean> {
	public final static DoesTermContainConds INSTANCE = new DoesTermContainConds();

	public Boolean visit(Location loc) {
		Function signature = loc.getSignature();
		assert signature != null;
		FunctionDefinition definition = signature.getDefinition();
		assert definition != null: "No definition for " + signature.getName();
		Term body = definition.getBody();
		assert body != null: "No body definition for " + signature.getName();
		return visit(body);
	}

	public Boolean visit(Term term) {
		return visit((Object) term);
	}

	public Boolean visit(VariableTerm variable) {
		return false;
	}

	public Boolean visit(LetTerm letTerm) {
		return visit(letTerm.getBody());
	}

	public Boolean visit(FunctionTerm funcTerm) throws Exception {
		/*Function func = funcTerm.getFunction();
		if(Defs.isStatic(func) || Defs.isDerived(func)) {
			return visit(func.getDefinition().getBody());
		}*/
		return false;
	}

	public Boolean visit(TupleTerm tupleTerm) {
		return false;
	}

	public Boolean visit(LocationTerm location) throws AsmNotSupportedException {
		return false;
	}

	public Boolean visit(NaturalTerm number) {
		return false;
	}

	public Boolean visit(IntegerTerm number) {
		return false;
	}

	public Boolean visit(RealTerm number) {
		return false;
	}

	
	public Boolean visit(EnumTerm term) {
		return false;
	}

	public Boolean visit(BooleanTerm bool) {
		return false;
	}

	public Boolean visit(UndefTerm undef) {
		return false;
	}
	
	public Boolean visit(DomainTerm term) {
		return false;
	}

	public Boolean visit(ExistTerm existTerm) throws Exception {
		return false;
	}

	public Boolean visit(ExistUniqueTerm exitUniqueTerm) throws Exception {
		return false;
	}

	public Boolean visit(ForallTerm forallTerm) throws Exception {
		return false;
	}

	public Boolean visit(MapTerm mapTerm) {
		return false;
	}
	
	public Boolean visit(CaseTerm caseTerm) {
		return true;
	}

	public Boolean visit(ConditionalTerm conditionalTerm) {
		return true;
	}
}
