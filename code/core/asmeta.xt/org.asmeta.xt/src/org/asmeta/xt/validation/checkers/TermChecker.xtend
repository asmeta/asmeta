package org.asmeta.xt.validation.checkers

import java.util.ArrayList
import java.util.HashMap
import org.asmeta.xt.asmetal.AnyDomain
import org.asmeta.xt.asmetal.AsmetalPackage
import org.asmeta.xt.asmetal.BagCT
import org.asmeta.xt.asmetal.BagTerm
import org.asmeta.xt.asmetal.BasicTerm
import org.asmeta.xt.asmetal.BinaryOperation
import org.asmeta.xt.asmetal.CaseTerm
import org.asmeta.xt.asmetal.ComprehensionTerm
import org.asmeta.xt.asmetal.ConditionalTerm
import org.asmeta.xt.asmetal.EnumTerm
import org.asmeta.xt.asmetal.ExistTerm
import org.asmeta.xt.asmetal.ExistUniqueTerm
import org.asmeta.xt.asmetal.Expression
import org.asmeta.xt.asmetal.FiniteQuantificationTerm
import org.asmeta.xt.asmetal.ForallTerm
import org.asmeta.xt.asmetal.Function
import org.asmeta.xt.asmetal.FunctionTerm
import org.asmeta.xt.asmetal.LetTerm
import org.asmeta.xt.asmetal.MapCT
import org.asmeta.xt.asmetal.MapTerm
import org.asmeta.xt.asmetal.RuleAsTerm
import org.asmeta.xt.asmetal.SequenceCT
import org.asmeta.xt.asmetal.SequenceTerm
import org.asmeta.xt.asmetal.SetCT
import org.asmeta.xt.asmetal.SetTerm
import org.asmeta.xt.asmetal.StructuredTD
import org.asmeta.xt.asmetal.Term
import org.asmeta.xt.validation.ErrorCode
import org.asmeta.xt.validation.utility.DomainCalculator
import org.asmeta.xt.validation.utility.DomainTree
import org.asmeta.xt.validation.utility.ErrorType
import org.asmeta.xt.validation.utility.Utility
import org.eclipse.emf.ecore.EStructuralFeature

class TermChecker {
	
	def static ErrorType isAnyDomainOK(SetTerm term) {
		
		if ( term.start_term === null ) {
			var EStructuralFeature feature = AsmetalPackage.Literals.SET_TERM__START_TERM
			var String code = ErrorCode.SET_TERM__ANY_DOMAIN_NOT_DECLARED
			return SharedCheckers.returnIsAnyDomainOK( feature, code )
		}
		return null	
	}
	
	def static ErrorType isAnyDomainOK(BagTerm term) {
		
		if ( term.start_term === null ) {
			var EStructuralFeature feature = AsmetalPackage.Literals.BAG_TERM__START_TERM
			var String code = ErrorCode.BAG_TERM__ANY_DOMAIN_NOT_DECLARED
			return SharedCheckers.returnIsAnyDomainOK( feature, code )
		}
		return null	
	}
	
	def static ErrorType isAnyDomainOK(SequenceTerm term) {
		
		if ( term.start_term === null ) {
			var EStructuralFeature feature = AsmetalPackage.Literals.SEQUENCE_TERM__START_TERM
			var String code = ErrorCode.SEQUENCE_TERM__ANY_DOMAIN_NOT_DECLARED
			return SharedCheckers.returnIsAnyDomainOK( feature, code )
		}
		return null	
	}
	
	def static ErrorType isAnyDomainOK(MapTerm term) {
		
		if ( term.term === null || term.term.size === 0 ) {
			var EStructuralFeature feature = AsmetalPackage.Literals.MAP_TERM__TERM
			var String code = ErrorCode.MAP_TERM__ANY_DOMAIN_NOT_DECLARED
			return SharedCheckers.returnIsAnyDomainOK( feature, code )
		}
		return null	
	}
	
		
	def static ErrorType isDeclaredOnlyOnce(SetTerm term) {
		
		if ( term.start_term !== null ) {
			
			if (term.start_term instanceof BasicTerm) {
				var code_start = Utility.getBasicTermCode( term.start_term  as BasicTerm )
				var errorOK = false
				
				if ( term.end_term !== null) {
					
					var String code_end;
					if (term.end_term instanceof BasicTerm) { 
						code_end = Utility.getBasicTermCode( term.end_term  as BasicTerm ) 
						errorOK = code_end.equals( code_start )
					}
				}
				else {
					
					var HashMap<String, String> names = new HashMap<String, String>
					var code = ""
					names.put( code_start, code_start )
					
					for ( Term t : term.other_terms ) {
						code = Utility.getBasicTermCode( t as BasicTerm )
						
						if ( !errorOK && names.containsKey( code ) ) errorOK = true
						else names.put( code, code )
					}
					
				}
				
				if ( errorOK && term.other_terms.size()>0) {
					var EStructuralFeature feature = AsmetalPackage.Literals.SET_TERM__START_TERM
					var String message = 'Duplicates are not allowed in a set'
					var String code = ErrorCode.SET_TERM__ADDED_TWICE
					return new ErrorType( message, feature, code )			
				}	
			}
			else if (term.start_term instanceof StructuredTD) {
				// If it is a StruvturedTD, we have to check each su-element
				return null
			}			
		}
		
		return null
		
	}
	
	def static ErrorType isColletionTermSameDomain(SequenceTerm term) {
		
		if ( term.start_term !== null ) {
			
			var domain_start = DomainCalculator.getDomainTerm( term.start_term )
				
			var errorOK = false
			
			if ( term.end_term !== null) {
				var domain_end = DomainCalculator.getDomainTerm( term.end_term )
				errorOK = !Utility.isCompatible( domain_start,  domain_end )
				
			}
			else {
				var DomainTree tree
				for ( Term t : term.other_terms ) {
					tree = DomainCalculator.getDomainTerm( t )
					if ( !errorOK &&  !Utility.isCompatible( domain_start, tree ) ) errorOK = true
				}
				
			}
			
			if ( errorOK ) {
				var EStructuralFeature feature = AsmetalPackage.Literals.SEQUENCE_TERM__START_TERM
				var String message = 'Every element of a sequence must have the same type-domain'
				var String code = ErrorCode.SEQUENCE_TERM__DIFFERENT_DOMAINS
				return new ErrorType( message, feature, code )			 
			}
			
			
		}
		
		return null		

	}
	
			

	def static ErrorType isColletionTermSameDomain(SetTerm term) {
		
		if ( term.start_term !== null ) {
			
			var domain_start = DomainCalculator.getDomainTerm( term.start_term )
				
			var errorOK = false
			
			if ( term.end_term !== null) {
				var domain_end = DomainCalculator.getDomainTerm( term.end_term )
				errorOK = !Utility.isCompatible( domain_start,  domain_end )
				
			}
			else {
				var DomainTree tree
				for ( Term t : term.other_terms ) {
					tree = DomainCalculator.getDomainTerm( t )
					if ( !errorOK &&  !Utility.isCompatible( domain_start, tree ) ) errorOK = true
				}
				
			}
			
			if ( errorOK ) {
				var EStructuralFeature feature = AsmetalPackage.Literals.SET_TERM__START_TERM
				var String message = 'Every element of a set must have the same type-domain'
				var String code = ErrorCode.SET_TERM__DIFFERENT_DOMAINS
				return new ErrorType( message, feature, code )			 
			}
			
			
		}
		
		return null		

	}
	
			
	def static ErrorType isColletionTermSameDomain(BagTerm term) {
		
		if ( term.start_term !== null ) {
			
			var domain_start = DomainCalculator.getDomainTerm( term.start_term )
				
			var errorOK = false
			
			if ( term.end_term !== null) {
				var domain_end = DomainCalculator.getDomainTerm( term.end_term )
				errorOK = !Utility.isCompatible( domain_start,  domain_end )
				
			}
			else {
				var DomainTree tree
				for ( Term t : term.other_terms ) {
					tree = DomainCalculator.getDomainTerm( t )
					if ( !errorOK &&  !Utility.isCompatible( domain_start, tree ) ) errorOK = true
				}
				
			}
			
			if ( errorOK ) {
				var EStructuralFeature feature = AsmetalPackage.Literals.BAG_TERM__START_TERM
				var String message = 'Every element of a bag must have the same type-domain'
				var String code = ErrorCode.BAG_TERM__DIFFERENT_DOMAINS
				return new ErrorType( message, feature, code )			 
			}
			
			
		}
		
		return null		


	}
	
			
	def static ErrorType isColletionTermSameDomain(MapTerm term) {
		
		var boolean errorOK = false
		
		if ( term.term !== null && term.term.size > 1 ) {
			
			var DomainTree base_source = DomainCalculator.getDomainTerm( term.term.get(0) )
			var DomainTree base_target = DomainCalculator.getDomainTerm( term.term.get(1) )
			var DomainTree tree
			
			for ( var int i =  2 ; i < term.term.size ; i++ ) {
				
				if ( !errorOK ) {
					// KEY
					tree = DomainCalculator.getDomainTerm( term.term.get(i) )
					if ( !Utility.isCompatible( base_source, tree ) ) errorOK = true
			
					i++
			
					// VALUE
					tree =  DomainCalculator.getDomainTerm( term.term.get(i) )
					if ( !Utility.isCompatible( base_target, tree ) ) errorOK = true
				}

			}
						
			if ( errorOK ) {
				var EStructuralFeature feature = AsmetalPackage.Literals.MAP_TERM__TERM
				var String message = 'Every pair of a map must be compatible with the type-domain for the second pair element. '
				var String code = ErrorCode.MAP_TERM__DIFFERENT_DOMAINS
				return new ErrorType( message, feature, code )			 
			}
			
		}

		
		return null		


	}
	
	
	def static ErrorType isStepOK(SetTerm term) {
		
		if ( term.step !== null ) {	
			var EStructuralFeature feature = AsmetalPackage.Literals.SET_TERM__STEP
			var String code1 = ErrorCode.SET_TERM__STEP_NEGATIVE
			var String code2 = ErrorCode.SET_TERM__STEP_NAN
			return returnStepError( term.step, code1, code2, feature )
		}
		
		return null		

	}
	
	def static ErrorType isStepOK(BagTerm term) {
		
		if ( term.step !== null ) {	
			var EStructuralFeature feature = AsmetalPackage.Literals.BAG_TERM__STEP
			var String code1 = ErrorCode.BAG_TERM__STEP_NEGATIVE
			var String code2 = ErrorCode.BAG_TERM__STEP_NAN
			return returnStepError( term.step, code1, code2, feature )
		}
		
		return null		

	}
	
	def static ErrorType isStepOK(SequenceTerm term) {
		
		if ( term.step !== null ) {	
			var EStructuralFeature feature = AsmetalPackage.Literals.SEQUENCE_TERM__STEP
			var String code1 = ErrorCode.SEQUENCE_TERM__STEP_NEGATIVE
			var String code2 = ErrorCode.SEQUENCE_TERM__STEP_NAN
			return returnStepError( term.step, code1, code2, feature )
		}
		
		return null		

	}
		
	def static ErrorType returnStepError( Term step, String code1, String code2, EStructuralFeature feature ) {
		
		var String message
		
		try {
			var Double step_number = new Double( Utility.getStringFromTerm(step) )
			if ( step_number < 0 ) {
				
				message = 'The step must be a positive number'
		//		code = ErrorCode.SET_TERM__STEP_NEGATIVE
				return new ErrorType( message, feature, code1 )			
			
			}		
		}
		catch( NumberFormatException e ) {
			message = 'The step must be a number'
			return new ErrorType( message, feature, code2 )		
		}
			
		return null
	}
	
	def static isFunctionOK(BinaryOperation operation) {
		
		var EStructuralFeature feature = AsmetalPackage.Literals.EXPRESSION__OP	
		var String op_name = Utility.getOperandFunctionName( operation.op )
		
		// some binary functions are special: they work on all domain
		// this functions are eq and neq
		// if the domains are compatible and the op is either = or !=, we search for eq(Prod(D,D))
		var DomainTree domain_right = DomainCalculator.getDomainTerm( operation.right )
		var String domain_name_right = Utility.getRightDomainNameForBinary( Utility.getDomain( domain_right.getCodeFromTree ) )	
		
		var DomainTree domain_left = DomainCalculator.getDomainTerm( operation.left )
		var String domain_name_left = Utility.getRightDomainNameForBinary( Utility.getDomain( domain_left.getCodeFromTree ) )

		// serve anche cercare i tipi di 
		if ( domain_right instanceof AnyDomain || domain_left instanceof AnyDomain  ) return null
		
		var String code
		
		if (  ( op_name.equals("eq") || op_name.equals("neq") ) && Utility.isCompatible( domain_right, domain_left ) ) {	
		 	code = op_name + "(Prod(D,D))"
		}
		else {
			code = op_name + "(Prod(" + domain_name_left + "," + domain_name_right + "))"
		}

		var boolean error = false
		var func = Utility.getFunction( code )

		if ( func === null ) {
			var ArrayList<DomainTree> list_domain = new ArrayList
			list_domain.add(domain_right)
			list_domain.add(domain_left)
			
			var tree = DomainTree.mergeTree( list_domain )
			var ArrayList<Function> list = Utility.getListOfPossibleFunction( op_name )		
			
			if ( list === null ) error = true
			else func = Utility.searchForMostCompatible( list, tree )
			
			if (func === null) error = true
			
		}

		if ( error ) return SharedCheckers.returnErrorFunctionString( code, feature )
		else return null
		
	}

	def static isFunctionOK(Expression operation) {
		
		var EStructuralFeature feature = AsmetalPackage.Literals.EXPRESSION__OP	
		
		var DomainTree domain = DomainCalculator.getDomainTerm( operation.operand )
		var String op_name = Utility.getOperandFunctionName( operation.op )
		
		var String code
		if ( domain === null ) code = op_name + "(null)"
		else code = op_name + "(" + domain.getCodeFromTree + ")"

		return SharedCheckers.returnErrorFunctionString( code, feature )
	}
	
	def static ErrorType isAnyDomainOK(FunctionTerm term) {	
		var EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_TERM__FUNCTION_NAME
		var String code = ErrorCode.FUNCTION_TERM__ANY_DOMAIN_NOT_DECLARED
		return SharedCheckers.returnIsAnyDomainOK( feature, code )
	}
	
	
	def static isFunctionOK(FunctionTerm function_term) {
		
		var EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_TERM__FUNCTION_NAME
		
		// the function term could be a function or a domain
		
		// first we check if it's a function or a domain
		
		if ( function_term.arguments === null ) {
			if ( !Utility.isFunctionName( function_term.functionName ) ) {
				if (Utility.isRule(function_term.functionName)) 
					return null
				else 
					return SharedCheckers.returnErrorDomainString( function_term.functionName, feature )
			}
		}
		else {
			if ( Utility.isFunctionName( function_term.functionName ) ) {
				
				// we know that it's a function term, now we have to check if the function have the right domain	
				var domain_arguments = DomainCalculator.getDomainTupleTerm( function_term.arguments )
				var DomainTree tree = new DomainTree(domain_arguments)
				var ArrayList<Function> list = Utility.getListOfPossibleFunction( function_term.functionName )
				
				if ( list === null ) return null
			
				//println("---------")
				//println("funzione " + function_term.function)
				var Function func = Utility.searchForMostCompatible( list, tree )
				//println(func)
				//println("---------")
				
				
				// compatible function not found
				if ( func === null ) {

					// trova la prima funzione con name
					func = Utility.getFunctionByName( function_term.functionName )

					var function_arity = Utility.calculateFunctionArity(func)
					
					var body_arity = 0
					if ( function_term.arguments === null ) body_arity = 0
					else body_arity = DomainTree.getArity(tree)
					
					if ( function_arity === body_arity ) {	
						/*var Domain d
						if ( func.domain === null ) d = null
						else d = Utility.getDomain( Utility.calculateDomainCode(func.domain) )
						*/
						
						var String res = function_term.functionName + "(" + DomainTree.getCodeFromTree(domain_arguments) + ")"
						
						return SharedCheckers.returnErrorFunctionString( res, feature )
					}
					else {
						
						var String message
						var String code = ErrorCode.FUNCTION_TERM__INVALID_ARITY_FOR_ZERO_ARITY
						
						if ( function_arity === 0 ) {
							code = ErrorCode.FUNCTION_TERM__INVALID_ARITY_FOR_ZERO_ARITY
							message = "The associated function arity is 0, but the function term specify arguments of the function application"
						}
						else {
							code = ErrorCode.FUNCTION_TERM__INVALID_ARITY_FOR_N_ARITY
							message = "The associated function arity is greater than 0, but the function term doesn't specify the actual arguments of the function application"
						}
							
						return  new ErrorType( message, feature, code )	
		
					}
				}
				
				
				
			}
			else {
				if (Utility.isRule(function_term.functionName)) 
					return null
				else 
					return SharedCheckers.returnErrorFunctionString( function_term.functionName, feature )
			}
		}
		
		return null
		
		
	}
	
	def static ErrorType isSymbolOK(EnumTerm enum_term) {
		
		if ( !Utility.symbolExist( enum_term.symbol ) ) {
			var EStructuralFeature feature = AsmetalPackage.Literals.CONSTANT_TERM__SYMBOL
			var String message = "The enumeration element '" + enum_term.symbol + "' is not defined"
			var String code = ErrorCode.ENUM_TERM__SYMBOL_NOT_FOUND
			return new ErrorType( message, feature, code )			
		}
		
		return null
		
	}
	
	def static ErrorType isGuardOK(ConditionalTerm cond_term) {
		var EStructuralFeature feature = AsmetalPackage.Literals.CONDITIONAL_TERM__GUARD
		return SharedCheckers.returnErrorGuardNotBoolean( cond_term.guard, feature )
	}
	
	def static ErrorType areBranchesOK(ConditionalTerm cond_term) {
		if ( cond_term.elseTerm === null ) return null
		
		var dom_then = DomainCalculator.getDomainTerm( cond_term.thenTerm )
		var dom_else = DomainCalculator.getDomainTerm( cond_term.elseTerm )
		
		if ( !Utility.isCompatible( dom_then, dom_else ) ) {
			var EStructuralFeature feature = AsmetalPackage.Literals.CONDITIONAL_TERM__THEN_TERM
			var String message = "The conditional term isn't compatible to the 'else term'"
			var String code = ErrorCode.CONDITIONAL_TERM__IF_ELSE_DOMAIN_NOT_COMPATIBLE
			return new ErrorType( message, feature, code )			
		}
		
	}

	
	/**
	 * Controllo che quello che ritorno sia compatibile con il dominio del case
	 */
	def static isResultTermsOK(CaseTerm case_term) {
		
		var String code = ErrorCode.CASE_TERM__RESULT_TERM_DOMAIN_NOT_COMPATIBLE
		var EStructuralFeature feature = AsmetalPackage.Literals.CASE_TERM__COMPARED_TERM
		
		var DomainTree case_domain = DomainCalculator.getDomainTerm( case_term )
		var DomainTree result_domain = DomainCalculator.getDomainTerm( case_term.comparedTerm )
		
		for ( Term branches : case_term.resultTerms) {
			result_domain = DomainCalculator.getDomainTerm( branches )
			if ( !Utility.isCompatible( result_domain, case_domain ) ) {
				var String message = "The term to match (" + result_domain.model.root + ") isn't compatible to the left-hand terms of the case-clauses (" + case_domain.model.root + ")"
				return new ErrorType( message, feature, code )		
			} 				
		}
		
		return null			
	}
	
	/**
	 * Controllo che quello che c'è dentro lo switch abbia un dominio compatibile con quello che ho subito dopo il case
	 */
	def static isComparingTermOK(CaseTerm case_term) {
		var String code = ErrorCode.CASE_TERM__COMPARED_AND_COMPARING_DOMAIN_NOT_COMPATIBLE
		var EStructuralFeature feature = AsmetalPackage.Literals.CASE_TERM__COMPARED_TERM
		
		var DomainTree compared_domain = DomainCalculator.getDomainTerm( case_term.comparedTerm )
		var DomainTree comparing_domain
		
		for ( Term branches : case_term.comparingTerm) {
			comparing_domain = DomainCalculator.getDomainTerm( branches )
			if ( !Utility.isCompatible( comparing_domain, compared_domain ) ) {
				var String message = "The term to match (" + comparing_domain.model.root + ") isn't compatible to the left-hand terms of the case-clauses (" + compared_domain.model.root + ")"
				return new ErrorType( message, feature, code )		
			} 				
		}
		
		return null			
	}
	
	def static isOtherwiseTermOK(CaseTerm case_term) {
		if ( case_term.otherwiseTerm === null ) return null
		
		var String message = "A case term must be compatible to the term of the otherwise-clause"
		var String code = ErrorCode.CASE_TERM__OTHERWISE_DOMAIN_NOT_COMPATIBLE
		var EStructuralFeature feature = AsmetalPackage.Literals.CASE_TERM__OTHERWISE_TERM
		
		var DomainTree domain_case = DomainCalculator.getDomainTerm( case_term )
		var DomainTree domain_otherwise = DomainCalculator.getDomainTerm( case_term.otherwiseTerm )
		
		if ( !Utility.isCompatible( domain_case, domain_otherwise ) ) return new ErrorType( message, feature, code )		
	}
	
	def static ErrorType isRuleOK(RuleAsTerm ruleas_term) {
		
		var String message = "Rule " + ruleas_term.name + " not found"
		var String code = ErrorCode.BODY__RULE_NOT_FOUND
		var EStructuralFeature feature = AsmetalPackage.Literals.RULE_AS_TERM__NAME
		
		// check if rule exist
		var ruleList = Utility.getRule( ruleas_term.name )

		if ( ruleList === null  || ruleList.size == 0) {
			return new ErrorType( message, feature, code )
		}
		
		var rule = Utility.searchForMostCompatibleRule(ruleList, ruleas_term.domains)
		
		if (rule === null) return new ErrorType( message, feature, code )
		
		if ( ruleas_term.domains === null || ruleas_term.domains.size === 0 ) return null
		else if (rule.domain === null && ruleas_term.domains == null) return null
		else if (ruleas_term.domains.size != rule.domain.size) return new ErrorType( message, feature, code )
		else if (ruleas_term.domains.size == 1){
			if (!Utility.isCompatible(ruleas_term.domains.get(0), rule.domain.get(0))) return new ErrorType( message, feature, code )
		} else {						
			var knownDomains = DomainTree.getComposedNode("Prod", ruleas_term.domains)	
			var newDomains = DomainTree.getComposedNode("Prod", rule.domain)	
			if (!Utility.isCompatible(new DomainTree(knownDomains), new DomainTree(newDomains))) return new ErrorType( message, feature, code )
		}
		
		return null
		 
	}

	def static ErrorType isVariableListOK(LetTerm let_term) {
		var EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE
		var String code = ErrorCode.LET_TERM__VARIABLE_ALREADY_USED
		return SharedCheckers.returnErrorVariableDeclared("LetTerm", feature, code, let_term.variable, let_term )
	}
	
	def static ErrorType isVariableListOK(FiniteQuantificationTerm finite_term) {
		if ( finite_term instanceof ForallTerm ) return isVariableListOK(finite_term)
		else if ( finite_term instanceof ExistTerm )
			return isVariableListOK(finite_term)
		else if ( finite_term instanceof ExistUniqueTerm ) return isVariableListOK(finite_term)
		else return null
	}
	
	def static ErrorType isVariableListOK(ForallTerm forall_term) {
		var EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE
		var String code = ErrorCode.FORALL_TERM__VARIABLE_ALREADY_USED
//		Utility.changeFunctionsInDomains(forall_term)
		return SharedCheckers.returnErrorVariableDeclared("ForallTerm", feature, code, forall_term.variable, forall_term )
	}
	
	def static ErrorType isVariableListOK(ExistTerm exist_term) {
		var EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE
		var String code = ErrorCode.EXIST_TERM__VARIABLE_ALREADY_USED
//		Utility.changeFunctionsInDomains(exist_term)
		return SharedCheckers.returnErrorVariableDeclared("ExistTerm", feature, code, exist_term.variable, exist_term )
	}
	
	def static ErrorType isVariableListOK(ExistUniqueTerm exist_unique_term) {
		var EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE
		var String code = ErrorCode.EXIST_UNIQUE_TERM__VARIABLE_ALREADY_USED
//		Utility.changeFunctionsInDomains(exist_unique_term)
		return SharedCheckers.returnErrorVariableDeclared("ExistUniqueTerm", feature, code, exist_unique_term.variable, exist_unique_term )
	}
	
	def static ErrorType isVariableDomainOK(FiniteQuantificationTerm finite_term) {
	
		var EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE
		var String message = 'The domain of each term appearing in the variables ranges must be a powerset domain.'
		var String code = ErrorCode.FINITE_QUANT_TERM__VARIABLE_DOMAIN_NOT_POWERSET

		var boolean errorOK = false

		for ( Term range : finite_term.ranges ) {
			if ( !errorOK ) {
				// We cannot manage only powersets since also a Domain can be used as domain for a variable in a Exists term
				if ( !Utility.isPowerSetDomain(range) && 
					!Utility.imported_all_domain_map.containsKey(DomainCalculator.getDomainTerm(range).getCodeFromTree) &&
					!Utility.isDomainName(DomainCalculator.getDomainTerm(range).getCodeFromTree)
					) 
						errorOK = true
			}
		}
		
		if ( errorOK ) {
			return new ErrorType( message, feature, code )
		}
		
		return null
		 
	}
		
	def static ErrorType isGuardOK( FiniteQuantificationTerm finite_term ) {
		var EStructuralFeature feature = AsmetalPackage.Literals.FINITE_QUANTIFICATION_TERM__GUARD
		return SharedCheckers.returnErrorGuardNotBoolean( finite_term.guard, feature )
	}
	
	def static ErrorType isGuardOK(ComprehensionTerm compr_term) {
		if ( compr_term.guard === null ) return null
		var EStructuralFeature feature = AsmetalPackage.Literals.COMPREHENSION_TERM__GUARD
		return SharedCheckers.returnErrorGuardNotBoolean( compr_term.guard, feature )
	}
	
	def static ErrorType isVariableDomainOK(SetCT setct_term) {
	
		var EStructuralFeature feature = AsmetalPackage.Literals.COMPREHENSION_TERM__RANGES
		var String message = 'In a SetCT, the domains of variables must be Powerset.'
		var String code = ErrorCode.SET_CT_TERM__VARIABLE_DOMAIN_NOT_POWERSET

		var boolean errorOK = false

		for ( Term range : setct_term.ranges ) {
			if ( !errorOK ) {
				if ( !Utility.isPowerSetDomain(range) ) errorOK = true
			}
		}
		
		if ( errorOK ) {
			return new ErrorType( message, feature, code )
		}
		
		return null
		 
	}
	
	def static ErrorType isVariableDomainOK(MapCT mapct_term) {
	
		var EStructuralFeature feature = AsmetalPackage.Literals.COMPREHENSION_TERM__RANGES
		var String message = 'In a MapCT, terms in ranges, Di, representing where variables vary, must be powersets..'
		var String code = ErrorCode.MAP_CT_TERM__VARIABLE_DOMAIN_NOT_POWERSET

		var boolean errorOK = false

		for ( Term range : mapct_term.ranges ) {
			if ( !errorOK ) {
				if ( !Utility.isPowerSetDomain(range) ) errorOK = true
			}
		}
		
		if ( errorOK ) {
			return new ErrorType( message, feature, code )
		}
		
		return null
		 
	}
	
	def static ErrorType isVariableDomainOK(BagCT bagct_term) {
	
		var EStructuralFeature feature = AsmetalPackage.Literals.COMPREHENSION_TERM__RANGES
		var String message = 'In a BagCT, terms in ranges, Bi, representing where variables vary, must be bags.'
		var String code = ErrorCode.BAG_CT_TERM__VARIABLE_DOMAIN_NOT_BAG

		var boolean errorOK = false

		for ( Term range : bagct_term.ranges ) {
			if ( !errorOK ) {
				if ( !Utility.isBagDomain(range) ) errorOK = true
			}
		}
		
		if ( errorOK ) {
			return new ErrorType( message, feature, code )
		}
		
		return null
		 
	}
	
	def static ErrorType isVariableDomainOK(SequenceCT sequencect_term) {
	
		var EStructuralFeature feature = AsmetalPackage.Literals.COMPREHENSION_TERM__RANGES
		var String message = ' In a SequenceCT, terms in ranges, Bi, representing where variables vary, must be sequences'
		var String code = ErrorCode.SEQUENCE_CT_TERM__VARIABLE_DOMAIN_NOT_SEQUENCE

		var boolean errorOK = false

		for ( Term range : sequencect_term.ranges ) {
			if ( !errorOK ) {
				if ( !Utility.isSequenceDomain(range) && !(range instanceof SequenceTerm)) errorOK = true
			}
		}
		
		if ( errorOK ) {
			return new ErrorType( message, feature, code )
		}
		
		return null
		 
	}
		
	def static ErrorType isVariableListOK(SetCT setct_term) {
		var EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE
		var String code = ErrorCode.SET_CT_TERM__VARIABLE_ALREADY_USED
		return SharedCheckers.returnErrorVariableDeclared("SetCT", feature, code, setct_term.variable, setct_term )
	}
	
	def static ErrorType isVariableListOK(MapCT setct_term) {
		var EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE
		var String code = ErrorCode.MAP_CT_TERM__VARIABLE_ALREADY_USED
		return SharedCheckers.returnErrorVariableDeclared("MapCT", feature, code, setct_term.variable, setct_term )
	}
	
	def static ErrorType isVariableListOK(SequenceCT setct_term) {
		var EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE
		var String code = ErrorCode.SEQUENCE_CT_TERM__VARIABLE_ALREADY_USED
		return SharedCheckers.returnErrorVariableDeclared("SequenceCT", feature, code, setct_term.variable, setct_term )
	}
	
	def static ErrorType isVariableListOK(BagCT setct_term) {
		var EStructuralFeature feature = AsmetalPackage.Literals.VARIABLE_BINDING_TERM__VARIABLE
		var String code = ErrorCode.BAG_CT_TERM__VARIABLE_ALREADY_USED
		return SharedCheckers.returnErrorVariableDeclared("BagCT", feature, code, setct_term.variable, setct_term )
	}
}