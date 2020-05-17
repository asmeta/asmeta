package org.asmeta.xt.validation.validators

import org.asmeta.xt.asmetal.CaseRule
import org.asmeta.xt.asmetal.ChooseRule
import org.asmeta.xt.asmetal.ConditionalRule
import org.asmeta.xt.asmetal.ExtendRule
import org.asmeta.xt.asmetal.ForallRule
import org.asmeta.xt.asmetal.LetRule
import org.asmeta.xt.asmetal.TurboDerivedRule
import org.asmeta.xt.asmetal.UpdateRule
import org.asmeta.xt.validation.checkers.RuleChecker
import org.asmeta.xt.validation.utility.ErrorType
import org.asmeta.xt.validation.utility.Utility
import org.asmeta.xt.asmetal.TermAsRule

class RuleValidator {
	
	def static ErrorType checkError(UpdateRule update_rule) {
		
		var ErrorType error = null
		
		error = RuleChecker.isUpdateCompatibleOK( update_rule )
		if ( error !== null ) return error	
			
		error = RuleChecker.isLocationOK( update_rule )
		if ( error !== null ) return error	
		
		return null	 
		
	}
	
	def static ErrorType checkError(ConditionalRule conditional_rule) {
		
		var ErrorType error = null
		
		error = RuleChecker.isGuardOK( conditional_rule )
		if ( error !== null ) return error	
		
		return null	 
		
	}
	
	def static ErrorType checkError( ExtendRule extend_rule ) {
		
		Utility.fillVariableMap( extend_rule )
		
		var ErrorType error = null
		
		error = RuleChecker.isReserveDomainOK( extend_rule )
		if ( error !== null ) return error
		
		error = RuleChecker.isExtendedDomainOK( extend_rule )
		if ( error !== null ) return error
				
		error = RuleChecker.isVariableListOK( extend_rule )
		if ( error !== null ) return error
		
		return null	 
		
	}	
			
	def static ErrorType checkError( ChooseRule choose_rule ) {
		
		Utility.fillVariableMap( choose_rule )
		
//		Utility.changeFunctionsInDomains(choose_rule)
		
		var ErrorType error = null
		
		error = RuleChecker.isGuardOK( choose_rule )
		if ( error !== null ) return error	
		
		error = RuleChecker.isVariableListOK( choose_rule )
		if ( error !== null ) return error	
		
		error = RuleChecker.isRangeListOK( choose_rule )
		if ( error !== null ) return error	
		
		return null	 
		
	}
	
	def static ErrorType checkError( ForallRule forall_rule ) {
		
		Utility.fillVariableMap( forall_rule )
		
		var ErrorType error = null
		
		error = RuleChecker.isGuardOK( forall_rule )
		if ( error !== null ) return error	
		
		error = RuleChecker.isVariableListOK( forall_rule )
		if ( error !== null ) return error	
		
		error = RuleChecker.isRangeListOK( forall_rule )
		if ( error !== null ) return error	
		
		return null	 
		
	}
	
	def static ErrorType checkError( LetRule let_rule ) {
		
		Utility.fillVariableMap( let_rule )
		
		var ErrorType error = null
		
		error = RuleChecker.isVariableListOK( let_rule )
		if ( error !== null ) return error	
		
		return null	 
		
	}
	
	
	def static ErrorType checkError( CaseRule case_rule ) {
	
		var ErrorType error = null
		
		error = RuleChecker.areBranchesOK( case_rule )
		if ( error !== null ) return error	
		
		return null	 
		
	}
	
	def static ErrorType checkError( TurboDerivedRule while_rule ) {
	
		var ErrorType error = null
		
		error = RuleChecker.isGuardOK( while_rule )
		if ( error !== null ) return error	
		
		return null	 
		
	}
	
	def static ErrorType checkError( TermAsRule term_as_Rule ) {
	
		var ErrorType error = null
		
		error = RuleChecker.isRuleOk( term_as_Rule )
		if ( error !== null ) return error	
		
		return null	 
		
	}

	
}