package org.asmeta.xt.validation.checkers

import java.util.HashMap
import java.util.List
import org.asmeta.xt.asmetal.AsmetalPackage
import org.asmeta.xt.asmetal.Body
import org.asmeta.xt.asmetal.Domain
import org.asmeta.xt.asmetal.Property
import org.asmeta.xt.asmetal.SetTerm
import org.asmeta.xt.asmetal.StructuredTD
import org.asmeta.xt.asmetal.TemporalProperty
import org.asmeta.xt.asmetal.Term
import org.asmeta.xt.asmetal.VariableTerm
import org.asmeta.xt.validation.ErrorCode
import org.asmeta.xt.validation.utility.DomainCalculator
import org.asmeta.xt.validation.utility.DomainTree
import org.asmeta.xt.validation.utility.ErrorType
import org.asmeta.xt.validation.utility.Utility
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.asmeta.xt.asmetal.Rule
import org.asmeta.xt.validation.utility.VariableKind
import org.asmeta.xt.asmetal.LocationTerm
import org.asmeta.xt.validation.utility.BasicDomains

class SharedCheckers {
		
	def static ErrorType returnIsAnyDomainOK(EStructuralFeature feature, String code) {
		var dom = Utility.getDomain( "Any" )
		if ( dom === null ) {
			var String message = 'The AnyDomain is not defined'
			return  new ErrorType( message, feature, code )				
		}
		else return null
	}
	
	def static ErrorType returnIsReserveDomainOK(EStructuralFeature feature, String code) {
		var dom = Utility.getDomain( "Reserve" )
		if ( dom === null ) {
			var String message = 'The domain Reserve has not been declared'
			return new ErrorType( message, feature, code )				
		}
		else return null
	}	
		
	def static ErrorType returnHasUniqueNameProprierty(TemporalProperty spec, String name_of_data, String code) {
		var HashMap<String, String> names = new HashMap
		
		// if the specification doesn't have a name, don't check
		if ( spec.name === null ) return null
		
		var EStructuralFeature feature = AsmetalPackage.Literals.PROPERTY__NAME
		var String message = "Two " + name_of_data + " specifications can not have the same name."
	
		var Body body = spec.eContainer as Body

		
		for ( Property prop : body.property ) {
			if ( prop.name !== null && names.containsKey(prop.name) && !prop.equals(spec) && prop.class == spec.class ) {		
				return new ErrorType( message, feature, code )
			}
			else names.put( prop.name, prop.name )
		}
		
	}
	
		
	def static ErrorType returnErrorNotBoolean( Term body, EStructuralFeature feature, String name_of_data ) {
		
		var String message = "The expression specifying " + name_of_data + " must be a term whose associated type-domain is Boolean"
		var String code = ErrorCode.CONSTRAINT_DOMAIN_BODY_NOT_BOOLEAN
				
		if ( !Utility.isBoolean( body ) ) 
			return new ErrorType( message, feature, code )
		else 
			return null
		
	}
		
	def static ErrorType returnErrorGuardNotBoolean( Term guard, EStructuralFeature feature ) {
		
		var String message = "The type-domain associated to the guard term must be the boolean domain."
		var String code = ErrorCode.TERM__DOMAIN_GUARD_NOT_BOOLEAN
				
		if (guard !== null && (!Utility.isBoolean( guard ))) 
			return new ErrorType( message, feature, code )
		else 
			return null
		
	}
	
	def static ErrorType returnErrorDomain( Domain domain, EStructuralFeature feature ) {
		
		// we don't have to check if the core domain exists because we alredy check that 
		// when we decleare a StructuredDomain
		if ( domain instanceof StructuredTD ) return null
		
		// we don't have to check if the core domain exists becase we alredy check that 
		// when we decleare a StructuredDomain
		var name = Utility.calculateDomainCode(domain)
		return returnErrorDomainString( name, feature )
		
	}
	
	def static ErrorType returnErrorDomainString( String name, EStructuralFeature feature ) {

		var asm_origin = Utility.getOriginalAsm(name)
		var dom = Utility.getDomain(name)

		if (dom === null) {
			// Check if it is a basic domain
			for (Domain d : BasicDomains.basic_domain_list) {
				if (d.name.equals(name)) {
					dom = d;
					Utility.imported_all_domain_map.put(name, dom);
				}
			}
		}

		var String message
		var String code
		
		// check if type domain exists
		if ( dom === null ) {
			// if the domain is not declared, check if it wasn't imported
			if ( Utility.isDomainDeclaredPrivate(name) ) {
				message = "The domain '" + name + "' is not exported by the ASM '" + asm_origin + "'. It cannot be imported by this ASM"
				code = ErrorCode.SIGNATURE__DOMAIN_NOT_IMPORTED
			}
			else {
				message = "'" + name + "' domain is not declared"
				code = ErrorCode.SIGNATURE__DOMAIN_NOT_FOUND		
			}
			return new ErrorType( message, feature, code )
		}
		
		return null
		
	}
	
	def static ErrorType returnErrorFunctionString( String name, EStructuralFeature feature ) {
		
		var func = Utility.getFunction(name)
		
		if ( func === null )
			func = Utility.getFunctionByName(name)

		var String message
		var String code
		
		// check if type domain exists
		if ( func === null ) {
			// if the domain is not declared, check if it wasn't imported
			if ( Utility.isFunctionDeclaredPrivate(name) ) {
				var asm_origin = Utility.getOriginalAsm(name)
				message = "The function '" + name + "' is not exported by the ASM '" + asm_origin + "'. It cannot be imported by this ASM"
				code = ErrorCode.SIGNATURE__FUNCTION_NOT_IMPORTED
			}
			else {
				message = "'" + name + "' function is not declared"
				code = ErrorCode.SIGNATURE__FUNCTION_NOT_FOUND
			}
			return new ErrorType( message, feature, code )
		}
		
		return null
		
	}
	
	def static ErrorType returnErrorRuleString( String name, EStructuralFeature feature ) {

		var asm_origin = Utility.getOriginalAsm(name)
		var func = Utility.getRule(name)

		var String message
		var String code
		
		// check if type domain exists
		if ( func === null ) {
			// if the domain is not declared, check if it wasn't imported
			if ( Utility.isFunctionDeclaredPrivate(name) ) {
				message = "The rule '" + name + "' is not exported by the ASM '" + asm_origin + "'. It cannot be imported by this ASM"
				code = ErrorCode.BODY__RULE_NOT_IMPORTED
			}
			else {
				message = "'" + name + "' rule is not declared"
				code = ErrorCode.BODY__RULE_NOT_FOUND		
			}
			return new ErrorType( message, feature, code )
		}
		
		return null
		
	}

	def static ErrorType returnErrorVariableDeclared( String term_name, EStructuralFeature feature, String code,
		List<VariableTerm> list, EObject parent ) {
	
		var String message 

		var boolean errorOK = false
		var String error_var = ""
		
		for ( VariableTerm v : list ) {
			if ( !errorOK ) {
			//	println("check per: " + v.name + " " + let_term)
				if ( !Utility.isVariableDeclaredOnceByMe(v.name, parent) ){
					error_var = v.name
					
					if (parent instanceof Rule)
						v.kind = VariableKind.RULE_VAR
					else if (parent instanceof LocationTerm)
						v.kind = VariableKind.LOCATION_VAR
					else
						v.kind = VariableKind.LOGICAL_VAR
					
					errorOK = true
				} 
			}
		}
		
		if ( errorOK ) {
			message = 'The variable "' + error_var + '" cannot be bound to a value in the "' + term_name + '". It is already used.'
			return new  ErrorType( message, feature, code )
		}
		
		return null
		 
	
	}
	// ErrorCode.CHOOSE_RULE__RANGE_NOT_POWERSET
	// 				code = ErrorCode.CHOOSE_RULE__DOMAINS_NOT_COMPATIBLE
	 //	var EStructuralFeature feature = AsmetalPackage.Literals.FORALL_RULE__RANGES
	def static ErrorType returnErrorPowersetRanges(List<Term> ranges_list, List<VariableTerm> variables_list, EStructuralFeature feature, 
		String code1, String code2 ) {
	
		var String message
		
		var DomainTree term_dom
		var DomainTree var_dom
		var Term range
		var VariableTerm variable
		
		for ( var int i = 0 ; i < ranges_list.size ; i++ ) {
			
			range = ranges_list.get(i)
			variable = variables_list.get(i)
			
			if ((range instanceof SetTerm && ((range as SetTerm).start_term !== null)) || !(range instanceof SetTerm)) {
			
				// check if the range is powerset			
				if ( !Utility.isPowerSetDomain(range) ) {
					message = "The domain of the variables ranges must be a PowersetDomain"
					return new ErrorType( message, feature, code1 )
				}
				
				// check compatibility
				term_dom = DomainCalculator.getDomainTerm(range)
				var_dom = DomainCalculator.getDomainTerm(variable)
									
				if ( !Utility.isCompatible( term_dom, var_dom ) ) {
					message = " The domain of the variables ranges must be a PowersetDomain whose base domain must be equal to the variable type domain"
					return new ErrorType( message, feature, code2 )
				}
			
			}
			
		}
		
		
		return null
	
	
	}
	
	
}