package org.asmeta.xt.validation.validators

import org.asmeta.xt.asmetal.AgentInitialization
import org.asmeta.xt.asmetal.Asm
import org.asmeta.xt.asmetal.DomainDefinition
import org.asmeta.xt.asmetal.DomainInitialization
import org.asmeta.xt.asmetal.ExportClause
import org.asmeta.xt.asmetal.FunctionDefinition
import org.asmeta.xt.asmetal.FunctionInitialization
import org.asmeta.xt.asmetal.Header
import org.asmeta.xt.asmetal.ImportClause
import org.asmeta.xt.asmetal.Initialization
import org.asmeta.xt.asmetal.Signature
import org.asmeta.xt.validation.checkers.StructureChecker
import org.asmeta.xt.validation.utility.ErrorType
import org.asmeta.xt.validation.utility.WarningType
import org.asmeta.xt.validation.utility.Utility
import org.asmeta.xt.validation.utility.MessageType

/** 
  * The class call the single validation rule and return the errors
  */
class StructureValidator {
	
	def static ErrorType checkError( Asm asm ) {
		
		var ErrorType error = null
		
		error = StructureChecker.isAsmNameOK(asm)
		if ( error !== null ) return error
		
		error = StructureChecker.isMainRuleArityOK(asm)
		if ( error !== null ) return error
		
		return null;		
		
	}

	def static WarningType checkWarning( Asm asm ) {
		
		var WarningType warning = null
			
		warning = StructureChecker.isModuleMainRuleOK(asm)
		if ( warning !== null ) return warning
		
		warning = StructureChecker.isModuleDefaultInitialStateOK(asm)
		if ( warning !== null ) return warning
		
		warning = StructureChecker.isModuleInitialStateOK(asm) 
		if ( warning !== null ) return warning
		
		return null		
		
	}
	
	def static ErrorType checkError( ImportClause import_clause ) {
		 
		var ErrorType error = null

		error = StructureChecker.isImportedClauseModuleNameOK( import_clause )
		if ( error !== null ) return error

		error = StructureChecker.isImportedListOK(import_clause)
		if ( error !== null ) return error
		
		return null
		
	}
	
	
	def static WarningType checkWarning( ImportClause import_clause ) {

		var WarningType warning = null

		warning = StructureChecker.haveSomethingToImport( import_clause )
		if ( warning !== null ) return warning
		
		return null
		
	}
	
	// it can return an error or a warning
	// null if there is no error
	def static MessageType checkError( ExportClause export_clause ) {
		 
		var Header header = export_clause.eContainer as Header
		var Asm asm = header.eContainer as Asm

		return StructureChecker.isExportedListOK(export_clause, header.signature, asm.bodySection)
	}
	
	def static ErrorType checkError( Signature signature ) {
		 
		var ErrorType error = null

		error = StructureChecker.isFunctionListOK( signature )
		if ( error !== null ) return error
		
		return null
		
	}
	
	def static ErrorType checkError( DomainDefinition domain_definition ) {
		 
		var ErrorType error = null
		
		error = StructureChecker.isDefinedOnceOK( domain_definition )
		if ( error !== null ) return error

		error = StructureChecker.isDeclaredDomainOK( domain_definition.definedDomainName )
		if ( error !== null ) return error
		
		error = StructureChecker.isBodyOK( domain_definition )
		if ( error !== null ) return error
		
		try{	
			Utility.domain_declarations_map.put(domain_definition.definedDomain, domain_definition)
		} catch (ClassCastException c) { }
		return null
		
	}
	
	def static ErrorType checkError( FunctionDefinition function_definition ) {
		 
		var ErrorType error = null
		
		Utility.fillVariableMap( function_definition )
		
		error = StructureChecker.isDefinedOnceOK( function_definition )
		if ( error !== null ) return error

		error = StructureChecker.isVariableListOK( function_definition )
		if ( error !== null ) return error
		
		error = StructureChecker.isDomainListOK( function_definition )
		if ( error !== null ) return error
		
		error = StructureChecker.isDefinedFunctionOK( function_definition )
		if ( error !== null ) return error
		
		error = StructureChecker.isBodyOK( function_definition )
		if ( error !== null ) return error
		
		return null
		
	}
	
	def static ErrorType checkError( Initialization init ) {
	
		return null
		
	}
	
	
	def static ErrorType checkError( DomainInitialization domain_init ) {
		 
		var ErrorType error = null
		
		error = StructureChecker.isDefineOnceOK( domain_init )
		if ( error !== null ) return error
		
		error = StructureChecker.isInizializedDomainOK( domain_init.initializedDomain )
		if ( error !== null ) return error
		
		error = StructureChecker.isBodyOK( domain_init )
		if ( error !== null ) return error
		
		return null
		
	}
	
	def static ErrorType checkError( FunctionInitialization function_init ) {
		 
		var ErrorType error = null
		
		Utility.fillVariableMap( function_init )
		
		error = StructureChecker.isDefinedOnceOK( function_init )
		if ( error !== null ) return error

		error = StructureChecker.isVariableListOK( function_init )
		if ( error !== null ) return error
		
		error = StructureChecker.isDomainListOK( function_init )
		if ( error !== null ) return error

		error = StructureChecker.isInizializedFunctionOK( function_init )
		if ( error !== null ) return error
		
		error = StructureChecker.isBodyOK( function_init )
		if ( error !== null ) return error
		
		return null
		
	}
	
	
	def static ErrorType checkError( AgentInitialization agent_inut ) {
		 
		var ErrorType error = null
				
		error = StructureChecker.isDomainOK( agent_inut )
		if ( error !== null ) return error

		error = StructureChecker.isProgramOK( agent_inut )
		if ( error !== null ) return error
		
		return null
		
	}
	

}