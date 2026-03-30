package org.asmeta.xt.validation.validators

import org.asmeta.xt.asmetal.BagDomain
import org.asmeta.xt.asmetal.BasicTD
import org.asmeta.xt.asmetal.CompassionConstraint
import org.asmeta.xt.asmetal.ConcreteDomain
import org.asmeta.xt.asmetal.CtlSpec
import org.asmeta.xt.asmetal.Domain
import org.asmeta.xt.asmetal.EnumElement
import org.asmeta.xt.asmetal.FairnessConstraint
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
import org.asmeta.xt.asmetal.TemporalProperty
import org.asmeta.xt.validation.checkers.DefinitionChecker
import org.asmeta.xt.validation.utility.ErrorType
import org.asmeta.xt.validation.utility.Utility

class DefinitionValidator {
	
	def static ErrorType checkError( Domain domain ) {
		 
		var ErrorType error = null
		
		if ( domain.eContainer instanceof Signature ) {
			error = DefinitionChecker.isDeclaredOnlyOnce( domain )
			if ( error !== null ) return error	
		}
		
		return null
		
	}
	
	def static ErrorType checkError( Function function ) {
		 
		var ErrorType error = null
		
		if ( function.eContainer instanceof Signature ) {
			error = DefinitionChecker.isDeclaredOnlyOnce( function )
			if ( error !== null ) return error	
			
			error = DefinitionChecker.isFunctionDomainOK( function );
			if ( error !== null ) return error	
			
			error = DefinitionChecker.isFunctionCodomainOK( function );
			if ( error !== null ) return error	

		}
	
		return null
		
	}
	
	
	def static ErrorType checkError( LocalFunction function ) {
		
		var ErrorType error
		
		error = DefinitionChecker.isParentOK( function )
		if ( error !== null ) return error	
		
		// TODO vedere se serve
		//error = checkError( function as Function )
			
		return null
	
	}
	
	
	def static ErrorType checkError( ConcreteDomain domain ) {
		 
		var ErrorType error = null
		
		if ( domain.eContainer instanceof Signature ) {
			error = DefinitionChecker.isTypeDomainOK( domain )
			if ( error !== null ) return error	
		}
		
		return null
		
	}
	
	def static ErrorType checkError( BasicTD domain ) {
		 
		var ErrorType error = null
		
		if ( domain.eContainer instanceof Signature ) {
			error = DefinitionChecker.isDomainNameOK( domain )
			if ( error !== null ) return error	
		}
		
		return null
		
	}
	
	def static ErrorType checkError( BagDomain domain ) {	 
		var ErrorType error = null
		
		error = DefinitionChecker.isCoreOK( domain )
		if ( error !== null ) return error	
		
		return null
	}
	
	def static ErrorType checkError( SequenceDomain domain ) {	 
		var ErrorType error = null
		
		error = DefinitionChecker.isCoreOK( domain )
		if ( error !== null ) return error	
		
		return null
	}
	
	def static ErrorType checkError( PowersetDomain domain ) {	 
		var ErrorType error = null
		
		error = DefinitionChecker.isCoreOK( domain )
		if ( error !== null ) return error	
		
		return null
	}
	
	def static ErrorType checkError( MapDomain domain ) {	 
		var ErrorType error = null
		
		error = DefinitionChecker.isCoreOK( domain )
		if ( error !== null ) return error	
		
		return null
	}
	
	def static ErrorType checkError( RuleDomain domain ) {	 
		var ErrorType error = null
		
		error = DefinitionChecker.isCoreOK( domain )
		if ( error !== null ) return error	
		
		return null
	}
	
		
	def static ErrorType checkError( EnumElement element ) {
		 
		var ErrorType error = null
		
		error = DefinitionChecker.isDeclaredOnlyOnce( element )
		if ( error !== null ) return error	
	
		return null
		
	}
	
	def static ErrorType checkError( ProductDomain domain ) {	 
		var ErrorType error = null
		
		error = DefinitionChecker.isCoreOK( domain )
		if ( error !== null ) return error	
		
		return null
	}
	
	def static ErrorType checkError( RuleDeclaration rule_declaration ) {
		 
		Utility.fillVariableMap( rule_declaration )
		 
		var ErrorType error = null
		 
		error = DefinitionChecker.isDefineOnceOK( rule_declaration )
		if ( error !== null ) return error
		
		error = DefinitionChecker.isVariableListOK( rule_declaration )
		if ( error !== null ) return error
		
		error = DefinitionChecker.isDomainListOK( rule_declaration )
		if ( error !== null ) return error
				
		return null
		
	}
	
	def static ErrorType checkError( Invariant invariant ) {
		
		Utility.resetVariableMap()
		
		var ErrorType error = null

		error = DefinitionChecker.isInvariantDomainOK( invariant )
		if ( error !== null ) return error
				
		return null
		
	}
		
	def static ErrorType checkError( InvariantElement invariant_element ) {
		 
		var ErrorType error = null

		error = DefinitionChecker.isConstrainedElementOK( invariant_element )
		if ( error !== null ) return error
				
		return null
		
	}
	
	def static ErrorType checkError( TemporalProperty spec ) {
		
		Utility.resetVariableMap()
		
		if ( spec instanceof CtlSpec ) return checkError(spec)
		else if ( spec instanceof LtlSpec ) return checkError(spec)
		else return null
		
	}
	
	def static ErrorType checkError( CtlSpec ctl_spec ) {
		 
		var ErrorType error = null

		error = DefinitionChecker.hasUniqueName( ctl_spec )
		if ( error !== null ) return error
		
		error = DefinitionChecker.isInvariantDomainOK( ctl_spec )
		if ( error !== null ) return error
				
		return null
		
	}
	
	def static ErrorType checkError( LtlSpec ltl_spec ) {
		 
		var ErrorType error = null

		error = DefinitionChecker.hasUniqueName( ltl_spec )
		if ( error !== null ) return error
		
		error = DefinitionChecker.isInvariantDomainOK( ltl_spec )
		if ( error !== null ) return error
				
		return null
		
	}
	
	def static ErrorType checkError( FairnessConstraint spec ) {
		
		if ( spec instanceof JusticeConstraint ) return checkError(spec)
		else if ( spec instanceof CompassionConstraint ) return checkError(spec)
		else return null
		
	}
	
	def static ErrorType checkError( JusticeConstraint justice ) {
		 
		var ErrorType error = null

		error = DefinitionChecker.isInvariantDomainOK( justice )
		if ( error !== null ) return error
				
		return null
		
	}
	
	def static ErrorType checkError( CompassionConstraint compassion ) {
		 
		var ErrorType error = null

		error = DefinitionChecker.isInvariantDomainOK( compassion )
		if ( error !== null ) return error
				
		return null
		
	}
	
		
	def static ErrorType checkError( InvariantConstraint invar ) {
		 
		Utility.resetVariableMap()
		 
		var ErrorType error = null

		error = DefinitionChecker.isInvariantDomainOK( invar )
		if ( error !== null ) return error
				
		return null
		
	}
	
}