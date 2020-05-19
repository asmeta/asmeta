package org.asmeta.xt.validation.checkers

import org.asmeta.xt.asmetal.AbstractTD
import org.asmeta.xt.asmetal.AsmetalPackage
import org.asmeta.xt.asmetal.CaseRule
import org.asmeta.xt.asmetal.ChooseRule
import org.asmeta.xt.asmetal.ConcreteDomain
import org.asmeta.xt.asmetal.ConditionalRule
import org.asmeta.xt.asmetal.DynamicFunction
import org.asmeta.xt.asmetal.ExtendRule
import org.asmeta.xt.asmetal.ForallRule
import org.asmeta.xt.asmetal.FunctionTerm
import org.asmeta.xt.asmetal.LetRule
import org.asmeta.xt.asmetal.MonitoredFunction
import org.asmeta.xt.asmetal.Term
import org.asmeta.xt.asmetal.TurboDerivedRule
import org.asmeta.xt.asmetal.UpdateRule
import org.asmeta.xt.asmetal.VariableTerm
import org.asmeta.xt.validation.ErrorCode
import org.asmeta.xt.validation.utility.DomainCalculator
import org.asmeta.xt.validation.utility.DomainTree
import org.asmeta.xt.validation.utility.ErrorType
import org.asmeta.xt.validation.utility.Utility
import org.eclipse.emf.ecore.EStructuralFeature
import org.asmeta.xt.validation.utility.VariableKind
import org.asmeta.xt.asmetal.LocationTerm
import org.asmeta.xt.asmetal.TermAsRule
import org.asmeta.xt.asmetal.RuleDomain

class RuleChecker {
	
	def static ErrorType isGuardOK(ConditionalRule cond_rule) {
		var EStructuralFeature feature = AsmetalPackage.Literals.CONDITIONAL_RULE__GUARD
		return SharedCheckers.returnErrorGuardNotBoolean( cond_rule.guard, feature )	
	}
	
	def static ErrorType isUpdateCompatibleOK(UpdateRule update_rule) {
		var String message = "In an update rule, the term on the right-hand side must be compatible with the one on the left-hand side"
		var String code = ErrorCode.UPDATE_RULE__DOMAINS_NOT_COMPATIBLE
		var EStructuralFeature feature = AsmetalPackage.Literals.UPDATE_RULE__UPDATING_TERM
		
		var location_domain = DomainCalculator.getDomainTerm(update_rule.location)
		var update_domain = DomainCalculator.getDomainTerm(update_rule.updatingTerm)
		
		// if the domain of the updating is undef, then the domain is always compatible
		if ( update_domain.codeFromTree.equals("Undef") ) {
			return null;
		}
		
		var res = !Utility.isCompatible( update_domain, location_domain )
		//println("risultato " + res)
		if ( res ){
		//	println("***************************** CHECK DOMAINS")
		//	update_domain.printTree
		//	location_domain.printTree
		
			// Check if there are other functions that are compatible 
			res = Utility.existOneCompatible(update_rule.location, update_rule.updatingTerm);
			if (!res) {
				return new ErrorType( message + " (" + location_domain.model.root +"-" + update_domain.model.root + ")", feature, code )
			}
		} 
			
		
	}
	
	def static ErrorType isLocationOK(UpdateRule update_rule) {
		var String message
		var String code
		var EStructuralFeature feature = AsmetalPackage.Literals.UPDATE_RULE__LOCATION
		
		// location term = update_rule instanceof DynamicFunction (exception: monitored function)
		if ( update_rule.location instanceof FunctionTerm ) {
			
			
			
			var f_term = update_rule.location as FunctionTerm
			if ( f_term === null ) return SharedCheckers.returnErrorFunctionString( f_term.functionName, feature )
			
			var function = DomainCalculator.getFunctionFromFunctionTerm(f_term)
	
			if (function instanceof DynamicFunction) {			
				/*if ( !function.isIsDynamic ) {
					code = ErrorCode.LOCATION_TERM__NOT_DYNAMIC
					message = 'In a location term, the leftmost function must be dynamic'
					return  ErrorType( message, feature, code )		
				}
				else {*/
					code = ErrorCode.UPDATE_RULE__INVALID_LOCATION
					message = "In an update rule, the left-hand side must be either a location term (with a non monitored function) or a location variable term"
	
					if ( function instanceof MonitoredFunction )
						return new ErrorType( message, feature, code )	
				//}

			}
			else {
				code = ErrorCode.LOCATION_TERM__NOT_DYNAMIC
				message = 'In a location term, the leftmost function must be dynamic'
				return new ErrorType( message, feature, code )		
			}
		}
		else if ( update_rule.location instanceof LocationTerm ) {
			
			
			var f_term = update_rule.location as LocationTerm
			if ( f_term === null ) return SharedCheckers.returnErrorFunctionString( f_term.functionName, feature )
			
			var function = DomainCalculator.getFunctionFromFunctionTerm(f_term)
	
			if (function instanceof DynamicFunction) {			
				/*if ( !function.isIsDynamic ) {
					code = ErrorCode.LOCATION_TERM__NOT_DYNAMIC
					message = 'In a location term, the leftmost function must be dynamic'
					return  ErrorType( message, feature, code )		
				}
				else {*/
					code = ErrorCode.UPDATE_RULE__INVALID_LOCATION
					message = "In an update rule, the left-hand side must be either a location term (with a non monitored function) or a location variable term"
	
					if ( function instanceof MonitoredFunction )
						return new ErrorType( message, feature, code )	
				//}

			}
			else {
				code = ErrorCode.LOCATION_TERM__NOT_DYNAMIC
				message = 'In a location term, the leftmost function must be dynamic'
				return new ErrorType( message, feature, code )		
			}
		}
		else if ( update_rule.location instanceof VariableTerm ) {
			// TODO da completare
			var v_term =  update_rule.location as VariableTerm 
			var domain = Utility.getDomainFromVariable( v_term.name )
			// Set the kind of the variable
			v_term.kind = VariableKind.LOCATION_VAR
			
			// check if variable exist
			if ( domain === null ) {
				code = ErrorCode.VARIABLE_TERM__NOT_DECLARED
				message = "The definition of  variable is not allowed and " + v_term.name + " variable occurs for the first time"
				return new ErrorType( message, feature, code )		
			}
			
			code = ErrorCode.UPDATE_RULE__INVALID_VARIABLE
			message = "In an Updating rule, the variable to update must be a location variable, not a rule variable"
			
		}
		
		return null
	}
	
	def static ErrorType isExtendedDomainOK(ExtendRule extend_rule) {	
		var EStructuralFeature feature = AsmetalPackage.Literals.EXTEND_RULE__EXTENDED_DOMAIN
		var String code = ErrorCode.EXTENDED_RULE__INVALID_DOMAIN
		var String message = 'In an extend rule, the domain to extend must be dynamic, and it must be an abstract TD or concrete domain subsetting an abstract TD'
	
		var isError = false
			
		var dom = Utility.getDomain( extend_rule.extendedDomain.name )
		if ( dom === null ) SharedCheckers.returnErrorDomainString( extend_rule.extendedDomain.name, feature )
	
		if ( dom instanceof ConcreteDomain ) {
			if ( dom.isDynamic() ) {
				var domain_code = Utility.calculateDomainCode( dom.typeDomain )
				var type_dom = Utility.getDomain( domain_code )

				if ( !(type_dom instanceof AbstractTD) ) isError = true	
			}
			else isError = true
		}
		else {	
			// is abstract?
			if ( !(dom instanceof AbstractTD && dom.isDynamic) ) isError = true	
		} 
	
		if (isError) return new ErrorType( message, feature, code )
	
		return null
	
	}
	
	def static ErrorType isReserveDomainOK(ExtendRule extend_rule) {	
		var EStructuralFeature feature = AsmetalPackage.Literals.EXTEND_RULE__EXTENDED_DOMAIN
		var String code = ErrorCode.EXTENDED_RULE__RESERVE_DOMAIN_NOT_DECLARED
		return SharedCheckers.returnIsReserveDomainOK( feature, code )
	}
	
	def static ErrorType isVariableListOK(ExtendRule extend_rule) {
		var EStructuralFeature feature = AsmetalPackage.Literals.EXTEND_RULE__BOUND_VAR
		var String code = ErrorCode.EXTENDED_RULE__VARIABLE_ALREADY_USED
		return SharedCheckers.returnErrorVariableDeclared("ExtendRule", feature, code, extend_rule.boundVar, extend_rule )
	}
	
	def static ErrorType isGuardOK(ChooseRule cond_rule) {
		var EStructuralFeature feature = AsmetalPackage.Literals.CHOOSE_RULE__GUARD
		return SharedCheckers.returnErrorGuardNotBoolean( cond_rule.guard, feature )	
	}

	def static ErrorType isVariableListOK(ChooseRule choose_rule) {
		var EStructuralFeature feature = AsmetalPackage.Literals.CHOOSE_RULE__VARIABLE
		var String code = ErrorCode.CHOOSE_RULE__VARIABLE_ALREADY_USED
		return SharedCheckers.returnErrorVariableDeclared("Chooserule", feature, code, choose_rule.variable, choose_rule )
	}
		
	def static ErrorType isRangeListOK(ChooseRule choose_rule) {
	
		var EStructuralFeature feature = AsmetalPackage.Literals.CHOOSE_RULE__RANGES
		var String code1 = ErrorCode.CHOOSE_RULE__RANGE_NOT_POWERSET
		var String code2 = ErrorCode.CHOOSE_RULE__DOMAINS_NOT_COMPATIBLE
		
		return SharedCheckers.returnErrorPowersetRanges( choose_rule.ranges, choose_rule.variable, feature, code1, code2 )

	}
	
	def static ErrorType isGuardOK(ForallRule forall_rule) {
		
		if ( forall_rule.guard === null ) return null
		
		var EStructuralFeature feature = AsmetalPackage.Literals.FORALL_RULE__GUARD
		return SharedCheckers.returnErrorGuardNotBoolean( forall_rule.guard, feature )	
	}

	def static ErrorType isVariableListOK(ForallRule forall_rule) {
		var EStructuralFeature feature = AsmetalPackage.Literals.FORALL_RULE__VARIABLE
		var String code = ErrorCode.FORALL_RULE__VARIABLE_ALREADY_USED
		return SharedCheckers.returnErrorVariableDeclared("ForallRule", feature, code, forall_rule.variable, forall_rule )
	}
	
	def static ErrorType isRangeListOK(ForallRule forall_rule) {
	
		var EStructuralFeature feature = AsmetalPackage.Literals.FORALL_RULE__RANGES
		var String code1 = ErrorCode.FORALL_RULE__RANGE_NOT_POWERSET
		var String code2 = ErrorCode.FORALL_RULE__DOMAINS_NOT_COMPATIBLE
		
		return SharedCheckers.returnErrorPowersetRanges( forall_rule.ranges, forall_rule.variable, feature, code1, code2 )

	}
	
	def static ErrorType isVariableListOK(LetRule choose_rule) {
		var EStructuralFeature feature = AsmetalPackage.Literals.LET_RULE__VARIABLE
		var String code = ErrorCode.LET_RULE__VARIABLE_ALREADY_USED
		return SharedCheckers.returnErrorVariableDeclared("LetRule", feature, code, choose_rule.variable, choose_rule )
	}
	
	def static ErrorType areBranchesOK(CaseRule case_rule) {
		
		var term_domain = DomainCalculator.getDomainTerm( case_rule.term )
		var DomainTree case_domain
		
		for ( Term term : case_rule.caseTerm ) {
			
			case_domain = DomainCalculator.getDomainTerm( term )
			
			if ( !Utility.isCompatible( term_domain, case_domain ) ) {
				var EStructuralFeature feature = AsmetalPackage.Literals.CASE_RULE__TERM
				var message = 'In a case rule, every term of the case-clauses must be compatible to the main term'
				var String code = ErrorCode.CASE_RULE__BRANCH_DOMAIN_NOT_COMPATIBLE
				return new ErrorType( message, feature, code )
			}
			
		}
		
		return null

	}
	
	def static ErrorType isGuardOK(TurboDerivedRule while_rule) {
		var EStructuralFeature feature = AsmetalPackage.Literals.TURBO_DERIVED_RULE__GUARD
		return SharedCheckers.returnErrorGuardNotBoolean( while_rule.guard, feature )	
	}
	
	def static ErrorType isRuleOk(TermAsRule term_as_Rule) {
		var EStructuralFeature feature = null
		
		if (term_as_Rule.term instanceof VariableTerm) 
			return null
			
		// If the term is a function Term, check the codomain
		if (term_as_Rule.term instanceof FunctionTerm) {
			var FunctionTerm ft = term_as_Rule.term as FunctionTerm
			if (ft.function !== null && ft.function.codomain !== null) {
				if (ft.function.codomain instanceof RuleDomain)
					return null
				else
					return new ErrorType( "Error in TermAsRule term: only Rule can be used as Codomain", feature, ErrorCode.TERM_AS_RULE_CODOMAIN_NOT_RULE )
			}
		}
		
		return null	
	}
	
}