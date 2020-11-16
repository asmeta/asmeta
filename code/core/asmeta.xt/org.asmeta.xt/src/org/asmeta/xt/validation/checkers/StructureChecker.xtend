package org.asmeta.xt.validation.checkers

import java.util.ArrayList
import java.util.HashMap
import org.asmeta.xt.asmetal.AgentInitialization
import org.asmeta.xt.asmetal.Asm
import org.asmeta.xt.asmetal.AsmetalPackage
import org.asmeta.xt.asmetal.Body
import org.asmeta.xt.asmetal.ConcreteDomain
import org.asmeta.xt.asmetal.DerivedFunction
import org.asmeta.xt.asmetal.DomainDefinition
import org.asmeta.xt.asmetal.DomainInitialization
import org.asmeta.xt.asmetal.Domain
import org.asmeta.xt.asmetal.DynamicFunction
import org.asmeta.xt.asmetal.ExportClause
import org.asmeta.xt.asmetal.FunctionDefinition
import org.asmeta.xt.asmetal.FunctionInitialization
import org.asmeta.xt.asmetal.Function
import org.asmeta.xt.asmetal.ImportClause
import org.asmeta.xt.asmetal.Initialization
import org.asmeta.xt.asmetal.RuleDeclaration
import org.asmeta.xt.asmetal.SetTerm
import org.asmeta.xt.asmetal.Signature
import org.asmeta.xt.asmetal.StaticFunction
import org.asmeta.xt.asmetal.Term
import org.asmeta.xt.asmetal.importData
import org.asmeta.xt.validation.ErrorCode
import org.asmeta.xt.validation.utility.DomainCalculator
import org.asmeta.xt.validation.utility.DomainTree
import org.asmeta.xt.validation.utility.ErrorType
import org.asmeta.xt.validation.utility.Utility
import org.asmeta.xt.validation.utility.WarningType
import org.eclipse.emf.ecore.EStructuralFeature

class StructureChecker {
	
			
	/**
	 * constraint: asm.name == file name
	 * example:
	 * 		asm ATMcheck
	 * 		signature
	 * 		...
	 * the name of the file must be ATMcheck.asm		
	 */ 
	def static ErrorType isAsmNameOK( Asm asm ) {
		
		var path = asm.eResource.URI.toFileString
		if ( path === null ) return null
		
		if (path.contains("\\")) path = path.replace("\\","/")
		
		var String code = ErrorCode.ASM__INVALID_NAME
		var EStructuralFeature feature = AsmetalPackage.Literals.ASM__NAME

		var path_split = path.split('/')
		
		var file_name = path_split.get( path_split.length-1 )
		var name_no_extension = file_name.substring( 0, file_name.length-4 )
		
		var String message = 'The file name "'+ name_no_extension +'" is not equal to the asm name "' + asm.name + '"'
			
		if ( !name_no_extension.equals( asm.name ) ) return new ErrorType( message, feature, code )
		else return null

		
	}
		
	/**
	 * OCL constraint A1
	 *  constraint: mainrule->notEmpty() implies mainrule.arity=0	 
	 */
	def static ErrorType isMainRuleArityOK(Asm asm) {		
		
		var String message = 'The arity of the main rule must be 0'
		var String code = ErrorCode.ASM__INVALID_MAINRULE_ARITY
		var EStructuralFeature feature = AsmetalPackage.Literals.ASM__MAINRULE
		
		if ( Utility.calculateRuleArity( asm.mainrule ) != 0 ) return new ErrorType( message, feature, code )
		else return null
	}
	
	/**
	 * If the asm is a module, it should't have a main rule
	 */
	 def static WarningType isModuleMainRuleOK(Asm asm) {	
	 	
	 	var String message = '"' + asm.name + '" is a module. It cannot have a Main Rule'
		var String code = ErrorCode.ASM__MODULE_MAINRULE
		var EStructuralFeature feature = AsmetalPackage.Literals.ASM__MAINRULE
		
		// if the asm is an asm, do not check
		if (  asm.type.equals('asm') ) return null
		// check if it's a module
		if ( asm.type.equals('module') && asm.mainrule !== null ) new WarningType( message, feature, code )
		else return null
	}
	
	/**
	 * If the asm is a module, it should't have a default initial state
	 */
	 def static WarningType isModuleDefaultInitialStateOK(Asm asm) {		
	 	
	 	var String message = '"' + asm.name + '" is a module. It cannot have any initial state'
		var String code = ErrorCode.ASM__MODULE_DEF_INITIAL_STATE
		var EStructuralFeature feature = AsmetalPackage.Literals.ASM__DEFAULT_INITIAL_STATE
		
		// if the asm is an asm, do not check
		if (  asm.type.equals('asm') ) return null
		// check if it's a module
		if ( asm.type.equals('module') && asm.defaultInitialState !== null ) new WarningType( message, feature, code )
		else return null
	}
	
	/**
	 * If the asm is a module, it should't have any initial state
	 */
	 def static WarningType isModuleInitialStateOK(Asm asm) {		
	 	
	 	var String message = '"' + asm.name + '" is a module. It cannot have any initial state'
		var String code = ErrorCode.ASM__MODULE_INITIAL_STATE
		var EStructuralFeature feature = AsmetalPackage.Literals.ASM__INITIAL_STATE
		
		// if the asm is an asm, do not check
		if (  asm.type.equals('asm') ) return null
		// check if it's a module
		if ( asm.type.equals('module') && asm.initialState.size !== 0 ) new WarningType( message, feature, code )
		else return null

	}
	
	/**
	 * Check if the imported file exist
	 */
	def static ErrorType isImportedClauseModuleNameOK(ImportClause importClause) {
		// get the base dir for the import (start from here)
		var dir = Utility.getBaseDir(importClause);
		// get the location for the imported file
		// it could be also absolute in some cases
		var absolutePath = Utility.getAbsoluteAddressAsm(importClause.moduleName,dir)
		if (absolutePath === null){
		 		var String message = "File "+ importClause.moduleName + " not found in " + dir  
				var String code = ErrorCode.IMPORT_CLAUSE__FILE_NOT_FOUND
				var EStructuralFeature feature = AsmetalPackage.Literals.IMPORT_CLAUSE__MODULE_NAME
				return new ErrorType( message, feature, code )
		}
		else 
		return null		
	}
	
	def static ErrorType isImportedListOK(ImportClause importClause) {

		var String message
		var String code
		var EStructuralFeature feature

		if ( importClause.importedList !== null || importClause.importedList.size > 0 ) {
			
			feature = AsmetalPackage.Literals.IMPORT_CLAUSE__MODULE_NAME
			var HashMap<String, String> names = new HashMap

			// check existing and duplicate data
			// TODO SELEZIONARE SOLO OGGETTO
			var String s
			for ( importData data : importClause.importedList  ) {
				s = data.name
				if ( names.containsKey(s) ) {
					message = "Found duplicate value '" + s + "'"
					code = ErrorCode.IMPORT_CLAUSE__CALLED_TWICE
					return new ErrorType( message, feature, code, data )
				}  
				else names.put(s, s)
				
				if ( !Utility.isInImportedMap(s)  ) {
				  	message = "Could't find '" + s + "'"
					code = ErrorCode.IMPORT_CLAUSE__NOT_FOUND
					return new ErrorType( message, feature, code, data )
				}
				
			}
			
		}

		return null
	}
	
	 def static WarningType haveSomethingToImport(ImportClause importClause) {
	 	
	 	// get the directory	 			
	 	var dir = Utility.getBaseDir(importClause);
	 	// parse the map
		var Asm asm = Utility.getImportedAsm( importClause.moduleName,dir)
	 	
	 	var String message = "The ASM '" + asm.name + "' does not export any function or rule. It cannot be imported by this ASM"
	 	var String code = ErrorCode.IMPORT_CLAUSE__NOTHING_TO_IMPORT
		var EStructuralFeature feature = AsmetalPackage.Literals.IMPORT_CLAUSE__MODULE_NAME	
		
		// check if the asm have something to export
		if ( asm.headerSection.exportClause === null ) return new WarningType( message, feature, code )
		
		if ( asm.headerSection.exportClause.exportAll ) return null
		
		if ( asm.headerSection.exportClause.exportedList.size === 0 ) return new WarningType( message, feature, code )
		
		// fill map
		Utility.fillImportedMap(asm.headerSection, importClause )
		Utility.fillAllImportedMap(asm.headerSection, false)

		for ( String s :  asm.headerSection.exportClause.exportedList ) {
			if ( Utility.isFunctionName(s) || Utility.isRule(s) || Utility.isDomainName(s)) return null
		}
				
		return new WarningType( message, feature, code )

	}
	
	
	
	def static ErrorType isExportedListOK(ExportClause exportClause, Signature signature, Body body) {

		var String message
		var String code
		var EStructuralFeature feature
	
		feature = AsmetalPackage.Literals.EXPORT_CLAUSE__EXPORTED_LIST
			
		// fill with new data
		Utility.fillSignatureMap( signature, false )	
		Utility.fillBodyMap( body )
		
		var boolean okExport
	
		// check existing and duplicate data
		if ( exportClause.exportAll ) {
			
			// since we are exporting all rule and function, we have to check if
			// in the asm we have declared some rule or function
			okExport = ( signature.function !== null && signature.function.size > 0) || 
				( body.ruleDeclaration !== null && body.ruleDeclaration.size > 0)

		}
		else {
			
			var HashMap<String, String> names = new HashMap
			// TODO SELEZIONARE SOLO OGGETTO
			for ( String s : exportClause.exportedList  ) {
				
				if ( names.containsKey(s) ) {
					message = "Found duplicate value '" + s + "'"
					code = ErrorCode.EXPORT_CLAUSE__CALLED_TWICE
					return new ErrorType( message, feature, code )
				}  
				else names.put(s, s)
				
				if ( !Utility.isInSignatureMap(s)  ) {
				  	message = "Could't find '" + s + "'"
					code = ErrorCode.EXPORT_CLAUSE__NOT_FOUND
					return new ErrorType( message, feature, code )
				}
				
				if ( !okExport && ( Utility.isFunctionName(s) || Utility.isRule(s) || Utility.isDomainName(s)) ) okExport = true

			}
			
		}	
		
		if ( !okExport ) {
			message = "Error: An export clause must contain at least one Domain or one function or one rule"
			code = ErrorCode.EXPORT_CLAUSE__NOTHING_TO_EXPORT
			return new ErrorType( message, feature, code )
		}
		
		return null
	}	
		
	def static ErrorType isDeclaredDomainOK( String domain_name ) {
		
		var ErrorType error
		var String message
		var String code
		var feature = AsmetalPackage.Literals.DOMAIN_DEFINITION__DEFINED_DOMAIN_NAME
		
		// check if the domain exists
		error = SharedCheckers.returnErrorDomainString( domain_name, feature )
		if ( error !== null ) return error
		
		// check if the domain is concrete
		var dom = Utility.getDomain(domain_name)
		if ( !(dom instanceof ConcreteDomain) ) {
			message =  "The domain '" + domain_name + "' is not a concrete-domain. It cannot be defined."
			code = ErrorCode.DOMAIN_DEFINITION__DOMAIN_NOT_CONCRETE		
			return new ErrorType( message, feature, code )
		}	
		
		var concrete_domain = dom as ConcreteDomain
		if ( concrete_domain.dynamic ) {
			message =  "Dynamic domains must be initialized, not defined"
			code = ErrorCode.DOMAIN_DEFINITION__IS_DYNAMIC_DOMAIN		
			return new ErrorType( message, feature, code )
		}

		return null 
	}
	
		
	def static ErrorType isFunctionListOK(Signature signature) {
		
		
		var String message
		var String code
		var EStructuralFeature feature = AsmetalPackage.Literals.SIGNATURE__FUNCTION
		
		var HashMap<String, String> names = new HashMap
		var int i = 0
		var String name
		
		Utility.fillOnlyDomainMap( signature )
		
		// check function twice and if domain exist
		for ( Function function : signature.function ) {
		
			// check if the function is declared twice
			name = Utility.calculateFunctionCode(function)
			if ( names.containsKey(name) ) {
					message = "Function '" + name + "' already declared"
					code = ErrorCode.SIGNATURE__FUNCTION_DEFINED_TWICE
					return new ErrorType( message, feature, code, function )
			}  
			else names.put(name, function.name)
			
			i++
		}
		
		return null
		
	}
	
	def static isDefinedOnceOK(DomainDefinition domain_definition) {
		
		var String message = "The domain '" + domain_definition.definedDomainName + "' has been defined twice"
		var String code = ErrorCode.DOMAIN_DEFINITION__DEFINED_TWICE
		var EStructuralFeature feature = AsmetalPackage.Literals.DOMAIN_DEFINITION__DEFINED_DOMAIN_NAME
		
		// get the body
		var Body body = domain_definition.eContainer as Body
		var HashMap<String, String> names = new HashMap
		
		for ( DomainDefinition definitions : body.domainDefinition ) {
			if ( names.containsKey(definitions.definedDomainName) && definitions.definedDomainName.equals(domain_definition.definedDomainName) ) 
				return new ErrorType( message, feature, code )
			else names.put( definitions.definedDomainName, definitions.definedDomainName )
		}
		
		return null
		
	}
	
			
	def static ErrorType isBodyOK( DomainDefinition domain_definition ) {
		
		var Term body = domain_definition.body
		var EStructuralFeature feature = AsmetalPackage.Literals.DOMAIN_DEFINITION__BODY
		var String message 
		var String code = ErrorCode.DOMAIN_DEFINITION__ILL_FORMED_BODY
			
		// only SetCT has a powerset domain
		if ( !(body instanceof SetTerm) ) {
			message = "The type-domain of the body of the domain definition must be a powerset"
			return new ErrorType( message, feature, code )	
		}
		
		// check if the set term is empty
		var set_term_body = body as SetTerm
		if ( set_term_body.start_term === null ) return null
		
		// check here if the data in the powerset are compatible with the domain
		var body_domain = DomainCalculator.getDomainTerm( body )
		var dom = Utility.getDomain( domain_definition.definedDomainName )
		
		if ( !Utility.isCompatible( body_domain, DomainTree.getTreeFromDomain(dom) ) ) {
			message = "The type-domain of the body of the domain definition is different from the domain"
			return new ErrorType( message, feature, code )	
		}
		
		return null
	}
	
	def static ErrorType isDefinedOnceOK(FunctionDefinition function_definition) {
		
		var String message = "The function '" + function_definition.definedFunctionName + "' has been defined twice"
		var String code = ErrorCode.FUNCTION_DEFINITION__DEFINED_TWICE
		var EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_DEFINITION__DEFINED_FUNCTION_NAME
		
		// get the body
		var Body body = function_definition.eContainer as Body
		var HashMap<String, String> names = new HashMap
		var ArrayList<FunctionDefinition> conflicts = new ArrayList
		
		// TODO calcolare codice di una function definition
		var String body_function_code = ""
		var String function_code = function_definition.definedFunctionName
		// creating the unique function code could slow the validator
		// so we check in two different stages if the function is defined twice
		// first stage: check if there are two function with the same name
		// second stage: if we have two or more function defined with the same name, 
		// 		we have to check if it is the same function or not
		
		// first stage
		for ( FunctionDefinition definitions : body.functionDefinition ) {
			body_function_code = definitions.definedFunctionName
			Utility.fillFunctionDefinitionMap(definitions)
			if ( function_code.equals(body_function_code) && names.containsKey(body_function_code) && !definitions.equals(function_definition) ) 
				conflicts.add( definitions )
			else names.put( body_function_code, body_function_code )
		}
		
		// second stage
		names.clear
		function_code = Utility.calculateFunctionCode(function_definition)

		// no conflicts
		
		if ( conflicts.size == 0 ) return null;

		// if the size is greater than 0, we add our function definitions and check 
		conflicts.add( function_definition ) 
		for ( FunctionDefinition definitions : conflicts ) {
			body_function_code = Utility.calculateFunctionCode(definitions)
			if (  function_code.equals(body_function_code) && names.containsKey(body_function_code) ) 
				return new ErrorType( message, feature, code )
			else names.put( body_function_code, body_function_code )
		}
		
		return null
		
	}	
	
	def static ErrorType isVariableListOK(FunctionDefinition function_definition) {
		
		var String message 
		var String code = ErrorCode.FUNCTION_DEFINITION__INVALID_VARIABLE
		var EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_DEFINITION__DEFINED_FUNCTION_NAME
		
		var HashMap<String, String> names = new HashMap
		
		for ( String variable : function_definition.variables) {
			if ( names.containsKey(variable) ) {
				message = "The variable " + variable + " cannot be used as parameter. It is already used."
				return new ErrorType( message, feature, code )
			}
			else names.put( variable, variable )
		}
	}
	
	def static ErrorType isDomainListOK(FunctionDefinition function_definition) {

		var EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_DEFINITION__DEFINED_FUNCTION_NAME
		
		var ErrorType error
		
		for ( Domain domain : function_definition.domain) {
			error = SharedCheckers.returnErrorDomain( domain, feature )
			if ( error !== null ) return error
		}
		
		return null
		
	}
	
	def static ErrorType isDefinedFunctionOK(FunctionDefinition function_definition) {
		
		var String function_code = Utility.calculateFunctionCode(function_definition)
		var EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_DEFINITION__DEFINED_FUNCTION_NAME
		
		var error = SharedCheckers.returnErrorFunctionString(function_code, feature)
		if ( error !== null ) return error
		
		var func = Utility.getFunction(function_code)
		
		if ( func === null )
			func = Utility.getFunctionByName(function_code)
		
		if ( !(func instanceof DerivedFunction) && !(func instanceof StaticFunction) ) {
			var String message = "Only static and derived functions can be defined"
			var String code = ErrorCode.FUNCTION_DEFINITION__FUNCTION_NOT_STATIC_DERIVED
			return new ErrorType( message, feature, code )
		}
		
		return null
	}
	
	def static ErrorType isBodyOK( FunctionDefinition function_definition ) {
		
		var EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_DEFINITION__DEFINED_FUNCTION_NAME
		var String message 
		var String code = ErrorCode.FUNCTION_DEFINITION__ILL_FORMED_BODY

		var Term body = function_definition.body
		var body_domain = DomainCalculator.getDomainTerm( body )
		
		var String function_code = Utility.calculateFunctionCode(function_definition)
		
		var func = Utility.getFunction( function_code )
		
		if ( func === null ) {
			func = Utility.getFunctionByName( function_code )
		}
		// search right domain
		var function_domain = Utility.getDomain( Utility.calculateDomainCode(func.codomain) )
		var tree = DomainTree.getTreeFromDomain(function_domain)

		/*println("----------------")
		body_domain.printTree
		tree.printTree
		println("----------------")*/

		if ( !Utility.isCompatible( body_domain, tree ) ) {
			message = "The domain of the function body ("+ body_domain.model.root + ") must be compatible with the function codomain ("+ tree.model.root + ")"
			return new ErrorType( message, feature, code )	
		}
		
		return null
	}
	
	def static ErrorType isDefineOnceOK(DomainInitialization domain_init) {
		
		var String message = "Within an initial state a concrete domain can be initialized only once"
		var String code = ErrorCode.DOMAIN_INIZIALIZATION__DEFINED_TWICE
		var EStructuralFeature feature = AsmetalPackage.Literals.DOMAIN_INITIALIZATION__INITIALIZED_DOMAIN
		
		// get the body
		var Initialization init_state = domain_init.eContainer as Initialization
		var HashMap<String, String> names = new HashMap
		
		for ( DomainInitialization init : init_state.domainInitialization ) {
			if ( names.containsKey(init.initializedDomain) && init.initializedDomain.equals(domain_init.initializedDomain) ) 
				return new ErrorType( message, feature, code )
			else names.put( init.initializedDomain, init.initializedDomain )
		}
		
		return null
		
	}
		
	def static ErrorType isInizializedDomainOK(String domain_name) {
			
		var ErrorType error
		var String message
		var String code
		var feature = AsmetalPackage.Literals.DOMAIN_INITIALIZATION__INITIALIZED_DOMAIN 
		
		// check if the domain exists
		error = SharedCheckers.returnErrorDomainString( domain_name, feature )
		if ( error !== null ) return error
		
		// check if the domain is concrete
		var dom = Utility.getDomain(domain_name)
		if ( !(dom instanceof ConcreteDomain) ) {
			message =  "Static domains can't be initialized"
			code = ErrorCode.DOMAIN_INIZIALIZATION__IS_STATIC		
			return new ErrorType( message, feature, code )
		}	
		
		var concrete_domain = dom as ConcreteDomain
		if ( !concrete_domain.isDynamic() ) {
			message =  "Static domains can't be initialized"
			code = ErrorCode.DOMAIN_INIZIALIZATION__IS_STATIC		
			return new ErrorType( message, feature, code )
		}

		return null 
	}
	
	def static ErrorType isBodyOK( DomainInitialization domain_definition ) {
		
		var Term body = domain_definition.body
		var EStructuralFeature feature = AsmetalPackage.Literals.DOMAIN_INITIALIZATION__BODY
		var String message 
		var String code = ErrorCode.DOMAIN_INIZIALIZATION__ILL_FORMED_BODY
			
		// only SetCT has a powerset domain
		if ( !(body instanceof SetTerm) ) {
			message = "The type-domain of the body of the domain initialization must be a powerset"
			return new ErrorType( message, feature, code )	
		}
		
		// check if the set term is empty
		var set_term_body = body as SetTerm
		if ( set_term_body.start_term === null ) return null
		
		// check here if the data in the powerset are compatible with the domain
		var body_domain = DomainCalculator.getDomainTerm( body )
		var dom = Utility.getDomain( domain_definition.initializedDomain )
		
		if ( !Utility.isCompatible( body_domain, DomainTree.getTreeFromDomain(dom) ) ) {
			message = "The type-domain of the body of the domain initialization is different from the domain"
			return new ErrorType( message, feature, code )	
		}
		
		return null
	}
	
	def static ErrorType isDefinedOnceOK(FunctionInitialization function_init) {
		
		var String message = "The function '" + function_init.inizializedFunctionName + "' has been defined twice"
		var String code = ErrorCode.FUNCTION_INIZIALIZATION__DEFINED_TWICE
		var EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_INITIALIZATION__INIZIALIZED_FUNCTION_NAME
		
		Utility.fillFunctionInitializationMap(function_init)
		
		// get the body
		var Initialization init = function_init.eContainer as Initialization
		var HashMap<String, String> names = new HashMap
		var ArrayList<FunctionInitialization> conflicts = new ArrayList
		
		// TODO calcolare codice di una function definition
		var String init_function_code = ""
		var String function_code = function_init.inizializedFunctionName
		// creating the unique function code could slow the validator
		// so we check in two different stages if the function is defined twice
		// first stage: check if there are two function with the same name
		// second stage: if we have two or more function defined with the same name, 
		// 		we have to check if it is the same function or not
		
		// first stage
		for ( FunctionInitialization in : init.functionInitialization ) {
			init_function_code = in.inizializedFunctionName
			if ( function_code.equals(init_function_code) && names.containsKey(init_function_code) && !in.equals(function_init) ) 
				conflicts.add( in )
			else names.put( init_function_code, init_function_code )
		}
		
		// second stage
		names.clear
		function_code = Utility.calculateFunctionCode(function_init)

		// no conflicts
		if ( conflicts.size == 0 ) return null;

		// if the size is greater than 0, we add our function definitions and check 
		conflicts.add( function_init ) 
		for ( FunctionInitialization inits : conflicts ) {
			init_function_code = Utility.calculateFunctionCode(inits)
			if (  function_code.equals(init_function_code) && names.containsKey(init_function_code) ) 
				return new ErrorType( message, feature, code )
			else names.put( init_function_code, init_function_code )
		}
		
		return null
		
	}
	
	
	
	def static ErrorType isVariableListOK(FunctionInitialization function_init) {
		
		var String message 
		var String code = ErrorCode.FUNCTION_INIZIALIZATION__INVALID_VARIABLE
		var EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_INITIALIZATION__INIZIALIZED_FUNCTION_NAME
		
		var HashMap<String, String> names = new HashMap
		
		// check duplicate variable
		for ( String variable : function_init.variables) {
			if ( names.containsKey(variable) ) {
				message = "The variable " + variable + " cannot be used as parameter. It is already used."
				return new ErrorType( message, feature, code )
			}
			else names.put( variable, variable )
		}
		
	}
	
	def static ErrorType isDomainListOK(FunctionInitialization function_definition) {

		var EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_INITIALIZATION__INIZIALIZED_FUNCTION_NAME
		
		var ErrorType error
		
		for ( Domain domain : function_definition.domain) {
			error = SharedCheckers.returnErrorDomain( domain, feature )
			if ( error !== null ) return error
		}
		
		return null
		
	}
	
	def static ErrorType isInizializedFunctionOK(FunctionInitialization function_init) {
		
		var String function_code = Utility.calculateFunctionCode(function_init)
		var String message
		var String code
		var EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_INITIALIZATION__INIZIALIZED_FUNCTION_NAME

		var error = SharedCheckers.returnErrorFunctionString(function_code, feature)
		if ( error !== null ) return error
		
		var func = Utility.getFunction(function_code)
		
		// if the function is null, then 
		if ( func === null )
			func = Utility.getFunctionByName(function_code)

		if ( !(func instanceof DynamicFunction) ) {
			message = "The function " + function_code + " is not a dynamic function. It cannot be initialized"
			code = ErrorCode.FUNCTION_INIZIALIZATION__FUNCTION_NOT_DYNAMIC
			return new ErrorType( message, feature, code )
		}
		
		return null
	}
	
	/**
	 * Check if the domain of the body is compatible with the domain of the function
	 */
	def static ErrorType isBodyOK( FunctionInitialization function_definition ) {
				
		//println("*** Checking " + function_definition.inizializedFunction)
		
		var EStructuralFeature feature = AsmetalPackage.Literals.FUNCTION_INITIALIZATION__INIZIALIZED_FUNCTION_NAME
		var String message 
		var String code = ErrorCode.FUNCTION_INIZIALIZATION__ILL_FORMED_BODY

		var Term body = function_definition.body
		var body_domain = DomainCalculator.getDomainTerm( body )
		
		// if the domain of the body is undef, then the domain is always compatible
		if ( body_domain.codeFromTree.equals("Undef") ) {
			return null;
		}
		
		var String function_code = Utility.calculateFunctionCode(function_definition)
		var func = Utility.getFunction( function_code )
		
		if ( func === null ) {
			func = Utility.getFunctionByName( function_code )
		}
		// search right domain
		var function_domain = Utility.getDomain( Utility.calculateDomainCode(func.codomain) )
		var merged_domains = DomainTree.getTreeFromDomain(function_domain)
		
		if ( !Utility.isCompatible( body_domain, merged_domains ) ) {		
			message = "The domain of the function body must be compatible with the function codomain"
			return new ErrorType( message, feature, code )	
		}
		
		return null
	}
	
	def static ErrorType isDomainOK(AgentInitialization agent_init) {

		var EStructuralFeature feature = AsmetalPackage.Literals.AGENT_INITIALIZATION__DOMAIN_NAME
		
		var ErrorType error
		
		error = SharedCheckers.returnErrorDomainString( agent_init.domainName, feature )
		if ( error !== null ) return error
		
		// check if the agent domain exist
		var agent_domain = Utility.getDomain("Agent")
		
		if ( agent_domain === null ) {
			var String message = " The AgentDomain is not defined"
			var String code = ErrorCode.AGENT_INIZIALIZATION__AGENT_DOMAIN_NOT_DECLARED
			return new ErrorType( message, feature, code )
		}
		
		if ( !Utility.isAgentDomain(agent_init.domainName) ) {
			var String message = "The domain " + agent_init.domainName + " is not the Agent Domain or a subset of it."
			var String code = ErrorCode.AGENT_INIZIALIZATION__INVALID_DOMAIN
			return new ErrorType( message, feature, code )
		}
		
		return null
		
	}
	
	def static ErrorType isProgramOK(AgentInitialization agent_init) {
		
		var rule_name = agent_init.program.name
		var ArrayList<RuleDeclaration> program = Utility.getRule( rule_name )

		
		if ( program === null || program.size == 0) {
			var EStructuralFeature feature = AsmetalPackage.Literals.AGENT_INITIALIZATION__PROGRAM
			var String message = "Rule '" + rule_name + "' not found"
			var String code = ErrorCode.AGENT_INIZIALIZATION__PROGRAM_NOT_FOUND
			return new ErrorType( message, feature, code )
		}
		
	}
}
	