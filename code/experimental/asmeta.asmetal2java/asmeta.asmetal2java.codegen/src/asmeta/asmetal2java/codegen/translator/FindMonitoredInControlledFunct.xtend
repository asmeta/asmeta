package asmeta.asmetal2java.codegen.translator

import org.asmeta.parser.util.ReflectiveVisitor;

import asmeta.definitions.MonitoredFunction
import asmeta.terms.basicterms.LocationTerm
import asmeta.terms.basicterms.FunctionTerm
import asmeta.terms.furtherterms.StringTerm
import asmeta.terms.furtherterms.NaturalTerm
import asmeta.terms.furtherterms.ConditionalTerm
import asmeta.terms.basicterms.BooleanTerm
import asmeta.terms.furtherterms.IntegerTerm
import asmeta.terms.furtherterms.EnumTerm
import asmeta.terms.furtherterms.CaseTerm
import asmeta.terms.basicterms.VariableTerm
import asmeta.terms.basicterms.SetTerm
import asmeta.terms.basicterms.UndefTerm
import asmeta.terms.furtherterms.SequenceTerm
import asmeta.terms.basicterms.TupleTerm
import asmeta.terms.furtherterms.RealTerm
import asmeta.terms.furtherterms.CharTerm

/* Check if the init function term contains monitored functions */
class FindMonitoredInControlledFunct extends ReflectiveVisitor<Boolean> {

	def boolean visit(LocationTerm object) {
		return visit(object as FunctionTerm)
	}

	def boolean visit(StringTerm term) {
		return false
	}
	
	def boolean visit(CharTerm term) {
		return false
	}
	
	def boolean visit(UndefTerm term) {
		return false
	}

	def boolean visit(IntegerTerm term) {
		return false
	}
	
	def boolean visit(RealTerm term) {
		return false
	}

	def boolean visit(NaturalTerm term) {
		return false
	}

	def boolean visit(BooleanTerm term) {
		return false
	}

	def boolean visit(EnumTerm term) {
		return false
	}

	def boolean visit(VariableTerm term) {
		return false
	}

	def boolean visit(FunctionTerm term) {
		var boolean found = false
		if (term.arguments === null) {
			if (term.function instanceof MonitoredFunction)
				return true
			else
				return false
		} else
			for (sterm : term.arguments.terms)
				found = (found || visit(sterm));
		return found
	}

	def boolean visit(CaseTerm term) {
		var boolean found = false
		for (comparing : term.comparingTerm)
			found = (found || visit(comparing));
		for (result : term.resultTerms)
			found = (found || visit(result));
		found = (found || visit(term.comparedTerm));
		if (term.otherwiseTerm !== null)
			found = (found || visit(term.otherwiseTerm));
		return found
	}

	def boolean visit(ConditionalTerm term) {
		var boolean found = false
		found = (found || visit(term.thenTerm));
		found = (found || visit(term.elseTerm));
		return found
	}

	def boolean visit(SetTerm term) {
		var boolean found = false
		for (comparing : term.term)
			found = (found || visit(comparing));
		return found
	}

	def boolean visit(SequenceTerm term) {
		var boolean found = false
		for (comparing : term.terms)
			found = (found || visit(comparing));
		return found
	}
	
	def boolean visit(TupleTerm term) {
		var boolean found = false
		for (comparing : term.terms)
			found = (found || visit(comparing));
		return found
	}

}
