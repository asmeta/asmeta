package org.asmeta.xt.validation.checkers

import java.util.HashMap
import org.asmeta.xt.asmetal.AsmetalFactory
import org.asmeta.xt.asmetal.BagDomain
import org.asmeta.xt.asmetal.BasicTD
import org.asmeta.xt.asmetal.Body
import org.asmeta.xt.asmetal.CompassionConstraint
import org.asmeta.xt.asmetal.ConcreteDomain
import org.asmeta.xt.asmetal.CtlSpec
import org.asmeta.xt.asmetal.Domain
import org.asmeta.xt.asmetal.EnumElement
import org.asmeta.xt.asmetal.EnumTD
import org.asmeta.xt.asmetal.Function
import org.asmeta.xt.asmetal.InvariantConstraint
import org.asmeta.xt.asmetal.InvariantElement
import org.asmeta.xt.asmetal.Invariant
import org.asmeta.xt.asmetal.JusticeConstraint
import org.asmeta.xt.asmetal.LocalFunction
import org.asmeta.xt.asmetal.LtlSpec
import org.asmeta.xt.asmetal.MapDomain
import org.asmeta.xt.asmetal.PowersetDomain
import org.asmeta.xt.asmetal.ProductDomain
import org.asmeta.xt.asmetal.RuleDeclaration
import org.asmeta.xt.asmetal.RuleDomain
import org.asmeta.xt.asmetal.SequenceDomain
import org.asmeta.xt.asmetal.Signature
import org.asmeta.xt.asmetal.StructuredTD
import org.asmeta.xt.asmetal.TypeDomain
import org.asmeta.xt.validation.ErrorCode
import org.asmeta.xt.validation.utility.ErrorType
import org.asmeta.xt.validation.utility.Utility
import org.eclipse.emf.ecore.EStructuralFeature
import org.asmeta.xt.asmetal.AsmetalPackage

class DefinitionChecker {
	
	
	def static isDeclaredOnlyOnce(Domain domain) {
		var String message
		var String code
		var EStructuralFeature feature = AsmetalPackage.Literals.DOMAIN__NAME
		
		if (  domain.eContainer instanceof Signature ) {
			
			var HashMap<String, String> names = new HashMap
			var String name		
			var Signature signature = domain.eContainer as Signature

			for ( Domain dom : signature.domain ) {
				name = dom.name
				if ( names.containsKey(name) && domain.equals(dom) ) {
						message = "Domain '" + name + "' already declared"
						code = ErrorCode.SIGNATURE__DOMAIN_DEFINED_TWICE
						return new ErrorType( message, feature, code )
				}  
				else names.put(name, dom.name)
			}
			
		}

		return null
	}

	def static isDeclaredOnlyOnce(Function function) {
		var String message
		var String code
		var EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION__NAME
		
		if (  function.eContainer instanceof Signature ) {
			
			var HashMap<String, String> names =  new HashMap
			var String name		
			var Signature signature = function.eContainer as Signature
			
			for ( Function func : signature.function ) {
				name = Utility.calculateFunctionCode(func)
				if ( names.containsKey(name) && function.equals(func) ) {
						message = "Function '" + name + "' already declared"
						code = ErrorCode.SIGNATURE__FUNCTION_DEFINED_TWICE
						return new ErrorType( message, feature, code )
				}  
				else names.put(name, func.name)
			}
			
		}
		
		return null
	}
	
	def static ErrorType isFunctionDomainOK(Function function) {
		if ( function.domain === null ) return null
		return SharedCheckers.returnErrorDomain( function.domain, AsmetalPackage.Literals.FUNCTION__DOMAIN )		
	}
	
	def static ErrorType isFunctionCodomainOK(Function function) {
		return SharedCheckers.returnErrorDomain( function.codomain, AsmetalPackage.Literals.FUNCTION__CODOMAIN )		
	}
	
	
	def static isTypeDomainOK(ConcreteDomain domain) {
		
		var String message
		var String code
		var EStructuralFeature feature = AsmetalPackage.Literals.CONCRETE_DOMAIN__TYPE_DOMAIN
		
		// if the domain is structured, we know it's a TypeDomain
		if ( domain.typeDomain instanceof StructuredTD ) return null
		
		var error = SharedCheckers.returnErrorDomain(domain.typeDomain, feature)
		if ( error !== null ) return error
		
		var name = Utility.calculateDomainCode(domain.typeDomain)
		var dom = Utility.getDomain(name)
	
		// check if type domain is TypeDomain
		if ( !(dom instanceof TypeDomain) ) {
			message = "A concrete domain cannot be defined over a concrete domain"
			code = ErrorCode.CONCRETE_DOMAIN__INVALID_TYPE_DOMAIN
			return new ErrorType( message, feature, code )
		}

	}
	
	def static isDomainNameOK( BasicTD domain ) {
		
		var String message = "A basic domain " + domain.name + " not allowed has been declared."
		var String code = ErrorCode.BASIC_DOMAIN__INVALID_NAME
		var EStructuralFeature feature = AsmetalPackage.Literals.DOMAIN__NAME
		
		var String[] allowed_name = #[ "Integer", "Natural", "Real", "Complex", "String" , "Char", "Boolean", "Undef" ]
		var boolean okName = false
		
		for ( var int i = 0 ; i < allowed_name.length ; i++ ) {
			if ( !okName && domain.name.equals( allowed_name.get(i) ) ) okName = true
		}		
		
		if ( !okName ) return new ErrorType( message, feature, code )
		else return null
		
	}
	
	def static ErrorType isCoreOK(BagDomain domain) {
		// check if the domain exist
		return SharedCheckers.returnErrorDomain(domain.domain, AsmetalPackage.Literals.BAG_DOMAIN__DOMAIN )
	}
	
	def static ErrorType isCoreOK(PowersetDomain domain) {
		// check if the domain exist
		return SharedCheckers.returnErrorDomain(domain.baseDomain, AsmetalPackage.Literals.POWERSET_DOMAIN__BASE_DOMAIN)
	}
	
	def static ErrorType isCoreOK(SequenceDomain domain) {
		// check if the domain exist
		return SharedCheckers.returnErrorDomain(domain.domain, AsmetalPackage.Literals.SEQUENCE_DOMAIN__DOMAIN)
	}
	
	def static ErrorType isCoreOK(MapDomain domain) {
		// check if the domain exist
		var error = SharedCheckers.returnErrorDomain(domain.sourceDomain, AsmetalPackage.Literals.MAP_DOMAIN__SOURCE_DOMAIN )
		if ( error !== null ) return error
		
		return SharedCheckers.returnErrorDomain(domain.targetDomain, AsmetalPackage.Literals.MAP_DOMAIN__TARGET_DOMAIN )
	}
	
	def static isCoreOK(ProductDomain domain) {
		// check if the domain exist
		var ErrorType error
		for ( Domain dom : domain.domains ) {
			error = SharedCheckers.returnErrorDomain(dom, AsmetalPackage.Literals.DOMAIN__NAME )
			if ( error !== null ) return error
		}
		return null
	}
	
	def static isCoreOK(RuleDomain domain) {
		// check if the domain exist
		var ErrorType error
		for ( Domain dom : domain.domains ) {
			error = SharedCheckers.returnErrorDomain(dom, AsmetalPackage.Literals.DOMAIN__NAME)
			if ( error !== null ) return error
		}
		return null
	}
	
	
	
	def static ErrorType isParentOK(LocalFunction function) {
		
		var String message = "An ASM signature can't declare local functions"
		var String code = ErrorCode.LOCAL_FUNCTION__INVALID_DECLARATION
		var EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION__NAME
		
		if ( function.eContainer instanceof Signature ) new  ErrorType( message, feature, code )
		else return null
	}
	
	def static ErrorType isDeclaredOnlyOnce(EnumElement element) {
		var String message
		var String code = ErrorCode.ENUM_DOMAIN_ALREADY_DECLARED
		var EStructuralFeature feature = AsmetalPackage.Literals.ENUM_ELEMENT__SYMBOL
		
			
		var HashMap<String, String> names = new HashMap
		var EnumTD domain = element.eContainer as EnumTD
		var Signature signature = domain.eContainer as Signature

		for ( Domain dom : signature.domain ) {
			
			if ( dom instanceof EnumTD ) {				
				for ( EnumElement el : dom.element ) {
					if ( names.containsKey(el.symbol) && element.equals(el) ) {
						message = "Element '" + el.symbol + "' already declared"
						return  new ErrorType( message, feature, code )
					}  
					else names.put(el.symbol, el.symbol)
				}
			}	
		}

		return null
	}
	
	
	/**
	 * Check if the rule is declared multiple times
	 * <ul>
	 * 	<li>Check rule name</li>
	 * 	<li>Check partial overload (only the number of the arguments of the rule)</li>
	 * </ul>
	 */		
	def static isDefineOnceOK(RuleDeclaration rule_declaration) {
		
		if (  rule_declaration.eContainer instanceof Body ) {
			
			var String rule_string = Utility.getRuleaAsString(rule_declaration)
			var String message = "The rule " + rule_string + " has been declared twice."
			var String code = ErrorCode.RULE_DECLARATION__DEFINED_TWICE
			var EStructuralFeature feature = AsmetalPackage.Literals.RULE_DECLARATION__NAME
			
			// get the body
			var Body body = rule_declaration.eContainer as Body
			var HashMap<String, String> rules_string = new HashMap
			
			for ( RuleDeclaration rule : body.ruleDeclaration ) {
				var String r_string = Utility.getRuleaAsString(rule)
				if ( rules_string.containsKey(r_string) /*&& rule.name.equals(rule_declaration.name)*/ ) {
					// the name was already on the list
					// check if the two functions have the same arguments
					return new ErrorType( message, feature, code )
				}					
				else {
					// insert the name of the rule in the hashmap
					rules_string.put( r_string, r_string )	
				}
			}
			
	
		}
		
		return null
		
	}
	
	def static ErrorType isVariableListOK(RuleDeclaration rule_declaration) {
		
		var String message 
		var String code = ErrorCode.RULE_DECLARATION__INVALID_VARIABLE
		var EStructuralFeature feature = AsmetalPackage.Literals.RULE_DECLARATION__NAME
		
		var HashMap<String, String> names = new HashMap
		
		for ( String variable : rule_declaration.variables) {
			if ( names.containsKey(variable) ) {
				message = "The variable " + variable + " cannot be used as parameter. It is already used."
				return new ErrorType( message, feature, code )
			}
			else names.put( variable, variable )
		}
	}
	
	def static ErrorType isDomainListOK(RuleDeclaration rule_declaration) {

		var EStructuralFeature feature = AsmetalPackage.Literals.RULE_DECLARATION__NAME
		
		var ErrorType error
		
		for ( Domain domain : rule_declaration.domain) {
			error = SharedCheckers.returnErrorDomain( domain, feature )
			if ( error !== null ) return error
		}
		
		return null
		
	}
	
	def static ErrorType isConstrainedElementOK(InvariantElement invariant_element) {

		var EStructuralFeature feature
		var String code
		var String message

		var String name = ""
		
		if ( invariant_element.domainList === null || invariant_element.domainList.size == 0 ) 
			name = invariant_element.constrainedName
		else
			name = invariant_element.constrainedName + 
				"(" Utility.calculateDomainCodeFromList(invariant_element.domainList) + ")"
		
		if ( !Utility.isInMaps(name) ) {
			
			feature = AsmetalPackage.Literals.INVARIANT_ELEMENT__CONSTRAINED_NAME
			message = "Could't find '" + invariant_element.constrainedName + "'"
			code = ErrorCode.INVARINT__NOT_FOUND
			
			return new ErrorType( message, feature, code )

		}
		
		var Invariant parent = invariant_element.eContainer as Invariant
		var HashMap<String, String> names = new HashMap
		
		// TODO sistemare con domain
		for ( InvariantElement el : parent.invar_list ) {
			if ( names.containsKey(el.constrainedName) && el.constrainedName.equals(invariant_element.constrainedName) ) {
				feature = AsmetalPackage.Literals.INVARIANT_ELEMENT__CONSTRAINED_NAME
				message = "'" + invariant_element.constrainedName + "' called twice"
				code = ErrorCode.INVARINT__CALLED_TWICE
				return new ErrorType( message, feature, code )
			}
			else names.put( el.constrainedName, el.constrainedName )
		}
		
		return null

	}
	
	def static ErrorType hasUniqueName(CtlSpec spec) {
		var String code = ErrorCode.CTL_SPEC__DUPLICATE_NAME
		return SharedCheckers.returnHasUniqueNameProprierty( spec, "CTL", code )
	}
	
	def static ErrorType hasUniqueName(LtlSpec spec) {
		var String code = ErrorCode.LTL_SPEC__DUPLICATE_NAME
		return SharedCheckers.returnHasUniqueNameProprierty( spec, "LTL", code )
	}
	
	
	
	def static ErrorType isInvariantDomainOK(Invariant inv) {
		var EStructuralFeature feature = AsmetalPackage.Literals.PROPERTY__NAME
		return SharedCheckers.returnErrorNotBoolean( inv.body, feature, "an invariant" )	
	}

	def static ErrorType isInvariantDomainOK(CtlSpec spec) {
		var EStructuralFeature feature = AsmetalPackage.Literals.PROPERTY__NAME
		return SharedCheckers.returnErrorNotBoolean( spec.body, feature, "a CTL property" )	
	}
	
	def static ErrorType isInvariantDomainOK(LtlSpec spec) {
		var EStructuralFeature feature = AsmetalPackage.Literals.PROPERTY__NAME
		return SharedCheckers.returnErrorNotBoolean( spec.body, feature, "a LTL property" )	
	}
	
	def static ErrorType isInvariantDomainOK(JusticeConstraint justice) {
		var EStructuralFeature feature = AsmetalPackage.Literals.JUSTICE_CONSTRAINT__BODY
		return SharedCheckers.returnErrorNotBoolean( justice.body, feature, "a JUSTICE constraint" )	
	}
	
	def static ErrorType isInvariantDomainOK(CompassionConstraint inv) {
		var EStructuralFeature feature 
		var part_msg = "a COMPASSION constraint"
		
		feature = AsmetalPackage.Literals.COMPASSION_CONSTRAINT__P
		var error = SharedCheckers.returnErrorNotBoolean( inv.p, feature, part_msg )	
		if ( error !== null ) return error
		
		feature = AsmetalPackage.Literals.COMPASSION_CONSTRAINT__Q
		return SharedCheckers.returnErrorNotBoolean( inv.q, feature, part_msg )	
	}
	
	def static ErrorType isInvariantDomainOK(InvariantConstraint inv) {
		var EStructuralFeature feature = AsmetalPackage.Literals.INVARIANT_CONSTRAINT__BODY
		return SharedCheckers.returnErrorNotBoolean( inv.body, feature, "an INVAR constraint" )	
	}


	
	
}